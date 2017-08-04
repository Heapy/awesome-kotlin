
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## The un-mockable

Given that by default Kotlin classes and functions are final (i.e, you need the `open` modifier to be able to inherit from them), when trying to mock behaviour in tests, you’re often left with no choice other than to declare these `open` or introduce interfaces solely for the purpose of testing. Neither of these are really great.

For instance, given the following code

```kotlin
class LoanCalculator {
    fun calculateAmount(customerId: Int): Double {
        return 100.0 * customerId
    }
}

```

and a class that uses the above

```kotlin
class LoanService(val loanCalculator: LoanCalculator) {
    fun authoriseCustomerLoan(customerId: Int): Double {
        if (customerId != 0) {
            return loanCalculator.calculateAmount(customerId)
        }
        return 0.0
    }
}

```

If I want to be able to mock the `calculateAmount` function, I could write something like the following

```kotlin
@Test
fun authoriseCustomerLoan() {
    val mockLoanCalculator = mock(LoanCalculator::class.java)
    `when`(mockLoanCalculator.calculateAmount(3)).thenReturn(300.0)
    val loanService = LoanService(LoanCalculator())
    val amount = loanService.authoriseCustomerLoan(3)
    assertEquals(300.0, amount)

}
```

but on running it, Mockito would complain indicating that it cannot mock a class/method that is final.

## MockMaker

[Mockito 2](http://mockito.org/) solves this. With the recent [release of version 2](https://github.com/mockito/mockito/wiki/What's-new-in-Mockito-2), one of the big changes is the ability to mock the un-mockable. In other words, you can mock final classes in Java and consequently all classes and member functions in Kotlin.

In order to take advantage of this feature, you write your code in exactly the same way you would for mocking an open class/function. However, you need to perform an additional step which basically consists in creating a file named `org.mockito.plugins.MockMaker` and placing this under the `resources/mockito-extensions` directory in your test folder[1].

The file contains a single line:

```
mock-maker-inline
```

![Mockito Resource Folder](http://hadihariri.com/images/mockito-1.png)  

If you’re using Gradle, you can also have it [generate the file for you.](https://github.com/hhariri/mockito-sample/blob/master/build.gradle#L16). You can download the [sample project I’ve created from GitHub](https://github.com/hhariri/mockito-sample/)

Finally, a big kudos to [Rafael](https://twitter.com/rafaelcodes) for his work in making this possible!

[1] This feature is still new and plans are to make it easier to configure as they receive feedback on its usage.

"""

Article(
  title = "Mocking Kotlin with Mockito",
  url = "http://hadihariri.com/2016/10/04/Mocking-Kotlin-With-Mockito/",
  categories = listOf(
    "Kotlin",
    "Testing"
  ),
  type = article,
  lang = EN,
  author = "Hadi Hariri",
  date = LocalDate.of(2016, 10, 4),
  body = body
)
