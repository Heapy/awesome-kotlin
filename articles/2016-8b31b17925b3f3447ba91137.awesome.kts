
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![](https://d262ilb51hltx0.cloudfront.net/max/800/1*J-1MC3QGbIuwq4tb-yr-iA.png)

_[XKCD, Randall Munroe](https://xkcd.com/303/) // [CC BY-NC 2.5](https://creativecommons.org/licenses/by-nc/2.5/)_

# Kotlin vs Java: Compilation speed

_If you convert an app from Java to Kotlin, will it take longer to compile?_

_This is part 3 in a series of articles on Kotlin._ [_Part 1 discussed_](https://medium.com/keepsafe-engineering/lessons-from-converting-an-app-to-100-kotlin-68984a05dcb6) _converting an Android app from Java to Kotlin, and_[ _part 2 contains_](https://medium.com/keepsafe-engineering/kotlin-the-good-the-bad-and-the-ugly-bf5f09b87e6f) _my thoughts on the Kotlin language._

In an earlier article, [I discussed converting an Android app from Java to 100% Kotlin](https://medium.com/keepsafe-engineering/lessons-from-converting-an-app-to-100-kotlin-68984a05dcb6). The Kotlin codebase was smaller and more maintainable than it’s Java predecessor, and so I concluded that the transition was worth it. But some people don't want to try out Kotlin because they are worried that it might not compile as quickly as Java. That’s definitely a valid concern; no one wants to spend the time to convert their codebase if it will result in long build times. So let’s take a look at the difference in compile times of the [App Lock app](https://play.google.com/store/apps/details?id=com.getkeepsafe.applock), before and after I converted it to Kotlin. I won’t be trying to compare the speed of a single line of Kotlin versus a single line of Java; instead, I’ll try to answer the question of whether converting a codebase from Java to Kotlin will affect its overall build time.

### How I tested build times

I wrote shell scripts to run Gradle builds repeatedly in a variety of scenarios. All tests are performed 10 times consecutively. The project is cleaned before each scenario, and for scenarios that use the [Gradle daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html), the daemon is stopped once before benchmarking that scenario.

All benchmarks in this article were performed on an Intel Core i7–6700 running at 3.4 GHz, with 32GiB of DDR4 memory and a Samsung 850 Pro SSD. The source code was built with Gradle 2.14.1.

### Tests

I wanted to run benchmarks in several common usage scenarios: clean builds with and without the Gradle daemon, incremental builds with no file changes, and incremental builds with a changed file.

Before the transition, App Lock’s Java codebase was 5,491 methods and 12,371 lines of code. After the rewrite, those numbers dropped down to was 4,987 methods and 8,564 lines of Kotlin code. No major architectural changes occurred during the rewrite, so testing the compile times before and after the rewrite should give a pretty good idea about the difference in build times between Java and Kotlin.

#### Clean builds with no Gradle daemon

This is the worst-case scenario for build times in both languages: running a clean build from a cold start. For this test, I disabled the Gradle daemon.

Here’s how long each of ten builds took:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*VM3VcA0f0XdjnTWDjOqgQg.png)

_Ten consecutive clean builds without the Gradle daemon_

The result in this scenario is that Java build times have an average of 15.5 seconds, while Kotlin averages 18.5 seconds: an increase of 17%. Kotlin is not off to a great start, but this isn’t how most people will compile their code.

> For clean builds with no Gradle daemon, Java compiles 17% faster than Kotlin.

It’s much more common to repeatedly compile the same codebase as you make changes to it. That’s the kind of scenario that the Gradle daemon was designed for, so let’s see how the numbers look when using it.

#### Clean builds with the Gradle daemon running

The problem with [JIT compilers](https://en.wikipedia.org/wiki/Just-in-time_compilation), like the JVM, is that they take time to compile code that’s executed on them, and so the performance of a process increases over time as it runs. If you stop the JVM process, that performance gain is lost. When building Java code, you would typically start and stop the JVM every time you build. That forces the JVM to redo work every time you build. To combat this, Gradle comes with a daemon that will stay alive between builds in order to maintain the performance gains from the JIT compilation. You can enable the daemon by passing --_daemon_ to Gradle on the command line, or by adding _org.gradle.daemon=true_ to your _gradle.properties_ file.

Here’s what the same series of clean builds as above looks like, but with the Gradle daemon running:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*l9HeNTP1a2EFCNUsuMlFow.png)

_Ten consecutive clean builds with the Gradle daemon running_

As you can see, the first run takes about the same amount of time as without the daemon, but subsequent runs increase in performance until the fourth run. In this scenario, it’s more useful to look at the average build time after the third run, where the daemon is warmed up. For the warm runs, the average time for doing a clean build in Java is 14.1 seconds, while Kotlin clocks in at 16.5 seconds: a 13% increase.

> For clean builds with the Gradle daemon warmed up, Java compiles 13% faster than Kotlin.

Kotlin is catching up to Java, but is still trailing behind slightly. However, no matter what language you use, the Gradle daemon will reduce build times by over 40%. If you’re not using it already, you should be.

So Kotlin compiles a little slower than Java for full builds. But you usually compile after making changes to only a few files, and incremental builds are going to have different performance characteristics. So let’s find out if Kotlin can catch up where it matters.

#### Incremental builds

One of the most important performance features of a compiler is its use of incremental compilation. A normal build will recompile all source files in a project, but an incremental build will keep track of which files have changed since the last build and only recompile those files and the files that depend on them. This can have a huge impact on compile times, especially for large projects.

Incremental builds were added to Kotlin in [version 1.0.2](https://blog.jetbrains.com/kotlin/2016/05/kotlin-1-0-2-is-here/), and you can enable them by adding _kotlin.incremental=true_ to your _gradle.properties,_ or by [using a command line option](https://kotlinlang.org/docs/reference/using-gradle.html#incremental-compilation).

So how do Kotlin compile times compare to Java’s when incremental compilation is used? Here are the benchmarks with incremental compilation when no files are changed:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*at-xrjHBLoSOzpGHzobUAA.png)

_Ten consecutive incremental builds with no files changed_

Next, we’ll test incremental compilation with a modified source file. To test this, I changed a java file and its Kotlin equivalent before each build. In this benchmark, the source file is a UI file that no other files depend on:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*pFr_QiMoES45By4FrviSxA.png)

_Ten consecutive incremental builds with one isolated file changed_

Finally, let’s look at incremental compilation with a modified source file, where the file is imported into many other files in the project:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*X0JgS3Go9vsMcwCe_95gWA.png)

_Ten consecutive incremental builds with one core file changed_

You can see that the Gradle daemon still takes two or three runs to warm up, but after that the performance of both languages is very similar. With no changes, Java takes 4.6 seconds per warm build, while Kotlin averages 4.5 seconds. When we change a file that isn’t used by any other files, Java requires an average of 7.0 seconds to do a warm build, and Kotlin clocks in at 6.1. And finally, when we change a file that is imported by many other files in the project, Java requires 7.1 seconds to do an incremental build once the Gradle daemon is warmed up, while Kotlin averages 6.0 seconds.

> In the most common configuration — partial builds with incremental compilation enabled — Kotlin compiles as fast or slightly faster than Java.

### Conclusion

We benchmarked a few different scenarios to see if Kotlin could keep up with Java when it comes to compilation times. While Java does beat Kotlin by 10–15% for clean builds, those are relatively rare. The much more common scenario for most developers is partial builds, where incremental compilation makes large improvements. With the Gradle daemon running and incremental compilation turned on, Kotlin compiles as fast or slightly faster than Java.

That’s an impressive result, and one that I didn’t expect. I have to commend the Kotlin team for designing a language that not only has a lot of great features, but can compile so quickly.

If you were holding off on trying out Kotlin because of compile times, you don’t have to worry any more: Kotlin compiles as quickly as Java.

_You can find the raw data I collected for all benchmarks_ [_here_](https://gist.github.com/ajalt/31c4a45001bea4438f313c899fa961a8)_._

Interested in programming Kotlin full time? Apply as a Kotlin Engineer at [Keepsafe](https://getkeepsafe.com/careers.html).

"""

Article(
  title = "Kotlin vs Java: Compilation speed",
  url = "https://medium.com/keepsafe-engineering/kotlin-vs-java-compilation-speed-e6c174b39b5d#.kls4hgglt",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "AJ Alt",
  date = LocalDate.of(2016, 9, 9),
  body = body
)
