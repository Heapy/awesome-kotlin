
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![](https://d262ilb51hltx0.cloudfront.net/max/800/1*UN-S8ELMC2kpHf4tJKfbLQ.png)
_Disclaimer: I am a Google employee, but the views expressed in this article are not those of my employer._

### Kotlin & Android: A Brass Tacks Experiment, Part 3

This is post 3 in a series about my experiment using Kotlin in a uniquely Android way. If you haven’t read [part 1 (setup)](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-1-3e5028491bcc) and [part 2](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f) yet, read up to catch up!

We left things last time with a fairly useful function called “v” using the type-safe builder pattern in Kotlin®. It can be used to build instances of arbitrary types of Android views.

```kotlin
import android.content.Context
import java.lang.reflect.Constructor

inline fun <reified TV : View>
        v(context: Context, init: TV.() -> Unit) : TV {
    val constr = TV::class.java.getConstructor(Context::class.java)
    val view = constr.newInstance(context)
    view.init()
    return view
}
```

We can import this definition into some Kotlin code and call it to create and initialize a new view of any type:

```kotlin
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView

val view = v<TextView>(context) {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    text = "Hello"
}
```

#### But is this practical?

What if we want to create a simple layout with two TextViews stacked on top of each other? Here’s a simple view hierarchy expressed in XML:

```xml
<LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical" >
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="World" />
</LinearLayout>

```

Our poor v function can’t build all that at once. But it could with some help! We just need an additional function that has smarts for creating views that get added to a parent ViewGroup (such as LinearLayout here, or RelativeLayout). So let’s make a new v function overload to buddy up with our existing v function:

```kotlin
inline fun <reified TV : View> v(parent: ViewGroup, init: TV.() -> Unit) : TV {
    val constr = TV::class.java.getConstructor(Context::class.java)
    val view = constr.newInstance(parent.context)
    parent.addView(view)
    view.init()
    return view
}
```

This function is _almost exactly_ like the original v except for one thing. Its first parameter is of type ViewGroup instead of Context. This new v function needs to know about a _parent_ ViewGroup is so it can add its own newly created view to that parent before initializing and returning it. That’s the first argument to this function. It will then get a hold of the Context needed to initialize the view from the parent argument instead of being passed the Context directly.

Let’s see how this new v function can work together with our old v to build the same view hierarchy as the above XML:

```kotlin
import android.content.Context
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView

val view = v<LinearLayout>(context) {
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    orientation = VERTICAL

    // adds a new TextView as the first child to the LinearLayout ("this")
    v<TextView>(this) {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "Hello"
    }

    // adds a another new TextView to the LinearLayout
    v<TextView>(this) {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "World"
    }
}
```

I love the way these functions nest naturally just like the XML!

If you recall from [part 2](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f) of this series, a Kotlin _lambda with receiver_ makes the receiver object available as “this” in the lambda body. In the specific case above, the LinearLayout typed receiver in the _outer_ v lambda (made available via the “this” keyword) is passed as the first parameter of both calls to the new _inner_ v function. Because LinearLayout is a type of ViewGroup, Kotlin knows that we intend to use our new v function overload instead of our original v that takes a Context.

With these two buddy v functions we can now _programmatically_ and _succinctly_ create nested views as deeply as we want with any type of ViewGroup parent and View child. We’re starting to get a sort uniquely expressive language (some might say “[domain specific language](https://en.wikipedia.org/wiki/Domain-specific_language)”) for creating Android views that’s close to the usual XML representation. It turns out that this is also faster than inflating XML, as we’ll discover in a future post in this series.

#### Room for Improvement

There’s still some awkwardness with this scheme when you want to specify a dimension of some view in density independent pixels. For example, in XML, you might simply say “48dp” as a value to describe width or height, but to say this programmatically in Kotlin using the API provided by Android, you’d have to write something gnarly like this:

```kotlin
TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    48,
    context.resources.displayMetrics)
```

Super yuck! Stay tuned for [next week’s post](https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-4-4b7b501fa457) where we’ll make use of another Kotlin language feature to address this problem and further tighten up expressions for view creation using our dynamic duo of v functions. Be sure to follow me here on Medium as [@CodingDoug](https://medium.com/@CodingDoug) or on [Twitter as the same](https://twitter.com/CodingDoug) to stay on top of this series!

"""

Article(
  title = "Kotlin & Android: A Brass Tacks Experiment, Part 3.",
  url = "https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-3-84e65d567a37#.lgtyczp3h",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Doug Stevenson",
  date = LocalDate.of(2016, 2, 16),
  body = body
)
