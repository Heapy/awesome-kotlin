
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I [first played](https://yobriefca.se/blog/2012/07/14/kotlin-heres-what-i-think-for-now/) with Kotlin back in 2012, I've written some Android apps with and without it and generally played around with it. Since the [release of Kotlin 1.0](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/) a few days ago I've decided to jump back into it and see if my original views still hold up.

> Herein lies opinion of the personal kind. Proceed with an open mind.

Scala is my day job, not only Scala but **legacy/old** Scala. Not only that but **a lot** of legacy/old Scala. Worse still not only a lot of legacy/old Scala but a lot of **other peoples** legacy/old Scala. Thanks to the feature rich and impressively flexible nature of Scala this means there is a lot of sharp edges, unpredictability and a fresh arduous learning curve for each functional area. It can seem like a language designed by [Atë](https://en.wikipedia.org/wiki/At%C3%AB) at times.

Java is no better of course, it just resides at the other end of a spectrum, with its anaemic yet rigid feature set and almost pointless type system it's no wonder you see so many over-engineered and baklava-class layered solutions. Yeah Java 8 has improved the language a fair bit but the sacrifices made for backwards compatibility has meant those changes don't often go far enough.

Both Scala and Java require a lot of work to simplify so there has to be a middle ground and I (still) think that middle ground is Kotlin.

Think of Kotlin as C# for the JVM, Scala the Good Parts, Java++ or simply a decent general purpose language that won't require the blood of your first born. It's created by Jetbrains, it's about 5ish years old and just gone version 1.0. It is used extensively on Jetbrains products, being hailed as "Swift for Android" and works seamlessly within a mixed code base. It offers features similar to C# Extension methods, Scala implicits, Scala case classes, multiple inheritance and solid collection functionality like LINQ. It doesn't just ape Scala and C# features but introduces relatively original concepts like [delegated properties](http://kotlinlang.org/docs/reference/delegated-properties.html), [class delegation](https://kotlinlang.org/docs/reference/delegation.html#class-delegation) and [typecasting](https://kotlinlang.org/docs/reference/typecasts.html).

I'd also proffer that it has an easier transition from Java to Kotlin than it is for Scala though I have no evidence for this other than my own experience of all 3 languages (including leading mixed skill teams in Scala and Java).

You could ask "but why not one of the other many languages available on the JVM?" and that would be fair. Many JVM languages haven't seen a great deal of traction or remain relatively niche but I think Kotlin is much better placed. It's more "general purpose" than a lot of the alt languages which means it will map better to current practises. It's closer to Java and/or Scala than other languages, it strikes the right balance around the type system with features like type inference and enhanced generics and it doesn't hurt that Jetbrains; a major player in the JVM world; is heavily invested in Kotlins future.

In the two years since I started playing with Kotlin it has aged well. It resisted adding a plethora of features for the sake of it and instead created a core syntax and feature set that allows developers enough wiggle room for being creative without turning them into Wizards of Arcane DSLs. This is a sweet spot for me when it comes to building and supporting a service over many years as it avoids the unnecessary pain that comes from the extreme ends of spectrum that Java and Scala tend to reside. Of course this doesn't mean it's perfect, nothing is. You're still adding another language to a project and taking on the associated baggage that comes with it like tooling differences and additional upskilling. Its not suddenly going to make your organisation move the JVM if they aren't already there. It's also not going to fix all your problems - bad architecture will remain bad architecture and bad patterns will remain bad patterns. However, with that said, **I believe Kotlin will reduce enough of the friction of general software development and maintenance on the JVM to make it worth the investment**.

"""

Article(
  title = "Kotlin - 2 Years On",
  url = "https://yobriefca.se/blog/2016/02/24/kotlin-2-years-on/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "James Hughes",
  date = LocalDate.of(2016, 2, 24),
  body = body
)
