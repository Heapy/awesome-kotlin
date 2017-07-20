

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
We are happy to announce the first Technology Preview of **Kotlin/Native** that compiles Kotlin directly to machine code. The Kotlin/Native compiler produces standalone executables that can run without any virtual machine.

It is not a fully functional release yet, but you can already play with the technology and take a look at its source code [here][]. The compiler is available under the Apache 2 OSS license.

![KotlinNative][]


## Mission ##

Kotlin/Native is another step toward making Kotlin usable throughout a modern application. Eventually, it will be possible to use Kotlin to write every component, from the server back-end to the web or mobile clients. Sharing the skill set is one big motivation for this scenario. Another is sharing actual code.

Our vision for inter-platform code reuse is the following: one can write entire modules in Kotlin in a platform-independent way and compile them for any supported platform (currently these are Kotlin/JVM, Kotlin/JS and the upcoming Kotlin/Native). We call these *common modules*. Parts of a common module may require a platform-specific implementation, which can be developed individually for each platform. Common modules provide a common API for all clients, but other (platform-specific) modules can extend this API to provide some exclusive capabilities on their platform.

Note that we do not intend to make arbitrary Kotlin/JVM programs runnable on Kotlin/Native or Kotlin/JS. It would be equivalent to implementing another JVM, which is both a lot of work and a lot of limitations for the runtime. We are going another way: providing a common language for all platforms while enabling creation of common libraries through seamless interoperability with platform code.

## Technology ##

Kotlin/Native uses the LLVM compiler infrastructure to generate machine code. In this preview, we support the following [target platforms][]:

 *  Mac OS X 10.10 and later (x86-64)
 *  x86-64 Ubuntu Linux (14.04, 16.04 and later), other Linux flavours may work as well
 *  Apple iOS (arm64), cross-compiled on MacOS X host
 *  Raspberry Pi, cross-compiled on Linux host

More platforms can be added relatively easily, as long as the LLVM support is available for them. We will probably support a few more platforms out-of-the-box in the future.

As usual, interoperability is among our top priorities, and Kotlin/Native can efficiently call C functions and pass/get data to/from them. You can generate Kotlin bindings from a C header files at build time and get fast type-safe access to any API native to the target platform. See detailed instructions [here][here 1].

### Memory management ###

Kotlin/Native is designed to potentially enable different memory management solutions for different target platforms. For example, in the future it may make sense to have a tracing GC for server/desktop platforms, while ARC makes a lot more sense on iOS. Some platforms may only need manual memory management, and get an even smaller Kotlin/Native runtime in return.

This Technology Preview features automatic reference counting with a cycle collector on top, but what the final memory management solution(s) will look like is unknown at this point.

### Current limitations ###

As mentioned above, Kotlin/Native is far from complete, so this Technology Preview has a number of limitations that will be eliminated at later stages:

 *  No performance optimization has been done yet, so benchmarking Kotlin/Native makes no sense at this point.
 *  The Standard Library and reflection support are far from complete, more APIs will be added later.
 *  Read more in the [Release Notes][].

## Future plans ##

We are currently working on the core technology for Kotlin/Native which is the same for all target platforms (compiler, core runtime and library). As a matter of possible future work, we are considering the following possible use cases:

 *  iOS applications (reusing code with Android)
 *  Embedded systems/IoT (e.g., Arduino and beyond)
 *  Data analysis and Scientific Computing
 *  Server-side and Microservices (low-footprint executables, utilizing the power of coroutines)
 *  Game Development

## How to try ##

We’ve prepared two archives with compiler, samples and documentation: [for Mac and iOS][] and [for Linux and Raspberry Pi][].

Check out the [Github project][] and [Release Notes][] for instructions.

**Your feedback is very welcome** in the \#kotlin-native channel on our [public Slack][] (Get your invite [here][here 2].

[here]: https://github.com/JetBrains/kotlin-native/
[KotlinNative]: https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/04/KotlinNative.png
[target platforms]: https://github.com/JetBrains/kotlin-native/blob/v0.1.0/RELEASE_NOTES.md#supported-platforms
[here 1]: https://github.com/JetBrains/kotlin-native/blob/v0.1.0/INTEROP.md
[Release Notes]: https://github.com/JetBrains/kotlin-native/blob/v0.1.0/RELEASE_NOTES.md
[for Mac and iOS]: http://download.jetbrains.com/kotlin/native/kotlin-native-macos-0.1.tar.gz
[for Linux and Raspberry Pi]: http://download.jetbrains.com/kotlin/native/kotlin-native-linux-0.1.tar.gz
[Github project]: https://github.com/JetBrains/kotlin-native
[public Slack]: https://kotlinlang.slack.com/
[here 2]: http://slack.kotl.in/
"""

Article(
  title = "Kotlin/Native Tech Preview: Kotlin without a VM",
  url = "https://blog.jetbrains.com/kotlin/2017/04/kotlinnative-tech-preview-kotlin-without-a-vm/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2017, 4, 4),
  body = body
)
