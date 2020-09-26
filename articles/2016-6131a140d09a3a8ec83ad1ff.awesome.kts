
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
My last week [article](https://blog.frankel.ch/solving-the-josephus-problem-in-kotlin) was about the solving the Josephus problem in Kotlin. For ease of comparison, here’s the version I wrote originally:

```kotlin
class Soldier(val position: Int) {
    var state = State.Living
    lateinit var next: Soldier
    fun suicide() {
        state = State.Dead
    }
    fun isDead() = state == State.Dead
}

enum class State {
    Living, Dead
}

class Circle(private val size: Int, private val step: Int) {

    private val first = Soldier(0)

    init {
        var person = first
        while (person.position < size - 1) {
            person = createNext(person)
        }
        val last = person
        last.next = first
    }

    private fun createNext(soldier: Soldier): Soldier {
        val new = Soldier(soldier.position + 1)
        soldier.next = new
        return new
    }

    fun findSurvivor(): Soldier {
        var soldier: Soldier = first
        var numberOfDead = 0
        while (numberOfDead < size - 1) {
            var count: Int = 0
            while (count < step) {
                soldier = nextLivingSoldier(soldier)
                count++
            }
            soldier.suicide()
            numberOfDead++
        }
        return nextLivingSoldier(soldier)
    }

    private fun nextLivingSoldier(soldier: Soldier): Soldier {
        var currentSoldier = soldier.next
        while (currentSoldier.isDead()) {
            currentSoldier = currentSoldier.next
        }
        return currentSoldier
    }
}
```

The post ended with an open question: was the code the _right_ way to do it? In particular:

* Is the code idiomatic Kotlin?
* Lack of `for` means using `while` with `var`s
* Too many mutability (`var`) for my own liking

I’ve received great feedback from the community on many different channels, including Ilya Ryzhenkov from JetBrains, Cédric Beust, Peter Somerhoff and Gaëtan Zoritchak. Thanks guys!

I think the most interesting is this one, very slightly modified from the original [Gist](https://gist.github.com/gzoritchak/1e2ec8d38fc39a4485ba) by Gaëtan:

```kotlin
class Soldier(val position: Int, var state:State = State.Living) {
    fun suicide() {
        state = State.Dead
    }
    fun isAlive() = state == State.Living
}

enum class State {
    Living, Dead
}

class Circle(val size: Int, val step: Int) {

    val soldiers = Array( size, {Soldier(it)}).toList()

    fun findSurvivor(): Soldier {
        var soldier = soldiers.first()
        (2..size).forEach {
            (1..step).forEach {
                soldier = soldier.nextLivingSoldier()
            }
            soldier.suicide()
        }
        return soldier.nextLivingSoldier()
    }

   tailrec private fun Soldier.nextLivingSoldier():Soldier =
            if (next().isAlive())
                next()
            else
                next().nextLivingSoldier()

   private fun Soldier.next() = soldiers.get(
           if (position == size - 1)
               0
           else
               position + 1
   )
}
```

I like this code a lot because it feels more Kotlin-esque. Improvements are many.

* The code is shorter without any loss of readability
* It’s entirely functional, there’s only a single `var` involved:
  * the 2 `while` loops with their associated `var` counter have been replaced by simple `forEach` on `Int` ranges.
  * Chaining `Soldier` instances is not handled in the `Soldier` class itself through the `next()` method but by the containing `Circle`. Thus, a simple backing array can store them and there’s no need for custom code with mutable variables.
* The _recursive_ `nextLivingSoldier()` function has been “annotated” with `tailrec` in order for the compiler to run its optimization magic.
* The `Soldier` class doesn’t know about its container `Circle`‘s `size`, so functions using it have been moved inside the `Circle` class as extension functions to the `Soldier` class. This is a great usage of Kotlin’s extension functions.

This experience reinforced my belief that learning a language by just reading about it is not enough. To truly make it yours, steps should be those:

1. obviously, learn the syntax of the language – and its API,
2. code a solution or an app with this language,
3. ask for feedback,
4. read, analyze and understand the feedback,
5. rinse and repeat.

"""

Article(
  title = "Feedback on the Josephus problem",
  url = "https://blog.frankel.ch/feedback-jospehus-problem",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Frankel",
  date = LocalDate.of(2016, 3, 13),
  body = body
)
