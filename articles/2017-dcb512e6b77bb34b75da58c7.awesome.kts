
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
I would like to announce release of Kotlin/Native v0.2, with the following changes:

* Added support for coroutines
* Fixed most stdlib incompatibilities
* Improved memory management performance
* Cross-module inline function support
* Unicode support independent from installed system locales
* Interoperability improvements
  * file-based filtering in definition file
  * stateless lambdas could be used as C callbacks
  * any Unicode string could be passed to C function
* Very basic debugging support
* Improve compilation and linking performance

One could download binaries from
* http://download.jetbrains.com/kotlin/native/kotlin-native-linux-0.2.tar.gz
* http://download.jetbrains.com/kotlin/native/kotlin-native-macos-0.2.tar.gz
for Linux and Mac hosts respectively.
Please report issues using https://youtrack.jetbrains.com/issues/KT (subsystem Native).
"""

Article(
  title = "Kotlin Native 0.2 Release",
  url = "https://github.com/JetBrains/kotlin-native/releases/tag/v0.2.0",
  categories = listOf(
    "Kotlin",
    "Kotlin Native"
  ),
  type = article,
  lang = EN,
  author = "Nikolay Igotti",
  date = LocalDate.of(2017, 5, 11),
  body = body
)
