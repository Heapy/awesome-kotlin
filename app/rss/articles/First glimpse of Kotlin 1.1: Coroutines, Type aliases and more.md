---
title: 'First glimpse of Kotlin 1.1: Coroutines, Type aliases and more'
url: https://blog.jetbrains.com/kotlin/2016/07/first-glimpse-of-kotlin-1-1-coroutines-type-aliases-and-more/
categories:
    - Kotlin
author: Andrey Breslav
date: Jul 14, 2016 15:01
---
While Kotlin 1.0.X releases keep delivering incremental updates and tooling features, we are working on the new language features in Kotlin 1.1. Today we are presenting the first preview version of 1.1, it’s far from Beta, but the brave and curious ones can play with new exciting things (and hopefully give us their invaluable feedback).

## Compatibility

This is not a stable version of Kotlin, and **no compatibility guarantees** are given here: in the future previews of 1.1, syntax, APIs, command-line switches and anything else may be changed. If you need a stable version of Kotlin, please stay on 1.0.X until further notice.

## Feedback

The upside of this temporary lack of guarantees is that we can make immediate use of all the feedback you provide us! The best way to tell us what you think is through [KEEP](https://github.com/Kotlin/KEEP): please leave your comments on the issues associated with proposals mentioned below. The implementations in Kotlin 1.1 M01 are prototypes of the functionality described in the KEEPs.

## Overview

The full changelog for 1.1 M01 is available [here](https://github.com/JetBrains/kotlin/blob/1.1-M1/ChangeLog.md#11-m01-eap-1).

## Coroutines

We all know that blocking is bad under a high load, that polling is a no-go, and the world is becoming more and more push-based and asynchronous. Many languages (starting with C# in 2012) support asynchronous programming through dedicated language constructs such as `async`/`await` keywords. In Kotlin, we generalized this concept so that libraries can define their own versions of such constructs, and `async` is not a keyword, but simply a function.

This design allows for integration of different asynchronous APIs: futures/promises, callback-passing, etc. It is also general enough to express lazy generators (`yield`) and cover some other use cases.

So, meet one of the bigger features of Kotlin 1.1: _coroutines_. This is a traditional CS term for “program components that generalize subroutines for nonpreemptive multitasking”, but we’ll not dive into theory here ![:)](https://i2.wp.com/blog.jetbrains.com/kotlin/wp-includes/images/smilies/simple-smile.png?zoom=1w=64&ssl=1)

```kotlin
fun main(args: Array<String>) {
    val future = async<String> {
        (1..5).map {
            await(startLongAsyncOperation(it)) // suspend while the long method is running
        }.joinToString("\n")
    }

    println(future.get())
}
```

The great thing about coroutines is that they can _suspend_ without blocking a thread, and yet they look like normal sequential code. Please see a [detailed description](https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md) and examples in a dedicated [KEEP repository](https://github.com/Kotlin/kotlin-coroutines) and comment on the [issues there](https://github.com/Kotlin/kotlin-coroutines/issues).

We are prototyping coroutine-based libraries [here](https://github.com/Kotlin/kotlinx.coroutines), to be later included with the Standard Library. This includes JDK’s `CompletableFuture`, asynchronous IO (NIO), RxJava, and off-loading tasks from the UI thread in Swing. The repo contains examples as well as the libraries themselves. To play with it, follow the instructions in the [readme file](https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md).

## Type aliases

In Kotlin 1.1 we can write

```kotlin
typealias Action<T> = (T) -> Unit
```

This means that we can use `Action<T>` interchangeably with `(T) -> Unit`, i.e. it is a true **alias**. Type aliases are useful for abbreviating longer types that are used in multiple places in the code:

* function types with complex signatures: `UserAction = (User, Context) -> ActionResponse`,
* complex generic types: `Multimap<K, V> = Map<K, List<V>>`

In anticipation of your questions: this feature does not cover the use cases where the aliased type is not assignable to the original type (something similar to newtype in Haskell): e.g. if we try to implement units of measurement and say

```kotlin
typealias Length = Double
typealias Weight = Double
```

it won’t do us much good, because `Length` can be freely assigned to `Weight` and vice versa. In fact, they can both be assigned to and from a regular `Double`. We understand the importance of such use cases, and are planning to cover them in the future, most likely through _value classes_, but that’s another story. For now we only have type aliases.

Read more and comment [here](https://github.com/Kotlin/KEEP/issues/4).

## Bound callable references

In Kotlin 1.0 one can obtain a reference to a function (or property) like this: `String::length`, i.e. using a name of the containing class. In 1.1 we are adding _bound references_: i.e. we’ll be able to say `mystr::length` where `mystr` is a variable (or any other expression). Such references are bound to their receiver, and thus are a special case of partial function application (that we are not supporting in the general case, at least for now).

Read more and comment [here](https://github.com/Kotlin/KEEP/issues/5).

## Local delegated properties & Inline properties

Delegated properties have proven to be a very useful abstraction, now we allow them inside functions/code blocks too. For example, we can say:

```kotlin
fun example(foo: (Bar) -> Foo, bar: Bar) {
    val memoizedFoo by lazy { foo(bar) }

    // use memoizedFoo instead of foo to get it computed at most once
    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}
```

DSLs and scripts will also benefit from this feature.

Read more and comment [here](https://github.com/Kotlin/KEEP/issues/25).

We also allow [inlining property accessors](https://github.com/Kotlin/KEEP/issues/34) now.

## Relaxed rules for sealed classes and data classes

We now lift some restrictions on data classes and sealed classes.

Data classes can now be inherited from other classes. Note that automatically generated methods may override those defined in superclasses!

For sealed classes we broaden the scope where their inheritors may be defined: before it was only inside the sealed class itself, now it’s anywhere in the same file.

Read more and comment [here](https://github.com/Kotlin/KEEP/issues/29) and [here](https://github.com/Kotlin/KEEP/issues/31).

## Scripting

As you’ve probably heard, we all will soon be able to write Gradle build scripts in Kotlin which will considerably improve the IDE experience for editing such scripts and make them more reliable through static type checking. This project motivated us to work more on Kotlin scripting in general: we are developing the infrastructure to enable using Kotln scripts in the context of different tools, as well as the plain command-line support.

More details in the [proposal](https://github.com/Kotlin/KEEP/issues/28).

## Java 7/8 support

We are working on the improved support for Java 8: 1.1 fixes the issues with Stream APIs that we used to have (and mitigated with a support library), and adds support for generating default methods in Kotlin interfaces, so that Java clients can implement them seamlessly. Read and comment [here](https://github.com/Kotlin/KEEP/issues/30).

To enable generation of version 8 class files, supply the `-jvm-target 1.8` command line switch.

We are also adding new functions to the Standard Library, and as they rely on Java API version newer than 1.6, we introduce new artifacts: `kotlin-stdlib-jre7` and `kotlin-stdlib-jre8` that carry extra functionality such as `AutoCloseable.use()`, Regex named groups support and stream-related functions. Use these artifacts instead of `kotlin-stdlib` from your Maven/Gradle builds if you need the APIs they add.

Read and discuss the proposals related to stdlib [here](https://github.com/Kotlin/KEEP/labels/stdlib).

## JavaScript

We are actively working on the JavaScript back-end: all the language features available in 1.0 are covered now, and we are close to getting JavaScript (runtime) module systems integrated into the picture. Note that all this functionality is also available in Kotlin 1.0.X.

Find the proposals related to JavaScript [here](https://github.com/Kotlin/KEEP/labels/JS).

## Contributors

We are very grateful to GitHub users [dotlin](https://github.com/dotlin), [Valdemar0204](https://github.com/Valdemar0204), [ensirius](https://github.com/ensirius) and [geoand](https://github.com/geoand) for their contributions to this version!

## How to try it

**In Maven/Gradle**. Add [https://bintray.com/kotlin/kotlin-eap-1.1](https://bintray.com/kotlin/kotlin-eap-1.1) (see instructions under _“Set me up!”_) as a repository to your project. Use version 1.1-M01 for your Kotlin artifacts.

**In the IDE**. If you are running [Kotlin 1.0.3](https://blog.jetbrains.com/kotlin/2016/06/kotlin-1-0-3-is-here/), go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list:

![Configure-Plugin-Updates](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/07/Configure-Plugin-Updates.png?zoom=1.5&w=400&ssl=1)

Press _Check for updates_ in the same dialog, and, when the new version is show, _Install_.

**On [try.kotlinlang.org](http://try.kotlinlang.org/)**. Use the drop-down list at the bottom-right corner to change the compiler version:  
![Screen Shot 2016-07-14 at 20.23.48](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/07/Screen-Shot-2016-07-14-at-20.23.48.png?zoom=1.5&resize=640%2C549&ssl=1)

**With SDKMan**. Run `sdk install kotlin 1.1-M01`.

Your feedback is very welcome, as always.

### Have a nice Kotlin!