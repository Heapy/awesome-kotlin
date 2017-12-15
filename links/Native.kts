
import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Kotlin Native") {
  subcategory("Projects") {
    link {
      name = "JetBrains/kotlin-native"
      desc = "Kotlin/Native is a LLVM backend for the Kotlin compiler, runtime implementation and native code generation facility using LLVM toolchain."
      href = "https://github.com/JetBrains/kotlin-native"
      type = github
      tags = Tags["native", "macos", "linux", "llvm"]
    }
    link {
      name = "perses-games/konan-sfml"
      desc = "Kotlin native with SFML example"
      href = "https://github.com/perses-games/konan-sfml"
      type = github
      tags = Tags["sfml", "linux", "native"]
    }
    link {
      name = "JetBrains/kotlinconf-spinner"
      desc = "Simple spinner-like game intended to demonstrate capabilities of Kotlin/Native software stack."
      href = "https://github.com/JetBrains/kotlinconf-spinner"
      type = github
      tags = Tags["native", "llvm", "android", "ios", "backend", "linux"]
    }
  }
}
