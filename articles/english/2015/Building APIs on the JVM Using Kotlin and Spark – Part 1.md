---
title: 'Building APIs on the JVM Using Kotlin and Spark – Part 1'
url: http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-1/
categories:
    - Kotlin
author: Travis Spencer
date: Aug 06, 2015 09:00
---
![Building-APIs-JVM-kotlin-spark-java-nordic-apis-pt-1](http://nordicapis.com/wp-content/uploads/Building-APIs-JVM-kotlin-spark-java-nordic-apis-pt-1.png)

When you start a new API project, one of the first questions to answer is what programming language to use. Picking the right one can make all the difference. Some languages help solve certain problems while others inhibit solutions. Even after a language is chosen and tens-of-thousands of lines of code have been written, there is the possibility of reducing complexity by using new languages on the same **runtime**.

In this two-part blog series, we’ll explain why the **JVM** provides a strong basis on which to run your APIs, and how to simplify their construction using a framework called **Spark** and a new programming language called **Kotlin**. In this first part, we will introduce you to the JVM and how it can execute code written in many programming languages, including Kotlin. We’ll discuss a number of Kotlin’s features and show them in the context of APIs.

In our [second post](http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-2/), we explore the Spark framework in detail, and how to complement this toolkit with additional components like Inversion of Control (IoC). During the series, we’ll develop a boilerplate that you can use to **create robust APIs with Kotlin and Spark**. The code can be [found on Github](https://github.com/travisspencer/kotlin-spark-demo/), so refer to it there as you read through the snippets below.

Watch Travis Spencer present on this topic at the Stockholm Java Meetup <iframe src="https://www.youtube.com/embed/eDHUabQMRb4" allowfullscreen="allowfullscreen" height="315" frameborder="0" width="560"></iframe>

## Introducing the JVM

The **Java Virtual Machine (JVM)** is a runtime that offers a range of language choices. Adoption of Clojure, Groovy, Scala, Jython, and other JVM-based languages are widespread. Aside from the broad range of language choices, another uncommon aspect of the JVM is its openness. You can get an implementation of the JVM not just from Oracle but also from an [expansive list of other providers](https://en.wikipedia.org/wiki/List_of_Java_virtual_machines). Many distributions of Linux come preloaded with [OpenJDK](https://en.wikipedia.org/wiki/OpenJDK), which is the reference implementation of Java, allowing API developers to host their service with very little cost and hassle.

One of the core tenants of Java is that each new version is **backward compatible** with previous ones. This has caused the Java programming language to evolve more slowly than others like C#, where major changes in the language come with massive upgrade efforts. ![](http://combineoverwiki.net/images/thumb/d/dc/Lambda_logo.svg/365px-Lambda_logo.svg.png)This has improved a lot over the years, and [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) has removed a lot of cruft in the language with the introduction of **lambdas**. Regardless of the pace of Java’s evolution, this commitment to backward compatibility has resulted in a **very stable base**.

The openness and ongoing compatibility of the JVM offers a number of very compelling reason to build APIs on this platform:

* **Return on Investment (ROI)**: Code that continues to run can continue to produce a return on the investment made to write it.
* **Knowledge remains relevant**: Since old code continues to run, all the knowledge gained to write that code continues to be useful. With new versions of the JVM, you can keep writing old-style code. You don’t have to retrain yourself when the runtime’s vendor upgrades the platform.
* **Ecosystem**: These factors have caused a very large ecosystem to flourish around the JVM. This system includes massive corporations and some of the biggest open source communities that will work hard to ensure its longevity.

Probably one of the biggest drawbacks of selecting Java to code up your API is that it is _**so**_ verbose. Who would use it if they didn’t have to?! ROI is often enough of a reason for companies to select it even for greenfield work, putting programmers through the pains of its verbosity even with the advent of alternatives like Node.js and [Golang](http://nordicapis.com/writing-microservices-in-go/).

But what if you had the most sugary syntax you could ever dream of without having to change anything else? What if you could retain your build system, packaging, dependencies, existing code and performance, _and_ get the language you’ve been longing for?

# Enter Kotlin

**![](https://upload.wikimedia.org/wikipedia/en/b/b5/Kotlin-logo.png)Kotlin** is a new programming language from Jetbrains, the makers of some of the best programming tools in the business, including [Intellij IDEA](https://www.jetbrains.com/idea/), an IDE for Java and other languages. As a leader in the Java community, Jetbrains understands the pains of Java and the many benefits of the JVM. Not wanting to throw the baby out with the bathwater, Jetbrains designed Kotlin to run on the JVM to get the best out of that platform.

They didn’t constrain it to this runtime, however — developers can also compile their Kotlin code into **JavaScript**, allowing it to be used in environments like **Node.js**. To work with untyped and typed environments like these, Kotlin is statically typed by default, but allows developers to define dynamic types as well. This balance provides great power and many opportunities.

## Why Kotlin?

[Mike Hearn](https://twitter.com/OctSkyward) does a spectacular job explaining [why you should consider using Kotlin](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3). In his article, he lists the following reasons:

* Kotlin compiles to JVM bytecode and JavaScript
* Kotlin comes from industry, not academia
* Kotlin is open source and costs nothing to adopt
* Kotlin programs can use existing Java or JavaScript frameworks
* The learning curve is very low
* Kotlin doesn’t require any particular style of programming (OO or functional)
* Kotlin can be used in Android development in addition to others where Java and JavaScript work
* There is already excellent IDE support (Intellij and Eclipse)
* Kotlin is highly suitable for enterprise Java shops
* It has the strong commercial support of an established software company

Read through [Mike’s article](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3) for more on the rationale for using Kotlin. Hopefully though, this list is enough to convince you to seriously consider Kotlin for your next API. To see how easy this can be, we’ll explain how to use Kotlin with a [micro-services framework called Spark](http://www.sparkjava.com/). We’ll delve deep into Spark in the next part of this series, but now we’ll explain some of the great Kotlin language features with some [sample code](https://github.com/travisspencer/kotlin-spark-demo/commit/3ebce75345c6a4ac08710578de42bbc7a5a1d297#diff-600376dffeb79835ede4a0b285078036).

# Examples of Kotlin’s Syntax

To see how Kotlin can be used to [create killer APIs](http://nordicapis.com/top-5-development-tips-for-a-killer-api/), we’ll walk through a sample that demonstrates many of the language’s features in the context of APIs. You can find the [entire code on Github](https://github.com/travisspencer/kotlin-spark-demo/), and it’s all open source.

To start, here’s the API’s entry point:

```kotlin
fun main(args: Array) = api(composer = ContainerComposer()) {
    route(path("/login", to = LoginController::class, renderWith = "login.vm"),
          path("/authorize", to = AuthorizeController::class, renderWith = "authorize.vm"),
          path("/token", to = TokenController::class))
}
```

Note that what we have is very readable even with zero knowledge of Kotlin. **This snippet starts an API that exposes various paths that are routed to controllers, some of which are rendered by a template and others that are not.** Easy. Kotlin lends itself to creating fluent APIs like this one, making for very readable code and approachable frameworks.

## Functions and Constructors

To create our Domain Specific Language ([DSL](https://en.wikipedia.org/wiki/Domain-specific_language)) for hosting APIs, we’re using several of Kotlin’s syntactic novelties and language features. Firstly:

* **Constructors**: In Kotlin, you do not use the `new` keyword to instantiate objects; instead, you use the class name together with parenthesis, as if you were invoking the class as a function (a la Python). In the above snippet, a new `ContainerComposer` object is being created by invoking its [default constructor](http://kotlinlang.org/docs/reference/classes.html#constructors).
* **Functions**: [Functions](http://kotlinlang.org/docs/reference/functions.html#function-declarations), which begin with the `fun` keyword, can be defined in a class or outside of one (like we did above). This syntax means that we can write Object Oriented (OO) code or not. This will give you more options and potentially remove lots of boilerplate classes.
* **Single expression functions**: The fluent API allows us to wire up all our routes in a single expression. When a [function consists of a single expression](http://kotlinlang.org/docs/reference/functions.html#single-expression-functions) like this, we can drop the curly braces and specify the body of our function after an equals symbol.
* **Omitting the return type**: When a function does not return a value (i.e., when it’s “void”), the return type is `Unit`. In Kotlin, [you do not have to specify this](http://kotlinlang.org/docs/reference/functions.html#unit-returning-functions); it’s implied.

If we weren’t to use these last two features, the slightly more verbose version of our main function would be this:

```kotlin
fun main(args: Array) : Unit {
    // ...
}
```

The difference is only a few characters, but the result is less noisy.

## Named Arguments and Passing Function Literals

The next part of this main method is the call to the `api` function. This function uses a few other interesting features:

* **Named arguments**: When calling the `path` function, we’re [specifying certain arguments by name](http://kotlinlang.org/docs/reference/functions.html#named-arguments) (e.g., `to` and `renderWith`). Doing so makes the code more fluid.
* **Passing function literals outside the argument list**: When a function, like our `api` function, expects an argument that is itself a function, you can pass it after closing the argument list. This cleans up the code a lot. To see what we mean by this, observe the `api` function definition:

  ```kotlin
  fun api(composer: Composable, routes: () -> List<Controllable>) {
      // ...
  }
  ```

This function takes two arguments:

1.  The composer (more on that in the next part of this series)
2.  The routes as a function that takes no arguments and produces a list

The second argument is the interesting one. It is a [lambda expression](http://kotlinlang.org/docs/reference/lambdas.html). In Kotlin, **lambdas** are written as `() -> T` where the parentheses contain the possibly-empty list of arguments and their types, an arrow symbol (i.e., `->` or “produces”), and the return type. Our `api` function uses this syntax to define it’s last argument. In Kotlin, when the last argument of a function is also a function, we can pass the lambda expression _outside_ of the call. Without this capability, calling the `api` method would look like this:

```kotlin
api(ContainerComposer(), {
    // ...
})
```

See the difference? If you’re a jQuery programmer, you’re probably numb to it. Have a look at this contrived example that shows the difference more clearly:

```kotlin
a({
    b({
        c({
        })
    })
})
```

See it now? When a method takes a function literal as an argument, you end of with a grotesque interchange of parentheses and braces. Instead, Kotlin allows us to pass the body of the function literal after the method call. JQuery programmers unite! This is your way out of the flip-flopping trap of delimiters you’ve found yourself in! Instead, Kotlin allows you write this sort of oscillation like this:

```kotlin
a() {
    b() {
        c()
    }
}
```

So clear. So easy to understand. This convention is what we’re using in the call to the `api` function. Our routes are defined in a lambda that returns a list of [data objects](http://kotlinlang.org/docs/reference/data-classes.html) (which we’ll explain shortly). The result is [half a dozen lines of code](https://github.com/travisspencer/kotlin-spark-demo/blob/master/src/main/kotlin/com/nordicapis/kotlin_spark_sample/main.kt) that start a Web server which hosts our API, sets up three routes, maps them to controllers, and associates templates to certain endpoints. That’s the powerful triad we mentioned early — **language, runtime, and JVM-based frameworks** — that allow us to quickly start serving up APIs!

## Generics

As you saw in the definition of the of the `api` function, Kotlin has support for **generics** (i.e., function templates). In Kotlin, generics aren’t confusing like they are in Java. The thing that makes Java’s generics so tricky is [wildcard parameterized types](http://www.angelikalanger.com/GenericsFAQ/FAQSections/ParameterizedTypes.html#WIldcard%20Instantiations). These were needed to ensure backward compatibility with older Java code. Kotlin doesn’t have wildcards, making it much easier to use generics in this new language. In fact, generics are so easy for Java and C# developers that there’s very little to say.

One aspect of Kotlin’s generics that may be unclear at first is the use of the `out` annotation on some template parameters. This is seen in the definition of the `RouteData` class [included in the sample code](https://github.com/travisspencer/kotlin-spark-demo/commit/3ebce75345c6a4ac08710578de42bbc7a5a1d297#diff-600376dffeb79835ede4a0b285078036):

```kotlin
data class RouteData(
    val path: String,
    val controllerClass: Class,
    val template: String? = null)
```

This class is parameterized along with the constructor argument; `controllerClass`. Both have this extra modifier on `T` though. What does this mean? It means that the output type `T` will not be passed in as input; instead, the `RouteData` class will only produce objects of type `T` as output. This is what is referred to as [declaration-site variance](http://kotlinlang.org/docs/reference/generics.html#declaration-site-variance). Because the `RouteData` class does not consume any values of type `T` but only produces them as return values, the compiler knows that it is safe for any class that extends `Controllable` to be used as a parameter value, since they can always safely be upcast at runtime to the `Controllable` interface.

If `T` were not an output-only type, a potentially unsafe downcast would be required at run-time. By providing this extra bit of info to the compiler, we can avoid wildcards (i.e., something like `RouteData<? extends Controllable>` in Java) and end up with safer, more easy-to-use generics.

## Data Classes

[The `data` annotation](http://kotlinlang.org/docs/reference/data-classes.html) is another interesting feature of Kotlin that we’re using in the `RouteData` class. This modifier tells the compiler that objects of this type will only hold data. This allows it to synthesize a bunch of boilerplate code for us. Specifically, the compiler will implement the following methods for us:

* `equals`
* `hashCode`
* `toString`
* `copy`; and
* Getters and setters for each constructor parameter that is marked with `val` (i.e., read-only variables).

After we define a data class we can also use it to [declare and set the values of multiple variables simultaneously](http://kotlinlang.org/docs/reference/data-classes.html#data-classes-and-multi-declarations) like this:

```kotlin
val (path, controllerClass, template) = routeData
```

Given a data object of type `RouteData`, we can sort of pull it apart into constituent parts, where each property of the class is assigned to respective variables. This can help you write self-documenting code, and can also be used to return more than one value from a function.

### Multiple Return Values from Functions

One attractive language feature of [Golang](http://nordicapis.com/writing-microservices-in-go/) is its syntax that allows developers to define multiple return values for a function. In Go, you can write very descriptive code like this:

```kotlin
func (file *File) Write(b []byte) (n int, err error) {
    // ...
}
```

This says that the function `Write` returns two values: an integer called `n` and `err`, an `error` object. This unique language construct obviates the need to pass references to objects that the function will modify when an additional return value is also needed. This is what one has to do in Java, which you can see in a [Java-based version of our `Controllable` type](https://github.com/travisspencer/stockholm-java-meetup-java-spark-demo/blob/master/src/main/java/java_meetup_spark_demo/Example08/Controllable.java):

```kotlin
interface Controllable {
    default boolean get(Request request, Response response,
        final Map<String, Object> model) { return true; }

    default boolean post(Request request, Response response,
        final Map<String, Object> model) { return true; }

    default boolean put(Request request, Response response,
        final Map<String, Object> model) { return true; }

// ...
}
```

We want to return two things from `get`, `post`, `put`, etc. — a Boolean flag that will be used to abort routing if `false`, and the model that contains the template values (if templating is used by the view). Here the Java language is actually working against us, forcing us to write unclear code. Google Go would help us write this more clearly, and so does Kotlin.

In Kotlin, our `Controllable` type is defined like this:

```kotlin
abstract class Controllable {
    public open fun get(request: Request, response: Response):
        ControllerResult = ControllerResult()

    public open fun post(request: Request, response: Response):
        ControllerResult = ControllerResult()

    public open fun put(request: Request, response: Response):
        ControllerResult = ControllerResult()

    // ...
}
```

Kotlin allows us to say that our controller’s methods return a `ControllerResult` type. Like `RouteData` described above, `ControllerResult` is a data class:

```kotlin
data class ControllerResult(
        val continueProcessing: Boolean = true,
        val model: Map<String, Any> = emptyMap())
```

With this one line, we can easily define a result type that we can use in the `Router` to control the flow of processing requests and provide views with model data.

Big deal, you may say. Create a class in Java and do the same. Sure. With Java though, our `ControllerResult` class becomes 50 annoying lines of code after we implement `equals`, `toString`, `hashCode`, a copy constructor, getters, and setters. As we described above, data classes include all these in **one line of code**. Take that in like a cool refreshing drink after a long day’s work in the hot summer sun!

Using data classes, our controllers can override methods in the `Controllable` class like this:

```kotlin
public override fun get(request: Request, response: Response): ControllerResult =
    ControllerResult(model = mapOf(
        "user" to request.session(false).attribute("username"),
        "data" to mapOf(
                "e1" to "e1 value",
                "e2" to "e2 value",
                "e3" to "e3 value")))
```

Using the `to` keyword and a read-only map produced by the standard Kotlin library’s `mapOf` function, we end up with a very succinct yet readable implementation of this method. Refer to the Kotlin docs for more info about [creating maps with the `to` keyword](http://kotlinlang.org/docs/reference/idioms.html#read-only-map), [the `mapOf` function](http://kotlinlang.org/api/latest/jvm/stdlib/kotlin/map-of.html), and [overriding methods defined in a base class](http://kotlinlang.org/docs/reference/classes.html#overriding-members). For now though, let’s see how we can use the `ControllerResult` objects in a safe and intuitive manner using one of the coolest Kotlin language features.

## Smart Casts

As we’ll discuss more in the next part of this blog series, the methods of our Controllers are invoked dynamically depending on the HTTP method used by the client. This is done in [the `Router` class](https://github.com/travisspencer/kotlin-spark-demo/blob/master/src/main/kotlin/com/nordicapis/kotlin_spark_sample/Router.kt) like this:

```kotlin
val httpMethod = request.requestMethod().toLowerCase()
val method = controllerClass.getMethod(httpMethod, javaClass(), javaClass())
val result = method.invoke(controller, request, response)

if (result is ControllerResult && result.continueProcessing) {
   controller.after(request, response)

   model = result.model
}
```

In the first three lines, we are using reflection to invoke a method on the controller that has the same name as the HTTP method specified by our API consumer. `Invoke` is defined in the Java runtime to return an `Object` which Kotlin represents as `Any`. This is helpful because two of our `Controllable`’s methods, `before` and `after`, do not return `ControllerResult` objects while the bulk of them do. Using [Kotlin’s smart casting language feature](http://kotlinlang.org/docs/reference/typecasts.html#smart-casts), we can write **very clear and safe code** to handle this discrepancy.

In the snippet above, we check at run time to see if the `Any` object is of type `ControllerResult`. This is done using [Kotlin’s is operator](http://kotlinlang.org/docs/reference/typecasts.html#smart-casts) in the if statement. If it is, we also check to see if the data object’s `continueProcessing` property returns `true`. We do this _without_ casting it. On the right side of the logical `&&` operator, Kotlin treats the `result` object not as type `Any` but as type `ControllerResult`. Without having to write code to perform the cast, we can access the object’s `ContinueProcessing` property. Without smart casting and without properties, we’d have to write this conditional statement verbosely in Java like this:

```java
if (result instanceof ControllerResult) {
    ControllerResult controllerResult = (ControllerResult)result;

    if (controllerResult.getContinueProcessing()) {
        // ...
    }
}
```

Even with C#’s `as` operator, we end up with code that isn’t as clear as Kotlin’s. In C#, we’d have to write this:

```kotlin
ControllerResult controllerResult = result as ControllerResult

if (controllerResult != null && controllerResult.continueProcessing) {
    // ...
}
```

Kotlin lets us avoid all this confusing, verbose code. This will ensure that our intent is more clear, and help us find and fix bugs in our APIs more quickly.

[![Java Meetup Medium CTA-01](http://nordicapis.com/wp-content/uploads/Java-Meetup-Medium-CTA-01.png)](http://www.meetup.com/Stockholm-Java-User-Group/events/224295980/)

# Using Kotlin

Because of its interoperability with other tools in the Java ecosystem, it is very easy to start using Kotlin. You can leverage all of the same tools and knowledge that you are using today in your Java development. Building with Ant or Maven? Those work with Kotlin. Writing code in Intellij IDEA, Eclipse, or Sublime? Keep using them!

To get started using your favorite tools with Kotlin, here’s some helpful links:

* [Intellij IDEA](http://kotlinlang.org/docs/tutorials/getting-started.html)
* [Kotlin Sublime Text 2 Package](https://github.com/vkostyukov/kotlin-sublime-package)
* [Eclipse](http://kotlinlang.org/docs/tutorials/getting-started-eclipse.html)
* [Maven usage guide](http://kotlinlang.org/docs/reference/using-maven.html)
* [Using Gradle with Kotlin](http://kotlinlang.org/docs/reference/using-gradle.html)
* [Ant integration](http://kotlinlang.org/docs/reference/using-ant.html)

## Converting Existing Code

When you start using Kotlin, it can help to convert an existing Java project to this new language. Then, you can see how familiar code looks using Kotlin. To convert existing code, you have to use [Intellij](http://kotlinlang.org/docs/tutorials/getting-started.html), [Eclipse](http://kotlinlang.org/docs/tutorials/getting-started-eclipse.html), or (if you only have a snippet) the [Kotlin on-line console](http://try.kotlinlang.org/). In the end, you will probably rewrite a lot of the converted code to use more idioms and Kotlinic styles, but converting existing code is a great starting point.

## Compiling Kotlin

Whether you convert your code or write it from scratch, you are going to need to compile it. The IDE plug-ins make this easy while you’re working. In order to use this language in larger projects, however, you are going to need to automate those builds. This will require integration with Maven, Ant, or Gradle. Jetbrains and the Kotlin community provides these out of the box, and usage is as you would expect. For an existing Java-based project using one of these, switching to Kotlin can be done by replacing just a few lines in your build script. For instance, the sample project accompanying this series was converted to compile Kotlin code instead of Java [with just a few dozen lines](https://github.com/travisspencer/kotlin-spark-demo/commit/3ebce75345c6a4ac08710578de42bbc7a5a1d297#diff-600376dffeb79835ede4a0b285078036).

## Debugging

Compiling code isn’t necessarily working code. To make your software functional, you can use one of the many JVM-based logging frameworks to print out debug statements. You can see this in the sample project that uses SLF4J and Log4j to print statements about API requests and responses. Here’s an indicative sample from the `AuthorizationController`:

```kotlin
public class AuthorizeController : Controllable() {
    private val _logger = LoggerFactory.getLogger(javaClass())

    public override fun before(request: Request, response: Response): Boolean {
        _logger.trace("before on Authorize controller invoked")

        if (request.session(false) == null) {
            _logger.debug("No session exists. Redirecting to login")

            response.redirect("/login")

            // Return false to abort any further processing
            return false
        }

        _logger.debug("Session exists")

        return true
    }

    // ...
}
```

A few things to note about that snippet before we move on:

* It’s normal SLF4J stuff, so there’s no learning curve for Java devs.
* The SLF4J `LoggerFactory` requires a static class to initialize it. This is a Java class, so we use Kotlin’s `::class` syntax to get [a `KClass` object](http://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/) and then call its `java` extension property. Voila! Java interop :)

Basic instrumentation will only take you so far; sometimes you need to roll up your sleeves and dive into the running code though. You can do this _easily_ in Intellij and Eclipse using those tools’ debuggers just as you would with your Java code. In these environments, you have all the same tools you have come to rely on when debugging other JVM-based code. For instance, the **call stack**, list of **running threads**, **expression evaluation**, and of course **breakpoints**, are all available to you! Here’s a screenshot of the above code running in Intellij with the debugger attached:

![debugging](http://nordicapis.com/wp-content/uploads/debugging.png)

Finding bugs will be <del>easy</del> _easier_ with that. This is a _very_ compelling example of why Kotlin is a better choice for creating large-scale APIs than some other languages (e.g., Go). ([Golang can be debugged using GDB](http://golang.org/doc/gdb), but that tool is not nearly as user-friendly as Intellij’s and Eclipse’s debuggers.)

# Conclusion

Unlike other programming environments, the JVM gives you both language and vendor choices that can help you create incredible API. On the JVM you can choose between **dynamic** languages like Groovy, **functional** ones like Scala and Clojure, and now another rival — [Kotlin](http://kotlinlang.org/). We talked through a number of reasons to use this language, and showed some of the features this new JVM upstart delivers.

All this is great, you may be saying, but what’s the catch? There are [some drawbacks](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3#5cef), but they are minor. The strongest argument against Kotlin is its immaturity — it isn’t at v1 yet. This is a problem that will inevitably be fixed with a bit more time. Even now at milestone 12, the compiler gives you warnings about the use of deprecated language features that will not be included in the initial release. This makes it easy to pivot and prepare.

[![battlehack](http://nordicapis.com/wp-content/uploads/battlehack.png)](https://2015.battlehack.org/stockholm)In [part 2](http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-2/) of this series, we delve deeper into [Spark](http://www.sparkjava.com/) and explain it more using the [sample Kotlin code](https://github.com/travisspencer/kotlin-spark-demo/) we started developing in this post. In the meantime, be sure to [register for our upcoming event in Stockholm](http://www.meetup.com/Stockholm-Java-User-Group/events/224295980/) where we’ll be discussing Kotlin, Clojure, Groovy, and building APIs on the JVM. Learning more about Kotlin could also give you the edge you’ll need if you plan to participate in PayPal’s upcoming hackathon, [BattleHack](https://2015.battlehack.org/stockholm), which is happening on November 14th and 15th in Stockholm.

Continue to Part 2: [Building APIs on the JVM Using Kotlin and Spark – Part 2](http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-2/)

## Kotlin Resources

If you aren’t going to be in Stockholm this month for the meetup, you can also learn more about this new language from these resources:

* [Kotlin language reference](http://kotlinlang.org/docs/reference/) (start here)
* [Kotlin programming language cheat sheet](https://gist.github.com/dodyg/5823184)
* [Designing DSLs in Kotlin](http://blog.jetbrains.com/kotlin/2011/10/dsls-in-kotlin-part-1-whats-in-the-toolbox-builders/)
* [RE: Why Kotlin is my next programming language – SubReddit discussion](https://www.reddit.com/r/programming/comments/3chgp3/why_kotlin_is_my_next_programming_language/?submit_url=https%3A%2F%2Fmedium.com%2F%40octskyward%2Fwhy-kotlin-is-my-next-programming-language-c25c001e26e3&already_submitted=true&submit_title=%22Why+Kotlin+is+my+next+programming+language%22+by+Mike+Hearn)
* [Sample Web API project using Kotlin and Spark](https://github.com/travisspencer/kotlin-spark-demo/commit/3ebce75345c6a4ac08710578de42bbc7a5a1d297#diff-600376dffeb79835ede4a0b285078036)
* [Getting the most from the JVM using Java, Clojure, Kotlin & Groovy meetup in Stockholm](http://www.meetup.com/Stockholm-Java-User-Group/events/224295980/)
* [Building APIs on the JVM Using Kotlin and Spark – Part 2](http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-2/)

_[Disclosure: PayPal is a sponsor of the Java Stockholm meetup being produced by Nordic APIs]_



