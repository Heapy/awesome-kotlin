
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Recently, I was enlightened. I finally, fully grasped how the Either Monad works. At least I think I do. In this post, I am looking to report my findings, both for the education of others of this powerful tool, as well as to see if my understanding of this monadic concept really holds water.

### What’s an Either Monad?

An Either Monad consists of two basic types, Left and Right. It is, essentially, the If/Else statement of the Monad world. Left signifies that something went wrong, while Right signifies that things are working as they should. Just remember, that you want your code to be all right. As the monad passes through different bindings and is worked with, it can either report a Left, and, essentially, skip the rest of the call chain, or it can report Right and continue in fashion.

### But wait, what’s a Monad?

Think of a Monad as a box. In this box, you can store stuff. A Monad is simply a data wrapper with a few different, unifying properties. For example, the Java8 Optional is considered a Monad, as is Kotlin’s ? types and Haskell’s Maybe, among others.

Monads follow some simple rules. They include two sequencing functions, a return function and a fail function. The sequencing functions are, in my example, noted as bind and seq.

Bind will take a Monad and, if all is well, apply a function to it’s contents, and return another Monad of the same general type. For example, an Either Monadic bind operation will always return another Either Monad, though the type of the data being wrapped might have changed, say from int to bool.

Seq will take a Monad and return the monad passed to it. It is a way to finish a chain of operations and ignore the result, and instead pick up from a new source Monad.

Return simply takes a variable, and returns it wrapped in a new Monad.

Fail takes a string, and throws an exception with it as the message. It’s type returns the Monad in question in order to be compliant with the bind function.

An example, written in Kotlin:

```kotlin
sealed class Either<L, R> {
    class Left<L, R>(val l: L) : Either<L, R>() {
        override fun toString(): String = "Left ${"$"}l"
    }
    
    class Right<L, R>(val r: R) : Either<L, R>() {
        override fun toString(): String = "Right ${"$"}r"
    }
    
    infix fun <Rp> bind(f: (R) -> (Either<L, Rp>)): Either<L, Rp> {
        return when (this) {
            is Either.Left<L, R> -> Left<L, Rp>(this.l)
            is Either.Right<L, R> -> f(this.r)
        }
    }
    
    infix fun <Rp> seq(e: Either<L, Rp>): Either<L, Rp> = e
    
    companion object {
        fun <L, R> ret(a: R) = Either.Right<L, R>(a)
        fun <L, R> fail(msg: String): Either.Right<L, R> = throw Exception(msg)
    }
}
```

Here, we have a Sealed class. This is because we only ever want to have two options for the Either monad, Left or Right. Each of these simply define a toString method, for convenience.

We have two methods within either, which represent the aforementioned bind and sequence functions. (In Haskell, these are (>>=) and (>>), respectively). These are infix methods, who’s usage is explained in the sample usage code below.

The bind method simply takes a function. If the Either in context is Left, we simply return a Left of proper type. If it is Right, then we apply the function to it.

The seq method simply returns whatever its input is.

We then have the two other functions required for a monad defined as static functions, as these are “creation” methods. Ret simply returns a new Right instance for whatever value is passed in a. Fail throws an exception with the message as defined in the parameter.

Ret is a convenience function for creating new Rights. Fail is a convenience function for throwing basic exceptions. For example, you can easily fail from within a bind, as shown in usage.

### Using this class

Here are some examples of usage. Note the use in some places of bind as an infix operator. Also note that there is a lot of Type parameters in the code. This is due to Either needing to know information about both the left and right hand side of a given instance. I believe that this is ok. It’s not as concise as I would like it to be, but it’s a trade-off for strong typing.

```kotlin
fun main(args: Array<String>) {
    
    // Simple bind, from one R type to another
    println(Either.Right<String, Int>(5) bind { it -> Either.ret<String, Boolean>(true)} )
    
    // Simple bind, demonstrating left pass-through
    println(Either.Left<String, Int>("error").bind({ it -> Either.ret<String, Int>(it + 1) }))
    
    // Simple bind, demonstrating conditions
    val right = Either.Right<String, Int>(5)
    val condition = { it: Int -> if (it > 3) {
        Either.Left<String, Int>("number is too big")
    } else {
        Either.Right<String, Int>(it * 2)
    }}
    
    // Examples utilizing this condition.
    println(right bind condition)
    println(Either.Right<String, Int>(2).bind(condition).bind(condition).seq(Either.Right<String, Float>(2.0f)))
    
    // Demonstration of Fail
    try {
        println(Either.Right<String, Int>(5) bind { Either.fail<String, Int>("asdf") })
    } catch (e: Exception) {
        println("exception: ${"$"}{e.message}")
    }
}
```

### Monads as a concept, not a base class

One mistake often made when someone is learning about Monads is that there should be some sort of Monad base class. I believe that this is driven by backgrounds in object oriented programming. OOP does not translate very well into a functional context. While there is definitely a class definition of a Monad in Haskell, because of the differences in the language, functional paradigm, and really just the strength and power of the Haskell type system, it does not translate well to a Java interface. Specifically when the data within the Monad changes, the type system is lacking in some ways to completely express, say, the bind function, when we move from an R value of Int to an R value of boolean.

Thus, Monads should be understood as a general concept, not as some base class we extend. They are not really meant to be interoperable with each other. Each Monad is a unique tool that provides it’s own benefits, such as the aforementioned Java8 Optional, or Haskell’s State monad. Each would need to be explicitly written, and not be expected to co-mingle.

### Conclusion

Monads are powerful. Really powerful. They’ve started to take the OOP world by storm, from simple language conventions like Nullability in Kotlin, to the advent of RxJava over the last year or so. As we move into a more multi-processed world, where core count continues to rise and processor speeds continue to stagnate, it will be more important than ever to understand Functional programming concepts, whether it be immutability, pure functions, or this, the Monad.

Thanks for reading.

"""

Article(
  title = "Exploring an Either Monad in Kotlin",
  url = "https://blog.exallium.com/exploring-an-either-monad-in-kotlin-92618b9c4623#.nlt1cxd96",
  categories = listOf(
    "Kotlin",
    "FP"
  ),
  type = article,
  lang = EN,
  author = "Alex Hart",
  date = LocalDate.of(2016, 11, 22),
  body = body
)
