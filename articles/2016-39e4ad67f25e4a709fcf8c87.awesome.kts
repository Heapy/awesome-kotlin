
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
It doesn't matter how many languages you have learnt, learning yet another one is always a wonderful experience. I have been working for a couple of months with Kotlin. Have got one app to ready for production state and another in the works.

To cut to the chase, I am really impressed by this language. As in really really impressed. And so I decided that I should write a few posts that can help readers get a better idea and learn about this language. So I started writing out the series and am already onto the third post now. But as I was writing it, I realised what I was writing was really about the mechanics of the language. Not so much about its spirit. Cos when one gets into the tactical nitty gritty details, one tends to miss the forest for the trees. So I decided to pen that here before I start that series (next week), and here's my few thoughts about why I really enjoy the language.

One of the reasons there is a lot of enthusiasm with the language is because it can be readily used for programming to the Android platform. That is how I ended up learning it. But very soon, my enthusiasm spread into the desktop/server space where I think Kotlin deserves a shout out for a very very credible effort.

[ EDIT: Deleted a few paragraphs]

So what does Kotlin bring to the table?

* Runs on the JVM
* Is extremely good at interoperating with existing java code and libraries.
* Statically typed (thus far far easier to refactor)
* Has a bit more evolved type system than Java.
* Is much safer than Java. Nullability is a first class compiler level construct - something that helps address a large number of likely defects aka the billion dollar bug). There is a lot to write home about here.
* Has a clear system of distinguishing between mutable and immutable structures. While it doesn't make an opinionated stand in favour of immutability, it gives you the programmer enough helpful constructs to chart your course down that path.
* Has support for type inference which helps reduce your boiler plate and keeps code shorter
* Has a wonderful story to tell in terms of it providing programmers an ability to write extension functions to existing classes (or for that matter even primitives).
* Support for Higher Order Functions, Higher kinded types
* Support for Android programming and JavaScript execution engine (experimental).

Does that make it the better language? I personally think it has achieved amongst the best set of tradeoffs that are attractive to me (obviously YMMV). And here's why I enjoy it so much.

* Its expressivity is comparable to and superior than python keeping your code short and sweet
* It being a statically typed language helps enormously speed up refactoring exercises. Even though I have been using it for only two months, I went through at least two brutal refactoring cycles and found Kotlin code to be a pleasure to change.
* It is safer, lot lot safer than python / java and delegates one of the most frequent tasks of programming ie. null checking to the compiler. Thats a huge effort of the back along with reduction in number of probable defects. Surely nothing to complain about here. And believe me this is a big big plus point.
* Uses the JVM which is a fine and fast runtime engine
* It is easy to learn. I actually went through the reference from start to end in 2 days and was a half decent programmer in about a weeks time. Since then I have written functional libraries for Option, Either, Stack, List, HTTP Clients, XML parsing helper libraries, rather complex X.509 certificate trust chain verifiers and a complete Java FX based app. Have started playing around with more functional constructs such as currying, functors etc. Look forward to working more with typeclasses. I just don't think I could have had such a pace of progress with any other language.
* It is non opinionated. For XML parsing I wanted to ensure very low memory footprint because I wanted to use it on android. I was able to write a library which heavily used mutation even as it maintained very low memory usage by keeping extra object allocation counts down. For another situation I wanted to use immutable functional paradigm, and Kotlin happily let me create my own Option/Either/List classes and use that paradigm end to end. It doesn't get in your way. And focuses on getting work done.
* I have been interfacing with a large number of java libraries. The interoperability has been an absolute breeze. It is actually refreshing to go through that experience. Doesn't matter if you are dealing with HTTP client APIs, integrating into a Slack HTTP service, interfacing with Java PKCS API, Kotlin does it easily and safely.

In a nutshell, I can write code that is safer, shorter, uses better building blocks, is easier to grok, gives me all the nice capabilities such as HOFs, lambdas, pretty competent type system that gives me all the feedback necessary when writing code and makes refactoring a breeze, and interoperates nicely and leverages the JVM universe.
"""

Article(
  title = "Few thoughts about Kotlin and why I like it so much",
  url = "http://blog.dhananjaynene.com/2016/04/few-thoughts-about-kotlin-and-why-i-like-it-so-much/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dhananjay Nene",
  date = LocalDate.of(2016, 4, 15),
  body = body
)
