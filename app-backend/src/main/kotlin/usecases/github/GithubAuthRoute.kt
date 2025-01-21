package usecases.github

import infra.db.transaction.TransactionContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.server.response.*
import infra.ktor.auth.UserContext
import io.ktor.http.*
import io.ktor.server.routing.RoutingContext

class GithubCallbackRoute(
    private val githubAccessToken: GithubAccessToken,
) : EasyKtorRoute {
    override val path = "/auth/github"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        val code = call.request.queryParameters["code"]
            ?: error("No code provided")

        val accessToken = githubAccessToken.exchange(code)

        // set cookie and redirect
        call.response.cookies.append("accessToken", "Bearer $accessToken")
        call.response.headers.append(HttpHeaders.Location, "/")
        call.respond(HttpStatusCode.TemporaryRedirect)
    }
}
