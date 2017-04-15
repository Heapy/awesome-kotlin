
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.ZH
import link.kotlin.scripts.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """
<iframe width="960" height="480" src="https://www.youtube.com/embed/BUAxqiGrKOc" frameborder="0" allowfullscreen></iframe>
"""

Article(
  title = "Redux for Android using Kotlin",
  url = "https://www.youtube.com/watch?v=BUAxqiGrKOc",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = video,
  lang = ZH,
  author = "Nevin Chen",
  date = LocalDate.of(2016, 11, 22),
  body = body
)
