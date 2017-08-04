
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.script.lang.kotlin.compile
import org.gradle.script.lang.kotlin.configure
import org.gradle.script.lang.kotlin.dependencies
import org.gradle.script.lang.kotlin.extra
import org.gradle.script.lang.kotlin.repositories
import org.gradle.script.lang.kotlin.testCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    extra["kotlinVersion"] = "1.1.4-eap-11"
    extra["commonMarkVersion"] = "0.9.0"

    repositories {
        jcenter()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1/") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlinVersion"]}")
    }
}

apply {
    plugin("kotlin")
    plugin("application")
}

configure<ApplicationPluginConvention> {
    mainClassName = "link.kotlin.scripts.Application"
}

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1/") }
    maven { setUrl("https://dl.bintray.com/heapy/heap") }
}

configure<KotlinProjectExtension> {
    experimental.coroutines = Coroutines.ENABLE
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:${extra["kotlinVersion"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${extra["kotlinVersion"]}")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:0.17")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.8")
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.sentry:sentry-logback:1.4.0")

    compile("com.rometools:rome:1.7.0")
    compile("com.github.dfabulich:sitemapgen4j:1.0.6")
    compile("org.jsoup:jsoup:1.10.2")
    compile("by.heap.remark:remark-kotlin:1.2.0")

    compile("org.jetbrains.kotlin:kotlin-script-util:${extra["kotlinVersion"]}")
    compile("com.atlassian.commonmark:commonmark:${extra["commonMarkVersion"]}")
    compile("com.atlassian.commonmark:commonmark-ext-gfm-tables:${extra["commonMarkVersion"]}")

    compile("com.squareup.okhttp3:okhttp:3.8.1")

    testCompile("junit:junit:4.12")
}
