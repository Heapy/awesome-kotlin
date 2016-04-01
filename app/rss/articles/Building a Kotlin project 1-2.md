---
title: 'Building a Kotlin project 1/2'
url: http://cirorizzo.net/2016/03/04/building-a-kotlin-project/
categories:
    - Android
    - Kotlin
author: Ciro Rizzo
date: Mar 04, 2016 21:19
---
### Part 1
The best way to learn a new language is to use it in a real use case.
That's way this new series of posts are focused on building a proper Android project using Kotlin.

## Scenario
To cover as many scenarios as possible the project will require:

* Accessing to the network
* Retrieving data through out a REST API call
* Deserialize data
* Showing Images in a list

For this purpose why not have an app showing kittens? ;)
Using the [http://thecatapi.com/](http://thecatapi.com/) API we can retrieve several funny cat images

![Example](http://cirorizzo.net/content/images/2016/03/xkittenApp.png.pagespeed.ic.ulo4yWl6Cg.png)

## Dependencies
It looks like a very good chance to try out some very cool libraries like

* [Retrofit2](http://square.github.io/retrofit/) for the network access, REST API calls and deserializing data
* [Glide](https://github.com/bumptech/glide) for showing images
* [RxJava](https://github.com/ReactiveX/RxJava) to bind data
* [RecyclerView CardView](http://developer.android.com/training/material/lists-cards.html) as UI
* Everything wrapped in a [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern

## Set Up the Project

Using [Android Studio](http://developer.android.com/sdk/index.html) is extremely simple create a new project from scratch

### Start a new Android Project

![Start a new Android Project](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_NewProject.png.pagespeed.ic.7fDR0qSTJd.png)

### Create a new project

![Create a new project](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_NewProject_Create_NEW-1.png.pagespeed.ic.rtJ-FIVYiG.png)

### Select Target Android Device

![Select Target Android Device](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_NewProject_Target.png.pagespeed.ic.bXlb6fWH62.png)

### Add an activity

![Add an activity](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_NewProject_Empty.png.pagespeed.ic.VYxIdhZ3Xk.png)

### Customize the Activity

![Customize the Activity](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_NewProject_Activity.png.pagespeed.ic.3g2X5Gs9Bn.png)

Press on Finish, the new project from the chosen template will be created.

![Done](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_Basic_Template.png.pagespeed.ic.3iX8nv51PP.png)


This is the starting point of our Kitten App!
However the code is still in Java, later we’re going to see how to convert it.

## Defining Gradle Build Tool

The next step is to adjust the Build Tool and defining which libraries we're going to use for the project.

> Before starting with this phase, have a look at what you need for an Android Kotlin project on this [post](http://www.cirorizzo.net/kotlin-code/)

Open the Module App `build.gradle` (highlighted with a red rectangle in the picture)

![Gradle](http://cirorizzo.net/content/images/2016/03/xAndroidStudio_Basic_Gradle_High.png.pagespeed.ic.0SHrJn4YZc.png)

It's a very good practice collecting all the libraries' version and Android properties in separate scripts and accessing to them through the `ext` property object provided by Gradle.

The easiest way is to add at the beginning of the `build.gradle` file the following snippet

```groovy
buildscript {
  ext.compileSdkVersion_ver = 23
  ext.buildToolsVersion_ver = '23.0.2'

  ext.minSdkVersion_ver = 21
  ext.targetSdkVersion_ver = 23
  ext.versionCode_ver = 1
  ext.versionName_ver = '1.0'

  ext.support_ver = '23.1.1'

  ext.kotlin_ver = '1.0.0'
  ext.anko_ver = '0.8.2'

  ext.glide_ver = '3.7.0'
  ext.retrofit_ver = '2.0.0-beta4'
  ext.rxjava_ver = '1.1.1'
  ext.rxandroid_ver = '1.1.0'

  ext.junit_ver = '4.12'

  repositories {
      mavenCentral()
  }

  dependencies {
      classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_ver"
  }
}
```

Then adding the Kotlin plugins as shown

```groovy
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
```

Before adding the dependencies for the libraries we're going to use in the project starting to change all the version numbers of the script with the `ext` properties added early at the beginning of the file

```groovy
android {
  compileSdkVersion "$compileSdkVersion_ver".toInteger()
  buildToolsVersion "$buildToolsVersion_ver"

  defaultConfig {
    applicationId "com.github.cirorizzo.kshows"
    minSdkVersion "$minSdkVersion_ver".toInteger()
    targetSdkVersion "$targetSdkVersion_ver".toInteger()
    versionCode "$versionCode_ver".toInteger()
    versionName "$versionName_ver"
}
...
```

One more change to the `builTypes` section

```groovy
buildTypes {
    debug {
        buildConfigField("int", "MAX_IMAGES_PER_REQUEST", "10")
        debuggable true
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }

    release {
        buildConfigField("int", "MAX_IMAGES_PER_REQUEST", "500")
        debuggable false
        minifyEnabled true
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}
sourceSets {
    main.java.srcDirs += 'src/main/kotlin'
}
```

Next step is to declare the Libraries used in the project

```groovy
dependencies {
  compile fileTree(dir: 'libs', include: ['*.jar'])
  testCompile "junit:junit:$junit_ver"

  compile "com.android.support:appcompat-v7:$support_ver"
  compile "com.android.support:cardview-v7:$support_ver"
  compile "com.android.support:recyclerview-v7:$support_ver"
  compile "com.github.bumptech.glide:glide:$glide_ver"

  compile "com.squareup.retrofit2:retrofit:$retrofit_ver"
  compile ("com.squareup.retrofit2:converter-simplexml:$retrofit_ver") {
    exclude module: 'xpp3'
    exclude group: 'stax'
}

  compile "io.reactivex:rxjava:$rxjava_ver"
  compile "io.reactivex:rxandroid:$rxandroid_ver"
  compile "com.squareup.retrofit2:adapter-rxjava:$retrofit_ver"

  compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_ver"
  compile "org.jetbrains.anko:anko-common:$anko_ver"
}
```

Finally the `build.gradle` is ready to work with the project.

Just one more thing is to add the `uses-permission` to access to Internet, so add the following line to the `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

And now we are ready to go to the next step

## Designing Project Structure

Another good practice is to structure the project having different packages and folders for different group of Classes composing our project so we can structure our project as shown below.

![Structure](data:image/png;base64,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)

> Right Click on the Root Package `com.github.cirorizzo.kshows` and then `New -> Package`

## Coding
The next [post](http://www.cirorizzo.net/2016/03/04/building-a-kotlin-project-2/) is on how to code the elements of the Kitten app

## Translations
This post is also available in Chinese on [gold.xitu.io](https://goo.gl/tBIMn7)
A special thanks for the translation to the gold.xitu.io Team



