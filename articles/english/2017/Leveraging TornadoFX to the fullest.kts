
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Carl Walker recently wrote an insightful post on [TableView Binding in Kotlin](http://courses.bekwam.net/public_tutorials/bkcourse_tableselectapp_kotlin.html) where he uses [TornadoFX](https://github.com/edvin/tornadofx) to create a nice looking TableView with a couple of buttons that are disabled depending on the state of the currently selected item.

![](http://courses.bekwam.net/public_tutorials/images/bkcourse_tableselectapp_kt_nontax.png)

He also wrote the [same program in plain Java](https://fxdocs.github.io/docs/index.html#_tableview) and the post illustrates how TornadoFX can greatly reduce boiler plate code.

Coming from a strong JavaFX background, Carl's approach is very reasonable, but I'd like to show you that we can improve upon it even more by leveraging some more TornadoFX features.

TornadoFX is all about reducing boiler plate, while increasing readability and maintainability.

First I'll post the complete application as Carl created it, then I'll walk through each element I feel we can improve upon and finally post the full source for the modified app.

Here is the initial application without imports:

```kotlin
data class Item(val sku : String, val descr : String, val price : Float, val taxable : Boolean)

class TableSelectView : View("TableSelectApp") {

    private val items = FXCollections.observableArrayList(
        Item("KBD-0455892", "Mechanical Keyboard", 100.0f, true),
        Item("145256", "Product Docs", 0.0f, false),
        Item("OR-198975", "O-Ring (100)", 10.0f, true)
    )

    var tblItems : TableView<Item> by singleAssign()
    var btnInventory : Button by singleAssign()
    var btnCalcTax : Button by singleAssign()

    override val root = vbox {
        tblItems = tableview(items) {

            column("SKU", Item::sku)
            column("Item", Item::descr)
            column("Price", Item::price)
            column("Taxable", Item::taxable)

            prefWidth = 667.0
            prefHeight = 376.0

            columnResizePolicy = CONSTRAINED_RESIZE_POLICY

            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
        }
        hbox {
            btnInventory = button("Inventory")
            btnCalcTax = button("Tax")

            spacing = 8.0
        }

        padding = Insets(10.0)
        spacing = 10.0
    }

    init {

        btnInventory.disableProperty().bind( tblItems.selectionModel.selectedItemProperty().isNull )

        btnCalcTax.disableProperty().bind(
                tblItems.selectionModel.selectedItemProperty().isNull().or(
                    Bindings.select<Boolean>(
                            tblItems.selectionModel.selectedItemProperty(),
                            "taxable"
                    ).isEqualTo( false )
                )
        )
    }
}

class TableSelectApp : App(TableSelectView::class)
```

## Syntactic Sugar

We'll start with something simple and probably insignificant, but we want to attack every piece of boiler plate, so I'll mention it anyway.

In JavaFX, you're used to setting properties on objects as you create them. A typical example is the `spacing` property on `VBox` and `HBox` containers. The original sample does:

```kotlin
vbox {
    spacing = 8.0
}
```

The box builders take spacing as a parameter, so you can write `vbox(8.0)` or `vbox(spacing = 8.0)` if you prefer. OK, that was low hanging fruit, but bear with me.

Next up is actually a feature we added to TornadoFX, but that also has a shorthand alternative. Configuring the constraints inside a `VBox` can be done inside a `vboxConstraints` block, but when you only configure a single constraint you're better off just using this shorthand:

```kotlin
vgrow = Priority.ALWAYS
```

Let's tackle the last minor issue before we move on to bigger fish. Setting preferred width and height can be done in a single statement:

```kotlin
setPrefSize(667.0, 376.0)
```

## Builder Encapsulation

I have a golden rule I always try to follow: _Whenever possible, avoid references to other ui elements_. This reduces coupling, but more importantly it means that you will configure a single ui element in just one place. It's almost always less code as well. The original sample uses the `singleAssign` delegate that makes sure we only assign a value to the variable once. This is the original code:

```kotlin
var tblItems : TableView<Item> by singleAssign()
var btnInventory : Button by singleAssign()
var btnCalcTax : Button by singleAssign()
```

When these objects are created inside the builders, they are assigned to these variables:

```kotlin
tblItems = tableview(items)
btnInventory = button("Inventory")
btnCalcTax = button("Tax")
```

Later, in the `init` block of the class, these variables are configured further. The buttons gets their `disabledProperty` bound, and the `tblItems` is references from these bindings. This is the major issue with this code IMO.

The items are declared in one place, instantiated another and configured a third place in the code. That's three different places to look for how each of these elements are treated. We can actually change all this so everything is done in one place - inside the builder expression that created them. We can get rid of those `singleAssign` variable declarations, and make the binding expression much more concise in the process.

The reason this is needed in the original sample, is that the binding expressions work on the selectedItem of the TableView. We want to avoid that alltogether, so before we can clean up these variables, let's create a ViewModel.

## ViewModel

An `ItemViewModel` can wrap an instance of your domain object and gives you properties you can bind against which will stay valid even when the item it represents is changed. We want the view model to solve two issues for us:

* It should indicate which `Item` is currently selected in the table
* It should have a property for the `taxable` state of that item

The latter is used in the button bindings, so we need to expose that as a property we can bind to, and we need that binding to be the same even when the item changes.

The ItemViewModel can be defined like this:

```kotlin
class MyItemViewModel : ItemViewModel<Item>() {
    val taxable: BooleanProperty = bind { SimpleBooleanProperty(item?.taxable ?: false) }
}
```

The `MyItemViewModel` can contain an item of type `Item` in our case, and it has a `BooleanProperty` called `taxable`. This property will always reflect the state of the selected item, or null if false. Now this might look a bit verbose. That's because our domain object `Item` doesn't contain JavaFX properties. That's another thing I would probably change if I wrote this app from scratch, but we'll keep it, so you see how to bind against POJO properties. If `Item` contained JavaFX properties it would be much simpler:

```kotlin
val taxable = bind { item?.taxableProperty }
```

Since TornadoFX makes it so easy to create JavaFX properties, you should always do that when your domain objects are exposed to JavaFX Nodes. Now that we have our view model, we can inject it into the view:

```kotlin
val mySelectedItem: MyItemViewModel by inject()
```

Next, we can get rid of all the `singleAssign` statements, the variable assignments (`tblItems =` etc) and that whole `init` block at the bottom. Instead we will define the bindings directly inside the builders.

Inside the TableView builder we bind the selected state of the tableview to our view model:

```kotlin
bindSelected(mySelectedItem)
```

Now whenever the selection changes, the item inside our view model is updated, and the `taxable` property will reflect the state of the selected item. This gives us a chance to clean up the bindings.

## View Model Usage

The inventory button should be disabled when there is no selection in the table view. Now we can define everything in one place, and also leverage the `empty` property you get for free with the `ItemViewModel`:

```kotlin
button("Inventory") {
    disableProperty().bind(mySelectedItem.empty)
}
```

It's much easier to reason about the intent of this code, as you can almost read it as an English expression, and it's defined in one place, not three.

We do the same for the tax button, but here something magical happens:

```kotlin
button("Tax") {
    disableProperty().bind(mySelectedItem.empty.or(mySelectedItem.taxable.not()))
}
```

Whoa?! Remember how this used to look? All though it makes my skin crawl, I'll recite it for you:

```kotlin
btnCalcTax.disableProperty().bind(
    tblItems.selectionModel.selectedItemProperty().isNull().or(
        Bindings.select<Boolean>(
            tblItems.selectionModel.selectedItemProperty(),
            "taxable"
        ).isEqualTo( false )
    )
)
```

It's almost hard to believe that these two pieces of code actually have the same effect. The first one "reads" something like "Selected item is empty or selected item is not taxable". This you can infer in a two second glance. I don't think you can say the same for the `Bindings.select` expression we had originally.

The real power here comes from binding the `ItemViewModel` to the TableView, and I'm sure you agree now that it was most definitely worth it.

## Tying it all together

Below you'll find the modified code, as I feel it should be written using TornadoFX 1.5.9\. I would probably make two other adjustments, but I wanted it to be as close to the original sample as possible. I would rename `Item` to `Product` or something similar to avoid the unfortunate name clash with `ItemViewModel`, which is a TornadoFX construct, but more importantly I would create the properties as real JavaFX properties, utilizing the property delegates of TornadoFX. That would clean up that swearing inside the ItemViewModel as I demonstrated.

Here is the modified code:

```kotlin
data class Item(val sku: String, val descr: String, val price: Float, val taxable: Boolean)

class MyItemViewModel : ItemViewModel<Item>() {
    val taxable: BooleanProperty = bind { SimpleBooleanProperty(item?.taxable ?: false) }
}

class TableSelectView : View("TableSelectApp") {

    private val items = FXCollections.observableArrayList(
            Item("KBD-0455892", "Mechanical Keyboard", 100.0f, true),
            Item("145256", "Product Docs", 0.0f, false),
            Item("OR-198975", "O-Ring (100)", 10.0f, true)
    )

    val mySelectedItem = MyItemViewModel()

    override val root = vbox(10.0) {
        tableview(items) {
            column("SKU", Item::sku)
            column("Item", Item::descr)
            column("Price", Item::price)
            column("Taxable", Item::taxable)
            bindSelected(mySelectedItem)
            setPrefSize(667.0, 376.0)
            columnResizePolicy = CONSTRAINED_RESIZE_POLICY
            vgrow = Priority.ALWAYS
        }
        hbox(8.0) {
            button("Inventory") {
                disableProperty().bind(mySelectedItem.empty)
            }
            button("Tax") {
                disableProperty().bind(mySelectedItem.empty.or(mySelectedItem.taxable.not()))
            }
        }

        padding = Insets(10.0)
    }

}
```

## Conclusion

TornadoFX has a lot of features to simplify your UI code. You most certainly don't need to use them all, just use whatever portion you feel comfortable with, but know that if you ever write boiler plate in a TornadoFX app, you're probably not following best practices or we're missing something in the framework 🙂

As the syntax and features have progressed so much during 2016, we have a lot dated samples out there. We'll try to clean up as many as possible over the coming weeks, but the [TornadoFX Guide](https://edvin.gitbooks.io/tornadofx-guide/content/) is in pretty good shape already, and is one of the best resources to help you get started.

"""

Article(
  title = "Leveraging TornadoFX to the fullest",
  url = "https://edvin.town/leveraging-tornadofx-to-the-fullest/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Edvin Town",
  date = LocalDate.of(2017, 1, 2),
  body = body
)
