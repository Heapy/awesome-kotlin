
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I've just released [Kosent](https://github.com/dmcg/konsent), an acceptance testing library for Kotlin.

The basic idea is that you can write your tests in a nice IDE-friendly and refactorable Kotlin DSL, but they generate
Gherkin that your customer can read and approve as the spec.

So you write this

```kotlin
@RunWith(Konsent::class)
@Preamble(
    "As a developer named Duncan",
    "I want to know that example.com is up and running")
class KonsentExampleTests : ChromeAcceptanceTest() {

    val duncan = actorNamed("Duncan")

    @Scenario(1) fun `Example_dot_com loads`() {
        Given(duncan).loadsThePageAt("http://example.com")
        Then(duncan) {
            shouldSee(::`the page location`, pathContains("example.com"))
            shouldSee(::`the page title`, equalTo("Example Domain"))
            shouldSee(::`the page content`, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }

    @Scenario(2, "Following a link from example.com") fun cant_have_dots_in_quoted_method_names() {
        Given(duncan).loadsThePageAt("http://example.com")
        When(duncan).followsTheLink("More information...", "http://www.iana.org/domains/example")
        Then(duncan).shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

    @Scenario(3) fun `Dispensing with the given when then`() {
        duncan.he.loadsThePageAt("http://example.com")
        duncan.he.followsTheLink("More information...", "http://www.iana.org/domains/example")
        duncan.shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }
}
```

and they read this

```gherkin
Feature: Konsent Example Tests
    As a developer named Duncan
    I want to know that example.com is up and running

    Scenario: Example_dot_com loads
        Given Duncan loads the page at "http://example.com"
        Then Duncan sees the page location "location contains "example.com"
        and Duncan sees the page title is equal to "Example Domain"
        and Duncan sees the page content contains a link [More information...](http://www.iana.org/domains/example)

    Scenario: Following a link from example.com
        Given Duncan loads the page at "http://example.com"
        When Duncan follows the link [More information...](http://www.iana.org/domains/example)
        Then Duncan sees the page location is equal to http://www.iana.org/domains/reserved

    Scenario: Dispensing with the given when then
        Duncan loads the page at "http://example.com"
        Duncan follows the link [More information...](http://www.iana.org/domains/example)
        Duncan sees the page location is equal to http://www.iana.org/domains/reserved
```

Thanks to my patrons at [Springer Nature](http://springernature.com), on whose dime this approach has been developed. We're
hiring, so [get in touch](https://twitter.com/duncanmcg) if you're in London and this stuff interests you.

Stay tuned - I'm planning a post soon on how to implement a DSL like this in Kotlin.

"""

Article(
  title = "Kosent",
  url = "http://oneeyedmen.com/konsent.html",
  categories = listOf(
    "Testing",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Duncan McGregor",
  date = LocalDate.of(2016, 5, 12),
  body = body
)
