
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/4W3ruTWUhpw" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Andrey Breslav: Kotlin Coroutines, JVMLS 2016",
  url = "https://www.youtube.com/watch?v=4W3ruTWUhpw",
  categories = listOf(
    "Kotlin",
    "Coroutines"
  ),
  type = video,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2016, 8, 3),
  body = body
)
