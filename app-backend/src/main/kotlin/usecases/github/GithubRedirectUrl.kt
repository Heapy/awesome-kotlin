package usecases.github

import di.Bean0
import io.ktor.http.*
import io.ktor.server.util.*

class GithubRedirectUrl(
    private val githubAuthConfig: GithubAuthConfig,
) : Bean0<String> {
    override fun invoke(): String {
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
