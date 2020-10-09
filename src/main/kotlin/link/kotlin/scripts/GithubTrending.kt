package link.kotlin.scripts

import link.kotlin.scripts.dsl.Category
import link.kotlin.scripts.dsl.Subcategory
import link.kotlin.scripts.model.Link
import link.kotlin.scripts.utils.Cache
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val trending = listOf(
    "Monthly" to "https://github.com/trending/kotlin?since=monthly",
    "Weekly" to "https://github.com/trending/kotlin?since=weekly",
    "Daily" to "https://github.com/trending/kotlin?since=daily"
)

interface GithubTrending {
    suspend fun fetch(): Category?

    companion object
}

private class CachedGithubTrending(
    private val cache: Cache,
    private val githubTrending: GithubTrending
) : GithubTrending {
    override suspend fun fetch(): Category? {
        val date = DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now())
        val cacheKey = "github-trending-$date"
        val cacheValue = cache.get(cacheKey, Category::class)

        return if (cacheValue == null) {
            val result = githubTrending.fetch()
            result ?: cache.put(cacheKey, result)
            result
        } else cacheValue
    }
}

private class JSoupGithubTrending(
) : GithubTrending {
    override suspend fun fetch(): Category? {
        val subcategories = trending.mapNotNull { it.toSubcategory() }
            .deleteDuplicates()
            .toMutableList()

        return if (subcategories.isNotEmpty()) {
            Category(
                name = "Github Trending",
                subcategories = subcategories
            )
        } else null
    }

    private fun List<Subcategory>.deleteDuplicates(): List<Subcategory> {
        val pool = mutableSetOf<Link>()

        return this.map { subcategory ->
            val filtered = subcategory.links.subtract(pool)
            val result = subcategory.copy(links = filtered.toMutableList())
            pool.addAll(subcategory.links)
            result
        }
    }

    private fun Pair<String, String>.toSubcategory(): Subcategory? {
        val doc = Jsoup.connect(this.second).get()

        val isAvailable = doc.select(".Box-row").isNotEmpty()

        return if (isAvailable) {
            val links = doc.select(".Box-row").map {
                Link(
                    github = it.select("h1 a").attr("href").removePrefix("/")
                )
            }

            return Subcategory(
                name = this.first,
                links = links.toMutableList()
            )
        } else {
            null
        }
    }
}

fun GithubTrending.Companion.default(
    cache: Cache
): GithubTrending {
    val jSoupGithubTrending = JSoupGithubTrending()

    return CachedGithubTrending(
        cache = cache,
        githubTrending = jSoupGithubTrending
    )
}
