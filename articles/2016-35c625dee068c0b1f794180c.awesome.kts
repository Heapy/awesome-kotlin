
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
> There are known knowns. These are things we know that we know. There are known unknowns. That is to say, there are things that we know we don’t know. But there are also unknown unknowns. There are things we don’t know we don’t know. — Donald Rumsfeld

I have been using Kotlin for almost a two years and I started from putting it to toy project intended for my tech talks. Do you remember that time when Kotlin had _traits_ instead of _interfaces_? From the very first line, I knew that this language will change my life, and you know what? I was totally right.

Knowledge makes you better and that is why world’s most successful engineers are constantly learning new things. Here is my list of five less known things about Kotlin and I hope you will find at least three worth knowing.

### 1. Name your imports

In Kotlin, _imports_ are used by the compiler to let you name your classes by their unqualified name. What happens if you have naming conflict? Sad things happen!

```kotlin
package com.code.sliski.userinfoscreen.ui

import ...

import android.view.View // Conflict

class UserInfoFragment : Fragment(), com.code.sliski.userinfoscreen.ui.View { // Conflict

    override fun onCreateView(inflater: LayoutInflater, 
                              container: ViewGroup?, 
                              state: Bundle?): View = // Conflict
            inflater.inflate(user_info_fragment,
                             container,
                             false)
}

interface View // Conflict
```

Personally, I hate using fully qualified package names in my code as it lowers readability and clarity. **In Python, you can name your imports to fix the conflicts and Kotlin supports it as well** ❤.

```kotlin
import android.view.View as AndroidView // Named import

class UserInfoFragment : Fragment(), View {
    
    override fun onCreateView(inflater: LayoutInflater, 
                              container: ViewGroup?, 
                              state: Bundle?): AndroidView = // Using named import
}
```
_Refactored code using named import_

### 2. Change companion object name

Companion object was introduced to replace static members. It is not only for declaring static properties but also to name them. How? Let’s have a look at this example.

```kotlin
// Using in Java
CustomButton button = new CustomButton(context);
button.setVisibility(CustomButton.Companion.getGONE());

// Using in Kotlin
val button = CustomButton(context)
button.visibility = CustomButton.VISIBLE

class CustomButton(context: Context?) : View(context) {
    companion object {
        // Visibility
        val GONE = 1
        val VISIBLE = 2
        val INVISIBLE = 3
    }
}
```

By default, Kotlin creates a static nested class _Companion_ for every companion object. That is why you need to use _CustomButton.Companion_ to access static members from Java code(you can also use it in Kotlin but it is not necessary). **Kotlin lets you change the default name of companion object to whatever name you want.** Refactored code looks like this.

```kotlin
// Using in Java
CustomButton button = new CustomButton(context);
button.setVisibility(CustomButton.Visibility.getGONE());

...

class CustomButton(context: Context?) : View(context) {
    companion object Visibility {
        val GONE = 1
        val VISIBLE = 2
        val INVISIBLE = 3
    }
}
```

The biggest drawback is that Kotlin does not support multiple companion objects for a class. It would be great for grouping static properties.

```kotlin
// Using in Java
CustomButton button = new CustomButton(context);
button.setVisibility(CustomButton.Visibility.getGONE());

...

class CustomButton(context: Context?) : View(context) {
    companion object Visibility {
        val GONE = 1
        val VISIBLE = 2
        val INVISIBLE = 3
    }
}
```

This code does not compile because Kotlin supports only one companion object per class

### 3. Compose functions

I bet you used function references before but have you ever tried to use them to compose functions? Imagine that you want to map an array of prices to prices that are taxed, discounted and rounded. Using common approach you will end up with something like this.

```kotlin
val prices = listOf(21.8, 232.5, 231.3)
prices.map(::taxed)
      .map(::discounted)
      .map(::rounded)

fun taxed(value: Double): Double = value * 1.4
fun discounted(value: Double): Double = value * 0.9
fun rounded(value: Double): Double = Math.round(value).toDouble()
```

This example is begging for composition so do not disregard it and make the code better place. Abracadabra!

```kotlin
val prices = listOf(21.8, 232.5, 231.3)
val taxedDiscountedRounded = compose(::taxed, ::discounted, ::rounded)
prices.map(taxedDiscountedRounded)

fun <A, B> compose(f: (A) -> A,
                   g: (A) -> A,
                   h: (A) -> B): (A) -> B = { x -> h(g(f(x))) }

fun taxed(value: Double): Double = value * 1.4
fun discounted(value: Double): Double = value * 0.9
fun rounded(value: Double): Double = Math.round(value).toDouble()
```

**Functions composition not only makes your code cleaner but also faster.** Once you understand it, you will be able to compose almost everything.

### 4. Change name of generated class

Extension functions are one of the most attractive features in Kotlin but using them in Java code can give you a serious headache. It is ugly and besides is nothing else like invoking a static method.

```kotlin
// Main.java
public static void main(String[] args) {
    String name = null;

    AnyKt.ifNull(name, new Function1<Object, Unit>() {
        @Override
        public Unit invoke(Object o) {
            return null;
        }
    });
}

// Any.kt
inline fun <T> T?.ifNull(function: (T?) -> Unit) {
    if (this == null) function(this)
}
```

Kotlin generates class _AnyKt_ with a static method so you can use it in Java. **There is an option to change the name of generated class to achieve better readability.**

```kotlin
// Main.java
public static void main(String[] args) {
    String name = null;

    Nullcheck.ifNull(name, new Function1<Object, Unit>() {
        @Override
        public Unit invoke(Object o) {
            return null;
        }
    });
}

// Any.kt
@file:JvmName("Nullcheck")
package ...

inline fun <T> T?.ifNull(function: (T?) -> Unit) {
    if (this == null) function(this)
}
```

### 5. Validate an assignment and “veto” it

The way in which Kotlin handles delegation is pretty spectacular so if you are not familiar with it, you should totally check my article: “Zero boilerplate delegation in Kotlin”.

Besides of “class delegation”, there is an interesting mechanism called “delegated properties”which is used for _lazy_ property initialization. How would you solve scenario in which you need to be able to intercept an assignment and “veto” it? Is there any clean way to do it? Yes, there is!

```kotlin
var price: Double by Delegates.vetoable(0.0) { prop, old, new ->
    validate(new)
}

fun validate(price: Double) : Boolean {
    // Validation checks
}
```

Sample shows usage of a built-in _vetoable_ delegate. Lambda passed to the _vetoable_ is called before the assignment of a new value to property. **Returning false from the lambda allows you to “veto” the assignment but if you want to pass it through return true.**

![](https://cdn-images-1.medium.com/max/880/1*gkc1Y_YumE5sIffEmO03Yw.jpeg)

[http://looneytunes.wikia.com/wiki/That's_All_Folks](http://looneytunes.wikia.com/wiki/That%27s_All_Folks)

"""

Article(
  title = "5 small things you probably don’t know about Kotlin",
  url = "https://medium.com/@piotr.slesarew/5-small-things-you-probably-dont-know-about-kotlin-255261940de6#.pgfivsdr7",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Piotr Ślesarew",
  date = LocalDate.of(2016, 11, 25),
  body = body
)
