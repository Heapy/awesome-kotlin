
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """


"""

Article(
  title = "Why You Must Try Kotlin For Android Development?",
  url = "https://medium.com/@amitshekhar/why-you-must-try-kotlin-for-android-development-e14d00c8084b#.2w8jdujf8",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Amit Shekhar",
  date = LocalDate.of(2016, 11, 10),
  body = body
)
