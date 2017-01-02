---
title: 'Why I prefer Kotlin'
url: https://dev.to/grahamcox82/why-i-prefer-kotlin
categories:
    - Kotlin
author: Graham Cox
date: Jan 02, 2017 09:12
---
I've been an enterprise Java developer for a little over 10 years, and I've been using Java for just over 16 years - back when Java 1.3 was the latest and greatest. It's been a long ride. Back then there were no Lambdas, no Streams, no Generics, no Annotations. There wasn't even an `assert` keyword.

These days, Java has a huge amount for a very vast and powerful ecosystem. And one of the most powerful parts of all of that is the built in support for alternative languages as part of the same system. The JVM makes it possible to have many different languages - meaning the actual code you type into the editor - compile down to compatible bytecode and all run together in the same application. That's a huge benefit for software development, since you can now write different parts of the application in the best suited language for the job.

So what exactly is Kotlin, and why do I like it so much? Unlike many of of the other JVM languages, Kotlin doesn't try to be anything special or different. It simply exists as a vastly simplified, streamlined Java language. There is very little, if anything, that Java can do that Kotlin can't do. The difference is that Kotlin does the same in significantly less code, making it significantly easier to read and maintain. Kotlin also has a large emphasis on Java Interop, meaning that it's trivial to use it in conjunction with existing Java libraries. Some other JVM languages make this much harder to achieve, if it's possible at all.

So, in general, my experience so far is that Kotlin allows me to do everything that Java does, but simpler.

The only thing that I've struggled with at all was some of the tooling - but this really doesn't bother me that much. By this, I'm talking Code Coverage (The JVM bytecode includes many lines that just don't exist in the Kotlin code, so covering them is less easy), FindBugs, PMD, Checkstyle, and so on. I've not explored Sonar for Kotlin yet, but it's very likely that this will fill in many of the gaps if it works.
