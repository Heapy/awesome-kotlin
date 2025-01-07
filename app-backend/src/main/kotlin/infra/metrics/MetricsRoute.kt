package infra.metrics

import infra.db.transaction.TransactionContext
import infra.ktor.easy.EasyKtorRoute
import infra.ktor.auth.UserContext
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

class MetricsRoute(
    private val meterRegistry: PrometheusMeterRegistry,
) : EasyKtorRoute {
    override val path = "/metrics"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        call.respondText(
            text = meterRegistry.scrape(),
            contentType = ContentType.Text.Plain
                .withCharset(Charsets.UTF_8)
                .withParameter("version", "0.0.4"),
        )
    }
}
