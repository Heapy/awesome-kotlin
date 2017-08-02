
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[Announce](https://blog.jetbrains.com/kotlin/2016/09/kotlin-night-in-london/) | [Post in Kotlin Blog](https://blog.jetbrains.com/kotlin/2016/11/kotlin-night-in-london-recordings/)

<iframe width="960" height="480" src="https://www.youtube.com/embed/videoseries?list=PLoo7Ank5cc7FaemZS4sZC0oFnSbdJ-PiH" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Kotlin Night London",
  url = "https://www.youtube.com/watch?list=PLoo7Ank5cc7FaemZS4sZC0oFnSbdJ-PiH&v=TMZD1GxAC8E",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "JetBrains",
  date = LocalDate.of(2016, 11, 15),
  body = body
)
