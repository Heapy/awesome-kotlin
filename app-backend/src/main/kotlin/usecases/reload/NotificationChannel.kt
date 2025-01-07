package usecases.reload

import infra.uuid.UuidSource
import io.heapy.komok.tech.logging.Logger
import io.ktor.websocket.*
import kotlinx.coroutines.channels.SendChannel
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.update

private typealias WebsocketChannel = SendChannel<Frame>

class NotificationChannel(
    private val uuidSource: UuidSource,
) {
    private val channels = AtomicReference(emptySet<WebsocketId>())

    suspend fun notifyAll(text: String) {
        val currentChannels = channels.load()
        log.info("Notifying all channels: {}", currentChannels.size)
        currentChannels.forEach { channel ->
            log.info("Sending message to channel: {}", channel.id)
            channel.webSocketSession.send(Frame.Text(text))
            log.info("Message sent to channel: {}", channel.id)
        }
    }

    fun register(outgoing: SendChannel<Frame>) {
        val websocketId = WebsocketId(
            id = uuidSource.generateUuid(),
            webSocketSession = outgoing,
        )
        log.info("Registering new channel: {}", websocketId.id)
        channels.update { current -> current + websocketId }
        outgoing.invokeOnClose {
            log.info("Channel closing for id: {}", websocketId.id)
            channels.update { current -> current - websocketId }
        }
    }

    private class WebsocketId(
        val id: String,
        val webSocketSession: WebsocketChannel,
    )

    private companion object : Logger()
}
