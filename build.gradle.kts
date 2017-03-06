import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.script.lang.kotlin.compile
import org.gradle.script.lang.kotlin.configure
import org.gradle.script.lang.kotlin.dependencies
import org.gradle.script.lang.kotlin.repositories
import org.gradle.script.lang.kotlin.task
import org.gradle.script.lang.kotlin.testCompile

buildscript {
    repositories {
        jcenter()
        maven {
            setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1/")
        }
    }

    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:1.2.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.0")
    }
}

apply {
    plugin("kotlin")
    plugin("application")
    plugin("com.github.johnrengelman.shadow")
}

configure<ApplicationPluginConvention> {
    applicationName = "kotbot"
    mainClassName = "link.kotlin.scripts.ApplicationKt"
}

configure<ShadowJar>("shadowJar") {
    mergeServiceFiles()
}

repositories {
    jcenter()
    maven {
        setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1/")
    }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:1.1.0")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.1.0")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.12")

    compile("io.bootique:bootique:0.21")
    compile("io.bootique.logback:bootique-logback:0.13")

    compile("com.squareup.okhttp3:okhttp:3.5.0")

    testCompile("junit:junit:4.12")
}

task(name = "wrapper", type = Wrapper::class) {
    gradleVersion = "3.4"
    distributionUrl = "http://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip"
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}
