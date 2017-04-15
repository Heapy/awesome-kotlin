
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
## How even a developer like me can build stunning cross-platform desktop apps (using Copy & Paste)

#### The problem

**UI design is hard**, very hard, especially for a developer like me.

I love to code, I always think about clever ways to tune and refactor my code.

At the same time I find building beautiful UIs so hard and frustrating that sometimes I wish I lived when the only way to interact with my app was a lovely black and white command line interface.

But then, I immediately remember I’ve been lucky enough to live (and code) in years in which **HTML is the glorious standard for web content creation.**

The best thing that could happen has already happened: **one common presentation “language” shared by everyone**.

The consequence is that, nowadays, you can find plenty of free online resources for creating stunning HTML5 UIs with little or no effort at all: tutorials, guides and libraries of already-made well-crafted UI widgets are available at your disposal.

**Want to start sketching up a shiny new project but no idea where to start with the UI?** Pick [Bootstrap](http://getbootstrap.com) and you’ll get, immediately, a lot of reusable UI components, already styled by professional designers.

Don’t like Bootstrap default theme? choose [another one](https://bootswatch.com).

Do you want to add some fancy interaction? Pick [React](https://facebook.github.io/react) and you’ll get the framework Facebook developers used for building fast, interactive UIs.

**All well and good until your customer needs a desktop application**: coming from the wonderful world of web development the risk of falling into deep depression becomes real.

In the world of desktop applications there is neither a common language nor a common technology stack to define user interfaces: billions of programming languages mixed with billions of UI toolkits.

**This heterogeneity of possible solutions leads to less documentation, less examples and most of these solutions aren’t even cross-platform at all**... to sum it up: a complete disaster!

Some solutions have been proposed to overcome this problem: Both the [NW.js](https://nwjs.io/) and the [Electron](http://electron.atom.io) projects let developers write HTML+Javascript code and run it as a desktop app.

These solutions are really valuable choices (ever heard of the [Atom](https://atom.io/) editor? :)), but, as you may guess, they’re not pure magic and have their own downsides.

Actually, your app will be packaged with a shrinked version of the chromium browser to make magic happens... The result? **A simple app built with NW.js/Electron has a size of at least 100MB!**

#### My solution

Luckily enough, it seems that (almost) everyone in this world has a JRE installed on his machine, and even more luckily it seems that JavaFX has a component called WebView that renders HTML5 content.

Hoorray! no more need to bundle a browser with my application! :)

So, **why not mix the power and conciseness of the brand new** [**Kotlin language**](https://kotlinlang.org/) **with all the benefits of HTML5 for UI creation?**

Let’s start now.

#### Your first HTML5 desktop app with Kotlin

We want to build an extremely simple PhoneBook desktop application.

Let’s start by defining a simple model for our contacts.

With Kotlin, this task translates to a one-liner, we can use a data class with four attributes: a name, a phone number, an email address and a physical address.

In the model class, we also add the logic to retrieve data: the “all” function may populate a list of Contacts, fetching data from literaly everywhere (text files, relational DBMS, NoSQL datasources, ...).

For the purpose of this example project we just create some fake data.

```kotlin
data class Contact(val name: String, val phone: String, val email: String, val address: String) {
    companion object {
        fun all() = listOf(Contact("Paul Black", "+39 334256789", "paul.black@example.com", "282 Kevin Brook Street, Imogeneborough"),
                           Contact("John Red", "+44 340556677", "john.red@example.com", "3316 Arron Smith Drive, New Roads"),
                           Contact("Ken White", "+32 39876544", "ken.white@example.com", "169 Ersel Street, Paxtonville"))
    }
}
```

We now need a function that, given a Contact, returns the HTML markup to render it.

In this project we will use Bootstrap to make things prettier and every Contact will be displayed inside its “panel”_._

As you can see, we use Kotlin String interpolation as a sort of template engine.

```kotlin
fun contactPanel(contact: Contact) =
\"\"\"
<div class="contact panel panel-info">
    <div class="panel-heading"> <h3 class="panel-title">${"$"}{contact.name}</h3> </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-3 col-lg-3" align="center">
                <img class="img-circle img-responsive"
                     alt="${"$"}{contact.name} pic"
                     src="${"$"}{HTML5View.resourceLink("/img/default-avatar.jpg")}" />
            </div>
            <div class="col-md-9 col-lg-9">
                <table class="table table-contact-information">
                    <tbody>
                        <tr> <td>Phone:</td> <td>${"$"}{contact.phone}</td> </tr>
                        <tr> <td>Email:</td> <td>${"$"}{contact.email}</td> </tr>
                        <tr> <td>Address:</td> <td>${"$"}{contact.address}</td> </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
\"\"\"
```

Making use of the above function, we should now build another function that, given a Contact list, returns the complete HTML view.

```kotlin
fun contactsView(contacts: List<Contact>) =
\"\"\"
<html>
    <head>
        <link rel="stylesheet" href="${"$"}{HTML5View.resourceLink("/css/bootstrap.min.css")}" />
        <link rel="stylesheet" href="${"$"}{HTML5View.resourceLink("/css/contacts.css")}" />
        <title>My contacts</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    ${"$"}{contacts.map { contactPanel(it) }.joinToString("\n")}
                </div>
            </div>
        </div>
    </body>
</html>
\"\"\"
```

As you may have already spotted in the HTML template, we need references to other static files such as images, css and javascript resources.

We use an helper method called “resourceLink” that solves this problem for us.

Let’s build this method into the class that is responsible to handle the window and Webview creation.

It’s really simple and takes advantage of the [Java resource system](http://docs.oracle.com/javase/7/docs/technotes/guides/lang/resources.html) to access static files that will be bundled inside the final executable Jar of our app.

```kotlin
fun resourceLink(path: String) = "${"$"}{HTML5View::class.java.getResource(path)}"
```

It’s time to take a look at the class that creates the JavaFX window and the WebView that will render our HTML and execute our Javascript code.

Using [TornadoFX](https://github.com/edvin/tornadofx), a lightweight JavaFX framework for Kotlin, building this class becomes really simple and natural: TornadoFX uses several language features from Kotlin to create JavaFX visual components with the most concise code as possible.

```kotlin
class HTML5View : View() {
    companion object {
        fun resourceLink(path: String) = "${"$"}{HTML5View::class.java.getResource(path)}"
    }

    override val root = WebView()
    init {
        with(root) {
            setPrefSize(800.0, 600.0)
            // Atomatically set the title of the window as the HTML document title
            titleProperty.bind(engine.titleProperty())
            // Show all the contacts
            engine.loadContent(contactsView(Contact.all()))
        }
    }
}
```

The last thing left to do is to create the “main” function that starts our application.

```kotlin
class HTML5App : App(HTML5View::class)

fun main(args: Array<String>) = Application.launch(HTML5App::class.java)
```

And that’s all the code needed for this simple PhoneBook application... Pretty easy right? :)

We can now use the “shadowJar” Gradle plugin to package a redistributable fat executable Jar with all the dependencies, and it weights only 4.6 MB!

![](https://cdn-images-1.medium.com/max/800/1*dJiZHivKunYuYpSI5NZfpg.png)

Starting form this example project, **it’s easy to extend this idea and build beautiful, lightweight and cross-platform applications** taking advantage of all the resources the web has to offer for creating HTML content.

You can find all the complete source code at [my Github account](https://github.com/lorenzo-ange).

[**lorenzo-ange/html5-desktop-app**](https://github.com/lorenzo-ange/html5-desktop-app) - _This is an example of a desktop application written with Kotlin and HTML5._

**Thanks for reading!**

"""

Article(
  title = "Write a lightweight, cross-platform HTML5 desktop app with Kotlin",
  url = "https://medium.com/@lorenzoangelini/write-a-lightweight-cross-platform-html5-desktop-app-with-kotlin-1033eb708800#.rt99y3jm1",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Lorenzo Angelini",
  date = LocalDate.of(2016, 12, 24),
  body = body
)
