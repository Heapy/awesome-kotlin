---
title: 'Kotlin: How to Make Your Java Code Base Cleaner and More Readable'
url: https://medium.com/@kford55/kotlin-why-i-feel-its-useful-in-a-java-only-code-base-206bdb37c79#.4jgdz27kr
categories:
    - Kotlin
author: Kenneth Ford
date: Oct 27, 2016 01:10
---
TLDR;

Kotlin is a JVM language developed by JetBrains that compiles to Java 6 Bytecode. It is interoperable with Java and allows you to work with both languages in the same project. It lets you take advantage of a lot of great features in a legacy code base, such as lambdas, filters and streams, null safety, and top class IDE support as well. [https://kotlinlang.org](https://kotlinlang.org/)

This post was inspired by Brent Watson’s ([@brent_watson](https://twitter.com/brent_watson)) presentation at the last New York Android Developers meetup at Facebook where he talked about changing the I/O schedule app into Kotlin. Although most of the talks and articles about Kotlin have been in an Android context, it can still be useful in a regular java environment.

Kotlin is a JVM Language built by JetBrains, the same people who make IntelliJ. It was unveiled in 2011 but really starting to pick up traction now, mainly from the Android community. This is for a couple of reasons, first is up until Android 7 you had to use Java 7\. If you wanted to get some of the features of Java 8 you had to use third-party libraries such as Retrolambda. The other reason is that it’s a very small library, so you put very little pressure on the method count limit for being a single DEX app. (~65k public methods) Using Kotlin for Android lets you do things like lambdas, higher-order functions, streams and have null safety in your app.

So why am I writing about it when I work in mainly a non-mobile environment? Because it’s interesting to me and there are a lot of people who are using it within regular Java code as well!

To start let’s talk about tools. Without tools, languages or libraries overall are useless and won’t get used. Luckily for us! Kotlin was built by JetBrains. The good part about deciding to use Kotlin is that you can do it one file at a time. [Brent](https://twitter.com/brent_watson) talked about how they transitioned their Android app to Kotlin, and they started by converting Unit Tests to Kotlin and then with real source code. It’s very easy to start messing around and using Kotlin. The best part is you don’t have to do anything to convert these files! (Well maybe a little bit)


![](https://d262ilb51hltx0.cloudfront.net/max/800/1*ETOr2IP8cjeHohYpi-zYHQ.png)

_IntelliJ’s tool to convert Java files to Kotlin_

Command-Shift-A, Convert Java File to Kotlin File, and you’re done! It converts everything into working Kotlin code unless you’re using some libraries and frameworks then you’ll have to take a minute and mess around with some things. Either way, it’s still very quick and painless. Now it won’t create the most idiomatic Kotlin code, but it’s a start. And it’s the best place to really start learning Kotlin. Whenever you convert a file you’re able to go through with a full understanding of what the code does and start to learn how Kotlin handles different parts of your Java code.

So why would we want to even begin to use Kotlin instead of Java? Well, we get a lot of things free out of the box with Kotlin that can make our code a lot cleaner and easier to read. The first thing to recognize is that if you have a class that just has properties, like a class to represent a JSON object, Kotlin has what they call Data Classes. It generates getters/setters, hashCode, toString, and equals in the background and into bytecode. So a large class with a ton of boilerplate collapses to one line.

```kotlin
data class User(val name: String, val age: Int)
```

I found that a lot of methods in classes can collapse to one line due to the null safety features in Kotlin.

We’ve all seen code like this in any Java code base

```kotlin
if (someObject != null && someObject.getSomeOtherObject() != null) {
    String someString =   someObject.getSomeOtherObject().getSomeString();
        if (someString != null) {
            return someString;
        }
    return “”;
}
```

Just so much unnecessary code which can get very confusing, especially when this is everywhere in your code base. In Kotlin it turns into this:

```kotlin
return someObject?.getSomeOtherObject()?.getSomeString() ?: ""
```

So what we see here are a few things. To give some context you can set variables to either have a null option or not. So in this code sample we’re assuming that someObject can be null, and so can someOtherObject. The ? mark gives you a safe call, so if it is null it just returns null instead of blowing up with a NullPointerException. Now the ?: is called the elvis operator and if the expression on the left side is null, it returns whatever is on the right side. The two code snippets do exactly the same. So imagine going through a lot of the major classes you may have in your legacy Java apps and being able to cut the lines of code down by a huge amount? It would make things much easier to follow, read, and understand.

This is the major feature that I think is really useful and helpful as you basically eradicate Null Pointer Exceptions. The best part is that IntelliJ is smart enough to know if you are not making a safe call with something that is nullable, so you get compile time errors! Add this to the ability to write lambdas, streams and filters, OOP and functional paradigms… all in Java 6 bytecode! Most things I’ve seen show that it compiles in about the same time as Java, so you won’t see a major increase in compile or build times which is important with some of these big major applications.

Overall as you can probably tell, I think Kotlin is a pretty cool new language that may allow a lot of people to transform some of their older code bases into more concise and easier to read code without much work at all and without the need for it to be all or nothing. Along with this, it’s not as huge of a paradigm shift as something like Scala, so it’s a lot easier to get a team who is new to Kotlin to start using it and understand it.

I didn’t go through everything in the language, so definitely check out the docs and other links I’ll have below.

Useful Links:

* Kotlin Website: [https://kotlinlang.org/](https://kotlinlang.org/)
* Kotlin Koans (Good interactive tutorial): [https://kotlinlang.org/docs/tutorials/koans.html](https://kotlinlang.org/docs/tutorials/koans.html)
* Amazing talk by Jake Wharton on Kotlin (Android): [https://www.youtube.com/watch?v=A2LukgT2mKc](https://www.youtube.com/watch?v=A2LukgT2mKc)

Thank you! :)
