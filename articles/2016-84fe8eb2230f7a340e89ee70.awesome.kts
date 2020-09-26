
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Today I was wondering if I could achieve a more elegant way to compose functions with Kotlin. Let’s imagine we have this:

```kotlin
fun same(val: Int) = val  
fun twice(val: Int) = val * 2  
fun trice(val: Int) = val * 3
```

```kotlin
fun composed(val: Int) = same(twice(trice(int)))
```

That gets the job done, but wouldn’t it be better if there was a way to remove all those brackets and get something more similar to a pipeline? Keep in mind: the order of execution will be inverse (trice-twice-same), which is not intuitive.

Turns out there is, if we use two awesome Kotlin features: [_extensions_](https://kotlinlang.org/docs/reference/extensions.html)and [_operator overloading_](https://kotlinlang.org/docs/reference/operator-overloading.html). What are those?

### Extensions

**Extensions** let you add extra methods to any type, calling them as if you were declaring a method inside that type. An example could be:

```kotlin
fun Int.double() {  
  return this * 2  
}
```

```kotlin
2.double() == 4
```

This basically means: add a method to the type `Int` with name `double`, which you can then invoke on any `Int`. The keyword `this` is used to get the instance on which you’re invoking the method (the _receiver_ object). How this works in more detail is compiling to a static method of which `this` is the first parameter.

An interesting thing is that, in Kotlin, a function can be used as a type too! So we might be able to write something like this, as an extension function:

```kotlin
fun (() -> Unit).andHello() {  
  this()  
  println("Hello, world!")  
}
```

```kotlin
fun greet() {  
  println("Hey there!")  
}
```

```kotlin
fun greetAndHello() {  
  ::greet.andHello()  
}
```

`() -> Unit` is the way you can describe a function type (you’d do the same if it was a parameter). Inside the `()` you would put the parameters, `Unit` means it’s a `void` function (otherwise it would be the return type, for example `(Int, Int) -> Int` would be a function taking 2 `Int` parameters and returning an `Int`.

Here we are referencing a function with the `::` operator, and applying the `andHello()` method to it. Pretty neat!

### Operator overloading

Imagine you have an interesting type, and you’d like to use some standard operators with it. Wouldn’t it be great, for example, if you could access the elements in a map simply using square brackets? You can indeed!

```kotlin
val map = mapOf("a" to 1, "b" to 2, "c" to 3)
```

```kotlin
println(map["a"]) // 1
```

How does this work? Via **operator overloading**: you declare a method with the keyword `operator` in front of it, plus a certain name and signature, and then implement the method as you would do with any other method.

```kotlin
class Map<Key, Value> {  
  operator get(key: Key): Value {  
    // get and return the value from the map here  
  }  
}
```

You can then use the operator on that type normally. That can come in handy to redeclare operators in a way that makes more sense for your classes (maybe you want to sum two`Time` instances together?).

You can find the full list of available operators and respective signatures in the [operator overloading documentation page](https://kotlinlang.org/docs/reference/operator-overloading.html).

So, let’s take a small leap and imagine this is the final result we wanted:

```kotlin
fun composed(val: Int) = (::trice..::twice..::same) (val)
```

What are we trying to achieve? It’s pretty clear: we want to call, in sequence, in this order, the functions. Notice how the call order is now reversed compared to the first example (and makes it easier to read).

I’m using the `..` operator (range) because it reminds me of _chaining_ when we talk about functions. For example, it’s used to call multiple methods in sequence on an object in Dart.

How could we achieve that? All those functions are receiving one parameter and returning something, and we know that we can create an extensions for it.

```kotlin
operator fun <T, R, V> ((T) -> R).rangeTo(other: (R) -> V): ((T) -> V) {  
    return {other(this(it))  
    }}
```

What is this? Let’s go one step at a time.

First of all, we’re declaring an operator overload. The `rangeTo` method represents the `..` operator.

We’re declaring this extension on _any_ function of type `(T) -> R` so any function that takes a parameter of type `T` and returns a type `R`. Notice that `T` and `R` can be the same (for example, `Int`s).

This extension accepts another function, of type `(R) -> V`, so it will take the previous function return type and return another type.

Finally, this will generate another function, of type `(T) -> V` which is what we expect after calling both functions.

With the acquired knowledge, let’s look at this again, does it make more sense?

```kotlin
fun composed(val: Int) = (::trice..::twice..::same) (val)
```

We’re applying the `..` operator to `trice`, `twice`, and `same`, in order. Since the return type is a function, the result of `::trice..::twice` can be then chained with `::same`. The result of this last operation is again a function, which then we invoke with `(val)` as an argument.

Kotlin metaprogramming features are extremely intersting, and give you the chance to make your code more expressive, especially when you combine features like extensions and operator overloading. This is only a small example of what you can achieve, so start playing with it if you aren’t already!

"""

Article(
  title = "Composing functions in Kotlin with extensions and operators",
  url = "https://medium.com/@fourlastor/composing-functions-in-kotlin-with-extensions-and-operators-76a499f5b4b7#.72408kmql",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Daniele Conti",
  date = LocalDate.of(2016, 10, 27),
  body = body
)
