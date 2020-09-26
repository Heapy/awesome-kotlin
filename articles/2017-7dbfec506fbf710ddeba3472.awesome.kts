
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """
A successful project usually grows, and Kotlin is no exception. We are adding new targets (JavaScript and Native) and new computation models (coroutines). This talk is about our vision of the future of Kotlin as a language and a ecosystem.

We'll talk strategy: what we think our industry needs at large and how we are going to fit Kotlin into this picture. We'll talk tactics: how we deal with legacy and compatibility issues, and whether there will ever be Kotlin 2.0. We'll talk operations: can we do “continuous delivery” for language features? Or, more generally, how agile can language development be?

<iframe src="https://player.vimeo.com/video/215556547?title=0&byline=0&portrait=0" width="960" height="540" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
"""

Article(
  title = "The Future of Kotlin: How agile can language development be?",
  url = "https://mixitconf.org/en/2017/the-future-of-kotlin-how-agile-can-language-development-be-",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2017, 4, 30),
  body = body
)
