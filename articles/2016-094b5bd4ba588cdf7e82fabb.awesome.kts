
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
As a software developer working in a tactical business environment, I'm always looking for ways to do more with less code. Even better, if I can modify and scale code to constantly adapt to business demands then I have to do less code re-writes.

Java has been my go-to language as it is practical, scalable, performant, portable, and statically-typed. As I became proficient and took on more ambitious projects, it started to feel cumbersome (and I kept eyeing C# wishfully). Thankfully I found RxJava last year, and reactive programming enabled me to take on tasks that I would hesitate to do before.

Utilizing RxJava almost exclusively for all my projects, I became much more productive and the quality of my applications increased. But I slowly started to realize the limitations of the Java language were holding RxJava back. Even with Java 8's lambdas, some functional programming tasks became very verbose.

For instance, using the `compose()` operator which accepts a custom `Transformer` allows you to create your own operator with existing RxJava operators. The problem is it can quickly become wordy and less fluid.

Here is a simple example. I can create a custom `Transformer` that turns an `Observable<T>` into an `Observable<ImmutableList<T>>` since I like Google Guava's immutable collections.

```java
public final class Launcher {

    public static void main(String[] args) {
        Observable<String> source = Observable.just("Alpha", 
            "Beta", "Gamma", "Delta", "Epsilon");

        source.compose(toImmutableList()).subscribe(System.out::println);
    }

    public static <T> Observable.Transformer<T,ImmutableList<T>> toImmutableList() {
        return obs -> obs.collect(() -> ImmutableList.<T>builder(),
             (b,t) -> b.add(t)).map(b -> b.build());
    }
}
```

With Java 8 this is not a very big deal. But this `Transformer` factory exists in the same class, and if I stored it in a separate factory class it would slowly start getting verbose.

```java
public final class Launcher {

    public static void main(String[] args) {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", 
            "Delta", "Epsilon");

        source.compose(GuavaTransformers.toImmutableList()).subscribe(System.out::println);
    }

    /*This would be in the GuavaTransformers class */
    public static <T> Observable.Transformer<T,ImmutableList<T>> toImmutableList() {
        return obs -> obs.collect(() -> ImmutableList.<T>builder(), 
            (b,t) -> b.add(t)).map(b -> b.build());
    }
}
```

Even worse if I start to create more complicated Transformers or Operators with arguments, my `compose()` statement can start to get pretty ugly. If I wanted to collect items into an `ImmutableListMultimap`, it starts to get less fluid with the lambda arguments.

```java
public final class JavaLauncher {

    public static void main(String[] args) {

        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma",
             "Delta", "Epsilon");

        source.compose(GuavaTransformers
            .toImmutableListMultiMap(s -> s.length())).subscribe(System.out::println);
    }

    /*This would be in the GuavaTransformers class */
    public static <T> Observable.Transformer<T,ImmutableList<T>> toImmutableList() {
        return obs -> obs.collect(() -> ImmutableList.<T>builder(), 
            (b,t) -> b.add(t)).map(b -> b.build());
    }

    public static <T,K> Observable.Transformer<T,ImmutableListMultimap<K,T>> 
            toImmutableListMultiMap(Func1<T,K> keyMapper) {

        return obs -> obs.collect(() -> ImmutableListMultimap.<K,T>builder(),
            (b,t) -> b.put(keyMapper.call(t), t)).map(b -> b.build());
    }
}
```

These may be trivial examples, but for larger applications these problems can quickly become amplified. Precious code real estate becomes eaten away even with the efficiencies of RxJava and Java 8 lambdas, and we have not even gotten to the subject of Tuples and data classes! But Kotlin solves all of these problems and more.

### Introducing Kotlin

I tried looking at Scala, Python, and other languages. I especially looked at Scala but despite all the praise it gets, I found it too esoteric. Then one day I found JetBrains sharing their new language called [Kotlin](https://kotlinlang.org/). They advertised it as an industry-grade, business-focused language emphasizing practicality rather than convention. JetBrains, the creator of the popular Java IDE Intellij IDEA, built it because they felt they could be more productive using a language that Java should have been. After studying Kotlin and re-writing two home projects with it, I quickly became sold and am ready to use it exclusively. The fact it is 100% interoperable with Java and all Java libraries made it a quick sell as well.

But in this post, what I really want to share is my experience using RxJava with Kotlin. **Ironically, I found RxJava works better with Kotlin than Java itself.** It just expresses functional programming concepts so much better.

For instance, I can "add" methods to the `Observable` using **extension methods**, without even extending the class! This is nothing new if you came from a C# background, but this was always the #1 thing I wished Java would have. Below I add `toImmutableList()` and `toImmutableListMultimap()` methods to the `Observable` (in Kotlin methods are actually called **functions**). I can then call those methods directly on the `Observable` rather than creating a `compose()` statement.

```kotlin
fun main(args: Array<String>) {

    val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    source.toImmutableListMultimap { it.length }.subscribe { println(it) }

}

fun <T> Observable<T>.toImmutableList() =
        collect({ ImmutableList.builder<T>()},{ b, t -> b.add(t)}).map { it.build() }

inline fun <K,T> Observable<T>.toImmutableListMultimap(
    crossinline keyMapper: (T) -> K) = collect({ ImmutableListMultimap.builder<K,T>()},
        { b, t -> b.put(keyMapper(t), t)}).map { it.build() }
```

There are a lot of observations to make here.

1.  We did not have to wrap these functions inside a class. Unlike Java, Kotlin does not force you to put static methods in a class. This is really helpful and helps eliminate a lot of boilerplate, especially for procedural programs.

2.  The type of the `source` variable is inferred, allowing us to not have to explicitly declare it as an `Observable<String>`. You can do that if you want to as shown below. In Kotlin the type comes _after_ the variable name (separated by a colon `:`). This is done because the variable name is likely more pertinent to you than the type, so it is declared first to making finding it easy.

    ```kotlin
    val source: Observable<String> = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    ```

3.  You can express lambdas much more easily. Instead of having to write out a lambda for a simple one-to-one mapping like `s -> s.length()`, you can express this with a much more succint `it.length`, where `it` refers to the single incoming item emitted (this is featured in other JVM languages). Also, there are no paranthesis `()` to receive functional arguments. Instead you use curley braces `{}` and express the entire function for that operator in it. This is especially helpful because you can put multiple lines in the curly braces `{ }` at any time.

    ```kotlin
    source.toImmutableListMultimap { it.length }.subscribe{ println(it)}
    ```

4.  You can "add" functions/methods to a class without actually extending a class using extension functions. This single statement below adds a `toImmutableList()` function to the `Observable` everywhere in your application (unless you make it `private` or alter its scope). How is this done? The compiler simply makes it a `static` method when turned into bytecode, but you get the nice syntactic sugar as well as seeing it in your auto-complete. You do not have to target generic types either with extension methods. For example, I could make a `concatStr()` extension method specifically targeting `Observable<String>` and not `Observable<T>`.

    ```kotlin
    fun <T> Observable<T>.toImmutableList() =
       collect({ ImmutableList.builder<T>()},{ b, t -> b.add(t)}).map { it.build()}
    ```

5.  Functional argument types are much simpler. Instead of expressing a functional type as `Func1<T,K>`, you can use a SAM-less type expression `(T) -> K`. This gets across much more easily that this function receives a `T` and turns it into a `K`. It is not a single-abstract-method type (SAM) which makes it easier to reason with and leaves out the question "which single-method interface am I using?". Of course, [Kotlin will handle converting lambdas to SAM when calling Java libraries, but it will not do it in Kotlin](http://stackoverflow.com/questions/34583595/behavior-with-kotlin-higher-order-functions-and-single-method-interfaces). Also, using the `inline` and `crossinline` keywords for a function accepting function arguments, [you can get great efficiency by eliminating object overhead](https://kotlinlang.org/docs/reference/inline-functions.html).
    
    ```kotlin
    inline fun <K,T> Observable<T>.toImmutableListMultimap(
       crossinline keyMapper: (T) -> K) = collect({ImmutableListMultimap.builder<K,T>()},
             { b, t -> b.put(keyMapper(t), t)}).map { it.build() }
    ```

### Data Classes

Another great feature of Kotlin is data classes. Have you ever wanted to simply zip two values together, but had to create an entire class just to pair them up with `hashCode()`, `equals()`, and `toString()` implemented?

```java
public final class JavaLauncher {

    public static void main(String[] args) {
        Observable<String> letter = Observable.just("Alpha", "Beta", "Gamma", 
            "Delta", "Epsilon");

        Observable<Integer> number = Observable.just(1,2,3,4,5);

        Observable<CodePair> zipped = Observable.zip(letter,number, 
            (l,n) -> new CodePair(l,n));

        zipped.subscribe(System.out::println);
    }

    private static final class CodePair {
        private final String letter;
        private final Integer number;

        CodePair(String letter, Integer number) {
            this.letter = letter;
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CodePair codePair = (CodePair) o;

            if (!letter.equals(codePair.letter)) return false;
            return number.equals(codePair.number);

        }

        @Override
        public int hashCode() {
            int result = letter.hashCode();
            result = 31 * result + number.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "CodePair{" +
                    "letter='" + letter + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
```

It is not fun I had to write 36 lines of code just to create a `CodePair` class holding two properties. This problem comes up in functional programming quite a bit, and the only alternative is creating esoteric tuples which only obfuscate the code.

But in Kotlin, you can declare something called a [data class](https://kotlinlang.org/docs/reference/data-classes.html). This allows you to quickly declare a class in one line with all its properties, and it will take care of the `hashCode()`, `equals()`, `toString()` and even clone/modify builders for you.

That 48-line mess in Java now becomes 5 lines in Kotlin.

```kotlin
fun main(args: Array<String>) {

    data class CodePair(val letter: String, val number: Int)

    val letter = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    val number = Observable.just(1, 2, 3, 4, 5)

    val zipped = Observable.zip(letter, number) { l, n -> CodePair(l, n) }

    zipped.subscribe { System.out.println(it) }
}
```

We declared a `CodePair` class right inside the `main()` function, and it only exists in the scope of the `main()` function. It has named properties `letter` and `number` you can access. This opens up a lot of tactical possibilities that were borderline impractical to do in Java. Being able to declare simple classes on the fly and have the common method implementations done allows fast, organized, and legible code to be developed quickly.

### Conclusions

I have only scratched the surface in sharing what Kotlin can do, with or without RxJava. This was not a tutorial but just a quick showcase of how RxJava expresses differently in Kotlin. I hope I have effectively shared my experience and you are curious to check out Kotlin. I know Scala can do quite a bit with RxScala, but Kotlin is different. It really serves folks who need the tactical abilities and simplicity of Python with the scalability and power of Java. When you throw RxJava into the mix with Kotlin, I have found it to be a very rewarding combination. Did I forget to mention that there are no primitives or boxed types either? There are so many features in Kotlin that would be out of scope to post here, [like nullable types](https://kotlinlang.org/docs/reference/null-safety.html)

As a sidenote, [Kotlin is supported on Android](https://kotlinlang.org/docs/tutorials/kotlin-android.html). You can also checkout the [RxKotlin](https://github.com/ReactiveX/RxKotlin) library which extends RxJava to take advantage of Kotlin functionalities (such as adding `toObservable()` methods to collections).

"""

Article(
  title = "Kotlin + RxJava = Functional Powerhouse",
  url = "http://tomstechnicalblog.blogspot.com.by/2016/02/kotlin-rxjava-what-rxjava-should-be.html?spref=tw",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Thomas Nield",
  date = LocalDate.of(2016, 2, 4),
  body = body
)
