plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.20.10")
    implementation("org.flywaydb:flyway-database-postgresql:11.20.2")
    implementation("org.postgresql:postgresql:42.7.9")
    implementation("ch.qos.logback:logback-classic:1.5.25")
}
