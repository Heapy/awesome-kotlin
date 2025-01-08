package usecases.links

import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val name: String? = null,
    val github: String? = null,
    val bitbucket: String? = null,
    val kug: String? = null,
    val href: String? = null,
    val desc: String? = null,
    val platforms: List<PlatformType> = emptyList(),
    val tags: List<String> = emptyList(),
    val star: Int? = null,
    val update: String? = null,
    val archived: Boolean = false,
    val unsupported: Boolean = false,
    val awesome: Boolean = false,
)

@Serializable
enum class PlatformType {
    ANDROID,
    COMMON,
    IOS,
    JS,
    JVM,
    NATIVE,
    WASM,
}

@Serializable
data class Subcategory(
    val name: String,
    val links: MutableList<Link>,
)

@Serializable
data class Category(
    val name: String,
    val subcategories: MutableList<Subcategory>,
)
