
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

[Slides](https://speakerdeck.com/marioariasc/functional-programming-in-kotlin-with-funktionale-2)

<iframe width="960" height="480" src="https://www.youtube.com/embed/klakgWp1KWg" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Functional Programming in Kotlin with funKTionale (Video)",
  url = "https://www.youtube.com/watch?v=klakgWp1KWg",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Mario Arias",
  date = LocalDate.of(2016, 12, 16),
  body = body
)
