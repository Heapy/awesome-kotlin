
import link.kotlin.scripts.Article
import link.kotlin.scripts.ArticleFeature.highlightjs
import link.kotlin.scripts.ArticleFeature.mathjax
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
In this post I show how to implement The DSL Way to manage 
Log4j configuration and extend an IDE without writing a plugin for it

The Problem
===========

[Log4j](http://logging.apache.org/log4j/1.2/) configuration can be 
either in `.xml` file or in `.properties` files. Both formats are not 
supported well in IDEs. 

I'll show how to create a decent IDE support for Log4j configuration files
*without* writing an IDE plugin at all. We illustrate 
how [The DSL Way](http://jonnyzzz.com/blog/2016/09/02/dsl-building/) is applied here.


The Basic Assumptions
=====================

I decided to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) as an IDE 
and [Kotlin](https://kotlinlang.org) as \\(Target Language \\).

Kotlin is a static typed opensource language by JetBrains. It's easy to learn
and use. For us it's vital that is has a static typed 
[DSLs](https://kotlinlang.org/docs/reference/type-safe-builders.html).


The Original Language
=====================

A configuration of a Log4j loggers looks like this:

``` 
 log4j.rootLogger=ERROR,stdout
 log4j.logger.corp.mega=INFO
 # meaningful comment goes here
 log4j.logger.corp.mega.itl.web.metrics=INFO
 log4j.appender.stdout=org.apache.log4j.ConsoleAppender
 log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
 log4j.appender.stdout.layout.ConversionPattern=%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n
````


A Transformation
================

Let's implement the following scheme for Log4j configurations in `.properties` file format.

![](http://i.imgur.com/3NyHYlD.png)

See [The DSL Way](http://jonnyzzz.com/blog/2016/09/02/dsl-building/) post for more details on the approach

The implementation of \\(generate\\) and \\(execute\\) transitions is an engineering task of average complexity.
Below I focus mostly on a creativity part -- on a build of a DSL API that provides good readability, refactoring 
and find-usages in an IDE

Building a DSLs
===============

Creating a DSL is a repeating process. You create a first version of it, check how it looks and how one 
can use it. Next some improvements are done. Next you repeat. At some point you have a nice solution. 

Building a DSL requires detailed knowledge of \\(Target Language\\), you should understand how 
to translate any strings into some valid expression in your language.
I would recommend checking the following articles on [Kotlin](https://kotlinlang.org) to learn 
more about how DSLs are created: 
- [Type-Safe builder in Kotlin](https://kotlinlang.org/docs/reference/type-safe-builders.html)
- [Delegated properties](https://kotlinlang.org/docs/reference/delegated-properties.html)
- [Operator overloading](https://kotlinlang.org/docs/reference/operator-overloading.html)
- [Kotlin Koans](https://kotlinlang.org/docs/tutorials/koans.html)


Step 0. A Straightforward DSL
=============================

We start with simplistic thing.  
As a starting point we need an entry function `log4j`, a builder interface `Log4J` with 
two methods `comment` and `param`. `Log4JBase` is added here for compatibility with future
code samples.

```kotlin` 
interface Log4JBase {
  fun comment(text: String)
  fun param(name: String, value: String)
}

interface Log4J: Log4JBase

fun log4j(builder: Log4J.() -> Unit) 

```

Please follow to [Kotlin](https://kotlinlang.org) documentation for better understanting 
of the code above. 

This allows us to \\(generate\\) the following Kotlin code for a Logger configurations

```kotlin
 log4j {
  param("log4j.rootLogger", "ERROR,stdout")
  param("log4j.logger.corp.mega", "INFO")
  comment("meaningful comment goes here")
  param("log4j.logger.corp.mega.itl.web.metrics", "INFO")
  param("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender")
  param("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout")
  param("log4j.appender.stdout.layout.ConversionPattern", "%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n")
 }
```

At that point we have a trivial DSL. Next we will be improving it. There is still no support 
for semantic checks or model. We now have all Kotlin language features opened for crafting 
a `.properties` file. The DSL Way handles `.properties` escaping allowing us to write strings as is.

Using Kotlin here creates a way to meta-extend the original format. We are able now to 
use functions, conditions, string manipulation, libraries and everything we have in Kotlin. 
All such tools are projected into the \\(Original Language\\), a `.properties` file. A \\(generator\\) 
can be smart to generate a compact code with use of Kotlin features. It may, for example,
fold duplicates into loops or function calls.

Let's make the DSL for Log4j configuration more expressive and readable

Step 1. Improving the DSL
=========================

There is a wellknown parameter `log4j.rootLogger`. IDE code completion is unaware about
a fancy property one should use. A user also may not know which is the right property. 
Finally, one may misprint the name of it. 
Let's replace it with an explicit call. For an 
[extension property](https://kotlinlang.org/docs/reference/extensions.html) in Kotlin is used
  
```kotlin
var Log4J.rootLogger : String
  set(value: String) = param("log4j.rootLogger", value)
  get() = throw Error("Read API is not implemented")
```

Now the improved part is 

```kotlin
log4j {
  rootLogger = "stdout"

  //instead of
  param("log4j.rootLogger", "stdout")
}
```

Step 2. Builders for Appenders
==============================

Let's take a look on the code 
```kotlin
  param("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender")
  param("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout")
  param("log4j.appender.stdout.layout.ConversionPattern", "%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n")
```

Log4j uses a key name encoding to achieve the goal. This requires one to re-type similar strings 
on and one. This may be a source of typos. From the other hand, this can be hard to read.
Let's avoid constant repeating strings and make those lines more expressive. For that we define
the following [extension methods](https://kotlinlang.org/docs/reference/extensions.html) in the 
\\(DSL Library\\).

```kotlin
fun Log4J.appender(name : String, type : String, builder : Log4JAppender.() -> Unit)
           
interface Log4JAppender : Log4JBase {
  fun layout(type: String, builder : Log4JLayout.() -> Unit)
}

interface Log4JLayout : Log4JBase 
```

And this allows us to tune the \\(generator\\) to have the following Kotlin code
```kotlin

//use this
appender("stdout", "org.apache.log4j.ConsoleAppender") {
  layout("org.apache.log4j.PatternLayout") {
    param("ConversionPattern", "%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n")
  }
}

//instead of
param("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender")
param("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout")
param("log4j.appender.stdout.layout.ConversionPattern",             
   
```

Step 3. Builder for Loggers
===========================

Let's simplify the rest of Log4j configuration code. Consider the following code 

```kotlin
rootLogger = "ERROR,stdout"
param("log4j.logger.corp.mega", "INFO")
param("log4j.additivity.corp.mega", "false")
```

Here we refer to a logger called `stdout` by typing it's name as a string. There are several keys used to encode
the logger. Let's normalize values and improve readability by spliting appender binging and level.

```kotlin
interface Log4JLogger : Log4JBase {
  var additivity : Boolean?
  var level : Log4JLevel?
  var appenders : List<String>
}

fun Log4J.logger(category: String, builder : Log4JLogger.() -> Unit)

fun Log4J.rootLogger(builder : Log4JLogger.() -> Unit)
```


Now the generated code would look like that
```kotlin
rootLogger {
  level = ERROR
  appenders += "stdout"
}

logger("corp.mega") {
  additivity = false
  level = INFO
}
```

Now one can specify parameters explicitly. And it reads way better.


Step 1 & 2 & 3. Outcome
=======================

At that point we managed to remove all common strings, encoded keys and values. Readability is now 
better as we replaced all bare `Log4J#param` calls with a dedicated API calls from a dedicated builders. 

There is a domain model created. We now have Logger, Appender, Layout entities. Each with a dedicated 
interfaces. Semantic checks are now implemented on compilation, meaning incorrect code would not compile at all. 
The rest of checks are implemented in the \\(emitter\\) implementation from the other.

Thanks to Kotlin static typed DSLs, IntelliJ IDEA understands code and provides code completion and navigation
for every expression.
 
The DSL code is more typo-resistant. All strings are now used once. There are no more tricky-encoded keys too. It' 
much harder now to author a misprint.

The generated DSL code is more expressive. One can read it and understand the meaning. There is no requirement 
to know Kotlin for that

Step 4. Find Usages and Rename for Appenders
============================================

Now we are ready to implement an IDE feature. We'd like to be able to rename appenders as well as be able to see
where a given appender is used. 

For every possible IDE feature we need for \\(Original Language\\). We need to find an equivalent construction 
in the \\(Target Language\\) and a similarly looking IDE feature for \\(Target Language\\). Next we shall
find the way to use such construction in the DSL. 

For appender usages and rename feature the Kotlin variable declaration suites the best.

We introduce `Log4JAppenderRef` interface. Make `Log4J#appender` function to return it. Next, in logger
configuration we replace the type of appender from `String` into `Log4JAppenderRef`.

Now appender usages are found via the respective variable usages. The appender name is specified only 
in `Log4JLogger#appender` function call. All other places uses the variable. Not it's safe to 
rename appender by changing this field.


Outcome
=======

This is a DSL for Log4j configurations usage example

```kotlin
log4j {
  val stdout = appender<ConsoleAppender>("stdout") {
    layout<PatternLayout> {
      conversionPattern = "%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n"
    }
  }

  rootLogger {
    level = ERROR
    appenders += stdout
  }

  logger("corp.mega.itl.web.metrics") {
    level = INFO
  }

  logger("corp.mega") {
    level = INFO
    appenders += stdout
  }
}
```

Creating a DSL is a iterative process. It is strongly dependent on subjective things like 'readability' 
or 'good looking'. Different DSLs are possible. And the way they are created depends on one's taste.

Conclusion
==========

By those steps we turned a `.properties` file of Log4j configuration into a well-looking DSL code in Kotlin. 
The DSL Way is implemented with that DSL and provides IDE support for authoring and reading Log4j configuration
files. 

The \\(generator\\) and \\(execution\\) parts implementation details are left uncovered. You may ask me
for details in the comments.

The DSL we created illustrates how once can turn a IDE language support problem 
into [The DSL Way](http://jonnyzzz.com/blog/2016/09/02/dsl-building/) approach.

You may have a look (or contribute) to the project sources on [my GitHub](https://github.com/jonnyzzz/Log4j2DSL)

You may follow to [this post](http://jonnyzzz.com/blog/2016/03/08/gradle-for-dsl/) for details on how to create 
a zero-configuration package for such DSLs and for The DSL Way approach.


"""

Article(
  title = "Crafting Log4j Configuration DSL",
  url = "http://jonnyzzz.com/blog/2016/09/09/log4j-dsl/",
  categories = listOf(
    "Kotlin",
    "DSL"
  ),
  type = article,
  lang = EN,
  author = "Eugene Petrenko",
  date = LocalDate.of(2016, 9, 9),
  body = body,
  features = listOf(mathjax, highlightjs)
)
