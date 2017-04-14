---
title: 'PultusORM : Sqlite ORM for Kotlin'
url: https://medium.com/@sakibsami/https-medium-com-sakibsami-pultusorm-sqlite-orm-for-kotlin-2e01ed9ddcf6#.645u2wr6n
categories:
    - Kotlin
author: Sakib Sami
date: Oct 07, 2016 05:24
---
Few days back I was developing a Kotlin desktop application and needed a database system like Sqlite to store data. I started searching on google & github for a Sqlite ORM library and after spending few hours I figure out [some library](https://github.com/KotlinBy/awesome-kotlin#database) but no one fulfil my requirements. So I decide to write one by myself. As I am too lazy so I tried to develop something easier and straightforward. I don’t want the developer to write some queries and use the ORM system. So I develop the library keep in mind that not only me but everyone needs something easy, something more handy, something more flexible which will do everything. Ok I have started writing PultusORM a sqlite ORM library for Kotlin.

[PultusORM](https://github.com/s4kibs4mi/PultusORM) is an opensource project available on github under MIT license.

Installation is as easy as eating a slice of pizza ;) [Have a look](https://github.com/s4kibs4mi/PultusORM#usages).

If you are done with installation move forward to write some code & query some data.

Like other application you don’t need to write a model class extending any other class -_- I am too lazy to extend something :/ . So what do you have to do ? Just write a class having some properties (Must be primitive types till now). Initiate a connection with the database just by providing database name or alone with database path, everything else handled for you.

```kotlin
class Post {
  @PrimaryKey
  @AutoIncrement
  var postId: Int = 0
  var postTitle: String? = null
  var postAuthor: String? = null
}

val pultusORM: PultusORM = PultusORM("blog.db") // specifying path is optional
val post: Post = Post()
post.postTitle = "Welcome to Kotlin"
post.postAuthor = "Sakib Sami"

pultusORM.save(post)  // Table will be created on fly if not exists
```

Sometimes I feel like queries should be async which will give me a callback with execution result. Well you have that option in PultusORM. All queries having callback option are async. For this you just need to implement Callback interface and pass it as callback parameter, its that simple. Do you still think eating pizza is more easier than this ? Maybe not !!!

Ok tired of eating pizza ? need some drinks ? Lassi is my favourite. Maybe you don’t dislike it but you have other options. In PultusORM there are lots of option for data retrieval. You can fetch all data of a specific class. Or you can fetch based on some options. Options like **equal, not equal, and, or, in, between, less, greater, sort, group** are ready for you. Not enough !!! want some more mouthwatering ? Feel free to [create a issue](https://github.com/s4kibs4mi/PultusORM/issues/new).

Lunch time is so confusing.I think should I eat some pizza or rice !!! But I like both. So day by day I keep changing food items. PultusORM also provide you data update support using all the condition options above as well as callback option which is async.

After reading this article you may want to delete this as its too boring !!! In medium you can’t but PultusORM won’t disappoint you. You have option to delete data from data store.

If you wish you can see the full [menu card](https://github.com/s4kibs4mi/PultusORM/wiki). Hope you will love developing apps using it.

Repository : [https://github.com/s4kibs4mi/PultusORM](https://github.com/s4kibs4mi/PultusORM)

Wiki : [https://github.com/s4kibs4mi/PultusORM/wiki](https://github.com/s4kibs4mi/PultusORM/wiki)
