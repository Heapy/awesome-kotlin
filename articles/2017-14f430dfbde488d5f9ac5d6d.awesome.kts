

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
The Kotlin 1.1 release was warmly met by the community. To demonstrate the new features in Kotlin 1.1, JetBrains offered an online event. All those interested were able to watch a live stream of Andrey Breslav’s demo presentation and get their questions answered during a Q&A session.

This motivated many local communities to meet up: more than 30 user groups hosted events in 21 countries.

You can find the full list of the events at [the Kotlin community web page][]. Over 3000 people joined the broadcast on the day of the event.

The recording of the demo presentation and the Q&A is available on YouTube:

<iframe width="960" height="480" src="https://www.youtube.com/embed/zpyJHSR-5ts" frameborder="0" allowfullscreen></iframe>

## Kotlin 1.1 Event Feedback ##

If you watched the live stream, we want to know what you think! Please share your feedback by [completing this form][]. It should only take about 7-10 minutes. Your input is very important in helping us improve all future Kotlin events.

## Kotlin Future Features Survey ##

![future\_features\_collage\_2][future_features_collage_2]
We also offered all communities to make an impact on Kotlin future. Event organizers received survey kits and event participants could have a say on the most expected features in an off-line mode. The survey gained much attention, and we’ve now placed it online to listen to the wider community. Now you can [have your say on the Kotlin future][] online!

Please note it’s more likely that you won’t see those features in v1.2, but we will take your opinion into account when prioritizing our work.

[the Kotlin community web page]: http://kotlinlang.org/community/talks.html?time=kotlin
[completing this form]: https://docs.google.com/forms/d/e/1FAIpQLSdgKsJzwc1ToAusi-xpEiiE1O4t3HA5xjlbZXDU5Mg0i3qvNg/viewform
[future_features_collage_2]: https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/04/collage_2.png
[have your say on the Kotlin future]: https://docs.google.com/forms/d/e/1FAIpQLSdnCgBonEV5pwN8L903BzdYb9Baf0dpwsJ5YrKnxLveiLFkEQ/viewform
"""

Article(
  title = "Kotlin 1.1 Event Report",
  url = "https://blog.jetbrains.com/kotlin/2017/04/kotlin-1-1-event-report/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Alina Dolgikh",
  date = LocalDate.of(2017, 4, 6),
  body = body
)
