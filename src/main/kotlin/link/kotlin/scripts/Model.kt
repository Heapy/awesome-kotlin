package link.kotlin.scripts

import link.kotlin.scripts.LinkType.article
import link.kotlin.scripts.LinkType.slides
import link.kotlin.scripts.LinkType.video
import link.kotlin.scripts.LinkType.webinar
import link.kotlin.scripts.model.Link

enum class LinkType {
    none,
    github,
    bitbucket,
    blog,
    kug,
    article,
    video,
    slides,
    webinar
}

enum class TargetType {
    ANDROID,
    COMMON,
    IOS,
    JS,
    JVM,
    NATIVE,
    WASM
}

fun LinkType.toView() = when (this) {
    article -> "Articles, Blog Posts"
    video -> "Videos"
    slides -> "Slides"
    webinar -> "Webinars"
    else -> ""
}

data class Subcategory(
    val id: String? = null,
    val name: String,
    val links: MutableList<Link>
) {
    operator fun Link.unaryPlus() = links.add(this)
}

data class Category(
    val id: String? = null,
    val name: String,
    val subcategories: MutableList<Subcategory>
) {
    operator fun Subcategory.unaryPlus() = subcategories.add(this)
}

enum class ArticleFeature {
    mathjax,
    highlightjs
}

typealias Links = List<Category>
