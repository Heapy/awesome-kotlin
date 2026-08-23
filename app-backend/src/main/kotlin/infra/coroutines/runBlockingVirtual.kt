package infra.coroutines

import java.util.concurrent.CompletableFuture
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * Runs a suspending block of code within the context of a virtual thread and blocks the current virtual thread
 * until the execution of the block is complete.
 *
 * This function must be called from a virtual thread, which is validated via a runtime check.
 * It allows executing suspending code within a synchronous paradigm tailored for use within virtual thread contexts.
 *
 * @param context The coroutine context in which the suspendable block will execute.
 * @param block The suspending lambda function to be executed within the virtual thread context.
 * @return The result of the suspending block once its execution is complete.
 * @throws IllegalStateException If the function is called from a non-virtual thread.
 */
fun <T> runBlockingVirtual(
    context: CoroutineContext,
    block: suspend () -> T,
): T {
    require(Thread.currentThread().isVirtual) {
        "Must be called from a virtual thread"
    }

    val future = CompletableFuture<T>()

    block.startCoroutine(
        Continuation(context) { res ->
            val _ = res.fold(
                onSuccess = future::complete,
                onFailure = future::completeExceptionally
            )
        }
    )

    return future.join()
}
