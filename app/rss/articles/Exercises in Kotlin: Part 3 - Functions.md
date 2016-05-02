---
title: 'Exercises in Kotlin: Part 3 - Functions'
url: http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-3-functions/
categories:
    - Kotlin
author: Dhananjay Nene
date: Apr 20, 2016 00:00
---
We looked at a bit of syntax and variable declarations in the last post [Exercises in Kotlin: Part 2 - High level syntax and Variables](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-2-high-level-syntax-and-variables/). We explore functions in this post. Here's how a typical function in Kotlin would look like.

```kotlin
fun add(first: Int, second: Int): Int {
    // will not compile
    // first = first + 2. Because first is a val and cannot be reassigned
    return first + second
}
```

As noted earlier, the arguments are declared with the names followed by the types. Also even though `val` is not specified, each argument is automatically a `val` and can not be reassigned within the function. Also the return type is specified after the argument list followed by a colon.

In this case, since the body of the function is an expression it can also alternatively be specified as

```kotlin
fun addExpr(first: Int, second: Int) = first + second
```

Note in particular that the curly brackets are replaced by `=`. And because the compiler can infer the return type, it is no longer required to provide it (though it would work just as well if you did provide one)

### Functions can have default arguments

```kotlin
fun powerOf(value: Double, power: Double = 2.0) = Math.pow(value, power)
```

The second argument `power` is no longer required to be specified, (when not specified its value will default to 2.0\. Thus allowing both the following statements

```kotlin
powerOf(3.0)         // This will return 9.0
powerOf(3.0, 4.0)    // This will return 81.0
```

This makes it convenient to require fewer overloaded functions. The order of the arguments needs to be retained, and all the arguments after the ones specified will need to have default values. While this makes a lot of defaulting easy, it still is a little problematic for selectively overriding defaults for only few of the arguments. This is helped by the next feature.

### Arguments can also be named

```kotlin
fun contrived(first: String = "First",
              second: String = "Second",
              third: String = "Third",
              fourth: String = "Fourth",
              fifth: String = "Fifth") =
        first + " " + second + " " + third + " " + fourth + " " + fifth

// ....

fun someOtherFunction() {
    contrived(second="2nd", fourth="4th")
    // the above returns "First 2nd Third 4th Fifth"
}
```

As shown in the example above using a combination of default and named arguments we can choose to have smaller function invocations by specifying only the non defaults. Thus only the arguments whose default values need to be overridden, making the code both terse and more readable

### Functions can have variable arguments

```kotlin
fun showStrings(suffix: String, vararg items: String) {
    for(item in items) {
        print(item + " ")
    }
    print(suffix)
    println()
}

fun somewhereElse() {
    showStrings("!", "Hello", "World") // prints "Hello World !"
}
```

Function can have variable number of arguments so long as all of them are of the same type. The type of the declared argument then becomes an array of that type. Thus in the above example, `items` is an array of String or `Array<String>`.

The above function also introduces you to a for statement when used with an array. The syntax is very simple and natural, `for itemVar in arrayVar { ... }`

(_I imagine there can be only one `vararg` type argument and that has to be declared at the end but never attempted to do anything differently_)

### Functions can have nested functions

```kotlin
fun showStrings2(suffix: String, vararg items: String): String {
    val sb = StringBuffer()
    fun appendToBuffer(item: String) {
        sb.append(item)
        sb.append(" ")
    }
    for(item in items) { appendToBuffer(item) }
    appendToBuffer(suffix)
    return sb.toString()
}

....

fun somewhereElse() {
    showStrings2("!", "Hello", "World") // returns "Hello World !"
}
```

As shown above, `appendToBuffer` is a nested function (called local function) It can access variables in the parent functions namespace (in the above situation - `sb`.).

### Aside: if - else can also be an expression

In java, `if`-`else` is a statement. And has a complementary ternary operator `? :` which is an expression. Kotlin merges both by allowing use of `if` - `else` as an expression, and such if/else expressions can be used as a part of the function expression body (or a part of regular block bodies as well). eg.

```kotlin
fun isBlankString(arg: String) = if (arg.trim() == "") true else false
```

For an if/else to be an expression, both if and else branches have to be specified and followed by an expression each

### Functions can be tail recursive.

Yes! They can be.

```kotlin
fun factorial(n: Int): Int {
    tailrec fun factorial(accumulator: Int, n: Int): Int = if (n == 1) accumulator else factorial(accumulator * n, n - 1)
    return factorial(1, n)
}
```

If a function is written in a form that lends itself to [tail recursion](https://en.wikipedia.org/wiki/Tail_call) the compiler generates optimised code that uses a loop based version which no longer risks a stack overflow.

### Extension functions.

This is a very interesting and very useful feature to Kotlin. It is the ability to seemingly externally add a method to a class. Thus the function `isBlankString` introduced above could be rewritten as

```kotlin
fun String.isBlankString() = this.trim() == ""

fun somewhereElse() {
    " ".isBlankString() // returns true
}
```

Note that in an extension function, you can use `this` to refer to the receiver of the function (in this case the String object on whom the `isBlankString` function is called). Also that the receiver class is resolved statically and not dynamically. So if you had an extension function defined for a `Base` class and a `Derived` class that inherits from `Base`, and called it on a variable declared of type `Base` but actually referencing an instance of type `Derived` the `Base` version of the extension function will be called.

### Infix functions

Class member functions or extension functions with a single argument, can in turn be declared as infix which allows from syntactic sugar in terms of how that function is invoked. Thus the function

```kotlin
infix fun Int.toThePowerOf(n: Int) = Math.pow(this.toDouble(), n.toDouble()).toInt()
```

could be invoked using either of the two ways, the latter becoming feasible only because of the keyword `infix` being used in the function declaration above.

```kotlin
5.toThePowerOf(3)
5 toThePowerOf 3
```

### Higher order functions

Higher order functions are functions which take functions as parameters or as a return type. For that let us first understand how a function can be alternatively represented as a variable using a function type.

```kotlin
fun double(n: Int) = n * 2
```

What if we wish to represent the function above as a variable. We need to understand the type of the function. In this case it happens to be of the type which takes a single `Int` and returns an `Int`. Knowing that we can now declare it as a variable

```kotlin
val doubleAsArg = { n : Int -> n * 2}
```

The way to invoke the function above would be similar to the way double is invoked

```kotlin
doubleAsArg(5) // would return 10
```

But the more interesting capability that is now possible is that you can write a function which takes another function as an argument, and in turn pass doubleAsArg as an argument to the newly written function. eg.

```kotlin
fun twice(function: (Int) -> Int, arg: Int) = function(function(arg))

fun somewhereElse() {
    twice(doubleAsArg,5) // will return 20
}
```

`doubleAsArg` could have been also declared using an alternative syntax as follows. For all practical purposes they are identical. However the declaration below makes the function type very explicit (it was inferred in the declaration above)

```kotlin
val doubleAsArgRedux: (Int) -> Int = { n -> n * 2}
```

#### Aside: Function Types

Just like `String`, `Int` etc, since functions are also things that can be passed around, they also have types. As is shown above, doubleAsArg above has the type `(Int) -> Int`. In general a function taking n arguments each of type Type1..TypeN, and returning a type TypeReturn will have the type `(Type1, Type2,..TypeN) -> TypeReturn`

### Lambdas

A lambda function is a function that is not declared upfront but is passed in immediately to another function. In the situation below the function that is passed as the first argument to the function twice is a lambda. A lambda expression is always surrounded by curly braces, its parameters (if any) are declared before -> while the body goes after -> (if required).

```kotlin
fun somewhereElse() {
    twice({n: Int -> n * 10}, 5) // returns 500
}
```

Note that for a lambda function the declaration of the parameter types is optional, and there is no way to declare the return type of the function (it has to be inferred). And it is always wrapped with curly braces. Thus the general syntax for a lambda function with n arguments is

```kotlin
{ p1: Type1, p2: Type2 .. pn: TypeN -> /* do something */ }
```

Note that the lambda function body is an expression and does not allow a return statement. It could consist of multiple statements and/or branches so long as they end with an expression. So the following is a valid lambda.

```kotlin
{ n: Int -> val twoN = 2 * n; if (twoN < 100) twoN * 2 else twoN * 3}
```

### Single parameter lambdas

If a lambda has a single argument and its type can be inferred, then the argument declaration along with its type and the subsequent `->` can be omitted. In such a case that parameter will have the default name `it`.

```kotlin
val quadruple: (Int) -> Int = { it * 4}
```

### Anonymous functions

An anonymous function is a function with no name. For obvious reasons you cannot declare it by itself. But you could say use it as the right hand side of an assignment to a variable or pass it as an argument to another function eg.

```kotlin
val triple = fun(n: Int): Int = n * 3

fun somewhereElse() {
    twice(triple, 5)
    twice(fun(n: Int): Int { return n * 3}, 5)
}
```

This is particularly useful when the return type cannot be contextually inferred

### Closures

Note that local functions, lambda functions and anonymous functions can access variables declared in their outer scope, ie. their closure. They can also modify such variables (assuming they are vars).

### Receivers

Lambdas and anonymous functions can be used as extension functions as well. Their function type then is `ReceiverType.(p1: Type1 .. pn: TypeN) -> ReturnType`

### Alternative invocation syntax

If a function has one or more arguments, but exactly one argument which is of a function type, then it can be invoked with an alternative syntax where the function is not specified within the parenthesis, but is instead specified after them wrapped in curly braces.

```kotlin
fun doubleAndThen(n: Int, then: (Int) -> Int): Int = then(n * 2)

fun somewhereElse() {
    doubleAndThen(5) { it * 5 } // returns 50
}
```

### Inline functions

You can specifically request that a function be inlined using the `inline` keyword. eg. you could've alternatively declared the `doubleAndThen` function above as

```kotlin
inline fun doubleAndThen(n: Int, then: (Int) -> Int): Int = then(n * 2)
```

In this case the compiler will emit the function (and the lambda passed to it) inlined at the call site where doubleAndThen is called. This causes your compiled code to grow in size but could pay off in performance.

If you do not want a particular lambda passed to the function as inlined, then you could mark it as noinline. eg.

```kotlin
inline fun doubleAndThen(n: Int, noinline then: (Int) -> Int): Int = then(n * 2)
```

If a function is inlined, then the lambdas passed to it are allowed to have a `return`

The next post in the series is [Exercises in Kotlin: Part 4 - Control flows and return](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-4-control-flows-and-return/)
