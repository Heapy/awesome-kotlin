
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We're starting Kotlin `1.0.3` EAP.

### What's new

1.0.3 brings some minor changes to compiler and lots of improvements to IntelliJ plugin:
- Improved completion, refactorings, Maven support, formatter and Spring support
- Language injection support
- New intentions, inspections and quickfixes
- Lots of bugfixes

See full [changelog](https://github.com/JetBrains/kotlin/blob/3731e170e459bf02b562464ca02ccb6812760ee2/ChangeLog.md)

### How to get EAP build

To use this build from Gradle or Maven, add [https://dl.bintray.com/kotlin/kotlin-eap](https://dl.bintray.com/kotlin/kotlin-eap) to your repositories.
To use the new version of the Kotlin plugin for IntelliJ IDEA, configure Early Access Preview channel in **Tools | Kotlin | Configure Kotlin Plugin Updates** and press "Check for updates now".

Build number is `1.0.3-eap-30`

Please do provide feedback and report any issues to our [issue tracker](https://youtrack.jetbrains.com/issues/KT#newissue) (please specify your plugin version and IDE version)

"""

Article(
  title = "Kotlin 1.0.3 EAP",
  url = "https://discuss.kotlinlang.org/t/kotlin-1-0-3-eap/1729",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Pavel Talanov",
  date = LocalDate.of(2016, 6, 8),
  body = body
)
