import infra.lifecycle.AutoClosableModule
import io.heapy.komok.tech.di.lib.Module
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
class HttpClientModule(
    private val autoClosableModule: AutoClosableModule,
) {
    val httpClient by lazy {
        val httpClient = HttpClient(engineFactory = CIO) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        autoClosableModule.addClosable(
            t = httpClient,
            close = HttpClient::close,
        )
    }
}
