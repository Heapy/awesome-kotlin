
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

Kotlin 1.0 is a programming language made by JetBrains, the creator of the popular IntelliJ IDEA development environment. This programming language for the JVM and Android focuses on safety, interoperability, clarity and tooling support and combines OO and functional features.

After a long wait, Kotlin 1.0 is finally here! According to the [official announcement](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/), ” Kotlin works everywhere where Java works: server-side applications, mobile applications (Android), desktop applications. It works with all major tools and services such as IntelliJ IDEA, Android Studio and Eclipse, Maven, Gradle and Ant, Spring Boot, GitHub, Slack and even Minecraft.”

Interoperability and seamless support for mixed Java+ Kotlin projects has been a focal point in the making of this project. Adoption is easier, which leads to less boilerplate code and more type-safety. Plus, the new programming language has a huge standard library which makes daily tasks not only easy, but also smooth while maintaining the byte code footprint low. Kotlin has been dubbed a ‘pragmatic’ programming language because in its creation developers focused on use cases to transform it into a good tool. Utility was the idea behind Kotlin 1.0, as emphasized by Andrey Breslav, the lead language designer of Kotlin: ” the less our users have to re-learn, re-invent, re-do from scratch, the more they can re-use, the better.”

Breslav explained that Kotlin 1.0 is mature enough and ready for production because the team has been using it in real-life projects on a broad scale over the past couple of years and pointed out that a handful of other companies have been using this new programming language in production “for some time now.”  The lead language designer of Kotlin revealed that the reason why it took so long for them to produce 1.0 was that they paid extra attention to validating their design decision in practice.

The team has kept Kotlin’s development very open since 2012 and has continuously talked to the community, collecting and addressing lots of feedback. Breslav announced that they are planning to establish a centralized venue for design proposals and discussions in order to make the process even more organized and visible. The team’s goals in the near future are to offer constant performance improvements for the Kotlin toolchain, JavaScript support and support generating Java 8 byte code.

## The good about Kotlin

Mike Hearn, a former bitcoin core developer who recently made waves when he declared that the bitcoin project had “failed”, wrote in a post on [Medium](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3#.w9lbw7fso) last July that Kotlin is the programming language he will probably use for the next five to 10 years. He opined that Kotlin is “of greatest interest to people who work with Java today, although it could appeal to all programmers who use a garbage collected runtime, including people who currently use Scala, Go, Python, Ruby and JavaScript.”

According to Hearn, this programming language “comes from industry, not academia and solves problems faced by working programmers today.” It is open source, approachable and can be learned in a few hours by reading the language reference. “The syntax is lean and intuitive. Kotlin looks a lot like Scala, but is simpler,” he added. It allows developers to continue to use their productivity enhancing tools and adopting it is low risk because “it can be trialled in a small part of your code base by one or two enthusiastic team members without disrupting the rest of your project.”

## The bad: pre-1.0

As Breslav remarked in the official announcement, the team took their time before they released Kotlin 1.0 because they paid extra attention to validating their design decision on practice. Therefore, all the problems pointed out by Hearn in his review of Kotlin should not exist anymore now that Kotlin 1.0 has been released. The former bitcoin core developer pointed out that Kotlin’s biggest problems were its immaturity (because it was a pre 1.0 language) and that sometimes the requirements of Java interop resulted in unintuitive limitations.

Hearn also criticized the size of the community, which was smaller at that time. However, according to Breslav, over 11.000 people were using Kotlin last month and almost 5.000 earlier this month. Plus, there is a growing list of companies using Kotlin, including Expedia and Prezi.

Language docs and tutorials can be found on the [official website](https://kotlinlang.org/).

"""

Article(
  title = "Kotlin 1.0: The good, the bad and the evident.",
  url = "https://jaxenter.com/kotlin-1-0-the-good-the-bad-and-the-evidence-124041.html",
  categories = listOf(
    "Kotlin",
    "Review"
  ),
  type = article,
  lang = EN,
  author = "Gabriela Motroc",
  date = LocalDate.of(2016, 2, 16),
  body = body
)
