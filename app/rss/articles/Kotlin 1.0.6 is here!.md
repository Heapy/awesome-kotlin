---
title: 'Kotlin 1.0.6 is here!'
url: https://blog.jetbrains.com/kotlin/2016/12/kotlin-1-0-6-is-here/
categories:
    - Kotlin
author: Yan Zhulanow
date: Dec 27, 2016 11:29
---
We are happy to announce the release of Kotlin 1.0.6, the new bugfix and tooling update for Kotlin 1.0. This version brings a significant number of improvements related to the IDE plugin and Android support.

We’d like to thank our external contributors whose pull requests are included in this release: [Kirill Rakhman](https://github.com/cypressious) and [Yoshinori Isogai](https://github.com/shiraji). We also want to thank everyone of our EAP users for their feedback. It is really valuable for us, as always.

You can find the full list of changes in the [changelog](https://github.com/JetBrains/kotlin/blob/1.0.6/ChangeLog.md#106). Some of the changes worth highlighting are described below.

## Convert `try-finally` to `use()` intention

We continue to add intentions for converting code to idiomatic Kotlin. The IDE now automatically suggests to replace `try-finally` block with the [use()](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/use.html) call when all the `finally` block does is closing a resource.  

![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/12/try.gif)

## “Add names to call arguments” intention

Named arguments help to increase code readability. With the new “Add names to call arguments” intention you can easily add the name to an argument, or just substitute names for all call arguments at once.

![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/12/args.gif)

## Other notable IDE plugin changes

* Inspection/intention for removing empty secondary constructor body, as well as empty primary constructor declaration;
* “Join declaration and assignment” intention;
* Fixes for inline functions and performance improvements in debugger;
* Numerous fixes in intentions, KDoc and Quick Doc.

## Android Support

* Android Studio 2.3 beta 1 is now supported, as well as the Android Gradle plugin version 2.3.0-alpha3 and newer.
* “Create XML resource” intention is added;
* Android Extensions support is now active in the IDE only if the corresponding plugin is enabled in the `build.gradle`;
* Significant number of fixes in Android Lint. Also the “Suppress Lint” intention is added.

## Kapt Improvements

We continue to work on the experimental version of Kotlin annotation processing tool (kapt). While there are still some things to do in order to fully support incremental compilation, performance of the annotation processing is significantly increased since Kotlin 1.0.4.

To enable experimental kapt, just add the following line to your `build.gradle`:

`apply plugin: 'kotlin-kapt'`

## All-open compiler plugin

The **all-open** compiler plugin makes classes annotated with a specific annotation and their members open without the explicit `open` keyword, so it becomes much easier to use frameworks/libraries such as Spring AOP or Mockito. You can read the detailed information about all-open in the corresponding [KEEP](https://github.com/Kotlin/KEEP/pull/40).

We provide all-open plugin support both for Gradle and Maven, as well as the IDE integration.

### How to use all-open with Gradle

```gradle
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
    }
}

apply plugin: "kotlin-allopen"

allOpen {
    annotation("com.your.Annotation")
}
```

If the class (or any of its superclasses) is annotated with `com.your.Annotation`, the class itself and all its members will become open. It even works with meta-annotations:

```kotlin
@com.your.Annotation
annotation class MyFrameworkAnnotation

@MyFrameworkAnnotation
class MyClass // will be all-open
```

We also provide the “kotlin-spring” plugin that already has all required annotations for the Spring framework:

```gradle
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
    }
}

apply plugin: "kotlin-spring"
```

Of course, you can use both `kotlin-allopen` and `kotlin-spring` in the same project.

### How to use all-open with Maven

```xml
<plugin>
    <artifactId>kotlin-maven-plugin</artifactId>
    <groupId>org.jetbrains.kotlin</groupId>
    <version>${kotlin.version}</version>

    <configuration>
        <compilerPlugins>
            <!-- Or "spring" for the Spring support -->
            <plugin>all-open</plugin>
        </compilerPlugins>

        <pluginOptions>
            <!-- Each annotation is placed on its own line -->
            <option>all-open:annotation=com.your.Annotation</option>
            <option>all-open:annotation=com.their.AnotherAnnotation</option>
        </pluginOptions>
    </configuration>

    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-allopen</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
    </dependencies>
</plugin>
```

## No-arg compiler plugin

The **no-arg** compiler plugin generates an additional zero-argument constructor for classes with a specific annotation. The generated constructor is synthetic so it can’t be directly called from Java or Kotlin, but it can be called using reflection. You can see motivating discussion [here](https://discuss.kotlinlang.org/t/feature-request-a-modifier-annotation-for-data-classes-to-provide-a-non-arg-constructor-on-jvm/1549/4).

### How to use no-arg in Gradle

The usage is pretty similar to all-open.

```gradle
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-noarg:$kotlin_version"
    }
}

// Or "kotlin-jpa" for the Java Persistence API support
apply plugin: "kotlin-noarg"

noArg {
    annotation("com.your.Annotation")
}
```

### How to use no-arg in Maven

```xml
<plugin>
    <artifactId>kotlin-maven-plugin</artifactId>
    <groupId>org.jetbrains.kotlin</groupId>
    <version>${kotlin.version}</version>

    <configuration>
        <compilerPlugins>
            <!-- Or "jpa" for the Java Persistence annotation support -->
            <plugin>no-arg</plugin>
        </compilerPlugins>

        <pluginOptions>
            <option>no-arg:annotation=com.your.Annotation</option>
        </pluginOptions>
    </configuration>

    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-noarg</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
    </dependencies>
</plugin>
```

## How to update

To update the IDEA plugin, use Tools | Kotlin | Configure Kotlin Plugin Updates and press the “Check for updates now” button. Also, don’t forget to update the compiler and standard library version in your Maven and Gradle build scripts.

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums](https://discuss.kotlinlang.org/), on Slack (get an invite [here](http://kotlinslackin.herokuapp.com/)), or to report issues in the [issue tracker](https://youtrack.jetbrains.com/issues/KT).

Let’s Kotlin!
