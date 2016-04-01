---
title: 'Kotlin & Android: A Brass Tacks Experiment, Part 6'
url: https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-6-49fea0ed3a7#.53axmkcxt
categories:
    - Android
    - Kotlin
author: Doug Stevenson
date: Mar 8, 2016 20:57
---
![](https://d262ilb51hltx0.cloudfront.net/max/1600/1*UN-S8ELMC2kpHf4tJKfbLQ.png)

_Disclaimer: I am a Google employee, but the views expressed in this article are not those of my employer._

### Kotlin & Android: A Brass Tacks Experiment, Part 6

Alright! We’re nearing the end of this series about experimenting with Kotlin to make view creation easier during Android app development. (Please [jump back to the beginning](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-1-3e5028491bcc) if you landed here prematurely.) So far we’ve learned a bunch of features of the Kotlin® language that help implement the type-safe builder pattern, including:

* Lambda with receiver & reified types ([part 2](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f))
* Extension functions ([part 4](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-4-4b7b501fa457))
* Extension properties ([part 5](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-part-5-bd79eb9c85d4))

These have all been instrumental in creating a “domain specific language” for expressing the programmatic creation of view hierarchies in a declarative fashion. For example, using all these techniques combined, we can write this to create two TextViews stacked on top of each other with some padding:

```kotlin
v<LinearLayout> {
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    orientation = VERTICAL
    padLeft = dp_i(16f)

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

This is pretty good on its own, and it definitely beats writing out all these view creations and associations traditionally. Now, to wrap up the coding part of this experiment, let’s do a couple more things to tighten up the syntax _even more!_

**How do we assign values to specialized layout parameter properties?**

It’s not immediately evident from the code above, but there is a problem if you want to assign some attributes of the layout parameters specific to LinearLayout. Up until now we have only specified the height and width of a view using the [LinearLayout.LayoutParams](http://developer.android.com/reference/android/widget/LinearLayout.LayoutParams.html) class constructor, and assigned that new object to the layoutParams synthetic property of a view. But what if we also wanted to assign values to the “gravity” property of the layout parameters? It might make sense to try it like this:

```kotlin
layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
layoutParams.gravity = Gravity.CENTER  // oops, compiler says no
```

But the compiler won’t allow that because the layoutParams property of View is of type ViewGroup.LayoutParams, the superclass of all other LayoutParams. In order to use layoutParams as a LinearLayout.LayoutParams, it will need to be downcast.

Instead of casting and assigning to another value as you would in the Java® language, we can use a Kotlin stdlib function called “[with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/with.html)” to make it easier to deal with layoutParams. The “with” function takes an object parameter along with lambda with receiver and executes that lambda against the given object. This description may not sound interesting, but it allows a syntax like this:

```kotlin
v<TextView> {
    with(layoutParams as LinearLayout.LayoutParams) {
        width = WRAP_CONTENT
        height = WRAP_CONTENT
        gravity = CENTER
    }
    text = "Hello"
}
```

Notice how we’re able to simultaneously cast layoutParams to LinearLayout.LayoutParams, then use that as the receiver inside the lambda for abbreviated access to its properties.

Another subtle thing to be aware of here is that an Android view container will automatically create and assign a correctly typed layout parameter object to layoutParams when a child view is added to it. In our case, adding a TextView child to a LinearLayout will _automatically_ create and assign a LinearLayout.LayoutParams to the TextView’s layoutParams property. This means we don’t actually need to create a new LayoutParams object — we can just access the one that’s already provided by the parent LinearLayout. Please remember that this only happens when adding a child view to a parent ViewGroup. The outermost ViewGroup will not have layout parameters assigned automatically because it’s not yet attached to its own parent!

All that said, this is not exactly the _most_ desirable syntax for layout parameters, but it’s the most straightforward way I could find to specify specialized layout parameters inside the lambdas we have for creating views.

**And now, for my last trick, I’ll make the “v” functions disappear!**

The “v” functions we’ve been using so far are pretty convenient, but what if we want to further shortcut their syntax? It would be even more convenient to take the layout from above and pattern it like this:

```kotlin
linearLayout {
    textView {
        // properties...
    }
    textView {
        // properties...
    }
}
```

This looks even more natural, and makes for easy reading. It also looks very similar to the Android Gradle configuration language. The trick to getting a syntax like this is to effectively create a sort of alias for each type of view we want to use. So, to create a structure like above, we need a function called “linearLayout” that works like “v<LinearLayout>” and a function called “textView” that works like “v<TextView>”. Kotlin makes this pretty easy to set up:

```kotlin
fun ViewGroup.linearLayout(init: LinearLayout.() -> Unit) = v(init)
fun Context.linearLayout(init: LinearLayout.() -> Unit) = v(init)

fun ViewGroup.textView(init: TextView.() -> Unit) = v(init)
fun Context.textView(init: TextView.() -> Unit) = v(init)
```

Here, I defined two functions for each type of view in order to account for the different v functions that could be invoked, depending on the starting point of a Context or a ViewGroup parent. The Kotlin language feature that makes these declarations possible is called _single expression function_. This is a special syntax for functions that allows you to:

1.  Omit the usual curly braces for the function body.
2.  Infer the return type based on the return type of the expression.
3.  Omit the return keyword.

Now, let’s use these convenience functions to recreate the view hierarchy from the beginning of this article:

```kotlin
linearLayout {
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    orientation = VERTICAL
    padLeft = dp_i(16f)

    textView {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "Hello"
    }
    textView {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        text = "World"
    }
}
```

Wow, we’ve come a long way since our humble beginnings in part 2! (As long as you’re willing to define alias functions for each type of view you want to build.)

**But the novelty of this technique is not all that novel after all!**

Some readers of this series have pointed out that this method using the type-safe builder pattern for building view hierarchies is really close to that of a project called [Anko](https://github.com/Kotlin/anko). I actually set out on this series without having any knowledge of it, and it wasn’t until I was finished with the first two parts of this series that one of my colleagues pointed this out to me. Anko provides a more comprehensive solution for building views than what I’ve been experimenting with here. Even after seeing it, I was still pretty interested in going through the process of discovering Kotlin language features and how they could be applied to Android development. If you’ve been following this series, I hope you’ve also enjoyed discovering these things!

**So, are we really any better off with this technique?**

You might be wondering now if it’s better in general to create your Android views programmatically like this in Kotlin than the traditional XML layout resources. Up till now, it certainly seems like a big win to use Kotlin in this way. So, for the next part in this series, I’ll compare the two techniques with each other to see how they compare to each other. Also, I’ll provide a sample project that you can use to easily compare equivalent implementations.

Don’t forget that you can follow me [here on Medium](https://medium.com/@CodingDoug) to get [the next part to this series](https://medium.com/p/kotlin-android-a-brass-tacks-experiment-wrap-up-2b37e3ac8957) (in addition to future blogs and series!). And for even faster updates, [follow me on Twitter](https://twitter.com/CodingDoug)!
