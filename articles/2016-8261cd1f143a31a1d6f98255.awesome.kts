
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I've recently started reading ["Android Concurrency" by G. Blake Meike](https://www.goodreads.com/book/show/26260944-android-concurrency), and so far I can highly recommend this great book: it contains quite a lot of insight into how various Android concurrency mechanisms work, when to prefer one approach over the other and how to get the best of the tools at your disposal. I decided to follow along with the code examples and recreate them on my computer, and since I'm deeply in love with Kotlin, I thought it would be a great idea to translate the examples into Kotlin on the go. In one of the first chapters the author revisits concurrency basics in Java, and when I started rewriting the code examples in Kotlin, I was quite surprised to find out that:

* There's no `synchronized` keyword in Kotlin
* There's no `volatile` keyword in Kotlin
* Kotlin's `Any`, which is analogous to Java's `Object`, doesn't have `wait()`, `notify()` and `notifyAll()` methods

So how does concurrency work in Kotlin? This question has been asked on the [Kotlin forum](https://discuss.kotlinlang.org/t/concurrency-in-kotlin/858) and here's the answer from Andrey Breslav, Kotlin Project Lead:

> Kotlin deliberately has no constructs for concurrency built into the language. We believe this should be handled by libraries.

Although Kotlin doesn't have these things built into the language, it still provides quite a bunch of low-level concurrency tools. Let's take a look at what's in store.

### Creating Threads

There are two ways to create a thread in Java: extending the `Thread` class, or instantiating it and passing a `Runnable` through the constructor. Since you can easily use Java classes in Kotlin, both solutions work just fine. Here's how you'd subclass `Thread`:

```kotlin
object : Thread() {  
  override fun run() {
    println("running from Thread: ${"$"}{Thread.currentThread()}")
  }
}.start()
```

This code uses Kotlin's [`Object Expressions`](https://kotlinlang.org/docs/reference/object-declarations.html#object-expressions) to create an anonymous class and override the `run()` method. And here's how to pass a `Runnable` to a newly created instance of `Thread`:

```kotlin
Thread({  
  println("running from lambda: ${"$"}{Thread.currentThread()}")
}).start()
```

You don't see a `Runnable` here: in Kotlin it can easily be replaced with a [lambda expression](https://kotlinlang.org/docs/reference/lambdas.html#lambda-expressions-and-anonymous-functions). Is there a better way? Sure! Here's how you can instantiate and start a thread Kotlin-style:

```kotlin
thread(start = true) {  
  println("running from thread(): ${"$"}{Thread.currentThread()}")
}
```

Neat, isn't it? We're using the `thread()` function, which magically hides away all the boilerplate code. In fact, there's zero magic inside `thread()`:

```kotlin
public fun thread(start: Boolean = true, isDaemon: Boolean = false, contextClassLoader: ClassLoader? = null, name: String? = null, priority: Int = -1, block: () -> Unit): Thread {  
  val thread = object : Thread() {
    public override fun run() {
      block()
    }
  }
  if (isDaemon)
    thread.isDaemon = true
  if (priority > 0)
    thread.priority = priority
  if (name != null)
    thread.name = name
  if (contextClassLoader != null)
    thread.contextClassLoader = contextClassLoader
  if (start)
    thread.start()
  return thread
}
```

It's just a very convenient wrapper function that's a joy to use.

### Synchronized Methods and Blocks

`synchronized` is not a keyword in Kotlin, it's replaced with an [`@Synchronized`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-synchronized/) annotation. The declaration of a synchronized method in Kotlin will look like this:

```kotlin
@Synchronized fun synchronizedMethod() {
  println("inside a synchronized method: ${"$"}{Thread.currentThread()}")
}
```

The annotation has the same effect as Java's `synchronized`: it'll mark the JVM method as synchronized. For synchronized blocks you'll have to use the [`synchronized()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/synchronized.html) function, which takes a `lock` as the parameter:

```kotlin
fun methodWithSynchronizedBlock() {  
  println("outside of a synchronized block: ${"$"}{Thread.currentThread()}")
  synchronized(this) {
    println("inside a synchronized block: ${"$"}{Thread.currentThread()}")
  }
}
```

The code looks and behaves pretty similar to the Java variant.

### Volatile Fields

Same story, there's no `volatile` keyword in Kotlin, but there's the [`@Volatile`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-volatile/index.html) annotation:

```kotlin
@Volatile private var running = false

fun start() {  
  running = true
  thread(start = true) {
    while (running) {
      println("Still running: ${"$"}{Thread.currentThread()}")
    }
  }
}

fun stop() {  
  running = false
  println("Stopped: ${"$"}{Thread.currentThread()}")
}
```

The behavior is similar to `@Synchronized`: `@Volatile` will mark the JVM backing field as volatile.

### wait(), notify() and notifyAll()

Every class in Kotlin inherits from `Any`, but `Any` doesn't declare `wait()`, `notify()` and `notifyAll()`, meaning that these methods can't be called on a Kotlin class. But you still can use an instance of `java.lang.Object` as a lock and call the methods on it. Here's a solution to the Producer/Consumer problem that uses `Object` as the lock:

```kotlin
private val lock = java.lang.Object()

fun produce() = synchronized(lock) {  
  while (items >= maxItems) {
    lock.wait()
  }
  Thread.sleep(rand.nextInt(100).toLong())
  items++
  println("Produced, count is ${"$"}items: ${"$"}{Thread.currentThread()}")
  lock.notifyAll()
}

fun consume() = synchronized(lock) {  
  while (items <= 0) {
    lock.wait()
  }
  Thread.sleep(rand.nextInt(100).toLong())
  items--
  println("Consumed, count is ${"$"}items: ${"$"}{Thread.currentThread()}")
  lock.notifyAll()
}
```

Does it look hacky? Well, it is. Truth is that if you're relying on such low-level constructs in your code - most likely you're doing something wrong. Nowadays there's a ton of high-level concurrency mechanisms for every purpose in both Java and Kotlin. [Here's](http://stackoverflow.com/questions/35520583/why-there-are-no-constructs-for-concurrency-in-kotlin) a great Stackoverflow answer that provides a list of tools available for writing concurrent code in Kotlin.

All code samples from this article are available on [GitHub](https://github.com/Egorand/kotlin-concurrency-primitives).

### Conclusion

Although they're not used frequently, it's still important to know and understand the basic concurrency tools. Turns out those work a bit differently in Kotlin than in Java, but all major mechanisms are supported. And remember that Kotlin interacts really well with Java, so you can just rely on Java classes if the Kotlin counterparts are missing. Have fun!

If you've enjoyed this short article, please share it with your network using one of the buttons below. Thanks, and have a Merry Christmas!

"""

Article(
  title = "Concurrency Primitives in Kotlin",
  url = "https://blog.egorand.me/concurrency-primitives-in-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Egor Andreevici",
  date = LocalDate.of(2016, 12, 23),
  body = body
)
