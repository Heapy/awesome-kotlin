
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

[Slides](https://speakerdeck.com/developer/kotlin-lang)

"""

Article(
  title = "Kotlin Lang",
  url = "https://speakerdeck.com/developer/kotlin-lang",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = slides,
  lang = EN,
  author = "Jemo Mgebrishvili",
  date = LocalDate.of(2016, 11, 20),
  body = body
)
