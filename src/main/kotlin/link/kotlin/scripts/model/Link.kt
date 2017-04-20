package link.kotlin.scripts.model

import link.kotlin.scripts.LinkType

data class Link(
    val name: String,
    val href: String = "",
    val desc: String = "",
    val type: LinkType = LinkType.none,
    val tags: Array<String> = arrayOf(),
    val whitelisted: Boolean = false,
    var star: Int? = null,
    var update: String? = null
)
