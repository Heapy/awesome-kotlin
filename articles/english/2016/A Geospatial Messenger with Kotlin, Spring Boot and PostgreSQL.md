---
title: 'A Geospatial Messenger with Kotlin, Spring Boot and PostgreSQL'
url: https://spring.io/blog/2016/03/20/a-geospatial-messenger-with-kotlin-spring-boot-and-postgresql
categories:
    - Kotlin
    - Spring
author: 'Sébastien Deleuze'
date: Mar 20, 2016 10:51
---

Following my first [Kotlin blog post](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin), today I want introduce the new Spring Boot + Kotlin application I have developed for my upcoming [Spring I/O 2016 conference](http://www.springio.net/) talk “Developing Geospatial Web Services with Kotlin and Spring Boot”.

# Dealing with native database functionalities

One of the goal of this application is to see how to take advantage of native database functionalities like we do in NoSQL world. Here we want to use Geospatial support provided by [PostGIS](http://postgis.net/), the spatial database extender for [PostgreSQL](http://postgresql.org/). [Native JSON support](https://www.compose.io/articles/is-postgresql-your-next-json-database/) could also be a good use case.

This Geospatial Messenger sample application is [available on GitHub](https://github.com/sdeleuze/geospatial-messenger) in 2 flavors:
- The `master` branch uses [Exposed](https://github.com/JetBrains/Exposed), a Kotlin SQL library with a typesafe API created by JetBrains. It could be compared to [Query DSL SQL](https://github.com/querydsl/querydsl/tree/master/querydsl-sql) or [jOOQ](http://www.jooq.org/) but provides an idiomatic Kotlin API and does not require code generation.
- The [`spring-data-jdbc-repository`](https://github.com/sdeleuze/geospatial-messenger/tree/spring-data-jdbc-repository) branch is using `spring-data-jdbc-repository`, a community project that allows to use Spring Data [`PagingAndSortingRepository`](https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html) API with raw SQL queries without JPA. I am using [this Jakub Jirutka fork](https://github.com/jirutka/spring-data-jdbc-repository/) which is an improved version of [Tomasz Nurkiewicz original project](https://github.com/nurkiewicz/spring-data-jdbc-repository).

A [Spring Data JPA + Hibernate Spatial variant](https://github.com/sebastianperruolo/spring-gis) would be interesting, so feel free to contribute it with a pull request ;-) Kotlin Query DSL support would be also nice but this is currently not supported (please comment on [this issue](https://github.com/querydsl/querydsl/issues/1828) if you are interested). In this blog post I will focus on the [Exposed](https://github.com/JetBrains/Exposed) variant.

# A tour of Geospatial Messenger code

Our domain model is described easily thanks to these 2 [Kotlin data classes](https://kotlinlang.org/docs/reference/data-classes.html):

```kotlin
data class Message(
    var content  : String,
    var author   : String,
    var location : Point? = null,
    var id       : Int?   = null
)

data class User(
    var userName  : String,
    var firstName : String,
    var lastName  : String,
    var location  : Point? = null
)
```

Exposed allows us to describe the structure of our tables with a type-safe SQL API quite handy to use (autocomplete, refactoring and error prone):

```kotlin
    object Messages : Table() {
        val id       = integer("id").autoIncrement().primaryKey()
        val content  = text("content")
        val author   = reference("author", Users.userName)
        val location = point("location").nullable()
    }

    object Users : Table() {
        val userName  = text("user_name").primaryKey()
        val firstName = text("first_name")
        val lastName  = text("last_name")
        val location  = point("location").nullable()
    }
```

It is interesting to notice that Exposed does not support natively PostGIS functionalities like geometry types or geospatial requests. That’s where [Kotlin extensions](https://kotlinlang.org/docs/reference/extensions.html) shine, and allow with a few lines of code to add such support without requiring to use extended classes:

```kotlin
fun Table.point(name: String, srid: Int = 4326): Column<Point>
  = registerColumn(name, PointColumnType())

infix fun ExpressionWithColumnType<*>.within(box: PGbox2d) : Op<Boolean>
  = WithinOp(this, box)
```

Our repository is also quite short and very flexible, since it allows you to write any kind of SQL request even with complex `WHERE` clause with a type-safe SQL API. Currently we need to use `db.transaction{ }` wrapper, I have created Exposed issue [#25](https://github.com/JetBrains/Exposed/issues/25) to be able to use regular [Spring transaction management](http://docs.spring.io/autorepo/docs/spring/4.2.x/spring-framework-reference/html/transaction.html) with [`@Transactional`](http://docs.spring.io/autorepo/docs/spring/4.2.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations) annotation, feel free to add your +1 ;-)

Please notice that since we are using Spring Framework 4.3, we [no longer need to specify an `@Autowired` annotation in such single-constructor class](https://spring.io/blog/2016/03/04/core-container-refinements-in-spring-framework-4-3#implicit-constructor-injection-for-single-constructor-scenarios).

```kotlin
@Repository
open class UserRepository(val db: Database) {

    open fun createTable() = db.transaction {
        create(Users)
    }

    open fun create(user: User) = db.transaction {
        Users.insert( toRow(user) )
    }

    open fun updateLocation(u:String, l: Point) = db.transaction {
        location.srid = 4326
        Users.update({Users.userName eq u}) { it[Users.location] = l}
    }

    open fun findAll() = db.transaction {
        Users.selectAll().map { fromRow(it) }
    }

    open fun findByBoundingBox(box: PGbox2d) = db.transaction {
        Users.select { Users.location within box }.map { fromRow(it) }
    }

    open fun deleteAll() = db.transaction {
        Users.deleteAll()
    }

    fun toRow(u: User): Users.(UpdateBuilder<*>) -> Unit = {
        it[userName] = u.userName
        it[firstName] = u.firstName
        it[lastName] = u.lastName
        it[location] = u.location
    }

    fun fromRow(r: ResultRow) =
        User(r[Users.userName],
             r[Users.firstName],
             r[Users.lastName],
             r[Users.location])
}
```

Controllers are also very concise and use Spring Framework 4.3 upcoming `@GetMapping` / `@PostMapping` annotations which are just method-specific shortcuts for `@RequestMapping` annotations:

```kotlin
    @RestController
    @RequestMapping("/user")
    class UserController(val repo: UserRepository) {

        @PostMapping
        @ResponseStatus(CREATED)
        fun create(@RequestBody u: User) { repo.create(u) }

        @GetMapping
        fun list() = repo.findAll()

        @GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
        fun findByBoundingBox(@PathVariable xMin:Double,
                              @PathVariable yMin:Double,
                              @PathVariable xMax:Double,
                              @PathVariable yMax:Double)
                = repo.findByBoundingBox(
                            PGbox2d(Point(xMin, yMin), Point(xMax, yMax)))

        @PutMapping("/{userName}/location/{x},{y}")
        @ResponseStatus(NO_CONTENT)
        fun updateLocation(@PathVariable userName:String,
                           @PathVariable x: Double,
                           @PathVariable y: Double)
                = repo.updateLocation(userName, Point(x, y))
    }
```

The client side is a pure HTML + Javascript application developed with [OpenLayers](http://openlayers.org/) mapping library (see [index.html](https://github.com/sdeleuze/geospatial-messenger/blob/master/src/main/resources/static/index.html) and [map.js](https://github.com/sdeleuze/geospatial-messenger/blob/master/src/main/resources/static/map.js) for more details) that geolocalizes you and creates geolocalized messages sent/received to/from other users thanks to Server-Sent Events.

![Screenshot](https://raw.githubusercontent.com/sdeleuze/geospatial-messenger/master/screenshot.png)

And last but not least, the REST API is fully tested and documented thanks to the awesome [Spring REST docs](http://projects.spring.io/spring-restdocs/) project, see [MessageControllerTests](https://github.com/sdeleuze/geospatial-messenger/blob/master/src/test/kotlin/io/spring/messenger/MessageControllerTests.kt) and [index.adoc](https://github.com/sdeleuze/geospatial-messenger/blob/master/src/main/resources/static/index.html) for more details.

# Conclusion

The main impression I had developing this application is that it was fun, efficient, with a high level of flexibility and safety provided by the SQL API and Kotlin type system and [null safety](https://kotlinlang.org/docs/reference/null-safety.html). The resulting Spring Boot application is a 18 MBytes self-contained executable jar with low memory consumption (the app can run with `-Xmx32m`!!!). Using Spring REST docs was also a pleasure, demonstrating again Kotlin nice Java interoperability.

The few pain points I have encountered ([array annotation attributes](https://youtrack.jetbrains.com/issue/KT-11235), [Java 8 Stream support](https://youtrack.jetbrains.com/issue/KT-5175), [full callable reference support](https://youtrack.jetbrains.com/issue/KT-6947)), are planned to be fixed in Kotlin 1.1. Exposed library is still young and need to mature, but from my point of view it is promising and shows how Kotlin could be used for building type-safe DSL API ([this HTML type-safe builder](https://kotlinlang.org/docs/reference/type-safe-builders.html) is also a good example).

And keep in mind that officially supported [Spring Data projects](http://projects.spring.io/spring-data/) works well with Kotlin as shown in the [spring-boot-kotlin-demo](https://github.com/sdeleuze/spring-boot-kotlin-demo) project in my [previous blog post](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin).

If you happen to be in Barcelona mid May (never a bad time to be in Barcelona anyway!), don’t miss the chance to join the [Spring I/O conference](http://www.springio.net/). Also, the registration for [SpringOne Platform](http://springoneplatform.io/) (early August, Las Vegas) has opened recently, in case you want to benefit from early bird ticket pricing. The latter is also still open for talk proposals. So if you’re interested to give a talk about Spring or Pivotal-related technologies, feel free to submit!
