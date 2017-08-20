
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We’re happy to announce the release of Kotlin 1.0.2, the second bugfix and tooling update for Kotlin. In addition to compiler and language bugfixes, Kotlin 1.0.2 adds a number of major features to the IDE and the build tools.

### Incremental Compilation in Gradle and the IDE

The new release continues our work on Kotlin incremental compilation support, speeding up the turnaround time during development. Incremental compilation in the IDE (which was previously marked as experimental) is now enabled by default. Also, the long-awaited support for **incremental compilation in Gradle builds** is now there.

To enable incremental compilation for Gradle, you need to set the **kotlin.incremental** property to true (for example, by adding the line `kotlin.incremental=true` to the `gradle.properties` file in the root directory of your project).

### Android Lint Checks

Kotlin 1.0.2 introduces support for **Android Lint checks** for Kotlin code, ensuring that issues like using the API not available in the Android version you’re targeting are correctly detected.

The set of checks supported in Kotlin 1.0.2 corresponds to the checks supported in Android Studio 1.5; checks added or improved in Android Studio 2.0 will be supported in the next release of Kotlin. Also, the current version runs Lint checks for Kotlin code only inside the IDE (as part of on-the-fly code inspections, or in batch mode through Analyze | Inspect Code). Running checks from the command line will be supported in the next release.

### Compact Standard Library

One other improvement relevant for Android developers is that the size of the standard library has been reduced by ~1500 methods (from approximately 6600 to 5100). Even before this change, the library was [smaller than those of Kotlin’s main competitors](https://github.com/SidneyXu/AndroidDemoIn4Languages), and now the situation is even better. Of course, the library is still fully binary compatible.

### Java 7/8 Support Libraries

As a temporary workaround for better Java 7/8 support before full support is introduced in Kotlin 1.1, we’re now providing support libraries that expose the APIs added in Java 7 and 8 (such as the Stream API) as extension functions on Kotlin standard library classes. See the [forum post](https://discuss.kotlinlang.org/t/jdk7-8-features-in-kotlin-1-0/1625) for instructions on using the libraries.

### IntelliJ IDEA Plugin features

The IntelliJ IDEA plugin has gained a number of major new features:

* For users of Android Studio, there’s now a possibility to create a **new activity** in Kotlin;
* For users of IntelliJ IDEA Ultimate, there is now initial support for the **Spring Framework**, including inspections, line markers, SpEL language injection support, actions to generate dependencies, and more;
* A bunch of **inspections and quickfixes** have been added, such as an inspection for highlighting `var`s that can be `val`;
* Improvements to Gradle integration, debugger, formatter, refactorings and other areas of the plugin.

### JavaScript support

We’ve resumed work on our JavaScript backend, and the version 1.0.2 fills in most of the remaining gaps in the language feature support when targeting JavaScript. Newly supported features include nested classes, local classes, non-local returns in local lambdas, unsafe casts and more.

### Maven Archetype

We’re now providing a Maven archetype to easily create Kotlin projects. Use “New project | Maven | Create from Archetype...” in IntelliJ IDEA, or the following command line:

```kotlin
mvn archetype:generate -Dfilter=org.jetbrains.kotlin:
```

### Dokka 0.9.8

Together with Kotlin 1.0.2, we’re releasing a new version of [Dokka](https://github.com/kotlin/dokka), the Kotlin documentation generation tool. If you’re using Dokka in your project, you need to upgrade Dokka together with Kotlin, because older Dokka versions are incompatible with Kotlin 1.0.2. New features in Dokka 0.9.8 include:

* Android Gradle plugin, for generating documentation for Android libraries and applications;
* Support for generating a javadoc jar file in the Maven plugin.

### Conclusion

You can see the full list of bugfixes and changes to the compiler, standard library and the tools in the [changelog](https://github.com/JetBrains/kotlin/blob/1.0.2/ChangeLog.md).

While working on the release, we received a lot of valuable feedback from the users of the [Early Access Preview builds](https://discuss.kotlinlang.org/t/kotlin-1-0-2-eap/1581). We’re really grateful to everyone who has provided feedback, and we welcome you to join the EAP program for future updates.

As usual, if you run into any problems with the new release, you’re welcome to ask for help on the [forums](https://discuss.kotlinlang.org/), on Slack (get an invite [here](http://kotlinslackin.herokuapp.com/)), or to report issues in the [issue tracker](https://youtrack.jetbrains.com/issues/KT).

"""

Article(
  title = "Kotlin 1.0.2 is Here",
  url = "http://blog.jetbrains.com/kotlin/2016/05/kotlin-1-0-2-is-here/",
  categories = listOf(
    "Kotlin",
    "Release"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2016, 5, 13),
  body = body
)
