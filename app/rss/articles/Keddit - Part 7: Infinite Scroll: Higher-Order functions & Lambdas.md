---
title: 'Keddit — Part 7: Infinite Scroll: Higher-Order functions & Lambdas'
url: https://medium.com/@juanchosaravia/keddit-part-7-infinite-scroll-higher-order-functions-lambdas-3a11fbd5090e#.cdsuea4kc
categories:
    - Android
    - Kotlin
    - Keddit
author: Juan Ignacio Saravia
date: Apr 09, 2016 15:36
---
![](https://d262ilb51hltx0.cloudfront.net/max/2000/1*coKbXMpkG8Fc1NJtM6Jp4w.png)

### Content

* [Introduction: A few words about this](https://medium.com/@juanchosaravia/learn-kotlin-while-developing-an-android-app-introduction-567e21ff9664)
* [Part 1:](https://medium.com/@juanchosaravia/learn-kotlin-while-developing-an-android-app-part-1-e0f51fc1a8b3) [Configuring Android Studio with Kotlin](https://medium.com/@juanchosaravia/learn-kotlin-while-developing-an-android-app-part-1-e0f51fc1a8b3)
* [Part 2: MainActivity.kt: Syntax, Null Safety and more…](https://medium.com/@juanchosaravia/learn-kotlin-while-developing-an-android-app-part-2-e53317ffcbe9)
* [Part 3: NewsFragment.kt: Extension Functions, Android Extensions…](https://medium.com/@juanchosaravia/keddit-part-3-extension-functions-android-extensions-and-more-faa7d232f232)
* [Part 4: RecyclerView — Delegate Adapters & Data Classes with Kotlin](https://medium.com/@juanchosaravia/keddit-part-4-recyclerview-delegate-adapters-data-classes-with-kotlin-9248f44327f7)
* [Part 5: Kotlin, RxJava & RxAndroid](https://medium.com/@juanchosaravia/keddit-part-5-kotlin-rxjava-rxandroid-105f95bfcd22)
* [Part 6: API — Retrofit & Kotlin](https://medium.com/@juanchosaravia/keddit-part-6-api-retrofit-kotlin-d309074af0)
* [Part 7: Infinite Scroll: Higher-Order functions & Lambdas](https://medium.com/@juanchosaravia/keddit-part-7-infinite-scroll-higher-order-functions-lambdas-3a11fbd5090e)
* Part 8: Orientation Change (Parcelables & Data Classes)
* Part 9: Unit & Integration Tests

### Part 7: Infinite Scroll: Higher-Order functions & Lambdas

Thanks to all the previous stories we have a small Reddit client App that **shows the Top news from Reddit** and what we need now is to allow the user to see not only the first 10 news but the next news that also belongs to this Top Reddit news. That’s why we introduce here the Infinite Scroll.

The implementation of the infinite scroll was inspired by [this article](http://msobhy.me/2015/09/05/infinite_scrolling_recyclerview/) and the author is [Mohamed Sobhy](https://twitter.com/@mSobhy90). I made just a few changes to provide an example of Higher-Order functions and passing a lambda expression as parameter.

### Higher-Order Functions

> A higher-order function is a function that takes functions as parameters, or returns a function.

Well, lets see how Kotlin allows you to pass functions as parameter or return it with some examples.

This function “**logExecution**” allows you to pass a function as parameter and log before and after the execution of this function.

```kotlin
fun logExecution(func: () -> Unit) {
    Log.d("tag", "before executing func")
    func()
    Log.d("tag", "after executing func")
}
```

#### func: ()-> Unit

As you already know, “**func**” is the name of the parameter and “**() -> Unit**” is the “type” of the parameter, in this case, we are saying that **func** will be a function that **doesn’t receive any parameter and doesn’t return any value** (remember that Unit works like void in Java).

You can call this function by passing a lambda expression that must not receive or return any value, like in this way:

```kotlin
logExecution( { Log.d("tag", "I'm a function") } )
```

but also Kotlin allows you to remove the parenthesis if there is only one function parameter or if the last parameter is a function:

```kotlin
logExecution { Log.d("tag", "I'm a function") }
```

If we change the **logExecution** signature to receive another parameter and we put the function parameter at the end, we can do this:

```kotlin
// added tag parameter:
fun logExecution(tag: String, func: () -> Unit) { ... }

// call in this way:
logExecution("tag") { Log.d("tag", "I'm a function") }
```

or:

```kotlin
logExecution("tag") {
    Log.d("tag", "I'm a function")
}
```

Also you can make the function to receive and return values:

```kotlin
fun logExecution(func: (String, String) -> Int) {
    val thisIsAnInt = func("Hello", "World")
}
```

As you can see Kotlin gives you a lot of power with Higher-Order functions. Also this allows you to remove a lot of Android ceremonies that requires you to initialize things before to execute something or never forget to close a cursor, and much more.

#### Async function example

Lets see a practical example. We are going to create a new function that receives a function and execute it in another thread:

```kotlin
fun runAsync(func: () -> Unit) {
    Handler().post(Runnable { func() })
}
```

Now we can execute a function outside of the Main UI Thread easily:

```kotlin
runAsync {
    // i.e.: save something in the Database
}
```

#### Is Lollipop example

Maybe you want to run some specific code for Lollipop devices and instead of doing the regular if check, you can use this function:

```kotlin
fun isLollipopOrAbove(func: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        func()
    }
}
```

and use it in this way:

```kotlin
isLollipopOrAbove {
    // run lollipop specific code safely
}
```

### Infinite Scroll

Ok lets see how we can take advantages of this new concept and create our infinite scroll behaviour.

The RecyclerView allows you to set a Scroll Listener so I created this [InfiniteScrollListener](https://github.com/juanchosaravia/KedditBySteps/blob/master/app/src/main/java/com/droidcba/kedditbysteps/commons/InfiniteScrollListener.kt) and I defined the signature of this class in this way:

```kotlin
class InfiniteScrollListener(
        val func: () -> Unit,
        val layoutManager: LinearLayoutManager)
                 : RecyclerView.OnScrollListener() {...}
```

What we are doing here is to receive a function parameter that will be invoked every time we get to the end of the RecyclerView ([see line 36 for more details](https://github.com/juanchosaravia/KedditBySteps/blob/master/app/src/main/java/com/droidcba/kedditbysteps/commons/InfiniteScrollListener.kt#L36)). In fact, it will be called before to reach the end of the list, you can set a threshold that will make our function to be invoked a few items before and in this way you provide a better experience and not making the user to see all the time the loader that more news were requested.

The function that we are going to pass as parameter is a function that request more news and update the NewsAdapter with the new received news:

```kotlin
news_list.addOnScrollListener(
   InfiniteScrollListener({ requestNews() }, linearLayout)
)
```

**requestNews()** is a function that we already have in our code, I update it to use pagination and request the next available news from Reddit.

As you may notice, I’m not passing requestNews() **but inside a lambda expression**. This is because the compiler will not take it as a function parameter but as a function that needs to be executed and use the return type of this function as the value that we are passing to the InfiniteScrollListener. As the return type of requestNews is “Unit”, it doesn’t match the InfiniteScrollListener parameter expected which is “()->Unit”, so this code will not compile except if you put this into the lambda expression that match perfectly the required parameter.

#### Commit:

Here you have all the code added to include Infinite Scroll behaviour:

[https://github.com/juanchosaravia/KedditBySteps/commit/3bedf81ad25aecf24aacc224c6591072eccf5b73](https://github.com/juanchosaravia/KedditBySteps/commit/3bedf81ad25aecf24aacc224c6591072eccf5b73)

### Conclusion

We are now able to scroll and see more news from Reddit Top list. All the details can be reviewed in the previous commit.

Higher-Order functions is an incredible feature and hope it is more clear now with these examples. I invite you to post other examples if you feel inspired.

See you in the next story!

**Twitter**: [https://twitter.com/juanchosaravia](https://twitter.com/juanchosaravia)
