plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

application {
    mainClass.set("Application")
    applicationName = "awesome"
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.komok.tech.to.be.injected)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.10.0")
    implementation("com.charleskorn.kaml:kaml:0.104.0")

    implementation("org.flywaydb:flyway-database-postgresql:11.20.2")
    implementation("org.jooq:jooq:3.20.10")
    implementation("org.postgresql:postgresql:42.7.9")
    implementation("com.zaxxer:HikariCP:7.0.2")

    implementation("at.favre.lib:bcrypt:0.10.2")

    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.0")
    implementation("io.ktor:ktor-client-cio:3.4.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.4.0")
    implementation("io.ktor:ktor-server-cio:3.4.0")
    implementation("io.ktor:ktor-server-resources:3.4.0")
    implementation("io.ktor:ktor-server-websockets:3.4.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.4.0")
    implementation("io.ktor:ktor-server-auth-jwt:3.4.0")
    implementation("io.ktor:ktor-server-metrics-micrometer:3.4.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.16.2")
    implementation("io.ktor:ktor-server-call-logging:3.4.0")
    implementation("io.ktor:ktor-server-default-headers:3.4.0")
    implementation("io.ktor:ktor-server-caching-headers:3.4.0")
    implementation("io.ktor:ktor-server-status-pages:3.4.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.21.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.21.0")

    implementation(libs.logback)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
