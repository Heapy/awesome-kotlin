
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[![](https://blog.frankel.ch/wp-content/resources/code-improvement-kotlin/icon_Kotlin.png)](https://kotlinlang.org/)This week, I tried to improve my pet Android application developed in Kotlin. As I was very new to Kotlin when I started it, most of the code just looks like Java written in Kotlin.

### Starting simple

Here’s one such snippet, that needs to initialize both a template message and its argument:

```kotlin
val messageTemplate: String
val params: Array <any>when (shownCount) {
    0 -> {
        messageTemplate = noItem
        params = arrayOf<any>()
    }
    1 -> {
        messageTemplate = itemShowSingle
        params = arrayOf<any>(totalCount)
    }
    else -> {
        messageTemplate = itemShowCount
        params = arrayOf(shownCount, totalCount)
    }
}
```

#### Pair
Kotlin versions after M3 don’t offer `Tuple` anymore, but specialized versions like `Pair` and `Triple` in the _stdlib_. Also, the `to()` extension function can create such pairs on the fly, without resorting to the constructor.

#### Destructuring declarations

Kotlin allows to initialize multiple variables at once when the function returns a `Pair`, a `Triple` or any _data_ class for that matter.

This is the new improved code:

```kotlin
val (messageTemplate, params) = when (shownCount) {
    0 -> noItem to arrayOf()
    1 -> itemShowSingle to arrayOf(totalCount)
    else -> itemShowCount to arrayOf(shownCount, totalCount)
}
```

### _stdlib_ also known as Kotlin’s toolbelt

A common task in Android is to send data to the SQLite database through `ContentValues` instances. Naive Java ports look like the following snippet:

```kotlin
val taskValues = ContentValues()
taskValues.put(T_NAME_COL, task.name)
taskValues.put(T_DESC_COL, task.description)
taskValues.put(T_PRIO_COL, task.priority)
taskValues.put(T_DONE_COL, if (task.done) 1 else 0)
taskValues.put(T_LIST_COL, list.id)
if (task.imagePath != null) {
    taskValues.put(T_IMG_COL, task.imagePath.toString())
}
```

Kotlin’s _stdlib_ provides a number of interesting functions.

#### apply()

`apply()` is an extension function set on `Any` type. It accepts a null-returning function as a parameter, applies it to the receiver and return the later. Note that in the scope of the lambda, `this` is the receiver.

#### let()

`let()` is another extension function set on `Any` type. It accepts a transforming function as a parameter and calls it with the receiver as the parameter.

#### Null-safe call operator

The `.?` operator will only called the right-hand operand if the left-hand operand is not null. It’s Kotlin’s idiomatic way for null checking

Using them in combination gives the following code:

```kotlin
val taskValues = ContentValues().apply {
    put(T_NAME_COL, task.name)
    put(T_DESC_COL, task.description)
    put(T_PRIO_COL, task.priority)
    put(T_DONE_COL, if (task.done) 1 else 0)
    put(T_LIST_COL, list.id)
    task.imagePath?.let { put(T_IMG_COL, it.toString()) }
}
```

### Going further

Another common Android task it to read data stored in UI components:

```kotlin
val id = nameView.tag as Long?
if (id != null) {
    task.id = id
}
```

#### Safe cast

Casts are handled in Kotlin with the `as` operator. However, nullable and non-nullable types don’t belong to the same hierarchy. Hence, if one casts `null` to a non-nullable type, a `ClassCastException` will be thrown at runtime. To avoid that, us the `as?` smart cast operator.

Using `let()` and the `.?` operator in conjunction with smart cast produces the next improvement:

```kotlin
(nameView.tag as? Long)?.let { task.id = it }
```

### The whole shebang

The final common snippet is related to querying SQLite databases. Basically, the usual flow is to create the object, create the cursor, iterate over it to read values and set object’s attributes from them. It looks like this:

```kotlin
fun findById(id: Long): Task {
    val cursor = readableDatabase.rawQuery("SELECT A LOT FROM TABLE", arrayOf(id.toString()))
    cursor.moveToFirst()
    val name = cursor.getString(1)
    val description = cursor.getString(2)
    val imagePath = cursor.getString(3)
    val task = Task(name, description)
    if (!cursor.isNull(4)) {
        val date = cursor.getLong(4)
        task.alarm = Date(date)
    }
    cursor.close()
    task.id = id
    if (imagePath != null) {
        task.imagePath = Uri.parse(imagePath)
    return task
}
```

#### Local functions

Methods in Java are about visibility and scoping. Basically, if one wants a method not reused in other classes, one sets the _private_ visibility. If this method is used only in another method, it just pollutes the class namespace. Embedded functions are a way to declare a function inside another one to avoid this pollution. Kotlin (as well as Scala) allows that.

#### with()

Another useful function from _stdlib_ is with(). Available on any type, it takes 2 parameters: the first is the receiver, the second a transforming function and calls the later on the former.

Combining those with some of the above features can improve the code a lot:

```kotlin
fun findById(id: Long): Task {

    fun toTask(cursor: Cursor): Task {
        with(cursor) {
            moveToFirst()
            val name = getString(1)
            val description = getString(2)
            val imagePath = getString(3)
            return Task(name, description).apply {
                if (!isNull(4)) {
                    val date = getLong(4)
                    val alarm = Date(date)
                    this.alarm = alarm
                }
                this.id = id
                imagePath?.let { this.imagePath = Uri.parse(it) }
                close()
            }
        }
     }

    return readableDatabase.rawQuery(
            "SELECTA LOT FROM TABLE",
            arrayOf(id.toString())).let { toTask(it) }
}
```

### Conclusion

Learning a new programming language is easy: many books promise to do that in 21 days. The hard part is how to write idiomatic code in that new language. This is a long and arduous journey, that needs a lot of reading such idiomatic code, writing “bad” code yourself and improving it over the course of many iterations.

**To go further:**

* [Destructuring Declarations](https://kotlinlang.org/docs/reference/multi-declarations.html)
* [Smart Casts](https://kotlinlang.org/docs/reference/typecasts.html#smart-casts)
* sdtlib functions [reference](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/#functions)
* [Safe calls](https://kotlinlang.org/docs/reference/null-safety.html#safe-calls)
* [Local functions](https://kotlinlang.org/docs/reference/functions.html#local-functions)

"""

Article(
  title = "Code improvements with Kotlin",
  url = "https://blog.frankel.ch/code-improvement-kotlin",
  categories = listOf(
    "Kotlin",
    "null"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Frankel",
  date = LocalDate.of(2016, 4, 17),
  body = body
)
