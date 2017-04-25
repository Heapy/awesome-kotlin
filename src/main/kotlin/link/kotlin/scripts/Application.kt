package link.kotlin.scripts

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.experimental.runBlocking
import java.lang.System.getenv
import java.nio.file.Files.createDirectory
import java.nio.file.Files.exists
import java.nio.file.Files.write
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING

object Application {
    private val LOGGER = logger<Application>()

    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        LOGGER.info("Start getting project links and articles")
        val compiler = DefaultScriptCompiler()
        val projectLinks = ProjectLinks(compiler).getLinks()
        val articles = Articles(compiler)
        LOGGER.info("Finish getting project links and articles")


        if (args.getOrElse(0, { "false" }) == "true") {
            // Output folder
            if (!exists(Paths.get("./dist"))) createDirectory(Paths.get("./dist"))
            if (!exists(Paths.get("./dist/articles"))) createDirectory(Paths.get("./dist/articles"))

            val mapper = jacksonObjectMapper()

            val config = ApplicationConfiguration(
                ghUser = getenv("GH_USER") ?: "",
                ghToken = getenv("GH_TOKEN") ?: "",
                mercuryToken = getenv("MERCURY_TOKEN") ?: ""
            )

            LOGGER.info("Start checking links")
            LinkChecker(projectLinks).check()
            LOGGER.info("Finish checking links")

            // Sitemap
            LOGGER.info("Start generating sitemap")
            val sitemap = DefaultSitemapGenerator(config).generate(articles.articles())
            write(Paths.get("./dist/sitemap.xml"), sitemap.toByteArray(), CREATE, TRUNCATE_EXISTING)
            LOGGER.info("Finish generating sitemap")

            // Stars
            LOGGER.info("Start fetching start")
            okhttp { client ->
                val stars = DefaultStarsGenerator(config, mapper, client).generate(projectLinks + articles.links())
                write(Paths.get("./app/LinksWithStars.json"), stars.toByteArray(), CREATE, TRUNCATE_EXISTING)
            }
            LOGGER.info("Start fetching finished")

            // Pages
            DefaultPageGenerator().generate(articles.articles())

            // Copy
            CopyTask().copy(mapOf(
                "pages/github.css" to "./dist/github.css",
                "pages/styles.css" to "./dist/styles.css",
                "pages/highlight.pack.js" to "./dist/highlight.pack.js",
                "robots.txt" to "./dist/robots.txt",
                "awesome-kotlin.svg" to "./dist/awesome-kotlin.svg"
            ))

            // RSS
            val rss = DefaultRssGenerator(articles.articles()).generate("rss.xml", 20)
            val fullRss = DefaultRssGenerator(articles.articles()).generate("rss-full.xml", articles.articles().size)
            write(Paths.get("./dist/rss.xml"), rss.toByteArray(), CREATE, TRUNCATE_EXISTING)
            write(Paths.get("./dist/rss-full.xml"), fullRss.toByteArray(), CREATE, TRUNCATE_EXISTING)
        } else {
            val readme = DefaultReadmeGenerator(projectLinks, articles.links()).generate()
            write(Paths.get("README.md"), readme.toByteArray(), CREATE, TRUNCATE_EXISTING)
        }
    }
}


