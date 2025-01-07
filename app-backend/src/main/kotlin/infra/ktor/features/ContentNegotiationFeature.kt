package infra.ktor.features

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import infra.ktor.KtorFeature

class ContentNegotiationFeature : KtorFeature {
    override fun Application.install() {
        install(ContentNegotiation) {
            json()
        }
    }
}
