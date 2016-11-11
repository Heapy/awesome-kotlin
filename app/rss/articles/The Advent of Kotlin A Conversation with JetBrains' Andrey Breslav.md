---
title: 'The Advent of Kotlin: A Conversation with JetBrains'' Andrey Breslav'
url: http://www.oracle.com/technetwork/articles/java/breslav-1932170.html
categories:
    - Kotlin
author: Janice J. Heiss
date: Apr 02, 2013 00:00
---
**Learn about Kotlin, a new statically typed language for the JVM.**

_Among the important Java software developments in recent years is the advent of such alternative languages for the JVM as Groovy, Jython, and JRuby—and, more recently, the rise of Scala. A new statically typed language, Kotlin, named after a Russian island off the coast of St. Petersburg, where Kotlin's Andrey Breslav and the Kotlin team reside, has recently been getting attention. A brainchild of the highly lauded Czech software development company JetBrains, maker of the Java IDE IntelliJ IDEA, Kotlin was named Language of the Month in the_ [_January 2012 issue of_ Dr. Dobb's Journal](http://www.drdobbs.com/jvm/language-of-the-month-kotlin/232600836?pgno=2).

_The primary purpose of Project Kotlin is to create for developers a general-purpose language that can serve as a useful tool that is safe, concise, flexible, and 100 percent Java-compatible. Both the compiler and the IntelliJ IDEA plug-in are open source under the Apache 2 license, with source code available through [GitHub](https://github.com/JetBrains/Kotlin)._

_Google software engineer Cedric Beust, creator of the Java testing framework TestNG, writing in [Javalobby](http://java.dzone.com/articles/five-reasons-why-you-should-0) in July 2011, commented that Kotlin was a cause for rejoicing because of its syntactic innovations; its strong IDE; commercial support from JetBrains, which is building the compiler and the IDEA support together; and its reified generics._

_Andrey Breslav, the lead language designer for Kotlin, began his career at Borland, where he worked on language implementations for MDA support. After spending a few years as a college teacher, he joined JetBrains in 2010 to lead Project Kotlin and currently serves as a member of the Java Community Process Expert Group for JSR 335, "Project Lambda." He is a frequent conference speaker at venues such as OSCON, JavaOne, Strange Loop, and Devoxx._

_We met with him to see what's happening with Project Kotlin._

![Andrey Breslav](http://www.oracle.com/ocom/groups/public/@otn/documents/digitalasset/1925119.jpg)

**Oracle Technology Network**: You have written that your goal with Kotlin is "to create a language that is 100 percent Java-compatible, safer than Java, more concise and flexible, and not overly complex." Give us some details about this.

**Breslav:** Every tool comes with a driving use case. We believe that for a new programming language, this use case is gradual migration, when the new language is being introduced little by little into a large code base written in the "old" language. In our case, we pay a lot of attention to making mixed Kotlin/Java projects work smoothly.

For example, IntelliJ IDEA is written in Java and we are willing to use Kotlin in this project. Of course, we are not going to rewrite the whole 10-year-old code base in another language. Most likely, we will start by writing tests in Kotlin, then new features, then maybe some existing subsystems that will be migrated to Kotlin while under refactoring.

> “Kotlin relies on Java libraries but makes them better, mostly through extensions but sometimes with compiler-supported techniques (collections, arrays, primitives).”
>
> **- Andrey Breslav** JetBrains

All of this means that Kotlin has to behave very well in the existing Java ecosystem: use Java libraries, provide Java APIs, and integrate with Java frameworks. Some other new languages adopt a different approach and aim at abandoning the JDK and building their own better platform. Although this approach involves running on the JVM and is technically Java-compatible, it is practically impossible in the use case I just described.

As for Kotlin, a good example is our collection library. The experience of Scala shows that having your own collection library—all nice and shiny—gives you significant benefits but also has drawbacks when you need to interoperate with Java code: all the data needs to be converted/wrapped, so the code does not look nice and performance is sometimes compromised significantly. This is why Kotlin adopts another strategy: we use vanilla Java collections but make them behave nicely by extracting "virtual" read-only interfaces and introducing declaration-site variance. This way we get both compatibility and a nice library.

Kotlin relies on Java libraries but makes them better, mostly through extensions but sometimes with compiler-supported techniques (collections, arrays, primitives). This gets us compatibility while keeping the language clean.

## Kotlin and Scala

**Oracle Technology Network:** In what ways is Kotlin simpler than Scala?

**Breslav:** It depends on the definition of _simpler_. Kotlin and Scala have very different approaches: Scala aims to give library designers as much power as possible and relies heavily on a rather elaborate type system, whereas Kotlin's goal is to be a good tool for the end user, so we put a lot of effort into keeping the list of features relatively short.

Here are a few things that Scala has and Kotlin does not:

*   Implicit parameters and implicit conversions
*   (Path-)dependent types
*   Full-scale existential types
*   View bounds, context bounds, and all other exotic bounds
*   Macros

This list is very far from complete.

> "Kotlin provides more-flexible abstractions than Java (including Java 8)."
>
> **- Andrey Breslav** JetBrains

**Oracle Technology Network:** In what ways is Kotlin more concise than Java?

**Breslav:** The less important part is that Kotlin generally requires less ceremony. A few examples:

*   Type inference is much stronger, so you don't have to repeat yourself, specifying the same types over and over again.
*   Class declarations are more concise, thanks to the concepts of properties and primary constructors.
*   Delegating overloads are not needed most of the time, thanks to default values for function parameters.
*   Static utility classes are not needed, because of top-level functions.

Of greater importance, Kotlin provides more-flexible abstractions than Java (including Java 8). Here are some examples:

*   Extension functions and properties in Kotlin can be added to any class/type without altering the definition of the class (as opposed to Java 8's ability to add methods to interfaces by changing their code). This enables us to beautify even existing Java libraries so that the good old JDK looks nice and shiny.
*   Higher-order functions (passing code around as values) are a lot more convenient, because Kotlin supports proper function types (as opposed to Java 8's SAM conversions that make you create a new interface every time you need a new function signature to be passed around).
*   Declaration-site variance, and variant collections in particular, make common data processing much more natural by eliminating the need for ubiquitous wildcards in generic types.

**Oracle Technology Network:** What is the most negative feedback you have received about Kotlin?

**Breslav:** "It's not released yet."

**Oracle Technology Network:** What is the most positive?

**Breslav:** "One language finally got everything right!"

## The Release Status of Kotlin

**Oracle Technology Network:** What is the release status of Kotlin? How can developers get involved?

**Breslav:** Kotlin reached the M5 milestone in early February 2013. As for big features, we are mostly stabilizing them and working on performance. Smaller features are being added, too. The project is open to contributions, and we do our best to process pull requests reasonably quickly.

We do not have a date for the 1.0 release. This is an important part of our philosophy. When the features planned for 1.0 are stable and the compiler is release-quality, we are not going to call it 1.0. It will be a beta, available to everyone to use but with no guarantee of backward compatibility for the updates. The point here is that at the "beta" stage, a language finally gets to be used in production (maybe other companies will refrain from doing so at first, but at least JetBrains will definitely use Kotlin), and extensive production use may reveal problems in the language design that need to be fixed.

Don't get me wrong: we are making all possible efforts to make the language really good. We rely on the experience of our best engineers and on the feedback we are constantly gathering from other companies and individuals. But we know that practice always brings surprises overlooked by theory, so we embrace the need to validate the design before releasing it and committing to keeping it unchanged. This is why we do not have a date for 1.0 and do not guarantee language design stability right after the beta: it's hard to predict what changes will be necessary.

When the design is properly validated and all the problems fixed, we will call it 1.0 and guarantee language stability from that point on.

**Oracle Technology Network:** JetBrains is currently using Kotlin in the production of IntelliJ IDEA and in other projects. Tell us how Kotlin is doing with IntelliJ IDEA. What are some of the challenges?

**Breslav:** First, only very brave souls are using Kotlin for production now. And the bravest among them use the less mature JavaScript back end that compiles Kotlin to run in the browser. There are very many challenges, but the result is awesome: [IntelliJ IDEA's Live Edit](http://blog.jetbrains.com/webide/2012/08/liveedit-plugin-features-in-detail/).

## Enhancing Java Libraries

**Oracle Technology Network:** Give us some examples of ways in which Kotlin can enhance Java libraries.

**Breslav:** First, the way Kotlin treats collections: it uses standard JDK classes at runtime, but at compile time, it introduces "virtual" read-only interfaces that are covariant, so that a (read-only) `List<String>` can be passed where a (read-only) `List<Object>` is expected.

Also, Kotlin introduces many extension functions for collection classes. For example, the `map()` and `filter()` functions allow fluent manipulation with data:

```kotlin
users.filter {u -> u.age >= 21}.map {u -> u.name}
```

Many other things are done through extensions: from `for`-loop support to operator overloading and tuplelike manipulations.

## Other Alternative Languages

**Oracle Technology Network:** Where does Kotlin fit in among the proliferated new languages for the JVM? How does it compare to Groovy, Jython, JRuby, Scala, and others? And with so many languages available to us, why do we need Kotlin?

**Breslav:** Groovy, Jython, and Ruby are primarily dynamic, whereas Kotlin is statically typed, which means the ability to catch more errors at compile time (type safety), faster execution, and smarter tools.

Groovy 2.0's static-compilation feature makes the generated code perform comparably to Java, and this is a great improvement for Groovy. But the type system it supports is the one Java uses, meaning that all the abstraction mechanisms are exactly the same—for example, you get Java generics with all the inconveniences of use site variance and so on.

Scala is a different story—it is a statically typed language—and we've learned a lot from Scala's design. Compared to Kotlin, Scala is much more powerful and much harder for people as well as machines to really understand.

**Oracle Technology Network:** Tell us about IDE support in Kotlin.

**Breslav:** We started an IntelliJ IDEA plug-in for Kotlin simultaneously with the compiler, and we reuse a lot of code between the two. This guarantees very accurate analysis in the IDE on the one hand and makes it possible to use IntelliJ IDEA's existing infrastructure for Java.

**Oracle Technology Network**: What are the most important features of Kotlin that Java lacks?

**Breslav:** Assuming that by "Java" you mean Java 8, and with the biggest pain of the decade, lambdas (frequently called "closures"), being there already: the most important features are powerful type inference, extensions, declaration-site variance, and read-only interfaces for collections.

On the library side: type-safe builders are also important.

## Getting Set Up with Kotlin

**Oracle Technology Network:** How can developers get set up with Kotlin? What feedback do you want?

**Breslav:** Download the compiler and/or a plug-in for IntelliJ IDEA, and write your cool application. We want feedback about what people like and dislike about Kotlin, what they find hard to understand, and how they use the features the language offers.

**Oracle Technology Network:** What kind of Java developer will benefit most from Kotlin?

> "We want feedback about what people like and dislike about Kotlin, what they find hard to understand, and how they use the features the language offers."
>
> **- Andrey Breslav** JetBrains


**Breslav:** A Java developer who has reached the limits of the language (and I believe that many of us did that a long time ago) realizes the need for a new language with benefits such as cleaner abstractions, concise syntax, and type safety.

**Oracle Technology Network:** In what ways is Kotlin safer than Java? What safety problems in Java does Kotlin address?

**Breslav:** Kotlin promotes null safety, though nullable types. It also offers control over data modification through read-only collections and data classes and enables safer runtime checks through smart casts.

**Oracle Technology Network:** What are some Java use problems that Kotlin fixes?

**Breslav:** It fixes half of the issues covered in Joshua Bloch's _Java Puzzler_s books: from quirks of implicit conversions of primitives, through problems with static members and their inheritance, all the way to properly variant generics and null safety.

**Oracle Technology Network:** What are the greatest misconceptions you encounter about languages for the JVM and Kotlin in particular?

**Breslav:** We come across the term _functional programming_ all the time, and people often mean different things by this, if anything at all.

Bigger functional languages such as ML and Haskell impose a certain discipline and beauty that are highly appreciated by some people, but this is clearly not the reason why your coworker in a nearby cubicle will be talking to you about functional programming over lunch.

The main selling point of "functional programming for the masses" is its usefulness for concurrency that everybody is struggling with today, and the argument for this usefulness usually boils down to "no side effects equals no problems" or "no shared memory equals no problems."

By this reasoning, all we need is immutable data or isolation (or both), but that does not necessarily bring any of the beauty and rigor of functional languages. Other things widely used in functional languages would be great to adopt in mainstream programming—this applies mostly to means of type-safe abstraction, such as higher-order functions (functions that take other functions as arguments) and parametric polymorphism (generics). But these features, again, do not define functional languages.

My point is: a good language does not need to be functional like Haskell, although it needs some features that are popular in the "functional world."

## See Also

* [January 2012 issue of _Dr. Dobb's Journal_](http://www.drdobbs.com/jvm/language-of-the-month-kotlin/232600836?pgno=2)
* [GitHub](https://github.com/JetBrains/Kotlin)
* [Javalobby](http://java.dzone.com/articles/five-reasons-why-you-should-0)
* [IntelliJ IDEA's Live Edit](http://blog.jetbrains.com/webide/2012/08/liveedit-plugin-features-in-detail/)
* [Kotlin](http://kotlin.jetbrains.org/)
* [JetBrains](http://www.jetbrains.com/)

## About the Author

Janice J. Heiss is the Java acquisitions editor at Oracle and a technology editor at _Java Magazine_.

## Join the Conversation

Join the Java community conversation on [Facebook](https://www.facebook.com/ilovejava), [Twitter](https://twitter.com/#!/java), and the [Java Source Blog](https://blogs.oracle.com/java/)!
