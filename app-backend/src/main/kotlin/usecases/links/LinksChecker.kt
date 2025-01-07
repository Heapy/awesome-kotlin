package usecases.links

import io.heapy.komok.tech.logging.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.head
import io.ktor.http.HttpStatusCode

class LinksChecker(
    private val httpClient: HttpClient,
) {
    suspend fun check(url: String) {
        try {

            val response = httpClient.head(url)

            if (response.status != HttpStatusCode.OK) {
                log.error("[$url]: Response code: ${response.status}.")
            }
        } catch (e: Exception) {
            log.error("Error ({}) checking link [$url].", e.message)
        }
    }

    private companion object : Logger()
}
