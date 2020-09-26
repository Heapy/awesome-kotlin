
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
As you may have heard Kotlin-based build scripting is coming in Gradle 3.0. In this talk Chris Beams and Rodrigo B. de Oliveira will cover the motivation behind providing first-class support for Kotlin-based build scripts as well as the principles and design tradeoffs that drive the effort. We’ll demonstrate a Kotlin-based Gradle build in action show you how to try it for yourself and share details about the project roadmap.

<iframe width="960" height="480" src="https://www.youtube.com/embed/vv4zh_oPBTw" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Writing Gradle Build Scripts in Kotlin",
  url = "https://www.youtube.com/watch?v=vv4zh_oPBTw",
  categories = listOf(
    "Kotlin",
    "Gradle"
  ),
  type = video,
  lang = EN,
  author = "Chris Beams & Rodrigo B. de Oliveiranp",
  date = LocalDate.of(2016, 8, 5),
  body = body
)
