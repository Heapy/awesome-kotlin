
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

We always said that Kotlin is really easy to learn. And it is! But it’s not only about the language: learning materials make a difference too. Today we are making another important step in this direction. We are happy to present Kotlin Educational Plugin.

[https://youtu.be/0ponbfQhESY](https://youtu.be/0ponbfQhESY)

Kotlin Edu is a plugin for IntelliJ IDEA 2016.1 which lets you take learning courses. A course contains a number of tasks, and every task has several placeholders which you need to fill in correctly to solve it.

At the moment, there is only one course — the well-known Kotlin Koans, which has been available [online](http://try.kotlinlang.org/koans) for some time and gained considerable popularity among Kotlin learners. The offline versions of the Koans has pretty similar user experience but with all strengths of refactorings and intention actions available in IntelliJ IDEA!

If you have any questions about Kotlin Koans, feel free to ask them in the **#koans** channel [in our Slack](http://blog.jetbrains.com/kotlin/2016/03/kotlin-educational-plugin/kotlinslackin.herokuapp.com).

P.S. If you want to create your own course, contact us directly via [email](mailto:roman.belov@jetbrains.com).

"""

Article(
  title = "Kotlin Educational Plugin",
  url = "http://blog.jetbrains.com/kotlin/2016/03/kotlin-educational-plugin/",
  categories = listOf(
    "Kotlin",
    "Education"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2016, 3, 17),
  body = body
)
