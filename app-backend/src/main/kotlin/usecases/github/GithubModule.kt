package usecases.github

import ConfigModule
import HttpClientModule
import di.bean
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

open class GithubModule(
    private val configModule: ConfigModule,
    private val httpClientModule: HttpClientModule,
) {
    open val githubAuthConfig by bean<GithubAuthConfig> {
        Hocon.decodeFromConfig(configModule.config.get.getConfig("github"))
    }

    open val githubRedirectUrl by bean {
        GithubRedirectUrl(
            githubAuthConfig = githubAuthConfig.get,
        )
    }

    open val githubRedirectRoute by bean {
        GithubRedirectRoute(
            githubRedirectUrl = githubRedirectUrl.get,
        )
    }

    open val githubAccessToken by bean {
        GithubAccessToken(
            githubAuthConfig = githubAuthConfig.get,
            httpClient = httpClientModule.httpClient.get,
        )
    }

    open val githubCallbackRoute by bean {
        GithubCallbackRoute(
            githubAccessToken = githubAccessToken.get,
        )
    }
}
