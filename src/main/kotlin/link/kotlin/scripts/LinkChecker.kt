package link.kotlin.scripts

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import link.kotlin.scripts.model.Link
import link.kotlin.scripts.utils.await
import link.kotlin.scripts.utils.logger
import link.kotlin.scripts.utils.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class LinkChecker(
    private val categories: List<Category>
) {
    suspend fun check() {
        val responses = flattenRequests(categories, okHttpClient)

        responses.forEach { response ->
            try {
                response.second.await().let {
                    val code = it.code()

                    if (code != 200) {
                        LOGGER.error("${response.first.href}: Response code: $code.")
                    }
                }
            } catch (e: Exception) {
                LOGGER.error("Error checking link.", e)
            }
        }
    }

    private suspend fun flattenRequests(
        categories: List<Category>,
        client: OkHttpClient
    ): List<Pair<Link, Deferred<Response>>> {
        return categories.map { category ->
            category.subcategories.map { subcategory ->
                subcategory.links.map { link ->
                    link to GlobalScope.async { get(link.href, client) }
                }
            }.flatten()
        }.flatten()
    }

    private suspend fun get(url: String, client: OkHttpClient): Response {
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).await()
    }

    companion object {
        private val LOGGER = logger<LinkChecker>()
    }
}
