package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.model.ApplicationConfiguration

class SitemapGenerator(
    private val configuration: ApplicationConfiguration
) {
    fun generate(articles: List<Article>): String {
        return sitemap {
            +SitemapUrl(configuration.siteUrl)

            articles.forEach {
                +SitemapUrl("${configuration.siteUrl}articles/${it.filename}")
            }
        }
    }
}
