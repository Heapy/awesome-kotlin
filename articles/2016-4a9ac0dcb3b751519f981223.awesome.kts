
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
After [Exercises in Kotlin: Part 3 - Functions](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-3-functions/) we now take a look at control flows and actually doing some basic exercises.

## if/else

As mentioned earlier, `if/else` is not just a statement but can also be used as an operator (in lieu of the ternary `? :` operator.

As an example, a familiar way to have if/else statements would be as follows.

```kotlin
fun greeting(hours: Int): String {
    var greeting: String = null
    if (hours < 12) {
        greeting = "Good Morning"
    } else if (hours < 16) {
        greeting = "Good Afternoon"
    } else {
        greeting = "Good Night"
    }
    return greeting
}
```

But since if/else can be an expression as well, another way to write the function above would be

```kotlin
fun greeting2(hours: Int): String =
    "Good " + if (hours < 12) {
        "Morning"
    } else if (hours < 16) {
        "Afternoon"
    } else {
        "Night"
    }
```

## when

There is another expression that can be used instead of multiple nested if/else expressions or situations where you might use a switch/case block in Java. Using `when` the function above could be written as

```kotlin
fun greeting3(hours: Int): String =
    "Good " + when {
        hours < 12 -> "Morning"
        hours < 16 -> "Afternoon"
        else -> "Night"
    }
```

Note that `when` is an expression and evaluates to a value (in the case above it would be "Morning", "Afternoon" or "Night". You could also use it more conventionally just to write other blocks of code eg.

```kotlin
fun greeting4(hours: Int): Unit {
 when {
        hours < 12 -> {
            println("Morning")
        }
        hours < 16 -> {
            println("Afternoon")
        }
        else -> {
            println("Night")
        }
    }
}
```

As you can note, there is no break statement (unlike switch case), and the right hand side of the conditions can be blocks which consist of multiple statements. If used as a part of a when expression, the value returned by a block is the one returned by the last expression in the block.

When used as an expression, the compiler expects the conditions to be exhaustive and thus many times you might need to add an else block. However the else block is not required when used as a statement.

There are many other things you can do including checking the actual instance or just the value as shown in the rather arbitrary example below

```kotlin
fun anotherWhen(any: Any): Any =
    when(any) {
        (any is Int && any < 10000) -> "Small Number"
        is Int -> "Big Number"
        is String -> "A string"
        else -> "Its just another Any"
    }
```

You might note that in the first condition, the right hand side of the condition ie. (any < 10000) actually compiles, because the compiler is able to figure out that any will be an Int if reaches the comparison with 10000 and allows it to be used as an Int. This smart casting is actually quite nice and helps save on verbose typecast statements (or for that matter explicit typecasts) when used in when expressions as above.

## while and do-while

The familiar constructs of `while` and `do while` are also available. eg.

```kotlin
fun countDown(n: Int) {
    var counter = n
    while(counter >= 0) {
        println(counter)
        counter--
    }
}
```

Note that in kotlin the while block will not allow you to perform an assignment within the while expression unlike java code. eg. `while((n = next()) > 0)` will not compile. In some of these situations you might actually find the `do while` construct useful. eg. the val n declared within the do while block remains in scope while evaluating the while expression.

```kotlin
do {
    val n = next()
    // do something
} while(n > 0)
```

## for loop

The for loop is much nicer and often more helpful than in Java. eg. to print each character in a string you could write

```kotlin
for(c in "String") {
    println(c)
}
```

One of the useful ability of for loop is to iterate over a range which can be written as `startValue..endValue` eg

```kotlin
// will print 100, 101, 102... 110
for (n in 100..110) {
    println(n)
}
```

There are many other aspects of the for loop which are better covered after some other topics, so other sample usages will be taken up later.

## break and continue

`break` and `continue` are also available and continue to have the same meaning as in java. For more advanced uses such as breaking to a or returning to a label, see [Returns and jumps](https://kotlinlang.org/docs/reference/returns.html) from the kotlin reference guide.

## Exercise - Counting Sundays

Here's an exercise for you to try out. (Note: the solution follows immediately after, so you might wish to avoid reading it if you want to attempt to do the exercise yourself first).

This one comes from [Project Euler](http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-4-control-flows-and-return/) Exercise #19

The problem is stated as follows

```kotlin
You are given the following information, but you may prefer to do some research for yourself.


1 Jan 1900 was a Monday.
Thirty days has September,
April, June and November.
All the rest have thirty-one,
Saving February alone,
Which has twenty-eight, rain or shine.
And on leap years, twenty-nine.
A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.

How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
```

One of the possible solutions to this problem is described below

```kotlin
fun sundaysInTwentiethCentury(): Int {
    fun daysInMonth(month: Int, year: Int) = when(month) {
        1,3,5,7,8,10,12 -> 31
        4,6,9,11 -> 30
        2 -> when {
            year % 400 == 0 -> 29
            year % 100 == 0 -> 28
            year % 4 == 0 -> 29
            else -> 28
        }
        else -> throw Exception("Invalid Month ${"$"}{month}")
    }
    fun daysInYear(year: Int): Int {
        var days = 0
        for(month in 1..12) {
            days += daysInMonth(month, year)
        }
        return days
    }
    fun dayOfWeekAfter(currentDow: Int, numberOfDays: Int) = ((currentDow-1) + numberOfDays) % 7 + 1
    var weekDayOnFirstOfMonth = dayOfWeekAfter(2, daysInYear(1900))
    var totalSundays = 0
    for (year in 1901..2000) {
        for(month in 1..12) {
            if (weekDayOnFirstOfMonth == 1) totalSundays++
            weekDayOnFirstOfMonth = dayOfWeekAfter(weekDayOnFirstOfMonth, daysInMonth(month,year))
        }
    }
    return totalSundays
}
```

"""

Article(
  title = "Exercises in Kotlin: Part 4 - Control flows and return",
  url = "http://blog.dhananjaynene.com/2016/04/exercises-in-kotlin-part-4-control-flows-and-return/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dhananjay Nene",
  date = LocalDate.of(2016, 4, 27),
  body = body
)
