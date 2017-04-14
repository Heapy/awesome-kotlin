package link.kotlin.scripts

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main(args: Array<String>) {
    if (args.getOrNull(0) != "CI") {
        // Output folder
        if (!Files.exists(Paths.get("./dist"))) Files.createDirectory(Paths.get("./dist"))

        val mapper = jacksonObjectMapper()

        val config = ApplicationConfiguration(
            ghUser = System.getenv("GH_USER") ?: "",
            ghToken = System.getenv("GH_TOKEN") ?: ""
        )

        val projectLinks = ProjectLinks().getLinks()
        val articles = Articles().articles()

        LinkChecker(projectLinks).check()

        // Sitemap
        val sitemap = DefaultSitemapGenerator().generate(articles)
        Files.write(Paths.get("./dist/sitemap.xml"), sitemap.toByteArray(), StandardOpenOption.CREATE)

        // Stars
        okhttp { client ->
            val stars = DefaultStarsGenerator(config, mapper, client).generate(projectLinks)
            Files.write(Paths.get("./app/LinksWithStars.json"), stars.toByteArray(), StandardOpenOption.CREATE)
        }

        // Pages
        DefaultPageGenerator().generate(articles)

        // RSS
        val rss = DefaultRssGenerator(articles).generate("rss.xml", 20)
        val fullRss = DefaultRssGenerator(articles).generate("rss-full.xml", articles.size)
        Files.write(Paths.get("./dist/rss.xml"), rss.toByteArray(), StandardOpenOption.CREATE)
        Files.write(Paths.get("./dist/rss-full.xml"), fullRss.toByteArray(), StandardOpenOption.CREATE)
    }

    val readme = DefaultReadmeGenerator().generate()
    Files.write(Paths.get("README.md"), readme.toByteArray())
}

