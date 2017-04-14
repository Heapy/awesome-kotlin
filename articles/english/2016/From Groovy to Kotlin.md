---
title: 'From Groovy to Kotlin'
url: https://dkandalov.github.io/groovy/kotlin/2016/06/06/From-Groovy-to-Kotlin.html
categories:
    - Kotlin
author: Dmitry Kandalov
date: Jun 06, 2016 02:57
---
This is a write-up of my experience converting source code of [Activity Tracker](https://github.com/dkandalov/activity-tracker) plugin for IntelliJ IDEs from [Groovy](http://www.groovy-lang.org/) to [Kotlin](https://kotlinlang.org/).

It is written for anyone familiar with Groovy or Kotlin and might be especially relevant if you are considering move from Groovy to Kotlin. Hopefully, it can be interesting for non-Groovy and non-Kotlin people as well.

Please note that this is not intended to be a thorough comparison or overview of the languages. The only differences mentioned are those which I came across while transforming Groovy code to Kotlin.

#### About migration

[Activity Tracker](https://github.com/dkandalov/activity-tracker) is a proof-of-concept plugin for IntelliJ IDEs to track and record user activity. It keeps all the data locally so you can see and control what is being logged.

IntelliJ plugins are usually written in Java with a bit of xml configs and IntelliJ platform Java API. Activity Tracker is not like that at all. In the first place, it mostly ignores standard xml configuration and uses [LivePlugin](https://github.com/dkandalov/live-plugin) Groovy API instead. From plugin point of view this means that in addition to standard Java APIs it has to interface with LivePlugin Groovy API. Secondly, Activity Tracker was itself written in Groovy.

Writing plugins in Groovy is not a common practice. At the time the main motivation for me was to use programming language more exciting than Java 6. These days IntelliJ uses Java 8 and Kotlin is “officially approved” language for writing plugins. So migrating from Groovy to Kotlin was not only about having fun but also about moving to standard technology.

#### No ‘new’ keyword

Unlike Groovy (and probably most JVM languages) there is no `new` keyword in Kotlin. To create an instance of a class you can just use class name with constructor parameters.

Groovy:

```groovy
new ActivityTracker.Config(...)
```

Kotlin:

```kotlin
ActivityTracker.Config(...)
```

#### No implicit narrowing/widening for numbers

Unlike Groovy (and probably most JVM languages) there is no implicit narrowing/widening conversion for numbers in Kotlin. That is if you have variable of type `Long` you cannot assign `Int` value to it and vice versa.

Even though this might seem strange, it makes perfect sense because `Int` and `Long` classes are not subtypes of each other. The same applies to `Double` and `Float`. Considering how subtle and difficult it can be to find implicit conversion bugs this is probably a good design.

(In case you were wondering about silent number underflow/overflow, it is still there. Works the same way as in Java.)

Groovy:

```groovy
def longValue = 123L
def intValue = 123
longValue = intValue // works ok
```

Kotlin:

```kotlin
var longValue = 123L
var intValue = 123
longValue = intValue // compilation error
longValue = intValue.toLong() // works ok
```

#### Closure type parameters

In Groovy types and type parameters are optional. You can skip types all together or specify them in when you feel like doing it. I found it useful to always add types to libraries and other APIs which might be heavily used from other code. It works fine except for `Closure<V>` type which has type parameter only for its return value. To be fair, there is `ClosureParams` annotation to specify types for closure inputs, but it’s too painful to use.

In Kotlin, closures (aka lambdas) have type parameters for inputs and output as you would expect.

Groovy:

```groovy
private updateState(Closure<State> closure) {...}
// or
private updateState(@ClosureParams(State.class) Closure<State> closure) {...}
```

Kotlin:

```kotlin
private fun updateState(closure: (State) -> State) {...}
```
    
#### “With” vs “run” and “apply”

One of the interesting features in Groovy is `.with` function defined on `Object` class. It takes a closure and executes it with `this` set to target object. The result of `.with` function is the value of the last expression in closure. This can be useful for calling a bunch of methods on object which doesn’t have fluent API.

Confusingly, Kotlin has `with` function which does exactly the same thing except that it cannot be called on object itself. So to replace Groovy `.with` in Kotlin there is `.run` function. In addition, there is the `.apply` function in Kotlin which is like `.run` but returns target object. This is useful for building object trees and avoiding `it` as the last expression in each closure.

Groovy:

```groovy
def actionGroup = new DefaultActionGroup().with {
    add(toggleTracking)
    add(new DefaultActionGroup("Current Log", true).with {
        add(showStatistics)
        add(openLogInIde)
        add(openLogFolder)
        addSeparator()
        add(rollCurrentLog)
        add(clearCurrentLog)
        it // <-- meh
    })
    //...
    it // <-- meh
}
```

Kotlin:

```kotlin
val actionGroup = DefaultActionGroup().apply {
    add(toggleTracking)
    add(DefaultActionGroup("Current Log", true).apply {
        add(showStatistics)
        add(openLogInIde)
        add(openLogFolder)
        addSeparator()
        add(rollCurrentLog)
        add(clearCurrentLog)
    })
    // ...
}
```

#### “Modifying” immutable objects

Both Groovy and Kotlin can define value-objects classes, i.e. a class with immutable fields and implicitly defined equality and hash code methods. In Groovy it’s a class with `@Immutable` annotation, in Kotlin `data class` definition. One of the things you might want to do with value-object is copy it into new object changing one or more fields.

Even though underlying implementation is different, from user point of view Groovy and Kotlin code looks similar.

Groovy:

```groovy
@Immutable(copyWith = true)
static final class State {
    boolean isTracking
    boolean trackIdeActions
}
new State(false, false).copyWith(trackIdeActions: true)
```
    
Kotlin:

```kotlin
data class State(
        val isTracking: Boolean,
        val trackIdeActions: Boolean)
State(false, false).copy(trackIdeActions = true)
```

#### Groovy getters and setters

When referencing getters/setters from Groovy code you can pretend you’re using a public field. So instead of Java-style getter `o.getFoo()`, you can use `o.foo`. And instead of setter `o.setFoo("bar")`, you can do `o.foo = "bar"`.

Kotlin also has groovy getters/setters, although for instance methods only.

Java:

```java
ActionManager actionManager = ActionManager.getInstance();
println(actionManager.getComponentName());
```


Groovy:

```groovy
def actionManager = ActionManager.instance
println(actionManager.componentName)
```
    
Kotlin:

```kotlin
val actionManager = ActionManager.getInstance()
println(actionManager.componentName)
```

#### Method names with spaces

Both Groovy and Kotlin allow method names with spaces. This might sound like a strange feature but it’s great for naming unit-tests so that you don’t have to choose between camel case, underscores or mixing both.

Another less practical but much more exciting question is whether any string can be a method name. For Groovy the answer is “yes”. Kotlin seems to be more restrictive.

Groovy:

```groovy
@Test def "convert event object into csv line"() {...}
@Test def "\n"() {...} // good names are hard
@Test def ""() {...}   // the shortest method name ever
```

Kotlin:

```kotlin
@Test fun `convert event object into csv line`() {...}
@Test fun `\n`() {...} // doesn't compile
@Test fun ``() {...}   // doesn't compile
```

#### Almost optional “return”

In Groovy the last expression in function/closure is its return value. You can use `return` keyword to return from function earlier, otherwise it’s entirely optional.

In Kotlin this is more complex. Functions must have `return` keyword while lambdas cannot use `return`. The result of the last expression in lambda is the value that lambda will return. And `return` in lambda means returning from enclosing method.

There must be good reasons behind this design in Kotlin but why last expression in function needs `return` keyword is a mystery for me.

In practice, I had no problems with it except when transforming Kotlin lambdas into methods and the other way round because the code has to be modified to add/remove `return`s.

#### Getting Class object

Kotlin has its own reflection classes, i.e. in addition to `java.lang.Class` there is `kotlin.reflect.KClass`. This makes sense because Kotlin has language features which do not exist in Java. (For example, you might want to check using reflection if function argument is optional.)

In Groovy, as far as I know, it’s not possible to check using reflection whether function argument is optional or not. Probably, analyzing Groovy AST is the way to do it.

Java:

```java
println(ActivityTracker.class);
``` 

Groovy:

```groovy
println(ActivityTracker)
```

Kotlin:

```kotlin
println(ActivityTracker::class.java)
```

#### Appending writer

Groovy has quite a few “helper” methods which are automatically “added” to Java core classes. For example, `withWriterAppend()` method in `ResourceGroovyMethods` class which simplifies appending to a text file using `Writer`.

In Kotlin there are also quite a few “helper” methods. In particular for IO operations, in `kotlin.io.FileReadWrite` there is `writer()` function. It does almost the right thing except that there is no option to make writer appendable so reproducing Groovy behaviour is somewhat verbose.

Java:

```java
// Too many lines of code
```

Groovy:

```groovy
new File(statsFilePath).withWriterAppend("UTF-8") { writer ->
    // use writer
}
```

Kotlin:

```kotlin
FileOutputStream(File(statsFilePath), true).buffered().writer(utf8).use { writer ->
    // use writer
}
```
    
#### Enhanced Collections and Maps

In Groovy there are few functions in `DefaultGroovyMethods` class which are automatically added to all collection classes. For example, `collectEntries()` function takes a closure and, assuming the closure returns two-elements arrays, puts them into a map with first element as a entry key and second element as its value. Or `sort()` function which take a closure and returns sorted collection or even a sorted map.

Kotlin has many similar functions available on collections and maps. There are few subtle differences though. Similar to Groovy `collectEntries()` Kotlin has `associateBy()` function but it’s only available on collections, not on maps. This makes it harder to convert one map into another. Another example is `sortBy()` function which in Kotlin exists only on collections and not maps.

(Note that except for few difference the code below is almost identical.)

Groovy:

```groovy
def eventsByFile = events
    .findAll{ it.eventType == "IdeState" && it.focusedComponent == "Editor" && it.file != "" }
    .groupBy{ it.file }
    .collectEntries{ [fileName(it.key), it.value.size()] }

// OMG, map sorted by its own value
eventsByFile.sort{ it.value }
```

Kotlin:

```kotlin
val eventsByFile = events
    .filter{ it.eventType == "IdeState" && it.focusedComponent == "Editor" && it.file != "" }
    .groupBy{ it.file }.toList()
    .associateBy({ it.first }, { it.second.size })

eventsByFile
    .map{ Pair(fileName(it.key), it.value.size)}
    .sortedBy{ it.second }
```

#### Same class in different class loaders

On JVM class loaders work somewhat like “namespaces”. For example, if you load exactly the same bytecode for a class in two different class loaders, then instances of the class won’t be assignable between the class loaders.

In Groovy this is still true but since Groovy is an optionally typed language, you can skip types and use object from another class loader calling methods dynamically. This is not a feature you would use every day but it can be useful.

Since Kotlin is statically typed language there is no workaround (except for some verbose reflection magic). To be precise, Kotlin has [dynamic types](https://kotlinlang.org/docs/reference/dynamic-type.html) but they are only supported for JavaScript, and not available on JVM.

Groovy:

```groovy
private updateState(Closure<State> closure) {
    // note that parameter class is commented out because on plugin reload it will
    // be a different type (since it's loaded in new classloader)
    stateVar.set { /*State*/ oldValue ->
        def newValue = closure.call(oldValue)
        onUpdate(oldValue, newValue)
        newValue
    }
}
```

Kotlin:

```kotlin
// Can't do this :(
// The workaround is to convert object to/from instance of a class
// from parent class loader, e.g. java.lang.String.
```

#### Extending Groovy interfaces/classes in Kotlin

If you plan to use Groovy API from Kotlin, be aware that it doesn’t work very well at the moment. Basically, Kotlin compiler doesn’t see method implementations of `groovy.lang.GroovyObject` generated by Groovy.

The only workaround I found is to manually implement these methods in Kotlin. If you know the answer, I’ll be grateful if you could reply to [this question on Kotlin forum](https://discuss.kotlinlang.org/t/extending-groovy-class-from-kotlin/1675).

Groovy:

```groovy
class MyGroovyClass {
    def foo() {}
}
```

Kotlin:

```kotlin
// compilation fails with:
// Object must be declared abstract or implement abstract base
// class member public abstract fun setProperty(p0: String!, p1: Any!): Unit
object : MyGroovyClass() {
    override fun foo() {}
}
```

Java:

```java
// yes, this works fine in Java
new MyGroovyClass() {
    @Override public Object foo() {
        return super.foo();
    }
};
```

#### Summary

Kotlin was created few years after Groovy and borrowed some features from it so when switching from Groovy, Kotlin feels like “a language I almost know”.

Being statically typed, Kotlin might have a bit more “resistance” than Groovy. On the other hand, it seems to be more suitable for writing “big legacy enterprise projects”.

If you expected opinion about which language is better, sorry there won’t be one. Both Groovy and Kotlin are good.

To conclude, here is the final code snippet showing strategically designed Kotlin core libraries:

```kotlin
public operator fun times(other: Long): Long
```
