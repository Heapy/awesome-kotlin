
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
#### Functional programming in Kotlin, a new language from JetBrains

I did [a video tutorial on FP in Kotlin](http://blog.jetbrains.com/kotlin/2015/11/webinar-recording-functional-programming-with-kotlin/), which can be viewed online.

If you use .NET you probably heard about F#, a multi-paradigm but essentially functional programming language for the CLR. And you probably heard good things about it. You may also have heard of Haskell, which is similar.

Perhaps you would like languages such as these on the JVM, but with great tool support and without _mandating_ a functional style ... just making it available when you want it.

[**The Kotlin language**](http://kotlinlang.org/) may at first glance appear to be nothing more than an upgraded Java: lots of syntax conveniences, some type inference and so on. But dig a little deeper and you will discover that many of the most popular and advanced features of functional languages are available.

Let’s get started with some simple examples.

* * *

### Algebraic data types

It’s normal for functional languages to support syntax like this:

```haskell
data  Maybe a  =  Nothing | Just a
  deriving (Eq, Ord)
```

That’s Haskell, and it defines a type called “Maybe” that has two so called _type constructors_, Nothing and Just. The Just type constructor takes a single parameter of unspecified type. The deriving keyword here means a Maybe can be compared for equality, and ordered. It can also be called a _tagged union_.

Kotlin doesn’t need a Maybe type because it has optionality as a built in part of the type system. Optionality is so common in programs that it makes sense to integrate it at a deep level, for both convenience and performance reasons:

```kotlin
val s: String? = if (Math.random() < 0.5) "Yay!" else null
println("length of string is .... ${"$"}{s.length()}")
```

Here, we get a compile error on the second line, because we’re trying to read the length of a string we might not actually have, depending on a coin flip. There’s a simple fix:

```kotlin
val s: String? = if (Math.random() < 0.5) "Yay!" else null
println("length of string is .... ${"$"}{s?.length() ?: -1}")
```

Here, s?.length() will yield null if s was null, and the ?: operator uses the right hand side if the left hand side is null. So this prints -1 if the coin flip didn’t yield the string.

However, we won’t think about that more here.

Because the Maybe type is so familiar to functional programmers, let’s define an equivalent of it here just for illustration.

```kotlin
sealed class Maybe<out T> {
    object None : Maybe<Nothing>()
    data class Just<T>(val t: T) : Maybe<T>()
}
```

The syntax isn’t as terse as Haskell, but isn’t bad either. The data modifier here is optional, but adding it gives us useful features.

We can do a variety of functional things with this:

```kotlin
val j = Maybe.Just(1)
val (i) = j
```

Here, we define a “just” containing an integer, and then we destructure it to get the integer back. Notice the lack of types: it’s all inferred. If the type had defined multiple fields, we could [destructure all of them](http://kotlinlang.org/docs/reference/multi-declarations.html), which is how this is actually meant to be used:

```kotlin
data class Pet(val name: String, val age: Int)
val alice = Pet("Alice", 6)
val (name, age) = alice
```

What about pattern matching? This is where the word “sealed” above comes in handy; we can do an exhaustive pattern match without an else/otherwise branch:

```kotlin
class User(val age: Int)

fun lookupFromDB(s: String): Maybe<User> = Maybe.None

fun printUser(username: String) {
    val rec = lookupFromDB(username)
    when (rec) {
        is Maybe.None -> println("not found")
        is Maybe.Just<User> -> println("${"$"}{rec.t.age} years old")
    }
}
```

Here, we define a simple class with a single immutable property (age), and a couple of functions. We have a lookupFromDB function that returns a Maybe: in this case, always a None, but that’s just an example.

Then we use [the when expression](http://kotlinlang.org/docs/reference/control-flow.html#when-expression) to do a pattern match on the type. When expressions are pretty flexible. They can use arbitrary expressions on the left hand side of each case and if the expression is a type query, the code on the right has the cast applied automatically. That’s why we can just access the t property of rec immediately.

### Immutability

Kotlin is not a pure FP language and does not have a ‘default’ for mutable vs immutable. It gently encourages immutability in a few places by choice of syntax, but otherwise makes you choose each and every time.

Here’s some code:

```kotlin
data class Person(var name: String, var age: Int)
val p = Person("Mike", 31)
p.name = "Bob"
```

Here “p” is an immutable **val**ue: it cannot be reassigned. It’s like a final variable in Java, or a let expression in Haskell/F#. But then the contents of this structure are mutable **var**iables, so they can be reassigned later. The IDE highlights identifiers differently if they’re mutable.

Here’s how it looks like, fully immutable

```kotlin
data class Person(val name: String, val age: Int)
val mike = Person("Mike", 31)
val olderMike = mike.copy(age = 32)
```

The copy method is auto generated whenever the data modifier is used. It has a named argument for every property, with the default value of that argument being whatever the current value is. Net result, you can use it to create a fresh object with tweaked fields.

Lists are immutable by default:

```kotlin
val people = listOf(mike, olderMike)
people.add(Person("Bob", 50))                     // ERROR

val peopleDB = arrayListOf(mike, olderMike)
peopleDB.add(Person("Bob", 50))

val view: List<Person> = peopleDB
val snapshot = peopleDB.toList()
```

The second line won’t compile: listOf() returns an immutable list. The fourth line does because we specifically picked an array list (vs a linked list), which is mutable. We can, of course, cast away the mutability to create a read only view, or clone the list to create a snapshot of it.

Currently there’s no dedicated list literal syntax. There might be in future, but for now, we must use functions.

### Mapping, filtering, reducing etc

Kotlin has support for efficient lambdas, and extends the default JDK collections classes to support common functions from FP standard libraries. This can even be used on Java 6 and thus Android:

```kotlin
val nums = listOf(1, 2, 3, 4)
val r = nums.map { it * 2 }.sum()       // r == 20
```

Here, the “it” identifier is a convenience: if a lambda has only one argument, it’s called “it” automatically. We can specify an explicit name when “it” would get too confusing, like in nested lambdas.

Map is an _extension function_. Where Java programmers would define a FooUtils class with static methods to add functionality to Foo classes, Kotlin lets you extend the class with a (statically dispatched) method directly. And then it uses that to give Java platform types like Iterable [new features](http://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-iterable/index.html).

A more advanced example:

```kotlin
val strs = listOf("fish", "tree", "dog", "tree", "fish", "fish")
val freqs = strs.groupBy { it }.mapValues { it.value.size() }
println(freqs)     // {fish=3, tree=2, dog=1}
```

### Recursive functions

Functional programmers like to express solutions to problems using recursion. This often needs an optimisation called _tail call optimisation_ to work well. Kotlin supports this in certain circumstances.

Here’s a simple example. A _fixpoint_ of a mathematical function is an input that gives itself as an output. To find a fixpoint of cosine, you can keep feeding the output back as an input until things stabilise. Here’s an example in an imperative fashion:

```kotlin
private fun cosFixpoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (x == y) return y
        x = y
    }
}
```

Pretty simple: starting at 1.0 we keep calling cos until cos(a) == a.

Here’s the same written in a recursive manner:

```kotlin
tailrec fun cosFixpoint(x: Double = 1.0): Double {
    val r = Math.cos(x)
    return if (x == r) x else cosFixpoint(r)
}
```

It could also be a one liner (or two here, with big fonts):

```kotlin
import java.lang.Math.cos

tailrec
fun f(x: Double = 1.0): Double = if (x == cos(x)) x else f(cos(x)))
```

That version relies on the JIT compiler to notice that it can eliminate the duplicated call to Math.cos(x).

### Currying, partial application, composition

These are features you’ll find in F# and Haskell, though I never felt a need for them myself. Currying turns a function into a chain of functions. Partial application lets you ‘fix’ certain parameters to a function, resulting in a new function.

Kotlin doesn’t support these out of the box. But it’s flexible enough that they can be added by [a library called funKtionale](https://github.com/MarioAriasC/funKTionale/wiki), in a natural manner:

```kotlin
import org.funktionale.currying.*

val sum2ints = { x: Int, y: Int -> x + y }
val curried: (Int) -> (Int) -> Int = sum2ints.curried()
assertEquals(curried(2)(4), 6)
val add5 = curried(5)
assertEquals(add5(7), 12)
```

... and ...

```kotlin
import org.funktionale.partials.*

val format = { prefix: String, x: String, postfix: String ->
    "${"$"}{prefix}${"$"}{x}${"$"}{postfix}"
}

val prefixAndBang = format(p3 = "!")

// Passing just the first parameter will return a new function
val hello = prefixAndBang(p1 = "Hello, ")

println(hello("world"))
```

### Lazyness

Kotlin is a strict/eager language, and this is how it should be. As far as I’m aware, no other well known language uses lazy-by-default except Haskell.

However you can do lazy computations if you want to. Here’s a dead simple real world example: avoiding the work of building a string if logging is disabled.

```kotlin
val loggingEnabled = System.getProperty("log") != null

fun log(s: String): Unit = if (loggingEnabled) println(s)

fun log(ls: () -> String): Unit = if (loggingEnabled) println(ls())
```

The log function is overloaded: it can take an actual string, or a function that calculates a string:

```kotlin
log("now!")
log { "calculate me later" }
```

Functional programming occasionally involves building infinite lists of things and operating on them in a lazy and possibly parallel manner. [This is often seen as a key selling point for FP](http://research.microsoft.com/en-us/um/people/simonpj/papers/stm/STMTokyoApr10.pdf) (see slide 10).

Since version 8 Java can do this too, and therefore so can Kotlin. For example:

```kotlin
val ONE = BigInteger.ONE
fun primes(n: Long) =
        Stream.iterate(ONE) { it + ONE }.
        filter { it.isProbablePrime(16) }.
        limit(n).
        toList()
```

Java calls infinite lazy lists _streams._ Here, we build a list of all the positive BigIntegers. Then we select only the ones that are probably prime numbers with chance 2^16, according to the Miller-Rabin primality test. Then we take _n_ of them and put them into a regular non-lazy list. This is classical functional programming.

How fast is this?

```kotlin
repeat(3) {
    val t = measureTimeMillis {
        primes(100000)
    }
    println("Took ${"$"}t msec")
}
```

On my laptop, after the first run when the JIT compiler has crunched, it takes about 1.5 seconds.

One nice thing about pipelines of pure functions is you can parallelise them. Let’s do that now:

```kotlin
fun primes(n: Long) =
        Stream.iterate(ONE) { it + ONE }.
        parallel().
        filter { it.isProbablePrime(16) }.
        limit(n).
        toArray()
```

We inserted a call to parallel() in our stream. This tells the JVM to run the rest of the pipeline in multiple threads. Rerunning the program shows that this improved performance 3x: it now only takes half a second. Not bad!

### STM

Software transactional memory is a way to write concurrent code. It is well explained in [this paper by Simon Peyton-Jones](http://research.microsoft.com/en-us/um/people/simonpj/papers/stm/beautiful.pdf), one of the architects of Haskell.

Instead of using locks you write code like this:

```kotlin
var account1 = 5
var account2 = 0

fun transfer(amount: Int) {
    atomic {
        account1 -= amount
        account2 += amount
    }
}
```

From the programmers perspective, anything that happens inside the atomic block takes effect all at once when the block is exited and there can be no race conditions inside it. But multiple threads can all be inside the atomic block at once, doing useful work. Pretty neat, right?

A simple implementation would be to have a giant global lock, but that’d be very slow. So fancier implementations record every change made inside the block by threads running concurrently, and detect conflicts: if there is a conflict, the block is retried. Haskell has an implementation of such a thing.

Kotlin does not have language support for software transactional memory. However, this is not such a big deal because via the JVM it gets support using libraries like [Scala STM](http://nbronson.github.io/scala-stm/quick_start.html) (see below), and even something better: _hardware_ transactional memory. Yup.

Modern (very modern) Intel chips support a set of processor extensions called TSX. TSX allows code to create an atomic transaction at the hardware level. Changes to RAM are buffered up in cache lines and interference between threads is tracked by the CPU itself. If there was a conflict, the CPU aborts the transaction and expects the code to either try again or fall back to regular locking. If no thread bumped into you, your writes are flushed to RAM in one go at the end.

Starting with Java 8 Update 40, so-called “RTM locking” is enabled by default when the CPU supports it. This converts _every_ Java synchronized block into a hardware level atomic transaction using TSX. That means you will have multiple threads running inside synchronized blocks at once. The JVM profiles the app to find blocks that experience frequent thread interference, where the CPU is wasting time due to constantly rolling back/retrying, and converts them back to using regular locks. As Kotlin runs on the JVM it gets this functionality for free.

This lets you write code in the “one big lock” style without suffering the performance downsides, as long as you are on sufficiently new hardware.

I should note here that STMs often provide extra features, like the ability to pause/retry a code block when the dependencies change, or the ability to explicitly cancel a transaction without retrying it (by throwing an exception). Hardware TM doesn’t offer this, or rather the JVM doesn’t surface the support at the moment. If you want more control, you must use a library and explicitly change your data model to incorporate transactional variables:

```kotlin
import scala.concurrent.stm.japi.STM.*

val counter = newRef(10)
try {
    atomic {
        increment(counter, 1)
        println("counter is ${"$"}{counter.get()}")    // -> 11
        throw Exception("roll back!!")
    }
} catch(e: Exception) {
    println("counter is ${"$"}{counter.get()}")        // -> 10
}
```

Haskell has one other neat trick up its sleeve —using its type system it can ensure that variables are only accessed inside atomic blocks, so you can never forget to wrap your code up properly. We can do something a bit similar in Kotlin:

```kotlin
class ThreadBox<T>(v: T) {
    private val value = v
    @Synchronized fun locked<R>(f: T.() -> R): R = value.f()
}

val bank = ThreadBox(object {
    val accounts = intArrayOf(10, 0, 0, 0)
})

fun transfer(from: Int, to: Int, amount: Int) {
    bank.locked {
        accounts[from] -= amount
        accounts[to] += amount
    }
}
```


A ThreadBox is a simple class that takes a pointer to some object in its constructor. It keeps that pointer privately. So, if there’s no other reference to the passed object it can only be accessed via the ThreadBox. When we declare _bank_, we use the object keyword to create an anonymous object and pass it in — so we know the only way to reach the accounts array is via the ThreadBox. And the ThreadBox only gives out that pointer inside an atomic block.

The compiler won’t let us access the array outside an atomic block ... unless we let a reference escape. So this is not as strong as the Haskell type system approach, but it’s a good start.

There’s a version of this code that can catch more mistakes [here](https://gist.github.com/mikehearn/1ad4a9c375e59e52b8cf).

The atomic method is a higher order function protected by a regular Java synchronized method, and it just immediately calls the provided code block under the lock. The JVM will ignore the lock on hardware that supports TSX and all threads can proceed in parallel, in an atomic transaction. As long as the two threads are using different account IDs for _from_ and _to_, no locking is done at all: that makes it nice and fast.

### Things Kotlin lacks

At the moment there is no way to control side effects: any function call can be potentially side effecting. It would be nice if a future version of the language introduced something like the C++ const keyword, or D’s transitive const, to reduce the reliance on actually immutable data structures. The JVM offers features that can seal off common sources of external state, such as the disk and network, however the heap is still available. It may be an interesting project to make the JVM’s sandboxing features easily available via a Kotlin DSL.

There is not at this moment any high performance immutable collections library. Both Clojure and Scala have maps and sets in which mutating the collection returns a new collection, with internal data sharing to make performance practical. The Kotlin standard library does not. If someone were to make one, using [the CHAMP code](http://michael.steindorfer.name/publications/oopsla15.pdf) published this year would give significant improvements over the algorithms used in Scala/Clojure.

"""

Article(
  title = "Kotlin ❤ FP",
  url = "https://medium.com/@octskyward/kotlin-fp-3bf63a17d64a",
  categories = listOf(
    "Kotlin",
    "Functional"
  ),
  type = article,
  lang = EN,
  author = "Mike Hearn",
  date = LocalDate.of(2015, 9, 18),
  body = body
)
