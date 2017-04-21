import link.kotlin.scripts.LinkType
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Kotlin Native") {
  subcategory("Projects") {
    link {
      name = "perses-games/konan-sfml"
      desc = "Kotlin native with SFML example"
      href = "https://github.com/perses-games/konan-sfml"
      type = LinkType.github
      tags = Tags["sfml", "linux", "native"]
    }
  }
}
