

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Over the past year we have seen significant growth in Kotlin adoption, represented not only by the lines of Kotlin code on GitHub ([8M new lines of code since 1.0 release][]) but also by the numerous companies that have been reaching out to us about their usage, the number of talks being presented by community members at conferences, the increase in new frameworks and libraries, new user groups and meet-ups, as well as community events

To thank the wonderful Kotlin community, and to also share the most exciting things happening in Kotlin, we’re happy to announce [KotlinConf][]**,** a two-day event taking place in San Francisco, November 2017.


[![KotlinConf][KotlinConf 1]][KotlinConf]


We’ll be opening up registration soon with early-bird tickets, so make sure you [sign-up for updates][KotlinConf].

## Call for Papers now open ##

We have keynotes lined up by **Andrey Breslav** and **Erik Meijer**, and talks by some other speakers that we’ll be announcing shortly. But this is a community event and we want you, as members of the Kotlin community to also participate in the conference. As such, there is an open [Call for Papers][] where you can submit talks on things you’ve been doing with Kotlin and would like to share. While there is no rush, don’t delay because the call ends on the 1st of May 2017. 

If you have any questions regarding the conference, please send an email to [info@kotlinconf.com][info_kotlinconf.com]. You can also ask questions on \#kotlinconf on [Slack][]. For sponsorship enquires, please email [sponsorship@kotlinconf.com][sponsorship_kotlinconf.com].

**2017 is going to be an exciting year for Kotlin and we hope to celebrate it with you in November!**


[8M new lines of code since 1.0 release]: https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1/
[KotlinConf]: https://kotlinconf.com/
[KotlinConf 1]: https://i0.wp.com/blog.jetbrains.com/kotlin/files/2017/03/KotlinConfBannerSmaller.png?resize=640%2C332&ssl=1
[Call for Papers]: https://sessionize.com/kotlinconf
[info_kotlinconf.com]: mailto:info@kotlinconf.com
[Slack]: https://kotlinlang.slack.com/
[sponsorship_kotlinconf.com]: mailto:sponsorship@kotlinconf.com
"""

Article(
  title = "Announcing KotlinConf",
  url = "https://blog.jetbrains.com/kotlin/2017/03/announcing-kotlinconf/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Hadi Hariri",
  date = LocalDate.of(2017, 3, 14),
  body = body
)
