
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![](https://d262ilb51hltx0.cloudfront.net/max/2000/1*qPZUptAisHb0s0TSLrBvHA.jpeg)
*Photo By [Johhy Silvercloud](https://www.flickr.com/photos/johnnysilvercloud/) / [CC-BY-SA](https://creativecommons.org/licenses/by-sa/2.0/)*

### Kotlin: The Good, The Bad, and The Ugly

In my [last article](https://medium.com/keepsafe-engineering/lessons-from-converting-an-app-to-100-kotlin-68984a05dcb6), I talked about converting Java codebases to Kotlin, and about some of the libraries I like. Here, I’m going to talk about my thoughts on the Kotlin language itself and the way it interacts with Java.

### The Good

There’s a lot to like about Kotlin. Some of the obvious features like null safety, property access, and unchecked exceptions are covered at length in other [publications](https://blog.jetbrains.com/kotlin/2016/01/kotlin-digest-2015/), so I won’t repeat them here. Instead, here are some of the less commonly discussed features of Kotlin that I really like.

#### Automatic conversion of Java to Kotlin

JetBrains’ Java to Kotlin converter integrated into IntelliJ saves a huge amount of time. It’s far from perfect, but it saves you from having to retype mundane code. Without it, migrating code from Java to Kotlin would take significantly longer.

#### lateinit, Delegates.notNull and lazy

Kotlin’s null safety is great, but due to the way the Android Activity lifecycle is designed, you’ll often find that you have to initialize a property in a callback like _onCreate_ instead of your class’s constructor. Let’s say you have a property that you would like to define like:

```kotlin
val name: String
```

If you have to initialize that property in onCreate, it cannot be a _val_: it has to be mutable var. But it still needs to be given a value at initialization time, so the obvious way to define it would be as a nullable property:

```kotlin
var name: String? = null
```

That works, but then you have to do null checks everywhere you access that property. Even though Kotlin has relatively painless null assertions, you’d rather not have to type _!!_ everywhere if you know that the property isn’t going to be null in practice. Fortunately, Kotlin has better ways: [_lateinit_](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties) and [_Delegates.notNull_](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.properties/-delegates/not-null.html). With either of those, you can define your property as a non-null type without an initializer:

```kotlin
lateinit var name: String  
var age: Int by Delegates.notNull<String>()
```

In either case, if you attempt to access the property before it’s initialized, an exception will be thrown _[1]_. _lateinit_ can’t be used on properties of primitive types, but otherwise the two methods are mostly the same _[2]_.

A third option that can be useful is the _lazy_ delegate. If your property can be initialized with data from only other properties or methods, then this might be a good fit. You can use it like so:

```kotlin
val imm: InputMethodManager by lazy {   
    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager   
}
```

The block passed to _lazy_ isn’t called until the first time the property is read, and the return value of the block is saved for future accesses, so the block will only be called once.

#### Functional collection extensions

Kotlin has some great functional [extensions methods on collections and iterables](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html#functions). Functions like [_any_](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/any.html), [_joinToString_](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/join-to-string.html), and [_associate_](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/associate.html) can save you from a lot of hand-coded _for_ loops that are necessary in Java.

There is also a lazy version of many of the functional collections operations that won’t make a copy of your collection after each operation, but my benchmarks find that the lazy and eager versions of operators have a similar enough performance that it usually won’t matter which one you use _[3]_.

#### Named and default function arguments

[Named parameters](https://kotlinlang.org/docs/reference/functions.html#named-arguments) and [Default parameters](https://kotlinlang.org/docs/reference/functions.html#named-arguments) are pretty basic, but they let you cut down on a lot of boilerplate of defining overloads, and replace one of the main use cases of the Builder pattern.

Depending on your use case, you could potentially even use default arguments for basic dependency injection by making a production dependency the default, and passing a mock during tests.

For example, if you have a presenter that needs some global state, you could define its constructor like this:

```kotlin
class Presenter(  
        val okhttp: OkHttp = productionOkHttp(),  
        val picasso: Picasso = productionPicassoInstance()  
) {...}
```

That allows you to create the presenter without arguments from your UI code, while allowing you to pass mock instances of the dependencies during tests. Full DI frameworks are more powerful, but this is a nice example of the usefulness of some simple language constructs.

### The Bad

Although Kotlin is great, it’s not perfect. Here are a few aspects of the language that I’m not in love with.

#### No namespaces

Kotlin allows you to define functions and properties at the top level of your files. That’s a great feature, but it can cause some confusion when combined with the fact that all top level declarations are referenced unqualified from Kotlin. This can sometimes make it difficult to tell what a function is when reading one of its usages.

For example, if you define a top level function:

```kotlin
fun foo() {...}
```

You will call that function as foo(). If you have a function with the same name in a different package, it’s not obvious from looking at the call site which function is being called. You can fully qualify the name of the function with the entire name of the package that it’s defined in, but given Java’s convention of very deep package names, that’s not ideal.

One workaround is to approximate a namespace by using with a singleton object class.

```kotlin
object FooActions {  
    fun foo() {...}  
}
```

That allows you to refer to the function as FooActions.foo() if you’re only calling the functions from Kotlin, but it’s not as pretty if you have Java code that needs to call that function. From Java, you have to refer to the function as FooActions.INSTANCE.foo(), which is certainly not ideal. You can avoid the INSTANCE step by annotating your function with @JvmStatic, which is about the best you can do currently. That’s not a big deal, but it’s some boilerplate that wouldn’t be necessary if Kotlin had namespaces.

#### No static modifier

Following on the previous point, Kotlin has unusual handling of static function and property declarations that are called from Java. It’s not bad, but it feels dirtier than necessary. For example, the Android _View_ class defines some static constants like _View.VISIBLE_ and static methods like View.inflate:

```kotlin
public class View {  
    public static final int VISIBLE = 0x00000000;  
    public static final int INVISIBLE = 0x00000004;  
    public static View inflate(Context context, int resource) {...}  
}
```

The declaration is simple. In contrast, here’s the equivalent Kotlin:

```kotlin
class View {  
    companion object {  
        @JvmField   
        val VISIBLE: Int = 0x00000000  
        @JvmField   
        val INVISIBLE: Int = 0x00000004  
        @JvmStatic  
        fun inflate(context: Context, resource: Int) {...}  
    }  
}
```

Although the Kotlin version isn’t terrible, it’s more verbose than I would normally expect from the language. If you skip the annotations, then Java code will have to use awful syntax to refer to your fields:

```kotlin
// With annotations:  
View.VISIBLE;
```

```kotlin
//Without annotations:  
View.Companion.getVISIBLE();
```

It feels odd that there are no better ways to create static functions and properties. I know that companion objects are real objects and can do stuff like implement interfaces, but that doesn’t feel like a compelling enough use case to completely replace normal static declarations.

#### Automatic conversion of Kotlin to Java

This was the first topic in the list of things I like about Kotlin, and it works well. But because it work so well 80% of the time, many of the cases where it fails can be frustrating.

Javadocs are often mangled, especially any paragraphs the wrap lines. Static fields and methods are converted to plain declarations on the companion object, which breaks any Java code that previously called them unless you manually add _@JvmField_ or _@JvmStatic_, respectively.

All of these problems will certainly get fixed as the Kotlin team has more time to work on the converter, so I’m optimistic in this case.

#### Required property accessor syntax

Kotlin has the great syntactic sugar called “property accessor syntax” that allows you to call JavaBeans-style getters and setters as if they were a Kotlin property. So for example, you can call the _Activity.getContext()_ method by writing _activity.context_ instead of writing the whole method name. If you use the actual method call in Kotlin, you will get a lint warning telling you to use the property syntax instead.

That’s definitely a nice feature, but there are a few cases where method names start with the word “get”, but you don’t want to use the property syntax. One common case is with Java’s atomic classes. If you have a _val i = AtomicInteger()_, you might want to call _i.getAndIncrement()_. But Kotlin wants you to call _i.andIncrement_. That’s clearly not an improvement.

You can annotate every call site with _@Suppress(“UsePropertyAccessSyntax”)_, but that’s ugly. It would be much better if there was a way to annotate functions you write with a similar annotation that would tell the linter that the function shouldn’t be treated like a property.

#### Method count

Writing code in Kotlin will certainly reduce the number of lines of code in your project. But it will also probably increase the method count of the compiled code, which is of course a drawback if you’re using Android. There are a number of reasons for that, but one of the larger contributors is the way Kotlin implements properties.

Unlike Java, the Kotlin language doesn’t provide any way to define a bare field. All val and var declarations instead create a property. This has the advantage of allowing you to add a get or set definition to a property whenever you want without breaking code that references the property. That’s a great feature that removes the need to write defensive getters and setters in the way that you often do in Java.

That feature comes at a cost, though. Every public _val_ causes Kotlin to generate both a backing field and a getter function that can be called from Java _[4]_. Every public _var_ will cause both a getter and setter to be generated. Fortunately, private properties with default getters and setters are generated as fields and don’t cause getters or setters to be generated. If you were previously exposing a lot of public fields in Java (which is a common practice for constants), you can end up with a surprising increase in method count.

If you are close to the method limit in your android app, I recommend using the workaround of applying the _@JvmField_ annotation on public constants for which you don’t expect to need a custom getter for in the future. This will prevent the getters from being generated, and can cut down on your method count.

It’s not all bad, though. As I discussed in my article on [converting an app to 100% Kotlin](https://medium.com/keepsafe-engineering/lessons-from-converting-an-app-to-100-kotlin-68984a05dcb6), the Kotlin standard library is small and can replace a number of common Java libraries that are much larger once you don’t need them in Java any more. Thanks to the standard library, the method count in that app decreased after converting it to Kotlin. So as long as you avoid the big areas that can increase your method count, you may end up ahead in the end.

### The Ugly

And finally, here are two design decisions that the Kotlin team made that I strongly disagree with, and that I don’t expect to change in the future.

#### SAM conversion and Unit returning lambdas

This one is a really baffling design decision.

One of the best features of Kotlin is the way it embraces lambda functions. If you have a Java function that takes a SAM interface as a parameter (an interface with a Single Abstract Method):

```kotlin
public void registerCallback(View.OnClickListener r)
```

You can call it by passing a plain lambda from either Kotlin or Java:

```java
// Java  
registerCallback(() -> { /** do stuff */ })
```

```kotlin
//Kotlin  
registerCallback { /** do stuff */ }
```

This is great. But trying to define a similar method in Kotlin is inexplicably harder. The direct translation is called the same from Java, but requires an explicit type when called from Kotlin:

```kotlin
fun registerCallback(r: View.OnClickListener)
```

```kotlin
// Kotlin. Note that parenthesis are required now._  
registerCallback(View.OnClickListener { /** do stuff */ })
```

That’s annoying to have to type out, especially if you convert some Java code to Kotlin and find out that it breaks existing Kotlin code.

The idiomatic way to define that function in Kotlin would be with a function type:

```kotlin
fun registerCallback(r: () -> Unit)
```

Which allows the nice function call syntax in Kotlin, but since all Kotlin functions are required to return a value, this makes calling the function from Java much worse. You have to explicitly return Unit from Java lambdas, so expression lambdas are no longer possible:

```kotlin
registerCallback(() -> {
    /** do stuff */  
    return Unit.INSTANCE;  
})
```

If you’re writing a library in Kotlin, there isn’t any good way to write a method with a function parameter that is ideal to call from both Java and Kotlin. I try to work around this in my [FlexAdapter](https://github.com/ajalt/flexadapter) library by providing overloads for each method with a function parameter that take either a SAM interface or a Kotlin function type. That lets you have a nice call syntax from both languages, but makes the library API less concise.

Hopefully the Kotlin designers change their mind and allow SAM conversions for functions defined in Kotlin in the future, but I’m not optimistic.

### Closed by default

Every downside to Kotlin I’ve talked about so far are mostly small syntax details that are not quite as clean I’d like, but aren’t a big deal overall. But there’s one design decision that is going to cause a huge amount of pain in the future: All classes and functions in Kotlin are closed by default. It’s a design decision pushed by Effective Java, and it might sound nice in theory, but it’s an obviously bad choice to anyone who’s had to use a buggy or incomplete third-party library.

> _Make all of your leaf classes final. After all, you’re done with the project — certainly no one else could possibly improve on your work by extending your classes. And it might even be a security flaw — after all, isn’t java.lang.String final for just this reason? If other coders in your project complain, tell them about the execution speed improvement you’re getting. — _[_Roedy Green, How to Write Unmaintainable Code_](http://www.mindprod.com/jgloss/unmaindesign.html)

The Kotlin documentation [actually has a paragraph trying to defend the choice](#), so I’ll address the three reasons they give.

#### “Best practices say that you should not allow these hacks anyway”

The arguments for closed inheritance are mostly centered around the “Fragile Base Class Problem”, which is the idea that, if you allow someone to subclass your library code, they could change the way it works, potentially causing bugs. While that’s a possibility, there are lots of way to use a library incorrectly that will cause bugs. If you override some functionality in a class, it obviously your responsibility if you break something.

I use the word “obviously”, because overriding functionality is the way to use a library that most explicitly places responsibility on the user. I tutored Computer Science students for years, and while they managed to make nearly every mistake you can imagine, they were never surprised when they broke something by overriding a method. There are much more subtle ways to break a library you use, like passing a value with the correct type but wrong units, or forgetting to call a required method.

I appreciate the approach of writing code that has fewer places that can break, and making classes final might seem to do that. But it’s a certainty that libraries will be incomplete or incorrect, and you will eventually need to use one of those libraries. Modifying the behavior of a closed class is going to require much worse hacks that are more likely to result in bugs than if you could just override one or two of that class’s methods.

But you don’t even have to take my word for it. Here’s a real world example that might have impacted you personally if you’re an Android dev:

AppCompat 23.2.0 finally saw the addition of [VectorDrawables](https://plus.google.com/+AndroidDevelopers/posts/iTDmFiGrVne) to the support library. This was a very welcome addition that promised to reduce APK size and memory usage. [Except that it caused a huge memory leak in any activities that tried to inflate them](https://code.google.com/p/android/issues/detail?id=205236). The support was [removed a couple weeks later](https://plus.google.com/+AndroidDevelopers/posts/iTDmFiGrVne).

Where did the memory leaks come from? In order to [implement VectorDrawable inflation](https://medium.com/@chrisbanes/appcompat-v23-2-age-of-the-vectors-91cbafa87c88), the support library authors needed to update the way _Context.getDrawable_ is implemented. But that function is final, so they had to make every View create a new copy of a Resources wrapper that could handle _VectorDrawables_. Besides being a large amount of work, this caused the various wrapped Resources to become out of sync, and to use a large amount of memory due to the duplication. If that function wasn’t final, that mess wouldn’t have happened.

#### “People successfully use other languages (C++, C#) that have similar approach”

People also successfully use languages like Python that allow anything at all to be changed at any time. Python has “non-public” methods like __asdict_ that are documented to by implementation details. It also has name mangled functions like ___intern_ that are harder to accidentally call. You can freely monkey-patch or override any of those functions whenever you want, and Python won’t complain.

In five years of full time Python development, I can’t think of a single time when someone broke my code by overriding a function. I can think of many instances where I’ve altered a non-public function in a safe, correct way in much less time than it would have taken to implement the same functionality if Python prevent me from doing so.

I’m not advocating blindly altering implementation detail of every class you come in contact with, but there’s no reason to make that impossible if it becomes necessary. A common saying in the Python community is that “We’re all consenting adults here.” If you need to make a change to one of my classes, you should be able to.

#### “If people really want to hack, there still are ways: you can always write your hack in Java and call it from Kotlin (see Java Interop), and Aspect frameworks always work for these purposes”

This is of course a ridiculous argument. You still can’t override closed Kotlin methods in Java without unacceptable use of reflection, so this doesn’t hold any weight.

Not being able to extend from a library means that it becomes very difficult to add features or fix bugs. And in the real world, more libraries than not will need hacking. That’s simply reality, and is never going to change. A library author is never going to be able to predict every possible use case that users will have. Making all of your classes final only prevents users from easily implementing the features you can’t. This was a surprisingly dogmatic choice given how practical the Kotlin designers were in the rest of the language.

If you write a Kotlin library, please make all of your public functions open. You’ll make life easier on your users in the future.

### Conclusion

Kotlin is overall a great language. It is much less verbose than Java, and has an excellent standard library that removes the need to use a lot of the libraries that make Java life bearable. Converting an app from Java to Kotlin is made much easier thanks to automated syntax conversion, and the result is almost always an improvement. If you’re an Android developer, you owe it to yourself to give it a try.

At [Keepsafe](https://www.getkeepsafe.com/), all new Android development is in Kotlin, and legacy Java code is steadily being converted to Kotlin as we make changes to it.

Interested in working with us? Have a look at our [job openings](https://jobs.lever.co/keepsafe).

**[1]** In the case of a _lateinit_ property, _kotlin.UninitializedPropertyAccessException_ will be thrown, where the _Delegates.notNull_ will throw an _IllegalStateException_.

**[2]** There are some details about _lateinit_ that are worth noting, especially if you plan on accessing a _lateinit_ property from Java code. First is that _lateinit_ cannot be applied to primitive types such as _Int_ or _Double_. The second is that a _lateinit_ property is backed by a field with the same visibility as the property, and this field is visible from Java. Additionally, that backing field can be freely set to null from Java. If any of those are issues for your use case, _Delegates.notNull_ may be a better choice.

**[3]** The lazy _Sequence_ operators can outperform the eager versions by up to 20%, but only once list sizes start growing very large. For lists under a megabyte or so in size, the lazy versions often perform the same or worse than the eager versions.

**[4]** This is a bit of a simplification. Kotlin will only generate a backing field if you don’t define a get function, or if the defined get function doesn’t reference the implicit _field_ identifier.

"""

Article(
  title = "Kotlin: The Good, The Bad, and The Ugly",
  url = "https://medium.com/keepsafe-engineering/kotlin-the-good-the-bad-and-the-ugly-bf5f09b87e6f#.prwij1b6d",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "AJ Alt",
  date = LocalDate.of(2016, 8, 20),
  body = body
)
