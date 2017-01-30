---
title: 'Kotlin in Practice with Spring Boot and Vaadin'
url: https://blog.philipphauer.de/kotlin-practice-spring-boot-vaadin/
categories:
    - Kotlin
    - Vaadin
author: Philipp Hauer
date: Dec 21, 2016 04:13
---
Coding with Kotlin is great fun. But things are getting really interesting when we try to use Kotlin in conjunction with popular frameworks like Spring Boot and Vaadin. The development with those frameworks can benefit a lot from Kotlin. However, we have to pay attention to some pitfalls.

![Kotlin in Practice with Spring Boot and Vaadin](https://blog.philipphauer.de/wp-content/uploads/2016/12/kotlin-in-practice-featured-image.png "Kotlin in Practice")

# TL;DR

* The development with Spring Boot and Vaadin significantly benefits from Kotlin.
* **Spring**:
    * Due to Kotlin’s compact constructor and field declaration, there is no reason for using field injection anymore. Constructor injection comes without any boilerplate.
    * Placing all classes for the external configuration properties in a single Kotlin file comes in handy.
    * However, you have to pay attention to some pitfalls when using Kotlin’s data classes. Especially the missing default constructor is a problem for some libraries like Jackson or Spring Data MongoDB.
* **Vaadin**:
    * Vaadin is highly event-driven. You are dealing a lot with event listeners. Fortunately, Kotlin allows us to write very concise event listeners using function references and lambdas.
    * Kotlin’s `apply()` method is extremely useful when you define UI components and layouts in Vaadin. It increases the readability: You can group your initialization code for a component and directly see the layout nesting in the code.
    * Using Kotlin’s data classes we can easily write beans that can be bound to Vaadin’s `BeanItemContainers`. The single expression functions can be used to map from one data classes to another (e.g. persistence entities to UI beans).
    * The mentioned points also apply for other GUI Frameworks like Swing, JavaFX and GWT.

# Source

I create a small project using Kotlin, Spring Boot, Spring Data MongoDB and Vaadin. Check out my GitHub repository [kotlin-spring-boot-vaadin-scaffolding](https://github.com/phauer/blog-related/tree/master/kotlin-spring-boot-vaadin-scaffolding).

# General

## Putting Multiple Classes in a Single File

Kotlin allows putting multiple classes in a single file. This is great to overcome the flood of files in Java. Moreover, you can group classes together, that belong semantically together. Examples:

* Entity classes for the persistence layer
* DTO classes for JSON serialization
* Classes for Spring’s external configuration properties (YAML)
* UI Components that belong together (like a Window class that is only used in a certain View)

```kotlin
data class BlogEntity (
        val author: AuthorEntity,
        val date: Instant,
        val content: String
)
data class AuthorEntity(val firstName: String, val lastName: String)
```

## Value Objects

Domain-Driven Design tells us to use Value Objects to increase readability and safety. But in chatty Java you won’t create a whole new class (in a new file) only to wrap a single String. Just think about a class like `EmailAddress`. However, in Kotlin, the definition of such a Value Object is a one-liner.

```kotlin
data class EmailAddress(val address: String)

//without value object:
fun process1(emails: List<String>) {}

//with value object:
fun process2(emails: List<EmailAddress>) {}
//=> expressive, readable, safe
```

There is no excuse for not using Value Objects anymore!

# Spring Boot

If you want to use Kotlin in conjunction with Spring Boot the post [“Developing Spring Boot applications with Kotlin”](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin) is a good starting point.

## Constructor Injection without Boilerplate

[Field injection is evil](http://olivergierke.de/2013/11/why-field-injection-is-evil/) due to poor testability. So constructor injection is the preferred way, but it requires more boilerplate in Java.

```java
public class CustomerResource {

    private CustomerRepository repo;
    private CRMClient client;

    public CustomerResource(CustomerRepository repo, CRMClient client) {
        this.repo = repo;
        this.client = client;
    }
}
```

That’s why I used to prefer field injection – until I started to use Kotlin. Kotlin completely removes the boilerplate.

```kotlin
class CustomerResourceKotlin(private val repo: CustomerRepository,
                             private val client: CRMClient) {
}
```

First, Kotlin combines the class definition with the constructor definition. Second, the `val` keyword before the constructor parameters tells Kotlin to create a field and to assign the parameter to this field. Note, that we can [leave out the `@Autowired` annotation](https://spring.io/blog/2016/03/04/core-container-refinements-in-spring-framework-4-3) if you only have a single constructor.

So you get the benefits of constructor injection without having the drawbacks. This way, there is no reason to use field injection anymore.

## The Missing Default Constructor in Kotlin

Kotlin’s data classes don’t have a default constructor (non-arg). However, many mapping/serialization libraries rely on the existence of this default constructor. Jackson and Spring Data MongoDB are such libraries.

### Jackson

Jackson’s deserialization fails with the following error:

```
Failed to instantiate [ClassName]: No default constructor found;
nested exception is java.lang.NoSuchMethodException: ClassName.<init>()
```

The solution is easy: Adding the [jackson-module-kotlin](https://github.com/FasterXML/jackson-module-kotlin) to you classpath will do the trick.

### Spring Data MongoDB

Spring Data MongoDB also requires a default constructor if you use default parameter values.

```
Caused by: org.springframework.data.mapping.model.MappingInstantiationException:
Failed to instantiate de.philipphauer.blog.scaffolding.db.SnippetEntity using constructor NO_CONSTRUCTOR with arguments
...
Caused by: org.springframework.beans.BeanInstantiationException:
Failed to instantiate [de.philipphauer.blog.scaffolding.db.SnippetEntity]: No default constructor found;
nested exception is java.lang.NoSuchMethodException: de.philipphauer.blog.scaffolding.db.SnippetEntity.<init>()
```

But you can fix this easily by adding the `@PersistenceConstructor` annotation.

```kotlin
@Document(collection="snippets")
data class SnippetEntity @PersistenceConstructor constructor(
        @Id val id: ObjectId? = null, //default value
        val code: String,
        val author: AuthorEntity
)
data class AuthorEntity(
        val firstName: String,
        val lastName: String
)
```

## External Configuration Properties (YAML)

```kotlin
@Configuration
@ConfigurationProperties(prefix = "myapp")
open class MyAppProps {
    @NotNull lateinit var requiredProp: String
    var optionalProp: String? = null
}

@Configuration
@ConfigurationProperties(prefix = "myapp.authentication")
open class AuthenticationProps {
    @NotNull lateinit var url: String
    var credentials: Credentials? = null
}
class Credentials{
    @NotNull lateinit var userName: String
    @NotNull lateinit var userPassword: String
}
```

* I personally really like to have all my configuration classes in a single file.
* In order to enable Spring to set the values, you have to use `var` instead of `val`.
* Use `lateinit` to tell the Kotlin compiler, that this field is initialized by a framework. This way, we can make the field null-safe without having to initiate it.
* The `@NotNull` annotation will cause Spring to check _during startup_ if a property is specified. So the service won’t start at all. Without the annotation, you’ll only get an error when you _access_ the value. So you may not notice a misconfiguration immediately.

## Open you Classes and Methods for Spring

By default classes and methods in Kotlin are final. If you want to open a class for subclassing or a method for overriding you have to explicitly declare them as `open`. Spring requires this in several cases:

* Classes for external configuration properties.
* Spring Configuration: Make both the class and the `@Bean`-methods non-final.

```kotlin
@Configuration
open class SpringConfiguration {
    @Bean
    @Scope("prototype")
    open fun logger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(injectionPoint.methodParameter.containingClass)
    }
}
```

But that’s no big deal at all because Spring will raise an error if you forget the `open` keyword:

```
org.springframework.beans.factory.parsing.BeanDefinitionParsingException:
Configuration problem: @Configuration class 'SpringConfiguration' may not be final.
Remove the final modifier to continue.
```

# Vaadin

## Concise Even Listener with Kotlin’s Lambdas

[Vaadin is highly event-driven](https://blog.philipphauer.de/evaluating-vaadin-strengths-weaknesses/): You are dealing a lot with event listeners. Fortunately, Kotlin allows us to write very concise event listeners.

Option 1: Use function references. That’s the most compact notation. But, it’s not really different from Java 8:

```kotlin
button.addClickListener(::greet)
```

Option 2: Use lambdas. Kotlin’s lambda notation is more intuitive and slightly shorter than Java’s. First, we use curly braces to specify a lambda in Kotlin. This makes them look like a function body (which is what they are). Second, we can leave out the regular parameter braces `()`, if the lambda is the last parameter.

```kotlin
button.addClickListener {event -> greet(event)}
```

Option 3: Use lambdas with the implicit single parameter name `it`. If the lambda has only a single parameter, we can omit the parameter declaration and use `it` to refer to the parameter.

```kotlin
button.addClickListener {greet(it)}
```

## Structuring UI Definitions with apply()

Every object in Kotlin has the built-in [`apply()` method](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/apply.html). It returns the receiver object and takes a function as a parameter. Within this function, you can call methods on your object. This comes in handy when we want to define and initialize UI components in Vaadin.

```kotlin
val myTable = Table("MyTable", container).apply {
   setSizeFull()
   setColumnHeader(PropertyIds.CODE, Labels.CODE)
   setColumnHeader(PropertyIds.AUTHOR, Labels.AUTHOR)
   setColumnHeader(PropertyIds.DATE, Labels.DATE)
   setColumnHeader(PropertyIds.STATE, Labels.STATE)
   addGeneratedColumn(PropertyIds.CODE, ShortenedValueColumnGenerator)
   setConverter(PropertyIds.DATE, StringToInstantConverter)
}
```

First of all, it’s concise. We don’t have to write the variable name `myTable` again and again. Secondly, we group the initialization logic for the UI component at a single point. This makes the code more readable.

Moreover, we can use this approach to make the UI hierarchy and nesting visible in the code:

```kotlin
val layout = FormLayout().apply {
   setMargin(true)
   isSpacing = true
   val codeLabel = Label().apply {
       contentMode = ContentMode.HTML
       caption = Labels.CODE
       value = snippet.code
   }
   val stateLabel = Label().apply {
       contentMode = ContentMode.HTML
       caption = Labels.STATE
       value = "${snippet.state.toIcon().html} ${snippet.state.toLabel()}"
   }
   val authorLabel = Label().apply {
       caption = Labels.AUTHOR
       value = snippet.author
   }
   val closeButton = Button("Close", FontAwesome.CLOSE).apply {
       addClickListener { close() }
   }
   addComponents(codeLabel, authorLabel, stateLabel, closeButton)
}
```

It’s obvious which component is contained in which layout. You just have to look at the indent. But don’t exaggerate this. If your method gets to long, consider using submethods or a dedicated component class.

`Apply()` is also useful, when you [bind member fields](https://vaadin.com/docs/-/part/framework/datamodel/datamodel-itembinding.html#datamodel.itembinding.formclass) of a form to item properties (using `FieldGroup.bindMemberFields()`).You can bundle the definition and configuration of the form field in one place. So the constructor becomes very short.

```kotlin
class CreateSnippetForm : FormLayout() {

    @PropertyId(PropertyIds.CODE)
    val code = TextArea(Labels.CODE).apply {
        nullRepresentation = ""
        setWidth(100f, Sizeable.Unit.PERCENTAGE)
        isRequired = true
        addStyleName("monospace")
    }

    @PropertyId(PropertyIds.AUTHOR)
    val author = TextField(Labels.AUTHOR).apply {
        nullRepresentation = ""
        setWidth(100f, Sizeable.Unit.PERCENTAGE)
    }

    val createButton = Button("Create Snippet", FontAwesome.CODE)

    init {
        setSizeFull()
        isSpacing = true
        addComponents(code, author, createButton)
    }
}

//usage:
val form = CreateSnippetForm().apply {
   createButton.addClickListener { createSnippet() }
}
val emptySnippet = SnippetCreationBean()
val fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(emptySnippet, form)
```

## Data Classes for Beans and Concise Mapping

Vaadin’s `BeanItemContainer` is very handy. It allows binding arbitrary beans to UI controls. In Java, however, the bean class definition is [extremely verbose, hard to read and error-prone](/kotlin-java-ecosystem-language/#Example_1_Define_and_Map_Beans). Using Kotlin, we only need a few lines:

```kotlin
// Persistence entities in Entities.kt:
data class SnippetEntity(
        val code: String,
        val author: AuthorEntity,
        val date: Instant
)
data class AuthorEntity(val firstName: String, val lastName: String)

// UI beans in Beans.kt:
data class SnippetOverviewBean(
        val code: String,
        val author: String,
        val date: Instant
)
```

Also the mapping from one class to another is very concise and readable. Thanks to single expression functions and named arguments. Let’s assume we want to map the MongoDB entities to their beans counterparts (which are supposed to be used within a `BeanItemContainer`).

```kotlin
fun mapToBeans(entities: List<SnippetEntity>) = entities.map(::mapToBean)

fun mapToBean(entity: SnippetEntity) = SnippetOverviewBean(
        code = entity.code,
        date = entity.date,
        author = "${entity.author.firstName} ${entity.author.lastName}"
)
```

## Putting UI classes together

As already mentioned above, Kotlin allows putting several top-level (public) classes in one file. Yes, you should use this with sound judgment. But in cases where a UI component (like a window or a form class) is only used in a single location (like a view) I locate these classes in a single Kotlin file. This arrangement states clearly: This window belongs to this view and is only used here. Sure, if the window class grows, you may need to move them to separate file in order to keep the view class readable.

## Extension Functions for Adding UI-Related Logic

With Kotlin’s extension functions you can add a method to an existing class. On the one hand, this can be useful when the class is not under your control (like `String`). On the other hand, it can be a nice mean to clearly separate concerns and layers.

Let’s assume the following enum:

```kotlin
enum class SnippetState {ACTIVATED, DEACTIVATED}
```

Let’s say that we want to write the functions `toLabel()` and `toIcon()`, but we don’t want to put these UI-related methods in the enum, which is also used in the persistence layer. In good old Java we would define static helper methods, but in Kotlin there are the elegant extension methods:

```kotlin
fun SnippetState.toIcon() = when (this){
    SnippetState.ACTIVATED -> FontAwesome.THUMBS_O_UP
    SnippetState.DEACTIVATED -> FontAwesome.THUMBS_O_DOWN
}
fun SnippetState.toLabel() = when (this){
    SnippetState.ACTIVATED -> "Activated"
    SnippetState.DEACTIVATED -> "Deactivated"
}
//usage:
val icon = state.toIcon()
//intuitive and readable
```

## Stateless UI Classes with Lambdas or `object`-Singletons

In Vaadin you often have to write stateless UI classes implementing `Table.ColumnGenerator` or `Converter`. The easiest way to implement them is to use function references.

```kotlin
private fun generateDetailsButton(source: Table, itemId: Any, columnId: Any) = Button("Details").apply {
    addStyleName(ValoTheme.BUTTON_LINK)
    addClickListener { Notifications.show("Details!") }
}
//Usage:
table.addGeneratedColumn("Details", ::generateDetailsButton)
```

However, there might be cases where this approach is not sufficient. In those cases, Kotlin’s built-in way to define singleton comes in handy. Just use the keyword `object` instead of `class`.

Case 1: You want to group multiple fields and methods together. This way, you can show that a field or method is only relevant in a certain context/class. This leads to high [cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science)).

```kotlin
private object ShortenedValueColumnGenerator : Table.ColumnGenerator {
    private val MAX_LENGTH = 20

    override fun generateCell(source: Table, itemId: Any, columnId: Any): Any?{
        val log = source.getItem(itemId).getItemProperty(columnId).value as? String
        return log?.shortenWithEllipsis()
    }

    fun String.shortenWithEllipsis(): String{
        if (this.length > MAX_LENGTH){
            return "${this.substring(0, MAX_LENGTH)}..."
        }
        return this
    }
}
//It's easy to reference the singleton:
table.addGeneratedColumn(PropertyIds.CODE, ShortenedValueColumnGenerator)
```

Case 2: The interface has more than one method. So it’s not a SAM/Functional Interface and you can’t use a lambda.

```kotlin
object StringToInstantConverter : Converter<String, Instant> {
    private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z")
            .withLocale(Locale.UK)
            .withZone(ZoneOffset.UTC)

    override fun convertToPresentation(value: Instant?, targetType: Class<out String>?, locale: Locale?)
            = DATE_FORMATTER.format(value)!!

    override fun convertToModel(value: String?, targetType: Class<out Instant>?, locale: Locale?): Instant {
        throw UnsupportedOperationException("Not yet implemented")
    }

    //enjoy the beauty of the following method definitions:
    override fun getPresentationType() = String::class.java 
    override fun getModelType() = Instant::class.java
}
```
