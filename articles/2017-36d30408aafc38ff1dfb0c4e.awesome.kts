
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
I've been using Kotlin for close to 6 months now, and have been experimenting with quite a number of frameworks trying to find the combination that will allow me the most flexibility as well as great performance.

Since I've used [Spring Boot](http://start.spring.io/) previously, and ran into a few troubles after trying to do things which the framework didn't support, I steered clear of it. `Spring5`, which supports asynchronous operations, only reached Milestone 1 when I was in the market for a new framework. So, I parked it until it was stable enough for production use.

`DropWizard` looked like a decent option as well, considering its use of `Jersey`, `Jetty`, `Jackson`, and `Metrics`.

`JDBI` + `Liquibase` made for decent choices, to take care of the DB layer, but I needed `Hibernate` for the having to switch between `MySQL`, `SQLServer`, and `PostgreSQL`. Since a lot of my existing logging infrastructure depends on `Log4j`, having to hack around to see how I can replace `Logback`, did not seem like a good use of my time.

I've been sharing articles about `VertX` from time to time, including spectacular benchmarks, but haven't used it myself until now. So, I wanted to see how easy it would be to write a complete system in `Kotlin` + `VertX`, and how complex it would be, compared to the other alternatives I've listed above.

Maybe you can be the judge of the ease of the aforementioned setup. Let's start with a basic maven `pom.xml` in the root directory ...

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.hashnode</groupId>
    <artifactId>demo</artifactId>
    <packaging>jar</packaging>
    <version>1.0.0</version>
    <name>Kotlin Vertx Hibernate Demo</name>    
    <properties>

    </properties>
    <dependencies>

    </dependencies>
    <build>

    </build>       
</project>
```

For Kotlin support, we need the Kotlin Standard lib:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-stdlib</artifactId>
    <version>${"$"}{kotlin.version}</version>
</dependency>
```

... and the Kotlin Maven plugin:

```xml
<build>
    <plugins>
        <plugin>
            <artifactId>kotlin-maven-plugin</artifactId>
            <groupId>org.jetbrains.kotlin</groupId>
            <version>${"$"}{kotlin.version}</version>
            <configuration>
                <jvmTarget>1.6</jvmTarget>
            </configuration>
            <executions>
                <execution>
                    <id>compile</id>
                    <phase>process-sources</phase>
                    <goals>
                        <goal>compile</goal>
                    </goals>
                </execution>
                <execution>
                    <id>test-compile</id>
                    <phase>process-test-sources</phase>
                    <goals>
                        <goal>test-compile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>    
    </plugins>
</build>
```

We would be be using 1.0.5-2 for this demo:

```xml
<properties>
    <kotlin.version>1.0.5-2</kotlin.version>
</properties>
```

For VertX, we need VertX Core, and if we need to open websockets, or enable REST endpoints, we need VertX Web.

```xml
<dependency>
    <groupId>io.vertx</groupId>
    <artifactId>vertx-core</artifactId>
    <version>${"$"}{vertx.version}</version>
</dependency>
<dependency>
    <groupId>io.vertx</groupId>
    <artifactId>vertx-web</artifactId>
    <version>${"$"}{vertx.version}</version>
</dependency>
```

We would be using 3.3.3 for this demo (note that a Kotlin specific version of VertX will be released in early 2017, for now we're using VertX Java inside Kotlin)

```xml
<properties>
    <vertx.version>3.3.3</vertx.version>
</properties>
```

And that's most of the XML boilerplate - you can always switch to `gradle` if you prefer to get away from the `maven` boilerplate.

Now in your `src/main/java` directory (you can also use `src/main/kotlin` if you prefer, but that would require us to configure the Maven Kotlin plugin, to look for source files in that directory), create a `HashnodeDemo.kt` file and add the following code to the file:

```kotlin
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import java.io.IOException

object HashnodeDemo : AbstractVerticle() {

    // initiate logging system
    private val log = LoggerFactory.getLogger(HashnodeDemo.javaClass)

    @JvmStatic
    @Throws(IOException::class)
    fun main(args: Array<String>) {

        // setup verx
        val vertx = Vertx.vertx()
        val router = Router.router(vertx)

        router.get("/").handler { it.response().end(" Hello World :-) ") }

        // start vertx
        vertx.createHttpServer().requestHandler { router.accept(it) }.listen(9090)

    }
}
```

Now if you run this class from your IDE (I'm using IntelliJ, so for me it's `right-click` and `Run HashnodeDemo`), and open your browser at `localhost:9090`, you should see `Hello World :-)` in your browser.

So, with a single `pom.xml` file, and a single Kotlin `object` we've got a web server that can respond to `GET` requests. Nothing fancy yet, but as you would see further on, VertX mostly gets out of your way allowing you almost full control of the response going back.

Handling WebSockets can also be done with extra minimal code, but I'll cover that in more detail in a future post:

```kotlin
vertx.createHttpServer().websocketHandler { ws ->
    log.info("WebSocket Connected")
    ws.handler {
        // print content of request to logs
        log.info(it.toString())
        // write data back to browser
        ws.writeFinalTextFrame("Hello World")
    }
}.requestHandler { router.accept(it) }.listen(port)
```

CORS support can also be added with an extra couple of lines (this route should be added before any `GET`, `POST`, etc... routes). To allow cross-domain cookies, use `allowCredentials`

```kotlin
// handle CORS
router.route().handler(
    CorsHandler.create(origin)
        .allowCredentials(true)
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.POST)
        .allowedMethod(HttpMethod.OPTIONS)
        .allowedHeader("X-PINGARUNER")
        .allowedHeader("Content-Type"))
```

VertX, just like NodeJS, uses an event-loop, and at no point should the event-loop be blocked. If you do block it, expect lots of warnings in your logs, and a poor performance. Fortunately, VertX gives you a mechanism out of the box that will allow you to handle blocking requests with ease.

To demonstrate, let's add some ASCII art that will displayed when the application starts. In `src/main/resources`, create `ascii.txt` and add some ASCII art to it:

```
          _    _           _                     _        _____                       
         | |  | |         | |                   | |      |  __ \                      
         | |__| | __ _ ___| |__  _ __   ___   __| | ___  | |  | | ___ _ __ ___   ___  
         |  __  |/ _` / __| '_ \| '_ \ / _ \ / _` |/ _ \ | |  | |/ _ \ '_ ` _ \ / _ \
         | |  | | (_| \__ \ | | | | | | (_) | (_| |  __/ | |__| |  __/ | | | | | (_) |
         |_|  |_|\__,_|___/_| |_|_| |_|\___/ \__,_|\___| |_____/ \___|_| |_| |_|\___/

              Starting Hashnode Demo 1.0.0 ...

Just before `//start vertx`, add the following block of code:

            // show ascii art
            vertx.executeBlocking<String>({
                it.complete(InputStreamReader(javaClass.getResourceAsStream("/ascii.txt")).readText())
            }, {
                log.info(it?.result())
            })
```

During startup, the blocking call to the file-system would run in a worker pool while the rest of VertX continues to run asynchronously — best of both worlds; handling an asynchronous request, while still being able to make use of blocking calls in the background...

Now before you follow this tutorial and add `Hibernate` to your project, consider the vast array of options available. `Hibernate`, in my opinion, is the most feature-complete ORM out there, but that also makes it the heaviest ORM available. Other options include:

*   [JDBI](http://jdbi.org/) looks decent if you just want to write normal SQL queries, by hand, and need help in binding params and results
*   [VertX JDBC](http://vertx.io/docs/vertx-jdbc-client/java/) is very bare-bone, and will probably require lots of work on your side, depending on what you are looking for
*   [ReQuery](https://github.com/requery/requery) comes bundled with RxJava out of the box, and feels almost like `Hibernate`, but it's much more lightweight
*   [Falkon](https://github.com/jayrave/falkon) is another option if you like the DSL style
*   [JOOQ](http://www.jooq.org/) also comes highly recommended.
*   Kotlin [Kwery](https://github.com/andrewoma/kwery), I've had a look at it, but haven't used it yet.

To get started with `Hibernate`, let's add some more dependencies in our `pom.xml`:

```xml
<!-- Hibernate -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>${"$"}{hibernate.version}</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>${"$"}{hibernate.version}</version>
    <exclusions>
        <exclusion>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
        </exclusion>
        <exclusion>
            <groupId>dom4j</groupId>
            <artifactId>dom4j</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>${"$"}{hibernate.version}</version>
</dependency>
```

Let's use MySQL for this demo:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${"$"}{mysql.version}</version>
</dependency>
```

And while we're at it, let's add a connection pool as well, to reduce latency:

```xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-c3p0</artifactId>
    <version>${"$"}{hibernate.version}</version>
</dependency>
```

We would be using the latest versions of the MySQL driver, and Hibernate:

```xml
<properties>        
    <mysql.version>6.0.5</mysql.version>
    <hibernate.version>5.2.4.Final</hibernate.version>
</properties>
```

We would also need to setup the `persistence.xml` file at `src/main/resources/META-INF/persistence.xml`

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.1" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="default">
        <description>Persistence XML</description>
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>

            <!-- Hibernate Config -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL5InnoDBDialect" />
            <property name="hibernate.generate_statistics" value="false" />
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.ejb.naming_strategy" value="org.hibernate.cfg.ImprovedNamingStrategy"/>
            <property name="hibernate.connection.charSet" value="UTF-8"/>
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="false"/>
            <property name="hibernate.use_sql_comments" value="false"/>

            <!-- JDBC Config -->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/hashnodedb?useSSL=false" />
            <property name="javax.persistence.jdbc.user" value="username" />
            <property name="javax.persistence.jdbc.password" value="password" />

            <!-- Connection Pool -->
            <property name="hibernate.connection.provider_class"
                      value="org.hibernate.connection.C3P0ConnectionProvider" />
            <property name="hibernate.c3p0.max_size" value="5" />
            <property name="hibernate.c3p0.min_size" value="1" />
            <property name="hibernate.c3p0.acquire_increment" value="1" />
            <property name="hibernate.c3p0.idle_test_period" value="300" />
            <property name="hibernate.c3p0.max_statements" value="0" />
            <property name="hibernate.c3p0.timeout" value="100" />

        </properties>
    </persistence-unit>
</persistence>
```

The `hibernate.dialect` sets the flavour of SQL you are running. We would be using `MySQL` with `InnoDB` for this demo. `hibernate.hbm2ddl.auto` can be set to `validate`, to validate the database against the current database model; `update` will update your database based on your model; whereas `create-drop` will recreate your database effectively every time your restart.

Set `hibernate.hbm2ddl.auto` to `validate` when going to production, otherwise you might just drop / corrupt your production database.

The `JDBC Config` is simply where you configure the location of your database, username, and password.

The `Connection Pool` section allows us to setup a connection pool, in this example we set it to use only 5 connections at maximum. If your application is the only application connecting to the database, you can set that close to the maximum number of connections your database can handle. Just remember that those connections will be kept open as long as the application is running.

Let's create a few `Hibernate` models — first, let's create a base model so that we don't have to re-declare all the basic fields in all the models we create. Create a new directory `src/main/java/com/hashnode/demo/model`

Now, let's create the base model in `StandardEntity.kt`:

```kotlin
package com.hashnode.demo.model

import java.util.*
import javax.persistence.*

@MappedSuperclass
open class StandardEntity {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null

    @Version
    @Column(name = "version")
    private var version: Int = 0

    @Temporal(TemporalType.TIMESTAMP)
    var created: Date = Date()

    @Temporal(TemporalType.TIMESTAMP)
    var modified: Date = Date()

    @PreUpdate
    protected fun onUpdate() {
        this.modified = Date()
    }

}
```

Notice the `@MappedSuperclass`. If you want to extend a base model in `Hibernate`, that base model should be annotated with `@MappedSuperclass`, otherwise extending the base model won't work.

Let's create another entity in `Company.kt`

```kotlin
package com.hashnode.demo.model

import com.hashnode.demo.model.StandardEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.Table

@Entity
@Table(name = "Companies")    
class Company(

    @Column(unique = true)
    var name: String = ""

) : StandardEntity() {

    fun getCompanyByName(em: EntityManager, name: String): Company {
        val query = em.createQuery("SELECT o FROM Company AS o WHERE o.name=:name", Company::class.java)
        query.setParameter("name", name)
        return query.singleResult
    }

}
```

Next we bootstrap `Hibernate` in our main class, `HashnodeDemo.kt`. First let's add a `lateinit` variable, just after we initiate logging:

```kotlin
object HashnodeDemo : AbstractVerticle() {

    // initiate logging system
    private val log = LoggerFactory.getLogger(HashnodeDemo.javaClass)
    lateinit var emf: EntityManagerFactory
```

`lateinit` indicates that we don't want the variable to be `null`, but we don't want to initialise it immediately either.

Just before we display the ASCII art, we instantiate `emf`.

```kotlin
// setup hibernate
emf = Persistence.createEntityManagerFactory("default", hibernate)
```

This setup would give you a very quick startup time, but `Hibernate` will only startup after the first query has been fired, meaning your first user will get a very slow response. To fix that, we make a dummy query which would force `Hibernate` to start in the background.

```kotlin
vertx.executeBlocking<Any>({
    it.complete(emf.createEntityManager().createNativeQuery("SELECT 'Hibernate Ready!'").singleResult)
}, {
    log.info(it?.result())
})
```

Since we're using the entity manager, all queries would require an entity manager, whether you do a query inside, or outside of a transaction. Let's create a few helper functions to make that easier.

Create another package where you can place your utils, and add a `Transaction.kt` file to it. Add the following code to your `Transaction.kt` file:

```kotlin
package com.hashnode.demo.util

import io.vertx.core.logging.LoggerFactory
import javax.persistence.EntityManager

val log = LoggerFactory.getLogger(AtomApi.javaClass)

/**
 * Inline wrapper function used for doing transactions
 */
inline fun transaction(f: (em: EntityManager) -> Unit) {
    val em = AtomApi.emf.createEntityManager()
    try {
        em.transaction.begin()
        f(em)
        em.transaction.commit()
    } catch (e: Exception){
        log.error(e.message, e)
        em.transaction.rollback()
    } finally {
        em.close()
    }
}

/**
 * Inline wrapper function to give access to the entity manager in codeblock
 */
inline fun notransaction(f: (em: EntityManager) -> Unit){
    val em = AtomApi.emf.createEntityManager()
    try {
        f(em)
    } catch (e: Exception){
        log.error(e.message, e)
    } finally {
        em.close()
    }
}
```

Now whenever you need to run a bunch of queries that should be transactional, use a transaction block:

```kotlin
transaction { em ->
    val query = em.createQuery("SELECT o FROM Company AS o WHERE o.id=:id", Company::class.java)
    query.setParameter("id", company.id)
    val company = query.singleResult

    company.name = "new company name"
    em.merge(company);
}
```

And for non-transactional queries, simply use the `notransaction` block in the same way.

```kotlin
notransaction { em ->
    val query = em.createQuery("SELECT o FROM Company AS o WHERE o.id=:id", Company::class.java)
    query.setParameter("id", company.id)
    val company = query.singleResult
    println(company.name)          
}
```

Any new entities, which you want to create (or update), should always be in a `transaction` block. This can be done in a one-liner (remember, if you don't specify the lambda-variable in Kotlin, it defaults to `it`)

```kotlin
transaction { it.persist(Company(name = "another company")) }
```

Remember that `JDBC` is blocking, so any `Hibernate` queries should be run using `vertx.executeBlocking`. This is typically how you would structure it, either pass in the `ServerWebSocket`; or the `Route`, if you're using the REST (`GET`, `POST`, etc...) methods. Execute the query inside the `executeBlocking` section, and write the result back in the second lambda.

```kotlin
fun listCompanies(vertx: Vertx, ws: ServerWebSocket, session: Session, data: String) {
vertx.executeBlocking<List<Company>>({
    notransaction { em ->
        val query = em.createQuery("SELECT o FROM Company", Company::class.java)                
        it.complete(query.resultList)
    }
    }, {
        if (it.succeeded()) {
            ws.writeFinalTextFrame(Event("listCompanies", it.result()).toString())
        }
    })
}
```

How you structure your files, is completely up to you. You could even use extension methods to cut down on the boilerplate, but you do have the option to structure things according to how they suit you the best.

As I've shown, `Kotlin` to `VertX`, works like a well-fitting glove to a hand. Adding an ORM-layer is straight-forward as well. I've also demonstrated the addition of `Hibernate`, which in my case was the right choice for the stack that I work on. If you don't need to jump between databases, and prefer something more lightweight, be sure to check out the other options I have mentioned above.

If you have any questions, feel free to post them here, and I'll respond when time allows.

"""

Article(
  title = "How I built my first Kotlin-VertX-Hibernate stack",
  url = "https://hashnode.com/post/how-i-built-my-first-kotlin-vertx-hibernate-stack-cixhsnv1b002au6539uu6iw7r",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jan Vladimir Mostert",
  date = LocalDate.of(2017, 1, 3),
  body = body
)
