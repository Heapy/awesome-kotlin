package usecases.rss

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.KtorRoute
import usecases.articles.ArticleSource

class RssRoute(
    private val articleSource: ArticleSource,
    private val rssGenerator: RssGenerator,
) : KtorRoute {
    override fun Route.install() {
        get("/rss.xml") {
            val articles = articleSource
                .getArticles()
                .sortedByDescending { it.date }
                .take(20)

            val rss = rssGenerator.generate(articles, "rss.xml")
            call.respondText(rss, ContentType.Application.Atom.withCharset(Charsets.UTF_8))
        }
    }
}

class FullRssRoute(
    private val articleSource: ArticleSource,
    private val rssGenerator: RssGenerator,
) : KtorRoute {
    override fun Route.install() {
        get("/rss-full.xml") {
            val articles = articleSource
                .getArticles()
                .sortedByDescending { it.date }

            val rss = rssGenerator.generate(articles, "rss-full.xml")
            call.respondText(rss, ContentType.Application.Atom.withCharset(Charsets.UTF_8))
        }
    }
}
