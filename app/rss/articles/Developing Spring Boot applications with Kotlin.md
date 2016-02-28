---
title: Developing Spring Boot applications with Kotlin.
url: https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin
categories:
    - Kotlin
    - Spring
author: 'Sébastien Deleuze'
date: Feb 15, 2016 12:58
---
Just in time for Kotlin 1.0 release, we are adding support for Kotlin language to https://start.spring.io in order to make it easier to start new Spring Boot projects with this language.

This blog post is also an opportunity for me to explain why I find this language interesting, to show you a sample project in detail and to give you some tips.
What is Kotlin?

Kotlin is a language created by JetBrains. It runs on top of the JVM (but not only), it is an object oriented language that includes many ideas from functional programming. I won’t go too much in details about all Kotlin features (PDF, HTML), but I would like to highlight the ones I find the most interesting:

    Kotlin is a statically typed language, but thanks to its clever type inference, it allows you to write code as short and expressive as dynamic language with performances close to pure Java projects
    Properties support
    Relatively lightweight standard library compared to other languages
    Easy to learn: a Java developer can quickly understand most of the language (this quick comparison to Java is worth to read)
    Java interop is a first class concern and great
    Perfect for Android development
    Built-in immutability and null safety support
    Code is easy to read, efficient to write
    Allows to extend existing libraries without having to inherit from the class or use any type of design pattern such as Decorator
    No semicolon required ;-)

You will find a lot of useful links to improve your Kotlin knowledge in this Kotlin digest 2015 blog post. Also have a look to these simple Kotlin exercices to have a quick overview of the language.
A sample Spring Boot + Kotlin project

Kotlin has been designed to play well with the Java ecosystem, and it seems to me that it shares the same pragmatic, innovative and opinionated mindset as Spring Boot, so they play well together. You can have a look at this simple Spring Boot + Spring Data JPA Kotlin project to see more concretely what it looks like.

Kotlin allows to write (and read) your domain model easily thanks to data classes. The compiler automatically derives the following members from all properties declared in the primary constructor:
- equals() / hashCode() pair
- toString() of the form “Customer(firstName=Foo, lastName=Bar, id=42)”
- componentN() functions corresponding to the properties in their order or declaration
- copy() function

You can see that Kotlin allows you to specify parameter default values, and types are declared after the name of the variable/parameter:

@Entity
data class Customer(
	var firstName: String = "",
	var lastName: String = "",
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = 0
)

The Spring MVC REST controller you can see below is using constructor level injection, and default visibility in Kotlin is public so no need to specify it. When a function returns a single expression, the curly braces can be omitted and the body is specified after a = symbol. It is even better since the return type can be inferred by the compiler.

@RestController
class CustomerController @Autowired constructor(val repository:CustomerRepository) {

	@RequestMapping("/")
	fun findAll() = repository.findAll()


	@RequestMapping("/{name}")
	fun findByLastName(@PathVariable name:String)
		= repository.findByLastName(name)
}

The Spring Data repository is self explanatory:

interface CustomerRepository : CrudRepository<Customer, Long> {
	fun findByLastName(name: String): List<Customer>
}

Since Kotlin supports top-level functions, you can declare your application as simple as:

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

You need to mark @Configuration classes, some others like most @Component ones and @Bean methods as open because they cannot be final in Spring due to proxy usage (classes and methods in Kotlin are final by default without the open modifier).
Additional tips

Even if Spring Boot and Kotlin work pretty well together, these additional tips are worth to know.
Property injection

We have seen previously how to do constructor injection, since that’s the recommended approach (especially with Kotlin). If you have to perform property injection, you will have to use late-initialized properties because normally, raw properties declared as having a non-null type must be initialized in the constructor.

@RestController
class CustomerController {

	@Autowired
	lateinit var repository:CustomerRepository

	// ...
}

Property placeholders

$ is used for String interpolation in Kotlin, so you should escape it when using property placeholders: @Value("\${some.property}"). As an alternative you can also use @ConfigurationProperties instead, see this Stack Overflow answer for more details.
Array annotation attribute

Unlike Java, Kotlin does not allow to specify array annotation attribute as a single value, so be aware that you will have to write @RequestMapping(method = arrayOf(RequestMethod.GET)) or @EnableAutoConfiguration(exclude = arrayOf(Foo::class)).

It will be possible to use a shorter syntax with upcoming Spring Framework 4.3 composed annotations like @GetMapping, see SPR-13992 for more details.
Jackson Kotlin Module

If you are using Jackson you are likely to want to add com.fasterxml.jackson.module:jackson-module-kotlin dependency in order to allow it to deal with data classes with no default constructor or with Kotlin collections.

In addition to the dependency, you need to register it in Jackson ObjectMapper:

@SpringBootApplication
open class Application {

	@Bean
	open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder
	    = Jackson2ObjectMapperBuilder().modulesToInstall(KotlinModule())

	// ...
}

Experiment with the Java to Kotlin converter

Last tip, the Java to Kotlin converter available in IntelliJ IDEA (Menu Code -> Convert Java file to Kotlin file) is quite useful when you can’t figure out how to write something in Kotlin. So do not hesitate to write something in Java and use it to find the Kotlin counterpart. This comparison to Java documentation can also provide some help.
Feedbacks

We are interested by your feedbacks about developing Spring applications with Kotlin. This blog post is just an introduction, there is much more to say especially about using Spring Boot with more Kotlin idiomatic code like with Exposed SQL library, so stay tuned …
