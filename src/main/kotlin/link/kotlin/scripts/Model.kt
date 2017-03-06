package link.kotlin.scripts

enum class LinkType {
    none,
    github,
    bitbucket,
    blog,
    kug
}

data class Link(val name: String,
                val href: String = "",
                val desc: String = "",
                val type: LinkType = LinkType.none,
                val tags: Array<String> = arrayOf(),
                val whitelisted: Boolean = false)

data class Subcategory(val id: String? = null,
                       val name: String,
                       val links: MutableList<Link>) {
  operator fun Link.unaryPlus() = links.add(this)
}

data class Category(val id: String? = null,
                    val name: String,
                    val subcategories: MutableList<Subcategory>) {
  operator fun Subcategory.unaryPlus() = subcategories.add(this)
}

enum class ArticleFeature {
    mathjax
}

enum class ArticleType {
    video,
    slides,
    webinar
}

data class Article(val title: String,
                   val url: String,
                   val categories: List<String>,
                   val features: List<ArticleFeature>,
                   val author: String,
                   val date: String,
                   val description: String,
                   val filename: String,
                   val file: String, // input filename
                   val type: ArticleType,
                   var prev: String,
                   var next: String
)

