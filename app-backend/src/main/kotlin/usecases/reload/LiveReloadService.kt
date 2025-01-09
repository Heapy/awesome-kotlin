package usecases.reload

import infra.file_watcher.FileMonitor
import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import server.ServerConfig
import java.nio.file.Path

class LiveReloadService(
    private val fileMonitor: FileMonitor,
    private val notificationChannel: NotificationChannel,
    private val serverConfig: ServerConfig,
) {
    private val job = SupervisorJob()

    fun start() {
        val fileChanges = fileMonitor.monitor(Path.of(serverConfig.reactDistPath))

        CoroutineScope(job).launch {
            for (change in fileChanges) {
                log.info("File change detected: $change")
                notificationChannel.notifyAll("reload")
            }
        }
    }

    private companion object : Logger()
}
