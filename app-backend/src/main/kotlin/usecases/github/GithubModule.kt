package usecases.github

import infra.config.ConfigModule
import infra.HttpClientModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module

@Module
open class GithubModule(
    private val configModule: ConfigModule,
    private val httpClientModule: HttpClientModule,
) {
    open val githubAuthConfig: GithubAuthConfig by lazy {
        configModule.decode("github_auth", GithubAuthConfig.serializer())
    }

    open val githubRedirectUrl by lazy {
        GithubRedirectUrl(
            githubAuthConfig = githubAuthConfig,
        )
    }

    open val githubRedirectRoute by lazy {
        GithubRedirectRoute(
            githubRedirectUrl = githubRedirectUrl,
        )
    }

    open val githubAccessToken by lazy {
        GithubAccessToken(
            githubAuthConfig = githubAuthConfig,
            httpClient = httpClientModule.httpClient,
        )
    }

    open val githubCallbackRoute by lazy {
        GithubCallbackRoute(
            githubAccessToken = githubAccessToken,
        )
    }
}
