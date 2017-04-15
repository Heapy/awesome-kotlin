
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
With A Domain Driven Approach, This Talk Will Go Through Several Coding Examples And How They Can Be Improved By Leveraging The Powerful Type System In Kotlin With Function Objects, Sealed Classes, Tuples, And The Core Collections. We'll Review The Way Your Application Communicates Across Layers, And How You Can Improve Your Apis With Explicit Types.

<iframe width="960" height="480" src="https://www.youtube.com/embed/zBs8Jptdb-g" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Revisiting Types In Kotlin",
  url = "https://www.youtube.com/watch?v=zBs8Jptdb-g",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Francisco Estevez",
  date = LocalDate.of(2016, 12, 17),
  body = body
)
