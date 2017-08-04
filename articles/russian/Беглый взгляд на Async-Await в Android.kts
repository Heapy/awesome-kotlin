
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes
import link.kotlin.scripts.model.LanguageCodes.RU
import link.kotlin.scripts.LinkType.article
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
