plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
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
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.10.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.8.0")
    implementation("com.charleskorn.kaml:kaml:0.72.0")

    implementation("org.flywaydb:flyway-database-postgresql:11.3.2")
    implementation("org.jooq:jooq:3.19.18")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("com.zaxxer:HikariCP:6.2.1")

    implementation("at.favre.lib:bcrypt:0.10.2")

    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.0")
    implementation("io.ktor:ktor-client-cio:3.1.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.0")
    implementation("io.ktor:ktor-server-cio:3.1.0")
    implementation("io.ktor:ktor-server-resources:3.1.0")
    implementation("io.ktor:ktor-server-websockets:3.1.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.0")
    implementation("io.ktor:ktor-server-auth-jwt:3.1.0")
    implementation("io.ktor:ktor-server-metrics-micrometer:3.1.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.14.4")
    implementation("io.ktor:ktor-server-call-logging:3.1.0")
    implementation("io.ktor:ktor-server-default-headers:3.1.0")
    implementation("io.ktor:ktor-server-caching-headers:3.1.0")
    implementation("io.ktor:ktor-server-status-pages:3.1.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")

    implementation("ch.qos.logback:logback-classic:1.5.16")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("io.mockk:mockk:1.13.16")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}
