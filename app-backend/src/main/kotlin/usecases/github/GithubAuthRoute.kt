package usecases.github

import io.ktor.server.application.call
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import ktor.KtorRoute

class GithubCallbackRoute(
    private val githubAccessToken: GithubAccessToken,
) : KtorRoute {
    override fun Routing.install() {
        get("/auth/github") {
            val code = call.request.queryParameters["code"]
                ?: error("No code provided")

            val accessToken = githubAccessToken(code)

            println(accessToken)

            call.respond("Hello, Github! $accessToken")
        }
    }
}
