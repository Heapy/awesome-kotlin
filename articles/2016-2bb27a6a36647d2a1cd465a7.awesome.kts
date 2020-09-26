
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
![Kotlin and Spring-Boot Logo.](http://www.thedevpiece.com/content/images/2016/12/kotlin-springboot.png)

It's been a while since Kotlin arrived. Kotlin is a JVM language created by JetBrains developed to be interoperate with Java libraries and apis.  

What draws attention about Kotlin is its simplicity. If you find Java too verbose, you also will be interested in Kotlin because it is possible to transit between both languages easily.

Okay then, this post is supposed to be very short, so, let's begin.

We are going to build a simple crud app using Spring Boot, specifically Spring Boot Data Rest and use Kotlin (of course) as our programming language.

First of all, our **build.gradle**:

```gradle
buildscript {  
    ext.kotlin_version = '1.0.5'

    repositories {
        jcenter()
    }

    dependencies {
        classpath "org.springframework.boot:spring-boot-gradle-plugin:1.4.2.RELEASE"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${"$"}kotlin_version"
    }
}

apply plugin: 'kotlin'  
apply plugin: 'java'  
apply plugin: 'eclipse'  
apply plugin: 'idea'  
apply plugin: 'org.springframework.boot'

repositories {  
    jcenter()
}

sourceSets {  
    main.java.srcDirs += 'src/main/kotlin'
}

dependencies {  
    compile "org.jetbrains.kotlin:kotlin-stdlib:${"$"}kotlin_version"

    compile 'org.slf4j:slf4j-api:1.7.14'

    compile("org.springframework.boot:spring-boot-starter-data-rest")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.h2database:h2")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile 'junit:junit:4.12'
    testCompile 'io.kotlintest:kotlintest:1.3.5'
}
```

And that's all you need if you want to set up Kotlin in with Gradle. Also, you can see that we are using two Spring Boot libraries: **data rest and data jpa.**

So, now we are going to create our entity and our rest repository in a file named **PersonApp.kt**:

```kotlin
package package com.thedevpiece.kotlin.spring.boot

import com.fasterxml.jackson.annotation.JsonInclude  
import org.springframework.data.repository.PagingAndSortingRepository  
import org.springframework.data.rest.core.annotation.RepositoryRestResource as Resource  
import javax.persistence.Entity  
import javax.persistence.Table  
import org.springframework.boot.autoconfigure.SpringBootApplication  
import org.springframework.boot.SpringApplication  
import javax.persistence.GeneratedValue  
import javax.persistence.Id

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "people")
data class Person(@Id @GeneratedValue val id: Long? = null, val name: String? = null, val age: Int? = null)

@Resource(collectionResourceRel = "people", path = "people")
interface PersonRepository : PagingAndSortingRepository

@SpringBootApplication
open class Application

fun main(args: Array) {  
    SpringApplication.run(Application::class.java, *args)
}
```

Since this is an example and our classes are incredibly simple, our app will have only this file.

Knowing that it is very easy to create tests using Kotlin, we are also going to create a simple feature test to assure that the get by id service is working as expected. By the way, our test lib is called **KotlinTest** and was inspired by [ScalaTest](http://www.scalatest.org/):

```kotlin
package com.thedevpiece.rapidoid.microservices.endpoints

import io.kotlintest.specs.FeatureSpec  
import org.springframework.beans.factory.annotation.Autowired  
import org.springframework.boot.test.context.SpringBootTest  
import org.springframework.boot.test.web.client.TestRestTemplate  
import org.springframework.test.annotation.DirtiesContext  
import org.springframework.test.context.ActiveProfiles  
import org.springframework.test.context.TestContextManager

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@DirtiesContext
class PersonEndpointTest : FeatureSpec() {  
    @Autowired
    val restTemplate: TestRestTemplate? = null

    @Autowired
    val repository: PersonRepository? = null

    override fun beforeAll() {
        TestContextManager(this.javaClass).prepareTestInstance(this)
    }

    init {
        feature("People endpoint") {
            scenario("Asserting getting a person is working") {
                repository?.save(Person(name = "Gabriel", age = 23))
                val entity = restTemplate?.getForEntity("/people/1", Person::class.java)
                entity?.statusCodeValue shouldBe 200
            }
        }
    }
}
```

And I guess that's all. As you can see, Kotlin is a interesting language, very pragmatic and focused on high productivity.

Thank you and, any questions, please, leave a comment.

[]'s

"""

Article(
  title = "Building microservices with Kotlin and Spring Boot",
  url = "http://www.thedevpiece.com/building-microservices-with-kotlin-and-springboot/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Gabriel Francisco",
  date = LocalDate.of(2016, 12, 15),
  body = body
)
