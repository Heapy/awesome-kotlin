
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[Kotlin](https://kotlinlang.org/) seems like the future of Android development. It is a new statically-typed programming language that runs on JVM, with a very refined syntax and enhanced features. There is a lot to love about it. Kotlin is interoperable with Java, which should reduce the risk of future incompatibility. The additional language features such as Function Extensions and High Order Function make it much more extensible and scalable. The code is concise with data classes, single expression function, infix and many more... enough said. Kotlin is just great!

![Screen_Shot_2016-08-10_at_2.10.19_pm.png](http://offers.bilue.com.au/hs-fs/hubfs/Screen_Shot_2016-08-10_at_2.10.19_pm.png?t=1470802416157&width=733&height=294)  

I started working on some real apps using Kotlin that have since been published on the playstore. I have to say it was relatively smooth sailing. Nonetheless, there were some hiccups along the way that are worth sharing.

### **1. Method Count Increase.**

Method count increase was one of the issues I was fully aware before starting with Kotlin. At the time of writing, there are an additional [7’191 methods](https://blog.jetbrains.com/kotlin/2016/03/kotlins-android-roadmap/) adding to the total method count. This would add more than 10% to the 65k methods limit. Nonetheless, I didn’t worry that much as the MultiDex support is there to help to overcome this issue.

### **2. Using Libraries that require Annotation.**

There are many cool libraries that can assist with making Android Development much more efficient. However, when switching over to Kotlin, using some of them becomes a challenge. There are two libraries which I can’t manage to use directly after switching to Kotlin, i.) [Icepick](https://github.com/frankiesardo/icepick) and ii.) [EventBus](https://github.com/greenrobot/EventBus). The main reason is that the Annotation (i.e. _@State _and_ @Subscribe_) is not picked up by the code. Fortunately, with EventBus, I managed to work around this by creating a composite class object using Java Code. Note that this doesn’t mean all libraries using Annotation would not work for Kotlin. I managed to use [Retrofit 2.0](http://square.github.io/retrofit/) and [Dagger 2.0](http://google.github.io/dagger/) (where both use Annotation extensively) in Kotlin directly.

### **3. Mocking Need Open Class/Function.**

By default a class and function is considered final for Kotlin. Mocking (using [Mockito](http://mockito.org/)) requires a non-final class. So in order to have that, we have to explicitly _open_ a class if we would like to mock it for testing. If this is not done, it would error out easily. The more tricky issue is the function. If the function is not _open_, there would be no error issue when running the test. Instead of intercepting the function, it would call the actual internal function, where the test would fail with NPE. Without knowing the function needs to be _open_, the root issue might not be easily discoverable.

### **4. Java to Kotlin Converter Limitation.**

The Kotlin Plugin for Android Studio is just great, especially allowing to auto convert from Java to Kotlin. However, the conversion might not be ideal. e.g.

is converted to

```kotlin
class SimpleClass(memberVariable: Int) {
  internal var memberVariable = 0

  init {
      this.memberVariable = memberVariable
  }
}
```

Whereby it could be as simple as

```kotlin
class SimpleClass(val memberVariable: Int) {}
```

Anyway, it’s always good to review the converted code and explore so that we don’t just have Kotlin code in Java style, without the real advantage of Kotlin.

### **5. Other Converter Issue.**

I love writing a new function from an object, and pressing _Alt-Enter_ to trigger the auto-function creation. If you are writing on the Java side of code, and call a Kotlin function (that you just intended to create), sorry you are out of luck. Android Studio will only auto create that function for you in the Kotlin code.

At times for experimental purposes we would also like to convert from Kotlin to Java, given that it was inter-operable with Java. This is not possible however the tools only allow you to convert from Java to Kotlin and not vice versa. Perhaps this is by design, and I could imagine it would be difficult for Java to handle conversion of more advanced Kotlin language features.

None of these issues are show stoppers. The advantage and fun of learning new things outweighs them in any case. The language features are richer and there is so much to explore. I haven’t really faced many issues from Kotlin’s language as yet. I’m sure I’ll uncover more issues, but I don’t expect them to “kill me”. Java is always there to the rescue :)

As with any new thing, one other challenge is finding community support. Suppose you are experimenting with new Android Features and face a road-block. If you post your question to Stackoverflow using your Kotlin code, you are unlikely to generate support.

So... you might as well be the one who supports others... which is in itself a good thing! :)

"""

Article(
  title = "Issues Faced With Kotlin During Android Development",
  url = "http://blog.bilue.com.au/issues-faced-with-kotlin-during-android-development-1",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Elisha Lye",
  date = LocalDate.of(2016, 8, 10),
  body = body
)
