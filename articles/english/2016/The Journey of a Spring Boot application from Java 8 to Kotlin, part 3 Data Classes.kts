
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Welcome to the third installment of our Java 8 -> Kotlin conversion for a Spring Boot application. [Last time](/post/spring-boot-configuration-in-kotlin/) we saw how converting a configuration class to Kotlin helped clean up some of the boilerplate code required in Java.

In this third installment, we are going to continue our theme of "write less code with Kotlin" and look at how [Kotlin data classes](https://kotlinlang.org/docs/reference/data-classes.html) help us clean up our POJOs data classes.

Our [starting point](https://github.com/mikegehard/user-management-evolution-kotlin/blob/83883fee6dac3cb06e10bd6a510403cbe3e1ef62/components/email/src/main/java/com/example/email/EmailMessage.java) is a plain old Java object (POJO) to hold some data to be sent via RabbitMQ:

```kotlin
package com.example.email;

import java.io.Serializable;

public class EmailMessage implements Serializable {

    private final String toAddress;
    private final String subject;
    private final String body;

    public EmailMessage(String toAddress, String subject, String body) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

```

Here is the [same class](https://github.com/mikegehard/user-management-evolution-kotlin/blob/f137c5ec25a2a575c30113a3260f55af6d0285ed/components/email/src/main/kotlin/com/example/email/EmailMessage.kt) implemented as a [Kotlin data class](https://kotlinlang.org/docs/reference/data-classes.html):

```kotlin
package com.example.email

import java.io.Serializable

data class EmailMessage(val toAddress: String, val subject: String, val body: String) : Serializable

```

Not only is this code much shorter than it's Java counterpart, it also has more functionality. As a Kotlin data class, this tiny amount of code gets:

* generated implementations for a `equals()/hashCode()` pair
* a default `toString()` method
* a `copy()` method that allows for easy altering of individual attributes of the object
* the ability to [destructure](https://kotlinlang.org/docs/reference/data-classes.html#data-classes-and-destructuring-declarations) the object in an assignment statement.

Other places this feature will come in handy is with [JSON deserialization](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin#jackson-kotlin-module) and [Spring Data JPA classes](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin#jackson-kotlin-module).

As someone evaluating a switch from Java to Kotlin, these data classes are a major reasons for adopting Kotlin in your Spring Boot application. They help you write, and thus maintain, less code and the less code we have to maintain the better in my mind.

"""

Article(
  title = "The Journey of a Spring Boot application from Java 8 to Kotlin, part 3: Data Classes",
  url = "http://engineering.pivotal.io/post/spring-boot-kotlin-data-classes/",
  categories = listOf(
    "Spring Boot",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mike Gehard",
  date = LocalDate.of(2016, 2, 29),
  body = body
)
