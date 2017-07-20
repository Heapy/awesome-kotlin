
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/rjc8_HGHy3c" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Type safe JavaFX CSS with TornadoFX",
  url = "https://www.youtube.com/watch?v=rjc8_HGHy3c",
  categories = listOf(
    "Kotlin",
    "TornadoFx"
  ),
  type = video,
  lang = EN,
  author = "Edvin Syse",
  date = LocalDate.of(2016, 4, 24),
  body = body
)
