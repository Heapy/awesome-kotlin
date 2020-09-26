
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/2IhT8HACc2E" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "JVMLS 2015 - Flexible Types in Kotlin",
  url = "https://www.youtube.com/watch?v=2IhT8HACc2E",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2015, 8, 12),
  body = body
)
