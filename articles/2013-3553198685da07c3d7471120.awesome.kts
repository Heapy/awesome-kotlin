
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
In this post we will focus on [Kotlin](http://kotlin.jetbrains.org/), with a simple [HTTP Server coding example](https://github.com/sjmaple/Kotlin-HTTP-Server) to showcase the language features and coding syntax.

[![](http://devnet.jetbrains.net/servlet/JiveServlet/showImage/2-5456865-25537/KotlinLogo.png)](http://devnet.jetbrains.net/servlet/JiveServlet/showImage/2-5456865-25537/KotlinLogo.png)

The Kotlin HTTP Server example we coded can be found on [github](https://github.com/sjmaple/Kotlin-HTTP-Server). Kotlin is a fairly young, statically-typed language, created by JetBrains and first announced at the JVM language summit in July 2011. The smart folks at JetBrains, creators of IntelliJ IDEA, have integrated many languages into their IDE and are pretty clued up on which requirements a modern-day developer is looking for, as well as the capabilities of the existing JVM languages.

The IDE support was the first ‘challenge’ I was faced with. As an Eclipse user, switching to the IntelliJ IDEA environment is always difficult, but it’s a necessary step if you want to code in a rich Kotlin environment. It’s fairly easy to install the Kotlin plugin, and create Kotlin artifacts, but just a shame the support isn’t also being pushed to other IDEs. Maybe we can subtly hint this to the JetBrains team? ;)

![kotlin-ide](http://zeroturnaround.com/wp-content/uploads/2013/01/kotlin-ide.png)

### Elegant coding

Coding in Kotlin really does provide some very elegant code. It removes the need for null checks, uses primary constructors, smart casts, range expressions... the list goes on. Let’s take a look at an example.

From my Java background I did like the combination of the `is`/`as` casting with the `when` structure. In Java terms, consider them as `instance of`, cast – `(A) obj` and `case` respectively. The `is` usage will also infer a cast if you go ahead and use the object straight away. E.g: `if (stream is Reader) stream.close()` In this example, the `stream.close()` is being called via the `Reader` interface. This would be the same as saying `if (stream is Reader) (stream as Reader).close()` but the extra code is not needed. This in combination with when allows you to switch over a variable, but not just using its value as you can get a richer involvement. Consider the following:

```kotlin
when (stream) {
    is Reader -> stream.close()
    is Writer -> stream.close()
    is InputStream -> stream.close()
    is OutputStream -> stream.close()
    is Socket -> stream.close()
    else -> System.err.println("Unable to close object: " + stream)
}
```

This is really clean, elegant code if you consider how you might want to implement this in Java. C# interestingly has a very similar usage of `is` and `as`, and also implements nullable types.

Method calls make use of parameter naming as well as defaults. It can be such a headache when a method takes in 5 booleans as you have to call the method very carefully so you pass `true` and `false` in the right order. Parameter naming gets round the confusion by qualifying the parameter name with the parameter itself on the method invocation. Nice. Again, this has been done in the past by other languages and scripting frameworks, but it’s a welcome addition to any language, particularly with parameter defaults, which allow the method invocation to omit certain parameters if the user is happy to accept default values. Lets take a look at an example:

```kotlin
private fun print(out : PrintWriter, pre : String, contentType: String = "text/html",
                  contentLength : Long = -1.toLong(), title : String, body : () -> Unit)
```

Here we have a method with several `String` parameters as well as a function as input. It is invoked using the following call. Notice that `contentType` and `contentLength` are both omitted from the invocation meaning the defaults in the declaration are used.

```kotlin
print(out = out,
      pre = "HTTP/1.0 404 Not Found",
      title = "File Not Found",
      body = {out.println("<H2>404 File Not Found: " + file.getPath() + "</H2>")})
```

So what does this mean? Well, there will be much less method overloading! Result! :D Seriously though, it’s amazing to think Java gone over 20 years without additions like this? Sometimes it does feel like you’re coding in the dark a little. Come on Java, catch up!

### Kotlin will help you write safe code, unless you don’t want to!

Let’s get geeky. Firstly you can say goodbye to NPEs! Kotlin uses ‘nullable types’ and ‘non-nullable types’ to differentiate between vars which could be null, and those which will never be null. Consider the following code:

```kotlin
var a : String = "a"
a = null // compilation error
```

To allow nulls, the var must be declared as nullable, in this case, written `String?`:

```kotlin
var b : String? = "b"
b = null // valid null assignment
```

Now, if you call a method on a, it’s guaranteed not to cause an NPE, so you can safely say

```kotlin
val l = a.length()
```

But if you want to call the same method on b, that would not be safe, and the compiler reports an error:

```kotlin
val l = b.length() // error: variable 'b' can be null
```

By knowing which vars can be null, the Kotlin compiler mandates that when you dereference a nullable type, you do so using one of the following methods:

Hmmm, I’m half way through writing this blog post and I’ve not mentioned anything that’s strikingly new or innovative... let’s continue and see how we go.

Safe calls in Kotlin are very similar to those in Groovy, including the notation. By dereferencing a nullable type using ‘`.?`‘ like in the example below, tells the compiler to call length on object b, unless it is null, in which case do nothing.

```kotlin
b?.length()
```

Those of you who are thinking ‘What? Get rid of null? Where’s the fun in that?”, there is the `!!` operator which allows for the potential of a NPE to be thrown, if you so wish.

You can also use the `?` notation with `as` to avoid exceptions being thrown if the cast is not possible. This is called a safe cast.

### Functions

Functions can be created inside (member functions) or outside of a class. A function can contain other functions (local functions) and you can use functions to extend existing classes such as the following:

```kotlin
fun Int.abs() : Int = if (this >= 0) this else -this
```

This example extends the `Int` class to return the absolute value it contains.

Functions are very powerful and are well used on the JVM in many languages (Still not in Java, until Java 8). Kotlin also allows the use of higher order functions which means you can pass a function as an argument to a method call (function literal).

Almost at the end now, and still nothing that I would call really stand out, knee smackingly amazing. Don’t misunderstand my point, I think Kotlin is a very nice language but so far it’s been getting that way by extracting pieces of existing languages and putting a new spin on it. Maybe this is to be expected given the exposure the JetBrains team have had to all the existing languages they support, and maybe this is what they intended to produce. Do we need extra languages to please every persons development style in the development community? Maybe... Maybe not.

### Documentation/Help

I found that the documentation for Kotlin was all in the same place. All Google searches led back to the [community site](http://confluence.jetbrains.net/display/Kotlin/Welcome). I think this is a shame, as it would be great to go somewhere else with other examples and resources with which to play around with, but I guess it’s still quite young. It would be nice to see more code in github and the like, for others to follow.

### Summary

Overall I really enjoyed using Kotlin. Nothing about Kotlin is particularly groundbreaking, but what makes it great is that it cherry picks some of the best parts of other languages.

**The parts I really liked about the language:**

*   The use of functions really gives the developer extra options in designing code.
*   Parameter names should be mandatory in every language as they’re so great! Kotlin uses them well with default values.
*   Extending classes is very easy and very useful.
*   The documentation is good and there are very interesting discussions in the comments.
*   It’s a pretty language! The keyword and fluff to useful code ratio is much better in Kotlin using constructs such as `when`, `is` and `as`
*   Kotlin is enjoyable to write and very easy to readand I would expect maintain, particularly because it’s a safe language.

**There were a couple of pain points though:**

*   The error flags in IntelliJ popping up all over the place if there is a single error, because of the knock on effect of the error. It can be hard to find the root cause.
*   I also had a few problems with try-finally for my stream closes, which I didn’t have time to resolve. When I added the close in the finally block, it seemed to use the result as part of the return by the look of the exception received:

    _Exception in thread “main” java.lang.VerifyError: (class: HttpServer, method: run signature: ()V) Register 9 contains wrong type
    at namespace.main(HTTPServer.kt:169)_

With milestone releases every 2-3 months, Kotlin may soon be a language which many Scala beginners may turn to, although I don’t think the happy Scala users will be too bothered about it. From what I’ve read online, some look down on it, but you’d expect that from opinionated techies wouldn’t you? :) (Not to divert you, but for more on Scala, feel free to check out our super popular [Scala report](http://zeroturnaround.com/labs/scala-2013-a-pragmatic-guide-to-scala-adoption-in-your-java-organization/), featuring an in-depth interview with Martin Odersky)

I’d like to look back in 6 months or a year to see how Kotlin has evolved, and with the JetBrains team driving it, we’re certain to be in for a fun ride. My initial impression is that Kotlin is going on a route quite similar to Scala, today. I wouldn’t expect anyone who is happy with Scala to migrate across to Kotlin, as the Kotlin folks have also mentioned, but for people who have been frustrated with Scala, it may be worth looking at some time in the future. I think it is sharing a busy market of developers with other languages that are too similar in features and syntax may not help them survive longer term. If you want to have a go yourself you can also play with [Kotlin in a browser](http://kotlin-demo.jetbrains.com). I’d love to hear your experiences in the comments!

**Don’t forget to check out the [HTTP Server sample Kotlin code on Github](https://github.com/sjmaple/Kotlin-HTTP-Server).**

_Psst! If you liked this post, [we wrote a 50-page RebelLabs report](http://zeroturnaround.com/rebellabs/devs/the-adventurous-developers-guide-to-jvm-languages/) on Java 8, Scala, Groovy, Fantom, Clojure, Ceylon, Kotlin & Xtend._

"""

Article(
  title = "The Adventurous Developer’s Guide to JVM languages – Kotlin",
  url = "http://zeroturnaround.com/rebellabs/the-adventurous-developers-guide-to-jvm-languages-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Simon Maple",
  date = LocalDate.of(2013, 1, 23),
  body = body
)
