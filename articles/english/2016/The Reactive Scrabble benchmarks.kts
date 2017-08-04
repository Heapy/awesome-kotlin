
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
### Introduction

In the past year, I've been posting benchmark results under the mysterious Shakespeare Plays (Reactive) Scrabble name. In this blog post, I'll explain what this benchmark is, where does it come from, how it works, what the intent is and how to apply it to your favorite and not-yet-benchmarked library.  

### History

The benchmark was [designed and developed](https://github.com/JosePaumard/jdk8-stream-rx-comparison) by [Jose Paumard](https://twitter.com/JosePaumard) and results presented in his [2015 Devoxx talk](https://www.youtube.com/watch?v=fabN6HNZ2qY) (a bit long but worth watching). The benchmark measures how fast a certain data-processing library can find the most valuable word from a set of words taken from (one of) Shakespeare's work based on the rules and point schema of Scrabble. RxJava at the time was in its 1.0.x version and to my surprise, it performed poorly compared to Java 8 Streams:  

[![](https://4.bp.blogspot.com/-GfETGQdChZo/WGJLllf3ddI/AAAAAAAAHiA/4d0vZHnioPE-yeQu1RY8OaULmi-gfQvQACLcB/s640/jose_scrabble_results.png)](https://4.bp.blogspot.com/-GfETGQdChZo/WGJLllf3ddI/AAAAAAAAHiA/4d0vZHnioPE-yeQu1RY8OaULmi-gfQvQACLcB/s1600/jose_scrabble_results.png)

[https://youtu.be/fabN6HNZ2qY?t=8369](https://youtu.be/fabN6HNZ2qY?t=8369)

The benchmark, utilizing JMH, is completely synchronous; no thread hopping happens yet RxJava performs 10x slower, or more likely, it has 10x more overhead in the associated set of operators. In addition, Jose also added a parallel-stream version which runs the main "loop" in parallel before joining for the final result.  

More disappointingly, RxJava 2 developer preview the time was terrible as well (relatively, [measured](https://twitter.com/akarnokd/status/696291409209487360) on a weak CPU in February).  

Therefore, instead of blaming the benchmark or the author, I set out on a quest to understand the benchmark's expectations and improve RxJava 2's performance and if possible, port that back to RxJava 1.  

### The original Stream-benchmark

Perhaps the most easy way to understand how the computation in the benchmark works, Let's see the original, non-parallel `Stream` version of it. Since going sequential or parallel requires only a `sequential()` or `parallel()` operator on a `Stream`, they both extend an abstract superclass containing the majority of the code and only get specialized for the operation mode in two additional classes.  

[ShakespearePlaysScrabbleWithStreamBeta.java](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithStreamsBeta.java)  

I added postfix "Beta" - meaning alternate version in this context - to distinguish between a version that has a slight difference in one of the computation steps. I'll explain why when I describe the original RxJava-benchmark down below.  

The benchmark is built in a somewhat unconventional, perhaps over-functionalized manner and I had a bit of trouble putting the functionality back together in my head. It isn't that complicated though.  

The inputs to the benchmark are hidden in a base class' fields `shakespeareWords` (`HashSet<String>`), `scrabbleWords` (`HashSet<String>`), `letterScore` (`int[]`) and `scrabbleAvailableLetters` (`int[]`). `shakespeareWords` contains all the words, lowercased, of Shakespeare's work. `scrabbleWords` contains the allowed words, lowercased, by Scrabble itself. `letterscore` contains the scores of the scores of letters through a-z and `scrabbleAvailableLetters` (seems to me) is there to limit the score if the particular letter appears multiple times in a word.  

The benchmark, due to dependencies of one step on the other, is written in "backwards" order, starting with a function that finds the score of a letter. Given an English letter with code `96-121`, the function maps it to the `0-25` range and gets the score from the array.  

```java
IntUnaryOperator scoreOfALetter = letter -> letterScores[letter - 'a'];
```

The next function, given a histogram of letters in a word in the form of a `Map.Entry` (where the key is the letter and the value is the number of occurrence in the word), calculates a bounded score of that letter in the word.

```java
ToIntFunction<Entry<Integer, Long>> letterScore =
    entry ->
        letterScores[entry.getKey() - 'a'] *
        Integer.min(
            entry.getValue().intValue(),
            scrabbleAvailableLetters[entry.getKey() - 'a']
        );
```

For that, we need the actual histogram of words which is computed by the following function:  

```java
Function<String, Map<Integer, Long>> histoOfLetters =
    word -> word.chars()
                .boxed()
                .collect(
                    Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                    )
                );
```

This is where a particular dataflow library comes into play. Given a word as Java `String`, split it into individual characters and count how many of each character is in that word. For example, "jezebel" will count `1-j`, `3-e`, `1-z`, `1-b` and `1-l`. In the Stream version, the `IntStream` of characters provided by `String` itself is converted into a boxed `Stream<Integer>` and grouped into a `Map` by a counting standard collector with no key mapping. Note that the return type of the function is `Map` and not `Stream<Map>`.  

The next function calculates the blank score of a character occurrence:  

```java
ToLongFunction<Entry<Integer, Long>> blank =
    entry ->
        Long.max(
            0L,
            entry.getValue() -
                scrabbleAvailableLetters[entry.getKey() - 'a']
        );
```

Given an entry from the histogram above, it gives bonus points if the particular letter occurs more than its score in the `scrabbleAvailableLetters` array. For example, if the letter '`d`' appears twice, the `scrabbleAvailableLetters` for it is 1 and this computes to 1. If the letter 'e' appears twice, the array entry for it is 12 and the function computes 0.  

The next function combines the `histoOfLetters` with the `blank` function to compute the number of blanks in an entire word:  

```java
Function<String, Long> nBlanks =
    word -> histoOfLetters.apply(word)
                          .entrySet().stream()
                          .mapToLong(blank)
                          .sum();
```

Here the histogram of the letters in the given word is computed and returned in a `Map<Integer, Long>`, then each entry of this `Map` is streamed, mapped into the blank letter value and then summed up into a final value. (Honestly, I'm not familiar with the rules of Scrabble and this last two functions seem to be extra convolution to have the computation work harder.)  
The follow-up function takes the result of `nBlanks` and checks if a word can be written with 2 or less blanks:  

```java
Predicate<String> checkBlanks = word -> nBlanks.apply(word) <= 2;
```

The next 2 functions pick the first 3 and last 3 letters of a word:  

```java
Function<String, IntStream> first3 = word -> word.chars().limit(3);
Function<String, IntStream> last3 = word -> word.chars().skip(Integer.max(0, word.length() - 4));
```

These won't stay separated and are immediately combined back together:  

```java
Function<String, IntStream> toBeMaxed =
    word -> Stream.of(first3.apply(word), last3.apply(word))
                  .flatMapToInt(Function.identity());
```

Practically, the first 3 and last 3 letters (with possibly overlap for shorter words) are concatenated back into a single IntStream via `flatMapToInt`, i.e., "jezebel" will stream letter-by-letter as "jezbel".  

Given the merged character stream, we compute the maximum score of the letters:  

```java
ToIntFunction<String> bonusForDoubleLetter =
     word -> toBeMaxed.apply(word)
                      .map(scoreOfALetter)
                      .max()
                      .orElse(0);
```

Note that `IntStream.max()` returns `Optional`.  

We then calculate the final score of a word:  

```java
Function<String, Integer> score3 =
    word ->
        2 * (score2.apply(word) + bonusForDoubleLetter.applyAsInt(word))
        + (word.length() == 7 ? 50 : 0);
```

This involves a bonus 50 points for words with length 7 and twice the score of the base word and the double letter bonus. Note that both `score2` and `bonusForDoubleLetter` are evaluated once and multiplied by the literal two.  

Now we reach the actual "loop" for calculating the scores of each word in the Shakespeare word set:  

```java
Function<Function<String, Integer>, Map<Integer, List<String>>> buildHistoOnScore =
    score -> shakespeareWords.stream()
             .filter(scrabbleWords::contains)
             .filter(checkBlanks)
             .collect(
                 Collectors.groupingBy(
                     score,
                     () -> new TreeMap<Integer, List<String>>(Comparator.reverseOrder()),
                     Collectors.toList()
                 )
             );
```

This is an odd function because it takes another function, the score function as input, returns a `Map` keyed by a score and a list of words that have that score. The body takes the set of `shakespeareWords`, streams it (the parallel version has `parallelStream()`) here, filters out those that are in the allowed Scrabble words set, filters out those that have less than two blank score, then groups the "remaining" words based on their computed score into a reverse-ordered `TreeMap` with `Integer` key and `List` elements - all with the help of standard `Stream Collectors`.  

Finally, we are not interested in all words but only the top 3 scoring set of words:  

```java
List<Entry<Integer, List<String>>> finalList =
                buildHistoOnScore.apply(score3)
                    .entrySet()
                    .stream()
                    .limit(3)
                    .collect(Collectors.toList());
```

We apply the `score3` function to the "loop" and put the top 3 entries into the final list. If all went well, we should get the following entries:  

```
120 = jezebel, quickly
118 = zephyrs
116 = equinox
```

### The original RxJava benchmark

Given the fact that RxJava and Java Streams have quite similar APIs and equivalent operators, the original RxJava benchmark was written with an odd set of helper components and changes to the pattern of the functions above.  

The first oddity is the introduction of a functional style of counting: `LongWrapper`  

```java
interface LongWrapper {
    long get();
 
    default LongWrapper incAndSet() {
        return () -> get() + 1;
    }
}
 
LongWrapper zero = () -> 0;
LongWrapper one = zero.incAndSet();
```

(This over-functionalization is a recurring theme with Jose, see this year's [JavaOne video](https://www.youtube.com/watch?v=Y4XkWSAm2XU) for example.)  

The second oddity that many return types that were scalar in the `Stream` version above were turned into `Observable`s - which adds unnecessary overhead and no operational benefit. So let's see how the original benchmark looks like with RxJava 1:  

First, the `letterScore` now returns a single element `Observable` with the score value:  

```java
Func1<Integer, Observable<Integer>> scoreOfALetter
    letter -> Observable.just(letterScores[letter - 'a']);
 
Func1<Entry<Integer, LongWrapper>, Observable<Integer>> letterScore =
    entry ->
        Observable.just(
            letterScores[entry.getKey() - 'a'] *
            Integer.min(
                (int)entry.getValue().get(),
                scrabbleAvailableLetters[entry.getKey() - 'a']
            )
        );
```

This has cascading effects as depending functions now have to deal with an `Observable`. RxJava doesn't have the direct means to stream the characters of a `String` so a helper indirection was introduced by reusing `Stream`'s tools and turning that into an `Iterable` RxJava can understand: 

```java
Func1<String, Observable<Integer>> toIntegerObservable =
    string -> Observable.from(
        IterableSpliterator.of(string.chars().boxed().spliterator()));
```

Building the histogram now uses the `LongWrapper` and RxJava's `collect()` operator to build the `Map` with it:  

```java
Func1<String, Observable<HashMap<Integer, LongWrapper>>> histoOfLetters =
    word -> toIntegerObservable.call(word)
            .collect(
                () -> new HashMap<>(),
                (HashMap<Integer, LongWrapper> map, Integer value) -> {
                    LongWrapper newValue = map.get(value) ;
                    if (newValue == null) {
                        newValue = () -> 0L ;
                    }
                    map.put(value, newValue.incAndSet()) ;
                }
             );
```

Calculating blanks also return `Observable` instead of a scalar value:  


```java
Func1<Entry<Integer, LongWrapper>, Observable<long>> blank =
    entry ->
        Observable.just(
            Long.max(
                0L,
                entry.getValue().get() -
                    scrabbleAvailableLetters[entry.getKey() - 'a']
            )
        );
 
Func1<String, Observable<Long>> nBlanks =
    word -> histoOfLetters.call(word)
            .flatMap(map -> Observable.from(() -> map.entrySet().iterator()))
            .flatMap(blank)
            .reduce(Long::sum);
 
Func1<String, Observable<Boolean>> checkBlanks =
     word -> nBlanks.call(word)
                    .flatMap(l -> Observable.just(l <= 2L));
```

Now calculating the scores:  

```java
Func1<String, Observable<Integer>> score2 =
     word -> histoOfLetters.call(word)
             .flatMap(map -> Observable.from(() -> map.entrySet().iterator()))
             .flatMap(letterScore)
             .reduce(Integer::sum);
 
Func1<String, Observable<Integer>> first3 =
     word -> Observable.from(
              IterableSpliterator.of(word.chars().boxed().limit(3).spliterator()));
 
 
Func1<String, Observable<Integer>> last3 =
    word -> Observable.from(
                IterableSpliterator.of(word.chars().boxed().skip(3).spliterator()));
 
 
Func1<String, Observable<Integer>> toBeMaxed =
    word -> Observable.just(first3.call(word), last3.call(word))
                .flatMap(observable -> observable);
 
Func1<String, Observable<Integer>> bonusForDoubleLetter =
    word -> toBeMaxed.call(word)
            .flatMap(scoreOfALetter)
            .reduce(Integer::max);
```

(Note that `last3` returns the letters 4..n instead of the last 3 letters, not sure if this was intentional or not. Changing it to really return the last 3 letters has no measurable performance difference.)  

Then we compute the final score per word:  

```java
Func1<String, Observable<Integer>> score3 =
    word ->
        Observable.just(
            score2.call(word),
            score2.call(word),
            bonusForDoubleLetter.call(word),
            bonusForDoubleLetter.call(word),
            Observable.just(word.length() == 7 ? 50 : 0)
        )
        .flatMap(observable -> observable)
        .reduce(Integer::sum);
```

Remember the "times 2" from the `Stream` benchmark, here both scores are streamed again instead of multiplying their result by 2 (via map). This inconsistency with the original `Stream` alone is responsible of ~30% overhead with the original RxJava 1 benchmark. For comparison, when the same double-streaming is applied to the original Stream benchmark, its measured sample time goes from **27 ms/op** up to **39 ms/op**.  

Lastly, the processing of the entire Shakespeare word set and picking the top 3:  

```java
Func1<Func1<String, Observable<Integer>>, Observable<TreeMap<Integer, List<String>>>>
buildHistoOnScore =
     score -> Observable.from(() -> shakespeareWords.iterator())
              .filter(scrabbleWords::contains)
              .filter(word -> checkBlanks.call(word).toBlocking().first())
              .collect(
                  () -> new TreeMap<Integer, List<String>>(Comparator.reverseOrder()),
                  (TreeMap<Integer, List<String>> map, String word) -> {
                      Integer key = score.call(word).toBlocking().first();
                      List<String> list = map.get(key);
                      if (list == null) {
                          list = new ArrayList<>();
                          map.put(key, list);
                      }
                      list.add(word);
                  }
               );
 
List<Entry<Integer, List<String>>> finalList2 =
    buildHistoOnScore.call(score3)
    .flatMap(map -> Observable.from(() -> map.entrySet().iterator()))
    .take(3)
    .collect(
        () -> new ArrayList<Entry<Integer, List<String>>>(),
        (list, entry) -> {
            list.add(entry);
        }
    )
    .toBlocking()
    .first();
```

Here, we need to go blocking to get the first (and) only value for the `checkBlanks` as well as getting the only List of results of the final list of top 3 scores and words. The collector function taking the `TreeMap` and the current entry had to be explicitly typed because for some reason Eclipse can't properly infer the types in that expression.  

### The optimized version

The mistakes and drawbacks of the original RxJava version has been identified over a long period of time and the optimized benchmark is still "under optimization". Using the right operator for the right job is essential in synchronous processing and some are better suited for this type of work and have less overhead due to their need to support an asynchronous operation mode. The other important thing is to know when to use a reactive type and when to stick to a scalar value.  

As mentioned above, the original RxJava benchmark had a bunch of the functions return `Observable` with a scalar value for no apparent benefit. Changing these back to scalar functions - just like the Stream version helps avoid unnecessary indirection and allocation:  

```java
Func1<Integer, Integer> scoreOfALetter = letter -> letterScores[letter - 'a'];
```

Streaming the characters of a word is the hottest operation and is executed several tens of thousands of time. Instead of the `Stream-Spliterator` indirection, one can simply index-map a string into its characters:  

```java
word -> Observable.range(0, word.length()).map(i -> (int)word.charAt(i));
```

Instead of the convoluted `LongWrapper` and its lambda-capture overhead, we can define a simple mutable container for the histogram:  

```java
public final class MutableLong {
 
    public long value;
 
    public void incAndSet() {
        value++;
    }
}
 
Func1<String, Observable<HashMap<Integer, MutableLong>>> histoOfLetters =
     word -> toIntegerObservable.call(word)
             .collect(
                 () -> new HashMap<>(),
                 (HashMap<Integer, MutableLong> map, Integer value) -> {
                     MutableLong newValue = map.get(value);
                     if (newValue == null) {
                         newValue = new MutableLong();
                         map.put(value, newValue);
                     }
                     newValue.incAndSet();
                 }
 
              );
```

The next optimization is the use of `flatMapIterable` and there is no need to get the iterator of an `entySet()` but just iterate it since it already implements `Iterable`:  

```java
Func1<String, Observable<Long>> nBlanks =
    word -> MathObservable.sumLong(
                histoOfLetters.call(word)
                .flatMapIterable(map -> map.entrySet())
                .map(blank)
            );
```

In addition, `reduce()` has some overhead because of constant boxing and unboxing of a sum or max value of the stream and can be replaced by a dedicated operator from the [RxJavaMath](https://github.com/ReactiveX/RxJavaMath) library: `MathObservable.sumLong()`.  

In synchronous scenarios, `concat` works better than `merge/flatMap` most of the time:  

```kotlin
Func1<String, Observable<Integer>> toBeMaxed =
    word -> Observable.concat(first3.call(word), last3.call(word));
 
Func1<String, Observable<Integer>> score3 =
    word ->
        MathObservable.sumInteger(
            Observable.concat(
                score2.call(word).map(v -> v * 2),
                bonusForDoubleLetter.call(word).map(v -> v * 2),
                Observable.just(word.length() == 7 ? 50 : 0)
            )
        );
```

Note the use of `map(v -> v * 2)` to multiply the two score components instead of streaming them again.  

In addition, there were several internal optimizations to RxJava to improve performance with this type of usage: `concat(o1, o2, ...)` received a dedicated operator instead of delegating to the `Observable` of `Observable`s overload. The `toBlocking().first()` overhead has been improved as well. Currently, the optimized benchmark with RxJava 1.2.4 runs under **67 ms/op**, the "Beta" benchmark runs under **100 ms/op** and the original benchmark runs under **170 ms/op**.  

### Benchmarking other libraries

Following similar patterns, other streaming libraries (synchronous and asynchronous) were benchmarked over the year. The following subsections summarize what it takes to have them do the Scrabble computation with the functional structures above, how they perform and why are they at the speed they are.  

[![](https://4.bp.blogspot.com/-V_cLB3fnjQM/WGJ4q3uAqBI/AAAAAAAAHiQ/TpuXI4nHu0gvPBXsrvKGk0y4w4C8Hn6vACLcB/s640/Scrabble_12_14.jpg)](https://4.bp.blogspot.com/-V_cLB3fnjQM/WGJ4q3uAqBI/AAAAAAAAHiQ/TpuXI4nHu0gvPBXsrvKGk0y4w4C8Hn6vACLcB/s1600/Scrabble_12_14.jpg)

[https://twitter.com/akarnokd/status/808995627237601280](https://twitter.com/akarnokd/status/808995627237601280)

#### Kotlin

Kotlin has its own, rich synchronous streaming standard library and performs quite well in the optimized benchmark: **20 ms/op**. It requires a [separate project](https://github.com/akarnokd/akarnokd-misc-kotlin/blob/master/src/main/kotlin/hu/akarnokd/kotlin/scrabble/ScrabbleKotlin.kt#L195) due to a complete separate JVM language which works best under IntelliJ. I'm not deeply familiar with Kotlin thus I'm not sure what it makes that much faster than `Stream` (or IxJava).  

The streaming part of the language is certainly well optimized but it is also possible using `HashMap` with primitive types gets custom implementation. The streaming standard library is very rich and the whole Scrabble logic could be expressed without building new operators or invoking external libraries.  

#### IxJava

IxJava, short for _Iterable eXtensions_ for Java started out as a companion library to Reactive4Java, the first black-box re-implementation of the Rx.NET library on the JVM (2011). Since then, it has been rewritten from scratch based on the advanced ideas of RxJava 2. The [optimized benchmark](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithIxOpt.java) runs around **23 ms/op**, 3 ms faster than the `Stream` version. Currently, this is the fastest Java library to do the Scrabble benchmark and has all the operators built in for the task. It features less allocation and less indirection, plus there are optimizations for certain shapes of inputs (constant-scalar, single-element sources).  

#### RxJava 2

RxJava is the de-facto standard reactive library for Java 6+ and version 2 supports the Reactive-Streams initiative with its `Flowable` type. Version 2 was rewritten from scratch in late 2015 and then has been drastically re-architected in mid 2016. The late 2015 version performed poorly with the scrabble benchmark but still 2 times faster than RxJava 1 at the time. Since the dataflow types in RxJava have to anticipate asynchronous and/or backpressured usage with largely the same code path, they have a noticeable overhead when using them in a pure synchronous manner.  

Therefore, the Scrabble benchmark is implemented for the [backpressure-enabled Flowable](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithRxJava2FlowableOpt.java) and the [backpressure-lacking (but otherwise similarly advanced) Observable](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithRxJava2ObservableOpt.java) types. They perform **27.75 ms/op** and **26.83 ms/op** respectively. Unfortunately, the main RxJava library lacks dedicated operators such as streaming the characters of a `String` and summing up a stream of numbers and these were implemented in the [RxJava 2 Extensions](https://github.com/akarnokd/RxJava2Extensions) companion library. The additional performance improvement over RxJava 1.x come from the much leaner architecture with fewer indirections, fewer allocations, dedicated `concat(source1, source2, ...)` operator, a very low overhead `blockingFirst()` and generally the operator fusion many operators participate in. In the late release-candidate phase, it was decided certain operators return `Single`, `Completable` or `Maybe` instead of its own type. The change did not affect the benchmark result in any measurable way (but the code had to change to work with the new types of course).  

In addition, the extension library features a `ParallelFlowable` type that allows parallel computations over a regular `Flowable` sequence, somewhat similar to parallel Streams. The parallelization happens for the set of Shakespeare words and requires a manual reduction back to sequential reactive type:  

```kotlin
Function<Function<String, Flowable<Integer>>, Flowable<TreeMap<Integer, List<String>>>>
buildHistoOnScore =
    score ->
        ParallelFlowable.from(Flowable.fromIterable(shakespeareWords))
        .runOn(scheduler)
        .filter(scrabbleWords::contains)
        .filter(word -> checkBlanks.apply(word).blockingFirst())
        .collect(
            () -> new TreeMap<Integer, List<String>>(Comparator.reverseOrder()),
            (TreeMap<Integer, List<String>> map, String word) -> {
                Integer key = score.apply(word).blockingFirst();
                List<String> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(key, list);
                }
                list.add(word);
            }
        )
        .reduce((m1, m2) -> {
            for (Map.Entry<Integer, List<String>> e : m2.entrySet()) {
                 List<String> list = m1.get(e.getKey());
                 if (list == null) {
                     m1.put(e.getKey(), e.getValue());
                 } else {
                     list.addAll(e.getValue());
                 }
            }
            return m1;
        });
```

The parallel version measures **7.23 ms/op** compared to the Java parallel Streams version with **6.71 ms/op**.  

#### Reactor 3

Pivotal's Reactor-Core library is practically RxJava 2 under a different company banner and implementation differences due to being Java 8+, originally contributed by me and as of today, the relevant components of Reactor 3 required by the Scrabble benchmark still uses my algorithms. The few implementation differences come from the use of atomic field updaters instead of atomic classes (such as AtomicInteger) which reduces the allocation amount even further. Unfortunately, even though the same field updaters are available for Java 6 and Android, certain devices don't play nicely with the underlying reflection mechanics. The [optimized benchmark code](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithReactor3Opt.java) uses custom implementation for streaming the characters of a word and finding the sum/max of a sequence.  

Given this difference, Reactor measures **27.39 ms/op**, putting it between RxJava 2's `Observable` and `Flowable`, somewhat expectedly.  

Reactor 3 has direct support for converting to its parallel type, `ParallelFlux`, which is also practically the same as RxJava 2 Extensions' `ParallelFlowable`. The [ParallelFlux' benchmark](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithReactor3ParallelOpt.java) clocks in at **8,53 ms/op**, however, that 1 ms difference to RxJava 2 is certainly odd and unclear why.  

#### Guava

Google Guava is library with lots of features, among other things, offering sub-library with a fluent-API support with `FluentIterable`. It has a limited set of streaming operators and the implementation has some unccessary overhead in it. The design reminds me of RxJava 1's Observable where there is a mandatory indirection to an inner type.  

Given the limited API, the [optimized benchmark code](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithGuavaOpt.java) uses custom operators such as streaming the characters of a word, custom `sum`/`max` and custom `collect()` operators, all written with the vocabulary of `FluentIterable` by me. Therefore, the measured `35.98 ms/op` is not entirely the achievement of the library authors.  

Interestingly, the backpressure-enabled, async capable `Flowable`/`Flux` outperforms the sync-only and thus theoretically lower overhead `FluentIterable`.  

#### Ix.NET

When the Rx.NET was developed several years ago, they implemented its dual, the Interactive eXtensions building a rich API over their standard, synchronous streaming IEnumerable interface.  

Ix.NET is well optimized, most likely due to the nice language features (`yield return`) and great compiler (state machine building around `yield return`). Even though .NET supports "primitive specialization", their JIT compiler is not a runtime optimizing compiler and this is likely why the ported [Scrabble benchmark measures](https://github.com/akarnokd/akarnokd-misc-dotnet/blob/master/akarnokd-misc-dotnet/ShakespearePlaysScrabbleIxNET.cs) only **45.4 ms/op**.  

Unfortunately, there were some missing operators from Ix.NET I had to write manually, such as the now-typically needed streaming of characters and the `reduce()` operator to support sum/max. (There is no need for custom sum because of the primitive specialization of `reduce()` provided automatically.)  

#### Reactor.NET

About a year ago, there was a non-zero chance I had to learn and include C# development in my professional line of work. Unfortunately, Rx.NET was and still is an old library with a significant performance overhead due to its synchronous ties, namely returning an `IDisposable` from `Subscribe()` instead of injecting it via an `OnSubscribe()` like all the other 3rd generation (inspired) libraries do. When 3.x didn't change the architecture, I decided instead of battling them over advancing, I could just roll my own library. Since in early 2016 I was involved with Pivotal's Reactor and its third-the-size API surface, I started working on [Reactor-Core.NET](https://github.com/reactor/reactor-core-dotnet) with all the 4th generation goodies RxJava 2 and Reactor now feature. Unfortunately, the risk of me doing C# faded and I took over leading RxJava, sending this project into sleep.  

Regardless, enough operators were implemented already so the [Scrabble benchmark for it](https://github.com/akarnokd/akarnokd-misc-dotnet/blob/master/akarnokd-misc-dotnet/ShakespearePlaysScrabbleReactorCore.cs) is available and measures **80.51 ms/op**. It may be party due to the .NET platform and also due to a less-than-optimal implementation for streaming characters.  

#### JOOLambda

Back to the Java land, [this library](https://github.com/jOOQ/jOOL) is part of the JOOx family of extension libraries, supporting JVM operations such as JDBC-based database interactions to extending the standard Java Stream with features. This, unfortunately, means wrapping a `Stream` or their `Seq` type and thus adding a level of indirection. This wouldn't be much of a problem but the API lacks operators that stay in the Seq type for tasks such as collect or sum/max. Therefore, these operators had to be emulated with other operators. A second unfortunate property of JOOLambda is the difficulty of extending it (even non-fluently). I could't find any way of implementing my own operator directly (as with the Rx style and Ix-style APIs) and the closest thing wanted me to implement 70+ standard `Stream` operators again.  

I believe it is still interesting to show how a convenient `collect()` operator can be implemented if there is no `reduce()` or even `scan()` to help us:  

```java
Function<String, Seq<HashMap<Integer, MutableLong>>> histoOfLetters =
    word -> {
        HashMap<Integer, MutableLong> map = new HashMap<>();
        return charSeq.apply(word)
               .map(value -> {
                    MutableLong newValue = map.get(value);
                    if (newValue == null) {
                        newValue = new MutableLong();
                        map.put(value, newValue);
                    }
                    newValue.incAndSet();
                    return map;
               })
               .skip(Long.MAX_VALUE)
               .append(map);
        };
```

First, the resulting `HashMap` is instantiated, knowing that this function will be invoked sequentially, non-recursively thus there won't be any clash between computations of different words. Second, we stream the characters of the word, map each character into the histogram inside the map. We need only a single element of the `HashMap` but there is no `takeLast()` operator to ignore all but the very last time the map is forwarded. Instead, we skip all elements and concatenate the single `HashMap` again to the now empty `Seq`.  

Summing up values is none the less convoluted with JOOL:  

```kotlin
Function<String, Seq<Integer>> score2 =
    word -> {
        int[] sum = { 0 };
        return histoOfLetters.apply(word)
               .flatMap(map -> Seq.seq(map.entrySet()))
               .map(letterScore)
               .map(v -> sum[0] += v)
               .skip(Long.MAX_VALUE)
               .append(0)
               .map(v -> sum[0]);
    };
```

We setup a single element array to be the accumulator for the summing, stream the histogram and sum up the letter scores into this array. We then skip all of it and concatenate 0 followed by mapping (this zero) to the contents of the sum array. Note that `append(sum[0])` is evaluated at assembly time (before the sum actually happens) yielding the initial zero every time.  

The code measures **86-92 ms/op**, however, this might not be that bad because when I'm writing this post, I've noticed a [missing optimization](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithJOOLOpt.java#L54) that adds unnecessary burden to a core computation - my bad. No worries, I'll remeasure everything again next year since some libraries have since updated their code.  

#### Cyclops-React

[This is an odd library](https://github.com/aol/cyclops-react), developed mainly by one person. Looking at the Github site I'm sure it used to say Reactive-Streams in the title. I've come across this library a month or so back when the author posted an extensive post about the benefits of it by extending Java Stream with [missing features and reactive concepts](https://medium.com/@johnmcclean/java-8-streams-10-missing-features-ec82ee90b6c0#.j17ift5f9). When I see "library" and "Reactive-Streams" I jump - writing a reactive library is a very difficult task. It turns out, the library's call in of "Reactive-Streams" was a bit misleading. It is no more reactive than IxJava, which is a completely synchronous streaming API, with the exception that there is a wrapper/converter to a Reactive-Streams Publisher. IxJava has that one but only in various other reactive libraries: `Flux.fromIterable()` and `Flowable.fromIterable()`.  

That aside, it is still a kind of dataflow library and as such can be benchmarked with Scrabble. Cyclops-React builds on top of JOOLambda and my first [naive implementation](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithCyclopsReactOpt.java) performed similarly to JOOLambda (to be precise, I measured Cyclops-React first, then JOOLambda to see where the poor performance might come from).  

Cyclops-React at the time didn't have any `collect()`/`reduce()` operators but it has `scan` (called `scanLeft`) and `takeLast` (called `takeRight`), allowing me to build the necessary computation steps:  

```kotlin
Function<String, ReactiveSeq<HashMap<Integer, MutableLong>>> histoOfLetters =
    word ->  toIntegerIx.apply(word)
             .scanLeft(new HashMap<Integer, MutableLong>(), (map, value) -> {
                 MutableLong newValue = map.get(value) ;
                 if (newValue == null) {
                     newValue = new MutableLong();
                     map.put(value, newValue);
                 }
                 newValue.incAndSet();
                 return map;
             })
             .takeRight(1);
```

From allocation perspective, this is very similar to JOOLambda's workaround since the `HashMap` is instantiated when the outer function is called and not for the consumer of the aggregation like with RxJava's `collect()` operator. One convenience though is the `takeRight(1)` that picks the very last value of the map (as `scan` emits it every time a new source comes up).  

The first benchmarks with version 1.0.3 yielded **108 ms/op**. The diagram at the beginning of this section lists it twice. The author of Cyclops-React and I tried to work out a better optimization, but due to the different understanding what the Scrabble benchmark represents, we didn't come to an agreement on the proper optimization (he practically wanted to remove `ReactiveSeq`, the base type of the library, and basically benchmark Java Stream again; I want to measure the overhead of ReactiveSeq itself).  

Since then, version 1.0.5 has been released with library optimizations and my code runs under **54 ms/op** while having the same structure as before. The author has also run a few Scrabble benchmarks of his own [that show lower overhead](https://twitter.com/cyclops_aol/status/809544009236496384), comparable to `Stream` now. If he achieved it by honoring the structure, that's fantastic. If he practically skipped his own type as the workhorse, that's bad.  

#### Rx.NET

The first, modern reactive library was designed and developed more than 8 years ago at Microsoft. Since then, Rx.NET has become open source, had 3 major releases, and helps (drives?) famous technologies such as Cortana.  

It's a bit sad it couldn't evolve beyond its 1st generation reactive architecture. First, it has heavily invested developers who are quite comfortable with how it is implemented, second, the .NET platform has absorbed its base interface types, `IObservable` and `IObserver`, that have the unfortunate design of requiring a synchronous `IDisposable` to be returned. Luckily, the 4th generation architecture works on the .NET platform and the community driven [Reactive-Streams.NET](https://github.com/reactive-streams/reactive-streams-dotnet) initiative may give some hope there as well.  

This unfortunate design remnant is visible in the [Scrabble benchmark](https://github.com/akarnokd/akarnokd-misc-dotnet/blob/master/akarnokd-misc-dotnet/ShakespearePlaysScrabbleRxNET.cs): **413 ms/op**. The main overhead comes from the trampolining the `range()` and enumerable-to-Observable conversion have. This trampolining is necessary to solve the synchronous cancellation problem RxJava solved by having a stateful consumer with a flag and callback mechanism indicating cancellation (which lead to the Subscription injection method in Reactive-Streams).  

Interestingly, I've [implemented](https://github.com/akarnokd/akarnokd-misc-dotnet/tree/master/akarnokd-misc-dotnet/observablex) a minimalist, non-backpressured type `Ox`, similar to RxJava 2's Observable type and [it measures](https://github.com/akarnokd/akarnokd-misc-dotnet/blob/master/akarnokd-misc-dotnet/ShakespearePlaysScrabbleOx.cs) **45 ms/op**, practically in par with the Ix.NET benchmark.  

#### Swave

Perhaps [this library](https://github.com/sirthias/swave/) is the youngest of the "reactive" libraries. It's implementation resembles of Akka-Stream with the graph-like internal workings, but it is not a native Reactive-Streams library. It has conversion from and to Publisher but steps themselves aren't Publishers. This adds interoperation overhead. In addition, the library is part of the _Yoda-family_ of reactive libraries; there is no `retry`. (Maybe because for retry to work, one needs to hold onto the chain that establishes the flow and allow resubscribing without the need for manual reassembing the entire flow.) The library is written in Scala entirely and I gave up on trying to call it from a Java project, hence a separate project for it.  

The library itself appears to be single developer only and the documentation is lacking a bit at the moment - not that I can't find operators on my own but a few times it was unclear I'm fighting with the Scala compiler (through IntelliJ) or with this library (you know, when IntelliJ says all is okay but then the build fails with a compilation error due to implicits). The library, version 0.5 at least, didn't have `collect`, `reduce`, `sum`, `max` but it does have `takeLast` and the emulations mentioned before work.  

None the less, I managed [to port the benchmark](https://github.com/akarnokd/akarnokd-misc-scala/blob/master/src/main/scala/ScrabbleWithSwave.scala) to Scala and run it, getting a surprising **781 ms/op**. Since I can't read Scala code, I can only speculate this comes from the graph-architecture overhead and/or some mandatory asynchronous-ness implicitly present.  

#### Akka-Stream

I've read so much goodness about Akka-Stream, about the technologies and frameworks it supports, its advanced and high performance optimizations over the flow-graph, the vibrant community and developer base around it, the spearheading of the Reactive-Streams initiative itself yet it constantly fails to deliver for me. In addition I've recently found out Akka-Stream is just inspired by Reactive-Streams and the reason they provide converter/wrapper to a Publisher instead of implementing it at every step is because working Reactive-Streams' deferred nature is too hard. Also I couldn't find any means for retrying an Akka-Stream `Source` so it could be yet another Yoda-library (so how does it support resilience then?).  

At least Akka-Stream has a Java DSL so I could implement the [Scrabble benchmark](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/scrabble/ShakespearePlaysScrabbleWithAkkaStreamOpt.java) within the familiar Java context. The DSL doesn't have `collect` but supports `reduce` (thus sum and max requires minimal work). Therefore, the collect operations were implemented with the same `map+drop(Long.MAX_VALUE)+concat(map)`.  

The benchmark results are "mind-blasting": **5563 ms/op**, that is, it takes about 5.5 seconds to compute the Scrabble answer once. Since Akka-Stream is originally written in Scala, I don't know for sure the source of this overhead but I have a few ideas: the graph-overhead, the mandatory asynchronous nature and perhaps the "fusion optimization" they employ that wastes time [trying to optimize](https://github.com/akka/akka/issues/20218) a graph that can't be further optimized.  

This problem seem to hit any use case that has `flatMap` in it - one of the most common operator involved in Microservices composition. Of course, one can blame the synchronous nature Scrabble use case which is not the target for Akka-Stream, however, its interoperation capabilities through Reactive-Streams Publisher shows some serious trouble (ops/s, larger is better):  

[![](https://2.bp.blogspot.com/-Ln91ysLDeC0/WGKwuJHW3kI/AAAAAAAAHiw/MU3rVRtMqmgZowQyRnuKmOJExEfdpBFawCLcB/s640/akka-rx-crossperf.png)](https://2.bp.blogspot.com/-Ln91ysLDeC0/WGKwuJHW3kI/AAAAAAAAHiw/MU3rVRtMqmgZowQyRnuKmOJExEfdpBFawCLcB/s1600/akka-rx-crossperf.png)

Here, [the task is to deliver](https://github.com/akarnokd/akarnokd-misc/blob/master/src/jmh/java/hu/akarnokd/comparison/AkkaStreamsCrossMapPerf.java) 1M elements (part of an Integer array) from one thread to another where the work is divided between Akka-Stream and RxJava 2: one delivers `count` number of `1M/count` items, and the other flattens the latter sub-section back to a single stream at the other side. Surprisingly, using Rx as the driver or middle worker improves throughput significantly (but not always). This benchmark stresses mostly the optimizer of Akka-Stream. Do people `flatMap` with Akka-Stream at all and nobody noticed this?  

### Conclusion

Writing a reactive library is hard, writing a benchmark to measure those libraries is at best non-trivial. Figuring out why some of them is extremely fast while others are extremely slow requires mastery in both synchronous and asynchronous design and development.  

Instead of getting mad at the Scrabble benchmark a year ago, I invested time and effort into improving and optimizing libraries that I could effect and thanks to it, those libraries are now considerably better at this benchmark and in general use due to the deep architectural and conceptional improvements.  

I must warn the reader about interpreting the results of the Scrabble benchmarks as the ultimate ranking of the libraries. The fact that libraries perform as they do in this particular benchmark doesn't mean they perform the same in any other situations with other type of tasks. The computation and/or IO overhead may hide the subtle differences in those cases, evening the field between them at the end.  

"""

Article(
  title = "The Reactive Scrabble benchmarks",
  url = "http://akarnokd.blogspot.com.by/2016/12/the-reactive-scrabble-benchmarks.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dávid Karnok",
  date = LocalDate.of(2016, 12, 27),
  body = body
)
