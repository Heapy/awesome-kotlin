package infra.ktor

import io.ktor.server.application.*

interface KtorFeature {
    fun Application.install()
}
