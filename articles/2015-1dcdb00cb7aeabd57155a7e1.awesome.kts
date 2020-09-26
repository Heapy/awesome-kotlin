
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I see a lot of people asking "Is Kotlin ready for production?" ... maybe these are people that are not already using Kotlin wanting a bit of comfort before they put effort into their own investigation. Most people I know that do actually use Kotlin daily think it is obvious Kotlin is already ready. here is why:

- If it compiles, it runs perfectly
- If it doesn't compile due to some rare compiler bug, there is always a work around
- If that doesn't work, drop to Java for 1 class and come back (really rare)

The only real breaking point is source code level changes between versions. And JetBrains even helps with those by providing global refactorings in IntelliJ IDEA that will update your code.

A huge benefit to Kotlin is that the company doing a lot of the development has a very high quality level of focus, represented in the extensive tests that back and protect each Kotlin build. This is a company that knows the cost of an error after shipping a product. And if there is an error, the responsiveness of the team is stellar. Bugs are triaged within a day, breaking bugs are fixed quickly, and other issues are considered thoughtfully. You can also submit pull requests and help out when something is within your coding grasp.

We have taken Kotlin into production via Solr-Undertow at some really large companies who trust it more than they trusted "Solr on Jetty" which has been around for many years. With Kotlin, back in my consulting days, we built a billion record processing pipeline that kicked the pants off of the previous Scala version, and it is the livelihood behind a billion dollar education company's search system. And now we have Kotlin in production in our startup, in fact we ONLY have Kotlin and are using it happily with Vert-x3, Amazon AWS services, ElasticSearch and other Java libraries.

Here is what is NOT helping Kotlin to be perceived as production ready:

- version number sounds fishy, 0.12.1230. This is solely a perception problem and ignores the years of work and the quality level behind that number.

- few major up-to-date Kotlin libraries. First, you don't need a Kotlin library to replace every Java library. Scala loved doing that, and used Killer Frameworks to sell Scala. Kotlin doesn't need it. In some areas bigger libraries are coming because they will be end-to-end more "Kotlin-loving" but it isn't required.

- trash left laying around. If you see an old GitHub repository from 2 years ago with M6 Kotlin code, ask the owner to delete it. A library should be active, at least current to M12, ready for M13 when it arrives, or the owner should hand it off or close it down so they do not confuse and hurt an emerging ecosystem around Kotlin. GitHub has so much trash these days, but it is important place in which people look for libraries.

- LinkedIn seems to resist Kotlin as a skill / search keyword. For 1 day we had Kotlin appearing in search, then it vanished again. LinkedIn engineering says we need higher frequency of the term in skills and CV's (positions). So if you use Kotlin, add it to your LinkedIn and help it break through the threshold.

To help out, my company is open-sourcing anything we can, and are taking this approach with the Kotlin community:

"We will open-source anything we can that does not hurt our competitiveness, we will document it, release it on maven central, we will keep it up to date to any Kotlin milestone/release changes, we will integrate nicely with other Kotlin libraries, we will report all bugs and issues to Kotlin YouTrack and follow up with testing; and we will not stop supporting Kotlin until every one of the key players in Kotlin sphere show up at a bar in Dublin, and we all agree to quit together. Until then, we are all in."

If you are a Kotlin supporter, step up your support because Kotlin can be the biggest player in the JVM space, is the best candidate, and deserves help from those that benefit from its existence.

"""

Article(
  title = "Production Ready Kotlin",
  url = "https://www.linkedin.com/grp/post/7417237-6042285669181648896",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jayson Minard",
  date = LocalDate.of(2015, 8, 26),
  body = body
)
