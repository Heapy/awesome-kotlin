import di.bean
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

open class MetricsModule {
    open val meterRegistry by bean {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
