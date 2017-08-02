
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
One of the major selling points of Kotlin is its _concise and expressive syntax_. But in how far does Kotlin actually allow you to write more concise code? Let’s look at four ways it accomplishes this.

## Data Classes

In Java, you sometimes create classes which act simply as data containers without much additional functionality. This may be the case, for example, when following the [Value Object Pattern](http://martinfowler.com/bliki/ValueObject.html) proposed by Martin Fowler. Let’s look at an Address class that just stores all data associated with a specific address:

```java
public class Address {
     private String street;
     private int streetNumber;
     private String postCode;
     private String city;
     private Country country;

     public Address(String street, int streetNumber, String postCode, String city, Country country) {
         this.street = street;
         this.streetNumber = streetNumber;
         this.postCode = postCode;
         this.city = city;
         this.country = country;
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;

         Address address = (Address) o;

         if (streetNumber != address.streetNumber) return false;
         if (!street.equals(address.street)) return false;
         if (!postCode.equals(address.postCode)) return false;
         if (!city.equals(address.city)) return false;
         return country == address.country;

     }

     @Override
     public int hashCode() {
         int result = street.hashCode();
         result = 31 * result + streetNumber;
         result = 31 * result + postCode.hashCode();
         result = 31 * result + city.hashCode();
         result = 31 * result + (country != null ? country.hashCode() : 0);
         return result;
     }

     @Override
     public String toString() {
         return "Address{" +
                 "street='" + street + '\'' +
                 ", streetNumber=" + streetNumber +
                 ", postCode='" + postCode + '\'' +
                 ", city='" + city + '\'' +
                 ", country=" + country +
                 '}';
     }

     public String getStreet() {
         return street;
     }

     public void setStreet(String street) {
         this.street = street;
     }

     public int getStreetNumber() {
         return streetNumber;
     }

     public void setStreetNumber(int streetNumber) {
         this.streetNumber = streetNumber;
     }

     public String getPostCode() {
         return postCode;
     }

     public void setPostCode(String postCode) {
         this.postCode = postCode;
     }

     public String getCity() {
         return city;
     }

     public void setCity(String city) {
         this.city = city;
     }

     public Country getCountry() {
         return country;
     }

     public void setCountry(Country country) {
         this.country = country;
     }
 }
```

Phew, that’s a lot of code for such a simple data class. If there was a method in there that adds any actual functionality, all the boilerplate code would greatly distract from that method. Consequently, the class is **less readable and less searchable**. For an immutable Address class, the amount of boilerplate would be a little less frustrating because it would then only contain getters but no setters.

Now, you can generate this kind of boilerplate code pretty fast with a modern IDE. That’s not a problem. The major advantage of Kotlin here really is readability: Kotlin makes it much clearer on the first view what’s actually going on – and doesn’t distract you from the important parts with boring boilerplate code.

**So let’s look at the equivalent class in Kotlin:**

```kotlin
 data class Address(var street: String,
                    var streetNumber: Int,
                    var postCode: String,
                    var city: String,
                    var country: Country)
```

That’s it. In most cases, a simple data class like this is a one-liner but to make it more readable, I split up the properties of the class in the above example. This simple data class declaration generates a constructor, getters & setters, hashCode(), equals(), toString(), and also adds some additional convenience methods for you.

Want to create an _immutable_ data class instead? Easy, just use the val keyword instead of var:

```kotlin
 data class Address(val street: String,
                    val streetNumber: Int,
                    val postCode: String,
                    val city: String,
                    val country: Country)
```

In Kotlin, val is used to create immutable variables whereas var creates mutable variables. Above, we use the same syntax to define mutable and immutable properties for classes using var and val respectively.

Now, let’s look at a language concept that doesn’t save us quite as much code but is really handy as well.

## Smart Casts

In Java, you often have to cast objects in situations where the compiler could actually do this for you because it’s clear that the object can be cast. Consider the following example:

```kotlin
 public class Cast {

     static void printString(String str) {
         System.out.println(str);
     }

     public static void main(String[] args) {
         Object hello = "Hello, World!";

         if (hello instanceof String) {
             printString((String) hello);
         }
     }
 }
```

If we tried to change printString((String) hello) to just printString(hello), the Java compiler would yell at us because hello is of type Object.

Now, the compiler could actually prove that hello is a suitable actual parameter for the formal parameter str of the method printString(String str). As you may have thought, Kotlin does this for us:

```kotlin
 fun printString(str: String) {
     println(str)
 }

 fun main(args: Array<String>) {
     val hello: Any = "Hello, World!"

     if (hello is String) {
         printString(hello)
     }
 }
```

Note that Kotlin’s Any type is the equivalent of Java’s Object, just like “is” is the equivalent of instanceOf, and that we can create package-level functions in Kotlin (that is, they are not nested inside a class or interface).

This is called **Smart Casts** in Kotlin. Admittedly, this doesn’t safe you tons of code, but still it’s super convenient.

Smart casts are by no means limited to the example above. Whenever the compiler can prove that it is safe to cast the object appropriately, it will:

```kotlin
 // Smart cast #1
 if (hello !is String) return
 printString(hello)  // Smart cast

 // ---------------------
 // Smart cast #2
 when(hello) {
     "Some value" -> println("Then do something")
     in 1..10 -> println("This would be printed of hello were an integer between 1 and 10.")
     is String -> {
         println("hello is of type String, this block will be run")
         printString(hello)  // Smart cast
     }
     else -> println("This is the default case.")
 }

 // ---------------------
 // Smart cast #3
 if (hello is String && hello.first().isLetter()) {  // Smart cast after &&
     println("The string starts with a letter")
 }

 // ---------------------
 // Smart cast #4
 if (hello !is String || hello.last().isDigit()) {  // Smart cast after ||
     println("This will not be printed.")
 }
```

In smart cast #1, it’s clear that hello must be a string. Otherwise the control flow wouldn’t even reach that point due to the return statement in the preceding line.

In smart cast #2, you can see a when-expression in Kotlin. Inside this, you can not only check for specific values but also for the type of the object. In the case block associated with the condition “hello is String”, Kotlin will again use smart casts on the hello object.

Smart casts #3 and #4 make use of lazy evaluation. In smart cast #3, the second part of the condition (after &&) will not be checked if the first part is already false (because then the whole condition must be false). Thus, in the second part, the hello object must be of type String because otherwise, Kotlin wouldn’t even evaluate that second part (just like Java).

Similarly, in smart cast #4, if hello were not of type String, the first part would already be true so that the second part would not be evaluated. Therefore, hello must be of type String whenver the second part is evaluated – and Kotlin knows that.

## Functional Programming

Similar to Java 8 which introduced functional language elements such as lambda expressions (function literals), Kotlin comes with functional capabilities baked in. You may be used to function literals like these in Java 8:

```kotlin
 public static void main(String[] args) {
     List<String> genres = Arrays.asList("Action", "Comedy", "Thriller");
     List<String> myKindOfMovies = genres.stream().filter(s -> s.length() > 6).map(s -> s + " Movie").collect(Collectors.toList());
     System.out.println(myKindOfMovies);  // Output: [Thriller Movie]
 }
```

The Stream API introduced in Java 8 can be convenient in many cases where you have to do some quick pipe-and-filter style processing. It can often make your code much more concise because you don’t have to use explicit for-each loops all the time.

Kotlin makes this even easier. By convention, for lambda expressions with only one parameter, Kotlin creates an implicit parameter called “it” so that you can skip typing the parameter:

```kotlin
 fun main(args: Array<String>) {
     val genres = listOf("Action", "Comedy", "Thriller")
     val myKindOfMovies = genres.filter { it.length > 6 }.map { it + " Movie" }
     println(myKindOfMovies)
 }
```

Note that there is actually another convention here. Whenever the last parameter of a method is a lambda expression, we can put it _behind_ the parentheses of the method call. In the example above, both lambda expressions are the _only_ parameters so that you can skip the parentheses altogether.

Also, we don’t have to use a collect() method at the end, the myKindOfMovies variables now stores an Iterable<String>. We can also make that explicit if we want to:

```kotlin
val myKindOfMovies: Iterable<String> = genres.filter { it.length > 6 }.map { it + " Movie" }
```

Next, we’ll see **how to define a singleton in Kotlin in a single line:**

## Objects as Singletons

There are many ways to create a singleton, some of which are not suitable in concurrent environments or don’t hold up against serialization attacks. Let’s look at the most common way to create a singleton in Java:

```kotlin
public class Singleton {
     private static Singleton theInstance = new Singleton();

     private Singleton() {
     }

     public static Singleton getInstance() {
         return theInstance;
     }
 }
```

This creates a class with a private constructor so that we can control which instances of it are created. Since we want to have only one object of this class at any given time, we create that instance as a private attribute and allow retrieving it from the outside using getInstance(). That way, there can never be more than one instance of this object (nasty tricks using reflection or serialization aside).

In Kotlin, we can create such a singleton in a single line by using the object keyword:

```kotlin
 object KotlinSingleton {}
```

Pretty neat, huh? In contrast to the normal habit of defining classes, this defines a single _object,_ which is semantically the same as a singleton – a class of which there should always be only one object. Such object declarations in Kotlin are initialized lazily, just like you may do it for a singleton in Java (even though it’s not the case in the example above).

In both Kotlin and Java you can choose to make your singleton/object immutable or not.

I want to mention that **there is a better way to create singletons in Java as well**: create an enum with only a single type! Joshua Bloch – one of the developers of the Java Collections Framework (amongst others) – advocates this in his very recommendable book “Effective Java”. Why? Well, enums give you all serialization machinery for free and prevent multiple instances even in the face of sophisticated serialization and reflection attacks – without you having to do anything for it. Arguably, they are also very concise:

```kotlin
 enum EnumSingleton {
     INSTANCE
 }
```

This approach can nearly even keep up with the conciseness of Kotlin.

Of course you are free to add properties and methods to Kotlin objects, just as you are free to add attributes and methods to Java enums.

## Closing Words

I hope this quick overview gave you some more understanding of the Kotlin programming language and how it handles some of the more annoying aspects of Java in more convenient ways. Kotlin’s syntax is generally rather concise but still perfectly expressive (“data class ...”, “object ...”, “{ it.length > 6 }”) which makes your code more readable and thus maintainable.

If you’re interested in learning more about the Kotlin programming language, you can check out my [10 tutorial videos for Kotlin](http://petersommerhoff.com/dev/kotlin/kotlin-beginner-tutorial/). They give you a gentle introduction to the language and help you with the setup to get started (don’t worry, it’s not that much). Alternatively, you can also jump ahead and [check out the full Kotlin course hosted on Udemy](https://www.udemy.com/kotlin-course/?couponCode=AMAZINGREADERS25). The link contains a 50% discount coupon for my blog readers.

"""

Article(
  title = "Writing Concise Code With Kotlin",
  url = "http://petersommerhoff.com/dev/kotlin/kotlin-concise-code/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Peter Sommerhoff",
  date = LocalDate.of(2016, 4, 24),
  body = body
)
