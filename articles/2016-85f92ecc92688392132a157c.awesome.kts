
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
###### _Part 1_

My first thought when I heard about Kotlin for the first time was "_Just another language to write the same thing using new keywords!_"

Luckily, I’m hugely curious and this thought wasn’t enough to stop me looking into it further. I started reading about it and the more I read, the more I became intrigued...

Now let me say:

> Kotlin is not just another language, it uses another **paradigm** to help developers to solve everyday challenges

#### Android Developers

I’ve been working with Android since 2010, initially just because I was curious to explore the potential of the promising platform from the big *G, but later for several commissioned projects. For each project, I used the standard language that was available: **Java** (just in some spare cases using C with NDK). From it’s release, the Android SDK promised to give a boost to Java using a custom library for Mobile developing and so far it’s been good.

Since the beginning The Android SDK really has been oriented to solve the Mobile challenges (UI responsiveness, Network availability, Sensors API), release after release, improving every time. The first JVM Dalvik did a very good job and the ART even more...  
But I was still getting still stuck with Java (_enhanced 1.6_) and all its Ceremonies in the code.

#### Android Studio

My first projects were on Eclipse IDE and to be honest I found it very useful and reliable with a plenty of documentation and a vivid community, but Google had other plans. They called it **Android Studio**.  
The first version of it was really unreliable, plenty of bugs and not easy to use, so I decided to wait a few versions before using it for my projects.  
Fortunately it didn't take so much time to release a very good, stable and absolutely reliable version of a proper **Android Studio** with all the quality of **JetBrains** products.  
But still Java was the language...

#### New kids in town

Meanwhile the Developer community starting to use new paradigms offered by different kinds of programming languages like the brand new _Swift2_ by **Apple** (and soon Open Source and available for **Linux** too), _Scala_, _Haskell/Miranda_, _F#_, etc. The functional programming paradigm rocked the Developers community like a tsunami, waking them up from the deep sleep of spending too many years working with imperative and procedural languages.

But the Android community still were stuck on Java, and even if the new Java 8 promised to refresh the language, the Android framework couldn't move on it due to the backward compatibility with the oldest version of libraries used by the framework.  
In the meantime **JetBrains** was working on it’s own language, **Kotlin**.

![Kotlin Logo](http://cirorizzo.net/content/images/2016/02/xKotlin-logo.png.pagespeed.ic.3prhfChkMF.png)


#### Kotlin

Since from the early Beta versions, Kotlin has astonished the Android Community (and even more like Back End and Front End developers, etc). It is definitely a _Concise_, _Neat_ language with 100% Java interoperability, studied by people who deeply know the issues and routines of development issues and how to afford them as best as they can.  
In other words I started to experiment with it several months ago and have since decided to use it in my existing and further projects.

#### Having a look to some code

This is just an example of how concise coding with Kotlin can be:

```kotlin
fun thisIsTheMethod(message: String) {
    buttonMessage.setOnClickListener {
        Toast.makeText(context, "Here the message: ${"$"}message",
                Toast.LENGTH_LONG).show()
    }
}
```


The above method is a clear example of how easy it can be to simplify typical Android code using Java. It usually looks like:

```java
public void thisIsTheMethod(final String message) {
    buttonMessage.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getBaseContext(),
                    String.format("Here the message: %s", message),
                    Toast.LENGTH_LONG).show();
        }
    });
}
```


Most of the boilerplate code used to write in Android can be reduced with Kotlin, still having a clean and understandable code.

At a glance, the method or function _thisIsTheMethod_ in Kotlin is implicitly _public_ a if hasn't any return (_void_ in Java, in Kotlin will see next what is) doesn't need any further keywords

```kotlin
fun thisIsTheMethod(message: String) {...}
```

Also the signature has param declaration quite different from Java. The type follow the param name but it hides something more: **Null Safety** type system.

I like to report from the official documentation the following:

> Kotlin’s type system is aimed to eliminate NullPointerException’s from our code

Just this should be something Android developers will love.

And last but not least the _setOnClickListener_ for the view (_buttonMessage_) that can be reduced in a SAM (Single Abstract Method) through the Lambda Expression. Yes, finally!

```kotlin
buttonMessage.setOnClickListener() {...}
```

Well, that’s it for now. I'm gonna go further in future posts to share more about the language and the benefits Kotlin has brought to my Android coding

It really is easy to switch to Kotlin from Java. Maybe now is the time for you to give it a closer look?  
[Jump to part two](http://www.cirorizzo.net/2016/02/03/kotlin-code/)

"""

Article(
  title = "My Kotlin Adventure",
  url = "http://cirorizzo.net/2016/01/23/kotlin-at-glance/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ciro Rizzo",
  date = LocalDate.of(2016, 1, 23),
  body = body
)
