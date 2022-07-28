@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    application
    kotlin("jvm")
    alias(libs.plugins.kt.serializatin)
}

application {
    mainClass.set("link.kotlin.server.Application")
    applicationName = "awesome"
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.hocon)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.coroutines)

    implementation(libs.flyway)
    implementation(libs.jooq.core)
    implementation(libs.postgresql)
    implementation(libs.hikari)

    implementation(libs.bcrypt)

    implementation(libs.ktor.serialization)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.content.negation)
    implementation(libs.ktor.server)
    implementation(libs.ktor.server.locations)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.content.negation)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.metrics.micrometer)
    implementation(libs.micrometer.prometheus)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.caching.headers)
    implementation(libs.ktor.server.status.pages)

    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.dataformat.xml)

    implementation(libs.slf4j.api)
    implementation(libs.logback)

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}
