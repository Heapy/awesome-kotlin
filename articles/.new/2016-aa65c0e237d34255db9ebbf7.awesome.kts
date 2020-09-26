
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "FoldingTabBar Comes to Android. Thank You, Google!",
  url = "https://yalantis.com/blog/foldingtabbar-for-android/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Andriy Hristyan",
  date = LocalDate.of(2016, 12, 15),
  body = body
)
