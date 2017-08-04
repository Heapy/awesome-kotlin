
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin really simplifies things such as defining constructors and writing immutable objects for your application. For example, flexible Kotlin constructor definitions eliminate the need of [builder classes](http://en.wikipedia.org/wiki/Builder_pattern "Builder Pattern - Wikipedia") (you simply don’t need them in 99% of all the possible use cases, if you use Kotlin), thus reducing overhead of having immutable objects in your application, while retaining full flexibility and expressiveness.

However, if you want to define non-trivial constructor (especially for [data classes](http://kotlinlang.org/docs/reference/data-classes.html "Kotlin Data Classes")) it might not be as trivial as just writing a function.

For example, when I started playing with Kotlin, I decided to start with something as simple as defining a class that would represent a [rational number](http://en.wikipedia.org/wiki/Rational_number "Rational Number - Wikipedia").

My first brute force (or naive, if you like) attempt to define that class was as follows:

```kotlin
data class Ratio (val numerator : Int, val denominator : Int)
```

The problems with this class are obvious: you can create ratios like Ratio(1, 2) and Ratio(2, 4) and they won’t be equal to each other. And I wanted exact opposite – whenever the user of this class constructs a ratio it divides a numerator and denominator to their greatest common divisor – to have coprime numerator and denominator in the corresponding fields of the newly constructed instance of Ratio class. Also I wanted to retain nice-to-have features of the [data class](http://kotlinlang.org/docs/reference/data-classes.html "Kotlin Data Classes") – I didn’t want to define copy, hashCode and equals myself.

So, at this point you’re welcome to play before reading my solution.

* * *

OK, now if you came up with your approach or just would like to see the possible solution – here is the [link to the full class definition](https://github.com/avshabanov/math/blob/master/approx/src/main/kotlin/ratio.kt "Ratio class definition in Kotlin") for those who interested.

In short: you can define custom class constructor (and yet retain call semantics) is to define `invoke` function in ‘class object’ section of your class that has special semantics: you can define static functions as well as factory function `invoke`. It may look as follows (simplified):

```kotlin
class Ratio private (val numerator : Int, val denominator : Int) {
  class object {
    val ZERO = Ratio(0, 1) // static member!
    val ONE = Ratio(1, 1) // static member!
    fun invoke(numerator : Int = 1, denominator : Int = 1) : Ratio {
      if (denominator == 0) throw IllegalArgumentException("denominator can't be zero")
      if (numerator == 0) return ZERO
      if (numerator == denominator) return ONE

      val d = gcd(numerator, denominator)
      return Ratio(numerator / d, denominator / d)
    } // <-- end of static function invoke

    fun gcd(a : Int, b : Int) : Int { /*omitted*/ } // static function!
  }

}
```

The beauty of Kotlin here is that you'll still be able to use 'constructor' semantics whenever you need to create an instance of `Ratio`, i.e. you can write as follows as if you had ordinary constructor:

```kotlin
val r1 = Ratio(3, 7) // invoke will be called here
val r2 = Ratio(numerator = 1, denominator = 4) // invoke will be called here
```

This is it, I hope you find it useful.

"""

Article(
  title = "Non-trivial constructors in Kotlin",
  url = "http://alexshabanov.com/2014/12/01/non-trivial-constructors-in-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Alex Shabanov",
  date = LocalDate.of(2014, 12, 1),
  body = body
)
