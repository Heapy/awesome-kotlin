

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We are holding the [Kotlin 1.1 event on March 23][]. Tune in to the live stream [at JetBrains TV][] and see **Andrey Breslav’s demo presentation** about the key features of Kotlin 1.1, including coroutines, JavaScript back-end and more.

Start tweeting your questions today and get answers during the **Q&A session live stream** on March 23. Use the hashtag \#kotlinqa.

We hold 2 live streams to accommodate different time zones. See **the detailed schedule and guidelines** [in the blogpost][Kotlin 1.1 event on March 23].

Check if there is a **Kotlin 1.1 event in your city**. If you don’t find a local community event, join the live stream individually.

[![Kotlin 1.1 event map](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2017/03/Kotlin_1_1event_map.png?resize=640%2C451&ssl=1)](http://kotlinlang.org/community/talks.html?time=kotlin)

Please note that the time of the live stream for the US has been changed to PDT time. The first live stream will start at 9 am PDT and the second at 11 am PDT.

[Kotlin 1.1 event on March 23]: https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-event-2/#more-4726
[at JetBrains TV]: http://jb.gg/kotlinevent1_1
"""

Article(
  title = "Kotlin 1.1 Event in Your City",
  url = "https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-event-in-your-city-2/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Alina Dolgikh",
  date = LocalDate.of(2017, 3, 21),
  body = body
)
