
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Several days ago, when we were developing a parser in Kotlin, we found ourselves copying and pasting series of tests with slight adjustments. This has become really annoying, especially when we thought of all the tests we would need to change every time the behaviour of our parser is going to change. As we have used Spock on previous projects, we wanted to replicate Spock's [Data Driven Testing](https://spockframework.github.io/spock/docs/1.0/data_driven_testing.html) approach in [Spek](https://jetbrains.github.io/spek/).

## Spock Example
Before diving into the Kotlin exploration, let's quickly review the Spock interface for data driven testing. Spock provides means for defining easily readable tests, and even supports inclusion of test data into the test name using the `@Unroll` annotation:

```groovy
    @Unroll
    def "maximum of #a and #b is #c"(int a, int b, int c) {
        expect:
            Math.max(a, b) == c

        where:
            a | b | c
            1 | 3 | 3
            7 | 4 | 4
            0 | 0 | 0
    }
```

The definition above will yield three tests in the report:

    maximum of 1 and 3 is 3 [PASS]
    maximum of 7 and 4 is 4 [FAIL]
    maximum of 0 and 0 is 0 [PASS]

When the set of data is more complicated, a label can be included as part of the test data parameters:

```groovy
    @Unroll
    def "maximum of two numbers when #testLabel"(String testLabel, int a, int b, int c) {
        expect:
            Math.max(a, b) == c

        where:
            testLabel                 | a | b | c
            "first number is bigger"  | 4 | 1 | 4
            "second number is bigger" | 0 | 5 | 5
            "numbers are equal"       | 3 | 3 | 3
    }
```

## Spek approach
Originally we would like to replicate the features of Spock interface as close as possible.

In Spek, as of version 1.0, all tests are defined within a lambda that is supplied as a constructor argument. This provides flexibility needed to replicate the RSpec DSL, but imposes limitations such as preventing the use of annotations.

### Trusting your heroes
For the sake of this article, let's assume that we are writing a game in Kotlin, and we need to test the query methods in our `Hero` class that depend on the state our `Hero` is in. The state change can be triggered by calling an appropriate method, as per the skeleton defined below:

```kotlin
class Hero() {
    val isRunning: Boolean get() = false
    val isStanding: Boolean get() = true
    val isJumping: Boolean get() = false

    fun run() {}
    fun stand() {}
    fun jump() {}
}
```

In contrast to the Spock example above, we need to run a series of tests for every state of the object. For every one of the four states (initial, running, standing and jumping), we would need to verify all three query methods. With Spock we would have to put the three assertions into a single test method.

### First attempt
Our first approach was to use lists of arbitrary objects to emulate the Spock `when` table. Then all our test blocks can be dynamically defined while iterating over those lists:

```kotlin
class HeroTests: Spek({
    describe("query methods") {
        listOf(
            listOf("initial state", false, false, true, Hero()),
            listOf("when running", false, false, true, Hero().apply { run() }),
            listOf("when jumping", false, false, true, Hero().apply { jump() }),
            listOf("when standing", false, false, true, Hero().apply { stand() })
        ).forEach { test ->
            describe(test.first) {
                fun testLabel(flag: Boolean, action: String): String = "${"$"}{if (flag) "should" else "shouldn't"} be ${"$"}action"

                it(testLabel(test[1], "running")) {
                    expect(test[1]) { test[4].isRunning }
                }

                it(testLabel(test[2], "walking")) {
                    expect(test[2]) { test[4].isWalking }
                }

                it(testLabel(test[3], "jumping")) {
                    expect(test[3]) { test[4].isJumping }
                }
            }
        }
    }
})
```

When running these tests, we can get a very nicely organized output:

![HeroTests report](https://dl.dropboxusercontent.com/u/364765/hero-tests-report.png)

> **Please note**
>
> Only the platform test runner should be used in IDEA to get meaningful test reports for Spek tests. At the time of writing, Gradle test runner output was pretty confusing and rather useless. For the test case above it reported 48 tests named "classMethod", and even though the stack trace for failed tests would indicate the point of failure, it is absolutely meaningless without knowledge of the failed iteration.

We now have a fine-grained output, and adding a new set of tests straight away is fairly simple and straightforward. However, it is difficult to keep track of the value types and meanings. And maintaining these tests could become difficult over time, especially when you need to restructure the test data.

### Moving test data to a DTO
In order to improve clarity and maintainability, we can replace the list of arbitrary objects with a DTO. Additionally, we can move some test reporting helpers, such as test and test group name generators, into that DTO as well:

```kotlin
data class HeroQueryTest(val state: String, val subject: Hero, val isRunning: Boolean, val isJumping: Boolean, val isStanding: Boolean) {
    val runningTestLabel = testLabel(isRunning, "running")
    val standingTestLabel = testLabel(isStanding, "standing")
    val jumpingTestLabel = testLabel(isJumping, "jumping")
    val testGroupLabel = "when ${"$"}state"

    private fun testLabel(condition: Boolean, action: String) = if (condition) "should be ${"$"}action" else "shouldn't be ${"$"}action"
}
```

In the code above, we could have used [introspection](https://kotlinlang.org/docs/reference/reflection.html) to derive the action string from the property name, but that would require to drag in the heavy `kotlin-reflect.jar`, which might not be needed otherwise. But just in case you were wondering, this is how it might look like:

```kotlin
import kotlin.reflect.KProperty

data class HeroQueryTest(val state: String, val subject: Hero, val isRunning: Boolean, val isJumping: Boolean, val isStanding: Boolean) {
    val runningTestLabel = HeroQueryTest::isRunning.testLabel(this)
    val standingTestLabel = HeroQueryTest::isStanding.testLabel(this)
    val jumpingTestLabel = HeroQueryTest::isJumping.testLabel(this)
    val testGroupLabel = "when ${"$"}state"

    private val KProperty<Boolean>.action: String get() = this.name.substring(2).toLowerCase()
    private fun KProperty<Boolean>.testLabel(owner: Any): String = if (this.getter.call(owner)) "should be ${"$"}{this.action}" else "shouldn't be ${"$"}{this.action}"
}
```

Regardless of the internal implementation, we transform our test case, utilizing the newly defined DTO:

```kotlin
class HeroTests : Spek({
    describe("query methods") {
        listOf(
                HeroQueryTest("created", isRunning = false, isJumping = false, isStanding = true, subject = Hero()),
                HeroQueryTest("running", isRunning = true, isJumping = false, isStanding = false, subject = Hero().apply { run() }),
                HeroQueryTest("jumping", isRunning = false, isJumping = true, isStanding = false, subject = Hero().apply { jump() }),
                HeroQueryTest("standing", isRunning = false, isJumping = false, isStanding = true, subject = Hero().apply { stand() })
        ).forEach { test ->
            describe(test.testGroupLabel) {
                it(test.runningTestLabel) {
                    expect(test.isRunning) { test.subject.isRunning }
                }

                it(test.jumpingTestLabel) {
                    expect(test.isJumping) { test.subject.isJumping }
                }

                it(test.standingTestLabel) {
                    expect(test.isStanding) { test.subject.isStanding }
                }
            }
        }
    }
})
```

This looks much nicer - named arguments can be used to rearrange constructor arguments in a way that would make the "table" more readable. At the same time, named arguments are also used to avoid any confusion regarding the meaning of the test data. Not to mention that all of our actual test definitions became clearer and are much easier to follow.

### Using a factory to configure the test subject
In real projects, configuring the subject may be much more complicated. In those cases, a factory object can be used to isolate that complexity, and to provide a readable name for the configured object:

```kotlin
object HeroFactory {
    val default: Hero get() = Hero()
    val running: Hero get() = Hero().apply { run() }
    val standing: Hero get() = Hero().apply { stand() }
    val jumping: Hero get() = Hero().apply { jump() }
}
```

rendering our test "table" even more readable:

```
    HeroQueryTest("created", isRunning = false, isJumping = false, isStanding = true, subject = HeroFactory.default),
    HeroQueryTest("running", isRunning = true, isJumping = false, isStanding = false, subject = HeroFactory.running),
    HeroQueryTest("jumping", isRunning = false, isJumping = true, isStanding = false, subject = HeroFactory.jumping),
    HeroQueryTest("standing", isRunning = false, isJumping = false, isStanding = true, subject = HeroFactory.standing)
```

### Improving the factory
We can go even a bit further, and combine the state label with the configured subject in the factory, returning an instance of a DTO holding both the state label and the subject. This approach is, of course, specific to the particular use case:

```kotlin
data class ConfiguredHero(val subject: Hero, val stateLabel: String)

object HeroFactory {
    val default: ConfiguredHero get() = ConfiguredHero(Hero(), "created")
    val running: ConfiguredHero get() = ConfiguredHero(Hero().apply { run() }, "running")
    val standing: ConfiguredHero get() = ConfiguredHero(Hero().apply { stand() }, "standing")
    val jumping: ConfiguredHero get() = ConfiguredHero(Hero().apply { jump() }, "jumping")
}

data class HeroQueryTest(val configuration: ConfiguredHero, val isRunning: Boolean, val isJumping: Boolean, val isStanding: Boolean) {
    val state = configuration.stateLabel
    val subject = configuration.subject
    // the remaining implementation remains untouched
}
```

Using this configuration makes our test definitions tidier than ever, while retaining the granularity of feedback we had on our first iteration:

```
    HeroQueryTest(HeroFactory.default, isRunning = false, isJumping = false, isStanding = true),
    HeroQueryTest(HeroFactory.running, isRunning = true, isJumping = false, isStanding = false),
    HeroQueryTest(HeroFactory.jumping, isRunning = false, isJumping = true, isStanding = false),
    HeroQueryTest(HeroFactory.standing, isRunning = false, isJumping = false, isStanding = true)
```

## Spock test revisited
Let's define the Spock example from the beginning of this article using our approach:

```kotlin
class MathTests : Spek({
    data class MaxTest(val a: Int, val b: Int, val c: Int)
    describe("max") {
        listOf(
                MaxTest(a = 1, b = 3, c = 3),
                MaxTest(a = 7, b = 4, c = 4),
                MaxTest(a = 0, b = 0, c = 0)
        ).forEach { test ->
            it("calculates maximum of ${"$"}{test.a} and ${"$"}{test.b} as ${"$"}{test.c}") {
                expect(test.c) { Math.max(test.a, test.b) }
            }
        }
    }
})
```

This test case is only one line longer than the original, readable and maintainable, but feels more cluttered.

Let's see if we can implement an approximation of the pipe syntax. Since | is unavailable as a function name, we can use the uppercase `I` instead:

```kotlin
    infix fun Int.I(other: Int): List<Int> = listOf(this, other)
    infix fun List<Int>.I(other: Int): List<Int> = this + listOf(other)
```

This way we could define our data table by listing numbers separated by `I`s. The resulting list should be fed into a factory to produce our DTO:

```kotlin
        fun maxTest(values: List<Int>): MaxTest = MaxTest(values[0], values[1], values[2])
```

on the result of which we can call `apply` to get rid of all of the `test.` prefixes. This is the final form of the test case:

```kotlin
class MathTests : Spek({
    class MaxTest(val a: Int, val b: Int, val c: Int)
    fun maxTest(values: List<Any>): MaxTest = MaxTest(values[0] as Int, values[1] as Int, values[2] as Int)
    infix fun Any.I(other: Any): List<Any> = listOf(this, other)
    infix fun List<Any>.I(other: Any): List<Any> = this + listOf(other)

    describe("max") {
        listOf(
                1 I 3 I 3,
                7 I 4 I 4,
                0 I 0 I 0
        ).forEach { data ->
            maxTest(data).apply {
                it("calculates maximum of ${"$"}a and ${"$"}b as ${"$"}c") {
                    expect(c) { Math.max(a, b) }
                }
            }
        }
    }
})
```

Although the data table now looks cleaner, and has become generic, it has lost it's labels, and the indentation won't be maintained by the IDE, as is the case with Spock. The latter means that in reality this table will become an unreadable mess, meaning that we'd be better off with the DTO constructor using labelled arguments.

# Conclusion
Although we were unable to fully replicate the simplicity of Spock's data tables, the end result became more flexible and powerful. We have discovered a way to create clean and maintainable data-driven tests with Spek, and even made one row in our data "table" generate several named assertions.

The shortest implementation can be made by using lists of arbitrary objects to hold the test data, but it would be hard to maintain. By introducing a few DTOs and a factory object, we have been able to increase both usability and maintainability of the test suite. And although the DTOs are tightly coupled to the test, the factory could be easily reused elsewhere in the test suite. Even so, the size of the whole test case is still kept under 50 lines of code, as opposed to a hundred lines of duplicated unmaintainable test code for the regular, non-data-driven approach.


"""

Article(
  title = "Data Driven Testing with Spek ",
  url = "http://engineering.pivotal.io/post/spek-data-driven-tests/",
  categories = listOf(
    "Kotlin",
    "Testing"
  ),
  type = article,
  lang = EN,
  author = "Konstantin Semenov",
  date = LocalDate.of(2016, 7, 3),
  body = body
)
