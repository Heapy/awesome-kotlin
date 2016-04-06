---
title: 'Kotlin: Practical Experience'
url: http://blog.scottlogic.com/2016/04/04/practical-kotlin.html
categories:
    - Kotlin
author: Allen Wallis
date: Apr 04, 2016 21:54
---
Kotlin 1.0.0 was officially released in February this year. Kotlin is a new language from JetBrains (of IntelliJ IDEA and ReSharper fame). It was originally announced in 2011 when a development version was made available to the public. While it can be compiled to JavaScript, the primary target is the JVM, and one of its core goals is 100% Java interoperability.

I have spent some time recently experimenting with Kotlin. There are many features of the language to enjoy when comparing against Java, terseness being high in the list. However, this post primarily serves to highlight a few gotchas that caught me out in the hopes that others may save some time when dealing with similar issues. While this may seem a slightly negative approach for a blog post, my goal is to offer a more practical take instead of only mentioning all the cool things you can do.

I really like Kotlin, I think it has a place in Java development regardless of whether you’re writing server-side or client-side, and regardless of your Java version. Kotlin’s internal API is based on Java 6 which allows Kotlin to be compatible with Android.

## Java 8 Compatibility

As mentioned, Kotlin is internally dependent on Java 6, which places restrictions on what Java library types and language features Kotlin itself can use. This means any new types and methods added in Java 7 or 8 are not available to the Kotlin standard library, however if your target VM is Java 8 then your Kotlin code can make use of the newer Java features.

### Java 8 Streams

A major addition that was introduced by Java 8 was the Stream API. This API typically makes full use of Java 8’s lambdas to allow users to write code similar to .NET’s LINQ. Since Kotlin is compatible with Java 6, they offer an equivalent through their standard library so that it is available regardless of the underlying version of Java. Below is a simple comparison of the Java 8 stream API and Kotlin’s equivalent standard library:

```java
List<Integer> list = new ArrayList<>();
for (int i = 0; i < 50; i++) {
    list.add(i);
}

List<Integer> filtered = list.stream()
        .filter(x -> x % 2 == 0)
        .collect(toList());
```

```java
val list = (0..49).toList()
val filtered = list
        .filter( { x -> x % 2 == 0 } )
```

There are a few things about Kotlin lambdas that I found confusing at first. Lambdas must always appear between curly brackets. This can make the lambda look like a standard block, but once you get used to it, it starts to make sense. There are a few special cases that can be applied to the code above to simplify it further. If the last argument to a method is a lambda, then the lambda definition can be moved outside of the method parentheses, and if the remaining method parentheses are then empty, they can be removed. Furthermore, if the lambda has a single parameter then the parameter declaration can be removed along with the arrow, and the parameter can be referred to by `it`. Applying these rules to the code above simplifies it as follows:

```kotlin
val list = (0..49).toList()
val filtered = list
        .filter { it % 2 == 0 }
```

A key thing to be aware of is that the code shown above is entirely implemented by the Kotlin standard libraries and is therefore independent of Java 8 streams. This is achieved through standard library extension methods.

#### Lazy Streams

There are subtle differences between the Java and Kotlin code above. Java is using lazy streams - the filter lambda is only applied once the call to `collect` is made. The Kotlin example however is eager, which is why the call to filter immediately returns a list.

There are ways to make Kotlin lazy, the main option would be to first convert the list to a `Sequence` and apply the lambdas to that instead. Sequences are evaluated lazily allowing a pipeline to be defined before the underlying lambdas are executed. The Kotlin code would then be as follows:

```kotlin
val list = (0..49).toList().asSequence()
    .filter { it % 2 == 0 }
    .toList()
```

This is only worth considering if the pipeline consists of more than the filter operation.

#### Parallel Streams

Something I ran into was the wish to use Java 8’s parallel streams. Kotlin’s standard library does not include its own parallel stream mechanism, and it is not clear where to start when consulting Kotlin’s standard library.

In Java one would either call `List.parallelStream()` or call `parallel()` directly on a stream. The subsequent pipeline will then be applied in parallel, and the results collected at the end. The issue is that typically in Kotlin lists are referenced via the Kotlin collection interfaces, even if the runtime instances are actually plain Java classes. This means the following code would not compile because the Kotlin collection interfaces do not know about the `stream()` methods or `Stream` interface.

```kotlin
listOf("One", "Two", "Three").parallelStream()
```

Considering these are Java 8 classes means it’s not surprising that there is currently no way for Kotlin to include a stream equivalent in their standard library - doing so would require them to reimplement a Kotlin stream equivalent for supporting Java 6 and 7, and then somehow have it use Java 8’s Stream API if running under a Java 8 VM. In fact Kotlin used to have their own `Stream` type before Java 8, but replaced it with `Sequence` shortly before finalising the 1.0.0 release.

One workaround is to implement extension methods for the `kotlin.collections.Collection` interface as follows:

```kotlin
fun <T> Collection<T>.stream(): Stream<T> {
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
    return (this as java.util.Collection<T>).stream()
}

fun <T> Collection<T>.parallelStream(): Stream<T> {
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
    return (this as java.util.Collection<T>).parallelStream()
}
```

This will allow the `stream()` and `parallelStream()` methods to be invoked directly on any Kotlin collection. Note the nasty suppression of warnings though. Firstly IDEA provides a generally good hint that you should probably not be using the `java.util.Collection` class directly - this is because it is normally hidden behind the Kotlin collection interfaces. The second warning is the unchecked cast which is unavoidable - the main issue is the generic type T which has been erased by Java. It is not possible to use Kotlin’s inlined reification feature in these methods either because we’re referring to the Java type that is already erased. This workaround is referenced from a [bug-report](https://youtrack.jetbrains.com/issue/KT-5175) posted for Java 8 streams on Kotlin’s bug tracker. This issue was posted when Kotlin still had its own `Stream` class. While the issue is not resolved (a subsequent release of Kotlin may see a more elegant solution), the removal of Kotlin’s `Stream` class makes the above workaround possible.

Another issue arises when collecting the results. In Java I would collect a stream to a list using `collect(Collectors.toList())`, Java is usually able to infer the types correctly, however because we’re using Kotlin which defines its own collection types, things are not as smooth. Here is a function definition that uses the `stream()` extension method defined above:

```kotlin
fun toIntList(strings:List<String>):List<Int> {
    return strings.stream()
        .map { it.toInt() }
        .collect(Collectors.toList<Int>())
}
```

The trick is to just make the type explicit when using `Collectors.toList`. There are some alternative workarounds posted to this [Stack Overflow question](http://stackoverflow.com/questions/35721528/how-can-i-call-collectcollectors-tolist-on-a-java-8-stream-in-kotlin/35722167#35722167). The update message posted claiming the issue is fixed in Kotlin 1.0.1 is not entirely accurate - I still find I need to make the types explicit when using collectors.

### SAM Types

A nice feature demonstrated in the above code is the fact that Kotlin lambdas are compatible with Java’s Single Abstract Method (SAM) interfaces. This can be seen above where a Kotlin lambda is supplied directly to Java’s `Stream.map` method. The Kotlin compiler automatically generates an adapter function to convert the Kotlin lambda to the desired SAM interface type. Sometimes however this requires a bit of assistance, especially to resolve ambiguity. An example is when using Java 8’s `CompletebleFuture`:

```kotlin
fun get(url:String, ex: Executor):CompletableFuture<String> {
    return CompletableFuture.supplyAsync( Supplier { downloadURL(url) }, ex)
}
```

This method takes a string and Java executor, and returns a `CompletableFuture` containing the URL’s content. It assumes there is a synchronous `downloadURL` method available. Because the `supplyAsync` Java method takes the lambda as the first parameter, followed by the executor parameter, we can’t use the Kotlin shortcut of pushing the lambda outside of the `supplyAsync` parentheses. To create the correct SAM we prefix the Kotlin lambda with the name of the SAM type we require - in this case a `java.util.function.Supplier`.

### Try-with-resources

This is actually a Java 7 feature, but still applies when using Java 8\. In Java we may do the following to copy from an input stream to an output stream. This example relies on Guava to save some nasty iteration, but demonstrates how in Java we can declare multiple `AutoCloseable` instances in a single try-with-resources declaration. When the try block completes, `AutoCloseable.close()` will be called on each reference.

```kotlin
try (InputStream input = new FileInputStream("SourceFile"); OutputStream output = new FileOutputStream("TargetFile")) {
    ByteStreams.copy(input, output); // using Guava
}
```

Kotlin’s approach is not to add special language constructs, but instead to provide an API: `use()` is simply an extension method on `Closeable`. The following code is the Kotlin equivalent:

```kotlin
FileInputStream("SourceFile").use { input ->
    FileOutputStream("TargetFile").use { output ->
        input.copyTo(output)
    }
}
```

Note that we have to nest the second `use` method call to include the second resource declaration, which also means we can’t use the implicit `it` parameter because we have to differentiate between `input` and `output`.

We also cannot use `AutoCloseable` which was only introduced in Java 7, so this restricts the usage to `Closeable` implementations only. The only workaround for the `AutoCloseable` case at the moment is to implement one’s own `use` extension method for `AutoCloseable`. JetBrains have indicated they are looking at this, though this goes back at least to 2014\. I guess it wasn’t seen as something that had to be fixed by 1.0.0, especially since a proper fix introduces the need for Kotlin to be able to target newer versions of Java as well as keep working for Java 6.

Another issue is that `use` is currently not able to use `Throwable.addSuppressed(Thowable)` for the case where an Exception is being thrown but the close call in the finally block throws its own exception, because this method was also only added in Java 7 specifically for supporting the try-with-resources construct. The source for Kotlin’s `use` includes a TODO item for this and correctly swallows the exception from close so that it doesn’t mask the original exception, hopefully a better solution will be forthcoming. This issue is [discussed here](https://discuss.kotlinlang.org/t/kotlin-needs-try-with-resources/214).

## Java Annotations

This is one particular case I encountered when trying to use Java’s XML binding API (JAXB), however this would affect other annotation processing libraries as well.

Consider the following Kotlin class:

```kotlin
@XmlRootElement(name = "contact")
data class Contact(
        @XmlAttribute var firstName:String? = null,
        @XmlAttribute var lastName: String? = null
)
```


I have already tried to cover the basics: by making all the fields nullable and providing null as the default argument, the resulting class will have an empty constructor, as required by JAXB. I have also annotated the root element, and the attributes. But when I try and parse an XML file, I see the following trace:

```kotlin
Exception in thread "main" com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException: 2 counts of IllegalAnnotationExceptions
Class has two properties of the same name "firstName"
    this problem is related to the following location:
        at public final java.lang.String blog.Contact.getFirstName()
        at blog.Contact
    this problem is related to the following location:
        at private java.lang.String blog.Contact.firstName
        at blog.Contact
Class has two properties of the same name "lastName"
    this problem is related to the following location:
        at public final java.lang.String blog.Contact.getLastName()
        at blog.Contact
    this problem is related to the following location:
        at private java.lang.String blog.Contact.lastName
        at blog.Contact
```

It looks like the attribute annotation is being applied to both the public getter as well as the private field, for both the `firstName` and `lastName` properties. This is primarily fallout from Kotlin’s conciseness. In the `Contact` class above, the parameters are both constructor arguments as well as property declarations. Since a Kotlin property will be compiled into a getter, setter and backing field, the annotations are applied based on a choice from the Target annotation from the underlying annotation implementation. The best workaround here was to be explicit about what the `XmlAttribute` annotation should apply to - this can be done using annotation use-site targets as follows:

```kotlin
@XmlRootElement(name = "contact")
data class Contact(
        @get:XmlAttribute var firstName:String? = null,
        @get:XmlAttribute var lastName: String? = null
)
```

Inserting `get:` before the annotation name will apply the annotation to the Java getter method, and this resolves the annotation/field clash. The full list of use-site targets can be found [here](https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets).

One more thing I found - for this simple case it is possible to fix the issue by adding `@XmlAccessorType(XmlAccessType.FIELD)` to the root element instead, however once I had more complex XML the `IllegalAnnotationsException` would just be pushed down to one of the deeper classes.

## Type-Safe Builders

One of the features of Kotlin that caught my eye at the start was the concept of type-safe builders. The example included in the [official documentation](https://kotlinlang.org/docs/reference/type-safe-builders.html) shows how an HTML document can be built. Once you have the builder code in place, you can use it as follows:

```kotlin
val html = html {
    head {
        title { +"Document Title" }
    }
    body {
        h1 { +"A Nice H1 Heading" }
        p {
            +"The wordy first paragraph including a "
            a(href = "http://kotlinlang.org") { +"link" }
            b { +" as well as some bold text" }
        }
    }
}
```

I liked this because the structure of the resulting document was reflected in the structure of the code. This is made possible by a combination of features:

*   Lambdas must always be defined between curly braces.
*   If the last argument to a method is a lambda, it can be appended after the method parentheses (which in turn can be omitted when empty).
*   _Function type with receiver_ lambda arguments.

Looking at the example code again in light of these 3 features, the code is actually just a set of nested method invocations taking a lambda as a parameter. Each lambda is a function type with receiver, which is similar to an extension function. The receiver (`this`) can be optionally omitted as it can with an extension method.

It took me a bit of effort to understand initially, so I suggest reading the official documentation to get a better idea. Once I understood the underlying concepts I was ready to start writing my own set of builders to support creation of a UI. This is where I came up against an issue with the type-safe builder pattern.

The issue is as follows - each time you define a nested lambda, it collects the scope of its parent invocations - all the way to the root. In the html example above, it means that the following will compile and generate no warnings, but the result will be confusing:

```kotlin
html {
    head {
        title { +"Document Title" }
    }
    body {
        h1 { +"A Nice H1 Heading" }
        p {
            +"The wordy first paragraph including a "
            body {
                h1 { +"This is unexpected" }
            }
            a(href = "http://kotlinlang.org") { +"link" }
            b { +" as well as some bold text" }
        }
    }
}
```

You can see I’ve added an extra `body` call within the deepest `p`. From thinking of this as a type-safe builder of an html document, this should not be possible, since it should not be possible to add a body element at that level. It would be nice if compilation failed. However, if we forget about the html side of things, and just think of this as a bunch of nested closures, then it makes sense. A closure has access to the variables of the outer scope in addition to the current scope. This nested closure effectively has implicit access to all the `this` keywords up the chain. If we explicitly insert the qualified `this` keywords, it looks as follows:

```kotlin
html {
    this.head {
        this.title { +"Document Title" }
    }
    this.body {
        this.h1 { +"A Nice H1 Heading" }
        this.p {
            +"The wordy first paragraph including a "
            this@html.body {
                this.h1 { +"This is unexpected" }
            }
            this.a(href = "http://kotlinlang.org") { +"link" }
            this.b { +" as well as some bold text" }
        }
    }
}
```


You can see the qualified `this@html` reference - this is also implicitly available, but the point is that the second `body` invocation is actually being made against the root html instance, and not against the the paragraph instance that is implied. This leads to the following html document being generated:

```html
<html>
  <head>
    <title>
      Document Title
    </title>
  </head>
  <body>
    <h1>
      This is unexpected
    </h1>
  </body>
  <body>
    <h1>
      A Nice H1 Heading
    </h1>
    <p>
      The wordy first paragraph including a
      <a href="http://kotlinlang.org">
        link
      </a>
      <b>
         as well as some bold text
      </b>
    </p>
  </body>
</html>
```

The reason the second `body` actually appears first is because the nested body is completed (and added to the document) before the first `body` is completed. This is confusing behaviour and is unfortunately simply the result of Kotlin’s closures behaving correctly. A workaround may be to prefix all the invocations by the unqualified `this` keyword. If you do this you will find a compiler error is then generated when you try make a call to `this.body` from inside `p`, however the resulting code looks messier, and it is still possible to forget the `this` prefix.

I’ve only found the following Stack Overflow question and answer [here](http://stackoverflow.com/questions/34834739/wrong-this-being-used-in-nested-closures#34836287), where the principle engineer at JetBrains confirms the issue, and hints that a future Kotlin version may add a modifier to restrict a lambda from having access to enclosing scopes.

I may wait and see where this goes before embracing type-safe builders. I like the idea - especially how it could be used to implement a DSL for creating a UI, however I think this would cause a usage issue.

## Conclusion

Overall I have had a very positive experience. I quickly adapted to the Kotlin syntax and enjoyed using it. The issues I’ve raised in this post should not be viewed in a negative light - for every issue I’ve raised here there are countless times I found myself impressed at how Kotlin integrates cohesively with Java. My main reason for creating this post is to collect my experiences for future reference, and hopefully to help people who run into the same issues.


