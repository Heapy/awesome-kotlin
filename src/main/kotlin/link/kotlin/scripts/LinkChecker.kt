package link.kotlin.scripts

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class LinkChecker(private val categories: List<Category>) {
    fun check(): Boolean {
        return okhttp { client ->
            val futures = mapToFuture(categories, client)

            CompletableFuture.allOf(*futures).handle { _, exception ->
                if (exception != null) {
                    val cause = exception.cause
                    if (cause is OkHttpException) {
                        logger.error("Requests to {}, completed exceptionally!", cause.request.url(), cause)
                    } else {
                        logger.error("One of request, completed exceptionally!", exception)
                    }
                    client.dispatcher().cancelAll()
                    false
                } else {
                    val unsuccessful = futures.map { it.get() }.filter { !it.isSuccessful }

                    if (unsuccessful.isNotEmpty()) {
                        unsuccessful.forEach {
                            logger.error("Link {} return status {}", it.request().url(), it.code())
                        }
                        false
                    } else {
                        logger.info("All links are good.")
                        true
                    }
                }
            }.get()
        }
    }

    private fun mapToFuture(categories: List<Category>,
                            client: OkHttpClient): Array<CompletableFuture<Response>> {
        return categories.map { category ->
            category.subcategories.map { subcategory ->
                subcategory.links.map { link ->
                    get(link.href, client)
                }
            }.flatten()
        }.flatten().toTypedArray()
    }

    private fun get(url: String, client: OkHttpClient): CompletableFuture<Response> {
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).async()
    }

    companion object {
        private val logger = LoggerFactory.getLogger("LinkChecker")
    }
}
