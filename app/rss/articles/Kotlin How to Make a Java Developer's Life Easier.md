---
title: 'Kotlin: How to Make a Java Developer''s Life Easier?'
url: https://anadea.info/blog/kotlin-how-to-make-java-developers-life-easier
categories:
    - Kotlin
author: Stanislav Sinitsky, Alexander Mikhalchenko
date: Dec 7, 2016 02:12
---
![Kotlin](https://anadea.info/uploads/image_attachment/image/1025/xKotlin.jpg.pagespeed.ic.Wy-yOp4EYc.jpg)

On February 15, 2016 JetBrains company released version 1.0 of [Kotlin](https://kotlinlang.org/), a programming language that has been in development for about 5 years. This language works on JVM. We've already talked about [what Java is](https://anadea.info/blog/java-myths-realities-and-prospects-for-application-development) with our developer Alexander Mihalchenko. Today, we met again with Alexander, this time to discuss Kotlin - why Kotlin is good and if there are any drawbacks of using this language, both for developers and for entrepreneurs.

**Hello, Alex.**  
Hi to you too.

**So, what is Kotlin?**  
Kotlin is a programming language, developed by JetBrains company, running on the Java platform. It uses JDK, like Java itself but has a different syntax. The idea isn't new, there are already existing languages that do the same - Scala and Closure, for example. They appeared mainly because of issues with Java in terms of syntax. In other words, Java is a good, reliable and powerful language, you can use it to build server-side applications. However, its syntax, let's say, is unnecessarily wordy. The fact is that Java's developers upgrade it in a rather inertly manner. They are keeping maximal backward compatibility and consider that, when uses move to a new version of Java, a code written back in '95 should work for them anyway. They even have a slogan: "write once, run anywhere". Of course, this is more about cross-platform benefits of Java...

**... but they decided to dig deeper?**  
Yes, the code written on one version of Java will work on all following versions.

**A bold aspiration!**  
In fact, this is where the problem lies - they do not throw old things from the language. This slows down the development of Java. Over time, various trash comes up. There's even a label for outdated classes, which marks them inside the JDK. According to documentation, when such label appears it means that the old functionality may be ceased in the next versions, but I'm not aware of any occasions when it actually happened.

**Do developers recognize that these outdated methods aren't used anymore?**  
They understand and even insist that these methods should not be used. But as far as there is a software apps written on old versions, it's not possible to simply remove the old code. All those applications will get broken.

**So, what did Kotlin get rid of?**  
Kotlin and other JVM-languages help developers to write programs with less code. In addition to everything that exists in Java, they provide things from the world of functional programming. This greatly facilitates code writing, makes it more concise and expressive. The less code we write, the less of it needs to be maintained and the fewer tests we have to write. Actually, the majority of programming languages emerged because of this particular reason - to change Java syntax, make it more convenient and more pragmatic.

**Is the Kotlin language in a high demand now?**  
Currently, it is not very popular - the language is young, its community hasn't come together yet. Due to immaturity of Kotlin vs Scala they can't compete so far. Scala is a more powerful programming language with a long history. Scala has already passed the stage of fixing major errors and it has been updated to include a bunch of interesting stuff, while this have yet to be done for Kotlin. More mature languages have the upper hand in that for now. Nevertheless, Kotlin has a lot of ambitions. The language has been in development for five years. Developers tried to make it pragmatic and avoid mistakes that have been made in Java. So, that "write once" won’t be the major rule and the language will be easier to develop. In the future, they promise a lot of interesting features that will cover functionality currently available in Scala.

**Is it true that, like Java, Kotlin will suit better to large-scale projects?**  
Yes. By the syntax, it can be used just as an addition to Java. We can take the Java stack for server applications and simply replace Java with Kotlin. Everything will work and it'll be easier to write code. The question is what to choose instead of Java. Today, I think, the majority of developers will pick Scala.

**What's your personal opinion? Do you prefer Kotlin or Scala?**  
I like both languages. I think Kotlin will go further in respect of Android. Surely it's possible to write Android apps on Scala but there is a problem - a large runtime. It pulls behind a heap of libraries and because of that the size of apk-files increases. Kotlin already has the smallest runtime among the other languages (except Java). Typically, it's Java plus "something" and that "something" is an overhead that is trailed behind the language. Kotlin has smallest one - about 700 KB, while in Scala it is a few megabytes.

**What are the disadvantages of Kotlin?**  
I haven't yet come across any bugs myself and I had no problems with compiler or anything else as well. As for the syntax, it coped with all challenges I have encountered. The thing it yields to Scala is dealing with multithreading. Java, by the way, also loses in this. There is Future API in Scala. It allows you to perform asynchronous tasks in several streams. As a result, you get a normal code instead of hash of various sections operating in different streams without any visible relation between them. Scala structures all of it through the means of syntax. In Kotlin, something similar is promised to be implemented in version 1.1\. But generally, I like to use Kotlin due to its solid syntax.

**What can you say about safety of Kotlin?**  
Here's the thing: all the code written in Kotlin is then converted into a Java code. It adds nothing from itself. Accordingly, Kotlin is safe to the same extent as Java. If we are speaking about safety of writing code, I really like Null safety in Kotlin programming. The compiler can warn coder that the reference may be empty. If we try to do anything with it, an error occurs at the compiling stage. In Java, application simply crushes at the implementation stage.

**What can Kotlin offer to business? What benefits does it provide to entrepreneurs?**  
For business, the key benefit of Kotlin vs Java is the fact that the development on Kotlin is cheaper. Why? It requires to write less code and as a result you get fewer code to maintain. This means that it is easier to write code and consequently the application can be developed faster. Time is money, after all.

**Small projects and Kotlin.**  
If we are speaking about small desktop apps, for commercial use it is better to choose C++. Yet again, Kotlin code is subsequently compiled to Java bytecode, therefore, as far as Java is good in some area, so good Kotlin as well. Kotlin is more helpful for writing programs, so it brings direct benefits primarily to coders.

**Thank you very much for the interview, Alex. It was interesting!**  
My pleasure.

## Summary

In fact, Kotlin's direct purpose is to facilitate life of coders, give them opportunity to use one code line where Java needs five lines. As Alexander said, it's like changing an old Ford Focus to a brand new BMW M3 - when you feel that flight you won't come back to the old wheels.

One of the undoubted advantages of Kotlin is the full compatibility with Java, including backwards one. All libraries for Java will work on Kotlin and vice versa. Also, this language reveals the charms of Closure and Scala for Android developers. Entrepreneurs will be pleased as well as now software projects will be delivered faster and cheaper.

The only drawback of the language is its young age. However, considering the fact that JetBrains spent five years on its development, they will unlikely abandon their brainchild any time soon. Therefore, it is a good idea to start adopting Kotlin right now, especially if you're developing apps for Android.
