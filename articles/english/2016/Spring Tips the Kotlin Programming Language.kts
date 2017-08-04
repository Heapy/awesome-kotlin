
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

Hi Spring fans! In this tip, we’ll quickly look at the Kotlin programming language and some very high-level things you need to know when building Spring Boot and Kotlin applications

<iframe width="960" height="480" src="https://www.youtube.com/embed/90WRtrbRi0Y" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Spring Tips: the Kotlin Programming Language",
  url = "https://www.youtube.com/watch?v=90WRtrbRi0Y",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Josh Long",
  date = LocalDate.of(2016, 10, 19),
  body = body
)
