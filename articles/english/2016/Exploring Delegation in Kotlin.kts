
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I’m a huge fan of interfaces in Java and also of composition over inheritance. Inheritance is some magic wiring with tight coupling that creates lots of friction when evolving a code base. I’ve written about interfaces before several times, for example [here](http://codemonkeyism.com/how-to-improve-programming-with-interfaces/) or [here](http://codemonkeyism.com/generation-java-programming-style/). Looking at Kotlin I wanted to see what I can do with interfaces and composition.

Take this example:

```kotlin
interface HasName {
  fun getName():String
}

class Person(): HasName {
   override fun getName():String = "Stephan"
}

fun main(args: Array<String>) {
  val p = Person()
  // Prints 'Stephan'
  println(p.getName())
}

```

Why would one introduce a `HasName` interface in this case? It reduces dependencies and coupling. This makes reasoning about code easier and speeds up compilation, especially incremental compilation.

How would we use the `HasName` interface? A function that checks for a good name could look like this:

```kotlin
fun goodName(p:Person):Boolean {
  return p.getName() == "Stephan"
}
```

Now the function depends on the whole of person, not just the name part. The function can not operate on a lot of things, just persons. What about other things with `Name`, like Dogs? Rewritting the code to

```kotlin
fun goodName(n:HasName):Boolean {
   return n.getName() == "Stephan"
}
```

makes the function more reusable.

Inside our `Person class` we have the code for the `HasName` functionality. It woud be nicer to be able to reuse the functionality from somewhere else.

In Kotlin we can delegate Interfaces to objects:

```kotlin
interface HasName {
  fun getName():String
}

class NameMixin(val n:String): HasName {
  override fun getName() = n
}

class Person(n:NameMixin): HasName by n {
}

fun main(args: Array<String>) {
    val p = Person(NameMixin("Stephan"))
    // Prints 'Stephan'
    println(p.getName())
}

```

This looks a little unnatural to me, as the user of the `Person class` needs to know about the `NameMixin`. See if we can do better

```kotlin
class Person(n:String): HasName by NameMixin(n) {
}

fun main(args: Array<String>) {
  val p = Person("Stephan")
  // Prints 'Stephan'
  println(p.getName())
}

```

looks cleaner as the consumer of `Person` does not know about `NameMixin`.

Kotlin can also use data classes (Thanks to Christian Helmbold for pointing this out).

```kotlin
interface HasName {
    val name: String
}

data class NameMixin(override val name: String) : HasName

class Person(named : HasName) : HasName by named
```

If we want to have more control, we can use a Factory inside `Person`.

```kotlin
class Person(n:String): HasName by Person.name(n) {
  companion object Name {
    fun name(n:String) = NameMixin(n)
  }
}

```

The name of the `companion object`, in this case `Name`, is optional but helps to structure the factory methods.

The mixin can be accessed with `this`:

```kotlin
class Person(n:String): HasName by Person.name(n) {
  ...
  fun isStephan():Boolean {
    // Access name property
    return this.getName() == "Stephan"
  }
}
```

Using a `companion object` with a factory method is better, because it gives us more control in the creation of the Mixin. But the control is not optimal. I wish we had something like

```kotlin
// Not working Kotlin code!
class Person(n:String): HasName {
    val name delegates from HasName
}
```

where I have access to name and more control over it. It would also be nice to have a way to access other Mixins from a Mixin. But overall, some nice functionality in Kotlin.

"""

Article(
  title = "Exploring Delegation in Kotlin",
  url = "http://codemonkeyism.com/exploring-delegation-in-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "@codemonkeyism",
  date = LocalDate.of(2016, 4, 4),
  body = body
)
