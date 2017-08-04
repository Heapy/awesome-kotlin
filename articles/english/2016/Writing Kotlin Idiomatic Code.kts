
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
# Writing Kotlin Idiomatic Code

[Kotlin](http://kotlinlang.org/), a new JVM language created by Jetbrains, is quickly becoming the go-to programming  language used for Android development at Hootsuite. Its many features and Java interoperability make writing code much faster, cleaner, and less verbose. However, without being aware of language idioms it can be easy to fall back to old Java patterns. In order to maximize the utility of this new language, it is important to familiarize yourself with writing idiomatic Kotlin code. Kotlin’s features, such as the nullable types, lambdas, and delegates, differentiate the language from Java and are useful for writing effective code.

[![Kotlin](http://code.hootsuite.com/wp-content/uploads/2016/08/Kotlin-500x111.png)](https://kotlinlang.org/)

**Null Checks and Safe Casts**

Null references, called a [“billion-dollar mistake”](https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare) by their creator, are a major problem in Java. Null checks are possible in Java, but they are cumbersome, verbose, and easy to miss. In Kotlin, nullability is part of the type system and the language forces null checks for nullable types. The Java way of performing a null check in Kotlin would look like this:

```kotlin
val school : School = null

if (school != null && school.students != null){
	doSomething(school.students.get(“billy”))	
}
```

Fortunately, Kotlin provides a number of easy ways of dealing with this cleanly. The safe call operator “?” provides a simple way of dealing with nullability. When combined with the let{} method, it allows for chained operations like this:

```kotlin
school?.students?.let { doSomething(it.get(“billy”)) }
```

In the above line of code, the null safety operator used on x and y prevents the code in the let block from being executed if either value is null. When the code in the let block runs it represents the non-null value of y. let blocks can also be useful when needing to access a nullable mutable variable several times since it makes a copy of the variable when it is executed. let blocks can also be used with the safe cast operator in order to replace type checks in if blocks like this:

```kotlin
if (x is String){
   println(x)
}
// can be replaced with
(x as? String)?.let { println(x) }
```

Finally, the safe elvis operator that allows you to return an alternative value or throw an error in the case of null:

```kotlin
val parent = node.getParent() ?: return null
val name = node.getName() ?: throw IllegalArgumentException("...")
```

**Lambdas, Closures, and Higher Order Operators**

Some other key features that Kotlin has over Java 7 are lambdas and closures, which allow your code to be more concise and functional. In Kotlin, functions are treated as first class citizens, which means means you can easily pass function parameters without needing to create an interface. For example, the Java way of setting an OnClickListener would look like this:

```kotlin
some_view.setOnClickListener(object : View.OnClickListener {
   override fun onClick(view: View) {
       println("clicked!")
   }
})
```

Using lambdas, this can be shorted like this:

```kotlin
val listener: (View) -> Unit = { println(“clicked!”) }
some_view.setOnClickListener (listener)

// Or you can use closures 

some_view.setOnClickListener {doSomething()}
```

Using higher order operators and closures, you can remove many loops that you would otherwise need in Java. For example, if one wished to sentence from a list of words with spaces in between one could do the following:

```kotlin
listOf("This", "is","a", "sentence").reduce { accumulator, s -> accumulator +" " + s }
```

In the above example listOf is used to create an immutable list of words. Then, the reduce operator is used to concat all of the words by accumulating them and appending a space to the end of each word to produce the string “This is a sentence”.

Kotlin also allows for higher order operators to be chained. In this more advanced example, this code finds the first perfect square that contains the numbers 123:

```kotlin
(1..100000).map { it * it }.find { it.toString().contains(Regex(".*123.*")) }
```

In this example we use the range operator to get a list of numbers, then we map that list to their squares. Afterwards, we use the find operator to find the first number that matches the regular expression. There many more higher order operators that you can read about [here](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html#functions).

**Delegates**

In Java, if you wish to expose an encapsulated property for a class, you need to write a getter and setter. For example, if you are in a custom view and wish to expose the value of a checkbox, you would need to expose property through a getter and setter. In Kotlin, properties automatically generate getters and setters, so this step is unnecessary. You can delegate out the property of the enclosed checkbox object like this:

```kotlin
var isChecked: Boolean by object {
   operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
       return checkbox.checked
   }

   operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
       checkbox.checked = value
   }
}
```

This uses two interesting features of Kotlin: object expressions and property delegates. An anonymous object is created which this property is delegated out to, meaning you can still access it using property access syntax and getters and setters are generated.

Another problem that delegates can solve is composition. It is often convenient when building a model that depends on views to allow each view to build it’s own component. If you wish to abstract away direct access to the views in this situation, property delegates provide a clean solution. Here’s an example of a class with an EditText view inside it:

```kotlin
class EditTextDelegate {
   // represents some editable text view
   var editText: EditText = EditText();

 operator  fun getValue(thisRef: Any?, property: KProperty<*>): String? {
       return editText.text
   }

 operator  fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
       editText.text = value
   }
}
```

You can then use your view class as a delegate like this:

```kotlin
class ComposedObject(textDelegate: EditTextDelegate){
  var text: String? by textDelegate
}
```

This allows you to ask each of the views that compose this model for their properties without needing to deal with getters and setters. Additionally, you can now access all of these abstracted view properties as if they are normal properties.

**Functions and Functional Programming**

Many of these new Kotlin features mean that functional programming is now an option when solving problems. One of the most important features that Kotlin provides for functional programming is immutability in both default lists and and objects, with it’s val type. In Effective Java Second Edition Joshua Bloch states “Classes should be immutable unless there’s a very good reason to make them mutable.” Many of these same arguments for immutability apply to Kotlin including thread safety, easy shareability, and simplicity. In Kotlin one can define an immutable class like this:

```kotlin
class Person(val name: String, val city: String)
```

When functional programming, recursion is key to solving problems without changing state. Recursive functions, however, can be expensive if they aren’t tail recursive because of the stack space required. Kotlin solves this problems with the tailrec operator which optimizes a tail recursive function (a function where the the the last call is recursive) to reuse the stack. For example:

```kotlin
tailrec fun fib(count: Long, seq: List<Int> = listOf(1,1)): List<Int> =
if (seq.size >= count) seq else fib(count, seq + (seq.last() + seq[seq.lastIndex - 1]))
```

Here we see that the last call in this function is a recursive call, which means that it is able to be optimized. Additionally, in the above function we can see that using operator overloading we are able to use the + operator on the list. This allows for more terse syntax because it returns a new list, not a boolean like List.add() and allows for immutable lists to be used.

Many of the the other parts of the this post such as lambdas and nullable types can be used to enhance functional programming. For more information on functional programming in Kotlin read Mike Hearn’s excellent [blog post](https://medium.com/@octskyward/kotlin-fp-3bf63a17d64a#.geo4mq2nx) goes into more detail.

**Conclusion**

Kotlin is language fast growing language, and there are still new practices being developed. Hopefully this blog post provides a clear set of examples of using Kotlin’s feature set more effectively.

Thank you to the Android team and Kimli for helping me write and edit this blog post.

"""

Article(
  title = "Writing Kotlin Idiomatic Code",
  url = "http://code.hootsuite.com/writing-kotlin-idiomatic-code/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Denis Trailin",
  date = LocalDate.of(2016, 8, 31),
  body = body
)
