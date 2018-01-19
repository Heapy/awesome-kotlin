package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import link.kotlin.scripts.utils.await
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Fetch latest kotlin versions from maven central.
 *
 * @author Ibragimov Ruslan
 */
interface VersionFetcher {
    suspend fun getLatestVersions(branches: List<String>): List<String>
}

class MavenCentralVersionFetcher(
    private val client: OkHttpClient
) : VersionFetcher {
    override suspend fun getLatestVersions(branches: List<String>): List<String> {
        val url = "http://central.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"

        val request = Request.Builder().get().url(url).build()
        val xml = client.newCall(request).await().body()?.string()

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
