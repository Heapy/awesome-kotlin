
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## Kotlin — Love at first line

Kotlin 1.0 has been [released](https://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/)
 a few days ago and it’s time to check out its awesome features.

For those of you who might have been living under a rock and don’t know what Kotlin is — Kotlin is a new JVM programming language that tries to “fill in the gaps” that Java has. It’s 100% interoperable with Java — meaning that you can have a mixed project that contains Kotlin & Java classes. The classes are compiled to Java bytecode, and that bytecode is “runnable” on Java6+, which makes it runnable on Android.

The language is awesome, and combined with the [Anko](https://github.com/Kotlin/anko) library it’s even [awesome-er*](http://www.urbandictionary.com/define.php?term=Awesome-er). I won’t be explaining the syntax of the language (for that you have the official [page](https://kotlinlang.org/)). I’m just going to try and expose a few of it’s awesome features.

### [Single-Expression functions](https://kotlinlang.org/docs/reference/functions.html#single-expression-functions)

If we have a function that boils down to a single expression, we can use the single-expression function syntax:

    override fun equals(other: Any?) = other is Task && other.id == id

### [Extensions](https://kotlinlang.org/docs/reference/extensions.html)

Extensions allow us to extend any existing class by adding functions and properties without the need to inherit from that class.

```kotlin
fun ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = false) =

    LayoutInflater
        .from(context)
        .inflate(layoutRes, this, attachToRoot)
```

The extension function above adds the **.inflate(...)** method to the ViewGroup class, so instead of doing this every time:


```kotlin
val view = LayoutInflater
    .from(parent)
    .inflate(R.layout.todo_list_item, parent, false)
```

now we can just do this:


```kotlin
val view = parent.inflate(R.layout.todo_list_item)
```

or:


```kotlin
val view = parent.inflate(R.layout.todo_list_item,
    attachToRoot = true)
```

I guess you already noticed that Kotlin also supports [default arguments](https://kotlinlang.org/docs/reference/functions.html#default-arguments).

### [Lambda](https://kotlinlang.org/docs/reference/lambdas.html#lambda-expressions-and-anonymous-functions)

Intentionally left blank.

### [Optionals / Null safety](https://kotlinlang.org/docs/reference/null-safety.html)

Forget about NullPointerExceptions. Kotlin has 2 types of variables, nullable and non-nullable. If we declare our variable as non-nullable — the compiler won’t let us assign a null value to it. Only nullable variables can be null.


```kotlin
var nonNullable: String = "This is a title" // Non-nullable variable
var nullable: String? = null // Nullable variable
```

In case of the *nonNullable* variable, we can safely call methods on it, without any null checks, because it *cannot have a null value*.

In case of the *nullable* variable, we can safely call methods with the help of the *safe-trasversal operator (?.)*, and forget about null checks:


```kotlin
val length = nullable?.length
```

The code above won’t fail, even if the *nullable* variable has a null value. In that case, the value of the *length* variable will be *null*.

### [Elvis operator](https://kotlinlang.org/docs/reference/null-safety.html#elvis-operator)

The result of a **safe call (?.)** is always a nullable variable. So in cases where we are calling a method on a null variable — the result will be null.

That can be inconvenient sometimes. For example, in the code sample above, we want our **length** variable to be a non-null variable because it’s logical for it to have a value of 0 in case of a null string.

In cases like that, we can use the **elvis operator ( ?: )**.


```kotlin
val length = nullable?.length ?: 0
```

The elvis operator will use the left side value if it’s not null. In case the left side value is null, it will use the right non-nullable value.

You can even use it to make your sanity checks more readable.


```kotlin
    ...
}
```

With help of the elvis operator, the same method in Kotlin will look like this:


```kotlin
public fun myMethod(str: String?) {
    // Sanity check
    str ?: return

    ...
}
```

The great part of using this is that the compiler will **smart cast** our **str** variable to a non-nullable variable after the “*str ?: return*” line.

### [Optional getters/setters](https://kotlinlang.org/docs/reference/properties.html#properties-and-fields)

Unlike in Java, where we are used to define all of our class properties **_private_** and write getters and setters, in Kotlin we write getters and setters only if we want to have some custom behaviour.

The simplest definition looks like this:


```kotlin
class Task {
   var completed = false
}
```

And we can access the property:


```kotlin
val task = Task()
if (task.completed) ...
```

If we wan’t to expose just the getter and allow setting the value only from within the class:


```kotlin
var completed = false
    private set
```

And if we want to have completely custom behaviour:


```kotlin
var completedInt = 0
var completed: Boolean
    get() = completedInt == 1
    set(value) { completedInt = if (value) 1 else 0 }
```

### [Lazy properties](https://kotlinlang.org/docs/reference/delegated-properties.html#lazy)

Kotlin allows us to declare lazy properties — properties that are initialized when we first access them.


```kotlin
private val recyclerView by lazy {
    find<RecyclerView>(R.id.task_list_new)
}
```

When we first access the **recyclerView** property, the lambda expression is evaluated and the returned value from the lamdba is saved and returned in that and every subsequent call.

### [Observable properties](https://kotlinlang.org/docs/reference/delegated-properties.html#observable)

In Kotlin we can observe properties. The syntax for declaring such properties is the following:


```kotlin
var tasks by Delegates.observable(mutableListOf<Task>()) {
    prop, old, new ->
        notifyDataSetChanged()
        dataChangedListener?.invoke()
}
```

This means that we are going to be notified every time the value of our property changes (the provided lambda will be called).


## [Anko extensions](https://github.com/Kotlin/anko)

Anko is a great library and has a lot of great extensions. I will list a couple.

### find<T>(id: Int)

It replaces the findViewById(int id) method. This extension function returns the view already cast to the given type T, so there is no need to cast it.


```kotlin
val recyclerView = find<RecyclerView>(R.id.task_list_new)
```

### [SQLite](https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite)

Anko has great support for SQLite databases. For a complete overview, check their [guide](https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite). I will just show you one example:


```kotlin
fun allTasks() = use {
    select(table)
        .orderBy(completed)
        .orderBy(priority, SqlOrderDirection.DESC)
        .exec {
            parseList(parser)
        }
}
```

The **use {...}** function opens the database for us, and closes it after the given lambda executes. So we don’t have to worry about closing it anymore and can forget about all those **try {...} catch(...) {...} finally {...}** blocks. Inside the lambda that we pass to the **use** function, **this** references our database. That is the power of Kotlin’s **type-safe builders**. Read more about them [here](http://blog.jetbrains.com/kotlin/2011/10/dsls-in-kotlin-part-1-whats-in-the-toolbox-builders/) and [here](https://kotlinlang.org/docs/reference/type-safe-builders.html).

The **select(...)...exec {}** call chain, selects data from the database. And the **parseList(parser)** call parses the rows of data and returns a list of objects, that our **parser** returns. The definition of our **parser** is:


```kotlin
val parser = rowParser {
    id: Int, name: String, priority: Int, completed: Int ->
    Task(id, name, priority, completed)
}
```

"""

Article(
  title = "Kotlin — Love at first line",
  url = "https://medium.com/@dime.kotevski/kotlin-love-at-first-line-7127befe240f#.p5hp6dxlh",
  categories = listOf(
    "Anko",
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dimitar Kotevski",
  date = LocalDate.of(2016, 2, 21),
  body = body
)
