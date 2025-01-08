package infra.lifecycle

import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

interface ShutdownManager {
    fun addHandler(handler: suspend () -> Unit)
    fun registerHook()
}

class JvmShutdownManager : ShutdownManager {
    private val handlers = mutableListOf<suspend () -> Unit>()

    override fun addHandler(
        handler: suspend () -> Unit,
    ) {
        handlers.add(handler)
    }

    override fun registerHook() {
        Runtime.getRuntime().addShutdownHook(thread(
            start = false,
            name = "ShutdownManager",
        ) {
            runBlocking {
                handlers.forEach { handler ->
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
