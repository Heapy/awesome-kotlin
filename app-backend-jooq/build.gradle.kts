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
    implementation("org.jooq:jooq-codegen:3.19.0")
    implementation("org.flywaydb:flyway-core:10.3.0")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}
