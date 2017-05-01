

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Here at Facile.it we are constantly dealing with a lot of **forms**: we use them to collect various information and data needed to feed our in-house comparing algorithms. These forms could be **really complex**, having multiple rules and dependencies between fields, and they are likely to be **changed and tuned frequently**.

When I joined Facile.it, a lot of forms within the Android app needed to be updated or modified and sticking with the existing strategy would have required me to do **a lot of work** just to add or remove a simple field. So I took a step back and I started thinking about a strategy that would have allowed me to define and structure a form in a more *flexible* and *declarative* way. I wanted to be able to declare the **relationships** between fields, their **validation rules** and their **serialized representation** (how they are sent to the server).

I firstly thought about defining it using some configuration file, maybe written in JSON or YAML. The *problem* with this strategy was that it would also have required me to write a lot code to parse and validate those files to be able to create some sort of representation of the form in Java…but don’t we already have the compiler for this kind of jobs?

I still wanted to be able to have both a **human readable representation** of the form and the right degree of **flexibility** to integrate the form definition into the app code. So I thought that creating a [Domain-Specific Language](https://en.wikipedia.org/wiki/Domain-specific_language) would have been a perfect strategy to solve the problem in an elegant and efficient way. Writing a DSL in Java could have ended up into something like that:

```kotlin
Form.create()
    .openSection(sectionId)
    .field(key1, "label1", style, ...)
    .field(key2, "label2", style, ...)
    .field(key3, "label3", style, ...)
    .closeSection()
    ...
    .build()
```

I don’t think the previous code is readable nor flexible and it requires a lot of boilerplate to be written.

## Kotlin to the rescue! ##

Unlike Java, Kotlin (take a look at my [previous post](https://engineering.facile.it/blog/eng/kotlin-intro/) about it) has a lot of features that makes it really powerful when it comes to write internal DSLs. The results are very similar to Groovy (think about a Gradle file) but thanks to its type system they could be [Type-Safe](https://kotlinlang.org/docs/reference/type-safe-builders.html#type-safe-builders).

The builders you can write with Kotlin are extremely readable and easy to understand even for people that don’t know either the language or the DSL itself. Here’s how a form built using my final DSL looks like:

```kotlin
val FORM = form {
    page("Page 1 Title") {
        section("Section 1 Title") {
            field(key = "fieldKey1") {
                checkbox("Checkbox Field Label") {
                    boolToStringConverter = { if (it == true) "Yes" else "No" }
                    rules = { listOf(NotMissing()) }
                }
            }
            field(key = "fieldKey2") {
                picker("Picker Field Label") {
                    placeHolder = "Select a value"
                    possibleValues = Available(listOf(
                            1 keyTo "Value1",
                            2 keyTo "Value2",
                            3 keyTo "Value3"))
                    representation = IF_VISIBLE representAs SIMPLE_KEY_TO_VALUE
                }
            }
        }
        section("Section 2 Title") {
            field(key = "fieldKey3") {
                picker("Picker Field Label") {
                    placeHolder = "Select a value"
                    possibleValues = ToBeRetrieved(someWebService.getValues())
                    representation = IF_VISIBLE representAs SIMPLE_KEY_TO_VALUE
                }
            }
            field(key = "fieldKey4") {
                input("Input Text Field Label") {
                    inputTextType = InputTextType.EMAIL
                    rules = { listOf(IsEmail()) }
                }
            }
            field(key = "fieldKey") {
                empty("Empty Field")
            }
        }
        section("Section 3 Title") {
            field(key = "fieldKey6") {
                toggle("Toggle Field Label") {
                    boolToStringConverter = { if (it == true) "OK" else "KO" }
                    rules = { listOf(NotMissing()) }
                    representation = ALWAYS representAs SIMPLE_KEY_TO_VALUE
                }
            }
        }
    }
}
```


And this is the result on Android using my [Form library](https://github.com/brescia123/forms):

![Form screenshot](https://engineering.facile.it/images/kotlin-dsl/form_screen.png)

Cool, isn’t it?

## Type-safe builders ##

### Some Kotlin important features ###

To grasp how Type-safe builders work in Kotlin we need to understand some key Kotlin features and how they can be combined together:

 *  **Higher-Order Functions and Lambdas**: in Kotlin we are allowed to write functions that have *functions as parameters or return type* (higher-order functions) and functions that are *not declared*, but are passed immediately as an expression (lambdas). Because of this, we can write things like:

```kotlin
// Higher-Order Function
fun transformWith(path: String, function: (String) -> List<String>): List<String> {
    return function(this)
}

// Lambda
{ path: String -> path.split("/") }
```

Thanks to Kotlin syntactic sugar we can use them in these ways:

```kotlin
transformWith("some/path/to", { path: String -> path.split("/") }) // -> [some, path, to]

// Functions which have a function as the last parameter can be written as follow
transformWith("some/path/to") { path -> path.split("/") } // -> [some, path, to]

// If the lambda has only one parameter it can be ommitted and referenced as "it"
transformWith("some/path/to") { it.split("/") } // -> [some, path, to]
```

 *  **Extension Functions**: they allow us to *extend* a type with functions without modifying the original class. They are useful to add functionalities to classes we don’t have control on or to create utility methods without the need to create “Utils classes” that contains static methods, as we are used to as Java developers. To continue the previous example we can write:

```kotlin
// Extension function
fun String.transformWith(function: (String) -> List<String>) {
    return function(this)
}

"some/path/to".transformWith { receiverString: String -> receiverString("/") } // -> [some, path, to]

// or more concisely
"some/path/to".transformWith { it.split("/") } // -> [some, path, to]
```

Note that we are referring to the string inside the closure of the extension function using `this` as it will be the String object on which the method will be called.

 *  **Function Literals with Receiver**: similarly to extension functions you are also allowed to define functions with a receiver that will be referred to as `this` inside the literal closure:

```kotlin
val transformWith: String.() -> List<String> = { this.split("/") }

"some/path/to".transformWith() // -> [some, path, to]
```

To better understand function literals with receiver you should think of them as follow: *lambda is to normal function as function literal with receiver is to extension function*.

```kotlin
// lambda
{ s: String -> s.split("/") }
// is to
fun function(s: String): List<String> { return s.split("/") }
// as
val functionLiteralWithReceiver = String.() -> List<String> = { this.split("/") }
// is to
fun String.extensionFunction(): List<String> { return function(this) }
```

Basically function literals with receiver are extension functions that can be passed to other functions.

### Wrapping up ###

Now we have all the elements required to understand and write a Type-safe builder.

Combining the above mentioned Kotlin features we can now write a function and name it `form`. This function will take as parameter a function literal with receiver usually called `init()` and will do the follow:

 *  create a new Form object
 *  call `init()` on it (that is using it as the receiver of the function literal)
 *  return the built object to the caller

```kotlin
fun form(init: Form.() -> Unit): Form {
    val form = Form()
    form.init()
    return form
}
```

Now let’s imagine that our Form class defines a function `field()` that actually creates a field object and adds it to the list of fields contained within the form:

```kotlin
class Form() {
    val fields: List<Field>
    ...
    fun field(key: String) { ... }
    ...
}
```

Taking advantage of Kotlin syntactic sugar we can use `form()` passing it the `init()` function as a lambda and call methods on the Form object to build it as follow:

```kotlin
val builtForm = form() {
    // Here we can take advantage of the compiler and, as a result, of the IDE code completion
    field("key1") // == this.field("key1") where this is the object create by form()
    field("key2")
}
builtForm.getFields() // -> [Field("key1"), Field("key2")]
```

As you can see Type-Safe builders are an **extremely powerful** and useful feature of Kotlin and they allow you to write very complex DSLs with a **really readable and clear syntax**. They give you a lot of **flexibility** letting you combine multiple builders to create a domain language that can meet your requirements.

If you want to learn more about this subject check out the official [documentation](https://github.com/Kotlin/kotlinx.html) or, for example, [kotlinx](https://github.com/Kotlin/kotlinx.html), an official project from the Kotlin team that allows you to create HTML documents with a custom DSL entirely written with Type-safe builders.
"""

Article(
  title = "Using Kotlin type-safe builders to create a DSL for Forms",
  url = "https://engineering.facile.it/blog/eng/kotlin-dsl/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Giacomo Bresciani",
  date = LocalDate.of(2017, 2, 8),
  body = body
)
