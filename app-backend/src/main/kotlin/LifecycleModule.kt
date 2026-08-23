import io.heapy.komok.tech.di.lib.Module
import lifecycle.GracefulShutdown
import lifecycle.JvmShutdownManager
import lifecycle.ShutdownManager

@Module
class LifecycleModule {
    val shutdownHandler: ShutdownManager by lazy {
        JvmShutdownManager()
    }

    val gracefulShutdown by lazy {
        GracefulShutdown().also {
            shutdownHandler.addHandler(it::shutdown)
        }
    }
}
