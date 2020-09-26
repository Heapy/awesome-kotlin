
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "An adventure with Kotlin - Part I",
  url = "http://angrybyte.me/post/154701023805/kotlin-adventures-1",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Milos Marinkovic",
  date = LocalDate.of(2016, 12, 20),
  body = body
)
