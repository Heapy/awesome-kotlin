package usecases.readme

import infra.ktor.KtorRoute
import io.ktor.http.ContentType
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import usecases.links.LinksSource

class ReadmeRoute(
    private val readmeGenerator: ReadmeGenerator,
    private val linksSource: LinksSource,
) : KtorRoute {
    override fun Route.install() {
        get("/readme.md") {
            val readme = readmeGenerator.generate(linksSource.getLinks())
            call.respondText(readme, ContentType.Text.Plain)
        }
    }
}
