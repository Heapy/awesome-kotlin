
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
### Rest API plumbing with kotlin

As a startup developper, I spend a lot of my time dealing with REST APIs. It can be internal APIs for our web applications or external APIs for integration with online services (email, documents, ...). For both uses, Kotlin features help a lot. Lets see how.

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*hzb7OdfBZ9vYEosTTfLddg.png)
_Internal and external REST calls_

#### Internal APIs

Regardless the web framework we use (my preferred one is currently _Ember.js_) the client/server communication is commonly implemented via a REST API exchanging data in JSON.

We define our API by specifying URLs and the JSON Format. URLs’ definition is managed by the web framework. As for the JSON format, jackson is a good solution for serializing POJOs. The structure of the JSON object is defined by the POJOs property names. To be sure to have an API independent of our domain objects, we need to have specific classes for the client-server communication.

Jackson is a nice framework that converts java classes to JSON seamlessly. The main problem with java is the verbosity of those POJOs classes. Even basic javabeans with no logic and no method seems bloated.

In the other hand, Kotlin data classes are really concise and allow generally to define data objects in a single line.

For all JSON inputs and outputs, we can have the data classes contained in the same file/class as the Rest Controller.

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*xdUrq3OnbITKiS4yXQhBnw.png)
_Kotlin files that contains both the controller and the data classes_

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*XVMVPgmMzeBa4dBCa0TGAA.png)
_Defining a PUT call with spring and kotlin_


The Nullabillity type system of Kotlin gives more control and information on the input/output format. In the example above, the documentId can be null by specification.

In that call, the client expects an empty JSON object (?!). We can provide it by returning an instance of the _Empty_ class.

For inputs, we need to use [jackson’s Kotlin](https://github.com/FasterXML/jackson-module-kotlin) module. It manages the constructor calls to avoid creating unnecessary empty constructors.

In less than 20 lines of kotlin we have defined our REST call, the URL and the format.

#### External APIs and the bounded context pattern

Beyond the issue of connecting with an external API and the technical part of it (remote calls, authentication,...) as developers we have to manage the [**Bounded Context pattern**](http://martinfowler.com/bliki/BoundedContext.html).

The external system has its own domain model, which makes sense in its context, but is certainly different from our model/context.

For example, the representation of Google Files in Google Drive is different from our file domain.

When retrieving objects from the external API we have to **transform those objects in our own model**. The transformation must deal with the values (retrieved or not) and the type of (generally different) properties .

Imagine the following naive class for my domain file:

```kotlin
class MyFile(
   val name:String,
   val creationDate: Instant,
   val modificationDate:Instant)
```

In the [Google API](https://developers.google.com/resources/api-libraries/documentation/drive/v2/java/latest/) dates are using _com.google.api.client.util.DateTime_ type for creation and modification dates.

A first conversion function could be:

```kotlin
fun gFileToMyFile(gFile: File) = MyFile(
    gFile.title,
    Instant.ofEpochMilli(gFile.createdDate.value),
    Instant.ofEpochMilli(gFile.modifiedDate.value))
```

It’s quite verbose and hard to read. We can improve the code by implementing a **property extension** on the google DateTime class:

```kotlin
val DateTime.instant: Instant
    get() = Instant.ofEpochMilli(value)
```

The DateTime class now has a new property simplifying the previous code snippet:

```kotlin
fun gFileToMyFile(gFile: File) = MyFile(
    gFile.title,
    gFile.createdDate.instant,
    gFile.modifiedDate.instant)
```

We can go further and **replace this function** with an **extension function** on the Google File class to call the transformation directly on the Google File instances.

```kotlin
fun File.toMyFile() = MyFile(
    title,
    createdDate.instant,
    modifiedDate.instant)
```

Converting a single Google File is now as easy as:

```kotlin
val myFile = gFile.toMyFile()
```

And the conversion of a list of files is done by mapping the original list using a function reference:

```kotlin
val myFiles = gFiles.map ( File::toMyFile )
```

Implementing the 2 conversions (_Google DateTime => java Instant_ and _Google File => MyFile)_ with small **extensions** results in much more readable code.

**For both uses, managing internal and external APIs, Kotlin appears to be** [**a great tool**](https://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/)**.**

"""

Article(
  title = "Rest API plumbing with kotlin",
  url = "https://medium.com/@gz_k/rest-api-plumbing-with-kotlin-b161af052178#.tdrmb8p5w",
  categories = listOf(
    "Spring",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Gaetan Zoritchak",
  date = LocalDate.of(2016, 3, 29),
  body = body
)
