package infra.metrics

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import infra.ktor.KtorRoute

class MetricsRoute(
    private val meterRegistry: PrometheusMeterRegistry,
) : KtorRoute {
    override fun Route.install() {
        get("/metrics") {
            call.respondText(
                text = meterRegistry.scrape(),
                contentType = ContentType.Text.Plain
                    .withCharset(Charsets.UTF_8)
                    .withParameter("version", "0.0.4"),
            )
        }
    }
}
