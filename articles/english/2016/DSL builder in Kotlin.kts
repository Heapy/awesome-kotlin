
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
> Design patterns describe simple and elegant solutions to specific problems in object-oriented software design. (GoF)

We all use design patterns even if we do not know their names. Builder pattern is one of the most famous and liked patterns for creating objects in an easy and scalable way. Being Java engineer I have had a lot of opportunities to master it. After a few times, I realized there is no fun in writing a builder. Thanks to IntelliJ IDEA we can generate it.

![](https://cdn-images-1.medium.com/max/800/1*Knnn1qwG93dPbf1KEDERtg.png)

This approach is pretty convenient, as generated code is easy to change and tweak. You can add some checks, validations etc. As always there are some pitfalls. How many times did you have to add some property to your model with existing builder? Imagine that builder was generated and enriched with validation. Is there any fast, codeless way to add the new property to it? The answer is NO. You will have to implement it character by character.

Another clever way is to achieve this by annotations using Lombok library.

```kotlin
import lombok.Builder;

@Builder
class Person {
    private final String name;
    private final String surname;
    private final int age;
}
```

### Kotlin

Alright, enough of Java world. Let’s talk about Kotlin ❤. When I started my journey with Kotlin I tried to apply my Java experience to every single line of code. The code worked and even looked better than Java, but still I felt there was something wrong with it. Kotlin is a high-level programming language with a lot of robust features. You should know them in order to produce more clear and readable structures.

Referring to builder pattern we can write a really naive Kotlin implementation based on Java experience.

```kotlin
class Person(val name: String, val surname: String, val age: Int) {
    companion object {
        class Builder {
            private var name: String = ""
            private var surname: String = ""
            private var age: Int = 0

            fun name(name: String): Builder {
                this.name = name
                return this
            }

            fun surname(surname: String): Builder {
                this.surname = surname
                return this
            }

            fun age(age: Int): Builder {
                this.age = age
                return this
            }

            fun build() = Person(name, surname, age)
        }
    }
}

fun main(args: Array<String>) {
    Person.Companion
          .Builder()
          .name("Piotr")
          .surname("Slesarew")
          .age(28)
          .build()
}
```

At first glance, this looks correctly, but can we do it better? Nowadays object-oriented programming is not enough. Start thinking in functions! Kotlin has the functional paradigm and it is possible to create objects using DSL. A DSL makes the same logic easy to read and write. How is it possible? BAM!

```kotlin
class Person private constructor(val name: String, val surname: String, val age: Int) {

    private constructor(builder: Builder) : this(builder.name, builder.surname, builder.age)

    companion object {
        fun create(init: Builder.() -> Unit) = Builder(init).build()
    }

    class Builder private constructor() {

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        lateinit var name: String
        lateinit var surname: String
        var age: Int = 0

        fun name(init: Builder.() -> String) = apply { name = init() }

        fun surname(init: Builder.() -> String) = apply { surname = init() }

        fun age(init: Builder.() -> Int) = apply { age = init() }

        fun build() = Person(this)
    }
}

fun main(args: Array<String>) {
    Person.create {
        name { "Peter" }
        surname { "Slesarew" }
        age { 28 }
    }
    
    // OR
    
    Person.create {
        name = "Peter"
        surname = "Slesarew"
        age = 28
    }
}
```

This sample shows how amazing Kotlin is and what kind of crazy structures we can create. In addition similar approach was used to build Anko. I am not saying that this is the best solution, but at least it is worth to be aware of.

> Functional programming is like a blessed curse. Purity feels amazing but you feel like you can’t program the “dirty” way anymore. — ([André Staltz](https://twitter.com/andrestaltz/status/696716396626124801))

_If this was interesting to you, please do hit the heart button ❤ or_ [_let me know on Twitter_](https://twitter.com/SliskiCode)_._

"""

Article(
  title = "DSL builder in Kotlin",
  url = "https://medium.com/@piotr.slesarew/dsl-builder-in-kotlin-be5816ba3ca7#.xbcofn4hp",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Piotr Ślesarew",
  date = LocalDate.of(2016, 10, 21),
  body = body
)
