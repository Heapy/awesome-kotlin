---
title: 'Taking Kotlin for a ride'
url: https://www.linkedin.com/pulse/taking-kotlin-ride-hadi-tok?published=t
categories:
    - Kotlin
author: Hadi Tok
date: Oct 16, 2016 11:07
---
I have read about Kotlin and tried it on their online editor ([http://try.kotlinlang.org/](http://try.kotlinlang.org/)) previously. But I was wondering about the work required to make Kotlin work on an existing project. So I took kotlin for a ride over the weekend on a new branch and converted one of the fragments to Kotlin and make it work with the rest of the project, in language aspect and project structure aspect.  So I will not talk about the features of Kotlin but mostly how to make it work on an existing Android project.

The first thing needed is to install Kotlin plugin on Android Studio. It could be  installed as a JetBrains plugin in plugins section on Android Studio Preferences. It has three main features:

* Editor support
* Kotlin setup on project
* Converting Java code to Kotlin

The editor support is pretty good. I don’t really felt anything lacking when writing code with Kotlin compared to Java. It is pretty important while learning a new language. You would need all the help from the IDE.

You can configure Kotlin in your project by Tools>Kotlin>Configure Kotlin in Project. The only problem I had was the apply plugin: 'kotlin-android'  line on the app build.gradle file which is placed above `apply plugin: 'com.android.application'` so I had to moved under. Other than it was fine.

As I mentioned I converted one of the fragments that uses different features of Java and android to Kotlin. On the syntax side  I only had a problem with `Iterator.remove()` apparently which doesn’t have an equivalent on Kotlin. Instead I used `Iterable<T>.filter`. I think it has more straight forward usage than iterator for loop. This was the only compilation problem I had.

One thing I had worries about was the annotation processing. It is used on Butterknife and dagger. I was using apt plugin ([https://bitbucket.org/hvisser/android-apt](http://bitbucket.org/hvisser/android-apt)) for this purpose. But it didn’t work with kotlin. I haven’t received and compilation or build exceptions but I received an exception on runtime regarding to `lateinit` (`UninitializedPropertyAccessException`)  on a variable supposed to be binded with Butterknife. Kotlin examples page on github([https://github.com/JetBrains/kotlin-examples](http://github.com/JetBrains/kotlin-examples)) came to help at this point. I modified my annotations and changed the apt plugin to Kotlin apt plugin ([http://blog.jetbrains.com/kotlin/2015/05/kapt-annotation-processing-for-kotlin/](http://blog.jetbrains.com/kotlin/2015/05/kapt-annotation-processing-for-kotlin/)) after then everything worked as expected.

Other thing that is useful to know is the json deserialization. With a simple module addition to the Jackson library this is also pretty straight forward. You can find the instructions on the github page(https://github.com/FasterXML/jackson-module-kotlin)

As conclusion I haven’t decided to move to Kotlin yet. I am impressed the easy integration and  interoperability with Java. I am planning to use on one or two fragments that will be developed from scratch and try to have more experience on it. But So far my experience has been good.
