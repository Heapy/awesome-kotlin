
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
You’ve probably heard the news announcing that you’ll soon be able to [write your Gradle build scripts and plugins in Kotlin](https://blog.jetbrains.com/kotlin/2016/05/gradle-meets-kotlin/). At the Kotlin Night in San Francisco, Hans Dockter from Gradle demoed the first prototype of the support. After the initial announcement, we’ve continued our work together with the Gradle team to flesh out the prototype and bring it closer to the release. And next week, at the [Gradle Summit in Palo Alto](https://gradlesummit.com/), we’ll be sharing the details on our progress.

In addition to presentations by Gradle developers and users, you’ll see [the keynote by Dmitry Jemerov from the Kotlin team](https://gradlesummit.com/schedule/kotlin), talking about the DSL support features of Kotlin and their use in the Gradle build script DSL. And in the expo area, you’ll be able to chat with developers working on Kotlin, IntelliJ IDEA and TeamCity, who will help you with any questions related to the use of Gradle together with JetBrains products.

The summit will be on June 23-24th in Palo Alto, California, and [the registration is open](https://info.gradlesummit.com/conference/palo_alto/2016/06/register). Looking forward to seeing you there!

"""

Article(
  title = "Meet the Kotlin Team at Gradle Summit",
  url = "https://blog.jetbrains.com/kotlin/2016/06/meet-the-kotlin-team-at-gradle-summit/",
  categories = listOf(
    "Kotlin",
    "Gradle"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2016, 6, 13),
  body = body
)
