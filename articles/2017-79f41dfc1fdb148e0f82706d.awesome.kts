import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
A viable business model is key to language adoption.

For the last 18 months, we at Java Magazine have been covering all sorts of interesting JVM languages — from the well known to the obscure. There is no doubt we could continue doing this for another couple of years without covering the same language twice. That’s in many ways the glory of the JVM: it is a great platform for language back ends.

The benefits of the JVM include performance, wide availability and familiarity, excellent tools, and thorough documentation. In addition, there’s a high level of confidence that the JVM will continue to be widely used, so languages that depend on it won’t suddenly need to ind a new platform (as those that targeted Adobe Flash, for example, were forced to do).

JVM languages generally fall into two major categories: those that are ports of existing languages (such as the JRuby port of Ruby and the Jython port of Python) and those that are built from the ground up for the JVM (Groovy, Kotlin, Scala, Golo, Fantom, and many others). Those in the latter group often position themselves as an improved alternative to Java the language. And indeed these languages do provide features or syntax that Java has not implemented — often for specific reasons. Other times, the languages lead to Java’s adoption of features, in which case the Java team has the benefit of examining those implementations when formulating its own. That Oracle sees value in this dialogue is apparent in its longtime production of the JVM Language Summit at midyear, where JVM language designers come together to compare notes among themselves and with the Java team members.

Because of our long coverage of JVM languages, I am occasionally asked which of them will become popular enough to “cross the chasm.” This term, which originated in Geoffrey Moore’s [book of the same name](https://en.wikipedia.org/wiki/Crossing_the_Chasm), refers to an increase in popularity that drives a technology from the exclusive domain of visionaries and early adopters into the wider embrace of pragmatists and especially of businesses. I believe there are only three languages that are capable of this crossing or have already done so: Groovy, Scala, and Kotlin.

Groovy found success as a quirky scripting language that has filled numerous niches where quick but expressive coding is needed. It is the scripting language for many testing frameworks and is used for writing build scripts in Gradle. It is also unique among the primary JVM languages (the three mentioned above plus Java) in that it did not require corporate sponsorship to become popular. (Even though Pivotal did support it for a few years, Groovy was popular long before Pivotal’s acquisition and has continued to be since Pivotal stopped sponsorship.) This is testament to the community skills of the project’s longtime leader, Guillaume Laforge.

Today, no language can hope to cross the chasm as Groovy did — that is, without serious financial backing. Writing a language is a very expensive proposition, as is promoting it. While originally an academic creation, Scala was backed by the startup Typesafe until the company realized — as Pivotal did with Groovy — that there is no revenue to be made in selling a new language. As a result, Typesafe changed its name to Lightbend and refocused on its nonlanguage products. The break from being the “Scala company” was so clean that the press release announcing the name change did not even mention the language in the body of the announcement. As I said, there’s just no money in languages.

Kotlin relies on a rather different model. The language was devised in part for JetBrains’ internal use. Its design is pragmatic and aimed at helping the company reduce costs in developing its extensive line of developer tools. The benefits of developing and promoting Kotlin outweigh its costs and, crucially, JetBrains derives its income from products other than Kotlin. The costs, however, are significant. According to Andrey Breslav at JetBrains, more than two dozen [full-time equivalents](https://en.wikipedia.org/wiki/Full-time_equivalent) are developing and promoting Kotlin.


In the process, Kotlin has morphed into more than just an efficiency tool for JetBrains. Its intensely pragmatic orientation has strongly resonated with a significant and active community, which accelerates its movement across the chasm. Kotlin thereby enables JetBrains to bring new developers into its tool ecosystem. But the growing user base also presents the company with the challenge that successful languages often face: managing the demands of users versus the company’s own desires for the language.

Because economics support Kotlin’s evolution and JetBrains’ longstanding knowledge of developers will help it work with the community, I expect that within the next few years Kotlin will fully cross the chasm and emerge as a—or possibly the—primary non-Java JVM language, so proving yet again the robustness of the JVM ecosystem.

Andrew Binstock, Editor in Chief
* javamag_us@oracle.com
* [@platypusguy](https://twitter.com/platypusguy)
"""

Article(
  title = "The Rise and Fall of JVM Languages",
  url = "http://www.javamagazine.mozaicreader.com/MarApr2017",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrew Binstock",
  date = LocalDate.of(2017, 4, 3),
  body = body
)
