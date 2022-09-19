
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
> Sometimes a large number of independent extensions are possible and would produce an explosion of subclasses to support every combination. _— (GoF)_

As a kid, I was a fan of diving as it gave me freedom and measureless joy. Over the years my passion changed and I have started diving in deep waters of the ocean of programming. Knowing how things work brings a lot of satisfaction so let’s try to understand amazing extensions in Kotlin.

It is said that problems with architecture design are strictly connected with lack of knowledge about how language mechanisms work and what they are for. Sadly but truly nowadays countless amount of engineers have gaps in fundamentals and that is why we need to evangelize them over and over again.

#### What are extensions for?

Kotlin introduces extension functions and extension properties to solve some common scenarios. Imagine that there is a need to add or extend existing functionality to a class. What are the options?

* Extend an entire class
* Use util function
* Decorate

Extending a class is always a risky option, util function will become a big static pain for testing and even using sophisticated Decorator Pattern(if applicable) generates tons of boilerplate. Kotlin extensions give the fourth and the best option you can get.

#### Extend non-extendable

Extension mechanism provides an ability to add functionality even to final classes. Let’s have a look at this code.

```kotlin
import org.junit.Assert.assertEquals
import org.junit.Test

fun String.removeWhitespaces() = replace(" ", "")

class StringKtTest {
    @Test fun removesWhitespacesFromString() {
        val tested = "Piotr Slesarew"

        val actual = tested.removeWhitespaces()

        val expected = "PiotrSlesarew"
        assertEquals(expected, actual)
    }
}
```

From the newbie perspective, it looks pretty odd. Adding functionality to String class? How is that even possible? Extensions do not actually modify classes they extend.

```kotlin
public final class StringKtTestKt {
  @NotNull
  public static final String removeWhitespaces(@NotNull String ${"$"}receiver) {}
}
```

The sample above shows that compiler generates a class and the static function with a receiver as a parameter. The receiver has a type of extended class. Now it is obvious why the code is so awful when extension functions are used in Java. In the end, they are only static functions.

```kotlin
String foo = "Piotr Slesarew";
StringKtTestKt.removeWhitespaces(foo);
```

#### Inlining matters

Inline functions are extremely beneficial in case of a having function that takes functions as parameters. That sounds like an inception but do not be frightened. It is just a functional programming and it is closely related to extension functions.

Lately, I was dealing a lot with collections and for most of the time, I had to search through them in reverse order. My pre-refactored code looked like this.

```kotlin
val collection = listOf("Piotr", "Slesarew")

collection.reversed()
          .forEach(::println)
```

As soon as I typed ._reversed().forEach()_ twentytimes I realized that it is a really good idea to squash it into ._reversedForEach(). R_efactored code used extension function which I added to _Iterable_ type.

```kotlin
val collection = listOf("Piotr", "Slesarew")

collection.reversedForEach(::println)

fun <T> Iterable<T>.reversedForEach(action: (T) -> Unit): Unit {
    for (element in this.reversed()) action(element)
}
```

Everything was great except a performance. I accidentally forgot to _inline_ my function so my code was creating redundant objects. This is a common mistake in designing extension functions.

![](https://cdn-images-1.medium.com/max/800/1*5Wsah0hFTzI4-a0MYBgOoA.png)
_**Left:** no inlining | **Right:** with inlining_

The picture shows Java code generated from Kotlin. It is clear that _inlining_ has a great impact on bytecode. For the closer look, I suggest to read carefully _inline functions_ section in Kotlin reference and play with some samples.

Summing up extension functions have a great potential to cure a lot of headaches but it needs to be remembered that with a great power comes a great responsibility so use them consciously.

_If this was interesting to you, please do hit the heart button ❤ or_ [_let me know on Twitter_](https://twitter.com/SliskiCode)_._

"""

Article(
  title = "Quick dive in Kotlin extensions",
  url = "https://medium.com/@piotr.slesarew/quick-dive-in-kotlin-extensions-317eda4d0c0d#.vulwu13rs",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Piotr Ślesarew",
  date = LocalDate.of(2016, 11, 5),
  body = body
)
