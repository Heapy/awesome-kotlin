
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[JMock](http://www.jmock.org/) may have lost the mocking war, but for the London crowd it's still the go-to mocking tool.
It turns out that a little Kotlin fairy-dust can make it even more expressive.

In [my last post](/mocks-v-approvals-tests-part2.html) we saw a pretty vanilla use of JMock. In fact there was *one* nice
Kotlin'ism, defining an extension method on Mockery. So where in Java we would write

```java
mockery.checking(new Expectations() { {
    oneOf(progress).reset(2);
    oneOf(indexer).createIndex();
} })
```

in Kotlin, by defining

```kotlin
fun Mockery.expecting(block: MyExpectations.() -> Unit) {
   this.checking(MyExpectations().apply(block))
}
```

we can write

```kotlin
mockery.expecting {
    oneOf(progress).reset(2)
    oneOf(indexer).createIndex()
}
```

In addition, in the name of more readable tests, I had added a subclass of Expectations
to allow me to write, for example

```kotlin
mockery.expecting {
   givenActiveJournalIds(
       "1" to throwException(RuntimeException("oops")),
       "2" to returnValue(journal2))
   }
   ...
```

This was achieved with

```kotlin
class MyExpectations : Expectations() {
   fun givenActiveJournalIds(vararg idResultPairs: Pair<String, Action>) {
       allowing(journals).loadActiveIds()
       will(returnValue(idResultPairs.map { it.first }))
       idResultPairs.forEach { pair ->
           allowing(journals).loadJournalWithArticles(pair.first, 99)
           will(pair.second)
       }
   }
}
```

which is tighter in Kotlin, but nothing that you couldn't do in Java.

Looking at a one of the tests, this plugs together into something like

```kotlin
@Test fun reports_exceptions_and_continues() {
   mockery.expecting {
       val x = RuntimeException("oops")

       givenActiveJournalIds(
           "1" to throwException(x),
           "2" to returnValue(journal2))

       oneOf(progress).reset(2)
       oneOf(indexer).createIndex()

       never(indexer).index(JournalJson(journal1))
       oneOf(progress).exception("1", x)

       oneOf(indexer).index(JournalJson(journal2))
       oneOf(progress).indexed(journal2)
   }
   refresher.refresh(journals, indexer, emptySet())
}
```

This isn't bad, but one of JMock's problems is that it doesn't differentiate between interactions
that are queries, and those that are operations. In this case the refresher queries the `journals`
to find what needs to be indexed, then operates on the `indexer` to add them. Introducing
`givenActiveJournalIds` gives a clue, but it would be nice to see the split more formally.

What I'd like to see is

```kotlin
@Test fun reports_exceptions_and_continues() {
    val x = RuntimeException("oops")
    mockery.given {
        activeJournalIds(
            "1" to throwException(x),
            "2" to returnValue(journal2))
    }.whenRunning {
        refresher.refresh(journals, indexer, emptySet())
    }.thenExpect {
        oneOf(progress).reset(2)
        oneOf(indexer).createIndex()

        never(indexer).index(JournalJson(journal1))
        oneOf(progress).exception("1", x)

        oneOf(indexer).index(JournalJson(journal2))
        oneOf(progress).indexed(journal2)
    }
}
```

This can be achieved through 2 extension methods and a little class -

```kotlin
fun Mockery.expecting(expectations: Expektations.() -> Unit): Mockery {
    this.checking(Expektations().apply(expectations))
    return this
}

fun Mockery.whenRunning(block: () -> Unit) = ThenClause(this, block)

class ThenClause(private val mockery: Mockery, private val block: () -> Unit) {
    fun thenExpect(expectations: Expektations.() -> Unit) {
        mockery.expecting(expectations)
        block()
        mockery.assertIsSatisfied()
    }
}
```

Actually that trick [can be played in Java 8 too](https://github.com/dmcg/nowthen).

If you're really eagle-eyed, you may have noticed `Expektations` pop up in that example.
That's to support my final trick, an extension method on `Nothing`. Why?

Well what if we wanted to simulate an exception in the indexing?

```kotlin
@Test fun `reports exceptions in indexing and continues`() {
    val x = RuntimeException("oops")
    mockery.given {
        activeJournalIds(
            "1" to returnValue(journal1),
            "2" to returnValue(journal2))
    }.whenRunning {
        refresher.refresh(journals, indexer, emptySet())
        executor.runUntilIdle()
    }.thenExpect {
        oneOf(progress).reset(2)
        oneOf(indexer).createIndex()

        oneOf(indexer).index(JournalJson(journal1))
        will(throwException(x))
        oneOf(progress).exception("1", x)

        oneOf(indexer).index(JournalJson(journal2))
        oneOf(progress).indexed(journal2)
    }
}
```

That `will()` as a separate statement has always bothered me about JMock - it should
bind to the previous statement, but can't because `indexer.index(...)` returns `Unit`
(`void` in Java). In Kotlin we can define

```kotlin
class Expektations: Expectations() {
    fun Any?.will(action: Action) = super.will(action)
    // fun Nothing.will(action: Action) = super.will(action) Update 2016-05-17 - looks like this isn't needed, as Unit extends Any
}
```

and now within our expectation blocks we can write

``` koklin
{
    oneOf(indexer).index(JournalJson(journal1)).will(throwException(x))
    // or
    allowing(journals).loadJournalWithArticles("1", 99).will(returnValue(journal1))
}
```

With a bit of work I think that would allow a typed JMock `Action` so that you could only
`returnValue` with the correct type, but I haven't pulled on that thread yet.

The final bonus marks in this post go for spotting

```kotlin
@Test fun `reports exceptions in indexing and continues`()
```

Ignoring the fact that it breaks [Prism](http://prismjs.com/)'s Kotlin highlighter, this hack was pointed out by
[Nat Pryce](http://natpryce.com) - backticks allow spaces in method names, giving beautifully
readable specs in code and test runners.

"""

Article(
  title = "JMock and Kotlin",
  url = "http://www.oneeyedmen.com/jmock-and-kotlin.html",
  categories = listOf(
    "Unit Testing",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Duncan McGregor",
  date = LocalDate.of(2016, 5, 1),
  body = body
)
