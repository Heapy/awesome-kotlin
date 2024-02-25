import di.bean
import lifecycle.GracefulShutdown
import lifecycle.JvmShutdownManager
import lifecycle.ShutdownManager

open class LifecycleModule {
    open val shutdownHandler by bean<ShutdownManager> {
        JvmShutdownManager()
    }

    open val gracefulShutdown by bean {
        GracefulShutdown().also {
            shutdownHandler.get.addHandler(it::shutdown)
        }
    }
}
