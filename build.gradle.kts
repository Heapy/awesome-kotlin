import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    application
    kotlin("jvm") version Versions.org_jetbrains_kotlin_jvm_gradle_plugin
    id("de.fayard.buildSrcVersions") version Versions.de_fayard_buildsrcversions_gradle_plugin
}

application {
    mainClassName = "link.kotlin.scripts.Application"
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/heapy/heap") }
}

dependencies {

    compile(Libs.kotlin_stdlib_jdk8)
    compile(Libs.kotlin_reflect)
    compile(Libs.kotlinx_coroutines_jdk8) 
    compile(Libs.jackson_module_kotlin) 
    compile(Libs.jackson_dataformat_xml) 
    compile(Libs.slf4j_api) 
    compile(Libs.logback_classic) 
    compile(Libs.sentry_logback) 
    compile(Libs.rome) 
    compile(Libs.sitemapgen4j) 
    compile(Libs.jsoup) 
    compile(Libs.remark_kotlin) 
    compile(Libs.kotlin_script_util) 
    compile(Libs.kotlin_compiler_embeddable) 
    compile(Libs.commonmark) 
    compile(Libs.commonmark_ext_gfm_tables) 
    compile(Libs.okhttp) 
    testCompile(Libs.junit)
}
