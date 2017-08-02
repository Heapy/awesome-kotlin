
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
### Using Kotlin For Tests in Android

Many developers are quite optimistic about Kotlin future for Android. It [sounds so sweet](https://medium.com/@sergii/say-hello-to-kotlin-78d8afff14a#.423ivzn8q): less verbose, more type-safety, zero-overhead null-safety, Java interop. But not everyone is ready to take a risk and start writing production code using new programming language. Indeed, it could be not mature enough and will add dependencies which increase method count of APK for more than 6.5K. But what if we’ll use Kotlin only for tests?

#### Project setup

So we can setup Kotlin plugin and related libraries to be only in [_testCompile_ ](https://docs.gradle.org/current/userguide/artifact_dependencies_tutorial.html)dependencies scope. Here is what we should set in build.gradle:

```groovy
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:23.2.0'

    testCompile 'junit:junit:4.12'
    testCompile "org.jetbrains.kotlin:kotlin-stdlib:${"$"}kotlin_ver"
    testCompile "org.jetbrains.kotlin:kotlin-test-junit:${"$"}kotlin_ver"
}
```

Another improtant detail in build.gradle is setting proper source sets to make Kotlin tests folders visible to compiler and IDE. Also we should make sure that Kotlin won’t be used in the production code:

```groovy
android {
    //...
    sourceSets {
        test.java.srcDirs += 'src/test/kotlin'
    }
}

afterEvaluate {
    android.sourceSets.all { sourceSet ->
        if (!sourceSet.name.startsWith("test"))
        {
            sourceSet.kotlin.setSrcDirs([])
        }
    }
}
```

#### Example

Let’s create a tiny Android app which will convert temperature from Celsius to Kelvin and Fahrenheit. Here I create ThermoConverter.java utility class which is quite easy to make unittestable.

Then we can add simple unit tests for the same functionality in _java_ and _kotlin_ source folders written in Java and Kotlin respectively. Android Studio will mark both _test/java_ and _test/kotlin_ folders with the proper color to indicate them as test packages.

Kotlin example unit test code could be as simple as the follows — it will be enough for our demo purposes.

```kotlin
class ThermoConverterTestKotlin {
    private val ALLOWED_DELTA = 0.01f

    @Test
    fun thermoTest() {
        val celsiusVal = 232.778f
        val thermoModel = ThermoConverter.getTemperature(celsiusVal)
        assertEquals(celsiusVal, thermoModel.celsius, ALLOWED_DELTA)
        assertEquals(451f, thermoModel.fahrenheit, ALLOWED_DELTA)
    }
}
```

And here is the overall project structure:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*shTe7vHBNQJLQOk1WBzZaQ.png)

What do we have now? With testCompile dependencies scope we’ve defined that dependencies won’t be included to the main APK file but will be used only for compilation tests sources. We are adding Kotlin source set to the default one used by java code so we can use the same package structure and can have the same package visibility.

Here is how tests result looks in Android Studio for both Java and Kotlin tests executed together:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*P2ygs6-7S2Wwg33HZVZrFg.png)

You can note that on this screenshot the execution time of tests with the same logic in Java and Kotlin is different —the second one seems to be slower. I did several experiments and it seems that this slowdown happens only at the first time JVM loads required by Kotlin dependencies, so it won’t bring any significant performance lag at least for the small amount of code.

#### Conclusion

Now you know the one more way how to try Kotlin in your Android project without production dependencies overhead and with lower maintenance risks. You can find full project example on [GitHub](https://github.com/sergiiz/KotlinTestDemo).

Thanks to [Antonio Gutierrez](https://medium.com/u/ba532f5866b4) for the original idea about possible Kotlin use-case for testing.

_Update._ Gradle configuration improved to make sure that Kotlin won’t be used in the production code. Thanks to [Dale King](https://medium.com/u/7997a2275b27).

"""

Article(
  title = "Using Kotlin For Tests in Android",
  url = "https://medium.com/@sergii/using-kotlin-for-tests-in-android-6d4a0c818776#.vx2bsqrbm",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Sergii Zhuk",
  date = LocalDate.of(2016, 3, 16),
  body = body
)
