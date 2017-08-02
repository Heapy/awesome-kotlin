
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

![Brain](http://cloudmark.github.io/images/func_as_data/brain.png)
 
 Programming languages have evolved largely through a series of abstractions; these abstractions mostly deal with control (e.g. functions) or data.  In the post, [Houses, Arrays and Higher Order Functions](http://cloudmark.github.io/Higher-Order-Functions-On-Arrays/), we have focused on the use of functions as control elements and have delved into the concept of higher order functions.  Today we will revisit functions and focus on their use as data elements.  Using functions to represent data might seem pretty counter intuitive at first but this blurred line between functions as control elements and functions as data elements is a powerful concept.  

                
# Sets
In order to illustrate how functions can be used as data elements we will create a Set library with the following operations: `union`, `intersection` and `difference`.  We will define a set using the characteristic function `T -> Boolean` i.e. a function which takes an element `T` as input and return a `true` or `false` depending on whether the element is contained within the set. Using classic OOP we can define a set as follows: 


```kotlin
class Set<T>(vararg values: T) {
    var elements: List<T> = values.toList()
    fun contains(element:T): Boolean = this.elements.contains(element)
}
```

In this case a set is defined as a class with a generic type `T`, we will use the underlying list data structure `elements` to implement the `contains` function.  We can use the OOP representation as follows: 


```kotlin
val s1 = Set(1)
val s2 = Set(2, 3, 4)
```

Using functions as data elements we would implement a Set as follows: 

```kotlin
fun <T>setOf(element: T): (T) -> Boolean  = { test -> element!!.equals(test) }
fun <T>contains(set: (T)->Boolean, element: T): Boolean  = set(element)
```

The function `setOf` is a _constructor_ function (as defined by SICP) which takes in an `element` and returns a higher order functions that implements the characteristic function `(T) -> Boolean`.  `contains` just delegates the test to the `setOf` higher order function by calling `set(element)`.  Some observant readers might have noticed that the class `Set` can ingest a number of values whilst the `setOf` function can only ingest one value.  Using the `union` operator (defined further on) we can extends the `setOf` function to accept a variable number of elements `T`.  

```kotlin 
fun <T>setOf(vararg elements: T): (T) -> Boolean {
    return elements.map { setOf(it) } .reduce { s, t -> union(s, t) }
}
```


Using the `setOf` function we can define the set `s1` and `s2` as follows: 

```kotlin
val s1 = setOf(1)
val s2 = setOf(2, 3, 4)
```

Note that even though the representation is completely different the end user consuming either library will not be exposed to the underlying details of our implementations (OOP vs Functions). To an end user (except for minor syntactic differences) these two Sets are not different from each another.  


# Union
Now that we have a means of representing a set, let us implement `union` in both representations.  In the OOP approach we would implement union as follows: 

```kotlin
class Set<T>(vararg values: T) {
    fun union(other:Set<T>): Set<T> {
        val unionSet = Set<T>()
        val elements = ArrayList(this.elements)
        elements.addAll(other.elements.filter { x -> !this.elements.contains(x) })
        unionSet.elements = elements
        return unionSet
    }
    ...
}
```

In this case we are using the backing data stucture `elements` to keep track of what is _in_ the set.  We can test this implementation as follows: 


```kotlin
val s1 = Set(1)
val s2 = Set(2, 3, 4)
println(s1.union(s2).contains(1)) // -> true
println(s1.union(s2).contains(2)) // -> true
println(s1.union(s2).contains(3)) // -> true
println(s1.union(s2).contains(4)) // -> true
println(s1.union(s2).contains(5)) // -> false
println(s1.union(s2).contains(0)) // -> false
```

Using functions we get a much more pure definition (mathematically speaking):  


```kotlin
fun <T>union(s1: (T)->Boolean, s2: (T)->Boolean): (T)->Boolean = 
        {element -> s1(element) || s2(element) }
```

An `element` is contained in the union of set `s1` and `s2` if the `element` is either in set `s1` or `s2`.  This is clearly represented by the line `element -> s1(element) || s2(element)`.  Don't be fooled by the fact that Kotlin does not support structural types, had the language supported structural types our definition would be a bit more concise: 

```kotlin
fun <T>union(s1: Set<T>, s2: Set<T>):Set<T> = 
        {element -> s1(element) || s2(element) }
```

Needless to say this is just semantic sugar; regular functions (with milk) will do just fine!  One would use the union operator as follows: 

```kotlin
val s1 = setOf(1)
val s2 = setOf(2, 3, 4)
println(contains(union(s1, s2), 1)) // -> true
println(contains(union(s1, s2), 2)) // -> true
println(contains(union(s1, s2), 3)) // -> true
println(contains(union(s1, s2), 4)) // -> true
println(contains(union(s1, s2), 5)) // -> false
println(contains(union(s1, s2), 0)) // -> false
```

Note again that the usage is very similar and an end user would find it hard to believe that the Set is implemented using functions.  

# Intersection 
Having defined `union`, `intersection` will only be one synaptic leap away.  Let's start off with the OOP version. 

```kotlin
class Set<T>(vararg values: T) {
    fun intersection(other:Set<T>): Set<T> {
        val intersectionSet= Set<T>()
        val elements = ArrayList<T>()
        elements.addAll(this.elements.filter { x -> other.contains(x) })
        intersectionSet.elements = elements
        return intersectionSet
    }
    ...
}
```

One would use the definition above as follows: 

```kotlin
val s3 = Set(1, 2)
val s4 = Set(2, 3, 4)
println(s3.intersection(s4).contains(1)) // -> false
println(s3.intersection(s4).contains(2)) // -> true
println(s3.intersection(s4).contains(3)) // -> false
println(s3.intersection(s4).contains(4)) // -> false
println(s3.intersection(s4).contains(5)) // -> false
println(s3.intersection(s4).contains(0)) // -> false
```

Using functions, we would represent the intersection function as: 

```kotlin
fun <T>intersection(s1: (T)->Boolean, s2: (T)->Boolean): (T)->Boolean = 
        { element-> s1(element) && s2(element) }
```

Note how close this function is to the original mathematical definition; an `element` is contained in the intersection of Set `s1` and `s2` if the `element` is contained in set `s1` and set `s2`, hence: 

```kotlin
element -> s1(element) && s2(element)
```

One would use the above definition as follows: 

```kotlin
val s3 = setOf(1, 2)
val s4 = setOf(2, 3, 4)
println(contains(intersection(s3, s4), 1)) // -> false
println(contains(intersection(s3, s4), 2)) // -> true
println(contains(intersection(s3, s4), 3)) // -> false
println(contains(intersection(s3, s4), 4)) // -> false
println(contains(intersection(s3, s4), 5)) // -> false
println(contains(intersection(s3, s4), 0)) // -> false
```


# Difference 
For completeness let us now implement `difference` in both representations.  In the OOP approach we would implement difference as follows: 

```kotlin
class Set<T>(vararg values: T) {
    fun difference(other:Set<T>): Set<T> {
        val differenceSet= Set<T>()
        val elements = ArrayList<T>()
        elements.addAll(this.elements.filter { x -> !other.contains(x) })
        differenceSet.elements = elements
        return differenceSet
    }
    ...
}
```

Using functions we can implement this using:

```kotlin
fun <T>difference(s1: (T)->Boolean, s2: (T)->Boolean): (T)->Boolean = 
        {element-> s1(element) && !s2(element) }
```



# Set of multiple elements 
Before we conclude I would like to give an intuition for the function

```kotlin
fun <T>setOf(vararg elements: T): (T) -> Boolean {
    return elements.map { setOf(it) } .reduce { s, t -> union(s, t) }
}
```

Specifically how we used `map` and `reduce` to create a _characteristic function_ which accepts multiple elements as follows:   

```kotlin 
val s3 = setOf(2, 3, 4, 7)
```

What `elements.map { setOf(it) }` does is map all `elements` to a characteristic function, hence 2 becomes: 

```kotlin
test -> 2.equals(test)
```

3 becomes: 

```kotlin
test -> 3.equals(test)
```

and so on.  

The `reduce` function will combine the characteristic functions as follows: 

```kotlin
union (
    union(
        union(
            {test -> 2.equals(test)}, 
            {test -> 3.equals(test)}
        ), 
        {test -> 4.equals(test)}
    ), 
    {test -> 7.equals(test)}
)
```

Each `union` function creates a new characteristic function combining the underlying two characteristic functions.  For e.g. 

```kotlin
union(
    {test -> 2.equals(test)}, 
    {test -> 3.equals(test)}
)
```


would create the following characteristic function: 

```kotlin
{test -> 
    {test1 -> 2.equals(test1)}(test) || 
    {test2 -> 3.equals(test2)}(test)
}
```

If we want to test whether 2 is in the set union, we would reduce the characteristic function as follows: 

```kotlin
union({test -> 2.equals(test)}, {test -> 3.equals(test)})(2)
{test -> 
    {test1 -> 2.equals(test1)}(test) || // 2 here is the element in the set s1 
    {test2 -> 3.equals(test2)}(test)    // 3 here is the element in the set s2
}(2) // 2 is the value to test.  
```

Replace all occurrences of `test` with the value 2, we obtain:  

```
{test1 -> 2.equals(test1)}(2) || {test2 -> 3.equals(test2)}(2)
```

Replace all occurrences of `test1` and `test2` with the value 2, we obtain:      

```
2.equals(2) || 3.equals(2)
true
```

Hence the characteristic function for the set union between `s1` and `s2` when applied to 2 has reduced to true, which is correct.    


# Conclusion
In this post we have looked at how we can use functions as data elements.  Using functions as a data representation might feel unnatural at first but this blurred boundary between functions as elements of control and functions as elements of data is quite powerful.  I really hope that you find this technique useful.  Stay safe and keep hacking!

  

"""

Article(
  title = "Functions as Data",
  url = "http://cloudmark.github.io/Functions-As-Data/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mark Galea",
  date = LocalDate.of(2016, 10, 19),
  body = body
)
