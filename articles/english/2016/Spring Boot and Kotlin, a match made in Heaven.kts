
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Despite being very new, spring boot has seen a spectacular start as an amazing kickstarter for spring-based application. You can now start your project in a matter of minutes, not days!

On the other hand, you're still using Java, with all its ceremony regarding code... Wouldn't it be great if we could pair Spring Boot with a powerful yet simple language?

In this talk, I'll live code a Spring Boot application using Kotlin, the friendly language provided by JetBrains. Come discover how you can now cut through all the red tape and finally focus only the important stuff.

<iframe width="960" height="480" src="https://www.youtube.com/embed/sEm_95BPPiA" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Spring Boot and Kotlin, a match made in Heaven",
  url = "https://www.youtube.com/watch?v=",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Nicolas Frankel",
  date = LocalDate.of(2016, 10, 23),
  body = body
)
