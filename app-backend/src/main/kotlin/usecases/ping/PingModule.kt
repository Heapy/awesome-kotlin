package usecases.ping

import di.bean

open class PingModule {
    open val route by bean {
        PingRoute()
    }
}
