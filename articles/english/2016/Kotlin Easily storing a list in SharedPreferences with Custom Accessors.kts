
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
SharedPreferences is at its core a simple tool with a deceptively verbose interface that weighs heavily on developer productivity. Kotlin on its own will not necessarily make Java-interfacing code pretty and concise, but you can apply a few particular concepts to create an elegant, downright easy-to-use solution to a common use-case:

**Storing a list of strings that will persist across app sessions.**

You can write code as if you have a regular old stored List<String> property, with the additional benefit that it is always in sync with SharedPreferences (*):

```kotlin
// Adding to a list
if (syncingSongIds.indexOf(song.id) == -1) {
    syncingSongIds += song.id
}

// Iterate through a list and do something with each element
syncingSongIds.forEach { id ->
    stopSyncing(id)
}

// Best of all: subtracting from a list
val intent = Intent(DONE_SYNCING)
intent.putExtra("song", song)
sendBroadcast(intent) // send a broadcast that we're done syncing

syncingSongIds -= song.id // and remove the song id from the persisted list of syncing songs
```

If you’re interested, read on.

I’m assuming you’re doing this all inside an object or class with access to a `context` property.

First of all, let’s set up easy access to the SharedPreferences file we’ll be reading/writing:

```kotlin
val sharedPreferences: SharedPreferences by lazy {
    // Replace with your code for accessing shared preferences
    // e.g. context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE)
}
```

For the uninitiated, the beauty of lazy properties (`by lazy`) is that they are initialized when you first access the property (i.e. when you first type `sharedPreferences`, it will call `context.getSharedPreferences(...)`) and stored from then on, so in this case, you will only ever call `context.getSharedPreferences(...)` once in the lifetime of your object.

*Skip this paragraph if you already know what a property without a backing field is*. In Java, instance variables are conventionally stored properties. In Kotlin, we get the option to provide completely custom accessors for a property without having any storage for the property. That means we can have a property called `count: Float` on a class `Counter` where the property is never actually **stored** on the Counter class. It’s very useful in instances where you want to have a property that wraps data stored somewhere else (database, server) without having to copy that data into the class containing the property. So the count property on Counter might actually be stored in (i.e., “backed by”) a file on disk: every time you increment the counter, it writes directly to the file on disk, and every time you retrieve the counter’s value, it reads directly from the file on disk. *While this particular example out of context is not a great idea as it incurs tons of I/O overhead, I feel it effectively illustrates the concept of a non-stored property*.

We are going to create a property that reads directly from, and writes directly to, SharedPreferences, using Kotlin’s syntax for custom accessors:

```kotlin
var syncingSongIds: List
    get() {
        val raw = sharedPreferences.getString("syncing_song_ids", null) ?: return emptyList()
        return raw.split(",")
    }
    set(value) {
        sharedPreferences.edit().putString("syncing_song_idslue.joinToString(",")).apply()
    }
```

That’s actually all you need to do for really simple strings that are guaranteed not to have commas in them.

**NB: This will only work for very simple strings. If you want to extend this functionality to be more powerful and accept any string or object type**, try using Google GSON or another JSON serialization library (JSONObject from the Android SDK is perfectly workable, if quirky) and replacing `raw.split(",")` and `value.joinToString(",")` with a JSON -> Object, and Object -> JSON conversion respectively.

**NB: Because we call `apply()` in the setter rather than `commit`, this code is relatively safe to call on the main thread but it does NOT have immediate consistency**. That is, if you call `syncingSongIds += "asdf"; print(syncingSongIds)` it may not include “asdf” in the printout. **To fix this, change `apply()` to `commit()`, but beware of incurring additional overhead** as the setter now has to write to disk synchronously every time it is called. It is your decision as to whether or not to call the setter on the main thread; I think it is relatively safe when done sparingly, but I would certainly keep an eye on that particular setter and make sure it doesn’t adversely impact performance in your app noticeably.

\* I’m not making any synchronicity guarantees–maybe as a part two, but ultimately if your code is not thread-safe, you should still modify and test these code snippets to fit your synchronicity needs.

"""

Article(
  title = "Kotlin: Easily storing a list in SharedPreferences with Custom Accessors",
  url = "http://blog.dennislysenko.com/2016/01/22/kotlin-easily-storing-a-list-in-sharedpreferences-with-custom-accessors/",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dennis Lysenko",
  date = LocalDate.of(2016, 2, 22),
  body = body
)
