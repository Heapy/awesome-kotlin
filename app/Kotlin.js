const data = [{
    name: "Links",
    subcategories: [{
        name: "Official Links",
        links: [{
            name: 'Home Page',
            href: 'http://kotlinlang.org/'
        }, {
            name: 'Language Reference',
            href: 'http://kotlinlang.org/docs/reference/',
        }, {
            name: 'Try Kotlin!',
            href: 'http://try.kotlinlang.org/',
        }, {
            name: 'Blog',
            href: 'http://blog.jetbrains.com/kotlin/',
        }, {
            name: 'Issue Tracker',
            href: 'http://youtrack.jetbrains.com/issues/KT',
        }, {
            name: 'Kotlin Repository',
            href: 'https://github.com/jetbrains/kotlin',
        }, {
            name: 'Twitter',
            href: 'https://twitter.com/project_kotlin',
        }]
    }, {
        name: "Resources",
        links: [{
            name: '/r/Kotlin',
            href: 'https://www.reddit.com/r/Kotlin/'
        }, {
            name: 'Quora Kotlin',
            href: 'https://www.quora.com/Kotlin?share=1',
        }, {
            name: 'Trending Kotlin on Github',
            href: 'https://github.com/trending?l=kotlin',
        }, {
            name: 'Antonio Leiva - Android and any other monsters',
            href: 'http://antonioleiva.com/',
        }]
    }, {
        name: "Books",
        links: [{
            name: 'Kotlin in Action - Dmitry Jemerov, Svetlana Isakova',
            href: 'https://manning.com/books/kotlin-in-action'
        }, {
            name: 'Kotlin for Android Developers - Antonio Leiva',
            href: 'https://leanpub.com/kotlin-for-android-developers',
        }]
    }]
}, {
    name: "Libraries",
    subcategories: [{
        name: "Android",
        links: [{
            name: 'JetBrains/anko',
            desc: 'Pleasant Android application development.',
            href: 'https://github.com/JetBrains/anko',
            type: 'github',
            star: '2075'
        }]
    }, {
        name: "Web",
        links: [{
            name: 'TinyMission/kara',
            desc: 'Web framework written in Kotlin.',
            href: 'https://github.com/TinyMission/kara',
            type: 'github',
            star: '177'
        }]
    }]
}];



const t = `

, {
            name: '',
            href: '',
        }, {
            name: '',
            href: '',
        }, {
            name: '',
            href: '',
        }

== Single Posts
_:page_facing_up:_  Jan 23, 2013 http://zeroturnaround.com/rebellabs/the-adventurous-developers-guide-to-jvm-languages-kotlin/[The Adventurous Developer’s Guide to JVM languages – Kotlin]
_:page_facing_up:_  Apr 02, 2013 http://www.oracle.com/technetwork/articles/java/breslav-1932170.html[The Advent of Kotlin: A Conversation with JetBrains' Andrey Breslav]
_:tv:_              Nov 03, 2014 https://vimeo.com/110781020[GeeCON Prague 2014: Andrey Cheptsov - A Reactive and Type-safe Kotlin DSL for NoSQL and SQL]
_:page_facing_up:_  Jul 06, 2015 https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3[Why Kotlin is my next programming language]
_:page_facing_up:_  Jul 20, 2015 http://blog.zuehlke.com/en/android-kotlin/[Android + Kotlin = <3]
_:page_facing_up:_  Jul 31, 2015 http://habrahabr.ru/company/jugru/blog/263905/[Без слайдов: интервью с Дмитрием Жемеровым из JetBrains (Russian)]
_:page_facing_up:_  Aug 06, 2015 http://nordicapis.com/building-apis-on-the-jvm-using-kotlin-and-spark-part-1/[Building APIs on the JVM Using Kotlin and Spark – Part 1]
_:tv:_              Aug 12, 2015 https://www.youtube.com/watch?v=2IhT8HACc2E[JVMLS 2015 - Flexible Types of Kotlin - Andrey Breslav]
_:page_facing_up:_  Aug 26, 2015 https://www.linkedin.com/grp/post/7417237-6042285669181648896[Production Ready Kotlin]
_:page_facing_up:_  Aug 27, 2015 https://speakerdeck.com/pardom/kotlin-new-hope-in-a-java-6-wasteland/[Kotlin: New Hope in a Java 6 Wasteland]

== Libraries

=== Android
https://github.com/JetBrains/anko[JetBrains/anko] - Pleasant Android application development.
https://github.com/JakeWharton/kotterknife[JakeWharton/kotterknife] - View injection library for Android.
https://github.com/pawegio/KAndroid[pawegio/KAndroid] - Kotlin library for Android.

=== Web
https://github.com/TinyMission/kara[TinyMission/kara] - Web framework written in Kotlin.
https://github.com/jean79/yested[jean79/yested] - A Kotlin framework for building web applications in Javascript.
https://github.com/hhariri/wasabi[hhariri/wasabi] - An HTTP Framework built with Kotlin for the JVM.
https://github.com/Kotlin/ktor[Kotlin/ktor] - Web backend framework for Kotlin

=== Util
https://github.com/JetBrains/spek[JetBrains/spek] - A specification framework for Kotlin.

=== Database
https://github.com/cheptsov/kotlin-nosql[cheptsov/kotlin-nosql] - NoSQL database query and access library for Kotlin.

=== JSON
https://github.com/cbeust/klaxon[cbeust/klaxon] - Lightweight library to parse JSON in Kotlin.
https://github.com/SalomonBrys/Kotson[SalomonBrys/Kotson] - Gson for Kotlin
https://github.com/FasterXML/jackson-module-kotlin[FasterXML/jackson-module-kotlin] - Jackson module that adds support for serialization/deserialization of Kotlin classes and data classes.

=== Misc
https://github.com/ReactiveX/RxKotlin[ReactiveX/RxKotlin] - RxJava bindings for Kotlin.
https://github.com/puniverse/quasar/tree/master/quasar-kotlin[puniverse/quasar] - Fibers, Channels and Actors for the JVM.
https://github.com/mplatvoet/kovenant[mplatvoet/kovenant] - Kovenant. Promises for Kotlin.
https://github.com/klutter/klutter[klutter/klutter] - A mix of random small libraries for Kotlin, the smallest reside here until big enough for their own repository.

== Projects

=== Android
https://github.com/antoniolg/Bandhook-Kotlin[antoniolg/Bandhook-Kotlin] - A showcase music app for Android entirely written using Kotlin language.
https://github.com/damianpetla/kotlin-dagger-example[damianpetla/kotlin-dagger-example] - Example of Android project showing integration with Kotlin and Dagger 2.

=== Web
https://github.com/ssoudan/ktSpringTest[ssoudan/ktSpringTest] - Basic Spring Boot app in Kotlin.

=== Build tools
https://github.com/brikk/brikk[brikk/brikk] - NPM-like dependency manager
https://github.com/cbeust/kobalt[cbeust/kobalt] - Build system inspired by Gradle

=== Misc
https://github.com/JetBrains/swot[JetBrains/swot] - Identify email addresses or domains names that belong to colleges or universities. Help automate the process of approving or rejecting academic discounts.
https://github.com/IRus/kotlin-dev-proxy[IRus/kotlin-dev-proxy] - Simple server for proxy requests and host static files written in Kotlin, Spark Java and Apache HttpClient.

''''
NOTE: Get help with AsciiDoc syntax: http://asciidoctor.org/docs/asciidoc-writers-guide/[AsciiDoc Writer’s Guide]

image::http://i.creativecommons.org/p/zero/1.0/80x15.png[CC0, link="http://creativecommons.org/publicdomain/zero/1.0/"]
`

exports.default = data;
