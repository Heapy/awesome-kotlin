import io.heapy.komok.tech.di.delegate.bean
import lifecycle.GracefulShutdown
import lifecycle.JvmShutdownManager
import lifecycle.ShutdownManager

open class LifecycleModule {
    open val shutdownHandler by bean<ShutdownManager> {
        JvmShutdownManager()
    }

    open val gracefulShutdown by bean {
        GracefulShutdown().also {
            shutdownHandler.value.addHandler(it::shutdown)
        }
    }
}
