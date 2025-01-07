package infra.file_watcher

import infra.coroutines.DispatchersModule
import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module

@Module
open class FileMonitorModule(
    private val lifecycleModule: LifecycleModule,
    private val dispatchersModule: DispatchersModule,
) {
    open val fileMonitor by lazy {
        FileMonitor(
            applicationScope = lifecycleModule.applicationScope,
            ioDispatcher = dispatchersModule.ioDispatcher,
        )
    }
}
