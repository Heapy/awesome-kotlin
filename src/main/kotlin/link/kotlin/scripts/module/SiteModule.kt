package link.kotlin.scripts.module

import io.heapy.komok.tech.di.delegate.bean
import link.kotlin.scripts.DefaultPagesGenerator
import link.kotlin.scripts.DefaultRssGenerator
import link.kotlin.scripts.DefaultSiteGenerator
import link.kotlin.scripts.DefaultSitemapGenerator
import link.kotlin.scripts.KotlinVersionFetcher
import link.kotlin.scripts.MavenCentralKotlinVersionFetcher
import link.kotlin.scripts.PagesGenerator
import link.kotlin.scripts.RssGenerator
import link.kotlin.scripts.SiteGenerator
import link.kotlin.scripts.SitemapGenerator
import link.kotlin.scripts.utils.callLogger

class SiteModule(
    private val configurationModule: ConfigurationModule,
    private val utilsModule: UtilsModule,
) {
    val kotlinVersionFetcher by bean<KotlinVersionFetcher> {
        MavenCentralKotlinVersionFetcher(httpClient = utilsModule.httpClient.value)
    }

    val sitemapGenerator by bean<SitemapGenerator> {
        DefaultSitemapGenerator(configuration = configurationModule.applicationConfiguration.value)
    }

    val pagesGenerator by bean<PagesGenerator> {
        DefaultPagesGenerator()
    }

    val rssGenerator by bean<RssGenerator> {
        DefaultRssGenerator()
    }

    val siteGenerator by bean {
        val instance = DefaultSiteGenerator(
            mapper = utilsModule.objectMapper.value,
            kotlinVersionFetcher = kotlinVersionFetcher.value,
            sitemapGenerator = sitemapGenerator.value,
            pagesGenerator = pagesGenerator.value,
            rssGenerator = rssGenerator.value
        )
        callLogger<SiteGenerator>(instance)
    }
}
