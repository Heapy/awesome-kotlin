
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
The Android SDK has come a long way since its early days to make developing for Android as comfortable and efficient as possible. Yet there’s one thing that still gets in our way: The Java language. While more modern languages like C# or Swift make it possible to write code that is elegant and at the same time easy to read and understand, we’re still stuck with Java 7 for Android Development. Its cumbersome syntax for executing code on a different thread asynchronously, for example, or the fact that you require separate libs for even the most basic tasks like null-safe string comparison really make it a pain for development.

## A New Hope

At this year’s Berlin DroidCon, I found one session in the conference schedule very intriguing: [A talk by Svetlana Isakova from JetBrains](http://www.droidcon.de/session/kotlin-swift-android) on [the Kotlin language](http://kotlinlang.org/) – “The Swift of Android”, according to the session title. Of course I attended, and during the talk I couldn’t help but start to smile, and that smile grew bigger and bigger: Kotlin and its accompanying extensions and plugins for Android addressed so many annoying issues of Java development for Android! No more enviously looking over to Apple’s Swift for iOS development! Also I found the syntax to be very intuitive. I simply had to try it out ASAP – and what better opportunity to do so than Zühlke’s educational Camp?

## An Easy Language To Get Into

For the 2015 Camp, there were already plans for exploring the development for Android Wear by writing an app that connected to a sensor via Bluetooth Low Energy. The app should get historic data from the sensor and forward it to a connected Android Wear device, including notifications for certain events.

The colleague who was going to develop the Android app together with me wasn’t aware of my decision to use Kotlin for the project until right before we started coding. But nevertheless, we both were able to pick up the new language and start writing our app very quickly. This is due to several things.

First of all, the [reference documentation](http://kotlinlang.org/docs/reference/) is very well written and easy to get into. JetBrains also provides a plugin for IntelliJ, Android Studio and Eclipse that greatly helps development in Kotlin. The Android Studio plugin we used not only provides proper debugging, auto-completion, code navigation and full refactoring support, but it also lets developers convert existing Java code files to Kotlin with just one click.

Another thing that makes it easy to use Kotlin in your Android project is the fact that, much like Scala, it’s **100% interoperable with Java code**! Kotlin compiles to regular Java Byte Code, and you can use existing Java classes in your project from Kotlin and vice versa. If you are familiar to Android development and want to write a new Activity you can do it like this:

```kotlin
class MyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        ...
    }
}
```

Simply inherit from `Activity` as before! The language is different, but you can use the framework and your own existing code, or for that matter, any existing Java library. Therefore you can gradually migrate to Kotlin if you don’t want to convert all your Java code to Kotlin right away.

The most important thing though is the incredible **ease of use** of the language. Being under development since 2010 and having gone open source in 2012, Kotlin has become very mature by now. It has adapted the great features of so many other languages. Here are some examples.

## Some Nifty Kotlin Features

When adding a property to your class, you don’t write the backing field and getters and setters separately. Instead, you do:

```kotlin
public var context: Context? = null
    get
    set (value) {
        ${"$"}context = value
        if (context != null) {
            setupBluetooth()
        }
    }
```

Looks familiar? Yup, that’s **C#-style properties** right there, which are accessed without calling the getter/setter explicitly. Also **classes and their member functions are final by default** and need to be declared `open` or `abstract` in order to be overridden.

Like Scala, Kotlin does away with static methods and fields, which are so easily misused in Java. Instead of using those, Kotlin facilitates the declaration of **singleton objects**. Instead of declaring a class with some “instance” method, you simply declare an object right away, which can be used directly:

```kotlin
object MySingleton {
    fun myFunction() { [...] }
}
MySingleton.myFunction()
```

**Null-safety** is also an important aspect of Kotlin. Every type is non-nullable by default, and you get a compile error when trying to assign or pass `null` to it. A type is made nullable by putting a question mark behind it. When accessing a nullable variable, Kotlin enforces a null check:

```kotlin
var foo: String? = "bar" // foo is nullable now
var length = foo.length() // Compile error
var character = foo?.charAt(0) // OK; result is null if foo == null
var length2: Int = if (foo != null) foo.length() else 0 // Inlined null check
var nonNullFoo: String = foo ?: "baz" // Elvis operator; result is "baz" if foo = null
var letter = foo?.subSequence(0, 1) ?: return
if (foo != null) { // Null check in the surrounding code
    character = foo.last() // -> Smart-cast to non-null
}
var bang = foo!!.capitalize() // Forced to non-null. CAUTION: If it is null, you get an NPE!
```

The concept is similar to Swift’s Optionals or Nullable Types in C#, though the syntax is a little different here. But it’s extremely comfy to use, and it prevents the dreaded `NullPointerException`s effectively.

If you’re missing a useful method on a foreign class, simply write an **extension function**:

```kotlin
fun String?.append(other: String): String {
    if (this == null) {
        return other
    }
    return this + other
}
var hi: String? = "Hello "
var all = hi.append("World")
```

Notice how the method extends the _nullable_ String? This way you can gracefully handle `null` objects without using null checks as shown above.

## At last – Lambdas!

And last but not least there’s Kotlin’s **Lambda** support. Especially in the context of any asynchronous communication, this is a godsend. For example, lambdas provide an elegant way of handling the many asynchronous callbacks that are used by Android’s Bluetooth Low Energy API. The syntax for declaring and using lambdas looks like this:

```kotlin
var filtered = intArray.filter { it > 0 }
intArray.forEachIndexed { index, element ->
    println(index + “:” + element)
}
```

And here’s a function declaration that takes a lambda argument:

```kotlin
fun myFunction(param1: String, callback: (result: Int?, error: String?) -> Unit) {
    [...]
}
```

When passing Lambdas to functions, they are usually in-lined in the function call, but you can also assign them to variables as **first-class objects** and pass them around later. Here’s how that looks like:

```kotlin
private var myCallback: Function1<String, Unit> = { param1: String ->
    println("Hi ${"$"}param1")
}
```

And it doesn’t stop there; these features of Kotlin and many more make it possible to write code that’s elegant and easily understandable. But let’s get into some Android stuff.

## Android Development With Pleasure

In addition to Kotlin’s own features, the [Anko library](https://github.com/JetBrains/anko) provides Extensions that eliminate a lot of the boilerplate code typically found in Android projects. One example is the **simplified “toast” statement**. Instead of the usual “Toast-makeText-context-content-length-show” sermon, you just do:

```kotlin
toast("A toast to Android!")
```

The **findViewById** procedure has also been simplified:

```kotlin
val myText: TextView = find(R.id.myTextView)
```

**View hierarchies** can be quickly defined in Kotlin in the Activity code, including layout and listeners:

```kotlin
linearLayout {
    button("Login") {
        textSize = 26f
        onClick {
            doSomeStuff()
        }
    }.layoutParams(width = wrapContent) {
        horizontalMargin = dip(5)
        topMargin = dip(10)
    }
}
```

Another extremely helpful feature is the simplified use of Android’s ugly and unwieldy `AsyncTask`. With Anko, **executing asynchronous code** on a worker thread and processing results on the UI thread is as simple as:

```kotlin
async {
    doSomeWork() // Long background task
    uiThread {
        result.text = "Done"
    }
}
```

Tired of **SQLite handling**, with all its `Cursor` handling and countless `try...catch` blocks? Just extend Anko’s `ManagedSQLiteOpenHelper`! It contains lots of little utilities that greatly simplify your database access code. Here’s an `INSERT` using your `ManagedSQLiteOpenHelper`:

```kotlin
use { // Now "this" is the SQLiteDatabase, opened for read/write
    try {
        insert("ShoppingList", // Table name
                "id" to item.id,
                "title" to item.title,
                "checked" to item.checked)
    } catch (exception: SQLiteException) { // SQL exceptions can still occur, of course
        error("INSERT threw exception: ${"$"}exception")
    }
} // At the end of the block the DB is automatically closed
```

Looks neat? Wait till you see some `SELECT` code:

```kotlin
var result: List<ShoppingItem> = ArrayList<ShoppingItem>()
use {
    try {
        result = select("ShoppingList", "id", "title", "checked")
                .where("checked = {checkedArg}", "checkedArg" to 1)
                .orderBy("id", SqlOrderDirection.ASC)
                .exec {
            parseList(classParser<ShoppingItem>())
        }
    } catch (exception: SQLiteException) {
        error("SELECT threw exception: ${"$"}exception")
    }
}
// Now process the result list
```

How’s that for brevity? Just assemble your `SELECT` statement using fluent-style syntax, then automatically transform the returned `Cursor` into a `List` of your data items. The Parser is automatically generated from your data class, provided it has a constructor that matches the selected columns.

And this is just the tip of the iceberg! Anko also provides simplified mechanisms for Intent dispatching, Service retrieval, Logging and a lot more.

## Conclusion

Having tried out Kotlin in the context of Android development now, I can definitely see it becoming my new language of choice for Android development. It’s well made, mature and makes coding an absolute joy. The official Android documentation may be written for Java, but it’s easy to “translate” it to Kotlin, so any Android developer should definitely give Kotlin & Anko a try. Here’s hoping that Google will provide an Android API reference & guide in Kotlin in the near future, and that it will eventually become the de-facto standard for Android!

"""

Article(
  title = "Android + Kotlin = <3",
  url = "http://blog.zuehlke.com/en/android-kotlin/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Michael Sattler",
  date = LocalDate.of(2015, 7, 20),
  body = body
)
