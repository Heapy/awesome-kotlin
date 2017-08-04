
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
<iframe width="960" height="480" src="https://www.youtube.com/embed/vmjfIRsawlg" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Kotlin for Java developers",
  url = "https://www.youtube.com/watch?v=vmjfIRsawlg",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Hadi Hariri",
  date = LocalDate.of(2014, 12, 11),
  body = body
)
