plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.20.3")
    implementation("org.flywaydb:flyway-database-postgresql:11.7.0")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("ch.qos.logback:logback-classic:1.5.18")
}
