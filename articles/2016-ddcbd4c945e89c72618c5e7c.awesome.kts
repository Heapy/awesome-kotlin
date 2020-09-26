
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Kobalt automatically detects how to run your tests based on the test dependencies that you declared:

```kotlin
dependenciesTest {
    compile("org.testng:testng:6.9.9")
}
```

By default, Kobalt supports TestNG, JUnit and Spek. You can also configure how your tests run with the test{} directive:

```Kotlin
test {
    args("-excludegroups", "broken", "src/test/resources/testng.xml")
}
```

The full list of configuration parameters can be found in the [TestConfig](https://github.com/cbeust/kobalt/blob/master/modules/kobalt-plugin-api/src/main/kotlin/com/beust/kobalt/TestDirective.kt#L6) class.

Additionally, you can define multiple test configurations, each with a different name. Each configuration will create an additional task named "test" followed by the name of that configuration. For example:

```Kotlin
test {
    args("-excludegroups", "broken", "src/test/resources/testng.xml")
}

test {
    name = "All"
    args("src/test/resources/testng.xml")
}
```

The first configuration has no name, so it will be launched with the task "test", while the second one can be run with the task "testAll".

The full series of articles on Kobalt can be found [here](http://beust.com/weblog/category/kobalt/).

"""

Article(
  title = "The Kobalt diaries: testing",
  url = "http://beust.com/weblog/2016/02/20/the-kobalt-diaries-testing/",
  categories = listOf(
    "Kotlin",
    "Kobalt"
  ),
  type = article,
  lang = EN,
  author = "Cédric Beust",
  date = LocalDate.of(2016, 2, 20),
  body = body
)
