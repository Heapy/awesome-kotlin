package infra.cache

import kotlin.time.Duration

interface CacheBuilder<K, V> {
    fun expireAfterWrite(duration: Duration)
    fun timeout(duration: Duration)
    fun loader(body: suspend (K) -> V)
    fun build(): LoadingCache<K, V>
}
