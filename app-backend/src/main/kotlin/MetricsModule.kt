import di.bean
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

open class MetricsModule {
    open val meterRegistry by bean {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
