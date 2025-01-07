package usecases.ping

import io.heapy.komok.tech.di.delegate.bean

open class PingModule {
    open val route by bean {
        PingRoute()
    }
}
