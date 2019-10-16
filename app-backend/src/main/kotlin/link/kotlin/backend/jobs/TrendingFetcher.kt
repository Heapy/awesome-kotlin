package link.kotlin.backend.jobs

import org.jsoup.Jsoup

private val trending = listOf(
    "https://github.com/trending/kotlin?since=weekly",
    "https://github.com/trending/kotlin?since=monthly",
    "https://github.com/trending/kotlin?since=daily"
)

// TODO:
//  test
//  interface

/**
 * Grabs data from Github trending
 *
 * @author Ruslan Ibragimov
 * @since 2019.10
 */
class GithubTrending {
    fun fetch(): List<String> {
        return trending
            .map(this::getTrendingRepositories)
            .flatten()
            .distinct()
    }

    private fun getTrendingRepositories(url: String): List<String> {
        val doc = Jsoup.connect(url).get()

        return doc.select(".repo-list h3 a").map { it.attr("href") }
    }
}
