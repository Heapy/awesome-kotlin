
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.HE
import link.kotlin.scripts.dsl.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """
Next Insurance was founded in the beginning of 2016 and first lines of our production code started accumulating in May 2016. In the beginning I have started writing in Java and experimenting with Kotlin, which saw its 1.0 release two months earlier. 6 months later, the development of our backend services has totally shifted to Kotlin. We still keep a few classes in Java just to make sure that the integration remains seamless but the vast majority of our codebase is written in Kotlin. In this talk I will cover the language features and why I think it is awesome, from null safety to smart casts and data classes. We will also look into the future with 1.1 async/await feature and more.

Haim Yadid is leading backend development in Next insurance. Developing in Java in the last 13 years most of the time focusing on performance optimization and GC tuning.

<iframe width="960" height="480" src="https://www.youtube.com/embed/qSFch-3ULAw" frameborder="0" allowfullscreen></iframe>
"""

Article(
  title = "Taking Kotlin to production, Seriously",
  url = "https://www.youtube.com/watch?v=qSFch-3ULAw",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = HE,
  author = "Haim Yadid",
  date = LocalDate.of(2016, 12, 9),
  body = body
)
