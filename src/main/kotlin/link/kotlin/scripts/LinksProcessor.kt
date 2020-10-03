package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.model.Link
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import link.kotlin.scripts.utils.logger
import org.apache.http.client.methods.HttpGet
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

interface LinksProcessor {
    suspend fun process(link: Link): Link

    companion object
}

private class DefaultLinksProcessor(
    private val configuration: ApplicationConfiguration,
    private val mapper: ObjectMapper,
    private val httpClient: HttpClient,
    private val linksChecker: LinksChecker
) : LinksProcessor {
    private val now: Instant = Instant.now()

    override suspend fun process(link: Link): Link {
        return when {
            link.github != null -> {
                LOGGER.debug("Fetching star count from github: ${link.github}.")
                val meta = try {
                    if (!configuration.dryRun) {
                        getGithubMetadata(link.github)
                    } else GithubMetadata.DEFAULT
                } catch (e: Exception) {
                    LOGGER.error("Error ({}) while fetching data for '${link.github}'.", e.message)
                    GithubMetadata.DEFAULT
                }
                link.check()

                link.copy(
                    name = link.name ?: link.github,
                    href = link.href ?: "https://github.com/${link.github}",
                    desc = link.desc ?: meta.description,
                    star = meta.stargazersCount,
                    update = meta.pushedAt?.let { LocalDateTime.ofInstant(it, ZoneId.of("UTC")).format(formatter) },
                    archived = meta.archived,
                    unsupported = meta.unsupported,
                    tags = (link.tags + meta.topics).distinct()
                )
            }
            link.bitbucket != null -> {
                val stars = getBitbucketStarCount(link.bitbucket)
                link.check()

                link.copy(
                    name = link.name ?: link.bitbucket,
                    href = link.href ?: "https://bitbucket.org/${link.bitbucket}",
                    star = stars?.size
                )
            }
            link.kug != null -> {
                link.check()

                link.copy(
                    name = link.name ?: link.kug
                )
            }
            else -> {
                link.check()
                link
            }
        }
    }

    private suspend fun Link.check() {
        if (this.href != null) {
            LOGGER.debug("Checking link [${this.href}]")
            linksChecker.check(this.href)
        }
    }

    private suspend fun getGithubRepoTopics(name: String): List<String> {
        return try {
            val request = HttpGet("https://api.github.com/repos/$name/topics").also {
                it.addHeader("Authorization", "token ${configuration.ghToken}")
                it.addHeader("Accept", "application/vnd.github.mercy-preview+json")
            }

            val response = httpClient.execute(request)

            if (response.statusLine.statusCode != 200) {
                LOGGER.error("[https://github.com/$name]: Response code: ${response.statusLine.statusCode}.")
            }

            mapper.readValue<GithubRepoTopicsResponse>(response.body()).names
        } catch (e: Exception) {
            LOGGER.error("Fetching topics from github: [$name]. Error: {}", e.message)
            emptyList()
        }
    }

    private suspend fun getGithubMetadata(name: String): GithubMetadata {
        val githubRepoInfo = getGithubRepoInfo(name)
        val topics = getGithubRepoTopics(name)

        return GithubMetadata(
            stargazersCount = githubRepoInfo?.stargazersCount,
            pushedAt = githubRepoInfo?.pushedAt,
            description = githubRepoInfo?.description,
            archived = githubRepoInfo?.archived ?: false,
            topics = topics
        )
    }

    private suspend fun getGithubRepoInfo(name: String): GithubRepoResponse? {
        return try {
            val request = HttpGet("https://api.github.com/repos/$name").also {
                it.addHeader("Authorization", "token ${configuration.ghToken}")
                it.addHeader("Accept", "application/vnd.github.v3+json")
            }

            val response = httpClient.execute(request)

            if (response.statusLine.statusCode != 200) {
                LOGGER.error("[https://github.com/$name]: Response code: ${response.statusLine.statusCode}.")
            }

            mapper.readValue<GithubRepoResponse>(response.body())
        } catch (e: Exception) {
            LOGGER.error("Fetching star count from github: [$name]. Error: {}", e.message)
            null
        }
    }

    private suspend fun getBitbucketStarCount(
        name: String
    ): BitbucketResponse? {
        return try {
            LOGGER.debug("Fetching star count from bitbucket: $name.")
            val request = HttpGet("https://api.bitbucket.org/2.0/repositories/$name/watchers")
            val response = httpClient.execute(request).body()
            mapper.readValue<BitbucketResponse>(response)
        } catch (e: Exception) {
            LOGGER.error("Fetching star count from bitbucket: [$name]. Error: {}", e.message)
            null
        }
    }

    private val GithubMetadata.unsupported: Boolean
        get() {
            return (this.pushedAt?.isBefore(now.minus(365, ChronoUnit.DAYS)) ?: false) && (this.stargazersCount ?: 100 < 100)
        }
}

class DescriptionMarkdownLinkProcessor(
    private val markdownRenderer: MarkdownRenderer
) : LinksProcessor {
    override suspend fun process(link: Link): Link {
        return if (link.desc != null) {
            link.copy(desc = markdownRenderer.render(link.desc))
        } else link
    }
}

class CombinedLinksProcessors(
    private val processors: List<LinksProcessor>
) : LinksProcessor {
    override suspend fun process(link: Link): Link {
        return processors.fold(link) { curr, processor ->
            processor.process(curr)
        }
    }
}

private val LOGGER = logger<DefaultLinksProcessor>()

fun LinksProcessor.Companion.default(
    configuration: ApplicationConfiguration,
    mapper: ObjectMapper,
    httpClient: HttpClient,
    linksChecker: LinksChecker,
    markdownRenderer: MarkdownRenderer
): LinksProcessor {
    val defaultLinksProcessor = DefaultLinksProcessor(
        configuration = configuration,
        mapper = mapper,
        httpClient = httpClient,
        linksChecker = linksChecker
    )

    val markdownLinkProcessor = DescriptionMarkdownLinkProcessor(
        markdownRenderer = markdownRenderer
    )

    return CombinedLinksProcessors(listOf(defaultLinksProcessor, markdownLinkProcessor))
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class BitbucketResponse(
    val size: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoResponse(
    @JsonProperty("stargazers_count")
    val stargazersCount: Int,
    @JsonProperty("pushed_at")
    val pushedAt: Instant,
    val description: String?,
    val archived: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoTopicsResponse(
    val names: List<String>
)

data class GithubMetadata(
    val stargazersCount: Int?,
    val pushedAt: Instant?,
    val description: String?,
    val archived: Boolean,
    val topics: List<String>
) {
    companion object {
        val DEFAULT = GithubMetadata(
            stargazersCount = null,
            pushedAt = null,
            description = null,
            archived = false,
            topics = emptyList()
        )
    }
}
