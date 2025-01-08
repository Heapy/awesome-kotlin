package usecases.links

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.heapy.komok.tech.logging.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import usecases.articles.MarkdownRenderer
import usecases.github_trending.GithubConfig
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

interface LinksProcessor {
    suspend fun process(link: Link): Link
}

class DefaultLinksProcessor(
    private val githubConfig: GithubConfig,
    private val mapper: ObjectMapper,
    private val httpClient: HttpClient,
    private val linksChecker: LinksChecker,
) : LinksProcessor {
    private val now: Instant = Instant.now()

    internal val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    override suspend fun process(link: Link): Link {
        return when {
            link.github != null -> {
                log.info("Fetching star count from github: ${link.github}.")

                val meta = try {
                    getGithubMetadata(link.github)
                } catch (e: Exception) {
                    log.error("Error ({}) while fetching data for '${link.github}'.", e.message)
                    GithubMetadata.EMPTY
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
            log.debug("Checking link [${this.href}]")
            linksChecker.check(this.href)
        }
    }

    private suspend fun getGithubRepoTopics(name: String): List<String> {
        return try {
            val response = httpClient.get("https://api.github.com/repos/$name/topics") {
                header("Authorization", "token ${githubConfig.token}")
                header("Accept", "application/vnd.github.mercy-preview+json")
            }

            if (response.status != HttpStatusCode.OK) {
                log.error("[https://github.com/$name]: Response code: ${response.status}.")
            }

            mapper.readValue<GithubRepoTopicsResponse>(response.bodyAsText()).names
        } catch (e: Exception) {
            log.error("Fetching topics from github: [$name]. Error: {}", e.message)
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
            archived = githubRepoInfo?.archived == true,
            topics = topics
        )
    }

    private suspend fun getGithubRepoInfo(name: String): GithubRepoResponse? {
        return try {
            val response = httpClient.get("https://api.github.com/repos/$name") {
                header("Authorization", "token ${githubConfig.token}")
                header("Accept", "application/vnd.github.v3+json")
            }

            if (response.status != HttpStatusCode.OK) {
                log.error("[https://github.com/$name]: Response code: ${response.status}.")
            }

            mapper.readValue<GithubRepoResponse>(response.bodyAsText())
        } catch (e: Exception) {
            log.error("Fetching star count from github: [$name]. Error: {}", e.message)
            null
        }
    }

    private suspend fun getBitbucketStarCount(
        name: String
    ): BitbucketResponse? {
        return try {
            log.debug("Fetching star count from bitbucket: $name.")
            val response = httpClient.head("https://api.bitbucket.org/2.0/repositories/$name/watchers")
                .bodyAsText()
            mapper.readValue<BitbucketResponse>(response)
        } catch (e: Exception) {
            log.error("Fetching star count from bitbucket: [$name]. Error: {}", e.message)
            null
        }
    }

    private val GithubMetadata.unsupported: Boolean
        get() {
            return (this.pushedAt?.isBefore(now.minus(365, ChronoUnit.DAYS)) == true) && (this.stargazersCount ?: 100 < 100)
        }

    private companion object : Logger()
}

class DescriptionMarkdownLinkProcessor(
    private val markdownRenderer: MarkdownRenderer,
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
        val EMPTY = GithubMetadata(
            stargazersCount = null,
            pushedAt = null,
            description = null,
            archived = false,
            topics = emptyList()
        )
    }
}
