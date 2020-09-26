
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[GeeCON Prague 2014: Andrey Cheptsov - A Reactive and Type-safe Kotlin DSL for NoSQL and SQL](https://vimeo.com/110781020)

"""

Article(
  title = "GeeCON Prague 2014: Andrey Cheptsov - A Reactive and Type-safe Kotlin DSL for NoSQL and SQL",
  url = "https://vimeo.com/110781020",
  categories = listOf(
    "Kotlin",
    "SQL"
  ),
  type = video,
  lang = EN,
  author = "Andrey Cheptsov",
  date = LocalDate.of(2014, 11, 3),
  body = body
)
