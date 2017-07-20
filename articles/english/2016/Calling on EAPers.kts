
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
At JetBrains we’ve always believed in our Early Access Program for our tools, giving developers a chance to use the latest features or fixes as soon as they’re ready. We’ve followed the same philosophy with Kotlin, knowing of course that with a language, some decisions can haunt you for life.

That is why during the pre-release days of Kotlin, we’d have milestone releases with features and important changes for our early adopters (including ourselves at JetBrains), collect feedback on whether things were working as designed. This release-feedback-adjust cycle has proven invaluable in so many occasions and has definitely helped shape Kotlin 1.0.

But it’s not over with the release. We’re still continuing to develop Kotlin with new language features and we need your continued support. As you know, we have two ‘branches’ of EAP’s, the 1.0.X ones which are more about bug fixes and tooling, and the 1.1 EAP which is about language changes. And it’s with these EAP’s, the ones with language changes, where we could really use your help. While we don’t recommend you using these 1.1 EAP’s in production code, we definitely do need you to give us feedback. In return, we promise to listen to everything you have to say!

## Using EAP’s

The easiest way to access EAP’s is via the Tools menu. Open _Tools | Kotlin | Configure Kotlin Plugin Updates_ in the main menu, select Early Access Preview channel 1.0.x or 1.1 and press the _Check for updates now_ button.
![Screen Shot 2016-08-01 at 17.55.03](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/08/Screen-Shot-2016-08-01-at-17.55.03.png?w=640&ssl=1)
To use the new builds from Maven or Gradle: add _https://dl.bintray.com/kotlin/kotlin-eap_ or _https://dl.bintray.com/kotlin/kotlin-eap-1.1_ as a repository to your project

_At the moment the first EAP of Kotlin 1.1 is available (with [coroutines](https://youtu.be/4W3ruTWUhpw), type aliases, bound callable reference, local delegated properties, Java 7/8 support and [many more](https://blog.jetbrains.com/kotlin/2016/07/first-glimpse-of-kotlin-1-1-coroutines-type-aliases-and-more/)). Also today we just published first EAP build of Kotlin 1.0.4 with lots of bugs fixes in the compiler and IDE, whole bunch of new intentions, inspections and quickfixes, and it’s now fully compatible with Gradle 2.14.1. For more information check out [the full Kotlin 1.0.4 changelog](https://github.com/JetBrains/kotlin/blob/767329fcab8249214c9c77db8ff1b8c1b3bd44b9/ChangeLog.md)_

## Giving Feedback

Most of the Kotlin team are available on our public ([Slack community](http://kotlinslackin.herokuapp.com)), and specifically on the #eap channel, where we’re very eager to listen to your feedback and help with any questions or issues you may have. Of course, our [issue tracker](http://kotl.in/issue) is always open too

One of our main goals with Kotlin has always been to make it an industrial language that is pragmatic. And it’s by using Kotlin in many types of applications and scenarios that will help us achieve this goal. For this, we continue to need your help!

Thank you and let’s Kotlin!

"""

Article(
  title = "Calling on EAPers",
  url = "https://blog.jetbrains.com/kotlin/2016/08/calling-on-eapers/",
  categories = listOf(
    "Kotlin",
    "EAP"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2016, 8, 4),
  body = body
)
