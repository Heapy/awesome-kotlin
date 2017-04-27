

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Members of our community have translated this blog post into several languages:
[![](https://i1.wp.com/emojipedia-us.s3.amazonaws.com/cache/82/1a/821aaf51852412ffcf042c2f2a61309e.png?w=25&ssl=1)](https://medium.com/@walmyrcarvalho/kotlin-1-1-suporte-a-javascript-co-rotinas-e-mais-d84b65ac2f92)
[![](https://i1.wp.com/emojipedia-us.s3.amazonaws.com/cache/51/a3/51a3fcf2d2c44c34bd3d4a90fff477f8.png?w=25&ssl=1)](http://www.weibo.com/ttarticle/p/show?id=2309404081257721035952)
[![](https://i1.wp.com/emojipedia-us.s3.amazonaws.com/cache/31/7a/317a88ee3fdec119bfbd5f69a6bf35b1.png?w=25&ssl=1)](https://medium.com/@gz_k/kotlin-1-1-javascript-coroutines-et-plus-ce2eb3d7004)
[![](https://i2.wp.com/emojipedia-us.s3.amazonaws.com/cache/a4/0d/a40df75ad96b0d143df710080c710f0f.png?w=25&ssl=1)](https://blog.jetbrains.com/jp/2017/03/01/739)
[![](https://i0.wp.com/emojipedia-us.s3.amazonaws.com/cache/f8/e1/f8e14a4d5c26dedc5b0639ca763bc03a.png?w=25&ssl=1)](http://kotlin.kr/2017/03/01/kotlin-1-dot-1.html)
[![](https://i2.wp.com/emojipedia-us.s3.amazonaws.com/cache/80/da/80da132bc3307d3d2ac0729493eb4c26.png?w=25&ssl=1)](https://habrahabr.ru/company/JetBrains/blog/323012/)
[![](https://i2.wp.com/emojipedia-us.s3.amazonaws.com/cache/77/3e/773e66b858a69ddbade6951fdb012949.png?w=25&ssl=1)](http://kotlin.es/2017/03/1.1.0/)

Today we release Kotlin 1.1. It’s a big step forward enabling the use of Kotlin in many new scenarios, and we hope that you’ll enjoy it.

![Kotlin 1.1](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/03/Kotlin11blogbanner1.png)

Our vision for Kotlin is to enable the use of a single expressive, performant, strongly typed language across all components of a modern application. Kotlin 1.1 makes two major steps towards this goal.

First, the _JavaScript target_ is no longer experimental, and supports all Kotlin language features, a large part of the standard library, as well as JavaScript interoperability. This allows you to migrate the browser frontend of your applications to Kotlin, while continuing to use modern JavaScript development frameworks such as React.

Second, we’re introducing support for _coroutines_. As a lightweight alternative to threads, coroutines enable much more scalable application backends, supporting massive workloads on a single JVM instance. In addition to that, coroutines are a very expressive tool for implementing asynchronous behavior, which is important for building responsive user interfaces on all platforms.

Below we describe these two changes further. In other news: we’ve added [type aliases](http://kotlinlang.org/docs/reference/whatsnew11.html#type-aliases), [bound callable references](http://kotlinlang.org/docs/reference/whatsnew11.html#bound-callable-references), [destructuring in lambdas](http://kotlinlang.org/docs/reference/whatsnew11.html#destructuring-in-lambdas) and more. See the details in our [What’s new](http://kotlinlang.org/docs/reference/whatsnew11.html) page (check out the runnable examples!).

## Coroutines

Coroutines in Kotlin make non-blocking asynchronous code as straightforward as plain synchronous code.

Asynchronous programming is taking over the world, and the only thing that is holding us back is that non-blocking code adds considerable complexity to our systems. Kotlin now offers means to tame this complexity by making coroutines first-class citizens in the language through the single primitive: _suspending functions_. Such a function (or lambda) represents a computation that can be suspended (without blocking any threads) and resumed later.

Technically, coroutines are light-weight means of cooperative multi-tasking (very similar to [fibers](https://en.wikipedia.org/wiki/Fiber_(computer_science))). In other words, they are just _much better threads_: almost free to start and keep around, extremely cheap to suspend (suspension is for coroutines what blocking is for threads), very easy to compose and customize.

We designed coroutines for maximum flexibility: very little is fixed in the language, and very much can be done as a library. The [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) project features libraries on top of Rx, CompletableFuture, NIO, JavaFx and Swing. Similar libraries can be written for Android and JavaScript. Even many built-in constructs available in other languages can now be expressed as Kotlin libraries. This includes generators/yield from Python, channels/select from Go and async/await from C#:

```kotlin
// runs the code in the background thread pool
fun asyncOverlay() = async(CommonPool) {
    // start two async operations
    val original = asyncLoadImage("original")
    val overlay = asyncLoadImage("overlay")
    // and then apply overlay to both results
    applyOverlay(original.await(), overlay.await())
}

// launches new coroutine in UI context
launch(UI) {
    // wait for async overlay to complete
    val image = asyncOverlay().await()
    // and then show it in UI
    showImage(image)
}
```

Read more in our [docs](http://kotlinlang.org/docs/reference/coroutines.html).

**An important note**. With all the benefits that they bring, Kotlin coroutines are a fairly new design that needs extensive battle-testing before we can be sure it’s 100% right and complete. This is why we will release it under an “experimental” [opt-in flag](http://kotlinlang.org/docs/diagnostics/experimental-coroutines.html). We do not expect the language rules to change, but APIs may require some adjustments in Kotlin 1.2.

## JavaScript support

As mentioned above, all language features in Kotlin 1.1, including coroutines, work on both JVM/Android and JavaScript. (Reflection for JavaScript is not available, but we’re looking into it.) This means that a web application can be written entirely in Kotlin, and we already have some experience doing that inside JetBrains. We will publish tutorials and other materials about this soon.

Kotlin for JavaScript has dynamic types to interoperate with “native” JavaScript code. To use well-known libraries through typed APIs, you can use the [ts2kt converter](http://github.com/kotlin/ts2kt) together with headers from [DefinitelyTyped](http://github.com/DefinitelyTyped/DefinitelyTyped).

We support both Node.js and the browser. The Kotlin Standard Library is available for use through `npm`.

Read more in our [docs](http://kotlinlang.org/docs/reference/js-overview.html).

## Tooling

Kotlin 1.1 is not a major release for Kotlin tooling: we prefer shipping tooling features that do not affect the language itself as soon as they are ready, so you’ve seen many such improvements in Kotlin 1.0.x versions. To highlight a few:

* Kotlin plugins for all the major IDEs: IntelliJ IDEA, Android Studio, Eclipse and NetBeans
* Incremental compilation in both IntelliJ IDEA and Gradle
* Compiler plugins for Spring, JPA and Mockito (making classses open and generating no-arg constructors)
* kapt for annotation processing
* Lint support for Android projects
* Numerous IDE intentions, inspections, quick fixes, refactorings and code completion improvements

We’ll keep working to make our tooling even better and deliver the updates in 1.1.x versions.

## First year of Kotlin: Adoption and Community

In short, Kotlin is growing. We’ve seen over 160’000 people using it during the last year. OSS projects on Github grew from 2.4M to 10M lines of Kotlin code (about 4x). Our Slack community has grown from 1’400 to over 5’700 people (over 4x). Numerous meet-ups and user groups are organized by the community [around the world](http://kotlinlang.org/community/talks.html). We are seeing more and more Kotlin books and online courses published.

![Kotlin GitHub Stats](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/03/GitHub-Stats-1.gif)

Kotlin is equally strong with server-side and Android developers (roughly 50/50 divide). Spring Framework 5.0 [has introduced Kotlin support](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0), so did [vert.x 3.4](http://vertx.io/blog/vert-x-3-4-0-beta1-release/). [Gradle](https://blog.gradle.org/kotlin-meets-gradle) and [TeamCity](https://blog.jetbrains.com/teamcity/2016/11/kotlin-configuration-scripts-an-introduction/) are using Kotlin for build scripts. More projects using Kotlin can be found at [kotlin.link](http://kotlin.link/).

Many well-known companies are using Kotlin: [Pinterest](https://www.youtube.com/watch?v=mDpnc45WwlI), [Coursera](https://building.coursera.org/blog/2016/03/16/becoming-bilingual-coursera/), [Netflix](https://twitter.com/robspieldenner/status/708355228832178176), [Uber](https://www.reddit.com/r/androiddev/comments/5sihp0/2017_whos_using_kotlin/ddfmkf7/), [Square](https://github.com/square/sqldelight), [Trello](https://twitter.com/danlew42/status/809065097339564032), [Basecamp](https://m.signalvnoise.com/some-of-my-favorite-kotlin-features-that-we-use-a-lot-in-basecamp-5ac9d6cea95), amongst others. [Corda](https://github.com/corda/corda), a distributed ledger developed by a consortium of well-known banks (such as Goldman Sachs, Wells Fargo, J.P. Morgan, Deutsche Bank, UBS, HSBC, BNP Paribas, Société Générale), has [over 90% Kotlin](https://www.corda.net/2017/01/10/kotlin/) in its codebase.

We are grateful to all our users, contributors and advocates in all parts of the world. Your support is very important to us!

### Organize your own Kotlin 1.1 Event

Kotlin 1.1 is a good reason to meet up with your local user group and friends. We have prepared some materials to help you organize such an event. On March 23 we’ll stream live sessions featuring the Kotlin team members, plus there’s an organizers pack that includes some swag and a Future Features Survey. Get more info and [register your event here](https://docs.google.com/forms/d/e/1FAIpQLSf6iXcrIpaNIqeeUJI2L6pntS5yy_iI01PbrO9gTMmX0kg5Lw/viewform).

## What’s next

To make Kotlin a truly full-stack language, we are going to provide tooling and language support for compiling the same code for multiple platforms. This will facilitate sharing modules between client and server. We will keep working on improving the JavaScript tooling and library support. Among other things, incremental compilation for the JavaScript platform is in the works already. Stay tuned for 1.1.x updates.

Java 9 is coming soon, and we will provide support for its new features before it ships.

We expect a lot of feedback on coroutines in the upcoming months, and improving this area of the language (in terms of both performance and functionality) is among our priorities.

Apart from this, our next release will be mostly focused on maintenance, performance improvements, infrastructure and bugfixing.

P.S. Running on multiple platforms is a strategic direction for Kotlin. With 1.1 we can run on servers, desktops, Android devices and browsers, but in the future we are going to compile Kotlin to native code and run on many more platforms (including, for example, iOS and embedded devices). A great team here at JetBrains is working on this project, and we are expecting to show something interesting fairly soon. This does not target any particular release yet, though.

## Installation instructions

As always, you can **try Kotlin online** at [try.kotlinlang.org](http://try.kotlinlang.org/).

**In Maven/Gradle**: Use `1.1.0` as the version number for the compiler and the standard library. See the docs [here](http://kotlinlang.org/docs/reference/using-gradle.html).

**In IntelliJ IDEA**: 2017.1 has Kotlin 1.1 bundled, in earlier versions Install or update the Kotlin plugin to version 1.1.

**In Android Studio**: Install or update the plugin through _Plugin Manager_.

**In Eclipse**: install the plugin using [Marketplace](https://marketplace.eclipse.org/content/kotlin-plugin-eclipse).

**The command-line compiler** can be downloaded from the [Github release page](https://github.com/JetBrains/kotlin/releases/tag/v1.1).

**Compatibility**. In Kotlin 1.1 the language and the standard library are [backwards compatible (modulo bugs)](http://kotlinlang.org/docs/reference/compatibility.html): if something compiled and ran in 1.0, it will keep working in 1.1. To aid big teams that update gradually, we provide a compiler switch that disables new features. [Here](http://kotlinlang.org/docs/reference/compatibility.html#binary-compatibility-warnings) is a document covering possible pitfalls.

_Have a nice Kotlin!_

P.S. See discussions on [Reddit](https://www.reddit.com/r/programming/comments/5wvpv8/kotlin_11_released_with_javascript_support/) and [Hacker News](https://news.ycombinator.com/item?id=13763483)
"""

Article(
  title = "Kotlin 1.1 Released with JavaScript Support, Coroutines and more",
  url = "https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Roman Belov",
  date = LocalDate.of(2017, 3, 1),
  body = body
)
