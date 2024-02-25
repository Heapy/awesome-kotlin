package ktor

import io.ktor.server.routing.*

interface KtorRoute {
    fun Routing.install()
}
