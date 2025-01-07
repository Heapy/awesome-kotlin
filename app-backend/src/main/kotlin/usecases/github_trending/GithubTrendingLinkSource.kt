package usecases.github_trending

import org.jsoup.Jsoup
import usecases.links.Category
import usecases.links.Link
import usecases.links.Subcategory

private val trending = listOf(
    "Monthly" to "https://github.com/trending/kotlin?since=monthly",
    "Weekly" to "https://github.com/trending/kotlin?since=weekly",
    "Daily" to "https://github.com/trending/kotlin?since=daily"
)

class GithubTrendingLinkSource {
    fun fetch(): Category? {
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
                    github = it.select("h2 a").attr("href").removePrefix("/")
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
