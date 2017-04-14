package link.kotlin.scripts

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.CompletableFuture

class DefaultStarsGenerator(
    val config: ApplicationConfiguration,
    val mapper: ObjectMapper,
    val okHttpClient: OkHttpClient
) {
    fun generate(allLinks: Links): String {
        val links = allLinks.map { category ->
            category.subcategories.map { subcategory ->
                subcategory.links.map { link ->
                    when (link.type) {
                        LinkType.github -> {
                            getGithubStarCount(okHttpClient, link.name)

                        }
                        LinkType.bitbucket -> {
                            println()
                        }
                        else -> CompletableFuture.completedFuture(link)
                    }
                }
            }
        }

        return ""
    }
}


val c = """
  return JSON_GET(options, repository, json => ({
    stars: json.stargazers_count,
    update: json.pushed_at
  }));
"""

private fun getGithubStarCount(client: OkHttpClient, name: String): CompletableFuture<Response> {
    val request = Request.Builder()
        .url("https://api.github.com/repos/$name")
        .header("User-Agent", "Awesome-Kotlin-List")
        .build()

    return client.newCall(request).async()
}

class GithubResponse(
    val stargazers_count: Int,
    val pushed_at: String
)