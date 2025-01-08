package infra.ktor.features

import io.ktor.server.application.*
import io.ktor.server.resources.*
import infra.ktor.KtorFeature

class ResourcesFeature : KtorFeature {
    override fun Application.install() {
        install(Resources)
    }
}
