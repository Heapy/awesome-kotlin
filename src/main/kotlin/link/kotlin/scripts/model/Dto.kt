package link.kotlin.scripts.model

import link.kotlin.scripts.dsl.Category
import link.kotlin.scripts.dsl.PlatformType
import link.kotlin.scripts.dsl.Subcategory

/**
 * Representation for frontend
 */
data class LinkDto(
    val name: String,
    val href: String,
    val desc: String,
    val platforms: List<PlatformType>,
    val tags: Set<String>,
    val star: Int? = null,
    val update: String? = null,
    val state: LinkStateDto
)

enum class LinkStateDto {
    AWESOME,
    UNSUPPORTED,
    ARCHIVED,
    DEFAULT
}

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

data class SubcategoryDto(
    val name: String,
    val links: List<LinkDto>
)

data class CategoryDto(
    val name: String,
    val subcategories: List<SubcategoryDto>
)

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
