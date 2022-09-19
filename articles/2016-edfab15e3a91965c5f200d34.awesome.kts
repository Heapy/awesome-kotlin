
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![shutterstock_379642018](http://insights.dice.com/wp-content/uploads/2016/09/shutterstock_379642018.jpg)

Named after an island near St. Petersburg, Russia (take a gander at its spectacular sunset, above), [Kotlin](https://kotlinlang.org/) is a statically typed open-source programming language that runs on the JVM, just like Java. JetBrains released version 1 in February 2016, after five years of development.

Originally coded by a team of Russian developers, Kotlin positions itself as an industrial-strength object-oriented language superior to Java. It uses Java code from the existing Java Class Library, and developers can use it to develop Android apps or generate JavaScript source code. You [can run it online in Intelli Idea](http://try.kotlinlang.org/#/Examples/Hello,%20world!/Simplest%20version/Simplest%20version.kt), the JetBrains IDE.

Here are some additional features that make Kotlin different from Java:

### The Language

With Kotlin, a main function is needed at the package level:

```kotlin
// Simplest version

fun main(args: Array<String>) {
    println("Hello World!")
}

// Second one with return type and returned value

fun main(args: Array<String>) : Int {
    println("Hello, world!")
    return 0
}
```

Either of the above works. (Comments are either /* .. */ for blocks or // for single lines, and you can nest block comments.) In the below, note how variable declarations are the Pascal way around; var means mutable (i.e., it can be changed), while val declares them read-only, so you must assign a value there:

```kotlin
val a: Int = 10000 // read-only
val b: Int = 0x0F   // hexadecimal read-only
var c = 0b1011 // binary also type inferred

println("a = ${"$"}a b = ${"$"}b c = ${"$"}c")
```

This outputs:

```
a = 10000 b = 15 c = 11
```

Note that primitive types (Int etc) in Kotlin are objects (unlike in Java), so this works:

```kotlin
println(0x0e.toString())
```

Syntax is straightforward, but make sure that expressions have brackets. Ifs can be used as expressions or statements and blocks. Anything inside { } can have a value. Else is optional inside statements, but mandatory in expressions:

```kotlin
var max: Int

if (a > b)
    max = a
else
    max = b

// Expression

val max = if (a > b) a else b
```

The switch statement is called when, but is more flexible, as it supports arbitrary expressions as constants:

```kotlin
fun cases(obj: Any) {

    when (obj) {
        1           -> print("One")
        "Hello"     -> print("Greeting")
        is Long     -> print("Long") // checks if type is long
        !is String  -> print("Not a string") if type is not a string
        else        -> print("Unknown")
    }
}
```

### Objects and Classes

The example below shows a simple Dice class with a constructor that passes in the maximum dice value. Two private immutable properties hold the Java.util.Random instance and the maxvalue. Note that the declaration of the Random instance does not use new. A public function Roll() is called for Random; this uses the single line function form with = instead of return.

In the main function, an instance of this dice class is created and a for loop calls Roll() ten times:

```kotlin
import java.util.Random;

class Dice (maxValue : Int) {
    private val rnd : Random = Random()
    private val maxvalue: Int
    
    init {
        maxvalue = maxValue
    }
    
    public fun Roll() : Int = rnd.nextInt(maxvalue) + 1
}

fun main(args: Array<String>) : Unit {
    val dice: Dice= Dice(6)
    
    for (i in 1..10) {
        println(dice.Roll())
    }
}
```

The constructor (called a primary constructor) is part of the class header. It can’t contain any code except in an init { } section. It’s also possible to do this without init by initializing the maxvalue property directly; the init can then be removed.

```kotlin
private val maxvalue: Int = maxValue
```

### Delegated Properties

Properties can have getters and setters, though immutable ones only get a getter. Usually properties are either reading from or writing to a backing field; getters and setters can extend this via delegated properties. The syntax of a class declaration in this case looks like this:

```kotlin
class Example {
    var p: String by Delegate()
}
```

There are a couple of delegate types. Let’s start off with ‘Lazy’ for lazy initialization, which calls a lambda expression on the first access; this value is used on subsequent ones. Here’s lazy in use:

```kotlin
class LazySample {
    var inc: Int = 10;
    val lazy: String by lazy {
        println("Assigned!")
        (inc++).toString()
    }
}

fun main(args: Array<String>) {
    val sample = LazySample()
    println("lazy = ${"$"}{sample.lazy}")
    println("lazy = ${"$"}{sample.lazy}")
    println("inc = ${"$"}{sample.inc}")
}

```

The output is:

```
Assigned!
lazy = 10
lazy = 10
inc = 11
```

This shows that despite accessing sample.lazy twice, only the first access initialized the value. If you comment out the three printlns and run it, you’ll see that there’s no assignment printed, as lazy is never assigned a value. That “by lazy” defers it until reading.

Next up on the delegate types: ‘observable.’ In this example, a TaxRate value has been made ‘observable,’ and defaults to the value passed in: 18.5. But changing it explicitly invokes the handler, which prints out that it has changed. It can be very useful when you need to find out what is changing a property:

```kotlin
import kotlin.properties.Delegates

class TaxRate {
    var value: Float by Delegates.observable(18.5f) { d, old, new ->
        println("Rate changed from ${"$"}old% to ${"$"}new%")
    }
}

fun main(args: Array<String>) {
    val rate = TaxRate()
    rate.value = 19.0f
}

```

This outputs:

```
Rate changed from 18.5% to 19.0%
```

### Conclusions

I’m a great believer that reduced cognitive load is good for programmers. It’s easier to understand code with simpler, more powerful syntax, and Kotlin certainly delivers on that.

Kotlin has been compared to Scala, though is somewhat simpler and compiles a lot faster. It’s not quite a snapshot of the language, [but the Idioms page](http://kotlinlang.org/docs/reference/idioms.html) tells you a lot about features and syntax in one go.

Any developers wanting to know differences between Kotlin and Java at a glance can refer to the [Comparison to Java page](https://kotlinlang.org/docs/reference/comparison-to-java.html) via Kotlin’s reference materials.

"""

Article(
  title = "A Developer’s Look at Kotlin",
  url = "http://insights.dice.com/2016/09/09/developers-look-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "David Bolton",
  date = LocalDate.of(2016, 9, 9),
  body = body
)
