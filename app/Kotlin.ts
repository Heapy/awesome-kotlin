import {articlesLinks} from './rss/DataGenerator';

const data: Category[] = [{
  name: 'Links',
  subcategories: [{
    name: 'Official Links',
    links: [{
      name: 'JetBrains/kotlin',
      href: 'https://github.com/jetbrains/kotlin',
      type: 'github',
      tags: ['kotlin']
    }, {
      name: 'Home Page',
      href: 'http://kotlinlang.org/'
    }, {
      name: 'Language Reference',
      href: 'http://kotlinlang.org/docs/reference/'
    }, {
      name: 'Slack (6200+ users)',
      href: 'http://kotlinslackin.herokuapp.com/'
    }, {
      name: 'Public chat archive of Kotlin\'s Slack',
      href: 'http://kotlinlang.slackarchive.io/',
    }, {
      name: 'Try Kotlin!',
      href: 'http://try.kotlinlang.org/'
    }, {
      name: 'Blog',
      href: 'http://blog.jetbrains.com/kotlin/'
    }, {
      name: 'Issue Tracker',
      href: 'https://youtrack.jetbrains.com/issues/KT'
    }, {
      name: 'Twitter',
      href: 'https://twitter.com/kotlin'
    }, {
      name: 'Kotlin/KEEP',
      desc: 'Kotlin Evolution and Enhancement Process',
      href: 'https://github.com/Kotlin/KEEP',
      type: 'github',
      tags: ['keep', 'async', 'await']
    }]
  }, {
    name: 'Resources',
    links: [{
      name: '/r/Kotlin',
      href: 'https://www.reddit.com/r/Kotlin/'
    }, {
      name: 'Stackoverflow Documentation on Kotlin',
      href: 'http://stackoverflow.com/documentation/kotlin/topics',
      tags: ['documentation', 'stackoverflow']
    }, {
      name: 'Quora Kotlin',
      href: 'https://www.quora.com/topic/Kotlin',
      whitelisted: true
    }, {
      name: 'Trending Kotlin on Github',
      href: 'https://github.com/trending?l=kotlin'
    }, {
      name: 'Antonio Leiva - Android and any other monsters',
      href: 'http://antonioleiva.com/',
      type: 'blog'
    }, {
      name: 'LinkedIn: Kotlin Developers (Join!)',
      href: 'https://www.linkedin.com/groups/7417237/profile',
      whitelisted: true
    }, {
      name: 'Kotlin - Google+',
      href: 'https://plus.google.com/communities/104597899765146112928'
    }, {
      name: 'From Java To Kotlin',
      href: 'https://github.com/fabiomsr/from-java-to-kotlin'
    }, {
      name: 'dbacinski/Design-Patterns-In-Kotlin',
      desc: 'Design Patterns implemented in Kotlin.',
      href: 'https://github.com/dbacinski/Design-Patterns-In-Kotlin',
      type: 'github',
      tags: ['Behavioral Patterns', 'Creational Patterns', 'Structural Patterns']
    }, {
      name: 'Kotlin Cheat Sheet',
      href: 'https://speakerdeck.com/agiuliani/kotlin-cheat-sheet'
    }]
  }, {
    name: 'Books and Courses',
    links: [{
      name: 'Kotlin in Action - Dmitry Jemerov, Svetlana Isakova',
      href: 'https://manning.com/books/kotlin-in-action'
    }, {
      name: 'Kotlin for Android Developers - Antonio Leiva',
      href: 'https://leanpub.com/kotlin-for-android-developers'
    }, {
      name: 'Programming Kotlin - Stephen Samuel, Stefan Bocutiu',
      href: 'https://www.packtpub.com/application-development/programming-kotlin'
    }, {
      name: 'Kotlin for Java Developers',
      desc: '160-minute Android Course.',
      href: 'https://teamtreehouse.com/library/kotlin-for-java-developers'
    }, {
      name: 'Kotlin Programming: Next Level Java Development',
      desc: 'Learn coding in Kotlin from scratch!',
      href: 'https://www.udemy.com/kotlin-course/'
    }, {
      name: 'Introduction to Kotlin Programming by Hadi Hariri',
      desc: 'From Hello World to Interoperability with Java',
      href: 'http://shop.oreilly.com/product/0636920052982.do'
    }, {
      name: 'Advanced Kotlin Programming',
      desc: 'From Nested Functions to Asynchronous Programming',
      href: 'http://shop.oreilly.com/product/0636920052999.do'
    }]
  }]
}, {
  name: 'Libraries/Frameworks',
  subcategories: [{
    name: 'Web',
    links: [{
      name: 'Kotlin/ktor',
      desc: 'Web backend framework for Kotlin.',
      href: 'https://github.com/Kotlin/ktor',
      type: 'github'
    }, {
      name: 'TinyMission/kara',
      desc: 'Web framework written in Kotlin.',
      href: 'https://github.com/TinyMission/kara',
      type: 'github'
    }, {
      name: 'jean79/yested',
      desc: 'A Kotlin framework for building web applications in Javascript.',
      href: 'https://github.com/jean79/yested',
      type: 'github'
    }, {
      name: 'hhariri/wasabi',
      desc: 'An HTTP Framework built with Kotlin for the JVM.',
      href: 'https://github.com/wasabifx/wasabi',
      type: 'github',
      tags: ['web']
    }, {
      name: 'Kotlin/kotlinx.html',
      desc: 'Kotlin DSL for HTML.',
      href: 'https://github.com/Kotlin/kotlinx.html',
      type: 'github',
      tags: ['web', 'html']
    }, {
      name: 'MarioAriasC/KotlinPrimavera',
      desc: 'Spring support libraries for Kotlin.',
      href: 'https://github.com/MarioAriasC/KotlinPrimavera',
      type: 'github',
      tags: ['spring']
    }, {
      name: 'kohesive/kovert',
      desc: 'An invisible, super easy and powerful REST and Web framework over Vert.x or Undertow.',
      href: 'https://github.com/kohesive/kovert',
      type: 'github',
      tags: ['web', 'http', 'rest', 'vert.x', 'undertow']
    }, {
      name: 'sdeleuze/spring-kotlin',
      desc: 'Kotlin extensions for Spring projects.',
      href: 'https://github.com/sdeleuze/spring-kotlin',
      type: 'github',
      tags: ['spring', 'extensions']
    }, {
      name: 'Kotlin/kotlinx.coroutines',
      desc: 'Libraries built upon Kotlin coroutines.',
      href: 'https://github.com/Kotlin/kotlinx.coroutines',
      type: 'github',
      tags: ['async', 'await', 'yield', 'generator']
    }, {
      name: 'taskworld/kraph',
      desc: 'GraphQL request string builder written in Kotlin',
      href: 'https://github.com/taskworld/kraph',
      type: 'github',
      tags: ['graphql', 'builder']
    }, {
      name: 'sepatel/tekniq',
      desc: 'Full-feature HTTP DSL Framework, HTTP Client, JDBC DSL, Loading Cache and Configuration',
      href: 'https://github.com/sepatel/tekniq',
      type: 'github',
      tags: ['web', 'jdbc', 'http client', 'spark java', 'cache']
    }, {
      name: 'vert-x3/vertx-lang-kotlin',
      desc: 'This module provides Kotlin language bindings including DSL and extension functions for vert.x 3',
      href: 'https://github.com/vert-x3/vertx-lang-kotlin/',
      type: 'github',
      tags: ['web', 'vert.x']
    }, {
      name: 'olegcherr/Aza-Kotlin-CSS',
      desc: 'Kotlin DSL for CSS',
      href: 'https://github.com/olegcherr/Aza-Kotlin-CSS',
      type: 'github',
      tags: ['web', 'css']
    }, {
      name: 'jooby/kotlin',
      desc: 'Kotlin idioms for Jooby microframework',
      href: 'http://jooby.org/doc/lang-kotlin',
      tags: ['web', 'jooby', 'microframework']
    }, {
      name: 'gimlet2/kottpd',
      desc: 'REST framework in pure Kotlin, inspired by spark-java',
      href: 'https://github.com/gimlet2/kottpd',
      tags: ['web', 'REST', 'http']
    }]
  }, {
    name: 'Tests',
    links: [{
      name: 'JetBrains/spek',
      desc: 'A specification framework for Kotlin.',
      href: 'https://github.com/jetbrains/spek',
      type: 'github',
      tags: ['test', 'assert', 'bdd']
    }, {
      name: 'npryce/hamkrest',
      desc: 'A reimplementation of Hamcrest to take advantage of Kotlin language features.',
      href: 'https://github.com/npryce/hamkrest',
      type: 'github',
      tags: ['test', 'assert']
    }, {
      name: 'nhaarman/mockito-kotlin',
      desc: 'Using Mockito with Kotlin.',
      href: 'https://github.com/nhaarman/mockito-kotlin',
      type: 'github',
      tags: ['test', 'mock']
    }, {
      name: 'MarkusAmshove/Kluent',
      desc: 'Fluent Assertion-Library for Kotlin.',
      href: 'https://github.com/MarkusAmshove/Kluent',
      type: 'github',
      tags: ['test', 'assert']
    }, {
      name: 'winterbe/expekt',
      desc: 'BDD assertion library for Kotlin.',
      href: 'https://github.com/winterbe/expekt',
      type: 'github',
      tags: ['test', 'assert', 'bdd']
    }, {
      name: 'kotlintest/kotlintest',
      desc: 'KotlinTest is a flexible and comprehensive testing tool for the Kotlin ecosystem based on and heavily inspired by the superb Scalatest.',
      href: 'https://github.com/kotlintest/kotlintest',
      type: 'github',
      tags: ['test', 'bdd', 'matchers']
    }, {
      name: 'dmcg/konsent',
      desc: 'An acceptance test library for Kotlin.',
      href: 'https://github.com/dmcg/konsent',
      type: 'github',
      tags: ['test', 'bdd', 'gherkin']
    }, {
      name: 'raniejade/kspec',
      desc: 'Kotlin Specification Framework.',
      href: 'https://github.com/raniejade/kspec',
      type: 'github',
      tags: ['test', 'bdd']
    }, {
      name: 'EPadronU/balin',
      desc: 'Balin is a browser automation library for Kotlin. It\'s basically a Selenium-WebDriver wrapper library inspired by Geb.',
      href: 'https://github.com/EPadronU/balin',
      type: 'github',
      tags: ['test', 'selenium', 'UI', 'automation']
    }, {
      name: 'dmcg/k-sera',
      desc: 'A JMock wrapper for Kotlin.',
      href: 'https://github.com/dmcg/k-sera',
      type: 'github',
      tags: ['mock', 'test']
    }, {
      name: 'dam5s/aspen',
      desc: 'Aspen is an RSpec and Spek inspired test runner for Kotlin.',
      href: 'https://github.com/dam5s/aspen',
      type: 'github',
      tags: ['test', 'specification', 'rspec', 'spek']
    }]
  }, {
    name: 'Dependency Injection',
    links: [{
      name: 'SalomonBrys/Kodein',
      desc: 'Painless Kotlin Dependency Injection .',
      href: 'https://github.com/SalomonBrys/Kodein',
      type: 'github',
      tags: ['di', 'dependency injection']
    }, {
      name: 'kailan/kodeinject',
      desc: 'Constructor dependency injection for Kodein',
      href: 'https://github.com/kailan/kodeinject',
      type: 'github',
      tags: ['di', 'dependency injection', 'kodein']
    }, {
      name: 'kohesive/injekt',
      desc: '(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin',
      href: 'https://github.com/kohesive/injekt',
      type: 'github',
      tags: ['di', 'dependency injection']
    }]
  }, {
    name: 'Functional Programming',
    links: [{
      name: 'MarioAriasC/funKTionale',
      desc: 'Functional constructs for Kotlin.',
      href: 'https://github.com/MarioAriasC/funKTionale',
      type: 'github',
      tags: ['fp', 'functional']
    }, {
      name: 'ReactiveX/RxKotlin',
      desc: 'RxJava bindings for Kotlin.',
      href: 'https://github.com/ReactiveX/RxKotlin',
      type: 'github',
      tags: ['fp', 'functional']
    }, {
      name: 'kittinunf/Result',
      desc: 'The modelling for success/failure of operations in Kotlin.',
      href: 'https://github.com/kittinunf/Result',
      type: 'github',
      tags: ['fp', 'functional', 'monad']
    }, {
      name: 'brianegan/bansa',
      desc: 'A state container for Kotlin & Java, inspired by Elm & Redux.',
      href: 'https://github.com/brianegan/bansa',
      type: 'github',
      tags: ['fp', 'functional', 'UI', 'Interface', 'Redux']
    }, {
      name: 'pardom/redux-kotlin',
      desc: 'Direct port of Redux for Kotlin.',
      href: 'https://github.com/pardom/redux-kotlin',
      type: 'github',
      tags: ['fp', 'functional', 'UI', 'Interface', 'Redux']
    }, {
      name: 'beyondeye/Reduks',
      desc: 'A "batteries included" port of Reduxjs for Kotlin+Android',
      href: 'https://github.com/beyondeye/Reduks',
      type: 'github',
      tags: ['fp', 'functional', 'UI', 'Interface', 'Redux']
    }, {
      name: 'pakoito/Komprehensions',
      desc: 'Do comprehensions for Kotlin and 3rd party libraries.',
      href: 'https://github.com/pakoito/Komprehensions',
      type: 'github',
      tags: ['comprehensions', 'fp', 'functional']
    }, {
      name: 'h0tk3y/kotlin-monads',
      desc: 'Monads for Kotlin',
      href: 'https://github.com/h0tk3y/kotlin-monads',
      type: 'github',
      tags: ['fp', 'functional', 'monads']
    }]
  }, {
    name: 'JSON',
    links: [{
      name: 'cbeust/klaxon',
      desc: 'Lightweight library to parse JSON in Kotlin.',
      href: 'https://github.com/cbeust/klaxon',
      type: 'github',
      tags: ['json']
    }, {
      name: 'SalomonBrys/Kotson',
      desc: 'Gson for Kotlin, Kotson enables you to parse and write JSON with Google\'s Gson using a conciser and easier syntax.',
      href: 'https://github.com/SalomonBrys/Kotson',
      type: 'github',
      tags: ['json']
    }, {
      name: 'FasterXML/jackson-module-kotlin',
      desc: 'Jackson module that adds support for serialization/deserialization of Kotlin classes and data classes.',
      href: 'https://github.com/FasterXML/jackson-module-kotlin',
      type: 'github',
      tags: ['json', 'jakson']
    }, {
      name: 'fboldog/ext4klaxon',
      desc: 'Type Extensions (Long, Int, Enum, Date) for Klaxon.',
      href: 'https://github.com/fboldog/ext4klaxon',
      type: 'github',
      tags: ['json']
    }, {
      name: 'Jire/KTON',
      desc: 'Object notation in pure Kotlin!',
      href: 'https://github.com/Jire/KTON',
      type: 'github',
      tags: ['JSON', 'XML']
    }]
  }, {
    name: 'Database',
    links: [{
      name: 'JetBrains/Exposed',
      desc: 'Exposed is a prototype for a lightweight SQL library written over JDBC driver for Kotlin language.',
      href: 'https://github.com/jetbrains/Exposed',
      type: 'github',
      tags: ['database', 'query', 'schema', 'dao']
    }, {
      name: 'cheptsov/kotlin-nosql',
      desc: 'NoSQL database query and access library for Kotlin.',
      href: 'https://github.com/cheptsov/kotlin-nosql',
      type: 'github',
      tags: ['database', 'mongodb', 'query']
    }, {
      name: 'jankotek/mapdb',
      desc: 'MapDB provides concurrent Maps, Sets and Queues backed by disk storage or off-heap-memory. It is a fast and easy to use embedded Java database engine.',
      href: 'https://github.com/jankotek/mapdb',
      type: 'github'
    }, {
      name: 'seratch/kotliquery',
      desc: 'A handy database access library in Kotlin.',
      href: 'https://github.com/seratch/kotliquery',
      type: 'github',
      tags: ['database', 'sql', 'query']
    }, {
      name: 'andrewoma/kwery',
      desc: 'Kwery is an SQL library for Kotlin.',
      href: 'https://github.com/andrewoma/kwery',
      type: 'github',
      tags: ['database', 'sql', 'query']
    }, {
      name: 'square/sqldelight',
      desc: 'Generates Java models from CREATE TABLE statements.',
      href: 'https://github.com/square/sqldelight',
      type: 'github',
      tags: ['database', 'sql', 'type-safe builder']
    }, {
      name: 'x2bool/kuery',
      desc: 'Typesafe SQL with Kotlin.',
      href: 'https://github.com/x2bool/kuery',
      type: 'github',
      tags: ['database', 'sql', 'type-safe builder']
    }, {
      name: 'Litote/kmongo',
      desc: 'KMongo - Kotlin toolkit for Mongo',
      href: 'https://github.com/Litote/kmongo',
      type: 'github',
      tags: ['database', 'mongodb', 'query']
    }, {
      name: 'requery/requery',
      desc: 'Modern SQL based query & persistence for Java/Kotlin/Android.',
      href: 'https://github.com/requery/requery',
      type: 'github',
      tags: ['database', 'query', 'type-safe builder']
    }, {
      name: 'consoleau/kotlin-jpa-specification-dsl',
      desc: 'This library provides a fluent DSL for querying spring data JPA repositories using spring data Specifications.',
      href: 'https://github.com/consoleau/kotlin-jpa-specification-dsl',
      type: 'github',
      tags: ['database', 'query', 'jpa']
    }, {
      name: 's4kibs4mi/PultusORM',
      desc: 'PultusORM is a sqlite ORM library for kotlin on top of sqlite jdbc driver.',
      href: 'https://github.com/s4kibs4mi/PultusORM',
      type: 'github',
      tags: ['database', 'query', 'sqlite']
    }, {
      name: 'Ganet/rxaerospike',
      desc: 'RxJava2 wrapper for aerospike-client-java.',
      href: 'https://github.com/Ganet/rxaerospike',
      type: 'github',
      tags: ['database', 'arospike', 'rx', 'rxjava2']
    }, {
      name: 'Raizlabs/DBFlow',
      desc: 'A blazing fast, powerful, and very simple ORM android database library that writes database code for you.',
      href: 'https://github.com/Raizlabs/DBFlow',
      type: 'github',
      tags: ['orm', 'jap', 'kapt', 'database']
    }, {
      name: 'KotlinPorts/kt-postgresql-async',
      desc: 'Kotlin/Gradle port of mauricio\'s async driver for postgres/mysql.',
      href: 'https://github.com/KotlinPorts/kt-postgresql-async',
      type: 'github',
      tags: ['postgres', 'mysql', 'database driver']
    }]
  }, {
    name: 'Tools',
    links: [{
      name: 'Kotlin/dokka',
      desc: 'Documentation Engine for Kotlin.',
      href: 'https://github.com/Kotlin/dokka',
      type: 'github'
    }, {
      name: 'Levelmoney/kbuilders',
      desc: 'KBuilders turns your Java builders into beautiful Type-Safe Builders.',
      href: 'https://github.com/Levelmoney/kbuilders',
      type: 'github'
    }, {
      name: 'holgerbrandl/kscript',
      desc: 'Scripting utils for Kotlin.',
      href: 'https://github.com/holgerbrandl/kscript',
      type: 'github',
      tags: ['bash', 'scripting', 'kts']
    }, {
      name: 'shyiko/ktlint',
      desc: 'Kotlin linter.',
      href: 'https://github.com/shyiko/ktlint',
      type: 'github',
      tags: ['style', 'linter']
    }, {
      name: 'jtransc/jtransc',
      desc: 'JVM AOT compiler created in Kotlin.',
      href: 'https://github.com/jtransc/jtransc',
      type: 'github',
      tags: ['aot', 'compiller']
    }, {
      name: 'arturbosch/detekt',
      desc: 'Static code analysis for Kotlin.',
      href: 'https://github.com/arturbosch/detekt',
      type: 'github',
      tags: ['check style', 'checkstyle']
    }, {
      name: 'ligee/kotlin-jupyter',
      desc: 'Kotlin kernel for Jupyter/iPython.',
      href: 'https://github.com/ligee/kotlin-jupyter',
      type: 'github',
      tags: ['juputer', 'repl']
    }]
  }, {
    name: 'Desktop',
    links: [{
      name: 'edvin/tornadofx',
      desc: 'Lightweight JavaFX Framework for Kotlin/',
      href: 'https://github.com/edvin/tornadofx',
      type: 'github',
      tags: ['javafx', 'desktop', 'application']
    }]
  }, {
    name: 'Http Clients',
    links: [{
      name: 'kittinunf/Fuel',
      desc: 'The easiest HTTP networking library for Kotlin/Android.',
      href: 'https://github.com/kittinunf/Fuel',
      type: 'github',
      tags: ['http', 'http client', 'file upload']
    }, {
      name: 'jkcclemens/khttp',
      desc: 'Kotlin HTTP requests library.',
      href: 'https://github.com/jkcclemens/khttp',
      type: 'github',
      tags: ['http', 'http client']
    }]
  }, {
    name: 'Editors',
    links: [{
      name: 'JetBrains/intellij-community',
      desc: 'IntelliJ IDEA Community Edition',
      href: 'https://github.com/JetBrains/intellij-community',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'alexmt/atom-kotlin-language',
      desc: 'Adds syntax highlighting to Kotlin files in Atom',
      href: 'https://github.com/alexmt/atom-kotlin-language',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'vkostyukov/kotlin-sublime-package',
      desc: 'A Sublime Package for Kotlin.',
      href: 'https://github.com/vkostyukov/kotlin-sublime-package',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'udalov/kotlin-vim',
      desc: 'Kotlin Syntax Highlighter for Vim.',
      href: 'https://github.com/udalov/kotlin-vim',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'sargunster/kotlin-textmate-bundle',
      desc: 'Kotlin bundle for TextMate.',
      href: 'https://github.com/sargunster/kotlin-textmate-bundle',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'ice1000/NppExtension',
      desc: 'Kotlin Language extension for Notepad++',
      href: 'https://github.com/ice1000/NppExtension',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }, {
      name: 'ftomassetti/kanvas',
      desc: ' A truly hackable editor: simple, lightweight, understandable.',
      href: 'https://github.com/ftomassetti/kanvas',
      type: 'github',
      tags: ['editor', 'ide', 'language']
    }]
  }, {
    name: 'Syntax Highlighters',
    links: [{
      name: 'jneen/rouge',
      desc: 'A pure-ruby code highlighter that is compatible with pygments. (wip)',
      href: 'https://github.com/jneen/rouge',
      type: 'github',
      tags: ['syntax', 'higlight']
    }, {
      name: 'isagalaev/highlight.js',
      desc: 'Javascript syntax highlighter. (out-of-date)',
      href: 'https://github.com/isagalaev/highlight.js',
      type: 'github',
      tags: ['syntax', 'higlight']
    }, {
      name: 'github/linguist',
      desc: 'For Ruby/Github, uses Sublime package',
      href: 'https://github.com/github/linguist',
      type: 'github',
      tags: ['syntax', 'higlight']
    }, {
      name: 'birkenfeld/pygments-main',
      desc: 'Python syntax highlighter.',
      href: 'https://bitbucket.org/birkenfeld/pygments-main',
      type: 'bitbucket',
      tags: ['syntax', 'higlight']
    }]
  }, {
    name: 'Game Development',
    links: [{
      name: 'czyzby/ktx',
      desc: 'Kotlin utilities for LibGDX applications.',
      href: 'https://github.com/czyzby/ktx',
      type: 'github',
      tags: ['LibGDX', 'game dev']
    }, {
      name: 'AlmasB/FXGL',
      desc: 'JavaFX 8 Game Library written in Java + Kotlin',
      href: 'https://github.com/AlmasB/FXGL',
      type: 'github',
      tags: ['javafx', 'desktop', 'games']
    }]
  }, {
    name: 'Misc',
    links: [{
      name: 'Kotlin/kotlinx.reflect.lite',
      desc: 'Lightweight library allowing to introspect basic stuff about Kotlin symbols.',
      href: 'https://github.com/Kotlin/kotlinx.reflect.lite',
      type: 'github',
      tags: ['reflection']
    }, {
      name: 'puniverse/quasar',
      desc: 'Fibers, Channels and Actors for the JVM.',
      href: 'https://github.com/puniverse/quasar/tree/master/quasar-kotlin',
      type: 'github'
    }, {
      name: 'MehdiK/Humanizer.jvm',
      desc: 'Humanizer.jvm meets all your jvm needs for manipulating and displaying strings, enums, dates, times, timespans, numbers and quantities.',
      href: 'https://github.com/MehdiK/Humanizer.jvm',
      type: 'github',
      tags: ['pluralization']
    }, {
      name: 'mplatvoet/kovenant',
      desc: 'Promises for Kotlin and Android',
      href: 'https://github.com/mplatvoet/kovenant',
      type: 'github',
      tags: ['promise', 'android']
    }, {
      name: 'kohesive/klutter',
      desc: 'A mix of random small libraries for Kotlin, the smallest reside here until big enough for their own repository.',
      href: 'https://github.com/kohesive/klutter',
      type: 'github'
    }, {
      name: 'kohesive/solr-undertow',
      desc: 'Solr Standalone Tiny and High performant server.',
      href: 'https://github.com/kohesive/solr-undertow',
      type: 'github',
      tags: ['solr', 'undertow']
    }, {
      name: 'leprosus/kotlin-hashids',
      desc: 'Library that generates short, unique, non-sequential hashes from numbers.',
      href: 'https://github.com/leprosus/kotlin-hashids',
      type: 'github',
      tags: ['hash']
    }, {
      name: 'mplatvoet/progress',
      desc: 'Progress for Kotlin.',
      href: 'https://github.com/mplatvoet/progress',
      type: 'github',
      tags: ['progress']
    }, {
      name: 'leprosus/kotlin-cli',
      desc: 'Kotlin-CLI - command line interface options parser for Kotlin.',
      href: 'https://github.com/leprosus/kotlin-cli',
      type: 'github',
      tags: ['cli', 'command line interface']
    }, {
      name: 'sargunster/CakeParse',
      desc: 'Simple parser combinator library for Kotlin.',
      href: 'https://github.com/sargunster/CakeParse',
      type: 'github',
      tags: ['parser', 'combinator', 'grammar', 'lexer']
    }, {
      name: 'sargunster/KtUnits',
      desc: 'Tiny unit conversion library for Kotlin.',
      href: 'https://github.com/sargunster/KtUnits',
      type: 'github',
      tags: ['time', 'unit', 'conversion']
    }, {
      name: 'hotchemi/khronos',
      desc: 'An intuitive Date extensions in Kotlin.',
      href: 'https://github.com/hotchemi/khronos',
      type: 'github',
      tags: ['time', 'date']
    }, {
      name: 'yole/kxdate',
      desc: 'Kotlin extensions for Java 8 java.time API',
      href: 'https://github.com/yole/kxdate',
      type: 'github',
      tags: ['time', 'date']
    }, {
      name: 'ingokegel/jclasslib',
      desc: 'jclasslib bytecode viewer is a tool that visualizes all aspects of compiled Java class files and the contained bytecode.',
      href: 'https://github.com/ingokegel/jclasslib',
      type: 'github',
      tags: ['bytecode']
    }, {
      name: 'holgerbrandl/krangl',
      desc: 'krangl is a {K}otlin library for data w{rangl}ing',
      href: 'https://github.com/holgerbrandl/krangl',
      type: 'github',
      tags: ['tabular data', 'data processing']
    }, {
      name: 'debop/koda-time',
      desc: 'Joda Time Extensions in Kotlin. (From Java 8 use java.time instead)',
      href: 'https://github.com/debop/koda-time',
      type: 'github',
      tags: ['joda-time', 'jsr-310']
    }, {
      name: 'MicroUtils/kotlin-logging',
      desc: 'Lightweight logging framework for Kotlin. Used as a wrapper for slf4j with Kotlin extensions.',
      href: 'https://github.com/MicroUtils/kotlin-logging',
      type: 'github',
      tags: ['logging', 'slf4j']
    }, {
      name: 'cesarferreira/kotlin-pluralizer',
      desc: 'Kotlin extension to pluralize and singularize strings.',
      href: 'https://github.com/cesarferreira/kotlin-pluralizer',
      type: 'github',
      tags: ['pluralize', 'singularizen']
    }, {
      name: 'JoelW-S/groothy',
      desc: 'Kotlin implementation of Groovy Truth.',
      href: 'https://github.com/JoelW-S/groothy',
      type: 'github',
      tags: ['groovy truth']
    }, {
      name: 'elect86/kotlin-unsigned',
      desc: 'Boxed unsigned support, Ubyte, Uint, Ulong and Ushort.',
      href: 'https://github.com/elect86/kotlin-unsigned',
      type: 'github',
      tags: ['unsigned']
    }, {
      name: 'Jire/Strukt',
      desc: 'Value types on the JVM, today!',
      href: 'https://github.com/Jire/Strukt',
      type: 'github',
      tags: ['gc-free', 'structure']
    }, {
      name: 'soywiz/korio',
      desc: 'Korio: Kotlin cORoutines I/O: Streams + Async TCP Client/Server + Virtual File System for JVM, Node.JS and Browser.',
      href: 'https://github.com/soywiz/korio',
      type: 'github',
      tags: ['vfs', 'coroutiones', 'io']
    }, {
      name: 'soywiz/korim',
      desc: 'Korim: Kotlin cORoutines IMaging utilities depending on Korio.',
      href: 'https://github.com/soywiz/korim',
      type: 'github',
      tags: ['image', 'coroutiones']
    }, {
      name: 'soywiz/korui',
      desc: 'Korui: Kotlin cORoutines User Interfaces: korio + kimage + korui',
      href: 'https://github.com/soywiz/korui',
      type: 'github',
      tags: ['ui', 'coroutiones']
    }, {
      name: 'jimschubert/kopper',
      desc: 'A simple Kotlin option parser',
      href: 'https://github.com/jimschubert/kopper',
      type: 'github',
      tags: ['cli', 'parser']
    }, {
      name: 'moshbit/Kotlift',
      desc: 'Kotlift is the first source-to-source language transpiler from Kotlin to Swift.',
      href: 'https://github.com/moshbit/Kotlift',
      type: 'github',
      tags: ['swift']
    }, {
      name: 'consoleau/kassava',
      desc: 'This library provides some useful kotlin extension functions for implementing toString() and equals() without all of the boilerplate.',
      href: 'https://github.com/consoleau/kassava',
      type: 'github',
      tags: ['hashCode', 'equals', 'toString']
    }, {
      name: 'moove-it/fakeit',
      desc: 'Generates realistic fake data — like names, emails, dates, countries — to be used in your Android development environment.',
      href: 'https://github.com/moove-it/fakeit',
      type: 'github',
      tags: ['testing', 'android', 'utility']
    }]
  }, {
    name: 'Extensions',
    links: [{
      name: 'Kotlin/kotlinx.support',
      desc: 'Extension and top-level functions to use JDK7/JDK8 features in Kotlin 1.0.',
      href: 'https://github.com/Kotlin/kotlinx.support',
      type: 'github',
      tags: ['jdk8', 'jdk7']
    }]
  }, {
    name: 'Configuration',
    links: [{
      name: 'npryce/konfig',
      desc: 'A Type Safe Configuration API for Kotlin',
      href: 'https://github.com/npryce/konfig',
      type: 'github',
      tags: ['configuration']
    }, {
      name: 'mariomac/kaconf',
      desc: 'KickAss Configuration. An annotation-based configuration system for Java and Kotlin',
      href: 'https://github.com/mariomac/kaconf',
      type: 'github',
      tags: ['configuration']
    }, {
      name: 'config4k/config4k',
      desc: 'A Kotlin wrapper for Typesafe Config',
      href: 'https://github.com/config4k/config4k',
      type: 'github',
      tags: ['configuration']
    }]
  }, {
    name: 'Graphics',
    links: [{
      name: 'elect86/glm',
      desc: 'Porting of cpp g-truck glm, opengl math lib',
      href: 'https://github.com/elect86/glm',
      type: 'github',
      tags: ['glm']
    }, {
      name: 'elect86/ovr',
      desc: 'Oculus binding',
      href: 'https://github.com/elect86/ovr',
      type: 'github',
      tags: ['vr', 'oculus']
    }, {
      name: 'elect86/openvr',
      desc: 'Openvr binding',
      href: 'https://github.com/elect86/openvr',
      type: 'github',
      tags: ['vr', 'openvr']
    }, {
      name: 'elect86/kt',
      desc: 'gli, port of cpp g-truck gli, texture util',
      href: 'https://github.com/elect86/kt',
      type: 'github',
      tags: ['gli']
    }, {
      name: 'java-graphics/assimp',
      desc: 'Java Open Asset Import Library',
      href: 'https://github.com/java-graphics/assimp',
      type: 'github',
      tags: ['assimp', 'stl', 'md2']
    }]
  }]
}, {
  name: 'Kotlin JavaScript',
  subcategories: [{
    name: 'JavaScript',
    links: [{
      name: 'andrewoma/reakt',
      desc: 'Reakt is a Kotlin wrapper for facebook\'s React library.',
      href: 'https://github.com/andrewoma/reakt',
      type: 'github',
      tags: ['react', 'javascript', 'ui']
    }, {
      name: 'pixijs/pixi-native',
      desc: 'The aim of this project is to provide a fast lightweight 2D library that works across all devices.',
      href: 'https://github.com/pixijs/pixi-native',
      type: 'github',
      tags: ['javascript', '2d', 'canvas', 'WebGL']
    }, {
      name: 'bashor/ts2kt',
      desc: 'Converter of TypeScript definition files to Kotlin declarations (stubs)',
      href: 'https://github.com/bashor/ts2kt',
      type: 'github',
      tags: ['javascript', 'typescript']
    }, {
      name: 'shafirov/klogging',
      desc: 'Kotlin logging, both js and jvm.',
      href: 'https://github.com/shafirov/klogging',
      type: 'github',
      tags: ['javascript', 'logging']
    }]
  }, {
    name: 'Frontend',
    links: [{
      name: 'olegcherr/Aza-Kotlin-CSS',
      desc: 'Kotlin DSL for CSS',
      href: 'https://github.com/olegcherr/Aza-Kotlin-CSS',
      type: 'github',
      tags: ['css']
    }]
  }]
},{
  name: 'Projects',
  subcategories: [{
    name: 'Web',
    links: [{
      name: 'sdeleuze/spring-boot-kotlin-demo',
      desc: 'Basic Spring Boot app in Kotlin.',
      href: 'https://github.com/sdeleuze/spring-boot-kotlin-demo',
      type: 'github',
      tags: ['spring', 'spring-boot']
    }, {
      name: 'IRus/kotlin-dev-proxy',
      desc: 'Simple server for proxy requests and host static files written in Kotlin, Spark Java and Apache HttpClient.',
      href: 'https://github.com/IRus/kotlin-dev-proxy',
      type: 'github',
      tags: ['rest', 'web']
    }, {
      name: 'ratpack/example-ratpack-gradle-kotlin-app',
      desc: 'An example of a Kotlin Ratpack app built with Gradle.',
      href: 'https://github.com/ratpack/example-ratpack-gradle-kotlin-app',
      type: 'github',
      tags: ['ratpack', 'gradle', 'rest', 'web']
    }, {
      name: 'mariomac/codebuilder',
      desc: 'Demo app about asynchronous architectures for long-response-time web applications.',
      href: 'https://github.com/mariomac/codebuilder',
      type: 'github',
      tags: ['vertx.io', 'async', 'example']
    }, {
      name: 'rocketraman/kotlin-web-hello-world',
      desc: 'This project shows how to do a web-based "Hello World!" with Kotlin in combination with various JVM-based web frameworks.',
      href: 'https://github.com/rocketraman/kotlin-web-hello-world',
      type: 'github',
      tags: ['spark java', 'vertx', 'wasabi', 'ktor', 'akka', 'example']
    }, {
      name: 'ivanpopelyshev/vertx-facebook-messenger',
      desc: 'Seed project for facebook messenger bots. Vertx, Kotlin.',
      href: 'https://github.com/ivanpopelyshev/vertx-facebook-messenger',
      type: 'github',
      tags: ['chat bot', 'vert.x', 'facebook']
    }, {
      name: 'corda/corda',
      desc: 'Corda is a distributed ledger platform designed to record, manage and automate legal agreements between business partners. ',
      href: 'https://github.com/corda/corda',
      type: 'github',
      tags: ['p2p', 'blockchain']
    }, {
      name: 'spolnik/JAlgoArena',
      desc: 'JAlgoArena is a highly scalable programming contest platform which you can host on own infrastructure. It allows to define new problems and solve them in Kotlin and Java.',
      href: 'https://github.com/spolnik/JAlgoArena',
      type: 'github',
      tags: ['programming', 'contest']
    }]
  }, {
    name: 'Build tools',
    links: [{
      name: 'cbeust/kobalt',
      desc: 'Build system inspired by Gradle.',
      href: 'https://github.com/cbeust/kobalt',
      type: 'github'
    }, {
      name: 'gradle/gradle-script-kotlin',
      desc: 'Kotlin language support for Gradle build scripts.',
      href: 'https://github.com/gradle/gradle-script-kotlin',
      type: 'github',
      tags: ['gradle', 'build']
    }, {
      name: 'nebula-plugins/nebula-kotlin-plugin',
      desc: 'Provides the Kotlin plugin via the Gradle plugin portal, automatically depends on the standard library, and allows Kotlin library versions to be omitted.',
      href: 'https://github.com/nebula-plugins/nebula-kotlin-plugin',
      type: 'github',
      tags: ['gradle', 'build']
    }, {
      name: 'pubref/rules_kotlin',
      desc: 'Bazel rules for Kotlin.',
      href: 'https://github.com/pubref/rules_kotlin',
      type: 'github',
      tags: ['bazel', 'build']
    }]
  }, {
    name: 'Misc',
    links: [{
      name: 'brikk/brikk',
      desc: 'Brikk dependency manager (Kotlin, KotlinJS, Java, ...).',
      href: 'https://github.com/brikk/brikk',
      type: 'github',
      tags: ['dependency managment']
    }]
  }, {
    name: 'Desktop',
    links: [{
      name: 'ice1000/Dekoder',
      desc: ' A kotlin music player, materially designed.',
      href: 'https://github.com/ice1000/Dekoder',
      type: 'github',
      tags: ['JavaFX', 'Desktop', 'Player']
    }]
  }, {
    name: 'Examples',
    links: [{
      name: 'Kotlin/kotlin-koans',
      desc: 'Kotlin Koans are a series of exercises to get you familiar with the Kotlin Syntax.',
      href: 'https://github.com/Kotlin/kotlin-koans',
      type: 'github',
      tags: ['koans']
    }, {
      name: 'JetBrains/kotlin-examples',
      desc: 'Various examples for Kotlin.',
      href: 'https://github.com/JetBrains/kotlin-examples',
      type: 'github',
      tags: ['maven', 'gradle', 'android', 'realm', 'buttetknife', 'dagger', 'dbflow', 'junit-test', 'dokka']
    }, {
      name: 'JetBrains/swot',
      desc: 'Identify email addresses or domains names that belong to colleges or universities. Help automate the process of approving or rejecting academic discounts.',
      href: 'https://github.com/jetbrains/swot',
      type: 'github'
    }, {
      name: 'robfletcher/midcentury-ipsum',
      desc: 'Swingin’ filler text for your jet-age web page.',
      href: 'https://github.com/robfletcher/midcentury-ipsum',
      type: 'github',
      tags: ['ratpack']
    }, {
      name: 'robfletcher/lazybones-kotlin',
      desc: 'The Lazybones app migrated to Kotlin as a learning exercise.',
      href: 'https://github.com/robfletcher/lazybones-kotlin',
      type: 'github',
      tags: ['ratpack']
    }, {
      name: 'wangjiegulu/KotlinAndroidSample',
      desc: 'Android sample with kotlin.',
      href: 'https://github.com/wangjiegulu/KotlinAndroidSample',
      type: 'github',
      tags: ['android', 'sample']
    }, {
      name: 'dodyg/Kotlin101',
      desc: '101 examples for Kotlin Programming language.',
      href: 'https://github.com/dodyg/Kotlin101',
      type: 'github',
      tags: ['examples']
    }, {
      name: 'dkandalov/kotlin-99',
      desc: 'Solve 99 problems with Kotlin!',
      href: 'https://github.com/dkandalov/kotlin-99',
      type: 'github',
      tags: ['examples', 'study']
    }, {
      name: 'dkandalov/rosettacode-kotlin',
      desc: 'Repository with source code from [RosettaCode](http://rosettacode.org/).',
      href: 'https://github.com/dkandalov/rosettacode-kotlin',
      type: 'github',
      tags: ['examples', 'study']
    }, {
      name: 'sanity/pairAdjacentViolators',
      desc: 'A Kotlin implementation of the Pair Adjacent Violators algorithm for isotonic regression.',
      href: 'https://github.com/sanity/pairAdjacentViolators',
      type: 'github',
      tags: ['examples']
    }]
  }, {
    name: 'Idea Plugins',
    links: [{
      name: 'Vektah/CodeGlance',
      desc: 'Intelij IDEA plugin for displaying a code mini-map similar to the one found in Sublime.',
      href: 'https://github.com/Vektah/CodeGlance',
      type: 'github',
      tags: ['idea', 'plugin']
    }, {
      name: 'intellij-rust/intellij-rust',
      desc: 'Rust IDE built using the IntelliJ Platform.',
      href: 'https://github.com/intellij-rust/intellij-rust',
      type: 'github',
      tags: ['idea', 'plugin', 'rust']
    }, {
      name: "dkandalov/activity-tracker",
      desc: "Plugin for IntelliJ IDEs to track and record user activity.",
      href: "https://github.com/dkandalov/activity-tracker",
      type: "github",
      tags: ["idea", "plugin"]
    }]
  }]
}, {
  name: 'Android',
  subcategories: [{
    name: 'Libraries',
    links: [{
      name: 'Kotlin/anko',
      desc: 'Pleasant Android application development.',
      href: 'https://github.com/Kotlin/anko',
      type: 'github',
      tags: ['android']
    }, {
      name: 'JakeWharton/kotterknife',
      desc: 'View injection library for Android.',
      href: 'https://github.com/JakeWharton/kotterknife',
      type: 'github',
      tags: ['android']
    }, {
      name: 'MarcinMoskala/ActivityStarter',
      desc: 'Activity starter generator and arguments injection library for Android.',
      href: 'https://github.com/MarcinMoskala/ActivityStarter',
      type: 'github',
      tags: ['android']
    }, {
      name: 'MarcinMoskala/KotlinPreferences',
      desc: 'Kotlin Android Library, that makes preference usage in Kotlin simple and fun.',
      href: 'https://github.com/MarcinMoskala/KotlinPreferences',
      type: 'github',
      tags: ['android']
    },  {
      name: 'MarcinMoskala/PreferenceHolder',
      desc: 'Kotlin Android Library, that makes preference usage in Kotlin simple and fun using object with fields binded to SharedPreferences.',
      href: 'https://github.com/MarcinMoskala/PreferenceHolder',
      type: 'github',
      tags: ['android']
    }, {
      name: 'nsk-mironov/kotlin-jetpack',
      desc: 'A collection of useful extension methods for Android.',
      href: 'https://github.com/nsk-mironov/kotlin-jetpack',
      type: 'github',
      tags: ['android', 'bindings']
    }, {
      name: 'pawegio/KAndroid',
      desc: 'Kotlin library for Android providing useful extensions to eliminate boilerplate code.',
      href: 'https://github.com/pawegio/KAndroid',
      type: 'github',
      tags: ['android']
    }, {
      name: 'chibatching/Kotpref',
      desc: 'Android SharedPreference delegation for Kotlin.',
      href: 'https://github.com/chibatching/Kotpref',
      type: 'github',
      tags: ['android']
    }, {
      name: 'TouK/bubble',
      desc: 'Library for obtaining screen orientation when orientation is blocked in AndroidManifest.',
      href: 'https://github.com/TouK/bubble',
      type: 'github',
      tags: ['android']
    }, {
      name: 'ragunathjawahar/kaffeine',
      desc: 'Kaffeine is a Kotlin-flavored Android library for accelerating development.',
      href: 'https://github.com/ragunathjawahar/kaffeine',
      type: 'github',
      tags: ['android']
    }, {
      name: 'mcxiaoke/kotlin-koi',
      desc: 'Koi, a lightweight kotlin library for Android Development.',
      href: 'https://github.com/mcxiaoke/kotlin-koi',
      type: 'github',
      tags: ['android']
    }, {
      name: 'BennyWang/KBinding',
      desc: 'Android View Model binding framework write in kotlin, base on anko, simple but powerful.',
      href: 'https://github.com/BennyWang/KBinding',
      type: 'github',
      tags: ['android', 'bindings']
    }, {
      name: 'inaka/KillerTask',
      desc: ' Android AsyncTask wrapper library, written in Kotlin.',
      href: 'https://github.com/inaka/KillerTask',
      type: 'github',
      tags: ['android', 'AsyncTasks']
    }, {
      name: 'grandstaish/paperparcel',
      desc: 'Boilerplate reduction library written specifically for working with Kotlin data classes on Android.',
      href: 'https://github.com/grandstaish/paperparcel',
      type: 'github',
      tags: ['android', 'annotation processing']
    }, {
      name: 'andre-artus/AnvilKotlin',
      desc: 'Minimal UI library for Android inspired by React.',
      href: 'https://github.com/andre-artus/AnvilKotlin',
      type: 'github',
      tags: ['android', 'react', 'view']
    }, {
      name: 'mathcamp/fiberglass',
      desc: 'Easy lightweight SharedPreferences library for Android in Kotlin using delegated properties.',
      href: 'https://github.com/mathcamp/fiberglass',
      type: 'github',
      tags: ['android', 'SharedPreferences']
    }, {
      name: 'nitrico/LastAdapter',
      desc: 'Don\'t write a RecyclerView adapter again. Not even a ViewHolder!.',
      href: 'https://github.com/nitrico/LastAdapter',
      type: 'github',
      tags: ['android', 'RecyclerView', 'ViewHolder']
    }, {
      name: 'denisidoro/krouter',
      desc: 'A lightweight Android activity router.',
      href: 'https://github.com/denisidoro/krouter',
      type: 'github',
      tags: ['router', 'android']
    }, {
      name: 'metalabdesign/AsyncAwait',
      desc: 'async/await for Android built upon coroutines introduced in Kotlin 1.1.',
      href: 'https://github.com/metalabdesign/AsyncAwait',
      type: 'github',
      tags: ['async', 'await', 'android',]
    }, {
      name: 'jupf/staticlog',
      desc: 'StaticLog - super lightweight static logging for Kotlin, Java and Android.',
      href: 'https://github.com/jupf/staticlog',
      type: 'github',
      tags: ['logging', 'android']
    }, {
      name: 'zserge/anvil',
      desc: 'Minimal UI library for Android inspired by React.',
      href: 'https://github.com/zserge/anvil',
      type: 'github',
      tags: ['android', 'layout']
    }, {
      name: 'DanielMartinus/Stepper-Touch',
      desc: 'Fun playful Android stepper widget for counting, written in Kotlin.',
      href: 'https://github.com/DanielMartinus/Stepper-Touch',
      type: 'github',
      tags: ['widget', 'android', 'counter', 'ui']
    }]
  }, {
    name: 'Frameworks',
    links: [{
      name: 'nekocode/kotgo',
      desc: 'An android development framwork on kotlin using MVP architecture.',
      href: 'https://github.com/nekocode/kotgo',
      type: 'github',
      tags: ['android']
    }, {
      name: 'lightningkite/kotlin-core',
      desc: 'A full framework for making Android apps. Based on Anko and Kotson.',
      href: 'https://github.com/lightningkite/kotlin-core',
      type: 'github',
      tags: ['kotson', 'anko', 'android']
    }]
  }, {
    name: 'Projects',
    links: [{
      name: 'antoniolg/Bandhook-Kotlin',
      desc: 'A showcase music app for Android entirely written using Kotlin language.',
      href: 'https://github.com/antoniolg/Bandhook-Kotlin',
      type: 'github'
    }, {
      name: 'antoniolg/Kotlin-for-Android-Developers',
      desc: 'Companion App for the book "Kotlin Android Developers".',
      href: 'https://github.com/antoniolg/Kotlin-for-Android-Developers',
      type: 'github',
      tags: ['android']
    }, {
      name: 'damianpetla/kotlin-dagger-example',
      desc: 'Example of Android project showing integration with Kotlin and Dagger 2.',
      href: 'https://github.com/damianpetla/kotlin-dagger-example',
      type: 'github'
    }, {
      name: 'dodyg/AndroidRivers',
      desc: 'RSS Readers for Android.',
      href: 'https://github.com/dodyg/AndroidRivers',
      type: 'github',
      tags: ['rss', 'android']
    }, {
      name: 'MakinGiants/banjen-android-banjo-tuner',
      desc: 'App that plays sounds helping to tune a brazilian banjo.',
      href: 'https://github.com/MakinGiants/banjen-android-banjo-tuner',
      type: 'github',
      tags: ['android', 'application', 'tuner']
    }, {
      name: 'inaka/kotlillon',
      desc: 'Android Kotlin Examples.',
      href: 'https://github.com/inaka/kotlillon',
      type: 'github',
      tags: ['android', 'application']
    }, {
      name: 'MakinGiants/todayhistory',
      desc: 'App that shows what happened today in history.',
      href: 'https://github.com/MakinGiants/todayhistory',
      type: 'github',
      tags: ['android', 'application']
    }, {
      name: 'RxKotlin/Pocket',
      desc: 'This app help user to save links easily, and can export to Evernote as weekly.',
      href: 'https://github.com/RxKotlin/Pocket',
      type: 'github',
      tags: ['android', 'application', 'rx']
    }, {
      name: 'SidneyXu/AndroidDemoIn4Languages',
      desc: 'Comparison between Java, Groovy, Scala, Kotlin in Android Development.',
      href: 'https://github.com/SidneyXu/AndroidDemoIn4Languages',
      type: 'github',
      tags: ['Java', 'Groovy', 'Scala', 'Kotlin', 'Android']
    }, {
      name: 'inorichi/tachiyomi',
      desc: ' Free and open source manga reader for Android.',
      href: 'https://github.com/inorichi/tachiyomi',
      type: 'github',
      tags: ['android', 'application']
    }, {
      name: 'ziggy42/Blum-kotlin',
      desc: 'A simple android Twitter client written in Kotlin',
      href: 'https://github.com/ziggy42/Blum-kotlin',
      type: 'github',
      tags: ['android', 'twitter', 'application']
    }]
  }, {
    name: 'Extensions',
    links: [{
      name: 'ajalt/timberkt',
      desc: 'Easy Android logging with Kotlin and Timber.',
      href: 'https://github.com/ajalt/timberkt',
      type: 'github',
      tags: ['android', 'logging']
    }]
  }, {
    name: 'Tools',
    links: [{
      name: 'kiruto/debug-bottle',
      desc: 'Debug Bottle is an Android runtime debug / develop tools written using kotlin language.',
      href: 'https://github.com/kiruto/debug-bottle',
      type: 'github',
      tags: ['android', 'debug']
    }]
  }]
}, {
  name: 'Kotlin User Groups',
  subcategories: [{
    name: 'Europe',
    links: [{
      name: 'Kotlin Developers in Manchester',
      desc: 'United Kingdom',
      href: 'http://www.meetup.com/Kotlin-Manchester/',
      type: 'kug',
      tags: ['United Kingdom']
    }, {
      name: 'Belarus Kotlin User Group',
      desc: 'Belarus',
      href: 'https://bkug.by/',
      type: 'kug',
      tags: ['Belarus']
    }, {
      name: 'Kotlin User Group Munich',
      desc: 'Deutschland',
      href: 'http://www.meetup.com/de-DE/Kotlin-User-Group-Munich/',
      type: 'kug',
      tags: ['Deutschland']
    }, {
      name: 'Lyon Kotlin User Group',
      desc: 'France',
      href: 'http://www.meetup.com/Lyon-Kotlin-User-Group/',
      type: 'kug',
      tags: ['France']
    }, {
      name: 'KotlinMAD',
      desc: 'Spain',
      href: 'https://www.meetup.com/KotlinMAD/',
      type: 'kug',
      tags: ['Spain']
    }, {
      name: 'Kotlin Yorkshire Meetup Group',
      desc: 'United Kingdom',
      href: 'http://www.meetup.com/Kotlin-Yorkshire-Meetup-Group/',
      type: 'kug',
      tags: ['United Kingdom']
    }, {
      name: 'Kotlin London',
      desc: 'United Kingdom',
      href: 'http://www.meetup.com/kotlin-london/',
      type: 'kug',
      tags: ['United Kingdom']
    }, {
      name: 'Kotlin User Group Berlin',
      desc: 'Germany',
      href: 'https://www.meetup.com/kotlin-berlin/',
      type: 'kug',
      tags: ['Germany']
    }, {
      name: 'Kotlin User Group Cologne',
      desc: 'Germany',
      href: 'https://www.meetup.com/Kotlin-User-Group-Cologne/',
      type: 'kug',
      tags: ['Germany']
    }]
  }, {
    name: 'America',
    links: [{
      name: 'Bay Area Kotlin User Group',
      desc: 'USA',
      href: 'http://www.meetup.com/Bay-Area-Kotlin-User-Group/',
      type: 'kug',
      tags: ['USA']
    }, {
      name: 'Chicago Kotlin Users Group',
      desc: 'USA',
      href: 'https://www.meetup.com/Chicago-Kotlin/',
      type: 'kug',
      tags: ['USA']
    }, {
      name: 'Kotlin Group of Boulder',
      desc: 'USA',
      href: 'http://www.meetup.com/Kotlin-Group-Boulder/',
      type: 'kug',
      tags: ['USA']
    }, {
      name: 'New York Kotlin Meetup',
      desc: 'USA',
      href: 'http://www.meetup.com/New-York-Kotlin-Meetup/',
      type: 'kug',
      tags: ['USA']
    }]
  }, {
    name: 'Asia',
    links: [{
      name: 'Japan Kotlin User Group',
      desc: 'Japan',
      href: 'https://kotlin.doorkeeper.jp/',
      type: 'kug',
      tags: ['Japan']
    }, {
      name: 'Korean Kotlin User Group',
      desc: 'Korea',
      href: 'http://kotlin.kr/',
      type: 'kug',
      tags: ['Korea']
    }]
  }, {
    name: 'Australia',
    links: [{
      name: 'Brisbane Kotlin User Group',
      desc: 'Australia',
      href: 'https://www.meetup.com/Brisbane-Kotlin-User-Group/',
      type: 'kug',
      tags: ['Australia']
    }]
  }]
}];

export const links = [...data, ...articlesLinks];
