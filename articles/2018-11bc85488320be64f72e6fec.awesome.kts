
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
# v1

Kotlin is a language for the JVM, invented by JetBrains a few years ago. But what the hell is Kotlin EE? If you never heard of Kotlin EE, don’t panic, it does not exist. But you can use it right away and boost your productivity!

I invented the term Kotlin EE as a synonym for using the Kotlin language together with the Java EE API to create services of any size (microservices, nanoservices etc.) with just a few lines of code and the ability to focus on the business logic.

Kotlin and Java EE are a perfect couple for writing micro- or nanoservices. Kotlin is a very pragmatic language, builds on many concepts and techniques from Java, designed for developer productivity. Kotlin works great with all existing Java libraries and frameworks and runs with the same level of performance as Java.

The Java EE API allows us to code against a proven and stable API. Provided libraries like JAX-RS for writing RESTful APIs and Jackson for JSON (de)serializing decrease the need for additional third-party libraries which results in a short build time and a small artifact size. Benefit from a very fast build and test feedback and stay focused on your code.

In this talk, I try to prove my statements from above. Live on stage I write a service in Kotlin with a RESTful JSON API with only a few lines of code and run the service using a local Docker cloud where you can see how these can be scaled up and down to manage fluctuating loads. Coding, building, testing, deploying, scaling: fast and efficient!

<iframe width="960" height="480" src="https://www.youtube.com/watch?v=fpjDY6XRBJM" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Kotlin EE: Boost your Productivity",
  url = "https://www.youtube.com/watch?v=fpjDY6XRBJM",
  categories = listOf(
    "Cloud",
    "Docker",
    "Jakarta EE",
    "Java EE",
    "Kotlin"
  ),
  type = video,
  lang = EN,
  author = "Marcus Fihlon @ Voxxed Days Bucharest",
  date = LocalDate.of(2018, 3, 23),
  body = body
)
