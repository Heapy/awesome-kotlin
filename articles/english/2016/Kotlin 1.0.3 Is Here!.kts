
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We are delighted to present **Kotlin 1.0.3**. This update is not full of brand new and shiny features, it is more about bug fixes, tooling improvements and performance boosts. That’s why you’ll like it ![😉](https://s.w.org/images/core/emoji/72x72/1f609.png) Take a look at the full [change log](https://github.com/JetBrains/kotlin/blob/1.0.3/ChangeLog.md) and issues stats by subsystem:

![Kotlin 1.0.3. Fixed issues](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/Pasted-image-at-2016_06_28-07_25-PM.png)  

Specifically we want to express our gratitude to our contributors whose commits are included in 1.0.3 namely [Yaroslav Ulanovych](https://github.com/yarulan), [Jake Wharton](https://github.com/JakeWharton) and [Kirill Rakhman](https://github.com/cypressious). Kirill has done more than a dozen improvements to formatter and submitted 20+ commits — great job, Kirill, we really appreciate it. Here we also want to thank each and every one of our EAP users who tested and provided their priceless feedback on 1.0.3 prerelease builds.

Although this update is not feature-rich, there are several important improvements and features which are worth highlighting here:

## What’s new in the compiler:

* New option `-jdk-home` to specify the JDK against which the code is compiled
* Options to specify Kotlin language version (`-language-version`) and target Java version (`-jvm-target`) (will have effect in 1.1, added now for forward compatibility)
* More efficient bytecode (no more iterator in `indices` loop, avoid unnecessary operations with `Unit`)
* Various improvements to diagnostic messages

## What’s new in the IDE:

* Autosuggestion for Java to Kotlin conversion for Java code copied from browser and other sources outside of the IDE
  ![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/copypaste.gif)
* Language injection for strings passed to parameters annotated with @Language. Also predefined Java injections applied in Kotlin code. Read more about using language injections in the [documentation](https://www.jetbrains.com/help/idea/2016.1/using-language-injections.html)
  ![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/inject.gif)

* Completion now always shows non-imported classes and methods and adds imports automatically when they are selected
  ![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/import-1.gif)

* Smart completion works after ‘by’ and ‘in’
  ![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/smart.gif)

* Move Element Left/Right actions work for Kotlin
  ![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/move.gif)

* _Decompile_ button is now available in Kotlin bytecode toolwindow and for .class files compiled with Kotlin
  ![decompile](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/decompile.png)

* Now you can navigate from stacktrace to the call site of an inline function

* Inspections and intentions to check and adjust Kotlin configuration in pom.xml files
* Various Spring support improvements

## How to update

To update the plugin, use Tools | Kotlin | Configure Kotlin Plugin Updates and press the “Check for updates now” button. Also, don’t forget to update the compiler and standard library version in your Maven and Gradle build scripts.

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums](https://discuss.kotlinlang.org/), on Slack (get an invite [here](http://kotlinslackin.herokuapp.com/)), or to report issues in the [issue tracker](https://youtrack.jetbrains.com/issues/KT).

Let’s Kotlin!

"""

Article(
  title = "Kotlin 1.0.3 Is Here!",
  url = "https://blog.jetbrains.com/kotlin/2016/06/kotlin-1-0-3-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2016, 6, 30),
  body = body
)
