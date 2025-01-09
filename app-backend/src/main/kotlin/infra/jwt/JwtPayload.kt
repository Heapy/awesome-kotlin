package infra.jwt

import kotlinx.serialization.Serializable

@Serializable
data class JwtPayload(
    val sub: String,
    val exp: Long,
    val iat: Long,
)
