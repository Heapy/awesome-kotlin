package usecases.links

class LinksDtoSource(
    private val linksSource: LinksSource,
) {
    suspend fun get(): List<CategoryDto> {
        return linksSource
            .getLinks()
            .map { it.toDto() }
    }
}
