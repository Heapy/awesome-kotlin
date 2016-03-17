---
title: 'Exploring Kotlin'
url: http://blog.cacoethes.co.uk/software/exploring-kotlin
categories:
    - Kotlin
    - Groovy
author: Peter Ledbrook
date: Jun 27, 2015 21:54
---

_This post is outdated, because Kotlin have a lot changes from June, 2015_

Welcome to the start of an intermittent series of blog posts where I just talk about various bits of technology that I’m in the process of discovering. Don’t expect much in the way of teaching, but do expect much in the way of erroneous comments as I discuss stuff I don’t yet properly understand.

I’m starting the series with Kotlin, one of a new wave of languages for the JVM. I say wave, but the only other one I can think of is Ceylon. Still, Kotlin and Ceylon are important because, as I understand it, they both aim to provide a solid alternative (AKA replacement) to Java. As you’d expect if you follow me, this post has a definite Groovy flavour with most of the comparisons between that and Kotlin.

So why am I looking at Kotlin anyway? Partly to expand my horizons, partly in search of a statically-typed alternative to Java that I’m happy with, and partly because I think it has a reasonable chance of picking up a sizeable following. It seems like a pragmatic evolution to Java that respects the need for good Java integration and doesn’t introduce anything that’s terribly hard for an older programmer like me to understand. It does include a whole bunch of features I like.

## There is no static

Sometimes the best way to learn about a language is to just jump into the deep end. In this case, I thought I’d try to migrate my Lazybones project from Groovy to Kotlin. I do have a small ulterior motive for doing this: the Lazybones tool has a small but noticeable startup delay when running it. If I can eliminate that, I’ll be very happy.

You can follow my progress on [GitHub](https://github.com/pledbrook/lazybones-kotlin). One thing to bear in mind is that the code would probably look a bit different if I started with Kotlin. I tend to rely on Groovy’s dynamic features, which don’t translate so well to a statically-typed language. All the more surprising then that the migration has gone fairly smoothly.

The first thing that struck me though, and had me scratching my head for a while, was the complete lack of the `static` keyword. It’s not even available on the `import` statement. I first came across `static` a long time ago when I was learning C and it took an embarrassingly long time to work out what it did. Almost as embarrassing as not knowing how many ‘r’s are in ‘embarrass’. It did eventually click and that understanding has helped me through C, C++, Java and Groovy. So I was a bit at sea initially, especially as I had lots of `static`s dotted around the code base.

After that initial shock, I admit I quickly came around to Kotlin’s way of thinking. The `static` keyword is mostly a kludge in Java and has a variety of use cases that could be handled differently. For example, functions can’t exist independently of a class, even if they don’t belong on an object. This is the tyranny of Java’s particular Object-oriented viewpoint. Kotlin eliminates this use case by allowing functions defined outside of classes. The same goes for constants. Sharing data between objects of the same type can be achieved through companion objects. If you absolutely need a static field or method, for example when you’re calling Kotlin code from Java, you can use a `@platformStatic` annotation.

## The good stuff

Subsequent work highlighted a lot of similarities with Groovy, such as the Elvis operator (not sure if it’s called that in Kotlin), null-safe navigation, lambda function syntax, embedded string expressions, and multi-line strings. The Kotlin extension methods for the standard types are also a reasonably close match to those of Groovy, although Kotlin thankfully uses the more standard names such as ‘map’, ‘fold’ and ‘filter’. Just remember to add the ‘kotlin-stdlib’ JAR to your project’s classpath, or you’ll miss out on a lot of essential features like I did and remain confused as to why none of the examples on the web work for you.

On the flip side, there were a lot of repetitive tasks I had to undertake during the migration. Types always go after variable and function declarations, which allows for the essential `fun`, `val` and `var` keywords, but does result in a lot of cutting and pasting. Kotlin also handles properties differently. The `get()` and `set()` syntax is nice, but getters and setters on Java and Groovy code are not treated as properties. This means you have to invoke the methods explicitly. Yuck.

Beyond the feeling of familiarity, Kotlin provides some other very pleasant surprises. Unlike Java 8, you don’t need to define or use an interface to take advantage of lambdas:

```kotlin
enum class NameType(val intermediateType : NameType?,
                    val toIntermediateFn : (String) -> String,
                    val fromIntermediateFn : (String) -> String) {
    ...
}
```

Simply use the syntax `(<arg types>) -> <return type>` on your properties or function signatures.

The most far-reaching feature is Kotlin’s null handling. Types come in two forms: those that allow null values, and those that don’t. It’s easy to distinguish between them because the former have a ‘?’ suffix, for example `String?` vs `String`. The compiler then checks that you don’t attempt to use a potentially null value for an argument or variable that can’t be null. This causes all sorts of fun when using non-Kotlin libraries because almost every value can potentially be null, but Kotlin provides some nice get out of jail free cards for such occasions. And most importantly, it forces you to really think about when and where you want to allow null values and how you should be dealing with them.

I won’t go into any more details, but what could have been a really frustrating feature when working with existing code and libraries turned out to be less bothersome than expected once I’d become familiar with most of its aspects. It even highlighted bits of code that probably weren’t handling nulls properly. I was particularly shocked at how laissez-faire my Groovy code appeared to be with regard to nulls.

**Update** Jetbrains provide a tool called [KAnnotator](https://github.com/jetbrains/kannotator) that analyses Java libraries for whether they accept and/or return nulls. This makes the libraries much easier to integrate into Kotlin code. Thanks to Rob Fletcher for the pointer.

The compiler is also a lot more intelligent than Java’s (I suspect at the cost of compilation speed). It follows branches appropriately and can tell when they return with what types. This picked up at least one bug in my code, albeit nothing major. It also seems to be aware of null and `instanceof` checks (although in Kotlin these use is instead). That means you don’t need an explicit cast or null-safe navigation after such a check. Unfortunately this feature only seemed to work some of the time and I couldn’t work out why it didn’t in those cases. Hopefully the edge cases will be ironed out or at least well documented as Kotlin approaches 1.0.

## What I miss

It’s not all great for a Groovy developer like me. The number one concern is that I can’t seem to mix the two languages in the same project. At least with the Gradle integration the Kotlin compilation takes place before Java, which in turn comes before Groovy. My limited understanding suggests that you can use Kotlin-compiled code from Groovy, but not vice versa. This is a shame as Groovy is particularly strong in dealing with configuration data, JSON, and the like.

I initially thought that Kotlin’s `dynamic` keyword, which disables type checking on the marked object, would give me similar behaviour to Groovy in such cases, but IntelliJ IDEA kept on saying that it couldn’t be used in the current context. Err…what? What context would that be? It transpires that in this case the context is the JVM! In other words, `dynamic` only seems to work for KotlinJS (a version that compiles to Javascript). This seems like a lost opportunity considering the addition of `invokedynamic` to the JVM. Perhaps the implementation would complicate the language or maybe the language designers want to maintain Java 6 as the minimum JVM version.

I also desperately miss Groovy Truth. This allows you to treat various values, such as empty strings, as if they were equivalent to the boolean `true` or `false`. For example, if I have a nullable string and just want to check whether it contains at least one character I prefer:

```groovy
if (str) doSomething()
```

rather than

```java
if (str != null && str.isNotEmpty()) doSomething()
```

The kludge I’ve gone for seems a bit dangerous as I don’t know how widely applicable it is:

```kotlin
if (str?.isNotEmpty() ?: false) doSomething()
```

Anyway, I’m hoping that I’ve missed a particular feature of Kotlin that prevents the return of the dreaded explicit null checks.

List and map literals would have been nice, but methods like `arrayListOf()` and `hashMapOf()` do the job fine. Regular expression literals plus something like the `=~` operator would also have made my life easier, but the Kotlin approach isn’t massively worse. In fact, the `Regex` class is growing on me.

Lastly, I really wanted to make use of method references in the `NameType` class, but I just couldn’t get them to work the way I want. I suspect that method references on instance methods have a different signature to function references. Unfortunately, this area of Kotlin isn’t particularly well documented. That’s not too surprising when you consider that the language hasn’t even hit 1.0 yet, but I do think that adoption is only for the brave right now.

## Do I like it?

When all is said and done, I’m pleasantly surprised by Kotlin. Although I perhaps hadn’t realised how reliant I was on some of Groovy’s more dynamic features, the transition to a fully statically typed language wasn’t nearly as bad as it had every right to be. There were a few struggles, but nothing that kept me from almost completing the transition in a short timeframe. The only thing left to do is convert a couple of configuration-related classes – probably the hardest ones to do over all.

One final thing I learned is that I hate generics. Really. I think Kotlin’s ‘*’ wildcard was the only thing that helped me retain my sanity.

As I said at the beginning of the post, Kotlin is an evolution rather than a revolution. That’s why I think it has a reasonable chance of success. Will it prove a significant competitor to Java? I don’t know, but I doubt it. There is so much inertia around Java that sometimes it feels like nothing will supersede it. Nonetheless, Kotlin is definitely worth an investigation if you’re looking for a statically-typed alternative to Java for your own projects. As I understand it, Jetbrains are pretty reliant on it, so it’s unlikely to disappear soon if at all. And you’re guaranteed first class support in at least one IDE!

Do remember that this is a first impressions account of the language, so don’t take anything as gospel truth other than these were my experiences. I’ll try to post additional articles as I gain a better understanding of the language.
