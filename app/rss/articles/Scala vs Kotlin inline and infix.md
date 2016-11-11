---
title: 'Scala vs Kotlin: inline and infix'
url: https://blog.frankel.ch/scala-vs-kotlin/3/
categories:
    - Kotlin
    - Scala
author: Nicolas Fränkel
date: Aug 21, 2016 09:02
---
This is the third post in the Scala vs Kotlin comparison serie:

1.  [Pimp my library](/scala-vs-kotlin/1/)
2.  [Operator overloading](/scala-vs-kotlin/2/)

This week, I’d like to address two features: inline and infix - not because they’re related but because neither of them would be enough to fill a post.

Inlining comes from C (and then C++). In those languages, a hint could be provided to the compiler through the `inline` keyword. By doing so, it may replace an inlined function call by the function body itself in order to skip the overhead of a function call.

Infix notation is to be in line with prefix and postfix, it’s relative to the place of the operator compared its 2 operands. Hopefully, the following example is clear enough:

* _Prefix_: **+** 2 2
* _Postfix_: 2 2 **+**
* _Infix_: 2 **+** 2

## Scala

Scala offers inlining through the `@inline` annotation on a function. As for C/C++, this is a an hint to the compiler. As per the [ScalaDocs](http://www.scala-lang.org/api/current/#scala.inline):

> An annotation on methods that requests that the compiler should try especially hard to inline the annotated method.

The compiler has the final say in whether the function will be inlined, or not. On the opposite site, a function can be annotated with `@noinline` to prevent inlining altogether:

> An annotation on methods that forbids the compiler to inline the method, no matter how safe the inlining appears to be.

As for infix annotation, it’s interestingly quite different from the definition above. In this context, it means that dot and parentheses can be omitted while calling functions that have a single parameter. There are some additional constraints:

*   Either the function must have **no** side-effects - be _pure_
*   **Or** the parameter must be a function

```scala
val isLess1 = 1.<(2)
val isLess2 = 1 < 2
```

Lines 1 and 2 are equivalent. Obviously, line 2 is much more readable. Thanks to infix annotation, Scala doesn’t need operators, as every function can not only [look](/scala-vs-kotlin/2/#scala) but be called like an operator.

## Kotlin

In Kotlin, inlining is set with the `inline` _keyword_. However, it’s much more than just a compiler hint: it’s a requirement. Whenever `inline` is used, the compiler **will** inline the function, no matter what.

As such, it’s very important to use inlining only on small functions. Other limitations might include keeping its use to code under our control, _e.g._ to use it only for application code or code that is not part of a library’s public API.

Note that inlining affects both the function itself as well as arguments that are lambdas. To make lambda arguments not inlined, use the `noinline` keyword.

Infix notation is not automatic in Kotlin as it requires the function to be marked with the `infix` keyword. Additionally, the function needs to be attached to a class, either because it’s a member or an extension. Of course, the single parameter still applies.

```kotlin
// Defined in Kotlin's runtime
infix fun and(other: kotlin.Int): kotlin.Int { /* compiled code */ }

val bool1 = 1.and(2)
val bool2 = 1 and 2
```

Be aware that infix notation only looks similar to an operator, it’s still a regular method call underneath.

```kotlin
// This is valid
val bool3 = 1 < 2

// This is not valid, because < is an operator
val bool4 = 1.<(2)
```
