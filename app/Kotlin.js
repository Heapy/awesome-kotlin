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
            name: 'Slack (2000+ users)',
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
        }]
    }, {
        name: "Resources",
        links: [{
            name: '/r/Kotlin',
            href: 'https://www.reddit.com/r/Kotlin/'
        }, {
            name: 'Quora Kotlin',
            href: 'https://www.quora.com/topic/Kotlin'
        }, {
            name: 'Trending Kotlin on Github',
            href: 'https://github.com/trending?l=kotlin'
        }, {
            name: 'Antonio Leiva - Android and any other monsters',
            href: 'http://antonioleiva.com/',
            type: 'blog'
        }, {
            name: 'LinkedIn: Kotlin Developers (Join!)',
            href: 'https://www.linkedin.com/topic/group/kotlin-developers?gid=7417237'
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
            desc: ' BDD assertion library for Kotlin.',
            href: 'https://github.com/winterbe/expekt',
            type: 'github',
            tags: ['test', 'assert', 'bdd']
        }, {
            name: 'kotlintest/kotlintest',
            desc: 'KotlinTest is a flexible and comprehensive testing tool for the Kotlin ecosystem based on and heavily inspired by the superb Scalatest.',
            href: 'https://github.com/kotlintest/kotlintest',
            type: 'github',
            tags: ['test', 'bdd', 'matchers']
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
            desc: 'Dependency Injection / Object Factory for Kotlin',
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
            desc: ' A state container for Kotlin & Java, inspired by Elm & Redux.',
            href: 'https://github.com/brianegan/bansa',
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
            href: 'https://github.com/jankotek/mapdb/tree/mapdb3',
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
        }]
    },{
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
        }]
    }, {
        name: "Build tools",
        links: [{
            name: 'cbeust/kobalt',
            desc: 'Build system inspired by Gradle.',
            href: 'https://github.com/cbeust/kobalt',
            type: 'github'
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
        name: "Examples",
        links: [{
            name: 'Kotlin/kotlin-koans',
            desc: 'Kotlin Koans are a series of exercises to get you familiar with the Kotlin Syntax.',
            href: 'https://github.com/Kotlin/kotlin-koans',
            type: 'github',
            tags: ['koans']
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
        }]
    }, {
        name: "Projects",
        links: [{
            name: 'antoniolg/Bandhook-Kotlin',
            desc: 'A showcase music app for Android entirely written using Kotlin language.',
            href: 'https://github.com/antoniolg/Bandhook-Kotlin',
            type: 'github'
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
            name: 'MakinGiants/android_banjo_tuner',
            desc: 'App that plays sounds helping to tune a brazilian banjo.',
            href: 'https://github.com/MakinGiants/android_banjo_tuner',
            type: 'github',
            tags: ['android', 'application', 'tuner']
        }, {
            name: 'inaka/kotlillon',
            desc: 'Android Kotlin Examples',
            href: 'https://github.com/inaka/kotlillon',
            type: 'github',
            tags: ['android', 'application']
        }]
    }]
}, {
    name: "Articles, Videos, Blog Posts",
    subcategories: [{
        name: "Posts",
        links: [{
            name: 'Exploring the Kotlin Standard Library',
            desc: 'Jan 22, 2013',
            href: 'http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-1.html',
            tags: []
        }, {
            name: 'The Adventurous Developer’s Guide to JVM languages – Kotlin',
            desc: 'Jan 23, 2013',
            href: 'http://zeroturnaround.com/rebellabs/the-adventurous-developers-guide-to-jvm-languages-kotlin/'
        }, {
            name: 'The Advent of Kotlin: A Conversation with JetBrains\' Andrey Breslav',
            desc: 'Apr 02, 2013',
            href: 'http://www.oracle.com/technetwork/articles/java/breslav-1932170.html'
        }, {
            name: 'Non-trivial constructors in Kotlin',
            desc: 'Dec 01, 2014',
            href: 'http://alexshabanov.com/category/languages/kotlin/'
        }, {
            name: 'Quasar and Kotlin – a Powerful Match',
            desc: 'Jun 04, 2015',
            href: 'http://blog.paralleluniverse.co/2015/06/04/quasar-kotlin/',
            type: 'post',
            tags: ['quasar', 'fibers']
        }, {
            name: 'Why Kotlin is my next programming language',
            desc: 'Jul 06, 2015',
            href: 'https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3'
        }, {
            name: 'Android + Kotlin = <3',
            desc: 'Jul 20, 2015',
            href: 'http://blog.zuehlke.com/en/android-kotlin/'
        }, {
            name: 'Без слайдов: интервью с Дмитрием Жемеровым из JetBrains (Russian)',
            desc: 'Jul 31, 2015',
            href: 'https://habrahabr.ru/company/jugru/blog/263905/'
        }, {
            name: 'Building APIs on the JVM Using Kotlin and Spark – Part 1',
            desc: 'Aug 06, 2015',
            href: 'http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-1/'
        }, {
            name: 'Production Ready Kotlin',
            desc: 'Aug 26, 2015',
            href: 'https://www.linkedin.com/grp/post/7417237-6042285669181648896'
        }, {
            name: 'Kotlin: New Hope in a Java 6 Wasteland',
            desc: 'Aug 27, 2015',
            href: 'https://realm.io/news/droidcon-michael-pardo-kotlin/'
        }, {
            name: 'Kotlin ❤ FP',
            desc: 'Sep 18, 2015',
            href: 'https://medium.com/@octskyward/kotlin-fp-3bf63a17d64a',
            tags: ['functional']
        }, {
            name: 'Blog about Kotlin language and Android development.',
            desc: 'Oct 21, 2015',
            href: 'http://kotlin4android.com/',
            type: 'blog',
            tags: ['android']
        }, {
            name: 'Playing with Spring Boot, Vaadin and Kotlin.',
            desc: 'Jan 10, 2016',
            href: 'https://blog.frankel.ch/playing-with-spring-boot-vaadin-and-kotlin',
            type: 'post',
            tags: ['vaadin', 'spring', 'web']
        }, {
            name: 'Mimicking Kotlin Builders in Java and Python.',
            desc: 'Jan 16, 2016',
            href: 'https://programmingideaswithjake.wordpress.com/2016/01/16/mimicking-kotlin-builders-in-java-and-python/',
            type: 'post',
            tags: ['builders']
        }, {
            name: 'Developing Spring Boot applications with Kotlin.',
            desc: 'Feb 15, 2016',
            href: 'https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin',
            type: 'post',
            tags: ['spring', 'web']
        }, {
            name: 'Kotlin & Android: A Brass Tacks Experiment, Part 1.',
            desc: 'Feb 1, 2016',
            href: 'https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-1-3e5028491bcc#.5c7ixfzdv',
            type: 'post',
            tags: ['android']
        }, {
            name: 'Kotlin & Android: A Brass Tacks Experiment, Part 2.',
            desc: 'Feb 1, 2016',
            href: 'https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-2-c67661cfdf5f#.4s2hprcjw',
            type: 'post',
            tags: ['android']
        }, {
            name: 'Kotlin & Android: A Brass Tacks Experiment, Part 3.',
            desc: 'Feb 16, 2016',
            href: 'https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-3-84e65d567a37#.lgtyczp3h',
            type: 'post',
            tags: ['android']
        }]
    }, {
        name: "Videos",
        links: [{
            name: 'Fun with Kotlin',
            desc: 'Jan 14, 2016',
            href: 'https://vimeo.com/151846078',
            type: 'vimeo',
            tags: ['kotlin']
        }, {
            name: 'JVMLS 2015 - Flexible Types of Kotlin - Andrey Breslav',
            desc: 'Aug 12, 2015',
            href: 'https://www.youtube.com/watch?v=2IhT8HACc2E',
            type: 'youtube'
        }, {
            name: 'vJUG: Kotlin for Java developers.',
            desc: 'Dec 11, 2014',
            href: 'https://www.youtube.com/watch?v=vmjfIRsawlg',
            type: 'youtube'
        }, {
            name: 'GeeCON Prague 2014: Andrey Cheptsov - A Reactive and Type-safe Kotlin DSL for NoSQL and SQL',
            desc: 'Nov 03, 2014',
            href: 'https://vimeo.com/110781020',
            type: 'vimeo'
        }, {
            name: 'Kotlin NoSQL for MongoDB in Action.',
            desc: 'Oct 22, 2014',
            href: 'https://www.youtube.com/watch?v=80xgl3KThvM',
            type: 'youtube',
            tags: ['nosql', 'database', 'mondodb']
        }, {
            name: 'Kotlin vs Java puzzlers - Svetlana Isakova',
            desc: 'Sep 10, 2014',
            href: 'https://vimeo.com/105758307',
            type: 'vimeo'
        }]
    }, {
        name: "Webinars",
        links: [{
            name: 'Functional Programming with Kotlin ',
            desc: 'Nov 5, 2015',
            href: 'http://blog.jetbrains.com/kotlin/2015/11/webinar-recording-functional-programming-with-kotlin/',
            type: 'webinar',
            tags: ['fp', 'functional', 'webinar']
        }, {
            name: 'Quasar: Efficient and Elegant Fibers, Channels and Actors',
            desc: 'Sep 22, 2015',
            href: 'http://blog.jetbrains.com/kotlin/2015/09/webinar-recording-quasar-efficient-and-elegant-fibers-channels-and-actors/',
            type: 'webinar',
            tags: ['webinar', 'fibers', 'channels', 'actors']
        }]
    }]
}];

module.exports = data;
