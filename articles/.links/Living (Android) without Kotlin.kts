
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Living (Android) without Kotlin",
  url = "https://hackernoon.com/living-android-without-kotlin-db7391a2b170#.kme29gfhg",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Piotr Ślesarew",
  date = LocalDate.of(2016, 12, 21),
  body = body
)
