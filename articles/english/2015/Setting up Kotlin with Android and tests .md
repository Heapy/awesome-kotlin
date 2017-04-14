---
title: 'Setting up Kotlin with Android and tests'
url: http://engineering.pivotal.io/post/setting-up-kotlin-with-android-and-tests/
categories:
    - Kotlin
    - Android
author: Laura Kogler
date: Nov 11, 2015 14:39
---

I recently heard about [Kotlin](https://kotlinlang.org/) with the release of their 1.0 Beta last week. Kotlin is a modern programming language that runs in the JVM and is interoperable with Java. Kotlin can also be used to create Android apps.

I was curious, so I decided to set up a small Android app using Kotlin and try out some different methods of testing.

## Setting up the app
Setting up a hello word Android app in Kotlin was actually quite easy!  The Kotlin plugin for IntelliJ / Android Studo has a tool for converting your Java code to Kotlin, so you can get started really quickly! I was able to follow this [tutorial](https://kotlinlang.org/docs/tutorials/kotlin-android.html) to get my Android app set up.

Two points to watch out for:

 1. Make sure you have installed the Kotlin plugins in Android Studio before you start

 1. I had to highlight the text of my java file before selecting "Convert Java File to Kotlin File" in order to get the converter to work.

## Android instrumentation tests
I started by writing a simple Android Instrumentation test in Java (you can freely mix Java files and Kotlin files in your project and it seems to work fine).  Once that was working, I converted it to Kotlin:

```kotlin
class MainActivityTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java) {
   private var mainActivity: Activity? = null

   @Throws(Exception::class)
   override fun setUp() {
       super.setUp()
       mainActivity = activity
   }

   fun test_itDisplaysHelloWorld() {
       val textView = mainActivity!!.findViewById(R.id.main_text) as TextView
       val actual = textView.text.toString()
       Assert.assertEquals("Hello World!", actual)
   }
}
```

The only trouble I had was that after I converted the file, the Android Studio test runner configuration was broken.  To fix it I had to edit my run configuration in Android Studio by navigating to `Run -> Edit Configurations -> Android Tests` and setting the instrumentation runner to `android.test.InstrumentationTestRunner`.  After that, lo and behold, the test worked fine in Kotlin!

## Robolectric tests
After my resounding success at getting the Android Instrumentation Tests to work, I decided to try my luck at Robolectric.  It turns out, Robolectric pretty much Just Worked too.  Here's a Robolectric test written in Kotlin, equivalent to the instrumentation test above:

```kotlin
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class)
class ExampleRobolectricTest {

    @Test
    fun itShouldDisplayHelloWorld() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val textView = activity.findViewById(R.id.main_text) as TextView

        assertThat(textView.text).isEqualTo("Hello World!")
    }
}
```

## Unit tests with Spek
[Spek](https://jetbrains.github.io/spek/) is a testing framwork written in Kotlin with a pretty, rspec-like syntax. I would love to be able to write my Android tests using something like Spek, so I decided to try!
### Bad news #1: Android Instrumentation tests + Spek
Spek requires your tests to inherit from a base class called `Spek`.  Android instrumentation tests require your tests to inherit from `InstrumentationTestCase`.  As far as I can tell, this is a deal-breaker for using the two systems together, for the moment.

### Bad news #2: Robolectric tests + Spek
Robolectric doesn't specifically require your tests to inherit from any particular class, which theoretically opens the door to using it together with Spek.  However, naively combining them doesn't work as expected, as the test runner cannot find any tests.  I think the reason for this is that Robolectric relies on using the RobolectricTestRunner, which extends the `BlockJUnit4ClassRunner` (which identifies tests based on annotations), while Spek runs with it's own `JUnitClassRunner`.

In the future I may look into writing a RobolectricSpek test runner, but for now, it looks like these two systems do not play nicely together.

### Finally, the good news
Although Spek does not seem to be compatible with either Robolectric or Android instrumentation tests, it actually works fine as a replacement for vanilla JUnit tests.  I think this kind of test is of dubious utility in a typical Android project, since there's usually not a lot of code that can be tested purely with JUnit, but if you have these kinds of tests, you could easily convert them to use Spek.  Here's a sample test I wrote in my hello world app:

```kotlin
import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

class ExampleUnitTest : Spek() {
    init {
        given("Two numbers") {
            val firstNumber = 3
            val secondNumber = 5
            on("adding the numbers") {
                val result = firstNumber + secondNumber
                it("should return the correct sum") {
                    assertEquals(8, result)
                }
            }
        }
    }
}
```

## Conclusion
Kotlin seems like a promising new language with a lot of features that make it nicer to use than Java.  Setting up a "Hello World" Android app was extremely simple, and getting tests to run with Robolectric or the Android instrumentation runner was no problem.  I look forward to trying it out more in the future!
