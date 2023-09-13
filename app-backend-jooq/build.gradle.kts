plugins {
    kotlin("jvm").version("1.9.0")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.18.6")
    implementation("org.flywaydb:flyway-core:9.21.1")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}
