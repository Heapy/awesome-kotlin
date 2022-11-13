plugins {
    kotlin("jvm").version("1.7.21")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.17.5")
    implementation("org.flywaydb:flyway-core:9.7.0")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("ch.qos.logback:logback-classic:1.4.4")
}
