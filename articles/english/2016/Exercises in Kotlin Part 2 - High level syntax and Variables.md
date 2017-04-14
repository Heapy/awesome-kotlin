---
title: 'Exercises in Kotlin: Part 2 - High level syntax and Variables'
url: http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-2-high-level-syntax-and-variables/
categories:
    - Kotlin
author: Dhananjay Nene
date: Apr 19, 2016 00:00
---
This post first discusses the code that was written so far in the last post, [Exercises in Kotlin: Part 1 - Getting Started](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-1-getting-started/). Subsequently it focuses on the basic syntax of variables, discusses a bit about packaging and also about Type Inference

## A review of code so far

Let us take a look at the code we wrote so far in the last blog post [first part of this series](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-1-getting-started/) and glean what we can from the same.

### helloworld.kt

```kotlin
package com.example.kotlin.learning

fun main(args: Array<String>) {
    println("Hello World!")
}
fun greetings() = "Hello World!"
```

Some of the things that can be noted from this program are follows

*   _Similar package convention_: Kotlin follows similar package naming conventions as Java.
*   _Standalone functions_: Yes, Kotlin has them. That means there is no requirement to have a class just in order to wrap functions, that frankly have no reasons to be part of a class.
*   _Keyword_ `fun`: Functions are declared using the keyword `fun`.
*   _Variable declaration order_: When declaring variables (or arguments to a function as in this case of the arguments to `main`), the order of declaration is reversed, with the type specification _following_ the variable/argument name and a colon used to separate the two.
*   _Array is a generic type_: Arrays are not represented by `[]` but a generic type `Array<T>` instead where `T` is the type whose array is being represented. Thus at a syntactic level there is no necessity for using `[]` for arrays.
*   There is a helper function called `println()` similar to `System.out.println()`
*   _Semicolon as a statement terminator is optional_: A semi-colon does not seem to be mandatory
*   _Optional function return types_: Return type of the function is not required. to be specified if the return type is a _Unit_. Which is why the function `main` does not require a return type declaration.
*   _Alternative function representation_: As the function declaration for `greetings()` shows, function bodies can be substituted by expressions if the separator `=` is used between the function declaration and the expression. When the function body is specified as an expression, the compiler will attempt to infer the return type and in most cases no return type declaration will be required.

### testgreet.kt

```kotlin
package com.example.kotlin.learning

import org.junit.Test
import org.junit.Assert.assertEquals

class TestGreet() {
    @Test
    fun testGreetings() {
        assertEquals("Greeting should be 'Hello World!'",
                "Hello World!", greetings())
    }
}
```

From this we can glean the following

*   _Class declaration_: A Class declaration is similar to java in the sense it starts with the keyword `class` followed by the class name. The class body is also wrapped within `{` and `}`.
*   _Constructor_: There is no explicit constructor that _seems_ to be required. That is not true. The parenthesis after the class name, in this case with empty content, are in fact the argument list for the primary constructor. More details about that later.
*   _Method declarations_: Methods are also declared using the `fun` keyword.
*   _Annotations_: Annotations can also be used just like they are used in java (in this case the `@Test` annotation.

The above gives us some sort of understanding about Kotlin syntax, Let us look at a few more examples to flesh out some of the more commonly used details. This has a main function. So you can copy this code into a file and compile and run it.

## Example 1 : Comments and variables

```kotlin
/**
* This is a comment
*/

/*
   So is
   this */

// As is this

val PI = 3.14159

fun circleArea(r: Double) = PI * r * r

fun main(args: Array<String>) {
    var radius: Double = 1.0
    val increment = 1
    // increment = increment + 1 <-- will not compile since increment is a val
    println("The radius of a circle with radius ${radius} is ${circleArea(radius)}")
    radius = radius + increment
    println("The radius of a circle with radius ${radius} is ${circleArea(radius)}")
}

```

The output of the above program is

```kotlin
The radius of a circle with radius 1.0 is 3.14159
The radius of a circle with radius 2.0 is 12.56636
```

Note the following:

#### Comments

*   Kotlin follows the same syntax for comments as Java does. Enough said.

#### Primitive types

*   Kotlin has primitive types. Though they are not named identical to the ones in Java. They are in fact the same names with the first character capitalised viz Boolean, Byte, Int, Long, Float, Double etc. (not sure if there is an exception to that).
*   There are member methods available for these primitives unlike Java. Thus you can call a `toString()` on an `Int`
*   Although they seem to be like objects, their storage space is the same as primitives in Java _unless_ they are declared as _nullable_ (more about that later) or used by generic classes in which case their storage is like boxed primitives.

#### Variables

*   As mentioned earlier, variables can be declared at the top level (eg `PI`)
*   Functions can also have local variables. eg. `radius` and `increment`
*   Variables are of two types. `val` ie. read-only variables and `var` ie. read-write variables. In Java all variables by default are read-write and those that are to be treated as read-only are marked using the keyword `final`. However in Kotlin it is required to use one of the two keywords `val` or `var` explicitly. The statement `// increment = increment + 1` was commented out, because since `increment` is a `val` such a statement would not compile. `radius` on the other hand being a `var` can change its value, as is done at `radius = radius + increment`.
*   A predominantly functional style of programming will almost always use `val`s and rarely if any `var`s. The rationale for that is beyond the scope of this post.
*   A variable may or may not have an explicitly type declared. The compiler will attempt to infer it based on the value it is initialised to. However often that may not be feasible, and the programmer may be required to specify the type explicitly.

#### Parameters

*   Parameter declarations for functions are similar to a variable declaration. However they are not preceded by a `val` or a `var`. They are always `val`s. They always need an explicit type declaration.
*   A function may return no value whatsoever (represented using the type `Unit`). The return type of the functional is optional for functions having an expression body (as in case of `circleArea`) or functions that have a block body but have a `Unit` return type. In other situatons the compiler will not attempt to infer the return type, and the programmer is required to provide it explicitly.

#### String templates

*   String interpolation is supported by allowing expressions to be embedded in strings using the ${`expr`} syntax as in the arguments to `println()`. This feature is called string templates in Kotlinspeak

## (Optional) Some more advanced aspects about variables

There are some nuanced aspects of variables that you might use only occasionally. These are discussed here. You could chose to skip this section and instead move on to the next post since these might entangle you into learning and thinking about infrequently leveraged capabilities.

Consider the following code, which is an extended version of the code we saw earlier. It is kind of a little odd, but has been deliberately constructed to talk about the advanced features.

```kotlin
const val PI = 3.14159

fun circleArea(r: Double) = PI * r * r

var defRadius = 5.0

var defaultRadius: Double
    get() = defRadius
    set(value) { defRadius = value }

val area: Double
    get() = circleArea(defaultRadius)

fun main(args: Array<String>) {
    var radius = 1.0
    val increment = 1
    println("The radius of a circle with radius ${radius} is ${circleArea(radius)}")
    radius = radius + increment
    println("The radius of a circle with radius ${radius} is ${circleArea(radius)}")
    println("The radius of a circle with radius 5.0 is ${area}")
    defaultRadius = 6.0
    println("The radius of a circle with radius 6.0 is ${area}")
}
```

The output of the code above is as follows

```kotlin
The radius of a circle with radius 1.0 is 3.14159
The radius of a circle with radius 2.0 is 12.56636
The radius of a circle with radius 5.0 is 78.53975
The radius of a circle with radius 6.0 is 113.09723999999999
```

Things to be noted.

*   _getters_: Variables can have getter functions as shown for the variable `area` above. Frankly, not sure why they would be used for top level variables. Though I could imagine them to be used to work around variable visibility constraints by having a private var being accessible as a read-only variable function from another file by using a getter function.
*   _setters_: We can also similarly use setters as in the case of `defaultRadius`. Again this is a bit of a contrived example, since one could have just as easily directly used `defRadius`. However do note, getters and setters to tend to be more useful when used with class member variables, a topic we will get to later.
*   _const variables_: Variables can be prefixed with a const as in case of `PI` above. However this capability is restricted to top level variables only. In addition such variables have to be initialised to a `String` or a primitive type. and may not have a custom getter. In turn such consts can be used as parameters to annotations.
*   _package namespaces and visibility_: Just like top level functions, you can also have top level variables/constants being declared. Also note that all the standalone functions get declared in the namespace of the package. So if you have multiple files with the same package and also same function (or top level variable) names, that will be treated as an error since one of them will end up attempting to redeclare the other. On the other hand if you declare a top level variable as private, it is not private to that package namespace, but is instead treated as private within a file

We shall be reviewing functions in the next post [Exercises in Kotlin: Part 3 - Functions](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-3-functions/)
