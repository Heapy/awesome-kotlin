package usecases.stars_job

import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class StarsJobScheduler(
    private val starsJob: StarsJob,
    private val applicationScope: CoroutineScope,
) {
    fun start() {
        applicationScope.launch {
            delay(5.seconds)
            while (isActive) {
                log.info("About to run stars job")
                starsJob.run()
                log.info("Stars job finished, sleeping for 24 hours")
                delay(24.hours)
            }
        }
    }

    private companion object : Logger()
}
