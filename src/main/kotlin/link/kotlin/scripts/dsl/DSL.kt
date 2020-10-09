package link.kotlin.scripts.dsl

import link.kotlin.scripts.dsl.ArticleFeature.highlightjs
import link.kotlin.scripts.dsl.LanguageCodes.EN
import java.time.LocalDate
import link.kotlin.scripts.model.Link

enum class PlatformType {
    ANDROID,
    COMMON,
    IOS,
    JS,
    JVM,
    NATIVE,
    WASM
}

data class Subcategory(
    val name: String,
    val links: MutableList<Link>
) {
    operator fun Link.unaryPlus() = links.add(this)
}

data class Category(
    val name: String,
    val subcategories: MutableList<Subcategory>
) {
    operator fun Subcategory.unaryPlus() = subcategories.add(this)
}

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
    var name: String? = null
    var href: String? = null
    var desc: String? = null

    var github: String? = null
    var bitbucket: String? = null
    var kug: String? = null

    private var awesome: Boolean = false
    @LinkDSL
    fun awesome() {
        this.awesome = true
    }

    private var platforms: List<PlatformType> = emptyList()
    @LinkDSL
    fun setPlatforms(vararg platforms: PlatformType) {
        this.platforms = platforms.toList()
    }

    private var tags: List<String> = emptyList()
    @LinkDSL
    fun setTags(vararg tags: String) {
        this.tags = tags.toList()
    }

    fun toLink(): Link {
        return Link(
            name = name,
            href = href,
            desc = desc,
            platforms = platforms,
            tags = tags,
            bitbucket = bitbucket,
            github = github,
            kug = kug,
            awesome = awesome
        )
    }
}

// Articles

enum class ArticleFeature {
    mathjax,
    highlightjs
}

enum class LinkType {
    article,
    video,
    slides,
    webinar
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

/**
 * List of languages names - codes according ISO 639-1
 * https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
enum class LanguageCodes(val id: String) {
    EN("english"),
    RU("russian"),
    IT("italian"),
    ZH("chinese"),
    HE("hebrew");

    companion object {
        fun contains(language: String) =
            values().map(LanguageCodes::id).contains(language)
    }
}
