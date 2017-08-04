package link.kotlin.scripts

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.experimental.runBlocking
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.utils.CopyTask
import link.kotlin.scripts.utils.DefaultScriptCompiler
import link.kotlin.scripts.utils.logger
import link.kotlin.scripts.utils.measureAndLog
import link.kotlin.scripts.utils.okHttpClient
import java.nio.file.Files
import java.nio.file.Files.write
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            runBlocking {
                LOGGER.info("Start getting project links and articles")
                val compiler = DefaultScriptCompiler()
                val projectLinks = ProjectLinks(compiler).getLinks()
                val articles = Articles(compiler)
                LOGGER.info("Finish getting project links and articles")

                when (args.getOrNull(0)) {
                    "true" -> site(projectLinks, articles)
                    else -> readme(projectLinks, articles)
                }
            }
        } catch (t: Throwable) {
            LOGGER.error("Unhandled error", t)
            throw t
        }
    }

    private suspend fun readme(projectLinks: Links, articles: Articles) {
        val readme = DefaultReadmeGenerator(projectLinks, articles.links()).generate()
        write(Paths.get("README.md"), readme.toByteArray(), CREATE, TRUNCATE_EXISTING)
    }

    private suspend fun site(projectLinks: Links, articles: Articles) {
        // Output folder
        measureAndLog("creating output folders") {
            if (!Files.exists(Paths.get("./dist"))) Files.createDirectory(Paths.get("./dist"))
            if (!Files.exists(Paths.get("./dist/articles"))) Files.createDirectory(Paths.get("./dist/articles"))
        }

        val mapper = jacksonObjectMapper()
        val config = ApplicationConfiguration()

        measureAndLog("checking links") {
            LinkChecker(projectLinks).check()
        }

        // Sitemap
        measureAndLog("generating sitemap") {
            val sitemap = DefaultSitemapGenerator(config).generate(articles.articles())
            write(Paths.get("./dist/sitemap.xml"), sitemap.toByteArray(), CREATE, TRUNCATE_EXISTING)
        }

        // Stars
        measureAndLog("fetching stars") {
            val stars = DefaultStarsGenerator(config, mapper, okHttpClient)
                .generate(projectLinks + articles.links())
            write(Paths.get("./app/LinksWithStars.json"), stars.toByteArray(), CREATE, TRUNCATE_EXISTING)
        }

        // Pages
        measureAndLog("generating articles") {
            DefaultPageGenerator().generate(articles.articles())
        }

        // Copy
        measureAndLog("copying assets") {
            CopyTask.copy(mapOf(
                "pages/github.css" to "./dist/github.css",
                "pages/styles.css" to "./dist/styles.css",
                "pages/highlight.pack.js" to "./dist/highlight.pack.js",
                "robots.txt" to "./dist/robots.txt",
                "awesome-kotlin.svg" to "./dist/awesome-kotlin.svg"
            ))
        }

        // RSS
        measureAndLog("generating RSS") {
            val rssGenerator = DefaultRssGenerator(articles.articles())
            val rss = rssGenerator.generate("rss.xml", 20)
            val fullRss = rssGenerator.generate("rss-full.xml", articles.articles().size)
            write(Paths.get("./dist/rss.xml"), rss.toByteArray(), CREATE, TRUNCATE_EXISTING)
            write(Paths.get("./dist/rss-full.xml"), fullRss.toByteArray(), CREATE, TRUNCATE_EXISTING)
        }

        okHttpClient.dispatcher().executorService().shutdown()
    }

    private val LOGGER = logger<Application>()
}


