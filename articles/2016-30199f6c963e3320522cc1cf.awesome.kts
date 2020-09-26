
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
The Kotlin programming language is gaining popularity amongst the Android developer community. It’s a modern language that gives more power in everyday routines. Kotlin code generally looks cleaner and nicer, and it’s much easier to work with when you have less verbosity or code duplication. And this is especially noticeable comparing with the soon-to-be-archaic versions of Java used on Android.

But what’s even more important, is that Kotlin is 100% compatible with all existing Java frameworks, and has good tooling in Android Studio and IntelliJ IDEA. It’s a pragmatic language with a very low learning curve, and can be quickly grasped by Java developers.

In this talk we’ll discuss the concepts of the language that provide the desired expressiveness, as well as additional goodies designed specifically for Android.

<iframe width="960" height="480" src="https://www.youtube.com/embed/Es32UqHNza0" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "You can do Better with Kotlin",
  url = "https://www.youtube.com/watch?v=Es32UqHNza0",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = video,
  lang = EN,
  author = "Svetlana Isakova",
  date = LocalDate.of(2016, 5, 24),
  body = body
)
