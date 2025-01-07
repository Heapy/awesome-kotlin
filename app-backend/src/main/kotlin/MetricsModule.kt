import io.heapy.komok.tech.di.delegate.bean
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

open class MetricsModule {
    open val meterRegistry by bean {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
