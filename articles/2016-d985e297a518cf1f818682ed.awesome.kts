
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

[Kotlin DSL: Anko](https://docs.google.com/presentation/d/12mkyGQZO22kf0_6kp2K6xyFdpg0nBLqGtNcVR-cV4M8/pub)

"""

Article(
  title = "Kotlin DSL: Anko",
  url = "https://docs.google.com/presentation/d/12mkyGQZO22kf0_6kp2K6xyFdpg0nBLqGtNcVR-cV4M8/pub",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = slides,
  lang = EN,
  author = "@maciekjanusz",
  date = LocalDate.of(2016, 4, 6),
  body = body
)
