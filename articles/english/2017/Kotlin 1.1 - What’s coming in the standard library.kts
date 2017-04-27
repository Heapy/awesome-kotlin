

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Java 9 is coming and brings [Project Jigsaw](http://openjdk.java.net/projects/jigsaw/spec/sotms/) to the table — the Java platform module system. One of the constraints it imposes is that no two modules can declare public API in the same package. The situation, when there are two modules that contribute to the same package, is called “split” package.

We face this issue with split packages in our runtime artifacts: first, kotlin-runtime and kotlin-stdlib modules share a lot of kotlin.* packages, second, kotlin-runtime and kotlin-reflect share kotlin.reflect package. What we’re going to do to make our artifacts more friendly to the module system:

1. We merge kotlin-runtime and kotlin-stdlib into the single artifact kotlin-stdlib. Also we’re going to rename kotlin-runtime.jar, shipped in the compiler distribution, to kotlin-stdlib.jar, to reduce the amount of confusion caused by having differently named standard library in different build systems.
    That rename will happen in two stages: in 1.1 there will be both kotlin-runtime.jar and kotlin-stdlib.jar with the same content in the compiler distribution, and in 1.2 the former will be removed.
2. In kotlin-reflect module we move all API from kotlin.reflect to kotlin.reflect.full package. Kotlin 1.1 will have the former API deprecated with the replacements suggested, and it will be removed eventually in 1.2.
    Note that this change will affect only extension functions and properties provided by kotlin-reflect for reflection interfaces and a couple of exception classes. Reflection interfaces themselves are located in the standard library and won’t be moved.

If you use maven or gradle and depend on kotlin-stdlib, you won’t need to change anything. If you depend on kotlin-runtime, you should replace that dependency with kotlin-stdlib.

## New Standard Library API in 1.1

Here we introduce new functions and classes that are going to be published in Kotlin 1.1.

If you have already tried these new bits in Kotlin 1.1-Beta or in earlier releases and have some feedback, do not hesitate to share it in our [forum](https://discuss.kotlinlang.org/), [slack](https://kotlinlang.org/community.html) or [issue tracker](https://youtrack.jetbrains.com/issues/KT).

### takeIf() and also()

These are two general-purpose extension functions applicable to any receiver.

`also` is like `apply`: it takes the receiver, does some action on it, and returns that receiver.
The difference is that in the block inside `apply` the receiver is available as `this`,
while in the block inside `also` it’s available as `it` (and you can give it another name if you want).
This comes handy when you do not want to shadow `this` from the outer scope:

```kotlin
fun Block.copy() = Block().also { it.content = this.content }
```

`takeIf` is like `filter` for a single value. It checks whether the receiver meets the predicate, and
returns the receiver, if it does or `null` if it doesn’t.
Combined with an elvis-operator and early returns it allows to write constructs like:

```kotlin
val outDirFile = File(outputDir.path).takeIf { it.exists() } ?: return false
// do something with existing outDirFile

val index = input.indexOf(keyword).takeIf { it >= 0 } ?: error("keyword not found")
// do something with index of keyword in input string, given that it's found
```

### groupingBy()

This API is to group a collection by key and fold each group in the same time. It consists of two parts: `groupingBy` function, and terminal operations — `fold`, `reduce`, `eachCount`.

First you invoke `collection.groupingBy { key }` function, which just returns a `Grouping` instance binding the provided key selector to the collection of elements. Then you call one of folding operations available for `Grouping`, which iterates the collection and populates the resulting map with the result of folding elements in each group.

For example, it can be used to count the frequencies of characters in a text:

```kotlin
val charFrequencies: Map<Char, Int> = text.groupingBy { it }.eachCount()
```

### minOf() and maxOf()

These functions can be used to find the lowest and greatest of two or three given values, where values are primitive numbers or `Comparable` objects. There is also an overload of each function that take an additional `Comparator` instance, if you want to compare objects that are not comparable themselves.

```kotlin
val list1 = listOf("a", "b")
val list2 = listOf("x", "y", "z")
val minSize = minOf(list1.size, list2.size)
val longestList = maxOf(list1, list2, compareBy { it.size })
```

### Map.getValue()

This extension on `Map` returns an existing value corresponding to the given key or throws an exception, mentioning which key was not found.
If the map was produced with `withDefault`, this function will return the default value instead of throwing an exception.

```kotlin
val map = mapOf("key" to 42)
// returns non-nullable Int value 42
val value: Int = map.getValue("key")
// throws NoSuchElementException
map.getValue("key2")

val mapWithDefault = map.withDefault { k -> k.length }
// returns 4
val value2 = mapWithDefault.getValue("key2")
```

### Map.minus() operator

In Kotlin 1.0 you could easily get a copy of a read-only Map with a new key-value pair inserted with the extension operator `Map.plus()`. However to remove a key from the map you have to resort to less straightforward ways to like `Map.filter()` or `Map.filterKeys()`.
Now `Map.minus()` operator fills this gap. There are 4 overloads available: for removing a single key, a collection of keys, a sequence of keys and an array of keys.

```kotlin
val map = mapOf("key" to 42)
val emptyMap = map - "key"
```

### Array-like List instantiation functions

Similar to the `Array` constructor, there are now functions that create `List` and `MutableList` instances and initialize each element by calling a lambda:

```kotlin
List(size) { index -> element }
MutableList(size) { index -> element }
```

### String to number conversions

There is a bunch of new extensions on the String class to convert it to a number without throwing an exception on invalid number:
`String.toIntOrNull(): Int?`, `String.toDoubleOrNull(): Double?` etc.

```kotlin
val port = System.getenv("PORT")?.toIntOrNull() ?: 80
```

Note that these functions will box resulting numbers before returning them, since the return type is nullable, and nullable numbers are represented as boxed values.

Also integer conversion functions, like `Int.toString()`, `String.toInt()`, `String.toIntOrNull()`,
each got an overload with `radix` parameter, which allows to specify the base of conversion (2 to 36).

### onEach()

`onEach` is a small, but useful extension function for collections and sequences, which allows to perform some action, possibly with side-effects, on each element of the collection/sequence in a chain of operations.
On iterables it behaves like `forEach` but also returns the iterable instance further. And on sequences it returns a wrapping sequence, which applies the given action lazily as the elements are being iterated.

```kotlin
inputDir.walk()
        .filter { it.isFile && it.name.endsWith(".txt") }
        .onEach { println("Moving ${"$"}it to ${"$"}outputDir") }
        .forEach { moveFile(it, File(outputDir, it.toRelativeString(inputDir))) }
```

### Map.toMap() and Map.toMutableMap()

These functions can be used for easy copying of maps:

```kotlin
class ImmutablePropertyBag(map: Map<String, Any>) {
    private val mapCopy = map.toMap()
}
```

### Abstract collections

These abstract classes can be used as base classes when implementing Kotlin collection classes.
For implementing read-only collections there are `AbstractCollection`, `AbstractList`, `AbstractSet` and `AbstractMap`, and for mutable collections there are `AbstractMutableCollection`, `AbstractMutableList`, `AbstractMutableSet` and `AbstractMutableMap`.
On JVM these abstract mutable collections inherit most of their functionality from JDK’s abstract collections.
"""

Article(
  title = "Kotlin 1.1: What’s coming in the standard library",
  url = "https://blog.jetbrains.com/kotlin/2017/01/kotlin-1-1-whats-coming-in-the-standard-library/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "ilya.gorbunov",
  date = LocalDate.of(2017, 1, 24),
  body = body
)
