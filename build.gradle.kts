import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm").version(kotlinVersion)
}

application {
    mainClass.set("link.kotlin.scripts.Application")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.kotlin.link") }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        languageVersion = "1.4"
    }
}

dependencies {
    implementation(stdlib)
    implementation(reflect)
    implementation(coroutines)

    implementation(jacksonXml)
    implementation(jacksonKotlin)
    implementation(jacksonJsr310)

    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.sentry:sentry-logback:1.7.30")

    implementation("com.rometools:rome:1.15.0")
    implementation("com.github.dfabulich:sitemapgen4j:1.1.2")
    implementation("org.jsoup:jsoup:1.13.1")

    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))

    implementation(commonmark)
    implementation(commonmarkExtGfmTables)

    implementation(ktorClientApache)
    implementation(ktorClientJackson)

    testImplementation(mockk)
    testImplementation(junitApi)
    testRuntimeOnly(junitEngine)
}
