import di.bean
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import ktor.plugins.configureMonitoring
import ktor.plugins.configureSockets
import ktor.plugins.defaults
import usecases.github.GithubModule
import usecases.kug.KugModule
import usecases.links.LinksModule
import usecases.ping.PingModule
import usecases.signup.JwtModule
import usecases.signup.LoginModule
import usecases.signup.RegisterModule
import utils.withEach
import kotlin.time.Duration

open class ServerModule(
    private val githubModule: GithubModule,
    private val pingModule: PingModule,
    private val loginModule: LoginModule,
    private val registerModule: RegisterModule,
    private val linksModule: LinksModule,
    private val kugModule: KugModule,
    private val jwtModule: JwtModule,
    private val metricsModule: MetricsModule,
    private val lifecycleModule: LifecycleModule,
    private val configModule: ConfigModule,
) {
    open val unauthenticatedRoutes by bean {
        listOf(
            githubModule.githubRedirectRoute.get,
            githubModule.githubCallbackRoute.get,

            pingModule.route.get,

            loginModule.route.get,
            registerModule.route.get,

            linksModule.route.get,
            kugModule.getKugRoute.get,
            kugModule.updateKugsRoute.get,
        )
    }

    open val ktorServer by bean {
        System.setProperty("io.ktor.server.engine.ShutdownHook", "false")

        val unauthenticatedRoutes = unauthenticatedRoutes.get
        val jwtConfig = jwtModule.jwtConfig.get
        val serverConfig = serverConfig.get
        val meterRegistry = metricsModule.meterRegistry.get

        embeddedServer(
            factory = CIO,
            port = serverConfig.port,
            host = serverConfig.host,
        ) {
            defaults(jwtConfig)

            routing {
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
            configureSockets()
            configureMonitoring(meterRegistry)
        }.also { server ->
            lifecycleModule.shutdownHandler.get.addHandler {
                server.stop(
                    gracePeriodMillis = serverConfig.gracefulShutdownTimeout.inWholeMilliseconds,
                    timeoutMillis = 5000,
                )
            }
        }
    }

    open val serverConfig by bean<ServerConfig> {
        Hocon.decodeFromConfig(configModule.config.get.getConfig("server"))
    }

    @Serializable
    data class ServerConfig(
        val port: Int,
        val host: String,
        val gracefulShutdownTimeout: Duration,
    )
}
