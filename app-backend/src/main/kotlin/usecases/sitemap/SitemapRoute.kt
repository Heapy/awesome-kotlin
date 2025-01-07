package usecases.sitemap

import infra.ktor.KtorRoute
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import usecases.articles.ArticleSource

class SitemapRoute(
    private val sitemapGenerator: SitemapGenerator,
    private val articleSource: ArticleSource,
) : KtorRoute {
    override fun Route.install() {
        get("/sitemap.xml") {
            val articles = articleSource
                .getArticles()
                .map {
                    Article(it.filename)
                }

            call.respondText(
                sitemapGenerator.generate(articles),
                ContentType.Application.Xml.withCharset(Charsets.UTF_8)
            )
        }
    }
}
