
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin doesn’t have true pattern matching, and that’s fine. In order to make matchable classes in Scala, there is an awful lot of overhead required to make it work, and I highly respect Kotlin’s goal of _not_ adding much overhead anywhere. But that doesn’t mean we can’t try to make our own way to get something closer to pattern matching.

## **Using `when`**

Kotlin’s `when` block is incredibly handy; It has several ways that it can work. The first way is simple equality check:

```kotlin
when (x) {
    1 -> print("x == 1")
    2 -> print("x == 2")
    else -> print("x is neither 1 nor 2")
}
```

And cases can be combined using a comma:

```kotlin
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}
```

It can also do `is` and `in` checks:

```kotlin
when(x) {
    in 1..10 -> print("in range")
    is String -> print("I guess it's not even a number")
}
```

And with the last one, you can see that you can combine any of the previous into one `when` block. You also don’t need `else` if you’re using `when` as a statement instead of an expression. You also don’t need `else` if the expression version has all possibilities listed (as far as the compiler can tell).

You can also use `when` without a value on top, so that it simply works like a set of `if-else if` blocks:

```kotlin
when {
    a == b -> doSomething()
    b == c -> doSomethingElse()
    else -> doThatOtherThing()
}
```

With all these possibilities, do you know which version we’re going to use to build our pattern matching system? Surprisingly, it’s the simplest one with equality checks.

Now, I realize that you can do `sealed` classes as a sort of union type and `is` with when to match on those, but that’s has a limited set of use-cases. With the following system, I believe you can cover _all_ use cases.

## **So How Do We Do It?**

First, we realize that equality checks use `equals()` and that `equals()` is something we can override. So, we make some sort of `Pattern` type to use in the `when` block, and `equals()` checks if the object `is Pattern` and proceeds to use the `Pattern` to calculate “equality”.

Here’s a glimpse at how it loosely looks:

```kotlin
interface Pattern<in Subject> {
    fun match(subject: Subject): Boolean
}

class MySubject {
    ...
    fun equals(other: Any): Boolean {
        if(other is Pattern<*>)
            return other.match(this)
        else ...
    }
}

class SomePattern {
    override fun match(subject: Any): Boolean {
        ...
    }
}
```

And it would be used as follows:

```kotlin
val x = MySubject()
...
when(x) {
    SomePattern() -> doSomething()
    SomeOtherPattern() -> doSomethingElse()
}
```

## **Tweaks**

There’s quite a few things that can be done to alter this idea to make it more palatable in different situations.

### **Shortcutting**

First, you can try to make the patterns a little more accessible by shortcutting them on the subject class. If a pattern is parameterized – for example, a `List` could have a parameterized pattern that checks for a certain length, `IsLength` which would need to take in a parameter for the length – you can put a shortcut function on the `companion object` instead of directly calling the class’ constructor. If it’s not parameterized, you can cache an instance of the pattern as a value on the `companion object` of the subject class.

### **Lambda Pattern**

The `Pattern` interface only has one method. You know what that means? It’s a functional interface (in Java 8 terms). That means, in Kotlin, `Pattern` doesn’t even need to exist. Instead of `equals()` checking if the object is a `Pattern`, have it check if it’s a `Function1<SubjectType, Boolean>`. You can obviously still shortcut some built-in patterns, but now you can even put in some on-the-fly lambdas into your `when` block:

```kotlin
when(x) {
    {it: Subject -> it.isTheCoolest} -> doSomething()
}
```

This is sadly not all that useful, since type inference won’t be able to determine the type for the input parameter. You need to. At that point, you might as well use the unparameterized `when` block:

```kotlin
when {
    x.isTheCoolest -> doSomething()
}
```

That doesn’t mean using lambdas for the pattern is a bad thing. You can still use method references, which makes quick and simple on-the-fly patterns possible (even for properties):

```kotlin
when(x) {
    Subject::isTheCoolest -> doSomething()
}
```

That’s certainly better than the fully qualified lambda. More complex lambdas can be defined as functions or in values instead:

```kotlin
fun moreComplexCheck(subject: Subject): Boolean {
    ...
}

val moreComplexCheck2 = {subject: Subject -> ...}

when(x) {
    ::moreComplexCheck -> doSomething()
    moreComplexCheck2 -> doSomethingElse()
}
```

## **Outro**

So, there you have it! Better pattern matching in Kotlin! What do you think? I realize it’s a misuse of `equals()`, but I think it’s worth it in some cases.

"""

Article(
  title = "Improved Pattern Matching in Kotlin",
  url = "https://programmingideaswithjake.wordpress.com/2016/08/27/improved-pattern-matching-in-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 8, 27),
  body = body
)
