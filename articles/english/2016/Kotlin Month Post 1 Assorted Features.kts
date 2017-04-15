
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
To open up Kotlin Month, we’ll look at a small assortment of Kotlin’s features that aren’t big enough to merit their own post and don’t fit into a grouping for the upcoming posts, starting with operator overloading.

## Operator Overloading

Because of the mess it made in C++, Java decided to not include [operator overloading](https://kotlinlang.org/docs/reference/operator-overloading.html), and it has unfortunately stuck to that decision, presumably because people haven’t made a big enough stink about it. This makes sense if a majority of Java programmers haven’t had much/any experience with a language that does, which I think may be the case at this point.

My opinion is that it isn’t specifically operator overloading that was the problem in C++; the problem was the free reign to invent whatever operators you wanted. This is still technically possible in Kotlin (see the next feature), but it’s still harder to abuse in its case. And obviously, operators can still be abused and used in ways they’re not meant to be, but that applies for just about anything in programming.

Anyway, I really enjoy the increased readability and conciseness of properly used operators over functions, so it’s nice to have.

## Infix “Operator” Methods

Using the [infix notation](https://kotlinlang.org/docs/reference/functions.html#infix-notation) for functions allows Kotlin to allow extending the list of available “operators” without falling into the same deep pit that C++ did by limiting them to legal method names. By doing so, they kept coders from using symbols that had no inherent meaning, making it much more likely that people will be able to decipher the meaning of the operator, assuming it’s named well.

One pointer I would give to those considering infixing one of their functions is that you shouldn’t use them in the case where they’re meant to be chained in a fluent interface. It can become very difficult to distinguish between objects and operators, since they’re all just a bunch of identifiers. A potential exception to this rule is an internal DSL that is meant to be used in a configuration-like file.

For the most part, I don’t think this feature will be used a whole lot, but I can definitely see enough use cases to make it a valuable part of Kotlin.

## Streaming

Of all the features of all the languages out there, being able to filter, map, and reduce over a collection declaratively is probably my all-time favorite. Kotlin does not disappoint, to this end. Being required to compile to Java 6, they didn’t automatically have access to the new Stream API, so they made their own essentially, called [Sequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence/index.html). It is largely the same idea, but doesn’t support doing the work in parallel. Luckily, it makes up for it by having an even larger set of methods to use. Not only that, but the collections themselves (and even Iterators) have the same set (or at least similar set) of methods as a Sequence, making it so you don’t have to make the Stream or Sequence instance to do a simple filter or map. Unfortunately, doing the calls from a collection or iterator is eager and creates (usually) a List right away. That’s what Sequence is for: to make it lazy.

I really like that they 1) made Iterators be iterable (meaning you can put Iterators directly into a for loop, just like in Python; the iterator() method simply returns “this”) and 2) the streaming methods can be called from iterators, too, so you don’t need an eagerly created collection to start with.

Also, [Ranges](https://kotlinlang.org/docs/reference/ranges.html) allow for the quick creation of numbers to iterate over as well as checking if a value is within it.

And Strings are iterable!

## Data Classes

[Data classes](https://kotlinlang.org/docs/reference/data-classes.html) are a quick, easy way to make bean-like classes in Kotlin. By annotating a class with “data”, it will use all the properties defined in the primary constructor to define toString(), hashCode(), equals(), and the [component functions](https://kotlinlang.org/docs/reference/multi-declarations.html). It also creates a [copy() method](https://kotlinlang.org/docs/reference/data-classes.html#copying) that is pretty awesome.

In an older article, I tried to recreate this for Python, then someone commented that it pretty much already existed as named tuples :P

## Outro

I could on and on, as everything that Kotlin does differently from Java is generally pretty good, but then I’d be digging a bit too deep into the minutia of it all and bore you. If you want to learn more about it, check out [my other Kotlin articles](https://programmingideaswithjake.wordpress.com/kotlin/) or look at [Kotlin’s awesome reference page](https://kotlinlang.org/docs/reference/).

Next week, I’ll be shouting my praises of Kotlin’s decisions about inheritance, its special cases, and defaults.

"""

Article(
  title = "Kotlin Month Post 1: Assorted Features",
  url = "https://programmingideaswithjake.wordpress.com/2016/02/27/kotlin-month-post-1-assorted-features/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 2, 27),
  body = body
)
