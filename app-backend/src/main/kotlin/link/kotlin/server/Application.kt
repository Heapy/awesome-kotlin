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
import io.github.config4k.extract
import io.ktor.auth.authenticate
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import link.kotlin.server.lifecycle.JvmShutdownManager
import link.kotlin.server.lifecycle.ShutdownManager
import link.kotlin.server.plugins.JwtConfiguration
import link.kotlin.server.plugins.defaults
import link.kotlin.server.routes.DefaultKotlinerDao
import link.kotlin.server.routes.KotlinerDao
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
import kotlin.time.Duration

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

    open val ktorServer by lazy {
        embeddedServer(
            factory = CIO,
            port = serverConfig.port,
            host = serverConfig.host
        ) {
            defaults(jwtConfig)

            routing {
                ping()
                login(jwtConfig, bcryptVerifier, kotlinerDao)
                register(bcryptHasher, kotlinerDao)

                authenticate("jwt") {

                }
            }
            configureSockets()
            configureMonitoring()
        }
    }

    open fun run() {
        shutdownHandler.registerHook()
        flyway.migrate()
        ktorServer.start(wait = true)
        logger.info("Server started in {}", Duration.milliseconds(runtimeMXBean.uptime))
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
        config.extract("server")
    }

    open val jwtConfig: JwtConfiguration by lazy {
        config.extract("jwt")
    }

    open val config: Config by lazy {
        ConfigFactory.load()
    }
}

data class ServerConfig(
    val port: Int,
    val host: String,
)
