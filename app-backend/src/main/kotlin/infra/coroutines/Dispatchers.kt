package infra.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

private val VirtualThreadDispatcher = run {
    val factory = Thread.ofVirtual().name("Loom-", 0).factory()
    Executors.newThreadPerTaskExecutor(factory).asCoroutineDispatcher()
}

@Suppress("UnusedReceiverParameter")
val Dispatchers.Loom: CoroutineDispatcher
    get() = VirtualThreadDispatcher
