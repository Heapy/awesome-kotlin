package usecases.github

import ConfigModule
import HttpClientModule
import io.heapy.komok.tech.di.delegate.bean
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

open class GithubModule(
    private val configModule: ConfigModule,
    private val httpClientModule: HttpClientModule,
) {
    open val githubAuthConfig by bean<GithubAuthConfig> {
        Hocon.decodeFromConfig(configModule.config.value.getConfig("github"))
    }

    open val githubRedirectUrl by bean {
        GithubRedirectUrl(
            githubAuthConfig = githubAuthConfig.value,
        )
    }

    open val githubRedirectRoute by bean {
        GithubRedirectRoute(
            githubRedirectUrl = githubRedirectUrl.value,
        )
    }

    open val githubAccessToken by bean {
        GithubAccessToken(
            githubAuthConfig = githubAuthConfig.value,
            httpClient = httpClientModule.httpClient.value,
        )
    }

    open val githubCallbackRoute by bean {
        GithubCallbackRoute(
            githubAccessToken = githubAccessToken.value,
        )
    }
}
