---
title: 'Building DSL Instead of an IDE Plugin'
url: http://jonnyzzz.com/blog/2016/09/02/dsl-building/
categories:
    - Kotlin
    - DSL
author: Eugene Petrenko
date: Sep 02, 2016 03:04
features:
    - mathjax
---
An alternative way of doing IDE support without any IDE plugin code for not yet supported and specific languages.

Problem Statement
=================

Most of us use quite smart IDEs daily with languages we use for development. IDE helps us not only
to highlight keywords. It is able to check types, show errors, do refactoring, complete and analyze code.

Of course we spent some time working with languages that are not (or weakly) supported by an IDE. Those languages
could be used for configurations files, logger settings, business rules, scripts, text files and so on.
This is a specific domain where a language is used. It can be hard to read code on that language, it can
be also hard to write it too. One may easily waste time writing incorrect code.

It's the lack of decent IDE support for such languages. Let's see how we can solve it

I will use \\(Original Language\\) term to refer to an unsupported language. 

A Trivial Solution
==================

Say we have an \\(Original Language\\) we use, and there is no decent support for it in IDE. 

The very first solution is to replace the \\(Original Language\\) with some other language. 
 
Suppose the \\(Original Language\\) cannot be thrown away. And it's likely there is a code on that language. 
There has to be a system that accepts it too.

Okay. We may implement a plugin for our an IDE to support it.

Writing a plugin is a complicated task. One needs to know IDE API. Moreover, you'll implement a parsing 
so that it'd be fast enough to play well in IDE. The parser should also support invalid text input too, e.g. 
incorrect code that is being entered. That can be tricky.

Well, a parser may not be necessary, if, say IDE support some base language, e.g. `.properties` or `.xml` format.
But in that case a deep IDE APIs has to be used.

Finally, a plugin will depend on IDE. Meaning it has to be updated to work with newer IDE versions. It's not in
our control to lock an IDE version.

This can be done, but, there's an **alternative way**.


The DSL Way
===========

Everyone uses their IDE. And of course there is a language we use daily. For example, it could be
[Java](http://www.oracle.com/technetwork/java/index.html),
[Scala](http://www.scala-lang.org/),
[Kotlin](https://kotlinlang.org),
[Go](https://golang.org/), 
[Groovy](http://www.groovy-lang.org/).
We know for sure, an IDE is smart on those languages.

My idea is to an \\(Original Language\\), which is unsupported by an IDE, into a code in a supported language. 
I will use a \\(Target Language \\) term for it. Next, run the 
code to have a code in a \\(Original Language\\) back. As shown on the diagram below:

![](https://i.imgur.com/w5a5xx5.png)

To implement it we need 
- \\(generator\\), a program that reads a valid code in the \\(Original Language\\) and generates a code in the \\(Target Language \\)
- \\(API Library\\), a peace of code that includes all definition required for a generated code to be more readable
- \\(execute\\), a peace of code that makes compiled generated code in the \\(Target Language \\) to emit a code in the \\(Original Language\\)

The goal of those transformations is to have a readable \\(Generated Code \\). Refactorings for \\(Generated Code \\) 
in \\(Target Language \\) are projected to the \\(Original Language\\). 

Selecting a Target Language
===========================

Most we need from a \\( Target Language \\) is a decent IDE support.
 
We shall select a \\( Target Language \\) with a good readability for everyone. 
Languages with better DSL or fluent APIs capabilities are likely to play better.

Static typing is a nice to have. It makes it harder to make a error. Moreover, 
it's likely to require no setup for an IDE to work. For some dynamic languages
an additional [helper](https://confluence.jetbrains.com/display/GRVY/Scripting+IDE+for+DSL+awareness) may be required.
 
The DSL Way Benefits
====================

The DSL Way approach projects all features from an \\( Target Language \\) into a \\(Original Language\\). 
We can say an \\(Original Language\\) is extended with all possible high-level features of a \\( Target Language \\).
For example, we can use functions, loops, conditions, libraries and so on. There is no need to 
have a support for such constructs in the \\(Original Language\\).
One may also tune a \\(generator\\) to say fold duplicates in the input into a loops or functions.
 
Thanks to \\(API Library\\) we have good readability. It's a creativity part here. But if implemented correctly, 
it improves readability drastically.

The parsing is easier to implement for The DSL Way approach. We only need to have it supporting valid inputs. By induction,
we start with a valid code and generate a valid code. It's way more complex for The IDE Plugin case. 

Refactorings and code analysis features for a \\(Target Language \\) are projected to a \\(Original Language\\). All 
what is needed is to design \\(API Library\\) and \\(generator\\) in the right way. Say you need a *find usages* 
for some domain object. To have it, you may make a \\(generator\\) declare and use a variable for such entities. 
Once variables are used, rename feature is implemented for free.

Example
=======

Let's consider [Log4j](http://logging.apache.org/log4j/1.2/) configuration in `.properties` file as \\(Original Language\\).

```properties
log4j.rootLogger=ERROR,stdout
log4j.logger.corp.mega=INFO,stdout
# meaningful comment goes here
log4j.logger.corp.mega.itl.web.metrics=INFO

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n
```

I decided to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) as IDE 
and [Kotlin](https://kotlinlang.org) as \\(Target Language \\).
 
The configuration below can be turned into the following Kotlin code.

```kotlin
log4j {
  val stdout = appender<ConsoleAppender>("stdout") {
    layout<PatternLayout> {
      conversionPattern = "%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n"
    }
  }

  rootLogger {
    level = ERROR
    appenders += stdout
  }

  logger("corp.mega.itl.web.metrics") {
    level = INFO
  }

  logger("corp.mega") {
    level = INFO
    appenders += stdout
  }
}
```

The evaluation of this Kotlin program yields the similar `.properties` file back. 

The DSL code is way easier to read and to write.

Thanks to Kotlin static typed DSLs, IntelliJ IDEA understands the code well, warns all incorrect usages,
code complete all possibilities. 

Moreover, using such DSL is the way to implement automatic tests for your log4j configurations.
One may easily setup automatic tests task as a part of their continuous integration, since the code has no dependency on 
an IDE part. 
 
It opens the way to reuse common configuration files as well as to run. 

Conclusion
==========

In the post we discussed The DSL Way of doing an IDE support **without** writing any IDE related code. 
The created DSL can be used not only with IDE. It can easily be re-used in any possible applications like code sharing,
automatic test, continuous integration and so on.

You may follow to [this post](http://jonnyzzz.com/blog/2016/03/08/gradle-for-dsl/) for details on how to create decent package
for The DSL Way toolset

*PS*. Do you have more examples for \\(Original Language\\)? Please share in comments below
