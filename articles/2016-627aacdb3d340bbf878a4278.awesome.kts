
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Today we are happy to present the first BETA release of the Kotlin plugin for the NetBeans IDE.

The main features are:

* Building and Running Your Code
* Maven and Ant Support
* Java Interoperability
* Code Highlighting
* Diagnostics
* Code Completion
* Navigation
* Debugging
* Unit Testing
* Auto-Import
* Mark Occurrences
* Quick Search
* Code Formatting

## Installation

To give it a try you will need an installation of NetBeans 8.1. The beta version of Kotlin plugin is available from the [NetBeans Plugin Portal](http://plugins.netbeans.org/plugin/68590/kotlin).

Installation process:

1. Download Kotlin plugin
2. Launch NetBeans IDE
3. Choose **Tools** and then **Plugins** from the main menu
4. Switch to **Downloaded** tab
5. On the **Downloaded** tab click **Add Plugins...** button
6. In the file chooser, navigate to the folder with downloaded plugin. Select the NBM file and click OK. The plugin will show up in the list of plugins to be installed.
7. Click **Install** button in the Plugins dialog
8. Complete the installation wizard by clicking **Next**, agreeing to the license terms and clicking **Install** button.

## Using Kotlin in NetBeans

To start using Kotlin in NetBeans you could create a new Java project (Maven- or Ant-based) or open an existing one. You can mix Java and Kotlin freely, Java classes are accessible from Kotlin and vice versa. At the moment plugin supports Kotlin 1.0.3.

Here is a quick overview of the features.

### Diagnostics

[![diagnostics](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/diagnostics.png?zoom=1.5&resize=640%2C224&ssl=1)](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/diagnostics.png?ssl=1)

### Code Completion

[![completion](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/completion.png?zoom=1.5&resize=640%2C297&ssl=1)](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/completion.png?ssl=1)

### Navigation

From Kotlin to Kotlin:

[![navigationk2k](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationK2K.png?zoom=1.5&resize=640%2C160&ssl=1)](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationK2K.png?ssl=1)

From Kotlin to Java:

[![navigationk2j](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationK2J.png?zoom=1.5&resize=640%2C214&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationK2J.png?ssl=1)

From Java to Kotlin:

[![navigationj2k](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationJ2K.png?zoom=1.5&resize=640%2C213&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/navigationJ2K.png?ssl=1)

### Debugging

You can

* Set breakpoints
* Use Step in/out/over
* View local variables and contents of Kotlin objects

[![debugging](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/debugging.png?zoom=1.5&resize=640%2C486&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/debugging.png?ssl=1)

### Unit Testing

To write tests in Kotlin you should have JUnit in the classpath of your project.

[![junit](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/junit.png?zoom=1.5&resize=640%2C348&ssl=1)](https://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/09/junit.png?ssl=1)

### Auto-Import

[![autoimport](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/autoImport.png?zoom=1.5&resize=640%2C193&ssl=1)](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/autoImport.png?ssl=1)

### Mark Occurrences

[![occurrences](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/occurrences.png?zoom=1.5&resize=325%2C115&ssl=1)](https://i2.wp.com/blog.jetbrains.com/kotlin/files/2016/09/occurrences.png?ssl=1)

### Quick Search

[![quicksearch](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/quickSearch.png?zoom=1.5&resize=640%2C118&ssl=1)](https://i1.wp.com/blog.jetbrains.com/kotlin/files/2016/09/quickSearch.png?ssl=1)

### Feedback Welcome

Your feedback is very important. Feel free to add issues and feature requests in the [plugin issue tracker](https://github.com/Baratynskiy/kotlin-netbeans/issues).

Just like Kotlin itself, the NetBeans plugin is an open-source project, so your contributions can help it evolve faster.

"""

Article(
  title = "Kotlin NetBeans plugin beta is out!",
  url = "https://blog.jetbrains.com/kotlin/2016/09/kotlin-netbeans-plugin-beta-is-out/",
  categories = listOf(
    "Kotlin",
    "Netbeans"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2016, 9, 19),
  body = body
)
