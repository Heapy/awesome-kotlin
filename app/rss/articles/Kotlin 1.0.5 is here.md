---
title: 'Kotlin 1.0.5 is here'
url: https://blog.jetbrains.com/kotlin/2016/11/kotlin-1-0-5-is-here/
categories:
    - Kotlin
author: Dmitry Jemerov
date: Nov 8, 2016 10:55
---
We’re happy to announce that we’ve just released **Kotlin 1.0.5**, which continues the series of bugfix and tooling updates for Kotlin 1.0.

We’d like to thank our external contributors whose pull requests were included in this release: [Kirill Rakhman](https://github.com/cypressious), [Vladislav Golub](https://github.com/ensirius), [Vsevolod Tolstopyatov](https://github.com/qwwdfsad), [Yoshinori Isogai](https://github.com/shiraji), [takahirom](https://github.com/takahirom) and [gitreelike](https://github.com/gitreelike). Thanks to everyone who tried the EAP builds and sent us feedback, too!

The complete list of changes in the release can be found in the [changelog](https://github.com/JetBrains/kotlin/blob/1.0.5/ChangeLog.md). Some of the changes worth highlighting are:

### Loop to Lambda Conversion

The IntelliJ IDEA plugin can now detect many cases where imperative `for` loops can be rewritten in a more compact and idiomatic manner using standard library functions such as `filter` and `map`. As a simple example, the following snippet:

```kotlin
val result = arrayListOf<String>()
for (s in list) {
    if (s.isNotEmpty()) {
        result.add(s)
    }
}
```

...will be automatically converted to:

```kotlin
val result = list.filter { it.isNotEmpty() }
```

To trigger the conversion, put the caret on the `for` keyword and press <kbd>Alt-Enter</kbd>.  

### Postfix Code Completion

IntelliJ IDEA’s [postfix code completion](https://blog.jetbrains.com/idea/2014/03/postfix-completion/) is now supported for Kotlin, with a large array of templates. Note that the feature depends on platform changes made in IntelliJ IDEA 2016.2 and is therefore unavailable in Android Studio 2.2; it will be supported in newer versions of Android Studio based on newer IntelliJ Platform versions.

[![1-0-5-postfixcompletion](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-postfixCompletion.png?zoom=1.5&resize=640%2C465&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-postfixCompletion.png?ssl=1)

### New Refactorings

The Kotlin plugin now supports “Extract Interface” and “Extract Superclass” refactorings, which were previously only supported only for Java and some other languages, as well as an entirely new refactoring “Introduce Type Parameter”, providing an easy way to change a class or function into a generic one.

[![1-0-5-extractinterface](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-extractInterface.png?zoom=1.5&resize=640%2C364&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-extractInterface.png?ssl=1)

### Android IDE Support Improvements

Kotlin 1.0.5 updates the Kotlin Lint checks to feature parity with Android Studio 2.2’s Java Lint checks, fixing a lot of issues in the process. It also adds a long-awaited feature: “Extract string resource” intention, allowing to move a hard-coded string literal from Kotlin code to a string resource file.

[![1-0-5-android-extract-string-resource](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-android-extract-string-resource.png?zoom=1.5&resize=640%2C188&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/11/1.0.5-android-extract-string-resource.png?ssl=1)

### JavaScript Support Improvements

Kotlin 1.0.5 adds two major new features to the JavaScript backend:

*   The `@JsName` annotation allows to control the names of JavaScript functions and properties generated from Kotlin code, making it much easier to call Kotlin-compiled code from plain JavaScript;
*   Class literals (`Foo::class`) are now supported. The value of a `::class` expression does not implement the full `KClass` API; it only defines a `simpleName` property to access the class name.

### How to update

To update the plugin, use Tools | Kotlin | Configure Kotlin Plugin Updates and press the “Check for updates now” button. Also, don’t forget to update the compiler and standard library version in your Maven and Gradle build scripts.

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums](https://discuss.kotlinlang.org/), on Slack (get an invite [here](http://kotlinslackin.herokuapp.com/)), or to report issues in the [issue tracker](https://youtrack.jetbrains.com/issues/KT).

Let’s Kotlin!
