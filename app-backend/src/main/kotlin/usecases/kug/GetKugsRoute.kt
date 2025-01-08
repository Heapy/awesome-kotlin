package usecases.kug

import io.ktor.server.response.*
import io.ktor.server.routing.*
import infra.ktor.KtorRoute

class GetKugsRoute(
    private val kugDao: KugDao,
) : KtorRoute {
    override fun Route.install() {
        get("/kugs") {
            call.respond(kugDao.getAll())
        }
    }
}
