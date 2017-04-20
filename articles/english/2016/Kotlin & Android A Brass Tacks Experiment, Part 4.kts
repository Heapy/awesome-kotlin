
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![Header Picture](https://cdn-images-1.medium.com/max/800/1*UN-S8ELMC2kpHf4tJKfbLQ.png)

*Disclaimer: I am a Google employee, but the views expressed in this article are not those of my employer.*

## Kotlin & Android: A Brass Tacks Experiment, Part 4

Welcome to part 4 in this a series! If you missed [part 2](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f) and [part 3](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-3-84e65d567a37), consider jumping back to those for a minute to see where we are in this journey to learn what the Kotlin® language can uniquely offer for Android development.

At this point, we have a pair of functions that we can use to succinctly express the creation of an entire Android view hierarchy in Kotlin code instead of the usual XML resources. Kotlin’s *type-safe builder* pattern is really shining here! However, in practice, there are still some rough edges with this scheme. Most notably, Android developers are used to having some special expressions in XML for certain Android concepts, such as sizes of Views measured in density independent pixels. This is super easy in XML and super tiresome in code!

Here’s an example of a tiresome way to set the maxWidth property of a TextView to 120dp using our new v function:


```kotlin
val view = v<TextView>(context) {
    // ugly!
    maxWidth = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 120, context.resources.displayMetrics).toInt()
}
```
[gist](https://gist.github.com/CodingDoug/abe3765f9fe641871ede)

Contrast that to XML layouts, where you’d simply say:

```xml
<TextView android:maxWidith="120dp" />
```
[gist](https://gist.github.com/CodingDoug/c239a76530670537ce6b)

Bah! We just lost all the convenience that our v functions were trying to gain!

### We need an abbreviated way to convert dp to px.

What I’d like is a function to provide shortened syntax for specifying pixel measurements in other units. Here’s what I want to say instead of the above mess:

```kotlin
val view = v<TextView>(context) {
    // simpler way to set maxWidth to 120dp
    maxWidth = dp_i(120)
}
```
[gist](https://gist.github.com/CodingDoug/cef8e7f5fe6324ff6436)

What I’m proposing here is a function that takes a value measured in dp and returns the value converted to px for the current device. Why call the function “dp_i” and not just “dp”? Sometimes Android wants to take pixel measurements as a floating point number and sometimes as an integer. I don’t want to manually cast the return value (still too many more characters), so I’ll just make one function for of each type, “dp_i” and “dp_f”.

But there’s a wrinkle here. If you look back at the full code that computes the dp value, it requires a Context to operate. I don’t want to have to pass a Context as another argument to dp_i every time I call it. So I’m going to use a feature of Kotlin called [extension functions](https://kotlinlang.org/docs/reference/extensions.html) to get the brevity I prefer.

Let’s jump right into the code. Written as extension functions, here’s what dp_i and dp_f look like:

```kotlin
import android.view.View

fun View.dp_f(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}

fun View.dp_i(dp: Float): Int {
    return dp_f(dp).toInt()
}
```
[gist](https://gist.github.com/CodingDoug/489045d5a92be732dec9)

### How does an extension function work?

The first thing to notice in the above code is the apparent name of the functions. You might have expected to see just “dp_f” for the first function, but instead we have “View.dp_f”. This is a special syntax in Kotlin for extension functions. There is a dot between a class name (here, android.view.View) and the name of the function to define. What we did here was tell Kotlin to augment the View class with a new two new methods called “dp_f” and “dp_i”. There’s a couple things you get with extension functions like these.

**First, code in the body of the extension functions can act like a member of View by accessing its members and methods (but only of “public” and “internal” visibility)**. This means that dp_f can use the View’s Context reference as exposed by the synthetic property called “context”. Now we don’t have to pass a Context as a parameter since it comes implicitly with the View.

**Second, other code that imports these extension functions can call them as if they’re normal member methods on instances of View objects**. This means that our v function’s lambda with receiver argument of type View can call these methods similarly to ordinary functions, implicitly using the receiver View object reference. So you can say “maxWidth = dp_i(120)” in the lambda, and Kotlin will recognize that you want to call the dp_i function on the View type receiver object.

**One important thing to know here is that Kotlin doesn’t actually make changes to a class definition when defining an extension function**. A class will always be its own complete unit after it’s been loaded by a program, so we can only use extension functions to add code around it. Also, the existing methods on View also can’t reach back into and extension function, because it’s not a real member defined with the class.

The upshot of these points, for this experiment, is that we now have convenient functions for converting dp to px in our v function lambdas!

**We’re not stopping here! How about another shortcut using extension functions?**

We’ve seen that you can do tricky things with extension functions to make some kinds of functions more convenient to use. Let’s continue with that thought to tighten up our v functions.

Currently, we have these two function signatures, the first for building the root view using a Context and the second for creating nested child views in a parent view:

```kotlin
inline fun <reified TV : View> v(context: Context, init: TV.() -> Unit) : TV

inline fun <reified TV : View> v(parent: ViewGroup, init: TV.() -> Unit) : TV
```
[gist](https://gist.github.com/CodingDoug/c91ef8277e3edae1be6e)

It would be nice if we didn’t have to pass the Context or ViewGroup as the first parameter. With extension functions, we achieve this just like we did above when avoiding passing a Context to dp_f. Here’s a re-implementation of both functions as extension functions, with the commented out lines showing the original code for v above the newly modified lines:

```kotlin
//inline fun <reified TV : View> v(context: Context, init: TV.() -> Unit) : TV {
inline fun <reified TV : View> Context.v(init: TV.() -> Unit) : TV {
    val constr = TV::class.java.getConstructor(Context::class.java)

 // val view = constr.newInstance(context)
    val view = constr.newInstance(this)

    view.init()
    return view
}

//inline fun <reified TV : View> v(parent: ViewGroup, init: TV.() -> Unit) : TV {
inline fun <reified TV : View> ViewGroup.v(init: TV.() -> Unit) : TV {

    val constr = TV::class.java.getConstructor(Context::class.java)

 // val view = constr.newInstance(parent.context)
    val view = constr.newInstance(context)

    parent.addView(view)
    view.init()
    return view
}
```
[gist](https://gist.github.com/CodingDoug/43d17d44459805e51072)

You can see that we’re removing the first argument to each function (Context and ViewGroup), and instead, expecting those references to come from the instance of the class they are extending. The functions now have just a single argument — the lambda with receiver that modifies the created View.

With these modified functions, if we’re coding inside an Activity (which is a subclass of Context), we refer to v as a member of the Activity object. We can take advantage of this to build nested views even easier like this:

```kotlin
v<LinearLayout> {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    orientation = VERTICAL

    v<TextView> {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "Hello"
    }
    v<TextView> {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "World"
    }
}
```
[gist](https://gist.github.com/CodingDoug/3961d2ccc55376cf1274)

The invocations of v don’t even look like function calls because we no longer need parenthesis at all. If you recall from [part 2 in this series](https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f), if the last argument to a function is a lambda, you can place it after the parenthesis. And in this case, when there’s only one argument, you don’t need the parenthesis at all!

Kotlin’s extension functions have just gone a long way toward helping us express an Android view hierarchy in a very readable and succinct way in code. However, there’s still some more problem spots that could use some attention. For example, lets take this code that assigns 16dp of left padding to a TextView:

```kotlin
v<TextView> {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    setPadding(dp_i(16), 0, 0, 0)
    text = "Hello"
}
```
[gist](https://gist.github.com/CodingDoug/6119424973b9c0f3824d)

It’s pretty ugly to mix a method call to setPadding() with the synthetic property accessors for layoutParams and text. setPadding() is causing us problems here because it’s not a JavaBeans-style setter — it has more than one argument. Therefore, Kotlin can’t assign a synthetic property to it. But fear not! This can be fixed with clever use of another Kotlin language feature, as we’ll discover in the upcoming part 5.

If you want to stay on top of this series, you can follow me both [here on Medium](https://medium.com/@CodingDoug) and [on Twitter](https://twitter.com/CodingDoug) as CodingDoug and get instant notification of future parts!

"""

Article(
  title = "Kotlin & Android: A Brass Tacks Experiment, Part 4",
  url = "https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-4-4b7b501fa457#.dllcmjpbu",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Doug Stevenson",
  date = LocalDate.of(2016, 2, 4),
  body = body
)
