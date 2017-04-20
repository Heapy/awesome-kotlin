
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

## Introduction
[KillerTask](https://github.com/inaka/KillerTask) is an Android library to create asynchronous background tasks.

Developed in [Kotlin](https://kotlinlang.org/) and inspired by [TinyTask](https://github.com/inaka/TinyTask), this is more beautiful and easy to use for Kotlin Android developments.

### Wait... Kotlin?

To learn what Kotlin is, how to use it in Android and their characteristics, you can take a look at: ["Android development with Kotlin"](http://inaka.net/blog/2016/01/15/android-development-with-kotlin/).

### So, is this the same as TinyTask? :/

Well, both are just wrappers around AsyncTask, with a funny looking API. But no, is not exactly the same.

The main difference between KillerTask and TinyTask is that this library is written in Kotlin language, very different from Java. If you use Kotlin for develop your Android apps, you will find this much more clear and reliable.

In addition, KillerTask has a better management of threads compared to TinyTask's actual last version (1.0.1).

## Include it in your project

Add the following to your `app/build.gradle` file:

```groovy
repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  // ...
  compile 'com.github.inaka:killertask:v1.2'
  // ...
}
```

## How it works

Create a new KillerTask

```kotlin
var killerTask = KillerTask(doWork, onSuccess, onFailed)
```

Having for example:

```kotlin
// task function
val doWork: () -> String = {
  var connection: URLConnection? = null;

  try {
      var url = URL("https://api.github.com/gists")
      connection = url.openConnection();
  } catch (e: Exception) {
      e.printStackTrace()
  }

  var httpConn = connection as HttpURLConnection;
  httpConn.connectTimeout = 3000;
  httpConn.readTimeout = 5000;

  // implicit return
  httpConn.responseCode.toString() + " " + httpConn.responseMessage
}

// onSuccess function
val onSuccess: (String) -> Unit = {
  result: String ->
  Log.wtf("success result", result)
}

// onFailed function
val onFailed: (Exception?) -> Unit = {
  e: Exception? ->
  Log.wtf("error result", e.toString())
  e?.printStackTrace()
}
```

To execute it:

```kotlin
killerTask.go()
```

To cancel it:

```kotlin
killerTask.cancel()
```

### All together

You can write it all together, for example:

```kotlin
KillerTask(
    { "test" }, // main task returning "test"
    {result: String -> Log.wtf("result", result)}, // onSuccess actions
    {e: Exception? -> Log.wtf("result", e.toString())} // onFailed actions
  ).go()
```

### Variable parameters

Actually, the only strongly necessary parameter is the first one (the main task).

* Example without onSuccess and onFailed actions:

```kotlin
KillerTask(
    { Log.wtf("LOG", "KillerTask is awesome") }, // main task
    { Log.wtf("LOG", "Super awesome!")} // onSuccess
  ).go()
```

* Example only with on Failed actions:

```kotlin
KillerTask(
    { // main task
        Log.wtf("LOG", "KillerTask is awesome")
        "super" // implicit return
    },
    {}, // onSuccess is empty
    { e: Exception? -> Log.wtf("LOG", e.toString()) } // onFailed
  ).go()
```

### Example of an app using KillerTask

[kotlillon](https://github.com/inaka/kotlillon) Compilation of simple Kotlin examples, all together in one Android app.

## Support

For questions or general comments regarding the use of this library, please use our public [hipchat room](http://inaka.net/hipchat).

If you find any bugs or have a problem while using this library, please [open an issue](https://github.com/inaka/KillerTask/issues/new) (or send a pull request with your solution).

You can check all of our open-source projects at [inaka.github.io](http://inaka.github.io/).



"""

Article(
  title = "KillerTask, the solution to AsyncTask implementation",
  url = "http://inaka.net/blog/2016/01/25/killertask-the-solution-to-asynctask-implementation/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Fernando Ramirez",
  date = LocalDate.of(2016, 1, 25),
  body = body
)
