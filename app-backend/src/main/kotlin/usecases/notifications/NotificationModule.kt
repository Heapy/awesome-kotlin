package usecases.notifications

import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.logging.Logger
import usecases.version.KotlinVersionDao

@Module
open class NotificationModule {
    open val notificationService by lazy {
        NotificationService()
    }
}

class NotificationService {
    fun newKotlinVersionNotification(
        kotlinVersion: KotlinVersionDao.KotlinVersion,
    ) {
        log.info("New Kotlin version $kotlinVersion")
    }

    private companion object : Logger()
}
