

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
When people start looking at Corda’s code the things they notice immediately is that it’s written in [Kotlin](https://kotlinlang.org/), a new programming language from JetBrains that targets the JVM and Javascript. This was an unusual choice, so in this post I’ll give some background on why we did it and discuss experiences from our “year of enterprise Kotlin”.

## Why Kotlin?

We can break this decision down into two parts:

1.  Which platform to use? JVM, .NET, Node, Python/Ruby, Go, Haskell or native?
2.  Having selected the JVM, which language targeting it? Java? If not, why not? And if not, then which of Scala, Ceylon, Clojure, Kotlin, Python, Ruby, Javascript or Haskell to use (as they all have implementations that target the JVM).

The reasons for choosing the JVM as a platform are well understood in the enterprise space and not something that needs to be dwelled on much here. Suffice it to say that if you want a scalable, thread-safe, garbage collected, cross platform runtime with a very large collection of well documented libraries that solve common business tasks then your field has already been narrowed to just the JVM and .NET.

At the time Corda started development, it didn’t have a name and it wasn’t  clear that it’d go ahead and turn into a product. In fact, when the project that would become Corda began in December 2015 (my first day on the job), there were no plans to build a new enterprise distributed ledger. Corda started out as a collection of prototypes to explore some new ideas and requirements that the consortium’s _Architecture Working Group_ was interested in, particularly around limited data visibility, and a data model that gave the scalability of the “UTXO set” approach along with the generic programmability of Ethereum’s imperative smart contracts.

Because it wasn’t clear if these prototypes would turn into anything or just inform the thinking of other products on the market, we faced a tricky choice: on one hand, we wanted to rapidly explore algorithms and data structures in a way that was productive. On the other, it needed to be something that could still be used to build a large enterprise product and for which we could rapidly hire.

Java obviously fit the bill, but its lack of modern conveniences significantly reduces productivity and, more subtly, developer morale.

Dynamic typing was out: the correctness, tooling and performance benefits of static typing are too great to ignore.

Languages radically different from the mainstream are also out, as we wanted to be able to hire financial industry domain experts – whilst building a team around languages like Haskell [is by no means impossible](https://lexi-lambda.github.io/blog/2016/06/12/four-months-with-haskell/), intersecting people with serious banking experience _and_ lazy pure functional languages _and_ who happen to live in London felt risky. In addition, the nature of the product means our “users” are actually plugin/app developers who build on the platform, and it wouldn’t make sense to require them to learn entirely new paradigms and toolchains. Our choice of language should not constrain our users too much.

Added up, these requirements left us with Kotlin, Scala and Ceylon. All are rather similar languages and all were quite appealing. We went for Kotlin for these reasons:

* Near seamless Java interop
    * In particular, Kotlin programs use a compiler-enhanced version of the regular JDK collections. Thus there are no interop issues created by the use of a different collections library. Passing collections in and out of Java libraries is something we do all over the place in Corda, so it’s important that this be painless.
    * Kotlin classes produce ordinary looking Java APIs with get/set/is methods as appropriate for the type. No special annotations or effort is required. Because Corda exposes an API that is intended to be transparently usable by Java developers, this is a big win: ordinary code tends to produce APIs that can’t be told apart from a Java API, with only a few minor aspects needing thought (e.g. you need to manually annotate methods to specify that they throw checked exceptions, otherwise they aren’t catchable from Java).
* Inlining of small functions like map/filter/fold/groupBy is done by the compiler frontend instead of relying on the JVM to do it. Unfortunately the JVM’s JIT compilers, whilst generally excellent, don’t reliably remove the overhead of heavy use of higher order functions. Using Kotlin fixes this, along with allowing you to do flow control inside the lambda functions. This is the kind of feature that’s obscure but adds up, as we write code in this functional style all over the place and could easily dig ourselves into a performance hole if this style translated badly to machine code.
* Because Kotlin code translates to equivalent Java very closely, almost all existing Java-oriented tools work out of the box. This isn’t always true of other languages, for instance, Quasar struggles to instrument Scala code because it wants method annotations and Scala translates lambdas to methods that can’t be annotated. Kotlin lambdas are often inlined anyway (see above) and can be annotated when not.
* Excellent documentation and tiny standard library makes it a very fast language to learn. We do not explicitly advertise our positions as needing Kotlin experience and have been hiring people who didn’t know it already, with ramp-up times in the range of 1-3 days before the new team member is producing idiomatic code.
* IntelliJ is the most popular IDE, judging from what our interview candidates select (they have a free choice of tools). Kotlin has the best IntelliJ support of all the post-Java languages.
* I already had experience with it and had found it to be enjoyable, so I felt confident new team members would enjoy it too.

If Kotlin didn’t exist we’d probably have gone with Scala instead: it is heavily inspired by Scala and they’re both great languages.

## Our year of Kotlin

So what was it like, using a new language in an enterprise context for a year?

The highlight has undoubtedly been spontaneously hearing from team members how much they’re enjoying working with it. Programming languages are a very personal thing and people tend to have strong opinions on them. When you ask people to learn a new language as the first task in their new job, and when you don’t even _tell_ developers that this is what you’ll be doing, there’s always a risk that some team members will just hate it and find it hurts rather than helps their productivity.  That hasn’t been an issue here.

Some issues that frequently crop up in the post-Java/C# enterprise space and our experiences:

* **Code that looks very different depending on who wrote it.**  By and large not a big issue. Unlike Go, which mandates a particular code style, Kotlin code written by different authors can look different. But IntelliJ has a reformatter tool that is capable of unifying code style across the codebase. It’s more limited than the Java equivalent, but has been sufficient. A more subtle issue that can crop up, especially with Scala codebases, is Java-OOP vs Haskell-FP style coding. Scala code that uses libraries like scalaz [can prove difficult to read](https://gist.github.com/folone/6089236) for people who are expecting to see a better Java. Kotlin comes down pretty firmly on the better Java side of the debate, and [although you can do some kinds of functional programming in Kotlin](https://blog.plan99.net/kotlin-fp-3bf63a17d64a#.lmrq68n03), the community hasn’t divided into camps (at least, not yet). We’ve had some cases where code was written as if it were Haskell, but that has got worked out in code review.
* **Libraries.** We use over 50 open source libraries in Corda and they have all been painless. We have not found ourselves writing wrapper or adapter layers. Kotlin projects typically use Gradle or Maven as their build system: there’s no official Kotlin-specific replacement for these tools (though Gradle is adopting Kotlin as its new scripting language!).
* **SQL DSLs.** C# has LINQ, Java has jooq and Kotlin has [Exposed](https://github.com/JetBrains/exposed). This is an area where Kotlin is a bit weaker than the competition: Exposed is a great example of how to use Kotlin’s DSL building features, but the library itself does not have a stable API and is a side project. Jooq can of course be used from Kotlin as well and in hindsight that would probably have been a better route to go.
* **IDE/tooling.**  The Kotlin plugin for IntelliJ is of course also written by JetBrains and is generally superb. However, it’s not quite as sophisticated as the Java support is. New editor features like [parameter hints](https://blog.jetbrains.com/idea/2016/09/intellij-idea-2016-3-eap-faster-git-log-parameter-hints-and-more/) have to be manually ported to Kotlin and as such support tends to lag behind the much older Java plugin. We’ve also found the IDE plugin to report internal errors quite frequently, although the frequency of IDE exceptions has fallen dramatically over the course of the year (and they don’t seem to impact anything when they occur). Tooling is also pain-free, as tools written for Java typically work out of the box. The exception are tools that actually parse source code rather than bytecode: those obviously cannot be reused.That said, Kotlin’s compiler and IDE plugin are not as robust as Java’s even a year after the 1.0 release. You basically never encounter internal compiler errors in javac. They are something we do still encounter in Kotlin on rare occasions.
* **Customer acceptance.** Corda’s users are typically large, conservative financial institutions. Such companies tend to prefer the use of well known, established languages. Kotlin, being neither, obviously raised some eyebrows when we started out. “Why Kotlin” is a question that has largely faded away over the past year as people looked closer and realised that it wasn’t as risky as new languages typically are.We have also tried to address this by providing Java code samples to prove that developers can build on the platform without having to learn Kotlin. There we’ve been a bit less successful: many developers who are new to Corda still start out by getting familiar with Kotlin first. It’s a bit unclear if this is because we don’t provide enough Java-specific samples and docs or whether it’s a good excuse to learn a cool new tool. We’ve also been assisted here by the growing acceptance of Kotlin inside the large investment banks. We’ve heard from several consortium members over the past year that their internal software development teams have started seriously looking at Kotlin for their own use: often prompted by the existence of the Java-to-Kotlin converter tool that makes introduction into an existing codebase much more seamless.
* **Commercial support.** A risk with using obscure languages is that they might not stick around, or might have goals that are not compatible with the needs of the user base that grows around the product (e.g. in the case of research languages where the primary goal of the developers is to publish papers). One reason we felt comfortable going with Kotlin is that JetBrains is a stable, profitable company that has been around for over 15 years. JetBrains is rapidly “eating its own dogfood” by introducing Kotlin code into the core of its primary products; thus there is little risk of it becoming unmaintained. Additionally, JetBrains is old enough that its core market (IDEs and developer productivity tools) is no longer particularly fashionable or new, giving a low risk of a corporate buyout that causes an unpredictable strategy change.Whilst there are no commercial support packages available for Kotlin, in practice the team fixes reported issues pretty fast. JetBrains are currently aiming to release the next major upgrade of the language about a year after 1.0, a release cycle which is quite compatible with enterprise development cycles.

## Conclusion?

We have no regrets: picking an unproven language at the start of this project was a risk, but a calculated one. It’s worked out well for us and we’d do it again.
"""

Article(
  title = "Corda: Kotlin",
  url = "https://www.corda.net/2017/01/10/kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mike Hearn",
  date = LocalDate.of(2017, 1, 10),
  body = body
)
