package ktor

import io.ktor.server.routing.Routing

interface KtorRoute {
    fun Routing.install()
}
