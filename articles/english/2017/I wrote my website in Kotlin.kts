
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
A few weeks ago I thought I should have a [website](http://lewisrhine.com/). Since I am an Android developer, I thought it should be in material design and because I’m a full on fanboy lunatic I thought it should be written on Kotlin.

If you didn’t know Kotlin can also compile to JavaScript. It’s still experimental but Jetbrain has said they are still committed to it. It’s something we should care about. In case you haven’t noticed JavaScript is taking over the world. While there are plenty of other JavaScript transpilers, if React Native gets as big as it seems I would rather write it in Kotlin over JavaScript.

For my site, Google’s [Material Design Lite](https://getmdl.io/) seemed like the easiest way to do it.

I whipped up a quick single page front-end framework named Kotlin Material Design Lite… Yeah, I know a boring name. The docs on Kotlin-JavaScript are slim, to say the least. So there is a good chance there are better methods than the ones I came up. It’s also very much not complete. It mostly just has the components I needed. But I do plan to keep working on it and any other contributions are welcome. If nothing else, it is a great way to learn more about Kotlin-JavaScript and be ready for when the tide of JavaScript drowns us all.

There are three main parts to KMDL.

```kotlin
fun mdlApp(init: MdlApp.() -> Unit): MdlApp {
    val app = MdlApp()
    app.init()
    return app
}

class MdlApp() {
    private val app = document.getElementById("MdlApp")

    init {
        requireNotNull(app) { "No MldApp Element found!" }
    }

    fun navigationLayout(content: MdlContent, cssClass: String = "", init: Layout.() -> Unit) {
        val nl = Layout(content, cssClass)
        nl.init()
        nl.mainElement.append(nl.content.content.mainElement)
        app?.append(nl.mainElement)
    }
}
```

This class looks for a div with an id of “MdlApp”. In Kotlin-JavaScript you interact with the DOM using “document” so when I call “document.getElementById(“MdlApp”)” it searches the HTML for an element with the id of MdlApp and returns it or null if it doesn’t find it.

The other main part is an abstract class MdlComponent. It’s what’s used to build out all the Material Design Lite Components.

```kotlin
abstract class MdlComponent(tag: String, classType: String, cssClassId: String = "") {
    val mainElement = document.createElement(tag).apply { this classType "${"$"}cssClassId ${"$"}classType" }

    var backgroundColor: MdlColor.Background? = null
        set(value) {
            value?.let { mainElement.setAttribute("class", mainElement.getAttribute("class")?.plus(" ${"$"}it")!!) }
        }

    var textColor: MdlColor.Text? = null
        set(value) {
            value?.let { mainElement.setAttribute("class", mainElement.getAttribute("class")?.plus(" ${"$"}it")!!) }
        }

    fun <T> htmlPram(parent: Element = mainElement): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
        private var prop: T? = null

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return prop ?: throw IllegalStateException("Property ${"$"}{property.name} should be initialized before get.")
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            prop = value
            set(property.name, prop!!)
        }

        private fun set(name: String, value: T) {
            parent.setAttribute(name, "${"$"}value")
            if (name == "href") parent.setAttribute("target" ,"_blank")
        }
    }

    fun htmlTextPram(text: String = "", parent: Element = mainElement): ReadWriteProperty<Any, String> = object : ReadWriteProperty<Any, String> {
        private var prop: String = text

        init {
            set(prop)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): String = prop

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            prop = value
            set(prop)
        }

        private fun set(text: String) {
            parent.textContent = text
        }
    }

    fun <T : MdlComponent> appendToMain(initItem: T) = object : ReadWriteProperty<Any, T> {
        var item = initItem

        init {
            mainElement.append(item.mainElement)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): T = item

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            mainElement.append(value.mainElement)
            item = value
        }
    }

    fun <T : MdlComponent> appendToMainIf(condition: Boolean, initItem: T) = object : ReadWriteProperty<Any, T> {
        var item = initItem

        init {
            if (condition) mainElement.append(item.mainElement)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): T = item

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            if (condition) {
                mainElement.append(value.mainElement)
                item = value
            }
        }
    }

    operator fun Element.unaryPlus() {
        mainElement.append(this)
    }
}
```

document.createElement is how Kotlin-JavaScript creates elements on the DOM. This class also makes heavy use of delegate properties to make creating the components attributes easier. For example the Img

```kotlin
class Img(cssClassId: String = "") : MdlComponent("img", cssClassId) {
    var src: String by htmlPram()
    var width: Int by htmlPram()
    var height: Int by htmlPram()
    var border:Int by htmlPram()
    var alt: String by htmlPram()
}

```

The src property uses the htmlPram delegate which uses the property’s name to set HTML attributes.

Now inside the main function just build out a MdlApp using the mdlApp function.

```kotlin
fun main(args: Array<String>) {
    val mdlApp = mdlApp {
        navigationLayout(About, "layout") {
            header {}
            drawer("drawer") {
                mainElement.header("drawer-header ${"$"}{MdlColor.Background.blueGrey(Shade.s300)}") {
                    img("avatar") { src = "images/roundprofile.png" }
                    b { textContent = "Lewis Rhine" }
                    append(document.createTextNode("Android Developer"))
                }

                nav("navigation") {
                    link { text = "About"; materialIcons = "account_circle"; onClick { content = About } }
                    link { text = "Blog"; materialIcons = "book"; href = "https://medium.com/lewisrhine" }
                    link { text = "Projects"; materialIcons = "build"; href = "https://github.com/lewisrhine" }
                    link {
                        text = "Twitter"
                        href = "https://twitter.com/lewisrhine"
                        mainElement.append(document.createElement("i").apply {
                            setAttribute("class", "material-icons fa fa-twitter")
                        })
                    }
                    link {
                        text = "Instagram"
                        href = "https://www.instagram.com/lewisrhine"
                        mainElement.append(document.createElement("i").apply {
                            setAttribute("class", "material-icons fa fa-instagram")
                        })
                    }
                    link {
                        text = "LinkedIn"
                        href = "https://www.linkedin.com/in/lewisrhine"
                        mainElement.append(document.createElement("i").apply {
                            setAttribute("class", "material-icons fa fa-linkedin")
                        })
                    }
                    link { text = "email"; href = "mailto:lewisrhine@gmail.com"; materialIcons = "email" }
                }
            }
        }
    }
}
```

The last part is the MdlContent.

```kotlin
fun content(title: String, cssClassId: String = "", body: Element.() -> Unit)  = Content(title, cssClassId, body)

class Content(val title: String, cssClassId: String = "", body: Element.() -> Unit) : MdlComponent("div", "mdl-layout__content", cssClassId) {
    init {
        mainElement.body()
    }
}

interface MdlContent {
    val content: Content
}
```

It’s used to build out content pages for the app.

```kotlin
object About : MdlContent {
    override val content = content("About") {
        setAttribute("style", " background: url('images/whoiam.jpg') center / cover; filter: alpha(opacity=60); padding: 10px;")

        grid {
            cell(3) {}

            cellCard(6) {
                title = "About me"
                supportingText = "Completely self-taught, I began my love for writing code when I was you kid and found out about QBasic on the family computer. In my day to day life, I enjoy keeping up with new developments within the technology and android community. I am very passionate about clean thought out architecture in the code I write. And believe strongly in testing as much as possible. Even on Android where it's not the easiest to accomplish."
            }

            cell(3) {}

            cell(1) {}
            cell(10) {
                chip(contact = true) { src = "images/java-logo.png"; text = "Java" }
                chip(contact = true) { src = "images/kotlin-logo.png"; text = "Kotlin" }
                chip(contact = true) { src = "images/android-logo.png"; text = "Android Native" }
                chip(contact = true) { src = "images/rxjava-logo.png"; text = "RxJava" }
                chip(contact = true) { src = "images/javascript-logo.png"; text = "JavaScript" }
                chip(contact = true) { src = "images/react-logo.png"; text = "React Native" }
                chip(contact = true) { src = "images/unity-logo.png"; text = "Unity3D" }
                chip { text = "Junit" }
                chip(contact = true) { src = "images/mockito-logo.png"; text = "Mockito" }
            }
            cell(1) {}

            cellCard(4) {

                size()
                title = "Rithmio"
                supportingText = "Mar 2016 — present"
                mainElement.list {
                    item(ListIem("-At Rithmio I Introduced new technologies like Kotlin and RxJava which have helped to make the team faster and more efficient."))
                    item(ListIem("-Rithmio EDGE: I designed a new architecture based on Flux style circular data streams that made the code base more testable and stable."))
                    item(ListIem("-Cadence Counter: I built a prototype app from the ground up that had a strict two-week window of completion. I was able to complete it in only a week giving the project an extra week for testing. "))
                }

            }

            cellCard(4) {
                size()
                title = "MeetBall"
                supportingText = "Jun 2015 — Mar 2016"
                mainElement.list {
                    item(ListIem("-At MeetBall I was the sole Android developer took over code base from a contractor and quickly moved the code a more structured testable state."))
                    item(ListIem("-Integrated Beacon awareness into the app using the Radius Networks SDK"))
                }
            }

            cellCard(4) {
                title = "Tinker Entertainment"
                supportingText = "Sep 2014 — Nov 2014"
                mainElement.list {
                    item(ListIem("While defunct, I picked up this project for my friend’s new company venture when his original developer dropped out. With the game’s code base written in C# and using the Unity3D framework, I quickly adapted myself to the language and provided an initial product release."))
                }
            }
        }
    }
}
```

Again the project, like Kotlin-JavaScript is still experimental but you can take a look at the code and you want to contribute [here](https://github.com/LewisRhine/Kotlin-Material-Design-Lite).

"""

Article(
  title = "I wrote my website in Kotlin",
  url = "https://medium.com/lewisrhine/i-wrote-my-website-in-kotlin-cc55263d2028#.k20k8snpp",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Lewis Rhine",
  date = LocalDate.of(2017, 1, 5),
  body = body
)
