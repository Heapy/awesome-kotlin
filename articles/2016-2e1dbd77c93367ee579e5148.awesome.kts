
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We are pleased to announce the third milestone of the upcoming Kotlin 1.1. This release brings new language features as well as improvements and fixes in the JavaScript backend, compiler and IDEA plugin. The new release also includes all tooling features introduced in Kotlin 1.0.5, and is compatible with IntelliJ IDEA 2016.3 EAP and Android Studio 2.2 and 2.3.

As with other milestone releases, we give **no backwards compatibility guarantees** for new language and library features. Anything introduced in milestone releases of 1.1 is **subject to change** before the final 1.1 release.

Please do share your feedback regarding the new features or any problems that you may run into with this release, via [YouTrack](https://youtrack.jetbrains.com/issues/KT), [forums](https://discuss.kotlinlang.org) and [Slack](http://kotlinlang.slack.com/).

The full changelog for 1.1-M03 is available [here](https://github.com/JetBrains/kotlin/blob/1.1-M03/ChangeLog.md).

## Underscore for unused symbols

You can now use an underscore instead of the name of an unused parameter of a lambda:

```kotlin
foo { _, x -> ... }
```

And an unused variable name in destructuring declarations can now be replaced with an underscore as well.

```kotlin
val (x, _, z) = expr
```

Both cases are described in detail in the appropriate [KEEP](https://github.com/Kotlin/KEEP/blob/master/proposals/underscore-for-unused-parameters.md).

## Underscore in numeric literals

In accordance with Java 8 specification, Kotlin supports now numeric literals with single underscore symbols between digits. For example:

```kotlin
val ONE_MILLION = 1_000_000
```

See the [KEEP](https://github.com/Kotlin/KEEP/blob/master/proposals/underscores-in-numeric-literals.md) for more details and examples.

## Generic Enum values access

The support of the [proposal](https://github.com/Kotlin/KEEP/blob/master/proposals/generic-values-and-valueof-for-enums.md) is landed to the standard library in a form of two intrinsic functions:

```kotlin
inline fun <reified T : Enum<T>> enumValues(): Array<T>
inline fun <reified T : Enum<T>> enumValueOf(name: String): T
```

They allow to enumerate the values of a generic enum type. E.g.

```kotlin
enum class RGB { RED, GREEN, BLUE }

print(enumValues<RGB>().joinToString { it.name }) // prints RED, GREEN, BLUE
```

## Scope control for builder-like DSLs

As described in details in the [KEEP](https://github.com/Kotlin/KEEP/blob/master/proposals/scope-control-for-implicit-receivers.md), DSL authors had problems in expressing scope restrictions for builder-like constructs. E.g., for some html-builder DSL:

```kotlin
table {
  tr {
    tr {} // PROBLEM: Table.tr() is valid here
  }
}
```

To solve the problem we added the `@DslMarker` annotation that allows to control visibility scope in these cases more precisely. For usage example see  [preview version of kotlinx.html library using this feature](https://github.com/Kotlin/kotlinx.html/tree/dsl-markers) (see [HtmlTagMarker](https://github.com/Kotlin/kotlinx.html/blob/dsl-markers/shared/src/main/kotlin/api.kt#L103) and [HTMLTag](https://github.com/Kotlin/kotlinx.html/blob/dsl-markers/shared/src/main/kotlin/htmltag.kt#L5) implementations and [DSL-markers.md](https://github.com/Kotlin/kotlinx.html/blob/dsl-markers/DSL-markers.md) for the preview library usage info).

## Standard library unification

The standard library unification process for different platforms is moving forward. We have started unifying exception types in 1.1-M2 and now some more common types, which are supported on all platforms, are available in `kotlin.*` packages, and are imported by default. These include:

* `ArrayList`, `HashSet`, `LinkedHashSet`, `HashMap`, `LinkedHashMap` in `kotlin.collections`
* `Appendable` and `StringBuilder` in `kotlin.text`
* `Comparator` in `kotlin.comparisons`

On JVM these are just typealiases of the good old types from `java.util` and `java.lang`

## New language features support in the JavaScript backend
n
JavaScript backend now supports the following Kotlin language features on par with JVM backend:

* Coroutines
* Destructuring in lambda parameters
* Unused variable name in destructuring declaration

## JS code generation improvements

JavaScript backend now generates more statically checkable code, which is friendlier to JS code processing tools, like minifiers, optimisers, linters, etc.

## How to Try It

**In Maven/Gradle:** Add [http://dl.bintray.com/kotlin/kotlin-eap-1.1](http://dl.bintray.com/kotlin/kotlin-eap-1.1) as a repository for the build script and your projects; use 1.1-M03 as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list, then press _Check for updates_.

Drive to Kotlin!

"""

Article(
  title = "Kotlin 1.1-M03 is here!",
  url = "https://blog.jetbrains.com/kotlin/2016/11/kotlin-1-1-m03-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ilya Chernikov",
  date = LocalDate.of(2016, 11, 24),
  body = body
)
