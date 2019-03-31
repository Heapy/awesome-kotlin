package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.utils.await
import link.kotlin.scripts.utils.logger
import link.kotlin.scripts.utils.parseInstant
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class DefaultStarsGenerator(
    private val config: ApplicationConfiguration,
    private val mapper: ObjectMapper,
    private val okHttpClient: OkHttpClient
) {
    suspend fun generate(links: Links): String {
        val deferredCategories = links.map { category ->
            GlobalScope.async(Dispatchers.Default) { processCategory(category) }
        }

        val mapped = deferredCategories.map { it.await() }

        return mapper.writeValueAsString(mapped)
    }

    private suspend fun processCategory(category: Category): Category {
        val deferredSubcategories = category.subcategories.map { subcategory ->
            GlobalScope.async(Dispatchers.Default) { processSubcategory(subcategory) }
        }

        val processed = deferredSubcategories.map { it.await() }.toMutableList()

        return category.copy(subcategories = processed)
    }

    private suspend fun processSubcategory(subcategory: Subcategory): Subcategory {
        return subcategory.copy(links = subcategory.links.map { link ->
            when (link.type) {
                LinkType.github -> {
                    LOGGER.info("Fetching star count from github: ${link.name}.")
                    val json = try {
                        val response = getGithubStarCount(okHttpClient, link.name, config.ghUser, config.ghToken)
                        mapper.readTree(response.body()?.string() ?: "")
                    } catch (e: Exception) {
                        LOGGER.error("Error while fetching data for '${link.name}'.", e)
                        throw e
                    }

                    val stargazers_count = json["stargazers_count"]?.asInt()
                    val pushed_at = json["pushed_at"]?.asText() ?: ""

                    if (pushed_at.isNotEmpty()) {
                        link.star = stargazers_count
                        link.update = parseInstant(pushed_at).format(formatter)
                    }

                    link
                }
                LinkType.bitbucket -> {
                    LOGGER.info("Fetching star count from bitbucket: ${link.name}.")
                    val response = getBitbucketStarCount(okHttpClient, link.name)

                    val stars = mapper.readValue<BitbucketResponse>(response.body()?.string() ?: "")
                    link.star = stars.size
                    link
                }
                else -> link
            }
        }.toMutableList())
    }

    companion object {
        private val LOGGER = logger<DefaultStarsGenerator>()
    }
}


private suspend fun getGithubStarCount(client: OkHttpClient, name: String, user: String, pass: String): Response {
    if (user == "" || pass == "") {
        throw RuntimeException("You should run this script only when you added GH_USER and GH_TOKEN to env." +
            "Token can be found here: https://github.com/settings/tokens")
    }

    val request = Request.Builder()
        .url("https://api.github.com/repos/$name")
        .header("User-Agent", "Awesome-Kotlin-List")
        .header("Authorization", "token $pass")
        .build()

    return client.newCall(request).await()
}

private suspend fun getBitbucketStarCount(client: OkHttpClient, name: String): Response {
    val request = Request.Builder()
        .url("https://api.bitbucket.org/2.0/repositories/$name/watchers")
        .header("User-Agent", "Awesome-Kotlin-List")
        .build()

    return client.newCall(request).await()
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BitbucketResponse(
    val size: Int
)

