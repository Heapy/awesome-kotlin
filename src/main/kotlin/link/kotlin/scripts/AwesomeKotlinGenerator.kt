package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.Category
import link.kotlin.scripts.utils.writeFile

interface AwesomeKotlinGenerator {
    fun getLinks(): List<Category>
    fun getArticles(): List<Article>
    fun generateReadme(links: List<Category>)
    fun generateSiteResources(links: List<Category>, articles: List<Article>)

    companion object
}

class DefaultAwesomeKotlinGenerator(
    private val linksSource: LinksSource,
    private val articlesSource: ArticlesSource,
    private val readmeGenerator: MarkdownReadmeGenerator,
    private val siteGenerator: SiteGenerator
) : AwesomeKotlinGenerator {
    override fun getLinks(): List<Category> {
        return linksSource.getLinks()
    }

    override fun getArticles(): List<Article> {
        return articlesSource.getArticles()
    }

    override fun generateReadme(links: List<Category>) {
        writeFile("./readme/README.md", readmeGenerator.generate(links))
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
