package usecases.github

import infra.db.transaction.TransactionContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.auth.UserContext

class GithubRedirectRoute(
    private val githubRedirectUrl: GithubRedirectUrl,
) : EasyKtorRoute {
    override val path = "/auth/github/redirect"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        call.response.header(HttpHeaders.Location, githubRedirectUrl.get())
        call.respond(HttpStatusCode.Found)
    }
}
