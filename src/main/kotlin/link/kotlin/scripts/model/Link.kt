package link.kotlin.scripts.model

import link.kotlin.scripts.dsl.PlatformType

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
)
