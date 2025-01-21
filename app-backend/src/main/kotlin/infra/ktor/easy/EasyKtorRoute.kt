package infra.ktor.easy

import infra.db.transaction.TransactionContext
import infra.db.transaction.TransactionType
import infra.ktor.auth.AccessControl
import infra.ktor.auth.Anonymous
import infra.ktor.auth.UserContext
import io.ktor.http.HttpMethod
import io.ktor.server.routing.RoutingContext

interface EasyKtorRoute {
    val transactionType: TransactionType
        get() = TransactionType.NONE
    val accessControl: AccessControl
        get() = Anonymous
    val method: HttpMethod
        get() = HttpMethod.Companion.Get
    val path: String

    context(
        _: TransactionContext,
        _: UserContext
    )
    suspend fun RoutingContext.handle()
}
