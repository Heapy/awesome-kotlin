package infra.file_watcher

import io.heapy.komok.tech.di.lib.Module

@Module
open class FileMonitorModule {
    open val fileMonitor by lazy {
        FileMonitor()
    }
}
