package infra.ktor.auth

import infra.ktor.features.AuthenticationException
import io.ktor.server.routing.RoutingContext

class KtorAuth(

) {
    context(RoutingContext)
    fun <T> auth(
        accessControl: AccessControl = AlwaysAllow,
        body: context(UserContext) () -> T,
    ) {
        val token = call.request.cookies["token"]

        if (token == null) {
            throw AuthenticationException()
        }
//
//        val userContext = validateTokenAndGetUserContext(token)
//
//        if (!isAuthorized(userContext, permission)) {
//            throw AuthorizationException("User is not authorized")
//        }
//
//        with(userContext) {
//            body()
//        }
    }
}

