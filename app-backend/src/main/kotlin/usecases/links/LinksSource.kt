package usecases.links

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import usecases.github_trending.GithubTrendingLinkSource

class LinksSource(
    private val githubTrendingLinkSource: GithubTrendingLinkSource,
    private val categoryProcessor: CategoryProcessor,
) {
    private val links = GlobalScope.async(start = CoroutineStart.LAZY) {
        getLinksInternal()
    }

    suspend fun getLinks(): List<Category> {
        return links.await()
    }

    private suspend fun getLinksInternal(): List<Category> {
        val json = LinksSource::class.java
            .classLoader
            .getResource("data/links.json")
            .readText()

        val jsonFileLinks = Json
            .decodeFromString(ListSerializer(Category.serializer()), json)

        val githubTrendingLinks = listOfNotNull(githubTrendingLinkSource.fetch())

        val all = jsonFileLinks + githubTrendingLinks

        return all
            .map { categoryProcessor.process(it) }
    }
}
