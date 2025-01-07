package infra.coroutines

import io.heapy.komok.tech.di.lib.Module
import kotlinx.coroutines.Dispatchers

@Module
open class DispatchersModule {
    open val ioDispatcher by lazy {
        Dispatchers.Loom
    }

    open val defaultDispatcher by lazy {
        Dispatchers.Default
    }
}
