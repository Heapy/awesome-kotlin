
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
###### _Part 2_

As mentioned in the previous [post](http://www.cirorizzo.net/2016/01/23/kotlin-at-glance/), Kotlin representing a better way to program on the Android platform and the better companion for it is [Android Studio](http://developer.android.com/sdk/index.html).
If you're one of the kind still struggling to start to use this Official Android IDE, well this is good chance to give it a try.

#### Setup Android Studio

Android Studio comes from [JetBrains](https://www.jetbrains.com/), the same of Kotlin; so no worries about strange configurations or weird plugins: Android Studio has already all what you need to start coding Kotlin.

> The IDE's got another magic feature who convert Java code to Kotlin from Menu **_Code->Convert Java file to Kotlin file_**

Another cool thing is you can have a project mixing Java and Kotlin at same time across different files (of course you cannot mix Java and Kotlin in the same file/class!).

Let's start having a look what is needed to start or adding some Kotlin classes to an Android Project.

As mentioned early, you don't need to install anything else to have Kotlin on Android Studio, but you need to specify in the project build tool (Module `build.gradle` file) to include all we need.

At the beginning of the file, just after the standard Android plugin adding the Kotlin one

```groovy
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
```

For our purpose one more plugin to use the Kotlin Android Extension (not mandatory)

```groovy
apply plugin: 'kotlin-android-extensions'
```

And in the `buildscript` section of the same file, adding the Kotlin libraries. For the latest version check the [official page](https://kotlinlang.org/docs/reference/using-gradle.html) to have the latest update

```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:<current version>"
    }
}
```

It's my intention to talk on this Kotlin posts series about the basic of the language and how the standard library _Kotlin Android Extension_ (the second library included above) can help and improve exponentially the Kotlin skill programming.

> There's another powerful library called [Anko](https://github.com/Kotlin/anko), a type-safe DSL (...and more) who can help a lot as well, we're going to go deeper later on

And one more dependency to include at `dependencies` section

```groovy
dependencies {
   compile 'org.jetbrains.kotlin:kotlin-stdlib:<current version>'
}
```

If you're gonna to have your project entirely in Kotlin, your project setup is finished, but if you want to mix Java as well, or basically you want to start adding new Kotlin classes to your existing project is strongly suggested to define two different folders, one for Java and one for Kotlin

```
project
   - src
      - main (root)
         - java
         - kotlin
```


and in that case adding in the `build.gradle` the following snap

```groovy
sourceSets {
    main.java.srcDirs += 'src/main/java'
    main.java.srcDirs += 'src/main/kotlin'
}
```

Well done! Now you're ready to start coding...

#### Starting Kotlin Project

Starting a project the first class we're thinking of is an Activity class ;) so let's start from it

> At the moment is not possible starting a Kotlin project by the scratch in Android Studio using wizard template

Let's create an Android project using the template `Empty Activity` and after it we'll have a Java class (...erm, what?)

```java
public class MainActivity extends AppCompatActivity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }
}
```

well let's using the JetBrains magic through the auto converter feature selecting the menu `Code->Convert Java file to Kotlin file` and...

```java
class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
   }
}
```

As you can notice there are a bunch of smart things like:

* no more semicolon `;` (it's optional, so if you still struggling to get rid of it, Kotlin is tolerant! ;)
* The _scope_ of the class or method if `public` is omitted (i.e. `class MainActivity`)
* get rid `extends` keyword for inheritance and just colon `:` instead
* inherited class used with brackets `()` means to be initialized with the primary constructor (i.e. `AppCompatActivity()`)
* methods are function and the keyword is just `fun`
* function returning `void` just not needs any additional keyword
* the type of a member, param, etc is placed after it and followed by a colon `:`
* and at last but not least the _Null Safety_ concept; in Kotlin every member, variable comes from a type, is not nullable by default, if you need (permit!) to use a nullable one, has to be declared using a special keyword at the end of the type `?` (i.e. `savedInstanceState: Bundle?`)

So far easy, isn't it?
Well, will be even more!

#### Koltin Android Extensions

The plugin included early at the beginning of Gradle file

```groovy
apply plugin: 'kotlin-android-extensions'
```


is a powerful tool who helps to avoid a lots of boilerplate for binding views in our class and preventing one of the biggest feckless function in Android: `findViewById()`.

Starting have a look to our layout `activity_main.xml`, for sure it'll have the standard `TextView`

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!"
    android:id="@+id/textVw"/>
```

well, usually in Java we need to declare the View

```java
private TextView textVw;
```


and then binding it with instance of the object defined in the layout through this

```java
textVw = (TextView) findViewById(R.id.textVw);
```


and starting to use it

```java
textVw.setText("Hello, How're You?");
```

Boring, isn't it?

All this code in Kotlin is now **useless**, the only thing to do is just starting to use the view

```kotlin
textVw.text = "Hello, How're You?"
```

That's it!

Koltin Android Extensions thinks all the rest...
You'll notice about the `import` added by the IDE in the class

```
import kotlinx.android.synthetic.main.activity_main.*
```

This is used to have the binding with our `TextView`

What do you think about? Still skeptical?
Well, I know you're a developer (_<del>stubborn</del> skeptical people_) ;)
Let's go further on this thing improving our project step by step later on...

See you soon on the next post!

"""

Article(
  title = "Kotlin Coding",
  url = "http://cirorizzo.net/2016/02/03/kotlin-code/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ciro Rizzo",
  date = LocalDate.of(2016, 2, 3),
  body = body
)
