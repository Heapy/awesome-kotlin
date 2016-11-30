---
title: 'Kotlin ternary operator'
url: https://gist.github.com/naixx/9d94c1498c4d45ffda3a
categories:
    - Kotlin
author: naixx
date: Nov 30, 2016 02:14
---
## Motivation

Kotlin is very expressive language, but like Scala it lacks of ternary operator. Currently language provided alternative `if` looks a bit verbose. 

```kotlin
val result = if (myExpression) 1 else 2
```
Compared to a classical Java or C++ variant

```java
int result = myExpression ? 1 : 2
```

Unlike Scala, Kotlin allows only fixed names for operators, so we cant fully reproduce classic syntax, but we can have something similar

```kotlin
val result = myExpression % 1 / 2

```

If you want to use complex boolean expression, you can wrap it in braces

```kotlin
val result = (a == null && b > 5) % 1 / 2
```

The impact I see here is temporary object creation, probably `inline` can't help.

```kotlin
class Ternary<T>(val expr: Boolean, val then: T) {
    public fun div(elze: T): T = if (expr) then else elze
}

public fun <T> Boolean.mod(a: T): Ternary<T> = Ternary(this, a)
```
