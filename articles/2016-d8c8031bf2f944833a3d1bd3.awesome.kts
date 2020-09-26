
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Using [@JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/) tag helps to avoid multiple constructors with Kotlin

## The headache

It’s really possible that you had some code like:

```kotlin
class MyCustomView : FrameLayout {
  
  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
  : super(context, attrs, defStyleAttr) {
    init()
  }

  fun init() { 
    // ...
  }
}
```

As you can see there are all the constructors needed for a _custom_ `FrameLayout` but all with same “body”.

![](http://blog.makingiants.com/assets/article_images/hmm.jpg)

# The remedy

Refactored with `@JvmOverloads`:

```kotlin
class MyCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
: FrameLayout(context, attrs, defStyleAttr){

  init {
    // ...
  }
}
```

## Thanks to

* [Kotlin discuss](https://discuss.kotlinlang.org/t/simple-constructors-for-inheritance/1874/2)
"""

Article(
  title = "KotlinLifeguard #1",
  url = "http://blog.makingiants.com/kotlin-lifeguard-1/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Daniel Gomez Rico",
  date = LocalDate.of(2016, 7, 28),
  body = body
)
