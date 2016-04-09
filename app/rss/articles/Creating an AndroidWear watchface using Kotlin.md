---
title: 'Creating an AndroidWear watchface using Kotlin'
url: https://medium.com/@robj.perez/creating-an-androidwear-watchface-using-kotlin-e5f725813fa9#.xcftwvys6
categories:
    - Android
    - Android Wear
    - Kotlin
author: Roberto Perez
date: Mar 28, 2016 09:19
---
### Creating an AndroidWear watchface using Kotlin

![](https://d262ilb51hltx0.cloudfront.net/max/600/1*78W0Fk8Ca3OGCdZiebTvSw.png)

Last week we released our [latest watch face](https://play.google.com/store/apps/details?id=com.meronapps.lcdmodularwatchface) for Android Wear, and it is a little bit special, it is the first done using something different from Java. It is entirely written in Kotlin. In this post I would like to share our experiences while building it and how doing in the language created by Jetbrains has improved the development process.

To add a little bit of context, [we are](http://meronapps.com) a Madrid based small company which, among other things, have been doing a lot of wearables applications, we have worked with Pebble, Sony smartwatch, Samsung’s Tizen, Apple Watch and, of course, Android Wear. In our Google’s OS take on watches, this is our seventh watch face, and all of them were developed using the ubiquitous Java.

If you don’t know Kotlin, it is a open sourced language designed by Jetbrains, yes, they are the ones behind the powerful IntelliJ Idea, the IDE which powers Android Studio. Kotlin is yet another language for the JVM and brings a lot of fresh air to JVM development. Since it does not uses any “strange” things like AOT compilation or bytecode generation, it is fully interoperable with Java and it works wherever Java works, so it does in Android. If you haven’t took it a look, I recommend you to do it, you can get started at [https://kotlinlang.org/](https://kotlinlang.org/)

Once we did the introductions, let’s go to the heart of the post, the learnings from our watch face development.

#### Ready for Production

During the development of the watchface, Kotlin 1.0 was officially released, but truth is said, everything worked perfectly. Developer tools are formed by several packages, and all of them worked pretty well even though they were beta versions, which became stable afterwards.

Android Studio Kotlin plugin works pretty well, it helps you with code completion, syntax highlighting, code navigation, code conversion from Java and all the candies you can expect from a mature IDE.

Kotlin’s Android gradle plugin was perfectly integrated with Android, you just click on Run, and it works on your phone, zero problems. There is just a single issue, the new feature from the Android build tools, Instant Run, doesn’t work yet. This was somehow expected, if you know how it works by patching the dex file, it is expected that the compilation process of Kotlin might has some problems with it.

Since we started using Kotlin when it was about to reach 1.0, the language itself is pretty stable, the syntax changes, if any, didn’t have any impact in our code, we didn’t have any problems the compiler and standard library either.

#### Fully interoperable with Java

One of the pillars of this language is that it is fully compatible with all the existing Java code, it means that you can use all Android libraries, and if you already have libraries and code, you can call it without any problems nor changes.

This feature is key to adopt Kotlin in your new developments, in fact, we started by using some of our libraries which we have developed for all of our projects. What it happened, is that we converted all those libraries to Kotlin and now our previous projects are using a library written using kotlin code.

#### No more _if (foo != null)_ and less NullPointerException

If you are a java developer, you probably have suffered the curse of writing _if (foo != null)_ thousand of times, and when you forget it, a _NullPointerException_ will remind you to write it.

Kotlin tries to eliminate this infamous check with some syntax sugar. If a variable can be null, you need to explicit say it, you cannot assign a null to a _String_ type, but you can do it to a _String?_. (note the question mark at the end). You cannot dereference a _?_ variable without explicitly unwrapping it either by using !! or by using a _?_ at the end. If a variable is null and you use a _?_ at the end, it won’t crash with a NPE. That means you can write _rectangle?.size?.width?_, if rectangle or size is null, nothing happens.

#### Fill your listeners using cool lambdas

When it comes to java, you know that you are going to put in use you typing skills. Chances are that you are using a modern IDE and it will save you a little bit from that pain, but all the boilerplate code is there.

If you write Applications which respond to events like an Android application or an UI app, this is particularly notorious when you need to write control _Listeners_. Kotlin does magic in this side, if you need to implement an Interface which has only one method, you can use a Kotlin lambda to do it, saving you hundreds of characters and making your code more expressive with less useless decorations. Let’s see an example to show this better

```java
// Java (129 characters of boilerplate code)

view.setOnClickListener(new OnClickListener() {
  @Override
  public void onClick(View v) {
    // Do something cool
  }
});
```

```kotlin
// Kotlin (37 characters to do exactly the same)

view.onClick { // Do something cool }
```

#### No more findViewById

This point is specifically related with Android. Kotlin has a library called Android Extensions which will save you from typing useless code.

When I started in Android Programming some years ago, I hated why I needed to write so many _findViewById()_ and hated more the typecast of that method. For non Android programmers, that method returns a generic _View_ and you probably will need to cast it to a _Button_ or a _TextView_ to do something useful with it. Among other cool things, the Android Extensions library allows you to use any view from a layout just by using its id. You only need to import the layout like it was another class and all ids will be visible in your code magically.

```java
// Java

TextView tv = ((TextView)findViewById(R.id.my_textview)).setText(“Java y u make me write so much!”)
```

```kotlin
// Kotlin

import kotlinx.android.synthetic.main_layout.*
my_textview.text = "isn’t kotlin cool?"
```

#### Syntax sugar

Kotlin has a lot more to offer in terms of making your code more readable and allowing you to type less. Java getters and setters are automatically converted to properties, instead of write _person.getName()_ or _setName(“”)_, Kotlin allows you call that java code by writing _person.name_ or _person.name = “”._

Like modern languages, you don’t need to write the type of a variable when the compiler can infer the type, which can be done most of the times.

Assignments can contain a conditional expresions, Kotlin has mutable and inmutable references, I usually feel safer when a variable is inmutable, sometimes, the value of a variable depends on some condition, and this could force you to make the variable mutable. I think this is better explained by this piece of code.

```java
// Java

String greeting = null;
if (isDaytime()) {
  greeting = “Good Morning”;
} else {
  greeting = “good Evening”;
}
```

Kotlin allows you to write it in this way:

```kotlin
val greeting = if (isDaytime()) {
  “Good Morning”
} else {
  “Good Evening”
}
```

Using this code, greeting is inmutable and you don’t need to check if it is null when using this variable.

#### Vibrant community

One of the important things when using a new technology is the how its users communicate themselves. Kotlin community is great, you only need to connect to its slack channels and you will be able to talk with the developers of the language. Moreover, an increasing number of projects are starting to be developed using Kotlin, so you can share your experiences with others and learn by talking with them.

#### Conclusions

I could be talking about the features of Kotlin which we used while developing the watch face for hours, things like the functional features of Kotlin, the pattern matching of “switch” clauses, the bugs that optional types and immutability has saved us time of debugging and so on. In our experience choosing of Kotlin has been a great decision, it made our code more readable and one of the most important outcomes, we enjoyed a lot while developing the application which at the end is all that matters, isn’t it? :).
