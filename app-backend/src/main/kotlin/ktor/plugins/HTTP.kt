package ktor.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.*
import io.ktor.http.content.CachingOptions
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import usecases.signup.JwtModule
import utils.logger
import kotlin.time.Duration.Companion.days

fun Application.defaults(
    jwtConfig: JwtModule.JwtConfig,
) {
    install(Resources)

    install(ContentNegotiation) {
        json()
    }

    install(DefaultHeaders) {
        header("X-Application", "awesome-kotlin/v2")
    }

    install(CachingHeaders) {
        options { _, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(
                    CacheControl.MaxAge(
                        maxAgeSeconds = 365.days
                            .inWholeSeconds
                            .toInt()
                    )
                )
                else -> null
            }
        }
    }

    install(StatusPages) {
        exception<AuthenticationException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
        }
        exception<ConstraintViolationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.fields)
        }
    }

    authentication {
        jwt("jwt") {
            realm = jwtConfig.realm
            authHeader { call ->
                try {
                    call.request.cookies["token"]
                        ?.let { cookieValue ->
                            parseAuthorizationHeader("Bearer $cookieValue")
                        }
                } catch (cause: IllegalArgumentException) {
                    logger {}.info("Failed to parse JWT from cookie", cause)
                    null
                }
            }
            verifier(
                JWT
                    .require(Algorithm.HMAC512(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtConfig.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

class ConstraintViolationException(
    val fields: List<ConstraintViolationFields>,
) : RuntimeException()

@Serializable
data class ConstraintViolationFields(
    val message: String,
    val fields: List<String>,
)
