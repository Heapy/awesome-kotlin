plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "link.kotlin.server.Application"
    applicationName = "awesome"
}

repositories {
    jcenter()
}

dependencies {
    implementation(stdlib)
    implementation(reflect)
    implementation(coroutines)

    implementation(jacksonXml)
    implementation(jacksonKotlin)
    implementation(jacksonJsr310)

    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation(mockk)
    testImplementation(junitApi)
    testRuntimeOnly(junitEngine)
}
