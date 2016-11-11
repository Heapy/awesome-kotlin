---
title: 'The Journey of a Spring Boot application from Java 8 to Kotlin: The Application Class'
url: http://engineering.pivotal.io/post/spring-boot-application-with-kotlin/
categories:
    - Spring Boot
    - Kotlin
author: Mike Gehard
date: Feb 13, 2016 09:35
---
After writing a significant amount of Ruby/Rails for many years, lately I have found myself writing a ton of Spring Boot applications. [Spring Boot](http://projects.spring.io/spring-boot/) is a great framework for the JVM that focuses on developer productivity by making "it easy to create stand-alone, production-grade Spring based Applications that you can 'just run'". It has a lot of the feel of Rails in the "convention over configuration" department but because I end up using Java 8, I have lost some of the "joy" that you get when writing in Ruby. Even though Java 8 provides significant improvements over Java 7, I wanted to find out if I could get some more of that joy back by using [Kotlin](https://kotlinlang.org/) to write Spring Boot applications.

Kotlin is a new language from the folks at [JetBrains](https://www.jetbrains.com/), creators of IntelliJ and RubyMine, as a replacement for Java in developing their products. Their goal was to create a more concise JVM based language that helps increase developer productivity, avoid some common pitfalls in Java development and be 100% compatible with existing Java programs. It targets Java 6 as the baseline while still adding some great language features so it is quite useful for Android development as well.

This post, and all that follow, will use an existing Java 8/Spring Boot [application](https://github.com/mikegehard/user-management-evolution-kotlin/tree/running-java) as a starting point for the exploration. This will allow me to see direct comparisons between Java 8 syntax and Kotlin syntax. The journey will allow me to experience first hand what a Spring Boot/Kotlin application might look like as well as learn the language as I go that is a bit more than a "Hello World" application.
If you want to follow along as I travel, you can check out the evolving source on [GitHub here](https://github.com/mikegehard/user-management-evolution-kotlin).


In addition, these posts are not meant to be a full tutorial on Kotlin and will only cover the language features pertinent to the transformation. If you want a full tutorial, the Kotlin website has a lot of [great information](https://kotlinlang.org/docs/reference/).

Finally, if you have any suggestions for improvements in [the code](https://github.com/mikegehard/user-management-evolution-kotlin), please feel free to submit a GitHub issue or submit a pull request.

## The starting line

The first thing we need when starting a Spring Boot application is an application class. Here is the [application class](https://github.com/mikegehard/user-management-evolution-kotlin/blob/running-java/applications/billing/src/main/java/com/example/billing/Application.java) that I started with:

```kotlin
package com.example.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

...

```

No surprises here. We create a static `main()` method on the Application class that Spring Boot detects when you run the executable jar file.

Here is that same application class in Kotlin:

```kotlin
package com.example.billing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker

// This class must not be final or Spring Boot is not happy.
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}

```

The first difference you may notice is the lack of semicolons. Yes ladies and gentlemen, no semicolons in Kotlin. While not a huge deal for some, it was a step in the right direction for me.

The next difference I noticed is the `open` keyword in front of the class definition. Classes in Kotlin are final by default, as per Item 17 from [Effective Java](http://www.oracle.com/technetwork/java/effectivejava-136174.html): *Design and document for inheritance or else prohibit it*. This is my first experience with friction between Kotlin's "enforce good practices" and Spring Boot's conventions. The `@SpringBootApplication` is a convenience annotation that marks the class with the `@Configuration`, `@EnableAutoConfiguration` and `@ComponentScan` annotations. It is the `@Configuration` annotation that forces the use of the `open` keyword. Removing the `open` keyword causes a runtime error when the application boots:

```kotlin
org.springframework.beans.factory.parsing.BeanDefinitionParsingException: Configuration problem: @Configuration class 'Application' may not be final. Remove the final modifier to continue.
```

This is easy enough to fix since this application class doesn't contain any configuration information. Instead of using the `@SpringBootApplication` annotation you can [substitute](https://github.com/mikegehard/user-management-evolution-kotlin/commit/a9045e1968193fc70b4c43a330fde265b3325f7a) the `@EnableAutoConfiguration` and `@ComponentScan` annotations.

```kotlin
package com.example.billing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@EnableCircuitBreaker

class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}

```

The final differences that I noticed are in the definition of the `main()` method. Kotlin has an idea of [companion objects](https://kotlinlang.org/docs/reference/object-declarations.html#companion-objects). These objects are used in a way similar to static methods in Java but not exactly. That is where the `@JvmStatic` annotation comes in. This annotation tells Kotlin to generate an actual Java static method instead of the "kinda,sorta" one that is the default in Kotlin. This annotation is a great example of the investment in JVM compatibility.

The `main()` method is also missing the `public` modifier. [Methods are public by default](https://kotlinlang.org/docs/reference/visibility-modifiers.html) in Kotlin which reduces a bit of the boilerplate present in Java applications.

Finally, you will notice that arrays in Kotlin are actual parameterized classes instead of the primitive type that they are in Java. Kotlin also puts the type annotation after the variable is defined. We will get into why this is important in future posts.

One final gotcha with the Kotlin application class is that you have to tell Spring Boot where to find the application class. In Gradle, it is as easy as this:

```kotlin
springBoot {
    mainClass = 'com.example.billing.Application'
}
```
*Update - Feb, 16 2016: You do not need to tell Spring Boot where the application class is any longer. I got this from an early JetBrains blog post.*

# An alternate application class

Kotlin also allows functions to be defined outside of classes so we can re-write the application class as:

```kotlin
package com.example.billing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@EnableCircuitBreaker

class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

```

If we do this, the `main()` method is defined on a class called `ApplicationKt`, named after the file `Application.kt`, and this slightly changes the `build.gradle` entry:

```kotlin
springBoot {
    mainClass = 'com.example.billing.ApplicationKt'
}
```
*Update - Feb, 16 2016: You do not need to tell Spring Boot where the application class is any longer. I got this from an early JetBrains blog post.*

This definition simplifies the signature of the `main()` method a bit. Gone are the annotations and the explicit companion object so the code is a little less cluttered.

I'm not sure which one I prefer better. Using the companion object is more explicit about which class contains the `main()` method but the above definition is more succinct. Here we trade less code for an implicit understanding that an ApplicationKt class gets generated by the compiler.	Over time, I'm thinking the abbreviated application class will grow on me.

# Closing thoughts
In my mind, Kotlin is a step in the right direction as a "better Java". It seems to me that the language designers did what they could to remain compatible with existing Java programs while not being handcuffed by the legacy of Java. The lack of semi colons may seem trivial but will add up in large codebases and the enforcement of best practices at the language level will also help large codebases.

Yes there are some slight gotchas when it comes to smoothly integrating with Spring Boot but those are outweighed by the benefits of the new syntax and language constructs.

In our next installment, we will take a look at Java Spring Boot configuration classes and compare them to their Kotlin brethren. I'm hoping that we will continue to see gains from Kotlin that will outweigh the friction with Spring Boot.
