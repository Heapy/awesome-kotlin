@file:JvmName("Application")

import di.bean
import usecases.github.GithubModule
import usecases.kug.KugModule
import usecases.links.LinksModule
import usecases.ping.PingModule
import usecases.signup.JwtModule
import usecases.signup.LoginModule
import usecases.signup.RegisterModule
import utils.close
import utils.logger
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() {
    ApplicationFactory().use {
        it.run()
    }
}

open class ApplicationFactory : AutoCloseable {
    open val httpClientModule by lazy {
        HttpClientModule()
    }

    open val yamlModule by lazy {
        YamlModule()
    }

    open val configModule by lazy {
        ConfigModule()
    }

    open val jdbcModule by lazy {
        JdbcModule(
            configModule = configModule,
        )
    }

    open val lifecycleModule by lazy {
        LifecycleModule()
    }

    open val jwtModule by lazy {
        JwtModule(
            configModule = configModule,
        )
    }

    open val jooqModule by lazy {
        JooqModule(
            jdbcModule = jdbcModule,
        )
    }

    open val flywayModule by lazy {
        FlywayModule(
            jdbcModule = jdbcModule,
        )
    }

    open val loginModule by lazy {
        LoginModule(
            jooqModule = jooqModule,
            jwtModule = jwtModule,
        )
    }

    open val registerModule by lazy {
        RegisterModule(
            jooqModule = jooqModule,
        )
    }

    open val kugModule by lazy {
        KugModule(
            httpClientModule = httpClientModule,
            yamlModule = yamlModule,
            jooqModule = jooqModule,
        )
    }

    open val githubModule by lazy {
        GithubModule(
            configModule = configModule,
            httpClientModule = httpClientModule,
        )
    }

    open val pingModule by lazy {
        PingModule()
    }

    open val linksModule by lazy {
        LinksModule()
    }

    open val metricsModule by lazy {
        MetricsModule()
    }

    open val serverModule by lazy {
        ServerModule(
            githubModule = githubModule,
            pingModule = pingModule,
            loginModule = loginModule,
            registerModule = registerModule,
            linksModule = linksModule,
            kugModule = kugModule,
            jwtModule = jwtModule,
            metricsModule = metricsModule,
            lifecycleModule = lifecycleModule,
            configModule = configModule,
        )
    }

    open suspend fun run() {
        val gracefulShutdown = lifecycleModule.gracefulShutdown.get
        lifecycleModule.shutdownHandler.get.registerHook()
        flywayModule.flyway.get.migrate()
        serverModule.ktorServer.get.start(wait = false)
        log.get.info("Server started in {}", runtimeMXBean.get.uptime.milliseconds)
        gracefulShutdown.waitForShutdown()
    }

    open val runtimeMXBean by bean<RuntimeMXBean> {
        ManagementFactory.getRuntimeMXBean()
    }

    open val log by bean {
        logger<ApplicationFactory>()
    }

    override fun close() {
        jdbcModule.close {}
        httpClientModule.close {}
    }
}
