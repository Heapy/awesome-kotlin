
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

Andrey Breslav, the Kotlin project lead, gives an overview of what Kotlin is today, as a tool for developers and as an ecosystem. He then takes a peek into the near future, to their next release.

See the discussion on [Hacker News](http://news.ycombinator.com/item?id=11814488).


Transcription below provided by Realm: a replacement for SQLite that you can use in Java or Kotlin. [Check out the docs!](https://realm.io/docs/java/latest/)

<iframe width="960" height="480" src="https://www.youtube.com/embed/LtfxNPOImyg" style="border-radius: 5px;" frameborder="0" allowfullscreen></iframe>

<iframe width="960" height="480" src="//speakerdeck.com/player/f1dea41f659a4c70a6e8fb20291b871b?" allowfullscreen="true" mozallowfullscreen="true" webkitallowfullscreen="true" style="border-radius: 5px;" frameborder="0"></iframe>

Andrey Breslav, the lead language designer for Kotlin, began his career at Borland, where he worked on language implementations for MDA support. After spending a few years as a college teacher, he joined JetBrains in 2010 to lead Project Kotlin and currently serves as a member of the Java Community Process Expert Group for JSR 335, “Project Lambda.” He is a frequent conference speaker at venues such as OSCON, JavaOne, Strange Loop, and Devoxx.


[@abreslav](https://twitter.com/abreslav)

### Introduction 00:00

My name is Andrey, I lead the [Kotlin](http://kotlinlang.org) team at [JetBrains](https://www.jetbrains.com). I am here to tell you about where we stand and about what is next.

### Learning Materials 00:30

This talk is not an introduction to Kotlin. If you want to learn Kotlin, there are many sources to learn from. There is [language reference, tutorials, talk videos](http://kotlinlang.org). Check out our [mini-IDE](http://try.kotlinlang.org), Kotlin Koans is a set of problems that walks you through the language, similar to an IDE with a educational plug-in. _I am here to motivate you to learn!_

### Where We Stand 01:03

Kotlin 1.0 released in February 2016. Since then we were doing incremental updates: 1.0.2 is our current version, and we are working on 1.1 (a feature driven release, although I am not giving you a date yet). The Early Access Program (EAP) will start this summer.

In this slide (_see video_) we have the people that have used Kotlin (_maybe not admitting it publicly!_). JetBrains is a big user, we were probably the first to start trying it in production. We have over half a million lines of code and production in different projects, from IntelliJ production of our main IDE to server side products (...to everything). A few projects are written in Kotlin from scratch. Others are catching up. We are grateful to everyone trying Kotlin in production. It is such an early stage of the release time! Join in, you will be in a good company.

And being in a good company, you will not be alone. We have a lively community, Slack, forums, StackOverflow, full of people who are happy to tell you what you are doing wrong, and how to fix whatever issues you have. Come over to our GitHub Project, it is open source, you can join (we already have over 100 OSS contributors!). If you are stuck, we will work as hard as we can to be responsive. If you have a critical issue, we will role it out as an EAP build, which you can use to work on the problem.

### KEEP 03:14

Kotlin Evolution and Enhancement Process (KEEP): we try to accumulate all the design process we are having, and be as open as possible. If you go to this [GitHub repo](https://github.com/Kotlin/KEEP) you will see all the proposals that are considered for Kotlin 1.1 at the moment. You can provide your ideas, feedback, do corrections. We are very grateful: use cases are what drive our design. KEEP is the venue for our plans.

### Our Plans - Lines of Development 03:56

Now we have 1.0, 1.0.2, as the main release, and our development is in two lines. We have those incremental updates, 1.0.2, 1.0.3, which are source compatible (meaning that language does not change more than the bugs). We fix bugs in the compiler; this sometimes slightly changes the language (but that is always for the better). There you find bugfixes, performance updates, and tooling features. This is where IDEs are improved: build systems.

1.1 is another parallel line, where we do language features, and their source compatibility is backward compatibility. 1.0 may not be able to compile 1.1 (because there are new features 1.0 does not know about). Backwards is always compatible. If you have your old code, it will work with 1.1.

_This slide does not mention anything about binary compatibility, because that goes without saying._

### Tooling Plans: 1.0.X 05:18

*   We released 1.0.2, which has incremental compilation for Gradle (_and speeds things up quite a bit_). Now, when you change something you are calling code, and recompile it with Gradle, with the incremental flag enabled (it is an experimental flag, you do not have it on by default), you only have those files recompiled that actually need recompilation. The whole project is not rebuilt, only those individual files touched or that were affected by your changes. And we will be working on this feature; after some time it will be not experimental anymore (_hopefully!_). That is one big infrastructural change.

*   Spring Support in the IDE. You have some of the features that the big IntelliJ idea has for Java, but in caudlen.

*   Reduce our standard library size. That is not because our standard library is big (it is probably the smallest among the alternative jvm languages). Last time we shaved off some 1500 methods off the library binary, without changing the API. It is a compatible change, but there are fewer functions now. If you are an Android developer, it matters to you.

*   Android Lint Checks (_people were asking for those_). The IDE warns you if you do something wrong in Android specific terms (not language specific terms). We will have more and more Lint Checks added over time.

*   Jack&Jill, the new Android tool chain we are integrating with. We are fixing bugs to be able to Jill compile the Kotlin center library (because, without the bug fixes, it would not).

### New Targets 07:49

And for 1.1... we are planning many things!

1.  **Java 8/9**. Our current target is Java 6: whatever you compile in Kotlin can run on anything that runs Java 6, including Java 8/9 (but it does not use the features of 8/9). We are working on generating default methods for Java 8. In Kotlin, you can always implement methods and interfaces, but if you want to extend such an interface in Java, Java 6 would not know that those methods are implemented (Java 8 would). We also have some issues with Stream API collection in Java 8. We have a support library for Java 8 streams, but that will be removed in Kotlin 1.1 and we will just support natively in the compiler.

2.  The JavaScript backend for Kotlin has been around for years, but we de-prioritized it to shape 1.0 for Java as soon as we could. We are working on it again, and all the language features are covered. We are working on runtime module support (amd, umd, common.js). JavaScript has many common toolchains (npm, browserify, gulp, ...), and we will try to support as many of them as we can.

3.  Another popular question, can I run Kotlin in a native environment without a virtual machine? The current answer is no; at least, not easily. No code is written in this section yet, and it is probably not going to be available in 1.1, but we are looking into this and we will probably have some news sometime later.

### Scripting: example.kts 10:08

One thing that is needed from any modern language is to be a scripting language. This is more or less supported, although it has not been officially promoted that much. You can write a script in Kotlin, the file extension would be .kts. For now, you can import anything and write the expressions and statements on the top level of the file. It will be compiled to a Java class and run as normal.

However, you cannot define dependencies inside a script, which means you have to either configure your class path, or be stuck with the jtk and the Kotlin standard library. This is something we are working on now, and it will probably help many other usages of Kotlin. This is an infrastructural change.

#### Type Aliases 11:04

When you want to pass a function (say, a foo and bar to buzz), you end up repeating this function signature many times. That is why people want some abbreviation mechanism, to give a name to this complex type.

```kotlin
typealias Int32 = Int
typealias Predicate<T> = (T) -> Boolean
```

Here my complex type is T to Boolean, a function, and I want a name for it. I say `typealias Predicate<T> = (T) -> Boolean`. This is not a new type, this is just an abbreviation; an alias. `Int32` here is just an `Int`. These are interchangeable, but they will help you abbreviate your long types and make your APIs more speaking. The new type is something many people want. It is not something a type alias is. It will be covered later, probably in 1.1, with value types (or something related to those). You have probably heard about Project Valhalla (those of you interested in Java), it is easier.

### Bound Method References 12:35

You probably know that Kotlin has reflection (on top of whatever platform reflection there is). There is language support for reflection.

```kotlin
val p: Predicate<String> = “foo”::equals
    // behaves as { x -> “foo”.equals(x) }

val c: KClass<Foo> = x.foo()::class
```

Those two colons (type ::foo) is something that is supported now. You can take a type, and take a reference to it is member, a function or a property. Or, you can take a type and make it a class literal by saying Type::class. This is something you can do now, but in 1.1 you will be able to do that with an object. It can take a foo (“foo” is a string, obviously), and it can take an `equals` of that string, which is a function of one argument, and put to a variable as a predicate. It will be comparing any string you pass to it to foo. This is a partial application, but a limited one. Convenient, according to our use-case surveys.

More or less works, like lamda over there. Same thing you will be able to do with a class; you can take a class of x to foo by saying ::class.

### Enhancements for Properties 13:54

If you do not know what delegated properties are... Google it, because they are cool. _(Ed. note: [Here you go!](https://kotlinlang.org/docs/reference/delegated-properties.html))_ This is something that allows good re-using code, Kotlin. Good news: in 1.1 we will allow that as a local variable. Now you can use **delegated properties** on a top level or in a class; you will be able to do it in a function. This enables DSLs, and this will help us with new cases.

```kotlin
fun foo() {
    val lazyBar by lazy { ... }
    while (...) {
        if (...) {
            lazyBar.doBaz()
    ...
}
```

Inline properties: technical thing for some libraries; for binary compatibility, it is better to have properties inlineable.

```kotlin
val foo: Foo
    inline get() = ...
    inline set(v) { ... }
```

### Hierarchies for Data Classes 14:53

(_If you have not heard of data classes, learn about them_). _(Ed. note: [Here you go!](https://kotlinlang.org/docs/reference/data-classes.html))_

```kotlin
data class User(val name: String, val age: Int)

// automatically gets
equals() / hashCode() / toString()
copy() // val newUser = someUser.copy(name = “Jane Doe”)
componentN() // val (name, age) = someUser
```


Kotlin has this simple but useful feature, where it can define a class with two members. It is a `class User`, which has a name and an age (these are fields). It can mark it data, and the compiler can generate all this for you. It will give you equals() and hashCode() based on those values, toString(), rendering those. A copy() method to change a name of an object in a shallow copy of it. And componentN() functions for destructuring. It can say: I have a user (a user is a pair of name and age), I declare a local variable, name and age at the same time and assign the user to two of them. I have two values at the same time assigned to those names.

Data classes are good, but we did not support any inheritance for those. 1.1 will fix that; you will be able to say, have a sealed class, and be able to extend a data class from it.

```kotlin
sealed class C() {
    data class Example(...) : C()
}
```

This covers everything people need about algebraic data types in Kotlin. It is not Haskell yet (it will never be), but it is as close to Haskell as use cases we see require.

### Destructuring in Lambdas 16:22

```kotlin
myMap.forEach {
    (k, v) ->
    println(“${"$"}k => ${"$"}v”)
}
```

A map from N to string is a sequence of pairs, map entries of keys and values. In the library, Map has a .forEach method. That simply iterates through the pairs, and you can do whatever with them. In Kotlin 1.0, you only have one variable for the whole pair. You can say, forEach has an entry, and it can say `(k, v) -> println(“${"$"}k => ${"$"}v”)`. And this new syndex in 1.1 will help you destructure right away. If you say K and V is a pair (which means that the entry is structured in the two), and then you can print the two. _Have I lost all of my audience by now?_

I have a black screen for you (_see video_). You are not supposed to read the code. All you care about is this staircase of brackets. This is the picture I Googled by the query ‘callback hell’. This picture is a typical piece of JavaScript (not only JavaScript, other languages suffer from this as well). This illustrates the world of asyncronist computation as it is, more or less, today in many languages.

We want to be asynchronist as much as we can, because we like to offload things on different threads or other different ways of execution. The way I want to read from a file is: “Here is my file, I want to read from it. When the read is done, here is my callback, call it when it is finished”. That is great; I can call something, I am not blocking. I can continue working; the read will do its work. But, if after that read I want to write, and then another read, each next asynchronist call has to be nested inside that callback. It is a callback in a callback in a callback in a callback... and at the end I have this staircase. It is okay if it fits in 115 lines, but sometimes it does not. And this is why some languages have async/await.

### Asynchronous Computation 19:10

Some languages want to write the same asyncronist code that does not block on many calls, but in a sequential way.

```kotlin
fun loadImage(url: URL) = async {
    val bytes = await(loadBytes(url))
    bytesToImage(bytes)
}
```

I just say loadBytes and then bytesToImage, meaning that loadBytes should work asynchronistically and execute bytes image only when it is done. There is this `await` keyword, that means call load bytes, put the rest of the computation away somewhere, do not block the thread, give the thread up to somebody else who can use it. And, when the loadBytes is done, resurrect the whole computation and run the next line.

This is what C# introduced in version five. Many other languages adopted this idea because it is fruitful, and this is something we want to cover. But we do not want to copy it from others because it is not general enough, in our opinion. They have a asynchronist computation, a sync block, and a suspension point. Await means we can suspend here and then resurrect afterwards.

#### Coroutines 20:24

Alternatively, we want to do coroutines. It is a generalization. We want to cover async/await/yield on generator blocks from other languages, but with maximum flexibility. We want to support virtually all existing asynchronous APIs, futures, callbacks, promises. And this means that the language is not bound to a given task framework.

```kotlin
fun loadImage(url: URL) = async {
    val bytes = await(loadBytes(url))
    bytesToImage(bytes)
}
```


We have these `async` and `await`: these are library functions, not keywords. It is the same machinery with a different API, and much flexibility. The prototype for this is under way (maybe available in June), and we will be able to play with library making and library using, using this abstraction.

### Conclusion 21:25

I invite you to come to KEEP, and review the proposals. Give us your feedback, ideas, use cases.

Kotlin has been known for making nice APIs, and some people like making APIs so much that I have the honor to invite Hans Docter on stage, because he has [something to show you](https://realm.io/news/gradle-kotlin/).

See the discussion on [Hacker News](http://news.ycombinator.com/item?id=11814488).

"""

Article(
  title = "Where We Stand & What's Next for Kotlin",
  url = "https://realm.io/news/andrey-breslav-whats-next-for-kotlin-roadmap/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2016, 6, 1),
  body = body
)
