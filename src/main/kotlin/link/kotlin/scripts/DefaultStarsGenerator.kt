package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import link.kotlin.scripts.utils.logger
import link.kotlin.scripts.utils.parseInstant
import org.apache.http.client.methods.HttpGet

class DefaultStarsGenerator(
    private val config: ApplicationConfiguration,
    private val mapper: ObjectMapper,
    private val httpClient: HttpClient
) {
    suspend fun generate(links: Links): String {
        val categories = links.map { category ->
            processCategory(category)
        }

        return mapper.writeValueAsString(categories)
    }

    private suspend fun processCategory(category: Category): Category = coroutineScope {
        val deferredSubcategories = category.subcategories.map { subcategory ->
            async { processSubcategory(subcategory) }
        }

        val processed = deferredSubcategories.awaitAll().toMutableList()

        category.copy(subcategories = processed)
    }

    private suspend fun processSubcategory(subcategory: Subcategory): Subcategory {
        return subcategory.copy(links = subcategory.links.map { link ->
            when (link.type) {
                LinkType.github -> {
                    LOGGER.info("Fetching star count from github: ${link.name}.")
                    val json = try {
                        val response = getGithubStarCount(httpClient, link.name, config.ghUser, config.ghToken)
                        mapper.readTree(response)
                    } catch (e: Exception) {
                        LOGGER.error("Error while fetching data for '${link.name}'.", e)
                        return@map link
                    }

                    val stargazers_count = json["stargazers_count"]?.asInt()
                    val pushed_at = json["pushed_at"]?.asText() ?: ""
                    val archived = json["archived"]?.asBoolean() ?: false

                    if (pushed_at.isNotEmpty()) {
                        link.star = stargazers_count
                        link.update = parseInstant(pushed_at).format(formatter)
                        link.archived = archived
                    }

                    link
                }
                LinkType.bitbucket -> {
                    val stars = getBitbucketStarCount(httpClient, link.name)
                    link.star = stars?.size
                    link
                }
                else -> link
            }
        }.toMutableList())
    }

    private suspend fun getBitbucketStarCount(
        client: HttpClient,
        name: String
    ): BitbucketResponse? {
        return try {
            LOGGER.info("Fetching star count from bitbucket: $name.")
            val request = HttpGet("https://api.bitbucket.org/2.0/repositories/$name/watchers")
            val response = client.execute(request).body()
            mapper.readValue<BitbucketResponse>(response)
        } catch(e: Exception) {
            LOGGER.error("Fetching star count from bitbucket", e)
            null
        }
    }

    companion object {
        private val LOGGER = logger<DefaultStarsGenerator>()
    }
}

private suspend fun getGithubStarCount(
    client: HttpClient,
    name: String,
    user: String,
    pass: String
): String {
    if (user.isEmpty() || pass.isEmpty()) {
        throw RuntimeException("You should run this script only when you added GH_USER and GH_TOKEN to env." +
            "Token can be found here: https://github.com/settings/tokens")
    }

    val request = HttpGet("https://api.github.com/repos/$name").also {
        it.addHeader("Authorization", "token $pass")
    }

    return client.execute(request).body()
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BitbucketResponse(
    val size: Int
)

