package infra.coroutines

import io.heapy.komok.tech.di.lib.Module
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {
    val ioDispatcher by lazy {
        Dispatchers.Loom
    }

    val defaultDispatcher by lazy {
        Dispatchers.Default
    }
}
