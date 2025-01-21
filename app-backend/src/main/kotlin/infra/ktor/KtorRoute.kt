package infra.ktor

import io.ktor.server.routing.Route

interface KtorRoute {
    fun Route.install()
}

context(route: Route)
fun KtorRoute.installRoute() {
    route.install()
}
