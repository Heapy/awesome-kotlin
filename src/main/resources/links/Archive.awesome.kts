/**
 * Instead of deleting links (since they become 404, etc) move them here.
 */
category("Archive") {
  subcategory("Android") {
    link {
      github = "0xe1f/KotX"
      desc = "Kotlin extension and tool library for Android"
    }
    link {
      github = "Kotlin/anko"
      desc = "Pleasant Android application development."
    }
  }
  subcategory("Resources") {
    link {
      name = "Quora Kotlin"
      href = "https://www.quora.com/topic/Kotlin"
    }
    link {
      name = "Stackoverflow Documentation on Kotlin"
      href = "https://stackoverflow.com/documentation/kotlin/topics"
      setTags("documentation", "stackoverflow")
    }
  }
  subcategory("Libraries") {
    link {
      github = "s4kibs4mi/PultusORM"
      desc = "PultusORM is a sqlite ORM library for kotlin on top of sqlite jdbc driver."
      setTags("database", "query", "sqlite")
    }
  }
  subcategory("Build Tools") {
    link {
      github = "brikk/brikk"
      desc = "Brikk dependency manager (Kotlin, KotlinJS, Java, ...)."
      setTags("dependency management")
    }
  }
  subcategory("Everything") {
    link {
      name = "Public chat archive of Kotlin's Slack"
      href = "https://kotlinlang.slackarchive.io/"
    }
    link {
      github = "Kategory/Kategory"
      desc = "Functional datatypes & abstractions for Kotlin."
    }
    link {
      github = "MarioAriasC/funKTionale"
      desc = "Functional constructs for Kotlin."
    }
    link {
      github = "SalomonBrys/Kodein"
      desc = "Painless Kotlin Dependency Injection."
    }
    link {
      github = "kohesive/injekt"
      desc = "(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin."
    }
    link {
      github = "raniejade/kspec"
      desc = "Kotlin Specification Framework."
    }
    link {
      github = "Levelmoney/kbuilders"
      desc = "KBuilders turns your Java builders into beautiful Type-Safe Builders."
    }
    link {
      github = "griffon/griffon-kotlin-plugin"
      desc = "Griffon Support"
    }
    link {
      github = "danfma/kotlinjs-react"
      desc = "A react wrapper to the kotlin library."
    }
  }
}
