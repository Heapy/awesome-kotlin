
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes
import link.kotlin.scripts.dsl.LanguageCodes.RU
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Беглый взгляд на Async-Await в Android",
  url = "https://habrahabr.ru/post/314574/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = RU,
  author = "Макс Ровкин",
  date = LocalDate.of(2016, 11, 8),
  body = body
)
