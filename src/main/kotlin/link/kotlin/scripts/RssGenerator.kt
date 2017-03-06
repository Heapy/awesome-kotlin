package link.kotlin.scripts

import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.feed.synd.SyndImageImpl
import link.kotlin.scripts.data.Links

interface RssGenerator {
    fun generate(name: String, limit: Int): String
}

class DefaultRssGenerator(val links: Links): RssGenerator {
    override fun generate(name: String, limit: Int): String {
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
            generator = "Kotlin"
//            setEntries(entries.map(::toSyndEntry))
        }

        return "rss"
    }
}