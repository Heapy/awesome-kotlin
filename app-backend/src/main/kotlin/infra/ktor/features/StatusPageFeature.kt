package infra.ktor.features

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import infra.ktor.KtorFeature

class StatusPageFeature : KtorFeature {
    override fun Application.install() {
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

