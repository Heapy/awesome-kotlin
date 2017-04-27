import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
As [you know](https://blog.jetbrains.com/kotlin/2017/01/kotlin-1-1-beta-is-here/), Kotlin 1.1 will be shipping with [coroutines](https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md) as an experimental feature, and will require an opt-in compiler switch to enable them.

This post seeks to do the following:

* Explain our motivation to not release coroutines unconditionally;
* Explain our plans regarding compatibility of coroutines in future versions of Kotlin;
* Ask your opinion about the overall approach and some particular aspects of it (scroll to the bottom if you only want to share your opinion ;)).

## Why coroutines are experimental in Kotlin 1.1

We believe that it is a very important feature, because it opens a door on a whole new style of programming: asynchronous, non-blocking code with straightforward structure. And we believe that we have a very good design there: few built-in things, a lot of flexibility, clear mental model, good performance where it matters.

Nevertheless we are not too eager to say that this design is _final_. We understand that the whole area of asynchronous programming is relatively new (to the main stream at least), and the experience that other languages got before us (from Scheme and Go to C# and Dart) is not necessarily applicable to Kotlin, because of many subtle and not-so-subtle differences. Overall, it's largely unknown how people might use coroutines, what turns out to be most important and even what use cases that we didn't think of are out there. Of course, one can't expect to know all use cases upfront, so it's a matter of intuition/expert opinion as for when we have enough. We collected as much information as we could, did some extensive prototyping, wrote [different libraries](https://github.com/Kotlin/kotlinx.coroutines), and we feel it's more of a terra incognita at the moment.

So, coroutines are experimental in 1.1, because we expect the design to change.

Note that it's not the implementation bugs we worry about: there will be bugs, and we'll fix them as usual, but overall we think that coroutines are in a good shape implementation-wise.

## Why release coroutines at all

Normally, when a feature is not ready, we don't release it at all. We keep it in our language design back yard until we're reasonably sure it's good enough to get into the language. So, why include an experimental feature in 1.1?

The reason is: it's too big for our back yard :) This is to say: we did all we could do internally, but the feature is too multifaceted in its possible applications to be released without an extensive battle-testing outside our team.

So, we need your feedback and we need it as diverse as possible. This is why we make coroutines available to anyone, but give a fair warning that the design may change. (An opt-in switch `-Xcoroutines=enable` will remove the warning.) We want people to use them and communicate their use cases, issues and ideas back to us. The more coroutine-based code is written out there, the sooner we can adjust the design and get to the final version of it.

And, honestly, it's also a bit too good to be kept in the back yard :) Very many people can benefit from coroutine-based APIs and libraries today, an this aligns nicely with what's said above: the more you benefit, the more we can learn about your use cases and needs. We expect that very many of you will find their use cases already covered just fine in 1.1, but those who don't will give us valuable feedback.

## What may change in future versions

I, personally, think that everyone should grab and use coroutines as soon as 1.1 comes out, and the readers of this forum should probably start as soon as yesterday :) But some of us may be concerned with the possible changes coming in future versions of Kotlin that may pose some migration difficulties. Here, I'll try to give an honest account of what we expect to change and below I explain how we are going to mitigate the possible compatibility issues. (If it's too scary for your setting, you may want to keep the coroutines out of your project until they graduate the experimental status.)

When it comes to compatibility, there are two large groups of concerns: source compatibility and binary compatibility.

Source compatibility is somewhat less of a concern, because if you have the source that used to compile in version X, but fails to compile in version Y, then all you need to do is fix it, and we have a very positive experience in providing you with tools that do that automatically (or almost automatically). So, what may break in our source code? We do not expect the language syntax to change, it's too little syntax, after all. But we think that **the core APIs may change**.

Binary compatibility is usually harder: when you have a binary, you often do not control the source (or it's too costly for you to fix the source and build a new binary). So, what may change in our binaries? Well, the same **core APIs affect the binaries** as well as source, plus we may need to change **something in the ABI**, i.e. how we represent coroutines in the byte code.

## How we plan to make it relatively easy to upgrade to the final design

Actually, I think, we have a pretty good plan for how to both extensively use coroutines now and avoid much pain later:

1. On the source/API level we will simply use the usual _deprecate-migrate-remove cycle_, which has proven pretty smooth with the help of our `ReplaceWith` functionality in the IDE
2. Also, the newer compilers will keep supporting the experimental design for a while
    a. For a while they will understand binaries produced by 1.1 and generate correct calls to them
    b. With a compiler switch `-language-version=1.1` they will emit code as 1.1 does
3. On the binary level we are going to simply _keep the experimental APIs in a separate package_. This is the way that, for example, JUnit and Rx used when rolling out radically new APIs.

So, all the APIs related to coroutines in `kotlin-stdlib` will ship in a package named `kotlin.coroutines.experimental` (or maybe we find some better name, see below). When the final design is ready, we'll publish it under `kotlin.coroutines`, and keep the old binaries compatible and working. We can remove the experimental package later on (after waiting a considerable time to give everybody a change to migrate), or rather move it to a separate artifact so that everyone can use it at any time if they have to.

**An important thing is**: _every library that uses coroutines in its public API should do the same_. I.e. if you are writing a library that it here to stay, so you care about the users of your future versions, you will also need to name your package something like `org.my.library.experimental`. And when the final design of coroutines comes, drop the “experimental” suffix from the main API, but keep the old package around for those of your users who might need it for binary compatibility.

We realize that there are some settings under which nothing is acceptable but 100% backward compatibility, no bargaining allowed, but we expect these to be a minority of our users.

**So, the big question to you**: does this plan sound reasonable? Do you think we can improve on it?

## Some bikeshedding questions

Now, let the [bikeshedding](https://en.wikipedia.org/wiki/bikeshedding) begin :) We are not running a democratic procedure here, but your opinions and arguments will help us a lot.

**Question number one**: what suffix should be used for those experimental packages?

* `experimental` is very clear, but looks a bit long, so do `incubation` and `tentative`.
    * Consider: `kotlin.coroutines.experimental`, `org.my.library.experimental.async`
* Among shorter options, we are looking at `probe` and `pilot`. How do you feel about these?
    * Consider: `kotlin.coroutines.pilot`, `org.my.library.pilot.async`

A note about another option: we considered using something like “v0” as a suffix: `kotlin.coroutines.v0`, but there are two downsides:

* it sort of suggests that there will be v1, v2, etc, which we are not planning to have;
* it may look weird in your library whose version is, e.g. 1.5.

**Question number two**: would it be better to put the experimental API in a separate JAR file?

Currently we are planning to ship the experimental package in the `kotlin-stdlib.jar` (`kotlin-runtime.jar` in the compiler distribution), and it will be possible to move it to another JAR later. We are on the fence on this, so your opinions are welcome.

_Thanks for your time. Your input is very welcome!_
"""

Article(
  title = " Experimental status of coroutines in 1.1 and related compatibility concerns",
  url = "https://discuss.kotlinlang.org/t/experimental-status-of-coroutines-in-1-1-and-related-compatibility-concerns/2236",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrey Breslav",
  date = LocalDate.of(2017, 1, 20),
  body = body
)
