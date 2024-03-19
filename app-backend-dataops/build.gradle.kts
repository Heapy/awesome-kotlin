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
    implementation("org.jooq:jooq-codegen:3.19.6")
    implementation("org.flywaydb:flyway-database-postgresql:10.10.0")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("ch.qos.logback:logback-classic:1.5.3")
}
