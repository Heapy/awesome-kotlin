package usecases.web

import kotlinx.serialization.Serializable

@Serializable
class WebApplicationContext(
    val host: String,
    val port: Int,
    val protocol: String,
)
