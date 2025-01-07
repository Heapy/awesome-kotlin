import infra.db.FlywayModule
import infra.lifecycle.AutoClosableModule
import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module
import server.KtorServerModule
import usecases.reload.LiveReloadModule
import usecases.stars_job.StarsJobModule
import usecases.version.KotlinVersionModule
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

@Module
open class ApplicationModule(
    private val lifecycleModule: LifecycleModule,
    private val starsJobModule: StarsJobModule,
    private val kotlinVersionModule: KotlinVersionModule,
    private val flywayModule: FlywayModule,
    private val ktorServerModule: KtorServerModule,
    private val liveReloadModule: LiveReloadModule,
    private val autoClosableModule: AutoClosableModule,
) : AutoCloseable {
    open val main by lazy {
        Main(
            shutdownManager = lifecycleModule.shutdownManager,
            gracefulShutdown = lifecycleModule.gracefulShutdown,
            ktorServer = ktorServerModule.ktorServer,
            runtimeMXBean = runtimeMXBean,
            flyway = flywayModule.flyway,
            liveReloadService = liveReloadModule.liveReloadService,
            starsJobScheduler = starsJobModule.starsJobScheduler,
            kotlinVersionsJobScheduler = kotlinVersionModule.kotlinVersionsJobScheduler,
        )
    }

    override fun close() {
        autoClosableModule.close()
    }

    open val runtimeMXBean: RuntimeMXBean by lazy {
        ManagementFactory.getRuntimeMXBean()
    }
}
