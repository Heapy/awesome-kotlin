
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
There are dozens of programming languages that are used to build a software which runs on JVM. Languages like Groovy, Scala and Clojure are the well known already and now the new cool kid [Kotlin](https://kotlinlang.org/) brings some more disruption into the Java world.

Kotlin is a statically typed, open source language from JetBrains. It has been around since 2011, but wasn't heavily used until last years. We have seen constant updates within 1.0.* versions and JetBrains has already announced the new 1.1 (EAP) version which brings more tools, such as type aliases, inline properties and coroutines. 

The great thing about Kotlin is that it is 100% compatible with Java, you can mix Java and Kotlin with no extra effort! 

After reading [the post](http://tengio.com/blog/more-readable-tests-with-kotlin/) from @tengio I've tried to use Kotlin in writing [tests](https://github.com/vtorosyan/kotlin-spring-data-neo4j/tree/master/src/test/kotlin/com/examples/kotlin) and found it suprisingely effective and natural. 

To illustrate how Kotlin can be used to make testing easy and natural let's write some class which validates given password and write unit tests for it.

The requirements for the password are rather simple
* Password should contain only numbers and letters
* Password should have more than 5 and less than 10 characters

```java
public class PasswordValidator {
	public boolean validate(String password) { /** ... */ }
}
```

We don't need implementation details as we are doing TDD, so everything is ready to write some tests now! 
There is an unofficial naming convention from [R. Osherove](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html) for unit tests which I've been using for a lot of years. Basically, the convention says that the name of the method should describe the unit of work, state under which the test is done, and expected behaviour.

```
[UnitOfWork_StateUnderTest_ExpectedBehavior]
```

I've seen this convention in many projects and I believe that it is really human-driven, simply makes sense and the best one so far in Java(e.g. compared to standard given-when-then approach). Having that convention, we can have the following test specifications

```java
@Test
public void validate_PasswordWith6CharsContainingNumbersAndDigits_ReturnTrue() {}

@Test
public void validate_PasswordWith5CharsContainingNumbersAndDigits_ReturnFalse() {}

@Test
public void validate_PasswordWith6CharsContainingBrackets_ReturnFalse() {}

@Test
public void validate_PasswordWith5CharsContainingBrackets_ReturnFalse() {}

```


Now let's write the same test specifications using Kotlin. In Kotlin, you can use backticks(``) to name your methods or classes as you like. For example if we transform our `PasswordValidator` class from Java to Kotlin, it can look like

```kotlin
class `Password Validator`() {
	fun `validate given password`() { /** ... */ }
} 
```

Obviously, this is not something convenient and we won't do it, but we'll use it in our test specifications.


```kotlin
@Test
fun `Validate password and return true when the parameter has 6 chars and contains only numbers and digits`() {}

@Test
fun `Validate password and return false when the parameter has 5 chars and contains only numbers and digits`() {}

@Test
fun `Validate password and return false when the parameter has 6 chars and contains brackets`() {}

@Test
fun `Validate password and return false when the parameter has 5 chars and contains brackets`() {}
```

As you can see the test is 100% human readable, even someone who has no idea what test or class is doing can read the method name and understand the purpose. Even more, if you have already somewhere test cases/scenarios written, they can be easily transformed to the unit tests by copy pasting the titles of your test scenarios.

Luckily, it does not end here. We want to have tests as executable, behaviour driven specifications. Having some context about the functionality only in the test method name is not behaviour driven at all. The new [Spek](https://jetbrains.github.io/spek/) framework comes for a help. Here is our tests in Spek

```kotlin
class PasswordValidatorTest : Spek({
    describe("A password validator") {
        val validator = PasswordValidator()

        it("should return true when the parameter has 6 chars and contains only numbers and digits") {
            val result = validator.validate("qazwsx123")
            assertTrue(result)
        }

        it("should return false when the parameter has 5 chars and contains only numbers and digits") {
            val result = validator.validate("qazw2")
            assertFalse(result)
        }

        it("should return false when the parameter has 6 chars and contains brackets") {
            val result = validator.validate("qazws}")
            assertFalse(result)
        }

        it("should return false when the parameter has 5 chars and contains brackets") {
            val result = validator.validate("qaw2}")
            assertFalse(result)
        }
    }
})
```

Although it's not really convenient to put the specification in the initialization block of Spek, but we have purely behaviour driven, human readable and machine executable test. 

Spek is the most used framework in Kotlin world now, but not the only one. If you are coming from Scala world you may find interesting the [KotlinTest](https://github.com/kotlintest/kotlintest), which is highly inspired by [ScalaTest](http://www.scalatest.org/).

If you want to experiment more, check out the list below for some other frameworks/libraries for Kotlin/JVM 

* [spek](https://github.com/jetbrains/spek) - A specification framework for JVM.
* [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - A small library that provides helper functions to work with Mockito in Kotlin.
* [kotlintest](https://github.com/kotlintest/kotlintest) - Flexible and comprehensive testing tool for the Kotlin ecosystem based on and heavily inspired by the superb Scalatest.
* [hamkrest](https://github.com/npryce/hamkrest) - A reimplementation of Hamcrest to take advantage of Kotlin language features.
* [expekt](https://github.com/winterbe/expekt) - Expekt is a BDD assertion library for Kotlin, inspired by Chai.js. It works with your favorite test runner such as JUnit and Spek.
* [Kluent](https://github.com/MarkusAmshove/Kluent) - Kluent is a "Fluent Assertions" library written specifically for Kotlin.
* [kspec](https://github.com/raniejade/kspec) -Specifications for Kotlin.
* [balin](https://github.com/EPadronU/balin) - Balin is a browser automation library for Kotlin. It's basically a Selenium-WebDriver wrapper library inspired by Geb.
* [konsent](https://github.com/dmcg/konsent) - An acceptance testing library for Kotlin.

"""

Article(
  title = "Natural testing with Kotlin",
  url = "http://vtorosyan.github.io/natural-testing/",
  categories = listOf(
    "Kotlin",
    "Testing"
  ),
  type = article,
  lang = EN,
  author = "Vardan Torosyan",
  date = LocalDate.of(2016, 8, 31),
  body = body
)
