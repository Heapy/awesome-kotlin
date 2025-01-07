package infra.lifecycle

import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.update
import kotlin.concurrent.thread

typealias ShutdownHandler = suspend () -> Unit

interface ShutdownManager {
    fun addHandler(handler: ShutdownHandler)
    fun registerHook()
}

class JvmShutdownManager(
    private val ioDispatcher: CoroutineDispatcher,
) : ShutdownManager {
    private val handlers = AtomicReference(emptyList<ShutdownHandler>())

    override fun addHandler(
        handler: ShutdownHandler,
    ) {
        handlers.update { current -> current + handler }
    }

    override fun registerHook() {
        Runtime.getRuntime().addShutdownHook(
            thread(
                start = false,
                name = "ShutdownManager",
            ) {
                runBlocking(ioDispatcher) {
                    handlers
                        .load()
                        .reversed()
                        .forEach { handler ->
                            try {
                                handler()
                            } catch (e: Exception) {
                                log.error("Error while calling shutdown handler", e)
                            }
                        }
                }
            })
    }

    private companion object : Logger()
}
