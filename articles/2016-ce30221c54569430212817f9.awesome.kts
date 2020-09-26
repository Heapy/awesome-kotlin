
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## **Intro**

In this, the fourth and final post in the Kotlin Month series, we take a look at a widely overarching feature of Kotlin that is tightly coupled with a few other features: Properties. Besides the obvious benefits of properties that they provide in all languages, Kotlin has reusable properties via Delegated Properties as well as really clean syntax for distinguishing between read-only and full properties.

## **`var` and `val` and Type Inference**

Kotlin makes it very easy to create clean, expressive properties. `var` and `val` make it so we don’t have to _remember_ to mark our fields as `final`. Depending on circumstances, the type might be able to be omitted as well.

### **More Concise Than Most Other Property Syntaxes**

Kotlin’s properties are supremely concise in comparison to any other syntax that I’ve seen. Here’s why:

* In Kotlin, we never need to declare the backing fields for a property, although we can use a backing _property_ for more complicated implementations when needed. If you ever need access to the backing field in the getter and setter methods, you simply refer to it as `field`.
* In Kotlin, unless we’re actually defining an alternative implementation of `get` and `set`, we don’t need to type them; `var` and `val` are used to determine whether it’s read-only or read-write.
* If the property is being initialized directly from the primary constructor without checks or changes, it can simply be declared in the parameter list of the primary constructor. See the [official documentation on Kotlin’s constructors](https://kotlinlang.org/docs/reference/classes.html#constructors) for more information.
* As mentioned earlier, we don’t even need to specify the type for the property if we’re assigning a value to it right away (this only applies to properties declared in the body of the class, as opposed to those declared in the primary constructor).
* [Delegated properties](https://kotlinlang.org/docs/reference/delegated-properties.html). If we end up using the same code for multiple properties’ getters and setters, we can extract that code into a delegated property, which is just a class that defines the `getValue(...)` operator function and, optionally, the `setValue(...)`] operator function. These work a lot like descriptors in Python, except there’s one per instance, rather than one per class. To use them, we use our normal in-body property definition, followed by `by` and an instance of the delegated property class. Some built-in delegated property types in Kotlin include `lazy`, which allows us to lazily instantiate the value in the property; `observable`, which makes the property observable; and using `Map`s via some extension functions.

### **The Not-So-Great Parts**

There are some not-so-great things about properties in Kotlin, though. The first of which is the fact that the class definition line can become unhelpfully long. This is because this one line contains `class`, the name of the class, any type parameters (generics), the primary constructor argument list, and any inheritance. Now, even when these are simple, the line can easily clear 80 characters if there are more than two properties in the constructor, especially if there’s some inheritance. It’s gets even longer if a visibility modifier needs to be applied to anything; to the class, to the constructor (which not only requires the visibility modifier, but also the `constructor` keyword to show that the modifier is applying to that), or to the properties – which are already longer than typical parameters because of the `val` or `var` keyword. Luckily, Kotlin has forgone the `extends` and `implements` keywords in favor of `:`. If we still used those keywords, those lines would be unbearably long.

Breaking up the class definition line into multiple lines helps, but it’s always a little awkward to have the inheritance stuff near the end. I’d kind of like to have it right after the class name and type parameters if we want. Oh well.

The other problem comes from the fact that there can potentially be two different places to define properties: in the primary constructor or in the class body. This can make it more difficult to find properties sometimes. I think a convention needs to be put out there that states that, if you have any (public?) properties that are defined in the class body, all properties should be defined there. It ends up with more typing (but still less than Java or C#, since you can set a property equal to a parameter in the primary constructor), but clarifies the code by having only one place to look for properties. None in the constructor? They must all be in the body, then. This also keeps private properties from lengthening the class definition line with the visibility modifier.

## **Kotlin Month Summary**

We’ve looked at a lot of my favorite features of Kotlin, but there’s so much more to see, so if you’re not familiar with Kotlin, check out their documentation. It’s great, and is able to stay short by letting you assume that anything else they haven’t explained is just like in Java. I’ve read through the whole thing a couple times, actually; it’s really well written;

If there were only 4 things I could take from Kotlin and put into Java, they would be, in order of “importance”:

1.  Extension Methods
2.  First-Class Delegation
3.  Properties
4.  Simple Primary Constructor (It would probably be done a bit differently than in Kotlin, just to work better with Java’s existing syntax and to hopefully avoid the really long class declaration line)

## **Outro**

So, this is the end of this post as well as the Kotlin Month! series. But this definitely isn’t the end of me praising Kotlin. In fact, I’ve submitted an abstract to do a talk on Kotlin at [That Conference](https://www.thatconference.com/). It’s a talk that does an introduction to Kotlin before (unless I decide to do it _while_) showing the steps involved in migrating Java code to Kotlin.

For those too lazy to click the link, That Conference is a dev conference from August 8 – 10 in Wisconsin Dells (a bit south of central Wisconsin). At an indoor themepark/waterpark. How cool is that. I hope to meet some of you guys there!

"""

Article(
  title = "Kotlin Month Post 4: Properties",
  url = "https://programmingideaswithjake.wordpress.com/2016/03/19/kotlin-month-post-4-properties/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 3, 19),
  body = body
)
