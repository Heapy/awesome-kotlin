
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## **Intro**

In this third post for [Kotlin Month](https://programmingideaswithjake.wordpress.com/kotlin/#kotlinmonth), I’m going to go over some of the safety features that Kotlin comes with. Follow the link to see the previous posts.

## **Null Safety**

This is one of the most touted features of Kotlin, from what I’ve seen. Personally, it has given me a little bit of grief, but some built-in property delegates, like `lazy` and `lateinit` are helpful workarounds.

Here’s the safety that Koltin provides: A variable cannot contain `null` unless its type specifies that it’s nullable. You can declare a type as nullable by placing a `?` at the end of the type’s name. For example, for a variable that can hold `null` or `String`, its type would be `String?`. That’s step one of the null safety.

The next step is that you cannot directly call methods or properties from a nullable variable. In order to safely dereference a nullable object, you must place a `?` before the `.`. This operator returns `null` if the object being dereferenced is `null`, or else returns the result of the method or property use. For example, if there’s a `String?` variable called `str`, and we want to find out if it’s empty, we would call it with `str?.isEmpty()`. The result is either `null`, `true`, or `false`. With the next operator, we can use that `null` to mean `true`.

The next operator, often called the elvis operator, since it kind of looks like it’d be a pompadoured emoticon, helps us turn `null` values into default values. It works a lot like the ternary operator (which Kotlin actually doesn’t have, since its `if`/`else` block is an expression, not a statement), except the left hand side is a combination of the left and middle of the ternary operator. It either evaluates to `null` or the desired type. If it _does_ resolve as `null`, it uses the right side if the operator. So, our `String?` example above would become `str?.isEmpty() ?: true`, which returns whatever `isEmpty()` returns, or else `true` if `str` was `null`. By the way, the `?:` part was the operator.

The last piece of the puzzle is the `!!` operator. This is a way of telling the compiler one of two things. Either “I’d rather get a `NullPointerException` than have to deal with null safety”” or “”I know that the type says that it’s nullable, but I can _guarantee_ that it won’t be `null`; at least not at this point.”

Now, because of Java interop, Kotlin can’t make any guarantees about the nullability of types coming from Java code. It goes the pragmatic route and lets the developer decide (and deal with the consequences, if need be) whether it can be `null` or not. Annotations can be used to tell the compiler, also. Check out their documentation on [Java interop with null safety](http://kotlinlang.org/docs/reference/java-interop.html#null-safety-and-platform-types) for more information.

## **Smart Casting**

Smart casting is nifty little feature that, while not coming up all that often in most cases, is really nice for cleaning up your code. Say you have code like this:

```kotlin
if(someVar instanceof SomeType) {
   SomeType anotherVar = (SomeType)someVar;
   ...
}
```

This kind of casting is such a pain. Once you’ve determined that `someVar` is of type `SomeType`, you don’t get to just use it as if it is. No, first you have to create _another_ variable (or, if you’re “lucky”, you can do it without the new variable for a single-line expression, but then you need _another_ set of parentheses for the cast – `((SomeType)someVar)...`) as well as write out the cast.

In Kotlin, you write it like this:

```kotlin
if(someVar is SomeType) {
   ...
}
```

First off, the type check operartor is shorter as `is`. The cast operator is cleaner too. If we had needed to use it in this case (which we didn’t), it would have been `var anotherVar = someVar as SomeType`. It’s not shorter, but it’s cleaner.

The reason we didn’t need to cast it is because the compiler knows that, inside the block, `someVar` is definitely a `SomeType`. So, we didn’t even need to use another variable for that because it just pretends that `someVar`‘s type is `SomeType` instead of whatever it was declared as.

An interesting note about this is that it applies to nullability, too. Since `String?` isn’t considered to be the same thing as `String`, but rather a supertype, it can be smart cast from `String?` to `String`:

```kotlin
if(someStr != null) {
   ... as if someStr is no longer a nullable type
}
```

There is a limit to smart casting, though. If the variable in question could potentially be changed by another thread at any time, it will not do it. Therefore, smart casting can only be applied to `final` variables (`val`s) or local variables (those created within the function).

Check out [Kotlin’s documentation on smart casts](http://kotlinlang.org/docs/reference/typecasts.html#smart-casts) if you want to learn more.

## **Generics**

Straight-up, Kotlin has better generics than Java. ‘Nuff said.

I’m just kidding. But seriously, Kotlin’s generics are better; they can even allow you to do things that Java won’t compile. I can’t give you an example, but I spotted a StackOverflow example once where someone tried to turn their Kotlin code into Java code, but they couldn’t because the Java code doesn’t allow generics to be used in a certain way, but luckily the byte code is forgiving and allows Kotlin to do it.

Moving beyond that, Kotlin also uses declaration-site variance as a primary variance rather than Java’s use-site variance. It also follows the same syntax as C#’s variance (using `out` and `in` instead of `_ extends` or `_ super`), which is much easier to understand in most cases. Since I’m not good at explaining this stuff, I’d like to just point you to [Kotlin’s documentation on it](http://kotlinlang.org/docs/reference/generics.html).

### **Inline Functions and Reified Generics**

Kotlin allows you to define inline functions (functions that are compiled in a way that, when called in code, it actually “copies” the code from inside the function into the place where it’s called), which can be really useful [and is only suggested to be used] when making functions that accept lambdas and method references that are called within, since _that_ can be inlined too, without actually creating a function object. Another interesting feature of inline functions is the ability to have _reified generics_ (generics without type erasure), since, in its inlined state, the compiler can actually _know_ what the actual type is (assuming it wasn’t already a generic type to begin with). There’s actually quite the swathe of things that can be done with inline functions, so you should check them out in [Kotlin’s documentation on them](http://kotlinlang.org/docs/reference/inline-functions.html).

## **Outro**

Thus ends my list of safety features in Kotlin. You should totally just go and fall in love with Kotlin now, as I have. Even if you won’t do it now, I still have next week’s post to sway you, so look forward to that.

"""

Article(
  title = "Kotlin Month Post 3: Safety",
  url = "https://programmingideaswithjake.wordpress.com/2016/03/13/kotlin-month-post-3-safety/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 3, 13),
  body = body
)
