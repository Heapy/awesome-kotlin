package link.kotlin.scripts

import link.kotlin.scripts.ArticleFeature.highlightjs
import link.kotlin.scripts.model.LanguageCodes
import link.kotlin.scripts.model.LanguageCodes.EN
import java.time.LocalDate
import link.kotlin.scripts.TargetType.JVM
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
    LinkType.article -> "Articles, Blog Posts"
    LinkType.video -> "Videos"
    LinkType.slides -> "Slides"
    LinkType.webinar -> "Webinars"
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

data class Article(
    val title: String,
    val url: String,
    val body: String,
    val author: String,
    val date: LocalDate,
    val type: LinkType,
    val categories: List<String> = listOf(),
    val features: List<ArticleFeature> = listOf(highlightjs),
    val description: String = "",
    val filename: String = "",
    val lang: LanguageCodes = EN,
    val enclosure: Enclosure? = null
)

data class Enclosure(
    val url: String,
    val size: Int
)

@DslMarker
annotation class LinkDSL

@LinkDSL
fun category(name: String, config: Category.() -> Unit): Category {
    return Category(name = name, subcategories = mutableListOf()).apply {
        config(this)
    }
}

@LinkDSL
fun Category.subcategory(name: String, config: Subcategory.() -> Unit) {
    val subcategory = Subcategory(name = name, links = mutableListOf())
    config(subcategory)
    this.subcategories.add(subcategory)
}

@LinkDSL
fun Subcategory.link(config: LinkBuilder.() -> Unit) {
    val linkBuilder = LinkBuilder()
    config(linkBuilder)
    this.links.add(linkBuilder.toLink())
}

class LinkBuilder {
    var name: String = ""
    var href: String = ""
    var desc: String? = null
    var platforms: Array<TargetType> = arrayOf(JVM)
    var type: LinkType = LinkType.none
    var tags: Array<String> = arrayOf()

    var whitelisted: Boolean = false
    fun toLink(): Link {
        return Link(
            name = name,
            href = href,
            desc = desc ?: "",
            platforms = platforms,
            type = type,
            tags = tags,
            whitelisted = whitelisted
        )
    }
}

object Tags {
    inline operator fun <reified K> get(vararg items: K) = arrayOf(*items)
}

