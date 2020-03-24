package link.kotlin.scripts

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withTimeout
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.logger
import org.apache.http.client.methods.HttpHead

class LinkChecker(
    private val httpClient: HttpClient
) {
    suspend fun check(links: List<String>) {
        links.map { link ->
            GlobalScope.async {
                try {
                    val response = withTimeout(1000) {
                        httpClient.execute(HttpHead(link))
                    }

                    if (response.statusLine.statusCode != 200) {
                        LOGGER.error("$link: Response code: ${response.statusLine.statusCode}.")
                    }
                } catch (e: Exception) {
                    LOGGER.error("Error checking link $link.", e)
                }
            }
        }.awaitAll()
    }

    companion object {
        private val LOGGER = logger<LinkChecker>()
    }
}
