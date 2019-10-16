import Dep.Kotlin

plugins {
    kotlin("jvm").version(Dep.kotlinVersion)
    application
}

application {
    applicationName = "awesome"
    mainClassName = "link.kotlin.backend.Backend"
}

repositories {
    jcenter()
    maven { url = uri("https://dl.bintray.com/heapy/heap") }
}

dependencies {
    implementation(Kotlin.stdlib)
    implementation(Kotlin.reflect)
    implementation(Dep.coroutines)

    implementation(Dep.jacksonXml)
    implementation(Dep.jacksonKotlin)

    implementation("io.github.config4k:config4k:0.4.1")
    implementation("io.undertow:undertow-core:2.0.26.Final")

    implementation("org.koin:koin-core-ext:2.0.1")
    implementation("org.koin:koin-logger-slf4j:2.0.1")

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.sentry:sentry-logback:1.6.3")

    implementation("com.rometools:rome:1.7.0")
    implementation("com.github.dfabulich:sitemapgen4j:1.0.6")
    implementation("org.jsoup:jsoup:1.10.2")
    implementation("by.heap.remark:remark-kotlin:1.2.0")

    implementation(Dep.commonmark)
    implementation(Dep.commonmarkExtGfmTables)

    implementation(Dep.httpClient)

    testImplementation(Dep.mockk)
    testImplementation(Dep.junit)
}
