package link.kotlin.scripts.utils

import com.fasterxml.jackson.databind.ObjectMapper
import link.kotlin.scripts.model.ApplicationConfiguration
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.reflect.KClass

interface Cache {
    fun <T> put(key: String, value: T)
    fun <T : Any> get(key: String, type: KClass<T>): T?

    companion object
}

fun Cache.Companion.cacheKey(data: String): String {
    val magnitude = MessageDigest.getInstance("MD5").digest(data.toByteArray())
    return "%032x".format(BigInteger(1, magnitude))
}

class FileCache(
    private val folder: Path,
    private val mapper: ObjectMapper
) : Cache {
    override fun <T> put(key: String, value: T) {
        val data = mapper.writeValueAsString(value)
        writeFile(folder.resolve(key), data)
    }

    override fun <T : Any> get(key: String, type: KClass<T>): T? {
        val path = folder.resolve(key)
        return if (Files.exists(path)) {
            try {
                val data = Files.readAllBytes(path)
                mapper.readValue(data, type.java)
            } catch (e: Exception) {
                LOGGER.info("Removing invalid cache entry [$path].")
                Files.delete(path)
                null
            }
        } else null
    }

    companion object {
        private val LOGGER = logger<FileCache>()
    }
}

private class DisableCache(
    private val cache: Cache,
    private val configuration: ApplicationConfiguration
) : Cache {
    override fun <T> put(key: String, value: T) {
        if (configuration.cacheEnabled) {
            cache.put(key, value)
        }
    }

    override fun <T : Any> get(key: String, type: KClass<T>): T? {
        return if (configuration.cacheEnabled) {
            cache.get(key, type)
        } else null
    }

}

fun Cache.Companion.default(
    folder: Path = Paths.get(System.getProperty("user.home"), ".cache", "awesome-kotlin"),
    mapper: ObjectMapper,
    configuration: ApplicationConfiguration
): Cache {
    Files.createDirectories(folder)

    val fileCache = FileCache(
        folder = folder,
        mapper = mapper
    )

    return DisableCache(
        cache = fileCache,
        configuration = configuration
    )
}
