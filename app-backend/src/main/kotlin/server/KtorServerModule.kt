package server

import infra.config.ConfigModule
import infra.config.decode
import infra.ktor.KtorFeaturesModule
import infra.lifecycle.LifecycleModule
import infra.utils.withEach
import io.heapy.komok.tech.di.lib.Module
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.routing
import java.io.File

@Module
open class KtorServerModule(
    private val lifecycleModule: LifecycleModule,
    private val configModule: ConfigModule,
    private val ktorFeaturesModule: KtorFeaturesModule,
    private val ktorRoutesModule: KtorRoutesModule,
) {
    open val ktorServer by lazy {
        System.setProperty("io.ktor.server.engine.ShutdownHook", "false")

        embeddedServer(
            factory = CIO,
            port = serverConfig.port,
            host = serverConfig.host,
        ) {
            ktorFeaturesModule.features.withEach {
                install()
            }

            routing {
                staticFiles("/", File(serverConfig.reactDistPath)) {
                    default("index.html")
                }

                ktorRoutesModule.routes.withEach {
                    install()
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
}
