
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Many times we’re asked by people working with or planning to work with Kotlin, what our adoption rate is and who’s using Kotlin and what for.

While we do accept [pull requests](https://github.com/JetBrains/kotlin-web-site/blob/master/_data/companies-using-kotlin.yml) and run into an occasional _tweet_ we would really like to know first-hand from you, whether you’re using Kotlin in production and if so, how, and what issues you have faced. This not only helps us answer the question when someone asks about our adoption, but learn more about our community. With close to 4000 people on our [Kotlin Slack](http://kotlinslackin.herokuapp.com/) alone, it’s hard to keep track of every piece of feedback!

As such, we’re asking you if you could kindly give us two minutes of your time and fill out the following survey. Please note that by providing us your details, you are not automatically giving us consent to use your name, application or company name. We would ask for written confirmation from you before doing so.

Thank you!

### [Fill Out Survey](https://blog.jetbrains.com/kotlin/2016/09/kotlin-census-call-to-action/)

"""

Article(
  title = "Kotlin census: call to action",
  url = "https://blog.jetbrains.com/kotlin/2016/09/kotlin-census-call-to-action/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2016, 9, 1),
  body = body
)
