package usecases.ping

import io.ktor.resources.Resource
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import ktor.KtorRoute

@Resource("/ping/{name}")
class PingRequest(val name: String)

class PingRoute : KtorRoute {
    override fun Routing.install() {
        get<PingRequest> {
            call.respondText("Pong: ${it.name}")
        }
    }
}

