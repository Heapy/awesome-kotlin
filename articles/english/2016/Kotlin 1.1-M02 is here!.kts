
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We’re happy to announce the second milestone release of Kotlin 1.1. This release brings one long-awaited new language feature, **destructuring in lambdas**, as well as many improvements to features introduced in 1.1-M1, including type aliases, coroutines and bound references. The new release also includes all tooling features introduced in Kotlin 1.0.4 and 1.0.5-eap-66, and is fully compatible with IntelliJ IDEA 2016.3 EAP and Android Studio 2.2.

As with Kotlin 1.1-M01, we give **no backward compatibility guarantees** for new language and library features. Anything introduced in milestone releases of 1.1 is **subject to change** before the final 1.1 release.

And once again: please do share your feedback regarding the new language features or any problems that you may run into with this release, via [YouTrack](https://youtrack.jetbrains.com/issues/KT), [forums](http://discuss.kotlinlang.org) and [Slack](https://kotlinlang.slack.com).

The full changelog for 1.1-M02 is available [here](https://github.com/JetBrains/kotlin/blob/1.1-M2/ChangeLog.md).

## Destructuring in Lambdas

Kotlin 1.0 supports [destructuring declarations](https://kotlinlang.org/docs/reference/multi-declarations.html) – a feature that allows you to “unpack” a composite value (such as a data class) and assign its components to several distinct variables. Kotlin 1.1 extends this to **lambda parameters**, letting you unpack a composite variable passed to a lambda and access its components under distinct names. For example, you can use this to iterate over a list of pairs:

```kotlin
listOfPairs.map {
    (a, b) -> a + b
}
```

You can find more details in the [KEEP](https://github.com/Kotlin/KEEP/blob/master/proposals/destructuring-in-parameters.md). Note that the feature is currently supported only for the JVM backend. Nested destructuring, as well as destructuring of arguments passed to regular functions and constructors, is currently unsupported.

## Standard Library

Kotlin 1.1-M02 adds several new APIs to the standard library:

*   distinct `AbstractCollection` and `AbstractMutableCollection` hierarchies to use as base classes for implementing new Kotlin collection classes ([KEEP-53](https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/abstract-collections.md));
*   `Map.toMap()` and `Map.toMutableMap()` extension functions for copying maps ([KEEP-13](https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/map-copying.md))

## Reflection

The reflection library has gained a substantial amount of new features. You can now obtain more useful information out of a KType instance and create custom KType instances, introspect modifiers on declarations, get superclasses or check subtyping, etc. To see what’s new you can scan down [the following commit](https://github.com/JetBrains/kotlin/commit/ed1490dbc43f88696f82e5307df43269ecbb32b1).

## IDE

The IntelliJ IDEA plugin has been extended to support the new 1.1 language features, with new refactorings “Introduce type alias” and “Inline type alias”, an intention action to create a type alias from usage, as well as quickfixes to apply destructuring in lambdas automatically.

## Scripting

Starting with this release, Kotlin supports JSR-223 (the `javax.script` API), allowing you to easily run Kotlin scripts from your application and to use Kotlin as an embeddes scripting language. It also continues the work required to support Kotlin scripting in Gradle build files.

## JavaScript

JavaScript support in 1.1-M02 has been extended to support **type aliases** and **class literals** (`Foo::class`).

In addition to that, we’re working to make more of the Kotlin API available in multiplatform projects. To that end, we’ve defined all the standard exception classes in the `kotlin` package. When targeting the JVM, the Kotlin exceptions are defined as type aliases for the corresponding Java exceptions, and the JS backend provides their full-fledged implementations. We’ve also provided a full Kotlin implementation for the standard collection classes, which is now used in JS projects. (Kotlin on the JVM still uses standard Java collection classes.)

## How to Try It

**In Maven/Gradle:** Add `http://dl.bintray.com/kotlin/kotlin-eap-1.1` as a repository for the build script and your projects; use 1.1-M02 as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list, then press _Check for updates_.

**On [try.kotlinlang.org](http://try.kotlinlang.org/)**. Use the drop-down list at the bottom-right corner to change the compiler version to 1.1-M02.

**With SDKMan**. Run `sdk install kotlin 1.1-M02`.

If you are using [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) library please use updated version 0.1-alpha-2, it’s almost the same, but it’s recompiled with 1.1-M02 compiler. You can follow the updates in the [readme file](https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md).

Have a nice Kotlin!

"""

Article(
  title = "Kotlin 1.1-M02 is here!",
  url = "https://blog.jetbrains.com/kotlin/2016/10/kotlin-1-1-m02-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Denis Zharkov",
  date = LocalDate.of(2016, 10, 20),
  body = body
)
