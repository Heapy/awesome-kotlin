package usecases.kug

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
