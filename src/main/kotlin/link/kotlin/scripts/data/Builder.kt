package link.kotlin.scripts.data

import link.kotlin.scripts.Category
import link.kotlin.scripts.Link
import link.kotlin.scripts.LinkType
import link.kotlin.scripts.Subcategory

fun category(name: String, config: Category.() -> Unit): Category {
  return Category(name = name, subcategories = mutableListOf()).apply {
    config(this)
  }
}

fun Category.subcategory(name: String, config: Subcategory.() -> Unit) {
  val subcategory = Subcategory(name = name, links = mutableListOf())
  config(subcategory)
  this.subcategories.add(subcategory)
}

fun Subcategory.link(config: LinkBuilder.() -> Unit) {
  val linkBuilder = LinkBuilder()
  config(linkBuilder)
  this.links.add(linkBuilder.toLink())
}

class LinkBuilder {
  var name: String = ""
  var href: String = ""
  var desc: String? = null
  var type: LinkType = LinkType.none
  var tags: Array<String> = arrayOf()

  var whitelisted: Boolean = false
  fun toLink(): Link {
    return Link(
      name = name,
      href = href,
      desc = desc ?: "",
      type = type,
      tags = tags,
      whitelisted = whitelisted
    )
  }
}

object Tags {
  inline operator fun <reified K> get(vararg items: K) = arrayOf(*items)
}
