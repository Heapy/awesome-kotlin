
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![kotlin_800x320](http://35gd0g2fpmpc2rmy8u1ld360.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/kotlin_800x320.png)

Even though we love our coffee here at OptimalBI, we have never had a place in our hearts for Java. Even so Java has a lot to offer for the modern cloud-driven world, but writing good, maintainable and human readable code has never been part of a Java developers life.
This is where [Kotlin ](https://kotlinlang.org/) comes into things. Kotlin is a statically typed language that runs on the JVM (Java Virtual Machine) which is designed to write better and safer Java-like applications that also have a certain level of human readability.

## Why do we need Java anyway?

At OptimalBI we do stuff in the cloud. This means that we have a large toolbox we can reach into to help us with a bunch of different tasks. As you might have seen from [Optimal Spyglass](https://github.com/OptimalBI/optimal-spyglass-open-source) Java was one of the tools we have used to create a reliable cross-platform GUI, using the always helpful AWS SDK. Java was chosen here so that support for all platforms could be offered easily without maintaining multiple versions[![Campagnolo_Tool_Kit_Super_Record_Wooden_Box_Nr._16](http://35gd0g2fpmpc2rmy8u1ld360.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/Campagnolo_Tool_Kit_Super_Record_Wooden_Box_Nr._16-300x225.jpg)](http://35gd0g2fpmpc2rmy8u1ld360.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/Campagnolo_Tool_Kit_Super_Record_Wooden_Box_Nr._16.jpg) of the same application. The issue here is that most of the code in Optimal Spyglass tend towards making Java’s life easier to run rather than developer’s lives easier to add features.
Being in the cloud sometimes means that we have a need for long-running jobs; Monitoring system health, SQL execution systems, etc. There is a strange Java shaped hole in our toolbox for these types of jobs, but despite fitting in the toolbox, Java never fitted in our hearts. We just never felt like battling the language to do what we wanted it to do. So up until recently we have jammed Python or NodeJS into the Java shaped hole and hoped that they would be quiet and help us out until we have a better fitting solution.

## What about the other traditional big chunky languages?

![Prog-languages](http://35gd0g2fpmpc2rmy8u1ld360.wpengine.netdna-cdn.com/wp-content/uploads/2016/05/Prog-languages-300x125.png)

Java is not the only language that can run long jobs without creaking, but all of these languages have some drawbacks, big or small.

**C#** is one of our favorite languages; it talks well to SQL Server, is has one of the easiest to read syntax for your traditional developer types, it compiles to a proper executable, it has a proper GC, it is easy to make services that run on windows, it has the best desktop/mobile GUI system ... and much more. All of that aside, C# has one big problem, Windows. When you run in the cloud you want your OS to quietly get on with managing the system and stay out of the way of your nicely performance tweaked code. Windows likes to poke his head in the door a little too much and then charges you license fees for the privilege. We like our code to run on Linux and C# doesn’t [(yet... reliably...)](https://dotnet.github.io/).

**C++** makes it too hard to produce a good system in the time-frames we have available to us. When you always have in the back of your mind that if you have a bad day in the office you could accidentally fill 64 GB of RAM with an array of Integers that you forgot to clear, it’s really hard to make good code quickly. Or at least for your average non-superhero based programmer. (Where is C++ man when I need him?)

[**Node.js**](https://nodejs.org) was the industry favorite child recently, but the JavaScript world is full of terrible code that no one _wants_ to pay to maintain. That being said with a proper development team Node.js is a very powerful tool. ([TypeScript](https://www.typescriptlang.org) anyone?)

There are many, more than this, but this blog is long as it is

There are many choices but from a functional point of view, Java still manages to keep its head above the ocean of programming languages.

## So what does this Kotlin thing do for us anyway?

> _“Kotlin is designed to be an industrial-strength object-oriented language, and to be a better language than Java but still be fully interoperable with Java code, allowing companies to make a gradual migration from Java to Kotlin.”_ – Andrey Breslav, Development Lead for Kotlin.

Kotlin is a statically typed language just like Java, that ends up as Java byte code that then can be run on the JVM. This means the outputs are .jar files as we would expect for a Java application.

One of Kotlin’s main selling points is that they are 100% interoperable with Java. This means that if you want a JSON serializer for your Kotlin application that you can just grab and run the rather useful Google GSON library and Kotlin will have no issues using it.

Another thing that Kotlin likes to brag about is its safety or mainly it’s system of avoiding NullPointerExceptions. Types in Kotlin have no ability to hold nulls (unless you ask really really nicely) which mean that there is no chance of accidentally asking for a null to split itself on “,”.

For us though the main thing Kotlin does is clean up the mess that is working with Java. Take the simple job of writing a line to the terminal for example. System.out.println(“Man this is annoying to do all the time”); becomes println(“Java don’t hurt me, no more!”);. This is easier to type, but also easier to read, makes developers happier. And a happy developer is a good developer.

There are many examples of why Kotlin helps us with writing modern cloud-based applications with Java level reliability (another blog post someday perhaps), all we know for now is we have found a better fit for that hole in our toolkit. Will it stick around? We will have to wait and see.

##### _Coffee to Code – Tim Gray_

##### _Tim blogs about the sharp end of code and the languages we write in._

"""

Article(
  title = "Kotlin, dragging java into the modern world",
  url = "http://optimalbi.com/blog/2016/05/20/kotlin-dragging-java-into-the-modern-world/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Tim Gray",
  date = LocalDate.of(2016, 5, 20),
  body = body
)
