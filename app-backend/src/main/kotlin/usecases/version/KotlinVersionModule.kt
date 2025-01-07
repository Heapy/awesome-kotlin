package usecases.version

import infra.HttpClientModule
import infra.db.JooqModule
import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module
import infra.serialization.XmlModule
import io.heapy.komok.tech.time.TimeSourceModule
import usecases.notifications.NotificationModule

@Module
open class KotlinVersionModule(
    private val xmlModule: XmlModule,
    private val httpClientModule: HttpClientModule,
    private val lifecycleModule: LifecycleModule,
    private val timeSourceModule: TimeSourceModule,
    private val jooqModule: JooqModule,
    private val notificationModule: NotificationModule,
) {
    open val kotlinVersionFetcher: KotlinVersionFetcher by lazy {
        MavenCentralKotlinVersionFetcher(
            xml = xmlModule.xml,
            httpClient = httpClientModule.httpClient,
        )
    }

    open val kotlinVersionDao by lazy {
        KotlinVersionDao(
            timeSource = timeSourceModule.timeSource,
        )
    }

    open val kotlinVersionsJobScheduler by lazy {
        KotlinVersionsJobScheduler(
            kotlinVersionsJob = kotlinVersionsJob,
            applicationScope = lifecycleModule.applicationScope,
        )
    }

    open val kotlinVersionsJob by lazy {
        KotlinVersionsJob(
            kotlinVersionDao = kotlinVersionDao,
            kotlinVersionFetcher = kotlinVersionFetcher,
            transactionProvider = jooqModule.transactionProvider,
            notificationService = notificationModule.notificationService,
        )
    }

    open val route by lazy {
        KotlinVersionRoute(
            kotlinVersionDao = kotlinVersionDao,
        )
    }
}
