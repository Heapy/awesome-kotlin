
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
In Android, every tutorial teaching you the basics describe how to design screen through XML files. It’s also possible to achieve the same result with Java (or any JVM-based language). Android screen design is not the only domain where XML and Java are valid options. For example, Spring configuration and Vaadin screen design allow both. In all those domains, there’s however a trade-off involved: on one hand, XML has a quite rigid structure enforced by an XML-schema while Java gives power to do pretty well anything at the cost of readability. “With great power comes great responsibility”. In the latter case, it’s up to the individual developer to exercise his/her judgement in order to keep the code as readable as possible.

[Domain-Specific Languages](https://en.wikipedia.org/wiki/Domain-specific_language) sit between those two ends of the spectrum, as they offer a structured syntax close to the problem at hand but with the full support of the underlying language constructs when necessary. As an example, the AssertJ library provides a DSL for assertions, using a fluent API. The following snippet is taken from its documentation:

```kotlin
// entry point for all assertThat methods and utility methods (e.g. entry)
import static org.assertj.core.api.Assertions.*;

// collection specific assertions (there are plenty more)
// in the examples below fellowshipOfTheRing is a List<TolkienCharacter>
assertThat(fellowshipOfTheRing).hasSize(9)
                               .contains(frodo, sam)
                               .doesNotContain(sauron);
```

DSLs can be provided in any language, even if some feel more natural. Scala naturally comes to mind, but Kotlin is also quite a great match. Getting back to Android, Jetbrains provides the excellent Anko library to design Android screen using a Kotlin-based DSL. The following snippet highlights Anko (taken from the doc):

<a name="anko"></a>

```kotlin
verticalLayout {
    val name = editText()
    button("Say Hello") {
        onClick { toast("Hello, ${"$"}{name.text}!") }
    }
}
```

There are two Kotlin language constructs required for that.

## Extension functions

I already wrote about extension functions, so I’ll be pretty quick about it. In essence, [extension functions](/extension-functions-for-more-consistent-apis/) are a way to add behavior to an **existing** type.

For example, `String` has methods to set in lower/upper case but nothing to capitalize. With extension functions, it’s quite easy to fill the gap:

```kotlin
fun String.toCapitalCase() = when {
    length < 2 -> toUpperCase()
    else -> this[0].toUpperCase() + substring(1).toLowerCase()
}
```

At this point, usage is straightforward: `"foo".toCapitalCase()`.

The important point is the usage of `this` in the above snippet: it refers to the actual string instance.

## Function types

In Kotlin, function **are** types. Among all consequences, this means functions can be passed as function parameters.

```kotlin
fun doSomethingWithInt(value: Int, f: (Int) -> Unit) {
    value.apply(f);
}
```

The above function can be now passed any function that takes an `Int` as a parameter and returns `Unit` _e.g._ `{ print(it) }`. It’s called like that:

```kotlin
doSomethingWithInt(5, { print(it) })
```

Now, Kotlin offers syntactic sugar when calling methods: if the lambda is the **last** parameter in a function call, it can be separated from the other arguments like this:

```kotlin
doSomethingWithInt(5) { print(it) }
```

## Putting it all together

Getting back to the [Anko snippet above](#anko), let’s check how the `verticalLayout { ... }` method is defined:

```kotlin
inline fun ViewManager.verticalLayout(theme: Int = 0, init: _LinearLayout.() -> Unit): LinearLayout {
    return ankoView(`${"$"}Anko${"$"}Factories${"$"}CustomViews`.VERTICAL_LAYOUT_FACTORY, theme, init)
}
```

As seen in the first paragraph, the `init` parameter is an extension function defined on the `_LinearLayout` type. `this` used in its context will refer to the instance of this latter type.

The second paragraph explained what represents the content of the braces: the `init` parameter function.

## Conclusion

Hopefully, this post show how easy it is to create DSL with Kotlin thanks to its syntax.

I developed one such DSL to create GUI for the [Vaadin](https://vaadin.com/) framework: the name is Kaadin and the result is [available online](https://nfrankel.github.io/kaadin/).

"""

Article(
  title = "Easy DSL design with Kotlin",
  url = "https://blog.frankel.ch/easy-dsl-design-with-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nicolas Fränkel",
  date = LocalDate.of(2016, 10, 30),
  body = body
)
