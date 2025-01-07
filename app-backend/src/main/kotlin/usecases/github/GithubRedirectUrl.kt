package usecases.github

import io.ktor.http.*
import io.ktor.server.util.*

class GithubRedirectUrl(
    private val githubAuthConfig: GithubAuthConfig,
) {
    operator fun invoke(): String {
        return url {
            protocol = URLProtocol.HTTPS
            host = "github.com"
            path("login", "oauth", "authorize")

            parameters.append("client_id", githubAuthConfig.clientId)
            parameters.append("redirect_uri", githubAuthConfig.redirectUri)
            parameters.append("allow_signup", "true")
        }
    }
}
