package link.kotlin.server.lifecycle

import link.kotlin.server.utils.logger
import kotlin.concurrent.thread

interface ShutdownManager {
    fun registerHook()
}

class JvmShutdownManager(
    private val handlers: List<() -> Unit>,
) : ShutdownManager {
    override fun registerHook() {
        Runtime.getRuntime().addShutdownHook(thread(
            start = false,
            name = "ShutdownManager",
        ) {
            handlers.forEach { handler ->
                try {
                    handler()
                } catch (e: Exception) {
                    log.error("Error while calling shutdown handler", e)
                }
            }
        })
    }

    private companion object {
        private val log = logger<JvmShutdownManager>()
    }
}
