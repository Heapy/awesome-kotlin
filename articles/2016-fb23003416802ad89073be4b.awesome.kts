
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
# v1

While in New York for Droidcon NYC, Huyen hangs out with Christina Lee, Pinterest Android engineer and cross-country runner, to talk about bringing Kotlin into your production apps.

<iframe width="960" height="480" src="https://www.youtube.com/embed/xRDqDe4rxkM" frameborder="0" allowfullscreen></iframe>

# v2

Christina Lee, Pinterest
It is hard to argue that Kotlin is not an amazing language. What is easy to argue is that there are many unknowns about its performance in the wild and at scale, which makes it hard to make an informed decision about incorporating it into your app. Fortunately for you, we went there and have lived to tell the tale. After more than a year of maintaining a pure Kotlin app at Highlight, and more recently helping Pinterest ramp up Kotlin in Android, we've learned much about the ramifications of moving away from Java. Come learn from our mistakes and successes as we cover the highs, and lows, of using Kotlin in the wild!

<iframe width="960" height="480" src="https://www.youtube.com/embed/mDpnc45WwlI" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Christina Lee: Kotlin in Production",
  url = "https://www.youtube.com/watch?v=xRDqDe4rxkM",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Android Dialogs",
  date = LocalDate.of(2016, 10, 18),
  body = body
)
