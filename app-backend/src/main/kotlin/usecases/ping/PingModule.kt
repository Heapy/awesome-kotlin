package usecases.ping

import io.heapy.komok.tech.di.lib.Module

@Module
class PingModule {
    val route by lazy {
        PingRoute()
    }
}
