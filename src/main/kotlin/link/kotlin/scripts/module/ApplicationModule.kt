package link.kotlin.scripts.module

import io.heapy.komok.tech.di.delegate.bean
import link.kotlin.scripts.AwesomeKotlinGenerator
import link.kotlin.scripts.DefaultAwesomeKotlinGenerator
import link.kotlin.scripts.utils.callLogger

class ApplicationModule(
    private val linksModule: LinksModule,
    private val articlesModule: ArticlesModule,
    private val siteModule: SiteModule,
) {
    val awesomeKotlinGenerator by bean {
        val implementation = DefaultAwesomeKotlinGenerator(
            linksSource = linksModule.linksSource.value,
            articlesSource = articlesModule.articlesSource.value,
            siteGenerator = siteModule.siteGenerator.value
        )
        callLogger<AwesomeKotlinGenerator>(implementation)
    }
}
