plugins {
    application
    kotlin("jvm").version("1.9.20")
    kotlin("plugin.serialization").version("1.9.20")
}

application {
    mainClass.set("link.kotlin.scripts.Application")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0")

    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("com.rometools:rome:2.1.0")
    implementation("com.github.dfabulich:sitemapgen4j:1.1.2")
    implementation("org.jsoup:jsoup:1.17.1")

    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))

    implementation("org.commonmark:commonmark:0.21.0")
    implementation("org.commonmark:commonmark-ext-gfm-tables:0.21.0")

    implementation("io.ktor:ktor-client-apache:2.3.6")
    implementation("io.ktor:ktor-client-jackson:2.3.6")

    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}
