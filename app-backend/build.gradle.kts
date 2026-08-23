plugins {
    application
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.serialization)
}

allOpen {
    annotation("io.heapy.komok.tech.di.lib.Module")
}

application {
    mainClass.set("Application")
    applicationName = "awesome"
}

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
            "-Xreturn-value-checker=full",
        )
        optIn.addAll(
            "kotlin.concurrent.atomics.ExperimentalAtomicApi",
        )
    }

    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.hocon)
    implementation(libs.kaml)
    implementation(libs.xmlutil)

    implementation(libs.flyway.postgresql)
    implementation(libs.jooq)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)

    implementation(libs.bcrypt)
    implementation(libs.bouncycastle.bcpkix)

    implementation(libs.rome)

    implementation(libs.sitemapgen4j)

    implementation(libs.commonmark)
    implementation(libs.commonmark.ext.gfm.tables)

    implementation(libs.jsoup)

    ksp(libs.komok.tech.di)
    implementation(libs.komok.tech.di.lib)
    implementation(libs.komok.tech.to.be.injected)
    implementation(libs.komok.tech.config.dotenv)
    implementation(libs.komok.tech.logging)
    implementation(libs.komok.tech.time)

    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.metrics.micrometer)
    implementation(libs.micrometer.prometheus)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.caching.headers)
    implementation(libs.ktor.server.status.pages)

    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.dataformat.xml)

    implementation(libs.logback)

    testImplementation(testFixtures(libs.komok.tech.time))
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.distZip {
    enabled = false
}
