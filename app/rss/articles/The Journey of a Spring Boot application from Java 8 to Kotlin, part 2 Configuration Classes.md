---
title: 'The Journey of a Spring Boot application from Java 8 to Kotlin, part 2: Configuration Classes'
url: http://engineering.pivotal.io/post/spring-boot-configuration-in-kotlin/
categories:
    - Spring Boot
    - Kotlin
author: Mike Gehard
date: Feb 23, 2016 14:29
---
In the [first post](/post/spring-boot-application-with-kotlin/) of this series, we looked at the conversion of a Spring Boot application class from Java 8 to Kotlin. The nice thing about these migrations is that they can be done incrementally since Kotlin plays very nicely with legacy Java. In fact, it was one of the design considerations for the language.

In this second post, we will look at the conversion of a configuration class into Kotlin.

Here is an example of a Spring Boot configuration class as written in Java 8:

```kotlin
package com.example.billing;

import com.example.billing.reocurringPayments.Service;
import com.example.payments.RecurlyGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Configuration {
    @Bean
    public com.example.payments.Gateway paymentGateway() {
        return new RecurlyGateway();
    }

    @Bean
    public Service serviceThatMayFail() {
        return new Service();
    }
}
```

Here is that same configuration class written in Kotlin:

```kotlin
package com.example.billing

import com.example.billing.reocurringPayments.Service
import com.example.payments.RecurlyGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Configuration {
    @Bean
    open fun paymentGateway() = RecurlyGateway()

    @Bean
    open fun serviceThatMayFail() = Service()
}
```

There aren't a bunch of huge differences but here are some of the smaller differences that stand out to me:

* The Configuration class must be declared open. This is because Spring Boot subclasses your configuration class but Kotlin makes them final by default. See [here](https://kotlinlang.org/docs/reference/classes.html#inheritance) for details.
* The @Bean functions must be declared open for the same reason as above.
* There are no return types on the functions because Kotlin will infer the types. This type inference is one of
my favorite features of Kotlin.
* Kotlin has implicit returns (and no braces) for [single-expression functions](https://kotlinlang.org/docs/reference/functions.html#single-expression-functions). When you only have one expression in a function body, Kotlin will automatically assume that you want to return that value so there is no need for an explicit `return` or braces. For bodies with multiple expressions, the `return` is still mandatory because the compiler may not be able to guess what the return type is for the function.
* No `new` keyword when initializing an object. This coupled with the type inference, implicit returns and single statement/no braces makes for a nice compact configuration class.

Spring configuration classes are a mixed bag for me in Kotlin. The actual code difference is only 4 lines of code (18 vs 14) but the visual noise is significantly reduced in Kotlin. Having to declare both the class and all of the methods as open seems a bit clunky for me but I'm willing to overlook it due to the type inference, lack of return for single expression functions and the other improvements that these classes gain from Kotlin.

Thanks for reading. In our next installment, we will take a look at Spring controllers in Kotlin.
