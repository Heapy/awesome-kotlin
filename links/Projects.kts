import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Projects") {
  subcategory("Web") {
    link {
      name = "sdeleuze/spring-boot-kotlin-demo"
      desc = "Basic Spring Boot app in Kotlin."
      href = "https://github.com/sdeleuze/spring-boot-kotlin-demo"
      type = github
      tags = Tags["spring", "spring-boot"]
    }
    link {
      name = "IRus/kotlin-dev-proxy"
      desc = "Simple server for proxy requests and host static files written in Kotlin, Spark Java and Apache HttpClient."
      href = "https://github.com/IRus/kotlin-dev-proxy"
      type = github
      tags = Tags["rest", "web"]
    }
    link {
      name = "ratpack/example-ratpack-gradle-kotlin-app"
      desc = "An example of a Kotlin Ratpack app built with Gradle."
      href = "https://github.com/ratpack/example-ratpack-gradle-kotlin-app"
      type = github
      tags = Tags["ratpack", "gradle", "rest", "web"]
    }
    link {
      name = "mariomac/codebuilder"
      desc = "Demo app about asynchronous architectures for long-response-time web applications."
      href = "https://github.com/mariomac/codebuilder"
      type = github
      tags = Tags["vertx.io", "async", "example"]
    }
    link {
      name = "rocketraman/kotlin-web-hello-world"
      desc = "This project shows how to do a web-based \"Hello World!\" with Kotlin in combination with various JVM -" +
        " based web frameworks."
      href = "https://github.com/rocketraman/kotlin-web-hello-world"
      type = github
      tags = Tags["spark java", "vertx", "wasabi", "ktor", "akka", "example"]
    }
    link {
      name = "ivanpopelyshev/vertx-facebook-messenger"
      desc = "Seed project for facebook messenger bots. Vertx, Kotlin."
      href = "https://github.com/ivanpopelyshev/vertx-facebook-messenger"
      type = github
      tags = Tags["chat bot", "vert.x", "facebook"]
    }
    link {
      name = "corda/corda"
      desc = "Corda is a distributed ledger platform designed to record, manage and automate legal agreements between business partners. "
      href = "https://github.com/corda/corda"
      type = github
      tags = Tags["p2p", "blockchain"]
    }
    link {
      name = "spolnik/JAlgoArena"
      desc = "JAlgoArena is a highly scalable programming contest platform which you can host on own infrastructure. It allows to define new problems and solve them in Kotlin and Java."
      href = "https://github.com/spolnik/JAlgoArena"
      type = github
      tags = Tags["programming", "contest"]
    }
  }
  subcategory("Build tools") {
    link {
      name = "cbeust/kobalt"
      desc = "Build system inspired by Gradle."
      href = "https://github.com/cbeust/kobalt"
      type = github
    }
    link {
      name = "gradle/gradle-script-kotlin"
      desc = "Kotlin language support for Gradle build scripts."
      href = "https://github.com/gradle/gradle-script-kotlin"
      type = github
      tags = Tags["gradle", "build"]
    }
    link {
      name = "nebula-plugins/nebula-kotlin-plugin"
      desc = "Provides the Kotlin plugin via the Gradle plugin portal, automatically depends on the standard library, and allows Kotlin library versions to be omitted."
      href = "https://github.com/nebula-plugins/nebula-kotlin-plugin"
      type = github
      tags = Tags["gradle", "build"]
    }
    link {
      name = "pubref/rules_kotlin"
      desc = "Bazel rules for Kotlin."
      href = "https://github.com/pubref/rules_kotlin"
      type = github
      tags = Tags["bazel", "build"]
    }
  }
  subcategory("Misc") {
    link {
      name = "brikk/brikk"
      desc = "Brikk dependency manager (Kotlin, KotlinJS, Java, ...)."
      href = "https://github.com/brikk/brikk"
      type = github
      tags = Tags["dependency managment"]
    }
    link {
      name = "lice-lang/lice"
      desc = "A Lisp-like language's interpreter written in Kotlin"
      href = "https://github.com/lice-lang/lice"
      type = github
      tags = Tags["lisp", "interpreter"]
    }
  }
  subcategory("Desktop") {
    link {
      name = "ice1000/Dekoder"
      desc = " A kotlin music player, materially designed."
      href = "https://github.com/ice1000/Dekoder"
      type = github
      tags = Tags["JavaFX", "Desktop", "Player"]
    }
  }
  subcategory("Examples") {
    link {
      name = "Kotlin/kotlin-koans"
      desc = "Kotlin Koans are a series of exercises to get you familiar with the Kotlin Syntax."
      href = "https://github.com/Kotlin/kotlin-koans"
      type = github
      tags = Tags["koans"]
    }
    link {
      name = "JetBrains/kotlin-examples"
      desc = "Various examples for Kotlin."
      href = "https://github.com/JetBrains/kotlin-examples"
      type = github
      tags = Tags["maven", "gradle", "android", "realm", "buttetknife", "dagger", "dbflow", "junit-test", "dokka"]
    }
    link {
      name = "JetBrains/swot"
      desc = "Identify email addresses or domains names that belong to colleges or universities. Help automate the process of approving or rejecting academic discounts."
      href = "https://github.com/jetbrains/swot"
      type = github
    }
    link {
      name = "robfletcher/midcentury-ipsum"
      desc = "Swingin’ filler text for your jet-age web page."
      href = "https://github.com/robfletcher/midcentury-ipsum"
      type = github
      tags = Tags["ratpack"]
    }
    link {
      name = "robfletcher/lazybones-kotlin"
      desc = "The Lazybones app migrated to Kotlin as a learning exercise."
      href = "https://github.com/robfletcher/lazybones-kotlin"
      type = github
      tags = Tags["ratpack"]
    }
    link {
      name = "wangjiegulu/KotlinAndroidSample"
      desc = "Android sample with kotlin."
      href = "https://github.com/wangjiegulu/KotlinAndroidSample"
      type = github
      tags = Tags["android", "sample"]
    }
    link {
      name = "dodyg/Kotlin101"
      desc = "101 examples for Kotlin Programming language."
      href = "https://github.com/dodyg/Kotlin101"
      type = github
      tags = Tags["examples"]
    }
    link {
      name = "dkandalov/kotlin-99"
      desc = "Solve 99 problems with Kotlin!"
      href = "https://github.com/dkandalov/kotlin-99"
      type = github
      tags = Tags["examples", "study"]
    }
    link {
      name = "dkandalov/rosettacode-kotlin"
      desc = "Repository with source code from [RosettaCode](http://rosettacode.org/)"
      href = "https://github.com/dkandalov/rosettacode-kotlin"
      type = github
      tags = Tags["examples", "study"]
    }
    link {
      name = "sanity/pairAdjacentViolators"
      desc = "A Kotlin implementation of the Pair Adjacent Violators algorithm for isotonic regression."
      href = "https://github.com/sanity/pairAdjacentViolators"
      type = github
      tags = Tags["examples"]
    }
  }
  subcategory("Idea Plugins") {
    link {
      name = "Vektah/CodeGlance"
      desc = "Intelij IDEA plugin for displaying a code mini-map similar to the one found in Sublime."
      href = "https://github.com/Vektah/CodeGlance"
      type = github
      tags = Tags["idea", "plugin"]
    }
    link {
      name = "intellij-rust/intellij-rust"
      desc = "Rust IDE built using the IntelliJ Platform."
      href = "https://github.com/intellij-rust/intellij-rust"
      type = github
      tags = Tags["idea", "plugin", "rust"]
    }
    link {
      name = "dkandalov/activity-tracker"
      desc = "Plugin for IntelliJ IDEs to track and record user activity."
      href = "https://github.com/dkandalov/activity-tracker"
      type = github
      tags = Tags["idea", "plugin"]
    }
  }
}
