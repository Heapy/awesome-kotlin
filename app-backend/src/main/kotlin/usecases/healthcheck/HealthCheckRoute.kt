package usecases.healthcheck

import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import infra.ktor.KtorRoute

class HealthCheckRoute : KtorRoute {
    override fun Route.install() {
        get("/healthcheck") {
            call.respondText(
                text = """{"status":"ok"}""",
                contentType = io.ktor.http.ContentType.Application.Json,
            )
        }
    }
}

