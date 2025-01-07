package usecases.reload

import infra.uuid.UuidModule
import io.heapy.komok.tech.di.lib.Module

@Module
open class NotificationChannelModule(
    private val uuidModule: UuidModule,
) {
    open val notificationChannel by lazy {
        NotificationChannel(
            uuidSource = uuidModule.uuidSource,
        )
    }
}
