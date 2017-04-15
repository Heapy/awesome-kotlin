
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Coding Functional Android Apps in Kotlin: Getting Started",
  url = "https://code.tutsplus.com/tutorials/start-developing-android-apps-with-kotlin-part-1--cms-27827",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Jessica Thornsby",
  date = LocalDate.of(2016, 12, 19),
  body = body
)
