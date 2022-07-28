package link.kotlin.server.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import link.kotlin.server.dao.KotlinerDao
import link.kotlin.server.plugins.AuthenticationException
import link.kotlin.server.plugins.JwtConfiguration
import java.util.Date

fun Routing.login(
    jwtConfiguration: JwtConfiguration,
    bcryptVerifier: BCrypt.Verifyer,
    kotlinerDao: KotlinerDao,
) {
    post("/login") {
        val request = call.receive<LoginBody>()
        val db = kotlinerDao.get(request.email)

        db ?: throw AuthenticationException()

        val result = bcryptVerifier.verify(
            request.password,
            db.password
        )

        if (result.verified) {
            val token = JWT.create()
                .withAudience(jwtConfiguration.audience)
                .withIssuer(jwtConfiguration.issuer)
                .withClaim("id", db.id)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC512(jwtConfiguration.secret))

            call.respond(hashMapOf("token" to token))
        } else {
            throw AuthenticationException()
        }
    }
}

@Serializable
data class LoginBody(
    val email: String,
    val password: CharArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginBody

        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.contentHashCode()
        return result
    }
}
