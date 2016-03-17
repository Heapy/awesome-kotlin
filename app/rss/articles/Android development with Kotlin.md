---
title: 'Android development with Kotlin'
url: http://inaka.net/blog/2016/01/15/android-development-with-kotlin/
categories:
    - Kotlin
    - Android
author: Fernando Ramirez
date: Jan 15, 2016 21:54
---

## What is Kotlin?

[Kotlin](https://kotlinlang.org/) is a statically typed programming language for the Java Virtual Machine, Android and web browsers. Inside Android apps we can use Kotlin language to replace Java code, but also we can use both together having Java classes and Kotlin classes in the same project.
## Getting started

Using Android Studio

### 1. Install the Kotlin plugin

Go to "Android Studio" menu, "Preferences", "Plugins", search 'Kotlin' and install the plugin. Then you will have to restart Android Studio.

### 2. Start a new Android project

Let's create a new Android project: go to "Start a new Android Studio project" or "File" menu, and click "New project".

### 3. Configure gradle

You can configure Kotlin in the project by clicking "Help" on the menu, then "Find Action" (or press ⇧⌘A) and finally enter "Configure Kotlin in Project". Then you should choose the Kotlin version that you want. After that, the app/build.gradle file should be updated. Also, instead of using "Find Action", you can edit the app/build.gradle file manually. Here is an example:


```groovy
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.2"

    defaultConfig {
        applicationId "examples.kotlin.inaka.com"
        minSdkVersion 17
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    sourceSets {
        main.java.srcDirs += 'src/main/kotlin'
    }
}

buildscript {
    ext.kotlin_version = '1.0.0-beta-4583'
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
}
```

### 4. Convert .java to Kotlin (.kt) files

Open a Java file and choose "Help" on the menu, then "Find Action" (or press ⇧⌘A) and enter the action "Convert Java File to Kotlin File". Alternatively you can call this option via "Code" menu, "Convert Java File to Kotlin File".

## Some characteristics

Using Kotlin to develop Android apps introduce many differences and 'advantages' compared to Java development. Here I will mention some of them:

### Variables and type inference

```kotlin
var age = 5 // you don't need to declare as an Int
```

But if you want to declare the type:

```kotlin
var age: Int = 5
```

Java's 'final' = Kotlin's 'val':

```kotlin
val name = "Fernando" // 'val' can't be reassigned
```

### Pattern matching

```kotlin
fun valueDependingOnType(type: Int) {
    var value = 0
    when (type) {
        1 -> toast(value.toString())
        2 -> {
            value = 100
            toast(value.toString())
        }
        else -> toast(type.toString())
    }
}
```

### Safe call - null verification

Verifying null object with Java:

```kotlin
int len;
if(list != null){
  len = list.size();
}
```

Same as above but using Kotlin language:

```kotlin
var len = list?.size
```

Safe cast example:

```kotlin
val x: String? = y as? String

```

### Properties

Avoid usage of getters and setters:

```kotlin
var user = User()
user.name = "Fernando" // set user's name
toast(user.name)       // get user's name
```

### Companion object

A Kotlin class can have a companion object. This is like the static variable for Java classes. For example:

```kotlin
class MainActivity : AppCompatActivity() {

  companion object {
      val PICK_CONTACT = 100
  }

  ...
}
```

Then you can call MainActivity.PICK_CONTACT and it will receive the value 100.

### String templates

```kotlin
fun printFirst(array: Array<String>) {
  if (array.size == 0) return
  print("First value: ${array[0]}")
}
```

### Ranges

```kotlin
if (x in 1..y)
print("x is  >= 1 and <= y")
```

### Lambda expressions

```kotlin
fun foo(numbers: ArrayList<Int>) {
   val doubled = numbers.map { elem -> elem * 2 }
   ...
}
```

or

```kotlin
fun foo(numbers: ArrayList<Int>) {
   val doubled = numbers.map { it * 2 }
   ...
}
```

### Kotlin extensions - finding views

You don't need to call the findViewById() method. For example, if you want to change programatically the text of a TextView with id "textView1" from activity_main.xml you can do:

```kotlin
import kotlinx.android.synthetic.main.activity_main.*
```

And then into onCreate method:

```kotlin
textView1.text = "example text"
```

## Resources

[Kotlin language official documentation](https://kotlinlang.org/docs/reference/)

### Some open source example projects

* [kotlillon](https://github.com/inaka/kotlillon) Compilation of simple Kotlin examples, all together in one Android app
* [restful_android_kotlin](https://github.com/ramabit/restful-android-kotlin) Restful Android app example, using Kotlin with Realm, Retrofit, Picasso, Robospice and more

### Some interesting Kotlin libraries for Android development

* [Anko](https://github.com/Kotlin/anko)
* [RxKotlin](https://github.com/ReactiveX/RxKotlin)
* [kotterknife](https://github.com/JakeWharton/kotterknife)
* [kotlin-nosql](https://github.com/cheptsov/kotlin-nosql)
