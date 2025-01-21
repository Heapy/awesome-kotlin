package usecases.healthcheck

import infra.db.transaction.TransactionContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import infra.ktor.auth.UserContext

class HealthCheckRoute : EasyKtorRoute {
    override val path: String = "/healthcheck"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        call.respondText(
            text = """{"status":"ok"}""",
            contentType = io.ktor.http.ContentType.Application.Json,
        )
    }
}

