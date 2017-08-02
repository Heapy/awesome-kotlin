import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Work with a statically typed, low-ceremony language that provides first-class functions, nullability protections, and complete integration with existing Java libraries.

It has been more than five years since Project Kotlin, an open source language targeting the JVM, was announced by JetBrains. Since then, much progress has been made, some language designs have changed, a new platform — namely JavaScript — is now supported, and even the project name has changed. It’s now simply known as Kotlin. But if there’s one thing that still remains, it is the initial goals and intent for why Kotlin was developed.

At JetBrains, we have been developing IDEs for many programming languages, and yet despite this, much of our code continues to be written in Java. In 2009, we were looking for an alternative to Java, something that could reduce the size of our codebase by being more concise and, at the same time, offer features that we felt could provide important benefits. We needed a language that was also syntactically similar enough to Java that ramp-uptime would not be substantial. The main contender at the time was Scala, but the compiler performance wasn’t that great and providing tooling for Scala was (and continues to be) quite challenging. Tooling and performance were important aspects in our decision, so we opted not to go with Scala. Thus was born Project Kotlin.

Shortly afterward, news about the JVM language Ceylon was announced; and at one point we considered joining efforts. However, at the time, Ceylon’s focus on interoperability was somewhat of a low priority. Given that we have a large codebase in Java, being able to use and extend it was of considerable importance to us. Thus, we decided to continue down our own path with Kotlin.

## Removing the Pain

Five years later and close to reaching the 1.0 milestone, Kotlin remains true to its initial goals of being concise, safe, interoperable, “toolable”, and performant. Many, if not all, of these goals are for a single purpose: removing some of the pain and the errors we encounter when writing code.

The IntelliJ platform is an extremely large codebase on which all our IDEs, including IntelliJ IDEA, are built. The open source Community Edition alone, which is hosted on [GitHub](https://github.com/JetBrains/intellij-community/), has millions of lines of code. While there is a lot of functionality, much of the code is often boilerplate code that is necessary because, well, because it’s Java. One of Kotlin’s goals has been to reduce the amount of somewhat pointless code yet maintain readability and functionality. For instance, a typical Java bean with property getters, setters, toString, and equality in Kotlin can be reduced to the following:

```kotlin
data class Customer(
    var name: String,
    var email: String
)
```

Things such as smart-casting remove the need for verbosity by delegating the work to the compiler. For example, when checking an immutable value for a specific type, it’s no longer necessary to cast to that type when operating on it:

```kotlin
fun convert(obj: Shape) {
    if (obj is Circle) {
        val radius = obj.radius()
        // ...
    }
}
```

Kotlin also has support for named objects, which, in essence, means a singleton would simply be written as follows:

```kotlin
val MySingleton = object {
    val numberOfDays = 10
}
```

Another pain point Kotlin addresses is the need to use functional constructs — such as lambda expressions and higher-order functions — and to treat functions as first-class citizens. While Java 8 addresses some of these concerns, our goal was and continues to be to provide this functionality when using Java 6, 7, or 8 — thus, even allowing support for these features on the Android platform. This is one reason that Kotlin has enjoyed considerable popularity in the Android development community.

Functions can be top-level in Kotlin, much like they are in JavaScript, meaning there’s no need to attach a function to an object. As such, we could simply declare a function in a file like this:

```kotlin
fun toSentenceCase(input: String) {
    // ...
}
```

Much like C#, Kotlin also allows extension functions, meaning a type (either of Java or Kotlin) can be extended with new functionality simply by suffixing the type. Taking the previous example, if I want the String type to have `toSentenceCase()`, I can simply write the following:

```kotlin
fun String.toSentenceCase() {
    // 'this' would hold an
    // instance of the object
}
```

To work efficiently with functions as primitives, there needs to be support for higher-order functions — that is, functions that take functions as parameters or return functions. With Kotlin, this is possible, for example:

```kotlin
fun operate(x: Int, y: Int, operation: (Int, Int) -> Int) {
    // ...
}
```

This code declares a function that takes three parameters: two integers and a third parameter that is a function that, in turn, takes two integers and returns an integer. We can then invoke functions as follows:

```kotlin
fun sum(x: Int, y: Int) {
    // ...
}

operate(2, 3, ::sum)
```

This code shows a function, `sum`, being defined and passed as a parameter to operate. It shows that Kotlin supports referencing functions by name. Of course, a lambda expression can be passed in as well:

```kotlin
operate(2, 3, { x, y -> x +y })
```

These capabilities deliver an elegant way of doing function pipelining:

```kotlin
val numbers = 1..100

numbers
    .filter { it % 2 == 0 }
    .map { it + 5 }
    .forEach {
        println(it)
    }
```

One more issue we attack with Kotlin is null pointer exceptions. In Kotlin, by default, things cannot be null, meaning that potentially the only way we’d get a null reference exception would be if we explicitly force it.

```kotlin
var city = "London"
```

In the code above, city could never be assigned a null value. If we want it to be null, we need to go out of our way to be explicit:

```kotlin
var city : String? = null
```

where `?` indicates that a type can be nullable. When interoperating with Java, we provide certain mechanisms to warn of possible null references, as well as providing some operators to make the code more concise, such as the safe call operator:

```kotlin
var file = File("...")
file?.length()
```

Because of the `?.` , this code would invoke `length()` on `file` only if `file` were not `null`. The standard library, a small runtime that ships with Kotlin, also provides additional functions in this area, such as the `let` function, which when combined with the safe call operator allows for succinct code, such as the following:

```kotlin
obj?.let {
... // execute code here
}
```

This results in the code block executing if the object is not null.

One last thing worth mentioning about Kotlin is its ability to easily enable the creation of DSLs — without the overhead that necessarily comes with maintaining them or the language knowledge required to implement them. Top-level functions, higher-order functions, extension functions, and a few conventions, such as not having to use brackets when the last parameter to a function is another function — these features allow for creating rich DSLs that are strongly typed. The quintessential example is that of type-safe Groovy-style builders. The following function generates the expected HTML output:

```kotlin
html {
    head {
        title { +"XML encoding with Kotlin" }
    }
    body {
        h1 { +"XML encoding with Kotlin" }
        p { +"this is an alternative markup to XML" }
    }
}
```

## Growth and the Road Ahead

The previous code snippets are just a few examples of what Kotlin provides. It wouldn’t be possible to cover all the little details and conveniences Kotlin offers in a single article.

Over the past year, and despite not having released version 1.0, we’ve noticed a substantial growth and interest in Kotlin. There has been an increase in downloads and visits to the Kotlin site, as well as an increase in technical questions in both our forums and public venues such as StackOverflow.

While a lot of this interest is due to the Android community and our contributions in terms of additional tooling and frameworks for this space, there’s also interest in other more generic areas such as web development, both client-side (given Kotlin’s ability to compile to JavaScript) and server-side.

We’re close to reaching the first major release, and we’ve made significant steps toward that. Over the past few milestone releases, we’ve been removing and adjusting some things in the language to make sure that once we release, we’ll be fairly certain that what we ship is there to stay. As any language designer or developer knows, whatever goes in a language stays as baggage pretty much forever.

As a company that provides tooling for developers, we’ve tried to make language release transitions as smooth as possible. To this end, a new release usually comes with a compiler warning about a potential upcoming change or deprecation. The IDE also provides a quick fix to easily migrate code to newer syntax. We believe that this way, we create a smooth experience for developers that are already using Kotlin in production.

Beyond these quick fixes, we’re also focusing on improving other aspects of tooling. For Kotlin to be successful, the entry barrier should be low in all aspects. That is why we not only provide tooling for IntelliJ IDEA, in both Ultimate and the open source Community Edition, but also for build tools such as Gradle, Ant, and Maven, as well as a simple command-line compiler. We’ve also released a preliminary version of Kotlin for Eclipse, and we’re hoping that much like there are contributions to Kotlin in other areas, the community will contribute to Eclipse support as well.

## Conclusion

In conclusion, we developed Kotlin for our own use primarily and are heavily invested in it. For us, it’s a tool that we’re using to drive our own business, which is developer tools. We already have several internal and public-facing web applications written in Kotlin. Some of our newer tools are being written in Kotlin and our existing tools, such as IntelliJ IDEA and YouTrack, are adopting Kotlin. `</article>`

> This article is the inaugural installment of a new series on JVM languages that will appear in Java Magazine. We will examine the full range of languages, from large commercial efforts to projects driven by determined groups of hackers. In the next issue, we’ll cover Jython. —Ed.

## LEARN MORE
* [Kotlin home](http://kotlinlang.org/)
* [Kotlin on StackOverflow](https://stackoverflow.com/questions/tagged/kotlin)
* [Kotlin on Reddit](https://www.reddit.com/r/Kotlin/)
"""

Article(
  title = "Kotlin: A Low-Ceremony, High-Integration Language",
  url = "http://www.oracle.com/technetwork/issue-archive/2015/15-sep/index.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "HADI HARIRI",
  date = LocalDate.of(2015, 9, 30),
  body = body
)
