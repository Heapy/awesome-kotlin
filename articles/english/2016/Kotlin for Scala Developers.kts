
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
At [51zero](/about) we pride ourselves on being flexible when it comes to language choice. Java, Scala, C#, and more recently, [Kotlin](https://kotlinlang.org/).  Kotlin is a JVM language developed by Jetbrains (the people behind IntelliJ amongst other things) and recently has just reached version 1.0 public. We’ve even ported ScalaTest over to Kotlin in the guise of [KotlinTest](https://github.com/kotlintest/kotlintest) (of which we’ll do a blog soon).

There are some great articles already published on the main features of Kotlin, for example [here](http://petersommerhoff.com/dev/kotlin/kotlin-for-java-devs/), [here](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3#.8e0v3qg1x) and [here](https://opencredo.com/kotlin/), which are useful introductions for Java developers wishing to look at a different language. This blog is for Scala developers interested in having a play with Kotlin, where we review features of Scala you use every day, and their equivalents, or closest features, are in Kotlin.

## Data classes vs Case classes

One of the main selling points of Scala when you first start to use it over Java is the use of case classes to take away all the boilerplate from Java beans. (Quite why Java didn't add this years ago I have no idea, surely the addition of an annotation that the compiler can pick up on wouldn't break existing code). But anyway, Kotlin has the same case class functionality in the form of data classes. The minor difference is that Kotlin won’t automatically infer the constructor parameters to be fields, so you must declare them as either val or var.  Eg, the following scala case class:


```scala
case class Person(name: String, age: Int)
```

Would become:

```kotlin
data class Person(val name: String, val age: Int)
```


The automatic derivation of equals(), hashCode() etc works in the same way as Scala.

## Implicit Conversions

Kotlin doesn’t support implicits as such but it does allow us to achieve one common use of implicits, which is the _pimp-my-library_ pattern. In Scala, when we want to invoke foo() on a type A which contains no foo() method, the compiler will check in the appropriate scopes for an implicit method that accepts an A and returns a type B which does have the foo() method. So we create another type B, which contains all the extra methods we want for A, and then add a conversion from A to B (or mark B as an implicit class that accepts an A).  In Kotlin we can use extension methods defined on A directly.

So the scala code:

```scala
implicit class RichString(str: String) {

 def reverse: String = ...

 def take(k: Int): String = ...

}
```

Becomes this Kotlin code:

```kotlin
fun String.reverse() = ... // the “string” is available as “this”

fun String.take(k: Int) = ...
```

Allows us to invoke this as `"hello".reverse()`

This works for generic types too.

## Option

Kotlin doesn't have an Option type provided by the SDK (other than the Optional type available in Java 8) instead focusing on offering null safety as a first class feature. Whether you prefer this over the Haskell inspired Options is a matter of preference. I’ll explain a way to do something very common with options.

Often given an option returning method, you want to map over the result before calling getOrElse or something similar. The equivalent in Kotlin would be the ?. operator which says, if the value is null, keep it null, otherwise execute the method. So this

```scala
map.get("key").map(_.foo)
```

Would become

```kotlin
map.get("key")?.foo
```

Getting the value out of the option in Scala usually involves a getOrElse or a fold. In Kotlin we would use the so called Elvis operator (am I the only one that thinks this name is ridiculous! Let's call it the getOrElse operator). So this

```scala
option.getOrElse(-1)
```

would become

```kotlin
value ?: -1
```

## Property access

In Scala, we can access methods that declare a single empty parameter list without the parenthesis, so that methods and fields can both appear as properties. Eg,

```scala
class Foo {

 def wibble(): String = "wibble"

}
```

Can be invoked as either foo.wibble or foo.wibble()

In Kotlin, we can do something similar for methods that adopt the java-bean standard. So if you have a method called fun getName() then we can do foo.name and that will be compiled into a call to the getName() function.

Kotlin provides something similar for setters. If there is a method called setName(str: String) then we can invoke this as either `foo.setName("moo")` or `foo.name = "moo"`. The second form will be compiled into the first form.

## Operator Overloading

Scala allows us to declare methods with almost any operators to make (for what its proponents would argue) richer and more readable symbols for methods where operators make sense (the canonical example tends to be matrices, vectors or complex numbers). The opponents would say you end up with things like [this](http://www.flotsam.nl/dispatch-periodic-table.html). Personally, I don’t mind either way.

Kotlin allows us to override some operators, but only ones predefined. Eg, plus, minus etc. To do this we just declare a method with the name that matches the symbol, and annotate the function declaration with the keyword operator. Eg

```kotlin
operator fun plus(vector: Vector): Vector = ...
```

The full list of supported operators can be found [here](https://kotlinlang.org/docs/reference/operator-overloading.html).

## Pattern Matching

Kotlin unfortunately doesn't support full pattern matching, so you cannot do things like

```scala
person match {
 case Person("bobby", age) =>
 case _ =>
}
```

There is a limited form of extraction, which you can read about later, and there is a replacement for switch which allows matching on types and invocation of functions on the argument. This uses the keyword when, eg,

```kotlin
when(obj) {
 is String -> // matches on the type
 parseInt(obj) -> // invoked method parseInt
 else -> // otherwise
}
```

There is a neat way to use when’s as a replacement for if-else-if chains, you just use when without an argument, as such

```kotlin
when {
 person.name == "bobby" ->
 x.isOdd() ->
 else ->

}
```

## Apply method

In Scala, methods named apply() do not need to have the method name in order to be invoked, eg

```scala
object Foo {
 def apply() = println("hello")
 def apply(str: String) = println(str)
}
```

Can be invoked simply as Foo() or Foo(“hello”)

In Kotlin, the equivalent is to name the method invoke() with the additional operator annotation, eg

```kotlin
object Foo {
 operator fun invoke() = println("hello")
 operator fun invoke(str: String) = println(str)
}
```

And then we can do the same, Foo() or Foo(“hello”)

## Anonymous Lambda Parameter

In scala we can refer to the lambda parameter using an underscore, eg list.map(_.name) which is very concise and very readable. Kotlin doesn’t offer a direct equivalent but there is an implicit **it** parameter available: list.map { **it**.name }.

This has the drawback that its ever so slightly more verbose, but offers the advantage that you can use it in multiple places, eg, in Scala we can’t do

```scala
list.map(_.firstname + _.lastname)
```

but instead must introduce a parameter

```scala
list.map(x => x.firstname + x.lastname)
```

In Kotlin we can use the **it** parameter multiple times, eg

```kotlin
list.map { it.firstname + it.lastname }
```

## Companion objects

Companion objects are supported in Kotlin using slightly different syntax. Instead of declaring an object with the same name, declare an object with the keyword companion inside the class itself.

```kotlin
class Foo {
 companion object {
   // methods here
 }
}
```

## Extractors

While kotlin doesn’t have extractors as such, it does support a limited form of extraction called desugaring declarations which go some way to performing the same job. Any class that implements the functions component1(), component2(), and so on (similar to the product() provided by Scala’s Product1, Product2, ProductN traits) can be desugared into component variables. For example

```kotlin
val person = Person("billy", 21)
val (name, age) = person
```

Which is compiled into

```kotlin
val person = Person("billy", 21)
val name = person.component1()
val age = person.component2()
```

In Scala, case classes automatically extend the appropriate ProductN trait. In Kotlin data classes automatically implement the appropriate componentN() functions.

This also works in for statements, replacing the scala code:

```scala
for ((a,b) <- person) { }
```

With the almost identical

```kotlin
for ((a,b) in person) { }
```

That’s all for now. Hopefully this article provides some hints and tips for Scala developers who are interested in exploring a little bit of Kotlin.

"""

Article(
  title = "Kotlin for Scala Developers",
  url = "http://www.51zero.com/blog/2016/4/14/kotlin-for-scala-developers",
  categories = listOf(
    "Scala",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Stephen Samuel",
  date = LocalDate.of(2016, 4, 14),
  body = body
)
