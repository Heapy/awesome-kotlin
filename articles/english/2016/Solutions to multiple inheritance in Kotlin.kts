
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I have recently come across a little problem that recurs from time to time as I write new code.

I had a base class with some really useful methods that I wanted to make another class extend, but the ditto class already extended some other, equally useful, base class.

If this were Java, I would just add a field to the class, using [composition rather than inheritance](https://en.wikipedia.org/wiki/Composition_over_inheritance) and be done with it, even if that felt a little clunky, after all that's the best you can do in Java.

However, these days I'm using [Kotlin](https://kotlinlang.org/) more often than not. Moving on with the times, I guess!

Anyway, Kotlin has a really interesting feature called [class delegation](https://kotlinlang.org/docs/reference/delegation.html), which allows you to implement composition in a much nicer way than in Java. I was already familiar with this kind of thing from using the [Groovy @Delegate](http://docs.groovy-lang.org/latest/html/gapi/groovy/lang/Delegate.html) AST in the past, so I decided to give it a go.

Before we proceed, I must note that because classes can have state and initialization logic (including side-effects), Kotlin does not allow true [multiple inheritance](https://en.wikipedia.org/wiki/Multiple_inheritance) as that could [cause havoc](https://blog.jetbrains.com/kotlin/2011/08/multiple-inheritance-part-2-possible-directions/) in slightly more complex class hierarchies (it does allow declaring properties and implementing methods in interfaces, though, as that's free of the subtle problems you could encounter with a more permissive approach)... so we will have to do something similar to Java, but with much appreciated help from the language!

Here's how it works. Imagine you have two different classes you want to mix-in (ie. inherit from) to your type. As long as you have interfaces for those types, it's fairly simple:

```kotlin
 interface CanFly {  
    fun fly()  
 }  

 interface CanWalk {  
    fun walk()  
 }  
```

The concrete classes:

```kotlin  
 class Flyer : CanFly {  
    override fun fly() = println("Flying!")  
 }  

 class Walker : CanWalk {  
    override fun walk() = println("Walking...")  
 }  
```

Now, we want a type that has both the ability to walk and fly! In other words, we want a `Pony` :)

```kotlin  
 class Pony(flyer: CanFly, walker: CanWalk) :  
        CanFly by flyer,  
  CanWalk by walker  
```

And to actually make a Pony:

```kotlin  
 val pony = Pony(Flyer(), Walker())  
```

If the type we're interested in does not implement a useful interface, we can use a simple adapter that implements the interface we're interested in to achieve similar results.

For example, if `Walker` (the concrete class) did not implement `CanWalk`, we could create a `CanWalkAdapter` like this:

```kotlin  
 class CanWalkAdapter(private val walkFun: () -> Unit) : CanWalk {  
    override fun walk() = walkFun()  
 }  
```

Notice that only the (non-default) interface methods need to be manually overridden. The `Pony` class itself would remain the same, and to get our `Pony` in this situation would not be too hard:

```kotlin     
 val pony = Pony(Flyer(), CanWalkAdapter { Walker().walk() })  
```

This is just a silly example, so to show how this technique can actually be useful, let's imagine we have a class that already _"burned"_ its only chance at inheriting from a base class... and that we want to add caching to it!

You might already have a class that implements generic caching, and you would certainly want to re-use it. You couldn't mix-in an interface because interfaces, as we saw above, cannot have state, and without state you'll have trouble implementing a cache!

So, to solve this problem, let's try to use class delegates, as in the silly Pony example above.

First, here's our class that will require a cache:

```kotlin  
 abstract class BaseType  

 // the super-class is already taken class MyType : BaseType() {  

    fun someFunctionThatCanBeCached(input: Int): String {  
        // some complicated logic  
  return input.toString()  
    }  
 }  
```

A really simple cache interface/implementation:

```kotlin  
 interface Cache<K, V> {  
    fun get(input: K, create: (K) -> V): V  
 }  

 class MapCache<K, V> : Cache<K, V> {  

    val map = mutableMapOf<K, V>()  

    override fun get(input: K, create: (K) -> V): V =  
            map.getOrPut(input) { create(input) }  
 }  
```

As we saw above, even if the cache-implementation class did not implement an interface (which we need for class delegation), we could've used an adapter to solve that.

And finally, the modified `MyType` implementation that delegates caching to some `Cache` implementation:

```kotlin  
 class MyType(cache: Cache<Int, String>) :  
        BaseType(), Cache<Int, String> by cache {  

    fun someFunctionThatCanBeCached(input: Int): String {  
        return get(input) {  
  // some complicated logic  
  println("Running complicated logic")  
            input.toString()  
        }  
  }  
 }  
```

We can now run this code to make sure the cache is working:

```kotlin  
 fun main(args: Array<String>) {  
    val myType = MyType(MapCache())  

    println(myType.someFunctionThatCanBeCached(3))  
    println(myType.someFunctionThatCanBeCached(3))  

 }  
```

Running it prints the following:

```
Running complicated logic
3
3
```

As expected, the "complicated logic" only ran the first time.

Now `MyType` has a cache, and can also be used anywhere a cache is required because with minimal effort, it implements the `Cache` interface (which in the real world might have quite a few more methods than this, so would be cumbersome to implement the Java-way).

This is a simple, but quite useful way to re-use functionality in Kotlin. And one more small way in which using Kotlin can improve your productivity and code quality at the same time.

"""

Article(
  title = "Solutions to multiple inheritance in Kotlin",
  url = "https://sites.google.com/a/athaydes.com/renato-athaydes/posts/solutionstomultipleinheritanceinkotlin",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Renato Athaydes",
  date = LocalDate.of(2016, 11, 30),
  body = body
)
