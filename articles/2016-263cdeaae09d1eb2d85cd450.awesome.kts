
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
*Taking a peek into the near future*

Andrey Breslav, the lead language designer of Kotlin at JetBrains, is spilling the beans about the next Kotlin release in a new [video](https://realm.io/news/andrey-breslav-whats-next-for-kotlin-roadmap/). Breslav talks about where Kotlin stands right now and what’s next for it.

Three months after releasing Kotlin 1.0.1, Andrey Breslav,  the lead language designer of Kotlin, shared a [video](https://realm.io/news/andrey-breslav-whats-next-for-kotlin-roadmap/) in which he explained where this programming language stands and what’s next for it.

<iframe webkitallowfullscreen="true" mozallowfullscreen="true" allowfullscreen="true" src="//speakerdeck.com/player/f1dea41f659a4c70a6e8fb20291b871b?" style="border: 0px none; background: transparent none repeat scroll 0% 0%; margin: 0px; padding: 0px; border-radius: 5px; width: 960px; height: 480px;" frameborder="0"></iframe>

Breslav announced that the development is in two lines: the incremental updates, 1.0.2, 1.0.3, which are source compatible, and 1.1 —another parallel line where they do language features, and their source compatibility is backward compatibility. 1.0 may not be able to compile 1.1.

## Goals: Java 8/9

Breslav revealed that their current target is Java 6, which means that whatever users compile in Kotlin can run on anything which runs Java 6, including Java 8/9. JetBrains is now working on generating default methods for Java 8 because even though one can always implement methods and interfaces in Kotlin, extending such an interface in Java is not possible with Java 6 since it wouldn’t know that those methods are implemented.

Furthermore, the support library for Java 8 streams will be removed in Kotlin 1.1.

The lead language designer of Kotlin answered a question that has been on everybody’s lips in the past few months, namely “Can I run Kotlin in a native environment without a virtual machine?” Although the answer is “No,” Breslav sweetened it by suggesting that this is not a permanent answer.

Click [here](https://realm.io/news/andrey-breslav-whats-next-for-kotlin-roadmap/) if you want to see the video or read the entire summary.

**SEE ALSO: [10 features I wish Java would steal from the Kotlin language](https://jaxenter.com/10-features-i-wish-java-would-steal-from-the-kotlin-language-2-125308.html)**

## An unexpected ode to Kotlin

Kotlin is loved —we can conclude this after seeing Java champion [Lukas Eder](https://jaxenter.com/10-features-i-wish-java-would-steal-from-the-kotlin-language-2-125308.html) and former Bitcoin core developer [Mike Hearn](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3#.b3lyfhjmr) praise this language. In his ode to Kotlin, Hearn opined that this programming language will be a very successful project because it comes from industry, not academia, it costs nothing to adopt, it can be learned in a few hours and it enforces no particular philosophy of programming. Kotlin imposes no runtime overhead, is highly suitable for enterprise Java shops and adopting it is low risk.

Because nothing is perfect in this world, Hearn also encountered some issues while using Kotlin: there are no type aliases yet, the IDE plugin still throws exceptions more often than it should and it targets Java 6 bytecode. Plus, the community is still experiencing its growth pains and is pickier about some things than Java, but the good news is that Kotlin is outgrowing its minuses.

"""

Article(
  title = "What’s in store for Kotlin this year",
  url = "https://jaxenter.com/whats-in-store-for-kotlin-this-year-126672.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Gabriela Motroc",
  date = LocalDate.of(2016, 6, 2),
  body = body
)
