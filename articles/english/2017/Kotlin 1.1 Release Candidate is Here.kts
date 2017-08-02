

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
As of today, Kotlin 1.1 has finally reached the release candidate stage. This means that most of our development work is done, we’re happy with the results, and we’ll soon publish them as a final Kotlin 1.1 release. We’ve done a lot of testing for this release internally, but the real world is always more varied than any test environment, so we need your help. Please try this build, and let us know about your experience!

![11RC-01][]


The only new feature in the release candidate is the `takeUnless` function – a counterpart of [takeIf][] (added earlier in 1.1) but with an inverted condition. As for bugfixes, there’s much more, and the [changelog][] gives you a complete list. Among other things, we’ve fixed several performance problems in the IDE – both long-standing sore points and recent regressions.

## Migration Notes ##

As we [noted][] earlier, all binaries produced by pre-release versions are outlawed by the compiler: you’re now **required to recompile** everything that was compiled by 1.1‑M0x and Beta’s. All the code from 1.0.x is, of course, perfectly fine without recompilation.

Up until now, you could run the Kotlin compiler under any version of Java starting with Java 6, but this is about to change – starting with one of the first 1.1.x updates, the compiler will only run under Java 8 or 9. To prepare you for the migration, the compiler now emits a warning if you run it under Java 6 or 7. Note that this only affects the build environment; **the compiled code is still compatible with Java 6 by default**, and we have no plans to remove the support for that.

The `.javaClass` extension property is now deprecated. As a replacement, please use `::class.java`. The IDE offers a quickfix to update usages, both individually and across the entire project.

To reduce the size of the JavaScript standard library, we’ve deprecated a lot of helper functions in the `kotlin.dom` and `kotlin.dom.build` packages, and we’re going to remove them in a future update.

## How to try it ##

**In Maven/Gradle:** Add `http://dl.bintray.com/kotlin/kotlin-eap-1.1` as a repository for the build script and your projects; use `1.1.0-rc-91` as the version number for the compiler and the standard library.

**In IntelliJ IDEA:** Go to *Tools → Kotlin → Configure Kotlin Plugin Updates*, then select “Early Access Preview 1.1” in the *Update channel* drop-down list, then press *Check for updates*.

**In Eclipse**: install the plugin with the following update site
`https://dl.bintray.com/jetbrains/kotlin/eclipse-plugin/0.8.0`

**The command-line compiler** can be downloaded from the [Github release page][].

**On [try.kotlinlang.org][]**.

Let’s Kotlin!


[11RC-01]: https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/02/11RC-01.png
[takeIf]: https://kotlinlang.org/docs/reference/whatsnew11.html#takeif-and-also
[changelog]: https://github.com/JetBrains/kotlin/blob/1.1-rc/ChangeLog.md
[noted]: https://blog.jetbrains.com/kotlin/2017/01/kotlin-1-1-beta-is-here/
[Github release page]: https://github.com/JetBrains/kotlin/releases/tag/v1.1-rc
[try.kotlinlang.org]: http://try.kotlinlang.org/
"""

Article(
  title = "Kotlin 1.1 Release Candidate is Here",
  url = "https://blog.jetbrains.com/kotlin/2017/02/kotlin-1-1-release-candidate-is-here/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mikhail Glukhikh",
  date = LocalDate.of(2017, 2, 17),
  body = body
)
