package usecases.reload

import infra.file_watcher.FileMonitorModule
import io.heapy.komok.tech.di.lib.Module
import server.KtorServerModule

@Module
open class LiveReloadModule(
    private val fileMonitorModule: FileMonitorModule,
    private val notificationChannelModule: NotificationChannelModule,
    private val serverModule: KtorServerModule,
) {
    open val liveReloadService by lazy {
        LiveReloadService(
            fileMonitor = fileMonitorModule.fileMonitor,
            notificationChannel = notificationChannelModule.notificationChannel,
            serverConfig = serverModule.serverConfig,
        )
    }
}
