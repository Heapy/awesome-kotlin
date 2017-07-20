
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## Intro

Continuing on with [Kotlin Month](https://programmingideaswithjake.wordpress.com/kotlin/#kotlinmonth), this week, I’ll be discussing topics in Kotlin that have to do with inheritance and default values.

## Composition Over Inheritance

Kotlin has put in a feature and a couple defaults that help developers keep the principle of Composition Over Inheritance at the forefront of their mind. The first of these is a feature that lets you composition almost as easily as inheritance when it comes to defining wrapper classes.

## First-Class Delegation

Kotlin provides first-class access to delegation with very little help from you, the developer. All that needs to be done is:

1. The new class inherits from an interface
1. The new class provides a primary constructor that defines a property that inherits from the same interface
1. After declaring the inheritance of the interface, include “by <name of property>”

For example:

```kotlin
interface A {
   fun doSomething(): Unit
}

class B (val a: A) : A by a
```

In this example, we have interface `A`, which has a `doSomething()` method to be implemented. Class `B` is our delegator class. It inherits from `A`, includes a primary constructor with a property called `a` that inherits from `A` (`(val a: A)`), and says to delegate to `a` with `A by a`.

Now `B` doesn’t need to explicitly implement any of the methods from `A` because they’re implicitly provided via delegation, just like it would with normal inheritance if `B` was inheriting from a fully implemented class. In this case, instead of just pointing to its parent class implementation, it gets a default implementation as if `B` had been defined like this:

```kotlin
class B (val a: A) A {
   override fun doSomething() {
      a.doSomething()
   }
}
```

The delegation can be done for multiple interfaces at a time, too.

I created a [class decorator in Python](https://programmingideaswithjake.wordpress.com/2015/05/23/python-decorator-for-simplifying-delegate-pattern/) that did something like this a while back, since I liked this idea so much.

Kotlin practically needed something like this in the language to go along with a certain set of defaults...

## Final By Default

Classes and public methods are `final` by default in Kotlin, meaning they can not be inherited from or overridden, respectively. In order to make those options available, classes and methods must be marked as `open`.

This goes along with item 17 in Effective Java, namely “Design and document for inheritance or else prohibit it”, just as this entire item lines up with item 16 in Effective Java, “Favor Composition Over Inheritance”.

These two pieces work together quite nicely and will hopefully mean that code written in Kotlin will be of better quality because of it.

Many people are against the decision of final by default, but they largely seem to not realize that C# has had the same thing all along (without first-class delegation, I might add), and I haven’t heard any complaints from their side. I would venture to guess that most of the people that argue this point also don’t have a mindset of preferring composition. Obviously, there are some cases where this may backfire (especially if a library doesn’t provide an interface for a certain class you want to extend), but this should hopefully prevent more problems than it makes.

## Visibility Modifier Defaults

What is the most commonly typed word in all of Java? I would venture to guess that it’s `public`, with `return` and `import` in a close second place. For the most part, that `public` keyword is clutter; most things are public, and it should probably be the default. Kotlin decided to do just that, making the code cleaner.

Now, this does go against the general advice for encapsulation for making members of a class as private as possible, but even when this advice is followed pretty well, `public` is still the most common visibility modifier. So, thankfully, `public` is the default in Kotlin.

## Sealed Classes

Sealed Classes are a more interesting than useful feature, in my opinion. They provide a way to lock in a hierarchy so that the only subclasses of the sealed class are those defined within its borders. This is really handy for the split-type monads (such as Either and Maybe/Optional) and, judging by the example used in the documents, expression trees.

Again, Kotlin has an additional feature that plays well with this feature, and that’s the when expression, which is a reimagined `switch`. The `when` expression is made aware by the compiler to know whether all of the subclasses are presented so that it doesn’t force you to needlessly provide a default `else` clause.

Again, I find this feature more interesting than useful, especially since I worked out a (slightly more painful) way to largely recreate the same thing in Java (with an additional idea or two running through my head). If you’re curious how I do it, leave a comment letting me know, and I’ll do a post about it.

# Extension Methods

Extension methods are methods that you can “add onto a class” after the fact. I use quotes because they’re not actually added to the class (at least not in Kotlin); rather, they are a static methods that can be used as if they’re methods on an instance, allowing you to import those methods to be used only in certain instances and to discover them with autocompletion. But mostly it’s a syntax that makes static methods look less stupid.

Why are these not more of a thing in the programming world? All those `String` and `Date` utility classes would be so much easier to use if they could be used as extensions to the actual class. Other than C#, I don’t know of any other languages that do this (understandably, dynamic languages don’t actually need anything special for this feature, since you can literally add any method to a class any time you want). If you know of any, I’d appreciate it if you listed them in the comments.

Another awesome thing about extension methods is how Kotlin combines them with lambdas to make extension lambdas, which make certain things like [their](https://kotlinlang.org/docs/reference/type-safe-builders.html) [type-safe](https://programmingideaswithjake.wordpress.com/2016/01/16/mimicking-kotlin-builders-in-java-and-python/) [builders](https://programmingideaswithjake.wordpress.com/2016/01/23/kotlin-like-builders-in-java-and-python-continued-additional-parameters/) so much cleaner.

## Outro

That’s the end of *this* article. Tune in next week for the third post of [Kotlin Month!](https://programmingideaswithjake.wordpress.com/kotlin/#kotlinmonth) That one will go over the safety features that Kotlin has, such as null safety and improved generics.
"""

Article(
  title = "Kotlin Month Post 2: Inheritance and Defaults",
  url = "https://programmingideaswithjake.wordpress.com/2016/03/05/kotlin-month-post-2-inheritance-and-defaults/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 3, 5),
  body = body
)
