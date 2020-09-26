
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
It’s been a month since we released Kotlin 1.0, and while our user base roughly doubled during this time, we prepared the first bugfix update.

Kotlin 1.0.1 starts a series of 1.0.X versions delivering safe bug fixes and performance improvements to the language (as well as other parts of the project), and new features to our tools and integrations. This time it’s only relatively small IDE features, but bigger things are on the horizon for 1.0.2 and later.

## Changes in 1.0.1

Please find the full change log [here](https://github.com/JetBrains/kotlin/blob/1.0.1/Changelog.md). Some numbers and highlights:


* it’s 47 fixes in the compiler, library and Gradle plugin improvements (performance),
* Compatibility with Gradle 2.12,
* IDE features:
    * Compatibility with IDEA 2016,
    * Kotlin Education Plugin (for IDEA 2016),
    * [KT-9752](https://youtrack.jetbrains.com/issue/KT-9752) More usable file chooser for “Move declaration to another file”,
    * [KT-9697](https://youtrack.jetbrains.com/issue/KT-9697) Move method to companion object and back,
* and 39 assorted fixes in the IDE.

We thank the participants of the EAP who tried the preview builds and reported feedback. Please join the [EAP](https://discuss.kotlinlang.org/t/kotlin-1-0-1-eap/1525) and let’s make Kotlin better together!

"""

Article(
  title = "Kotlin 1.0.1 is Here!",
  url = "http://blog.jetbrains.com/kotlin/2016/03/kotlin-1-0-1-is-here/",
  categories = listOf(
    "Kotlin",
    "Release"
  ),
  type = article,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2016, 3, 16),
  body = body
)
