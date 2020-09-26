package link.kotlin.scripts.import

import link.kotlin.scripts.utils.Cache
import org.jsoup.Jsoup

private val trending = listOf(
    "https://github.com/trending/kotlin?since=weekly",
    "https://github.com/trending/kotlin?since=monthly",
    "https://github.com/trending/kotlin?since=daily"
)

class GithubTrending(
    private val cache: Cache
) {
    fun fetch(): List<String> {
        return trending
            .map(this::getTrendingRepositories)
            .flatten()
            .distinct()
    }

    private fun getTrendingRepositories(url: String): List<String> {
        val doc = Jsoup.connect(url).get()

        val isAvailable = doc.select(".blankslate").isEmpty()

        if (isAvailable) {
            val repos = doc.select(".repo-list h3 a").map { it.attr("href") }
            return repos
        } else {
            return listOf()
        }
    }
}
