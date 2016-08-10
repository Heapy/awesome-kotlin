---
title: 'JDK7/8 features in Kotlin 1.0'
url: https://discuss.kotlinlang.org/t/jdk7-8-features-in-kotlin-1-0/1625
categories:
    - Kotlin
author: Ilya Gorbunov
date: Apr 21, 2016 19:36
---
Currently it's rather inconvenient to use those methods of mapped builtin types, that were added in JDK8\. These include `Collection.stream()`, `Map.getOrDefault(K, V)`, etc. We have several long-standing issues related in our tracker: [KT-9194](https://youtrack.jetbrains.com/issue/KT-9194), [KT-5175](https://youtrack.jetbrains.com/issue/KT-5175) and [KT-10864](https://youtrack.jetbrains.com/issue/KT-10864).

This happens because Kotlin targets JDK 1.6 as the minimum JDK version, so it can't expose those members of builtin types that are not available in JDK 1.6.
We plan to remove the limitation in Kotlin 1.1 by exposing all builtin type members available in the target platform (excluding some blacklisted exceptions), but as a short-term fix we could provide a way to call these methods via extensions.

We have collected those extensions in a small Kotlin library: [kotlinx.support](https://github.com/Kotlin/kotlinx.support).
It consists of two artifacts: **kotlinx-support-jdk8** and **kotlinx-support-jdk7**. The former has a dependency on the latter, so if you target JDK8 you could only reference kotlinx-support-jdk8 and have the latter as a transitive dependency.

These artifacts are available for downloading from the maven repository on bintray [https://bintray.com/kotlin/kotlinx.support/kotlinx.support](https://bintray.com/kotlin/kotlinx.support/kotlinx.support) and also on jcenter [https://bintray.com/bintray/jcenter?filterByPkgName=kotlinx.support](https://bintray.com/bintray/jcenter?filterByPkgName=kotlinx.support).

### kotlinx-support-jdk8

This library provides extensions to call default methods of collection interfaces introduced in JDK8.

```kotlin
import kotlinx.support.jdk8.collections.*
import kotlinx.support.jdk8.text.*

// some quite more verbose way to say 'listOf<String>().flatMap { it.asIterable() }'
val chars = listOf("abc", "def").stream()
                .flatMap { it.chars().boxed().map { it.toChar() } }
                .collect(Collectors.toList<Char>())
```

### kotlinx-support-jdk7

This library exposes `Throwable` methods to work with suppressed exceptions, and introduces [long-awaited](https://youtrack.jetbrains.com/issue/KT-5899) `use` extension for `AutoCloseable`. Note that this `use` being imported explicitly has precedence over `use` from Standard Library and thus can be used for `Closeable` as well.

```kotlin
import kotlinx.support.jdk7.use

class Resource(val faultyClose: Boolean = false) : Closeable {

    override fun close() {
        if (faultyClose)
            throw IOException("Close failed")
    }
}

// the IOException thrown by 'close' would be added
// to the list of suppressed exceptions of IllegalStateException
// thrown from lambda passed to 'use'
val result = Resource(faultyClose = true).use {
    throw IllegalStateException("operation failed")
}
```
