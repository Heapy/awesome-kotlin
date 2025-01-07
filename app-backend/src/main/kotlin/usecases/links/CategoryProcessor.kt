package usecases.links

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CategoryProcessor(
    private val linksProcessor: LinksProcessor,
) {
    suspend fun process(category: Category): Category = coroutineScope {
        val result = category.copy(
            subcategories = category.subcategories.map { subcategory ->
                subcategory.copy(
                    links = subcategory.links.map { link ->
                        async { linksProcessor.process(link) }
                    }.awaitAll().toMutableList()
                )
            }.toMutableList()
        )

        result
    }
}
