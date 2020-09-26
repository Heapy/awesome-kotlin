
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

# v1

In this talk, Ty will walk you through setting up and using Kotlin with Gradle to streamline your workflow for Android development, both within the build tool phase and within the application itself. After a brief overview of Kotlin, we’ll dive into how it can be used with Gradle to accelerate Android Development with a consistent language.

[Slides](https://speakerdeck.com/tysmith/better-android-development-with-kotlin-and-gradle-1)

<iframe width="960" height="480" src="https://www.youtube.com/embed/bVSeKexyzhs" frameborder="0" allowfullscreen></iframe>

# v2

In this talk, Ty will walk you through using Kotlin with Gradle and Android to streamline your workflow for app development, both within the build tool phase and within the application itself. After a brief overview of Kotlin language, he'll dive into how it can be used with Gradle to accelerate Android Development with a consistent language through the entire stack. He'll provide real world examples of using Kotlin in Gradle plugins, Gradle script, and Android apps. 

<iframe width="960" height="480" src="https://www.youtube.com/embed/_DaZQ374Chc" frameborder="0" allowfullscreen></iframe>

# v3

Ty Smith, Uber
In this talk, Ty will walk you through setting up and using Kotlin with Gradle to streamline your workflow for Android development, both within the build tool phase and within the application itself, so that you can use a consistent language through the entire Android stack. After a brief overview of Kotlin, we’ll dive into how it can be used with Gradle to accelerate Android Development with a consistent language. I'll walk through a real world example of building a Gradle plugin in and scripts in Groovy, then I'll convert those into Kotlin. An open source repo of the sample will be provided to follow along.

<iframe width="960" height="480" src="https://www.youtube.com/embed/HM7DqX9TY0k" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Better Android Development with Kotlin and Gradle",
  url = "https://www.youtube.com/watch?v=bVSeKexyzhs",
  categories = listOf(
    "Kotlin",
    "Android",
    "Gradle"
  ),
  type = video,
  lang = EN,
  author = "Ty Smith",
  date = LocalDate.of(2016, 10, 3),
  body = body
)
