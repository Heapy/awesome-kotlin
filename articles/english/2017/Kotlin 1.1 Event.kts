

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
In addition to the new features that Kotlin 1.1 brings to your projects, it is also a good reason to meet up with your local community and friends to learn about new opportunities behind the release and impact on the future of Kotlin.

You can organize a Kotlin 1.1 Event together with the JetBrains team and your community on **March 23**. We will hold 2 live stream sessions to accommodate different time zones. You can join the live stream at **5pm or 7pm CET (9am and 11am PDT)**.

[Let us know about your event](https://docs.google.com/forms/d/e/1FAIpQLSf6iXcrIpaNIqeeUJI2L6pntS5yy_iI01PbrO9gTMmX0kg5Lw/viewform) so we can announce it at the blog.

![1600](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2017/03/1600.png?resize=640%2C320&ssl=1)

## The Kotlin 1.1 Event timing:

* **5pm/7pm CET (9am/11am PDT)** – Andrey Breslav’s demo presentation (30 minutes, live stream on YouTube). The link will be provided later;
* **5.30pm/7.30pm CET (9.30am/11.30am PDT)** – 30-minute break. You can have your say on features you want the most in some future versions of Kotlin. Use the Future Features kit and share the result on Twitter. Rules are given below;
* **6.00pm/8.00pm CET (10.00am/12.00pm PDT)** – Interactive Q&A session with the Kotlin team. Rules are given below (45 minutes, live stream);
* Talks and/or workshops from local speakers are welcome. You can schedule them at your own discretion.

## Q&A session rules

* You can start tweeting your questions with the #kotlinQA hashtag on March 21 and until the end of the Q&A session – March 23, 8.45pm CET (12.45pm PDT);
* A Kotlin team representative will sort the questions out;
* The team will answer the questions during the Q&A session live stream;
* If some questions remain unanswered during the session, we will respond to them via Twitter;
* During the session we will give special priority to frequently asked questions.

## Future Features kit rules

The purpose of this survey is to gather the preferences and needs for language features from the community. You can have your say on features you want to see the most in some future versions of Kotlin. Please note it’s more likely that you won’t see those features in v1.2, but we will take your opinion into account when prioritizing our work.

* The Kotlin Future Features kit includes 20 cards with the names and descriptions of features, and stickers for bidding;
* The feature cards should be put on a board (or wall);
* Every attendee gets 3 (three) stickers that she/he can distribute freely among features: bid on up to three different features, or put two or even three bids on a single feature;
* Attendees can read through the descriptions on the cards to learn about the features, and then proceed to bidding as described above;
* Take a picture of the result;
* Post it on twitter with #kotlinevent.

#### [Let us know about your event.](https://docs.google.com/forms/d/e/1FAIpQLSf6iXcrIpaNIqeeUJI2L6pntS5yy_iI01PbrO9gTMmX0kg5Lw/viewform)
"""

Article(
  title = "Kotlin 1.1 Event",
  url = "https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-event-2/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Alina Dolgikh",
  date = LocalDate.of(2017, 3, 6),
  body = body
)
