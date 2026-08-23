import io.heapy.komok.tech.di.lib.Module
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

@Module
class MetricsModule {
    val meterRegistry by lazy {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
