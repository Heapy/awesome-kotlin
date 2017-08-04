
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin programming language now on everyone's lips. Some people seriously considered it as a full-fledged replacement of Java in Android development. It is a modern, statically-typed language aimed at trying to make the code simpler and clearer for everyone and bring long-awaited Java features to Android developers. Let's look at the history of that language, sort out the pros and cons of the language, as well as a look at an example and how you can start using Kotlin in current projects.


## Why Kotlin was created and by whom?

Creating of language began in 2010 at Jetbrains by two talented programmers - Andrew Breslav and Dmitry Zhemerov. These guys dreamed about removing those restrictions which are imposed on developers in Java 6: no streams, no lambda functions, no try-with-resources and etc. Kotlin creators wanted to invent null-safety language that could be more flexible and friendlier than Java. They provided a solution of so-called ‘one billion problem’: null by default. Many of these features have appeared only in the latest versions of Java but were implemented by Kotlin creators.

## A slight dive into Kotlin syntax

Starting to develop an Android application on Kotlin is not very difficult. Let's see how the declaration of variables looks.

```kotlin
var explicitName : String = "Kotlin" // Here we declare the type

var implicitName = "Kotlin" // In that case we declare a variable implicitly
```

Kotlin also allows you to declare immutable objects with keyword ‘val’.

It's time to tell you how null-safety in Kotlin works. In Java, when we need to check variable for null, we usually write block.

```kotlin
if (name != null) {
   name.length
}
```

In Kotlin, we can do the same thing in one line.

```kotlin
Name?.length
```

Let’s define the variable this way:

```kotlin
val name : String = null
```

The compiler will generate an error - there should be at least some value. Since Kotlin considers itself as null-safety language, that makes a bet on prevention of such situations during compilation.

If you're a fan of hardcore, it’s possible to compile the code, but I would not recommend to do it this way:

```kotlin
val name: String!! = null
```

## Collections

If you had an experience with developing on Java, there is nothing special in Kotlin. I would say that Kotlin simplified everything for us again. Instead of LinkedLists and ArrayLists, we have  Lists, Maps, etc.

Kotlin allows us to define immutable lists.

```kotlin
val list: List<String> = listOf("Kotlin", "is", "awesome")
list.add("though") // No such method, guys!
```

If you decided to use mutable list, you can use generic MutableList<T>:

```kotlin
val mutableList: MutableList<String> = mutableListOf("Kotlin", "is","awesome")
mutableList.add("though")
```


## Methods

Let’s write a simple method:

```kotlin
fun summarize(a: Int, b: Int) : Int {
    return a + b
}
```

The first argument is the name of the parameter, and then type. After the colon, we specify the return type, if required. Do not forget that Kotlin is very, very friendly, so with this elementary function, we can write it in one line.

```kotlin
fun summarize(a: Int, b: Int) : Int = a + b
```

Kotlin also provides an ability to use default parameters:

```kotlin
fun summarize(a: Int, b: Int = 20) : Int {
    return a + b
}
```

And yeah, you can write lambda-expressions.

## Classes

Classes and methods in Kotlin are final by default. We can construct classes in two ways: with primary and secondary constructors. The primary class constructor is a part of header. Also, it doesn’t have any code to execute. If we need to do some operations, we can use ‘init’ block.

```kotlin
class Cat(val name: String)  {
    init {
        println("We've just initialized Cat with ${"$"}{name}")
    }
}
```

Secondary constructor works, like in Java, without any difference.

```kotlin
class Dog: Animal {
    constructor(name: String) {
    }
}
```

As you can see, super method works like inheritance and it’s absolutely incredible.  

```kotlin
class Cat : Animal {
    constructor(name: String) : super(name) {
    }
}
```

## Data-classes

I’m sure, during development, you’re using POJO-objects that don’t contain any specific logic. Kotlin creators decided to make developers satisfied and now you can submit your model as a data-class.

```kotlin
data class Person(
    val firstName: String,
    val lastName: String
)
```

That’s all. All the necessary getters and setters are there by default.

If you want to use data class, just make an instance by this construction:

```kotlin
val person = Person("Paul", "Johnson")
```

We can also make changes based on initialized instance of data class using ‘copy’ method.

```kotlin
val newPerson = person.copy(first_name="Carl")
```

## Extension functions

Extension functions is an incredible replacement of many Utils classes which may have been in the separate package. Are you familiar with monkey-patching in Ruby? In Kotlin, that feature is called “extension functions” and it provides an ability to shorten your code the easiest way. Let’s see an example:

```kotlin
fun Context.showMeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
```

We have just replaced this ugly boilerplate construction with an elegant and short method.

## Enough of these tutorial-like snippets! Let’s get to the real code!

In March, within a few weeks, I wrote Munchkin Level Counter for use in the world-famous game. I decided to build an application with the usage of pattern proposed by Android-developer and Kotlin-lover, Antoine Leiva, who demonstrated how to build an application in the MVP architecture. It is even more reminiscent of VIPER popular architecture for developing iOS, which, in our version, deleted the letter R (Router).

We are constantly improving our application to provide the best user experience. We added dependency injection to Dagger and transferred all the code on Kotlin. We plan to use RxJava to eliminate the heaps of the implementation of interfaces that do not make the code  elegant nor beautiful for the perception of any developer.

Let's see how on the presenter class Toad on Kotlin.

```kotlin
public class DashboardPresenterImpl implements DashboardPresenter, DashboardInteractor.OnLoadPlayerListener {

   private DashboardView dashboardView;
   private DashboardInteractor interactor;

   public DashboardPresenterImpl(DashboardView dashboardView, DashboardInteractor interactor) {
       this.dashboardView = dashboardView;
       this.interactor = interactor;
   }

   @Override
   public void updatePlayerListItem(Player player, int position) {
       if (dashboardView != null) {
           interactor.updatePlayer(player, position, this);
       }
   }

   @Override
   public void onResume() {
       if (dashboardView != null) {
           interactor.loadPlayersList(this);
       }
   }

   @Override
   public void onFinished(ArrayList<Player> players) {
       if (dashboardView != null) {
           dashboardView.setItems(players);
       }
   }

   @Override
   public void onPlayerUpdated(Player player, int position) {
       if (dashboardView != null) {
           dashboardView.updatePlayerData(player, position);
       }
   }

   @Override
   public void onDestroy() {
       if (dashboardView != null) {
           dashboardView = null;
       }
   }

   @Override
   public void setGameFinished() {
       if (dashboardView != null) {
           interactor.setGameFinished();
       }
   }

   @Override
   public void insertStep(Player player) {
       if (dashboardView != null) {
           interactor.insertStep(player);
       }
   }
}
```

You can see that whenever we send the data through the presenter in the interactor, it works with business logic. If the user closes the application, and Activity is destroyed, we can be sure that the callback interactor will not appeal to DashboardView interface which will appeal to the reel to send data to the user's screen (View).

We have re-written this class in Kotlin. It can be achieved with plug-in for Android Studio, made by JetBrains, and will automatically convert the code from Java to Kotlin.

```kotlin
class DashboardPresenterImpl : DashboardPresenter, DashboardInteractor.OnLoadPlayerListener {

   private var interactor: DashboardInteractor
   private var dashboardView: DashboardView?

   constructor(dashboardView: DashboardView, interactor: DashboardInteractor) {
       this.dashboardView = dashboardView
       this.interactor = interactor
   }

   override fun updatePlayerListItem(player: Player, position: Int) {
       interactor.updatePlayer(player, position, this)
   }

   override fun onResume() {
       interactor.loadPlayersList(this)
   }

   override fun onFinished(players: ArrayList<Player>) {
       dashboardView?.setItems(players)
   }

   override fun onPlayerUpdated(player: Player, position: Int) {
       dashboardView?.updatePlayerData(player, position)
   }

   override fun onDestroy() {
       dashboardView = null
   }

   override fun setGameFinished() {
       interactor.setGameFinished()
   }

   override fun insertStep(player: Player) {
       interactor.insertStep(player)
   }
}
```

We were able to reduce the code by approximately 20 lines. We excluded checks at Null in three lines, which occupied an important place. When we turn to the interactor, we do not need to know about the status of the twist, so from this part of the code, it can be eliminated. It is much more important to know the state of the twist when we send a callback from interactor back to the presenter. We need to know the state of the twist and interface call methods that are implemented in the Activity code. As a result, the code looks cleaner, clearer and still performs its tasks.

Let's look at how we can improve the code in the Activiti.

We have an activity where we load game results in three fragments that display charts and a list of players with sorting by maximum points.

```kotlin
public class GameResultActivity extends AppCompatActivity implements GameResultView {

   public static final String TAG = LogUtil.makeLogTag(GameResultActivity.class);

   private GameResultPresenter presenter;
   private Toolbar toolbar;
   private ViewPager vpCharts;
   private TabLayout tlChartsTitle;
   private ChartsPagerAdapter vpChartsAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       presenter = new GameResultPresenterImpl(this, this);
       setContentView(R.layout.activity_game_result);
       toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       presenter.onCreate();
   }

   @Override
   public void loadChartFragments() {
       vpCharts = (ViewPager) findViewById(R.id.vp_charts);
       vpChartsAdapter = new ChartsPagerAdapter(getSupportFragmentManager(), this);
       vpCharts.setAdapter(vpChartsAdapter);
       vpCharts.setOffscreenPageLimit(3);
       tlChartsTitle = (TabLayout) findViewById(R.id.tl_charts_title);
       tlChartsTitle.setupWithViewPager(vpCharts);
   }

   @Override
   protected void onResume() {
       super.onResume();
   }

   @Override
   public void onBackPressed() {
       super.onBackPressed();
       presenter.onBackPressed();
       Intent intent = new Intent(this, PlayersListActivity.class);
       startActivity(intent);
       finish();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               onBackPressed();
               return true;
           default:
               break;
       }
       return false;
   }

   @Override
   protected void onStop() {
       super.onStop();
       presenter.onStop();
   }
}
```

You can see how an activity can look in Kotlin:

```kotlin
class GameResultActivity : AppCompatActivity(), GameResultView {

   companion object {
       val TAG = LogUtil.makeLogTag(GameResultActivity::class.java)
   }

   private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar? }
   private val vpCharts by lazy { findViewById(R.id.vp_charts) as ViewPager? }
   private val tlChartsTitle by lazy { findViewById(R.id.tl_charts_title) as TabLayout? }
   private val vpChartsAdapter: ChartsPagerAdapter = ChartsPagerAdapter(supportFragmentManager, this)

   private val presenter: GameResultPresenter = GameResultPresenterImpl(this, this)

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_game_result)
       setSupportActionBar(toolbar)
       supportActionBar?.setHomeButtonEnabled(true)
       supportActionBar?.setDisplayHomeAsUpEnabled(true)
       presenter.onCreate()
   }

   override fun loadChartFragments() {
       vpCharts?.adapter = vpChartsAdapter
       vpCharts?.offscreenPageLimit = 3
       tlChartsTitle?.setupWithViewPager(vpCharts)
   }

   override fun onBackPressed() {
       super.onBackPressed()
       presenter.onBackPressed()
       val intent = Intent(this, PlayersListActivity::class.java)
       startActivity(intent)
       finish()
   }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when (item.itemId) {
           android.R.id.home -> {
               onBackPressed()
               return true
           }
       }
       return false
   }

   override fun onStop() {
       super.onStop()
       presenter.onStop()
   }
}
```

Let's look what we have changed.

First, we have transferred the entire boilerplate-code findViewById View element code of the overridden method onCreate in properties, where we presented our view-components as "lazy" field. What does this mean? It means initialization happens as soon as we turn to this field in the onCreate. Second, we have transferred initialization of presenter and adapter for ViewPager.

## But how we can make it much better?

We can simply use Butterknife. There are no big changes in comparison with standard Android development with Java.

```kotlin
private val toolbar by bindView(R.id.toolbar)
private val vpCharts by bindView(R.id.vp_charts)
private val tlChartsTitle by bindView(R.id.tl_charts_title)
```

Looks better! Let’s see what Kotlin offers us to work with creating layouts.

## Anko

Typically, Android developers have to write their views in the form of the XML markup. The parsing of XML-markup spent processor resources which caused the applications to load each activity a little longer. Usage of Anko DSL with Kotlin gives an enormous advantage. We can simply turn XML markup in domain-specific language provided by Anko.

## Kotlin in action

Kotlin already being used in production. In May, I was able to listen to the presentation of two developers from the Belarusian startup, Juno. They built its application in new language. What interesting things did uncover? What is their stack? First, their application is made in the MVVM-architecture. Second, they use Dagger2, RxKotlin, Retrofit and Gson. The developers claim that they don’t completely use for-loops because of RxKotlin. Spek helps them to unit test their code.

## What’s next?

The developers of Kotlin are constantly improving their language to provide the best experience to developers. For now, we can say that the goals which have been set in the beginning of the development of Kotlin have been achieved. Kotlin makes you write a better, cleaner and safer code, provides long-awaited Java features, and lets you use Java-code along with Kotlin. These are the pros, but we have cons too: compile time is a little bit longer than in Java and some of library is not compatible with Kotlin. But it’s not a serious issue, because the most popular libraries are used by developers from all over the world. So, Kotlin is a great choice to start development for Android now.

"""

Article(
  title = "Getting started with Kotlin in Android development",
  url = "https://datarockets.com/blog/kotlin-in-android-development",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Chyrta",
  date = LocalDate.of(2016, 8, 30),
  body = body
)
