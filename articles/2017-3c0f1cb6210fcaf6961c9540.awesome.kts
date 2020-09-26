

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We’re happy to announce that [Kotlin in Action][] – a book about Kotlin written by the members of the Kotlin team – is now out, as both a eBook and a printed book. The book is written for experienced Java developers and covers all aspects of the language, without focusing on any specific problem domain. We received a lot of positive feedback about the book during Manning’s Early Access Preview program, so we hope that you’ll enjoy it too!

![Kotlin in Action book][]


Two chapters from the book, covering Kotlin’s type system and its support for domain-specific languages, are available as a [free preview][] on the publisher’s Web site. And in our online mini-IDE for Kotlin, you can try out all examples from the book, starting from the [very first one][], also for free.

To accompany the release of the book, Manning is offering a special discount, valid today only: you can get half off the book if you enter the code *dotd021017au* during checkout.

Happy reading, and have a nice Kotlin!


[Kotlin in Action]: https://www.manning.com/books/kotlin-in-action
[Kotlin in Action book]: https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/02/20170209_112611.jpeg
[free preview]: https://www.manning.com/books/kotlin-in-action#downloads
[very first one]: http://try.kotlinlang.org/#/Kotlin%20in%20Action/chapter%201/1.1/1.1_ATasteOfKotlin.kt
"""

Article(
  title = "Our first book about Kotlin is out",
  url = "https://blog.jetbrains.com/kotlin/2017/02/our-first-book-about-kotlin-is-out/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2017, 2, 10),
  body = body
)
