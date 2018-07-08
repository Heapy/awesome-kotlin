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
      name = "cbedoy/DYUM"
      desc = "Basic Kotlin Implementation following MVP, and using Third Party library by Natural Analytics Language"
      href = "https://github.com/cbedoy/DYUM"
      type = github
      tags = Tags["natural language", "third_party", "mvp", "demo", "basic kotlin", "retrofit"]
    }
    link {
      name = "eddywm/KTFLITE"
      desc = "Computer Vision on Android with Kotlin and Tensorflow Lite."
      href = "https://github.com/eddywm/KTFLITE"
      type = github
      tags = Tags["tensorflow", "deep-learning", "computer-vision", "android", "machine-learning"]
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
    link {
      name = "mixitconf/mixit"
      desc = "MiXiT website"
      href = "https://github.com/mixitconf/mixit"
      type = github
      tags = Tags["web", "gradle", "spring"]
    }
    link {
      name = "derveloper/kiny"
      desc = "Your super simple private serverless service running your kotlin functions like AWS lambda."
      href = "https://github.com/derveloper/kiny"
      type = github
      tags = Tags["serverless", "lambda"]
    }
    link {
      name = "ssouris/petclinic-spring5-reactive"
      desc = "Spring 5 Reactive Petclinic app written in Kotlin."
      href = "https://github.com/ssouris/petclinic-spring5-reactive"
      type = github
      tags = Tags["spring", "reactive", "web"]
    }
    link {
      name = "yyunikov/spring-boot-2-kotlin-starter"
      desc = "Spring Boot 2.0 Kotlin application starter with configurations for Gradle, Mongo, JUnit 5 tests, logging, CircleCI and Docker compose."
      href = "https://github.com/yyunikov/spring-boot-2-kotlin-starter"
      type = github
      tags = Tags["spring", "gradle", "reactive", "web", "spring-boot", "docker", "junit-test"]
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
      name = "quicktype/quicktype"
      desc = "Generate Kotlin types and converters from JSON, Schema, TypeScript, and GraphQL"
      href = "https://github.com/quicktype/quicktype"
      type = github
      tags = Tags["json", "api", "web"]
    }
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
    link {
      name = "sureshg/InstallCerts"
      desc = "Create a PKCS12 TrustStore by retrieving server certificates."
      href = "https://github.com/sureshg/InstallCerts"
      type = github
      tags = Tags["certificate", "crypto"]
    }
    link {
      name = "vicboma1/GameBoyEmulatorEnvironment"
      desc = "Front-End developed with Kotlin Experimental for my GameBoy Emulator (coroutines)."
      href = "https://github.com/vicboma1/GameBoyEmulatorEnvironment"
      type = github
      tags = Tags["GameBoy", "Emulator", "frontend", "kotlin", "experimental"]
    }
    link {
      name = "borisf/classyshark-bytecode-viewer"
      desc = "View your Kotlin generated classes as Java and bytecode format."
      href = "https://github.com/borisf/classyshark-bytecode-viewer"
      type = github
      tags = Tags["bytecode", "decompiler"]
    }
    link {
      name = "jenkinsci/doktor-plugin"
      desc = "Jenkins plugin for automated documentation uploading to Confluence."
      href = "https://github.com/jenkinsci/doktor-plugin"
      type = github
      tags = Tags["jenkins", "documentation", "confluence"]
    }
    link {
      name = "mkobit/jenkins-pipeline-shared-libraries-gradle-plugin"
      desc = "Gradle plugin to help with build and test of Jenkins Pipeline Shared Libraries."
      href = "https://github.com/mkobit/jenkins-pipeline-shared-libraries-gradle-plugin"
      type = github
      tags = Tags["jenkins", "gradle"]
    }
    link {
      name = "kpspemu/kpspemu"
      desc = "Multiplatform (JS and JVM) PSP Emulator written in Kotlin."
      href = "https://github.com/kpspemu/kpspemu"
      type = github
      tags = Tags["Emulator", "Kotlin/JS", "Kotlin/JVM"]
    }
    link {
      name = "chrislo27/RhythmHeavenRemixEditor"
      desc = "An audio custom remix editor designed for the Rhythm Heaven series, using libGDX and Kotlin."
      href = "https://github.com/chrislo27/RhythmHeavenRemixEditor"
      type = github
      tags = Tags["libgdx", "Rhythm Heaven", "custom remix"]
    }
    link {
      name = "Poweranimal/PowerCollections"
      desc = "Powerfull Collections, Sets, Lists and Maps."
      href = "https://github.com/Poweranimal/PowerCollections"
      type = github
      tags = Tags["collections", "maps", "lists", "sets", "weakreference", "observable", "bounded"]
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
      name = "vicboma1/Kotlin-Koans"
      desc = "hese are the simple solutions of the kotlin koans online with intellij project."
      href = "https://github.com/vicboma1/Kotlin-Koans"
      type = github
      tags = Tags["examples"]
    }
    link {
      name = "vicboma1/GettingStartedKotlin"
      desc = "Learn the basics of getting started with kotlin."
      href = "https://github.com/vicboma1/GettingStartedKotlin"
      type = github
      tags = Tags["examples"]
    }
    link {
      name = "vicboma1/Kotlin-Examples-Problems"
      desc = "These are the simple solutions of the kotlin example problems online."
      href = "https://github.com/vicboma1/Kotlin-Examples-Problems"
      type = github
      tags = Tags["examples"]
    }
    link {
      name = "pakoito/FunctionalAndroidReference"
      desc = "A different Android app showcasing Functional Reactive Programming."
      href = "https://github.com/pakoito/FunctionalAndroidReference"
      type = github
      tags = Tags["android", "examples", "functional"]
    }
    link {
      name = "bmaslakov/kotlin-algorithm-club"
      desc = "Classic algorithms and data structures in Kotlin."
      href = "https://github.com/bmaslakov/kotlin-algorithm-club"
      type = github
      tags = Tags["examples"]
    }
    link {
      name = "gyulavoros/kotlin-todomvc"
      desc = "Kotlin TodoMVC – full-stack Kotlin application demo"
      href = "https://github.com/gyulavoros/kotlin-todomvc"
      type = github
      tags = Tags["examples", "javascript", "web", "gradle", "todomvc"]
    }
    link {
      name = "mkraynov/todomvc-react-kotlin"
      desc = "React Kotlin TodoMVC – example based on create-react-kotlin-app and todomvc-react"
      href = "https://github.com/mkraynov/todomvc-react-kotlin"
      type = github
      tags = Tags["examples", "javascript", "web", "react", "todomvc"]
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
    link {
      name = "izhangzhihao/intellij-rainbow-brackets"
      desc = "Rainbow Brackets / Rainbow Parentheses for IntelliJ IDEA based IDEs."
      href = "https://github.com/izhangzhihao/intellij-rainbow-brackets"
      type = github
      tags = Tags["idea", "plugin"]
    }
  }
}
