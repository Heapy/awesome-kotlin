
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
## Kotlin 1.0 is here

[JetBrains](https://www.jetbrains.com/) (the people behind IntelliJ IDEA) have [recently announced](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/) the first RC for version 1.0 of [Kotlin](https://kotlinlang.org/), a new programming language for the JVM. I say ‘new’, but Kotlin has been in the making for a few years now, and has been used by JetBrains to develop several of their products, including Intellij IDEA. The company [open-sourced](https://github.com/JetBrains/kotlin) Kotlin in 2011, and have worked with the community since then to make the language what it is today.

![Kotlin](https://opencredo.com/wp-content/uploads/2016/02/kronshtadt.jpg)

## What is Kotlin and why you should care

There is no shortage of [JVM languages](https://en.wikipedia.org/wiki/List_of_JVM_languages) to choose from these days. Kotlin (named after [Kotlin island](https://en.wikipedia.org/wiki/Kotlin_Island), near St. Petersburg, pictured above) positions itself as a pragmatic and easy-to-use alternative to Java, with which it is highly interoperable, to the point where you can mix Kotlin and Java code in the single codebase. The emphasis on interoperability lowers the adoption barrier, which can make Kotlin rather appealing to any Java developer who wished Java was more expressive and less verbose. Kotlin compiles to Java 6-compatible bytecode so you can use the powerful features it offers on older JVM versions (or Android where it has become quite popular already).

## Hello World! with Kotlin

Let’s see some code and compare it with Java and Scala (from which Kotlin has borrowed quite a few language constructs) to get a first impression about the language and highlight a few differences compared to the alternatives.

```kotlin
// Kotlin
fun main(args: Array<String>) {
    println("Hello, World!")
}
```

```scala
// Scala
object App {
  def main(args: Array[String]) {
    println("Hello, world!")
  }
}
```

```java
// Java
public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

You can observe almost immediately how compact Kotlin’s version is (even compared to Scala). Kotlin benefits from top-level (or package-level) functions that are not members of a class or object, which is perfect for the `main` function. Since package declaration is omitted the function belongs to the default package.

There are a few language features shown above that make Kotlin programs somewhat similar to Scala ones:

* Type declarations are defined after function argument names, variable names or function/method names
* the `Unit` return type (equivalent to Java void) is optional
* `Array` is a regular generic type
* The `println()` function, and other print functions, are available implicitly
* semicolons are optional :)

## Type system

Kotlin’s type system is close enough to Java’s to provide a good level of interoperability, although it still empowers developers to produce elegant code in a productive way; for instance, type inference removes the need to declare variable types everywhere. A lot of features in Kotlin have been influenced by the [Effective Java](http://www.oracle.com/technetwork/java/effectivejava-136174.html) series, and try to tackle some of the not-so-great elements that Java has inherited from its early days. Kotlin makes it easier to follow best practice, and prevents some of the more questionable practices completely.

The key features of Kotlin’s type system (compared to Java) are:


* every class inherits from type `Any`, which defines only 3 functions (`equals()`, `hashCode()` and `toString()`), compared to quite a few more in Java’s `Object`
* all [data types](https://kotlinlang.org/docs/reference/basic-types.html) are represented by classes, including numbers (`Int`, `Long`, `Double`, etc.), characters (`Char`) and booleans (`Boolean`)
* a class’s primary [constructor](https://kotlinlang.org/docs/reference/classes.html#constructors) can be defined as part of the class declaration
```kotlin
class Foo(val bar: String, val baz: Int)
```
* the `new` keyword is missing
```kotlin
val foo = Foo("bar", 42)
```
* there are no static methods and any static code needs to be defined in a [class companion object](https://kotlinlang.org/docs/reference/object-declarations.html#companion-objects)
* classes and functions are final by default and have to be declared `open` to be overridable
* any type can be enhanced by using [extension functions](https://kotlinlang.org/docs/reference/extensions.html) to define additional functions/methods (no more `xUtils` classes)
* [generics](https://kotlinlang.org/docs/reference/generics.html) have been revamped to make working with variance much easier
* [objects](https://kotlinlang.org/docs/reference/object-declarations.html) can be used to implement the singleton pattern or to avoid the need for anonymous inner classes
* [data classes](https://kotlinlang.org/docs/reference/data-classes.html) make it trivial to create Java Bean-style data containers
```kotlin
data class Person(val firstName: String, val lastName: String)
```

## Functional programming

Kotlin is not a functional language but provides decent support for FP elements where they make sense in the OO world. It lifts functions to first-class-citizen status and makes using them very easy (the same sadly can’t be said about Java, even in version 8). Kotlin supports [higher-order functions](https://kotlinlang.org/docs/reference/lambdas.html#higher-order-functions) which means that functions can be passed in as arguments to other functions or returned from functions.

```kotln
// List.map() is an example of a higher-order function
fun  List.map(transform: (T) -> R): List {
  val result = arrayListOf()
  for (item in this)
    result.add(transform(item))
  return result
}
```

Additionally, [lambda expressions](https://kotlinlang.org/docs/reference/lambdas.html#lambda-expression-syntax) and [anonymous functions](https://kotlinlang.org/docs/reference/lambdas.html#anonymous-functions) can be used when function expressions or references are expected.

```kotlin
val doubled = ints.map { it -> it * 2 }
```

Some other nice features around working with [functions](https://kotlinlang.org/docs/reference/functions.html#functions) in Kotlin include:


* default argument values
```kotlin
fun read(b: Array, off: Int = 0, len: Int = b.size()) {...}
```
* named arguments
```kotlin
read(bytes, len = 100)
```
* infix notation
```kotlin
val plusOne = value + 1 // plus operator is a function call using infix notation
```
* Unit return is optional
```kotlin
fun printMe(message: String): Unit { println(message) }
```
* single-expression functions
```kotlin
fun printMe(message: String): Unit = println(message)
```
* tail-recursive functions
```kotlin
tailrec fun findFixPoint(x: Double = 1.0): Double = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
```

## Null safety

Handling null references has been quite a chore in Java from the very beginning. In recent versions of the language a few utility classes have been added to make it somewhat more manageable (`Objects` in Java 7 and Optional in Java 8 come to mind), but the language itself doesn’t provide much-needed native support. Kotlin is different as it adopts and further enhances Groovy’s approach to [null-safety](https://kotlinlang.org/docs/reference/null-safety.html) and makes working with object references less of a hassle.

By default, the compiler will prevent assigning null value to an object reference and you have to explicitly declare a variable as nullable.

```kotlin
var a: String = "abc"
a = null // compilation error

var b: String? = "abc" // '?' declares variable as nullable
b = null // ok
```

With that initial safety feature in place you can then safely follow nullable object references without worrying about `NullPointerException` (or employing an excessive degree of null-checking). Additionally, the so-called Elvis operator (surely you can see Elvis’ hair do in `?:`) provides a syntactic sugar for defining a default value if a null reference has been encountered.

```kotlin
val departmentHead = employee?.department?.head?.name ?: "n/a"
```

On top of that, Kotlin offers safe-casting support, where instead of throwing ClassCastException the expression returns a null value.

```kotlin
val aInt: Int? = a as? Int
```

## Collections

Unlike Scala, Kotlin doesn’t provide its own collection framework built from ground up. The rationale behind that choice is Java interoperability. Collections are the bread and butter of software engineering and the team behind Kotlin decided they didn’t want to make it difficult to use Java collections from Kotlin, or to make developers to learn new collection types. [Collections in Kotlin](https://kotlinlang.org/docs/reference/collections.html) reflect those in Java with one difference – Kotlin distinguishes between immutable and mutable collections, favouring immutability (although mutable Java collections are being used behind the scenes).

```kotlin
val numbers: MutableList = mutableListOf(1, 2, 3)
val readOnlyView: List = numbers
println(numbers)        // prints "[1, 2, 3]"
numbers.add(4)
println(readOnlyView)   // prints "[1, 2, 3, 4]"
readOnlyView.clear()    // -> does not compile

val strings = hashSetOf("a", "b", "c", "c")
assert(strings.size == 3)
```

Additionally, the [collection API in Kotlin](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html) offers a range of easy-to-use extension functions around collection types.

```kotlin
val items = listOf(1, 2, 3, 4)
items.first() == 1
items.last() == 4
items.filter { it % 2 == 0 }   // returns [2, 4]
items.filter { it % 2 == 0 } take 1 forEach { println(it) } // prints out 2
items.fold(0) { total, current -> total + current } // returns 10
```

Creating and using maps is quite convenient too.

```kotlin
val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
println(readWriteMap["foo"])
val readOnlyView: Map = HashMap(readWriteMap)
val doubled = readOnlyView.mapValues { entry -> entry.value * 2 }
for ((key, value) in doubled) println("${"$"}key -> ${"$"}value")
```

## Control flow

Kotlin offers some improved [control flow constructs](https://kotlinlang.org/docs/reference/control-flow.html), certainly compared with Java. So for instance, the `if ... else` block is an expression (returns a value).

```kotlin
val max = if (a > b) a else b
```

The `switch` operator (as found in C and Java) has been replaced by `when` expression form, similar to pattern-matching in Scala, that also makes a convenient replacement for `if ... else` chains.

```kotlin
when {
  x.isOdd() -> print("x is odd")
  x.isEven() -> print("x is even")
  else -> print("x is funny")
}
```

The `when` expression supports [smart-casts](https://kotlinlang.org/docs/reference/typecasts.html#smart-casts) too, when combined with matching on the type of the when argument.

```kotlin
val hasPrefix = when(x) {
  is String -> x.startsWith("prefix") // x is automatically casted to String
  else -> false
}
```

A `for` loop can iterate over any type that provides an iterator implementing `next()` and `hasNext()`.

```kotlin
for (item in collection)
  print(item)

for ((index, value) in array.withIndex()) {
    println("the element at ${"$"}index is ${"$"}value")
}
```

## Other features

I think we’ve covered quite a lot already, but there are still a few more features worth highlighting to round up the language overview:

* [string templates](https://kotlinlang.org/docs/reference/basic-types.html#string-templates) (known in Scala as string interpolation) make it easy to format string messages easily by resolving variables and expressions

```kotlin
val s = "abc"
val str = "${"$"}s.length is ${"$"}{s.length}" // evaluates to "abc.length is 3"
```
* [destructuring](https://kotlinlang.org/docs/reference/multi-declarations.html) allows convenient extraction of data class or collection elements
```kotlin
val person = Person("Rafal", 22) // I wish I was 22 again!
val (name, age) = person

for ((key, value) in map) {
   // Map.Entry can be destructured too
}
```
* [type checks](https://kotlinlang.org/docs/reference/typecasts.html) support smart-casting
```koltin
if (x is String) {
   print(x.length) // x is automatically cast to String
}
```
* [structural equality](https://kotlinlang.org/docs/reference/equality.html#structural-equality) can be checked simply with the `==` operator (no need to use `equals()`)
* [referential equality](https://kotlinlang.org/docs/reference/equality.html#referential-equality) can be checked with the `===` operator
* creating [ranges](https://kotlinlang.org/docs/reference/ranges.html) is supported natively
```koltin
for (i in 1..10 step 2) println(i)
```
* predefined [operators can be overloaded](https://kotlinlang.org/docs/reference/operator-overloading.html) (so no crazy operators allowed but still quite powerful)
* all [exceptions](https://kotlinlang.org/docs/reference/exceptions.html) are unchecked (no more forced exception handling)
* `try` is an expression
```koltin
val a: Int? = try { parseInt(input) } catch (e: NumberFormatException) { null }
```
* [type-safe builders](https://kotlinlang.org/docs/reference/type-safe-builders.html) help creating rich DSLs
```koltin
html {
  head {
     title {+"HTML built with Kotlin"}
  }
  // ...
}
```

By now I’m sure you are overloaded with all this Kotlin knowledge, so I will stop here. I hope I was able to showcase the entire host of very powerful capabilities that Kotlin offers. By all means explore [the language reference](https://kotlinlang.org/docs/reference/) to find out more about various language features.

## Java interoperability

One of the design goals behind Kotlin has always been to stay close enough to Java to lower the barrier to adoption. Obviously, on the other hand the need to stay interoperable limits the the scope to introduce many new features into the language. I think Kotlin has managed to achieve quite a lot without breaking its ties to the Java world.

Most of the [interoperability](https://kotlinlang.org/docs/reference/java-interop.html) aspects revolve around mapping concepts or types between both languages.

For instance, since Java doesn’t offer null safety support similar to Kotlin, Java types are treated in a special way and called [platform types](https://kotlinlang.org/docs/reference/java-interop.html#null-safety-and-platform-types). Null checks are relaxed for such types with the risk of `NullPointerExceptions` being thrown at runtime.

```
val list = ArrayList() // non-null (constructor result)
list.add("Item")
val size = list.size() // non-null (primitive int)
val item = list[0] // platform type inferred (ordinary Java object)

item.substring(1) // allowed, may throw an exception if item == null

val nullable: String? = item // allowed, always works
val notNull: String = item // allowed, may fail at runtime
```

Similarly, Java generics are handled in a special way when types are imported to Kotlin and using Java arrays of primitives requires a workaround where specialised classes are used to represent them in Kotlin.

When it comes to [Kotlin classes being called from Java](https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html), much of the work required to make them interoperable happens on the Kotlin side. Developers can use an range of annotations to instruct the compiler to produce code that will be usable from Java.

## Tooling

One of the interesting aspects about Kotlin is that the language has been created by the company behind a popular IDE product. As one might expect IDE support (well, for Intellij IDEA at least) for Kotlin has been very much a part of working on the language itself. The result is a comprehensive [Kotlin support in Intellij IDEA](https://kotlinlang.org/docs/tutorials/getting-started.html) on a par with that for Java. JetBrains also maintains an [Eclipse plugin for Kotlin](https://kotlinlang.org/docs/tutorials/getting-started-eclipse.html) so this open-source IDE should also provide a good level of support.

Kotlin creators have resisted the need to redefine much of the external tooling around the language. So for instance Kotlin projects can be built using Maven, Gradle or even Ant so except for the compiler plugin not much else is changing for those coming from the Java background.

## Kotlin ecosystem

There is a growing community around the language so perhaps not surprisingly one can already find a good selection of open-source libraries and frameworks written in Kotlin. If you want to stick with your favourite Java framework, you don’t have to make a full transition to Kotlin as you can use your existing framework in Kotlin without too much hassle and even mix Java and Kotlin code in a single codebase.

The official Kotlin website lists several [tools, libraries and frameworks](https://kotlinlang.org/docs/resources.html), covering MVC/Web, HTTP client, dependency injection and text editor support among over things. Also, the [Awesome Kotlin](https://kotlin.link) GitHub website offers a great selection of resources to explore.

## Summary

I believe Kotlin deserves at least a try, especially if you are coming from the Java background. The language brings a lot of good features from Scala and Groovy closer to Java, so you can benefit from the best of both worlds: the vastness of the Java ecosystem with expressiveness and productivity enhanced by constructs and features coming from other languages. If you do want to give Kotlin a go, why don’t you [try it online](http://try.kotlinlang.org/) first or just create a Kotlin project in your favourite IDE and start experimenting with it.

Throughout this post I was covering Kotlin from the perspective of somebody coming from Java as I believe this is a primary expansion area for the new language. It’s worth knowing that Kotlin is also targeting Android development (also Java but still on version 6) as well as (still experimental) JavaScript runtime as an alternative to the JVM. With such a wide range of applications and some great features I reckon Kotlin stands a good chance of attracting quite a few new users.

"""

Article(
  title = "Kotlin: a new JVM language you should try",
  url = "https://opencredo.com/kotlin/",
  categories = listOf(
    "null",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Rafal Gancarz",
  date = LocalDate.of(2016, 3, 3),
  body = body
)
