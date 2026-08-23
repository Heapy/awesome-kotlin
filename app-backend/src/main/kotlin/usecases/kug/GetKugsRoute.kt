package usecases.kug

import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import ktor.KtorRoute

class GetKugsRoute(
    private val kugDao: KugDao,
) : KtorRoute {
    override fun Routing.install() {
        get("/kugs") {
            call.respond(kugDao.getAll())
        }
    }
}
