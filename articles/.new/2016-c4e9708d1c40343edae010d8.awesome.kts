
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

"""

Article(
  title = "Kotlin VS Java: Basic Syntax Differences",
  url = "https://yalantis.com/blog/kotlin-vs-java-syntax/",
  categories = listOf(
    "Kotlin",
    "Java"
  ),
  type = article,
  lang = EN,
  author = "Irina Galata",
  date = LocalDate.of(2016, 12, 13),
  body = body
)
