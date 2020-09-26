
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Spring I/O 2016 - 19 -20 May Barcelona

As described in this announcement I made on [Spring blog](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin), it is now easy to create a Spring Boot application using Kotlin.

Thanks to a sample Geospatial chat application, I will show how Spring Boot and Kotlin share the same pragmatic, innovative and optionated mindset to allow you to build simple but powerful projects.

That talk will also provide an opportunity to show how to use a relational database with Spring Data but without JPA in order to use advanced PostgreSQL functionalities (like its powerful spatial database extender PostGIS or the native JSON support) while keeping a lightweight stack.

<iframe width="960" height="480" src="https://www.youtube.com/embed/tTTEiQj4BHA" frameborder="0" allowfullscreen></iframe>



Update Nov 25 2016: Also see new version of this talk at Spring One Platform

[Developing a Geospatial Webservice with Kotlin and Spring Boot](https://www.infoq.com/presentations/geospatial-kotlin-boot)

"""

Article(
  title = "Developing a Geospatial Webservice with Kotlin and Spring Boot",
  url = "https://www.youtube.com/watch?v=tTTEiQj4BHA",
  categories = listOf(
    "Kotlin",
    "Spring"
  ),
  type = video,
  lang = EN,
  author = "Sébastien Deleuze",
  date = LocalDate.of(2016, 11, 25),
  body = body
)
