

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Today we are launching a new [Community section at our web-site](https://kotlinlang.org/community/)!

![Explore Kotlin Community](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/01/Screen-Shot-2017-01-26-at-17.37.05.png)

It will provide you with the guidelines to organize Kotlin related events, and the description of support managed by JetBrains. We are now happy to announce that we have formalized the support process for User Groups and Events around Kotlin.

It is exciting to see that in 2016 about 150,000 of developers all over the world tried Kotlin. We now have an amazing community, which enables us to hold Kotlin-dedicated talks and meetups. We have also held two Kotlin Nights: the first one [was in San-Francisco](https://blog.jetbrains.com/kotlin/2016/06/kotlin-night-recordings/) and the other one [was in London](https://blog.jetbrains.com/kotlin/2016/09/kotlin-night-in-london/). What is even more fantastic is that you, the community, start to organise these events, and we are here to help. It is also great to see demand for more Kotlin Nights.

We offer the following programs:

## [Kotlin User Groups Support Program](https://kotlinlang.org/community/user-groups.html)

We appreciate what you do and would love to help you find partners and speakers for your meetups, as well as to help promote your events. We can give you swag and provide you with an advice. Please [fill up the form for your user group](https://docs.google.com/forms/d/e/1FAIpQLSdkLbD_SPbXZDVW2nQPgUiLCW4HOSXysOVK1jPLcShPfyhkNA/viewform) or feel free to reach out via [e-mail](mailto:alina@jetbrains.com).

## [Kotlin Nights Support Program](https://kotlinlang.org/community/kotlin-nights.html)

We encourage everyone who wants to organise a Kotlin Nights event to do so. We can provide support in terms of merchandise, and promote your event via our media channels. We have a Kotlin Night Kit available for you to use, which includes the guidelines, branding materials and swag for speakers and attendees. If you need help reaching out to potential partners or with any other steps, feel free to ping us via [e-mail](mailto:alina@jetbrains.com).

## [Kotlin Talks & Speakers Support](https://kotlinlang.org/community/talks.html)

If you are a speaker, let us know about your upcoming event, and we will send you a t-shirt and stickers for the attendees. By the way, they are available online for everyone at [Stickermule](https://www.stickermule.com/user/1069238064/stickers) and [Apparel Store](https://www.ptxstore.com/jetbrains/product_info.php?products_id=3011). Check out the New Events web page and [submit your talk](https://docs.google.com/forms/d/e/1FAIpQLSfeXstxUcBsOypWtE9McIpYU82szB3yIYkU-30fNXOVoJocEQ/viewform) to show up on the global [Kotlin activities map](https://kotlinlang.org/community/talks.html).

JUGs and GDGs are also encouraged to participate! If you have Kotlin-related talks or specially dedicated meetups, [submit them](https://docs.google.com/forms/d/e/1FAIpQLSfeXstxUcBsOypWtE9McIpYU82szB3yIYkU-30fNXOVoJocEQ/viewform), and we will figure out an optimal support plan for you.

Moreover, we have a standard JetBrains Community Support Program for user groups related to software development , as well as community-driven events, not just Kotlin-related. [You can easily apply here](https://www.jetbrains.com/support/community/?fromMenu#section=communities).

If you have any questions, please ping [Alina](mailto:alina@jetbrains.com), who will be more than happy to help you.
"""

Article(
  title = "Announcing the Support Program for Kotlin User Groups and Events",
  url = "https://blog.jetbrains.com/kotlin/2017/01/announcing-the-support-program-for-kotlin-user-groups-and-events/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Alina Dolgikh",
  date = LocalDate.of(2017, 1, 30),
  body = body
)
