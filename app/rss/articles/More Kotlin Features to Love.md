---
title: 'More Kotlin Features to Love'
url: http://blog.jimbaca.com/2016/02/22/more-kotlin-features-to-love/
categories:
    - Kotlin
author: Jim Baca
date: Feb 22, 2016 15:44
---
Constants are important
One of the things that I’ve come to appreciate more and more as a programmer are constants. Before I would have yawned and said big deal why? Now I’d say they are so important as they prevent so many needless bugs. If we are guaranteed that something is not null then we can focus simply on getting our work done instead having to null check the world.

that is gnarly
In Java we used the final keyword to create a constant. Unfortunately the gnarly requirement for using the final keyword is that you must either declare the value inline or set it in the constructor. I’ve run into a fair number of cases where I want something to be final, but don’t know it yet, worse is when it belongs in a class but will not be available until after that class has been constructed and had one of it’s methods executed. Fortunately Kotlin allows us to have constants declared that aren’t required to be set during the constructor. We can use the lazy keyword:

```kotlin
val myConstant : String by Lazy{
     calculateConstant();
}
```

This allows us to have a constant that is instantiated the first time myConstant is used. This is perfect for two reasons:

1. We don’t need to do this calculation in the constructor(which is bad anyways as it violates SOLID principles by having logic in the constructor other than variable assignment)
2. The result of the Lazy calculation is cached, and will not be run in subsequent requests(good for expensive calculations)

## Sort of Ternary Operator

While Kotlin doesn’t have a true Ternary Operator it does have an operator that comes close that I had not noticed before:

```kotlin
println(listOfStrings?.size ?: "empty") // will first check if the strings is not null and print the size, or empty if it is null
```

## Kotlin is concise

The common theme I keep coming back to with Kotlin is that the language allows use to concisely write code. For example in Java if we wanted to execute a block of code if a null check was passed we’d do this:

```kotlin
if (object != null) {
     // do some action with object
}
```

but in Kotlin we can be more concise and do the following:

```kotlin
object?.let {
    // if object is not null execute what is in the curly braces
}
```

It’s a much simpler syntax for doing the same thing

![Kotlin is concise](http://i.giphy.com/UnQ68p0AVulkQ.gif)