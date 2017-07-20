
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
> Kotlin already looks like a tool that is going to improve productivity significantly. We anticipate that it is definitely going to reduce the pain points faced by Java developers. Here is why we think Kotlin will be the next big thing for the Java community.

Kotlin is a high-performance language, it runs on the JVM and uses existing Java libraries and tools. Plus, Java can also interact with Kotlin components seamlessly. The primary goal of Kotlin is to make it useful for practical, real-life projects; one can easily learn it compared to other JVM-based languages (such as Scala). Since Java has a deep interoperability with Kotlin, Java developers may be more attracted to learn and test it.

Kotlin also has a strong commercial support and stable business model. From a developer’s perspective, Kotlin is very promising and gets better [with every release](https://jaxenter.com/kotlin-1-0-4-is-here-129315.html). So we can safely predict that Kotlin will be the next big thing for the Java community.

## Is Kotlin a good fit for Java developers?

All that JetBrains wanted was to provide a useful language that solves existing problems. Kotlin has the following objectives which will help users solve or avoid existing issues in Java and ultimately perform better.

* Provides an intuitive language that can infer from codes written by developers and do a lot of work on behalf of developers.
* Improves productivity by reducing the effort needed to write code. For example, a 50 lines (approx.) code written in Java can be reduced to 2/3 (approx.) lines in Kotlin.
* Prevents null pointer exceptions.
* Provides quick code compilation.
* Reduces learning time for developers by providing documentation and support.
* Kotlin code syntax is easy to understand, so the code review is simple even for a newcomer.
* Runtime overhead is also low as the standard library is small and compact.

Apart from the above-mentioned advantages, Kotlin also provides easy code conversion between Java and Kotlin, easy IDE integration, full debugging, refactoring and profiling etc. Therefore, we anticipate that Kotlin is definitely going to reduce the pain points faced by the Java developers.

## The Kotlin features Java developers should love

These are the features Java developers should love:

**No more null pointer exceptions**

This is probably the most important feature. The null pointer exception is one of the most dreaded, unsolved issues developers face. Kotlin’s type system does not compile code that assigns or returns null. See the example below.

```kotlin
val name: String = null // tries to assign null, won't compile
stephen getName() : String = null // tries to return null, won't compile
```

Since every method call on a nullable type can potentially cause a null pointer exception, the Kotlin compiler forces the developer to use the Elvis operator whenever the call result is assigned to a non-null type.

**Less code**

Kotlin compiler can infer or understand from the code written by a developer and can develop or write the remaining code. Java requires the developer to explicitly write everything which consumes a lot of time and effort. Kotlin saves effort and time and improves productivity.

For example, the Kotlin compiler can infer types in variable declarations, as well as compiler-generated getters/setters/equals/hashCode. On the other hand, the Java compiler does not infer as much and requires the developer to explicitly write code.

**Easy to learn and use **

Since there is nothing revolutionary about Kotlin, any software developer can learn and use it, especially the Java and Scala developers. The main reason is that Kotlin is dependent on Java in many ways and extensively uses the Java library.

Also, Kotlin was inspired by Scala and according to many people, it looks a lot like Scala. The code uses the JVM and the Javac, the primary Java compiler. The learning curve for Java developers is not steep and they can leverage the Kotlin documentation and get started quickly with coding.

**Backward compatible**

Kotlin offers backward compatibility for Java versions 6 and 7. It states on its blog that over the next few releases, it is going to speed up development workflow, reduce the size of the runtime library and provide more tooling support. Kotlin introduced a feature known as incremental [compilation](https://jaxenter.com/kotlin-1-0-4-is-here-129315.html) in its Gradle build plug-in. This enables Kotlin to compile and build only the changes in the code and not the whole code over. This is going to save a lot of time.

**IDE interoperability**

Kotlin has a lot of supports for enhancing productivity. It is highly interoperable with the IDEs, so refactoring, debugging, searching, unit testing are very easy to perform.

Maybe a Java developer can convert part of his Java code into Kotlin and see how it performs. No matter what path you choose, Kotlin is definitely going to make a good impression on you.

## Conclusion

Kotlin already looks like a tool that is going to improve productivity significantly. Many developers are positive about Kotlin enabling them to achieve a breakthrough in their productivity.

There are many online discussions which claim that Kotlin solves many Java pain points without really reinventing how coding should be done. What are the challenges ahead? First, it needs to impress companies like Google, which is a huge Java user and has been working on other alternatives to Java.

It will be interesting to watch how Kotlin accomplishes its plans and how it faces forthcoming challenges.

"""

Article(
  title = "Why Kotlin is the next big thing for Java developers",
  url = "https://jaxenter.com/kotlin-next-big-thing-java-developers-129319.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nitin Y",
  date = LocalDate.of(2016, 9, 23),
  body = body
)
