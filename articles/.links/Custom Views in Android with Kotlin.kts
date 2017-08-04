
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Custom Views in Android with Kotlin",
  url = "https://antonioleiva.com/custom-views-android-kotlin/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Antonio Leiva",
  date = LocalDate.of(2016, 12, 27),
  body = body
)
