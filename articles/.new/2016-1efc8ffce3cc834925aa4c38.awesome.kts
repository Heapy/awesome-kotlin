
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Android: Improving sign-in experience with Google Sign-In and SmartLock",
  url = "https://medium.com/@p.tournaris/android-improving-sign-in-experience-with-google-sign-in-and-smartlock-f0bfd789602a#.djgn5w44q",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Pavlos-Petros Tournaris",
  date = LocalDate.of(2016, 12, 14),
  body = body
)
