package infra.metrics

import infra.db.JdbcModule
import io.heapy.komok.tech.di.lib.Module
import io.micrometer.core.instrument.binder.db.PostgreSQLDatabaseMetrics
import io.micrometer.core.instrument.binder.jvm.*
import io.micrometer.core.instrument.binder.logging.LogbackMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

@Module
open class MetricsModule(
    private val jdbcModule: JdbcModule,
) {
    open val meterRegistry by lazy {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }

    open val binders by lazy {
        listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            JvmHeapPressureMetrics(),
            ProcessorMetrics(),
            JvmThreadMetrics(),
            FileDescriptorMetrics(),
            UptimeMetrics(),
            LogbackMetrics(),
            PostgreSQLDatabaseMetrics(
                jdbcModule.dataSource,
                "awesome_kotlin"
            ),
        )
    }

    open val route by lazy {
        MetricsRoute(
            meterRegistry = meterRegistry,
        )
    }
}
