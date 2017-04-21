import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Kotlin JavaScript") {
  subcategory("JavaScript") {
    link {
      name = "andrewoma/reakt"
      desc = "Reakt is a Kotlin wrapper for facebook's React library."
      href = "https://github.com/andrewoma/reakt"
      type = github
      tags = Tags["react", "javascript", "ui"]
    }
    link {
      name = "pixijs/pixi-native"
      desc = "The aim of this project is to provide a fast lightweight 2D library that works across all devices."
      href = "https://github.com/pixijs/pixi-native"
      type = github
      tags = Tags["javascript", "2d", "canvas", "WebGL"]
    }
    link {
      name = "bashor/ts2kt"
      desc = "Converter of TypeScript definition files to Kotlin declarations (stubs)"
      href = "https://github.com/bashor/ts2kt"
      type = github
      tags = Tags["javascript", "typescript"]
    }
    link {
      name = "shafirov/klogging"
      desc = "Kotlin logging, both js and jvm."
      href = "https://github.com/shafirov/klogging"
      type = github
      tags = Tags["javascript", "logging"]
    }
    link {
      name = "stangls/kotlin-js-jquery"
      desc = "A small framework for writing client -side web -applications in Kotlin."
      href = "https://github.com/stangls/kotlin-js-jquery"
      type = github
      tags = Tags["javascript", "jquery"]
    }
  }
  subcategory("Frontend") {
    link {
      name = "olegcherr/Aza-Kotlin-CSS"
      desc = "Kotlin DSL for CSS"
      href = "https://github.com/olegcherr/Aza-Kotlin-CSS"
      type = github
      tags = Tags["css"]
    }
  }
  subcategory("Game Development") {
    link {
      name = "perses-games/kudens"
      desc = "Develop browser games in Kotlin"
      href = "https://github.com/perses-games/kudens"
      type = github
      tags = Tags["games", "game dev", "javascript"]
    }
  }
}
