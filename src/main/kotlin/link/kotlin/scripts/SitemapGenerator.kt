package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.model.default

interface SitemapGenerator {
    fun generate(articles: List<Article>): String

    companion object
}

private class DefaultSitemapGenerator(
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

fun SitemapGenerator.Companion.default(
    configuration: ApplicationConfiguration = ApplicationConfiguration.default()
): SitemapGenerator {
    return DefaultSitemapGenerator(
        configuration = configuration
    )
}
