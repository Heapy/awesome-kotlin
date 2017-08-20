package link.kotlin.scripts.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.nio.file.Files.write
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING

class Cache(
    private val mapper: ObjectMapper
) {
    private val cache = mutableMapOf<String, Any>()
    private val cachePath = Paths.get("./dist/cache.json")


    fun load(data: InputStream) {
        cache += mapper.readValue<Map<String, Any>>(data)
    }

    fun save() {
        val json = mapper.writeValueAsBytes(cache)
        write(cachePath, json, CREATE, TRUNCATE_EXISTING)
    }

    fun put(key: String, data: Any) {
        cache.put(key, data)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T {
        return cache[key] as T? ?: default
    }
}
