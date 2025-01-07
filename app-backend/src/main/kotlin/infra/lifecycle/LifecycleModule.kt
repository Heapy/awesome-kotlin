package infra.lifecycle

import infra.coroutines.DispatchersModule
import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
open class LifecycleModule(
    private val dispatchersModule: DispatchersModule,
) {
    open val shutdownManager by lazy<ShutdownManager> {
        JvmShutdownManager(
            ioDispatcher = dispatchersModule.ioDispatcher,
        )
    }

    open val gracefulShutdown by lazy {
        GracefulShutdown().also { gracefulShutdown ->
            shutdownManager.addHandler {
                log.info("Shutting down gracefully")
                gracefulShutdown.shutdown()
            }
        }
    }

    open val applicationScope by lazy {
        val job = SupervisorJob()
        CoroutineScope(job + dispatchersModule.defaultDispatcher).also {
            shutdownManager.addHandler {
                log.info("Shutting down application scope")
                job.cancel()
            }
        }
    }

    private companion object : Logger()
}
