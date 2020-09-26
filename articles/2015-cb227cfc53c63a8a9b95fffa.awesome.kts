
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
June 04, 2015

Following the release of [Kotlin M12](http://blog.jetbrains.com/kotlin/2015/05/kotlin-m12-is-out/) and of [Quasar 0.7.0](http://blog.paralleluniverse.co/2015/05/29/quasar-pulsar-0-7-0/) introducing support for it, let’s now have a closer look at how Kotlin and Quasar work together.

[Kotlin](http://kotlinlang.org/) is JetBrains’ fun, statically-typed, safe, interoperable and expressive language targeting the JVM, Android and JavaScript. Pragmatic and efficient, it also features an excellent [IntelliJ Idea](https://www.jetbrains.com/idea/) integration from the start (which is my own and many other folks’ favourite IDE BTW). Finally our joint work with the Kotlin team has made Quasar on Kotlin smooth, natural and fast.

Enabling Quasar support for Kotlin in your project is as easy as adding `quasar-kotlin` as a dependency and using one of Kotlin plugins for build systems, for example [Gradle’s](http://kotlinlang.org/docs/reference/using-gradle.html). We’ll start from a bird’s-eye view of our [ping-pong actor test](https://github.com/puniverse/quasar/blob/master/quasar-kotlin/src/test/kotlin/co/paralleluniverse/kotlin/actors/PingPong.kt) and then we’ll zoom in:

```kotlin
data class Msg(val txt: String, val from: ActorRef<Any?>)

class Ping(val n: Int) : Actor() {
    Suspendable override fun doRun() {
        val pong = ActorRegistry.getActor<Any?>("pong")
        for(i in 1..n) {
            pong.send(Msg("ping", self()))          // Fiber-blocking
            receive {                               // Fiber-blocking
                when (it) {
                    "pong" -> println("Ping received pong")
                    else -> null                    // Discard
                }
            }
        }
        pong.send("finished")                       // Fiber-blocking
        println("Ping exiting")
    }
}

class Pong() : Actor() {
    Suspendable override fun doRun() {
        while (true) {
            // snippet Kotlin Actors example
            receive(1000, TimeUnit.MILLISECONDS) {  // Fiber-blocking
                when (it) {
                    is Msg -> {
                        if (it.txt == "ping")
                            it.from.send("pong")    // Fiber-blocking
                    }
                    "finished" -> {
                        println("Pong received 'finished', exiting")
                        return                      // Non-local return, exit actor
                    }
                    is Timeout -> {
                        println("Pong timeout in 'receive', exiting")
                        return                      // Non-local return, exit actor
                    }
                    else -> defer()
                }
            }
            // end of snippet
        }
    }
}

public class Tests {
    Test public fun testActors() {
        spawn(register("pong", Pong()))
        spawn(Ping(3))
    }
}
```

## Data

```kotlin
data class Msg(val txt: String = "Hello", val from: ActorRef<Any?>)
```

Yes, Kotlin supports [data classes](http://kotlinlang.org/docs/reference/data-classes.html). This means that when you need a one-liner to hold some info, it can be a one-liner for real. Kotlin will generate sensible `equals`, `hashCode`, `toString` as well as deconstruction support, so that you can easily write:

```kotlin
val myMsg = Msg(txt = "Hi", from = me)
// ...
val (txt, from) = myMsg
```

Add to that type inference, a shorter construction syntax for class instances (no `new` needed), default parameter values, invocation with named arguments and support for immutability with `val` declarations and you’ve got a full toolbox for your message-crafting actor needs (and more). Should you need to copy your message in full or in part:

```kotlin
val myNewMsg = myMsg.copy(txt = "Howdy")
```

`Pair` and `Triple` are included in the standard library, too <sup id="fnref:bigger-tuples">[1](#fn:bigger-tuples)</sup>.

`ActorRef<Any?>` represents a reference to a Quasar actor whose `send` can accept values of any type, including `null`s. `Any` is the utmost super-type in Kotlin and the question mark specifies that it is _nullable_ here (the default in Kotlin is non-nullable). If a value is of a non-nullable type, Kotlin will check against nulls at compile time for Kotlin code, and at runtime for values produced by Java invocations <sup id="fnref:java-interop">[2](#fn:java-interop)</sup>.

## Classes

```kotlin
class Ping(val n: Int) : Actor()
```

`Ping` inherits from the base `Actor` Kotlin class <sup id="fnref:untyped-actors">[3](#fn:untyped-actors)</sup> and it is _final_. This is the default in Kotlin because designing for inheritance is difficult and control over class hierarchies is important, so inheritance support must be declared explicitly through the `open` class modifier.

In Kotlin you can define your primary constructor without specifying a body and declare its parameters as properties by prefixing `val` (immutable) or `var` (mutable), which you need to do if you plan to use them not only during initialization but in methods too. A parent class’ constructor invocation is inline with inheritance declaration, and a short `:` is enough to specify that.

We declare `n` as a property because our actor is going to use it in its `doRun` execution body:

```kotlin
Suspendable override fun doRun() {
      // ...
}
```

The `override` modifier is mandatory and the `Suspendable` annotation allows our actor’s fiber to invoke fiber-blocking calls such as `send` and `receive`. [Kotlin annotations](http://kotlinlang.org/docs/reference/annotations.html) don’t need to be preceded by `@` in most situations, which allow them to appear naturally as user-defined modifiers. Kotlin also supports [JSR-269 annotation processing](http://blog.jetbrains.com/kotlin/2015/05/kapt-annotation-processing-for-kotlin/).

## Fiber-blocking Kotlin actors

```kotlin
val pong = ActorRegistry.getActor<Any?>("pong")
for(i in 1..n) {
    pong.send(Msg("ping", self()))          // Fiber-blocking
    receive {                               // Fiber-blocking
        when (it) {
            "pong" -> println("Ping received pong")
            else -> null                    // Discard
        }
    }
}
pong.send("finished")                       // Fiber-blocking
```

`Ping` leads the game here: it gets a reference to `Pong` from the global registry, then `send`s and `receive`s ping pongs `n` times, after which it tells `Pong` to bail out before quitting itself. It’s once again as simple as it was with regular threads: just straightforward imperative constructs and blocking calls, only much more efficient thanks to fibers.

Kotlin makes it even sweeter though. Apart from the nice looping construct with [ranges](http://kotlinlang.org/docs/reference/ranges.html), let’s have a deeper look at the [_selective receive_](http://docs.paralleluniverse.co/quasar/#sending-and-receiving-messages-actorref) block:

```kotlin
receive {                               // Fiber-blocking
    when (it) {
        "pong" -> println("Ping received pong")
        else -> null                    // Discard
    }
}
```

It feels like a new construct, doesn’t it? This neat small DSL employs no less than 3 advanced Kotlin features: [lambdas](http://kotlinlang.org/docs/reference/lambdas.html) with [inline functions](http://kotlinlang.org/docs/reference/inline-functions.html) and the type-safe [matching `when` expression](http://kotlinlang.org/docs/reference/control-flow.html#when-expression).

The block following `receive` is actually a single-argument _lambda_ for which Kotlin provides an extremely compact syntax. You can even avoid naming the argument: it’ll be called `it` as in this case, and of course you can skip brackets along the way. This is `receive`’s Kotlin signature:

```kotlin
inline protected fun receive(proc: (Any) -> Any?)
```

`proc` is a selection and transformation function: it can accept (and possibly transform), discard or delay an incoming message. If a message is accepted, it is returned by the `receive` call.

Here `proc` contains only a matching `when` expression, which is type-checked for matching exhaustiveness and, of course, type correctness <sup id="fnref:when">[4](#fn:when)</sup>. Yielding `null` in `proc` discards the value and _doesn’t_ return from `receive`; calling `defer()` will skip the message in the current `receive` call, leaving it in the mailbox, while producing any other value (even `Unit` like the `println` statement in the `"pong"` branch) will make the `receive` call return and control flow will proceed to the next iteration in the `for` loop.

Let’s take a glance at `Pong`’s main loop:

```kotlin
receive(1000, TimeUnit.MILLISECONDS) {  // Fiber-blocking
    when (it) {
        is Msg -> {
            if (it.txt == "ping")
                it.from.send("pong")    // Fiber-blocking
        }
        "finished" -> {
            println("Pong received 'finished', exiting")
            return                      // Non-local return, exit actor
        }
        is Timeout -> {
            println("Pong timeout in 'receive', exiting")
            return                      // Non-local return, exit actor
        }
        else -> defer()
    }
}
```

In this case we’re calling a selective receive with a timeout <sup id="fnref:receive">[5](#fn:receive)</sup>. Two more notable Kotlin features and two API features work together here to make this code short, readable and powerful. As for Kotlin:

* `is Msg` is a type check that performs a _smart cast_, so that `it` acquires the `Msg` type in its clause and properties can be accessed as simply as `it.txt` and `it.from`.
* `return` in the _inline lambda_ performs a _non-local return_, that is it makes the `doRun` invocation return, after which the actor will quit <sup id="fnref:only-inline">[6](#fn:only-inline)</sup>.

The Kotlin Actors API adds the following:

* `defer()` implements _selective receive_ by skipping messages in the current `receive` call.
* A `Timeout` message can be used to handle timeouts directly in the processing lambda if you so wish, without even returning from the `receive` call.

## Now with plain, healthy fibers

There’s now a neat Kotlin port of the `quasar-gradle-template` in the `kotlin` branch. I built it very quickly by copying and pasting the previous Java source to a new Kotlin file in IntelliJ Idea and confirming Kotlin conversion! Of course then I polished it to my tastes and added the semantics that were missing in Java, and here’s the result:

```kotlin
package testgrp

// ...

fun doAll(): Int? {
    val increasingToEcho = Channels.newIntChannel(0) // Synchronizing channel (buffer = 0)
    val echoToIncreasing = Channels.newIntChannel(0) // Synchronizing channel (buffer = 0)

    val increasing = Fiber(SuspendableCallable(@Suspendable {
        var curr = 0
        for (i in 0..9) {
            Fiber.sleep(1000)
            println("INCREASER sending: " + curr)
            increasingToEcho.send(curr)
            curr = echoToIncreasing.receive()
            println("INCREASER received: " + curr)
            curr++
            println("INCREASER now: " + curr)
        }
        println("INCREASER closing channel and exiting")
        increasingToEcho.close()
        curr;
    })).start()

    val echo = Fiber(SuspendableCallable(@Suspendable {
        val curr: Int?
        while (true) {
            Fiber.sleep(1000)
            curr = increasingToEcho.receive()
            println("ECHO received: " + curr)

            if (curr != null) {
                println("ECHO sending: " + curr)
                echoToIncreasing.send(curr)
            } else {
                println("ECHO detected closed channel, closing and exiting")
                echoToIncreasing.close()
                break
            }
        }
    })).start()

    increasing.join()
    echo.join()

    return increasing.get()
}

public fun main(args: Array<String>) {
    doAll()
}
```

Wait, where are the classes? Functions can be toplevel in Kotlin: a class is generated for each package with static methods corresponding to toplevel functions. We’re not doing real OOP here, so we won’t define classes and Kotlin allows us to skip them altogether if we don’t need them. That makes sense and eliminates boilerplate, doesn’t it?

As for the packages, they are completely _disjoint_ from source files organization in directories and that allows freedom and convenience. Of course with great powers comes great responsibility but why should I bother creating directories only in order to use packages, when my project is made of a single source file (or a handful of them)? And since Kotlin is very concise, this situation will arise more often than you think.

Now, where are the types instead? This example is strongly typed but most of the types are inferred. There are only a handful of them:

```kotlin
fun doAll(): Int?
```

In Kotlin, `public` and `protected` functions need to be _explicitly typed_ both in the argument and return type because they _are public API_ and we don’t want to alter it by mistake because we changed the body (and type inference has adjusted types consequently without us noticing) <sup id="fnref:public-api">[7](#fn:public-api)</sup>.

In this case though, this function has _module-level_ access (“internal”). Why does it still need to be fully typed? Because it has a block body which can have complex control flow and type inference could easily confuse the reader (and hinder maintainability).

```kotlin
val increasing = Fiber(SuspendableCallable(@Suspendable {
```

What’s happening here? We’re passing a `Suspendable`-annotated lambda instead of a full blown object expression (which is more pleasant than Java’s anonymous classes anyway), similarly to what we’d do with Java 8 lambdas. The `SuspendableCallable` constructor helps Kotlin’s type inference engine to interoperate with Java functional interfaces (this will be simplified very soon so that there’ll be no need to explicitly mention `SuspendableCallable`). Finally we don’t need to declare actual type parameters for `Fiber` (no ugly _diamonds_ either, sorry for people that like them) and of course not even for the `increasing` local.

```kotlin
var curr: Int?
```

Since we know that the value of the `curr` mutable local value will come from Java, we know it could become `null` (and at some point it actually needs to, in order to represent a closed Quasar channel). We’re telling Kotlin that in advance, so that it won’t emit fail-fast runtime checks that will trigger an exception when `curr` gets assigned a `null` value.

By contrast no type declaration is needed by the `curr` mutable local in the `increasing` fiber, because it will be inferred to be `Int` and since it will be managed completely within Kotlin code, the compiler will check statically that it will never become `null`:

```kotlin
var curr = 0 // Int
```

## What else is there?

Kotlin has loads of other advanced and convenient features that make it a pleasure to use not just with Quasar but in every occasion:

* Type-checking covers [null safety](http://kotlinlang.org/docs/reference/null-safety.html) and allows [smart type casts](http://kotlinlang.org/docs/reference/typecasts.html); imperative programmers will enjoy [returns and jumps](http://kotlinlang.org/docs/reference/returns.html) and [ranges](http://kotlinlang.org/docs/reference/ranges.html) as well as [excellent Java interoperability](http://kotlinlang.org/docs/reference/java-interop.html) and [unchecked exceptions](http://kotlinlang.org/docs/reference/exceptions.html#checked-exceptions).
* Functional guys will appreciate [higher-order functions and local functions](http://kotlinlang.org/docs/reference/functions.html) as well as [first-class method](http://kotlinlang.org/docs/reference/reflection.html#function-references) and [property accessor](http://kotlinlang.org/docs/reference/reflection.html#property-references) references.
* DSL craftpeople will make good use of functional features, convenient syntax shortcuts and [type-safe builders](http://kotlinlang.org/docs/reference/type-safe-builders.html).
* OOP has no shortage of tools either: [extension functions](http://kotlinlang.org/docs/reference/extensions.html), [trait-like interfaces](http://kotlinlang.org/docs/reference/interfaces.html), [compact and common sense inheritance with sensible defaults](http://kotlinlang.org/docs/reference/classes.html), [singleton objects](http://kotlinlang.org/docs/reference/object-declarations.html#object-expressions), [object expressions](http://kotlinlang.org/docs/reference/object-declarations.html#object-expressions), [generics with declaration-site variance and type projections](http://kotlinlang.org/docs/reference/generics.html), [feature-rich enums](http://kotlinlang.org/docs/reference/enum-classes.html), [delegation](http://kotlinlang.org/docs/reference/delegation.html), [properties accessors](http://kotlinlang.org/docs/reference/properties.html#getters-and-setters) and [delegated properties](http://kotlinlang.org/docs/reference/delegated-properties.html).

* * *

1. If you need bigger tuples it’s probably an hint that you’d better give them a more specific name for readability’s sake. [↩](#fnref:bigger-tuples)
2. See [Java interoperability, platform types](http://kotlinlang.org/docs/reference/java-interop.html) and [null safety](http://kotlinlang.org/docs/reference/null-safety.html). [↩](#fnref:java-interop)
3. Kotlin actors are always untyped because typed actors are useful only in very simple cases, like short-lived transient actors, but not so much in more complex situations. [↩](#fnref:untyped-actors)
4. `when` supports type maching, value matching against arbitrary expressions and range matching; see [control flow](http://kotlinlang.org/docs/reference/control-flow.html). [↩](#fnref:when)
5. The timeout overload is `inline protected fun receive(timeout: Long, unit: TimeUnit?, proc: (Any) -> Any?)` and it is non-blocking if the timeout is 0. [↩](#fnref:receive)
6. This is possible only with inline lambdas. [↩](#fnref:only-inline)
7. This is likely to become only a warning in the future. [↩](#fnref:public-api)


"""

Article(
  title = "Quasar and Kotlin - a Powerful Match",
  url = "http://blog.paralleluniverse.co/2015/06/04/quasar-kotlin/",
  categories = listOf(
    "Kotlin",
    "Quasar",
    "Fibers"
  ),
  type = article,
  lang = EN,
  author = "Fabio",
  date = LocalDate.of(2015, 6, 4),
  body = body
)
