
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![](http://beust.com/pics/parallel-build-result.png)

I’ve always wondered why the traditional build systems we use on the JVM (Maven, Gradle) don’t support parallel builds (edit: this is not quite true, see the comments), so now that I developed my [own build system](http://beust.com/kobalt), I decided to find out if there was any particular difficulty to this problem that I had overlooked. This turned out to be easier than I anticipated and with interesting benefits. Here is a preliminary result of my findings.

## Defining parallel builds

While most builds are sequential by nature (task B can’t usually run until task A has completed), projects that contain multiple sub projects can greatly benefit from the performance boost of parallel builds. This speed up will obviously not apply to single projects or subprojects that have direct dependencies on each other (although this is not quite true as we will see below). If you’re curious, the engine that powers Kobalt’s parallel builds is an improved version of TestNG’s own `DynamicGraphExecutor`, which I described in details [in this post](http://beust.com/weblog/2009/11/28/hard-core-multicore-with-testng/).

In order to measure the efficacy of parallel builds, I needed a more substantial project, so I picked [ktor](https://github.com/Kotlin/ktor), Kotlin’s web framework developed by JetBrains. ktor is interesting because it contains more than twenty sub projects with various dependencies with each other. Here is a dependency graph:

![](http://beust.com/pics/ktor-dependencies.png)

You can see that `core` is the main project that everybody else depends on. After that, the dependencies open up and we have the potential to build some of these projects in parallel: `locations`, `samples`, etc...

I started by converting the `ktor` build from Maven to Kobalt. Right now, `ktor` is made of about twenty-five `pom.xml` files that add up to a thousand lines of build files. Kobalt’s build file (`Build.kt`) is one file of about two hundred lines, and you can [find it here](https://github.com/cbeust/ktor/blob/kobalt/kobalt/src/Build.kt). The fact that this build file is a pure Kotlin file allows to completely eliminate the redundancies and maximize the reuse of boiler plate code that most sub projects define.

Extracting the dependencies from `Build.kt` is trivial thanks to Kobalt’s convenient syntax to define dependencies:

```kotlin
$ grep project kobalt/src/Build.kt
val core = project {
val features = project(core) {
val tomcat = project(core, servlet) {
```

We see the `core` project at the top of the dependency graph. Then `features` depends on `core`, `tomcat` depends on both `core` and `servlet` and so on...

So without further ado, let’s launch the build in parallel mode and let’s see what happens:

```
$ ./kobaltw --parallel assemble
```

At the end of a parallel build, Kobalt optionally displays a summary of the way it scheduled the various tasks. Here is what we get after building the project from scratch:

```
╔══════════════════════════════════════════════════════════════════════════════════════════════════════
║  Time (sec) ║ Thread 39           ║ Thread 40           ║ Thread 41           ║ Thread 42           ║
╠══════════════════════════════════════════════════════════════════════════════════════════════════════
║  0          ║ core                ║                     ║                     ║                     ║
║  45         ║ core (45)           ║                     ║                     ║                     ║
║  45         ║                     ║ ktor-locations      ║                     ║                     ║
║  45         ║                     ║                     ║ ktor-netty          ║                     ║
║  45         ║                     ║                     ║                     ║ ktor-samples        ║
║  45         ║ ktor-hosts          ║                     ║                     ║                     ║
║  45         ║                     ║                     ║                     ║                     ║
║  45         ║ ktor-hosts (0)      ║                     ║                     ║                     ║
║  45         ║ ktor-servlet        ║                     ║                     ║                     ║
║  45         ║                     ║                     ║                     ║                     ║
║  45         ║                     ║                     ║                     ║                     ║
║  45         ║                     ║                     ║                     ║ ktor-samples (0)    ║
║  45         ║                     ║                     ║                     ║ ktor-freemarker     ║
║  49         ║                     ║                     ║                     ║ ktor-freemarker (3) ║
...
╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝

PARALLEL BUILD SUCCESSFUL (68 seconds, sequential build would have taken 97 seconds)
```

The “Time” column on the left describes at what time (in seconds) each task was scheduled. Each project appears twice: when they start and when they finish (and when they do, the time they took is appended in parentheses).

## Analyzing the thread scheduling

As you can see, `core` is scheduled first and since all the other projects depend on it, Kobalt can’t build anything else until that project completes, so the other four threads remain idle during that time. When that build completes forty-five seconds later, Kobalt now determines that quite a few projects are now eligible to build, so they get scheduled on all the idle threads: `ktor-locations`, `ktor-netty`, etc... The first to complete is `ktor-hosts` and Kobalt immediately schedules the next one on that thread.

Finally, Kobalt gives you the complete time of the build and also how long a sequential build would have taken, calculated by simply adding all the individual project times. It’s an approximation (maybe these projects would have been built faster if they weren’t competing with other build threads) but in my experience, it’s very close to what you actually get if you perform the same build sequentially.

Obviously, the gain with parallel build is highly dependent on the size of your projects. For example, if project C depends on projects A and B and these two projects are very small, the gain in parallelizing that build will be marginal. However, if A and B are both large projects, you could see your total build time divided by two. Another big factor in the gain you can expect is whether you use an SSD. Since all these parallel builds are generating a lot of files in various directories concurrently, I suspect that a physical hard drive will be much slower than an SSD (I haven’t tested, I only have SSD’s around).

## Taking things further

When project B depends on project A, it certainly looks like you can’t start building B until A has completed, right? Actually, that’s not quite true. It’s possible to parallelize (or more accurately, vectorize) such builds too. For example, suppose you launch `./kobaltw test` on these two projects:

```kotlin
$ ./kobalw test
----- A:compile
----- A:assemble
----- A:test
----- B:compile
----- B:assemble
----- B:test
```

But the dependency of B on A is not on the full build: B certainly doesn’t need to wait for A to have run its tests before it can start building. In this case, B is ready to build as soon as A is done assembling (i.e. created A.jar). So here, we could envision having threads scheduled at the task level, and not at the project level. So what we could really do is:


```
╔═════════════════════════════════════════════════════════╗
║  Time (sec) ║ Thread 39           ║ Thread 40           ║
╠═════════════════════════════════════════════════════════╣
║             ║ A:compile           ║                     ║
║             ║ A:assemble          ║                     ║
║             ║ A:test              ║ B:compile           ║
║             ║                     ║ B:assemble          ║
║             ║                     ║ B:test              ║
╔═════════════════════════════════════════════════════════╗
║  Time (sec) ║ Thread 39           ║ Thread 40           ║
╠═════════════════════════════════════════════════════════╣
║             ║ A:compile           ║                     ║
║             ║ A:assemble          ║                     ║
║             ║ A:test              ║ B:compile           ║
║             ║                     ║ B:assemble          ║
║             ║                     ║ B:test              ║
```

As you can see above, Kobalt schedules `B:compile` as soon as `A:assemble` has completed while starting `A:test` on a separate thread, resulting in a clear reduction in build time.

This task-based approach can improve build times significantly since tests (especially functional tests) can take minutes to run.

## Wrapping up

I started implementing parallel builds mostly as a curiosity and with very low expectations but I ended up being very surprised to see how well they work and how they improve my build times, even when just considering project-based concurrency and not task-based concurrency. I’d be curious to hear back from Kobalt users on how well this new feature performs on their own projects.

And if you haven’t tried Kobalt yet, it’s extremely easy [to get started](http://beust.com/kobalt).

"""

Article(
  title = "The Kobalt diaries: Parallel builds",
  url = "http://beust.com/weblog/2016/08/06/the-kobalt-diaries-parallel-builds/",
  categories = listOf(
    "Kotlin",
    "Kobalt"
  ),
  type = article,
  lang = EN,
  author = "Cédric Beust",
  date = LocalDate.of(2016, 8, 6),
  body = body
)
