@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    application
    alias(libs.plugins.kt.jvm)
}

application {
    mainClass.set("link.kotlin.scripts.Application")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.kotlin.link") }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.coroutines)

    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.dataformat.xml)

    implementation(libs.slf4j.api)
    implementation(libs.logback)

    implementation(libs.rome)
    implementation(libs.sitemapgen4j)
    implementation(libs.jsoup)

    implementation(libs.kotlin.scripting.common)
    implementation(libs.kotlin.scripting.jvm)
    implementation(libs.kotlin.scripting.jvm.host)

    implementation(libs.commonmark)
    implementation(libs.commonmark.ext.gfm.tables)

    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.jackson)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}
