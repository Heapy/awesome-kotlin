package link.kotlin.server.routes

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.Routing

@Location("/ping/{name}")
class PingRequest(val name: String)

fun Routing.ping() {
    get<PingRequest> {
        call.respondText("Pong: ${it.name}")
    }
}
