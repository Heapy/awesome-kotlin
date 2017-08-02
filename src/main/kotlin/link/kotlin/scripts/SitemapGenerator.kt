package link.kotlin.scripts

import link.kotlin.scripts.model.ApplicationConfiguration

interface SitemapGenerator {
    fun generate(articles: List<Article>): String
}

class DefaultSitemapGenerator(
    private val configuration: ApplicationConfiguration
) : SitemapGenerator {

    override fun generate(articles: List<Article>): String {
        return sitemap {
            +SitemapUrl(configuration.siteUrl)

            articles.forEach {
                +SitemapUrl("${configuration.siteUrl}articles/${it.filename}")
            }
        }
    }
}
