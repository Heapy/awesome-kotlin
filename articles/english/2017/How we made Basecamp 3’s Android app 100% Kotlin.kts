

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
![1_OQsP3e0v1JDMPihSMl2DSQ.png][]
Made with ❤️ in Chicago.

We started our Kotlin journey a year ago based on two hunches: that it would 1) make a huge difference in programmer happiness and 2) wildly improve our work quality and speed.

I’m happy to report that our hunches were right! As of this week, Basecamp 3’s Android app is written in 100% Kotlin. 🎉

That puts us in a unique position to share tips from the experience of going from all Java to all Kotlin. How do you get started? What should you look out for? What are the best ways to keep learning Kotlin?

Read on!

## 🤓 Wrap your head around the basics

First thing’s first — take some time to get acclimated with the the language. There are free resources galore, but here are a few I’d recommend:

 *  [Jake Wharton’s talk about Kotlin for Android][Jake Wharton_s talk about Kotlin for Android]. This was the lightbulb moment for me. After two watches, I really started to get it.
 *  [Some of my favorite Kotlin features][]. I wrote this a few months after we started with Kotlin. I specifically wrote it to be basic and straightforward so it’d be easy for beginners (which I was at the time!)
 *  [The Kotlin docs][]. When people tell me to read the docs, I often wince. But the Kotlin docs are legit, and are an excellent way to get acclimated.

## 🐢 Start slow but make it real

Writing your first bit of Kotlin is usually the hardest part of getting started.

To alleviate the pressure, people will often suggest you start with tests or perhaps a Gradle plugin. And while that’s extremely sound advice, it doesn’t work for me. It’s too boring and obscure to get excited about.

I propose something different — write real production code.

This is important because 1) it’s something you can see working in your app right away and 2) it’s more fun! That feeling of accomplishment and seeing something work shouldn’t be discounted — it builds your confidence and keeps you motivated.

Of course I don’t recommend converting a giant class all at once on your first go. Instead try converting a utility or helper *method.* Take a single method from one of your util classes and rewrite it in Kotlin.

Done! You now have your first bit of production ready Kotlin.

## 😵 Don’t try to learn the whole language at once

Kotlin has *a lot* of great stuff in it — so much that you might be tempted to use it all right away.

And while that can work, I wouldn’t recommend it. Instead find a few key concepts that click in *your* brain (not what others tell you are the best parts of the language), and practice using those to their fullest.

When I got started, three areas clicked for me: [flow control][] (`when/if/for`), [extension functions][] (add functionality to any class you want), and [null safety][] (in particular the use of `let`). Focusing on just those few concepts helped me get started without feeling overwhelmed.

Don’t be afraid to start small. You need space in your brain not only to pick up new things, but to let them stick. You’re already going to be dealing with basic syntactical differences — if you try to cram all the goodness all at once, something is bound to get overwritten in your brain.

## 🔀 Learn from the auto converter

JetBrains has done a solid job with their Kotlin plugin and its ability to auto convert Java to Kotlin. It can get your class 60–70% of the way there, leaving you with some tuning and idiomatic/stylistic things to take care of.

There are two great ways to learn from this:

1.  Auto convert the class, but keep the Java class handy. Put them side by side, and see how the Kotlin compares. You’ll pick up a ton by just seeing how the auto converter chose to implement the Java in Kotlin.
2.  Auto convert the class, but don’t leave it in that state — while the 60% version will run, that last 40% is what makes the difference between OK code and great code. Attack everything for clarity and conciseness and look for ways to follow [Kotlin idioms][] that the auto converter couldn’t figure out.

## 🤔 Question all your Java habits

As you begin writing Kotlin, the sad reality is that you’ll probably have some bad Java habits you’ll need to break.

I found myself writing terrible `if/else` blocks in cases where a `when` would be so much better. I was writing null checks around objects when a simple `object?.let{}` would’ve been better. And much more.

You may also have built up some strong ¯\\\_(ツ)\_/¯ attitudes toward ugly code blocks. Because Java is so verbose, over time you may have begun to accept an occasional ugly code block as “well, that’s Java”.

Writing in Kotlin will help you see through those, but you will need to let go of the idea that ugly code is OK. Sure there will be times when ugly code may be situationally necessary, but you won’t be able to blame Java’s ceremony any more.

So regularly question whether you are doing something the “Kotlin way”. When you see code that feels long or complicated, pause and take another look at it. Over time you’ll develop new, better habits that will overwrite your Java ones.

## 🏕️💨 Leave the campsite cleaner than you found it (no new Java ever)

A good way to keep your code heading in the right direction is to tidy up small bits of Java as you pass by. As the saying goes, leave the campsite cleaner than you found it.

A lot of times when building something new, you’ll incidentally need to make a small change to an existing Java class — add an attribute, change a type, etc. These are often small classes like a model or utility class.

Convert them! Don’t be lazy and leave them be. This incremental, slow approach over time will save you and your team much frustration in the long run.

## ❄️ Use cool downs for Kotlin

A great time to work on Kotlin conversions is when you’re cooling down off a big release.

Often we’ll do our normal two week cycle, release a big feature, and then take a few days to watch for stability and customer issues.

Those couple days aren’t going to be enough time to get into something big, so it’s a great time to convert some classes to Kotlin. Over time you’ll get faster at this and be able to knock out a few classes per day.

## 👴🏻 Curb your enthusiasms

When you start to feel comfortable with Kotlin, you might get a little…excited. That’s a good thing! That energy and enthusiasm keeps you motivated and learning.

But it’s also easy to go overboard. I certainly did.

I‘d collapse whatever I could into single-expression functions to save lines, only to realize I was giving up clarity. I’d use too many `when` blocks, even in cases where a simple `if/else` would’ve been sufficient and clearer. I’d write extension functions galore, then realize I was creating more cognitive overhead than I was saving.

It’s wonderful to be enthusiastic and use all the tools that Kotlin gives you. But try to keep your cool (unlike me) and make sure you’re using language features for the right reasons.

## 💸 Don’t forget about your customers

While seeing more and more Kotlin makes your life much better, you need to keep one very important (obvious?) thing in mind: your customers don’t care.

I fully support the argument that programmer happiness leads to great code and ultimately a better product. It’s crucially important.

But as with all good things, you need to find the right balance. Converting all your Java to Kotlin might be a fun goal for you, but meanwhile your customers might be struggling with a nagging bug.

The great thing is that those two things aren’t mutually exclusive. Grab those bugs and squash them with Kotlin — it’s a win-win! (If we’re being honest, Java probably caused the bug in the first place anyway).

## 👯 Don’t go it alone

Depending on your company makeup, this might be easier said than done. But if you can, find a Kotlin buddy!

There is no question that I’m more proficient at Kotlin because of my buddy [Jay][]. We regularly share ideas, review each other’s Kotlin, and poke fun at [Jamie][], our designer.

Sometimes that stuff happens in informal chats, but the by far the most valuable place to learn is in pull requests. You can let your code speak for itself, and the feedback can do the same. When it comes to learning Kotlin (or any new language really), this is absolutely crucial.

Alright, you made it! I know that’s quite a bit to chew on — maybe some obvious, maybe some not. But I hope it helps get you on the right path to joining us in Kotlinland!

*If this article was helpful to you, please do hit the* 💚 *button below. Thanks!*

*We’re hard at work making the* [*Basecamp 3 Android app*][Basecamp 3 Android app] *better every day (in Kotlin, of course). Please check it out!*

[1_OQsP3e0v1JDMPihSMl2DSQ.png]: https://cdn-images-1.medium.com/max/800/1*OQsP3e0v1JDMPihSMl2DSQ.png
[Jake Wharton_s talk about Kotlin for Android]: https://www.youtube.com/watch?v=A2LukgT2mKc
[Some of my favorite Kotlin features]: https://m.signalvnoise.com/some-of-my-favorite-kotlin-features-that-we-use-a-lot-in-basecamp-5ac9d6cea95
[The Kotlin docs]: https://kotlinlang.org/docs/reference/basic-syntax.html
[flow control]: https://kotlinlang.org/docs/reference/control-flow.html
[extension functions]: https://kotlinlang.org/docs/reference/extensions.html
[null safety]: https://kotlinlang.org/docs/reference/null-safety.html
[Kotlin idioms]: https://kotlinlang.org/docs/reference/idioms.html
[Jay]: https://twitter.com/jayohms
[Jamie]: https://twitter.com/asianmack
[Basecamp 3 Android app]: https://play.google.com/store/apps/details?id=com.basecamp.bc3
"""

Article(
  title = "How we made Basecamp 3’s Android app 100% Kotlin",
  url = "https://m.signalvnoise.com/how-we-made-basecamp-3s-android-app-100-kotlin-35e4e1c0ef12",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dan Kim",
  date = LocalDate.of(2017, 4, 29),
  body = body
)
