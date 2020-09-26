
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Just in time for [Kotlin 1.0 release](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/), we are adding support for Kotlin language to [https://start.spring.io](https://start.spring.io/#!language=kotlin) in order to make it easier to start new Spring Boot projects with this language.

This blog post is also an opportunity for me to explain why I find this language interesting, to show you a sample project in detail and to give you some tips.

## What is Kotlin?

[Kotlin](https://kotlinlang.org/) is a language created by [JetBrains](https://www.jetbrains.com/). It runs on top of the JVM (but not only), it is an object oriented language that includes many ideas from functional programming. I won’t go too much in details about all Kotlin features ([PDF](https://kotlinlang.org/docs/kotlin-docs.pdf), [HTML](https://kotlinlang.org/docs/reference/)), but I would like to highlight the ones I find the most interesting:

* Kotlin is a statically typed language, but thanks to its [clever type inference](https://kotlinlang.org/docs/reference/basic-types.html), it allows you to write code as short and expressive as dynamic language with performances close to pure Java projects
* [Properties support](https://kotlinlang.org/docs/reference/properties.html#declaring-properties)
* Relatively lightweight standard library compared to other languages
* Easy to learn: a Java developer can quickly understand most of the language (this [quick comparison to Java](https://kotlinlang.org/docs/reference/comparison-to-java.html) is worth to read)
* [Java interop](https://kotlinlang.org/docs/reference/java-interop.html) is a first class concern and great
* Perfect for Android development
* Built-in immutability and [null safety](https://kotlinlang.org/docs/reference/null-safety.html) support
* Code is easy to read, efficient to write
* Allows to [extend existing libraries](https://kotlinlang.org/docs/reference/extensions.html) without having to inherit from the class or use any type of design pattern such as Decorator
* No semicolon required ;-)

You will find a lot of useful links to improve your Kotlin knowledge in this [Kotlin digest 2015](http://blog.jetbrains.com/kotlin/2016/01/kotlin-digest-2015/) blog post. Also have a look to these simple [Kotlin exercices](http://try.kotlinlang.org/koans) to have a quick overview of the language.

## A sample Spring Boot + Kotlin project

Kotlin has been designed to play well with the Java ecosystem, and it seems to me that it shares the same pragmatic, innovative and opinionated mindset as Spring Boot, so they play well together. You can have a look at this simple [Spring Boot + Spring Data JPA Kotlin project](https://github.com/sdeleuze/spring-boot-kotlin-demo) to see more concretely what it looks like.

Kotlin allows to write (and read) your domain model easily thanks to [data classes](https://kotlinlang.org/docs/reference/data-classes.html). The compiler automatically derives the following members from all properties declared in the primary constructor:
- `equals()` / `hashCode()` pair
- `toString()` of the form “Customer(firstName=Foo, lastName=Bar, id=42)”
- `componentN()` functions corresponding to the properties in their order or declaration
- `copy()` function

You can see that Kotlin allows you to specify parameter default values, and types are declared after the name of the variable/parameter:

```kotlin
@Entity
data class Customer(
	var firstName: String = "",
	var lastName: String = "",
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = 0
)
```

The Spring MVC REST controller you can see below is using constructor level injection, and default visibility in Kotlin is `public` so no need to specify it. When a function returns a single expression, the curly braces can be omitted and the body is specified after a `=` symbol. It is even better since the return type can be inferred by the compiler.

```kotlin
@RestController
class CustomerController @Autowired constructor(val repository:CustomerRepository) {

	@RequestMapping("/")
	fun findAll() = repository.findAll()


	@RequestMapping("/{name}")
	fun findByLastName(@PathVariable name:String)
		= repository.findByLastName(name)
}
```

The Spring Data repository is self explanatory:

```kotlin
interface CustomerRepository : CrudRepository<Customer, Long> {
	fun findByLastName(name: String): List<Customer>
}
```

Since Kotlin supports top-level functions, you can declare your application as simple as:

```kotlin
@SpringBootApplication
open class Application {

	@Bean
	open fun init(repository: CustomerRepository) = CommandLineRunner {
		repository.save(Customer("Jack", "Bauer"))
		repository.save(Customer("Chloe", "O'Brian"))
		repository.save(Customer("Kim", "Bauer"))
		repository.save(Customer("David", "Palmer"))
		repository.save(Customer("Michelle", "Dessler"))
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}
```

You need to mark `@Configuration` classes, some others like most `@Component` ones and `@Bean` methods as `open` because they cannot be `final` in Spring due to proxy usage (classes and methods in Kotlin are `final` by default without the `open` modifier).

## Additional tips

Even if Spring Boot and Kotlin work pretty well together, these additional tips are worth to know.


### Property injection

We have seen previously how to do constructor injection, since that’s the [recommended approach](http://olivergierke.de/2013/11/why-field-injection-is-evil/) (especially with Kotlin). If you have to perform property injection, you will have to use [late-initialized properties](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties) because normally, raw properties declared as having a non-null type must be initialized in the constructor.

```kotlin
@RestController
class CustomerController {

	@Autowired
	lateinit var repository:CustomerRepository

	// ...
}
```

### Property placeholders

`${"$"}` is used for [String interpolation](https://kotlinlang.org/docs/reference/basic-types.html#string-templates) in Kotlin, so you should escape it when using property placeholders: `@Value("\${"$"}{some.property}")`. As an alternative you can also use `@ConfigurationProperties` instead, see [this Stack Overflow](http://stackoverflow.com/a/33883230/1092077) answer for more details.

### Array annotation attribute

Unlike Java, Kotlin does not allow to specify array annotation attribute as a single value, so be aware that you will have to write `@RequestMapping(method = arrayOf(RequestMethod.GET))` or `@EnableAutoConfiguration(exclude = arrayOf(Foo::class))`.

It will be possible to use a shorter syntax with upcoming Spring Framework 4.3 composed annotations like `@GetMapping`, see [SPR-13992](https://jira.spring.io/browse/SPR-13992) for more details.

### Jackson Kotlin Module

If you are using Jackson you are likely to want to add `com.fasterxml.jackson.module:jackson-module-kotlin` dependency in order to allow it to deal with data classes with no default constructor or with Kotlin collections.

In addition to the dependency, you need to register it in Jackson `ObjectMapper`:

```kotlin
@SpringBootApplication
open class Application {

	@Bean
	open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder
	    = Jackson2ObjectMapperBuilder().modulesToInstall(KotlinModule())

	// ...
}
```

### Experiment with the Java to Kotlin converter

Last tip, the Java to Kotlin converter available in IntelliJ IDEA (Menu Code -> Convert Java file to Kotlin file) is quite useful when you can’t figure out how to write something in Kotlin. So do not hesitate to write something in Java and use it to find the Kotlin counterpart. This [comparison to Java documentation](https://kotlinlang.org/docs/reference/comparison-to-java.html) can also provide some help.

## Feedbacks

We are interested by your feedbacks about developing Spring applications with Kotlin. This blog post is just an introduction, there is much more to say especially about using Spring Boot with more Kotlin idiomatic code like with [Exposed](https://github.com/JetBrains/Exposed) SQL library, so stay tuned ...

"""

Article(
  title = "Developing Spring Boot applications with Kotlin.",
  url = "https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin",
  categories = listOf(
    "Kotlin",
    "Spring"
  ),
  type = article,
  lang = EN,
  author = "Sébastien Deleuze",
  date = LocalDate.of(2016, 2, 15),
  body = body
)
