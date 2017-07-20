
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![Header Picture](https://cdn-images-1.medium.com/max/800/1*UN-S8ELMC2kpHf4tJKfbLQ.png)

*Disclaimer: I am a Google employee, but the views expressed in this article are not those of my employer.*

## Kotlin & Android: A Brass Tacks Experiment, Part 5

Well, we’ve made it to part 5 in an ongoing weekly blog about my experiences using the Kotlin® language to do useful things for Android development. If you’ve landed here randomly or unexpectedly, perhaps you’d like to jump to one of the prior parts first and catch up to where we are now?

[Part 1](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-1-3e5028491bcc) (setup) | [Part 2](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f) (code starts here) | [Part 3](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-3-84e65d567a37) | [Part 4](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-4-4b7b501fa457)

At this point, we have a pretty good way of expressing the creation of an Android view hierarchy with fewer keystrokes than the equivalent XML resource, and *far* fewer than the equivalent written purely in the Java® language. Its syntax is declarative, the nesting of views is obvious, and we know how to use Kotlin *extension functions* to add convenient utility functions as needed.

But we left things last time with an awkward situation while trying to set a view’s left padding. Here’s what it looks like currently to set padding. Note that we have to call setPadding() with its four arguments instead of assigning to a *synthetic property* that’s derived from JavaBeans-style getters and setters:

```kotlin
v<TextView> {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    setPadding(dp_i(16), 0, 0, 0)  // bleh - it's not consistent
    text = "Hello"
}
```

To be consistent in this view builder code, we would rather specify the left padding directly by assignment instead of calling a method with four parameters. At first blush, you might consider using another extension function to attach an extension method called “setLeftPadding(int)” to the View class. You could do this, but then you wouldn’t actually be able to refer to it as “leftPadding = dp_i(16)” in the *lambda with receiver*. It turns out that member functions that look like JavaBeans-style accessors that are *defined in Kotlin* don’t get automatically converted into synthetic properties like member methods *defined in Java classes*. Bummer... but not really!

Instead, Kotlin also allows you to define *extension properties*, which allow a syntax that looks just like the synthetic properties for Java class methods that we’ve been using so far. An extension property is grafted onto an existing class just like a Kotlin extension method so you can access them on instances of that class, assuming the extension property is imported into your code.

So what we can do is define extension properties to set padding values in a way that’s consistent with the other synthetic properties of view. I’ll just show a single property here for left padding; the others follow suit:

```kotlin
var View.padLeft: Int
    // Specify the setter behavior; value is the assigned Int
    set(value) {
        // Use View.setPadding to set left padding value,
        // uses Kotlin synthetic properties for the others
        setPadding(value, paddingTop, paddingRight, paddingBottom)
    }
    // Specify the getter behavior
    get() {
        return paddingLeft
    }
```

Using this “padLeft” extension property, our earlier TextView creation can now be written like this:

```kotlin
v<TextView> {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    padLeft = dp_i(16)  // ahhh - much better!
    text = "Hello"
}
```

Consistency in assignment for all the properties. Excellent!

There’s a few things to note about the padLeft extension property:

* It uses a syntax similar to the “class dot function” notation for extension functions, but now it’s “class dot property”.
* The property type is given after the colon.
* It’s declared using “var”, which is Kotlin’s way of saying a variable or property is *mutable*. This means we can assign values directly to it. (On the other hand, “val” properties are *immutable* and can only be read.)
* An extension property that’s mutable requires us to provide both a getter and setter implementation. (Likewise, an immutable property requires only a getter).

The implementation of the padLeft *setter* is defined in terms the existing setPadding() method of View. It takes the value provided from the right hand side of the assignment expression and uses that as the first parameter to setPadding(), along with the values of synthetic properties for the other existing padding values of the TextView. Below the setter, the implementation of the padLeft *getter* is simply defined using the View’s existing “paddingLeft” synthetic property.

Side note: I find it somewhat ironic that Android’s View class provides JavaBeans-style *getters* for each directional padding metric but *not setters*. Oh well, we fixed it with extension properties!

If you ever need more from a class API that you don’t control, just add what you want with Kotlin’s extension functions and properties. 😃

## Any other Kotlin tricks to share?

There a couple more simple tricks that can be used to tighten up the syntax even more. One trick allows you to get rid of all references to “v” in your view builder expressions, and another will make it easier to specify complicated layout parameters. So check in next time to see how these work!

As usual, you can follow me [here on Medium](https://medium.com/@CodingDoug) and also [on Twitter as CodingDoug](https://twitter.com/CodingDoug) to get notified of the next part to this series. I hope you’ve found it useful for learning Kotlin language features as well as learning some new things about Android views.

"""

Article(
  title = "Kotlin & Android: A Brass Tacks Experiment, Part 5",
  url = "https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-5-bd79eb9c85d4#.tyblv8pv8",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Doug Stevenson",
  date = LocalDate.of(2016, 3, 1),
  body = body
)
