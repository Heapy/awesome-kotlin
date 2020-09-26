
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## Alliance between Gradle Inc. and JetBrains makes the Build a first-class citizen in Software Development

SAN FRANCISCO--([BUSINESS WIRE](http://www.businesswire.com/))--Gradle Inc. today announced an alliance with JetBrains to provide a Kotlin-based Build Programming Language for Gradle, the popular open source build automation software.

> Build programming is advanced engineering— @Gradle @JetBrains alliance promotes @Kotlin-based builds #ElevateBuilds

Why is this important? Historically, build automation has relied on simplistic shell scripts or inflexible XML files. This no longer works. In the age of Continuous Delivery and DevOps, builds have become a full-fledged engineering discipline. Today, a product’s build is mission-critical; it automates and assembles, connects, tests, packages and—in many cases—deploys or ships that product. As such, the design, implementation and maintainability of build code demands first-class treatment. Today, Gradle is taking the next step in elevating the discipline of build automation by supporting writing Gradle builds in Kotlin—a modern, pragmatic, and statically-typed language developed by JetBrains. The result for Gradle users will be best-in-class usability and performance, and deep IDE support in IntelliJ IDEA and Eclipse—including refactoring, symbol navigation, code completion and content assist.
Why is this important? Historically, build automation has relied on simplistic shell scripts or inflexible XML files. This no longer works. In the age of Continuous Delivery and DevOps, builds have become a full-fledged engineering discipline. Today, a product’s build is mission-critical; it automates and assembles, connects, tests, packages and—in many cases—deploys or ships that product. As such, the design, implementation and maintainability of build code demands first-class treatment. Today, Gradle is taking the next step in elevating the discipline of build automation by supporting writing Gradle builds in Kotlin—a modern, pragmatic, and statically-typed language developed by JetBrains. The result for Gradle users will be best-in-class usability and performance, and deep IDE support in IntelliJ IDEA and Eclipse—including refactoring, symbol navigation, code completion and content assist.

“JetBrains is widely respected for their excellent products.” said Hans Dockter, CEO and founder of Gradle Inc. “This alliance is based on mutual technical admiration and a common goal to place the most effective tools in the hands of developers.”

Build programming is an advanced engineering discipline— @Gradle @JetBrains alliance promotes @Kotlin-based builds #ElevateBuilds

**Modern Build Infrastructure, Largest Community**

A majority of enterprises are already using Gradle to build and deliver mission-critical software written in dozens of programming languages and platforms including Java, Android, C, C++, Python, Kotlin, Groovy, Scala, Hadoop and many more. Gradle is the new market leader for build automation, with 10.4 million direct downloads in 2015, and is growing fast with nearly 2 million new direct downloads per month in 2016. Because Gradle is a core part of Google’s Android Studio IDE (based on JetBrains’ IntelliJ IDEA), virtually every Android developer also uses Gradle. The fact that Google chose both JetBrains and Gradle Inc. for Android tooling further validates IntelliJ IDEA and Gradle as world-class technologies.

“The ultimate goal of Kotlin is to make developers' lives easier, and we’re excited to partner with a great product like Gradle on this mission.” said Andrey Breslav, Kotlin team lead at JetBrains. “Together we’ll create a unique experience to the benefit of Gradle’s many users.”

**Advanced Customers Lead the Way**

Mobile, SaaS, IoT and Container technologies along with modern DevOps practices have transformed the requirements for modern build systems. With the DevOps and Continuous Delivery age producing more and more complex automation requirements on the build, there is an increased need to see build code as first-class, performance optimized software—an enterprise asset as opposed to a collection of scattered, convenience scripts.

“We are building the future of software delivery for all enterprises by working closely and at a large scale with some of the best software teams in the world.” said Adam Murdoch, CTO and co-founder of Gradle Inc. “We see these teams increasing their investment in scaling up their automation pipeline to improve their ability to deliver software.”

With organizations running tens of thousands of builds a day, having a fast, maintainable, extensible build is required to be successful. Since build code has become a first-class citizen in software, the need for a statically-typed, elegant, high-performing build language with deep IDE integration led Gradle to embrace Kotlin.

**Learn More at Gradle Summit 2016**

Learn more about this development and much more at the Continuous Delivery conference of the year, Gradle Summit 2016 in Palo Alto CA June 23-24th. You can win a free ticket at [http://gradle.org](http://cts.businesswire.com/ct/CT?id=smartlink&url=http%3A%2F%2Fgradle.org&esheet=51343942&newsitemid=20160517006230&lan=en-US&anchor=http%3A%2F%2Fgradle.org&index=1&md5=0a36f28ca4ba005bd64f525e4c278adf).

**About Gradle**

Gradle Inc. is a Silicon Valley startup whose mission is to transform how software is built and shipped. Gradle Inc. develops, distributes and supports the Gradle Platform—consisting of the popular Gradle open source build tool and Gradle.com commercial SaaS service. With approximately 2 million downloads per month, Gradle is the most popular build automation system world-wide. Powered by a unique Build Programming Language, Gradle also boasts an ecosystem with over eight hundred plugins supporting all facets of software development and deployment. Gradle is at the heart of the Continuous Delivery pipelines of some of the most advanced software companies in the world including LinkedIn, Netflix, Unity Technologies and many more. Gradle helps enterprises ship software faster, with fewer defects, and more continuously by unifying and automating build processes. Gradle Inc. also provides training, consulting and support for its customers. Our motto is “Build Happiness”. Learn more at [gradle.org](http://cts.businesswire.com/ct/CT?id=smartlink&url=http%3A%2F%2Fgradle.org&esheet=51343942&newsitemid=20160517006230&lan=en-US&anchor=gradle.org&index=2&md5=3e96189fc8c662c318c35dfc5645b553) and follow us on Twitter at [@Gradle](http://cts.businesswire.com/ct/CT?id=smartlink&url=http%3A%2F%2Ftwitter.com%2Fgradle&esheet=51343942&newsitemid=20160517006230&lan=en-US&anchor=%40Gradle&index=3&md5=83692a7d6b9a8e7968410ff7e19a8df2).

**About JetBrains**

JetBrains s.r.o. is a technology-leading software development firm specializing in the creation of intelligent, productivity-enhancing software. It maintains its headquarters in Prague, Czech Republic, with its R&D labs located in St. Petersburg, Moscow, Munich and Boston. JetBrains employs over 600 people and is organically grown. Its product catalogue includes award-winning tools such as IntelliJ IDEA and ReSharper, and its IntelliJ Platform has been chosen by variety of companies to build their own tooling on, including Google's Android Studio. For more information, see [www.jetbrains.com](http://cts.businesswire.com/ct/CT?id=smartlink&url=https%3A%2F%2Fwww.jetbrains.com%2F&esheet=51343942&newsitemid=20160517006230&lan=en-US&anchor=www.jetbrains.com&index=4&md5=b00800605a6efcea3b36eed88b76c699).

"""

Article(
  title = "Gradle Elevates the Build to First-Class Programming With Kotlin Language",
  url = "http://www.businesswire.com/news/home/20160517006230/en/Gradle-Elevates-Build-First-Class-Programming-Kotlin-Language",
  categories = listOf(
    "Kotlin",
    "Gradle"
  ),
  type = article,
  lang = EN,
  author = "BusinessWire",
  date = LocalDate.of(2016, 5, 17),
  body = body
)
