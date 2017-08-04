
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

[Slides](https://speakerdeck.com/pardom/kotlin-new-hope-in-a-java-6-wasteland)

"""

Article(
  title = "Kotlin: New Hope in a Java 6 Wasteland",
  url = "https://speakerdeck.com/pardom/kotlin-new-hope-in-a-java-6-wasteland",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = slides,
  lang = EN,
  author = "Michael Pardo",
  date = LocalDate.of(2015, 5, 9),
  body = body
)
