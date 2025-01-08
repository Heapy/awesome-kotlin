package infra.cache

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

internal class SimpleLoadingCache<K, V>(
    private val loader: suspend (K) -> V,
    private val expireAfterWrite: Duration = 5.minutes,
    private val timeout: Duration = 1.minutes,
) : LoadingCache<K, V> {
    private data class CacheEntry<V>(
        val value: Deferred<V>,
        val expirationTime: Long,
    )

    private val store = mutableMapOf<K, CacheEntry<V>>()
    private val mutex = Mutex()
    private val job = SupervisorJob()

    private fun instant(): Long {
        return Instant.now().epochSecond
    }

    override suspend fun get(key: K): V {
        val deferred = mutex.withLock {
            val entry = store[key]?.takeIf { it.expirationTime > instant() }

            if (entry == null) {
                val newDeferred = CoroutineScope(job).async {
                    withTimeout(timeout) {
                        loader(key)
                    }
                }
                store[key] = CacheEntry(
                    value = newDeferred,
                    expirationTime = instant() + expireAfterWrite.inWholeSeconds,
                )
                newDeferred
            } else {
                entry.value
            }
        }

        return deferred.await()
    }
}
