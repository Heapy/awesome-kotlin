package link.kotlin.scripts

import link.kotlin.scripts.data.Links

interface SitemapGenerator {
    fun generate(): String
}

class DefaultSitemapGenerator(val links: Links) : SitemapGenerator {
    override fun generate(): String {
        return "https://github.com/dfabulich/sitemapgen4j"
    }
}