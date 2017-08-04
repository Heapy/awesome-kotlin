
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
About a month ago I [posted an article](http://tomstechnicalblog.blogspot.com/2016/10/kotlin-for-data-science.html) proposing [Kotlin](http://kotlinlang.org/) as another programming language for data science. It is a pragmatic, readable language created by [JetBrains](https://www.jetbrains.com/), the creator of [Intellij IDEA](https://www.jetbrains.com/idea/) and [PyCharm](https://www.jetbrains.com/pycharm/). It has received growing popularity on Android and focuses on industrial use rather than experimental functionality. Just like Java and Scala, Kotlin compiles to bytecode and runs on the Java Virtual Machine. It also works with Java libraries out-of-the-box with no hiccups, and in this article I’m going to show how to use it with [Apache Spark](https://spark.apache.org/).  

Officially, you can use Apache Spark with [Scala](http://www.scala-lang.org/), [Java](https://java.com/en/), [Python](https://spark.apache.org/docs/0.9.0/python-programming-guide.html), and [R](https://spark.apache.org/docs/latest/sparkr.html). If you are happy using any of these languages with Spark, you likely will not need Kotlin. But if you tried to learn Scala or Java and found it was not for you, you might want to give Kotlin a look. It is a legitimate fifth option that works out-of-the-box with Spark.  

I recommend using [Intellij IDEA](https://www.jetbrains.com/idea/) as it natively includes Kotlin support. It is an excellent IDE that you can also use with Java and Scala. I also recommend using [Gradle](https://gradle.org/) for your build automation.  

> Kotlin is replacing Groovy as the official scripting language for Gradle builds. You can read more about it in the article _[Kotlin Meets Gradle](https://blog.gradle.org/kotlin-meets-gradle)_.

## Setting Up

To get started, make sure to install the following:  

*   [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Java JDK
*   [Intellij IDEA](https://www.jetbrains.com/idea/) - IDE for Java, Kotlin, Scala, and other JVM projects
*   [Gradle](https://gradle.org/gradle-download/) - Build automation system, download _Binary Only distribtion_ and unzip it to a location of your choice

You will need to configure Intellij IDEA to use your Gradle location. Launch Intellij IDEA and set this up in _Settings -> Build, Execution, and Deployment -> Gradle_. If you have trouble there should be plenty of walkthroughs online.  

Let’s create our Kotlin project. Using your operating system, create a folder with the following structure:  

```kotlin
kotlin_spark_project
   |
   └────src
         |
         └────main
               |
               └────kotlin
```

Your project folder needs to have a folder structure inside of it containing `/src/main/kotlin/`. This is important so Gradle will recognize this as a Kotlin project.  
Next, create a text file named `build.gradle` and use a text editor to put in the following contents. This is the script that will configure your project as a Kotlin project. You can read more about Kotlin Gradle configurations [here](https://kotlinlang.org/docs/reference/using-gradle.html).  

```gradle
buildscript {
    ext.kotlin_version = '1.0.5'
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${"$"}kotlin_version"
    }
}

apply plugin: "kotlin"

repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib:${"$"}kotlin_version"

    //Apache Spark
    compile 'org.apache.spark:spark-core_2.10:1.6.1'
}

```

Finally, launch Intellij IDEA and click _Import Project_ and navigate to the location of your Kotlin project folder you just created. In the wizard, check _Import project from external model_ with the _Gradle_ option. Click _Next_, then select _Use Local Gradle Distribution_ with the Gradle copy you downloaded. Then click _Finish_.  

Your workspace should now be set up with a Kotlin project as shown below. If you do not see the project explorer on the left press ALT + 1. Then double-click on the project folder and navigate down to the `kotlin` folder.  

![](https://i.imgur.com/YzcYoLW.png)  

Right click the `kotlin` folder and select _New -> Kotlin File/Class_.  

![](https://i.imgur.com/ocw0WNx.png)  

Name the file “SparkApp” and press _OK_. You will now see a `SparkApp.kt` file added to your `kotlin` folder. An editor will open on the right.  

## Using Spark with Kotlin

Let’s put our Spark usage in the `SparkApp.kt` file. Spark was written with Scala. While Kotlin does not work directly with Scala, it does have 100% interoperability with Java. Thankfully, `Spark` has a Java API by providing a `JavaSparkContext`. We can leverage this to use Spark out-of-the-box with Kotlin.  
Create a `main()` function below which will be the entry point for our Kotlin application. Be sure to import the needed Spark dependencies as well. In your `main()` function, configure your `SparkConf` and create a new `JavaSparkContext` off of it.  

```kotlin
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

fun main(args: Array<String>) {

    val conf = SparkConf()
            .setMaster("local")
            .setAppName("Kotlin Spark Test")

    val sc = JavaSparkContext(conf)
}
```

The `JavaSparkContext` provides a Java API to create Spark streams. Thankfully, we can use the excellent [Kotlin lambda syntax](https://kotlinlang.org/docs/reference/lambdas.html#lambda-expression-syntax) which the Kotlin compiler will translate into the needed Java functional types.  
Let’s turn a `List` of Strings containing alphanumeric text values separated by `/` characters. Let’s break these alphanumeric values up, filter only for numbers, and then find their sum.  

```kotlin
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import kotlin.reflect.KClass

fun main(args: Array<String>) {

    val conf = SparkConf()
            .setMaster("local")
            .setAppName("Kotlin Spark Test")

    val sc = JavaSparkContext(conf)

    val items = listOf("123/643/7563/2134/ALPHA", "2343/6356/BETA/2342/12", "23423/656/343")

    val input = sc.parallelize(items)

    val sumOfNumbers = input.flatMap { it.split("/") }
            .filter { it.matches(Regex("[0-9]+")) }
            .map { it.toInt() }
            .reduce {total,next -> total + next }

    println(sumOfNumbers)
}
```

If you click the Kotlin logo right next to your `main()` function in the gutter, you can run this Spark application.  

![](https://i.imgur.com/TSlEJT2.png)  

A console should pop up below and start logging Spark’s events. I did not turn off logging so it will be a bit noisy. But ultimately you should see the value of `sumOfNumbers` printed.  

![](https://i.imgur.com/nqhIM7u.png)  

## Conclusion

I will show a few more examples in the coming weeks on how to use Kotlin with Spark (you can also check out my [GitHub project](https://github.com/thomasnield/kotlin-spark-test)). Kotlin is a pragmatic, readable language that I believe has potential for adoption in Spark. It just needs more documentation for this purpose. But If you want to learn more about Kotlin, you can read the [Kotlin Reference](https://kotlinlang.org/docs/reference/) as well as check out [a few books](https://www.manning.com/books/kotlin-in-action) that are out there. I heard great things about the [O’Reilly video series on Kotlin](http://shop.oreilly.com/product/0636920052982.do) which I understand is helpful for folks who do not have knowledge on Java, Scala, or other JVM languages.  

If you learn Kotlin you can likely translate existing books and documentation on Spark into Kotlin usage. I’ll do my best to share my discoveries and any nuances I may encounter. For now, I do recommend giving it a look if you are not satisfied with your current languages.

"""

Article(
  title = "Using the Kotlin Language with Apache Spark",
  url = "http://tomstechnicalblog.blogspot.com.by/2016/11/using-kotlin-language-with-spark.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Thomas Nield",
  date = LocalDate.of(2016, 11, 30),
  body = body
)
