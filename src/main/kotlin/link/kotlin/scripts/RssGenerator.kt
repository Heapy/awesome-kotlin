package link.kotlin.scripts

import com.rometools.rome.feed.synd.SyndCategoryImpl
import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.feed.synd.SyndImageImpl
import com.rometools.rome.io.SyndFeedOutput
import link.kotlin.scripts.dsl.Article
import java.io.StringWriter
import java.time.Instant
import java.util.Date

interface RssGenerator {
    fun generate(articles: List<Article>, name: String): String

    companion object
}

private class DefaultRssGenerator : RssGenerator {
    override fun generate(articles: List<Article>, name: String): String {
        val feed = SyndFeedImpl().apply {
            title = "Kotlin Programming Language"
            link = "https://kotlin.link/"
            uri = "https://kotlin.link/$name"
            description = "News, blog posts, projects, podcasts, videos and other. All information about Kotlin."
            feedType = "atom_1.0" // or RSS2?
            image = SyndImageImpl().apply {
                link = "https://kotlin.link/favicon.ico"
            }
            docs = "https://validator.w3.org/feed/docs/rss2.html"
            author = "ruslan@ibragimov.by (Ruslan Ibragimov)"
            webMaster = "ruslan@ibragimov.by (Ruslan Ibragimov)"
            copyright = "CC0 1.0 Universal (CC0 1.0)"
            language = "en"
            categories = listOf(
                SyndCategoryImpl().apply { this.name = "Kotlin" },
                SyndCategoryImpl().apply { this.name = "JVM" },
                SyndCategoryImpl().apply { this.name = "Programming" },
                SyndCategoryImpl().apply { this.name = "Android" }
            )
            generator = "Kotlin 1.4.10"
            publishedDate = Date.from(Instant.now())
            entries = articles.map(::toSyndEntry)
        }

        val writer = StringWriter()
        val output = SyndFeedOutput()
        output.output(feed, writer)
        return writer.buffer.toString()
    }
}

private fun toSyndEntry(article: Article): SyndEntry {
    return SyndEntryImpl().apply {
        uri = article.url
        link = "https://kotlin.link/articles/${article.filename}"
        title = article.title
        author = article.author
        description = SyndContentImpl().also { content ->
            content.value = article.description
        }
    }
}

fun RssGenerator.Companion.default(): RssGenerator {
    return DefaultRssGenerator()
}

