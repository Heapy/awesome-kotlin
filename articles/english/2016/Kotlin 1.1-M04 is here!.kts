
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We are glad to present you the fourth milestone of the upcoming Kotlin release. We’re wrapping up the development of the version 1.1, with the final release planned for Q1 2017. Most features are already in decent shape, so it is a good time to try it and give us your feedback. We will appreciate it a lot!

As with other milestone releases, we give **no backwards compatibility guarantees** for new language and library features. Anything introduced in milestone releases of 1.1 is **subject to change** before the final 1.1 release.

Please do share your feedback regarding the new features or any problems that you may run into with this release, via [YouTrack](https://youtrack.jetbrains.com/issues/KT), [forums](https://discuss.kotlinlang.org) and [Slack](http://kotlinlang.slack.com/).

This milestone brings a significant rework of coroutine syntax and semantics, making coroutines simpler and more flexible. It also contains standard library enhancements, new language features and compiler plugins, numerous features and improvement in the JS backend, and many other fixes and updates.  
The new release also includes all features introduced in the [Kotlin 1.0.6](https://discuss.kotlinlang.org/t/kotlin-1-0-6-eap/2117/10), including updates for compatibility with **Android Studio 2.3 Beta 1**.

The full changelog is available [here](https://github.com/JetBrains/kotlin/blob/1.1-M04/ChangeLog.md) and some key changes are listed below:  

## Coroutines

We have significantly re-thought the coroutines design, making it simpler, composable and more powerful:

* All suspending and coroutine builder functions now have intuitive signatures (no more weird transformations to memorise).
* There is only one basic language concept of suspending functions and the corresponding suspending function types. The special `coroutine` keyword was dropped. The coroutine is now simply an instance of a suspendable computation that is started using `startCoroutine` function from the standard library.
* Complex suspending functions can be composed out of more primitive suspending functions. In this release they can tail-call other suspend functions, but this restriction will be lifted in the future.
* Suspending functions can be defined to wrap any callback-style API and can be freely used inside any asynchronous coroutine. The controllers are not needed anymore. The `generate` and `yield` pair, that builds synchronous sequences, restricts suspensions inside generate blocks using `@RestrictsSuspension` annotation.
* Type inference for coroutines is now implemented. You can omit types in most use-cases for coroutine builders and the types will be inferred automatically.

The classical `await` suspending function can now be implemented via a tail-call to the `suspendCoroutine` suspending function that is a part of the standard library:

```kotlin
suspend fun <T> await(f: CompletableFuture<T>): T =
    suspendCoroutine<T> { c: Continuation<T> ->
        f.whenComplete { result, exception ->
            if (exception == null) // the future has been completed successfully
                c.resume(result) 
            else // the future has been completed with an exception
                c.resumeWithException(exception)
        }
    }
```

The corresponding builder is called `async` and implemented via the `startCoroutine` function:

```kotlin
fun <T> async(block: suspend () -> T): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    block.startCoroutine(completion = object : Continuation<T> {
        override fun resume(value: T) {
            future.complete(value)
        }
        override fun resumeWithException(exception: Throwable) {
            future.completeExceptionally(exception)
        }
    })
    return future
}
```

And they can be used together to write a more naturally-looking code with futures:

```kotlin
async {
    val original = asyncLoadImage("...original...") // creates a Future
    val overlay = asyncLoadImage("...overlay...")   // creates a Future
    ...
    // suspend while awaiting the loading of the images
    // then run `applyOverlay(...)` when they are both loaded
    return applyOverlay(original.await(), overlay.await())
}
```

However, futures are just one of many supported use-cases for coroutines. The full overview of the coroutine implementation and their usage samples are available in the revised [KEEP document](https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md).

We think that now we have got a great design of coroutines for Kotlin, but we realize that it has not been battle-tested enough. Therefore we are going to release it in 1.1 under an opt-in incubation flag. Starting from this milestone you’ll get “This feature is experimental: coroutines” warning when using coroutines. You can turn off this warning with `-Xcoroutines=enable` compiler flag or disable this features with `-Xcoroutines=error` compiler flag. The corresponding setting is also available under Kotlin compiler settings in the IDEA. To set this option for a gradle project you can add `kotlin.coroutines=enable` or `kotlin.coroutines=error` to local.properties file at the root of project.

If you are using [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) library please use updated version `0.2-alpha-1`, adapted to the newest changes in the coroutines design. This version also introduces `yieldAll` method in the generate scope. Please see the [readme file](https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md) for further details.

## Language features

### Type for properties can be inferred from getter

For example in the code below the type for the property `foo` will be inferred as `String`. See the issue [KT-550](https://youtrack.jetbrains.com/issue/KT-550) for some more details.

```kotlin
val foo get() = ""
```

### Floating point related features, fixes and improvements

Floating point number comparisons now use IEEE 754 compliant comparison where the type is known statically to be `Double` or `Float`. For ranges of floating point numbers we’ve introduced specialized `ClosedFloatingPointRange` interface, which provides its own comparison method, so that extension operations employing ranges, like `coerceIn`, could be implemented on top of that. Its instances are obtained with the operator `..` invoked on two `Float` or `Double` values. See [KT-4481](https://youtrack.jetbrains.com/issue/KT-4481) and [KT-14651 for details.](https://youtrack.jetbrains.com/issue/KT-14651)

### Interception of delegated property binding

It is possible now to intercept delegate to property binding using the `provideDelegate` operator.  
For example, if we want to check property name before binding, we can write something like this:

```kotlin
class ResourceLoader<T>(id: ResourceID<T>) {
    operator fun provideDelegate(thisRef: MyUI, property: KProperty<*>): ReadOnlyProperty<MyUI, T> {
        checkProperty(thisRef, property.name)
        ... // property creation
    }

    private fun checkProperty(thisRef: MyUI, name: String) { ... }
}

fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> { ... }

class MyUI {
    val image by bindResource(ResourceID.image_id)
    val text by bindResource(ResourceID.text_id)
}
```

Here method `provideDelegate` will be called in constructor initializer for class `MyUI`. So, we can check property consistency at the moment of creation. Earlier such checks were only possible at the moment of calling getter or setter.

Unfortunately, the feature is not yet properly documented, but you can use [this draft document](https://github.com/orangy/KEEP/blob/fabb56360f2d7a293ac720cace89cd445da3c919/proposals/attach-to-property.md#createdelegate) as an initial reference.

### Enhanced nullability of some JDK methods

Some functions in JDK have a nullability contract defined in the documentation, some do not accept null values, some never return null, and yet others can return null sometimes.  
Unfortunately, the JDK does not use any annotations to express such contracts and only states them in the docs. Once before 1.0 we used an external annotations artifact for the JDK which could be supplied to the compiler to alter the signatures of JDK functions, but that approach wasn’t reliable enough.

Now we’re introducing another approach: embedding the information required to enhance the JDK signatures directly into the compiler. As a first step we cover the nullability of a small subset of the API:

* `java.util.Optional` factory and member functions:
    * `of`: that doesn’t allow null values
    * `ofNullable`, it takes a nullable value, and return an `Optional` of non-nullable type.
    * `get` always return non-nullable value.
* `java.lang.ref.Reference` and all of its inheritors, like `WeakReference` and `SoftReference`:
    * `get` returns a nullable value, as it can become null at any moment if the referenced object is garbage collected.
* default methods of JDK’s `Iterator`, `Iterable`, `Collection`, `List`, `Map` which are exposed as platform-dependent functions of Kotlin builtin collection interfaces.
* java functional types, now they have non-platform types in their invocation methods when they’re constructed with non-platform types.

These enhancements are safe in most cases. In particular, they are safe when the enhanced type becomes more specific (non-nullable) in return position or more general (nullable) in parameter position. But when the type is changed in the opposite direction, the change will be breaking.  
We strive not to introduce such breaking enhancements unless not respecting the correct nullability would lead to an exception in runtime. So for example `Optional.of` now takes a non-nullable argument which is more restrictive, but trying to pass `null` to that method would result in an exception anyway.  
On the other hand, we decided not to specify the correct nullability for `File.listFiles` which actually can return null sometimes, because in most cases there’s no meaningful fallback other than to throw another exception.

### Other changes

* Problem of using a non-public member from a public inline function could now be resolved with the `@PublishedApi` annotation. When it’s applied on an _internal_ member, it becomes effectively public and available to invoke from a public inline function.. See [KT-12215](https://youtrack.jetbrains.com/issue/KT-12215) for details.
* `const val` is now inlined at the call site (see [KT-11734](https://youtrack.jetbrains.com/issue/KT-11734))
* SAM conversions have now the same priority in overload resolution as regular members. That fixes [KT-11128](https://youtrack.jetbrains.com/issue/KT-11128) and alike.
* We consider our choice of `mod` name for `%` (remainder) operator a mistake with some not so nice consequences (see e.g. [KT-14650](https://youtrack.jetbrains.com/issue/KT-14650)). Therefore we decided to introduce `rem` operator, deprecate the `mod` and provide all tools to make this transition smooth.

## Standard library

### String to number conversions

There is a bunch of new extensions on the `String` class to convert it to a number without throwing an exception on invalid number: `String.toIntOrNull(): Int?`, `String.toDoubleOrNull(): Double?` etc.  
Beware that these functions will box resulting numbers before returning them, as the return type assumes it.

Also integer conversion functions, like `Int.toString()`, `String.toInt()`, `String.toIntOrNull()`, each got an overload with `radix` parameter, which allows to specify the base of conversion.

We would like to thank [Daniil Vodopian](https://github.com/voddan) for his substantial contribution to the development of these functions.

### onEach

`onEach` is a small, but useful extension function for collections and sequences, which allows to perform some action, possibly with side-effects, on each element of the collection/sequence in a chain of operations.  
On iterables it behaves like `forEach` but also returns the iterable instance further. And on sequences it returns a wrapping sequence, which applies the given action lazily as the elements are being iterated.

Thanks to [Christian Brüggemann](https://github.com/cbruegg) for the initial prototype.

## JavaScript backend

### `external` instead of `@native`

From this milestone `@native` annotation becomes deprecated and instead you have to use `external` modifier.  
Unlike the JVM target, the JS one permits to use `external` modifier with classes and properties.  
Note, that you don’t need to mark members of `external` classes as `external`: this modifier  
is automatically inherited by the members. So, instead of

```kotlin
@native fun alert(message: Any?): Unit {}
```

you can write

```kotlin
external fun alert(message: Any?)
```

### Improved import handling

You can now describe declarations which should be imported from JavaScript modules more precisely.  
If you add `@JsModule("<module-name>")` annotation on an external declaration it will be properly imported to a module system (either CommonJS or AMD) during the compilation. For example, with CommonJS the declaration will be imported via `require(...)` function.  
Additionally, if you want to import a declaration either as a module or as a global JavaScript object, you can use `@JsNonModule` annotation

Let’s see the full example below. You can import jQuery library to a Kotlin source file like this:

```kotlin
@JsModule("jquery")
@JsNonModule
@JsName("$")
external abstract class JQuery {
    fun toggle(duration: Int = 0): JQuery
    fun click(handler: (Event) -> Unit): JQuery
}

@JsModule("jquery")
@JsNonModule
@JsName("$")
external fun JQuery(selector: String): JQuery
```

In this case, JQuery will be imported as a module named `jquery` and alternatively, it can be used as a `$`-object, depending on what module system Kotlin compiler is configured to use.

You can use these declarations in your application like this:

```kotlin
fun main(args: Array<String>) {
    JQuery(".toggle-button").click {
        JQuery(".toggle-panel").toggle(300)
    }
}
```

You can check the generated JS code for this snippet for the CommonJS and “plain” module systems [here](https://gist.github.com/ligee/50d30ad9bca5ea925ff7d913ff232004).

## How to Try It

**In Maven/Gradle:** Add [http://dl.bintray.com/kotlin/kotlin-eap-1.1](http://dl.bintray.com/kotlin/kotlin-eap-1.1) as a repository for the build script and your projects; use 1.1-M04 as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to _Tools → Kotlin → Configure Kotlin Plugin Updates_, then select “Early Access Preview 1.1” in the _Update channel_ drop-down list, then press _Check for updates_.

The **command-line compiler** can be downloaded from the [Github release page](https://github.com/JetBrains/kotlin/releases/tag/v1.1-M04).

**On [try.kotlinlang.org](http://try.kotlinlang.org)**. Use the drop-down list at the bottom-right corner to change the compiler version to 1.1-M04.

Merry Kotlin!

"""

Article(
  title = "Kotlin 1.1-M04 is here!",
  url = "https://blog.jetbrains.com/kotlin/2016/12/kotlin-1-1-m04-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ilya Chernikov",
  date = LocalDate.of(2016, 12, 21),
  body = body
)
