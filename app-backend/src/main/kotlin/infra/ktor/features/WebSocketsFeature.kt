package infra.ktor.features

import infra.ktor.KtorFeature
import io.heapy.komok.tech.logging.Logger
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import usecases.reload.NotificationChannel
import kotlin.time.Duration.Companion.seconds

class WebSocketsFeature(
    private val notificationChannel: NotificationChannel,
) : KtorFeature {
    override fun Application.install() {
        install(WebSockets) {
            pingPeriod = 15.seconds
            timeout = 15.seconds
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        routing {
            webSocket("/api/ws") {
                log.info("WebSocket connection established from $call")
                notificationChannel.register(outgoing)
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            log.info("Received: $text")
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private companion object : Logger()
}
