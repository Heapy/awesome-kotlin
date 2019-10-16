//package link.kotlin.backend.services
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties
//import com.fasterxml.jackson.dataformat.xml.XmlMapper
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.withContext
//import org.apache.http.client.methods.HttpGet
//
///**
// * Fetch latest kotlin versions from maven central.
// *
// * @author Ibragimov Ruslan
// */
//interface VersionFetcher {
//    suspend fun getLatestVersions(branches: List<KotlinVersion>): List<String>
//}
//
//internal class MavenCentralVersionFetcher(
//    private val client: HttpClient,
//    private val xmlMapper: XmlMapper
//) : VersionFetcher {
//    override suspend fun getLatestVersions(branches: List<KotlinVersion>): List<String> {
//        val url = "http://central.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"
//
//        val xml = client.execute(HttpGet(url)).body()
//
//        val metadata = withContext(IO) {
//            xmlMapper.readValue(xml, MavenMetadata::class.java)
//        }
//
//        val versions = metadata.versioning.versions
//
//        return branches.map { findMax(versions, it) }
//    }
//}
//
//fun findMax(versions: List<String>, version: KotlinVersion): String {
//    return versions.filter { it.startsWith(version.value) }.max() ?: ""
//}
//
//@JsonIgnoreProperties("groupId", "artifactId")
//data class MavenMetadata(
//    val versioning: MavenVersioning
//)
//
//@JsonIgnoreProperties("latest", "release", "lastUpdated")
//data class MavenVersioning(
//    val versions: List<String>
//)
