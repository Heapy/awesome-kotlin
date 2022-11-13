plugins {
    application
    kotlin("jvm").version("1.7.21")
    kotlin("plugin.serialization").version("1.7.21")
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
        jvmTarget = "17"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.4.1")
    implementation("com.charleskorn.kaml:kaml:0.49.0")

    implementation("org.flywaydb:flyway-core:9.7.0")
    implementation("org.jooq:jooq:3.17.5")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("at.favre.lib:bcrypt:0.9.0")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
    implementation("io.ktor:ktor-client-cio:2.1.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-server-cio:2.1.3")
    implementation("io.ktor:ktor-server-locations:2.1.3")
    implementation("io.ktor:ktor-server-websockets:2.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-server-auth-jwt:2.1.3")
    implementation("io.ktor:ktor-server-metrics-micrometer:2.1.3")
    implementation("io.micrometer:micrometer-registry-prometheus:1.10.0")
    implementation("io.ktor:ktor-server-call-logging:2.1.3")
    implementation("io.ktor:ktor-server-default-headers:2.1.3")
    implementation("io.ktor:ktor-server-caching-headers:2.1.3")
    implementation("io.ktor:ktor-server-status-pages:2.1.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0")

    implementation("ch.qos.logback:logback-classic:1.4.4")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}
