
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/lpPbCWpBM3I" frameborder="0" allowfullscreen></iframe>


"""

Article(
  title = "Kotlin in Real Projects",
  url = "https://www.youtube.com/watch?v=lpPbCWpBM3I",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Anton Keks",
  date = LocalDate.of(2016, 11, 1),
  body = body
)
