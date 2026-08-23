package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.Category

interface AwesomeKotlinGenerator {
    fun getLinks(): List<Category>
    fun getArticles(): List<Article>
    fun generateSiteResources(links: List<Category>, articles: List<Article>)
}

internal class DefaultAwesomeKotlinGenerator(
    private val linksSource: LinksSource,
    private val articlesSource: ArticlesSource,
    private val siteGenerator: SiteGenerator
) : AwesomeKotlinGenerator {
    override fun getLinks(): List<Category> {
        return linksSource.getLinks()
    }

    override fun getArticles(): List<Article> {
        return articlesSource.getArticles()
    }

    override fun generateSiteResources(links: List<Category>, articles: List<Article>) {
        siteGenerator.createDistFolders()
        siteGenerator.copyResources()
        siteGenerator.generateLinksJson(links)
        siteGenerator.generateKotlinVersionsJson()
        siteGenerator.generateFeeds(articles)
        siteGenerator.generateSitemap(articles)
        siteGenerator.generateArticles(articles)
    }
}
