package usecases.sitemap

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.buildUrl
import io.ktor.http.path
import usecases.web.WebApplicationContext

class SitemapGenerator(
    private val externalWebApplicationContext: WebApplicationContext,
) {
    private val siteUrl by lazy {
        buildUrl {
            protocol = when (externalWebApplicationContext.protocol) {
                "http" -> URLProtocol.HTTP
                "https" -> URLProtocol.HTTPS
                else -> URLProtocol.HTTPS
            }
            host = externalWebApplicationContext.host
            port = externalWebApplicationContext.port
        }
    }

    fun generate(articles: List<Article>): String {
        return sitemap {
            +SitemapUrl(siteUrl.toString())

            articles.forEach { article ->
                +SitemapUrl(
                    url = URLBuilder(siteUrl)
                        .apply {
                            path("articles", article.filename)
                        }
                        .toString()
                )
            }
        }
    }
}

class Article(
    val filename: String
)
