package infra.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

private val VirtualThreadDispatcher = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

@Suppress("UnusedReceiverParameter")
val Dispatchers.Loom: CoroutineDispatcher
    get() = VirtualThreadDispatcher
