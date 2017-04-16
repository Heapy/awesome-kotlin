package link.kotlin.scripts

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("Main")

    logger.info("Start getting project links and articles")
    val compiler = DefaultScriptCompiler()
    val projectLinks = ProjectLinks(compiler).getLinks()
    val articles = Articles(compiler)
    logger.info("Finish getting project links and articles")

    if (args.getOrNull(0) != "CI") {
        // Output folder
        if (!Files.exists(Paths.get("./dist"))) Files.createDirectory(Paths.get("./dist"))

        if (!Files.exists(Paths.get("./dist/articles"))) Files.createDirectory(Paths.get("./dist/articles"))

        val mapper = jacksonObjectMapper()

        val config = ApplicationConfiguration(
            ghUser = System.getenv("GH_USER") ?: "",
            ghToken = System.getenv("GH_TOKEN") ?: ""
        )

        logger.info("Start checking links")
        LinkChecker(projectLinks).check()
        logger.info("Finish checking links")

        // Sitemap
        logger.info("Start generating sitemap")
        val sitemap = DefaultSitemapGenerator().generate(articles.articles())
        Files.write(Paths.get("./dist/sitemap.xml"), sitemap.toByteArray(), StandardOpenOption.CREATE)
        logger.info("Finish generating sitemap")

        // Stars
        okhttp { client ->
            val stars = DefaultStarsGenerator(config, mapper, client).generate(projectLinks)
            Files.write(Paths.get("./app/LinksWithStars.json"), stars.toByteArray(), StandardOpenOption.CREATE)
        }

        // Pages
        DefaultPageGenerator().generate(articles.articles())

        // RSS
        val rss = DefaultRssGenerator(articles.articles()).generate("rss.xml", 20)
        val fullRss = DefaultRssGenerator(articles.articles()).generate("rss-full.xml", articles.articles().size)
        Files.write(Paths.get("./dist/rss.xml"), rss.toByteArray(), StandardOpenOption.CREATE)
        Files.write(Paths.get("./dist/rss-full.xml"), fullRss.toByteArray(), StandardOpenOption.CREATE)
    }

    val readme = DefaultReadmeGenerator(projectLinks, articles.links()).generate()
    Files.write(Paths.get("README.md"), readme.toByteArray())
}

