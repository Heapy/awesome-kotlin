package server

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class ServerConfig(
    val port: Int,
    val host: String,
    val gracefulShutdownTimeout: Duration,
    val reactDistPath: String,
)
