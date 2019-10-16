object Module {
    const val backend = ":app-backend"
}

object Dep {
    const val kotlinVersion = "1.3.50"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    private val jacksonVersion = "2.9.9"
    val jacksonXml = "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion"
    val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion"

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.2"

    private val commonmarkVersion = "0.12.1"
    val commonmark = "com.atlassian.commonmark:commonmark:$commonmarkVersion"
    val commonmarkExtGfmTables = "com.atlassian.commonmark:commonmark-ext-gfm-tables:$commonmarkVersion"

    val httpClient = "org.apache.httpcomponents:httpasyncclient:4.1.4"

    val mockk = "io.mockk:mockk:1.9.3"
    val junit = "org.junit.jupiter:junit-jupiter-engine:5.4.2"
}
