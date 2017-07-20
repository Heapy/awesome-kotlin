

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We’re happy to announce the second beta of Kotlin 1.1. Please give the new version a try – your feedback is essential for ensuring that we can deliver a quality release.

Since the first beta release, we’ve mostly been focused on stability, bugfixes, and improving the key focus areas of this release: coroutine support and the JavaScript backend. The full list of changes since 1.1 Beta can be found in the [changelog](https://github.com/JetBrains/kotlin/blob/0e1b61b422bd0d006158d8b68fa34e960853c5c6/ChangeLog.md). And if you’re interested in a recap of everything added in version 1.1, check out our [what’s new page](https://kotlinlang.org/docs/reference/whatsnew11.html).

## Migration Notes

For JavaScript projects, we’ve changed the name of the artifact for the standard library. Instead of `kotlin-js-library`, it is now `kotlin-stdlib-js`. You’ll need to update your Maven and Gradle scripts accordingly when you update to 1.1 beta 2 or a newer build.

In addition to that, testing support classes (in the package `kotlin.test`) for JavaScript are now packaged as a separate artifact, as it was previously done for the Java version. If you’re using kotlin.test in your JS project, add a dependency on `kotlin-test-js`.

The coroutines APIs in the Kotlin standard library have been moved to the `kotlin.coroutines.experimental` package; you need to update your imports if you’ve used these APIs in your code. See [Andrey’s forum post](https://discuss.kotlinlang.org/t/experimental-status-of-coroutines-in-1-1-and-related-compatibility-concerns/2236) for the background on this change.

We’ve also made it easier to enable the experimental coroutine support in your Gradle projects. Instead of editing gradle.properties, you can add the following snippet to your build.gradle:

```kotlin
kotlin {
    experimental {
        coroutines 'enable'
    }
}
```

If you’re using the [kotlinx.coroutines library](https://github.com/kotlin/kotlinx.coroutines), please update your dependency to version `0.6-beta`. Earlier versions of the library are incompatible with this Kotlin update.

## New Features

We did add a few last-minute features in this beta. Here are the most important ones:

* The compiler now reports a warning if you declare an extension that has the same signature as a member of the same class and will always be shadowed (for example, `String.length()`)
* Type inference for member references passed to generic functions is now much improved ([KT-10711](https://youtrack.jetbrains.com/issue/KT-10711))
* The `minus` operator can now be used with maps, returning a copy of the map with the given keys removed. The `-=` operator can be used on mutable maps to remove the given keys from the map.
* It is now possible to access the delegate instance of a delegated property using `KPropertyN.getDelegate()` (see [KT-8384](https://youtrack.jetbrains.com/issue/KT-8384) for details);
* Intention (contributed by Kirill Rakhman) to merge two nested `if` statements;
* Support for building Android projects when the Jack toolchain is enabled (`jackOptions { true }`);
* Intention (contributed by Kirill Rakhman) to generate `View` constructors in Android applications.

## Source Compatibility with Kotlin 1.0

Another area to which we paid a lot of attention in this update is **source compatibility with Kotlin 1.0**. This allows you to try Kotlin 1.1, even if your team is using Kotlin 1.0, without worrying that you’ll break the build by using some of the features added in the new release.

To enable the compatibility mode:

* For Maven, Ant and the command-line compiler, set the `-language-version` compiler argument to 1.0.
* In a Gradle build, add `kotlinOptions { languageVersion = "1.0" }` to your `compileKotlin` task.
* In the IDE, specify the language version in the Kotlin facet settings or in Settings | Build, Execution, Deployment | Compiler | Kotlin Compiler

## How to try it

**In Maven/Gradle:** Add `http://dl.bintray.com/kotlin/kotlin-eap-1.1` as a repository for the build script and your projects; use `1.1.0-beta-38` as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list, then press _Check for updates_.

The command-line compiler can be downloaded from the [Github release page](https://github.com/JetBrains/kotlin/releases/tag/v1.1-beta2).

**On [try.kotlinlang.org](http://try.kotlinlang.org/)**. Will be available soon.

Let’s Kotlin!
"""

Article(
  title = "Kotlin 1.1 Beta 2 is here",
  url = "https://blog.jetbrains.com/kotlin/2017/02/kotlin-1-1-beta-2-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2017, 2, 2),
  body = body
)
