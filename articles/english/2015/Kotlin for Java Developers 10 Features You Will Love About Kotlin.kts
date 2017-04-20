
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
# Kotlin for Java Developers: 10 Features You Will Love About Kotlin

Kotlin is a statically typed JVM language built by Jetbrains, the makers of the IntelliJ IDE. [Kotlin](https://kotlinlang.org/) is built upon Java and provides useful features such as null-safety, data classes, extensions, functional concepts, smart casts, operator overloading and more.

This crash course into Kotlin for Java developers demonstrates the most important advantages that Kotlin has over Java and compares some of the language concepts. You can skim the code snippets and boldly marked parts for a quick overview but I recommend you read the whole article (even though it is rather long).

## Contents

1. Why Would I Care About Kotlin?
2. Null Safety
3. Data Classes
4. Extension Functions
5. Smart Casts
6. Type Inference
7. Functional Programming
8. Objects (aka Easily Create Singletons)
9. Default Arguments
10. Named Arguments
11. Bonus: Enforcing Best Practices
12. What Now?

## Why Would I Care About Kotlin?

What made me particularly interested in Kotlin is the fact that it is **uber-interoperable with Java** and is backed up by Jetbrains and their popular Java IDE IntelliJ. Why did that make me more interested in Kotlin you ask?

Well, interoperability with Java is majorly important because Java has been one of the [most widely used programming language](http://www.tiobe.com/index.php/content/paperinfo/tpci/index.html) for quite a while now. From a business perspective, this means that most real-world code is written in Java and that companies want to maintain their Java code base as long as possible — typically, the value of the legacy code is in the millions.

**With Kotlin, organizations have the chance to try out a new programming language with minimal risk.** Java files can be converted to equivalent Kotlin files which can then be worked on. Similarly, all types defined in Kotlin (like classes and enums) can be used from within Java just like any other Java type. From a developer point of view, it is great to be able to use the Java libraries that you are used to. You can use Java IO, JavaFX, Apache Commons, Guava and all your own classes right from Kotlin.

Also, hyperbolically, **a programming language is only as good as its tool support**. This is why the second point speaking in favor of Kotlin for me was that IntelliJ provides built-in language support. It also contains the aforementioned Java-to-Kotlin converter and code generators for Java and JavaScript from Kotlin code.

These two points also separate Kotlin from other JVM languages such as Scala, Ceylon, Clojure or Groovy.

**Alright, enough talk.** Let’s jump into the actual language features of Kotlin.

## 1) Null Safety

```kotlin
class Person {
    val givenName: String = ""
    val familyName: String = ""
    val address: Address? = null
}
```

In this example, givenName and familyName cannot be null — the program would **fail at compile-time**. You must explicitly make a variable nullable to be able to assign null to it. This is done via the “?” after the variable type. So the address property may be null in the given code.

Kotlin also fails at compile-time whenever a NullPointerException may be thrown at run-time — that is, when you try to call a method or reference a property from a nullable type:

```kotlin
val givenName: String? = null
val len = givenName.length
```

If you try to compile this, the Kotlin compiler will give you an error: “Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type kotlin.String?”. We’ll see how to handle these cases where you _know_ the variable _cannot_ be null in a second.

So far so good, but what are those safe and non-null asserted calls the compiler is talking about? **Safe calls simply return the value of the call as normally if the callee is not null, and return null otherwise:**

```kotlin
val givenName: String? = ""
val len = givenName?.length
```

In this case, len will be zero as expected. If givenName was null, len would also be assigned null. Thus the return type of givenName?.length is Int?, a nullable integer.

With non-null asserted calls, you assert to the compiler that you _know_ the variable _cannot_ be null at run-time at the position you use it:

```kotlin
val givenName: String? = "Roger"
val len = givenName!!.length
```

To work with nullable types effectively, the **Elvis operator** comes in handy. It allows you to use a nullable if it is not null and a default value otherwise:

```kotlin
val text: String? = null
val len = text?.length ?: -1
```

In this example, len will be -1 because the nullable text is in fact null so that the defined default value is used. You may have noticed that this is basically just the widely known _ternary operator_ where the first operand is equal to the expression itself:

```kotlin
val len = text?.length ?: -1
val len = text?.length ? text?.length : -1
```

These two lines are semantically the same.

## 2) Data Classes

For simple classes which mainly hold data, you can avoid a lot of boilerplate compared to Java code. Consider the following **typical data class in Java:**

```java
class Book {
    private String title;
    private Author author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
```

Lots of boilerplate code that you’ll skip when trying to find out what the class really does. **In Kotlin, you can define the same class concisely in one line:**

```kotlin
data class Book(var title: String, var author: Author)
```

Kotlin will also **generate useful hashCode(), equals(), and toString()** implementations. Printing a book will create an output like Book(title=Effective Java, author=Author(name=Joshua Bloch)).

**Challenge**: [Write an Author class](http://try.kotlinlang.org/) that will lead to an output like this!

Not only that, it will also allow you to **easily make copies of data classes:**

```kotlin
val book = Book("Effective Java", Author("Joshua Bloch"))
val copy = book.copy()
val puzzlers = book.copy(title = "Java Puzzlers")
val gof = book.copy(title = "Design Patterns", author = Author("Gang of Four"))
```

You can change arbitrary properties of the copied object by adding named parameters to the copy() method.

One more goody is the possibility to use **destructuring declarations** on data classes, retrieving the respective property values:

```kotlin
val book = Book("The Phoenix Project", Author("Kevin Behr"))
val (title, author) = book
```

## 3) Extension Functions

Kotlin allows us to **extend the functionality of existing classes without inheriting from them**. This is enabled by extension functions and extension properties. Let’s say you want to extend the GridPane class from the JavaFX GUI framework with a method to retrieve the elements in row i and column j:

```kotlin
fun GridPane.getElementAt(rowIndex: Int, columnIndex: Int): Node? {
    this.children.forEach {
        if (GridPane.getColumnIndex(it) == columnIndex && GridPane.getRowIndex(it) == rowIndex) {
            return it;
        }
    }

    return null;
}
```

There are several things to mention here. First, inside the extension function you can refer to the object on which it was called using “this”. Second, you use Kotlin’s forEach() function on the list of child nodes. This is equivalent to the forEach() method included in Java 8 and allows some functional-style programming. Third, inside the forEach(), you can refer to the current element using the implicit loop variable “it”.

Note that the return type comes after the parentheses containing the parameters and is a nullable Node since the method may return null.

**There’s one thing to be aware of:** If you try to call an extension function with the arguments that are also applicable for an existing member function inside the class, the member will always “win” — meaning that it will take precedence and overshadow your extension function.

## 4) Smart Casts

How often have you already cast objects where it was actually redundant? More often than you can count I bet, like this:

```java
if (node instanceof Leaf) {
    return ((Leaf) node).symbol;
}
```

The Kotlin compiler on the other hand is really intelligent when it comes to casts. Meaning: **it will handle all those redundant casts for you**. This is called **smart casts**.

The equivalent Kotlin code for the code snippet above looks like this:

```kotlin
if (node is Leaf) {
    return node.symbol;
}
```

The instanceof operator in Kotlin is called “is”. And, more importantly, there is no need to clutter your code with casts that the compiler can actually take care of.

Now this goes much further than just this simple case:

```kotlin
if (person !is Student)
    return

person.immatriculationNumber
```

In this case, if person were not a student, the control flow would never reach line 4. Accordingly, the Kotlin compiler knows that person must be Student object and performs a smart cast.

Let’s look at some **lazily evaluated conditional expressions**:

```kotlin
if (document is Payable && document.pay()) {  // Smart cast
    println("Payable document ${"$"}{document.title} was payed for.")
}
```

Conditionals like these use lazy evaluation in Kotlin, just like in Java. So if the document were not a Payable, the second part would not be evaluated in the first place. Hence, if evaluated, Kotlin knows that document is a Payable and uses a smart cast.

The same goes for disjunction:

```kotlin
if (document !is Payable || document.pay() == false) {  // Smart cast
    println("Cannot pay document ${"$"}{document.title}.")
}
```

When expressions are another place where Kotlin will apply smart casts wherever possible:

```kotlin
val result = when (expr) {
    is Expr.Number      -> expr.value
    is Expr.Sum         -> expr.first + expr.second
    is Expr.Difference  -> expr.first - expr.second
    is Expr.Exp         -> Math.pow(expr.base, expr.exponent)
}
```

Depending on the type of the object, you can simply use the respective properties in each when block.

Note: For the example above, the Expr class must be a sealed class with only these exact four subclasses.

## 5) Type Inference

**In Kotlin, you don’t have to specify the type of each variable explicitly, even though Kotlin _is_ strongly typed**. You can choose to explicitly define a data type, for example if you don’t want a small integer to be stored in an Int variables but rather a Short or even a Byte. You can do that using the colon notation where the data type stands behind the variable name:

```kotlin
val list: Iterable<Double> = arrayListOf(1.0, 0.0, 3.1415, 2.718)  // Only need Iterable interface

val arrayList = arrayListOf("Kotlin", "Scala", "Groovy")  // Type is ArrayList
```

You can choose to use explicit types as in Java but you’re also free to write more concise Python-like variable declarations. Explicit types are useful to reference the most general interface (which you should always do).

## 6) Functional Programming

While Java evolved to incorporate several functional programming concepts since Java 8, Kotlin has functional programming baked right in. This includes **higher-order functions, lambda expressions, operator overloading, lazy evaluation** and lots of useful methods to work with collections.

The combination of lambda expressions and the Kotlin library really makes your day easier when working with collections:

```kotlin
val numbers = arrayListOf(-42, 17, 13, -9, 12)
val nonNegative = numbers.filter { it >= 0 }
println(nonNegative)
```

Note that, when using lambda expressions with a single argument, Kotlin creates an implicit variable called “it” which refers to the lambda expression’s only argument. So the second line above is equivalent to:

```kotlin
val nonNegative = numbers.filter { it -> it >= 0 }
```

Kotlin provides all essential functional facilities such as **filter, map & flatMap, take & drop, first & last, fold & foldRight, forEach, **reduce_,_ and anything else the pragmatic functional programmer’s heart longs for:

```kotlin
println(numbers.take(2))  // First two elements: [-42, 17]

println(numbers.drop(2))  // List without first two elements: [13, -9, 12]

println(numbers.foldRight(0, { a, b -> a + b }))  // Sum of all elements: -9

numbers.forEach { print("${"$"}{it * 2} ") }  // -84 34 26 -18 24

---

val genres = listOf("Action", "Comedy", "Thriller")
val myKindOfMovies: Iterable<String> = genres.filter { it.length <= 6 }.map { it + " Movie" }
println(myKindOfMovies)  // [Action Movie, Comedy Movie]

```

## 7) Objects (aka Easily Create Singletons)

Kotlin has a keyword called _object_ which allows us to define an object, similar to a class. But of course, that object then only exists as a **single instance**. **This is a useful way to create singletons** but the feature is not restricted to only singletons.

Creating an object is as simple as this:

```kotlin
object CardFactory {

    fun getCard(): Card {
        // ...
    }
}
```

And you can then use that object like a class with static members:

```kotlin
fun main(args: Array<String>) {
    val card = CardFactory.getCard()
}
```

You can even **let your objects have superclasses**:

```kotlin
object SubmitButtonListener : ActionListener {

    override fun actionPerformed(e: ActionEvent?) {
        // Submit form...
    }
}
```

This concept is a powerful extension to the classes, interfaces & enums available in Java because oftentimes, elements of the domain model are inherently objects (they exist only once and thus should always have at most instance at runtime).

## 8) Default Arguments

Default arguments are a feature I’m dearly missing in Java because it’s just so convenient, makes your code more concise, more expressive, more maintainable, more readable, more everything-that’s-good.

In Java, you often have to duplicate code in order **define different variants of a method or constructor**. Take a look at this:

```java
public class NutritionFacts {
    private final String foodName;
    private final int calories;
    private final int protein;
    private final int carbohydrates;
    private final int fat;
    private final String description;

    public NutritionFacts(String foodName, int calories) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = -1;
        this.carbohydrates = -1;
        this.fat = -1;
        this.description = "";
    }

    public NutritionFacts(String foodName, int calories, int protein, int carbohydrates, int fat) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.description = "";
    }

    public NutritionFacts(String foodName, int calories, int protein, int carbohydrates, int fat, String description) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.description = description;
    }
}
```

Ugh, pretty bad. But it gets even worse if you want to provide more different constructors or add more possible attributes.

The example above demonstrate the so-called _telescoping anti-pattern_ prevalent in Java. You can improve this design by using the Builder pattern instead.

But with Kotlin, you can do this even easier using default values for arguments:

```kotlin
class NutritionFacts(val foodName: String,
                     val calories: Int,
                     val protein: Int = 0,
                     val carbohydrates: Int = 0,
                     val fat: Int = 0,
                     val description: String = "") {
}
```

This makes each of the parameters with a default value an _optional parameter_. And it actually gives you many more possibilities to invoke the constructor than the Java class above:

```kotlin
val pizza = NutritionFacts("Pizza", 442, 12, 27, 24, "Developer's best friend")
val pasta = NutritionFacts("Pasta", 371, 14, 25, 11)
val noodleSoup = NutritionFacts("Noodle Soup", 210)
```

Note that you may also want to make this sort of class a _data class_ to have methods like equals() and toString() generated for you.

**In short: You get more for less. And keep your code clean at the same time.**

## 9) Named Arguments

Default arguments become even more powerful in combination with named arguments:

```kotlin
val burger = NutritionFacts("Hamburger", calories = 541, fat = 33, protein = 14)
val rice = NutritionFacts("Rice", 312, carbohydrates = 23, description = "Tasty, nutritious grains")
```

**Anyone reading the code knows what’s going on** without having to look at what the parameters mean. This increases readability and can make you more productive when used correctly. For example, this is especially useful when you have several boolean parameters like this:

```kotlin
myString.transform(true, false, false, true, false)
```

Unless you’ve implemented that function 10 seconds ago, there’s no way you know what’s going on here (there’s no guarantee you know even _if_ you’ve implemented it 10 seconds ago).

Make your life (and that of your fellow developers) easier by using named arguments:

```kotlin
myString.transform(
    toLowerCase = true,
    toUpperCase = false,
    toCamelCase = false,
    ellipse = true,
    normalizeSpacing = false
)
```

## 10) Bonus: Enforcing Best Practices

Generally, Kotlin enforces many of the best practices you should follow when using Java. You can read about them in Josh Bloch’s book [“Effective Java”](http://www.oracle.com/technetwork/java/effectivejava-136174.html).

First of all, the use of **val vs. var** promotes making every variable final that is not supposed to change — while also providing a more concise syntax for it. This is useful when creating immutable objects for example.

This way, beginners learning the language will also learn to follow this practice right from the start because you tend to think about whether to use **val** or **var** each time and learn to prefer val to var when possible.

Next, Kotlin also supports the principle to either design for inheritance or prohibit it — because in Kotlin, you have to explicitly declare a class as **open** in order to inherit from it. That way, you have to remember to _allow_ inheritance instead of having to remember to _disallow_ it.

## What Now?

**If this overview made you curious** to learn more about Kotlin, you can [check out my 10 beginner tutorial videos for Kotlin](http://petersommerhoff.com/dev/kotlin/kotlin-beginner-tutorial/) or **[go straight to the full course](https://www.udemy.com/kotlin-course/?couponCode=READERSONLY9USD) (with 95% reader discount)**.

**The course is beginner-friendly and starts completely from scratch.** If you already know Java or a comparable language, you’ll still find it a valuable resource to get to know Kotlin.

"""

Article(
  title = "Kotlin for Java Developers: 10 Features You Will Love About Kotlin",
  url = "http://petersommerhoff.com/dev/kotlin/kotlin-for-java-devs/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Peter Sommerhoff",
  date = LocalDate.of(2015, 12, 12),
  body = body
)
