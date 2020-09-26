
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Hey hey, dear reader!

I'm [seeing how people](https://www.toptal.com/android/kotlin-boost-android-development) (including myself some time ago) tend to think that `val` in Kotlin means **immutable**. Unfortunately, this is not true 😞

Obviously, `val` can not guarantee that underlying object is **immutable**, that's clear.

> But can we say that `val` guarantees that underlying **reference** to the object is **immutable**?

No...

Kotlin allows you declare `get()` of the `val` which breaks immutability of the property and leaves only `read` permission for external "users".

Example:

```kotlin
class OhNo {

  val yeah: Int
    get() = Random().nextInt()

}

```

Things are especially sad when you declare `val` in an `interface`:

```kotlin
interface SomeService {  
  val user: User
}
```

**Until you check implementation** (which is not always possible) you can't be sure that value will be same each time you read it from the property.

Real world example is `List.size` from Kotlin stdlib.

#### How to live with that?

We have a **convention** (hopefully haha, at least I point to that in code reviews) internally in our team (100% Kotlin) to not declare properties like `val currentSomething: Something`, if value can be different each time you read it — please declare a function: `fun currentSomething()` or just `fun something()` because when it's a function then it's clear that resulting value can be different on each call. But for types that clearly represent "volatility" themselves, like `rx.Observable` it's ok to have them as `val`.

Good thing is that in `data class` you can't define `get()` for properties declared in the constructor, but you can do it for non-constructor properties.

> We've discussed that with [@abreslav](https://twitter.com/abreslav) and [@volebamor](https://twitter.com/volebamor) from Kotlin team, but looks like there is nothing Kotlin team can do about this at the moment. Would be great to have clear separation of immutable `val` and `readonly val`, but that's just dreams. Though I've opened an [issue](https://youtrack.jetbrains.com/issue/KT-13578) for highlighting that `val` has overriden `get()` in IDE.

Stay immutable!

"""

Article(
  title = "Kotlin: val does not mean immutable, it just means readonly, yeah",
  url = "https://artemzin.com/blog/kotlin-val-does-not-mean-immutable-it-just-means-readonly-yeah/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Artem Zinnatullin",
  date = LocalDate.of(2016, 8, 30),
  body = body
)
