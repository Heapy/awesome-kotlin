plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

application {
    mainClass.set("link.kotlin.scripts.Application")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.coroutines.jdk8)

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")

    implementation(libs.logback)

    ksp(libs.komok.tech.di)
    implementation(libs.komok.tech.di.lib)
    implementation(libs.komok.tech.dotenv)
    implementation(libs.komok.tech.logging)

    implementation("com.rometools:rome:2.1.0")
    implementation("com.github.dfabulich:sitemapgen4j:1.1.2")
    implementation("org.jsoup:jsoup:1.18.3")

    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))

    implementation("org.commonmark:commonmark:0.24.0")
    implementation("org.commonmark:commonmark-ext-gfm-tables:0.24.0")

    implementation("io.ktor:ktor-client-apache:3.0.3")
    implementation("io.ktor:ktor-client-jackson:3.0.3")

    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.distZip {
    enabled = false
}

tasks.distTar {
    enabled = false
}
