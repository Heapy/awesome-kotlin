---
title: 'Testing in Kotlin'
url: http://www.51zero.com/blog/testing-in-kotlin
categories:
    - Kotlin
    - Testing
author: Stephen Samuel
date: May 10, 2016 00:00
---
Following on from my recent article on Kotlin for Scala developers, in this blog I’ll introduce how you can use the [KotlinTest](https://github.com/kotlintest/kotlintest) framework to write unit tests in Kotlin, which is a great way to begin exploring Kotlin.

KotlinTest is based on [ScalaTest](http://www.scalatest.org/) (a superb jUnit like test framework created by Bill Venners) and offers the same choice of testing styles as well as many assertions and helpers for matching through the use of a light DSL.

To use KotlinTest first add the dependencies to your build. We’ll assume you’re using gradle (but obviously Maven is fine too).

```kotlin
compile 'io.kotlintest:kotlintest:1.1.1'
```

Then we can create a test. Each test must extend from one of the spec classes. Each spec class offers a different way of laying out your tests. Some are very similar and similar to jTest’s @Test annotation, and some are more complicated offering BDD style descriptions.

### Testing Styles

Lets give an example of the FlatSpec, which allows you to write tests like this:

```kotlin
class SomeTests : FlatSpec() {
  init {
    "ListStack.pop" should "return the last element from stack” {
      // your test here
    }
    "ListStack.pop" should "remove the element from the stack" {
      // another test
    }
  }
}
```

As you can see, you start with some text, then the keyword should, then some more text, and finally the test as a block. This gives you a readable description of what your test is doing. The split of the test description into two parts means that the IDE / build tools can nest the test output into something like:

ListStack.pop should

*   return the last element from stack    ok
*   remove the element from the stack    ok

All tests need to be placed inside the init {} block. For those of you coming from a Scala background, this is because Kotlin doesn’t allow the body of the class to be treated as the constructor like you can in Scala.

A quick example of another style is WordSpec, in which the tests are nested. So,

"ListStack.pop" should {
  "remove the last element from stack" {
    // test 1
  }
}

"ListStack.peek" should {
  "should leave the stack unmodified" {
    // test 2
  }
}

I think this is fairly self explanatory. The rest of the testing styles are available on the KotlinTest homepage.

### Matchers

Let's move onto the matchers. When writing your tests in Java, it is common to use assertions like assertEquals(a, b). This is fine, but we can do something a bit more readable with KotlinTest. As a simple example, to assert two values are equal, we can say

```kotlin
"a" shouldBe "a" or "a" shouldEqual "a"
```

We can do clever things with strings:

```kotlin
someStr should start with "foo"
someStr should end with "bar"
someStr should have substring "xxx"
```

Or with collections

```kotlin
myList should have size 4
mySet should contain element "a"
```

There are many matchers available for collections, strings, longs, ints, etc.

Sometimes we need to assert that an exception is thrown under certain conditions. The typical way to do this in other test frameworks is to wrap the code in a try/catch and then assert false at the bottom of the try, eg

```kotlin
try {
  // this code should throw an exception
  assert(false)
} catch (Exception e) {
  // good
}

```

We can remove this boilerplate in KotlinTest using the `shouldThrow<T>` function. Just wrap your code with this function, replacing T with the type (or supertype) of Exception you want to catch, eg

```kotlin
shouldThrow<IllegalArgumentException> {
  // code that should throw an exception
}
```

One of the more tricky things in testing, is when you have code that completes non-deterministically. Perhaps you’re sending off an async call and need to wait until a reply comes back.

The naive way to do this is to sprinkle your test code with Thread.sleeps but how long to wait? You don’t want the time-out to expire prematurely. So if your call takes 2 seconds to complete, you set your sleep to 5 seconds. Until the time your build server is running slow, and 5 seconds wasn't enough, so you set it to 15 seconds. But now you wait 15 seconds every time regardless.

Another tool is to use countdown latches, and these are often my preferred choice. Have some code trigger the latch once it's completed (i.e. in a callback) and have the main test thread block on the latch.

If that doesn't work, KotlinTest introduces the eventually function. This allows you to repeatedly probe for a test condition and once it passes the function will return. This means you can wait up to a set time, but no more. It’s very easy to use, you just supply the max time you’re prepared to wait before considering it a failure. Extend the Eventually interface in your test class, e.g.:

```kotlin
Class MyTest : FlatSpec(), Eventually {
  Init {
    "This test" should "finish quickly" {
      eventually(5, TimeUnit.SECONDS) {
        // code here
      }
    }
  }
}
```

Now if the assertions doesn't pass within 5 seconds, the test will fail. If the assertions pass quicker than 5 seconds, your test will return as soon as it can.

KotlinTest borrows another feature from ScalaTest in the form of inspectors. Inspectors are helpers for assertions on elements of collections.

Let's suppose you wanted to test that a collection of strings had one at most one string “root”, and the rest of the elements were not “root”, you could do this:

```kotlin
val count = strings.count { it == "root" }
(count ==0 || count == 1) shouldBe true
```

We can do this very easily using an inspector called forAtMostOne. This function will assert that there either zero or one elements pass the assertions, otherwise the test fails.

```kotlin
forAtMostOne(strings) {  it == "root" }
```

There are many inspectors: forAll, forNone, forSome, forExactly(k), forAtMostOne, forAtLeastOne. The full list can be found [here](https://github.com/kotlintest/kotlintest#inspectors)

Another quick example to assert that in a collection of people, some of them are called David some live in London, and some work for 51zero. (By some we mean at least 1, but not all).

```kotlin
forSome(people) { it .firstName() shouldBe "David" }
forSome(people) { it .location() shouldBe "London" }
forSome(people) { it .employer() shouldBe "51zero" }
```

Writing that without an inspector would be at least a few lines longer.

The final thing I want to mention is setting up and tearing down tests. We can do this before and after each test, or before and after each test suite (test suite is the class file). To do this we just override whichever methods we need, e.g.

```kotlin
override fun beforeEach() {
  println("Test starting")
}

override fun afterEach() {
  println("Test completed")
}

override fun beforeAll() {
  println("Setting up my tests")
}

override fun afterAll() {
  println("Cleaning up after my tests")
}
```

That’s all for now. This should help get you up and running and using Kotlin in your projects. In the next article, we’ll write about how you can create custom matchers to allow your own DSL syntax for complicated matchers.

Please let [us](http://www.51zero.com/about) know your experience of testing in Kotlin in the comments below.
