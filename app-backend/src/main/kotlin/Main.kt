import infra.lifecycle.GracefulShutdown
import infra.lifecycle.ShutdownManager
import io.heapy.komok.tech.logging.Logger
import org.flywaydb.core.Flyway
import server.KtorServer
import usecases.reload.LiveReloadService
import usecases.stars_job.StarsJobScheduler
import usecases.version.KotlinVersionsJobScheduler
import java.lang.management.RuntimeMXBean
import kotlin.time.Duration.Companion.milliseconds

class Main(
    private val shutdownManager: ShutdownManager,
    private val gracefulShutdown: GracefulShutdown,
    private val flyway: Flyway,
    private val ktorServer: KtorServer,
    private val runtimeMXBean: RuntimeMXBean,
    private val liveReloadService: LiveReloadService,
    private val starsJobScheduler: StarsJobScheduler,
    private val kotlinVersionsJobScheduler: KotlinVersionsJobScheduler,
) {
    suspend fun run() {
        shutdownManager.registerHook()
        gracefulShutdown.executeAndWait {
            flyway.migrate()
            ktorServer.start(wait = false)
            log.info("Server started in {}", runtimeMXBean.uptime.milliseconds)
            liveReloadService.start()
            starsJobScheduler.start()
            kotlinVersionsJobScheduler.start()
        }
    }

    private companion object : Logger()
}
