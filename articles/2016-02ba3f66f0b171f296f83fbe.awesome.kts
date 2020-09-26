
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
### Kotlin, the somewhat obscure modern Android-friendly programming language

[Swift](https://developer.apple.com/swift/) is a terse functional programming language with high Objective-C interoperability used for iOS development.

Swift is better than Objective-C.

You already know this.

But you probably don’t know what Kotlin is.

[Kotlin](https://kotlinlang.org/) is Swift for Android. [1]

You’re not using Objective-C anymore, so why are you using Java?

We built [Roll for Android](http://tryroll.com) in [Kotlin](https://kotlinlang.org/).

### What is Kotlin? Really.

Kotlin is a terse swift-like functional programming language on the JVM platform with high Java interoperability. Kotlin is unique in that its standard library and runtime are extremely small in bytes, yet very powerful. Most is eliminated at compile time (in contrast to [Scala](http://www.scala-lang.org/)), so it can feasibly be used in memory “constrained” environments like Android (not embedded systems, but smartphones):

```kotlin
val immutableVariable = "a string"

// a function
fun inc(x: Int): Int {
  return x+1
}

// anonymous function bound to a variable
val inc: (Int) -> Int = { it + 1 }
```

### Why Kotlin and not “X”?

We wanted to iterate on a lot of the software structure we came up with for our [Swift iOS app](http://tryroll.com), and for a bunch of pieces we needed a powerful type-system. So rather than just stick with Java — it’s way too verbose — we started looking at alternatives. We wanted a strong static type system, yet the obvious choice, Scala, has too much overhead for Android. Kotlin really stood out for us. [This document by Jake Wharton at Square](https://docs.google.com/document/d/1ReS3ep-hjxWA8kZi0YqDbEhCqTt29hG8P44aA9W0DM8/edit?hl=en&forcehl=1) made the decision easier. So we took the risk.

### Great Features

*   [Null type safety!](https://kotlinlang.org/docs/reference/null-safety.html)

In Kotlin, just like in Swift, optional values are tracked through the type-system. You will not have null pointer exceptions in your Kotlin code:

```kotlin
val x: String? = tryToGetString()
// x is not a String (it could be null)

if (x != null) {
  // x is smart-casted to a String here!
}
```

*   [Lambdas can be inlined!](https://kotlinlang.org/docs/reference/inline-functions.html)

An under appreciated feature of Kotlin (if you’re targeting Android for example) is the zero overhead higher order functions on collections.

In Kotlin these standard library functions are inlined and desugared to for-loops.

Thus, you can write expressive Scala-like code but without all the anonymous inner class overhead (which while negligible on servers, is [an issue in Dalvik](http://developer.android.com/training/articles/memory.html) and possibly Android’s new runtime ART [no evidence], so Android devices will be affected):

```kotlin
val strs = listOf("this", "is", "a", "bunch", "of", "words")

val charCount = strs.map{ it.length }.fold(0, { a, b -> a + b })

val evenWords = strs.filter{ it.length % 2 == 0 }
```

*   [Reified generics!](https://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters)

Reified generics in Kotlin let you use the generic T in your code at runtime. For example, we’ve used reified generics to implement a clean API for dictionary deserialization to model data classes. Here, we attempt to deserialize an untyped map into some strongly typed data class of type T (like a User):

```kotlin
private inline fun deserialize<reified T>(dict: Map<String, Any?>): T { ... }
// usage val model = deserialize<ModelData>(modelDataDict)
```

*   [Statically resolved extension functions!](https://kotlinlang.org/docs/reference/extensions.html)

Extension functions in Kotlin allow you to add “methods” to objects. These are resolved statically so there is no performance overhead or runtime confusion.

Extension functions even work on generic “primitives” like Functions or optional types.

For example, we have defined monadic bind on options (like if-let in Swift if it were an expression and not a statement) and use it all over our codebase [2]:

```kotlin
inline fun <T, R> T?.bind(transform: (T) -> R?): R? =
    this?.let(transform)
```

*   [Algebraic data types and pattern matching!](https://kotlinlang.org/docs/reference/classes.html#sealed-classes)

Algebraic data types or (ADTs) (also called Sum types or tagged unions) allow you model data that can be one of several different variants. Once you’ve used ADTs, you can’t live with out them. The compiler will enforce that you have exhaustively handled all the cases. Swift has them, and as of recently Kotlin does too.

Here is an example of the model for a button that is either disabled, shows a number, or shows info about a user:

```kotlin
sealed class ButtonState {
  object Disabled: ButtonState()
  class ShowingNum(val num: Int): ButtonState()
  class ShowingPerson(val u: User): ButtonState()
}

// this state shows 5
val showFiveState = ShowingNum(5)

// all states handled (enforced by the compiler!)
fun printbutton(state: ButtonState): String = when (state) {
  is ShowingNum -> "<{state.num}>"
  is ShowingPerson -> "<{state.u.name}>"
  Disabled -> "-<>-"
}
```

*   Single-method interfaces/classes can be represented as lambdas! aka

```java
view.setOnClickListener(new View.OnClickListener() {
  @Override public void onClick(View v) {
    /* do something */
  }
});
```

becomes

```kotlin
view.setOnClickListener { /* do something */ }
```

This is great for things like [RxJava](http://reactivex.io/).

Speaking of which, the Java interoperability is first-class and fantastic. Any Android Java libraries we’ve tried work great from Kotlin.

### Libraries Created

Using these features, we’ve built:

*   a simple dependency injection framework
*   a handful of really useful extensions on things like T? (for example monadic bind)
*   a hack for algebraic data types and pattern matching (before sealed class was released)
*   a single-atom-state functional reactive UI component framework
*   and of course a fairly complex app.

Android Studio’s Kotlin support is fantastic (good job JetBrains!) — it’s a pleasure to use.

### Build Time

The biggest issue for us is the build time. Gradle builds used to take around 5–10 minutes. We invested a week of engineering time in getting a [Buck](https://buckbuild.com/) build working alongside Gradle. Buck builds are 3 minutes max and usually are around 45 seconds. With the most recent Android Studio update, incremental Gradle builds are back down under a minute. This is not too much longer than a pure Java app of our size would take to build.

### Use Kotlin!

Kotlin is great! The time saved due to the benefits of Kotlin make up for any time lost optimizing build times.

If you’re interested in learning more, get in touch. We’re hiring!

Note: this post was adapted from an earlier [HackerNews comment](https://news.ycombinator.com/item?id=9947020)

[1] aside: It is more accurate to say Swift is the Kotlin of iOS since Kotlin has existed in the open for many more years than Swift. Due to Kotlin’s obscurity, however, we’re forced to make the Kotlin to Swift comparison.

[2] Thanks @[daniil_vodopian](https://twitter.com/daniil_vodopian) for the terser bind definition

"""

Article(
  title = "Kotlin, the somewhat obscure modern Android-friendly programming language",
  url = "https://medium.com/math-camp-engineering/kotlin-3e963864db9e#.jumx6jh63",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Brandon Kase",
  date = LocalDate.of(2016, 1, 15),
  body = body
)
