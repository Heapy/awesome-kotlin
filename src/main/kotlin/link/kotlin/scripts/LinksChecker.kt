package link.kotlin.scripts

import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.logger
import org.apache.http.client.methods.HttpHead

interface LinksChecker {
    suspend fun check(url: String)

    companion object
}

private class DefaultLinksChecker(
    private val httpClient: HttpClient
) : LinksChecker {
    override suspend fun check(url: String) {
        try {
            val response = httpClient.execute(HttpHead(url))

            if (response.statusLine.statusCode != 200) {
                LOGGER.error("[$url]: Response code: ${response.statusLine.statusCode}.")
            }
        } catch (e: Exception) {
            LOGGER.error("Error ({}) checking link [$url].", e.message)
        }
    }

    companion object {
        private val LOGGER = logger<DefaultLinksChecker>()
    }
}

fun LinksChecker.Companion.default(
    httpClient: HttpClient
): LinksChecker{
    return DefaultLinksChecker(
        httpClient = httpClient
    )
}
