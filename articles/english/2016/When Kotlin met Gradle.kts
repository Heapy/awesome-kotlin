
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
_Love story alert_

![Kotlin](https://jaxenter.com/wp-content/uploads/2016/06/Screen-Shot-2016-06-06-at-2.56.13-PM.png)

Source: [http://gradle.org/](http://gradle.org/)

The idea of having a Kotlin-based approach to writing Gradle build scripts and plugins was enticing enough to convince the JetBrains team and the Gradle team to concoct a way to get the best of both worlds. And this is how Gradle Script Kotlin was born.

Kotlin seems tireless these days! First, Gradle announced what they call _[Gradle Script Kotlin](http://gradle.org/blog/kotlin-meets-gradle/)_; then JetBrains announced [a new release of their plugin](https://blog.jetbrains.com/kotlin/2016/06/kotlin-eclipse-plugin-0-7-is-here/) for Eclipse IDE. But first things first!

## Gradle + Kotlin = New project

Gradle’s Chris Beams [announced](http://gradle.org/blog/kotlin-meets-gradle/) in mid-May that after years of closely watching Kotlin, the team sat down with some people from the JetBrains team in late 2015 and tried to answer the following questions: what might it look like to have a Kotlin-based approach to writing Gradle build scripts and plugins? How might it help teams—especially big ones—work faster and write better structured, more maintainable builds?

Kotlin could give Gradle users proper IDE support from auto-completion to refactoring and everything in-between since this statically-typed language offers deep support in both IDEA and Eclipse. Plus, the fact that it is rich with features such as first-class functions and extension methods means that it could retain and improve on the best parts of writing Gradle build scripts. The result is called Gradle Script Kotlin, a Kotlin-based build language for Gradle.

[The first milestone](https://github.com/gradle/gradle-script-kotlin/releases/tag/v1.0.0-M1) towards version 1.0 was launched less than a month ago and its repository was open-sourced at [https://github.com/gradle/gradle-script-kotlin](https://github.com/gradle/gradle-script-kotlin). Take a look at the outcome:

[![Screen Shot 2016-06-06 at 3.28.27 PM](https://jaxenter.com/wp-content/uploads/2016/06/Screen-Shot-2016-06-06-at-3.28.27-PM.png)](http://gradle.org/blog/kotlin-meets-gradle/)

Source: [http://gradle.org/blog/kotlin-meets-gradle/](http://gradle.org/blog/kotlin-meets-gradle/)


As you begin to explore what’s possible in the IDE, you will will be able to ‘play with’ things such as refactoring, quick documentation, auto-completion and content assist, navigation to source and more.

## Kotlin Eclipse plugin 0.7

Roughly two weeks after _Gradle Script Kotlin_ was announced, Kotlin returned to the spotlight thanks to a new release. In addition to the support for Kotlin 1.0.2 compiler, Eclipse plugin 0.7 brings highly important features and improvements such as a rebuilt code formatting feature and the _Organize Imports_ feature which cleans unused imports, adds missing imports for classes used in the file and resorts them.

The team also added some quick-fixes about missing or illegal modifiers. As a result, it is now possible to add an open modifier to a declaration which is overridden or subclassed and it is easy to deal with the “class must be declared abstract” compiler error. Plus, invalid modifier removing is now available for the quick-fix popup.

"""

Article(
  title = "When Kotlin met Gradle",
  url = "https://jaxenter.com/when-kotlin-met-gradle-126726.html",
  categories = listOf(
    "Kotlin",
    "Gradle"
  ),
  type = article,
  lang = EN,
  author = "Gabriela Motroc",
  date = LocalDate.of(2016, 6, 6),
  body = body
)
