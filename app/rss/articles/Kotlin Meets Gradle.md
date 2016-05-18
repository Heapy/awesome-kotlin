---
title: 'Kotlin Meets Gradle'
url: http://gradle.org/blog/kotlin-meets-gradle/
categories:
    - Kotlin
    - Gradle
author: Chris Beams
date: May 18, 2016 00:00
---
Many readers will be familiar with JetBrains’ excellent [Kotlin](http://kotlinlang.com) programming language. It’s been under development since 2010, had its first public release in 2012, and went 1.0 GA earlier this year.

We’ve been watching Kotlin over the years, and have been increasingly impressed with what the language has to offer, as well as with its considerable uptake—particularly in the Android community.

Late last year, Hans sat down with a few folks from the JetBrains team, and they wondered together: what might it look like to have a Kotlin-based approach to writing Gradle build scripts and plugins? How might it help teams—especially big ones—work faster and write better structured, more maintainable builds?

The possibilities were enticing.

Because Kotlin is a statically-typed language with deep support in both IDEA and Eclipse, it could give Gradle users proper IDE support from auto-completion to refactoring and everything in-between. And because Kotlin is rich with features like first-class functions and extension methods, it could retain and improve on the best parts of writing Gradle build scripts—including a clean, declarative syntax and the ability to craft DSLs with ease.

So we got serious about exploring these possibilities, and over the last several months we’ve had the pleasure of working closely with the Kotlin team to develop a new, Kotlin-based build language for Gradle.

We call it Gradle Script Kotlin, and Hans just delivered the first demo of it onstage at JetBrains’ Kotlin Night event in San Francisco. We’ve [released the first milestone](https://github.com/gradle/gradle-script-kotlin/releases/tag/v1.0.0-M1) towards version 1.0 of this work today, along with open-sourcing its repository at [https://github.com/gradle/gradle-script-kotlin](https://github.com/gradle/gradle-script-kotlin).

[![KotlinGradleBanner](http://gradle.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/KotlinGradleBanner.gif)](http://gradle.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/KotlinGradleBanner.gif)

So what does it look like, and what can you do with it? At a glance, it doesn’t look _too_ different from the Gradle build scripts you know today:

[![pasted image 0](http://gradle.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/pasted-image-0.png)](http://gradle.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/pasted-image-0.png)

But things get very interesting when you begin to explore what’s possible in the IDE. You’ll find that, suddenly, the things you usually expect from your IDE _just work_, including:

* auto-completion and content assist
* quick documentation
* navigation to source
* refactoring and more

The effect is dramatic, and we think it’ll make a big difference for Gradle users. Now, you might be wondering about a few things at this point—like whether existing Gradle plugins will work with Gradle Script Kotlin (yes, they will), and whether writing build scripts in Groovy is deprecated (no, it’s not). You can find complete answers to these and other questions in the [project FAQ](https://github.com/gradle/gradle-script-kotlin/wiki/Frequently-Asked-Questions). Do let us know if you have a question that’s not answered there.

Of course, all this is just the beginning. We’re happy to announce that Kotlin scripting support will be available in Gradle 3.0, and we’ll be publishing more information about our roadmap soon. In the meantime, there’s no need to wait—you can try out Gradle Script Kotlin for yourself right now by [getting started with our samples](https://github.com/gradle/gradle-script-kotlin/tree/master/samples#readme).

And we hope you do, because we’d love your feedback. We’d love to hear what you think, and how you’d like to see this new work evolve. You can file issues via the project’s [GitHub Issues](https://github.com/gradle/gradle-script-kotlin/issues) and please come chat with us in the #gradle channel of the public [Kotlin Slack](http://kotlinslackin.herokuapp.com/).

I’d like to say a big thanks to my colleague Rodrigo B. de Oliveira for the last few months of working together on this project—it’s been a lot of fun! And a big thanks to the Kotlin team, in particular Ilya Chernikov and Ilya Ryzhenkov for being so responsive in providing us with everything we needed in the Kotlin compiler and Kotlin IDEA plugin. Onward!
