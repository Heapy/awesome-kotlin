
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
I’ve been playing with Anko for a while now and I was curious what advantages Anko offered. So I did some performance tests.

![](https://cdn-images-1.medium.com/max/660/1*5ZzaJr-LterwFJUy-Mu40g.png)

_Skeuomorphism_

So I decided to migrate a quite advanced layout I used a while back to Anko. The layout contains a RelativeLayout container with 17 child ImageViews (all with a drawables using 9 slice), 1 SurfaceView as the viewfinder, 2 TextViews and a regular View. Which is still a quite decent layout since there are no nested containers.

### Why does Anko preform better?

XML Layouts are parsed at runtime. In other the XML needs to be retrieved form the assets, and the XmlPullParser needs to parse all the elements and create them one by one. The attributes need to be parsed and be set correctly. This all is overhead, but how much time is actually getting wasted?

![](https://cdn-images-1.medium.com/max/880/1*cZgL3CXQrkeAeW7WrzYcrg.png)

_Anko performance test_

I’ve run the test on 4 older devices, but devices that all Android Devs have to deal with every day. I ran the layout about 4 times on all devices running [DevMetrics](https://github.com/frogermcs/AndroidDevMetrics). And we see a staggering difference from 350% to 600%.

With the increasing requirements for design / animations and performance, I think we all care about how fast and good our application preform. So I hope to convince you that using Anko will not cost you a lot of effort.

Beside the fact that layouts are a lot faster due to avoiding overhead, we are now building are layouts at runtime so we can include any logic while building if wanted! So let’s recreate the master-detail sample from Android Studio with Anko.


```kotlin
class MainActivity : AppCompatActivity() {
  private var toolBar: Toolbar? = null
  private var container: ViewGroup? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    coordinatorLayout {
      fitsSystemWindows = true

      appBarLayout {
        toolBar = toolbar {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) elevation = 4f
        }.lparams(width = matchParent, height = actionBarSize())
      
      }.lparams(width = matchParent)

      container = frameLayout()
        .lparams(width = matchParent, height = matchParent) {
          behavior = AppBarLayout.ScrollingViewBehavior()
        }
    }
  }
}
```

_MainActivity_

So as you can immediately see, there is not so much different from writing layouts in Anko compared to xml. And we’re already taking advantage of building layouts in xml, we can do a compatibility check and set elevation depending on the OS version, compared to doing so in a separate xml layout folder.

But now we’re cluttering our MainActivity with all that Anko code, so let’s extract that. Anko offers a solution using `AnkoComponent` but, you still have to use `findViewById` and thus it also still requires casting. We can easily solve that.

```kotlin
interface ViewBinder<in T> {
    fun bind(t: T) : View
    fun unbind(t: T)
}
```

Let’s create an interface that offers a bind and an unbind method. Similar like you’d bind and unbind views with Butter Knife.

We can now easily extract our previous layout into a `MainLayout.kt` class.

```kotlin
class MainLayout : ViewBinder<MainActivity> {

  override fun bind(mainActivity: MainActivity): View =
    mainActivity.UI {
      coordinatorLayout {
        fitsSystemWindows = true

        appBarLayout {
          mainActivity.toolBar = toolbar {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) elevation = 4f
          }.lparams(width = matchParent, height = actionBarSize())
      
        }.lparams(width = matchParent)

        mainActivity.container = frameLayout()
          .lparams(width = matchParent, height = matchParent) {
            behavior = AppBarLayout.ScrollingViewBehavior()
          }
      }
    }.view

  override fun unbind(mainActivity: MainActivity) {
    mainActivity.container = null
    mainActivity.recycView = null
  }
}
```

```kotlin
public class MainActivity extends AppCompatActivity {

    LinearLayout container;
    RecyclerView recycView;
    FrameLayout detailContainer;
    
    private MainLayout mainLayout = new MainLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mainLayout.bind(this));
        ...
    }
}
```

And now our activity looks a lot neater doesn’t it? Not anything special going on here, aside of the fact that we can now just call `setContentView(mainLayout.bind(this))`, it will set the content view, and bind your views to your view fields. And as you can see, there is no need for `findViewById` nor is there a need for casting!

### Runtime layouts

If you ever wanted logic during building layouts, you’re going to love this.

```kotlin
configuration(orientation = Orientation.LANDSCAPE, smallestWidth = 700) {
  recyclerView {
    init()
  }.lparams(width = widthProcent(50), height = matchParent)
  
  frameLayout().lparams(width = matchParent, height = matchParent)
}

fun <T : View> T.widthProcent(procent: Int): Int =
  getAppUseableScreenSize().x.toFloat().times(procent.toFloat() / 100).toInt()
```

Let’s analyse the code from above, anko offers `configuration` which is basically a fancy `if` for checking runtime configuration with a nice syntax. Anko offers checking config for `screenSize, density, language, orientation, fromSdk, sdk, uiMode, nightMode, rightToLeft and smallestWidth`. So this layout DSL will run if the device is in Landscape and the screen width in landscape is 700dp.

We can now also easily calculate sizes, and the width here will be calculate at runtime and set for 50% of the screen width.

### Conclusion

Anko offers several solutions to the traditional way of building layouts in XML. It bypasses all overhead that Android deals with when building xml layouts. You don’t have to deal with `findViewById` nor with casting. And by building layouts at runtime you can add any logic you want. It could improve your MVVM setup, or you could make your layouts more dynamic by adding some logic. And this all for a very small cost, considering all the extreme effort we do for clean and high performing apps.

All code used in this example can be found on [github](https://github.com/nomisRev/FasterLayoutsWithAnko)

"""

Article(
  title = "400% faster layouts with Anko",
  url = "https://medium.com/@vergauwen.simon/400-faster-layouts-with-anko-da17f32c45dd#.okv8w4291",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Simon Vergauwen",
  date = LocalDate.of(2016, 11, 10),
  body = body
)
