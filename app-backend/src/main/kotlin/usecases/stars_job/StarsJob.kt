package usecases.stars_job

import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import infra.lifecycle.LifecycleModule
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class StarsJob {
    fun run() {
        log.info("Hello, StarsJob!")
    }

    private companion object : Logger()
}

class StarsJobScheduler(
    private val starsJob: StarsJob,
    private val applicationScope: CoroutineScope,
) {
    fun start() {
        applicationScope.launch {
            delay(5.seconds)
            while (true) {
                log.info("About to run stars job")
                starsJob.run()
                log.info("Stars job finished, sleeping for 24 hours")
                delay(24.hours)
            }
        }
    }

    private companion object : Logger()
}

@Module
open class StarsJobModule(
    private val lifecycleModule: LifecycleModule,
) {
    open val starsJob by lazy {
        StarsJob()
    }

    open val starsJobScheduler by lazy {
        StarsJobScheduler(
            starsJob = starsJob,
            applicationScope = lifecycleModule.applicationScope,
        )
    }
}
