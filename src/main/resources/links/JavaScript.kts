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
    link {
      name = "Kotlin/kotlin-fullstack-sample"
      desc = "Kotlin Full-stack Application Example."
      href = "https://github.com/Kotlin/kotlin-fullstack-sample"
      type = github
      tags = Tags["fullstack", "javascript", "web", "ktor"]
    }
    link {
      name = "danfma/kodando"
      desc = "Kotlin JS bindings and libraries."
      href = "https://github.com/danfma/kodando"
      type = github
      tags = Tags["bindings", "react", "mobx", "rxjs"]
    }
    link {
      name = "kengorab/kotlin-javascript-boilerplate"
      desc = "An extremely barebones boilerplate project for compiling Kotlin to Javascript."
      href = "https://github.com/kengorab/kotlin-javascript-boilerplate"
      type = github
      tags = Tags["boilerplate", "javascript", "kotlin2js"]
    }
    link {
      name = "markaren/three.kt"
      desc = "Kotlin wrappers for three.js JavaScript 3D library"
      href = "https://github.com/markaren/three.kt"
      type = github
      tags = Tags["web", "javascript", "kotlin-js", "three-js"]
    }
    link {
      name = "rjaros/kvision"
      desc = "Object oriented Web UI framework for Kotlin/JS"
      href = "https://github.com/rjaros/kvision"
      type = github
      tags = Tags["web", "javascript", "kotlin-js", "framework"]
    }
    link {
      name = "nosix/vue-kotlin"
      desc = "Libraries and tools supporting the use of Vue.js in Kotlin"
      href = "https://github.com/nosix/vue-kotlin"
      type = github
      tags = Tags["vuejs2", "javascript", "kotlin-js", "gradle-plugin", "ui"]
    }
    link {
        name = "Kabbura/Kunafa"
        desc = "Easy to use, high level framework in Kotlin for front-end web-development and creating web apps without using HTML, CSS, or JavaScript."
        href = "https://github.com/Kabbura/Kunafa"
        type = github
        tags = Tags["web", "javascript", "kotlin-js"]
    }
    link {
      name = "Foso/MpApt"
      desc = "Kotlin Native/JS/JVM Annotation Processor library"
      href = "https://github.com/Foso/MpApt"
      type = github
    }
    link {
      name = "fortytwoapps/kstitch"
      desc = "An object oriented toolkit for MongoDB Stitch in KotlinJS."
      href = "https://github.com/fortytwoapps/kstitch"
      type = github
    }
  }
  subcategory("Frontend") {
    link {
      name = "olegcherr/Aza-Kotlin-CSS"
      desc = "Kotlin DSL for CSS"
      href = "https://github.com/olegcherr/Aza-Kotlin-CSS"
      type = github
      tags = Tags["web", "css"]
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
  subcategory("Build Tools") {
    link {
      name = "Kotlin/kotlin-frontend-plugin"
      desc = "Gradle Kotlin plugin for frontend development."
      href = "https://github.com/Kotlin/kotlin-frontend-plugin"
      type = github
      tags = Tags["webpack", "npm", "gradle", "karma", "javascript"]
    }
    link {
      name = "huston007/kotlin-loader"
      desc = "Kotlin webpack loader."
      href = "https://github.com/huston007/kotlin-loader"
      type = github
      tags = Tags["webpack", "javascript"]
    }
  }
  subcategory("Integration") {
    link {
      name = "kotlin/ts2kt"
      desc = "Converter of TypeScript definition files to Kotlin declarations (stubs)."
      href = "https://github.com/kotlin/ts2kt"
      type = github
      tags = Tags["javascript", "typescript"]
    }
  }
}
