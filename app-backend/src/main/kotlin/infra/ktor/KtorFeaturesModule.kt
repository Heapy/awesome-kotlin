package infra.ktor

import infra.ktor.features.*
import io.heapy.komok.tech.di.lib.Module
import infra.metrics.MetricsModule
import usecases.reload.NotificationChannelModule

@Module
open class KtorFeaturesModule(
    private val metricsModule: MetricsModule,
    private val notificationChannelModule: NotificationChannelModule,
) {
    open val cachingFeature by lazy {
        CachingFeature()
    }

    open val contentNegotiationFeature by lazy {
        ContentNegotiationFeature()
    }

    open val resourcesFeature by lazy {
        ResourcesFeature()
    }

    open val statusPageFeature by lazy {
        StatusPageFeature()
    }

    open val webSocketsFeature by lazy {
        WebSocketsFeature(
            notificationChannel = notificationChannelModule.notificationChannel,
        )
    }

    open val callLoggingFeature by lazy {
        CallLoggingFeature()
    }

    open val metricsFeature by lazy {
        MetricsFeature(
            meterRegistry = metricsModule.meterRegistry,
            binders = metricsModule.binders,
        )
    }

    open val features by lazy {
        listOf(
            cachingFeature,
            contentNegotiationFeature,
            resourcesFeature,
            statusPageFeature,
            callLoggingFeature,
            metricsFeature,
            webSocketsFeature,
        )
    }
}
