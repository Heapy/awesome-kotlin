
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

Lately I have been doing a good amount of reading on functional programming, specifically [Haskell](http://haskellbook.com/) and [Elm](http://elm-lang.org/). As part of this reading, I've been exposed to the wonderful world of type systems more advanced than the ones that I am used to, i.e. the Java type system. Exposure to [algebraic data types (ADTs)](https://en.wikipedia.org/wiki/Algebraic_data_type) is one of the things that I've enjoyed about these readings. In the rest of this article, I will demonstrate how ADTs can be used in the Kotlin type system to assure that you've handled all of the possible outcomes from a business use case. For those already familiar with the [`Either` type](https://hackage.haskell.org/package/base-4.8.2.0/docs/Data-Either.html) much of this will be old news to you.

Algebraic data types allow me to create a closed set of possible options for a specific type in my domain.

```kotlin
sealed class CreateSubscriptionResult {
    class Success(val subscription: Subscription): CreateSubscriptionResult()
    class Failure(val errors: List<String>): CreateSubscriptionResult()
}
```

In this case I am using the [`sealed` keyword](https://kotlinlang.org/docs/reference/classes.html#sealed-classes) to tell the type system that there will not be any more possible outcomes for a `CreateSubscriptionResult`. Now I can use the `when` keyword to force the consumer of the `CreateSubscriptionResult` to make sure it handles all of the possible outcomes.

```kotlin
return when (result) {
    is CreateSubscriptionResult.Success ->
        // Do something on success
    is CreateSubscriptionResult.Failure ->
        // Do something on failure
}
```

Were I to omit one of the possible outcomes,

```kotlin
return when (result) {
    is CreateSubscriptionResult.Success ->
        // Do something on success
}
```

then the Kotlin compiler will tell me that I've forgotten something.

```
when expression must be exhaustive, add necessary 'is Failure' branch or 'else' branch instead
```

Well isn't that nice. I now can use the type system to remind myself, and my fellow developers, that something is missing and keep those types of bugs from cropping up in my software without a lot of boilerplate code. If another outcome is added at some point, the compiler will tell me that and I can then figure out how to handle it.

By using the type system to do this, I enable a faster feedback loop than had I written a test for it. Yes I may still need a test for the logic inside of each branch but I would postulate that if I keep it simple enough (like a difference in response code) that a test may be overkill because of the type system assurance. I'll leave that decision up to you.

One place I have been experimenting with this type of pattern is in my [Spring controllers](https://github.com/mikegehard/user-management-evolution-kotlin/blob/master/applications/ums/src/main/kotlin/com/example/ums/subscriptions/SubscriptionsController.kt#L36-L47). I like how it makes the code that handles the outcomes easy to read and understand. Another benefit is that I now have an explicit contract between the use case and the consumer that outlines all of the possible outcomes for the use case. When I combine this contract with the `when` keyword, the compiler will enforce that the client either handles each outcome or decides to explicitly punt on some by using the `else` keyword.

Have some feedback? I'd love to hear it. Reach out to me on Twitter @mikegehard and we can have a conversation about it.

"""

Article(
  title = "Algebraic Data Types In Kotlin",
  url = "http://engineering.pivotal.io/post/algebraic-data-types-in-kotlin/",
  categories = listOf(
    "Kotlin",
    "Functional Programming"
  ),
  type = article,
  lang = EN,
  author = "Mike Gehard",
  date = LocalDate.of(2016, 3, 19),
  body = body
)
