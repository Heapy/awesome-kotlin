---
title: 'A new hope for the JVM: Kotlin'
url: http://engineering.facile.it/blog/eng/kotlin-intro/
categories:
    - Kotlin
author: Giacomo Bresciani
date: Oct 17, 2016 10:08
---
![Kotlin](http://engineering.facile.it/images/kotlin-intro/logo_Kotlin.svg)

## Premise

Java is an **old** programming language. Version 1.0 was released in 1996 by Sun Microsystems and even though it has evolved and grown a lot over the past twenty years it is still carrying on some bad design choices such as _null_ (ask [Tony Hoare](https://en.wikipedia.org/wiki/Tony_Hoare?section=3#Apologies_and_retractions)), primitive types or lack of a proper function type. With the last version of the language (Java 8) Java tried to address some of these problems introducing concepts such as [`Optional`](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or [lambda expression](http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html). Although these additions clearly represent a step forward for the language I still have the feeling that they are just _patches_ applied to **mitigate problems** and not to solve them at their very source. For example `Optional` could be used to reduce NPE (Null Pointer Exception) but it is clearly not designed for [this purpose](https://twitter.com/mariofusco/status/780770300178956289) and lambda expressions, implemented in Java 8 with SAM types, still force you to write an interface only to define a _function_.

## The Android world

All the above concerns about Java are even more problematic within the Android world where, due to the notorious [fragmentation](https://developer.android.com/about/dashboards/index.html) (a huge amount of devices are stuck with an **outdated VM**), you are forced to target lower Java versions (6 and 7). Google is addressing the problem with its new compiler [Jack](https://source.android.com/source/jack.html) that enables _some_ of the features of Java 8 maintaining backward compatibility with older OS versions. But still it lets us deal with the verbosity of the language and it doesn’t truly solve the problem.

## A new hope

![Kotlin Hello World!](http://engineering.facile.it/images/kotlin-intro/kotlin_helloworld.png)

Back in 2011 the JetBrains team (the guys behind IntelliJ and so Android Studio) unveiled [Kotlin](https://kotlinlang.org/), a new programming language that targets the JVM (and can also be compiled to JavaScript). Kotlin is a statically-typed language that combines Object Oriented and functional features enforcing no particular philosophy of programming, and it introduces a whole new set of concepts and tools that helps making the code **safer, cleaner and much more readable**. Thanks to its nature it works everywhere Java do and it is also **interoperable** with it, meaning it will not force you to rewrite the entire codebase to taste it: you can add it to your project a little at a time ([maybe starting with tests](https://medium.com/@sergii/using-kotlin-for-tests-in-android-6d4a0c818776#.lyvd3h43x) 😉). It also features a REPL `kotlinc-jvm` that allows you to test language features with no effort (see the [doc](https://kotlinlang.org/docs/tutorials/command-line.html#running-the-repl) form more info). I am going to rapidly cover some features of Kotlin that address the previously mentioned Java limitations.

### Null-safety

In Kotlin **a variable cannot be null**. If you want or need a variable to be _nullable_ you have to add `?` to the variable type:

```kotlin
val x: Int = null // compile error
val y: Int? = null // ok
```

Thanks to this information the compiler sees `Int` and `Int?` as two completely different types and can therefore enforce the [null-safety](http://kotlinlang.org/docs/reference/null-safety.html) of your variables. The `?.` allows you to safe call methods on nullable variables without throwing NPE but simply returning null at the end of the call chain:

```kotlin
val x: Int? = null
x?.toString()?.substring(2) // no NPE, returns null
```

The `?:` operator (Elvis operator) allows you to provide a “default” value when the variable is `null`:

```kotlin
// The two expressions are semantically equivalent:
text?.length ?: -1
(text.length != null) ? text.length  : -1
```

### Higher-Order Functions and Lambdas

In Kotlin is possible to declare a method (or more generally a function) that returns or takes another **function as parameter**. The syntax to define the [function type](https://kotlinlang.org/docs/reference/lambdas.html) is similar to other languages such as Scala or Swift and is very **intuitive**:

```kotlin
val function: (T1, T2) -> R = {t1, t2 -> r}
```

### Data classes

```kotlin
data class City(val name: String, val state: String)
```

[Data classes](https://kotlinlang.org/docs/reference/data-classes.html) address the verbosity of Java when dealing with classes that have the only purpose to hold data. With a single line you get `equals()/hashCode()`, `toString()` and getters/setters for free (and if you are a Java developer you already knows the benefits!)

# Conclusions

Java has to maintain backward compatibility with previous versions and still has to support the huge amount of developers and codebases present all around the world; therefore it is natural that every new feature and design change is to be considered, weighted and reasoned really carefully, inevitably **slowing down its evolution**. But this does not have to mean that us, as Android developers, “tied” to the JVM, should not try more modern and advanced languages such as Kotlin. At bottom, a part of our job (one of the best!) is to try and **experiment** new technologies and to **learn** new concepts and techniques that improve our ability to address problems in the best possible way (and of course, to have some fun 😄).

I think that it is fundamental for a software engineer to be **exposed to more than a single programming language**: learning new patterns, exploring other programming paradigms or simply using and understanding a never-seen syntax has an immeasurable value for our growth and most of the times it turns out to be unexpectedly useful even when coding with ”our” language. So why not do it with a language that allows us to continue working on projects targeting our beloved JVM?
