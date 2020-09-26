
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
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
