
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
<iframe src="https://player.vimeo.com/video/181814363" width="960" height="540" frameborder="0"></iframe>

<a href="https://vimeo.com/181814363">Kotlin - Ready for Production : Hadi Hariri</a> from <a href="https://vimeo.com/javazone">JavaZone</a> on <a href="https://vimeo.com">Vimeo</a>.

[Transcript](https://realm.io/news/gotocph-hadi-hariri-kotlin-ready-for-production/)

"""

Article(
  title = "Kotlin - Ready for Production",
  url = "https://vimeo.com/181814363",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Hadi Hariri",
  date = LocalDate.of(2016, 9, 7),
  body = body
)
