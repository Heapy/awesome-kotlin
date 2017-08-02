
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
## **Intro**

Kotlin is probably my favorite language right now, and possibly one of the coolest things it has to offer is type-safe builders, built upon several features (explained in a bit). I find myself really _really_ wanting to have this feature in my other two primary languages, Java and Python. This article explains what I believe to be the closest we can get to having type-safe builders in those languages

## **Kotlin**

To start, I need to explain Kotlin’s ability to do type-safe builders. For a quick explanation of what these builders are, you should check out [their page about them](https://kotlinlang.org/docs/reference/type-safe-builders.html). In this article, we’ll be implementing a tiny subset of their html builder.

Kotlin’s ability to create type-safe builders is due to many small features. The first is the lambda syntax; `{param, list -> block.of.code()}`. If the lambda has zero parameters, you can ignore the parameter list and the arrow. The same is true when it only has one parameter, as that parameter is implicitly called `it`. For example, `{doSomethingWith(it)}` is a legitimate lambda, assuming `doSomethingWith()` takes an object that is the same type as what is being passed into the lambda.

The next feature is how to pass lambdas into functions. If the last argument is a lambda, it can be passed _after_ the parentheses of the function call. For example, `myFunc(arg1){lambdaArg()}`. If the lambda is the _only_ argument, the parentheses can be ignored altogether: `aFunc{lambdaArg()}`. This allows you to define functions that can look like language features. You could technically define your own if-else blocks, or any of the loops, if it weren’t for the fact that those keywords are reserved.

Next is extension methods and the fact that you can define lambdas that work like them. Extension methods are new methods that are defined for a class or interface _outside_ the class of the interface. For example, you could create new methods for the `String` class. In actuality, they’re just static methods that take an implicit first parameter of the type they’re for. In the Kotlin code, that first parameter is assigned to the `this` identifier, which is used implicitly, just like in a real method.

You can define lambdas that work like extension methods (`SomeClass.() -> Unit` instead of `(SomeClass) -> Unit`, too, so that inside the lambda, you can make calls on the object without explicitly referencing it.

All these features, plus really good type inferencing, come together to create the ability to make type-safe builders from functions taking extension lambdas. So, we can write this:

```kotlin
html {
   head {
      title("A Title")
   }
   body {
      p = "paragraph"
      p = "'nother one"
      p = "last paragraph"
   }
}
```

To return an `Html` object that contains a `Head` and a `Body`, the `Head` containing a `Title` with the text, “A Title”. The `Body` contains 3 `Paragraphs`.

You may note that `title` and [p] are different in how they’re defined. It probably would have been smarter to have `title` to use the `=` syntax instead of `p`, but `p` shows off how creative these builders can be better than `title`. I did a similar thing with Python, since it also supports properties.

Let’s look at the Kotlin code that allows up to create these objects

```kotlin
fun html(htmlBuilder: Html.() -> Unit): Html {
   val html = Html()
   html.htmlBuilder()
   return html
}

class Html {
   private var head: Head? = null
   private var body: Body? = null

   fun head(headBuilder: Head.() -> Unit) {
      head = Head()
      head?.headBuilder()
   }

   fun body(bodyBuilder: Body.() -> Unit) {
      body = Body()
      body?.bodyBuilder()
   }
}
```

We start with the `Html` class and the `html()` function used to start the builder. The `html` function isn’t necessary, since the code could be used as an `Html` constructor, but it allows us to keep the constructor simple and all the functions lowercase without going against naming conventions.

You’ll note that everything is actually pretty darn short. Only the `html` function is 3 lines, and that’s only because it has to return the result at the end. If we used a constructor on `Html` instead, it would only have the line `htmlBuilder()`.

Here’s `Head` and `Title`.

```kotlin
class Head {
   private var title: Title? = null

   fun title(text: String) {
      title = Title(text)
   }
}

class Title (private val text: String) { }
```

Still going pretty nicely. `Title` doesn’t require a builder, since it just holds text. If it weren’t for the fact that there would need to be some more complex build mechanics, I’d actually have `Head` just hold the `String` itself instead of creating a `Title` class and object.

```kotlin
class Body {
   private val paragraphs: ArrayList<Paragraph> = ArrayList()

   var p: String
      private get() = null!!
      set(value) {
         paragraphs.add(Paragraph(value))
      }
}

class Paragraph (private val text: String) { }
```

Here’s the really interesting thing. Instead of having a `p()` method, like we did for `Title`, we used `p`‘s setter to keep adding `Paragraph` objects to the list. In this case, it’s not the most intuitive; it’s just there to show you how creative one could get with these builders.

Keep in mind, too that these classes are just the builder classes, so they’re allowed to be stateful. There should be a `build()` method that recursively calls the `build()` methods of all the the enclosed objects to create a nice, immutable object.

## **Java**

In Java, you can pretty much create the exact same classes, except that the builder doesn’t look as clean, since it doesn’t have all the lovely features above. So, to start you off, here’s what the builder code ends up looking like.

```kotlin
html(html -> {
   html.head(head ->
      head.title("A Title")
   );
   ht.body(body -> {
      body.p("paragraph");
      body.p("'nother one");
      body.p("last paragraph");
   });
});
```

And _that_ is as close to the builder syntax that you can get in Java. Note that there’s no difference in the way that `title()` and `p()` are called, since Java doesn’t provide any property-like construct. Also, notice that you need to have a name for everything. With the implicit `this`, you must write something like `hd.title(...)` rather than just `title(...)`, and that’s not even mentioning the fact that we have to define the parameter list for the lambda.

There’s a couple other things you could do, but those are even worse, the first being just using normal code:

```java
Html html = new Html();
   Head head = html.head();
      head.title("A Title");
   Body body = html.body();
      body.p("paragraph");
      body.p("'nother one");
      body.p("last paragraph");
```

This isn’t _terrible_, but it ends up being relatively verbose because of the lack of full type inference (I have to specify that `head` and `body` are of their respective types), and the extra tabbing is purely for looks, since no brackets are used. The other way I thought of doing it will be shown after the Python version, since it tries to sort of replicate _that_ version.

So, let’s look at the code

```java
public class Html {
   public static Html html(Consumer<Html> htmlBuilder)
   {
      Html html = new Html();
      htmlBuilder.accept(html);
      return html;
   }

   private Head head = null;
   private Body body = null;

   public void head(Consumer<Head> headBuilder) {
      head = new Head();
      headBuilder.accept(head);
   }

   public void body(Consumer<Body> bodyBuilder) {
      body = new Body();
      bodyBuilder.accept(body);
   }
}
```

This is as direct of a port to Java as it gets. The `html()` function was moved into the `Html` class as static method, since it has to go _somewhere_ in Java. We used a `Consumer<Html>`, since that’s the closest thing Java has to the kind of lambdas we want.

Here are `Head` and `Title`:

```java
public class Head {
   private Title title = null;

   public void title(String text) {
      title = new Title(text);
   }
}

public class Title {
   private final String text;

   public Title(String text) {
      this.text = text;
   }
}
```

Not much of note here. It’s probably about what you expected. Now to finish off with `Body` `Paragraph`.

```java
public class Body {
   private final List paragraphs = new ArrayList<>();

   public void p(String text) {
      paragraphs.add(new Paragraph(text));
   }
}

public class Paragraph {
   private final String text;

   public Paragraph(String text) {
      this.text = text;
   }
}
```

It almost feels like it’s not worth writing these classes, doesn’t it, they’re so simple. Keep in mind, this is the bare-bones builder part. Again, this code doesn’t actually include the functionality for building the actual, immutable DOM tree.

That’s what it takes to build the Java version. Other that some of the syntax verbosity, it’s almost easier to create in Java than in Kotlin because there aren’t any extra features to think about and apply![😛](https://s0.wp.com/wp-content/mu-plugins/emoji/twemoji/svg/1f61b.svg)

## **Python**

Trying to figure out a way to do something like this in Python required me to get lucky enough to see a video that showed a novel (but unintuitive) way of using context managers (`with` statements). The problem in Python is that lambdas are only allowed to have a single expression or statement. Context managers allow a (very limited) way of getting around single-line lambdas by effectively allowing you to return an object (or nothing) at entry that can be used while within the context manager as if being within lambda.

So, for example, the builder would look like this in Python:

```python
myhtml = Html()
with myhtml as html:
    with html.head() as head:
        head.title("A Title")
    with html.body() as body:
        body.p = "paragraph"
        body.p = "'nother one"
        body.p = "last paragraph"
```

This may actually look like a waste because this can written as the following almost as easily:

```python
html = Html()
head = html.head()
head.title("A Title")
body = html.body()
body.p = "paragraph"
body.p = "'nother one"
body.p = "last paragraph"
```

The biggest benefit of the `with` blocks is the indentation, since Python has indentation restrictions due to it using indentation over curly braces. Context managers are possibly worth it just for _that_ benefit. But there’s another benefit that I’ll bring up near the end, after showing you the basic code required for making these in Python

```python
class Html:
    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        return False

    def head(self):
        self._head = Head()
        return self._head

    def body(self):
        self._body = Body()
        return self._body
```

Here, you can see that the `Html` class has the required `__enter__()` and `__exit__()` methods to be a context manager. They do practically nothing; `__enter__()` only returns `self`, and `__exit__()` simply signifies that it didn’t deal with any exceptions that may have been passed in. The `head()` and `body()` methods do pretty much what you’d expect by now, with the assumption that `Head` and `Body` are also context manager types.

```python
class Head:
    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        return False

    def title(self, text):
        self._title = Title(text)

class Title:
    def __init__(self, text):
        self.text = text

class Body:
    p = property()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        return False

    @p.setter
    def p(self, text):
        if not hasattr(self, 'paragraphs'):
            self.paragraphs = []
        self.paragraphs.append(Paragraph(text))

class Paragraph:
    def __init__(self, text):
        self.text = text
```

The only new thing here to bother looking at is the use of `property` on `Body` for its `p` tag. Luckily, we can don’t need getters on `property`s that we need to have return `None`, like in Kotlin.

Okay, now we look at the interesting, less obvious reason why it’s helpful to use context managers for this situation. In Java and Kotlin, we would have needed an additional call at the end to a `build()` method (or else have the `html()` function do it for us) and have it do a recursive traversal all at once in the end to take care of it. With the context manager, the `__enter__()` and `__exit__()` methods could pass out the builder version of the object upon entry, then build it on exit. That means that each intermediate stage of the builders already contain the fully built versions by the time they exit.

This can actually be a little difficult to wrap your head around. Here’s an example that does a partial implementation using `Html`, `HtmlBuilder`, and `Head`:

```python
class Html:
    def __enter__(self):
        self._builder = HtmlBuilder()
        return self._builder

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.head = self._builder._head
        self.body = self._builder._body
        del self._builder
        return False

class HtmlBuilder:
    def head(self):
        self._head = Head()
        return self._head

    def body(self):
        ...

class Head:
    def __enter__(self):
        self._builder = HeadBuilder()
        return self._builder

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.title = self._builder._title
        del self._builder
        return False
```

Here, the `Html` object’s `__enter__()` method creates and saves a builder on itself, then returns it. Upon `__exit__()`, it builds itself from values stored on the builder and deletes the builder from itself. Upon first thought, at least for me, one might think that the objects stored on the builder aren’t finished objects, but they are. The methods on the builder object return a proper class with its own `__enter__()` and `__exit__()` methods which will also guarantee that it’s built properly, as is seen with `HtmlBuilder`‘s `head()` method and with the implementation of `Head`. With this setup, the calling code is actually still the same as it was the first time.

Last thing: now that we know that we can use context managers to do this, you might think that Java’s `try` resource manager might actually work okay for it. And you’d be right. In fact, it ends up with a cleaner syntax (other than the random `try` keywords) than the lambda version, too. Here’s what the resource manager version would look like when called:

```java
Html html = Html();
try(html) {
   try(Head head = html.head()) {
      head.title("A Title");
   }
   try(Body body = html.body()) {
      body.p("paragraph");
      body.p("'nother one");
      body.p("last paragraph");
   }
}
```

At this point, I’ll leave it to you to try and figure out how to implement this. Hint: I don’t think it can work like the second version of the Python build, where it builds as it goes. I think everything in this Java version of the code requires builders until, at the end, you call the `build()` method on `html` to create the true versions.

## **Outro**

Holy cow, this thing ended up being kind of long, didn’t it? I hope that you had some fun with this exercise, since I’m not sure how useful it really is (other than learning that you could potentially simulate 0- or 1-parameter lambdas with context managers.

Sadly, I never did get around to talking about adding the additional parameters like the Kotlin site’s example does, such as assigning a class, id, etc in the function calls. There are additional features that Kotlin has that makes this really clean and easy, but this article clearly doesn’t have room for it. I’ll tackle it next week.

Thanks for reading!

Note: As of yesterday, all editing is finished. From here, I “just” need to get a cover designed, which I have an idea for; get all the formatting figured out for both print and e-book versions; write the appendix (mostly just a collection of code snippets from the book, fleshed out more); and finish writing the GitHub repo that will have all the super helpful classes and functions for building your own descriptors more quickly, easily, and with fewer problems. I expect to get all of this done by the end of summer, but hopefully sooner. My life is about to get a little busier, so I don’t know how much time I’ll be able to devote to all of this.

"""

Article(
  title = "Mimicking Kotlin Builders in Java and Python",
  url = "https://programmingideaswithjake.wordpress.com/2016/01/16/mimicking-kotlin-builders-in-java-and-python/",
  categories = listOf(
    "Builders",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jacob Zimmerman",
  date = LocalDate.of(2016, 1, 16),
  body = body
)
