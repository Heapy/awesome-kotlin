import infra.lifecycle.AutoClosableModule
import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.logger
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import kotlin.time.Duration.Companion.milliseconds

@Module
class ApplicationModule(
    private val lifecycleModule: LifecycleModule,
    //private val flywayModule: FlywayModule,
    private val serverModule: ServerModule,
    private val autoClosableModule: AutoClosableModule,
) : AutoCloseable {
    suspend fun run() {
        val gracefulShutdown = lifecycleModule.gracefulShutdown
        lifecycleModule.shutdownHandler.registerHook()
        // TODO: Enable migrations after schema cleanup
        // flywayModule.flyway.migrate()
        serverModule.ktorServer.start(wait = false)
        log.info("Server started in {}", runtimeMXBean.uptime.milliseconds)
        gracefulShutdown.waitForShutdown()
    }

    val runtimeMXBean: RuntimeMXBean by lazy {
        ManagementFactory.getRuntimeMXBean()
    }

    val log by lazy {
        logger<ApplicationModule>()
    }

    override fun close() {
        autoClosableModule.close()
    }
}
