import link.kotlin.scripts.LinkType.bitbucket
import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.TargetType.*
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Libraries/Frameworks") {
  subcategory("Web") {
    link {
      name = "ktorio/ktor"
      desc = "Web backend framework for Kotlin. Easy to use, fun and asynchronous."
      href = "https://github.com/ktorio/ktor"
      platforms = arrayOf(JVM, ANDROID, NATIVE, IOS)
      type = github
      tags = Tags["web"]
    }
    link {
      name = "darkredz/Zeko-RestApi"
      desc = "Fun, simple & lightweight async RESTful API framework on top of Vert.x. Automatic Swagger doc & code generation via Kotlin kapt"
      href = "https://github.com/darkredz/zeko-restapi-framework"
      type = github
      tags = Tags["web", "http", "rest", "vert.x", "swagger", "openapi", "microframework", "rest-api", "reactive", "mvc"]
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
      name = "aPureBase/KGraphQL"
      desc = "A GraphQL implementation written in Kotlin"
      href = "https://github.com/aPureBase/KGraphQL"
      type = github
      tags = Tags["graphql", "web"]
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
      name = "jooby-project/jooby"
      desc = "Modular micro web framework for Java and Kotlin"
      href = "https://github.com/jooby-project/jooby/"
      type = github
      tags = Tags["web", "jooby", "microframework", "http", "rest"]
    }
    link {
      name = "gimlet2/kottpd"
      desc = "REST framework in pure Kotlin, inspired by spark-java"
      href = "https://github.com/gimlet2/kottpd"
      type = github
      tags = Tags["web", "rest", "http"]
    }
    link {
      name = "kwebio/kweb-core"
      desc = "Build rich live-updating web apps in pure server-side Kotlin."
      href = "https://github.com/kwebio/kweb-core"
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
    link {
      name = "mvysny/vaadin-on-kotlin"
      desc = "A simple way to write full-stack database-backed component-oriented web apps"
      href = "https://github.com/mvysny/vaadin-on-kotlin"
      type = github
      tags = Tags["web", "fullstack", "vaadin"]
    }
    link {
      name = "perwendel/spark-kotlin"
      desc = "A DSL in idiomatic Kotlin for the Spark web framework."
      href = "https://github.com/perwendel/spark-kotlin"
      type = github
      tags = Tags["web", "rest", "http"]
    }
    link {
      name = "hexagonkt/hexagon"
      desc = "A Microservices framework that takes care of HTTP, serialization and storage."
      href = "https://github.com/hexagonkt/hexagon"
      type = github
      tags = Tags["web", "rest", "http"]
    }
    link {
      name = "danneu/kog"
      desc = "A web framework focused on simplicity, middleware, and functional composition"
      href = "https://github.com/danneu/kog"
      type = github
      tags = Tags["web", "http", "rest", "jetty", "websockets"]
    }
    link {
      name = "tipsy/javalin"
      desc = "A Simple REST API Library for Java/Kotlin."
      href = "https://github.com/tipsy/javalin"
      type = github
      tags = Tags["kotlin", "rest-api", "web-framework", "microservice", "servlet", "jetty"]
    }
    link {
      name = "laviua/komock"
      desc = "HTTP/Consul/SMTP/Spring Config mocker framework written in Kotlin"
      href = "https://github.com/laviua/komock"
      type = github
      tags = Tags["kotlin", "http", "consul", "microservice", "mock", "jetty", "netty", "smtp", "ssl"]
    }
    link {
      name = "hypercube1024/firefly"
      desc = "An asynchronous web framework for rapid development of high-performance web application."
      href = "https://github.com/hypercube1024/firefly"
      type = github
      tags = Tags["kotlin", "web", "http", "tcp", "ssl", "reactive"]
    }
    link {
      name = "phenax/h"
      desc = "HTML templating library written in Kotlin"
      href = "https://github.com/phenax/h"
      type = github
      tags = Tags["kotlin", "http", "web", "html", "template"]
    }
    link {
      name = "bootique/bootique-kotlin"
      desc = "Provides extension function and features for smooth development with Bootique and Kotlin."
      href = "https://github.com/bootique/bootique-kotlin"
      type = github
      tags = Tags["kotlin", "web-framework", "undertow", "jetty"]
    }
    link {
      name = "SeunAdelekan/Kanary"
      desc = "A micro webframework for Kotlin"
      href = "https://github.com/SeunAdelekan/Kanary"
      type = github
      tags = Tags["web"]
    }
    link {
      name = "ExpediaDotCom/graphql-kotlin"
      desc = "Code-only GraphQL schema generation for Kotlin"
      href = "https://github.com/ExpediaDotCom/graphql-kotlin"
      type = github
      tags = Tags["graphql", "web"]
    }
    link {
      name = "moia-dev/lambda-kotlin-request-router"
      desc = "A REST request routing layer for AWS lambda handlers written in Kotlin"
      href = "https://github.com/moia-dev/lambda-kotlin-request-router"
      type = github
    }
    link {
      name = "spypunk/sponge"
      desc = "A website crawler and links downloader command line tool written in Kotlin"
      href = "https://github.com/spypunk/sponge"
      type = github
    }
    link {
      name = "alpas/alpas"
      desc = "Kotlin web framework inspired by Laravel/Rails. Easy, elegant and productive."
      href = "https://github.com/alpas/alpas"
      type = github
    }
    link {
      name = "jetbrains/kotless"
      desc = "Kotlin serverless framework reducing the routine of serverless deployment."
      href = "https://github.com/jetbrains/kotless"
      type = github
      tags = Tags["web", "microservice", "serverless"]
    }
    link {
      name = "jwstegemann/fritz2"
      desc = "small lib to build reactive web-apps in pure Kotlin based on Flows"
      href = "https://github.com/jwstegemann/fritz2"
      type = github
      tags = Tags["web", "ui", "reactive", "coroutines", "html", "routing"]
    }
    link {
      name = "AurityLab/graphql-kotlin-toolkit"
      desc = "GraphQL toolkit for Kotlin (includes code generator and spring boot integration)"
      href = "https://github.com/AurityLab/graphql-kotlin-toolkit"
      type = github
      tags = Tags["web", "graphql", "codegenerator", "spring"]
    }
    link {
      name = "apollographql/apollo-android"
      desc = "Typesafe GraphQL client for the JVM and Kotlin native"
      href = "https://github.com/apollographql/apollo-android"
      type = github
      tags = Tags["web", "graphql"]
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
      name = "robstoll/atrium"
      desc = "Multiplatform assertion library for Kotlin supporting i18n."
      href = "https://github.com/robstoll/atrium"
      type = github
      tags = Tags["test", "assertion-library", "assert"]
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
      name = "kotest/kotest"
      desc = "Formerly known as KotlinTest, Kotest is a flexible and comprehensive testing tool that is multiplatform enabled."
      href = "https://github.com/kotest/kotest"
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
    link {
      name = "qwertukg/SeleniumBuilder"
      desc = "DSL for Selenium 2.0. Provide a possibility to write tests in Kotlin builder style."
      href = "https://github.com/qwertukg/SeleniumBuilder"
      type = github
      tags = Tags["selenium", "test"]
    }
    link {
      name = "mockk/mockk"
      desc = "Pure Kotlin mocking library."
      href = "https://github.com/mockk/mockk"
      type = github
      tags = Tags["test", "mock"]
    }
    link {
      name = "mvysny/DynaTest"
      desc = "Write your tests in DSL way. Runs on JUnit5 Platform."
      href = "https://github.com/mvysny/dynatest"
      type = github
      tags = Tags["test", "assert", "dsl"]
    }
    link {
      name = "tyro/arbitrater"
      desc = "Arbitrater is a library for creating arbitrary (randomized) instances of classes by reflection for use in testing."
      href = "https://github.com/tyro/arbitrater"
      type = github
      tags = Tags["test", "random", "random-generation"]
    }
    link {
      name = "xgouchet/Elmyr"
      desc = "A utility to make Kotlin/Java tests random yet reproducible"
      href = "https://github.com/xgouchet/Elmyr"
      type = github
      tags = Tags["test", "random", "random-generation"]
    }
    link {
      name = "neworld/kupiter"
      desc = "Kotlin DSL for Junit5"
      href = "https://github.com/neworld/kupiter"
      type = github
      tags = Tags["test", "junit5", "dsl"]
    }
    link {
      name = "karumi/KotlinSnapshot"
      desc = "Verify your data with snapshot testing."
      href = "https://github.com/Karumi/KotlinSnapshot"
      type = github
      tags = Tags["snapshot", "test", "assert"]
    }
    link {
      name = "permissions-dispatcher/kompile-testing"
      desc = "Testing tools for kotlinc and kapt."
      href = "https://github.com/permissions-dispatcher/kompile-testing"
      type = github
      tags = Tags["kapt", "test"]
    }
    link {
      name = "robfletcher/strikt"
      desc = "An assertion library for Kotlin"
      href = "https://github.com/robfletcher/strikt/"
      type = github
      tags = Tags["test", "strikt", "assert"]
    }
    link {
      name = "dmcg/minutest"
      desc = "Simple, Expressive, Extensible Testing for Kotlin on the JVM"
      href = "https://github.com/dmcg/minutest"
      type = github
      tags = Tags["test", "minutest", "dsl"]
    }
    link {
      name = "codecentric/hikaku"
      desc = "A library that tests if the implementation of a REST-API meets its specification."
      href = "https://github.com/codecentric/hikaku"
      type = github
      tags = Tags["test", "assert", "api", "rest"]
    }
    link {
      name = "serpro69/kotlin-faker"
      desc = "Port of ruby faker gem written in kotlin"
      href = "https://github.com/serpro69/kotlin-faker"
      type = github
      tags = Tags["test", "testing", "data-generator", "faker"]
    }
    link {
      name = "skrapeit/skrape.it"
      desc = "A DSL-driven HTML/XML parser-library that enables meaningful testing of rendered HTML templates."
      href = "https://github.com/skrapeit/skrape.it"
      platforms = arrayOf(JVM)
      type = github
      tags = Tags["test", "html", "template", "dom", "dsl", "parser", "webcrawler", "scraper", "ktor", "spring-boot"]
    }
    link {
      name = "krzema12/PlotAssert"
      desc = "Test the shape of your functions!"
      href = "https://github.com/krzema12/PlotAssert"
      type = github
      tags = Tags["test", "testing", "dsl", "ascii-art"]
    }
    link {
      name = "EranBoudjnah/TestIt"
      desc = "Generate unit testing boilerplate from kotlin files."
      href = "https://github.com/EranBoudjnah/TestIt"
      type = github
      tags = Tags["test", "testing", "generator", "generation", "mock"]
    }
    link {
      name = "EranBoudjnah/RandomGenKt"
      desc = "Initialize instances of any class with generated data."
      href = "https://github.com/EranBoudjnah/RandomGenKt"
      type = github
      tags = Tags["test", "testing", "random", "random-generation"]
    }
    link {
      name = "KennethWussmann/mock-fuel"
      desc = "JUnit 5 extension to easily mock external HTTP requests made with the HTTP client Fuel."
      href = "https://github.com/KennethWussmann/mock-fuel"
      type = github
      tags = Tags["test", "testing", "mock", "fuel", "junit"]
    }
    link {
      name = "jcornaz/kwik"
      desc = "A property-based testing library for Kotlin. Execute tests with randomized inputs with a test-engine agnostic and compile-time safe library."
      href = "https://github.com/jcornaz/kwik"
      type = github
      tags = Tags["test", "testing", "assert", "random", "random-generation"]
    }
    link {
      name = "from-source/kiwi"
      desc = "Fluent assertions library with support of json path."
      href = "https://github.com/from-source/kiwi"
      type = github
      tags = Tags["test", "testing", "assert", "dsl", "multiplatform", "jsonpath"]
    }
  }
  subcategory("Dependency Injection") {
    link {
      name = "Kodein-Framework/Kodein-DI"
      desc = "Painless Kotlin Dependency Injection."
      href = "https://github.com/Kodein-Framework/Kodein-DI"
      type = github
      tags = Tags["di", "dependency injection"]
    }
    link {
      name = "kailan/kodeinject"
      desc = "Constructor dependency injection for Kodein."
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
    link {
      name = "JLLeitschuh/kotlin-guiced"
      desc = "Convenience Kotlin API over the Google Guice DI Library."
      href = "https://github.com/JLLeitschuh/kotlin-guiced"
      type = github
      tags = Tags["Dependency Injection", "Guice"]
    }
    link {
      name = "authzee/kotlin-guice"
      desc = "Guice DSL extensions for Kotlin"
      href = "https://github.com/authzee/kotlin-guice"
      type = github
      tags = Tags["guice", "dependency injection", "di"]
    }
    link {
      name = "Ekito/koin"
      desc = "A functional Kotlin dependency injection framework for Android and JVM."
      href = "https://github.com/Ekito/koin"
      type = github
      tags = Tags["android", "dependency-injection", "injection", "functional"]
    }
    link {
      name = "Rasalexman/KODI"
      desc = "light-weight KOtlin Dependency Injection Framework with or without reflection module without kapt"
      href = "https://github.com/Rasalexman/KODI"
      type = github
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
    link {
      name = "marcoferrer/kroto-plus"
      desc = "Protoc plugin for bringing together Kotlin, Protobuf, Coroutines, and gRPC."
      href = "https://github.com/marcoferrer/kroto-plus"
      type = github
      tags = Tags["coroutines","grpc","protobuf"]
    }
    link {
      name = "cloudoptlab/cloudopt-next"
      desc = "A next-generation Java web lightweight framework based on vertx and kotlin. "
      href = "https://github.com/cloudoptlab/cloudopt-next"
      type = github
      tags = Tags["web", "vertx", "spring", "restful", "springboot", "springboot", "cloudopt", "next"]
    }
    link {
      name = "Rasalexman/coroutinesmanager"
      desc = "try-catch safety coroutines manager"
      href = "https://github.com/Rasalexman/coroutinesmanager"
      type = github
    }
    link {
      name = "rozkminiacz/FlowRiddles"
      desc = "Repository for learning Kotlin Flow API"
      href = "https://github.com/rozkminiacz/FlowRiddles"
      type = github
      tags = Tags["kotlin", "kotlin-flow", "learning", "riddles"]
    }
  }
  subcategory("Functional Programming") {
    link {
      name = "arrow-kt/arrow"
      desc = "Functional companion to Kotlin's Standard Library."
      href = "https://github.com/arrow-kt/arrow"
      type = github
      tags = Tags["fp", "functional"]
    }
    link {
      name = "arrow-kt/arrow-meta"
      desc = "Functional companion to Kotlin's Compiler."
      href = "https://github.com/arrow-kt/arrow-meta"
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
      name = "michaelbull/kotlin-result"
      desc = "A Result monad for modelling success or failure operations - inspired by Elm, Rust, & Haskell."
      href = "https://github.com/michaelbull/kotlin-result"
      type = github
      tags = Tags["fp", "functional", "result", "monad", "either", "type"]
    }
    link {
      name = "fork-handles/result4k"
      desc = "Result monad for type safe error handling in Kotlin"
      href = "https://github.com/fork-handles/forkhandles/blob/trunk/result4k"
      type = github
      tags = Tags["fp", "functional", "result", "monad", "either", "type", "error handling"]
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
      name = "poetix/klenses"
      desc = "Lenses for Kotlin."
      href = "https://github.com/poetix/klenses"
      type = github
      tags = Tags["fp", "functional", "lenses"]
    }
    link {
      name = "reactor/reactor-core"
      desc = "Non-Blocking Reactive Streams Foundation for the JVM. Natively supports Kotlin, since 3.1.0.M3."
      href = "https://github.com/reactor/reactor-core"
      type = github
      tags = Tags["reactive", "stream", "functional"]
    }
    link {
      name = "UrbanCompass/Snail-Kotlin"
      desc = "An observables framework for Kotlin."
      href = "https://github.com/UrbanCompass/Snail-Kotlin"
      type = github
      tags = Tags["observables", "fp", "functional"]
    }
  }
  subcategory("JSON") {
    link {
      name = "Kotlin/kotlinx.serialization"
      desc = "Kotlin multiplatform / multi-format reflectionless serialization"
      href = "https://github.com/Kotlin/kotlinx.serialization"
      type = github
    }
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
      name = "Shengaero/kotlin-json"
      desc = "A lightweight, stylistic, optimized, and multiplatform JSON library for Kotlin-JVM and Kotlin-JS"
      href = "https://github.com/Shengaero/kotlin-json"
      type = github
      tags = Tags["json", "multiplatform"]
    }
    link {
      name = "fboldog/ext4klaxon"
      desc = "Type Extensions (Long, Int, Enum, Date) for Klaxon."
      href = "https://github.com/fboldog/ext4klaxon"
      type = github
      tags = Tags["json"]
    }
    link {
      name = "marifeta/kvalidator"
      desc = "Kotlin validator (compatible with laravel validation rules) for json kotlinx.serialization!"
      href = "https://github.com/marifeta/kvalidator"
      type = github
      tags = Tags["json", "validation", "laravel", "laravel-validation"]
    }
    link {
      name = "Jire/KTON"
      desc = "Object notation in pure Kotlin!"
      href = "https://github.com/Jire/KTON"
      type = github
      tags = Tags["JSON", "XML"]
    }
    link {
      name = "fluidsonic/fluid-json"
      desc = "A JSON library written in pure Kotlin."
      href = "https://github.com/fluidsonic/fluid-json"
      type = github
      tags = Tags["json"]
    }
    link {
      name = "s4kibs4mi/kotlin-jsonq"
      desc = "A simple Kotlin library to Query over Json Data."
      href = "https://github.com/s4kibs4mi/kotlin-jsonq"
      type = github
      tags = Tags["json", "json-query", "json-manager", "kotlin-library", "kotlin-android"]
    }
    link {
      name = "aafanasev/kson"
      desc = "Auto-generate GSON type adapters for Kotlin data classes"
      href = "https://github.com/aafanasev/kson"
      type = github
      tags = Tags["json", "gson", "type adapter", "annotation processing", "kapt"]
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
      name = "ebean-orm/ebean"
      desc = "Ebean is a Java & Kotlin ORM including type safe kotlin queries"
      href = "https://github.com/ebean-orm/ebean"
      type = github
      tags = Tags["database", "sql", "orm", "query", "type-safe builder", "jpa"]
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
      name = "darkredz/zeko-sql-builder"
      desc = "Zeko SQL Builder is a high-performance lightweight SQL query library with optional data access through HikariCP & Vert.x JDBC client"
      href = "https://github.com/darkredz/Zeko-SQL-Builder"
      type = github
      tags = Tags["database", "sql", "query", "vert.x", "hikari-cp"]
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
    link {
      name = "shyiko/levelkt"
      desc = "LevelDB client for Kotlin and/or Java 8+."
      href = "https://github.com/shyiko/levelkt"
      type = github
      tags = Tags["leveldb", "embedded"]
    }
    link {
      name = "SubiyaCryolite/jds"
      desc = "Jenesis Data Store: a dynamic, cross platform, high performance, ORM data-mapper. Designed to assist in rapid development and data mining."
      href = "https://github.com/SubiyaCryolite/jds"
      type = github
      tags = Tags["orm", "postgres", "mysql", "mssql", "sqlite", "oracle"]
    }
    link {
      name = "dizitart/nitrite-database"
      desc = "Potassium Nitrite is a kotlin extension of nitrite database, an open source nosql embedded document store with mongodb like api."
      href = "https://github.com/dizitart/nitrite-database/tree/master/potassium-nitrite"
      type = github
      tags = Tags["nosql", "embedded", "documentdb", "object-storage"]
    }
    link {
      name = "pm-dev/kotlin-gremlin-ogm"
      desc = "Kotlin-gremlin-ogm is a type-safe object/graph mapping library for Gremlin enabled graph databases."
      href = "https://github.com/pm-dev/awesome-kotlin"
      type = github
      tags = Tags["nosql", "graph", "database", "gremlin", "janusgraph", "orm"]
    }
    link {
      name = "fluidsonic/fluid-mongo"
      desc = "Coroutine support for MongoDB built on top of the official Reactive Streams Java Driver"
      href = "https://github.com/fluidsonic/fluid-mongo"
      platforms = arrayOf(JVM)
      type = github
      tags = Tags["database", "mongodb", "nosql", "coroutines"]
    }
    link {
      name = "jasync-sql/jasync-sql"
      desc = "Kotlin port of mauricio's async driver for postgres/mysql."
      href = "https://github.com/jasync-sql/jasync-sql"
      type = github
      tags = Tags["postgres", "mysql", "database driver"]
    }
    link {
      name = "kotlin-orm/ktorm"
      desc = "A lightweight ORM Framework for Kotlin. Provides strong-typed and flexible SQL DSL and convenient sequence APIs to reduce our duplicated effort on database operations. "
      href = "https://github.com/kotlin-orm/ktorm"
      type = github
      tags = Tags["ORM", "SQL", "DSL", "JDBC"]
    }
    link {
      name = "TouK/krush"
      desc = "Idiomatic persistence layer for Kotlin, based on Exposed. It’s based on a compile-time JPA annotation processor that generates Exposed DSL table and objects mappings from your data classes."
      href = "https://github.com/TouK/krush"
      type = github
    }
  }
  subcategory("Tools") {
    link {
      name = "SonarSource/sonarlint-intellij"
      desc = "An IDE extension that helps you detect and fix quality issues as you write code."
      href = "https://github.com/SonarSource/sonarlint-intellij"
      type = github
      tags = Tags["scripting", "ide", "linter", "language"]
    }
    link {
      name = "Kotlin/dokka"
      desc = "Documentation Engine for Kotlin."
      href = "https://github.com/Kotlin/dokka"
      type = github
    }
    link {
      name = "Vorlonsoft/EasyDokkaPlugin"
      desc = "Gradle Script plugin to generate documentation by Dokka for Kotlin and Java, Android and non-Android projects."
      href = "https://github.com/Vorlonsoft/EasyDokkaPlugin"
      type = github
    }
    link {
      name = "Vorlonsoft/GradleMavenPush"
      desc = "Gradle Script plugin to upload Gradle Android/Kotlin/Java Artifacts to Maven repositories (JCenter, Maven Central, ...)."
      href = "https://github.com/Vorlonsoft/GradleMavenPush"
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
      name = "pinterest/ktlint"
      desc = "An anti-bikeshedding Kotlin linter with built-in formatter."
      href = "https://github.com/pinterest/ktlint"
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
      name = "detekt/detekt"
      desc = "Static code analysis for Kotlin."
      href = "https://github.com/detekt/detekt"
      type = github
      tags = Tags["check style", "checkstyle"]
    }
    link {
      name = "mkohm/detekt-hint"
      desc = "Detection of design principle violations as a plugin to detekt."
      href = "https://github.com/mkohm/detekt-hint"
      type = github
    }
    link {
      name = "cypressious/KotlinW"
      desc = "A small wrapper for the Kotlin compiler that can be used to execute .kts scripts."
      href = "https://github.com/cypressious/KotlinW"
      type = github
      tags = Tags["wrapper", "scripting"]
    }
    link {
      name = "s1monw1/KtsRunner"
      desc = "Library for executing .kts files from regular Kotlin code using Java Scripting Engines API"
      href = "https://github.com/s1monw1/KtsRunner"
      type = github
      tags = Tags["dsl", "scripting"]
    }
    link {
      name = "jmfayard/buildSrcVersions"
      desc = "Better Gradle dependencies management inside the IDE. Search for available updates."
      href = "https://github.com/jmfayard/buildSrcVersions"
      type = github
      tags = Tags["gradle", "plugin", "build", "development", "libraries", "versions"]
    }
  }
  subcategory("Continuous Integration") {
    link {
      name = "danger/kotlin"
      desc = "Stop saying \"you forgot to …\" in code review in Kotlin"
      href = "https://github.com/danger/kotlin"
      type = github
      tags = Tags["danger", "ci", "continuous", "integration", "code", "review"]
    }
  }
  subcategory("Code Generators") {
    link {
      name = "jhipster/jhipster-kotlin"
      desc = "A scaffold generator to generate web apps or APIs using springboot and angularJS or React"
      href = "https://github.com/jhipster/jhipster-kotlin"
      type = github
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
    link {
      name = "egorzhdan/networkinkt"
      desc = "Multiplatform coroutine-based HTTP client."
      href = "https://github.com/egorzhdan/networkinkt"
      type = github
      tags = Tags["http", "http client", "coroutines"]
    }
    link {
      name = "rybalkinsd/kohttp"
      desc = "Kotlin DSL-based HTTP client."
      href = "https://github.com/rybalkinsd/kohttp"
      type = github
      tags = Tags["http", "http client", "dsl", "okhttp"]
    }
    link {
      name = "curiousnikhil/Asynkio"
      desc = "Make asynchronous calls painlessly with async/await style."
      href = "https://github.com/CuriousNikhil/AsynKio"
      type = github
      tags = Tags["http", "http client", "coroutines"]
    }
    link {
      name = "speekha/httpmocker"
      desc = "Kotlin library to handle offline mode easily with OkHttp."
      href = "https://github.com/speekha/httpmocker"
      type = github
      tags = Tags["http", "http client", "offline", "okhttp"]
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
      name = "fwcd/KotlinLanguageServer"
      desc = "Smart code completion, diagnostics and more for Kotlin using the Language Server Protocol. VS Code extension included."
      href = "https://github.com/fwcd/KotlinLanguageServer"
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
      name = "zeganstyl/thelema-engine"
      desc = "3d graphics engine in Kotlin, based on sources of libGDX. Platforms: JVM, TeaVM, Koltin/JS, Kotlin/Native. See live demo: [TeaVM](https://zeganstyl.github.io/thelema-teavm-tests/), [Kotlin/JS](https://zeganstyl.github.io/thelema-kxjs-demo/)"
      href = "https://github.com/zeganstyl/thelema-engine"
      type = github
      tags = Tags["thelema-engine", "game dev", "games", "desktop", "webgl"]
    }
    link {
      name = "libktx/ktx"
      desc = "Kotlin utilities for LibGDX applications."
      href = "https://github.com/libktx/ktx"
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
    link {
      name = "vassilibykov/AdventKT"
      desc = "A Kotlin-based DSL for text adventures, with a partial replica of the classic Colossal Cave as an example."
      href = "https://github.com/vassilibykov/AdventKT"
      type = github
      tags = Tags["games", "text adventures"]
    }
    link {
      name = "Hexworks/zircon"
      desc = "An extensible text GUI library which targets multiple platforms and designed specifically for game developers, written in Kotlin."
      href = "https://github.com/Hexworks/zircon"
      type = github
      tags = Tags["text-gui", "games", "game-dev"]
    }
    link {
      name = "korlibs/KorGE"
      desc = "Modern Multiplatform Game Engine for Kotlin. Write games for the JVM, JavaScript, Android and iOS in no time using Kotlin."
      href = "https://github.com/korlibs/korge"
      type = github
      tags = Tags["desktop", "android", "games", "game-dev"]
    }
  }
  subcategory("Misc") {
    link {
      name = "themichailov/kache"
      desc = "Kotlin functions caching library, reducing count of function executions."
      href = "https://github.com/themichailov/kache"
      type = github
      tags = Tags["cache"]
    }
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
      name = "DragonKnightOfBreeze/breeze-framework"
      desc = "Integrated code framework base on Kotlin, provide many useful extensions for standard library and some frameworks."
      href = "https://github.com/DragonKnightOfBreeze/breeze-framework"
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
      name = "debop/koda-time"
      desc = "Joda Time Extensions in Kotlin. (From Java 8 use java.time instead)"
      href = "https://github.com/debop/koda-time"
      type = github
      tags = Tags["joda-time", "jsr-310"]
    }
    link {
      name = "saschpe/log4k"
      desc = "Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS, JavaScript and plain JVM environments."
      href = "https://github.com/saschpe/log4k"
      type = github
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
      name = "doyaaaaaken/kotlin-csv"
      desc = "A pure kotlin simple csv reader/writer."
      href = "https://github.com/doyaaaaaken/kotlin-csv"
      type = github
      tags = Tags["csv", "kotlin multiplatform"]
    }
    link {
      name = "phxql/aleksa"
      desc = "Aleksa is a small framework for writing Alexa Skills in Kotlin."
      href = "https://github.com/phxql/aleksa"
      type = github
      tags = Tags["alexa", "amazon echo", "text to speech"]
    }
    link {
      name = "TicketmasterMobileStudio/actions-on-google-kotlin"
      desc = "Port of official Node.js SDK to Kotlin. Complete with all features and tests and nearly identical API."
      href = "https://github.com/TicketmasterMobileStudio/actions-on-google-kotlin"
      type = github
      tags = Tags["actions on google", "google assistant", "google home"]
    }
    link {
      name = "cretz/asmble"
      desc = "Compile WebAssembly to JVM and other WASM tools."
      href = "https://github.com/cretz/asmble"
      type = github
      tags = Tags["wasm", "webassembly"]
    }
    link {
      name = "h0tk3y/better-parse"
      desc = "A nice parser combinator library for Kotlin"
      href = "https://github.com/h0tk3y/better-parse"
      type = github
      tags = Tags["parser", "parser-combinator", "grammar", "lexer"]
    }
    link {
      name = "fork-handles/parser4k"
      desc = "Recursive descent parser combinator library"
      href = "https://github.com/fork-handles/forkhandles/tree/trunk/parser4k"
      type = github
      tags = Tags["parser", "parser-combinator", "recursive descent"]
    }
    link {
      name = "fork-handles/tuples4k"
      desc = "Tuple classes"
      href = "https://github.com/fork-handles/forkhandles/tree/trunk/tuples4k"
      type = github
      tags = Tags["tuples"]
    }
    link {
      name = "Kotlin/kotlinx.atomicfu"
      desc = "The idiomatic way to use atomic operations in Kotlin."
      href = "https://github.com/Kotlin/kotlinx.atomicfu"
      type = github
      tags = Tags["atomic"]
    }
    link {
      name = "vjames19/kotlin-futures"
      desc = "A collections of extension functions to make the JVM Future, CompletableFuture, ListenableFuture API more functional and Kotlin like."
      href = "https://github.com/vjames19/kotlin-futures"
      type = github
    }
    link {
      name = "kunalsheth/units-of-measure"
      desc = "A type-safe dimensional analysis library for Kotlin."
      href = "https://github.com/kunalsheth/units-of-measure"
      type = github
      tags = Tags["dimensional-analysis", "typesafety", "metaprogramming"]
    }
    link {
      name = "spoptchev/kotlin-preconditions"
      desc = "Precondition error checking in kotlin."
      href = "https://github.com/spoptchev/kotlin-preconditions"
      type = github
      tags = Tags["preconditions"]
    }
    link {
      name = "spoptchev/scientist"
      desc = "A kotlin library for refactoring code. Port of GitHub's scientist."
      href = "https://github.com/spoptchev/scientist"
      type = github
      tags = Tags["scientist", "refactoring"]
    }
    link {
      name = "soywiz/klock"
      desc = "Consistent and portable date and time utilities for multiplatform kotlin (JVM, JS and Common)."
      href = "https://github.com/soywiz/klock"
      type = github
      tags = Tags["js", "date", "time"]
    }
    link {
      name = "d-max/dsl-logger"
      desc = "Simple DSL for logging with logger abstraction layer"
      href = "https://github.com/d-max/dsl-logger"
      type = github
      tags = Tags["dsl", "logging", "android", "slf4j"]
    }
    link {
      name = "evoasm/kasm"
      desc = "x64/x86-64 assembler and execution library"
      href = "https://github.com/evoasm/kasm"
      type = github
      tags = Tags["x64", "x86", "assembly", "assembler"]
    }
    link {
      name = "korlibs/kds"
      desc = "Optimized Kotlin Data Structures for JVM, JS and Common"
      href = "https://github.com/korlibs/kds"
      type = github
      tags = Tags["ds"]
    }
    link {
      name = "s1monw1/TLSLibrary"
      desc = "Simple TlsLibrary written in Kotlin - Provides DSL for creating TLS connections"
      href = "https://github.com/s1monw1/TLSLibrary"
      type = github
      tags = Tags["dsl", "tls", "ssl", "jsse"]
    }
    link {
      name = "KotlinNLP/SimpleDNN"
      desc = "SimpleDNN is a machine learning lightweight open-source library part of KotlinNLP and has been designed to support relevant neural network architectures in natural language processing tasks."
      href = "https://github.com/KotlinNLP/SimpleDNN"
      type = github
      tags = Tags["machine-learning", "recurrent-neural-networks", "feedforward-neural-network", "natural-language-processing"]
    }
    link {
      name = "nickhristov/krakdown"
      desc = "A native markdown parser written in Kotlin."
      href = "https://github.com/nickhristov/krakdown"
      type = github
      tags = Tags["markdown", "parser"]
    }
    link {
      name = "ziggy42/kolor"
      desc = "A library to print colored strings, with Kotlin."
      href = "https://github.com/ziggy42/kolor"
      type = github
      tags = Tags["ansi-colors", "colors", "shell"]
    }
    link {
      name = "holgerbrandl/kravis"
      desc = "A Kotlin grammar for scientific data visualization"
      href = "https://github.com/holgerbrandl/kravis"
      type = github
      tags = Tags["Data Science"]
    }
    link {
      name = "MiloszKrajewski/stateful4k"
      desc = "State Machine Construction Kit for Kotlin"
      href = "https://github.com/MiloszKrajewski/stateful4k"
      type = github
      tags = Tags["state machine"]
    }
    link {
      name = "charleskorn/kaml"
      desc = "YAML support for kotlinx.serialization"
      href = "https://github.com/charleskorn/kaml"
      type = github
      tags = Tags["serialization", "yaml"]
    }
    link {
      name = "jershell/kbson"
      desc = "Bson support for kotlinx.serialization"
      href = "https://github.com/jershell/kbson"
      type = github
    }
    link {
      name = "pemistahl/lingua"
      desc = "A language detection library suitable for long and short text alike"
      href = "https://github.com/pemistahl/lingua"
      type = github
      tags = Tags["nlp", "natural-language-processing", "linguistics", "languages", "language-detection", "language-modeling", "machine-learning"]
    }
    link {
      name = "sandjelkovic/kxjtime"
      desc = "Lightweight Kotlin extensions for java.time API"
      href = "https://github.com/sandjelkovic/kxjtime"
      type = github
      tags = Tags["extensions","time","date","jdk8", "java.time", "utility"]
    }
    link {
      name = "pmwmedia/tinylog"
      desc = "Lightweight logging framework with native logging API for Kotlin."
      href = "https://github.com/pmwmedia/tinylog"
      platforms = arrayOf(JVM, ANDROID)
      type = github
      tags = Tags["logging", "logger"]
    }
    link {
      name = "Lewik/klog"
      desc = "Minimalistic and multiplatform logging for Kotlin"
      href = "https://github.com/Lewik/klog"
      type = github
      platforms = arrayOf(JVM, JS, COMMON)
      tags = Tags["logging", "multiplatform"]
    }
    link {
      name = "aafanasev/sekret"
      desc = "Kotlin compiler plugin to exclude secret properties from toString() of Data class"
      href = "https://github.com/aafanasev/sekret"
      platforms = arrayOf(JVM, ANDROID)
      type = github
      tags = Tags["Data class", "toString"]
    }
    link {
      name = "Rasalexman/KDispatcher"
      desc = "Simple and light-weight event dispatcher for Kotlin"
      href = "https://github.com/Rasalexman/KDispatcher"
      type = github
    }
    link {
      name = "dotCipher/kase-format"
      desc = "String case conversion and detection library"
      href = "https://github.com/dotCipher/kase-format"
      type = github
    }
    link {
      name = "vittee/kformula"
      desc = "Mathematical expression engine written in Kotlin, running on JVM."
      href = "https://github.com/vittee/kformula"
      type = github
    }
    link {
      name = "kotlin-telegram-bot/kotlin-telegram-bot"
      desc = "A wrapper for the Telegram Bot API written in Kotlin."
      href = "https://github.com/kotlin-telegram-bot/kotlin-telegram-bot"
      type = github
    }
    link {
      name = "aminography/PrimeCalendar"
      desc = "Provides all of the java.util.Calendar functionalities for Civil, Persian, Hijri, Japanese, etc, as well as their conversion to each other."
      href = "https://github.com/aminography/PrimeCalendar"
      type = github
    }
    link {
      name = "pearxteam/kasechange"
      desc = "Multiplatform Kotlin library to convert strings between various case formats including Camel Case, Snake Case, Pascal Case and Kebab Case"
      href = "https://github.com/pearxteam/kasechange"
      type = github
      platforms = arrayOf(ANDROID, COMMON, IOS, JS, JVM, NATIVE, WASM)
      tags = Tags["multiplatform", "string", "case-conversion"]
    }
    link {
      name = "pearxteam/kpastebin"
      desc = "Multiplatform Kotlin library to interact with the pastebin.com API"
      href = "https://github.com/pearxteam/kpastebin"
      type = github
      platforms = arrayOf(ANDROID, COMMON, IOS, JS, JVM, NATIVE)
      tags = Tags["multiplatform", "pastebin", "api"]
    }
    link {
      name = "justwrote/kjob"
      desc = "A coroutine based persistent background scheduler written in Kotlin."
      href = "https://github.com/justwrote/kjob"
      type = github
      tags = Tags["job-scheduler", "task", "job-queue", "kotlin", "runner", "job", "mongodb", "kotlin-library", "kotlin-coroutines", "queue", "job-processor"]
    }
    link {
      name = "fluidsonic/fluid-pdf"
      desc = "Easy PDF generation with HTML & CSS using Chromium or Google Chrome"
      href = "https://github.com/fluidsonic/fluid-pdf"
      platforms = arrayOf(JVM)
      type = github
      tags = Tags["pdf"]
    }
    link {
      name = "AhmedMourad0/no-copy"
      desc = "A Kotlin compiler plugin that performs 'copy-erasure' on data classes."
      href = "https://github.com/AhmedMourad0/no-copy"
      platforms = arrayOf(ANDROID, COMMON, IOS, JS, JVM, NATIVE, WASM)
      type = github
      tags = Tags["data-class", "compiler-plugin", "value-based-classes", "binary-compatibility"]
    }
  }
  subcategory("Raspberry Pi") {
    link {
      name = "mhashim6/Pi4K"
      desc = "Pi4J Kotlin bindings."
      href = "https://github.com/mhashim6/Pi4K"
      type = github
      tags = Tags["raspberry-pi", "raspberrypi", "gpio", "dsl", "pi4j"]
    }
  }
  subcategory("Multiplatform") {
    link {
      name = "ionspin/kotlin-multiplatform-bignum"
      desc = "Pure kotlin multiplatform arbitrary precision arithmetic library."
      href = "https://github.com/ionspin/kotlin-multiplatform-bignum"
      type = github
      tags = Tags["multiplatform", "bignum", "biginteger", "bigdecimal", "arbitrary-precision"]
    }
    link {
      name = "davidepianca98/KMQTT"
      desc = "MQTT Broker library/executables for Kotlin multiplatform."
      href = "https://github.com/davidepianca98/KMQTT"
      type = github
    }
    link {
      name = "GitLiveApp/firebase-kotlin-sdk"
      desc = "A Kotlin-first Multiplatform SDK for Firebase supporting iOS, Android & Web"
      href = "https://github.com/GitLiveApp/firebase-kotlin-sdk"
      type = github
      tags = Tags["firebase", "firestore", "multiplatform"]
    }
  }
  subcategory("Extensions") {
    link {
      name = "Kotlin/kotlinx.support"
      desc = "Extension and top-level functions to use JDK7/JDK8 features in Kotlin 1.0."
      href = "https://github.com/Kotlin/kotlinx.support"
      type = github
      tags = Tags["extensions", "jdk8", "jdk7"]
    }
    link {
      name = "vanshg/KrazyKotlin"
      desc = "A collection of useful Kotlin Extension"
      href = "https://github.com/vanshg/KrazyKotlin"
      type = github
      tags = Tags["extensions"]
    }
    link {
      name = "hankdavidson/ktime"
      desc = "Extensions to the java8 time library"
      href = "https://github.com/hankdavidson/ktime"
      type = github
      tags = Tags["extensions", "jdk8", "time", "date", "range"]
    }
    link {
      name = "LukasForst/katlib"
      desc = "A collection of Kotlin extension functions and utilities."
      href = "https://github.com/LukasForst/katlib"
      type = github
      tags = Tags["extensions"]
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
    link {
      name = "daviddenton/configur8"
      desc = "A Kotlin port of Configur8 - A Type Safe Configuration API for Kotlin, extendable to user-defined types."
      href = "https://github.com/daviddenton/configur8"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "ufoscout/properlty"
      desc = "Simple configuration library with placeholders resolution and zero magic!"
      href = "https://github.com/ufoscout/properlty"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "uchuhimo/konf"
      desc = "A type-safe cascading configuration library for Kotlin/Java, supporting most configuration formats"
      href = "https://github.com/uchuhimo/konf"
      type = github
      tags = Tags["configuration"]
    }
    link {
      name = "sksamuel/hoplite"
      desc = "A library for loading configuration files into typesafe Kotlin data classes in a boilerplate-free way"
      href = "https://github.com/sksamuel/hoplite"
      type = github
    }
    link {
      name = "Nohus/AutoKonfig"
      desc = "A Kotlin configuration library with batteries included. Type-safe, zero lines of setup."
      href = "https://autokonfig.nohus.dev/"
      tags = Tags["configuration"]
    }
  }
  subcategory("Graphics") {
    link {
      name = "data2viz/data2viz"
      desc = "multiplatform dataviz library, d3js port"
      href = "https://github.com/data2viz/data2viz"
      type = github
      tags = Tags["d3js", "multiplatform", "svg", "javafx"]
    }
    link {
      name = "kotlin-graphics/glm"
      desc = "g-truck glm port, opengl mathematics library"
      href = "https://github.com/kotlin-graphics/glm"
      type = github
      tags = Tags["glm", "opengl", "vulkan", "matrices", "vectors", "math-library"]
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
      tags = Tags["gli", "opengl", "vulkan", "texture"]
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
      tags = Tags["vr", "openvr", "steamvr", "opengl", "vulkan"]
    }
    link {
      name = "kotlin-graphics/assimp"
      desc = "Open Asset Import Library port"
      href = "https://github.com/kotlin-graphics/assimp"
      type = github
      tags = Tags["assimp", "stl", "md2", "ply", "obj", "collada", "fbx", "opengl", "vulkan"]
    }
    link {
      name = "kotlin-graphics/imgui"
      desc = "jvm port of imgui"
      href = "https://github.com/kotlin-graphics/imgui"
      type = github
      tags = Tags["opengl", "gui", "gamedev", "dear-imgui"]
    }
    link {
      name = "kotlin-graphics/bullet"
      desc = "bullet port"
      href = "https://github.com/kotlin-graphics/bullet"
      type = github
      tags = Tags["opengl", "vulkan", "gamedev", "physics", "simulation", "robotics", "kinematics"]
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
    link {
      name = "java-opengl-labs/Vulkan"
      desc = "port of https://github.com/SaschaWillems/Vulkan"
      href = "https://github.com/java-opengl-labs/Vulkan"
      type = github
      tags = Tags["vulkan", "tutorial", "lwjgl"]
    }
    link {
      name = "Jonatino/JOGL2D"
      desc = "Zero-overhead 2D rendering library for JOGL using Kotlin."
      href = "https://github.com/Jonatino/JOGL2D"
      type = github
      tags = Tags["jogl", "opengl"]
    }
    link {
      name = "soywiz/kaifu2x"
      desc = "Waifu2x port to Kotlin as library and CLI. Convolutional-neural-network based upscaler for Anime-like images and noise/artifact reduction."
      href = "https://github.com/soywiz/kaifu2x"
      type = github
      tags = Tags["waifu2x", "convolutional-neural-networks", "kotlin", "noise-reduction", "scaler"]
    }
    link {
      name = "nwillc/ksvg"
      desc = "Kotlin SVG image generation DSL, supporting inline and file formats."
      href = "https://github.com/nwillc/ksvg"
      type = github
      tags = Tags["ksvg", "svg", "kotlin", "dsl"]
    }
    link {
      name = "openrndr/openrndr"
      desc = "OPENRNDR, a framework for creative coding in Kotlin. Accelerated 2D and 3D graphics, vector graphics, shaders and animation"
      href = "https://github.com/openrndr/openrndr"
      type = github
      tags = Tags["opengl", "creative-coding", "kotlin", "dsl", "shaders", "ljwgl"]
    }
    link {
      name = "openrndr/orx"
      desc = "ORX is a collection extras for OPENRNDR which add support for sensors, live-coding, user interfaces and much more."
      href = "https://github.com/openrndr/orx"
      type = github
      tags = Tags["opengl", "creative-coding", "kotlin", "dsl", "shaders", "ljwgl"]
    }
    link {
      name = "markaren/three.kt"
      desc = "Kotlin port of three.js JavaScript 3D library"
      href = "https://github.com/markaren/three.kt"
      type = github
      tags = Tags["opengl", "lwjgl3", "three-js"]
    }
  }
  subcategory("Data Science") {
    link {
      name = "Kotlin for Data Science"
      desc = "Kotlin for Data Science overview page"
      href = "https://kotlinlang.org/docs/reference/data-science-overview.html"
    }
    link {
      name = "JetBrains/lets-plot-kotlin"
      desc = "Grammar of graphics visualization for Kotlin"
      href = "https://github.com/JetBrains/lets-plot-kotlin"
      type = github
    }
    link {
      name = "Kotlin/kotlin-jupyter"
      desc = "Official Kotlin Jupyter kernel"
      href = "https://github.com/Kotlin/kotlin-jupyter"
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
      name = "mipt-npm/kmath"
      desc = "A (not so numpy-like) multiplatform mathematical library with higher level abstractions."
      href = "https://github.com/mipt-npm/kmath"
      type = github
    }
    link {
      name = "MarcinMoskala/KotlinDiscreteMathToolkit"
      desc = "Set of extensions for Kotlin that provides Discrete Math functionalities as an Kotlin extension functions."
      href = "https://github.com/MarcinMoskala/KotlinDiscreteMathToolkit"
      type = github
      tags = Tags["data science", "math"]
    }
    link {
      name = "sekwiatkowski/Komputation"
      desc = "A neural network framework written in Kotlin."
      href = "https://github.com/sekwiatkowski/Komputation"
      type = github
      tags = Tags["data science", "neural-networks", "deep-learning", "machine-learning", "artificial-intelligence"]
    }
    link {
      name = "sanity/pairAdjacentViolators"
      desc = "A Kotlin implementation of the Pair Adjacent Violators algorithm for isotonic regression."
      href = "https://github.com/sanity/pairAdjacentViolators"
      type = github
      tags = Tags["data science", "pair adjacent violators"]
    }
    link {
      name = "breandan/kotlingrad"
      desc = "Shape-Safe Differentiable Programming with Algebraic Data Types."
      href = "https://github.com/breandan/kotlingrad"
      type = github
    }
  }
  subcategory("Command Line Interface") {
    link {
      name = "ajalt/clikt"
      desc = "Clikt - Intuitive command line interface parsing for Kotlin."
      href = "https://github.com/ajalt/clikt"
      type = github
      tags = Tags["cli", "command line parser", "argument parser", "option parser"]
    }
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
    link {
      name = "ajalt/mordant"
      desc = "Mordant - Full-featured text styling for Kotlin command line applications."
      href = "https://github.com/ajalt/mordant"
      type = github
      tags = Tags["cli", "ansi", "color"]
    }
    link {
      name = "aPureBase/arkenv"
      desc = "Fully fledged command line arguments / environment variables parser via simple Kotlin delegates"
      href = "https://github.com/aPureBase/arkenv"
      type = github
      tags = Tags["cli", "argument", "env", "environment", "variables", "parser"]
    }
    link {
      name = "fork-handles/bunting4k"
      desc = "Simple, typesafe, testable command line flags"
      href = "https://github.com/fork-handles/forkhandles/tree/trunk/bunting4k"
      type = github
      tags = Tags["cli", "command", "argument", "flag", "option", "parser"]
    }
  }
  subcategory("Browsers") {
    link {
      name = "wendigo/chrome-reactive-kotlin"
      desc = "Chrome Debugging Protocol for Kotlin (with RxJava2)"
      href = "https://github.com/wendigo/chrome-reactive-kotlin"
      type = github
      tags = Tags["web", "chrome", "automation", "browser"]
    }
  }
}
