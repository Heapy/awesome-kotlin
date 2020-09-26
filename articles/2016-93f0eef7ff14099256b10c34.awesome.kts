
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
It’s no mystery that I’m a fan of both Spring Boot and Vaadin. When the [Spring Boot Vaadin add-on](https://github.com/vaadin/spring) became <abbr title="General Availability">GA</abbr>, I was ecstatic. Lately, I became interested in [Kotlin](https://kotlinlang.org/), a JVM-based language offered by JetBrains. Thus, I wanted to check how I could develop a small Spring Boot Vaadin demo app in Kotlin – and learn something in the process. Here are my discoveries, in no particular order.

### Spring needs non-final stuff

It seems Spring needs `@Configuration` classes and `@Bean` methods to be non-final. As my previous Spring projects were in Java, I never became aware of that because I never use the `final` keyword. However, Kotlin classes and methods are final _by default_: hence, you have to use the open keyword in Kotlin.

```kotlin
@Configuration
open class AppConfiguration {
    @Bean
    @UIScope
    open fun mainScreen() = MainScreen()
}
```

### No main class

Spring Boot applications require a class with a `public static void main(String... args)` method to reference a class annotated with `@SpringBootApplication`. In general, those two classes are the same.

Kotlin has no concept of such static methods, but offers pure functions and objects. I tried to be creative by having an annotated object referenced by a pure function, both in the same file.

```kotlin
@SpringBootApplication
open class BootVaadinKotlinDemoApplication

fun main(vararg args: String) {
    SpringApplication.run(arrayOf(BootVaadinKotlinDemoApplication::class.java), args)
}
```

### Different entry-point reference

Since the main function is not attached to a class, there’s no main class to reference to launch inside the IDE. Yet, Kotlin creates a .class with the same name as the file name suffixed with `Kt`.

My file is named `BootVaadinKotlinDemoApplication.kt`, hence the generated class name is `BootVaadinKotlinDemoApplicationKt.class`. This is the class to reference to launch the application in the IDE. Note that there’s no need to bother about that when using `mvn spring-boot:run` on the command-line, as Spring Boot seems to scan for the main method.

### Short and readable bean definition

Java syntax is seen as verbose. I don’t think it’s a big issue when its redundancy is very low compared to the amount of useful code. However, in some cases, even I have to admit it’s a lot of ceremony for not much. When of such case is defining beans with the Java syntax:

```kotlin
@Bean @UIScope
public MainScreen mainScreen() {
    return new MainScreen();
}
```

Kotlin cuts through all of the ceremony to keep only the meat:

* No semicolon required
* No new keyword
* Block replaced with an equal sign since the body consists of a single expression
* No return keyword required as there’s no block
* No return type required as it can easily be inferred

```kotlin
@Bean @UIScope
fun mainScreen() = MainScreen()
```

Spring configuration files are generally quite long and hard to read. Kotlin makes them much shorter, without sacrificing readability.

### The init block is your friend

In Java, the constructor is used for different operations:

1.  storing arguments into attributes
2.  passing arguments to the super constructor
3.  other initialization code

The first operation is a no-brainer because attributes are part of the class signature in Kotlin. Likewise, calling the super constructor is handled by the class signature. The rest of the initialization code is not part of the class signature and should be part of an `init` block. Most applications do not this part, but Vaadin needs to setup layout and related stuff.

```kotlin
class MainScreenPresenter(tablePresenter: TablePresenter,
                          buttonPresenter: NotificationButtonPresenter,
                          view: MainScreen, eventBus: EventBus) : Presenter<MainScreen>(view, eventBus) {

    init {
        view.setComponents(tablePresenter.view, buttonPresenter.view)
    }
}
```

### Use the apply method

Kotlin has a standard library offering small dedicated functions. One of them is apply, defined as `inline fun T.apply(f: T.() -> Unit): T (source)`. It’s an extension function, meaning every type will have it as soon as it’s imported into scope. This function requires an argument that is a function and that returns nothing. Inside this function, the object that has been apply-ed is accessible as `this` (and `this` is implicit, as in standard Java code). It allows code like this:

```kotlin
VerticalLayout(button, table).apply {
    setSpacing(true)
    setMargin(true)
    setSizeFull()
}
```

### Factor view and presenter into same file

Kotlin makes code extremely small, thus some files might be only a line long (not counting import). Opening different files to check related classes is useless. Packages are a way to organize your code; I think files might be another way in Kotlin. For example, Vaadin views and presenters can be put into the same file.

```kotlin
class NotificationButton: Button("Click me")

class NotificationButtonPresenter(view: NotificationButton, eventBus: EventBus): Presenter<NotificationButton>(view, eventBus) { ... }

```

### Lambdas make great listeners

As of Java 8, single-method interfaces implemented as anonymous inner classes can be replaced with lambdas. Kotlin offers the same feature plus:

* it allows to omit parentheses if the lambda is the only argument
* if the lambda has a single argument, its default name is `it` and it doesn’t need to be declared

Both make for a very readable syntax when used in conjunction with the Vaadin API:

```kotlin
view.addValueChangeListener {
    val rowId = it.property.value
    val rowItem = view.containerDataSource.getItem(rowId)
    eventBus.publish(SESSION, rowItem)
}
```

Note: still, more complex logic should be put into its [own function](https://morevaadin.com/content/lambdas-java-8/).

"""

Article(
  title = "Playing with Spring Boot, Vaadin and Kotlin",
  url = "https://blog.frankel.ch/playing-with-spring-boot-vaadin-and-kotlin",
  categories = listOf(
    "Kotlin",
    "Spring",
    "Vaadin"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Frankel",
  date = LocalDate.of(2016, 1, 10),
  body = body
)
