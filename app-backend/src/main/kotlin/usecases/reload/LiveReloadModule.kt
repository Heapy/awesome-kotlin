package usecases.reload

import infra.file_watcher.FileMonitorModule
import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module
import server.KtorServerModule

@Module
open class LiveReloadModule(
    private val fileMonitorModule: FileMonitorModule,
    private val notificationChannelModule: NotificationChannelModule,
    private val serverModule: KtorServerModule,
    private val lifecycleModule: LifecycleModule,
) {
    open val liveReloadService by lazy {
        LiveReloadService(
            fileMonitor = fileMonitorModule.fileMonitor,
            notificationChannel = notificationChannelModule.notificationChannel,
            serverConfig = serverModule.serverConfig,
            applicationScope = lifecycleModule.applicationScope,
        )
    }
}
