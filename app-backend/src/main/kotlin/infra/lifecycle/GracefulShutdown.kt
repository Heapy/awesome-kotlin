package infra.lifecycle

import kotlinx.coroutines.CompletableDeferred

class GracefulShutdown {
    private val deferred = CompletableDeferred<Unit>()

    fun shutdown() {
        deferred.complete(Unit)
    }

    suspend fun waitForShutdown() {
        deferred.await()
    }
}
