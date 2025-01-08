package usecases.reload

import io.heapy.komok.tech.di.lib.Module

@Module
open class NotificationChannelModule {
    open val notificationChannel by lazy {
        NotificationChannel()
    }
}
