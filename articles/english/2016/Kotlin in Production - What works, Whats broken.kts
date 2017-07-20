
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
We have been using Kotlin in the [DripStat](https://dripstat.com) backend since Kotlin's 1.0 release using the Kotlin Intellij plugin. Here is a summary of our experiences as of Kotlin 1.0.3.

### The Good

At the language level, Kotlin seems to be excellent.

**1 - Seamless Java interop**

Kotlin nails the Java interop at the language level. It is completely seamless. This was the reason why we were comfortable introducing Kotlin in our codebase in the first place.

**2 - Less verbosity**

Kotlin code is much less verbose than Java code. This makes it more pleasing to both read and write.

**3 - Null checks**

Kotlin enforces null checks at the language level. When you interact with Java code, it even extends that to the runtime level. This has resulted in catching some bugs pretty early and also more robust code.

### What can be improved

**1 - Lack of `parallelStream()`**

Kotlin's collection api have no equivalent to Java's `parallelStream()`. This is dearly missed.

**2 - Cannot subclass Data classes**

This is something that we feel the need for more and more as our codebase grows. It seems to be planned for Kotlin 1.1 but doesnt exist as of today.

**3 - Type inference on method return values**

While type inference everywhere else results in more concise code, on method return values, it [results in an actual loss of information](http://blog.dripstat.com/type-inference-in-java-jep-286-can-be-disastrous/). You cannot tell the type of the variable unless you look at the called method's signature, and the IDE plugin currently doesn't show the type. While Kotlin does allow specifying the variable type, it results in much more verbosity than Java.

### Whats Broken

We have found that the Intellij plugin for Kotlin is extremely buggy and very far behind Java.

**1 - Editor crashes**

This is such a huge issue since 1.0.3 that we cannot write any more Kotlin code. The editor frequently stops doing syntax highlighting, code completion etc. The only remedy when this happens is to restart the entire IDE.

[KT-13199](https://youtrack.jetbrains.com/issue/KT-13199)

**2 - Inaccurate Call Hierarchy view**

The Call Hierarchy view frequently doesn't show all calls to a method if that method is used across both Java and Kotlin. This is an extremely serious bug. Entire technical decisions can be based on whether a piece of code is used in a certain location. The fact that the Call Hierarchy view shows incomplete information has a huge impact.

[KT-12398](https://youtrack.jetbrains.com/issue/KT-12398)

**3 - Flaky gradle integration**

The Kotlin Gradle integration has been pretty flaky in the past. It almost feels like every new release of either Gradle or Kotlin breaks it. We are almost afraid of upgrading either due to this.

[KT-13179](https://youtrack.jetbrains.com/issue/KT-13179) [KT-12771](https://youtrack.jetbrains.com/issue/KT-12771)

**4 - Cant see inferred variable type**

For a language that uses type inference so heavily, you would expect the IDE to have top-notch support for showing the type of variables. However, this is broken in the Kotlin Intellij plugin.

It is also surprising that Intellij forces you to press a keyboard shortcut each time to bring this up, instead of showing the variable type constantly in a fixed location like the status bar.

[KT-10095](https://youtrack.jetbrains.com/issue/KT-10095)

**5 - Refactorings**

While it does have very basic move class, rename class, rename method refactorings, the vast majority of refactorings from Java are simply not present. Even the rename refactoring is extremely limited.

[KT-13082](https://youtrack.jetbrains.com/issue/KT-13082) [KT-2615](https://youtrack.jetbrains.com/issue/KT-2615)

**6 - No Postfix completion, Duplicate Detection**

Postfix completion and Duplicate detection come to mind as some of the many features we have come to rely on and frequently use in the Java editor, that are simply not present.

[KT-4710](https://youtrack.jetbrains.com/issue/KT-4710)

### Conclusion

Kotlin seemed to promise a better Java, without compromises. It does achieve that at the language level. However, a big part of using Java is Jetbrains' own excellent Java tooling. Here the Kotlin plugin has a lot of work to do to catch up with what Jetbrains has built over 15 years for Java.

"""

Article(
  title = "Kotlin in Production - What works, Whats broken",
  url = "http://blog.dripstat.com/kotlin-in-production-the-good-the-bad-and-the-ugly-2/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "DripStat",
  date = LocalDate.of(2016, 9, 24),
  body = body
)
