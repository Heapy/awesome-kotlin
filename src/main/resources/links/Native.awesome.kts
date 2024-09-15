category("Kotlin Native") {
  subcategory("Projects") {
    link {
      github = "JetBrains/kotlin-native"
      desc = "Kotlin/Native is a LLVM backend for the Kotlin compiler, runtime implementation and native code generation facility using LLVM toolchain."
      setTags("native", "macos", "linux", "llvm")
      awesome()
    }
    link {
      github = "perses-games/konan-sfml"
      desc = "Kotlin native with SFML example"
      setTags("sfml", "linux", "native")
    }
    link {
      github = "JetBrains/kotlinconf-spinner"
      desc = "Simple spinner-like game intended to demonstrate capabilities of Kotlin/Native software stack."
      setTags("native", "llvm", "android", "ios", "backend", "linux")
    }
    link {
      github = "trueangle/kotlin-native-aws-lambda-runtime"
      desc = "A runtime for executing AWS Lambda functions written in Kotlin/Native, designed to reduce cold start issues common with the JVM platform."
      setTags("native", "linux")
    }
  }
  subcategory("Frameworks") {
    link {
      github = "KwangIO/kwang"
      setTags("native", "backend", "linux", "C", "high-performance")
    }
    link {
      github = "msink/kotlin-libui"
      setTags("native", "GUI", "C", "portable", "desktop", "libui")
    }
  }
}
