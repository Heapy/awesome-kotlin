package usecases.version

import infra.db.transaction.TransactionProvider
import io.heapy.komok.tech.logging.Logger
import usecases.notifications.NotificationService

class KotlinVersionsJob(
    private val kotlinVersionFetcher: KotlinVersionFetcher,
    private val kotlinVersionDao: KotlinVersionDao,
    private val transactionProvider: TransactionProvider,
    private val notificationService: NotificationService,
) {
    suspend fun run() {
        transactionProvider.transaction {
            val latestVersions = kotlinVersionFetcher.getLatestVersions()
            val newVersions = kotlinVersionDao.mergeVersions(latestVersions)
            newVersions.forEach { version ->
                notificationService.newKotlinVersionNotification(version)
            }
        }
    }

    private companion object : Logger()
}
