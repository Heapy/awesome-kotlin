---
title: 'Extension Functions Can Be Utility Functions'
url: http://oneeyedmen.com/extension-functions-can-be-utility-functions.html
categories:
    - Kotlin
author: Duncan McGregor
date: Oct 05, 2016 03:42
---
I enjoyed [Extension functions are not utility functions](https://medium.com/@dimsuz/extension-functions-are-not-utility-functions-74a5f9b53892#.o57pbn5k2)

I thought that I disagreed, but on more careful reading maybe not. Instead I’ll pull out some extension functions from my current codebase.

We’re finding that extension functions are a simple way to make code more expressive.

Without an extension function:

```kotlin
private fun addressFrom(node: JsonNode): Address = ...

val addresses = nodes.map { addressFrom(it) }
```

With an extension function:

```kotlin
private fun JsonNode.toAddress(): Address = ...

val addresses = nodes.map { it.toAddress() }
```

You might the find first example more readable like this:

```kotlin
private fun toAddress(node: JsonNode): Address = ...

val addresses = nodes.map(::toAddress)
```

in which case fill yer boots, but my mind doesn’t work that way.

Extension functions work well here, but the place where they really shine is method chaining.

Without them:

```kotlin
private fun addressFrom(node: JsonNode): Address = ...

private fun isInUK(address: Address): Boolean = ...

val isInUK = isInUK(addressFrom(node))
```

Extension functions don’t make you turn your brain inside out:

```kotlin
private fun JsonNode.toAddress(): Address = ...

private fun Address.isInUK(): Boolean = ...

val isInUK = node.toAddress().isInUK()
```

You may have noticed the `private` in the examples above - most of the extension functions that we write this way are used locally - just to make a few lines of code more intuitive. But that’s by no means universally true - this is from some library code to extend Jackson in more general ways:

```kotlin
fun JsonNode.getExpected(name: String): JsonNode = get(name) ?: throw MissingPropertyException("property '$name' is missing")

fun JsonNode.getNonBlankText(name: String) = getExpected(name).asText().apply {
    if (isBlank()) throw JsonInterpretationException("property $name is blank")
}
```

I had wondered whether this style was best suited to operations on types that we don’t own. But I find we have code that acts on our own types:

```kotlin
fun suggestionsTemplateDataFor(suggestions: List<JournalJson>) = mapOf(
    "results" to suggestions.withOAText()
)

private fun List<JournalJson>.withOAText(): List<Map<String, Any?>> = this.map { it.withOAText() }

private fun JournalJson.withOAText() = TreeMap(this.asPropertyMap().plus( "openAccessText" to this.accessType.toUIString()))

private fun AccessType.toUIString() = when (this) {
    AccessType.OPEN_ACCESS -> "Yes"
    AccessType.SUBSCRIPTION -> "No"
    AccessType.OPEN_CHOICE -> "Optional"
    else -> "Unknown"
}
```

Here `JournalJson` and `AccessType` are simple data classes, unencumbered by all the operations that the rest of the system would like them to have; because they can define operations like `withOAText()` as extension functions when they need them. And if defining an extension on `List<JournalJson>` isn’t a utility function I don’t know what is, but here it really helps us to at least get the gist of what is going on at the top level.

Hidden in there is a call to

```kotlin
fun Any.asPropertyMap(): Map<String, Any?> = SpaceBlanket(this)
```

which _is_ the archetypal extension function definition - high-level and fundamental.

There are more thoughts on this subject in a follow up article - [More Kotlin Extension Fun](http://oneeyedmen.com/more-kotlin-extension-fun.html)

If you like this, [Nat Pryce](www.natpryce.com) and I are going to be talking about Expressive Kotlin at the [Kotlin Night London](https://info.jetbrains.com/Kotlin-Night-London.html) next Wednesday, 12 October 2016.

Thanks to [Springer Nature](http://www.springernature.com) for allowing me to publish examples from their code. If you’re looking for a Kotlin job in London, they are hiring - please contact me using one of the links in the sidebar.
