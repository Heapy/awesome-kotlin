
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## ... but I’m OK with it.

Earlier this month, I published an account of my very first experience of Android development. It wasn’t pretty.

[**Android is hard** _TL;DR. I have my very first Android app out!_ hackernoon.com](https://hackernoon.com/android-is-hard-b7a5a5549655)

Being honest pays, and in this case it paid so much that one of the editors at Hackernoon picked my story up, and then some. Numbers were shooting up, I could not be happier. Then something happened.

You know when you are aimlessly complaining about X on the Internet, and some guy shows up and says “Why don’t you use Y?” Question mark, that’s it. I hate this kind of comments. No evidence for why Y is better than X or why Y — oh, why — would make my life any less miserable. To be fair, pretty much anything is guaranteed to make your life less miserable than Java, so I said what the heck, and tried my luck with Kotlin.

I heard good things about Kotlin, you know, like how it is null-safe, and Java is not, and that is the premier source of bemusement of roughly 99.9% of the people who ever used anything that could run Java.

![](https://cdn-images-1.medium.com/max/800/1*UzblnQYcfXEZlg37__zxWw.png)

When you see it...

Kotlin has many other nice features, but I was sold at null-safety. Kotlin is also developed by the nice people at JetBrains, who happen to be the same that develop IntelliJ IDEA, the IDE on which Android Studio is based. With the most amazing of _non sequuntur_, Android Studio has an automatic get-Java-the-hell-out-of-my-life plugin that turns your Java code into Kotlin. I haven’t tested it extensively, but it worked very well for me. Granted, it left behind a number of Javaisms, but they were pretty easy to fix.

To make a long story short, I hit “make it so” to run my app on my phone. I have been around programming long enough that I was not expecting this to work, and yet it did. Within seconds, I had to ask the Internet how to check whether my Kotlin app was really running, or my phone was running the Ghost of Apps Past. After some digging, it suddenly hit me: Kotlin is not only compiled to JVM bytecode, but it also effectively uses much of Java’s and Android’s regular libraries. That makes perfect sense. It’s almost as if Kotlin code is getting transpiled — yes, that’s a word — to Java, and then a regular old boring Java compiler is used, except that it isn’t, it’s much more clever than that, and that’s all that matters.

On the other hand, Kotlin is effectively lying to me. Whenever I declare a Map, and initialise it with a `mapOf()`, what happens is that I’m given a cleverly disguised `LinkedHashMap`, and that is what you end up using in your code: a regular, old `LinkedHashMap` with all its quirks and methods. Don’t get me wrong, Kotlin is a nice language, but the more I dove into making sure that my newly-converted code still made sense, the more the feeling that I was being cheated grew. In the end, I’m still writing an Android app, and everything is written in Java, and that means that there is an awful lot of things that can be null at runtime. Sure, Kotlin has ways of dealing with that, and very nice ways at that, but I found myself having to use them way more often than I was expecting. To make things worse, there is practically no way of telling in advance what values may be null at runtime, because the Java codebase is so poorly annotated, and so the best advice I got was to always assume that everything that touches Java can be null at any time.

The single reason for me to switch to Kotlin, vaporised. I let that sink in for a moment...

... and then I still got on with Kotlin because overall it’s a much nicer language to look at.

For those who are wondering, “_non sequuntur_” is the correct plural of “_non sequitur_”. Get your Latin straight, you Anglophones.

"""

Article(
  title = "Kotlin is cheating on me...",
  url = "https://hackernoon.com/kotlin-is-cheating-on-me-e048cde4f66#.5vq8chhbz",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Andrea Franceschini",
  date = LocalDate.of(2016, 12, 24),
  body = body
)
