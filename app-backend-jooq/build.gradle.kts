plugins {
    kotlin("jvm")
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:3.18.7")
    implementation("org.flywaydb:flyway-core:10.1.0")
    implementation("org.postgresql:postgresql:42.7.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}
