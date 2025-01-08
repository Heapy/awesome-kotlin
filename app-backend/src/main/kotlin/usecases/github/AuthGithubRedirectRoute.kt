package usecases.github

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.KtorRoute

class GithubRedirectRoute(
    private val githubRedirectUrl: GithubRedirectUrl,
) : KtorRoute {
    override fun Route.install() {
        get("/auth/github/redirect") {
            call.response.header(HttpHeaders.Location, githubRedirectUrl())
            call.respond(HttpStatusCode.Found)
        }
    }
}
