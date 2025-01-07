package usecases.version

import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class KotlinVersionsJobScheduler(
    private val kotlinVersionsJob: KotlinVersionsJob,
    private val applicationScope: CoroutineScope,
) {
    fun start() {
        applicationScope.launch {
            delay(5.seconds)
            while (isActive) {
                log.info("About to run kotlin version job")
                try {
                    kotlinVersionsJob.run()
                    log.info("Kotlin version job finished, sleeping for 1 hour")
                } catch (e: Exception) {
                    log.error("Error during KotlinVersionJob", e)
                }
                delay(1.hours)
            }
        }
    }

    private companion object : Logger()
}
