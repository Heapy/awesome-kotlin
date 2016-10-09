---
title: 'More Kotlin Extension Fun'
url: http://oneeyedmen.com/more-kotlin-extension-fun.html
categories:
    - Kotlin
author: Duncan McGregor
date: Oct 06, 2016 03:39
---
This is a follow-up to my last post, [Extension Functions Can Be Utility Functions](http://oneeyedmen.com/extension-functions-can-be-utility-functions.html). Here I’ll look at more pros and cons of extension functions.

The article brought out 3 extension function use-cases.

1.  Adding a little local expressiveness with private methods like `JsonNode.toAddress()`
2.  Adding methods you wish were available on types that you don’t own - `JsonNode.getNonBlankText(name: String)`
3.  Keeping your own classes small by defining operations as extension functions rather than actual methods - `AccessType.toUIString()`

On the Kotlin Slack Channel, Daniel Wellman asked

_“How do you decide when methods should be extension functions and when they should be members (functions) on classes/objects _that you own_?”_

Nathan Armstrong replied

_“I think it’s usually when it encapsulates operations on a type in some context tangential to the one the type was made for. For example, when writing code that intersects two domains or concerns, using extension methods can help you use your existing types more naturally alongside one another without leaking that local utility into other parts of the codebase”_

He’s right. So right that I’m going to say that again with many more words and claim the credit.

Most of my Java projects have ended up with a package of domain objects - `Person`, `Address` etc. Kotlin makes these nice and simple with data classes, and when 1.1 allows inheritance these will become even more useful.

Over time these classes accrete operations that make sense to our application. Things like `Person.asJson()` and `Address.isCloseMyHouse()`. These operations dilute the essence of the class for our happy convenience, but after a while we can’t see the platonic wood for expedient trees.

Extension functions let us express the PersonNess in the `Person` class, and the JSONness elsewhere. In this case maybe type-classes would be better, but for now, we can move our JSON writing and useful-to-us-but-not-really-essential operations off of the class and into extension functions.

A nice side-effect of this is that we can build our domain classes in a module that doesn’t have dependencies on Jackson and some SQL library and the whole of the rest of our app. Maybe we’ll finally get reuseable abstractions for those fundamental real-world things that every app has to implement from scratch because of all the special cases that define our Customer rather than yours.

A word of warning though. Extension functions are not polymorphic. If I have

```kotlin
class Person

class Customer: Person

fun Person.toJson() = ...

fun Customer.toJson() = ...
```

the bindings are as follows:

```kotlin
Person().toJson() // calls Person.toJson()

Customer().toJson() // calls Customer.toJson()

val person: Person = readCustomer(42)
person.toJson() // calls Person.toJson() no matter what the actual type of person
```

If you like this, [Nat Pryce](http://www.natpryce.com) and I are going to be talking about Expressive Kotlin at the [Kotlin Night London](https://info.jetbrains.com/Kotlin-Night-London.html) next Wednesday, 12 October 2016.

Thanks to [Springer Nature](http://www.springernature.com) for allowing me to publish examples from their code. If you’re looking for a Kotlin job in London, they are hiring - please contact me using one of the links in the sidebar.
