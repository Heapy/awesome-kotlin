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
        // TODO:
        //  Fetch repo tags from github
        //  Deduplicate link check
        //  Model class for GH response
        return when {
            link.github != null -> {
                LOGGER.debug("Fetching star count from github: ${link.github}.")
                val json = try {
                    val response = getGithubStarCount(link.github, configuration.ghUser, configuration.ghToken)
                    mapper.readTree(response)
                } catch (e: Exception) {
                    LOGGER.error("Error ({}) while fetching data for '${link.github}'.", e.message)
                    return link
                }

                val stargazersCount = json["stargazers_count"]?.asInt()
                val pushedAt = json["pushed_at"]?.asText() ?: ""
                val description = json["description"]?.asText() ?: ""
                val archived = json["archived"]?.asBoolean() ?: false

                if (link.href != null) {
                    LOGGER.debug("Checking link [${link.href}]")
                    linksChecker.check(link.href)
                }

                if (pushedAt.isNotEmpty()) {
                    link.copy(
                        name = link.name ?: link.github,
                        href = link.href ?: "https://githib.com/${link.github}",
                        desc = link.desc ?: description,
                        star = stargazersCount,
                        update = parseInstant(pushedAt).format(formatter),
                        archived = archived
                    )
                } else link
            }
            link.bitbucket != null -> {
                val stars = getBitbucketStarCount(link.bitbucket)

                if (link.href != null) {
                    LOGGER.debug("Checking link [${link.href}]")
                    linksChecker.check(link.href)
                }

                link.copy(
                    name = link.name ?: link.bitbucket,
                    href = link.href ?: "https://bitbucket.org/${link.bitbucket}",
                    star = stars?.size
                )
            }
            link.kug != null -> {
                if (link.href != null) {
                    LOGGER.debug("Checking link [${link.href}]")
                    linksChecker.check(link.href)
                }

                link.copy(
                    name = link.name ?: link.kug
                )
            }
            else -> {
                if (link.href != null) {
                    LOGGER.debug("Checking link [${link.href}]")
                    linksChecker.check(link.href)
                }
                link
            }
        }
    }

    private suspend fun getGithubStarCount(
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

        val response = httpClient.execute(request)

        if (response.statusLine.statusCode != 200) {
            LOGGER.error("[https://github.com/$name]: Response code: ${response.statusLine.statusCode}.")
        }

        return response.body()
    }

    private suspend fun getBitbucketStarCount(
        name: String
    ): BitbucketResponse? {
        return try {
            LOGGER.debug("Fetching star count from bitbucket: $name.")
            val request = HttpGet("https://api.bitbucket.org/2.0/repositories/$name/watchers")
            val response = httpClient.execute(request).body()
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

@JsonIgnoreProperties(ignoreUnknown = true)
class BitbucketResponse(
    val size: Int
)

