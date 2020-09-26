
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
One of the great things happening right now in software development is the blending of functional and Object Ordained ideas. While things like Scala have been doing this for a while now, the explosion of popularity of things like Kotlin, Swift, and Rx are giving developers the best of both worlds. Hell, even JavaSript now has classes (I know JS lovers, classes are the devil).

The problem is that the benefits of functional practices don’t easily jump out to people. If you have taught in OOP your entire professional life it’s going to be hard to change. It is completely valid to see the benefits and not think it’s worth it, but if you don’t completely understand what the real benefits are you can’t make an educated decision.

The best example of this is is the Android world right now is Rx. This is a question that comes up a lot with Rx, “Yeah but... does it need to be a stream?” It’s a really important question. It’s like a levee breaking the relentless waves of “Use Rx! Rx everything!” For me, it’s also a mark of the failings of communication on the pro-Rx side.

Rx is all about reactive functional programming but the reactive part gets all the attention. It leads to a lot of misunderstanding, misuse, and mistrust of Rx. When you’re getting a list of items why not just return a list? Why would you need to return it as a stream? If you are told to look at Rx as reactive streams only, it’s easy to say you don’t need a stream. Just get the list.

The problem is that this leads to thinking of the list as an object with state, not just data. There is a good chance that once you get the list you are going to just pass it around to other classes. If one of them needs to sort or filter the list you are going to just call those methods on the object and change its state. But what about the other classes using it? They are expecting that list to be in a different state. Before you know it you have an out of control state.

However, if you think of it as a stream of data that you can run through a series of functions you can start to frame the true benefits. If you need to sort or filter the data instead of modifying the state of an object, ask for the data stream again this time running through a sort, or filter function. You are not just making that list a stream for the sake of making it a stream. You are doing it because it allows the use of more state safe practices in functional programming.

I want to show a simple example of how thinking functionally can help the quality of life in Android. If you are an Android developer, you have written a ton of RecyclerView Adapters. Despite the fact that most adapters are going to be pretty basic you have to create a whole new class. Why not just have a function that will do all the that work for us that we can just reuse when creating a simple adapter.

```kotlin
inline fun <T> simpleAdapter(data: List<T> = emptyList(), viewLayout: Int, crossinline bindViewHolder: (holder: RecyclerView.ViewHolder, item: T) -> Unit) = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(viewLayout, parent, false)) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindViewHolder(holder, data[position])
    override fun getItemCount(): Int = data.size
```

Notice the last parameter this function takes is another function that is invoked by the adapter when onBindViewHolder is called. Let’s look at how we use this function.

```kotlin
inline fun <T> simpleAdapter(data: List<T> = emptyList(), viewLayout: Int, crossinline bindViewHolder: (holder: RecyclerView.ViewHolder, item: T) -> Unit) = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(viewLayout, parent, false)) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindViewHolder(holder, data[position])
    override fun getItemCount(): Int = data.size
```

What this does besides saving time and class files is it restricts you from thinking of the adapter as a class. You can’t override all its methods and change everything about how they work to force it to work the way you think it should. You have no access to change the state of the data.

I know this sounds terrifying but remember we can be our own worst enemies a lot of the time. So building safety walls to stop ourselves can be a really good thing.

"""

Article(
  title = "Thinking Functionally",
  url = "https://medium.com/lewisrhine/thinking-functionally-7149814df9b9#.1vlgn9qm1",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Lewis Rhine",
  date = LocalDate.of(2016, 12, 7),
  body = body
)
