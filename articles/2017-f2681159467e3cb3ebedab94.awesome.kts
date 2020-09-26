

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Congratulations! Today Kotlin 1.1 has reached Beta, and this means that

* it’s time to try it out,
* there’s still time to give us your feedback (and we really need it!),
* the release is coming fairly soon.

![Kotlin 1.1 Beta](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/01/1.1-Beta-Banner-2-01.png)

We’ve seen a lot of interest in Kotlin over the past year, and would like to thank all our users, contributors and supporters. Special thanks to early adopters of new features for their bravery and feedback to our EAP builds!

## An overview of what is coming in Kotlin 1.1

The biggest news of Kotlin 1.1 are

* full support of compilation to **JavaScript**, and
* **Coroutines** on the JVM, Android and JavaScript.

We’ll give some more details about these below, but they are not the only exciting news of 1.1. Many more language improvements and new features are coming (more details are available on our [What’s new](https://kotlinlang.org/docs/reference/whatsnew11.html) page):

* [Type aliases](https://github.com/Kotlin/KEEP/issues/4): `typealias Action<T> = (T) -> Unit`
* [Bound callable references](https://github.com/Kotlin/KEEP/issues/5): `expr::foo`
* Type inference based on getters: `val myString get() = "hi"`
* Compiler plugins for
    * making classes `open` by default
    * generating no-arg constructors by default
    * extension lambdas in SAM conversions
* [Inheritance for `data` classes](https://github.com/Kotlin/KEEP/issues/31)
* [Subclasses of `sealed` classes in the same file](https://github.com/Kotlin/KEEP/issues/29)
* [Destructuring in lambdas](https://github.com/Kotlin/KEEP/blob/master/proposals/destructuring-in-parameters.md): `map.forEach { (k, v) -> ...}`
* [Underscore for unused parameters](https://github.com/Kotlin/KEEP/blob/master/proposals/underscore-for-unused-parameters.md)
* [Scope control for builder-like DSL’s](https://github.com/Kotlin/KEEP/blob/master/proposals/scope-control-for-implicit-receivers.md): `@DslMarker`
* [`provideDelegate`](https://blog.jetbrains.com/kotlin/2016/12/kotlin-1-1-m04-is-here/#provide-delegate) operator convention
* [Local delegated properties](https://github.com/Kotlin/KEEP/issues/25)
* [JDK 8 methods on Kotlin collections](https://github.com/Kotlin/KEEP/blob/master/proposals/jdk-dependent-built-ins.md): `list.parallelStream()`
* [Inline properties](https://github.com/Kotlin/KEEP/blob/master/proposals/inline-properties.md)
* `enumValues()`/`enumValueOf()` for generic access to enums
* [Underscore in numeric literals](https://github.com/Kotlin/KEEP/blob/master/proposals/underscores-in-numeric-literals.md): `1_000_000`

## Deprecation

We deprecate the unfortunate name `mod` that we used for the `%` operator, and replace it with `rem`, which is semantically correct and agrees with existing libraries such as `java.math.BigInteger`. Deprecation warnings and the tooling will guide you through the migration process.

## JavaScript

It’s simple: the full Kotlin language can be now compiled to JavaScript. It doesn’t mean that we have ported all of the JDK into the browser: the language and its Standard Library are not coupled with JDK, but you can use Kotlin strings, collections, sequences, arrays and other core APIs on JS as well as JVM/Android.

Numerous popular JS libraries will be available through typed headers (converted from [DefinitelyTyped](https://github.com/DefinitelyTyped/DefinitelyTyped)). We support all popular runtime module systems for JavaScript as well as [webpack](https://webpack.github.io/) and other important tools.

We’ll dedicate a lot of effort in Kotlin 1.2 and beyond to making the JavaScript tooling smooth and helpful. Our goal is to enable pleasant full-stack development with Kotlin.

## Coroutines

Honestly, it’s hard to over-emphasize coroutines. The future has come, and we are stuck with it: we need non-blocking asynchronous APIs to keep up with the loads of data we are processing. We’ve been through callback hell and conquered it, but we deserve better. We want to simply write the code following its natural _sequential_ logic, and let the compiler figure the asynchrony out for us. This is what coroutines are about: async/await, generate/yield, non-blocking IO, Rx and much more brought under the single unified paradigm of a _suspending function_. Such a function (or lambda) represents a computation that can be suspended (without blocking any threads) and resumed later.

```kotlin
future {
    val original = asyncLoadImage("...original...") // creates a Future
    val overlay = asyncLoadImage("...overlay...") // creates a Future
    ...
    // suspend while awaiting the loading of the images
    // then run `applyOverlay(...)` when they are both loaded
    return applyOverlay(original.await(), overlay.await())
}
```

The main benefit of coroutines is their flexibility:

* The language part is minimal
* Everything can be written as a library
* Libraries are in total control of all aspects of suspending and resuming computations: threads, exceptions and other aspects of computation are entirely customizable.

We have written a set of libraries for interesting common use cases: [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)

Read more about coroutines [here](https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md).

**An important note**. With all the benefits that they bring, Kotlin coroutines are a fairly new design that needs extensive battle-testing before we can be sure it’s 100% right and complete. This is why we will release it under an “experimental” opt-in flag. We do not expect the language rules to change, but APIs may require some adjustments in Kotlin 1.2.

* Command line: `-Xcoroutines=enabled`
* Gradle: `kotlin.coroutines=enable` in `gradle.properties` or `local.properties`
* Maven: `<configuration> <args> <arg>-Xcoroutines=enable</arg> </args> </configuration>`
* IDE: Use a quick-fix (Alt+Enter) or modify the facet options (_Project Structure -> Modules -> Your Module -> Compiler -> Coroutines (experimental)_)

## Standard Library, Tooling and Frameworks

Kotlin’s Standard Library is getting updated with [many useful utilities](https://kotlinlang.org/docs/reference/whatsnew11.html#standard-library) and extensions including those specific for JDK 7 and 8.

Our collaboration with [Gradle](https://blog.gradle.org/kotlin-meets-gradle) has resulted in gradle-script-kotlin which means that you can now write type-safe build scripts for Gradle, using Kotlin scripting.

We now support JSR 223, which is utilized by [the Spring Framework](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0) along with type-safe DSLs and other things.

## How to Try It

As with other pre-release versions, we give **no backward compatibility guarantees** for Kotlin 1.1‑Beta. Moreover, when we reach final RC, all binaries produced by pre-release versions will be outlawed by the compiler: you’ll be required to recompile everything that was compiled by 1.1‑M0x and Beta (all the code from 1.0.x is perfectly fine without recompilation).

**In Maven/Gradle:** Add [http://dl.bintray.com/kotlin/kotlin-eap-1.1](http://dl.bintray.com/kotlin/kotlin-eap-1.1) as a repository for the build script and your projects; use `1.1.0-beta-17` as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list, then press _Check for updates_.

The command-line compiler can be downloaded from the [Github release page](https://github.com/JetBrains/kotlin/releases/tag/v1.1-beta).

**On [try.kotlinlang.org](http://try.kotlinlang.org/)**. Use the drop-down list at the bottom-right corner to change the compiler version to 1.1‑Beta.

Let’s Kotlin!
"""

Article(
  title = "Kotlin 1.1 Beta Is Here!",
  url = "https://blog.jetbrains.com/kotlin/2017/01/kotlin-1-1-beta-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2017, 1, 19),
  body = body
)
