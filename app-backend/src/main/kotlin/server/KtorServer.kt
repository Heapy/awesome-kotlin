package server

import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.EmbeddedServer

typealias KtorServer = EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>
