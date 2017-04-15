
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## Introduction

One of the most interesting pieces of news we’ve all seen recently is Google announcing [(limited) Java 8 support for Android N](http://developer.android.com/preview/j8-jack.html) with the Jack compiler for Java. Naturally, our users are curious about how these news affect them, and this blog post outlines our plans and our vision for Kotlin’s place in the Android development world in light of Google’s announcements.

### Kotlin’s Roadmap for Android

We pay a lot of attention to making Kotlin a good tool for Android development. In the next few releases (1.0.X) we will continue improving our toolchain and libraries. Our current efforts are focused on three main areas: speeding up the development workflow, reducing the size of the runtime library and providing more complete tooling support.

#### Development Workflow

To speed up the development workflow, **incremental compilation** is coming to Kotlin’s Gradle plugin. This will improve the build times considerably: when a source file is changed, we’ll only recompile this particular file and those files really depending on it, not the whole module.

The next thing we plan to do to improve Android build performance is providing an integration with Android’s new **[Jack and Jill toolchain](http://tools.android.com/tech-docs/jackandjill)**. Right now there are some issues that prevent Jack from handling Kotlin-generated bytecode correctly ([196084](https://code.google.com/p/android/issues/detail?id=196084) and [203531](https://code.google.com/p/android/issues/detail?id=203531)), but we plan to work together with the Google team to either resolve the issues or provide workarounds on our side. Once this is done, we’ll be able to translate only changed class files using Jill during incremental compilation, as opposed to translating all class files every time (which is the only possible behavior in the old Android tooling).

Last, but not least: **Instant Run**. Currently, Cold Swap works fine for Kotlin, but Warm and Hot Swap require some further investigation. We’ll do what we can to get them fixed ASAP. In the meantime, [JRebel for Android](https://zeroturnaround.com/software/jrebel-for-android/) works fine with Kotlin already.

#### Runtime Size

We are planning several improvements to **reduce the methods count** of kotlin-stdlib. Our current result is 7’191: ![http://www.methodscount.com/?lib=org.jetbrains.kotlin%3Akotlin-stdlib%3A1.0.0](https://img.shields.io/badge/Methods%20count-core:%206289%20|%20deps:%20902-e91e63.svg)

Optimizing top-level functions representation in multi-file facade classes and moving inline-only functions out of the runtime-available binary will win us several thousand methods.

#### Tooling Support

The main thing missing from Kotlin 1.0’s Android support story is **Lint Checks**, and we’re happy to announce that they are coming to Kotlin. We have already implemented all the checks available in Android Studio 1.5 (planning to publish them in Kotlin 1.0.2), and the new 2.0 checks are on the way. Our Lint checks are built on top of a common representation of Kotlin and Java code, and we plan to contribute that representation to the Android SDK, so that new checks added in future versions of the Android SDK will work with Kotlin out of the box.

More Android-specific IDE support, such as a _New Kotlin Activity_ action, code insight and navigation features, and others will also be added gradually as 1.0.X releases are published.

### Kotlin and Java 8

Java 8 has been around for quite a while now, and thus many of our users, those who are not doing Android development, have chosen Kotlin over Java 8 and are happy about it. Now that Android has official support for the Java 8 language features, how does that change the decision of choosing between Kotlin and Java?

First of all, while Java 8 does bring lambdas to Android, there are important differences between the way the Android toolchain supports lambdas on existing platform versions (that don’t run the N release) and the way Kotlin supports lambdas. To understand the difference, let’s see how the following simple code snippets are compiled:

Kotlin: `list.forEach { process(it) }`
Java 8: `list.forEach(it -> process(it))`

Java’s version is a tiny bit longer, but let’s not focus on that. Instead, let’s see what happens under the hood. Starting with Java:

* in Android’s Java 8 every lambda is compiled to a class, which has two methods: constructor and the actual body (affecting the method count of your application);
* this class is later instantiated at runtime, in many cases — every time the `forEach` is called (creating pressure on the garbage collector);
* and to access it, Java uses a polymorphic call to `Consumer.accept`, which may happen on every iteration in a tight loop (affecting performance, because the runtime cannot always inline such calls).

Kotlin, on the other hand, supports inline functions, and `forEach` is one such function. When you use an inline function with a lambda, both the body of the function and the lambda are inlined at the call site. As a result:

* the bytecode of the lambda is inserted directly into the bytecode of the calling method, so the method count does not increase;
* executing the code does not allocate any objects, so there is no garbage collector pressure;
* the resulting bytecode does not contain any polymorphic method calls, ensuring the best possible execution speed at runtime.

Bottomline: lambdas in Java 8 on Android are not at all free, and one should probably think twice every time and choose between good code and performance. In Kotlin, you don’t have to make such compromises, and you can use lambdas as much as you need to express the ideas in your code.

And of course, Kotlin has many other language features which are not available in any version of Java. Just to list a few:

*   Support for **null-safety** prevents most of the NullPointerException problems in your code, saving you from the dreaded “Unfortunately, application has stopped” message;
*   **Properties, primary constructors and data classes** greatly reduce the amount of boilerplate in the code representing the data model of your application:
    `data class User(val name: String, val groupId: String = "default")`
*   **Delegated properties** allow to extract the common logic in property getters and setters and to put that into a library:
    `val imageData by lazy { loadData(imageFile) }`

And the DSL construction features of Kotlin give you an entirely new level of flexibility in building your application which is simply not available in Java. For example, it gives you the option to replace XML layouts with an embedded DSL and to describe the UI of your application in the same language as the rest of the code, with full access to the abstraction features of the language. Here’s how this can be accomplished using the [Anko library](https://github.com/kotlin/anko):

```kotlin
verticalLayout {
    val name = editText()
    button("Say Hello") {
        onClick { toast("Hello, ${"$"}{name.text}!") }
    }
}
```

As you see, Kotlin has lots and lots of things to offer to make you more productive, beyond what Java 8 can offer to a Java 6 developer. And it’s also easy to learn, with [comprehensive documentation](https://kotlinlang.org/docs/reference/), [interactive exercises](http://blog.jetbrains.com/kotlin/2016/03/kotlin-educational-plugin/) and books covering both [Kotlin in general](https://www.manning.com/books/kotlin-in-action) and the use of [Kotlin for Android development](https://leanpub.com/kotlin-for-android-developers). So if you haven’t tried Kotlin yet, now is as good time as any!

"""

Article(
  title = "Kotlin’s Android Roadmap",
  url = "http://blog.jetbrains.com/kotlin/2016/03/kotlins-android-roadmap/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2016, 3, 30),
  body = body
)
