
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
A simple suspend function, and it’s bytecode.

The new thing in Kotlin 1.1 is [coroutines](http://kotlinlang.org/docs/reference/coroutines.html). As we know from the documentation, it is the `suspend` keyword that was added to the language. The rest is implemented as libraries.

Let’s take a look at the bytecode side of this feature.

# An Empty Suspend function

I have the following code snippet:

```kotlin
suspend fun b() {}
```

Let’s take a look to the bytecode from this method. For the experiment, I use Kotlin 1.1.1 with IntelliJ IDEA 2017.1. Results may depend on version. I use `javap -c` to generate those dumps

```
public static final java.lang.Object b(kotlin.coroutines.experimental.Continuation<? super kotlin.Unit>);
    Code:
       0: aload_0
       1: ldc           #13                 // String ${"$"}continuation
       3: invokestatic  #19                 // Method kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
       6: getstatic     #25                 // Field kotlin/Unit.INSTANCE:Lkotlin/Unit;
       9: areturn
```

The interface `Continuation` is declared in the Kotlin standard library, see [documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/). It contains `context` and methods to complete continuation: `resume` and `resumeWithException`.

# A Trivial Suspend function

```kotlin
suspend fun b2() {
  a()
  c()
}
```

Here `a()` and `c()` are calls to ordinary Java methods, which were declared in Kotlin without the `suspend` keyword.

```
public static final java.lang.Object b2(kotlin.coroutines.experimental.Continuation<? super kotlin.Unit>);
    Code:
       0: aload_0
       1: ldc           #13                 // String ${"$"}continuation
       3: invokestatic  #19                 // Method kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
       6: invokestatic  #29                 // Method a:()V
       9: invokestatic  #31                 // Method c:()V
      12: getstatic     #25                 // Field kotlin/Unit.INSTANCE:Lkotlin/Unit;
      15: areturn
```

As we see from this code, there is nothing special done to the method. The only return value and additional parameter were added.

# A suspend function with a suspend call

```kotlin
suspend fun b3() {
  a()
  b3()
  c()
}
```

In this example, we call `b3()` suspend function from itself. Here `a()` and `c()` are calls to ordinary Java methods, which were declared in Kotlin without `suspend` keyword. The generated code now looks way different.

```
public static final java.lang.Object b3(kotlin.coroutines.experimental.Continuation<? super kotlin.Unit>);
    descriptor: (Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;
    Code:
       0: aload_0
       1: ldc           #13                 // String ${"$"}continuation
       3: invokestatic  #19                 // Method kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
       6: new           #34                 // class streams4/ZKt${"$"}b3$1
       9: dup
      10: aload_0
      11: invokespecial #38                 // Method streams4/ZKt${"$"}b3$1."<init>":(Lkotlin/coroutines/experimental/Continuation;)V
      14: getstatic     #25                 // Field kotlin/Unit.INSTANCE:Lkotlin/Unit;
      17: aconst_null
      18: invokevirtual #42                 // Method streams4/ZKt${"$"}b3$1.doResume:(Ljava/lang/Object;Ljava/lang/Throwable;)Ljava/lang/Object;
      21: areturn
```

Instead of having the method in-place, it now generates an inner class for the state-machine to implement the `suspend`.

The class `streams4/ZKt${"$"}b3$1` is generated as follows

```
final class streams4.ZKt${"$"}b3$1 extends kotlin.coroutines.experimental.jvm.internal.CoroutineImpl {
  public final java.lang.Object doResume(java.lang.Object, java.lang.Throwable);
    descriptor: (Ljava/lang/Object;Ljava/lang/Throwable;)Ljava/lang/Object;
    Code:
       0: invokestatic  #13                 // Method kotlin/coroutines/experimental/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED:()Ljava/lang/Object;
       3: astore_3
       4: aload_0
       5: getfield      #17                 // Field kotlin/coroutines/experimental/jvm/internal/CoroutineImpl.label:I
       8: tableswitch   { // 0 to 1
                     0: 32
                     1: 58
               default: 74
          }
      32: aload_2
      33: dup
      34: ifnull        38
      37: athrow
      38: pop
      39: invokestatic  #23                 // Method streams4/ZKt.a:()V
      42: aload_0
      43: aload_0
      44: iconst_1
      45: putfield      #17                 // Field kotlin/coroutines/experimental/jvm/internal/CoroutineImpl.label:I
      48: invokestatic  #27                 // Method streams4/ZKt.b3:(Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;
      51: dup
      52: aload_3
      53: if_acmpne     66
      56: aload_3
      57: areturn
      58: aload_2
      59: dup
      60: ifnull        64
      63: athrow
      64: pop
      65: aload_1
      66: pop
      67: invokestatic  #30                 // Method streams4/ZKt.c:()V
      70: getstatic     #36                 // Field kotlin/Unit.INSTANCE:Lkotlin/Unit;
      73: areturn
      74: new           #38                 // class java/lang/IllegalStateException
      77: dup
      78: ldc           #40                 // String call to 'resume' before 'invoke' with coroutine
      80: invokespecial #44                 // Method java/lang/IllegalStateException."<init>":(Ljava/lang/String;)V
      83: athrow

  streams4.ZKt${"$"}b3$1(kotlin.coroutines.experimental.Continuation);
    descriptor: (Lkotlin/coroutines/experimental/Continuation;)V
    Code:
       0: aload_0
       1: iconst_0
       2: aload_1
       3: invokespecial #58                 // Method kotlin/coroutines/experimental/jvm/internal/CoroutineImpl."<init>":(ILkotlin/coroutines/experimental/Continuation;)V
       6: return
}
```

The implementation of `b3()` function is moved to a state machine anonymous object. The main method of the inner object does a switch over states of the state machine. The `b3()` function is split by every `suspend` function call. On the example below, we have only 2 states. This is up to helper functions to assert the machine is always in a correct state.

On every `suspend` function call, Kotlin creates an object to encapsulate the state of the state machine, that is created to implement the continuations on top of JVM.

# Conclusion

Coroutines in Kotlin are awesome, easy and powerful constructs that give us the power to fight the complexity (by the cost of an extra abstraction level). I’m looking forward to using coroutines to simplify asynchronous code in my apps.

For more information and details see [Kotlin coroutines](http://kotlinlang.org/docs/reference/coroutines.html) documentation.
"""

Article(
  title = "Bytecode behind coroutines in Kotlin",
  url = "http://jonnyzzz.com/blog/2017/04/26/corotines-or-state-machine/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Eugene Petrenko",
  date = LocalDate.of(2017, 4, 26),
  body = body
)
