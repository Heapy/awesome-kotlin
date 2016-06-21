---
title: 'Ad-hoc polymorphism in Kotlin'
url: http://beust.com/weblog/2016/06/20/ad-hoc-polymorphism-in-kotlin/
categories:
    - Kotlin
author: Cédric Beust
date: Jun 20, 2016 08:47
---
Even though Kotlin doesn’t natively support ad-hoc polymorphism today, it’s actually pretty straightforward to use it with little effort. Doing so is not as straightforward as it is in Haskell, obviously, but it’s been simple enough that I haven’t really encountered situations in Kotlin where the lack of native support in the language was a showstopper. In this article, I present two techniques you can use to leverage ad-hoc polymorphism in Kotlin.

## Extending for “fun” and some profit

Here is what the [Haskell wiki](https://wiki.haskell.org/Polymorphism) has to say about ad-hoc polymorphism:

> Despite the similarity of the name, Haskell’s type classes are quite different from the classes of most object-oriented languages. They have more in common with interfaces, in that they specify a series of methods or values by their type signature, to be implemented by an instance declaration.

Before we get into details, let’s define exactly what we are trying to do and why we’re trying to do it. One intuitive way of looking at ad-hoc polymorphism is that it enables us to retroactively make values conform to a certain type. This is a bit abstract but I’m pretty sure you have encountered this problem many times before even if you never realized it. Let’s look at a simple example.

A library typically defines types and then offers functions that take parameters and returns values of that type. The only way to make use of that library is to find a way to bring your objects into that library’s object world, or in other words, be able to convert your objects to objects that this library expects and vice versa. You will find many examples of type classes if you do a simple search: values that behave like a Number, Functor, Applicative, Monad, Monoid, Equality, Comparability, etc… In order not to repeat what’s already out there and in an effort to remain focused on concrete problems, I’m going to pick a different field: JSON.

The need to parse JSON and also convert your objects to JSON is pretty much universal, so in all likeliness, you are already using a JSON library in your code. And at some point, you have had to ask yourself a very simple question: “How do I convert my current objects to JSON so I can use this library”. The library probably defines some kind of `JsonObject` type and most of its API is defined in terms of this type, either with functions accepting parameters of that type or returning such values:

```kotlin
interface JsonObject {
    fun toJson() : String
}
```

In order to leverage this library, converting your objects so they conform to this interface is very important, and once you have converted your objects, you gain full access to all the functionalities that this library offers, such as pretty printing in JSON, doing search/replaces in JSON, reshaping JSON object from one form to another, etc…

If you own (i.e. you are the author of) these classes, doing so is very easy. For example, you can modify your class to implement that interface:

```kotlin
class Account : JsonObject {
    fun override toJson() : String { ... }
}
```

The advantage of this approach is that you can now pass all your `Account` values directly to functions of the JSON libraries that accept a `JsonObject`. This is very useful, but the downside is that you have now polluted your class with a concern that make your design more bloated. If you are going to extend this approach to other types, very soon, your `Account` class will extend multiple interfaces filling various functionalities, and you have now tied your business logic to a lot of dependencies (i.e. you now need this JSON library in order to compile your `Account`…).

Another approach is simply to write a function that converts your business objects to `JsonObjects`:

```kotlin
fun Account.toJson() : JsonObject { ... }
```

This defines an extension function that adds the method `toJson()` directly on the `Account` class, where it belongs. The extension function buys us two very important benefits:

* Since it’s an extension function, its implementation runs on the `Account` instance itself (in other word, `this` is of type `Account`).
* This function is defined without making any modification to the `Account` class itself. Not only does it leave your class untouched and unpolluted with unrelated concerns, you can also apply this approach to classes that you don’t own. This is extremely important and a critical step toward ad-hoc polymorphism.

With this approach, we have won separation of concerns and a great amount of flexibility but we have lost some typing power: we can no longer pass an instance of `Account` to a function accepting a `JsonObject` parameter, we need to call `toJson()` on that instance first.

This is how far we can go with Kotlin today and this fits in Kotlin’s general design principle to stay away from implicit conversions, a decision I’ve come to respect greatly after several years writing Kotlin code.

## Escaping the tyranny of nominal typing

Let’s look at another approach to implement ad hoc polymorphism. Consider the following simple function that saves an object to a database:

```kotlin
fun persist(person: Person) {
    db.save(person.id, person)
}
```

The object is saved to the database and associated to its `id`. A little later, I want to persist an `Account` object, and in the spirit of proper software engineering, I’d rather abstract my existing code rather than writing a second `persist()` function. So I make my function more generic and in the process, I discover that in order to be persisted, my `Account` instance needs to be able to give me its `id`. After refactoring, my code now looks like this:

```kotlin
interface Id {
    val id : String
}

class Person : Id {
    override val id: String get() = "1"
}

fun persist(id: Id) { ... }
```

But now, I find myself having to make `Account` implement `Id`, which is exactly what I am trying to avoid with ad hoc polymorphism (either because I think it’s bad design or more simply because the class `Account` is not mine, so I can’t modify it). The realization here is that these type names get in the way of my goal and I’d rather keep all these concepts separate.

What if, instead, the `persist()` method accepted an additional parameter (a function) that allows me to obtain an `Id` from its parameter?

```kotlin
fun <T> persist(o: T, toId: (T) -> String)

// Persist a Person: easy since Person extends Id
persist(person, { person -> person.id })

// Persist an Account: need to get an id some other way
persist(account, { account -> getAnIdForAccountSomehow(account) }
```

This new approach has a few interesting characteristics:

1.  Notice how completely generic the `persist()` method has become: it doesn’t reference `Person`, `Account` and not even `Id`, even though it needs some sort of id in order to operate. This function is literally applicable “for all” types (I am intentionally using double quotes here, some of you will probably immediately understand what “for all” means in this context).
2.  We have detached the ability to provide an id from our types. You still have the option to implement this functionality into your types (like `Person` does) but it’s now entirely optional (like `Account` shows). This gives you a lot of flexibility since you are now longer forced to use the id supplied by the class and you can also be more creative in your testing (e.g. trying to save two different objects but force them to have the same `id` in order to test for collision error cases).

This approach makes a drastic step toward a more functional solution to the problem of ad hoc polymorphism: we depend less on types and more on functions. As you can see, this approach provides some interesting benefits.

## An ad-hoc polymorphism proposal for Kotlin

When I reflected about this problem a while ago, it occurred to me that ad-hoc polymorphism has a lot in common with Kotlin’s extension functions: an extension function adds a function to a type outside of the definition of that type and ad-hoc polymorphism makes a type extend another type outside of the definition of that type. I came up with the concent of “Extension types” and I gave a quick overview of this idea [in this article](https://discuss.kotlinlang.org/t/extension-types-for-kotlin/1390). Extension types would allow us to make types retroactively implement other types with this made up syntax:

```kotlin
// Not legal Kotlin
override class String: Monoid<String>
```

The rest of the interface would be implemented with extension functions, as demonstrated in the link above. The downside of this proposal is that it adds some form of implicit conversion, something that is at odds with Kotlin’s current design, so it’s probably unlikely this proposal will go past the stage of strawman but I thought it would be interesting to draw a parallel between extension functions and extension types.

## Does Kotlin really need ad-hoc polymorphism?

The more I think about it, the more convinced I am that the value offered by ad-hoc polymorphism is very closely tied to the language you’re using it in. In other words, it’s not a universal tool but one that’s heavily dependent on how well supported it is in your language. Ad-hoc polymorphism is obviously a critical component of Haskell and it has given rise to high amounts of reuse and elegant abstractions in that language but I’m not sure Kotlin would benefit as much from it.

Another important aspect of deciding how useful ad-hoc polymorphism would be in a language is whether that language supports higher kinds (type families). Without higher kinds, your ability to abstract is limited, which lessens the value of ad-hoc polymorphism significantly. And since Kotlin doesn’t support higher kinds as of this writing, the importance of native support for ad-hoc polymorphism is questionable, or at least, certainly not as high a priority as other features.

At any rate, I have used the two techniques described above in my own code bases with reasonable benefit, so I hope they will be useful to others as well.
