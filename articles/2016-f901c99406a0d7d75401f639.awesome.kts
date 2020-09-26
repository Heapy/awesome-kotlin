
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[![jetbrains](http://www.globalnerdy.com/wordpress/wp-content/uploads/2016/02/jetbrains.png)](https://www.jetbrains.com/)Over the years, [JetBrains](https://www.jetbrains.com/) have released a number of nice development tools. Many of you from the .NET world live and die by [ReSharper,](https://www.jetbrains.com/resharper/) the add-on that supercharges Visual Studio and takes a lot of drudgery and donkey-work out of .NET development. If you’re a Java developer tired of the nightmare of self-flagellation that is Eclipse, you probably use [IntelliJ IDEA,](https://www.jetbrains.com/idea/) which is a far nice environment to work in. And if you’re building Android apps, you probably are still giving thanks for [Android Studio,](http://developer.android.com/tools/studio/index.html) which was built on IntelliJ.

[![kotlin](http://www.globalnerdy.com/wordpress/wp-content/uploads/2016/02/kotlin.jpg)](http://kotlinlang.org/)**Six years ago, JetBrains embarked on a project to build a new programming language named [Kotlin](https://kotlinlang.org/)** after an island near St. Petersburg, Russia, where one of their development teams is based. **[On February 15, 2016, JetBrains announced the 1.0 release of Kotlin,](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/)** which in their own words, “works everywhere where Java works”:

* IntelliJ IDEA, Android Studio and Eclipse
* Maven, Gradle and Ant
* [Spring Boot](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin) (Kotlin support released today!)
* GitHub, Slack and even Minecraft ![:)](http://i2.wp.com/blog.jetbrains.com/kotlin/wp-includes/images/smilies/simple-smile.png?w=640)

You can [try out Kotlin online](http://try.kotlinlang.org/) or on your own development environment:

* **IntelliJ IDEA** ([Ultimate or Community](https://www.jetbrains.com/idea/download/)): just create a Kotlin project or a Kotlin file in a Java project
* **Android Studio**: install the plugin through _Plugin Manager_
* **Eclipse**: install the plugin through [Marketplace](https://marketplace.eclipse.org/content/kotlin-plugin-eclipse)

...then download [its documentation](https://kotlinlang.org/docs/kotlin-docs.pdf) from the official site, after which you should check out these perfect-for-weekend-enjoyment resources...

### First, a little reading material

![why kotlin is my next programming language](http://www.globalnerdy.com/wordpress/wp-content/uploads/2016/02/why-kotlin-is-my-next-programming-language.jpg)

Start with [Mike Hearn’s](https://medium.com/@octskyward) essay on Medium, _Why Kotlin is my next programming language_. It lays out a pretty complete list of reasons why you’d want to take up development with Kotlin. If you’re convinced by the end of the essay, continue with the videos below...

### The _Fragmented_ podcast on Kotlin

[![fragmented](http://www.globalnerdy.com/wordpress/wp-content/uploads/2016/02/fragmented.png)](http://fragmentedpodcast.com/2015/10/)If you’re going for a walk, run, bike ride, or to the gym, and you’d like to find out more about Kotlin, [check out the October 2015 edition of the Android-centric _Fragmented_ podcast in which hosts Donn Felker and Kaushik Gopal talk about Kotlin with Hadi Hariri, JetBrain’s developer advocacy lead.](http://fragmentedpodcast.com/2015/10/) This one’s pretty in depth and runs 1 hour and 25 minutes.

### Fun with Kotlin

[Eder Bastos,](https://www.linkedin.com/in/ederb) and Android developer at [Raizlabs](http://www.raizlabs.com/), takes under 8 minutes to provide a nice tour of the Kotlin programming language and why you should consider it for your next Android project. This was published January 14, 2016:

<iframe src="https://www.youtube.com/embed/ZlQhmkp_jyk?rel=0" allowfullscreen="allowfullscreen" height="540" frameborder="0" width="960"></iframe>

### Kotlin: New Hope in a Java 6 Wasteland

[Michael Pardo](https://github.com/pardom) gave a Kotlin talk at [Droidcon NYC 2015](http://droidcon.nyc/2015/) on August 27, 2015:

<iframe src="https://www.youtube.com/embed/0BiPmgk3nyw?rel=0" allowfullscreen="allowfullscreen" height="540" frameborder="0" width="960"></iframe>

### Kotlin: The Swift of Android

Here’s [Svetlana Isakova](https://github.com/svtk) of [JetBrains](https://www.jetbrains.com/) (creator of Kotlin, Android Studio, ReSharper and a whole lot of IDEs) at [DroidCon Berlin](http://de.droidcon.com/) on June 4, 2015:

<iframe src="https://www.youtube.com/embed/-BvN0X5tqjw?rel=0" allowfullscreen="allowfullscreen" height="540" frameborder="0" width="960"></iframe>

### Android Development with Kotlin

Presented by [Jake Wharton](http://jakewharton.com/) at the [AndroidKW Meetup](http://www.meetup.com/androidkw/) in Waterloo, Canada, December 3, 2015:

<iframe src="https://www.youtube.com/embed/A2LukgT2mKc?rel=0" allowfullscreen="allowfullscreen" height="540" frameborder="0" width="960"></iframe>

You may also want to take a look at these other Android/Kotlin presentations by Jake Wharton:

*   [Advancing Android Development with Kotlin](http://jakewharton.com/presentation/2015-11-07-gdg-devfest/) GDG DevFest (Dublin, Ireland, November 7, 2015)
*   [Advancing Android Development with the Kotlin Language](http://jakewharton.com/presentation/2015-11-06-oredev/) Øredev (Malmö, Sweden, November 6, 2015)

### Functional Programming with Kotlin

Here’s a talk by Mike Hearn (the same Mike Hearn who wrote _Why Kotlin is my next programminglanguage_) where he shows Kotlin in action, with an emphasis on functional programming. This one was posted November 5, 2015, and he’s demonstrating using a pre-1.0-beta version:

<iframe src="https://www.youtube.com/embed/AhA-Q7MOre0?rel=0" allowfullscreen="allowfullscreen" height="540" frameborder="0" width="960"></iframe>

"""

Article(
  title = "Weekend resources for new Kotlin programmers",
  url = "http://www.globalnerdy.com/2016/02/20/weekend-resources-for-new-kotlin-programmers/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Joey deVilla",
  date = LocalDate.of(2016, 2, 20),
  body = body
)
