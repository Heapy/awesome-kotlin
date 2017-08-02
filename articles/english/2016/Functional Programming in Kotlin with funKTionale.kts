
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

_v1_

[Slides on SpeakerDeck](https://speakerdeck.com/marioariasc/functional-programming-in-kotlin-with-funktionale-1)

_v2_

[Slides on SpeakerDeck](https://speakerdeck.com/marioariasc/functional-programming-in-kotlin-with-funktionale-2)

"""

Article(
  title = "Functional Programming in Kotlin with funKTionale",
  url = "https://speakerdeck.com/marioariasc/functional-programming-in-kotlin-with-funktionale-1",
  categories = listOf(
    "Kotlin",
    "Functional Programming"
  ),
  type = slides,
  lang = EN,
  author = "Mario Arias",
  date = LocalDate.of(2016, 9, 29),
  body = body
)
