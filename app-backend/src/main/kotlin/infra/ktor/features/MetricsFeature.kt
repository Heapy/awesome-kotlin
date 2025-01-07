package infra.ktor.features

import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.micrometer.core.instrument.binder.MeterBinder
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import infra.ktor.KtorFeature

class MetricsFeature(
    private val meterRegistry: PrometheusMeterRegistry,
    private val binders: List<MeterBinder>,
) : KtorFeature {
    override fun Application.install() {
        install(MicrometerMetrics) {
            registry = meterRegistry
            meterBinders = binders
        }
    }
}
