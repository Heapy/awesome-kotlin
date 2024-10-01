plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.19.13")
    implementation("org.flywaydb:flyway-database-postgresql:10.18.2")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("ch.qos.logback:logback-classic:1.5.8")
}
