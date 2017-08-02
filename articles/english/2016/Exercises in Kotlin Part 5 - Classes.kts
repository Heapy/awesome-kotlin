
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
After the last post [Exercises in Kotlin: Part 4 - Control flows and return](http://dnblog.ddesk/2016/04/exercises-in-kotlin-part-4-control-flows-and-return/), we shall be exploring classes and objects in the exercises in this post. While retaining many similarities to Java classes, there are actually significant differences in syntax as well and these help classes become a little easier to work with in Kotlin. And save a lot of unnecessary boilerplate code.

## Minimalist Syntax

Probably the minimalistic (and for all practical purposes useless) declaration of a class in Kotlin will be

```kotlin
class Foo
```

## A typical class

Thats it. No constructor, no fields, no body. However as is obvious this is not particularly useful. Let us look at a more useful one.

```kotlin
class Account (val number: String, var balance: BigDecimal) {
    fun deposit(amount: BigDecimal) {
        balance += amount
    }
    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}
```

Here `Account` is the class name. It has two properties `number` which is a `String` and `balance` which is a `BigDecimal`. Another aspect is `number` is a read-only property ie. you can't change the value within the body of the class (say in a method) after one is provided at the construction time. As mentioned in an earlier post read-only properties (aka `final` in Java terms) are identified by the keyword `val`, while read-write properties are identified by `var`. Notice the succinct constructor. The properties of the class that are required by the primary constructor are specified immediately after the class name within parenthesis. You can have variables declared in the constructor that are neither `val`s nor `var`s. However they can be used only during construction time and do not become properties of the class (more about that later). Thus unlike Java you don't need to write constructors just in order copy values from the constructor parameter to a member field. Also unlike Java you do not need to write getter/ setter methods in most cases (though you could if you had a specific reason to write custom getter/setters).

Client code for the account class could perhaps use it as follows. Do note that the object is instantiated using the `ClassName(parameter_list)` construct. It is essentially similar to Java except that there is no `new` keyword preceding it.

```kotlin
// Instantiate object
val acc1 = Account("123", BigDecimal("456.70"))

// get value of a property and print it
println(acc1.number)

// invoke a method
acc1.deposit(BigDecimal("234.56"))

// change the value of a property
acc1.balance = BigDecimal("567.89")
```

## Member Visibility

Now `balance` needs to be a `var` since it changes. But some might be disappointed at how it can be modified from outside the class. One solution would be to make it a `private var` as follows

```kotlin
class Account (val number: String, private var balance: BigDecimal) {
    fun deposit(amount: BigDecimal) {
        balance += amount
    }
    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}

fun somewhereElse() {
    val acc1 = Account("123", BigDecimal("456.70"))

    // The following wouldn't compile
    acc1.balance = BigDecimal("567.89")

    // But neither would the following line
    println(acc1.balance)
}
```

What if you wanted it to be read-only from outside the class but read-write within the class? You could choose to use the following approach of passing in a parameter to the constructor, using it to declare another property within the body of the class and then declaring that variable to have a private setter. Note that in this case `initBalance` is simply a parameter passed to the constructor (since it is not declared as either a `val` or a `var`), and can be accessed in a read only fashion only during the construction stage. (Thus other methods cannot use this parameter)

```kotlin
class Account (val number: String, initBalance: BigDecimal) {
    var balance = initBalance
        private set

    fun deposit(amount: BigDecimal) {
        balance += amount
    }

    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}

```

## Multiple constructors

Now let us assume that we wish to allow the constructor to use a String based value for balance though the balance property on the account should continue to be a BigDecimal. You could modify the class as follows.

```kotlin
class Account (val number: String, initBalance: String) {
    var balance = BigDecimal(initBalance)
        private set

    fun deposit(amount: BigDecimal) {
        balance += amount
    }
    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}
```

Here `balance` is being initialised by constructing a `BigDecimal` using the `String` value. But we know if the value passed in is not a number, a `NumberFormatException` will be thrown. But we can't see that in the constructor declaration anywhere. Huh?

I won't dwell long on that, but note that Kotlin (unlike Java) does _NOT_ use checked exceptions. Any method could throw any exception at any point in time and it does not have to be documented as a part of the method signature. A fuller discussion of this is beyond the scope of this post, but it should be stated that the conventional wisdom tends towards preferring unchecked exceptions and kotlin supports that style of programming.

Now let us assume we wish to have both types of constructors, one taking the initial balance as a `String` and another as a BigDecimal. Each class can have one primary and zero or more secondary constructors, each one of them eventually calling the primary constructor via the keyword `this`. Note the parameter lists to secondary constructors cannot declare properties using `val` or `var` since these are anyways in turn declared by the primary constructor.

```kotlin
class Account (val number: String, initBalance: BigDecimal) {
    constructor(number: String, initBalance: String) :
        this(number, BigDecimal(initBalance))

    var balance = initBalance
        private set

    fun deposit(amount: BigDecimal) {
        balance += amount
    }
    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}
```

So far our constructors have been only initialising properties. But what if you wanted them to do more. I have shown the way to do so by adding additional `println()` statements during both the primary and secondary construction phases as below.

```kotlin
class Account (val number: String, initBalance: BigDecimal) {
    init {
        println("Into primary constructor")
    }
    constructor(number: String, initBalance: String) :
        this(number, BigDecimal(initBalance)) {
        println("Into secondary constructor")
    }

    var balance = initBalance
        private set

    fun deposit(amount: BigDecimal) {
        balance += amount
    }
    fun withdraw(amount: BigDecimal) {
        if (balance < amount) {
            throw Exception("Insufficient funds in the account")
        }
        balance -= amount
    }
}
```

As can be seen the non property initialisation of primary constructor can be done in the `init{...}` block. Similarly secondary constructors can also include a block at the end which lists out the necessary statements to be performed.

## Data Classes

Very often we need basic classes which are primarily place holders for a collection of different values. Though java does not have an equivalent, in C, you would have called it a `struct` and in scala you would call it a `case class`. In Kotlin we have `data class`. eg.

```kotlin
data class Point(val x: Double, val y: Double)
```

These classes are tailored for using them as collection of other properties.

*   A data class automatically generates a sensible `equals()/hashcode()` functions for the class based on the properties declared in the primary constructor.
*   A sensible `toString()` is automatically provided
*   Another helper method `copy()` to selectively override some property values when creating a copy is also automatically provided.
*   You can also use a data class similar to a record of values easily since it also generates `component1()`, `component2()`, ... `componentN()` functions which can treat the class as a record of sequence of properties (in the same order as declared in the primary constructor).

Lets take a usage at a sample usage of the class above

```kotlin
val p1 = Point(1.1,2.2)
println(p1)
println(p1.x)
println(p1.component1())

val p2 = p1.copy(x=3.3)
println(p2)

val (x, y) = p1
println("" + x + ":" + y)
```

The output for the above will be as follows

```
Point(x=1.1, y=2.2)
1.1
1.1
Point(x=3.3, y=2.2)
1.1:2.2
```

Note, how a second copy (`p2`) was easily created from the first while allowing the ability to override a few values. Also how the `point` object can be destructured into its component values, in this case `val`s `x` and `y`. If the `Point` class had 3 properties, we would have had to provide 3 variables on the left hand side of the assignment operator for it to succeed

## Enum classes

Let us go back to the Account class example. Let us assume we wanted to add an account type member. Account type can either be `Savings` or `Current` (aka Checking). In order to allow the capability we will declare an enum class

```kotlin
enum class AccountType {
    Savings, Current
}
```

Now we can go ahead and modify the Account class as follows

```kotlin
class Account (val number: String, initBalance: BigDecimal, val type: AccountType){ ... }

fun somewhereElse() {
    val acc1 = Account("123", BigDecimal("456.7"),AccountType.Savings)
    println(acc1.type)
}
```

Now let us take a look at some of the sample usages of the enum class

```kotlin
fun somewhereElse() {
    // Iterate through all the enums
    for( accType in AccountType.values()) {
        println(accType)
    }

    // Every enum instance has two properties: name and ordinal
    println(AccountType.Savings.name)
    println(AccountType.Current.ordinal)

    // You can recreate an enum instance based on a String
    println(AccountType.valueOf("Savings"))
}
```

This program will result in the following output

```
Savings
Current
Savings
1
Savings
```

Thus as you can see, _You could iterate through all the enum types by getting an array of all its possible types using_ Every enum instance has two properties, a `name` which is a string and an `ordinal` which allows the enum values to be used a set of ordered values and allows them to be comparable. The name of course is the name as was provided in the declaration and ordinal is the sequence in the declaration of values. * You can use the string value of the enum to obtain an enum instance using `valueOf()`

Finally you can provide additional properties to each of the enum instances as follows

```kotlin
enum class Color(r: Int, g: Int, b: Int) {
    Red(255,0,0),
    Green(0,255,0),
    Blue(0,0,255)
}
```

We have looked at the basics of classes in this post. We will look at inheritance next.

"""

Article(
  title = "Exercises in Kotlin: Part 5 - Classes",
  url = "http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-5-classes/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dhananjay Nene",
  date = LocalDate.of(2016, 4, 29),
  body = body
)
