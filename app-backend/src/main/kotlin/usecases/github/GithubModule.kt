package usecases.github

import HttpClientModule
import infra.config.ConfigModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module

@Module
class GithubModule(
    private val configModule: ConfigModule,
    private val httpClientModule: HttpClientModule,
) {
    val githubAuthConfig: GithubAuthConfig by lazy {
        configModule.decode("github_auth")
    }

    val githubRedirectUrl by lazy {
        GithubRedirectUrl(
            githubAuthConfig = githubAuthConfig,
        )
    }

    val githubRedirectRoute by lazy {
        GithubRedirectRoute(
            githubRedirectUrl = githubRedirectUrl,
        )
    }

    val githubAccessToken by lazy {
        GithubAccessToken(
            githubAuthConfig = githubAuthConfig,
            httpClient = httpClientModule.httpClient,
        )
    }

    val githubCallbackRoute by lazy {
        GithubCallbackRoute(
            githubAccessToken = githubAccessToken,
        )
    }
}
