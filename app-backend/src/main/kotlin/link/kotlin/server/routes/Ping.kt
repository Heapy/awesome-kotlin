package link.kotlin.server.routes

import io.ktor.server.application.call
import io.ktor.server.locations.Location
import io.ktor.server.locations.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing

@Location("/ping/{name}")
class PingRequest(val name: String)

fun Routing.ping() {
    get<PingRequest> {
        call.respondText("Pong: ${it.name}")
    }
}
