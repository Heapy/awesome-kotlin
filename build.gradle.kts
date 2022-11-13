plugins {
    application
    kotlin("jvm").version("1.7.21")
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0")

    implementation("ch.qos.logback:logback-classic:1.4.4")

    implementation("com.rometools:rome:1.18.0")
    implementation("com.github.dfabulich:sitemapgen4j:1.1.2")
    implementation("org.jsoup:jsoup:1.15.3")

    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))

    implementation("org.commonmark:commonmark:0.20.0")
    implementation("org.commonmark:commonmark-ext-gfm-tables:0.20.0")

    implementation("io.ktor:ktor-client-apache:1.6.3")
    implementation("io.ktor:ktor-client-jackson:1.6.3")

    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}
