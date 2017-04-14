package link.kotlin.scripts

import com.redfin.sitemapgenerator.WebSitemapGenerator
import java.io.File


interface SitemapGenerator {
    fun generate(articles: List<Article>): String
}

class DefaultSitemapGenerator : SitemapGenerator {
    override fun generate(articles: List<Article>): String {
        val wsg = WebSitemapGenerator
            .builder("https://kotlin.link/", File(""))
            .build()

        wsg.addUrl("https://kotlin.link/")

        articles.forEach {
            wsg.addUrl("https://kotlin.link/articles/${it.filename}")
        }

        return wsg.writeAsStrings().joinToString(separator = "\n")
    }
}
