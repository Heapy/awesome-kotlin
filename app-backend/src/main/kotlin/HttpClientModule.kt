import di.bean
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utils.close

open class HttpClientModule : AutoCloseable {
    open val httpClient by bean {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    override fun close() {
        if (httpClient.isInitialized) httpClient.get.close {}
    }
}
