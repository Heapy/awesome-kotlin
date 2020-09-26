
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Setting animation scale for Android UI tests",
  url = "http://www.thedroidsonroids.com/blog/setting-animation-scale-for-android-ui-tests/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Karol Wrótniak",
  date = LocalDate.of(2016, 12, 11),
  body = body
)
