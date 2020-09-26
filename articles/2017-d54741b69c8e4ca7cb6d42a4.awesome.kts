

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We’re happy to announce that Kotlin 1.0.7, the last update in the Kotlin 1.0.x series, is out. The main focus of this update is to backport the fixes related to Gradle and annotation processing so that they become available to those who can’t upgrade to version 1.1 at this time. The complete list of fixes is available in the [changelog](https://github.com/JetBrains/kotlin/blob/1.0.7/ChangeLog.md).

To use the new version in your Maven or Gradle builds, simply change the Kotlin version number in your build scripts. The command-line compiler can be downloaded from the [Github release page](https://github.com/JetBrains/kotlin/releases/tag/v1.0.7).

In IntelliJ IDEA and Android Studio, we recommend to use the 1.1 version of the plugin, and to switch the language version to 1.0 if you’re using Kotlin 1.0.7 to build your project. If you do want to install the version 1.0.7 of the plugin, you can do so by downloading the version for your IDE from the [Kotlin plugin Web site](https://plugins.jetbrains.com/plugin/6954-kotlin) and using the “Install plugin from disk…” button.

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums](https://discuss.kotlinlang.org/), on Slack (get an invite [here](http://kotlinslackin.herokuapp.com/)), or to report issues in the [issue tracker](https://youtrack.jetbrains.com/issues/KT).

Let’s Kotlin!
"""

Article(
  title = "Kotlin 1.0.7 is out",
  url = "https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-0-7-is-out/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2017, 3, 15),
  body = body
)
