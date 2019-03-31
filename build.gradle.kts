import Dep.Kotlin.reflect
import Dep.Kotlin.stdlib
import Dep.commonmark
import Dep.commonmarkExtGfmTables
import Dep.coroutines
import Dep.jacksonKotlin
import Dep.jacksonXml
import Dep.junit
import Dep.kotlinVersion
import Dep.mockk

plugins {
    application
    kotlin("jvm") version Dep.kotlinVersion
}

application {
    mainClassName = "link.kotlin.scripts.Application"
}

repositories {
    jcenter()
    maven { url = uri("https://dl.bintray.com/heapy/heap") }
}

dependencies {
    compile(stdlib)
    compile(reflect)
    compile(coroutines)

    compile(jacksonXml)
    compile(jacksonKotlin)

    compile("org.slf4j:slf4j-api:1.7.25")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.sentry:sentry-logback:1.6.3")

    compile("com.rometools:rome:1.7.0")
    compile("com.github.dfabulich:sitemapgen4j:1.0.6")
    compile("org.jsoup:jsoup:1.10.2")
    compile("by.heap.remark:remark-kotlin:1.2.0")

    compile("org.jetbrains.kotlin:kotlin-script-util:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
    compile(commonmark)
    compile(commonmarkExtGfmTables)

    compile("com.squareup.okhttp3:okhttp:3.8.1")

    testCompile(mockk)
    testCompile(junit)
}
