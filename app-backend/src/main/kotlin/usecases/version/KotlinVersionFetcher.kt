package usecases.version

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import nl.adaptivity.xmlutil.serialization.XML
import io.heapy.komok.tech.logging.Logger
import usecases.version.MavenModel.MavenMetadata

/**
 * Fetch latest kotlin versions from maven central.
 *
 * @author Ibragimov Ruslan
 */
interface KotlinVersionFetcher {
    suspend fun getLatestVersions(): List<String>
}

class MavenCentralKotlinVersionFetcher(
    private val xml: XML,
    private val httpClient: HttpClient,
) : KotlinVersionFetcher {
    override suspend fun getLatestVersions(): List<String> {
        log.info("Fetching latest kotlin versions from maven central")

        val url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"

        val response = httpClient.get(url).bodyAsText()

        val metadata = xml.decodeFromString(MavenMetadata.serializer(), response)

        return metadata.versioning.versions.version
    }

    private companion object : Logger()
}


