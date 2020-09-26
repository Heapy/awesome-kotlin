
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
![](http://beust.com/pics/sdk-manager.png)
**The dreaded SDK Manager**

Android has always had a weird dependency mechanism. On the JVM (and therefore, Android), we have this great Maven repository system which is leveraged by several tools (Gradle and Kobalt on Android) and which allows us to add dependencies with simple additions to our build files. This is extremely powerful and it has undoubtedly been instrumental in increasing the JVM’s popularity. This system works for pretty much any type of applications and dependencies.

Except for Android libraries.

The Android support libraries (and I’m using “support” loosely here to include all such libraries and not just the one that Google calls “support”) are not available on any of the Maven repositories. They are not even available on Google’s own repository. Instead, you need to use a special tool to download them, and once you do that, they land on your local drive as a local Maven repository, which you then need to declare to Gradle so you can finally add the dependencies you need.

I suspect the reason why these libraries are not available in a straight Maven repo is that you need to accept licenses before you can use them, but regardless, this separate download management makes building Android applications more painful, especially for build servers (Travis, Jenkins) which need to be configured specifically for these builds.

The graphical tool used to download this repository, called “SDK Manager”, is also a command tool called `"android"` that can be invoked without the GUI, and inspired by [Jake Wharton’s sdk-manager-plugin](https://github.com/JakeWharton/sdk-manager-plugin), I set out to implement automatic SDK management for [Kobalt](http://beust.com/kobalt).

Once I became more comfortable with the esoteric command line syntax used by the `"android"` tool, adding it to the Kobalt Android plug-in was trivial, and as a result, Kobalt is now able to completely install a full Android SDK from scratch.

In other words, all you need in your project is a simple Android configuration in your `Build.kt` file:

```kotlin
android {
    compileSdkVersion = "23"
    buildToolsVersion = "23.0.1"
    // ...
}

dependencies {
    compile("com.android.support:appcompat-v7:aar:23.0.1")
}
```

The Kobalt Android plug-in will then automatically download everything you need to create an `apk` from this simple build file:

* If `${"$"}ANDROID_HOME` is specified, use it and make sure a valid SDK is there. If that environment variable is not specified, install the SDK in a default location (`~/.android-sdk`).
* If no Build Tools are installed, install them.
* Then go through all the Google and Android dependencies for that project and install them as needed.
* And a few other things...

A typical run on a clean machine with nothing installed will look like this:

```bash
$ ./kobaltw assemble
...
Android SDK not found at /home/travis/.android/android-sdk-linux, downloading it
Couldn't find /home/travis/.android/android-sdk-linux/build-tools/23.0.1, downloading it
Couldn't find /home/travis/.android/android-sdk-linux/platform-tools, downloading it
Couldn't find /home/travis/.android/android-sdk-linux/platforms/android-23, downloading it
Couldn't find Maven repository for extra-android-m2repository, downloading it
Couldn't find Maven repository for extra-google-m2repository, downloading it
...
          ===========================
          | Building androidFlavors |
          ===========================
------ androidFlavors:clean
------ androidFlavors:generateR
------ androidFlavors:compile
  Java compiling 4 files
------ androidFlavors:proguard
------ androidFlavors:generateDex
------ androidFlavors:signApk
Created androidFlavors/kobaltBuild/outputs/apk/androidFlavors.apk
```

Obviously, these downloads will not happen again unless you modify the dependencies in your build file.

I’m hopeful that Google will eventually make these support libraries available on a real remote Maven repository so we don’t have to jump through these hoops any more, but until then, Kobalt has you covered.

This feature is available in the latest [`kobalt-android` plug-in](https://github.com/cbeust/kobalt-android) as follows:

```kotlin
val p = plugins("com.beust:kobalt-android:0.81")
```

"""

Article(
  title = "The Kobalt diaries: Automatic Android SDK management",
  url = "http://beust.com/weblog/2016/04/09/the-kobalt-diaries-automatic-android-sdk-management/",
  categories = listOf(
    "Kotlin",
    "Kobalt"
  ),
  type = article,
  lang = EN,
  author = "Cédric Beust",
  date = LocalDate.of(2016, 4, 9),
  body = body
)
