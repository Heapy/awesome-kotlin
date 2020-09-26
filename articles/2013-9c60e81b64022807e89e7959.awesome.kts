
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
In [Part 1](http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-1.html) and [Part 2](http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-2.html) of this series, I went through the default Kotlin namespace and kotlin.io. In Part 3 I'll be going over kotlin.concurrent.

The public functions in kotlin.concurrent are all utilities for creating timers, threads or timer tasks e.g.

```kotlin
// create a fixed rate timer that prints hello world every 100ms
// after a 100ms delay
val fixedRateTimer = fixedRateTimer(name = "hello-timer",
        initialDelay = 100, period = 100) {
    println("hello world!")
}
try {
    Thread.sleep(1000)
} finally {
    fixedRateTimer.cancel();
}
```

Note: by default the timers are not daemon timers. Without the cancel the timer would run indefinitely. This can often cause issues if you have a timer deployed in a container (like Tomcat). Naming them is always helpful in finding rogue non-daemon timers.

The thread() method creates a new thread

```kotlin
// run in a different thread
thread() {
    println("async")
}
println("sync")
```

although I would usually use an ExecutorService rather than managing threads directly. Fortunately the Kotlin standard library provides a number of extension methods on Executor and ExecutorService too.

```kotlin
// create a single thread executor
val singleThreadPool = Executors.newSingleThreadExecutor();
try {
    // Executor and ExecutorService are extended with an
    // invoke method
    val future = singleThreadPool<String>{
        "async"
    }
    println("sync")
    println(future.get(10, TimeUnit.SECONDS))
} finally {
    singleThreadPool.shutdown();
```

java.util.Timer also gets a couple of extension methods: schedule and scheduleAtFixedRate

```kotlin
// create a daemon thread
val timer = Timer("schedule", true);

// schedule a single event
timer.schedule(1000) {
    println("hello world!")
}
// schedule at a fixed rate
timer.scheduleAtFixedRate(1000, 1000) {
    println("hello world!")
}
```

Once again, I'd normally use a ScheduledExecutorService in preference. The standard library doesn't provide extension methods on ScheduledExecutorService but it is easy enough to add our own:

```kotlin
/**
 * Extension method on implementations of ScheduledExecutorService to schedule
 * an action
 */
fun <V, T: ScheduledExecutorService> T.schedule(
        delay: Long,
        unit: TimeUnit = TimeUnit.MILLISECONDS,
        action: () -> V): ScheduledFuture<V> {
    return this.schedule(
            callable { action() },
            delay, unit);
}
```

Which can be used as follows:

```kotlin
val scheduledExecutor = Executors.newScheduledThreadPool(1)
try {
    scheduledExecutor.schedule(1000) {
        println("hello world")
    }
} finally {
    scheduledExecutor.shutdown()
}
```

The kotlin.concurrent package also provides additional methods for using locks e.g.:

```kotlin
val lock = ReentrantLock();
val result = lock.withLock {
    // access a locked resource
}
val readWriteLock = ReentrantReadWriteLock()
readWriteLock.read {
    // execute an action with a read lock
}
readWriteLock.write {
    // execute an action with a write lock
}
```

If instead of locking, our code needs to try the lock and do one thing if the resource is available, and another if it is locked, we could extend Lock as follows:

```kotlin
/**
 * Only run if you can acquire a lock
 */
public inline fun <T> Lock.tryLock(action: ()->T, alternative: ()->T): T {
    if(tryLock()) {
        try {
            return action()
        }
        finally {
            unlock();
        }
    } else {
        return alternative();
    }
}
```

Which could be used like this:

```kotlin
val tryLock = ReentrantLock();
val tryLockResult = tryLock.tryLock({
    // run if we can get a lock
}, {
    // run if we couldn't get a lock
});
```

"""

Article(
  title = "Exploring the Kotlin Standard Library - Part 3",
  url = "http://jamie.mccrindle.org/2013/02/exploring-kotlin-standard-library-part-3.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jamie McCrindle",
  date = LocalDate.of(2013, 2, 7),
  body = body
)
