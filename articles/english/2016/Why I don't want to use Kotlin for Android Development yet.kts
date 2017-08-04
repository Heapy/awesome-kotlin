
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Even though Kotlin is better than Java in many points it still has significant (in my opinion) drawbacks.

> Please treat it as personal opinion & comment if you have solutions for problems listed below.

###### 1) Slow compilation

Small project (~100 classes in total, mostly in Kotlin) takes ~1 minute to assemble. This is simply unacceptable. [https://youtrack.jetbrains.com/issue/KT-6246](https://youtrack.jetbrains.com/issue/KT-6246)

###### 2) Performance of Kotlin plugin for IDEA

Syntax analysis and highlighting of Kotlin in IDEA (Android Studio) pretty often freezes development machine during typing, unacceptable.

###### 3) Problems with annotation processing

Sometimes it gives random errors and you have to do `clean`. Almost every day I see complaints about that on different resources. I'm not alone.

###### 4) Mocking Kotlin classes with Mockito is painful

Almost everything is `final` in Kotlin by default: classes, methods, etc. And I really like it because it forces immutability -> less bugs. But at the same time, it makes mocking via `Mockito` (which is kind of gold standard in JVM world) painful and goes contrary with language design.

Yes, PowerMock is possible solution, but it interferes with tools like Robolectric and generally it was always a good rule that you should not mock final classes and final method.

I understand that in Java we have that problem of everything non-final by design, but at the same time **I don't want to change code just for testing.**

###### 5) No static analyzers for Kotlin yet

Yes, `kotlinc` adds more safety to the code than `javac`, but if you want to achieve good performance of the compiler you don't want to turn it into static analyzer.

Static code analysis is good for CI, but probably you don't want to run it every time you click on `run` button in IDE during local development.

Java has: FindBugs, PMD, Checkstyle, Sonarqube, Error Prone, FB infer.

Kotlin has: `kotlinc`.

> Points above were objective, I hope. Points below are more subjective and personal.

###### 6) `==` does `equals()` instead of reference comparison

If Kotlin is "better" Java or "Java on steroids" then it should be _better_, but not _breaking_.

Imagine you're in the process of rewriting Java project to Kotlin, you will have Java and Kotlin code at the same time.

You'll have to read and write _same_ code that works differently from language to language. This is one of the reasons why I don't like Groovy.

###### 7) In bad hands operators overloading may lead to bad results

Statement 1: you will need to deal with old codebase written in Kotlin in future.
Statement 2: you can add operators overloading to **existing** java classes via extension functions.

Now imagine you see something like `val person3 = person1 + person2` in already written code you need to deal with.

Every project you'll work on may have its own meaning of operators for same classes 😿

Operators overloading is controversial, these links may help you decide (not all of them end with same conclusion):

* [Operator Overloading Considered Harmful](http://cafe.elharo.com/programming/operator-overloading-considered-harmful/)
* [Operator Overloading Ad Absurdum](http://james-iry.blogspot.ru/2009/03/operator-overloading-ad-absurdum.html)
* [Why Everyone Hates Operator Overloading](http://blog.jooq.org/2014/02/10/why-everyone-hates-operator-overloading/)

***

[@artem_zin](https://twitter.com/artem_zin)

"""

Article(
  title = "Why I don't want to use Kotlin for Android Development yet",
  url = "http://artemzin.com/blog/why-i-dont-want-to-use-kotlin-for-android-development-yet/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Artem Zinnatullin",
  date = LocalDate.of(2016, 3, 11),
  body = body
)
