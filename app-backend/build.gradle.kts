plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

application {
    mainClass.set("link.kotlin.server.Application")
    applicationName = "awesome"
}

repositories {
    mavenCentral()
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-opt-in=io.ktor.server.locations.KtorExperimentalLocationsAPI",
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.6.1")
    implementation("com.charleskorn.kaml:kaml:0.55.0")

    implementation("org.flywaydb:flyway-core:10.0.1")
    implementation("org.jooq:jooq:3.18.7")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("at.favre.lib:bcrypt:0.10.2")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
    implementation("io.ktor:ktor-client-cio:2.3.6")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-server-cio:2.3.6")
    implementation("io.ktor:ktor-server-locations:2.3.6")
    implementation("io.ktor:ktor-server-websockets:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.6")
    implementation("io.ktor:ktor-server-metrics-micrometer:2.3.6")
    implementation("io.micrometer:micrometer-registry-prometheus:1.12.0")
    implementation("io.ktor:ktor-server-call-logging:2.3.6")
    implementation("io.ktor:ktor-server-default-headers:2.3.6")
    implementation("io.ktor:ktor-server-caching-headers:2.3.6")
    implementation("io.ktor:ktor-server-status-pages:2.3.6")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}
