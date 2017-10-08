
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.RU
import link.kotlin.scripts.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """

<iframe width="960" height="480" src="https://www.youtube.com/embed/2oVpnArCdWI" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Дмитрий Полищук - Kotlin + Android: практический ликбез",
  url = "https://www.youtube.com/watch?v=2oVpnArCdWI",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = video,
  lang = RU,
  author = "Дмитрий Полищук",
  date = LocalDate.of(2016, 3, 9),
  body = body
)
