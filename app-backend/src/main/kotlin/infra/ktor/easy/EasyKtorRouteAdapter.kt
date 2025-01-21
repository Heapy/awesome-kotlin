package infra.ktor.easy

import infra.db.transaction.TransactionProvider
import infra.ktor.KtorRoute
import infra.ktor.auth.KtorAuth
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

class EasyKtorRouteAdapter(
    private val transactionProvider: TransactionProvider,
    private val ktorAuth: KtorAuth,
    private val route: EasyKtorRoute
) : KtorRoute {
    override fun Route.install() {
        route(
            path = route.path,
            method = route.method,
        ) {
            handle {
                transactionProvider.transaction(route.transactionType) {
                    ktorAuth.auth(route.accessControl) {
                        route.run {
                            handle()
                        }
                    }
                }
            }
        }
    }
}
