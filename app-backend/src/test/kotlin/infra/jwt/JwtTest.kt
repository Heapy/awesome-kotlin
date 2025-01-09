package infra.jwt

import io.heapy.komok.tech.time.TestTimeSource
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import types.UnitTest
import java.time.Instant
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

class JwtTest {
    @UnitTest
    fun `generateTokenDefaultDuration should generate a valid token`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource(
            initial = Instant.parse("2021-01-01T00:00:00Z")
        )
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val subject = "test-user"
        val token = jwt.generateTokenDefaultDuration(sub = subject)

        val parsedPayload = jwt.parseToken(token).getOrThrow()
        assertEquals(subject, parsedPayload.sub)
        timeSource.reset()
        val tokenInstant = timeSource.instant()
        assertEquals(tokenInstant.epochSecond, parsedPayload.iat)
        assertEquals(
            tokenInstant.plus(365.days.toJavaDuration()).epochSecond,
            parsedPayload.exp
        )
        assertEquals(
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJleHAiOjE2NDA5OTUyMDAsImlhdCI6MTYwOTQ1OTIwMH0.xxatnW7_PjJYKTN1QYqsTobF1t-bLYvl37-ji-kR8ECmfg7Dt27YgcB2NrYgjDUWL8rcPN_91unoiqqeFT_PoA",
            token
        )
    }

    @UnitTest
    fun `generateToken should include correct payload data`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource()
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val payload = JwtPayload(
            sub = "test-user",
            iat = timeSource.instant().epochSecond,
            exp = timeSource.instant().plus(1.days.toJavaDuration()).epochSecond
        )
        val token = jwt.generateToken(payload)

        val parsedPayload = jwt.parseToken(token).getOrThrow()
        assertEquals(payload.sub, parsedPayload.sub)
        assertEquals(payload.iat, parsedPayload.iat)
        assertEquals(payload.exp, parsedPayload.exp)
    }

    @UnitTest
    fun `parseToken should return an error for invalid token format`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource()
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val invalidToken = "invalid.token"
        val exception = assertThrows<IllegalArgumentException> {
            jwt.parseToken(invalidToken).getOrThrow()
        }
        assertEquals("Invalid token format", exception.message)
    }

    @UnitTest
    fun `parseToken should return an error for invalid signature`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource()
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val payload = JwtPayload(
            sub = "test-user",
            iat = timeSource.instant().epochSecond,
            exp = timeSource.instant().plus(1.days.toJavaDuration()).epochSecond
        )
        val token = jwt.generateToken(payload)
        val tokenParts = token.split(".")

        val tamperedPayload = tokenParts[1].replace("e", "x")
        val tamperedToken = "${tokenParts[0]}.$tamperedPayload.${tokenParts[2]}"

        val exception = assertThrows<IllegalArgumentException> {
            jwt.parseToken(tamperedToken).getOrThrow()
        }
        assertEquals("Invalid signature", exception.message)
    }

    @UnitTest
    fun `parseToken should return an error for expired token`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource()
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val payload = JwtPayload(
            sub = "test-user",
            iat = timeSource.instant().epochSecond,
            exp = timeSource.instant().minus(1.days.toJavaDuration()).epochSecond
        )
        val token = jwt.generateToken(payload)

        val exception = assertThrows<IllegalArgumentException> {
            jwt.parseToken(token).getOrThrow()
        }
        assertEquals("Invalid payload", exception.message)
    }

    @UnitTest
    fun `parseToken should return an error for token issued in the future`() {
        val secret = "my-secret-key"
        val timeSource = TestTimeSource()
        val jwt = Jwt(
            secret = secret,
            json = Json,
            timeSource = timeSource,
        )

        val payload = JwtPayload(
            sub = "test-user",
            iat = timeSource.instant().plus(1.days.toJavaDuration()).epochSecond,
            exp = timeSource.instant().plus(2.days.toJavaDuration()).epochSecond
        )
        val token = jwt.generateToken(payload)

        val exception = assertThrows<IllegalArgumentException> {
            jwt.parseToken(token).getOrThrow()
        }
        assertEquals("Invalid payload", exception.message)
    }
//
//    @UnitTest
//    fun `isSignatureValid should correctly validate token signature`() {
//        val secret = "my-secret-key"
//        val timeSource = TestTimeSource()
//        val jwt = Jwt(secret, Json, timeSource)
//
//        val payload = JwtPayload(
//            sub = "test-user",
//            iat = timeSource.instant().epochSecond,
//            exp = timeSource.instant().plus(1.days.toJavaDuration()).epochSecond
//        )
//        val token = jwt.generateToken(payload)
//
//        val jwtParts = token.split(".")
//        val jwtPartsObj = Jwt.JwtParts(
//            encodedHeader = jwtParts[0],
//            encodedPayload = jwtParts[1],
//            providedSignature = jwtParts[2]
//        )
//
//        assertTrue(jwtPartsObj.isSignatureValid())
//    }
//
//    @UnitTest
//    fun `decodePayload should correctly parse encoded payload`() {
//        val secret = "my-secret-key"
//        val timeSource = TestTimeSource()
//        val jwt = Jwt(secret, Json, timeSource)
//
//        val payload = JwtPayload(
//            sub = "test-user",
//            iat = timeSource.instant().epochSecond,
//            exp = timeSource.instant().plus(1.days.toJavaDuration()).epochSecond
//        )
//        val encodedPayload = Json.encodeToString(JwtPayload.serializer(), payload)
//            .toByteArray(Charsets.UTF_8)
//            .encodeBase64()
//
//        val jwtParts = Jwt.JwtParts(
//            encodedHeader = "",
//            encodedPayload = encodedPayload,
//            providedSignature = ""
//        )
//
//        val decodedPayload = jwtParts.decodePayload()
//        assertEquals(payload.sub, decodedPayload.sub)
//        assertEquals(payload.iat, decodedPayload.iat)
//        assertEquals(payload.exp, decodedPayload.exp)
//    }
}
