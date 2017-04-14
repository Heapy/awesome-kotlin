---
title: 'Exploring the Kotlin standard library'
url: http://beust.com/weblog/2015/10/30/exploring-the-kotlin-standard-library/
categories:
    - Kotlin
author: 'Cédric Beust'
date: Oct 30, 2015 17:30
---
[Standard.kt](https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/util/Standard.kt) is part of the Kotlin library and it defines some essential functions. What’s really striking about this source file is that it’s less than fifty lines long and that each of the function it defines (less than ten) is a one liner. Yet, each of these functions is very powerful. Here is a quick overview of the most important ones.

## let()

```kotlin
fun <T, R> T.let(f: (T) -> R): R = f(this)
```

`let()` is a scoping function: use it whenever you want to define a variable for a specific scope of your code but not beyond. It’s extremely useful to keep your code nicely self-contained so that you don’t have variables “leaking out”: being accessible past the point where they should be.

```kotlin
DbConnection.getConnection().let { connection ->
}
// connection is no longer visible here
```

`let()` can also be used as an alternative to testing against null:

```kotlin
val map : Map<String, Config> = ...
val config = map[key]
// config is a "Config?"
config?.let {
    // This whole block will not be executed if "config" is null.
    // Additionally, "it" has now been cast to a "Config" (no question mark)
}
```

## apply()

```kotlin
fun <T> T.apply(f: T.() -> Unit): T { f(); return this }
```

`apply()` defines an extension function on all types. When you invoke it, it calls the closure passed in parameter and then returns the receiver object that closure ran on. Sounds complicated? It’s actually very simple and extremely useful. Here is an example:


```kotlin
File(dir).apply { mkdirs() }
```

This snippet turns a `String` into a `File` object, calls `mkdirs()` on it and then returns the file. The equivalent Java code is a bit verbose:

```java
File makeDir(String path) {
  File result = new File(path);
  result.mkdirs();
  return result;
}
```

`apply()` turns this kind of ubiquitous code into a one liner.

## with()

```kotlin
fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
```

`with()` is convenient when you find yourself having to call multiple different methods on the same object. Instead of repeating the variable containing this object on each line, you can instead “factor it out” with a with call:

```kotlin
val w = Window()
with(w) {
  setWidth(100)
  setHeight(200)
  setBackground(RED)
}
```

## run()

```kotlin
fun <T, R> T.run(f: T.() -> R): R = f()
```

`run()` is another interesting one liner from the standard library. Its definition is so simple that it looks almost useless but it’s actually a combination of `with()` and `let()`, which reinforces what I was saying earlier about the fact that because all these functions from the standard library are regular functions, they can be easily combined to create more powerful expressions.

## Tying it all together

Of course, it’s actually possible (and encouraged) to combine these functions:

```kotlin
fun configurationFor(id: String) = map[id]?.let { config ->
  config.apply {
    buildType = "DEBUG"
    version = "1.2"
  }
}
```

 This code looks up a Config object from an id and if one is found, sets a few additional properties on it and then returns it. But we can simplify this code even further. This time, I’m providing a fully self-contained snippet so you can copy and paste it directly into [Try Kotlin](http://try.kotlinlang.org/) in order to run it yourself:

```kotlin
class Config(var buildType: String, var version: String)

val map = hashMapOf<String, Config>()

fun configurationFor(id: String) = map[id]?.let { config ->
    config.apply {
        buildType = "DEBUG"
        version = "1.2"
    }
}
```

Don’t you feel that this combination of `let()` and `apply()` feels a bit boilerplatey? Let’s rewrite it a bit more idiomatically:

```kotlin
fun configurationFor(id: String) = map[id]?.apply {
    buildType = "DEBUG"
    version = "1.2"
}
```

 Let’s unpack this rather dense snippet:

* Looking up a value on a hash map can be done either with `get()` or with the bracket notation, which is preferred.
* Since the key might not be present in the map, we use the safe dereference operator `?.` which guarantees that we will only enter `apply()` if the result is non null.
* Inside the `apply()` block, the this object is a `Config`, which lets us invoke functions on this object without any prefix. In this case, all we have is properties, but obviously, you could invoke regular functions just as well.
* Once that code has run, the altered `Config` is returned.

## use()

```kotlin
fun <T : Closeable, R> T.use(block: (T) -> R): R
```

Another interesting function of the standard library is [use()](https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/io/ReadWrite.kt#L154), which gives us the equivalent of [Java’s try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) and of C#’s [using](https://msdn.microsoft.com/en-us/library/yh598w02.aspx?f=255&MSPPError=-2147217396) statement.

This function applies to all objects of type `Closeable` and it automatically closes its receiver on exit. Note that as opposed to Java and C#, Kotlin’s `use()` is a regular library function and **not** directly baked in the language with a special syntax. This is made possible by Kotlin’s extension functions and closure syntax used coinjointly.

```java
// Java 1.7 and above
Properties prop = new Properties();
try (FileInputStream fis = new FileInputStream("config.properties")) {
    prop.load(fis);
}
// fis automatically closed
```

```kotlin
// Kotlin
val prop = Properties()
FileInputStream("config.properties").use {
    prop.load(it)
}
// FileInputStream automatically closed
```

Because Kotlin’s version is just a regular function, it’s actually much more composable than Java’s. For example, did you want to return this prop object after loading it?

```kotlin
// Kotlin
fun readProperties() = Properties().apply {
    FileInputStream("config.properties").use { fis ->
        load(fis)
    }
}
```

The `apply()` call tells us that the type of this expression is that of the object `apply()` is invoked on, which is `Properties`. Inside this block, `this` is now of type `Properties`, which allows us to call `load()` on it directly. In between, we create a `FileInputStream` that we use to populate this property object. And once we call `use()` on it, that `FileInputStream` will be automatically closed before this function returns, saving us from the ugly `try/catch/finally` combo that Java requires.

You will find a lot of these constructs in the [Kobalt build tool](http://beust.com/kobalt) code, feel free to browse it.
