package link.kotlin.scripts

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.nio.file.Files.write
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING

class Cache(private val mapper: ObjectMapper) {
    private var cache = mutableMapOf<String, Any>()

    fun load(data: InputStream) {
        cache = mapper.readValue(data)
    }

    fun save() {
        val json = mapper.writeValueAsBytes(cache)
        write(Paths.get("./dist/cache.json"), json, CREATE, TRUNCATE_EXISTING)
    }

    fun put(key: String, data: Any) {
        cache.put(key, data)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T {
        return cache[key] as T? ?: default
    }
}
