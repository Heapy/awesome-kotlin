import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.LinkType.kug
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

/**
 * Instead of deleting links (since they become 404, etc) move them here.
 */
category("Archive") {
  subcategory("Everything") {
    link {
      name = "Public chat archive of Kotlin's Slack"
      href = "http://kotlinlang.slackarchive.io/"
    }
    link {
      name = "Kategory/Kategory"
      desc = "Functional datatypes & abstractions for Kotlin."
      href = "https://github.com/Kategory/Kategory"
      type = github
      tags = Tags["fp", "functional", "typeclasses", "comprehensions", "lenses", "optics"]
    }
    link {
      name = "MarioAriasC/funKTionale"
      desc = "Functional constructs for Kotlin."
      href = "https://github.com/MarioAriasC/funKTionale"
      type = github
      tags = Tags["fp", "functional"]
    }
    link {
      name = "SalomonBrys/Kodein"
      desc = "Painless Kotlin Dependency Injection ."
      href = "https://github.com/SalomonBrys/Kodein"
      type = github
      tags = Tags["di", "dependency injection"]
    }
    link {
      name = "kohesive/injekt"
      desc = "(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin."
      href = "https://github.com/kohesive/injekt"
      type = github
      tags = Tags["di", "dependency injection"]
    }
    link {
      name = "raniejade/kspec"
      desc = "Kotlin Specification Framework."
      href = "https://github.com/raniejade/kspec"
      type = github
      tags = Tags["test", "bdd"]
    }
    link {
      name = "Levelmoney/kbuilders"
      desc = "KBuilders turns your Java builders into beautiful Type-Safe Builders."
      href = "https://github.com/Levelmoney/kbuilders"
      type = github
    }
    link {
      name =  "griffon/griffon-kotlin-plugin"
      desc = "Griffon Support"
      href = "https://github.com/griffon/griffon-kotlin-plugin"
      type = github
      tags = Tags["griffon"]
    }
    link {
      name = "Kotlin/anko"
      desc = "Pleasant Android application development."
      href = "https://github.com/Kotlin/anko"
      type = github
      tags = Tags["android"]
    }
    link {
      name = "danfma/kotlinjs-react"
      desc = "A react wrapper to the kotlin library."
      href = "https://github.com/danfma/kotlinjs-react"
      type = github
      tags = Tags["react", "javascript", "ui"]
    }
  }
}
