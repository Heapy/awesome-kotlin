
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
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
