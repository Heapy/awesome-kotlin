
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Classes in Kotlin: More power with less effort",
  url = "http://antonioleiva.com/classes-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Antonio Leiva",
  date = LocalDate.of(2016, 12, 7),
  body = body
)
