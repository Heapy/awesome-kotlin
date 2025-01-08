package infra.cache

import kotlin.time.Duration

interface LoadingCache<K, V> {
    suspend fun get(key: K): V
}

fun <K, V> buildCache(
    builder: CacheBuilder<K, V>.() -> Unit,
): LoadingCache<K, V> {
    val cacheBuilder = object : CacheBuilder<K, V> {
        private var expireAfterWrite: Duration? = null
        private var timeout: Duration? = null
        private var loader: (suspend (K) -> V)? = null

        override fun expireAfterWrite(duration: Duration) {
            expireAfterWrite = duration
        }

        override fun timeout(duration: Duration) {
            timeout = duration
        }

        override fun loader(body: suspend (K) -> V) {
            loader = body
        }

        override fun build(): LoadingCache<K, V> {
            val loader = loader ?: error("loader is not set")
            val expireAfterWrite = expireAfterWrite ?: error("expireAfterWrite is not set")
            val timeout = timeout ?: error("timeout is not set")

            return SimpleLoadingCache(
                loader = loader,
                expireAfterWrite = expireAfterWrite,
                timeout = timeout,
            )
        }
    }

    return cacheBuilder.apply(builder).build()
}

