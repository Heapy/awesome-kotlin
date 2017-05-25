import link.kotlin.scripts.LinkType.bitbucket
import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Libraries/Frameworks") {
  subcategory("Browsers") {
    link {
      name = "chrome-reactive-kotlin"
      desc = "Chrome Debugging Protocol for Kotlin (with RxJava2)"
      href = "https://github.com/wendigo/chrome-reactive-kotlin"
      type = github
      tags = Tags["web", "chrome", "automation", "browser"]
    }
  }

  subcategory("Web") {
    link {
      name = "Kotlin/ktor"
      desc = "Web backend framework for Kotlin."
      href = "https://github.com/Kotlin/ktor"
      type = github
      tags = Tags["web"]
    }
    link {
      name = "TinyMission/kara"
      desc = "Web framework written in Kotlin."
      href = "https://github.com/TinyMission/kara"
      type = github
      tags = Tags["web"]
    }
    link {
      name = "http4k/http4k"
      desc = "Toolkit for serving and consuming HTTP services in a functional and consistent way."
      href = "http://www.http4k.org"
      type = github
      tags = Tags["web", "http", "http client", "jetty", "netty", "undertow"]
    }
    link {
      name = "jean79/yested"
      desc = "A Kotlin framework for building web applications in Javascript."
      href = "https://github.com/jean79/yested"
      type = github
      tags = Tags["web"]
    }
    link {
      name = "hhariri/wasabi"
      desc = "An HTTP Framework built with Kotlin for the JVM."
      href = "https://github.com/wasabifx/wasabi"
      type = github
      tags = Tags["web"]
    }
    link {
      name = "Kotlin/kotlinx.html"
      desc = "Kotlin DSL for HTML."
      href = "https://github.com/Kotlin/kotlinx.html"
      type = github
      tags = Tags["web", "html"]
    }
    link {
      name = "MarioAriasC/KotlinPrimavera"
      desc = "Spring support libraries for Kotlin."
      href = "https://github.com/MarioAriasC/KotlinPrimavera"
      type = github
      tags = Tags["spring"]
    }
    link {
      name = "kohesive/kovert"
      desc = "An invisible, super easy and powerful REST and Web framework over Vert.x or Undertow."
      href = "https://github.com/kohesive/kovert"
      type = github
      tags = Tags["web", "http", "rest", "vert.x", "undertow"]
    }
    link {
      name = "sdeleuze/spring-kotlin"
      desc = "Kotlin extensions for Spring projects."
      href = "https://github.com/sdeleuze/spring-kotlin"
      type = github
      tags = Tags["spring", "extensions"]
    }
    link {
      name = "taskworld/kraph"
      desc = "GraphQL request string builder written in Kotlin"
      href = "https://github.com/taskworld/kraph"
      type = github
      tags = Tags["graphql", "builder"]
    }
    link {
      name = "sepatel/tekniq"
      desc = "Full-feature HTTP DSL Framework, HTTP Client, JDBC DSL, Loading Cache and Configuration"
      href = "https://github.com/sepatel/tekniq"
      type = github
      tags = Tags["web", "jdbc", "http client", "spark java", "cache"]
    }
    link {
      name = "vert-x3/vertx-lang-kotlin"
      desc = "This module provides Kotlin language bindings including DSL and extension functions for vert.x 3"
      href = "https://github.com/vert-x3/vertx-lang-kotlin/"
      type = github
      tags = Tags["web", "vert.x"]
    }
    link {
      name = "jooby/kotlin"
      desc = "Kotlin idioms for Jooby microframework"
      href = "http://jooby.org/doc/lang-kotlin"
      tags = Tags["web", "jooby", "microframework"]
    }
    link {
      name = "gimlet2/kottpd"
      desc = "REST framework in pure Kotlin, inspired by spark-java"
      href = "https://github.com/gimlet2/kottpd"
      type = github
      tags = Tags["web", "rest", "http"]
    }
    link {
      name = "kwebio/core"
      desc = " A Kotlin interface to one or more remote web-browsers."
      href = "https://github.com/kwebio/core"
      type = github
      tags = Tags["web", "rest", "http", "fullstack"]
    }
    link {
      name = "brianmadden/krawler"
      desc = "A web crawling framework written in Kotlin"
      href = "https://github.com/brianmadden/krawler"
      type = github
      tags = Tags["crawler4j", "web-crawler", "webcrawler", "link-checker"]
    }
  }
  subcategory("Tests") {
    link {
      name = "JetBrains/spek"
      desc = "A specification framework for Kotlin."
      href = "https://github.com/jetbrains/spek"
      type = github
      tags = Tags["test", "assert", "bdd"]
    }
    link {
      name = "npryce/hamkrest"
      desc = "A reimplementation of Hamcrest to take advantage of Kotlin language features."
      href = "https://github.com/npryce/hamkrest"
      type = github
      tags = Tags["test", "assert"]
    }
    link {
      name = "nhaarman/mockito-kotlin"
      desc = "Using Mockito with Kotlin."
      href = "https://github.com/nhaarman/mockito-kotlin"
      type = github
      tags = Tags["test", "mock"]
    }
    link {
      name = "MarkusAmshove/Kluent"
      desc = "Fluent Assertion-Library for Kotlin."
      href = "https://github.com/MarkusAmshove/Kluent"
      type = github
      tags = Tags["test", "assert"]
    }
    link {
      name = "winterbe/expekt"
      desc = "BDD assertion library for Kotlin."
      href = "https://github.com/winterbe/expekt"
      type = github
      tags = Tags["test", "assert", "bdd"]
    }
    link {
      name = "kotlintest/kotlintest"
      desc = "KotlinTest is a flexible and comprehensive testing tool for the Kotlin ecosystem based on and heavily inspired by the superb Scalatest."
      href = "https://github.com/kotlintest/kotlintest"
      type = github
      tags = Tags["test", "bdd", "matchers"]
    }
    link {
      name = "dmcg/konsent"
      desc = "An acceptance test library for Kotlin."
      href = "https://github.com/dmcg/konsent"
      type = github
      tags = Tags["test", "bdd", "gherkin"]
    }
    link {
      name = "raniejade/kspec"
      desc = "Kotlin Specification Framework."
      href = "https://github.com/raniejade/kspec"
      type = github
      tags = Tags["test", "bdd"]
    }
    link {
      name = "EPadronU/balin"
      desc = "Balin is a browser automation library for Kotlin. It's basically a Selenium-WebDriver wrapper library inspired by Geb."
      href = "https://github.com/EPadronU/balin"
      type = github
      tags = Tags["test", "selenium", "UI", "automation"]
    }
    link {
      name = "dmcg/k-sera"
      desc = "A JMock wrapper for Kotlin."
      href = "https://github.com/dmcg/k-sera"
      type = github
      tags = Tags["mock", "test"]
    }
    link {
      name = "dam5s/aspen"
      desc = "Aspen is an RSpec and Spek inspired test runner for Kotlin."
      href = "https://github.com/dam5s/aspen"
      type = github
      tags = Tags["test", "specification", "rspec", "spek"]
    }
  }
  subcategory("Dependency Injection") {
    link {
      name = "SalomonBrys/Kodein"
      desc = "Painless Kotlin Dependency Injection ."
      href = "https://github.com/SalomonBrys/Kodein"
      type = github
      tags = Tags["di", "dependency injection"]
    }
    link {
      name = "kohesive/injekt"
      desc = "(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin"
      href = "https://github.com/kohesive/injekt"
      type = github
      tags = Tags["di", "dependency injection"]
    }
    link {
      name = "kailan/kodeinject"
      desc = "Constructor dependency injection for Kodein"
      href = "https://github.com/kailan/kodeinject"
      type = github
      tags = Tags["di", "dependency injection", "kodein"]
    }
    link {
      name = "traversals/kapsule"
      desc = "Minimalist dependency injection library for Kotlin."
      href = "https://github.com/traversals/kapsule"
      type = github
      tags = Tags["di", "dependency injection"]
    }
  }
  subcategory("Coroutines") {
    link {
      name = "Kotlin/kotlin-coroutines"
      desc = "Design documents and examples for coroutines in Kotlin."
      href = "https://github.com/Kotlin/kotlin-coroutines"
      type = github
      tags = Tags["coroutines"]
    }
    link {
      name = "Kotlin/kotlinx.coroutines"
      desc = "Libraries built upon Kotlin coroutines."
      href = "https://github.com/Kotlin/kotlinx.coroutines"
      type = github
      tags = Tags["async", "await", "yield", "generator"]
    }
    link {
      name = "soywiz/korio"
      desc = "Korio: Kotlin cORoutines I/O: Streams + Async TCP Client/Server + Virtual File System for JVM, Node.JS and Browser."
      href = "https://github.com/soywiz/korio"
      type = github
      tags = Tags["vfs", "coroutiones", "io"]
    }
    link {
      name = "soywiz/korim"
      desc = "Korim: Kotlin cORoutines IMaging utilities depending on Korio."
      href = "https://github.com/soywiz/korim"
      type = github
      tags = Tags["image", "coroutiones"]
    }
    link {
      name = "soywiz/korui"
      desc = "Korui: Kotlin cORoutines User Interfaces: korio + kimage + korui"
      href = "https://github.com/soywiz/korui"
      type = github
      tags = Tags["ui", "coroutiones"]
    }
    link {
      name = "konrad-kaminski/spring-kotlin-coroutine"
      desc = "Kotlin coroutine support for Spring."
      href = "https://github.com/konrad-kaminski/spring-kotlin-coroutine"
      type = github
      tags = Tags["coroutines", "spring"]
    }
  }
  subcategory("Functional Programming") {
    link {
      name = "MarioAriasC/funKTionale"
      desc = "Functional constructs for Kotlin."
      href = "https://github.com/MarioAriasC/funKTionale"
      type = github
      tags = Tags["fp", "functional"]
    }
    link {
      name = "ReactiveX/RxKotlin"
      desc = "RxJava bindings for Kotlin."
      href = "https://github.com/ReactiveX/RxKotlin"
      type = github
      tags = Tags["fp", "functional"]
    }
    link {
      name = "kittinunf/Result"
      desc = "The modelling for success/failure of operations in Kotlin."
      href = "https://github.com/kittinunf/Result"
      type = github
      tags = Tags["fp", "functional", "monad"]
    }
    link {
      name = "brianegan/bansa"
      desc = "A state container for Kotlin & Java, inspired by Elm & Redux."
      href = "https://github.com/brianegan/bansa"
      type = github
      tags = Tags["fp", "functional", "UI", "Interface", "Redux"]
    }
    link {
      name = "pardom/redux-kotlin"
      desc = "Direct port of Redux for Kotlin."
      href = "https://github.com/pardom/redux-kotlin"
      type = github
      tags = Tags["fp", "functional", "UI", "Interface", "Redux"]
    }
    link {
      name = "beyondeye/Reduks"
      desc = "A \"batteries included\" port of Reduxjs for Kotlin+Android"
      href = "https://github.com/beyondeye/Reduks"
      type = github
      tags = Tags["fp", "functional", "UI", "Interface", "Redux"]
    }
    link {
      name = "pakoito/Komprehensions"
      desc = "Do comprehensions for Kotlin and 3rd party libraries."
      href = "https://github.com/pakoito/Komprehensions"
      type = github
      tags = Tags["comprehensions", "fp", "functional"]
    }
    link {
      name = "h0tk3y/kotlin-monads"
      desc = "Monads for Kotlin"
      href = "https://github.com/h0tk3y/kotlin-monads"
      type = github
      tags = Tags["fp", "functional", "monads"]
    }
    link {
      name = "FineCinnamon/Katz"
      desc = "Typeful functional programming in Kotlin."
      href = "https://github.com/FineCinnamon/Katz"
      type = github
      tags = Tags["fp", "functional", "cats"]
    }
    link {
      name = "poetix/klenses"
      desc = "Lenses for Kotlin."
      href = "https://github.com/poetix/klenses"
      type = github
      tags = Tags["fp", "functional", "lenses"]
    }
    link {
      name = "reactor/reactor-kotlin-extensions"
      desc = "Kotlin extensions for Reactor."
      href = "https://github.com/reactor/reactor-kotlin-extensions/"
      type = github
      tags = Tags["reactor", "extensions"]
    }
  }
  subcategory("JSON") {
    link {
      name = "cbeust/klaxon"
      desc = "Lightweight library to parse JSON in Kotlin."
      href = "https://github.com/cbeust/klaxon"
      type = github
      tags = Tags["json"]
    }
    link {
      name = "SalomonBrys/Kotson"
      desc = "Gson for Kotlin, Kotson enables you to parse and write JSON with Google's Gson using a conciser and easier syntax."
      href = "https://github.com/SalomonBrys/Kotson"
      type = github
      tags = Tags["json"]
    }
    link {
      name = "FasterXML/jackson-module-kotlin"
      desc = "Jackson module that adds support for serialization/deserialization of Kotlin classes and data classes."
      href = "https://github.com/FasterXML/jackson-module-kotlin"
      type = github
      tags = Tags["json", "jakson"]
    }
    link {
      name = "fboldog/ext4klaxon"
      desc = "Type Extensions (Long, Int, Enum, Date) for Klaxon."
      href = "https://github.com/fboldog/ext4klaxon"
      type = github
      tags = Tags["json"]
    }
    link {
      name = "Jire/KTON"
      desc = "Object notation in pure Kotlin!"
      href = "https://github.com/Jire/KTON"
      type = github
      tags = Tags["JSON", "XML"]
    }
  }
  subcategory("Database") {
    link {
      name = "JetBrains/Exposed"
      desc = "Exposed is a prototype for a lightweight SQL library written over JDBC driver for Kotlin language."
      href = "https://github.com/jetbrains/Exposed"
      type = github
      tags = Tags["database", "query", "schema", "dao"]
    }
    link {
      name = "cheptsov/kotlin-nosql"
      desc = "NoSQL database query and access library for Kotlin."
      href = "https://github.com/cheptsov/kotlin-nosql"
      type = github
      tags = Tags["database", "mongodb", "query"]
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
      tags = Tags["database", "sql", "query"]
    }
    link {
      name = "andrewoma/kwery"
      desc = "Kwery is an SQL library for Kotlin."
      href = "https://github.com/andrewoma/kwery"
      type = github
      tags = Tags["database", "sql", "query"]
    }
    link {
      name = "square/sqldelight"
      desc = "Generates Java models from CREATE TABLE statements."
      href = "https://github.com/square/sqldelight"
      type = github
      tags = Tags["database", "sql", "type-safe builder"]
    }
    link {
      name = "x2bool/kuery"
      desc = "Typesafe SQL with Kotlin."
      href = "https://github.com/x2bool/kuery"
      type = github
      tags = Tags["database", "sql", "type-safe builder"]
    }
    link {
      name = "Litote/kmongo"
      desc = "KMongo - Kotlin toolkit for Mongo"
      href = "https://github.com/Litote/kmongo"
      type = github
      tags = Tags["database", "mongodb", "query"]
    }
    link {
      name = "requery/requery"
      desc = "Modern SQL based query & persistence for Java/Kotlin/Android."
      href = "https://github.com/requery/requery"
      type = github
      tags = Tags["database", "query", "type-safe builder"]
    }
    link {
      name = "consoleau/kotlin-jpa-specification-dsl"
      desc = "This library provides a fluent DSL for querying spring data JPA repositories using spring data Specifications."
      href = "https://github.com/consoleau/kotlin-jpa-specification-dsl"
      type = github
      tags = Tags["database", "query", "jpa"]
    }
    link {
      name = "s4kibs4mi/PultusORM"
      desc = "PultusORM is a sqlite ORM library for kotlin on top of sqlite jdbc driver."
      href = "https://github.com/s4kibs4mi/PultusORM"
      type = github
      tags = Tags["database", "query", "sqlite"]
    }
    link {
      name = "Ganet/rxaerospike"
      desc = "RxJava2 wrapper for aerospike-client-java."
      href = "https://github.com/Ganet/rxaerospike"
      type = github
      tags = Tags["database", "arospike", "rx", "rxjava2"]
    }
    link {
      name = "Raizlabs/DBFlow"
      desc = "A blazing fast, powerful, and very simple ORM android database library that writes database code for you."
      href = "https://github.com/Raizlabs/DBFlow"
      type = github
      tags = Tags["orm", "jap", "kapt", "database"]
    }
    link {
      name = "KotlinPorts/kt-postgresql-async"
      desc = "Kotlin/Gradle port of mauricio's async driver for postgres/mysql."
      href = "https://github.com/KotlinPorts/kt-postgresql-async"
      type = github
      tags = Tags["postgres", "mysql", "database driver"]
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
      tags = Tags["bash", "scripting", "kts"]
    }
    link {
      name = "kohesive/keplin"
      desc = "Secure Kotlin scripting and binary lambda-scripts."
      href = "https://github.com/kohesive/keplin"
      type = github
      tags = Tags["scripting", "kts"]
    }
    link {
      name = "shyiko/ktlint"
      desc = "Kotlin linter."
      href = "https://github.com/shyiko/ktlint"
      type = github
      tags = Tags["style", "linter"]
    }
    link {
      name = "jtransc/jtransc"
      desc = "JVM AOT compiler created in Kotlin."
      href = "https://github.com/jtransc/jtransc"
      type = github
      tags = Tags["aot", "compiller"]
    }
    link {
      name = "arturbosch/detekt"
      desc = "Static code analysis for Kotlin."
      href = "https://github.com/arturbosch/detekt"
      type = github
      tags = Tags["check style", "checkstyle"]
    }
    link {
      name = "cypressious/KotlinW"
      desc = "A small wrapper for the Kotlin compiler that can be used to execute .kts scripts."
      href = "https://github.com/cypressious/KotlinW"
      type = github
      tags = Tags["wrapper", "scripting"]
    }
  }
  subcategory("Desktop") {
    link {
      name = "edvin/tornadofx"
      desc = "Lightweight JavaFX Framework for Kotlin/"
      href = "https://github.com/edvin/tornadofx"
      type = github
      tags = Tags["javafx", "desktop", "application"]
    }
  }
  subcategory("Http Clients") {
    link {
      name = "kittinunf/Fuel"
      desc = "The easiest HTTP networking library for Kotlin/Android."
      href = "https://github.com/kittinunf/Fuel"
      type = github
      tags = Tags["http", "http client", "file upload"]
    }
    link {
      name = "jkcclemens/khttp"
      desc = "Kotlin HTTP requests library."
      href = "https://github.com/jkcclemens/khttp"
      type = github
      tags = Tags["http", "http client"]
    }
  }
  subcategory("Editors") {
    link {
      name = "JetBrains/intellij-community"
      desc = "IntelliJ IDEA Community Edition"
      href = "https://github.com/JetBrains/intellij-community"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "alexmt/atom-kotlin-language"
      desc = "Adds syntax highlighting to Kotlin files in Atom"
      href = "https://github.com/alexmt/atom-kotlin-language"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "vkostyukov/kotlin-sublime-package"
      desc = "A Sublime Package for Kotlin."
      href = "https://github.com/vkostyukov/kotlin-sublime-package"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "udalov/kotlin-vim"
      desc = "Kotlin Syntax Highlighter for Vim."
      href = "https://github.com/udalov/kotlin-vim"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "sargunster/kotlin-textmate-bundle"
      desc = "Kotlin bundle for TextMate."
      href = "https://github.com/sargunster/kotlin-textmate-bundle"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "ice1000/NppExtension"
      desc = "Kotlin Language extension for Notepad++"
      href = "https://github.com/ice1000/NppExtension"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "ftomassetti/kanvas"
      desc = "A truly hackable editor: simple, lightweight, understandable."
      href = "https://github.com/ftomassetti/kanvas"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
    link {
      name = "mathiasfrohlich/vscode-kotlin"
      desc = "Kotlin language support for VS Code."
      href = "https://github.com/mathiasfrohlich/vscode-kotlin"
      type = github
      tags = Tags["editor", "ide", "language"]
    }
  }
  subcategory("Syntax Highlighters") {
    link {
      name = "jneen/rouge"
      desc = "A pure-ruby code highlighter that is compatible with pygments. (wip)"
      href = "https://github.com/jneen/rouge"
      type = github
      tags = Tags["syntax", "higlight"]
    }
    link {
      name = "isagalaev/highlight.js"
      desc = "Javascript syntax highlighter. (out-of-date)"
      href = "https://github.com/isagalaev/highlight.js"
      type = github
      tags = Tags["syntax", "higlight"]
    }
    link {
      name = "github/linguist"
      desc = "For Ruby/Github, uses Sublime package"
      href = "https://github.com/github/linguist"
      type = github
      tags = Tags["syntax", "higlight"]
    }
    link {
      name = "birkenfeld/pygments-main"
      desc = "Python syntax highlighter."
      href = "https://bitbucket.org/birkenfeld/pygments-main"
      type = bitbucket
      tags = Tags["syntax", "higlight"]
    }
    link {
      name = "cansik/kotlin-latex-listing"
      desc = "A syntax highlighting template for the Kotlin language in LaTeX listings."
      href = "https://github.com/cansik/kotlin-latex-listing"
      type = github
      tags = Tags["syntax", "higlight"]
    }
  }
  subcategory("Game Development") {
    link {
      name = "czyzby/ktx"
      desc = "Kotlin utilities for LibGDX applications."
      href = "https://github.com/czyzby/ktx"
      type = github
      tags = Tags["LibGDX", "game dev", "games"]
    }
    link {
      name = "AlmasB/FXGL"
      desc = "JavaFX 8 Game Library written in Java + Kotlin"
      href = "https://github.com/AlmasB/FXGL"
      type = github
      tags = Tags["javafx", "desktop", "games", "game dev"]
    }
    link {
      name = "icela/FriceEngine"
      desc = "Make game developing easy again!"
      href = "https://github.com/icela/FriceEngine"
      type = github
      tags = Tags["desktop", "games", "game dev"]
    }
  }
  subcategory("Misc") {
    link {
      name = "Kotlin/kotlinx.reflect.lite"
      desc = "Lightweight library allowing to introspect basic stuff about Kotlin symbols."
      href = "https://github.com/Kotlin/kotlinx.reflect.lite"
      type = github
      tags = Tags["reflection"]
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
      tags = Tags["pluralization"]
    }
    link {
      name = "mplatvoet/kovenant"
      desc = "Promises for Kotlin and Android"
      href = "https://github.com/mplatvoet/kovenant"
      type = github
      tags = Tags["promise", "android"]
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
      tags = Tags["solr", "undertow"]
    }
    link {
      name = "leprosus/kotlin-hashids"
      desc = "Library that generates short, unique, non-sequential hashes from numbers."
      href = "https://github.com/leprosus/kotlin-hashids"
      type = github
      tags = Tags["hash"]
    }
    link {
      name = "mplatvoet/progress"
      desc = "Progress for Kotlin."
      href = "https://github.com/mplatvoet/progress"
      type = github
      tags = Tags["progress"]
    }
    link {
      name = "sargunster/CakeParse"
      desc = "Simple parser combinator library for Kotlin."
      href = "https://github.com/sargunster/CakeParse"
      type = github
      tags = Tags["parser", "combinator", "grammar", "lexer"]
    }
    link {
      name = "sargunster/KtUnits"
      desc = "Tiny unit conversion library for Kotlin."
      href = "https://github.com/sargunster/KtUnits"
      type = github
      tags = Tags["time", "unit", "conversion"]
    }
    link {
      name = "hotchemi/khronos"
      desc = "An intuitive Date extensions in Kotlin."
      href = "https://github.com/hotchemi/khronos"
      type = github
      tags = Tags["time", "date"]
    }
    link {
      name = "yole/kxdate"
      desc = "Kotlin extensions for Java 8 java.time API"
      href = "https://github.com/yole/kxdate"
      type = github
      tags = Tags["time", "date"]
    }
    link {
      name = "ingokegel/jclasslib"
      desc = "jclasslib bytecode viewer is a tool that visualizes all aspects of compiled Java class files and the contained bytecode."
      href = "https://github.com/ingokegel/jclasslib"
      type = github
      tags = Tags["bytecode"]
    }
    link {
      name = "holgerbrandl/krangl"
      desc = "krangl is a {K}otlin library for data w{rangl}ing"
      href = "https://github.com/holgerbrandl/krangl"
      type = github
      tags = Tags["tabular data", "data processing"]
    }
    link {
      name = "debop/koda-time"
      desc = "Joda Time Extensions in Kotlin. (From Java 8 use java.time instead)"
      href = "https://github.com/debop/koda-time"
      type = github
      tags = Tags["joda-time", "jsr-310"]
    }
    link {
      name = "MicroUtils/kotlin-logging"
      desc = "Lightweight logging framework for Kotlin. Used as a wrapper for slf4j with Kotlin extensions."
      href = "https://github.com/MicroUtils/kotlin-logging"
      type = github
      tags = Tags["logging", "slf4j"]
    }
    link {
      name = "cesarferreira/kotlin-pluralizer"
      desc = "Kotlin extension to pluralize and singularize strings."
      href = "https://github.com/cesarferreira/kotlin-pluralizer"
      type = github
      tags = Tags["pluralize", "singularizen"]
    }
    link {
      name = "JoelW-S/groothy"
      desc = "Kotlin implementation of Groovy Truth."
      href = "https://github.com/JoelW-S/groothy"
      type = github
      tags = Tags["groovy truth"]
    }
    link {
      name = "Jire/Strukt"
      desc = "Value types on the JVM, today!"
      href = "https://github.com/Jire/Strukt"
      type = github
      tags = Tags["gc-free", "structure"]
    }
    link {
      name = "moshbit/Kotlift"
      desc = "Kotlift is the first source-to-source language transpiler from Kotlin to Swift."
      href = "https://github.com/moshbit/Kotlift"
      type = github
      tags = Tags["swift"]
    }
    link {
      name = "consoleau/kassava"
      desc = "This library provides some useful kotlin extension functions for implementing toString() and equals() without all of the boilerplate."
      href = "https://github.com/consoleau/kassava"
      type = github
      tags = Tags["hashCode", "equals", "toString"]
    }
    link {
      name = "moove-it/fakeit"
      desc = "Generates realistic fake data — like names, emails, dates, countries — to be used in your Android development environment."
      href = "https://github.com/moove-it/fakeit"
      type = github
      tags = Tags["testing", "android", "utility"]
    }
    link {
      name = "czyzby/kotlin-times"
      desc = "A simple utility library for readable loops."
      href = "https://github.com/czyzby/kotlin-times"
      type = github
      tags = Tags["extensions", "utility"]
    }
    link {
      name = "phxql/aleksa"
      desc = "Aleksa is a small framework for writing Alexa Skills in Kotlin."
      href = "https://github.com/phxql/aleksa"
      type = github
      tags = Tags["alexa", "amazon echo", "text to speech"]
    }
    link {
      name = "cretz/asmble"
      desc = "Compile WebAssembly to JVM and other WASM tools."
      href = "https://github.com/cretz/asmble"
      type = github
      tags = Tags["wasm", "webassembly"]
    }
  }
  subcategory("Extensions") {
    link {
      name = "Kotlin/kotlinx.support"
      desc = "Extension and top-level functions to use JDK7/JDK8 features in Kotlin 1.0."
      href = "https://github.com/Kotlin/kotlinx.support"
      type = github
      tags = Tags["jdk8", "jdk7"]
    }
  }
  subcategory("Configuration") {
    link {
      name = "npryce/konfig"
      desc = "A Type Safe Configuration API for Kotlin"
      href = "https://github.com/npryce/konfig"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "mariomac/kaconf"
      desc = "KickAss Configuration. An annotation-based configuration system for Java and Kotlin"
      href = "https://github.com/mariomac/kaconf"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "config4k/config4k"
      desc = "A Kotlin wrapper for Typesafe Config"
      href = "https://github.com/config4k/config4k"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "jdiazcano/cfg4k"
      desc = "Flexible and easy to use config library written in kotlin."
      href = "https://github.com/jdiazcano/cfg4k"
      type = github
      tags = Tags["configuration"]
    }
  }
  subcategory("Graphics") {
    link {
      name = "kotlin-graphics/glm"
      desc = "g-truck glm port, opengl mathematics library"
      href = "https://github.com/kotlin-graphics/glm"
      type = github
      tags = Tags["glm", "opengl", "vulkan"]
    }
    link {
      name = "kotlin-graphics/kotlin-unsigned"
      desc = "Unsigned operators and boxed types (Ubyte, Uint, Ulong and Ushort) for unsigned support."
      href = "https://github.com/kotlin-graphics/kotlin-unsigned"
      type = github
      tags = Tags["unsigned", "math"]
    }
    link {
      name = "kotlin-graphics/gli"
      desc = "g-truck gli port, image library"
      href = "https://github.com/kotlin-graphics/gli"
      type = github
      tags = Tags["gli", "opengl", "vulkan"]
    }
    link {
      name = "kotlin-graphics/uno-sdk"
      desc = "Unofficial OpenGL SDK"
      href = "https://github.com/kotlin-graphics/uno-sdk"
      type = github
      tags = Tags["gli", "opengl", "vulkan"]
    }
    link {
      name = "kotlin-graphics/ovr"
      desc = "Oculus binding"
      href = "https://github.com/kotlin-graphics/ovr"
      type = github
      tags = Tags["vr", "oculus", "opengl"]
    }
    link {
      name = "kotlin-graphics/openvr"
      desc = "OpenVR binding"
      href = "https://github.com/kotlin-graphics/openvr"
      type = github
      tags = Tags["vr", "openvr", "opengl", "vulkan"]
    }
    link {
      name = "java-graphics/assimp"
      desc = "Open Asset Import Library port"
      href = "https://github.com/java-graphics/assimp"
      type = github
      tags = Tags["assimp", "stl", "md2", "ply", "obj", "collada", "opengl", "vulkan"]
    }
    link {
      name = "GlimpseFramework/glimpse-framework"
      desc = "OpenGL made simple."
      href = "https://github.com/GlimpseFramework/glimpse-framework"
      type = github
      tags = Tags["opengl", "shaders"]
    }
    link {
      name = "java-opengl-labs/modern-jogl-examples"
      desc = "port of 'Learning Modern 3D Graphic Programming' by J.L.McKesson (jogl)"
      href = "https://github.com/java-opengl-labs/modern-jogl-examples"
      type = github
      tags = Tags["opengl", "tutorial", "jogl"]
    }
    link {
      name = "java-opengl-labs/learn-OpenGL"
      desc = "port of https://learnopengl.com/ tutorial (lwjgl)"
      href = "https://github.com/java-opengl-labs/learn-OpenGL"
      type = github
      tags = Tags["opengl", "tutorial", "lwjgl"]
    }
  }
  subcategory("Data Science") {
    link {
      name = "ligee/kotlin-jupyter"
      desc = "Kotlin kernel for Jupyter/iPython."
      href = "https://github.com/ligee/kotlin-jupyter"
      type = github
      tags = Tags["data science", "juputer", "repl"]
    }
    link {
      name = "thomasnield/kotlin-statistics"
      desc = "Advanced math and statistical extensions for Kotlin."
      href = "https://github.com/thomasnield/kotlin-statistics"
      type = github
      tags = Tags["data science", "extensions"]
    }
    link {
      name = "holgerbrandl/krangl"
      desc = "krangl is a {K}otlin library for data w{rangl}ing."
      href = "https://github.com/holgerbrandl/krangl"
      type = github
      tags = Tags["data science", "data wrangling"]
    }
    link {
      name = "kyonifer/koma"
      desc = "A scientific computing library for Kotlin."
      href = "https://github.com/kyonifer/koma"
      type = github
      tags = Tags["data science", "plot", "matlab", "numpy"]
    }
    link {
      name = "MarcinMoskala/KotlinDiscreteMathToolkit"
      desc = "Set of extensions for Kotlin that provides Discrete Math functionalities as an Kotlin extension functions."
      href = "https://github.com/MarcinMoskala/KotlinDiscreteMathToolkit"
      type = github
      tags = Tags["data science", "math"]
    }
  }
  subcategory("Command Line Interface") {
    link {
      name = "leprosus/kotlin-cli"
      desc = "Kotlin-CLI - command line interface options parser for Kotlin."
      href = "https://github.com/leprosus/kotlin-cli"
      type = github
      tags = Tags["cli", "command line interface"]
    }
    link {
      name = "jimschubert/kopper"
      desc = "A simple Kotlin option parser"
      href = "https://github.com/jimschubert/kopper"
      type = github
      tags = Tags["cli", "parser"]
    }
    link {
      name = "xenomachina/kotlin-argparser"
      desc = "Easy to use and concise yet powerful and robust command line argument parsing for Kotlin."
      href = "https://github.com/xenomachina/kotlin-argparser"
      type = github
      tags = Tags["command-line-parser", "argument-parser", "option-parser"]
    }
  }
}
