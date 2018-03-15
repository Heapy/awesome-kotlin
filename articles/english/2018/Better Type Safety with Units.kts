

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
You and I know that you can't compare apples to oranges, but does your compiler? Many times when dealing with quantities, programmers use numeric types like int or double to represent an amount.

```
    data class NFLEndzone(
            val widthFeet: Int = 160,
            val lengthFeet: Int = 30) {
        val areaSquareFeet: Int get() = widthFeet * lengthFeet
    }
```

In some programming languages, we can use type-aliases to be more descriptive about what a value's type is.

```
    typealias Feet = Int

    data class NFLEndzone(
            val width: Feet = 160,
            val length: Feet = 30) {
        val area: Feet get() = width * length
    }
```

However, this code is exactly the same in the eyes of a compiler, which can be problematic.

```
    typealias SquareYards = Int

    fun howMuchPaintIsNeeded(area: SquareYards) {
        // Calculate how many gallons are needed
        // to cover the endzone
    }

    val endzone = NFLEndzone()

    // The next line looks reasonable, but we're
    // passing square feet to a function expecting
    // square yards!
    howMuchPaintIsNeeded(area = endzone.area)
```

We could use names like areaSquareMeters and areaSquareFeet, but that seems pretty verbose. How can we do better? Typed units!

```

    data class NFLEndzone(
            val width: Feet = Feet(160.0f),
            val length: Feet = Feet(30.0f)) {
        val area get() = SquareFeet(width, length)
    }

    fun howMuchPaintIsNeeded(area: SquareYards) {
        // Calculate how many gallons is needed
        // to cover the endzone
    }

    val endzone = NFLEndzone()

    // Type mismatch
    // Required: SquareYards
    // Found: SquareFeet
    howMuchPaintIsNeeded(area = endzone.area)

    // Here's what the type definitions look like:

    class Feet(value: Float): FloatUnit<Feet>(value, { Feet(it) })

    class SquareFeet(value: Float): FloatUnit<SquareFeet>(value, { SquareFeet(it) }) {
        constructor(width: Feet, length: Feet): this(width.toFloat() * length.toFloat())
    }

    class SquareYards(value: Float): FloatUnit<SquareYards>(value, { SquareYards(it) })
```

This can seem like overkill or unnecessary, but with newer languages like Kotlin, types are often not obvious when reading & writing code. This makes it too easy to unknowingly use the wrong units when doing arithmetic or calling a function that expects cubic feet when your value represents square feet.

Check out [this gist](https://gist.github.com/pm-dev/b1eb3e6dda3ad5064c81be44e458286a) for the full source code.
"""

Article(
  title = "Better Type Safety with Units",
  url = "http://excusethedisruption.com/type-safe-units/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Peter Meyers",
  date = LocalDate.of(2018, 3, 15),
  body = body
)
