
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I recently got a chance to dive into two interesting JVM languages, Kotlin (1.0) and Ceylon (1.2.1). They’re both attempts at being better Javas. My thoughts and observations in no particular order:

* Kotlin has many commercial users already and it’s the JVM language besides Java and Scala that people talk about at the conferences I attend.
* Kotlin is _very_ solid. Except for an obscure type inference bug, I haven’t encountered much in the way of bugs at all. I unfortunately hit quite a few Ceylon bugs in both the compiler and the SDK.
* Ceylon has JBoss modules as part of the language. I think it’s exactly the right idea. The fact that Kotlin doesn’t have a language level module system is disappointing. If they’re waiting for Jigsaw, they’ll end up with something inferior.
* Both languages have typesafe null. This is the single most important improvement over Java.
* Kotlin supports Android very well, which I think explains the popularity of this young language. Who wants to code in pre-Java 8? It helps that Android Studio is based on IntelliJ IDEA. Ceylon does not support Android at all.
* Kotlin feels a lot like TypeScript, which I use for the web clients. Helps me switch back and forth. I _could_ use Kotlin in the browser, but I don’t see the point. With both Google and Microsoft backing it, TypeScript is the future of the web (I think both the Kotlin and Ceylon teams should put a bullet in the head of their Javascript backends. It’s a colossal waste of time.)
* Ceylon has the best Eclipse support. Kotlin has the best IntelliJ support.
* Kotlin has a fast compiler and programs start quickly under the JVM. Ceylon programs takes some time to both compile and launch. While running, they seem to perform equally well.
* Kotlin has a quirky object literal syntax (think JSON-ish structures). Ceylon got this one right.
* Kotlin has unchecked exceptions, and that’s it. Good. I still don’t know how I’m supposed to deal with errors consistently in Ceylon.
* Ceylon has a very nice Tour to get you started. Kotlin, not so much.
* Ceylon has reified generics. Ok, so why doesn’t Kotlin have reified generics again? I find no reasonable excuses for this omission.
* Kotlin manages to be both terse and readable. It has the right amount of annotations to make intentions clear. Ceylon is basically just as verbose as Java, except when “programming in the small” where list comprehensions and stuff like that shines. The syntax for Ceylon generics is an acquired taste.
* Using union types is like driving around in a brand new Ferrari with your mother-in-law in the passenger seat. Mixed emotions. Flow typing and union types are very powerful ideas, but they trick you into not paying attention to proper type abstractions. Result? Unmaintainable and unrefactorable code.
* Ceylon lacks overloading because union types and default arg values are supposed to remove the need for them. At times, I still ended up with “overloads” having arbitrary naming schemes.
* Both languages have multiple return values, using tuples and destructuring respectively. Useful when contained, but don’t expose this in public signatures if you want readable, maintainable and interop friendly code.
* The number one priority of any JVM language? Frictionless interop with Java and the SDK of course. Kotlin gets Java interop exactly right. Ceylon’s Java interop is surprisingly quirky.

I ended up liking Kotlin a lot and I’m already using it in a real Android-client, AWS-backend project. Ceylon has a lot of good ideas, but I never felt at home. You should try both languages though.

I’m currently learning Clojure. It will be interesting to see if I can get the chance to finally do a lisp project.
"""

Article(
  title = "Kotlin and Ceylon",
  url = "https://medium.com/@elviraw/kotlin-and-ceylon-3ee011125b7d#.lzdgs065k",
  categories = listOf(
    "Ceylon",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Elvira",
  date = LocalDate.of(2016, 2, 28),
  body = body
)
