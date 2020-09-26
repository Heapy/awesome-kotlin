
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We’ve been using the [Kotlin](https://kotlinlang.org) programming language for a few weeks on our latest project to perform technical experiments, explore the problem space, and write a few HTTP services. I’ve also ported [Hamcrest](https://github.com/hamcrest/JavaHamcrest) to Kotlin, as [HamKrest](https://github.com/npryce/hamkrest), to help us write tests, and written a small library for [type safe configuration](https://github.com/npryce/konfig) of our services.

## Why Kotlin?

The organisation I’m working with has mature infrastructure for deploying JVM services in their internal [PaaS](https://en.wikipedia.org/wiki/Platform_as_a_service) cloud. They use a mix of Java and Scala but have found Scala builds too slow. Watching my colleague struggle to use Java 8 streams to write what should have been basic functional map-and-fold code, I decided to have a look at some other “post-Java” JVM languages.

We wanted a typed language. We wanted [language aware editing](http://c2.com/cgi/wiki?LanguageAwareEditor). And we wanted a language that had an organisation behind its development and enough people using it that we could get questions answered, even the stupid ones we’d be likely to ask while learning.

That eliminated dynamically typed languages ([Groovy](http://www.groovy-lang.org/), [JavaScript](http://openjdk.java.net/projects/nashorn/), [Clojure](http://clojure.org/)) and languages that are less popular or have small, informal development teams behind them ([Xtend](http://www.eclipse.org/xtend/index.html), [Gosu](https://gosu-lang.github.io/), [Fantom](http://fantom.org/), [Frege](https://github.com/Frege/frege), etc.). In the end it came down to Red Hat’s [Ceylon](http://ceylon-lang.org/) and JetBrains’ [Kotlin](https://kotlinlang.org). Of the two, Ceylon is the most innovative, and therefore (to me, anyway) the most interesting, but Kotlin met more of our criteria. Kotlin has a more active community, is being used for commercial development by JetBrains and a number of other companies[<sup>1</sup>](#fn1), has good editing support within [IntelliJ](http://www.intellij.com), has an active community on social media, and promises easy interop with existing Java libraries.

## Good points

Kotlin was very quick to learn. Kotlin is a conservative increment to Java that smooths off a lot of Java’s rough edges. Kotlin is small and regular, with few special cases and gotchas to learn. In many ways it feels a bit like a compiled, typed Python with curly brace syntax.

The type system is a breath of fresh air compared to Java. Type inferencing makes code less cluttered. There is no distinction between primitive & reference types. Generics and subtyping work together far better than Java: the type system uses [declaration site variance](https://kotlinlang.org/docs/reference/generics.html#declaration-site-variance), not [use site variance](https://docs.oracle.com/javase/tutorial/java/generics/bounded.html), and variance does not usually have to be specified at all for functions. You never have unavoidable compiler warnings, as you do with Java’s type system.

Functional programming is more convenient in Kotlin than Java. You can define free-standing functions and constants at the top level of a module, instead of having to define them in a [“Utils” class](https://en.wikipedia.org/wiki/Utility_class). Function definitions can be nested. There is language support for immutable value types (aka “[data classes](https://kotlinlang.org/docs/reference/data-classes.html)”) and algebraic data types (“[sealed classes](https://kotlinlang.org/docs/reference/classes.html#sealed-classes)”). Null safety is enforced by the type system and variables and fields are non-nullable by default. The language defines standard function types and a lambda syntax for anonymous functions.

You can define [extension methods](https://kotlinlang.org/docs/reference/extensions.html) on existing types. They are only syntactic sugar for free-standing functions, but nevertheless can lead to more concise, expressive code. The Kotlin standard library defines a number of useful extension methods, especially on the Iterable and String types.

Kotlin supports, and carefully controls, [operator overloading](https://kotlinlang.org/docs/reference/operator-overloading.html). You can overload operators that act as functions (e.g. arithmetic, comparison, function call) but not those that perform flow control (e.g. short-cut logical operators). You cannot define your own operators, which will stop me going down the rabbit hole of “ascii-art programming”, which I found hard to resist when writing Scala.

[Class definitions](https://kotlinlang.org/docs/reference/classes.html) require a lot less boilerplate than in Java. Using old fashioned getter-and-setter style Java code is made more convenient by [language support for bean properties](https://kotlinlang.org/docs/reference/properties.html).

[Anonymous extension methods](https://kotlinlang.org/docs/reference/lambdas.html#function-literals-with-receiver) (borrowed from Groovy, I believe) make [domain specific embedded languages](https://kotlinlang.org/docs/reference/type-safe-builders.html) easier to write. Method chaining builder APIs are unnecessary in Kotlin and more awkward than using the [apply](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/apply.html) function to set properties of an object.

Kotlin has a philosophy of preferring behaviour to be explicitly specified. For example, there is no automatic coercion between numeric types, even from low to high precision. That means you have to explicitly convert from Int to Long, for example. I like that, but I expect some people will find it annoying.

## Surprises

### I didn’t miss destructuring pattern match

Kotlin doesn’t have destructuring pattern match & language support for tuples. [A limited form of destructuring](https://kotlinlang.org/docs/reference/multi-declarations.html) can be used in for loops and assignments. The [when expression](https://kotlinlang.org/docs/reference/control-flow.html#when-expression) is an alternative conditional expression that doesn’t do destructuring.

I was surprised to find that I didn’t really miss destructuring. Without tuples you have to use data classes with named fields. The flow-sensitive typing then works rather well where destructuring would be necessary in a language where the type system is not flow-sensitive. For example:

```kotlin
sealed class Result<out T> {
   class Success<T>(val value: T) : Result<T>()
   class Failure(val exception: Throwable): Result<Any>()
}

...

val e : Result<String> = doSomething()
if (e is Result.Success<String>) {
    // e is known to be a Result.Success<String> and
    // can be used as such without a downcast
    println(e.value)
}
```

### Sealed classes cannot be data classes

An algebraic data type cannot be be a data class, so you have to write equals, hashCode, etc. yourself. This is a surprising omission, since I always want an algebraic data type to be a value type. The Kotlin developers say that this will be fixed after version 1.0 is released.

### Functions are not quite first-class objects

There’s a difference between f1 and f2 below:

```kotlin
val f1 = {i:Int -> i+1}
fun f2(i:Int) = i + 1
```

Code can refer to the value of `f1` directly, but must use the “::” operator to obtain a reference to `f2`:

```kotlin
val f = ::f2
```

### Null safe operators push me to write more extension functions

The null safe operators (`?.` and `?:`) only help when dereferencing a nullable reference. When you have a nullable reference and want to pass a value (if it exists) to a function as a parameter, you have to use the awkward construct:

```kotlin
nullableThing?.let{ thing -> fn(thing, other, parameters) }
```

I find myself refactoring my functions to extension functions to reduce the syntactic noise, letting me rewrite the code above as:

```kotlin
nullableThing?.fn(other, parameters)
```

Is that a good thing? I’m not sure. Sometimes it feels a little forced.

### Companion objects

Kotlin borrows the concept of [companion objects](#companion-objects) from Scala. Why can’t classes be objects? Perhaps it’s a limitation of the JVM but compared to, say, Python, it feels clunky.

## Frustrations

I only have a couple of frustrations with Kotlin.

### Lack of polymorphism

Operators and the for loop are syntactic sugar that desugar to method calls. The methods are invoked statically and structurally — the target does not need to implement an interface that defines the method.

For example, `a + b` desugars to `a.add(b)`. However, there’s no way to write a generic function that sums its parameters, because `add` is not defined on an interface that can be used as an upper bound.

There’s no way to define the following function for any type that has an add operator method.

```kotlin
fun <T> sum(T first, T second) : T = first + second
```

You would have to define overloads for different types, but then wouldn’t be able to call `sum` within a generic function.

```kotlin
fun sum(first: Int, second: Int) = first + second
fun sum(first: Long, second: Long) = first + second
fun sum(first: Double, second: Double)= first + second
fun sub(first: Matrix<Double>, second: Matrix<Double>)= first + second
fun sum(first: Money, second: Money)= first + second
...
```

That kind of duplication is what generics are meant to avoid!

Extension methods are also statically bound. That means you cannot write a generic function that calls an extension method on the value of a type parameter.

For example, the standard library defines the same extension methods on unrelated types — `forEach`, `map`, `flatMap`, `fold`, etc. But there is no concept of, for example, “mappable” or “foldable”. Nor is there a way of defining such a concept and applying it to existing types to allow you to write generic functions over unrelated types. Kotlin doesn’t have [higher kinded types](https://en.wikipedia.org/wiki/Kind_(type_theory)) that would let you express this kind of generic function or [type classes](https://en.wikipedia.org/wiki/Type_class) that would let you add polymorphism without modifying existing type definitions.

Compared to [Rust’s traits](https://doc.rust-lang.org/book/traits.html), which support extension methods, operator overloading, type classes for parametric polymorphism, _and_ interfaces for subtype polymorphism, Kotlin’s monomorphic extension methods and operator overloading are quite limited and do not help refactor duplicated logic.

However, for typical monomorphic, procedural Java code this is probably not an issue.

### Optionality is a Special Case

Kotlin’s support for nullable types is implemented by a special case in the type system and special case operators that only apply to nullable references. The operators do not desugar to methods that can be implemented for other types. For example, `optionalThing?.foo` can be considered a map of the function `{thing -> thing.foo}` over the option `optionalThing`. If `foo` itself is nullable, then it can be considered a flatMap. But the expressions do not desugar to `map` and `flatMap` And if you want to map or flatMap a function, you have to use a different syntax: `optionalThing?.let(theFunction)`.

For typical Java code, which is monomorphic and uses null references with wild abandon, language support for nullability is invaluable. But I would find it more convenient if it could be used polymorphically, or if Kotlin used a common naming convention for optional and other functor types.

Null safety is not enforced when you interop with Java code. And you do that a lot. Kotlin doesn’t have many libraries or much of a runtime and makes it easy to call existing Java libraries directly. Kotlin expects you to know what you’re doing with respect to null references when calling Java code, and doesn’t force you to treat every value returned from Java as nullable. As a result, null safety is a bit of an illusion in a lot of our Kotlin code and it has come back to bite us.

## Conclusion

The code we’ve been writing has been a mix of coordinating and piping data between existing Java libraries – Apache HTTP client, Undertow HTTP server, JDBC, Sesame, JSON and XML parsers, and so on – and algorithmic code that analyses human readable text. For that kind of work, Kotlin has been very useful. The Kotlin code is far more concise than the equivalent Java. In our domain models and algorithmic code, Kotlin’s type safety and especially null safety, has been a big help.

Exactly how happy we’ve been with Kotlin has depended on the design style of the code we’re writing:

For functional programming, Kotlin has occasionally been frustrating, because we cannot use parametric polymorphism to factor out duplicated logic as much as we’d like.

For object-oriented programming, Kotlin’s concise syntax for class definitions and [language support for delegation](https://kotlinlang.org/docs/reference/delegation.html) avoids a lot of Java’s boilerplate.

However, most Java out there is monomorphic, procedural code moving data between “[NOJOs](http://puttingtheteaintoteam.blogspot.co.uk/2008/10/is-that-pojo-or-nojo.html)” and APIs that expect objects to have “bean” getters and setters. Kotlin has made working with that kind of API much easier and far more concise than doing so in Java.

1.  As far as I can tell, RedHat sponsor development of Ceylon but do not actually use it to develop their own products. If I’m wrong, please let me know in the comments.[↩](#fnref1)
"""

Article(
  title = "Early Impressions of Kotlin",
  url = "http://natpryce.com/articles/000815.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nat Pryce",
  date = LocalDate.of(2015, 12, 30),
  body = body
)
