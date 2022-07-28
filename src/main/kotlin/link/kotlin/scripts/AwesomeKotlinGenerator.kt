package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.Category
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.model.default
import link.kotlin.scripts.scripting.ScriptEvaluator
import link.kotlin.scripts.scripting.default
import link.kotlin.scripts.utils.Cache
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.KotlinObjectMapper
import link.kotlin.scripts.utils.callLogger
import link.kotlin.scripts.utils.default
import link.kotlin.scripts.utils.writeFile

interface AwesomeKotlinGenerator {
    fun getLinks(): List<Category>
    fun getArticles(): List<Article>
    fun generateReadme(links: List<Category>)
    fun generateSiteResources(links: List<Category>, articles: List<Article>)

    companion object
}

private class DefaultAwesomeKotlinGenerator(
    private val linksSource: LinksSource,
    private val articlesSource: ArticlesSource,
    private val readmeGenerator: ReadmeGenerator,
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

fun AwesomeKotlinGenerator.Companion.default(): AwesomeKotlinGenerator {
    val mapper = KotlinObjectMapper()
    val httpClient = HttpClient.default()
    val configuration = ApplicationConfiguration.default()
    val markdownRenderer = MarkdownRenderer.default()

    val cache = Cache.default(
        mapper = mapper,
        configuration = configuration
    )

    val scriptEvaluator = ScriptEvaluator.default(
        cache = cache
    )

    val kotlinVersionFetcher = KotlinVersionFetcher.default(
        httpClient = httpClient
    )

    val sitemapGenerator = SitemapGenerator.default(
        configuration = configuration
    )

    val siteGenerator = SiteGenerator.default(
        mapper = mapper,
        kotlinVersionFetcher = kotlinVersionFetcher,
        sitemapGenerator = sitemapGenerator,
        pagesGenerator = PagesGenerator.default(),
        rssGenerator = RssGenerator.default()
    )

    val linksChecker = LinksChecker.default(
        httpClient = httpClient
    )

    val linksProcessor = LinksProcessor.default(
        mapper = mapper,
        httpClient = httpClient,
        linksChecker = linksChecker,
        configuration = configuration,
        markdownRenderer = markdownRenderer
    )

    val categoryProcessor = CategoryProcessor.default(
        linksProcessor = linksProcessor
    )

    val githubTrending = GithubTrending.default(
        cache = cache
    )

    val implementation = DefaultAwesomeKotlinGenerator(
        linksSource = LinksSource.default(
            scriptEvaluator = scriptEvaluator,
            githubTrending = githubTrending,
            categoryProcessor = categoryProcessor
        ),
        articlesSource = ArticlesSource.default(
            scriptEvaluator = scriptEvaluator,
            articlesProcessor = ArticlesProcessor.default(
                markdownRenderer = markdownRenderer
            )
        ),
        readmeGenerator = ReadmeGenerator.default(),
        siteGenerator = siteGenerator
    )

    return callLogger<AwesomeKotlinGenerator>(implementation)
}
