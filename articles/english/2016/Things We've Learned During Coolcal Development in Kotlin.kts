
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Learning Kotlin is a pleasant process and only takes a few hours if you’re familiar with the Java programming language :)  The syntax is very intuitive, e.g. a when block is more readable than an if block or switch. At Netguru we had quite an adventure with Kotlin over the last few weeks while creating [the Coolcal app](https://play.google.com/store/apps/details?id=co.netguru.android.coolcal). [Here you can read about how we did it.](https://www.netguru.co/blog/coolcal-android-development-made-easy-and-fun) In this article I want to tell you more about what it’s like to learn Kotlin.

## Our Kotlin Adventure

The development process is much faster and hassle free with Kotlin. Look at the snippets below.

```kotlin
when (type) {
            WeatherCodes.CLEAR_SKY_DAY -> descArray[0]
            WeatherCodes.CLEAR_SKY_NIGHT -> descArray[1]
            else -> defaultMessage()
}
```

**When matches the argument against all branches until some branch condition is satisfied.** It can be an expression or a statement, as the expression value of the satisfied branch becomes the value of the whole expression; the statement values of individual branches are ignored.

Declaring a singleton object is done by simply adding the object keyword before the name of our object.

```kotlin
object InstancesLoader { //Implementaion}
```

Objects inside a class can be marked with companion object and can be called by using simply the class name as the qualifier.

```kotlin
companion object {
        val EVENT_DETAILS_BUNDLE = "event_details_bundle"
        }
//usage
ClassName.EVENT_DETAILS_BUNDLE
```

**In apps, we frequently need classes just to hold data; a lot of boilerplate code needs to be written.** But in Kotlin, everything is achieved by using data classes. The compiler automatically derives the necessary code.

```kotlin
data class Event(val id: Long,
                 val calendarId: Long,
                 val begin: Long,
                 val end: Long,
                 val isAllDay: Boolean = false,
                 val title: String? = null,
                 val displayColor: Int? = null,
                 val owner: String,
                 val location: String,
val description: String): Parcelable { //Parcelable implementation}
```

The code above implements members from primary constructor and necessary methods:

* `equals()`/`hashcode()` pair,
* `toString()`,
* `copy()`

Small things like no new keyword also improve the development process.

```kotlin
var adapter = EventAdapter(context, null, 0, this)
```

**The above snippet creates an instance of the EventAdapter class.** These features and many others make Kotlin a really enjoyable language.

Compared to Java, Kotlin is a really modern solution for implementation problems. **Null object handling is a big issue in Java, but Kotlin is a null type safety language** so we don’t have to worry about this, e.g

```kotlin
subscription?.unsubscribe()
```

The syntax above means that the variable subscription will execute the method unsubscribe if subscription isn’t null. This construction is very helpful and solves null handling problems.

What’s more - **when we need to check if a variable is null to operate on it after a null check, we don’t need to cast it to non-null type**; Kotlin supports smart cast operation.

```kotlin
if (dtStart == null || dtEnd == null) {
            return ""
        } else {
            return when (isAllDay) {
                true -> formatLongDateAllDay(dtStart)
                false -> formatLongDateNotAllDay(dtStart, dtEnd)
            }
}
```

In the example above dtStart and dtEnd were automatically cast to proper type.

According to [Effective Java](http://www.oracle.com/technetwork/java/effectivejava-136174.html), Item 17 (“Design and document for inheritance or else prohibit it”) **all classes should be final and Kotlin classes correspond to this statement.** This helps developers build a better code base.  

## Awesome libraries to browse

Awesome libraries exist in the Android developer community, and to give up on them would be painful. Fortunately Kotlin is 100% interoperable with Java so it’s possible to use popular Java libraries like Retrofit in Kotlin :)

```kotlin
@GET("forecast")
   fun getForecast(@Query("lat") latitude: Double,
                   @Query("lon") longitude: Double,
@Query("cnt") count: Long = 0): Observable<ForecastResponse>
```

If you are planning to code in Kotlin please take a look at these short tips. **Using higher-order functions in Kotlin creates additional memory allocations and runtime overhead, but we can remedy this problem using an inline modifier.** Remember, though, that inlining is not always possible or wanted - it causes generated code to grow. Listeners and callbacks are awful to write, but they can be clean and more readable thanks to lambda expressions .

```kotlin
eventIconClose.setOnClickListener { activity.finish() }
```

A nasty thing in Android is the findViewById function, but thanks to [Kotlin Android Extensions](https://kotlinlang.org/docs/tutorials/android-plugin.html) we can obtain the same functionality without this extra, awful code.

```kotlin
dayOfWeekTextView.text = timeFormatter.formatDayOfWeekShort(todayDt) 
```

Default values are very helpful - no more method overloading or creating several constructors to cut input parameters.

```kotlin
fun formatTimeOfDay(dt: Long?, isAllDay: Boolean = false)
```

We can use this method with only one parameter like formatTimeOfDay(dt)

**Sometimes a method or constructor has many parameters of the same type, which can be very error prone** - it’s easy to put the wrong value in the wrong place. To help developers beat this problem, language creators added named parameters. It’s worth using :)   

```kotlin
formatTime(timeMillis = i)
```

I is value of timeMillis parameter.

During the development process, three Netguru Android team members made commits to the code base. As it was an internal project we had very limited resources, commercial projects were always a priority. **This didn’t matter in terms of the quality or continuity of the project, as [our process](https://www.netguru.co/blog/development-process) makes it easier for a team member to join or leave.**

During development we found places to make code easier and more readable. We achieved this thanks to powerful language features. **Kotlin extension functions help us make our code easy to understand.** A good example of using this feature is getting a cursor value for a specific column. Writing this in Java for a generic field wouldn’t be as simple as in Kotlin.

```kotlin
inline fun <T> Cursor.from(columnName: String, getter: Cursor.(Int) -> T): T {
    val columnIndex = getColumnIndex(columnName)
return getter(columnIndex)
```

This method gets a value from cursor for a specific column name; usage of this method is even simpler.

```kotlin
var lastForecastSync: Long
        get() = preferences.getLong(PREF_FORECAST_SYNC, 0)
        set(value) {
            into(preferences){
                putLong(PREF_FORECAST_SYNC, value)
            }
        }
inline fun into(preferences: SharedPreferences,
                crossinline block: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    preferences.edit().block().apply()
}
```

```kotlin
fun Cursor.eventId() = from(_ID) { getLong(it) }
```

```kotlin
fun Cursor.eventDescription() = from(DESCRIPTION) { getString(it) }
```

Everything looks very intuitive and awesome. The next cool thing is using properties for handling shared preferences.

Using preferences implemented this way is very readable and clean.

<pre>appPreferences.lastForecast = response </pre>

The above code saves the response into lastForecast.

## Wrapping up

Kotlin is one of those languages whose processes are faster and less problematic than those of many other languages. I hope you will find my tips useful and that you will like [our showcase app - Coolcal](https://play.google.com/store/apps/details?id=co.netguru.android.coolcal). Please let me know what you think in the comments below. All feedback will be greatly appreciated.

"""

Article(
  title = "Things We've Learned During Coolcal Development in Kotlin",
  url = "https://www.netguru.co/blog/coolcal-development-in-kotlin",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Przemek Dąbrowski",
  date = LocalDate.of(2016, 7, 25),
  body = body
)
