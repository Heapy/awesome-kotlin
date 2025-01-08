package usecases.healthcheck

import io.heapy.komok.tech.di.lib.Module

@Module
open class HealthcheckModule {
    open val route by lazy {
        HealthCheckRoute()
    }
}
