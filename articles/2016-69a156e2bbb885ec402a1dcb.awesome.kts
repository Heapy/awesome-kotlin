
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
On May 17th we held an evening event at San Francisco in cooperation with Realm and Netflix. Thanks to everyone who joined us this evening! There were great talks and important announcements, and the good news is that all of them were recorded:

## Talk #1: Andrey Breslav. **Where We Stand and What’s Next**

Kotlin project lead Andrey Breslav gave an overview of what Kotlin is today and took a peek into the near future: what the Kotlin team is planning for the next release.

<iframe src="https://www.youtube.com/embed/POZmfjRHdfE" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

<iframe webkitallowfullscreen="true" mozallowfullscreen="true" allowfullscreen="true" src="//speakerdeck.com/player/f1dea41f659a4c70a6e8fb20291b871b?" style="border: 0px none; background: transparent none repeat scroll 0% 0%; margin: 0px; padding: 0px; border-radius: 5px; width: 960px; height: 480px;" class="speakerdeck-iframe" frameborder="0"></iframe>

## Bonus Talk by Hans Dockter. **Gradle goes Kotlin**

Hans Dockter, CEO of Gradle demoed the first milestone of writing a Gradle build script using Kotlin.

<iframe src="https://www.youtube.com/embed/4gmanjWNZ8E" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

## Talk #2: Jake Wharton. **Instrumentation Testing Robots**

Libraries like Espresso allow UI tests to have stable interactions with your app, but without discipline these tests can become hard to manage and require frequent updating. In this talk Jake covered how the so-called robot pattern allows you to create stable, readable, and maintainable tests with the aid of Kotlin’s language features.

See the full recording along with transcribing on [realm.io](https://realm.io/news/kau-jake-wharton-testing-robots/)

## Talk #3: Laura Kogler & Rob Fletcher. **Kotlin Testing**

Spek framework contributor Laura and Spock maintainer Rob demonstrated the current state of Kotlin testing frameworks and discussed expected further advancements in testing support.

Part1:

<iframe src="https://www.youtube.com/embed/pCg3P7AOtHo" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

<iframe webkitallowfullscreen="true" mozallowfullscreen="true" allowfullscreen="true" src="//speakerdeck.com/player/4bfe84ca3f6f45979f2bd1d67fb2d12b?" style="border: 0px none; background: transparent none repeat scroll 0% 0%; margin: 0px; padding: 0px; border-radius: 5px; width: 960px; height: 480px;" class="speakerdeck-iframe" frameborder="0"></iframe>

Part2:

<iframe src="https://www.youtube.com/embed/y5U8uiI2S0I" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

<iframe src="//www.slideshare.net/slideshow/embed_code/key/9ZqOCfx8zmnOXZ" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

Thanks all participants and speakers for great atmosphere and engaging discussions. Here are several photos from the event:
![Swag](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/06/IMG_0109-1.jpg?zoom=1.5&resize=370%2C278&ssl=1)
![Community](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/06/IMG_0114-1.jpg?zoom=1.5&resize=370%2C278&ssl=1)
![Hans](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/06/IMG_0129-1.jpg?zoom=1.5&resize=370%2C278&ssl=1)
![Kotlin&Gradle](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/06/IMG_0150-1.jpg?zoom=1.5&resize=370%2C278&ssl=1)

**See you on next K•NIGHT!**  

"""

Article(
  title = "Kotlin Night in San Francisco Recordings",
  url = "https://blog.jetbrains.com/kotlin/2016/06/kotlin-night-recordings/",
  categories = listOf(
    "Kotlin",
    "K.Night"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2016, 6, 14),
  body = body
)
