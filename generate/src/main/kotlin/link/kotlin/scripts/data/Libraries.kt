package link.kotlin.scripts.data

import link.kotlin.scripts.LinkType.bitbucket
import link.kotlin.scripts.LinkType.github

val libraries = category("Libraries/Frameworks") {
  subcategory("Web") {
    link {
      name = "Kotlin/ktor"
      desc = "Web backend framework for Kotlin."
      href = "https://github.com/Kotlin/ktor"
      type = github
      tags = arrayOf("web")
    }
    link {
      name = "TinyMission/kara"
      desc = "Web framework written in Kotlin."
      href = "https://github.com/TinyMission/kara"
      type = github
      tags = arrayOf("web")
    }
    link {
      name = "jean79/yested"
      desc = "A Kotlin framework for building web applications in Javascript."
      href = "https://github.com/jean79/yested"
      type = github
      tags = arrayOf("web")
    }
    link {
      name = "hhariri/wasabi"
      desc = "An HTTP Framework built with Kotlin for the JVM."
      href = "https://github.com/wasabifx/wasabi"
      type = github
      tags = arrayOf("web")
    }
    link {
      name = "Kotlin/kotlinx.html"
      desc = "Kotlin DSL for HTML."
      href = "https://github.com/Kotlin/kotlinx.html"
      type = github
      tags = arrayOf("html")
    }
    link {
      name = "MarioAriasC/KotlinPrimavera"
      desc = "Spring support libraries for Kotlin."
      href = "https://github.com/MarioAriasC/KotlinPrimavera"
      type = github
      tags = arrayOf("spring")
    }
    link {
      name = "kohesive/kovert"
      desc = "An invisible, super easy and powerful REST and Web framework over Vert.x or Undertow."
      href = "https://github.com/kohesive/kovert"
      type = github
      tags = arrayOf("web", "http", "rest", "vert.x", "undertow")
    }
    link {
      name = "sdeleuze/spring-kotlin"
      desc = "Kotlin extensions for Spring projects."
      href = "https://github.com/sdeleuze/spring-kotlin"
      type = github
      tags = arrayOf("spring", "extensions")
    }
    link {
      name = "Kotlin/kotlinx.coroutines"
      desc = "Libraries built upon Kotlin coroutines."
      href = "https://github.com/Kotlin/kotlinx.coroutines"
      type = github
      tags = arrayOf("async", "await", "yield", "generator")
    }
    link {
      name = "taskworld/kraph"
      desc = "GraphQL request string builder written in Kotlin"
      href = "https://github.com/taskworld/kraph"
      type = github
      tags = arrayOf("graphql", "builder")
    }
    link {
      name = "sepatel/tekniq"
      desc = "Full-feature HTTP DSL Framework, HTTP Client, JDBC DSL, Loading Cache and Configuration"
      href = "https://github.com/sepatel/tekniq"
      type = github
      tags = arrayOf("web", "jdbc", "http client", "spark java", "cache")
    }
    link {
      name = "vert-x3/vertx-lang-kotlin"
      desc = "This module provides Kotlin language bindings including DSL and extension functions for vert.x 3"
      href = "https://github.com/vert-x3/vertx-lang-kotlin/"
      type = github
      tags = arrayOf("web", "vert.x")
    }
  }
  subcategory("Tests") {
    link {
      name = "JetBrains/spek"
      desc = "A specification framework for Kotlin."
      href = "https://github.com/jetbrains/spek"
      type = github
      tags = arrayOf("test", "assert", "bdd")
    }
    link {
      name = "npryce/hamkrest"
      desc = "A reimplementation of Hamcrest to take advantage of Kotlin language features."
      href = "https://github.com/npryce/hamkrest"
      type = github
      tags = arrayOf("test", "assert")
    }
    link {
      name = "nhaarman/mockito-kotlin"
      desc = "Using Mockito with Kotlin."
      href = "https://github.com/nhaarman/mockito-kotlin"
      type = github
      tags = arrayOf("test", "mock")
    }
    link {
      name = "MarkusAmshove/Kluent"
      desc = "Fluent Assertion-Library for Kotlin."
      href = "https://github.com/MarkusAmshove/Kluent"
      type = github
      tags = arrayOf("test", "assert")
    }
    link {
      name = "winterbe/expekt"
      desc = "BDD assertion library for Kotlin."
      href = "https://github.com/winterbe/expekt"
      type = github
      tags = arrayOf("test", "assert", "bdd")
    }
    link {
      name = "kotlintest/kotlintest"
      desc = "KotlinTest is a flexible and comprehensive testing tool for the Kotlin ecosystem based on and heavily inspired by the superb Scalatest."
      href = "https://github.com/kotlintest/kotlintest"
      type = github
      tags = arrayOf("test", "bdd", "matchers")
    }
    link {
      name = "dmcg/konsent"
      desc = "An acceptance test library for Kotlin."
      href = "https://github.com/dmcg/konsent"
      type = github
      tags = arrayOf("test", "bdd", "gherkin")
    }
    link {
      name = "raniejade/kspec"
      desc = "Kotlin Specification Framework."
      href = "https://github.com/raniejade/kspec"
      type = github
      tags = arrayOf("test", "bdd")
    }
    link {
      name = "EPadronU/balin"
      desc = "Balin is a browser automation library for Kotlin. It's basically a Selenium-WebDriver wrapper library inspired by Geb."
      href = "https://github.com/EPadronU/balin"
      type = github
      tags = arrayOf("test", "selenium", "UI", "automation")
    }
    link {
      name = "dmcg/k-sera"
      desc = "A JMock wrapper for Kotlin."
      href = "https://github.com/dmcg/k-sera"
      type = github
      tags = arrayOf("mock", "test")
    }
    link {
      name = "dam5s/aspen"
      desc = "Aspen is an RSpec and Spek inspired test runner for Kotlin."
      href = "https://github.com/dam5s/aspen"
      type = github
      tags = arrayOf("test", "specification", "rspec", "spek")
    }
  }
  subcategory("Dependency Injection") {
    link {
      name = "SalomonBrys/Kodein"
      desc = "Painless Kotlin Dependency Injection ."
      href = "https://github.com/SalomonBrys/Kodein"
      type = github
      tags = arrayOf("di", "dependency injection")
    }
    link {
      name = "kohesive/injekt"
      desc = "(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin"
      href = "https://github.com/kohesive/injekt"
      type = github
      tags = arrayOf("di", "dependency injection")
    }
  }
  subcategory("Functional Programming") {
    link {
      name = "MarioAriasC/funKTionale"
      desc = "Functional constructs for Kotlin."
      href = "https://github.com/MarioAriasC/funKTionale"
      type = github
      tags = arrayOf("fp", "functional")
    }
    link {
      name = "ReactiveX/RxKotlin"
      desc = "RxJava bindings for Kotlin."
      href = "https://github.com/ReactiveX/RxKotlin"
      type = github
      tags = arrayOf("fp", "functional")
    }
    link {
      name = "kittinunf/Result"
      desc = "The modelling for success/failure of operations in Kotlin."
      href = "https://github.com/kittinunf/Result"
      type = github
      tags = arrayOf("fp", "functional", "monad")
    }
    link {
      name = "brianegan/bansa"
      desc = "A state container for Kotlin & Java, inspired by Elm & Redux."
      href = "https://github.com/brianegan/bansa"
      type = github
      tags = arrayOf("fp", "functional", "UI", "Interface", "Redux")
    }
    link {
      name = "pardom/redux-kotlin"
      desc = "Direct port of Redux for Kotlin."
      href = "https://github.com/pardom/redux-kotlin"
      type = github
      tags = arrayOf("fp", "functional", "UI", "Interface", "Redux")
    }
    link {
      name = "beyondeye/Reduks"
      desc = "A \"batteries included\" port of Reduxjs for Kotlin+Android"
      href = "https://github.com/beyondeye/Reduks"
      type = github
      tags = arrayOf("fp", "functional", "UI", "Interface", "Redux")
    }
    link {
      name = "pakoito/Komprehensions"
      desc = "Do comprehensions for Kotlin and 3rd party libraries."
      href = "https://github.com/pakoito/Komprehensions"
      type = github
      tags = arrayOf("comprehensions", "fp", "functional")
    }
    link {
      name = "h0tk3y/kotlin-monads"
      desc = "Monads for Kotlin"
      href = "https://github.com/h0tk3y/kotlin-monads"
      type = github
      tags = arrayOf("fp", "functional", "monads")
    }
  }
  subcategory("JSON") {
    link {
      name = "cbeust/klaxon"
      desc = "Lightweight library to parse JSON in Kotlin."
      href = "https://github.com/cbeust/klaxon"
      type = github
      tags = arrayOf("json")
    }
    link {
      name = "SalomonBrys/Kotson"
      desc = "Gson for Kotlin, Kotson enables you to parse and write JSON with Google's Gson using a conciser and easier syntax."
      href = "https://github.com/SalomonBrys/Kotson"
      type = github
      tags = arrayOf("json")
    }
    link {
      name = "FasterXML/jackson-module-kotlin"
      desc = "Jackson module that adds support for serialization/deserialization of Kotlin classes and data classes."
      href = "https://github.com/FasterXML/jackson-module-kotlin"
      type = github
      tags = arrayOf("json", "jakson")
    }
    link {
      name = "fboldog/ext4klaxon"
      desc = "Type Extensions (Long, Int, Enum, Date) for Klaxon."
      href = "https://github.com/fboldog/ext4klaxon"
      type = github
      tags = arrayOf("json")
    }
    link {
      name = "Jire/KTON"
      desc = "Object notation in pure Kotlin!"
      href = "https://github.com/Jire/KTON"
      type = github
      tags = arrayOf("JSON", "XML")
    }
  }
  subcategory("Database") {
    link {
      name = "JetBrains/Exposed"
      desc = "Exposed is a prototype for a lightweight SQL library written over JDBC driver for Kotlin language."
      href = "https://github.com/jetbrains/Exposed"
      type = github
      tags = arrayOf("database", "query", "schema", "dao")
    }
    link {
      name = "cheptsov/kotlin-nosql"
      desc = "NoSQL database query and access library for Kotlin."
      href = "https://github.com/cheptsov/kotlin-nosql"
      type = github
      tags = arrayOf("database", "mongodb", "query")
    }
    link {
      name = "jankotek/mapdb"
      desc = "MapDB provides concurrent Maps, Sets and Queues backed by disk storage or off-heap-memory. It is a fast and easy to use embedded Java database engine."
      href = "https://github.com/jankotek/mapdb"
      type = github
    }
    link {
      name = "seratch/kotliquery"
      desc = "A handy database access library in Kotlin."
      href = "https://github.com/seratch/kotliquery"
      type = github
      tags = arrayOf("database", "sql", "query")
    }
    link {
      name = "andrewoma/kwery"
      desc = "Kwery is an SQL library for Kotlin."
      href = "https://github.com/andrewoma/kwery"
      type = github
      tags = arrayOf("database", "sql", "query")
    }
    link {
      name = "square/sqldelight"
      desc = "Generates Java models from CREATE TABLE statements."
      href = "https://github.com/square/sqldelight"
      type = github
      tags = arrayOf("database", "sql", "type-safe builder")
    }
    link {
      name = "x2bool/kuery"
      desc = "Typesafe SQL with Kotlin."
      href = "https://github.com/x2bool/kuery"
      type = github
      tags = arrayOf("database", "sql", "type-safe builder")
    }
    link {
      name = "Litote/kmongo"
      desc = "KMongo - Kotlin toolkit for Mongo"
      href = "https://github.com/Litote/kmongo"
      type = github
      tags = arrayOf("database", "mongodb", "query")
    }
    link {
      name = "requery/requery"
      desc = "Modern SQL based query & persistence for Java/Kotlin/Android."
      href = "https://github.com/requery/requery"
      type = github
      tags = arrayOf("database", "query", "type-safe builder")
    }
    link {
      name = "consoleau/kotlin-jpa-specification-dsl"
      desc = "This library provides a fluent DSL for querying spring data JPA repositories using spring data Specifications."
      href = "https://github.com/consoleau/kotlin-jpa-specification-dsl"
      type = github
      tags = arrayOf("database", "query", "jpa")
    }
    link {
      name = "s4kibs4mi/PultusORM"
      desc = "PultusORM is a sqlite ORM library for kotlin on top of sqlite jdbc driver."
      href = "https://github.com/s4kibs4mi/PultusORM"
      type = github
      tags = arrayOf("database", "query", "sqlite")
    }
    link {
      name = "Ganet/rxaerospike"
      desc = "RxJava2 wrapper for aerospike-client-java."
      href = "https://github.com/Ganet/rxaerospike"
      type = github
      tags = arrayOf("database", "arospike", "rx", "rxjava2")
    }
    link {
      name = "Raizlabs/DBFlow"
      desc = "A blazing fast, powerful, and very simple ORM android database library that writes database code for you."
      href = "https://github.com/Raizlabs/DBFlow"
      type = github
      tags = arrayOf("orm", "jap", "kapt", "database")
    }
    link {
      name = "KotlinPorts/kt-postgresql-async"
      desc = "Kotlin/Gradle port of mauricio's async driver for postgres/mysql."
      href = "https://github.com/KotlinPorts/kt-postgresql-async"
      type = github
      tags = arrayOf("postgres", "mysql", "database driver")
    }
  }
  subcategory("Tools") {
    link {
      name = "Kotlin/dokka"
      desc = "Documentation Engine for Kotlin."
      href = "https://github.com/Kotlin/dokka"
      type = github
    }
    link {
      name = "Levelmoney/kbuilders"
      desc = "KBuilders turns your Java builders into beautiful Type-Safe Builders."
      href = "https://github.com/Levelmoney/kbuilders"
      type = github
    }
    link {
      name = "holgerbrandl/kscript"
      desc = "Scripting utils for Kotlin."
      href = "https://github.com/holgerbrandl/kscript"
      type = github
      tags = arrayOf("bash", "scripting", "kts")
    }
    link {
      name = "shyiko/ktlint"
      desc = "Kotlin linter."
      href = "https://github.com/shyiko/ktlint"
      type = github
      tags = arrayOf("style", "linter")
    }
    link {
      name = "jtransc/jtransc"
      desc = "JVM AOT compiler created in Kotlin."
      href = "https://github.com/jtransc/jtransc"
      type = github
      tags = arrayOf("aot", "compiller")
    }
    link {
      name = "arturbosch/detekt"
      desc = "Static code analysis for Kotlin."
      href = "https://github.com/arturbosch/detekt"
      type = github
      tags = arrayOf("check style", "checkstyle")
    }
    link {
      name = "ligee/kotlin-jupyter"
      desc = "Kotlin kernel for Jupyter/iPython."
      href = "https://github.com/ligee/kotlin-jupyter"
      type = github
      tags = arrayOf("juputer", "repl")
    }
  }
  subcategory("Desktop") {
    link {
      name = "edvin/tornadofx"
      desc = "Lightweight JavaFX Framework for Kotlin/"
      href = "https://github.com/edvin/tornadofx"
      type = github
      tags = arrayOf("javafx", "desktop", "application")
    }
  }
  subcategory("Http Clients") {
    link {
      name = "kittinunf/Fuel"
      desc = "The easiest HTTP networking library for Kotlin/Android."
      href = "https://github.com/kittinunf/Fuel"
      type = github
      tags = arrayOf("http", "http client", "file upload")
    }
    link {
      name = "jkcclemens/khttp"
      desc = "Kotlin HTTP requests library."
      href = "https://github.com/jkcclemens/khttp"
      type = github
      tags = arrayOf("http", "http client")
    }
  }
  subcategory("Editors") {
    link {
      name = "JetBrains/intellij-community"
      desc = "IntelliJ IDEA Community Edition"
      href = "https://github.com/JetBrains/intellij-community"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "alexmt/atom-kotlin-language"
      desc = "Adds syntax highlighting to Kotlin files in Atom"
      href = "https://github.com/alexmt/atom-kotlin-language"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "vkostyukov/kotlin-sublime-package"
      desc = "A Sublime Package for Kotlin."
      href = "https://github.com/vkostyukov/kotlin-sublime-package"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "udalov/kotlin-vim"
      desc = "Kotlin Syntax Highlighter for Vim."
      href = "https://github.com/udalov/kotlin-vim"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "sargunster/kotlin-textmate-bundle"
      desc = "Kotlin bundle for TextMate."
      href = "https://github.com/sargunster/kotlin-textmate-bundle"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "ice1000/NppExtension"
      desc = "Kotlin Language extension for Notepad++"
      href = "https://github.com/ice1000/NppExtension"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
    link {
      name = "ftomassetti/kanvas"
      desc = " A truly hackable editor: simple, lightweight, understandable."
      href = "https://github.com/ftomassetti/kanvas"
      type = github
      tags = arrayOf("editor", "ide", "language")
    }
  }
  subcategory("Syntax Highlighters") {
    link {
      name = "jneen/rouge"
      desc = "A pure-ruby code highlighter that is compatible with pygments. (wip)"
      href = "https://github.com/jneen/rouge"
      type = github
      tags = arrayOf("syntax", "higlight")
    }
    link {
      name = "isagalaev/highlight.js"
      desc = "Javascript syntax highlighter. (out-of-date)"
      href = "https://github.com/isagalaev/highlight.js"
      type = github
      tags = arrayOf("syntax", "higlight")
    }
    link {
      name = "github/linguist"
      desc = "For Ruby/Github, uses Sublime package"
      href = "https://github.com/github/linguist"
      type = github
      tags = arrayOf("syntax", "higlight")
    }
    link {
      name = "birkenfeld/pygments-main"
      desc = "Python syntax highlighter."
      href = "https://bitbucket.org/birkenfeld/pygments-main"
      type = bitbucket
      tags = arrayOf("syntax", "higlight")
    }
  }
  subcategory("Game Development") {
    link {
      name = "czyzby/ktx"
      desc = "Kotlin utilities for LibGDX applications."
      href = "https://github.com/czyzby/ktx"
      type = github
      tags = arrayOf("LibGDX", "game dev")
    }
    link {
      name = "AlmasB/FXGL"
      desc = "JavaFX 8 Game Library written in Java + Kotlin"
      href = "https://github.com/AlmasB/FXGL"
      type = github
      tags = arrayOf("javafx", "desktop", "games")
    }
  }
  subcategory("Misc") {
    link {
      name = "Kotlin/kotlinx.reflect.lite"
      desc = "Lightweight library allowing to introspect basic stuff about Kotlin symbols."
      href = "https://github.com/Kotlin/kotlinx.reflect.lite"
      type = github
      tags = arrayOf("reflection")
    }
    link {
      name = "puniverse/quasar"
      desc = "Fibers, Channels and Actors for the JVM."
      href = "https://github.com/puniverse/quasar/tree/master/quasar-kotlin"
      type = github
    }
    link {
      name = "MehdiK/Humanizer.jvm"
      desc = "Humanizer.jvm meets all your jvm needs for manipulating and displaying strings, enums, dates, times, timespans, numbers and quantities."
      href = "https://github.com/MehdiK/Humanizer.jvm"
      type = github
      tags = arrayOf("pluralization")
    }
    link {
      name = "mplatvoet/kovenant"
      desc = "Promises for Kotlin and Android"
      href = "https://github.com/mplatvoet/kovenant"
      type = github
      tags = arrayOf("promise", "android")
    }
    link {
      name = "kohesive/klutter"
      desc = "A mix of random small libraries for Kotlin, the smallest reside here until big enough for their own repository."
      href = "https://github.com/kohesive/klutter"
      type = github
    }
    link {
      name = "kohesive/solr-undertow"
      desc = "Solr Standalone Tiny and High performant server."
      href = "https://github.com/kohesive/solr-undertow"
      type = github
      tags = arrayOf("solr", "undertow")
    }
    link {
      name = "leprosus/kotlin-hashids"
      desc = "Library that generates short, unique, non-sequential hashes from numbers."
      href = "https://github.com/leprosus/kotlin-hashids"
      type = github
      tags = arrayOf("hash")
    }
    link {
      name = "mplatvoet/progress"
      desc = "Progress for Kotlin."
      href = "https://github.com/mplatvoet/progress"
      type = github
      tags = arrayOf("progress")
    }
    link {
      name = "leprosus/kotlin-cli"
      desc = "Kotlin-CLI - command line interface options parser for Kotlin."
      href = "https://github.com/leprosus/kotlin-cli"
      type = github
      tags = arrayOf("cli", "command line interface")
    }
    link {
      name = "sargunster/CakeParse"
      desc = "Simple parser combinator library for Kotlin."
      href = "https://github.com/sargunster/CakeParse"
      type = github
      tags = arrayOf("parser", "combinator", "grammar", "lexer")
    }
    link {
      name = "sargunster/KtUnits"
      desc = "Tiny unit conversion library for Kotlin."
      href = "https://github.com/sargunster/KtUnits"
      type = github
      tags = arrayOf("time", "unit", "conversion")
    }
    link {
      name = "hotchemi/khronos"
      desc = "An intuitive Date extensions in Kotlin."
      href = "https://github.com/hotchemi/khronos"
      type = github
      tags = arrayOf("time", "date")
    }
    link {
      name = "yole/kxdate"
      desc = "Kotlin extensions for Java 8 java.time API"
      href = "https://github.com/yole/kxdate"
      type = github
      tags = arrayOf("time", "date")
    }
    link {
      name = "ingokegel/jclasslib"
      desc = "jclasslib bytecode viewer is a tool that visualizes all aspects of compiled Java class files and the contained bytecode."
      href = "https://github.com/ingokegel/jclasslib"
      type = github
      tags = arrayOf("bytecode")
    }
    link {
      name = "holgerbrandl/krangl"
      desc = "krangl is a {K}otlin library for data w{rangl}ing"
      href = "https://github.com/holgerbrandl/krangl"
      type = github
      tags = arrayOf("tabular data", "data processing")
    }
    link {
      name = "debop/koda-time"
      desc = "Joda Time Extensions in Kotlin. (From Java 8 use java.time instead)"
      href = "https://github.com/debop/koda-time"
      type = github
      tags = arrayOf("joda-time", "jsr-310")
    }
    link {
      name = "MicroUtils/kotlin-logging"
      desc = "Lightweight logging framework for Kotlin. Used as a wrapper for slf4j with Kotlin extensions."
      href = "https://github.com/MicroUtils/kotlin-logging"
      type = github
      tags = arrayOf("logging", "slf4j")
    }
    link {
      name = "cesarferreira/kotlin-pluralizer"
      desc = "Kotlin extension to pluralize and singularize strings."
      href = "https://github.com/cesarferreira/kotlin-pluralizer"
      type = github
      tags = arrayOf("pluralize", "singularizen")
    }
    link {
      name = "JoelW-S/groothy"
      desc = "Kotlin implementation of Groovy Truth."
      href = "https://github.com/JoelW-S/groothy"
      type = github
      tags = arrayOf("groovy truth")
    }
    link {
      name = "elect86/kotlin-unsigned"
      desc = "Boxed unsigned support, Ubyte, Uint, Ulong and Ushort."
      href = "https://github.com/elect86/kotlin-unsigned"
      type = github
      tags = arrayOf("unsigned")
    }
    link {
      name = "Jire/Strukt"
      desc = "Value types on the JVM, today!"
      href = "https://github.com/Jire/Strukt"
      type = github
      tags = arrayOf("gc-free", "structure")
    }
    link {
      name = "soywiz/korio"
      desc = "Korio: Kotlin cORoutines I/O: Streams + Async TCP Client/Server + Virtual File System for JVM, Node.JS and Browser."
      href = "https://github.com/soywiz/korio"
      type = github
      tags = arrayOf("vfs", "coroutiones", "io")
    }
    link {
      name = "soywiz/korim"
      desc = "Korim: Kotlin cORoutines IMaging utilities depending on Korio."
      href = "https://github.com/soywiz/korim"
      type = github
      tags = arrayOf("image", "coroutiones")
    }
    link {
      name = "soywiz/korui"
      desc = "Korui: Kotlin cORoutines User Interfaces: korio + kimage + korui"
      href = "https://github.com/soywiz/korui"
      type = github
      tags = arrayOf("ui", "coroutiones")
    }
    link {
      name = "jimschubert/kopper"
      desc = "A simple Kotlin option parser"
      href = "https://github.com/jimschubert/kopper"
      type = github
      tags = arrayOf("cli", "parser")
    }
    link {
      name = "moshbit/Kotlift"
      desc = "Kotlift is the first source-to-source language transpiler from Kotlin to Swift."
      href = "https://github.com/moshbit/Kotlift"
      type = github
      tags = arrayOf("swift")
    }
    link {
      name = "consoleau/kassava"
      desc = "This library provides some useful kotlin extension functions for implementing toString() and equals() without all of the boilerplate."
      href = "https://github.com/consoleau/kassava"
      type = github
      tags = arrayOf("hashCode", "equals", "toString")
    }
  }
  subcategory("Extensions") {
    link {
      name = "Kotlin/kotlinx.support"
      desc = "Extension and top-level functions to use JDK7/JDK8 features in Kotlin 1.0."
      href = "https://github.com/Kotlin/kotlinx.support"
      type = github
      tags = arrayOf("jdk8", "jdk7")
    }
  }
  subcategory("Configuration") {
    link {
      name = "npryce/konfig"
      desc = "A Type Safe Configuration API for Kotlin"
      href = "https://github.com/npryce/konfig"
      type = github
      tags = arrayOf("configuration")
    }
    link {
      name = "mariomac/kaconf"
      desc = "KickAss Configuration. An annotation-based configuration system for Java and Kotlin"
      href = "https://github.com/mariomac/kaconf"
      type = github
      tags = arrayOf("configuration")
    }
  }
  subcategory("Graphics") {
    link {
      name = "elect86/glm"
      desc = "Porting of cpp g-truck glm, opengl math lib"
      href = "https://github.com/elect86/glm"
      type = github
      tags = arrayOf("glm")
    }
    link {
      name = "elect86/ovr"
      desc = "Oculus binding"
      href = "https://github.com/elect86/ovr"
      type = github
      tags = arrayOf("vr", "oculus")
    }
    link {
      name = "elect86/openvr"
      desc = "Openvr binding"
      href = "https://github.com/elect86/openvr"
      type = github
      tags = arrayOf("vr", "openvr")
    }
    link {
      name = "elect86/kt"
      desc = "gli, port of cpp g-truck gli, texture util"
      href = "https://github.com/elect86/kt"
      type = github
      tags = arrayOf("gli")
    }
    link {
      name = "java-graphics/assimp"
      desc = "Java Open Asset Import Library"
      href = "https://github.com/java-graphics/assimp"
      type = github
      tags = arrayOf("assimp", "stl", "md2")
    }
  }
}
