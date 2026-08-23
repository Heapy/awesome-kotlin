package usecases.github

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import ktor.KtorRoute

class GithubRedirectRoute(
    private val githubRedirectUrl: GithubRedirectUrl,
) : KtorRoute {
    override fun Routing.install() {
        get("/auth/github/redirect") {
            call.response.header(HttpHeaders.Location, githubRedirectUrl())
            call.respond(HttpStatusCode.Found)
        }
    }
}
