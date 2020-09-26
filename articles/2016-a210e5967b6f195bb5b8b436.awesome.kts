
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.RU
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Погружение в Async-Await в Android",
  url = "https://habrahabr.ru/post/314656/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = RU,
  author = "Макс Ровкин",
  date = LocalDate.of(2016, 11, 9),
  body = body
)
