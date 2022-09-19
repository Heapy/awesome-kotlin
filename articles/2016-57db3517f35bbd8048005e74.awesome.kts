
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
# Logging in Kotlin & Android: AnkoLogger vs kotlin-logging

In the [previous post](https://medium.com/@OhadShai/logging-in-kotlin-95a4e76388f2) _kotlin-logging_ framework was introduced. In this post I am going to compare it with _anko_ in the aspect of logging in Android.

[Anko](https://github.com/Kotlin/anko) is probably one of the most popular libraries in Kotlin today. It was written by JetBrains as a DSL (Domain Specific Language) for view editing instead of the plain old xml used in Android-Java.

Along with that _anko_ provide other helpers for android development, among them is the [_AnkoLogger_](https://github.com/Kotlin/anko/blob/d40dfa016a9cf74286127de16273a131e48348bd/doc/ADVANCED.md#logging) for logging. I am going to observe this specific aspect and compare it to using [_kotlin-logging_](https://github.com/MicroUtils/kotlin-logging) for logging in android.

### Installation simplicity

If you are a user of anko, then using _AnkoLogger_ has no additional installation. Super easy. If you are not using anko, then it is an overkill to install it just for logging. kotlin-logging is pretty simple to install with gradle, and there is an example [in here](https://github.com/MicroUtils/kotlin-logging-example-android).

### Package size

For anko this is not relevant since the AnkoLogger is just one class among many. kotlin-logging jar is about 10kb but requires additional dependencies of ~50kb (slf4j+android bridge). Pretty light-weight.

### Interface

In kotlin-logging the recommended interface is like that:

```kotlin
class FooWithLogging {  
    companion object: KLogging()  
    fun bar() {  
        logger.info { "twinkle twinkle ${"$"}little star" }
    }  
}
```

The logger is defined as a parent for the companion object. The companion object then has a property called _logger._ In the bytecode, that translates into a static member.

The logger in turn is an extension of slf4j Logger called KLogger. Hence original methods are available as well as lazy evaluation extensions (with curly braces).

In AnkoLogger the recommended interface is like that:

```kotlin
class SomeActivity : Activity(), AnkoLogger {  
    private fun someMethod() {  
        info("London is the capital of Great Britain")  
        debug(5) // .toString() method will be executed  
        warn(null) // "null" will be printed  
    }  
}
```

The logger is defined in an interface called _AnkoLogger_ that your class is extending. You don’t access it directly, instead just call the log methods by the level. Again, a lazy flavors exists for different log levels. AnkoLogger also “pollutes” your class with a public property called _loggerTag_, and all logging methods are public. Of course this can be fixed with a companion object.

### Logger name

Looks like a small detail but still...

In kotlin-logging the name of the logger is inferred from the class and package name. In cases the name is too long it is truncated and replaced with stars. more details in [Logger name mapping](http://www.slf4j.org/android). In AnkoLogger, the name of the logger is the class name only without package name. Again, if it is long it is getting truncated.

One notable different is that AnkoLogger doesn’t handle inheritance, so in case of inheritance the logger name is of the child class even when the log message was issued in the parent class. A little bit confusing :-(

### Performance and resource consumption

First I will start with a note about premature optimization: performance measurements can be very misleading and differ from one use case to another.

Under the hood both frameworks use the native logging of android so only minor differences should be expected.

The main difference in the design is that kotlin-logging has a companion (static) member of the logger as opposed to AnkoLogger that has a property for each object instance.

From my small tests kotlin-logging has slightly better speed, but I did not see any absolute advantage hence you should check that for your use case if it is required.

### Conclusion

I compared AnkoLogger with kotlin-logging for Android developers, that writes applications in Kotlin.

In case you would like to play with it a bit, I created a project with both frameworks in github: [https://github.com/MicroUtils/anko-vs-kotlin-logging](https://github.com/MicroUtils/anko-vs-kotlin-logging)

For me the bottom line is that if you use anko in your application then probably AnkoLogger is the solution for you, otherwise I think [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) is easier. The rest is a matter of style.

As always, comments are welcome.

"""

Article(
  title = "Logging in Kotlin & Android: AnkoLogger vs kotlin-logging",
  url = "https://medium.com/@OhadShai/logging-in-android-ankologger-vs-kotlin-logging-bb693671442a#.pi8n1ojoh",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "osha1",
  date = LocalDate.of(2016, 8, 14),
  body = body
)
