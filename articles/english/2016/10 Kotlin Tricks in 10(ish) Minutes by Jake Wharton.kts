
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin is a new and popular language for Android development. Its major advantages and features are immediately appealing and quick to learn, but it also has a lot of small and thoughtful parts which are harder to discover. This talk will cover 10 of my favorites with real-world examples.


<iframe width="960" height="480" src="https://www.youtube.com/embed/YKzUbeUtTak" frameborder="0" allowfullscreen></iframe>

[Slides](https://speakerdeck.com/jakewharton/10-kotlin-tricks-in-10ish-minutes-square-nyc-november-2016) [Post on Authors Site](http://jakewharton.com/10-kotlin-tricks-in-10ish-minutes/)

"""

Article(
  title = "10 Kotlin Tricks in 10(ish) Minutes",
  url = "https://www.youtube.com/watch?v=YKzUbeUtTak",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Jake Wharton",
  date = LocalDate.of(2016, 11, 10),
  body = body
)
