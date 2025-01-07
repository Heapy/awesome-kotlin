package usecases.version

import infra.db.transaction.TransactionContext
import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.auth.UserContext
import infra.ktor.easy.EasyKtorRoute

class KotlinVersionRoute(
    private val kotlinVersionDao: KotlinVersionDao,
) : EasyKtorRoute {
    override val path = "/api/kotlin-versions"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        call.respond(kotlinVersionDao.latestAndGreatest())
    }
}
