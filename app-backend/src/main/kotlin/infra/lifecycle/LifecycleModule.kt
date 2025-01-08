package infra.lifecycle

import io.heapy.komok.tech.di.lib.Module
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
open class LifecycleModule {
    open val shutdownHandler by lazy<ShutdownManager> {
        JvmShutdownManager()
    }

    open val gracefulShutdown by lazy {
        GracefulShutdown().also {
            shutdownHandler.addHandler(it::shutdown)
        }
    }

    open val applicationScope by lazy {
        val job = SupervisorJob()
        CoroutineScope(job).also {
            shutdownHandler.addHandler { job.cancel() }
        }
    }
}
