object Dep {
    const val kotlinVersion = "1.3.21"

    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    private val jacksonVersion = "2.9.8"
    val jacksonXml = "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion"
    val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion"

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.1.1"

    private val commonmarkVersion = "0.11.0"
    val commonmark = "com.atlassian.commonmark:commonmark:$commonmarkVersion"
    val commonmarkExtGfmTables = "com.atlassian.commonmark:commonmark-ext-gfm-tables:$commonmarkVersion"

    val httpClient = "org.apache.httpcomponents:httpasyncclient:4.1.4"

    val mockk = "io.mockk:mockk:1.9"
    val junit = "org.junit.jupiter:junit-jupiter-engine:5.4.0"
}
