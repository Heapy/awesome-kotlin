import infra.config.ConfigModule
import infra.config.decode
import infra.utils.withEach
import io.heapy.komok.tech.di.lib.Module
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
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
import java.io.File
import kotlin.time.Duration

@Module
class ServerModule(
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
    val unauthenticatedRoutes by lazy {
        listOf(
            githubModule.githubRedirectRoute,
            githubModule.githubCallbackRoute,

            pingModule.route,

            loginModule.route,
            registerModule.route,

            linksModule.route,
            kugModule.getKugRoute,
            kugModule.updateKugsRoute,
        )
    }

    val ktorServer by lazy {
        System.setProperty("io.ktor.server.engine.ShutdownHook", "false")

        val jwtConfig = jwtModule.jwtConfig
        val meterRegistry = metricsModule.meterRegistry

        embeddedServer(
            factory = CIO,
            port = serverConfig.port,
            host = serverConfig.host,
        ) {
            defaults(jwtConfig)

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
            configureSockets()
            configureMonitoring(meterRegistry)
        }.also { server ->
            lifecycleModule.shutdownHandler.addHandler {
                server.stop(
                    gracePeriodMillis = serverConfig.gracefulShutdownTimeout.inWholeMilliseconds,
                    timeoutMillis = 5000,
                )
            }
        }
    }

    val serverConfig: ServerConfig by lazy {
        configModule.decode("server")
    }

    @Serializable
    data class ServerConfig(
        val port: Int,
        val host: String,
        val gracefulShutdownTimeout: Duration,
        val reactDistPath: String,
    )
}
