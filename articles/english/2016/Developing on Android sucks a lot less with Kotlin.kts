
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![Kotlin](https://wiredcraft.com/images/posts/Android-app-development-Kotlin.jpg)

While Android development has improved tremendously in the past few years, many think that it still sucks when compared to iOS. A lot of folks are working hard to make it better, maybe even enjoyable. And [Kotlin](https://kotlinlang.org/), a new programming language, seems to be headed in that direction. Having been brought onboard by Starbucks in China to work on their mobile apps, we've been experimenting with Kotlin as a viable option to improving the UX of the Android app and making it overall more maintainable.

## What is Kotlin?

[Kotlin](https://kotlinlang.org/) is a new language from the guys at JetBrain, the team behind Intellij, Android Studio, and some of the best IDE in the industry. Essentially, Kotlin is an evolution of the Java syntax. Its source code can be compiled to JVM bytecode and it has 100% interoperability with Java. JetBrain even recently added the ability to compile Kotlin code to JavaScript, potentially broadening its reach.

## Who should use Kotlin?

So, who is Kotlin for? Newbies or experts?

I would argue both.

Newbies can enjoy a cleaner and more concise syntax and avoid a lot of beginner's mistakes people usually run into with Java. Experts will also most likely enjoy the cleaner syntax, and most likely find themselves more productive for it.

## What makes Kotlin different?

If you hate Java, Kotlin may be your solution. In many ways, it "fixes" Java, especially for Android. While the next major release of Java (8) looks pretty promising and brings a lot of modern language features (e.g. lambda and functional programming), there's no official schedule yet for when it will be made available for the Android platform. Kotlin is available right now.

Let's have a what it brings:

### 1. Cleaner syntax

Here's a "Hello world" in Kotlin:

```kotlin
fun main(args: Array<String>) {
  println("Hello, World!")
}
```

Compared to Java:

```java
public class HelloWorld {
  public static void main(String[] args) {
  //Prints "Hello, World" to the terminal window.System.out.println("Hello, World");
  }
}
```

I think you'll agree that Kotlin is much cleaner and way more concise.

### 2. Null safety

How many times did you run into a `Null Pointer Exception` while developing or testing? Kotlin takes all `null` value checks from runtime to compile time. By doing so, it ensures null safety for any code that passes compilation.

For example, the Java code below can be compiled, but will throw a `Null Pointer Exception` at runtime.

```java
String a  = null;
System.out.println(a.length());
```

With Kotlin, variables are not nullable by default. For example, this piece of Kotlin code won't compile:

```kotlin
val a:String = null
```

But if you really want to allow some variable to have null value, you can add a question mark:

```kotlin
val a: String? = null
println(a?.length())
```

In the example above, the first `?` is used to make the variable `a` nullable, the second `?` is just to check if the value of `a` is null.

### 3. Functional programming

One of the most important changes in Java 8 is lambda. While we're waiting for this to come to the Android platform, Kotlin can already add a lot of high-order functional programming features on top of Java 6.

By "high-order," I mean a function that can take another function as a parameter or return a function. For example, many programming languages have the `filter` function, which can take a Collection-like data structure and a function to filter the elements in the Collection that doesn't meet certain criteria. Then return a subset of the original Collection.

This is how you might implement a `filter` function in Kotlin:

```kotlin
fun <T> filter(items: Collection<T>, f: (T) -> Boolean): List<T> {
  val newItems = arrayListOf<T>()
  for (item in items) if (f(item)) newItems.add(item)
  return newItems
}
```

Then you can use it on a Collection data structure:

```kotlin
filter(numbers, { value ->
  value >= 5
})
```

Pretty clean implementation right? Wait, it can be even cleaner by using the special `it` keyword that represents each item inside the collection:

```kotlin
filter(numbers) { it >= 5 }
```

## Minimum overhead

Beyond adding features, the Kotlin language is actually pretty small. For the latest version to this day (`1.0.0-beta-4584`), runtime and standard library are a few kilobytes in size. It won't add much weight to your project.

The features we mentioned previously are just a few things that Kotlin brings to the table. For more, I suggest checking out the [official reference page](https://kotlinlang.org/docs/reference/).

## Using Kotlin on Android Studio

The author of Kotlin is also the author of some of the most popular IDE out there. There are official plugins for Eclipse, IntelliJ IDEA, and standalone command line compiler as well. Since we're focusing on Android, here are a few tips for Android Studio:

1. Download and install Intellij IDEA/Android Studio plugin. You can install it through the IDE preference panel or download if from JetBrain site.

![android-studio-Kotlin-development](https://wiredcraft.com/images/posts/Kotlin-android-app-development-2.png)

2. Once you have the plugin installed, you can start using it. If you're trying to convert an existing Java project, simply click `Code → Convert Java File to Kotlin File` in the menu bar. Kotlin will convert the source Java code to Kotlin code. Alternatively, if you're starting a project from scratch, you can create a new Java project and then convert all `.java` files into `.kt` files. Not the best, but hopefully Google and JetBrain will learn to play nice with each other in the future.

![android-studio-Kotlin-development](https://wiredcraft.com/images/posts/Kotlin-android-app-development-3.png)

That's it!

## Conclusion

Kotlin probably isn't as hot in the Android community as Swift is for the iOS world, but the number of libraries is growing. Being interoperable with Java 6 and backed up by JetBrain should put you at ease with investing in it; it will be around for a long time.

Kotlin fixed a lot of the issues we had with developing for Android. There's still room for improvement, but I encourage all of you out there who shared the frustrations of building Android apps to give it a try on your next project.

And finally, if you're looking for a rock-solid team of nerds and creatives in NYC, Berlin, or Shanghai to work on your next Android or iOS project, [reach out on Twitter](http://twitter.com/wiredcraft) or shoot us an email either [on our site](https://wiredcraft.typeform.com/to/GG4GQz) or at [info@wiredcraft.com](mailto:info@wiredcraft.com).
"""

Article(
  title = "Developing on Android sucks a lot less with Kotlin",
  url = "https://wiredcraft.com/blog/android-apps-development-kotlin/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Kuno Qing",
  date = LocalDate.of(2016, 3, 1),
  body = body
)
