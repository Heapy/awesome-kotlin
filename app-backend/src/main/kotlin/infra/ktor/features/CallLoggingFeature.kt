package infra.ktor.features

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import infra.ktor.KtorFeature
import org.slf4j.event.Level

class CallLoggingFeature : KtorFeature {
    override fun Application.install() {
        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/") }
        }
    }
}
