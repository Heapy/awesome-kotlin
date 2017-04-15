
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Yesterday I had a co-worker come to me with an interesting bug in a Kotlin class he was writing. He had a read-only property with an inline initializerlike this:

```kotlin
class MyProgressBar(context: Context) : ProgressBar(context) {  
    val bounds = Rect()</pre>

<pre name="dc70" id="dc70" class="graf graf--pre graf-after--pre">    override fun setProgress(progress: Int) {  
        super.setProgress(progress)  
        with(bounds) {  
            val right = left + ((progress.toFloat() / max) * width)  
            set(left, top, right.toInt(), bottom)  
        }  
    }  
}
```

Even though there were no IDE or lint warnings, when he tried to create an instance of that class, it would crash with a _NullPointerException_ when trying to read the _bounds_ property.

“How can a non-null, read-only property with an initializer ever be causing a _NullPointerException_?” you might ask. The answer to that question lies in the order that Kotlin runs constructors and initializers.

### Constructors vs Initializers

One aspect of class creation in Kotlin that might surprise people coming from other languages is that, when writing a class, there a few places that you can define code that will be executed when you create an instance of the class:

#### Property initializers

When you declare a property, you can immediately assign it a value like this:

```kotlin
val count: Int = 0
```

You’ll frequently use constant values as initializers, but you can also call a function or use a property delegate in order to run arbitrary code.

#### **Initializer blocks**

If you want more complex code, you can also use an initializer block, defined with the _init_ keyword:

```kotlin
init {  
    /* do some setup here */  
}
```

You can define those blocks anywhere at the top level of a class declaration, and they will be executed as part of class construction. You can even define more than one initializer block if you want, and they will all be executed.

#### Constructors

Kotlin also has [constructors](https://kotlinlang.org/docs/reference/classes.html#constructors), which can be defined in the class header or in the body of the class definition. You can define multiple secondary constructors, but only one will be called when you create a class instance unless the constructor explicitly calls another one. Constructors can also have default argument values which are evaluated each time the constructor is called. Like property initializers, these can be function calls or other expressions that will run arbitrary code.

### Execution order

All of those features are great, but it can be easy to overlook how they all interact, especially when inheritance is involved. So what order will code run if you define a class with all of them?

First, default constructor arguments are evaluated, starting with argument to the constructor you call directly, followed by arguments to any delegated constructors. Next, initializers (property initializers and _init_ blocks) are executed in the order that they are defined in the class, top-to-bottom. Finally, constructors are executed, starting with the primary constructor and moving outward through delegated constructors until the constructor that you called is executed. The constructor order is probably the most surprising, since no matter where in the class the constructor is defined, it is always executed after _all_ initializers have run.

That’s a lot of rules, so let’s create an small program to help us visualize all of this:

```kotlin
open class Parent {
    private val a = println("Parent.a")

    constructor(arg: Unit=println("Parent primary constructor default argument")) {
        println("Parent primary constructor")
    }

    init {
        println("Parent.init")
    }

    private val b = println("Parent.b")
}

class Child : Parent {
    val a = println("Child.a")

    init {
        println("Child.init 1")
    }

    constructor(arg: Unit=println("Child primary constructor default argument")) : super() {
        println("Child primary constructor")
    }

    val b = println("Child.b")

    constructor(arg: Int, arg2:Unit= println("Child secondary constructor default argument")): this() {
        println("Child secondary constructor")
    }

    init {
        println("Child.init 2")
    }
}
```

If we construct an instance of _Child_ by calling its secondary constructor with _Child(1)_, what will be printed to the console?

Here’s the output:

```kotlin
Child secondary constructor default argument  
Child primary constructor default argument  
Parent primary constructor default argument  
Parent.a  
Parent.init  
Parent.b  
Parent primary constructor  
Child.a  
Child.init 1  
Child.b  
Child.init 2  
Child primary constructor  
Child secondary constructor
```
n
As you can see, initializers are run top to bottom at the beginning of a class’ primary constructor. If you call a secondary constructor, the constructor that gets delegated to will be run before the secondary constructor. And most importantly, superclasses will be fully constructed before any subclass constructors will be run.

### Conclusion

The crash I talked about at the beginning of the article was due to the fact that the parent constructor was calling a method that was overridden in the child class. That overridden method tried to access a property it had defined with an initializer. But since subclass initializers don’t run until the superclass constructor finishes, the property hadn’t been initialized yet.

So there you have it: Kotlin’s execution order for constructors and initializers is straight forward, but there are a lot of pieces that come in to play. Since it’s not documented very explicitly, it’s easy to get tripped up.

"""

Article(
  title = "An in-depth look at Kotlin’s initializers",
  url = "https://medium.com/keepsafe-engineering/an-in-depth-look-at-kotlins-initializers-a0420fcbf546#.bf7j3he2b",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "AJ Alt",
  date = LocalDate.of(2016, 9, 23),
  body = body
)
