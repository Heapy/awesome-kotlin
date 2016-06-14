---
title: 'News from KotlinTest'
url: https://discuss.kotlinlang.org/t/news-from-kotlintest/1797
categories:
    - Testing
    - Kotlin
author: medium
date: Jun 14, 2016 00:00
---
In the last weeks [KotlinTest](https://github.com/kotlintest/kotlintest) got some new and useful features:

## Testing for Exceptions

```kotlin
val exception = shouldThrow<IllegalAccessException> {
  // code in here that you expect to throw an IllegalAccessException
}
exception.message should start with "Something went wrong"
```

## Before and after each

```kotlin
override fun beforeEach() {
  println("Test starting")
}

override fun afterEach() {
  println("Test completed")
}
```


## Test configuration

Each test case can followed by a call to the `config` function.

To execute a test two times with two threads you would write:

```kotlin
class MyTests : ShouldSpec() {
  init {

    should("return the length of the string") {
      "sammy".length shouldBe 5
      "".length shouldBe 0
    }.config(invocations = 10, threads = 2)

  }
}
```

You can tag tests to be able run them selectively:

```kotlin
...
}.config(tags = listOf("database", "linux"))
```

Timeouts are configured like this:

```kotlin
...
}.config(timeout = 2, timeoutUnit = TimeUnit.SECONDS)
```

To ignore a test, you set `ignored` to true:

```kotlin
...
}.config(ignored = true)
```

## Future

Some more features like property based (and table driven) testing are on the roadmap.