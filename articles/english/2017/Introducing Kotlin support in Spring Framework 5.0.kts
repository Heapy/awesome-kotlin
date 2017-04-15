
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Following the [Kotlin support on start.spring.io](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin) we introduced a few months ago, we have continued to work to ensure that Spring and [Kotlin](https://kotlin.link/) play well together. One of the key strengths of Kotlin is that it provides a very good [interoperability](https://kotlinlang.org/docs/reference/java-interop.html) with libraries written in Java. But there are ways to go even further and allow writing fully idiomatic Kotlin code when developing your next Spring application. In addition to Spring Framework support for Java 8 that Kotlin applications can leverage like functional web or bean registration APIs, there are additional Kotlin dedicated features that should allow you to reach a new level of productivity.

That’s why we are introducing a dedicated Kotlin support in [Spring Framework 5.0 M4](https://spring.io/blog/2016/12/30/spring-framework-5-0-m4-released), and I would like to summarize in this blog post the features that are designed to make your developer experience seamless when using these technologies together. You can use [this link](https://jira.spring.io/issues/?filter=15463) to find Kotlin related issues in Spring Framework bug tracker.

A key building block of our Kotlin support is [Kotlin extensions](https://kotlinlang.org/docs/reference/extensions.html). They allow to extend existing APIs in a non-intrusive way, providing a better alternative to utility classes or Kotlin specific class hierarchies to add Kotlin dedicated features to Spring. Some libraries like [KotlinPrimavera](https://github.com/MarioAriasC/KotlinPrimavera/wiki) from [Mario Arias](https://github.com/MarioAriasC) have already showed various kind of Kotlin helpers we can bring to Spring API in order to allow writing more idiomatic code. With Spring Framework 5, we integrate the most useful and popular extensions in Spring Framework dependencies, and we are adding new ones! Be aware that Kotlin extensions are statically resolved, you have to import them (like static imports in Java).

## Functional bean registration with Kotlin

Spring Framework 5.0 introduces a new way to register beans using lambda as an alternative to XML or JavaConfig with `@Configuration` and `@Bean`. In a nutshell, it makes it possible to register beans with a `Supplier` lambda that acts as a `FactoryBean`.

In Java you will for example write:

```kotlin
GenericApplicationContext context = new GenericApplicationContext();
context.registerBean(Foo.class);
context.registerBean(Bar.class, () -> new 
    Bar(context.getBean(Foo.class))
);
```

While in Kotlin, reified type parameters allow us to simply write:

```kotlin
val context = GenericApplicationContext {
    registerBean<Foo>()
    registerBean { Bar(it.getBean<Foo>()) }
}
```

You can see a concrete example of Spring application using both functional [web](https://github.com/mix-it/mixit/blob/master/src/main/kotlin/mixit/controller/UserController.kt) and [bean registration](https://github.com/mix-it/mixit/blob/master/src/main/kotlin/mixit/Context.kt) API at [https://github.com/mix-it/mixit/](https://github.com/mix-it/mixit/).

`ApplicationContext` related Kotlin extensions available are [BeanFactoryExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-beans/src/main/kotlin/org/springframework/beans/factory/BeanFactoryExtensions.kt), [ListableBeanFactoryExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-beans/src/main/kotlin/org/springframework/beans/factory/ListableBeanFactoryExtensions.kt), [GenericApplicationContextExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/kotlin/org/springframework/context/support/GenericApplicationContextExtensions.kt) and [AnnotationConfigApplicationContextExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/kotlin/org/springframework/context/annotation/AnnotationConfigApplicationContextExtensions.kt).

## Spring Web functional API, the Kotlin way

Spring Framework 5.0 comes with a `RouterFunctionDsl` that allows you to leverage the [Spring Functional Web API](https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework) recently announced with clean and idiomatic Kotin code:

```kotlin
fun route(request: ServerRequest) = route(request) {
    accept(TEXT_HTML).apply {
            (GET("/user/") or GET("/users/")) { findAllView() }
            GET("/user/{login}") { findViewById() }
    }
    accept(APPLICATION_JSON).apply {
            (GET("/api/user/") or GET("/api/users/")) { findAll() }
            POST("/api/user/") { create() }
            POST("/api/user/{login}") { findOne() }
    }
 }
```

Thanks to Yevhenii Melnyk for its early prototype and help!

## Leveraging Kotlin nullable information

Originally based on a community contribution from [Raman Gupta](https://github.com/rocketraman), Spring now takes advantage of [Kotlin null-safety support](https://kotlinlang.org/docs/reference/null-safety.html) to determine if an HTTP parameter is required without having to define explicitly the `required` attribute. That means `@RequestParam name: String?` with be treated as not required and `@RequestParam name: String` as required. This is also supported on Spring Messaging `@Header` annotation.

In a similar fashion, Spring bean injection with `@Autowired` or `@Inject` uses this information to know if a bean is required or not. `@Autowired lateinit var foo: Foo` implies that a bean of type `Foo` must be registered in the application context while `@Autowired lateinit var foo: Foo?` won’t raise an error if such bean does not exist.

## Extensions for RestTemplate and Functional Web API

For example, [Kotlin reified type parameters](https://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters) provide a workaround for JVM [generics type erasure](https://docs.oracle.com/javase/tutorial/java/generics/erasure.html), so we have introduced some extensions to take advantage of this feature to provide a better API when possible.

That allows to provide convenient API for `RestTemplate` (thanks to [Jon Schneider](https://github.com/jkschneider) from Netflix for contributing this). For example, to retrieve a list of `Foo` objects in Java you have to write:

```java
List<Foo> result = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Foo>>() { }).getBody();
```

Or that way if you use an intermediate array:

```java
List<Foo> result = Arrays.asList(restTemplate.getForObject(url, Foo[].class));
```

While in Kotlin with Spring Framework 5 extensions you will be able to write:

```kotlin
val result: List<Foo> = restTemplate.getForObject(url)
```

Web API Kotlin extensions available in Spring Framework 5.0 are [RestOperationsExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-web/src/main/kotlin/org/springframework/web/client/RestOperationsExtensions.kt), [ServerRequestExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-web-reactive/src/main/kotlin/org/springframework/web/reactive/function/server/ServerRequestExtensions.kt), [BodyInsertersExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-web-reactive/src/main/kotlin/org/springframework/web/reactive/function/BodyInsertersExtensions.kt), [BodyExtractorsExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-web-reactive/src/main/kotlin/org/springframework/web/reactive/function/BodyExtractorsExtensions.kt), [ClientResponseExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-web-reactive/src/main/kotlin/org/springframework/web/reactive/function/client/ClientResponseExtensions.kt), [ModelExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/kotlin/org/springframework/ui/ModelExtensions.kt) and [ModelMapExtensions](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/kotlin/org/springframework/ui/ModelMapExtensions.kt).

These extensions also provide member functions supporting natively Kotlin `KClass`, allowing you to specify `Foo::class` parameter instead of `Foo::class.java`.

## Reactor Kotlin extensions

[Reactor](https://projectreactor.io/) is the reactive foundation Spring Framework 5.0 is built upon, and there are good chances you are going to use its [Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html), [Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) and [StepVerifier](https://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html) APIs when developing a reactive web application.

So today we are also introducing Kotlin support in Reactor, via the new [reactor-kotlin](https://github.com/reactor/reactor-kotlin) project! It provides extensions to be able to create `Mono` instances from any class instance by writing `foo.toMono()` which many will prefer to `Mono.just(foo)`. It also supports for example creating a `Flux` from a Java 8 `Stream` instance with `stream.toFlux()`. `Iterable`, `CompletableFuture` and `Throwable` extensions as well as `KClass` based variants of Reactor API are also provided.

This is still the early days of this project, so feel free to [contribute](https://github.com/reactor/reactor-kotlin/pulls) your own extensions if you find missing bits.

## No need to declare your bean class as open anymore

Until now, one of the few pain points you faced when building a Spring Boot application with Kotlin was the need to add an `open` keyword on each class and their member functions of Spring beans proxified with CGLIB like `@Configuration` classes. The root cause of this requirement comes from the fact that in Kotlin, [classes are final by default](https://discuss.kotlinlang.org/t/classes-final-by-default/166).

Fortunately, Kotlin 1.0.6 now provides a `kotlin-spring` plugin that open classes and their member functions by default for classes annotated or meta-annotated with one of the following annotation:

* `@Component`
* `@Async`
* `@Transactional`
* `@Cacheable`

Meta-annotations support means that classes annotated with`@Configuration`, `@Controller`, `@RestController`, `@Service` or `@Repository` are automatically opened since these annotations are meta-annotated with `@Component`.

We have updated [start.spring.io](http://start.spring.io/#!language=kotlin) to enabled it by default. You can have a look to [this Kotlin 1.0.6 blog post](https://blog.jetbrains.com/kotlin/2016/12/kotlin-1-0-6-is-here/) for more details, including the new `kotlin-jpa` and `kotlin-noarg` plugins really useful with Spring Data entities.

## Kotlin based Gradle build configuration

Back in May, Gradle [announced](https://blog.gradle.org/kotlin-meets-gradle) that they are going to support writing build and config files in Kotlin in addition to Groovy. This makes it possible to have full auto-completion and validation in your IDE, because such files are regular statically-typed Kotlin Script files. This is likely to become the natural choice for Kotlin based project, but this is also valuable for Java projects too.

Since May, the [gradle-script-kotlin](https://github.com/gradle/gradle-script-kotlin) project has continued to evolve, and is now usable with 2 warnings to keep in mind:

* You need Kotlin 1.1-EAP IDEA plugin to get autocompletion (but wait Kotlin `1.1-M05` if you are using `kotlin-spring` plugin since `1.1-M04` does not work reliably with this plugin yet)
* The documentation is not complete, but the Gradle team is really helpful on the #gradle channel of the Kotlin Slack.

Both [spring-boot-kotlin-demo](https://github.com/sdeleuze/spring-boot-kotlin-demo) and [mixit](https://github.com/mix-it/mixit/) projects use such Kotlin based Gradle builds, so feel free to have a look. We are [discussing](https://github.com/spring-io/initializr/issues/334) adding such support on start.spring.io.

## Kotlin Script based templates

As of version 4.3, Spring Framework provides a [ScriptTemplateView](http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/view/script/ScriptTemplateView.html) to render templates using script engines that supports [JSR-223](https://www.jcp.org/en/jsr/detail?id=223). Kotlin 1.1-M04 provides such support and allows to render Kotlin based templates, see [this commit](https://github.com/spring-projects/spring-framework/commit/badde3a479a53e1dd0777dd1bd5b55cb1021cf9e) for details.

This enables some interesting use cases like writing type-safe templates using [kotlinx.html](https://github.com/Kotlin/kotlinx.html) DSL or simply Kotlin multiline `String` with interpolation, as demonstrated in this [kotlin-script-templating](https://github.com/sdeleuze/kotlin-script-templating) project. This could allow you to write this kind of templates with full autocompletion and refactoring support in your IDE:

```kotlin
import io.spring.demo.User
import io.spring.demo.joinToLine

\"\"\"
${"$"}{include("header", bindings)}
<h1>Title : ${"$"}title</h1>
<ul>
    ${"$"}{(users as List<User>).joinToLine{ "<li>User ${"$"}{it.firstname} ${"$"}{it.lastname}</li>" }}
</ul>
${"$"}{include("footer")}
\"\"\"
```

This feature is still work in progress, but I am collaborating with the Kotlin team to tentatively make it production ready with nested template and i18n support on both MVC and Reactive sides for Spring Framework 5.0 GA.

## Conclusion

The more I write Spring Boot applications with Kotlin, the more I feel these 2 technologies share the same mindset and allow you to write applications more efficiently with expressive, short and readable code, and Spring Framework 5 Kotlin support is a significant step towards combining these technologies in a natural, simple and powerful way.

Kotlin can be used to write [annotation-based Spring Boot applications](https://github.com/sdeleuze/spring-boot-kotlin-demo), but will also be a good fit with the new kind of [functional and reactive applications](https://github.com/mix-it/mixit/) that Spring Framework 5.0 will enable.

Kotlin team did a great job by fixing almost all the pain points we reported, so big thanks to them. The upcoming Kotlin 1.1 release is expected to also fix [KT-11235](https://youtrack.jetbrains.com/issue/KT-11235) in order to allow specifying array annotation attribute single value without `arrayOf()`. The main remaining issue you will face is maybe [KT-14984](https://youtrack.jetbrains.com/issue/KT-14984) that will require specifying explicitly lambda type where just specifying `{ }` should be enough.

Feel free to test Spring Framework 5.0 Kotlin support by going to [start.spring.io](https://start.spring.io/#!language=kotlin) and generating a Spring Boot `2.0.0 (SNAPSHOT)` project and send us your feedback here or in the `#spring` channel of [Kotlin Slack](http://slack.kotlinlang.org/). You can also [contribute](https://github.com/spring-projects/spring-framework/pulls) the Kotlin extensions you need ;-)

"""

Article(
  title = "Introducing Kotlin support in Spring Framework 5.0",
  url = "https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0",
  categories = listOf(
    "Kotlin",
    "Spring"
  ),
  type = article,
  lang = EN,
  author = "Sébastien Deleuze",
  date = LocalDate.of(2017, 1, 4),
  body = body
)
