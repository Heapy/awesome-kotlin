package link.kotlin.scripts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.experimental.future.await
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

class DefaultStarsGenerator(
    val config: ApplicationConfiguration,
    val mapper: ObjectMapper,
    val okHttpClient: OkHttpClient
) {
    fun generate(allLinks: Links) = runBlocking<String> {
        allLinks.forEach { category ->
            category.subcategories.forEach { subcategory ->
                subcategory.links.forEach { link ->
                    when (link.type) {
                        LinkType.github -> {
                            // TODO: Do request async
                            val response = getGithubStarCount(okHttpClient, link.name, config.ghUser, config.ghToken).await()
                            val stars = mapper.readValue<GithubResponse>(response.body().string())

                            link.star = stars.stargazers_count
                            link.update = LocalDateTime.parse(stars.pushed_at).format(formatter)
                        }
                        LinkType.bitbucket -> {
                            // TODO: Do request async
                            val response = getBitbucketStarCount(okHttpClient, link.name).await()

                            val stars = mapper.readValue<BitbucketResponse>(response.body().string())
                            link.star = stars.size
                        }
                        else -> Unit
                    }
                }
            }
        }

        mapper.writeValueAsString(allLinks)
    }
}


private fun getGithubStarCount(client: OkHttpClient, name: String, user: String, pass: String): CompletableFuture<Response> {
    if (user == "" || pass == "") {
        throw RuntimeException("You should run this script only when you added GH_USER and GH_TOKEN to env." +
            "Token can be found here: https://github.com/settings/tokens")
    }

    val request = Request.Builder()
        .url("https://api.github.com/repos/$name")
        .header("User-Agent", "Awesome-Kotlin-List")
        .header("Authorization", "token $pass")
        .build()

    return client.newCall(request).async()
}

private fun getBitbucketStarCount(client: OkHttpClient, name: String):
    CompletableFuture<Response> {
    val request = Request.Builder()
        .url("https://api.bitbucket.org/2.0/repositories/$name/watchers")
        .header("User-Agent", "Awesome-Kotlin-List")
        .build()

    return client.newCall(request).async()
}

@JsonIgnoreProperties(ignoreUnknown = true)
class GithubResponse(
    val stargazers_count: Int,
    val pushed_at: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
class BitbucketResponse(
    val size: Int
)

