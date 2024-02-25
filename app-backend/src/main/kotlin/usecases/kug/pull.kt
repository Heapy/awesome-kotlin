package usecases.kug

import com.charleskorn.kaml.Yaml
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class KugDownloadService(
    private val yaml: Yaml,
    private val httpClient: HttpClient,
) {
    suspend fun pull(): List<KugSection> {
        val data = download()
        return yaml.decodeFromString(ListSerializer(KugSection.serializer()), data)
    }

    internal suspend fun download(): String {
        return httpClient
            .get("https://raw.githubusercontent.com/JetBrains/kotlin-web-site/master/data/user-groups.yml")
            .bodyAsText()
    }

    @Serializable
    data class KugSection(
        val section: String,
        val anchorId: String,
        val groups: List<Kug>,
    )

    @Serializable
    data class Kug(
        val name: String,
        val country: String,
        val url: String,
        val isVirtual: Boolean = false,
        val position: Position? = null,
    )

    @Serializable
    data class Position(
        val lat: Double,
        val lng: Double,
    )
}
