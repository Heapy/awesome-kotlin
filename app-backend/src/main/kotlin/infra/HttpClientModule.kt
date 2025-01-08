package infra

import io.heapy.komok.tech.di.lib.Module
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import infra.lifecycle.AutoClosableModule

@Module
open class HttpClientModule(
    private val autoClosableModule: AutoClosableModule,
) {
    open val httpClient by lazy {
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

        httpClient
    }
}
