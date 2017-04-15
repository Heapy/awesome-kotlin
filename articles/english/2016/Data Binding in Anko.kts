
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

Ever since Google announced their data binding library there has been a debate in the community whether it is “the best thing since fragments” or “the worst thing since fragments”. (I knew you would see that coming, but I did it anyways)

When I first heard about it I was excited. I had been playing with AngularJS a lot at the time and I spent about an hour playing with the two-way data binding. Typing text in an input box and watching text in other places change as you type yelling, “Wheeeee, I love JavaScript!” Then I read more about how Google was implementing data binding and was horrified. Logic... In.... Your... X... M... L... Dun Dun Dun!

If you couldn’t tell from all those dramatic dots, I am on the “it’s bad” side of the binding argument. You can do some really cool things with it. But I just can’t get past having logic in your XML. In fact thinking about it more, why do anything in XML? It’s ugly, dumb, and I hate it.

Anko is a Kotlin library from Jetbrains that provides a lot of extension functions that make Android development much easier. One part of it leverages Kotlin’s ability to create a custom DSL to allow you to build out your views programmatically in a clean simple way. No XML needed. I have wanted to play with this feature of Anko for a while, besides the little bit I did when Kotlin was still in beta that is. So I thought it would be fun to try to build a simple data binding system we can use inside Anko.

First things first, I need a simple app with an Anko Component. I just used the example from Anko’s GitHub with some small name changes.

```kotlin
class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            val name = editText()
            button("Say Hello") {
                onClick { ctx.toast("Hello, ${"$"}{name.text}!") }
            }
        }
    }
}
```

And the Activity looks like this.

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }
}
```

I’m going to start simple by just trying to bind a string to a TextView. My first thought is to have a wrapper class that holds a property and has a hash map of bind functions that it invokes whenever the property is changed. This is what I came up with.

```kotlin
class Binder<T>(initValue: T) {
    private val bound: MutableMap<Int, (item: T) -> Unit> = HashMap()
    var item: T by Delegates.observable(initValue) { prop, old, new -> if (old  != new) bound.values.forEach { it(new) } }
    fun bind(id: Int, binding: (item: T) -> Unit) {
        bound.put(id, binding)
        binding(item)
    }

    fun unBind(id: Int) = bound.remove(id)
}
```

To simplify binding the data to views I wrote two extension functions that set the id based on the views. This means any view that has any bindings will need an id.

```kotlin
fun <T> View.bind(binder: Binder<T>, binding: (item: T) -> Unit) = binder.bind(this.id, binding)

fun <T> View.unBind(binder: Binder<T>) = binder.unBind(this.id)
```

I can come back to these guys at a later data and make them inline to increase performance but for now, I’m not going to worry about it.

I new up a Binder in the Activity passing it an initial string value.

```kotlin
private val bindText = Binder("Oh, Hi Mark.")
```

Then I add it as a property in the MainActivityUI constructor, add a TextView and bind a function that sets the text of that TextView. Like I said before, the TextView is going to need an id. There is probably a better way of doing it but I just set it to an Int of one.

```kotlin
class MainActivityUI(private val bindText: Binder<String>) : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            textView {
                Id = 1
                bind(bindText) { text = it }
            }
            val name = editText()
            button("Say Hello") {
                onClick { ctx.toast(“ Hello, ${"$"}{ name.text }!”) }
            }
        }
    }
}
```

Now whenever that item inside the Binder class is changed it will call that function and reset the TextView text. Also notice back inside the Binder class because we are using an observable delegate. It can check to see if the new string has the same value as the old one and only change it if is different. This saves the UI from needlessly setting the TextView if there was really no change in the value.

Now I want to be able to test that the data binding is working. In my Activity, I created a function to change the Binders text and map it to the button to simulate a change made from outside the UI.

In the Activity.

```kotlin
fun changeData(text: String) {
    bindText.item = text
}
```

And in the Component.

```kotlin
button("Say Hello") {
    onClick { (ctx as MainActivity).changeData("Hello Doggy") }
}
```

So now any time that string is changed it will automatically update the UI. Yay data binding! Now I want to make sure it can be unbound. First I added a new Binder to hold the state of whether or not to bind the data. Since we now have two Binders, I want to try to play with how a model would work. I want to use a pure data class because the are super nice things like models and I love them deeply.

```kotlin
data class Model(val bindText: Binder<String>, val bindOn: Binder<Boolean>)
```

I updated the Activity and Anko Component to use the model instead of just the string. Then I added a second button that binds to the new Boolean Binder.

```kotlin
button {
    id = 2
    bind(model.bindOn) { bindOn ->
        when (bindOn) {
            true -> text = "Binding On"
            false -> text = "Binding Off"
        }
        onClick { model.bindOn.item = (bindOn == false) }
    }
}
```

Now I added logic telling the TextView to bind and unbind based on bindOn

```kotlin
bind(model.bindOn) {
    when (it) {
        true -> bind(model.bindText) { text = it }
        false -> unBind(model.bindText)
    }
}
```

Ok, so I can now bind data to the view and unbind it when need be, even based on the binding of another piece of data like a Boolean. I can use pure data classes to do this without the need to use any annotations and generated classes. I’m pretty happy with it.

Now for some extra fun, I decided to add some two-way data binding. I added a textChangedListener to the editText and change the binders string onTextChanged.

```kotlin
val editText = editText().apply {
    textChangedListener {
        onTextChanged {
            charSequence, p1, p2, p3 ->
            bindText.item = “${"$"}charSequence”
        }
    }
}
```

And now I can type text in an EditText and watch text in textView change as I type... “Wheeeee, I love Kotlin!”

This was mostly a proof of concept. I’m sure there are better ways of doing this. I might come back to it and play around some more. If so I’ll be sure to make another.

The source code is up on my GitHub.

Thank you.

"""

Article(
  title = "Data Binding in Anko",
  url = "https://medium.com/lewisrhine/data-binding-in-anko-77cd11408cf9#.ym6ibevib",
  categories = listOf(
    "Kotlin",
    "Anko"
  ),
  type = article,
  lang = EN,
  author = "Lewis Rhine",
  date = LocalDate.of(2016, 12, 1),
  body = body
)
