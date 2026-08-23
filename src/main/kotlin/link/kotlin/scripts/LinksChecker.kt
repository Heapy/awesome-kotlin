package link.kotlin.scripts

import io.heapy.komok.tech.logging.Logger
import link.kotlin.scripts.utils.HttpClient
import org.apache.http.client.methods.HttpHead

interface LinksChecker {
    suspend fun check(url: String)
}

internal class NoopLinksChecker : LinksChecker {
    override suspend fun check(url: String) {
        log.info("Skipping link check for $url")
    }

    companion object : Logger()
}

internal class DefaultLinksChecker(
    private val httpClient: HttpClient,
) : LinksChecker {
    override suspend fun check(url: String) {
        try {
            val response = httpClient.execute(HttpHead(url))

            if (response.statusLine.statusCode != 200) {
                log.error("[$url]: Response code: ${response.statusLine.statusCode}.")
            }
        } catch (e: Exception) {
            log.error("Error ({}) checking link [$url].", e.message)
        }
    }

    companion object : Logger()
}
