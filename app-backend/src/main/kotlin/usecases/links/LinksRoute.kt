package usecases.links

import infra.db.transaction.TransactionContext
import infra.ktor.auth.UserContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

class LinksRoute(
    private val linksDtoSource: LinksDtoSource,
) : EasyKtorRoute {
    override val path = "/api/links"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        val links = linksDtoSource.get()
        val query = call.request.queryParameters["query"]

        call.respond(links.filterByQuery(query))
    }
}
