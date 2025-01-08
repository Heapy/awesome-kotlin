package usecases.github

import io.ktor.server.response.*
import io.ktor.server.routing.get
import infra.ktor.KtorRoute
import io.ktor.http.*
import io.ktor.server.routing.Route

class GithubCallbackRoute(
    private val githubAccessToken: GithubAccessToken,
) : KtorRoute {
    override fun Route.install() {
        get("/auth/github") {
            val code = call.request.queryParameters["code"]
                ?: error("No code provided")

            val accessToken = githubAccessToken(code)

            // set cookie and redirect
            call.response.cookies.append("accessToken", "Bearer $accessToken")
            call.response.headers.append(HttpHeaders.Location, "/")
            call.respond(HttpStatusCode.TemporaryRedirect)
        }
    }
}
