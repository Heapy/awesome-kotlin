
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Dependencies:

```gradle

// Android stuff...
dependencies {
    //...
    compile "org.jetbrains.kotlin:kotlin-stdlib:1.0.0"

    testCompile 'junit:junit:4.12'
    testCompile 'org.mockito:mockito-core:2.0.42-beta'
    testCompile('com.squareup.assertj:assertj-android:1.1.1') {
        exclude group: 'com.android.support', module: 'support-annotations'
    }
}

```

## Little Notes

* All the functions AND PROPERTIES should be open, by default functions and properties are final and mockito cant mock them.
* **DONT USE** spy functions make tests fail bc of some weird crash (*if someone had use them right comment!!*).

## Example

We will show:

1. `Settings`: manage settings storage, where to store them and which ones.
2. `SettingsView`: ...
3. `SettingsPresenter`: manage the bussiness logic for the settings.

You may have a class `Settings`:

```kotlin
open class Settings(context: Context) {
    val localCache = LocalCache(context)

    open var playJustWithHeadphones: Boolean
        get() = localCache.get("headphones", false)
        set(value) = localCache.put("headphones", value)
}
```

Then a presenter that use those settings:

```kotlin
class SettingsPresenter {
    private var mSettings: Settings? = null
    private var mView: SettingsView? = null

    fun onCreate(view: SettingsView, settings: Settings) {
        mView = view
        mSettings = settings

        view.setHeadphonesToggleCheck(settings.playJustWithHeadphones)
    }
}
```

Check that mocked Settings class **is open** and mocked var property **is open**

Then the tests passing:

```kotlin
class SettingsPresenterTests {
    @Mock lateinit var mockedView: SettingsView
    @Mock lateinit var mockedSettings: Settings
    lateinit var presenter: SettingsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SettingsPresenter()
    }

    @Test
    fun test_onCreate_updateGui() {
        Mockito.`when`(mockedSettings.playJustWithHeadphones).thenReturn(true)
        presenter.onCreate(mockedView, mockedSettings)

        Mockito.verify(mockedView).setHeadphonesToggleCheck(true)
    }
}
```

## Notes
* Using `lateinit` to let the variables be initialized on `@Before` and avoid using `?` or `!!` all over the tests.
* `SettingsView` and `Settings` are mocked using `MockitoAnnotations`
"""

Article(
  title = "Using Mockito for unit testing with Kotlin (1/x)",
  url = "http://makingiants.com/blog/using-mockito-for-unit-tests-with-kotlin-1x/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "MAKINGIANTS",
  date = LocalDate.of(2016, 2, 20),
  body = body
)
