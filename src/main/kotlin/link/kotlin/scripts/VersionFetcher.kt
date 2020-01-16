package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import org.apache.http.client.methods.HttpGet

/**
 * Fetch latest kotlin versions from maven central.
 *
 * @author Ibragimov Ruslan
 */
interface VersionFetcher {
    suspend fun getLatestVersions(branches: List<String>): List<String>
}

class MavenCentralVersionFetcher(
    private val client: HttpClient
) : VersionFetcher {
    override suspend fun getLatestVersions(branches: List<String>): List<String> {
        val url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"

        val xml = client.execute(HttpGet(url)).body()

        val mapper = XmlMapper()
        mapper.registerModule(KotlinModule())

        val metadata = mapper.readValue(xml, MavenMetadata::class.java)
        val versions = metadata.versioning.versions

        return branches.map { findMax(versions, it) }
    }

    fun findMax(versions: List<String>, version: String): String {
        return versions.filter { it.startsWith(version) }.max() ?: ""
    }
}

@JsonIgnoreProperties("groupId", "artifactId")
data class MavenMetadata(
    val versioning: MavenVersioning
)

@JsonIgnoreProperties("latest", "release", "lastUpdated")
data class MavenVersioning(
    val versions: List<String>
)
