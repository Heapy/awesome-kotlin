
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
This has caught me by surprise. After studying the [Kotlin language](https://kotlinlang.org/) to learn about how to best leverage this interesting new language for [jOOQ](http://www.jooq.org/), I stumbled upon this puzzler. What do you think the following program will print?

```kotlin
fun main(args: Array) {
    (1..5).forEach {
        if (it == 3)
            return
        print(it)
    }

    print("done")
}
```

Well... You might have guessed wrong. The above will print:

```
12
```

It will NOT print what most people might expect:

```
1245done
```

**Note to those of you who are not surprised**:

The above is peculiar for someone used to working with Java 8, where the following code will indeed print 1245done:

```java
public static void main(String[] args) {
    IntStream.rangeClosed(1, 5).forEach(it -> {
        if (it == 3)
            return;

        System.out.print(it);
    });

    System.out.print("done");
}
```

The syntactical reason is explained in this section of the Kotlin manual:
[https://kotlinlang.org/docs/reference/returns.html](https://kotlinlang.org/docs/reference/returns.html)

In lambdas / closures, the return statement will not (necessarily) return from the lambda / closure, but from the immediate enclosing scope of the lambda / closure. The rationale has been kindly given to me by Dmitry Jemerov from JetBrains in two tweets:

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr"><a href="https://twitter.com/lukaseder">@lukaseder</a> <a href="https://twitter.com/kotlin">@kotlin</a> reason is very simple: we want to have lambdas that work exactly like built-in language features (e.g. synchronised)</p>&mdash; Dmitry Jemerov (@intelliyole) <a href="https://twitter.com/intelliyole/status/701729566453321728">February 22, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr"><a href="https://twitter.com/lukaseder">@lukaseder</a> therefore ‘return’ in a lambda passed to ‘synchronised’ function must do the same as a ‘return’ in a Java ‘synchronised’ block</p>&mdash; Dmitry Jemerov (@intelliyole) <a href="https://twitter.com/intelliyole/status/701729699198799874">February 22, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

Cunningly, the Kotlin language has removed language-based support for Java constructs like try-with-resources, or the synchronized statement. That’s very reasonable, because these language constructs don’t necessarily belong in the language ([as we’ve previously claimed in another blog post](http://blog.jooq.org/2016/01/12/if-java-were-designed-today-the-synchronizable-interface/)), but could be moved to libraries instead. For example:

```kotlin
// try-with-resources is emulated using an
// extension function "use"
OutputStreamWriter(r.getOutputStream()).use {
    it.write('a')
}
```

(criticism here)

Or:

```kotlin
// Synchronized is a function!
val x = synchronized (lock, { computation() })
```

See also:
[https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/synchronized.html](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/synchronized.html)

After all, even in Java, the language feature only works because the language depends on library types, like Iterable (foreach), AutoCloseable (try-with-resources), or JVM features (monitor on each reference for synchronized)

## So, what’s the deal with return?

Along the lines of the above rationale, when language designers want to avoid language constructs for things that can be implemented with libraries, but still want you to feel like these were language constructs, then the only reasonable meaning of return inside of such a “construct-ish” lambda / closure is to return from the outer scope. So, when you write something like:

```kotlin
fun main(args : Array) {
    val lock = Object()
    val x = synchronized(lock, {
        if (1 == 1)
            return

        "1"
    })

    print(x)
}
```

The real intention is for this to be the equivalent of the following Java code:

```java
public static void main(String[] args) {
    Object lock = new Object();
    String x;

    synchronized (lock) {
        if (1 == 1)
            return;

        x = "1";
    }

    System.out.println(x);
}
```

In the Java case, obviously, the return statement exits the main() method, because there is no other reasonable stack frame to return from. Unlike in Kotlin, where one might argue the lambda / closure would produce its own stack frame.

But it really doesn’t. The reason for this is the inline modifier on the synchronized function:

```kotlin
public inline fun <R> synchronized(lock: Any, block: () -> R): R {
    monitorEnter(lock)
    try {
        return block()
    }
    finally {
        monitorExit(lock)
    }
}
```

See also:
[https://kotlinlang.org/docs/reference/inline-functions.html](https://kotlinlang.org/docs/reference/inline-functions.html)

Which means that the block closure passed as an argument isn’t really a pure lambda expression, but just syntactic sugar embedded in the call-site’s scope.

Weird. Cunning. Clever. But a bit unexpected.

Is this a good idea? Or will the language designers regret this, later on? Are all lambdas / closures potentially “language construct-ish”, where such a return statement is expected to leave the outer scope? Or are there clear cases where this inline behaviour just makes total sense?

We’ll see. In any case, it is very interesting for a language to have chosen this path.

"""

Article(
  title = "A Very Peculiar, but Possibly Cunning Kotlin Language Feature.",
  url = "http://blog.jooq.org/2016/02/22/a-very-peculiar-but-possibly-cunning-kotlin-language-feature/",
  categories = listOf(
    "Kotlin",
    "Puzzlers"
  ),
  type = article,
  lang = EN,
  author = "Lukas Eder",
  date = LocalDate.of(2016, 2, 22),
  body = body
)
