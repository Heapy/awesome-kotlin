

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Today we’re releasing the first bugfix update for **Kotlin 1.1**. The primary focus of this update is to address regressions causing incorrect code generation; we wanted to get those fixes out as quickly as possible. The details are available in the [changelog][].

The specific changes worth highlighting are:

 *  **Gradle incremental compilation** is now enabled by default. You can still turn it off as described in the [documentation][] if you need to.
 *  Kotlin plugins are now available in the **[Gradle plugin portal][]**. See the [documentation][documentation 1] for usage instructions.
 *  Using function types with receivers as parameter types of **JavaScript external declarations** is no longer allowed. Previously, lambdas passed to such parameters weren’t invoked with correct arguments, and there’s no easy workaround for this issue, so for now we’ve decided to disable the functionality.

We’ve also updated the Kotlin [Eclipse][] and [NetBeans][] plugins to include Kotlin 1.1.1, so you can enjoy the benefits of the new Kotlin version regardless of your IDE choice.

## How to update ##

To update the IDEA plugin, use Tools | Kotlin | Configure Kotlin Plugin Updates and press the “Check for updates now” button. Also, don’t forget to update the compiler and standard library version in your Maven and Gradle build scripts.
The command-line compiler can be downloaded from the [Github release page][].

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums][], on Slack (get an invite [here][]), or to report issues in the [issue tracker][].

Let’s Kotlin!

[changelog]: https://github.com/JetBrains/kotlin/blob/1.1.1/ChangeLog.md
[documentation]: http://kotlinlang.org/docs/reference/using-gradle.html#incremental-compilation
[Gradle plugin portal]: https://plugins.gradle.org/
[documentation 1]: http://kotlinlang.org/docs/reference/using-gradle.html
[Eclipse]: https://marketplace.eclipse.org/content/kotlin-plugin-eclipse
[NetBeans]: http://plugins.netbeans.org/plugin/68590/kotlin
[Github release page]: https://github.com/JetBrains/kotlin/releases/tag/v1.1.1
[forums]: https://discuss.kotlinlang.org/
[here]: http://kotlinslackin.herokuapp.com/
[issue tracker]: https://youtrack.jetbrains.com/issues/KT
"""

Article(
  title = "Kotlin 1.1.1 is out",
  url = "https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-1-is-out/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2017, 3, 14),
  body = body
)
