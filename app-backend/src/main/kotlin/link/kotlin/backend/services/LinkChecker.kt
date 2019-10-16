package link.kotlin.backend.services

import kotlinx.coroutines.withTimeoutOrNull
import link.kotlin.backend.logger
import org.apache.http.client.methods.HttpHead

interface LinkChecker {
    suspend fun check(link: String): LinkStatus
}

internal class DefaultLinkChecker(
    private val httpClient: HttpClient
) : LinkChecker {
    override suspend fun check(link: String): LinkStatus {
        return try {
            withTimeoutOrNull(10000) {
                httpClient.execute(HttpHead(link))
            }?.run {
                when (statusLine.statusCode) {
                    404 -> {
                        LOGGER.error("NOT FOUND: $link")
                        LinkStatus.NOT_FOUND
                    }
                    200 -> LinkStatus.OK
                    else -> {
                        LOGGER.error("${statusLine.statusCode}: $link")
                        LinkStatus.ERROR
                    }
                }
            } ?: LinkStatus.TIMEOUT
        } catch (e: Exception) {
            LOGGER.error("Error checking link $link.", e)
            LinkStatus.ERROR
        }
    }

    companion object {
        private val LOGGER = logger<LinkChecker>()
    }
}

enum class LinkStatus {
    OK,
    NOT_FOUND,
    TIMEOUT,
    ERROR
}
