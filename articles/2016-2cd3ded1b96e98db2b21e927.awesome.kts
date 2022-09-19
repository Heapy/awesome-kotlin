
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
It’s been a long time since I’ve updated this blog. Over the year I’ve moved away from Scala as my preferred language and towards Kotlin. I’ve found Kotlin a refreshing approach as its borrowed a lot of the good things I liked about Scala but kept it simple and practical by avoiding a lot of the gotchas and ambiguity that can exist in Scala.

Here is a collection of things I like about Scala and Kotlin and also a comparison of how these features are accomplished in each language.

## Type Declaration and Inference

Something I love about both these languages is they both have static typing with type inference. This gives you the power of compile time type checking with out the declarative boiler plate. Largely it works the same in both languages. Both languages also have preference to immutable type declaration as well with the optional type declaration being placed after the variable name.

Example, the below code is the same in both languages:

Declare a immutable variable named age of type Int:

```kotlin
val age = 1
```

Declare a mutable variable of type String:

```kotlin
var greeting = "Hello"
```

Both languages support lambda functions as first class citizens that can be assigned to variables or passed as function parameters.

Scala:

```scala
val double = (i: Int) => { i * 2 }
```

Kotlin:

```kotlin
val double = {i: Int -> i * 2}
```

## Data / Case Class

Both Scala and Kotlin have a similar concept of a data class which can be use to represent a data model object.

### Scala’s Approach

Scala calls this a case class and it can be defined like:

```scala
case class Person(name: String, age: Int)
```

This gives you the following main advantages over a normal class:

* Has an apply method (You don’t need to use the ‘new’ word to construct instances)
* Accessor methods are defined for each property (If property are defined as var then setters are also defined)
* toString, equal and hashCode is sensibly defined
* copy function
* Has an unapply method (which allows use in match expressions)

### Kotlin’s Approach

Kotlin calls this a data class and it’s defined like:

```kotlin
data class Person(val name: String, val age: Int)
```

Key Features

* Accessor methods are defined for each property (If property are defined as var then setters are also defined). This is not unique to data class and works on any class in Kotlin.
* Sensibly defined toString, equal and hashCode
* copy function
* component1..componentN functions. Similar use to unapply.
* Implements JavaBean getter and setters defined so native Java frameworks (Hibernate, Jackson) without change

Kotlin doesn’t need a special ‘apply’ method as it doesn’t require a ‘new’ keyword to instantiate class constructors. So this is a standard constructor definition like any other class.

### Comparison

Generally data and case classes are similar.

This example usage works the same in Kotlin or Scala:

```kotlin
val jack = Person("Jack", 1)
val olderJack = jack.copy(age = 2)
```

Generally I’ve found data and case classes interchangeable in day to day use. Kotlin does enforce some restriction on extending a data class with inheritance but its done for good reasons when you consider the implementations of equals and componentN functions and prevents the gotcha moments.

The Scala case classes can be more powerful in a match statements compared to Kotlin’s handling of data classes in ‘when’ statements which is something I miss.

Kotlin approach works a lot better when being used from existing Java frameworks as it will look like a normal java bean.

Both languages support supplying parameters by name and allow for default values.

## Null Safety / Optionality

### Scala’s Approach

Scala’s approach to null safety is the option monad. Simply an option can be one of two concrete types; Some(x) or None.

```scala
val anOptionInt: Option[Int] = Some(1)
```

OR

```scala
val anOptionInt: Option[Int] = None
```

You can operate on the option using functions on the option class like “isDefined” and “getOrElse” (to provide a default value) but more commonly you would use monad operations like map, foreach or fold which will treat the option as a collection containing 0 or 1 elements.

For example to sum two Optionally defined Ints you could do:

```scala
val n1Option: Option[Int] = Some(1)
val n2Option: Option[Int] = Some(2)
val sum = for (n1 <- n1Option; n2 <- n2Option) yield { n1 + n2 }
```

The variable sum will have the value Some(3). This is leveraging Scala’s for comprehension which can be foreach or a flat map function depending on the use of the yield keyword.

Another example of chaining could be:

```scala
case class Person(name: String, age: Option[Int])
val person:Option[Person] = Some(Person("Jack",Some(1)))
for (p <- person; age <- p.age) {
 println(s"The person is aged ${"$"}age")
}
```

This will print “The person is aged 1”

### Kotlin’s Approach

Kotlin’s approach borrows from groovy style syntax and is very practical in every day use. In Kotlin all types are non-nullable and must be explicitly declared nullable using ‘?’ if it can contain null.

The same example could be written

```kotlin
val n1: Int? = 1
val n2: Int? = 2
val sum = if (n1 != null && n2 != null) n1 + n2 else null
```

This is much closer to Java syntax except Kotlin will enforce compile time checks so its not possible to use a nullable variable without checking it is not null first so you won’t fear NullPointerExceptions. Its also not possible to assign a null to a variable declared as non-nullable. The compiler is quite smart in checking branch logic so you don’t have the situation of over guarding that you see in Java where the same variable is checked for null multiple times.

An equivalent Kotlin code for the second example of chaining is:

```kotlin
data class Person(val name: String, val age: Int?)
val person:Person? = Person("Jack", 1)
if (person?.age != null) {
  println("The person is aged ${"$"}{person?.age}")
}
```

An alternative is also available using “let” which could replace the if block with

```kotlin
person?.age?.let {
  println("The person is aged ${"$"}it")
}
```

## Comparison

I really prefer the Kotlin approach. It’s a lot easier to read and understand what’s going on and multiple levels of nesting is easy to handle. The scala approach has symmetry in that other monads can be acted on the same as option can (e.g. futures) which some people like but I’ve found it can get complicated really fast once there is a little bit of nesting. There are also a lot of gotcha’s with for comprehension as under the covers they are maps or flat maps but you don’t get the compile time warnings if you do something wrong like mix monads or do a pattern match without covering alternative paths which leads to runtime exceptions that are cryptic.

Kotlin’s approach also bridges the gap when integrating with Java code as they can default to nullable types where as Scala still has to support null as a concept without null safety protection.

## Functional Collections

Scala of course supports many functional goodies. Kotlin is a little more restrictive but the basics are covered.

There isn’t much difference in the basic fold and map functions.

Scala

```scala
val numbers = 1 to 10
val doubles = numbers.map {_ * 2}
val sumOfSquares = doubles.fold(0) {_ + _}
```

Kotlin

```kotlin
val numbers = 1..10
val doubles = numbers.map {it * 2}
val sumOfSquares = doubles.fold(0) {x,y -> x+y}
```

Both support the concept of lazy evaluated sequences. For example printing first 10 even squares.

Scala

```scala
val numbers = Stream.from(1) // all natural numbers
val squares = numbers.map {x => x * x}
val evenSquares = squares.filter {_%2 == 0}
println(evenSquares.take(10).toList)
```

Kotlin

```kotlin
val numbers = sequence(1) {it + 1} // all natural numbers
val squares = numbers.map {it * it}
val evenSquares = squares.filter {it%2 == 0}
println(evenSquares.take(10).toList())
```

## Implicits Conversion vs Extension Functions

This is an area where Scala and Kotlin diverge a little.

### Scala’s Approach

Scala has a a concept of implicit conversion that allows you to add extra behaviour to a class by automatically converting to another class when needed. An example of this

```scala
object Helpers {
 implicit class IntWithTimes(x: Int) {
   def times[A](f: => A): Unit = {
    for (i <- 1 to x) {
     f
    }
  }
 }
}
```

Then later in the code you can do:

```scala
import Helpers._
5.times(println("Hello"))
```

This will print “Hello” 5 times. How this works is when you try to use the “times” function which doesn’t exist on the Int the object will be automatically boxed into an IntWithTimes object and the times function will executed on that.

### Kotlin’s Approach

Kotlin has the concept of extension functions that can be used to accomplish a similar job. In the Kotlin approach you define a normal function but prefix the function name with a type to extend.

```kotlin
fun Int.times(f: ()->Unit) {
  for (i in 1..this) {
    f()
  }
}
 
5.times {println("Hello")}
```

### Comparison

Kotlin approach fits the use case that I generally would use this Scala capability for and has the advantage of being a little simpler to understand.

## Scala Features Not Present in Kotlin that I won’t Miss

One of the best part of the Kotlin language for me is not the features it has but more the features from Scala that are not in Kotlin

* Call by name – This destroys readability. If a function is being passed its a lot easier when its visible that its a function pointer in a basic review of the code. I don’t see any advantage this gives over passing explicit lambdas
* Implicit parameters – This is something I’ve really hated. It leads to situation of code changing drastically based on a change of import statement. It makes it really hard to tell what values will be passed to a function without good IDE support
* Overloaded FOR comprehension – To me this is a clunk to get around the problem with dealing with multiple monads
* The mess with optional syntax on infix and postfix operators – Kotlin is little more prescriptive and means that the code is less ambiguous to read and not as easy for simple typo to become a non-obvious error
* Operator Overload to the Max – Kotlin allows basic operator overloads for the basic operators (+, – etc.) but Scala allows any bunch of characters to be used and it seems to have been embraced by library developers. Am I really meant to remember difference between “~%#>” and “~+#>”?
* Slow compile times

"""

Article(
  title = "Scala vs Kotlin",
  url = "https://agilewombat.com/2016/02/01/scala-vs-kotlin/",
  categories = listOf(
    "Kotlin",
    "Scala"
  ),
  type = article,
  lang = EN,
  author = "Lionel",
  date = LocalDate.of(2016, 2, 1),
  body = body
)
