package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import org.apache.http.client.methods.HttpGet

/**
 * Fetch latest kotlin versions from maven central.
 *
 * @author Ibragimov Ruslan
 */
interface KotlinVersionFetcher {
    suspend fun getLatestVersions(branches: List<String>): List<String>

    companion object
}

private class MavenCentralKotlinVersionFetcher(
    private val httpClient: HttpClient
) : KotlinVersionFetcher {
    override suspend fun getLatestVersions(branches: List<String>): List<String> {
        val url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"

        val xml = httpClient.execute(HttpGet(url)).body()

        val mapper = XmlMapper()
        mapper.registerModule(kotlinModule {})

        val metadata = mapper.readValue(xml, MavenMetadata::class.java)
        val versions = metadata.versioning.versions

        return branches.map { findMax(versions, it) }
    }

    private fun findMax(versions: List<String>, version: String): String {
        return versions
            .filter { it.matches(versionRegex) }
            .filter { it.startsWith(version) }.maxOrNull() ?: ""
    }

    private val versionRegex = Regex("^[0-9]+.[0-9]+.[0-9]+$")
}

fun KotlinVersionFetcher.Companion.default(
    httpClient: HttpClient
): KotlinVersionFetcher {
    return MavenCentralKotlinVersionFetcher(
        httpClient = httpClient
    )
}

@JsonIgnoreProperties("groupId", "artifactId")
data class MavenMetadata(
    val versioning: MavenVersioning
)

@JsonIgnoreProperties("latest", "release", "lastUpdated")
data class MavenVersioning(
    val versions: List<String>
)
