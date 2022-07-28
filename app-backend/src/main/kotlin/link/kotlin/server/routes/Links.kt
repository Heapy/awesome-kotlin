package link.kotlin.server.routes

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Routing.links(
    links: List<CategoryV1>,
) {
    get("/links") {
        val isAwesome = call.request.queryParameters["awesome"].toBoolean()

        val allLinks: List<CategoryDto> = if (isAwesome) {
            links.map { cat ->
                val newSub = cat.subcategories
                    .map {sub ->
                        sub.copy(links = sub.links.filter { link -> link.awesome }.toMutableList())
                    }
                    .filter { sub ->
                        sub.links.isNotEmpty()
                    }
                    .toMutableList()

                cat.copy(subcategories = newSub)
            }.filter { it.subcategories.isNotEmpty() }
                .map { category -> category.toDto() }
        } else {
            links.map { category -> category.toDto() }
        }

        call.respond(allLinks)
    }
}

class LinkSource {
    private val files = listOf(
        "Links.json",
        "Libraries.json",
        "Projects.json",
        "Android.json",
        "JavaScript.json",
        "Native.json",
        "UserGroups.json"
    )

    fun get(): List<CategoryV1> {
        return files.map { file ->
            val json = LinkSource::class.java
                .classLoader
                .getResource("links/$file")
                .readText()
            Json.decodeFromString(json)
        }
    }
}

@Serializable
data class LinkV1(
    val name: String? = null,
    val github: String? = null,
    val bitbucket: String? = null,
    val kug: String? = null,
    val href: String? = null,
    val desc: String? = null,
    val platforms: List<PlatformTypeV1> = emptyList(),
    val tags: List<String> = emptyList(),
    val star: Int? = null,
    val update: String? = null,
    val archived: Boolean = false,
    val unsupported: Boolean = false,
    val awesome: Boolean = false
)

@Serializable
enum class PlatformTypeV1 {
    ANDROID,
    COMMON,
    IOS,
    JS,
    JVM,
    NATIVE,
    WASM
}

@Serializable
data class SubcategoryV1(
    val name: String,
    val links: MutableList<LinkV1>
)

@Serializable
data class CategoryV1(
    val name: String,
    val subcategories: MutableList<SubcategoryV1>
)

data class LinkDto(
    val name: String,
    val href: String,
    val desc: String,
    val platforms: List<PlatformTypeV1>,
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

private fun LinkV1.toDto(): LinkDto {
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

fun CategoryV1.toDto(): CategoryDto {
    return CategoryDto(
        name = name,
        subcategories = subcategories.map { it.toDto() }
    )
}

private fun SubcategoryV1.toDto(): SubcategoryDto {
    return SubcategoryDto(
        name = name,
        links = links.map { it.toDto() }
    )
}
