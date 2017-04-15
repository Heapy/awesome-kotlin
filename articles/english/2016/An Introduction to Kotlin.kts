
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
[Kotlin](https://kotlinlang.org/) is a relatively new language that keeps the good parts of Java while eliminating boilerplate and encouraging better Object Oriented programming style while still allowing a Functional paradigm. Best of all, it’s 100% Java-compatible which allows you to mix it with Java, Groovy, or any other Java-bytecode language. Kotlin is similar in many ways to Groovy by handling a lot of the more mundane aspects of coding in Java but unlike Groovy, which took a Dynamic Typing approach, Kotlin doubled-down on Static typing to help eliminate many of the common annoyances in Java.

## Kotlin Features

While Kotlin’s entire feature list is far too expansive to discuss in this post I would like to point out some of my favorites coming from a Java background. If you want to dive straight into some Kotlin code there is an [example program out on GitHub](https://github.com/mike-plummer/KotlinCalendar) that demonstrates most of the features discussed below.

### Null Safety and Immutability

By default all variables are enforced as non-nullable by the compiler unless explicitly defined otherwise. By enforcing nullability constraints the compiler can automatically guard you from potential NullPointerExceptions and will, in fact, generate an error if you attempt to write unsafe code.

```kotlin
val myNonNullVariable: ComplexObject = ComplexObject()  // Declare non-nullable variable
myNonNullVariable = null                              // Compilation error, cannot assign null to this variable

val myNullableVariable: ComplexObject?                // Declare a variable as nullable
println myNullableVariable.getFieldA()                // Compilation error since variable could be null here
println myNullableVariable?.getFieldA()               // Use a safe call to guard against NullPointerException
                                                      // If variable is null then safe call will return 'null'
```

This null-checking extends throughout Kotlin syntax, even into cast operations:

```kotlin
val variable: Any = "A string value"
val stringVariable = variable as String                        // Unsafe cast that will succeed - note lack of nullability on types

val nullVariable: Any? = null
val nullableStringVariable: String? = nullVariable as String?  // Unsafe cast that will succeed - note that both sides must specify nullability
val nullableStringVariable2: String? = nullVariable as? String  // The line above can also be done using the 'safe cast' operator
val invalidCast = stringVariable as List                       // Guaranteed to throw a ClassCastException
```

Kotlin also provides multiple mechanisms for declaring variables. When using the keyword ‘val’ a variable is implicitly treated as immutable meaning the compiler will only define a getter and will generate an error if an attempt is made to reassign. Declaring using ‘var’ means the variable is mutable thus a getter and setter will be generated.

```kotlin
class ComplexObject {
    val fieldA: String = "Value 1"   // Non-nullable immutable String (just a getter will be generated)
    var fieldB: String = "Value 2"   // Non-nullable but mutable (will have getter and setter generated)
    var fieldC: String?              // Nullable, will require null checks or safe call operator ( ?. )
    val fieldD = "Value 4"           // Type will be inferred if omitted but value is initialized
}
```

### Boilerplate Reduction

Much like Groovy, Kotlin handles auto-generating much of the common logic in your programs. As previously discussed Getter and Setter methods are auto-generated but can be overridden with custom logic if desired. In addition, marking a class with the keyword ‘data’ will treat it as a Data Transfer Object and will auto-generate ‘equals’, ‘hashCode’, ‘copy’, and ‘toString’ methods.

### Flexibility

Classes can overload any standard operator which can aid code brevity and readability. In cases where appropriate operators don’t exist an ‘infix’ function can be declared which enables it to be used without dot notation or parentheses, making it look and behave much like a custom operator.

```kotlin
// Use infix to declare an "operator" function to allow somewhat cleaner code
// This also happens to be an extension function (see below)
// In this case, perform a subtraction and return the absolute value of the result
infix fun Long.absSubtract(input: Long): Long {
    return Math.abs(this - input)
}

println(1L absSubtract 5L)  // prints positive 4
```

Extension functions can be added to any class at runtime which enables you to add behaviors to classes you use without forcing you to subclass them.

```kotlin
// Anywhere this function is imported all Duration objects will gain this function
fun Duration.prettyPrint(): String {
    return "${"$"}{toHours()}:${"$"}{toMinutes() % 60}:${"$"}{get(ChronoUnit.SECONDS) % 60}"
}

Duration.ZERO.prettyPrint()  // Outputs '0:0:0'
```

Finally, Kotlin has excellent support for delegating functions and properties to member variables or extended classes. This is an alternative to gaining behaviors through extension and is a really simple way to implement several popular design patterns with minimal code.

```kotlin
interface WakeUpProvider {
    fun wakeUp()
}

class AlarmClock() : WakeUpProvider {
    override fun wakeUp() {
        println("Alarm Clock woke you up!")
    }
}

// Make WakeUpSystem delegate to 'provider' any 'WakeUpProvider' interface functions
class WakeUpSystem(provider: WakeUpProvider) : WakeUpProvider by provider

fun main(args: Array) {
    val provider = AlarmClock()
    WakeUpSystem(provider).wakeUp() // Prints 'Alarm Clock woke you up!'
}
```

### The Kotlin Object

Object is a special type in Kotlin that is very different from Java’s Object base type. Object is a versatile construct that can be used much like ad-hoc objects in JavaScript, easily build a Singleton, or add static-like capabilities to your Kotlin classes.

#### Ad-hoc Objects

Object can be used to generate on-the-fly structures without going through the work of defining a class. When used as part of an expression like this the Object is evaluated as the line is executed which contrasts with other uses of Object that are initialized the first time they are referenced.

```kotlin
var adhoc = object {
    val first = "firstValue"
    val second = "secondValue"
}

println("${"$"}{adhoc.first}, ${"$"}{adhoc.second}")    // Prints "firstValue, secondValue"
```

#### Singleton

If you need a Singleton you can simply write an Object declaration (contrasted with the Object expression used above) that will behave much like a class with static fields and methods in Java.

```kotlin
object MySingleton {
    val aSingletonValue = "ValueA"
    fun aSingletonFunction() {
        println("Yay for Kotlin Objects!")
    }
}
...
println(MySingleton.aSingletonValue)       // Prints "ValueA"
println(MySingleton.aSingletonFunction())  // Prints "Yay for Kotlin Objects!"
```

#### Companion

Objects can be used as Companions in Kotlin classes to add static-like properties and functions to the class. The Companion will be constructed only once and shared amongst all instances of the containing class.

```kotlin
class ClassWithCompanion {
    companion object {
        fun PrintAMessage() {
            println("I'm a function in the Companion!")
        }
    }
}
ClassWithCompanion.PrintAMessage()    // Note the 'static' access. Prints "I'm a function in the Companion!"
```

### Java Goodness

Coming from a Java background? Good news! All the familiar Java concepts and libraries are still available. Interfaces, abstract/inner classes, the Java standard library, generics, enums, etc all are present and for the most part behave in exactly the same way. On top of the basics all the latest features in Java such as Lambdas and Streams are available in Kotlin.

A notable exception is the lack of ‘static’ variables and methods. Often ‘static’ was misused and ended up breaking many precepts of Object Oriented design. In Kotlin this has been supplanted by Object, Companions, and package-level declarations to encourage proper encapsulation and separation of concerns while still providing the same features.

For a more complete comparison of how Kotlin compares with Java I recommend [this article](https://kotlinlang.org/docs/reference/comparison-to-java.html).

#### Fixes for Common Annoyances

Java’s been around long enough for everyone to find a few features they dislike. Kotlin attempts to fix a number of the most common complaints.

##### Automatic Casting

How many times have you written code like this in Java?

```java
Object unknownObjectType = ...
if (unknownObjectType instanceof String) {
    String castString = (String) unknownObjectType;    // Have to cast in order to treat it like a String
    castString.indexOf("abc");
}
```

Never fear, you will never have to write that in Kotlin! The Kotlin Compiler is smart enough to track the state of a variable as it executes which means it ‘knows’ everything various conditionals and other constructs tell it.

```kotlin
val unknownObjectType: Any = ...
if (unknownObjectType is String) {
    unknownObjectType.indexOf("abc")    // Compiler knows it's a String here so it is auto-cast
}
```

##### Destructuring

Another common annoyance is that occasionally you need to call a function that returns an object but then have to immediately pull only a subset of fields out of that object. Kotlin supports ‘destructuring’ similar to ECMAScript 2015, and supports destructuring in loop expressions.

```kotlin
val complexObjectCollection: Collection
for ( (fieldA, fieldB) in complexObjectCollection) {
    // Variables fieldA and fieldB are accessible here and will be equal to fields
    // named 'fieldA' and 'fieldB' from each item in the collection
}
```

##### Generics

In the Java world generics are certainly a boon, but certain design decisions and limitations leave them with a number of significant gotchas. Most of these are related to the wildcard generic (?) which has been eliminated in Kotlin. Kotlin makes use of new keywords ‘in’ and ‘out’ to help further define generic bounds similar to the PECS (Producer-extend, Consumer-super) concept in Java without the same shortcomings.

##### Equality

Tired of having to remember when it’s safe to use == and doing null-checks before calling ‘.equals(..)’? Kotlin uses == as ‘structural equality’ and === as ‘referential equality’. Put another way, == is the same as calling ‘.equals(..)’ while guarding against null in Java whereas === maps to Java’s ==.

```kotlin
val stringA: String = "New York"
val stringB: String = "New " + "York"
val stringC: String = stringA
println(stringA == stringB)     // True, contents are equal
println(stringA === stringB)    // False, not the exact same object
println(stringA === stringC)    // True, both reference the same object
```

##### Inlining

Finally, for anyone with C or C++ experience I’m sure you’re well-aware that Java doesn’t allow you to request a method be [inlined](https://en.wikipedia.org/wiki/Inline_expansion). Kotlin allows you to mark any function with the ‘inline’ keyword which makes the compiler insert that function’s code anywhere the function is called. At runtime this means fewer function calls at the expense of increasing the size of your compiled artifact. This isn’t necessary for most programs but can be a useful performance tweak especially when calling many lightweight functions in a large loop.

### Exception Handling

Many people have mixed feelings on this but Kotlin does not have the concept of Checked Exceptions. If a method you’re calling potentially throws an exception you are not required to catch it. This reduces the number of try-catch blocks in your code, especially in situations where you know a normally-checked exception truly can’t be thrown. Other than this, exceptions basically work the same as in Java.

## Environment

There are a number of options for developing in Kotlin. Excellent IDE support is integrated into IntelliJ IDEA and a plugin is available for Eclipse. Outside of an IDE environment you can compile and run Kotlin using Gradle, Maven, Ant, or standalone command line tools.

Information and downloads for these are available at [kotlinlang.org](https://kotlinlang.org/).

## Testing

A new 100% Kotlin-based testing framework, Spek, is also under development. For those who have worked with Spock or Mocha it will be very familiar – tests are broken down into a series of nested “given-on-it” clauses to help semantically structure your tests and encourage targeted, readable tests.

```kotlin
class DurationExtensionSpecs: Spek() {
    init {
        given("A zero duration") {
            var duration = Duration.ZERO
            on("adding time values using method chaining") {
                duration = duration.plusHours(2).plusMinutes(5).plusSeconds(12)
                it("should reflect the expected value") {
                    shouldEqual(duration.toHours(), 2)
                    shouldEqual(duration.toMinutes() % 60, 5)
                    shouldEqual(duration.get(ChronoUnit.SECONDS) % 60, 12)
                }
            }
        }
    }
}
```

Thankfully the ever-popular Hamcrest library has been ported over to Kotlin as [HamKrest](https://github.com/npryce/hamkrest) which allows you to write very powerful tests the same way you would in JUnit.

## Example

I’ve coded up a simple example of using Kotlin in a standalone program [out on GitHub](https://github.com/mike-plummer/KotlinCalendar). This program demonstrates much of what I’ve discussed here, including many of Kotlin’s core features and Spek tests.

## Conclusion

I hope I’ve shown you a few reasons to get excited about Kotlin and want to use it in your next project. Especially now that version 1.0 has dropped it’s never been a better time. Popular frameworks like Spring Boot are [adding support](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin) which makes it downright easy to gain the power and expressiveness of Kotlin. If you have any questions leave a comment below. If you would like to see more detail on specific Kotlin features leave that below too; if there’s enough interest I will explore them in future posts. In the meantime, take a look at the [example code](https://github.com/mike-plummer/KotlinCalendar) and [Kotlin docs](https://kotlinlang.org/docs/). Happy coding!

"""

Article(
  title = "An Introduction to Kotlin",
  url = "https://objectpartners.com/2016/02/23/an-introduction-to-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mike Plummer",
  date = LocalDate.of(2016, 2, 23),
  body = body
)
