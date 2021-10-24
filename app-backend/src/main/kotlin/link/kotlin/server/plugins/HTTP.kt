package link.kotlin.server.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CachingHeaders
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.CachingOptions
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.serialization.json
import kotlin.reflect.KProperty
import kotlin.time.Duration

fun Application.defaults(
    jwtConfiguration: JwtConfiguration,
) {
    install(Locations)

    install(ContentNegotiation) {
        json()
    }

    install(DefaultHeaders) {
        header("X-Application", "awesome-kotlin/v2")
    }

    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(
                    CacheControl.MaxAge(
                        maxAgeSeconds = Duration
                            .days(365)
                            .inWholeSeconds
                            .toInt()
                    )
                )
                else -> null
            }
        }
    }

    install(StatusPages) {
        exception<AuthenticationException> { cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { cause ->
            call.respond(HttpStatusCode.Forbidden)
        }
        exception<ConstraintViolationException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.fields)
        }
    }

    authentication {
        jwt("jwt") {
            realm = jwtConfiguration.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC512(jwtConfiguration.secret))
                    .withAudience(jwtConfiguration.audience)
                    .withIssuer(jwtConfiguration.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtConfiguration.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

data class JwtConfiguration(
    val audience: String,
    val realm: String,
    val issuer: String,
    val secret: String,
)

class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

class ConstraintViolationException(
    val fields: List<ConstraintViolationFields>
) : RuntimeException()

data class ConstraintViolationFields(
    val message: String,
    val fields: List<KProperty<*>>
)
