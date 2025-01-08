package usecases.links

import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    val name: String,
    val href: String? = null,
    val desc: String? = null,
    val platforms: List<PlatformType> = emptyList(),
    val tags: Set<String> = emptySet(),
    val star: Int? = null,
    val update: String? = null,
    val state: LinkStateDto,
)

@Serializable
enum class LinkStateDto {
    AWESOME,
    UNSUPPORTED,
    ARCHIVED,
    DEFAULT,
}

@Serializable
data class SubcategoryDto(
    val name: String,
    val links: List<LinkDto>
)

@Serializable
data class CategoryDto(
    val name: String,
    val subcategories: List<SubcategoryDto>
)

private fun Link.toDto(): LinkDto {
    val state = when {
        awesome -> LinkStateDto.AWESOME
        archived -> LinkStateDto.ARCHIVED
        unsupported -> LinkStateDto.UNSUPPORTED
        else -> LinkStateDto.DEFAULT
    }

    return LinkDto(
        name = name ?: error("Link should have a name [$this]"),
        href = href ?: error("Link should have a href [$this]"),
        desc = desc ?: "",
        platforms = platforms,
        tags = tags.toSet(),
        star = star,
        update = update,
        state = state
    )
}

fun Category.toDto(): CategoryDto {
    return CategoryDto(
        name = name,
        subcategories = subcategories.map { it.toDto() }
    )
}

private fun Subcategory.toDto(): SubcategoryDto {
    return SubcategoryDto(
        name = name,
        links = links.map { it.toDto() }
    )
}


fun List<CategoryDto>.filterByAwesome(): List<CategoryDto> {
    return this.mapNotNull { category ->
        val subcategories = category.subcategories.mapNotNull { subcategory ->
            val links = subcategory.links.filter { linkV1 ->
                linkV1.state == LinkStateDto.AWESOME
            }

            if (links.isNotEmpty()) subcategory.copy(links = links) else null
        }

        if (subcategories.isNotEmpty()) category.copy(subcategories = subcategories) else null
    }
}

fun List<CategoryDto>.filterByKug(): List<CategoryDto> {
    return this.filter { categoryV1 ->
        categoryV1.name == "Kotlin User Groups"
    }
}

fun List<CategoryDto>.filterByQuery(query: String?): List<CategoryDto> {
    return if (query != null) {
        val searchTerm = query.lowercase()

        this.mapNotNull { category ->
            val subcategories = category.subcategories.mapNotNull { subcategory ->
                val links = subcategory.links.filter { link ->
                    link.name.contains(searchTerm, ignoreCase = true) ||
                        link.desc?.contains(searchTerm, ignoreCase = true) == true ||
                        link.tags.any { tag -> tag.contains(searchTerm, ignoreCase = true) }
                }
                if (links.isNotEmpty()) subcategory.copy(links = links) else null
            }
            if (subcategories.isNotEmpty()) category.copy(subcategories = subcategories) else null
        }
    } else {
        this
    }
}
