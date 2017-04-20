
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

**Kotlin** – one of the popular programming languages built on top of Java that runs on JVM. Thanks to JetBrains support and excellent IDE integration, it’s an ideal choice for Android development. 100% Java compatibility, interoperability and no runtime overhead is just the beginning of a long list of strengths. Kotlin is supposed to be a subset of SCALA, has clear benefits for developers on one hand and keeps short compile times on the other.

As a **mobile team** we got interested in Kotlin a few months before its final release which gave us time to test it thoroughly before production use. The language has some clear advantages for an **Android programmer** – it enables migration from Java projects that have been under development for some time already. Java & Kotlin **coexistence** simplifies Kotlin introduction as only new functionality is written in JetBrain’s new language leaving all the legacy code untouched.

Transitioning gives the developer an opportunity to use lambdas, new syntax for data objects, extension functions to easily expand Android SDK’s classes functionality and infix notation to write DSL-like structures. Almost all the libraries you use today will work with Kotlin thanks to 100% Java compatibility. The same is true for Android SDK classes – all of them will seamlessly work with the new programming language. Kotlin gives you more choice when it comes to reflection, creating documentation and being null-pointer safe. Android works great with it out of the box so you won’t need to change your development habits.

Our production project in Kotlin turned out to be a success after 4 months of development. We had **0 bugs related to Kotlin as a programming language**. Our code footprint is almost **30%** smaller thanks to JetBrain’s, we benefit from nullpointer safety, closures, translated enums, data objects and use infix notation for logging and displaying Snackbars.

## Kotlin Developer Starter in Android projects

<iframe src="https://www.slideshare.net/slideshow/embed_code/key/2IrMDNtY8cRKrJ" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen=""></iframe>

**[Kotlin Developer Starter in Android projects](https://www.slideshare.net/BartoszKosarzycki/kotlin-developer-starter-in-android-projects "Kotlin Developer Starter in Android projects")** from **[Bartosz Kosarzycki](http://www.slideshare.net/BartoszKosarzycki)**

This presentation is a Developer Starter – a set of hand-picked information allowing a person with no knowledge of Kotlin to start writing basic Android activities and set up a kotlin-based Android project. It starts with language background, reasons for its creation and advantages. Then presents basic use cases, syntax, structures and patterns. Later on Kotlin is presented in Android context. Simple project structure, imports and Kotlin usage with Android SDK is explained. In the end cost of Kotlin compilation is presented and the language is compared to SCALA and SWIFT.

## Kotlin advanced – language reference for android developers

<iframe src="https://www.slideshare.net/slideshow/embed_code/key/fPQ0XeGdMBpMqK" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen=""></iframe>

**[Kotlin advanced – language reference for android developers](https://www.slideshare.net/BartoszKosarzycki/kotlin-advanced-language-reference-for-android-developers "Kotlin advanced - language reference for android developers")** from **[Bartosz Kosarzycki](http://www.slideshare.net/BartoszKosarzycki)**

Second talk on Kotlin language we had at STXNext. We try go deeper into language specifics and look at the positive impact new syntax can have on boilerplate removal and readability improvement.

Kotlin really shines in Android development when one looks at “Enum translation”, “Extension functions”, “SAM conversions”, “Infix notation”, “Closures” and “Fluent interfaces” applied to lists. The talk, however, compares language-specifics of Java & Kotlin in terms of “Type Variance”, “Generics” and “IDE tools” as well.


![](https://stxnext.com/wp-content/uploads/2016/03/bartosz-kosarzycki-author.jpg)

### Bartosz Kosarzycki

Senior developer focused on new technologies and particularly interested in mobile development. Has 3 years experience in Android platform. Bartosz enjoys problem solving, is a huge fan of Agile methodologies and functional programming. For some time already is vividly interested in JVM-based languages like Scala & Kotlin.

"""

Article(
  title = "Android And Kotlin",
  url = "https://stxnext.com/blog/android-and-kotlin/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Bartosz Kosarzycki",
  date = LocalDate.of(2016, 4, 7),
  body = body
)
