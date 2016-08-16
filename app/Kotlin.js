const data = [{
    name: "Links",
    subcategories: [{
        name: "Official Links",
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
            name: 'Slack (3000+ users)',
            href: 'http://kotlinslackin.herokuapp.com/'
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
        name: "Resources",
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
            href: 'https://www.linkedin.com/topic/group/kotlin-developers?gid=7417237',
            whitelisted: true
        }, {
            name: 'Kotlin - Google+',
            href: 'https://plus.google.com/communities/104597899765146112928'
        }]
    }, {
        name: "Books",
        links: [{
            name: 'Kotlin in Action - Dmitry Jemerov, Svetlana Isakova',
            href: 'https://manning.com/books/kotlin-in-action'
        }, {
            name: 'Kotlin for Android Developers - Antonio Leiva',
            href: 'https://leanpub.com/kotlin-for-android-developers'
        }]
    }]
}, {
    name: "Libraries/Frameworks",
    subcategories: [{
        name: "Web",
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
            href: 'https://github.com/hhariri/wasabi',
            type: 'github'
        }, {
            name: 'Kotlin/kotlinx.html',
            desc: 'Kotlin DSL for HTML.',
            href: 'https://github.com/Kotlin/kotlinx.html',
            type: 'github',
            tags: ['html']
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
        }]
    }, {
        name: "Tests",
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
        }]
    }, {
        name: "Dependency Injection",
        links: [{
            name: 'SalomonBrys/Kodein',
            desc: 'Painless Kotlin Dependency Injection .',
            href: 'https://github.com/SalomonBrys/Kodein',
            type: 'github',
            tags: ['di', 'dependency injection']
        }, {
            name: 'kohesive/injekt',
            desc: '(Deprecated, @see Kodein) Dependency Injection / Object Factory for Kotlin',
            href: 'https://github.com/kohesive/injekt',
            type: 'github',
            tags: ['di', 'dependency injection']
        }]
    }, {
        name: "Functional Programming",
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
        }]
    }, {
        name: "JSON",
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
        name: "Database",
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
        }]
    }, {
        name: "Tools",
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
        }]
    }, {
        name: "Desktop",
        links: [{
            name: 'edvin/tornadofx',
            desc: 'Lightweight JavaFX Framework for Kotlin/',
            href: 'https://github.com/edvin/tornadofx',
            type: 'github',
            tags: ['javafx', 'desktop', 'application']
        }, {
            name: 'griffon/griffon-kotlin-plugin',
            desc: 'Griffon Support',
            href: 'https://github.com/griffon/griffon-kotlin-plugin',
            type: 'github',
            tags: ['griffon']
        }, {
            name: 'AlmasB/FXGL',
            desc: 'JavaFX 8 Game Library written in Java + Kotlin',
            href: 'https://github.com/AlmasB/FXGL',
            type: 'github',
            tags: ['javafx', 'desktop', 'games']
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
        name: "Editors",
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
        }]
    }, {
        name: "Syntax Highlighters",
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
        name: "JavaScript",
        links:[{
            name: 'danfma/kotlinjs-react',
            desc: 'A react wrapper to the kotlin library.',
            href: 'https://github.com/danfma/kotlinjs-react',
            type: 'github',
            tags: ['react', 'javascript', 'ui']
        }, {
            name: 'andrewoma/reakt',
            desc: 'Reakt is a Kotlin wrapper for facebook\'s React library.',
            href: 'https://github.com/andrewoma/reakt',
            type: 'github',
            tags: ['react', 'javascript', 'ui']
        }]
    }, {
        name: "Misc",
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
            name: 'npryce/konfig',
            desc: 'A Type Safe Configuration API for Kotlin',
            href: 'https://github.com/npryce/konfig',
            type: 'github',
            tags: ['configuration']
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
            name: 'MicroUtils/kotlin.logging',
            desc: 'Lightweight logging framework for Kotlin. Used as a wrapper for slf4j with Kotlin extensions.',
            href: 'https://github.com/MicroUtils/kotlin.logging',
            type: 'github',
            tags: ['logging', 'slf4j']
        }]
    }]
}, {
    name: "Projects",
    subcategories: [{
        name: "Web",
        links: [{
            name: 'ssoudan/ktSpringTest',
            desc: 'Basic Spring Boot app in Kotlin.',
            href: 'https://github.com/ssoudan/ktSpringTest',
            type: 'github'
        }, {
            name: 'IRus/kotlin-dev-proxy',
            desc: 'Simple server for proxy requests and host static files written in Kotlin, Spark Java and Apache HttpClient.',
            href: 'https://github.com/IRus/kotlin-dev-proxy',
            type: 'github',
            tags: ['tags', 'rest', 'web']
        }, {
            name: 'ratpack/example-ratpack-gradle-kotlin-app',
            desc: 'An example of a Kotlin Ratpack app built with Gradle.',
            href: 'https://github.com/ratpack/example-ratpack-gradle-kotlin-app',
            type: 'github',
            tags: ['ratpack', 'gradle', 'rest', 'web']
        }]
    }, {
        name: "Build tools",
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
        }]
    }, {
        name: "Misc",
        links: [{
            name: 'brikk/brikk',
            desc: 'Brikk dependency manager (Kotlin, KotlinJS, Java, ...).',
            href: 'https://github.com/brikk/brikk',
            type: 'github',
            tags: ['dependency managment']
        }]
    }, {
        name: "Desktop",
        links: [{
            name: 'ice1000/Dekoder',
            desc: ' A kotlin music player, materially designed.',
            href: 'https://github.com/ice1000/Dekoder',
            type: 'github',
            tags: ['JavaFX', 'Desktop', 'Player']
        }]
    }, {
        name: "Examples",
        links: [{
            name: 'Kotlin/kotlin-koans',
            desc: 'Kotlin Koans are a series of exercises to get you familiar with the Kotlin Syntax.',
            href: 'https://github.com/Kotlin/kotlin-koans',
            type: 'github',
            tags: ['koans']
        }, {
            name: 'JetBrains/kotlin-examples',
            desc: 'Various examples for Kotlin',
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
        }]
    }]
}, {
    name: "Android",
    subcategories: [{
        name: "Libraries",
        links: [{
            name: 'Kotlin/anko',
            desc: 'Pleasant Android application development.',
            href: 'https://github.com/Kotlin/anko',
            type: 'github',
            tags: ['android']
        }, {
            name: 'JakeWharton/kotterknife',
            desc: 'View injection library for Android',
            href: 'https://github.com/JakeWharton/kotterknife',
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
            name: 'graknol/AnvilKotlin',
            desc: 'Minimal UI library for Android inspired by React.',
            href: 'https://github.com/graknol/AnvilKotlin',
            type: 'github',
            tags: ['android', 'react', 'view']
        }]
    }, {
        name: "Frameworks",
        links: [{
            name: 'nekocode/kotgo',
            desc: 'An android development framwork on kotlin using MVP architecture.',
            href: 'https://github.com/nekocode/kotgo',
            type: 'github',
            tags: ['android']
        }]
    }, {
        name: "Projects",
        links: [{
            name: 'antoniolg/Bandhook-Kotlin',
            desc: 'A showcase music app for Android entirely written using Kotlin language.',
            href: 'https://github.com/antoniolg/Bandhook-Kotlin',
            type: 'github'
        }, {
            name: 'antoniolg/Kotlin-for-Android-Developers',
            desc: 'Companion App for the book "Kotlin Android Developers"',
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
            desc: 'Android Kotlin Examples',
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
        }]
    }]
}, {
    name: "Kotlin User Groups",
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
            href: 'http://bkug.by/',
            type: 'kug',
            tags: ['Belarus'],
            whitelisted: true
        }, {
            name: 'Kotlin User Group Munich',
            desc: 'Deutschland',
            href: 'http://www.meetup.com/de-DE/Kotlin-User-Group-Munich/',
            type: 'kug',
            tags: ['Deutschland']
        }]
    }, {
        name: 'America',
        links: [{
            name: 'Bay Area Kotlin User Group',
            desc: 'USA',
            href: 'http://www.meetup.com/Bay-Area-Kotlin-User-Group/',
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
        }]
    }]
}];

require("./rss/DataGenerator").forEach(category => data.push(category));

module.exports = data;
