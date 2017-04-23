
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.slides
import java.time.LocalDate

// language=Markdown
val body = """
<iframe src="//www.slideshare.net/slideshow/embed_code/key/jCpYrAZ3vUM3Rf" width="960" height="480" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen></iframe>
"""

Article(
  title = "Future of Kotlin - How agile can language development be?",
  url = "https://www.slideshare.net/abreslav/future-of-kotlin-how-agile-can-language-development-be",
  categories = listOf(
    "Kotlin"
  ),
  type = slides,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2017, 4, 22),
  body = body
)
