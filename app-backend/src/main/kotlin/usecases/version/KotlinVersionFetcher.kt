package usecases.version

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import nl.adaptivity.xmlutil.serialization.XML
import infra.cache.buildCache
import io.heapy.komok.tech.logging.Logger
import usecases.version.MavenModel.MavenMetadata
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * Fetch latest kotlin versions from maven central.
 *
 * @author Ibragimov Ruslan
 */
interface KotlinVersionFetcher {
    suspend fun getLatestVersions(branches: List<String>): List<String>
}

class CachedVersionFetcher(
    private val kotlinVersionFetcher: KotlinVersionFetcher,
) : KotlinVersionFetcher {
    private val cache = buildCache {
        timeout(30.seconds)
        expireAfterWrite(10.minutes)
        loader { branches ->
            kotlinVersionFetcher.getLatestVersions(branches)
        }
    }

    override suspend fun getLatestVersions(
        branches: List<String>,
    ): List<String> {
        return cache.get(branches)
    }
}

class MavenCentralKotlinVersionFetcher(
    private val xml: XML,
    private val httpClient: HttpClient,
) : KotlinVersionFetcher {
    override suspend fun getLatestVersions(
        branches: List<String>,
    ): List<String> {
        log.info("Fetching latest kotlin versions from maven central")

        val url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"

        val response = httpClient.get(url).bodyAsText()

        val metadata = xml.decodeFromString(MavenMetadata.serializer(), response)
        val versions = metadata.versioning.versions

        return branches.map {
            findMax(versions.version, it)
        }
    }

    private fun findMax(
        versions: List<String>,
        version: String,
    ): String {
        return versions
            .filter { it.matches(versionRegex) }
            .filter { it.startsWith(version) }
            .maxOrNull() ?: ""
    }

    private companion object : Logger() {
        private val versionRegex = Regex("^\\d+.\\d+.\\d+$")
    }
}


