---
title: 'Kotlin 1.0 Release Candidate is Out!'
url: http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-release-candidate-is-out/
categories:
    - Kotlin
    - Release
author: Andrey Breslav
date: Feb 04, 2016 14:39
---
Finally, Kotlin has graduated the Beta and we are happy to present the Release Candidate Build!

**NOTE**: as we [announced earlier](http://blog.jetbrains.com/kotlin/2015/12/kotlin-1-0-beta-4-is-out/), **RC requires all code to be recompiled** to make sure no code compiled with older versions is kept around (please recompile even if you were on the EAP version!).

This blog post gives an overview of the changes made since Beta 4\. Library changes are the biggest in this build. Also, some bugs have been fixed. Full list of changes is available [here](https://github.com/JetBrains/kotlin/releases/tag/build-1.0.0-rc-1036).

![Kotlin 1.0 RC](http://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/02/RC-Banner.png?resize=640%2C330)

_See the discussions on [Hacker News](https://news.ycombinator.com/item?id=11034273) and [Reddit](https://www.reddit.com/r/programming/comments/445jih/jvm_languages_news_kotlin_10_release_candidate_is/)_.

## Language

First of all, as promised before, there has been a clean-up:

*   All previously deprecated language constructs are now errors, not warnings.
*   All deprecated declarations previously generated in the byte code (such as static fields in interfaces etc) have been removed.

Most other language changes are minor tweaks and bug fixes. Some highlights are given below. See the full list [here](https://github.com/JetBrains/kotlin/releases/tag/build-1.0.0-rc-1036).

### Annotations on delegate fields

The new `@delegate:` annotation target (use-site) is now supported. For example, to mark the delegate object as `@Transient`, we can say:

```kotlin
class Example {
    @delegate:Transient
    val foo by Lazy { ... }
}

```

In the byte code, the field holding the delegate will be annotated.

### Type checking for use-site variance

We have fixed a number of annoying bugs connected with use-site variance (type projections). As a result, the compiler may find some previously missed bugs in your code.
For example, in the following case:

```kotlin
val ints = mutableListOf(1, 2, 3)
val strs = mutableListOf("abc", "def")
val comps: MutableList<out Comparable<*>> = ints
comps.addAll(strs) // ?! Adding strings to a list of ints
```

This code was mistakenly accepted before and is rejected now on the last line with the message:

```kotlin
Projected type MutableList<out Comparable<*>> restricts the use of addAll()
```

## Java Interoperability

Some improvements to synthesized properties derived from Java’s get/set pairs:

*   such declarations (as well as SAM-converted methods) are now resolved on par with members;
*   support added for Java setters that return values.

Support added for `@Nullable/@NotNull` annotations from various popular libraries such as `javax.annotations`, Android SDK, etc.
EAP users report:

> Android annotations being recognized broke a lot of my code in a good way

And highlighted bug fixes:

*   Private top-level Kotlin classes are now compiled to package-private Java classes
*   Members of private classes can-not be accessed from non-private inline functions

## Standard Library

*   Library code rearranged into more granular packages (no source changes should be required)
*   Some functions have been made inline
*   Many inline functions (most of them one-liners) can no longer be called from Java code. This will help us reduce the size of the runtime library in the future.
*   All old deprecations have been removed
*   `Map.getOrElse()` and `Map.getOrPut()` now treat keys associated with `null` values as **missing**.
*   `mutableListOf`, `mutableSetOf`, `mutableMapOf` added to construct mutable collections.
*   `toMutableList` added instead of `toArrayList`. The latter is deprecated
*   `associate` and `associateBy` are added to aid construction of maps (instead of `toMap`/`toMapBy`)
*   Comparator- and comparison-related functions are moved to `kotlin.comparisons` package (not imported by default)

More changes [here](https://github.com/JetBrains/kotlin/releases/tag/build-1.0.0-rc-1036)

## Tooling

To enable Android Extensions in Gradle in a more idiomatic way, we now say:

```kotlin
apply plugin: 'kotlin-android-extensions'
```

in the `build.gradle` file (individually for each project).

The old way doesn’t work any more and prints fixing instructions to the output.

## IDE Changes

*   Intention to replace iteration over map entries with a loop using a destructuring declaration
*   Inspection and quickfix to cleanup redundant visibility modifiers
*   Inspection to replace ‘assert’ calls checking that a variable is not null with `!!` or `?: error(...)`
*   Show “Kotlin not configured” notification when opening a .kt file in the IDE if the Kotlin runtime is not configured for the corresponding module
*   Action to generate the `toString()` method
*   Support for implementing members by primary constructor parameters
*   Parameter info popup works for showing type parameters
*   Completion offers name variants based on unresolved identifiers in current file
*   Quickfix for adding missing branches to a `when` expression
*   Support for moving nested classes to the upper level or into another top-level class
*   `@Suppress` now works for IDE inspections

## Installation Instructions

For the users of IntelliJ IDEA, automatic updates may not work in the IDE, so you’ll need to download the plugin and install it from a zip file:

*   Download [here](https://plugins.jetbrains.com/plugin/download?pr=&updateId=23813)
*   Go to _Preferences | Plugins_ and click _Install plugin from disk..._

Sorry for the inconvenience.

## Stay tuned

The final release is approaching, meanwhile — have a nice Kotlin! ![:)](http://i2.wp.com/blog.jetbrains.com/kotlin/wp-includes/images/smilies/simple-smile.png?w=640)

_P.S. See the discussions on [Hacker News](https://news.ycombinator.com/item?id=11034273) and [Reddit](https://www.reddit.com/r/programming/comments/445jih/jvm_languages_news_kotlin_10_release_candidate_is/)_.
