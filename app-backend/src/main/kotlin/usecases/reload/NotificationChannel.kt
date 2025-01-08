package usecases.reload

import io.heapy.komok.tech.logging.Logger
import io.ktor.websocket.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class NotificationChannel {
    private val mutex = Mutex()
    private val channels = mutableListOf<SendChannel<Frame>>()

    suspend fun notifyAll(text: String) {
        mutex.withLock {
            log.info("Notifying all channels: {}", channels.size)
            channels.forEachIndexed { idx, channel ->
                log.info("Sending message to channel: {}", idx)
                channel.send(Frame.Text(text))
                log.info("Message sent to channel: {}", idx)
            }
        }
    }

    suspend fun register(outgoing: SendChannel<Frame>) {
        mutex.withLock {
            log.info("Registering new channel")
            channels.add(outgoing)
            outgoing.invokeOnClose {
                log.info("Channel closed")
                channels.remove(outgoing)
            }
        }
    }

    private companion object : Logger()
}
