package infra.lifecycle

import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.Logger

@Module
open class AutoClosableModule : AutoCloseable {
    private val closable = mutableListOf<() -> Unit>()

    fun <T> addClosable(
        t: T,
        close: (T) -> Unit,
    ): T {
        log.info("Adding closable $t")
        closable.add {
            log.info("Closing $t")
            close(t)
            log.info("Closed $t")
        }
        return t
    }

    override fun close() {
        closable
            .reversed()
            .forEach { closeFunction ->
                try {
                    closeFunction()
                } catch (e: Exception) {
                    log.error("Error while closing resource", e)
                }
            }
    }

    private companion object : Logger()
}
