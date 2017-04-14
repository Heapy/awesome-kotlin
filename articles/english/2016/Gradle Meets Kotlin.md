---
title: 'Gradle Meets Kotlin'
url: http://blog.jetbrains.com/kotlin/2016/05/gradle-meets-kotlin/
categories:
    - Kotlin
    - Gradle
author: Hadi Hariri
date: May 18, 2016 02:18
---
Back at **JavaOne 2015**, during a lunch break we started chatting with **Hans Dockter, CEO of Gradle**. A couple of days after the conference, a few of us were at the Gradle offices talking about what would be the beginning of the collaboration between JetBrains and Gradle; to bring first-class tooling and support for a static language to Gradle.

Today, at the [Kotlin Night in San Francisco](http://info.jetbrains.com/Kotlin-Night-2016.html), Hans Dockter demoed the first milestone of writing a Gradle build script using Kotlin.

<iframe src="https://www.youtube.com/embed/4gmanjWNZ8E" allowfullscreen="" frameborder="0" height="480" width="960"></iframe>

```kotlin
import org.gradle.api.plugins.*
import org.gradle.api.tasks.wrapper.*
import org.gradle.script.lang.kotlin.*

apply<ApplicationPlugin>()

configure<ApplicationPluginConvention> {
    mainClassName = "samples.HelloWorld"
}

repositories {
    jcenter()
}

dependencies {
    "testCompile"("junit:junit:4.12")
}
```

Gradle allows developers and build engineers to deal with complex build automation scripts. As complexity grows, having a language that is statically-typed can help detect potential misconfigurations at compile time, contributing in reducing runtime issues. Static typing also opens up the door to more sophisticated tooling. All this, combined with key characteristics of Kotlin that enable easy creation of DSL’s, can provide Gradle users benefits while maintaining the level of fluency they are accustomed to.

For the past 6 months, we’ve been working closely with the Gradle team, in particular with Chris Beams and Rodrigo de Oliveira in bringing Kotlin to Gradle. It has been a tremendously rewarding experience because it has also helped us see use-case scenarios for making scripting in Kotlin a first-class citizen.

We are very excited for what Gradle has in store and are happy to continue collaborating with them closely in bringing a great experience to Gradle users.

For more information and how to get the bits to start playing with this, make sure you read the [blog post by the Gradle team](http://gradle.org/blog/kotlin-meets-gradle) for more details. In addition, if you are on the public [**Kotlin Slack**](https://kotlinlang.slack.com), there’s a newly created **#gradle** channel for discussions.
