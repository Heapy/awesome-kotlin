package usecases.version

import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.KtorRoute

class KotlinVersionRoute(
    private val kotlinVersionFetcher: KotlinVersionFetcher,
) : KtorRoute {
    override fun Route.install() {
        get("/api/kotlin-versions") {
            call.respond(kotlinVersionFetcher.getLatestVersions(listOf("1.9", "2.0")))
        }
    }
}
