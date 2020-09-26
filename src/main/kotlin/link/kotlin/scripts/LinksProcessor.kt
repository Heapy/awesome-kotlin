package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.model.Link
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import link.kotlin.scripts.utils.logger
import link.kotlin.scripts.utils.parseInstant
import org.apache.http.client.methods.HttpGet

interface LinksProcessor {
    suspend fun process(link: Link): Link

    companion object
}

private class DefaultLinksProcessor(
    private val configuration: ApplicationConfiguration,
    private val mapper: ObjectMapper,
    private val httpClient: HttpClient,
    private val linksChecker: LinksChecker
): LinksProcessor {
    override suspend fun process(link: Link): Link {
        return when (link.type) {
            LinkType.github -> {
                LOGGER.debug("Fetching star count from github: ${link.name}.")
                val json = try {
                    val response = getGithubStarCount(httpClient, link.name, configuration.ghUser, configuration.ghToken)
                    mapper.readTree(response)
                } catch (e: Exception) {
                    LOGGER.error("Error ({}) while fetching data for '${link.name}'.", e.message)
                    return link
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
            else -> {
                LOGGER.debug("Checking link [${link.href}]")
                linksChecker.check(link.href)
                link
            }
        }
    }


    private suspend fun getBitbucketStarCount(
        client: HttpClient,
        name: String
    ): BitbucketResponse? {
        return try {
            LOGGER.debug("Fetching star count from bitbucket: $name.")
            val request = HttpGet("https://api.bitbucket.org/2.0/repositories/$name/watchers")
            val response = client.execute(request).body()
            mapper.readValue<BitbucketResponse>(response)
        } catch(e: Exception) {
            LOGGER.error("Fetching star count from bitbucket", e)
            null
        }
    }
}

private val LOGGER = logger<DefaultLinksProcessor>()

fun LinksProcessor.Companion.default(
    configuration: ApplicationConfiguration,
    mapper: ObjectMapper,
    httpClient: HttpClient,
    linksChecker: LinksChecker
): LinksProcessor {
    return DefaultLinksProcessor(
        configuration = configuration,
        mapper = mapper,
        httpClient = httpClient,
        linksChecker = linksChecker
    )
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

    val response = client.execute(request)

    if (response.statusLine.statusCode != 200) {
        LOGGER.error("[https://github.com/$name]: Response code: ${response.statusLine.statusCode}.")
    }

    return response.body()
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BitbucketResponse(
    val size: Int
)

