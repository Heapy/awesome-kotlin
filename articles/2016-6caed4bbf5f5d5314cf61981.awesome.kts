
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin’s [extension functions](https://kotlinlang.org/docs/reference/extensions.html#extension-functions) are a great way to add behavior to a type sitting outside one’s control - the JDK or a third-party library.

For example, the JDK’s `String` class offers the `toLowerCase()` and `toUpperCase()` methods but nothing to capitalize the string. In Kotlin, this can be helped by adding the desired behavior to the `String` class through an extension function:

```kotlin
fun String.capitalize() = when {
    length < 2 -> toUpperCase()
    else -> Character.toUpperCase(toCharArray()[0]) + substring(1).toLowerCase()
}

println("hello".capitalize())
```

Extension functions usage is not limited to external types, though. It can also improve one’s own codebase, to handle null values more elegantly.

This is a way one would define a class and a function in Kotlin:

```kotlin
class Foo {
    fun foo() = println("foo")
}
```

Then, it can be used on respectively non-nullable and nullable types like that:

```kotlin
val foo1 = Foo()
val foo2: Foo? = Foo()
foo1.foo()
foo2?.foo()
```

Notice the compiler enforces the usage of the null-safe `.?` operator on the nullable type instance to prevent `NullPointerException`.

Instead of defining the `foo()` method directly on the type, let’s define it as an extension function, but with a twist. Let’s make the `.?` operator part of the function definition:

```kotlin
class Foo

fun Foo?.safeFoo() = println("null-safe foo")
```

Usage is now slightly modified:

```kotlin
val foo1 = Foo()
val foo2: Foo? = Foo()
val foo3: Foo? = null
foo1.safeFoo()
foo2.safeFoo()
foo3.safeFoo()
```

Whether the type is non-nullable or not, the calling syntax is consistent. Interestingly enough, the output is the following:

```
null-safe foo
null-safe foo
null-safe foo
```

Yes, it’s possible to call a method on a [`null` instances](https://kotlinlang.org/docs/reference/extensions.html#nullable-receiver)! Now, let’s update the `Foo` class slightly:

```kotlin
class Foo {
    val foo = 1
}
```

What if the `foo()` extension function should print the `foo` property instead of a constant string?

```kotlin
fun Foo?.safeFoo() = println(foo)
```

The compiler complains:

```
Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Foo?
```

The extension function needs to be modified according to the compiler’s error:

```kotlin
fun Foo?.safeFoo() = println(this.?foo)
```

The output becomes:

```
1
1
null
```

Extension functions are a great way to make API more consistent and to handle `null` elegantly instead of dropping the burden on caller code.

"""

Article(
  title = "Extension functions for more consistent APIs",
  url = "https://blog.frankel.ch/extension-functions-for-more-consistent-apis/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Fränkel",
  date = LocalDate.of(2016, 10, 16),
  body = body
)
