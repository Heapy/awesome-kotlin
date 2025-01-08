package usecases.github_trending

import infra.config.ConfigModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module

@Module
open class GithubTrendingLinkSourceModule(
    private val configModule: ConfigModule,
) {
    open val githubTrendingLinkSource by lazy {
        GithubTrendingLinkSource()
    }

    open val githubConfig: GithubConfig by lazy {
        configModule.decode("github", GithubConfig.serializer())
    }
}
