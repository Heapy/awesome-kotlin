package usecases.links

import infra.ktor.KtorRoute
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

class LinksRoute(
    private val linksDtoSource: LinksDtoSource,
) : KtorRoute {
    override fun Route.install() {
        get("/api/links") {
            val links = linksDtoSource.get()
            val isAwesome = call.request.queryParameters["awesome"]?.toBoolean() == true
            val isKugs = call.request.queryParameters["kugs"]?.toBoolean() == true
            val query = call.request.queryParameters["query"]

            val filteredLinks = if (isAwesome) {
                if (query != null) {
                    links.filterByQuery(query)
                } else {
                    links.filterByAwesome()
                }
            } else if (isKugs) {
                links.filterByKug().filterByQuery(query)
            } else {
                links.filterByQuery(query)
            }

            call.respond(filteredLinks)
        }
    }
}
