
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
This summer the JUnit team released a beta version of the next iteration, [version five](http://junit.org/junit5/). This newest version adds a ton of new features that will be a welcome relief for JVM developers who have been dealing with the limitations and idiosyncrasies of version four over the past decade. I’ve put together an [example project](https://github.com/mike-plummer/junit5-springboot-kotlin) to learn about the new feature set and tried to summarize some of my favorites in this post.

# Features of JUnit 5

**Backwards Compatibility:** One of the biggest concerns with any update is how it affects an existing baseline. One of the great features of version 5 is that it nicely coexists with any JUnit tests you already have. In fact, the platform supplies a special test runner that allows any version 5 test to be run as a version 4 test, albeit without some of the more advanced features. This allows a project to adopt the update and gradually migrate its test suite rather than forcing a ‘big bang’-style conversion.

**Nesting:** A common complaint of JUnit 4 tests is the relative difficulty of writing behavior-driven tests in the style of Cucumber or Jasmine (in the JavaScript arena). Behavior Driven Testing (BDT) aims to test software by focusing on desired activities and features rather than the more technical focus of traditional Test Driven Development (TDD). BDT writes tests in a more verbose style, typically structuring tests in logical groups using a natural language format. In JUnit 4 this often results in dauntingly-long unit test names and large amounts of repetition. JUnit 5 resolves many of these problems by supporting Nested tests.

```kotlin
@Autowired lateinit var dataBean: DataBean

@Nested
inner class dataBean() {
    @Nested
    inner class whenRetrievingData() {
        @Test
        fun returnsCorrectData() {
            assertEquals(dataBean.getData(), expectedData)
        }
    }
}
```

Nested tests allow for easier decomposition of tests into individual units that can easily share setup logic and also display in the test report in a more logical, grouped structure. For example, the code segment above will result in a test that naturally describes the expected behavior ‘dataBean, when retrieving data, returns correct data’ while allowing each segment of that behavior to be expanded with additional tests.

**Dynamic Tests:** In many situations testsneed to react to generated data or are unwieldy to write manually. For this use case JUnit 4 supplied the concept of Parameterized tests and JUnit 5 replaces it with simpler Dynamic Tests.

```kotlin
@TestFactory
fun valuesShouldIncludeAllStates(): Collection {
    return STATES.map { state -&gt;
        dynamicTest(state) {
            assertTrue(values.contains(state))
        }
    }.toList()
}
```

This short block generates 50 individual tests to validate that my dataset includes the names of all 50 US states. This mechanism works particularly well for data-driven testing but has one significant drawback: dynamic tests do not support Before/After lifecycle hooks the way that legacy Parameterized tests do. This is an unfortunate limitation and can be partially worked around using Nested tests, but if multiple dynamic tests share data it cannot be reset between tests.

**Extension:** Extensions replace much of the legacy ‘Runner’ construct by enabling easy enhancement and expansion of test capabilities. For example, extensions exist to automatically handle Spring and Mockito integration to help keep your tests clean and simple. By using extensions you can also neatly sidestep some of the limitations of test subclassing – multiple extensions can be applied to a single test.

**Tags, Filtering, and Naming:** Sometimes you only need to run a subset of tests, for example only those dealing with a particular service or functional path. Each test or block of tests can be tagged, and at runtime a subset of tags can be included or excluded from execution. In addition, sometimes a test needs a name that doesn’t conform with method naming conventions. An optional DisplayName can be supplied for each test to print out in the test reports which can help readability.

```kotlin
@Test
@Tag("LoadTests")
@DisplayName("Very long test that is disabled by default")
fun largeLoadTest() {
    // TEST CONTENT
}
```

**Dependency Injection:** Custom ParameterResolvers can be written that will be used to inject parameters into any constructors or test methods. This can help reduce boilerplate in your before/after lifecycle hooks and provides an easy way to swap out implementations when running tests against different back-ends. This is particularly useful if you’re using a Dependency Injection (DI) mechanism in your code like Guice or Java CDI as the ParameterResolver can hook into or replace many DI functions.

```kotlin
    @ExtendWith(TestTrackerResolver::class)
    class DependencyInjectionTest {
        @BeforeEach
        fun setup(tracker: TestTracker) {
            tracker.testCount++
        }
 
        @Test
        fun testTracker(tracker: TestTracker) {
            assertEquals(tracker.testCount, 1L)
        }
    }
 
    /** ParameterResolver that automatically supplies a TestTracker object where requested in the tests above **/
    class TestTrackerResolver: ParameterResolver {
        companion object {
            val tracker: TestTracker = TestTracker(0)
        }
 
        /** Whether this ParameterResolver supports the requested Parameter **/
        override fun supports(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
            return parameterContext?.parameter?.type == TestTracker::class.java
        }
 
        /** Supply a value for the requested Parameter. **/
        override fun resolve(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any? {
            return tracker
        }
    }
```

**Java 8:** Many test constructs have been updated to be lambda-friendly, and others like dynamic tests are a natural fit to be driven from a Stream. One of my personal favorite features is the idea of Supplier-based messages – in legacy tests a failure message was compiled and generated before the test even ran, but in the new format the Supplier will only be called in the event of a failure. This helps speed up tests ever so slightly but more importantly allows easy re-use of method references to generate detailed and standardized messages.

# Use with Spring Boot

Even though JUnit 5 is in beta it’s already easy to use in Spring Boot. An excellent [JUnit Extension](https://github.com/sbrannen/spring-test-junit5) supplies easy Spring integration including Autowiring. By using this extension and annotating with @SpringBootTest a test has a complete Spring context to work with. As of the time of this post that extension is not published out to a public repository, but the code can be pulled from GitHub and built manually in just a few minutes.

```kotlin
@SpringBootTest
@ExtendWith(SpringExtension::class)
class DataBeanTest : ApplicationTest() {

    @Autowired lateinit var dataBean: DataBean

    @Test
    fun testAutowiring() {
        assertNotNull(dataBean)
    }
}
```

# Spring Boot with Kotlin

Moving off the topic of JUnit, one of my favorite new languages in the JVM space is Kotlin. I’ve [written a bit about it here before](https://objectpartners.com/2016/02/23/an-introduction-to-kotlin/), and given its great feature set I wanted to see how well it worked for writing a Spring Boot application and JUnit 5 tests. Short answer: it works great once you know all the tricks.

## Defining the Application

As it is in Java, creating your SpringBoot application in Kotlin is easy and concise.

```kotlin
@SpringBootApplication
@ComponentScan("com.objectpartners.plummer.junit5")
open class Application

fun main(args: Array) {
    SpringApplication.run(Application::class.java, *args)
}
```

## Defining a Bean

With a quick annotation a Kotlin class becomes a Spring-managed bean.

```kotlin
interface FibonacciService {
    fun fibonacci(max: Long): List;
}

@Component
open class FibonacciServiceImpl: FibonacciService {
    override fun fibonacci(max: Long): List {
        Assert.isTrue(max &gt; 0)
        return generateSequence (0L to 1L, { previous -&gt; previous.second to previous.first + previous.second})
            .takeWhile { result -&gt; result.second  result.second}
            .toList()
    }
}
```

## Using a Bean

This is the only really tricky area. Kotlin’s null-safety rules can cause problems with Dependency Injection since those fields are by definition not initialized at the time of declaration. Early versions of the language forced you to work around this by either performing a faux-initialization at declaration or by using parameter-based injection rather than field-based, but now Kotlin has the ‘lateinit’ keyword. This indicates to the compiler that the field will be initialized at some point between declaration and usage and to make assumptions based on that guarantee. If a ‘lateinit’ field is not initialized prior to use a special exception will be thrown to identify the unsatisfied assumption.

```kotlin
@Autowired lateinit var fibonacciSvc: FibonacciService
```

# Wrap Up

Hopefully this article has gotten you interested in checking out JUnit5 and maybe even giving Kotlin a try. JUnit may not be the most flexible or powerful testing framework out there but this newest version improves what continues to be the simplest, most-used testing platform for the JVM and is ideal for anyone who is looking to incrementally update an existing test suite or wants to write tests that are easy, familiar, and maintainable. Be sure to check out the [example project](https://github.com/mike-plummer/junit5-springboot-kotlin) to see these features in action as part of a SpringBoot Kotlin application. Happy coding!

"""

Article(
  title = "JUnit 5 with Spring Boot (plus Kotlin)",
  url = "https://objectpartners.com/2016/07/26/junit-5-with-spring-boot-plus-kotlin/",
  categories = listOf(
    "Kotlin",
    "Spring Boot"
  ),
  type = article,
  lang = EN,
  author = "Mike Plummer",
  date = LocalDate.of(2016, 7, 26),
  body = body
)
