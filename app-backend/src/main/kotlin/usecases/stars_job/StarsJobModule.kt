package usecases.stars_job

import infra.lifecycle.LifecycleModule
import io.heapy.komok.tech.di.lib.Module

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
