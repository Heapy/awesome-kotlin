package link.kotlin.backend

import io.undertow.server.HttpHandler
import io.undertow.server.RoutingHandler
import io.undertow.server.handlers.PathHandler
import io.undertow.server.handlers.resource.PathResourceManager
import io.undertow.server.handlers.resource.ResourceHandler
import java.nio.file.Paths

fun createRootHandler(): HttpHandler {
    val apiRouting = RoutingHandler()
        .get("/readme") {
            TODO("README.adoc")
        }
        .get("/versions") {
            TODO("kotlin versions")
        }

    val articleRouting = RoutingHandler()
        .get("/{id}") {
            // /article/42?lang=ru
            it.responseSender.send(it.queryParameters["id"]?.first)
        }

    return PathHandler()
        .addPrefixPath("/", ResourceHandler(PathResourceManager(Paths.get("./dist"))))
        .addExactPath("rss.xml") {
            // 20 entries
            TODO("rss.xml")
        }
        .addExactPath("rss-full.xml") {
            // all (limit to something like 1000?)
            // search if there limit in spec
            TODO("rss-full.xml")
        }
        .addExactPath("sitemap.xml") {
            TODO("sitemap.xml")
        }
        .addPrefixPath("/article", articleRouting)
        .addPrefixPath("/api", apiRouting)
}
