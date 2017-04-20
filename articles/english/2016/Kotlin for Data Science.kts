
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
## Can Kotlin be an Effective Alternative for Python and Scala?

As I started diving formally into data science, I cannot help but notice there is a large gap between data science and software engineering. It is good to come up with prototypes, ideas, and models out of data analysis. However, executing those ideas is another animal. You can outsource the execution to a software engineering team, and that can go well or badly depending on a number of factors. In my experience, it is often helpful to do the execution yourself or at least offer assistance by modeling towards production.

Although Python can be used to build production software, I find its lack of static typing causes difficulty in scaling. It does not easily plug in with large corporate infrastructures built on the Java platform either. Scala, although an undeniably powerful JVM language, is rather esoteric and inaccessible for those who do not have a software engineering background. But [Kotlin](http://kotlinlang.org/), a new JVM language by [JetBrains](https://www.jetbrains.com/) (the creator of [Intellij IDEA](https://www.jetbrains.com/idea/), [PyCharm](https://www.jetbrains.com/pycharm/), and dozens of other developer tools), has an active community, rapid growth and adoption, and might serve as a more accessible alternative to Scala.

[Apache Spark](http://spark.apache.org/) is definitely a step in the right direction to close the gap between data science and software engineering, or more specifically, turning an idea into immediate execution. You can use [SparkR](https://spark.apache.org/docs/latest/sparkr.html) and [PySpark](http://spark.apache.org/docs/0.9.0/python-programming-guide.html) to interface R and Python with Spark. But if you want to use a production-grade JVM language, the only mainstream options seem to be Scala and Java. But Kotlin works out-of-the-box with Spark as well, since it is 100% interoperable with Java libraries.

## A Comparison Between Python and Kotlin

Let's take a look at a somehwat simple data analysis case study. We will leave Scala out and only compare Kotlin with Python. What I want to highlight is how Kotlin has the tactical conciseness of Python, and maybe even brings a little more to the table as a language for data analysis. Granted, there are not a lot of mainstream JVM data science libraries other than Apache Spark. But they do exist and perhaps there is room for growth. After all, Python caught up with R in a short time, and I am certainly not saying to drop your entire data analysis stack for Kotlin either. But the language may be worth keeping your eye on (and even exploring!).

This comparison was inspired by the social media example from the first chapter of [Data Science from Scratch](http://shop.oreilly.com/product/0636920033400.do) (Grus, _O'Reilly_). Let's start with declaring two sets of data, `users` and `friendships`. Using simply dicts, lists, and tuples without any classes, this is how it could be done in Python.

**Python**

```python
users = [ 
    { "id" : 0, "name" : "Hero" }, 
    { "id" : 1, "name" : "Dunn" }, 
    { "id" : 2, "name" : "Sue" }, 
    { "id" : 3, "name" : "Chi" }, 
    { "id" : 4, "name" : "Thor" }, 
    { "id" : 5, "name" : "Clive" }, 
    { "id" : 6, "name" : "Hicks" }, 
    { "id" : 7, "name" : "Devin" }, 
    { "id" : 8, "name" : "Kate" }, 
    { "id" : 9, "name" : "Klein" }, 
]
 
friendships = [ 
    (0,1), 
    (0,2), 
    (1,2), 
    (1,3), 
    (2,3), 
    (3,4), 
    (4,5), 
    (5,6), 
    (5,7), 
    (6,8), 
    (7,8), 
    (8,9)
]
```

The `users` is a `List` of `dict` items, and the `friendships` are a `List` of `Tuple` items. A feature of dynamic typing is you can be fast-and-loose creating data structures that maintain a raw data-like nature. There is no enforcement to uses classes or explicit types.

The equivalent to doing this in Kotlin would look like this:

**Kotlin**

```kotlin
val users = listOf(
    mapOf("id" to 0, "name" to "Hero"),
    mapOf("id" to 1, "name" to "Dunn"),
    mapOf("id" to 2, "name" to "Sue"),
    mapOf("id" to 3, "name" to "Chi"),
    mapOf("id" to 4, "name" to "Thor"),
    mapOf("id" to 5, "name" to "Clive"),
    mapOf("id" to 6, "name" to "Hicks"),
    mapOf("id" to 7, "name" to "Devin"),
    mapOf("id" to 8, "name" to "Kate"),
    mapOf("id" to 9, "name" to "Klein")
    )
 
 val friendships = listOf(
        listOf(0,1), listOf(0,2), listOf(1,2), listOf(1,3), listOf(2,3), listOf(3,4),
        listOf(4,5), listOf(5,6), listOf(5,7), listOf(6,8), listOf(7,8), listOf(8,9)
 )
```

For the `friendships`, we can actually create `Pair<Int,Int>` items. Kotlin does not really encourage Tuples (or any collection with differing types) and we will see what it offers instead later with the [`data class`](https://kotlinlang.org/docs/reference/data-classes.html). But let's use [Pairs](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/) in this example instead using the `to` operator.

```kotlin
val friendships = listOf(
        0 to 1, 0 to 2, 1 to 2, 1 to 3, 2 to 3, 3 to 4,
        4 to 5, 5 to 6, 5 to 7, 6 to 8, 7 to 8, 8 to 9
)
```

This may look effective at first glance, and Kotlin is statically typed. It is inferring the type for `users` and `friendships` as `List<Map<String,Any>>` and `List<List<Int>` respectively. Notice how `friendships` is a `List` containing `Map<String,Any>` items, meaning each item has a `String` for a key and an `Any` for the value. The reason for the `Any` is some values are `String` and others are `Int` (due to the "id" and "name"), and because the type is not consistent it cast them back down to `Any`. If we want to use Hero's "id", we need to cast it back up to an `Int` for it to be treated like an `Int` rather than a raw `Any`.

```kotlin
val herosId = users[0]["id"] as Int
```

Of course, if an "id" value is slipped in as a `String` accidentally this would throw an error. You can check if it is an `Int`, but at this point we are just fighting the statically-typed nature of Kotlin (just like Java and Scala). In Kotlin, we are much better off creating a `class` and doing things the statically-typed way. While this may make dynamic-typing advocates moan, check this out. Kotlin has a concise, readable way of declaring a class quickly and easily, even exceeding Python's standards

**Python**

```python
class User(Any):
    def __init__(self, id, name):
        self.id = id
        self.name = name
 
    def __str__(self):
        return "{0}-{1}".format(self.id, self.name)
 
users = [ 
    User(0,"Hero"), 
    User(1,"Dunn"), 
    User(2,"Sue"), 
    User(3,"Chi"), 
    User(4,"Thor"), 
    User(5,"Clive"), 
    User(6,"Hicks"), 
    User(7,"Devin"), 
    User(8,"Kate"), 
    User(9,"Klein"), 
]
```

**Kotlin**

```kotlin
data class User(val id: Int, val name: String)
 
val users = listOf(
      User(0, "Hero"),
      User(1, "Dunn"),
      User(2, "Sue"),
      User(3, "Chi"),
      User(4, "Thor"),
      User(5, "Clive"),
      User(6, "Hicks"),
      User(7, "Devin"),
      User(8, "Kate"),
      User(9, "Klein")
    )
```

Not too shabby, right? Technically, we did _less_ typing (as in keyboard typing) than Python (76 characters less to be exact, excluding spaces). And we achieved static typing in the process!

Kotlin is certainly a progressive language compared to Java, and it even has practical features like [data classes](https://kotlinlang.org/docs/reference/data-classes.html). We made our `User` a `data class`, which will automatically implement functionality typically used for classes holding plain data. It will implement `toString()` and `hashcode()`/`equals()` using the properties, as well as a nifty "copy-and-modify" builder by using a `copy()` function.

**Kotlin**

```kotlin
data class User(val id: Int, val name: String)
 
val user = User(10,"Tom")
val changedUser = user.copy(name = "Thomas")
 
println("Old user: ${"$"}user")
println("New user: ${"$"}changedUser")
```

**OUTPUT:**

```
Old user: User(id=11, name=Tom)
New user: User(id=11, name=Thomas)
```

> NOTE: In Kotlin, `val` precedes the declaration of an immutable variable. `var` precedes a mutable one.

Data classes are a valuable tool especially for working with data. And yes, Kotlin supports named arguments for constructors and functions as shown in the `copy()` function above.

Let's return back to our example. Say we wanted to find the mutal friends between two Users. Traditionally in Python, you would create a series of helper functions to assist in this task.

**Python**

```python
class User(object):
    def __init__(self, id, name):
        self.id = id
        self.name = name
 
    def __str__(self):
        return "{0}-{1}".format(self.id, self.name)
 
users = [ 
    User(0,"Hero"), 
    User(1,"Dunn"), 
    User(2,"Sue"), 
    User(3,"Chi"), 
    User(4,"Thor"), 
    User(5,"Clive"), 
    User(6,"Hicks"), 
    User(7,"Devin"), 
    User(8,"Kate"), 
    User(9,"Klein"), 
]
 
friendships = [ 
    (0,1), 
    (0,2), 
    (1,2), 
    (1,3), 
    (2,3), 
    (3,4), 
    (4,5), 
    (5,6), 
    (5,7), 
    (6,8), 
    (7,8), 
    (8,9)
]
 
def user_for_id(user_id):
    for user in users:
        if user.id == user_id:
            return user
 
 
 
def friends_of(user):
    for friendship in friendships:
        if friendship[0] == user.id or friendship[1] == user.id:
            for other_user_id in friendship:
                if other_user_id != user.id:
                    yield user_for_id(other_user_id)
 
def mutual_friends_of(user, otherUser):
    for friend in friends_of(user):
        for other_friend in friends_of(otherUser):
            if (friend.id == other_friend.id):
                yield friend
 
 
# print mutual friends between Hero and Chi 
 
for friend in mutual_friends_of(users[0],users[3]):
    print(friend)
```

**OUTPUT:**

```
1-Dunn
2-Sue
```

But we can do something similar in Kotlin.

**Kotlin**

```kotlin
fun main(args: Array<String>)  {
 
    data class User(val id: Int, val name: String)
 
    val users = listOf(
        User(0,"Hero"),
        User(1, "Dunn"),
        User(2, "Sue"),
        User(3, "Chi"),
        User(4, "Thor"),
        User(5, "Clive"),
        User(6, "Hicks"),
        User(7, "Devin"),
        User(8, "Kate"),
        User(9, "Klein")
        )
 
    
    val friendships = listOf(
            0 to 1, 0 to 2, 1 to 2, 1 to 3, 2 to 3, 3 to 4,
            4 to 5, 5 to 6, 5 to 7, 6 to 8, 7 to 8, 8 to 9
    )
 
    fun userForId(id: Int): User {
        for (user in users)
            if (user.id == id)
                return user
        throw Exception("User not found!")
    }
 
    fun friendsOf(user: User): List<User> {
        val list = mutableListOf<User>()
        for (friendship in friendships) {
            if (friendship.first == user.id)
                list += userForId(friendship.second)
            if (friendship.second == user.id)
                list += userForId(friendship.first)
        }
        return list
    }
 
    fun mutualFriendsOf(user: User, otherUser: User): List<User> {
        val list = mutableListOf<User>()
        for (friend in friendsOf(user))
            for (otherFriend in friendsOf(otherUser))
                if (friend.id == otherFriend.id)
                    list += friend
 
        return list
    }
 
    for (friend in mutualFriendsOf(users[0],users[3]))
        println(friend)
}
```

**OUTPUT:**

```kotlin
User(id=1, name=Dunn)
User(id=2, name=Sue)
```

Although Kotlin seems to have lost in this example by being wordier and resorting to Lists, hold on. Kotlin has no direct concept of [generators](https://wiki.python.org/moin/Generators) and `yield` keywords. However, we can accomplish something that fulfills the same purpose (and is arguably stylistically better) through `Sequence`.

```kotlin
fun main(args: Array<String>)  {
 
    data class User(val id: Int, val name: String)
 
    val users = listOf(
        User(0,"Hero"),
        User(1, "Dunn"),
        User(2, "Sue"),
        User(3, "Chi"),
        User(4, "Thor"),
        User(5, "Clive"),
        User(6, "Hicks"),
        User(7, "Devin"),
        User(8, "Kate"),
        User(9, "Klein")
        )
 
    
    val friendships = listOf(
            0 to 1, 0 to 2, 1 to 2, 1 to 3, 2 to 3, 3 to 4,
            4 to 5, 5 to 6, 5 to 7, 6 to 8, 7 to 8, 8 to 9
    )
 
    fun userForId(id: Int) = users.asSequence().filter { it.id == id }.first()
 
    fun friendsOf(user: User) = friendships.asSequence()
            .filter { it.first == user.id || it.second == user.id }
            .flatMap { sequenceOf(it.first,it.second) }
            .filter { it != user.id }
            .map { userForId(it) }
 
    fun mutualFriendsOf(user: User, otherUser: User) = friendsOf(user).flatMap { friend ->
        friendsOf(otherUser).filter { otherFriend -> otherFriend.id == friend.id }
    }
 
    mutualFriendsOf(users[0],users[3]).forEach { println(it) }
}
```

**OUTPUT:**

```
User(id=1, name=Dunn)
User(id=2, name=Sue)
```

We can use the `Sequence` to compose a series of operators as a chain, like `filter()`, `map()`, `flatMap()`, and many others. This style of functional programming has been getting a lot of traction over the past few years, primarily because it easily breaks up logic into simple pieces and increases maintainability. 99.99% of the time, I am never using `for` loops but rather using a Kotlin `Sequence`, a Java 8 `Stream`, or an RxKotlin/RxJava `Observable`. This chain-operator syntax is becoming less alien in Python as well (look at [PySpark](http://www.mccarroll.net/blog/pyspark/) and [RxPy](https://github.com/ReactiveX/RxPY)). What is great about this style of programming is you can read what is happening left-to-right, top-to-bottom rather than jumping through several loops and helper functions.

## Conclusions

In the coming months, I am going to blog about my experiences using Kotlin for data science, and I will continue to share what I learn. I may throw in an article occasionally covering [ReactiveX](http://reactivex.io/) for data science as well (for both Kotlin and Python). I acknowledge that the Java JVM platform, which Kotlin runs on, does not handle numbers as efficiently as Python or R (maybe [Project Valhalla will change that?](http://openjdk.java.net/projects/valhalla/)). But successful models inevitably need to turn into execution, and the Java platform increasingly seems to be the place that happens.

Kotlin merely provides a pragmatic abstraction layer that provides a tactical and concise syntax that seems excellent for data analysis. Not to mention, Kotlin has spurred many powerful open-source libraries even before a year after its release. One library, [TornadoFX](https://github.com/edvin/tornadofx), allows rapid turnaround of complex business user interfaces using Kotlin (As a disclaimer, I have helped with TornadoFX as well as [RxKotlinFX](https://github.com/thomasnield/RxKotlinFX)). The Kotlin community is incredibly active, growing, and engaged on the [Slack channel](http://kotlinslackin.herokuapp.com/). It [continues to be adopted on Android](https://www.raywenderlich.com/132381/kotlin-for-android-an-introduction), and JetBrains is using Kotlin to build all their tools (including PyCharm and Intellij IDEA). It is also [replacing Groovy as the official language for Gradle](https://blog.gradle.org/kotlin-meets-gradle). Because of these facts, I do not see Kotlin's momentum slowing down anytime soon.

I believe Kotlin could make a great first JVM language, more so than Java or Scala. There is a new [O'Reilly video series](http://shop.oreilly.com/product/0636920052982.do) that covers Kotlin from scatch, and its instructor Hadi Hariri (one of the JetBrains folks behind Kotlin) believes Pythonistas should be able to follow along. He said anybody familiar with classes, functions, properties, etc should be able to learn Kotlin in a day with this video series. Unfortunately, the existing [Kotlin documentation](https://kotlinlang.org/docs/reference/) and [books](https://www.manning.com/books/kotlin-in-action) assume prior Java knowledge, and hopefully more resources other than the video pop up in the future.

There is a lot of exciting features I have not covered about Kotlin in this article. Features like [nullable types](https://kotlinlang.org/docs/reference/null-safety.html), [extension properties and functions](https://kotlinlang.org/docs/reference/extensions.html), and [boilerplate-free delegates](https://kotlinlang.org/docs/reference/delegation.html) make the language pleasant to use and highly productive. So check out Kotlin if you are using Python or Scala for data science. I will continue blogging about my experiences, and showcase it being used in deeper data science topics as well as Spark.

"""

Article(
  title = "Kotlin for Data Science",
  url = "http://tomstechnicalblog.blogspot.com.by/2016/10/kotlin-for-data-science.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Thomas Nield",
  date = LocalDate.of(2016, 10, 28),
  body = body
)
