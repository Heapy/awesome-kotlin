---
title: 'UI Testing: separating Assertions from Actions with Kotlin DSL'
url: https://artemzin.com/blog/ui-testing-separating-assertions-from-actions-with-kotlin-dsl/
categories:
    - Kotlin
    - Testing
author: Artem Zinnatullin
date: Jul 27, 2016 08:52
---
Hello, dear reader!

Recently Jake Wharton did a talk ["Instrumentation Testing Robots"](https://realm.io/news/kau-jake-wharton-testing-robots/) 📺.

The pattern itself is not a new concept, **but** combining it with [Kotlin DSL](https://kotlinlang.org/docs/reference/type-safe-builders.html) is pretty nice! So we decided to try it for our [Juno app](https://play.google.com/store/apps/details?id=com.gojuno.rider) which is fully written in Kotlin, including Unit, Integration and Functional tests.

What we found is that mixing Actions and Assertions inside "Robots" (we call them Screens) doesn't look great, both for tests readability and "Robot" maintenance 🤖 (same is true for regular Screen abstractions).

> Though I used mixed variant for years… (sigh). For some reason, only seeing screen abstraction as a Kotlin DSL finally clicked that we need to divide assertions from actions.

#### Before: mixed Actions and Assertions 😿

```kotlin
// We actually use Spek but unfortunately I can't say
// that it works great for instrumentation tests yet…
@Test fun loginAndPasswordAreEntered() {
  loginScreen {

    login("artem_zin")
    password("*****")

    loginButtonActivated()
    noLoginWarnings()
    noPasswordWarnings()
  }
}
```

#### After: Assertions are separated from Actions 😸

```kotlin
@Test fun loginAndPasswordAreEntered() {
  loginScreen {

    login("artem_zin")
    password("*****")

    assert {
      loginButtonActivated()
      noLoginWarnings()
      noPasswordWarnings()
    }
  }
}
```

Now that's a clear separation of actions and assertions, right? Same is true for internal structure of `LoginScreen` class:

```kotlin
fun loginScreen(func: LoginScreen.() -> Unit) = LoginScreen().apply { func() }

class LoginScreen {

  fun login(login: String) = enterText(R.id.login, email)
  fun password(password: String) = enterText(R.id.password, password)
  fun assert(func: Assert.() -> Unit) = Assert().apply { func() }

  // Previously all these assertions 
  // were on the same level in the LoginScreen class as actions.
  class Assert {
    fun loginButtonActivated() = …
    fun noLoginWarnings() = …
    fun noPasswordWarnings() = …
  }
}
```

> Write tests and take care!
