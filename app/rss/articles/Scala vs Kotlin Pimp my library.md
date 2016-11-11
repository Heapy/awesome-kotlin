---
title: 'Scala vs Kotlin: Pimp my library'
url: https://blog.frankel.ch/scala-vs-kotlin/1/
categories:
    - Kotlin
    - Scala
author: Nicolas Fränkel
date: Jul 10, 2016 08:57
---
I’ve been introduced to the world of immutable data structures with the Scala programming language - to write I’ve been introduced to the <abbr title="Functional Programming">FP</abbr> world would sound too presumptuous. Although I wouldn’t recommend its usage in my day-to-day projects, I’m still grateful to it for what I learned: my Java code is now definitely not the same because Scala made me aware of some failings in both the language **and** my coding practices.

On the other hand, I became recently much interested in [Kotlin](https://kotlinlang.org/), another language that tries to bridge between the Object-Oriented and Functional worlds. In this serie of articles, I’d like to compare some features of Scala and Kotlin and how each achieve it.

In this article, I’ll be tackling how both offer a way to improve the usage of _existing_ Java libraries.

## Scala

Let’s start with Scala, as it coined the term [Pimp My Library](http://www.artima.com/weblogs/viewpost.jsp?thread=179766) 10 years ago.

Scala’s approach is based on _conversion_. Consider a base type lacking the desired behavior. For example, Java’s `double` primitive type - mapped to Scala’s `scala.Double` type, is pretty limited.

The first step is to create a new type with said behavior. Therefore, Scala provides a `RichDouble` type to add some methods _e.g._ `isWhole()`.

The second step is to provide an _implicit_ function that converts from the base type to the improved type. The signature of such a function must follow the following rules:

* Have a single parameter of the base type
* Return the improved type
* Be tagged `implicit`

Here’s how the Scala library declares the `Double` to `RichDouble` conversion function:


```scala
private[scala] abstract class LowPriorityImplicits {
    ...
    implicit def doubleWrapper(x: Double) = new runtime.RichDouble(x)
    ...
}
```

An alternative is to create an _implicit class_, which among other requirements must have a constructor with a single parameter of base type.

The final step step is to bring the conversion _in scope_. For conversion functions, it means importing the function in the class file where the conversion will be used. Note that in this particular case, the conversion function is part of the automatic imports (there’s no need to explicitly declare it).

At this point, if a function is not defined for a type, the compiler will look for an imported conversion function that transforms this type to a new type that provides this function. In that case, the type will be replaced with the conversion function.

```kotlin
val x = 45d
val isWhole = x.isWhole // Double has no isWhole() function

// But there's a conversion function in scope which transforms Double to RichDouble
// And RichDouble has a isWhole() function
val isWhole = doubleWrapper(x).isWhole
```

## Kotlin

One of the main reasons I’m cautious about using Scala is indeed the implicit part: it makes it much harder to reason about the code - just like <abbr title="Aspect-Oriented Programming">AOP</abbr>. Homeopathic usage of AOP is a life saver, widespread usage is counter-productive.

Kotlin eschews implicitness: instead of conversions, it provides [extension methods](https://kotlinlang.org/docs/reference/extensions.html#extension-functions) (and properties).

Let’s analyze how to add additional behavior to the `java.lang.Double` type.

The first step is to provide an extension function: it’s a normal function, but grafted to an existing type. To add the same `isWhole()` function as above, the syntax is the following:

```kotlin
fun Double.isWhole() = this == Math.floor(this) && !java.lang.Double.isInfinite(this)
```

As for Scala, the second step is to bring this function in scope. As of Scala, it’s achieved through an import. If the previous function has been defined in any file of the `ch.frankel.blog` package:

```kotlin
import ch.frankel.blog.isWhole

val x = 45.0
val isWhole = x.isWhole // Double has no isWhole() function

// But there's an extension function in scope for isWhole()
val isWhole = x == Math.floor(x) && !java.lang.Double.isInfinite(x)
```

Note that extension methods are resolved **statically**.

> Extensions do not actually modify classes they extend. By defining an extension, you do not insert new members into a class, but merely make new functions callable with the dot-notation on instances of this class.
> 
> We would like to emphasize that extension functions are dispatched statically, i.e. they are not virtual by receiver type. This means that the extension function being called is determined by the type of the expression on which the function is invoked, not by the type of the result of evaluating that expression at runtime.

## Conclusion

Obviously, Scala has one more indirection level - the conversion. I let anyone decide whether this is a good or a bad thing. For me, it makes it harder to reason about the code.

The other gap is the packaging of the additional functions. While in Scala those are all attached to the enriched type and can be imported as a whole, they have to be imported one by one in Kotlin.
