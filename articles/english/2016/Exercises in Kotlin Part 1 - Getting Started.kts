
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin is an exciting new language I have been working with for a couple of months now. I believe it has a lot of features going for it, and I think many will be benefited from learning a bit more about it. Hence this series of posts.

This post helps you with getting the necessary software installed on your desktop for compiling and running kotlin code

## Presumed Audience :

The reader is presumed to have a reasonable working knowledge of Java. While one of the reasons Kotlin is particularly exciting is because it is such a useful language for the Android platform, the early parts of this series shall focus on the desktop based JVMs. Since those learnings are common irrespective of whether you wish to use Kotlin for desktop/server or Android programming.

It is also assumed that the reader has working java development kit installed on his desktop. Most or all of the discussion about syntax will also likely compare Kotlin with Java.

## Information :

Some of the useful sites for Kotlin are

* [Kotlin Programming Language](https://kotlinlang.org/) : Language Home page.
* [Kotlin Reference](https://kotlinlang.org/docs/reference/) : Reference - Kotlin Programming Language
* [Intellij Idea](https://www.jetbrains.com/idea/) : IDE with built in kotlin support. From the same authors who bring you kotlin, ie. JetBrains
* [Kotlin Eclipse Plugin](https://github.com/JetBrains/kotlin-eclipse) : Kotlin plugin for eclipse. _Note: I haven't used it myself_

## Getting started using an IDE

_Note: if you do not prefer to use an IDE and are likely to code using text editors and build and run using command line, you can skip this section entirely and go to [Command Line Usage](#cmdline)_

Go to the Intellij idea page above and download the latest version. You can choose to download the community version. It has the necessary support for Kotlin. You may alternatively choose to use the Eclipse plugin described above, but I haven't used it so cannot help with getting it to work. Install the intellij idea ide and launch it. In case unsure how to do so, consult the [official guide](https://www.jetbrains.com/help/idea/2016.1/installing-and-launching.html) for downloading, installing and launching the ide.

Start a new project. Click on gradle in the left hand column. You should reach the new project dialog box as follows

![Intellij Idea New Project Dialog](http://blog.dhananjaynene.com/images/kotlin/kotlin-1-new-project.png)

I always use the gradle build tool, so I end up making the selections as shown. Select any appropriate JVM between 1.6 and 1.8 (built in one should do). You could use the dialog as shown above. You should next see a dialog box for groupid/artifactid as required by gradle (these mean the same thing as they do for maven, so fill up some group id eg. `com.example.kotlin` and artifactid eg. `learning`). The rest of this post assumes these two values, so if you use different values, please replace them appropriately in the code as necessary.

![Specify groupid and artifactid](http://blog.dhananjaynene.com/images/kotlin/kotlin-3-groupid-artifactid.png)

On the next two screens you could choose the defaults or customise as necessary

![Project Configuration](http://blog.dhananjaynene.com/images/kotlin/kotlin-4-project-config.png)

![Project Configuration 2](http://blog.dhananjaynene.com/images/kotlin/kotlin-5-project-configuration-2.png)

Since gradle defaults to maven like directory structures, let us create the directory necessary for our work using the groupid/artifactid used earlier and maven conventions. Select the "_learning_" folder at the top of the left pane, and select from main or right click menu, File -> New Directory and specify the directory as follows. (If the menu options File -> New Directory do not appear, you have selected the wrong "_learning_", select the one at the top of the left pane, not the one in the toolbar above it).

![Create source directory](http://blog.dhananjaynene.com/images/kotlin/kotlin-6-create-directory.png)

Similarly also create another directory for writing our test cases

![Create test directory](http://blog.dhananjaynene.com/images/kotlin/kotlin-7-create-test-directory.png)

You should see the appropriate packages for the main and test areas appear in the left hand pane as follows

![IDE View](http://blog.dhananjaynene.com/images/kotlin/kotlin-8-ide-view.png)

Right click on the package name under the src/main/kotlin directory and select "New Kotlin File/Class". It will pop up a dialog where you can enter helloworld

![New file dialog](http://blog.dhananjaynene.com/images/kotlin/kotlin-9-new-file-dialog.png)

This will create a new file "_helloworld.kt_" and open it into your ide

![New file](http://blog.dhananjaynene.com/images/kotlin/kotlin-10-new-file.png)

Enter the following code in the just opened file

```kotlin
package com.example.kotlin.learning

fun main(args: Array<String>) {
    println("Hello World!")
}
```

Run the program using "Run -> Run". It will pop up a very small dialog box, select HelloWorldKt from it. (You could also use Alt-Shift-F10 instead of Run -> Run, though not sure if the same key bindings work across operating systems, though thats what it is on Linux). This is the canonical Hello World program for Kotlin. Don't worry about understanding the syntax yet. We will get to it in the very next post, this post is to get you started.

One more thing to do is to get a sample test case running. So, let us add a function to the file we just created that we shall be testing, as follows

```kotlin
fun greetings() = "Hello World!"
```

Next, select the package in the src/test/kotlin directory, and after a right click select "New -> Kotlin File/Class". This time select the name as "_testgreet_", which will create a new file called _testgreet.kt_. Add the following code to the file.

```kotlin
package com.example.kotlin.learning

import org.junit.Test
import org.junit.Assert.assertEquals

class TestGreet() {
    @Test
    fun testGreetings() {
        assertEquals("Greeting should be 'Hello World!'",
                "Hello World!", greetings())
    }
}
```

Again select "Run -> Run..." or (Alt-Shift-F10 or equivalent). This will pop up a small dialog box. Select TestGreet. This will run the test case and should show an all green bar near the bottom of your IDE as shown in the image below.

![Kotlin Run Test](http://blog.dhananjaynene.com/images/kotlin/kotlin-11-run-test.png)

Now, we shall repeat the exercise using text editors and command line for building and running the program. Even if you _always_ use IDEs, I very much recommend that you at least do the remainder of the activities described on this post at least once. It is always helpful to have a general sense of what is happening at a command line level. However if you skip it, or it doesn't work for you for whatever reason, it won't constrain you from being able to continue with the rest of this series.

In either case, I shall not be covering the Intellij Idea IDE any further. If you are new to it or to the Eclipse plugin, learning it is beyond the scope of this series of posts, so I encourage you to play around with them, since that is likely to be useful as we go further along in this series.

## Installation for command line usage

The instructions for working with Kotlin from the command line are documented on the page [Working with the Command Line Compiler](https://kotlinlang.org/docs/tutorials/command-line.html)

Goto [Kotlin latest release](https://github.com/JetBrains/kotlin/releases/latest) to download the Kotlin compiler. It will likely be a file called `kotlin-compiler-<version>.zip`. The current version is 1.0.1-2 at the point in time this post was written. Unzip the file into an appropriate directory. And ensure that the executables kotlin and kotlinc (or their windows equivalent with the .bat extensions) are in your path.

Create a work directory for this exercise and change directory to it.

Create a file _helloworld.kt_ with the following content.

```kotlin
package com.example.kotlin.learning

fun main(args: Array<String>) {
    println("Hello World!")
}

fun greetings() = "Hello World!"
```

Compile it using the command

```bash
$ kotlinc helloworld.kt
```

Notice that it will have created a file _com/example/kotlin/learning/HelloworldKt.class_. This is the compiled _.class_ file. It is Java byte code though you wouldn't be able to run it as you might be used to just yet. In fact go ahead and try to run it as follows

```bash
$ java com.example.kotlin.learning.HelloworldKt
```

It will fail because you do not have the necessary kotlin runtime jar in your classpath. You should've seen an error message similar to the one below

```kotlin
Exception in thread "main" java.lang.NoClassDefFoundError: kotlin/jvm/internal/Intrinsics
        at com.example.kotlin.learning.HelloworldKt.main(helloworld.kt)
Caused by: java.lang.ClassNotFoundException: kotlin.jvm.internal.Intrinsics
        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at sun.misc.Launcher${"$"}AppClassLoader.loadClass(Launcher.java:331)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        ... 1 more
```

That runtime is located in the kotlin zip you had just downloaded. Let us point the classpath to it and attempt to run the program again. (Replace `<folder_where_you_unzipped_kotlin_zip>` below with the appropriate directory where you had unzipped the file you downloaded)

```bash
$ java -cp <folder_where_you_unzipped_kotlin_zip>/kotlinc/lib/kotlin-runtime.jar:. com.example.kotlin.learning.HelloworldKt
```

You should see "Hello World!" getting printed to the console. Kotlin provides an easier way to do this as well, so let us repeat the process, just a little differently.

Compile the source again, but this time create a .jar file

```bash
$ kotlinc helloworld.kt -include-runtime -d helloworld.jar
```

Now run it as follows

```bash
$ java -jar helloworld.jar
```

The program should run and "Hello World!" should get printed to the console.

Now let us run the test case. Create a file _testgreet.kt_ as follows

```kotlin
package com.example.kotlin.learning

import org.junit.Test
import org.junit.Assert.assertEquals

class TestGreet() {
    @Test
    fun testGreetings() {
        assertEquals("Greeting should be 'Hello World!'",
                "Hello World!", greetings())
    }
}
```

Now for compiling and running this you will need the jar for junit (which in turn requires hamcrest). The instructions for that are provided on the [Download and Install - junit4 wiki](https://github.com/junit-team/junit4/wiki/Download-and-Install) page. For now just go ahead and download the two following jars for junit and hamcrest.

* [junit jar](http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar)
* [hamcrest jar](http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-all/1.3/hamcrest-all-1.3.jar)

You will have downloaded _junit-4.12.jar_ and _hamcrest-all-1.3.jar_. Move both of them into your current directory after download.

```bash
$ kotlinc helloworld.kt -cp junit-4.12.jar -include-runtime -d helloworld.jar
```

```bash
$ java -cp junit-4.12.jar:hamcrest-all-1.3.jar:helloworld.jar org.junit.runner.JUnitCore com.example.kotlin.learning.TestGreet
```

You should see something like the follows (the time obviously could vary) that indicates success.

```kotlin
JUnit version 4.12
.
Time: 0.004

OK (1 test)
```

You should also be aware that Kotlin has a REPL that you can run using the command `kotlinc-jvm` as follows

```kotlin
$ kotlinc-jvm
Welcome to Kotlin version 1.0.1-2 (JRE 1.8.0_77-b03)
Type :help for help, :quit for quit
>>> println("Hello World!")
Hello World!
>>>
```

Congratulations you are now able to compile and run Kotlin programs. In the next post in this series, we shall attempt to understand the code that we just wrote, and explore the Kotlin language further.

"""

Article(
  title = "Exercises in Kotlin: Part 1 - Getting Started",
  url = "http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-1-getting-started/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dhananjay Nene",
  date = LocalDate.of(2016, 4, 18),
  body = body
)
