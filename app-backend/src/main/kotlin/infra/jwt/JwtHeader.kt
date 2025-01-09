package infra.jwt

import kotlinx.serialization.Serializable

@Serializable
data class JwtHeader(
    val alg: String,
    val typ: String,
)
