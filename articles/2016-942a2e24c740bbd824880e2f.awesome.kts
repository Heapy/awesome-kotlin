
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[Slides on Google Slides](https://docs.google.com/presentation/d/1pgzNnA4R3LU4hPnx0KTvQ0NFj23rPyq-ROr8kOiaxlM/edit#slide=id.p)

"""

Article(
  title = "Generating Kotlin Code for Better Refactorings, Tests, and IDE Support",
  url = "https://docs.google.com/presentation/d/1pgzNnA4R3LU4hPnx0KTvQ0NFj23rPyq-ROr8kOiaxlM/edit#slide=id.p",
  categories = listOf(
    "Kotlin"
  ),
  type = slides,
  lang = EN,
  author = "Eugene Petrenko",
  date = LocalDate.of(2016, 9, 20),
  body = body
)
