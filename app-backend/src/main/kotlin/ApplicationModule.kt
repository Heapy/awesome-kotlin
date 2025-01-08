import infra.db.FlywayModule
import infra.lifecycle.AutoClosableModule
import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.runBlocking
import server.KtorServerModule
import usecases.reload.LiveReloadModule
import usecases.stars_job.StarsJobModule
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import kotlin.time.Duration.Companion.milliseconds

@Module
open class ApplicationModule(
    private val lifecycleModule: LifecycleModule,
    private val starsJobModule: StarsJobModule,
    private val flywayModule: FlywayModule,
    private val ktorServerModule: KtorServerModule,
    private val autoClosableModule: AutoClosableModule,
    private val liveReloadModule: LiveReloadModule,
) : AutoCloseable {
    fun run() = runBlocking {
        val gracefulShutdown = lifecycleModule.gracefulShutdown
        starsJobModule.starsJobScheduler.start()
        lifecycleModule.shutdownHandler.registerHook()
        flywayModule.flyway.migrate()
        ktorServerModule.ktorServer.start(wait = false)
        liveReloadModule.liveReloadService.start()
        log.info("Server started in {}", runtimeMXBean.uptime.milliseconds)
        gracefulShutdown.waitForShutdown()
    }

    override fun close() {
        autoClosableModule.close()
    }

    open val runtimeMXBean: RuntimeMXBean by lazy {
        ManagementFactory.getRuntimeMXBean()
    }

    private companion object : Logger()
}
