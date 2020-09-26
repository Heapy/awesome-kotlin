
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
This article is overdue. After the hype around the [release of Kotlin 1.0](https://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/) has settled, let’s have a serious look at some Kotlin language features that we should have in Java as well.

In this article, I’m not going to wish for unicorns. But there are some low hanging fruit (as far as I naively can see), which could be introduced into the Java language without great risk. While you’re reading this article, be sure to copy paste examples to [http://try.kotlinlang.org](http://try.kotlinlang.org), an online REPL for Kotlin

## 1. Data class

Language designers hardly ever agree on the necessity and the feature scope of what a class is. In Java, curiously, every class always has _identity_ a concept that is not really needed in 80% – 90% of all real world Java classes. Likewise, [a Java class always has a monitor on which you can synchronize](http://blog.jooq.org/2016/01/12/if-java-were-designed-today-the-synchronizable-interface/).

In most cases, when you write a class, you really just want to group values, like Strings, ints, doubles. For instance:

```java
public class Person {
    final String firstName;
    final String lastName;
    public JavaPerson(...) {
        ...
    }
    // Getters
    ...
 
    // Hashcode / equals
    ...
 
    // Tostring
    ...
 
    // Egh...
}
```

By the time you’ve finished typing all of the above, your fingers will no longer be. Java developers have implemented ugly workarounds for the above, like IDE code generation, or [lombok](https://projectlombok.org), which is the biggest of all hacks. In a better Java, nothing in Lombok would really be needed.

As, for instance, if Java had Kotlin’s [data classes](https://kotlinlang.org/docs/reference/data-classes.html):

```kotlin
data class Person(
  val firstName: String,
  val lastName: String
)
```

The above is all we need to declare the equivalent of the previous Java code. Because a data class is used to store data (duh), i.e. values, the implementation of things like `hashCode()`, `equals()`, `toString()` is obvious and can be provided by default. Furthermore, data classes are first class tuples, so they can be used as such, e.g. to destructure them again in individual references:

```kotlin
val jon = Person("Jon", "Doe") 
val (firstName, lastName) = jon
```

In this case, we may hope. [Valhalla / Java 10](https://en.wikipedia.org/wiki/Project_Valhalla_(Java_language)) is being designed and with it, [value types](http://cr.openjdk.java.net/~jrose/values/values-0.html). We’ll see how many features will be provided on the JVM directly, and in the Java language. This will certainly be an exciting addition.

Notice how `val` is possible in Kotlin: [Local variable type inference. This is being discussed for a future Java version right now](http://blog.jooq.org/2016/03/10/java-as-new-local-variable-type-inference/).

## 2. Defaulted parameters

How many times do you overload an API like the following:

```java
interface Stream<T> {
    Stream<T> sorted();
    Stream<T> sorted(Comparator<? super T> comparator);
}
```

The above are exactly the same JDK `Stream` operations. The first one simply applies [`Comparator.naturalOrder()`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#naturalOrder--) to the second one. [So we could write the following, in Kotlin](https://kotlinlang.org/docs/reference/functions.html):

```kotlin
fun sorted(comparator : Comparator<T>
         = Comparator.naturalOrder()) : Stream<T>
```

The advantage of this isn’t immediately visible, when there is only one defaulted parameter. But imagine a function with tons of optional parameters:

```kotlin
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {
...
}
```

Which can be called in any of the following ways:

```kotlin
reformat(str)
reformat(str, true, true, false, '_')
reformat(str,
  normalizeCase = true,
  upperCaseFirstLetter = true,
  divideByCamelHumps = false,
  wordSeparator = '_'
)
```

The power of defaulted parameters is that they are especially useful when passing arguments by name, rather than by index. This is currently not supported in the JVM, which until Java 8, doesn’t retain the parameter name at all ([in Java 8, you can turn on a JVM flag for this](https://docs.oracle.com/javase/tutorial/reflect/member/methodparameterreflection.html), but with all of Java’s legacy, you shouldn’t rely on this yet).

Heck, this feature is something I’m using in PL/SQL every day. Of course, [in Java, you can work around this limitation by passing a parameter object](http://blog.jooq.org/2016/02/11/dear-api-designer-are-you-sure-you-want-to-return-a-primitive/).

## 3. Simplified instanceof checks

If you will, this is really an instanceof switch. Some people may claim that this stuff is evil, bad OO design. Nja nja. I say, this happens every now and then. And apparently, in Java 7, string switches were considered sufficiently common to modify the language to allow them. Why not instanceof switches?

```kotlin
val hasPrefix = when(x) {
  is String -> x.startsWith("prefix")
  else -> false
}
```

Not only is this doing an instanceof switch, it is doing it in the form of an assignable expression. [Kotlin’s version of this `when` expression is powerful](https://kotlinlang.org/docs/reference/control-flow.html). You can mix any sort of predicate expressions, similar to SQL’s `CASE` expression. For instance, this is possible as well:

```kotlin
when (x) {
  in 1..10 -> print("x is in the range")
  in validNumbers -> print("x is valid")
  !in 10..20 -> print("x is outside the range")
  else -> print("none of the above")
}
```

Compare to SQL (not implemented in all dialects):

```sql
CASE x
  WHEN BETWEEN 1 AND 10 THEN 'x is in the range'
  WHEN IN (SELECT * FROM validNumbers) THEN 'x is valid'
  WHEN NOT BETWEEN 10 AND 20 'x is outside the range'
  ELSE 'none of the above'
END
```

As you can see, only SQL is more powerful than Kotlin.

## 4. Map key / value traversal

Now this could really be done very easily only with syntax sugar. Granted, having [local variable type inference](http://blog.jooq.org/2016/03/10/java-as-new-local-variable-type-inference/) would already be a plus, but check this out

```kotlin
val map: Map<String, Int> = ...
```

And now, you can do:

```kotlin
for ((k, v) in map) {
    ...
}
```

After all, most of the time when traversing a map, it’ll be by [`Map.entrySet()`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#entrySet--). Map could have been enhanced to extend `Iterable<Entry<K, V>>` in Java 5, but hasn’t. That’s really a pity. After all, it has been enhanced in Java 8 to allow for internal iteration over the entry set in Java 8 via [`Map.forEach()`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#forEach-java.util.function.BiConsumer-):

```kotlin
map.forEach((k, v) -> {
    ...
});
```

It’s not too late, JDK gods. You can still let `Map<K, V> extend Iterable<Entry<K, V>>`

## 5. Map access literals

This one is something that would add tons and tons of value to the Java language. We have arrays, like most other languages. And like most other languages, we can access array elements by using square brackets:

```java
int[] array = { 1, 2, 3 };
int value = array[0];
``` 

Note also the fact that we have array initialiser literals in Java, which is great. So, why not also allow for accessing map elements with the same syntax?

```kotlin
val map = hashMapOf<String, Int>()
map.put("a", 1)
println(map["a"])
```

In fact, `x[y]` is just syntax sugar for a method call backed by `x.get(y)`. This is so great, we have immediately proceeded with renaming our `Record.getValue()` methods in jOOQ to `Record.get()` (leaving the old ones as synonyms, of course), such that you can now dereference your database record values as such, in Kotlin

```kotlin
ctx.select(a.FIRST_NAME, a.LAST_NAME, b.TITLE)
   .from(a)
   .join(b).on(a.ID.eq(b.AUTHOR_ID))
   .orderBy(1, 2, 3)
   .forEach {
       println(${"\"\"\""}${"$"}{it[b.TITLE]}
               by ${"$"}{it[a.FIRST_NAME]} ${"$"}{it[a.LAST_NAME]}${"\"\"\""})
   }
```

Since jOOQ holds all column type information on individual record columns, you can actually know in advance that `it[b.TITLE]` is a String expression. Great, huh? So, not only can this syntax be used with JDK maps, it can be used with any library that exposes the basic `get()` and `set()` methods.

Stay tuned for more jOOQ and Kotlin examples here:
[https://github.com/jOOQ/jOOQ/blob/master/jOOQ-examples/jOOQ-kotlin-example/src/main/kotlin/org/jooq/example/kotlin/FunWithKotlinAndJOOQ.kt](https://github.com/jOOQ/jOOQ/blob/master/jOOQ-examples/jOOQ-kotlin-example/src/main/kotlin/org/jooq/example/kotlin/FunWithKotlinAndJOOQ.kt)

## 6. Extension functions

This one is a controversial topic, and I can perfectly understand when language designers stay clear of it. But every now and then, [extension functions](https://kotlinlang.org/docs/reference/extensions.html) are very useful. The Kotlin syntax here is actually just for a function to pretend to be part of the receiver type:

```kotlin
fun MutableList<Int>.swap(index1: Int, index2: Int) {
  val tmp = this[index1] // 'this' corresponds to the list
  this[index1] = this[index2]
  this[index2] = tmp
}
```

This will now allow for swapping elements in a list:

```kotlin
val l = mutableListOf(1, 2, 3)
l.swap(0, 2)
```

This would be very useful for libraries like [jOOλ](https://github.com/jOOQ/jOOL), which extends the Java 8 `Stream` API by wrapping it in a jOOλ type ([another such library is StreamEx](https://github.com/amaembo/streamex), with a slightly different focus). The jOOλ `Seq` wrapper type is not really important, as it pretends to be a `Stream` on steroids. It would be great, if jOOλ methods could be put onto `Stream` artificially, just by importing them:

```java
list.stream()
    .zipWithIndex()
    .forEach(System.out::println);
```

The `zipWithIndex()` method isn’t really there. The above would just translate to the following, less readable code:

```java
seq(list.stream())
    .zipWithIndex()
    .forEach(System.out::println);
```

In fact, extension methods would even allow to bypass wrapping everything explicitly in a `stream()`. For instance, you could then do:

```java
list.zipWithIndex()
    .forEach(System.out::println);
```

As all of jOOλ’s method could be designed to also be applied to `Iterable`.

Again, this is a controversial topic. For instance, because

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr"><a href="https://twitter.com/lukaseder">@lukaseder</a> that does not allow for virtual dispatch. Extension methods are not virtual.</p>&mdash; Rafael Winterhalter (@rafaelcodes) <a href="https://twitter.com/rafaelcodes/status/692652876959711232">January 28, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

While giving the illusion of being virtual, extension functions really are just sugared static methods. It’s a significant risk for object oriented application design to engage in that trickery, which is why this feature probably won’t make it into Java.

## 7. Elvis operator

Optional is meh. It’s understandable that an `Optional` type needed to be introduced in order to abstract over the absence of primitive type values, which cannot be null. We now have things like [`OptionalInt`](https://docs.oracle.com/javase/8/docs/api/java/util/OptionalInt.html), e.g. to model things like:

```kotlin
OptionalInt result =
IntStream.of(1, 2, 3)
         .filter(i -> i > 3)
         .findFirst();
 
// Agressive programming ahead
result.orElse(OR_ELSE);
```

Optional is a monad

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">Also Google seems to be a bit confused about what a Monad is ... <a href="http://t.co/eJp9jY9cwG">pic.twitter.com/eJp9jY9cwG</a></p>&mdash; Mario Fusco (@mariofusco) <a href="https://twitter.com/mariofusco/status/389450525466296320">October 13, 2013</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

Yes. It allows you to `flatMap()` the absent value.

![o_O](http://s0.wp.com/wp-content/mu-plugins/wpcom-smileys/o_O.svg)

Sure, if you want to do sophisticated functional programming, you’ll start typing `map()` and `flatMap()` everywhere. Like today, when we’re typing getters and setters. Along will come lombok generating flatmapping calls, and Spring will add some [`@AliasFor` style annotation](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/annotation/AliasFor.html) for flatmapping. And only the enlightened will be able to decipher your code.

When all we needed was just a [simple null safety operator](https://kotlinlang.org/docs/reference/null-safety.html) before getting back to daily business. Like:

```kotlin
String name = bob?.department?.head?.name
```

I really like this type of pragmatism in Kotlin. Or do you prefer (flat)mapping?

```java
Optional<String> name = bob
    .flatMap(Person::getDepartment)
    .map(Department::getHead)
    .flatMap(Person::getName);
```

Can you read this? I cannot. Neither can I write this. If you get this wrong, you’ll get boxoxed.

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">“<a href="https://twitter.com/EmrgencyKittens">@EmrgencyKittens</a>: cat in a box, in a box. <a href="http://t.co/ta976gqiQs">pic.twitter.com/ta976gqiQs</a>”   And I think flatMap</p>&mdash; Channing Walton (@channingwalton) <a href="https://twitter.com/channingwalton/status/447778554114502657">March 23, 2014</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

Of course, [Ceylon is the only language that got nulls right](http://blog.jooq.org/2016/03/15/ceylon-might-just-be-the-only-language-that-got-nulls-right/). But Ceylon has tons of features that Java will not get before version 42, and I’m not wishing for unicorns. I’m wishing for the elvis operator, which could be implemented in Java too. The above expression is just syntax sugar for:

```java
String name = null;
if (bob != null) {
    Department d = bob.department
    if (d != null) {
        Person h = d.head;
        if (h != null)
            name = h.name;
    }
}
```

What can possibly be wrong with that simplification?

## 8. Everything is an expression

Now this might just be a unicorn. I don’t know if there is a JLS / parser limitation that will forever keep us in the misery of prehistoric distinction between statement and expression.

At some point in time, people have started using statements for things that yield side-effects, and expressions for more functional-ish things. It is thus not surprising, that all `String` methods are really expressions, operating on an immutable string, returning a new string all the time.

This doesn’t seem to go well with, for instance, `if-else` in Java, which is expected to contain blocks and statements, each possibly yielding side-effects.

But is that really a requirement? Can’t we write something like this in Java as well?

```kotlin
val max = if (a > b) a else b
```

OK, we have this weird conditional expression using `?:`. But what about Kotlin’s `when` (i.e. Java’s `switch`)?

```kotlin
val hasPrefix = when(x) {
  is String -> x.startsWith("prefix")
  else -> false
}
```

Isn’t that much more useful than the following equivalent?

```java
boolean hasPrefix;
 
if (x instanceof String)
    hasPrefix = x.startsWith("prefix");
else
    hasPrefix = false;
```

(yes, I know about `?:`. I just find `if-else` easier to read, and I don’t see why that should be a statement, not an expression. Heck, in Kotlin, even `try` is an expression, not a statement:

```kotlin
val result = try {
    count()
} catch (e: ArithmeticException) {
    throw IllegalStateException(e)
}
```

Beautiful!

## 9. Single expression functions

Now this. This would save so much time reading and writing simple glue code. And in fact, we already have the syntax in annotations. Check out Spring’s magical [@AliasFor](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/annotation/AliasFor.html) annotation, for instance. It yields:

```java
public @interface AliasFor {
    @AliasFor("attribute")
    String value() default "";
    @AliasFor("value")
    String attribute() default "";
}
```

Now, if you squint really hard, these are just methods yielding constant values, because annotations are just interfaces with generated byte code for their implementations. We can discuss syntax. Of course, this irregular usage of `default` is weird, given that it was not re-used in Java 8 for default methods, but I guess Java always needs the extra syntax so developers feel alive as they can better feel their typing fingers. That’s OK. We can live with that. But then again, why do we have to? Why not just converge to the following?

```java
public @interface AliasFor {
    String value() = "";
    String attribute() = "";
}
```

And the same also for class / interface default methods?

```java
// Stop pretending this isn't an interface
public interface AliasFor {
    String value() = "";
    String attribute() = "";
}
```

Now _that_ would look nice. But given Java’s existing syntax, this might just be a unicorn, so let’s move on to...

## 10. Flow-sensitive typing

Now _this_. THIS!

[We’ve blogged about sum types before.](http://blog.jooq.org/2016/02/16/an-ingenious-workaround-to-emulate-sum-types-in-java/) Java has sum types with exceptions since Java 7:

```java
try {
    ...
}
catch (IOException | SQLException e) {
    // e can be of type IOException and/or SQLException
    // within this scope
}
```

But Java, unfortunately, doesn’t have flow-sensitive typing. Flow-sensitive typing is of the essence in a language that supports sum types, but it is also useful otherwise. For instance, in Kotlin:

```kotlin
when (x) {
    is String -> println(x.length)
}
```

We don’t need to cast, obviously, because we already checked that `x is String`. Conversely, in Java:

```java
if (x instanceof String)
    System.out.println(((String) x).length());
```

Aaagh, all this typing. IDE autocompletion is smart enough to offer a contextual type’s methods already and then generate the unnecessary cast for you. But it would be great if this was never needed, every time we explicitly narrow a type using control flow structures.

[For more info, see this wikipedia entry about flow sensitive typing](https://en.wikipedia.org/wiki/Flow-sensitive_typing). A feature that could absolutely be added to the Java language. After all, we already got flow-sensitive final local variables since Java 8.

## 11. (Bonus) Declaration site variance

[Last but not least, better generics via declaration site variance](https://kotlinlang.org/docs/reference/generics.html). Many other languages know this, for instance also C#’s [`IEnumerable`](https://msdn.microsoft.com/en-us/library/9eekhta0(v=vs.110).aspx):

public interface IEnumerable<out T> : IEnumerable

The keyword `out` here means that the generic type `T` is _produced_ from the type `IEnumerable` (as opposed to `in`, which stands for consumption). In C#, Scala, Ceylon, Kotlin, and many other languages, we can declare this on the type declaration, rather than on its usage (although, many languages allow for both). In this case, we say that `IEnumerable` is covariant with its type `T`, which means again that `IEnumerable<Integer>` is a subtype of `IEnumerable<Object>`

In Java, this isn’t possible, which is why we have a [bazillion question by Java newbies on Stack Overflow](http://stackoverflow.com/q/4288084/521799). Why can’t I...

```java
Iterable<String> strings = Arrays.asList("abc");
Iterable<Object> objects = strings; // boom
```

In languages like Kotlin, the above would be possible. After all, why shouldn’t it? A thing that can produce strings can also produce objects, and we can even use it in this way in Java:

```java
Iterable<String> strings = Arrays.asList("abc");
for (Object o : strings) {
    // Works!
}
```

The lack of declaration site variance has made a lot of APIs very intelligible. Consider `Stream`:

```java
<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
```

This is just noise. A function is contravariant with its argument type and covariant with its result type _by nature_ a better definition of `Function` or `Stream` would be:

```java
interface Function<in T, out R> {}
interface Stream<out T> {}
```

If this were possible, all that `? super` and `? extends` garbage could be removed without losing any functionality.

In case you’re wondering what I’m even talking about?![:)](http://s1.wp.com/wp-content/mu-plugins/wpcom-smileys/simple-smile.svg)

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">Covariance and contravariance explained. Source: <a href="https://t.co/2S4ChNeAvq">https://t.co/2S4ChNeAvq</a> <a href="https://t.co/BfOME8puj2">pic.twitter.com/BfOME8puj2</a></p>&mdash; Lukas Eder (@lukaseder) <a href="https://twitter.com/lukaseder/status/686917793472753665">January 12, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

The great news is, this is being discussed for a (near) future version of Java:
[http://openjdk.java.net/jeps/8043488](http://openjdk.java.net/jeps/8043488)

## Conclusion

Kotlin is a promising language, even if it is very late to a game that already seems to have been decided, not in favour of alternative languages on the JVM. Nonetheless, it is a very interesting language to learn from, and with a lot of very good decisions made about some simple things.

Some of these decisions will hopefully be picked up by the Java language gods and integrated into Java. This list here shows some features that might be “easy” to add.

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr"><a href="https://twitter.com/shipilev">@shipilev</a> <a href="https://twitter.com/lukaseder">@lukaseder</a> And then it&#39;s yacc-shaving from there!</p>&mdash; Brian Goetz (@BrianGoetz) <a href="https://twitter.com/BrianGoetz/status/708350584294920193">March 11, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

More info about Kotlin idioms:
[https://kotlinlang.org/docs/reference/idioms.html](https://kotlinlang.org/docs/reference/idioms.html)

"""

Article(
  title = "10 Features I Wish Java Would Steal From the Kotlin Language",
  url = "http://blog.jooq.org/2016/03/31/10-features-i-wish-java-would-steal-from-the-kotlin-language/",
  categories = listOf(
    "Kotlin",
    "Java"
  ),
  type = article,
  lang = EN,
  author = "Lukas Eder",
  date = LocalDate.of(2016, 3, 31),
  body = body
)
