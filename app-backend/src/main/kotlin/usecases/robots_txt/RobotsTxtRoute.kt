package usecases.robots_txt

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import infra.ktor.KtorRoute
import usecases.web.WebApplicationContext

class RobotsTxtRoute(
    private val externalWebApplicationContext: WebApplicationContext,
) : KtorRoute {
    private val sitemapUrl by lazy {
        url {
            protocol = when (externalWebApplicationContext.protocol) {
                "http" -> URLProtocol.HTTP
                "https" -> URLProtocol.HTTPS
                else -> URLProtocol.HTTPS
            }
            host = externalWebApplicationContext.host
            port = externalWebApplicationContext.port
            path("sitemap.xml")
        }
    }

    override fun Route.install() {
        get("/robots.txt") {
            call.respondText(
                """
                User-agent: *
                Sitemap: $sitemapUrl
                """.trimIndent()
            )
        }
    }
}
