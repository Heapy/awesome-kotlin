
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/80xgl3KThvM" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Kotlin NoSQL for MongoDB in Action",
  url = "https://www.youtube.com/watch?v=80xgl3KThvM",
  categories = listOf(
    "Kotlin",
    "SQL"
  ),
  type = video,
  lang = EN,
  author = "Andrey Cheptsov",
  date = LocalDate.of(2015, 10, 22),
  body = body
)
