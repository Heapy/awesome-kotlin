package server

import infra.config.ConfigModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import infra.ktor.KtorFeaturesModule
import infra.lifecycle.LifecycleModule
import infra.utils.withEach
import java.io.File
import kotlin.time.Duration

@Module
open class KtorServerModule(
    private val lifecycleModule: LifecycleModule,
    private val configModule: ConfigModule,
    private val ktorFeaturesModule: KtorFeaturesModule,
    private val ktorRoutesModule: KtorRoutesModule,
) {
    open val ktorServer by lazy {
        System.setProperty("io.ktor.server.engine.ShutdownHook", "false")

        val features = ktorFeaturesModule.features
        val unauthenticatedRoutes = ktorRoutesModule.unauthenticatedRoutes
        val serverConfig = serverConfig

        embeddedServer(
            factory = CIO,
            port = serverConfig.port,
            host = serverConfig.host,
        ) {
            features.withEach {
                install()
            }

            routing {
                staticFiles("/", File(serverConfig.reactDistPath)) {
                    default("index.html")
                }

                unauthenticatedRoutes.withEach {
                    install()
                }

                authenticate("jwt") {
                    get("/test") {
                        val principal = call.principal<JWTPrincipal>()
                        principal?.getClaim("id", Long::class)?.let { id ->
                            call.respond("Hello, $id!")
                        } ?: call.respond("Hello, world!")
                    }
                }
            }
        }.also { server ->
            lifecycleModule.shutdownHandler.addHandler {
                server.stop(
                    gracePeriodMillis = serverConfig.gracefulShutdownTimeout.inWholeMilliseconds,
                    timeoutMillis = 5000,
                )
            }
        }
    }

    open val serverConfig: ServerConfig by lazy {
        configModule.decode(
            "server",
            deserializer = ServerConfig.serializer(),
        )
    }

    @Serializable
    data class ServerConfig(
        val port: Int,
        val host: String,
        val gracefulShutdownTimeout: Duration,
        val reactDistPath: String,
    )
}
