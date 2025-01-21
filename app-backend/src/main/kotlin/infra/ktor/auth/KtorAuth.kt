package infra.ktor.auth

import infra.db.transaction.TransactionContext
import infra.jwt.Jwt
import infra.ktor.features.AuthenticationException
import infra.ktor.features.AuthorizationException
import io.ktor.server.routing.RoutingContext

class KtorAuth(
    private val jwt: Jwt,
) {
    context(
        routing: RoutingContext,
        _: TransactionContext
    )
    suspend fun auth(
        accessControl: AccessControl,
        body: suspend UserContext.() -> Unit,
    ) {
        val userContext = if (accessControl == Anonymous) {
            AnonymousUserContext
        } else {
            val token = routing.call.request.cookies["token"]

            if (token == null) {
                throw AuthenticationException()
            }

            val parseToken = jwt.parseToken(token).getOrNull()

            if (parseToken == null) {
                throw AuthenticationException()
            }

            // No roles or permissions ATM
            DefaultUserContext(
                id = parseToken.sub,
                roles = setOf(),
                permissions = setOf(),
            )
        }

        if (!isAuthorized(userContext, accessControl)) {
            throw AuthorizationException()
        }

        userContext.body()
    }
}
