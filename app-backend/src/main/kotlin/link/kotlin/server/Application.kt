@file:JvmName("Application")

package link.kotlin.server

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies
import link.kotlin.server.plugins.configureMonitoring
import link.kotlin.server.plugins.configureSockets
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.auth.authenticate
import io.ktor.server.engine.embeddedServer
import io.ktor.server.routing.routing
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import kotlinx.serialization.json.Json
import link.kotlin.server.lifecycle.JvmShutdownManager
import link.kotlin.server.lifecycle.ShutdownManager
import link.kotlin.server.plugins.JwtConfiguration
import link.kotlin.server.plugins.defaults
import link.kotlin.server.dao.DefaultKotlinerDao
import link.kotlin.server.dao.DefaultKugDao
import link.kotlin.server.dao.KotlinerDao
import link.kotlin.server.dao.KugDao
import link.kotlin.server.routes.LinkSource
import link.kotlin.server.routes.kugs
import link.kotlin.server.routes.links
import link.kotlin.server.routes.login
import link.kotlin.server.routes.ping
import link.kotlin.server.routes.register
import link.kotlin.server.utils.logger
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import java.security.SecureRandom
import javax.sql.DataSource
import kotlin.time.Duration.Companion.milliseconds
import io.ktor.client.engine.cio.CIO as ClientCIO
import io.ktor.server.cio.CIO as ServerCIO

fun main() {
    ApplicationFactory().run()
}

open class ApplicationFactory {
    open val shutdownHandler: ShutdownManager by lazy {
        JvmShutdownManager(
            listOf(
                { ktorServer.stop(5000, 5000) },
            )
        )
    }

    open val kotlinerDao: KotlinerDao by lazy {
        DefaultKotlinerDao(dslContext)
    }

    open val kugDao: KugDao by lazy {
        DefaultKugDao(dslContext)
    }

    open val linkSource by lazy {
        LinkSource()
    }

    open val httpClient by lazy {
        HttpClient(ClientCIO) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    open val ktorServer by lazy {
        embeddedServer(
            factory = ServerCIO,
            port = serverConfig.port,
            host = serverConfig.host
        ) {
            defaults(jwtConfig)

            routing {
                ping()
                login(jwtConfig, bcryptVerifier, kotlinerDao)
                register(bcryptHasher, kotlinerDao)
                links(linkSource.get())
                kugs(httpClient, kugDao)

                authenticate("jwt") {

                }
            }
            configureSockets()
            configureMonitoring(meterRegistry)
        }
    }

    open val meterRegistry by lazy {
        PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }

    open fun run() {
        shutdownHandler.registerHook()
        flyway.migrate()
        ktorServer.start(wait = true)
        logger.info("Server started in {}", runtimeMXBean.uptime.milliseconds)
    }

    open val runtimeMXBean: RuntimeMXBean by lazy {
        ManagementFactory.getRuntimeMXBean()
    }

    open val logger: Logger by lazy {
        logger<ApplicationFactory>()
    }

    open val dataSource: DataSource by lazy {
        HikariDataSource(
            HikariConfig().also {
                it.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
                it.username = "awesome_kotlin"
                it.password = "awesome_kotlin"
                it.addDataSourceProperty("databaseName", "awesome_kotlin")
                it.addDataSourceProperty("portNumber", "9567")
                it.addDataSourceProperty("serverName", "localhost")
            }
        )
    }

    open val dslContext: DSLContext by lazy {
        System.setProperty("org.jooq.no-logo", "true")
        System.setProperty("org.jooq.no-tips", "true")
        DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    open val bcryptHasher: BCrypt.Hasher by lazy {
        BCrypt.with(
            BCrypt.Version.VERSION_2A,
            SecureRandom(),
            LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)
        )
    }

    open val bcryptVerifier: BCrypt.Verifyer by lazy {
        BCrypt.verifyer()
    }

    open val flyway: Flyway by lazy {
        Flyway.configure()
            .locations("classpath:db/migration/main")
            .dataSource(dataSource)
            .load()
    }

    open val serverConfig: ServerConfig by lazy {
        Hocon.decodeFromConfig(config.getConfig("server"))
    }

    open val jwtConfig: JwtConfiguration by lazy {
        Hocon.decodeFromConfig(config.getConfig("jwt"))
    }

    open val config: Config by lazy {
        ConfigFactory.load()
    }
}

@Serializable
data class ServerConfig(
    val port: Int,
    val host: String,
)
