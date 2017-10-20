
import com.beust.kobalt.buildScript
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.project

// Why kobalt using own directory for caching artifacts? Why not maven/gradle cache?

// Sync library without such version - empty project, no error.
// Fixed version - worked!

data class ProjectVersions(
    val kotlin: String = "1.1.51",
    val commonmark: String = "0.9.0",
    val testNg: String = "6.11",
    val kotlinCoroutines: String = "0.19.1"
)

val v = ProjectVersions()

val p = project {
    name = "awesome-kotlin"
    group = "link.kotlin"
    artifactId = name
    version = "0.1"

    buildScript {
        repos("https://dl.bintray.com/heapy/heap")
    }

    dependencies {
        compile(
            "org.jetbrains.kotlin:kotlin-stdlib-jre8:${v.kotlin}",
            "org.jetbrains.kotlin:kotlin-reflect:${v.kotlin}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${v.kotlinCoroutines}",
            "com.fasterxml.jackson.module:jackson-module-kotlin:2.8.8",
            "org.slf4j:slf4j-api:1.7.25",
            "com.rometools:rome:1.7.0",
            "ch.qos.logback:logback-classic:1.2.3",
            "io.sentry:sentry-logback:1.4.0",
            "com.github.dfabulich:sitemapgen4j:1.0.6",
            "org.jsoup:jsoup:1.10.2",
            "by.heap.remark:remark-kotlin:1.2.0",
            "org.jetbrains.kotlin:kotlin-script-util:${v.kotlin}",
            "com.atlassian.commonmark:commonmark:${v.commonmark}",
            "com.atlassian.commonmark:commonmark-ext-gfm-tables:${v.commonmark}",
            "com.squareup.okhttp3:okhttp:3.8.1"
        )
    }

    dependenciesTest {
        compile("org.testng:testng:${v.testNg}")
    }

    assemble {
        jar {
        }
    }

    application {
        mainClass = "link.kotlin.scripts.Application"
        jvmArgs("-Xmx2g")
        args("true")
    }
}
