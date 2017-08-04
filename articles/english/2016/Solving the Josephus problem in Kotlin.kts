
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I recently stumbled upon a [post](http://www.danvk.org/josephus.html) telling about the [Josephus problem](https://en.wikipedia.org/wiki/Josephus_problem) and trying to solve it in different scripting languages. For the sake of brevity, here’s the problem (taken from the referenced post):

> Flavius Josephus was a roman historian of Jewish origin. During the Jewish-Roman wars of the first century AD, he was in a cave with fellow soldiers, 40 men in all, surrounded by enemy Roman troops. They decided to commit suicide by standing in a ring and counting off each third man. Each man so designated was to commit suicide...Josephus, not wanting to die, managed to place himself in the position of the last survivor.

> In the general version of the problem, there are n soldiers numbered from 1 to n and each k-th soldier will be eliminated. The count starts from the first soldier. What is the number of the last survivor?

It seemed like a good challenge to test my building Kotlin skills. Here’s the solution I’ve come up with. First, the test class, using TestNG and a data provider – it’s the perfect use-case:

```kotlin
class JosephusTest {

    @DataProvider
    fun data(): Array<Array<Int>> {
        return arrayOf(
                arrayOf(2, 1, 0),
                arrayOf(3, 1, 0),
                arrayOf(10, 1, 0),
                arrayOf(3, 2, 0),
                arrayOf(4, 2, 1)
        )
    }

    @Test(dataProvider = "data")
    fun circle_of_size_and_step_should_survive_position(size: Int, step: Int, expectedPosition: Int) {
        val circle = Circle(size, step)
        val survivor = circle.findSurvivor()
        assertEquals(survivor.position, expectedPosition)
    }
}
```

Now, the code:

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

This code works fine and the tests are successful.

However, while trying to code the solution, I realized that there were no data-structure implementing circular linked lists neither in Java nor in Kotlin. I had thus to implement my own circular data-structure, but without implementing common collection features.

Now, my problem with the above code is that while the `Soldier` class looks fine by me, the `Circle` class doesn’t. There are too many vars in `Circle` and it feels too much like imperative programming. The lack of `for(;;)` in Kotlin forces me to use a `while` with an outside variable – twice: `count` and `numberOfDead`.

I’ve been thinking that I could improve the situation by changing the data-structure. I just don’t know how... Now, Kotlin and FP gurus, do you have any proposal?

"""

Article(
  title = "Solving the Josephus problem in Kotlin",
  url = "https://blog.frankel.ch/solving-the-josephus-problem-in-kotlin",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Franke",
  date = LocalDate.of(2016, 3, 6),
  body = body
)
