package infra.file_watcher

import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.WatchKey
import java.nio.file.WatchService

class FileMonitor {
    private val job = SupervisorJob()

    fun monitor(folder: Path): Channel<String> {
        val watchService: WatchService = FileSystems.getDefault().newWatchService()

        // Register the folder with the watch service for the events we want to monitor
        folder.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)

        val channel = Channel<String>()

        CoroutineScope(job).launch(Dispatchers.IO) {
            while (true) {
                val key: WatchKey = watchService.take() // Blocks until events are available

                log.info("Received watch key: $key")

                for (event in key.pollEvents()) {
                    log.info("Received watch event: $event")

                    val kind = event.kind()

                    channel.send(kind.name())
                }

                val valid = key.reset()
                if (!valid) {
                    log.error("Watch key is no longer valid")
                    break
                }
            }
        }

        return channel
    }

    private companion object : Logger()
}
