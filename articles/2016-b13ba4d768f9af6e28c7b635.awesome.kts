
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
One thing the majority of JVM developers find annoying is having to constantly restart their container. Make a small change, restart Tomcat/Jetty, wait 30+ seconds for code to build / compile (or several minutes in the case of large monolithic projects), see results; repeat.

Compared to Dart which auto-reloads your changes as soon as you hit save, this is very inefficient and kills productivity.

There's a commercial solution available called [JRebel](http://zeroturnaround.com/software/jrebel/) that aims to solve this problem, I took it for a test drive for a couple of weeks and managed to cut down several hours rapidly increasing my productivity and leaving me with a lot less time to spend on Hashnode.

![title here](https://res.cloudinary.com/hashnode/image/upload/v1458676272/k10afwmauljxts4kdek7.png)

Even though JRebel was saving me a lot of time and pretty much worked out of the box, I just couldn't justify the price tag - at almost $500 a year, converted to Rands, that's about the price of one month's rental in South Africa for a small flat - a bit heavy; all the time I'm saving, I'm paying over to them. Don't get me wrong, I enjoyed using JRebel, but the price tag is just too much when there are other solutions available.

There is an open-source alternative which works just as well for what I'm using it for - it's called [Spring Loaded](https://github.com/spring-projects/spring-loaded#readme). It probably won't cover everything that JRebel is doing, but I'm mostly busy in Spring, so it's perfect for my needs.

Download the [jar](http://repo.spring.io/release/org/springframework/springloaded/1.2.5.RELEASE/springloaded-1.2.5.RELEASE.jar) file, throw it somewhere where you won't accidentally delete it, open your `~/.bash_profile` if you're using OSX or on Linux it would typically be your `~/.bashrc` and add the following line followed by restarting your terminal:

`export MAVEN_OPTS="-javaagent:/absolute/path/Code/springloaded-1.2.5.RELEASE.jar -noverify"`

Now when you run your application using the maven jetty plugin or maven tomcat plugin (`mvn jetty:run`), every time you compile a class, it will be reloaded for you without having to do `Ctrl + C` and restarting `mvn jetty:run`. In Eclipse this will happen automatically if you have auto build turned on, in IntellJ, just assign a shortcut to the compile command (I'm using `Cmd + S`), once you're done with your changes, simply hit `Cmd + S` and you can immediately see your changes without restarts.

Happy coding!!

"""

Article(
  title = "How to Hot Deploy Java/Kotlin classes in Dev",
  url = "https://hashnode.com/post/how-to-hot-deploy-javakotlin-classes-in-dev-cim3u5oen00fnek53ho7q1t0v",
  categories = listOf(
    "Deploy",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jan Vladimir Mostert",
  date = LocalDate.of(2016, 3, 22),
  body = body
)
