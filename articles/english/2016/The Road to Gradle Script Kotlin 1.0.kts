
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Five months ago we [announced the first pre-release of Gradle Script Kotlin](/kotlin-meets-gradle), and we thought now would be a good time to review the progress we’ve made since. We have shipped eight additional pre-releases during that time, and the road to 1.0 is looking clearer every day. So let’s take a look at the ground we’ve covered so far and where we’re going from here, shall we?

## v0.1.0

As you may recall, this is what our [`hello-world` sample](https://github.com/gradle/gradle-script-kotlin/blob/cc14d3/samples/hello-world/build.gradle.kts) looked like at the time of our first release:

```kotlin
import org.gradle.api.plugins.*
import org.gradle.script.lang.kotlin.*

apply<ApplicationPlugin>()

configure<ApplicationPluginConvention> {
    mainClassName = "samples.HelloWorld"
}

repositories {
    jcenter()
}

dependencies {
    "testCompile"("junit:junit:4.12")
}
```

Oh, that annoying `org.gradle.script.lang.kotlin.*` import! The publicly condemned, IDE unfriendly, string-based `"testCompile"` dependency configuration! And of course—for those souls brave enough to have tried them—the infamous `generateKtsConfig` and `patchIdeaConfig` tasks required to get Kotlin-based build scripts working in IDEA. These were early days, no doubt, and they brought with them a few rough edges.

But despite its flaws, the programming language and IDE experience in 0.1.0 was already so good it got us hooked. As for the rough edges, we could already see ways to smooth them out, which led to the release of [0.2.0](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.2.0) one month later.

## v0.2.0

With [implicit imports](https://github.com/gradle/gradle-script-kotlin/issues/33) and a [tooling-friendly alternative to string-based dependency configurations](https://github.com/gradle/gradle-script-kotlin/issues/36), `hello-world` 0.2.0 started looking clean and concise:

```kotlin
apply<ApplicationPlugin>()

configure<ApplicationPluginConvention> {
    mainClassName = "samples.HelloWorld"
}

repositories {
    jcenter()
}

dependencies {
    testCompile("junit:junit:4.12")
}
```

[Seamless project imports](https://github.com/gradle/gradle-script-kotlin/issues/26) meant that Kotlin-based builds in IDEA started working out of the box, and the days of mistyping `generateKtsConfig` and `patchIdeaConfig` were no more.

Perhaps most importantly, 0.2.0’s [support for build script dependencies and external plugins](https://github.com/gradle/gradle-script-kotlin/issues/29) made Gradle Script Kotlin a viable choice for many real-world projects.

## v0.3.0

[0.3.0](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.3.0) was a major milestone for the project, as it was the first version to be included in a production Gradle distribution—[Gradle 3.0](https://github.com/gradle/gradle/releases/tag/v3.0.0), no less!

And 0.3.0 was all about that [Kotlin](https://kotlin.link/)! The [new Kotlin 1.1-M01 compiler](https://blog.jetbrains.com/kotlin/2016/07/first-glimpse-of-kotlin-1-1-coroutines-type-aliases-and-more/), support for [Kotlin-based plugins](https://github.com/gradle/gradle-script-kotlin/issues/84) and [`buildSrc` directories](https://github.com/gradle/gradle-script-kotlin/issues/86) plus some sugary [Kotlin-Groovy interoperability](https://github.com/gradle/gradle-script-kotlin/issues/103) primitives:

```kotlinnp
gradle.buildFinished(closureOf<BuildResult> {
    println("${"$"}action finished") // ${"$"}action refers to BuildResult.getAction()
})
```

With Gradle 3.0 out the door, the #gradle channel of the public [Kotlin Slack](http://kotlinslackin.herokuapp.com/) saw an increase in participation which helped us greatly in prioritizing the work that would come next.

## v0.3.1

We noticed people struggling with the lack of a more type-safe and IDE-friendly way of configuring dependencies, so [0.3.1](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.3.1) came with a [much-improved dependencies DSL](https://github.com/gradle/gradle-script-kotlin/issues/107):

```kotlin
dependencies {

    default(group = "org.gradle", name = "foo", version = "1.0") {
        isForce = true
    }

    compile(group = "org.gradle", name = "bar") {
        exclude(module = "foo")
    }

    runtime("org.gradle:baz:1.0-SNAPSHOT") {
        isChanging = true
        isTransitive = false
    }

    testCompile(group = "junit", name = "junit")

    testRuntime(project(path = ":core")) {
        exclude(group = "org.gradle")
    }
}
```

The [upgrade to Kotlin 1.1-dev-2053](https://github.com/gradle/gradle-script-kotlin/issues/108) notably enhanced the performance of code assistance within IDEA and thanks to a [valuable member of the community](https://github.com/tyvsmith) the first Gradle Script Kotlin [Android sample](https://github.com/gradle/gradle-script-kotlin/tree/96b6fe/samples/hello-android) was published.

## v0.3.2

With [0.3.2](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.3.2) we decided to tackle [the dreaded `it` problem](https://www.youtube.com/watch?v=vv4zh_oPBTw&feature=youtu.be&t=1387) head-on via [runtime code generation of Kotlin extensions](https://github.com/gradle/gradle-script-kotlin/issues/117). What is the dreaded `it` problem? Take the use of [`copySpec`](https://docs.gradle.org/3.1/javadoc/org/gradle/api/Project.html#copySpec(org.gradle.api.Action)) as an example. Prior to 0.3.2, one would have written:

```kotlin
copySpec {
    it.from("src/data")
    it.include("*.properties")
}
```

This syntax didn’t read very well, and was a departure from the fluid, readable DSL Gradle has long been known for. But never fear—with 0.3.2 `it` was gone:

```kotlin
copySpec {
    from("src/data")
    include("*.properties")
}
```

## v0.3.3 and v0.4.0

The recently-released versions [0.3.3](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.3.3) and [0.4.0](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.4.0) shipped the [first](https://github.com/gradle/gradle-script-kotlin/issues/137) of a series of improvements to [multi-project builds](https://github.com/gradle/gradle-script-kotlin/issues/112) including the ability to [define custom build logic using Kotlin in `buildSrc`](https://github.com/gradle/gradle-script-kotlin/blob/7c74044cd84c4c426f1bca9af9f48bf332620c73/samples/multi-project-with-buildSrc/README.md).

[0.4.0](https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.4.0) is available now and will ship with the forthcoming Gradle 3.2 distribution.

## Toward v1.0.0

What’s next, you ask? Here are some of the highlights of our upcoming releases in three key areas:

1.  [Performance](https://github.com/gradle/gradle-script-kotlin/issues/160): Faster project configuration via caching of compiled build scripts ([#31](https://github.com/gradle/gradle-script-kotlin/issues/31)).
2.  [Usability](https://github.com/gradle/gradle-script-kotlin/issues/54): Type-safe accessors for extensions and conventions contributed by plugins ([#159](https://github.com/gradle/gradle-script-kotlin/issues/159)); Comprehensive documentation ([#106](https://github.com/gradle/gradle-script-kotlin/issues/106)).
3.  [Convenience](https://github.com/gradle/gradle-script-kotlin/issues/30): Declarative and tooling-friendly application of plugins, a.k.a., the `plugins` block ([#168](https://github.com/gradle/gradle-script-kotlin/issues/168)).

Altogether, here’s how we envision the `hello-world` sample will look in Gradle Script Kotlin 1.0:

```kotlin
plugins {
    application
}

application {
    mainClassName = "samples.HelloWorld"
}

repositories {
    jcenter()
}

dependencies {
    testCompile("junit:junit:4.12")
}
```

How does that look to you? We’d love to hear what you think.

A big thanks to everyone that’s been along for the ride so far, and if you’re just getting started with Gradle Script Kotlin, welcome!

"""

Article(
  title = "The Road to Gradle Script Kotlin 1.0",
  url = "https://blog.gradle.org/kotlin-scripting-update",
  categories = listOf(
    "Kotlin",
    "Gradle"
  ),
  type = article,
  lang = EN,
  author = "Rodrigo B. de Oliveira",
  date = LocalDate.of(2016, 10, 26),
  body = body
)
