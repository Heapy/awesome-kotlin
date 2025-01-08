@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package infra.ktor

import io.ktor.server.routing.Route

interface KtorRoute {
    fun Route.install()
}

context(Route)
fun KtorRoute.installRoute() {
    with(this) {
        install()
    }
}
