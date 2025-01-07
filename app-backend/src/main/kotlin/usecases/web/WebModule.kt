package usecases.web

import infra.config.ConfigModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module

@Module
open class WebModule(
    private val configModule: ConfigModule,
) {
    open val internalWebApplicationContext: WebApplicationContext by lazy {
        configModule.decode("web.internal", WebApplicationContext.serializer())
    }

    open val externalWebApplicationContext: WebApplicationContext by lazy {
        configModule.decode("web.external", WebApplicationContext.serializer())
    }

    open val deploymentContext by lazy {
        DeploymentContext(
            internalContext = internalWebApplicationContext,
            externalContext = externalWebApplicationContext,
        )
    }
}
