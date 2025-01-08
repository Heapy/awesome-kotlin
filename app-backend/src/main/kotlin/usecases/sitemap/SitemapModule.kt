package usecases.sitemap

import io.heapy.komok.tech.di.lib.Module
import usecases.articles.ArticlesModule
import usecases.web.WebModule

@Module
open class SitemapModule(
    private val webModule: WebModule,
    private val articlesModule: ArticlesModule,
) {
    open val sitemapGenerator by lazy {
        SitemapGenerator(
            externalWebApplicationContext = webModule.externalWebApplicationContext
        )
    }

    open val route by lazy {
        SitemapRoute(
            articleSource = articlesModule.articleSource,
            sitemapGenerator = sitemapGenerator
        )
    }
}
