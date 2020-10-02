package link.kotlin.scripts

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.runBlocking
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.Category
import link.kotlin.scripts.utils.callLogger
import link.kotlin.scripts.utils.copyResources
import link.kotlin.scripts.utils.writeFile
import java.nio.file.Files
import java.nio.file.Paths

interface SiteGenerator {
    fun createDistFolders()
    fun copyResources()
    fun generateLinksJson(links: List<Category>)
    fun generateKotlinVersionsJson()
    fun generateSitemap(articles: List<Article>)
    fun generateFeeds(articles: List<Article>)
    fun generateArticles(articles: List<Article>)

    companion object
}

private class DefaultSiteGenerator(
    private val mapper: ObjectMapper,
    private val kotlinVersionFetcher: KotlinVersionFetcher,
    private val sitemapGenerator: SitemapGenerator,
    private val pagesGenerator: PagesGenerator,
    private val rssGenerator: RssGenerator
) : SiteGenerator {
    private val base = "./app-frontend"
    private val dist = "$base/dist"

    override fun createDistFolders() {
        // Output folder
        if (Files.notExists(Paths.get("$dist"))) Files.createDirectory(Paths.get("$dist"))
        if (Files.notExists(Paths.get("$dist/articles"))) Files.createDirectory(Paths.get("$dist/articles"))
    }

    override fun copyResources() {
        copyResources(
            "$base/pages/github.css" to "$dist/github.css",
            "$base/pages/styles.css" to "$dist/styles.css",
            "$base/pages/highlight.pack.js" to "$dist/highlight.pack.js",
            "$base/root/robots.txt" to "$dist/robots.txt",
            "$base/root/awesome-kotlin.svg" to "$dist/awesome-kotlin.svg"
        )
    }

    override fun generateLinksJson(links: List<Category>) {
        writeFile("$base/app/links.json", mapper.writeValueAsString(links))
    }

    override fun generateKotlinVersionsJson() = runBlocking {
        val versions = kotlinVersionFetcher.getLatestVersions(listOf("1.3", "1.4"))
        writeFile("$base/app/versions.json", mapper.writeValueAsString(versions))
    }

    override fun generateSitemap(articles: List<Article>) {
        writeFile("$dist/sitemap.xml", sitemapGenerator.generate(articles))
    }

    override fun generateFeeds(articles: List<Article>) {
        val rss = rssGenerator.generate(articles.take(20), "rss.xml")
        val fullRss = rssGenerator.generate(articles, "rss-full.xml")

        writeFile("$dist/rss.xml", rss)
        writeFile("$dist/rss-full.xml", fullRss)
    }

    override fun generateArticles(articles: List<Article>) {
        pagesGenerator.generate(articles, dist)
    }
}

fun SiteGenerator.Companion.default(
    mapper: ObjectMapper,
    kotlinVersionFetcher: KotlinVersionFetcher,
    sitemapGenerator: SitemapGenerator,
    pagesGenerator: PagesGenerator,
    rssGenerator: RssGenerator
): SiteGenerator {
    val instance = DefaultSiteGenerator(
        mapper = mapper,
        kotlinVersionFetcher = kotlinVersionFetcher,
        sitemapGenerator = sitemapGenerator,
        pagesGenerator = pagesGenerator,
        rssGenerator = rssGenerator
    )

    return callLogger<SiteGenerator>(instance)
}
