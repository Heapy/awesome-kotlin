---
title: 'Kotlin’s killer features'
url: https://blog.codecentric.de/en/2016/04/kotlins-killer-features/
categories:
    - Kotlin
author: Jasper Sprengers
date: Apr 04, 2016 10:00
---
SUMMARY: Kotlin is a new JVM language fully interoperable with Java bytecode. It is clearly inspired by Scala, but has a different design philosophy, a much gentler learning curve and some really helpful features like null-safe types.

## The Importance of TIOBE

How much value should one place in the TIOBE index? What is it really worth? Let me answer that question after I have checked the rating of my favourite programming language.

Here’s something that must irk all hard-core Scala afficionados: although it has been around for over twelve years and boasts some superior features, it hasn’t made much of a dent in Java’s dominance. In fact it takes a modest 30th place, well behind the Latin that is Fortran and (you may shudder) COBOL. Don’t get me wrong: I like Scala. It deserves much more adoption than it gets at present, so why doesn’t it? My suspicion is that its supposed benefits are not compelling enough to entice people to climb its steep learning curve. Functional programming is hard and not a likely selling point for the average programmer

[![Screen Shot 2016-04-04 at 06.53.46](https://blog.codecentric.de/files/2016/04/Screen-Shot-2016-04-04-at-06.53.46.png)](https://blog.codecentric.de/files/2016/04/Screen-Shot-2016-04-04-at-06.53.46.png)

Although this post is about [Kotlin](http://kotlinlang.org/), I feel I have to mention [Scala](http://www.scala-lang.org/), because it nevertheless has been an inspiration for the designers at Jetbrains. They have taken the good parts, left out/re-thought some of the hard stuff and added some new features. “If you are happy with Scala, you most likely do not need Kotlin”, [they even admit,](https://kotlinlang.org/docs/reference/comparison-to-scala.html) but I actually disagree. I like Scala, but after trying out Kotlin for no more than ten hours I can confidently say I like Kotlin better. Admittedly it says more about me as a programmer than about the languages per se, so I invite you to make up your own mind. Maybe it’s the fact that the official Kotlin documentation is only 144 pages. It would take some really fine print to cover all about Scala in the same number of pages.

## Do we need a new language anyway?

Not really. All modern languages are Turing complete. It’s just that I like to code and some languages give me far greater pleasure than others.

But now that you mention it, there _is_ actually a lot about Java that will continue to annoy us for many years to come, or at least for as long as old source code — written in the days before we all had mobile phones, for heaven’s sake — must keep running on every new JVM generation. If the historical development of Java is of a dirt track road evolved into a motorway, it’s a motorway that allows horse-drawn carriages simply because some teams didn’t get round to retiring their old horses.

[![Twenty years of sources, one JVM?](https://blog.codecentric.de/files/2016/04/carriage.jpg)](https://blog.codecentric.de/files/2016/04/carriage.jpg)

Twenty years of sources, one JVM?

Backward compatibility means we can’t rethink bad ideas or implement new stuff truly elegantly. It has given us gems like:

* raw types and type erasure
* Checked exceptions. No language has implemented it ever since, it must be a bad idea.
* Covariant arrays: I give you a bag of apples, you treat it as a bag of fruit, replace an apple with a pear and hand it back to me: runtime mayhem. Mutable collections should be invariant.
* Object wrappers for primitives, because that’s the only way collection types can contain them. And we all know autoboxing is the source of some of the worst NullPointers.
* And since the compiler likes you to spell things out rather than infer them, Java source remains more verbose than needed. It’s mitigated somewhat by the autocompletion prowess of modern IDEs, but only somewhat.

There are clear merits to backward compatibility, but they’re getting less pressing now that the IT world is (rightly) leaving behind the days of monolithic development. One virtual machine shouldn’t have to compile and run the fruits of ten years worth of coding. If we want to make bigger strides in language development we should look beyond Java the language. JVM languages like Scala and Kotlin throw out the bathwater of raw types and let us keep the baby.

## Life saving versus labour saving features

Many features that are commonly touted to win people over to Scala — or to Kotlin in due course —  are what I would term ‘labour saving’. Obvious examples are the code that can be inferred by the compiler and is therefore redundant and best left out. Type inference in variable declarations as well as compiler-generated getters/setters/equals/hashCode (the data qualifier on a class declaration) all save wear on your keyboard.

```kotlin
data class Greeter(val name: String) {
    fun greet() {
        println("Hello, $name")
    }
}
```

Java is extremely conservative with such compiler inference. A more sophisticated device is operator overloading, i.e. using arithmetic operators for method calls on your own classes, defined like this:

```kotlin
class Dollar(val cents: Int) {
    operator fun plus(cts: Int): Dollar = Dollar(cents + cts)
    operator fun plus(money: Dollar): Dollar = Dollar(cents + money.cents)
    override fun toString() = "$" + (cents.toFloat() / 100.0)
}
```

We can now use the plus operator (and minus, unary, etc.) to add either integers or other Dollar objects and receive a new Dollar object back.

```kotlin
val oneDollarTwenty = Dollar(100) + Dollar(20)
val alsoOneDollarTwenty = Dollar(100) + 20
```

Extension methods are another great feature. Similar to Scala’s implicit methods, they let you add methods on a per-use basis to classes otherwise closed to extension, typically numeric types or other primitives (like Scala, there are no real primitives; everything is an object).

```kotlin
operator fun Int.plus(money: Dollar): Dollar = money.plus(this)
```

All three combined they let you do things like this:

```kotlin
val p1 = Dollar(1200)
val p2 = Dollar(800)
println(p1 + p2 + 3)// invokes p1.plus(p2).plus(3)
println(3 + p1 + p2)// invokes 3.plus(p1).plus(p2)
```

The combination of type inference, operator overloading and extension methods can be very neat and very confusing at the same time:

```kotlin
val totalPrice = 3 + currentPrice() * orderTotal()
```

I can’t blame you for thinking that totalPrice must be a numeric value, but there’s no way to be sure. The _only_ way to use these features wisely is if you also take even better care than normal in naming or add a type declaration where it isn’t strictly required. Cutting out redundancy is great, but it means the code that’s left should be perfectly self-documenting. It’s your sacred duty as a clean coder. Promise?

## What’s a real killer feature anyway?

I suppose the greatest language features let you do things you couldn’t do before. Those are very few. Failing that, making a programmer’s life happier comes a very good second. Saving keystrokes is one thing, but I believe true killer features are the ones that:

* Help you prevent mistakes due to your own sloppiness.
* Implement fundamental concepts intuitively.

## The Billion dollar mistake made right

The true killer feature for me is Kotlin’s null-safe types. It’s the best solution to tackle null references, a.k.a [the Billion dollar mistake](https://en.wikipedia.org/wiki/Null_pointer) and the hated NullPointerException. The NPE is so pernicious that its abreviation doesn’t even need explaining in commit logs. And it’s all due to our own sloppiness! We know how to avoid it, following [Postel’s law](https://en.wikipedia.org/wiki/Robustness_principle): don’t return null references and always check your arguments for nullity, especially when invoked from the outside world. Yeah right.

Kotlin takes the only sensible route: the type system simply refuses to compile code that tries to assign or return null:

```kotlin
val name: String = null // tries to assign null, won't compile
fun getName() : String = null // tries to return null, won't compile
```

If you need nullability you have to ask Kotlin nicely. Every Foo class has a corresponding Foo? type to be used for null references.

```kotlin
fun getName() : String? = null
val name: String? = getName()
```

Nullable types require special care. You can’t treat them the same way as non-nullable types, and that’s a very good thing. Since every method call on a nullable type could cause an NPE, the compiler forces you to use the so-called Elvis operator when the result of the call is assigned to a non-null type:

```kotlin
val nameOrBruce: String = getName() ?: "Bruce"
val nameOrNull: String? = getName()
```

The first variable is non-null, therefore we must provide an alternative in case getName() returns null. That’s not needed for the second assignment, since nameOrNull is of a nullable type. The Elvis operator prevents NPEs also in chained calls (from the offical docs):

```kotlin
val name: String? = bob?.department?.head?.name
```

If any of these calls returns null, the rest is skipped. But of course you know better than to write such trainwreck statements (Law of Demeter).

We’ve had the Optional wrapper pattern in many different flavours as a better way to avoid null references. It’s been in the standard library since Java 8. Kotlin’s types are better by far:

* They’re more conscise: Optional wrappers add verbosity in creating and unwrapping.
* Optionals don’t offer compile-time safety at all: you can still return and pass null where an Optional is expected and get the same hated NPE.
* The Optional pattern is just a design pattern. It’s not an integral language feature.

But my greatest objection to the Optional pattern is that it is so flipping _optional_. Kotlin has made the right decision: only allow null if you explicitly ask for it. This is not a labour saving measure: it actually eliminates the opportunity for errors while forcing you to think more deeply about about design. Do you really need to return null from your method? Isn’t there a better way to signal such special cases? (There is by the way: check out sealed classes, similar to Scala’s case classes).

It will indeed be a cold day in hell before something like this makes it into the Java language, as long as backward compatibility stays non-negiotionable.

## A bag of apples isn’t always a bag of fruit

Java’s use of wildcards in parameterized types must be one of the least understood and least liked features. It boils down to this: if a juicer _produces_ only apple juice, I may safely call it a fruit juice dispenser (covariance). If the same machine _accepts_ all kinds of fruit, I may safely call it an apple juicer, while you call it a pear juicer (contra-variance).  In code, the dispenser is all about return types (what goes out), while the juicer is about input. In Java, if I want the flexibility to parameterize my juicer with different types of fruit, I have to do this:

```kotlin
public interface Juicer {
    T dispense();
}
```

This lets me make a Juicer<AppleJuice> and a Juice<CarrotJuice>, but it doesn’t let me do this:

```kotlin
Dispenser applejuiceDispenser = new Dispenser();
Dispenser juiceDispenser = applejuiceDispenser;//wont' compile
```

unless I use the wildcard in the type declaration:

```kotlin
Dispenser< extends Juice> juiceDispenser = applejuiceDispenser;
```

In Kotlin parameterized types are also invariant, but you can enhance the type declaration with **in** or **out** to signal contravariance and covariance, respectively. A juicer has fruit coming in and juice coming out. It can be expressed with two interfaces

```kotlin
interface Dispense<out T> {
abstract fun tap(): T
}
interface Squeezer<in T> {
abstract fun squeeze(fruit: T)
}
class FruitJuicer() : Squeezer, Dispenser {
 [...]
}
```

Since the in and out sides of the parameterization are in different interfaces, I can use these more freely and not be constrained by invariance:

```kotlin
var appleJuicer : Dispenser = FruitJuicer()
var elstarJuicer : Dispenser = FruitJuicer()
elstarJuicer = appleJuicer; //Won't compile, not covariant
appleJuicer = elstarJuicer; //OK

var appleSqueezer : Squeezer = FruitJuicer()
var elstarSqueezer: Squeezer = FruitJuicer()
elstarSqueezer = appleSqueezer; //OK, contravariant
```

Since the apple squeezer takes all kinds of apples, it’s okay to put only elstars in it. (if you didn’t know an elstar is an apple you should probably look at your eating habits). While this feature does not eliminate runtime goofs to the degree that nullable types do, I think it’s a very elegant solution to a fundamental programming concept that Java didn’t really solve intuitively.

## Summary

I hope to have convinced you that newcomer Kotlin has some really helpful features. Having experienced Scala well beyond the Hello World stage myself, I promise you with confidence that Kotlin is much easier to master. If you’re looking for greater productivity and sheer joy of coding, it’s well worth a try. Last but not least and no surprise, the IDE support in IntelliJ is excellent, coming from Jetbrains.
