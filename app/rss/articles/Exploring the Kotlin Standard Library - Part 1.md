---
title: 'Exploring the Kotlin Standard Library - Part 1'
url: http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-1.html
categories:
    - Kotlin
author: Jamie McCrindle
date: Jan 22, 2013 05:01
---
Exploring the API docs and source code for a language's standard library is usually illuminating and Kotlin is no different. In this series, I thought I'd look at some of the highlights of the Kotlin stdlib. In Part 1, I'll be going over the default Kotlin namespace.

Both the [API docs](http://jetbrains.github.com/kotlin/versions/snapshot/apidocs/index.html) and the [source code](https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib) for the stdlib are available on github.

First up, the iterate method creates an iterator from a stateful function that returns either a value if it has more data or a null if not. For infinite iterators, just never return null. The following code will create an infinite iterator over the integers 0, 1, 2, 3...:

```kotlin
var i = 0;
var iterator = iterate { i++ }
```

To do something useful with the iterator, we can use some of the other methods from the standard library e.g. filter, take, forEach etc. The following code will print all the integers between 0 and 100 that are divisible by 3 and 5.

```kotlin
var i = 0;
iterate { i++ } filter { it % 3 == 0 || it % 5 == 0} take 100 forEach { println(it) }
```

Next up, the stdlib makes it easy to construct read only collections as follows:

```kotlin
listOf(1, 2, 3, 4) // constructs a read only list of Int's
mapOf(1 to "A", 2 to "B") // constructs a read only map of Int to String
setOf("Hello", "World") // constructs a read only map of Int to String
```

Or more specific / mutable collections like this:

```kotlin
arrayListOf("Hello", "World") // constructs an array list of Strings
linkedListOf("Hello", "World") // constructs a linked list of Strings
hashMapOf(1 to "A", 2 to "B") // constructs a hash map of Ints to Strings
linkedMapOf(1 to "A", 2 to "B") // constructs a linked map
sortedMapOf(1 to "A", 2 to "B") // constructs a sorted map
sortedSetOf(1, 2, 3) // constructs a sorted set
```

All of the arrays, collections, lists and iterators share a similar set of methods: all, any, appendString, contains, count, drop, dropWhile, dropWhileTo, filter, filterNot, filterNotNull, filterNotNullTo, filterNotTo, filterTo, find, flatMap, flatMapTo, fold, foldRight, forEach, groupBy, groupByTo, makeString, map, mapTo, partition, plus, reduce, reduceRight, requireNoNulls, reverse, sort, sortBy, take, takeWhile, takeWhileTo, toArray, toCollection, toLinkedList, toList, toSet, toSortedList, toSortedSet.
Here are examples of using most of the above methods:

```kotlin
// all
//
// check that all values from 0 to 100 are positive
assertTrue((1..100).all{ it > 0 })

// any
//
// check that any of the words hello and world contain the letter h
assertTrue(array("hello", "world").any{ it.contains("h") })

// contains
//
// check that the list of hello and world contains world
assertTrue(listOf("hello", "world").contains("world"))

// count
//
// check that the number of values between 1 and 100 inclusive is 100
assertTrue((1..100).count() == 100)

// drop
//
// check that dropping the first string results in only the second
assertEquals(listOf("world"), listOf("hello", "world").drop(1))

// dropWhile
//
// check that dropping values from the range 1 to 100 while
// the value is less than 90 results in the values 90 to 100
// remaining
assertEquals((90..100).toList(), (1..100).dropWhile{ it < 90 })

// dropWhileTo
//
// check that dropping values from the range 1 to 100 while
// the value is less than 90 results in the values 90 to 100
// remaining, using an existing, mutable list
val dropWhileToList = arrayListOf<Int>()
assertEquals((90..100).toList(), (1..100).dropWhileTo(dropWhileToList) { it < 90 })

// filter
//
// filter the values 0 to 10 to a list containing only even numbers
assertEquals(listOf(0, 2, 4, 6, 8, 10), (0..10).filter { it % 2 == 0 })

// filterTo
//
// filter the values 0 to 10 to a list containing only even numbers
val filterToList = arrayListOf<Int>()
assertEquals(listOf(0, 2, 4, 6, 8, 10), (0..10).filterTo(filterToList) { it % 2 == 0 })

// filterNot
//
// filter the values 0 to 10 to a list containing only odd numbers
assertEquals(listOf(1, 3, 5, 7, 9), (0..10).filterNot { it % 2 == 0 })

// filterNot
//
// filter the values 0 to 10 to a list containing only odd numbers
val filterNotToList = arrayListOf<Int>()
assertEquals(listOf(1, 3, 5, 7, 9), (0..10).filterNotTo(filterNotToList) { it % 2 == 0 })

// filterNotNull
//
// filter out null values
assertEquals(listOf("hello", "world"), array(null, "hello", null, "world").filterNotNull())

// filterNotNullTo
//
// filter out null values
val filterNotNullTo = arrayListOf<String>()
assertEquals(listOf("hello", "world"), array(null, "hello", null, "world").filterNotNullTo(filterNotNullTo))

// find
//
// find the first element in hello world that starts with "h"
assertEquals("hello", array("hello", "world").find{ it.startsWith("h") })

// first
//
// get the first element of hello and world
assertEquals("hello", listOf("hello", "world").first())

// flatMap
//
// map over the values 1, 2 and 3, creating a list of lists of it and it + 1
// for each element and then flatten them into a single list
assertEquals(listOf(1, 2, 2, 3, 3, 4), listOf(1, 2, 3).flatMap{ listOf(it, it + 1) })

// flatMapTo
//
// map over the values 1, 2 and 3, creating a list of lists of it and it + 1
// for each element and then flatten them into a single list
val flatMapToList = arrayListOf<Int>()
assertEquals(listOf(1, 2, 3).flatMapTo(flatMapToList) { listOf(it, it + 1) }, listOf(1, 2, 2, 3, 3, 4))

// fold
//
// take the numbers 1 to 4. start with 0 and add each number to a running total and
// return the result
assertEquals(1 + 2 + 3 + 4, listOf(1, 2, 3, 4).fold(0) { total, next -> total + next })

// foldRight
//
// take the numbers 1 to 4. add each of them together in turn from the right
assertEquals(1 + 2 + 3 + 4, listOf(1, 2, 3, 4).foldRight(0) { a, b -> a + b })

// forEach
//
// for each of the values in the range 1..10, print out the value on a new line
(1..10) forEach { println(it) }

// groupBy
//
// take the values 0, 1 and 2 and turn them into a map of the characters to lists of integers
// where A = 0, B = 1, C = 3 etc.
assertEquals(mapOf('A' to listOf(0), 'B' to listOf(1), 'C' to listOf(2)),
        array(0, 1, 2).groupBy{ (it + 'A'.toInt()).toChar() })

// groupByTo
//
// take the values 0, 1 and 2 and turn them into a map of the characters to lists of integers
// where A = 0, B = 1, C = 3 etc.
val groupByToMap = hashMapOf<Char, MutableList<Int>>()
assertEquals(mapOf('A' to listOf(0), 'B' to listOf(1), 'C' to listOf(2)),
        array(0, 1, 2).groupByTo(groupByToMap) { (it + 'A'.toInt()).toChar() })

// last
//
// get the last element of hello and world
assertEquals("world", listOf("hello", "world").last())

// makeString
//
// turn a list into a string using a space as a separator
assertEquals("hello world", listOf("hello", "world").makeString(separator = " "))

// map
//
// go through the range of integers from 1 to 5 and multiply each one by 2
assertEquals(listOf(2, 4, 6, 8, 10), (1..5).map{ it * 2 })

// mapTo
//
// go through the range of integers from 1 to 5 and multiply each one by 2
val mapToList = arrayListOf<Int>()
assertEquals(listOf(2, 4, 6, 8, 10), (1..5).mapTo(mapToList) { it * 2 })

// partition
//
// partition the values 1 to 10 into a pair of lists of even and odd numbers
assertEquals(Pair(listOf(2, 4, 6, 8, 10), listOf(1, 3, 5, 7, 9)), (1..10).partition{ it % 2 == 0 })

// plus
//
// add 4 onto the end of an array
assertEquals(listOf(1, 2, 3, 4), array(1, 2, 3).plus(4))

// reduce
//
// take the values 1 to 4 and add them together using a running total
assertEquals(1 + 2 + 3 + 4, (1..4) reduce { total, next -> total + next })

// reduceRight
//
// take the values 1 to 4 and add them together using a running total
assertEquals(1 + 2 + 3 + 4, (1..4) reduceRight { a, b -> a + b })

// requireNoNulls
//
// require that a collection doesn't contain nulls
array<String?>("one", "two", "three").requireNoNulls()

// reverse
//
// reverse a collection
assertEquals(array(4, 3, 2, 1), (1..4).reverse())

// sort
//
// sort a mutable collection
assertEquals(array(1, 2, 3, 4), linkedListOf(4, 2, 3, 1).sort())

// sortBy
//
// sort a mutable collection in reverse
assertEquals(array(4, 3, 2, 1), linkedListOf(4, 2, 3, 1).sortBy{ -it })

// take
//
// take 5 numbers from the front of a range of 1 to a 100
assertEquals(listOf(1, 2, 3, 4, 5), (1..100) take 5)

// takeWhile
//
// take numbers while they're less then 5 from a range of 1 to a 100
assertEquals(listOf(1, 2, 3, 4, 5), (1..100) takeWhile { it <= 5 })

// takeWhileTo
//
// take numbers while they're less then 5 from a range of 1 to a 100
val takeWhileToList = arrayListOf<Int>()
assertEquals(listOf(1, 2, 3, 4, 5), (1..100).takeWhileTo(takeWhileToList) { it <= 5 })
```
