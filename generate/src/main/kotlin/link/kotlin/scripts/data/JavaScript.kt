package link.kotlin.scripts.data

import link.kotlin.scripts.LinkType.github

val javascript = category("Kotlin JavaScript") {
  subcategory("JavaScript") {
    link {
      name = "andrewoma/reakt"
      desc = "Reakt is a Kotlin wrapper for facebook's React library."
      href = "https://github.com/andrewoma/reakt"
      type = github
      tags = arrayOf("react", "javascript", "ui")
    }
    link {
      name = "pixijs/pixi-native"
      desc = "The aim of this project is to provide a fast lightweight 2D library that works across all devices."
      href = "https://github.com/pixijs/pixi-native"
      type = github
      tags = arrayOf("javascript", "2d", "canvas", "WebGL")
    }
    link {
      name = "bashor/ts2kt"
      desc = "Converter of TypeScript definition files to Kotlin declarations (stubs)"
      href = "https://github.com/bashor/ts2kt"
      type = github
      tags = arrayOf("javascript", "typescript")
    }
    link {
      name = "shafirov/klogging"
      desc = "Kotlin logging, both js and jvm."
      href = "https://github.com/shafirov/klogging"
      type = github
      tags = arrayOf("javascript", "logging")
    }
  }
  subcategory("Frontend") {
    link {
      name = "olegcherr/Aza-Kotlin-CSS"
      desc = "Kotlin DSL for CSS"
      href = "https://github.com/olegcherr/Aza-Kotlin-CSS"
      type = github
      tags = arrayOf("css")
    }
  }
}
