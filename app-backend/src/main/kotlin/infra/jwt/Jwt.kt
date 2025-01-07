package infra.jwt

import io.heapy.komok.tech.time.TimeSource
import kotlinx.serialization.json.Json
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Duration.Companion.days

private const val hashAlgorithm = "HmacSHA512"

class Jwt(
    secret: String,
    private val json: Json,
    private val timeSource: TimeSource,
) {
    private val secretKeySpec by lazy {
        SecretKeySpec(secret.toByteArray(Charsets.UTF_8), hashAlgorithm)
    }

    fun generateTokenDefaultDuration(
        sub: String,
    ): String {
        val now = timeSource.instant()
        val expiration = now.plusSeconds(365.days.inWholeSeconds)
        val jwtPayload = JwtPayload(
            sub = sub,
            exp = expiration.epochSecond,
            iat = now.epochSecond,
        )
        return generateToken(jwtPayload)
    }

    fun generateToken(
        payload: JwtPayload,
    ): String {
        val header = JwtHeader(
            alg = "HS512",
            typ = "JWT",
        )
        val headerJson = json.encodeToString(JwtHeader.serializer(), header)
        val payloadJson = json.encodeToString(JwtPayload.serializer(), payload)

        val encodedHeader = headerJson.toByteArray(Charsets.UTF_8).encodeBase64()
        val encodedPayload = payloadJson.toByteArray(Charsets.UTF_8).encodeBase64()

        val signatureInput = "$encodedHeader.$encodedPayload"
        val signature = signatureInput.hmacSHA512()

        return "$signatureInput.$signature"
    }

    fun parseToken(
        token: String,
    ): Result<JwtPayload> {
        return Result.success(token)
            .mapCatching { token ->
                token.split(".")
                    .let { parts ->
                        require(parts.size == 3) { "Invalid token format" }
                        JwtParts(
                            encodedHeader = parts[0],
                            encodedPayload = parts[1],
                            providedSignature = parts[2],
                        )
                    }
            }
            .mapCatching { jwtParts ->
                jwtParts
                    .isSignatureValid()
                    .let { isValid ->
                        require(isValid) { "Invalid signature" }
                        jwtParts
                    }
            }
            .mapCatching { jwtParts ->
                jwtParts
                    .decodePayload()
                    .also { payload ->
                        require(payload.isPayloadValid()) { "Invalid payload" }
                    }
            }
    }

    private data class JwtParts(
        val encodedHeader: String,
        val encodedPayload: String,
        val providedSignature: String,
    )

    private fun JwtParts.isSignatureValid(): Boolean {
        val signatureInput = "$encodedHeader.$encodedPayload"
        val calculatedSignature = signatureInput.hmacSHA512()
        return providedSignature == calculatedSignature
    }

    private fun JwtPayload.isPayloadValid(): Boolean {
        val now = timeSource.instant()
        val expiration = Instant.ofEpochSecond(exp)
        val issuedAt = Instant.ofEpochSecond(iat)

        return !expiration.isBefore(now) && !issuedAt.isAfter(now)
    }

    private fun JwtParts.decodePayload(): JwtPayload {
        val payloadJson = encodedPayload.decodeBase64()
        return json.decodeFromString(JwtPayload.serializer(), payloadJson)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun ByteArray.encodeBase64(): String {
        val arr = this
        return base64WithoutPadding.encode(arr)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun String.decodeBase64(): String {
        return base64WithoutPadding.decode(this).toString(Charsets.UTF_8)
    }

    private fun String.hmacSHA512(): String {
        val mac = Mac.getInstance(hashAlgorithm)
        mac.init(secretKeySpec)
        val hmacBytes = mac.doFinal(this.toByteArray(Charsets.UTF_8))
        return hmacBytes.encodeBase64()
    }

    private companion object {
        @OptIn(ExperimentalEncodingApi::class)
        val base64WithoutPadding = Base64
            .UrlSafe
            .withPadding(Base64.PaddingOption.ABSENT)
    }
}
