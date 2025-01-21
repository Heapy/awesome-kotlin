package usecases.kug

import infra.db.transaction.TransactionContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.auth.UserContext

class GetKugsRoute(
    private val kugDao: KugDao,
) : EasyKtorRoute {
    override val path = "/kugs"

    context(
        _: TransactionContext,
        _: UserContext
    )
    override suspend fun RoutingContext.handle() {
        call.respond(kugDao.getAll())
    }
}
