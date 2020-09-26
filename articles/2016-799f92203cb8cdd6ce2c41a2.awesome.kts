
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Last week, I started my comparison of Scala and Kotlin with the [Pimp my library](/scala-vs-kotlin/1/) pattern. In the second part of this serie, I’d like to address operator overloading.

## Overview

Before to dive into the nitty-gritty details, let’s try first to tell what it’s all about.

In every language where there are functions (or methods), a limited set of characters is allowed to define the name of said functions. Some languages are more lenient toward allowed characters: naming a function `\O/` might be perfectly valid.

Some others are much more strict about it. It’s interesting to note that Java eschewed the ability to use symbols in function names besides `${"$"}` - probably in response to previous abuses in older languages. It definitely stands on the less lenient part of the spectrum and the Java compiler won’t compile the previous `\O/` function.

The name operator overloading is thus slightly misleading, even if widespread. IMHO, it’s semantically more correct to talk about operator characters in function names.

## Scala

Scala stands on the far side of leniency spectrum, and allows characters such as `+` and `£` to be used to name functions, alone or in combinations. Note I couldn’t find any official documentation regarding accepted characters (but some helpful discussion is available [here](https://stackoverflow.com/questions/7656937/valid-identifier-characters-in-scala)).

This enables libraries to offer operator-like functions to be part of their API. One example is the `foldLeft` function belonging to the `TraversableOnce` type, which is also made available as the `/:` function.

This allows great flexibility, especially in defining <abbr title="Domain Specific Language">DSL</abbr>s. For example, mathematics: functions can be named `π`, `∑` or `√`. On the flip side, this flexibility might be subject to abuse, as `\O/`, `^_^` or even `|-O` are perfectly valid function names. Anyone for an emoticon-based API?

```scala
def ∑(i: Int*) = i.sum

val s = ∑(1, 2, 3, 5) // = 11
```

## Kotlin

Kotlin stands on the middle of the leniency scale, as it’s possible to define only a [limited set of operators](https://kotlinlang.org/docs/reference/operator-overloading.html).

Each such operator has a corresponding standard function signature. To define a specific operator on a type, the associated function should be implemented and prepended with the `operator` keyword. For example, the `+` operator is associated with the `plus()` method. The following shows how to define this operator for an arbitrary new type and how to use it:

```kotlin
class Complex(val i: Int, val j: Int) {
    operator fun plus(c: Complex) = Complex(this.i + c.i, this.j + c.j)
}

val c = Complex(1, 0) + Complex(0, 1) // = Complex(1, 1)

```

## Conclusion

Scala’s flexibility allows for an almost unlimited set of operator-looking functions. This makes it suited to design DSL with a near one-to-one mapping between domains names and function names. But it also relies on implicitness: every operator has to be known to every member of the team, present **and** future.

Kotlin takes a much more secure path, as it allows to define only a limited set of operators. However, those operators are so ubiquitous that even beginning software developer know them and their meaning (and even more so experienced ones).

"""

Article(
  title = "Scala vs Kotlin: Operator overloading",
  url = "https://blog.frankel.ch/scala-vs-kotlin/2/",
  categories = listOf(
    "Kotlin",
    "Scala"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Fränkel",
  date = LocalDate.of(2016, 7, 24),
  body = body
)
