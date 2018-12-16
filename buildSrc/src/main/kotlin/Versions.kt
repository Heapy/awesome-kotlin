import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val remark_kotlin: String = "1.2.0" 

    const val logback_classic: String = "1.2.3" 

    const val com_atlassian_commonmark: String = "0.11.0" 

    const val jackson_dataformat_xml: String = "2.9.3" // available: "2.9.7"

    const val jackson_module_kotlin: String = "2.9.2" // available: "2.9.7"

    const val sitemapgen4j: String = "1.0.6" // available: "1.1.1"

    const val rome: String = "1.7.0" // available: "1.11.1"

    const val okhttp: String = "3.8.1" // available: "3.12.0"

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.3.2" 

    const val sentry_logback: String = "1.6.3" // available: "1.7.15"

    const val junit: String = "4.12" 

    const val org_jetbrains_kotlin_jvm_gradle_plugin: String = "1.2.70" // available: "1.3.11"

    const val kotlin_compiler_embeddable: String = "1.2.51" // available: "1.3.10"

    const val kotlin_reflect: String = "1.2.51" // available: "1.3.10"

    const val kotlin_script_util: String = "1.2.51" // available: "1.3.10"

    const val kotlin_scripting_compiler_embeddable: String = "1.2.70" // available: "1.3.10"

    const val kotlin_stdlib_jdk8: String = "1.2.51" // available: "1.3.10"

    const val kotlinx_coroutines_jdk8: String = "0.22.5" // available: "1.0.1"

    const val jsoup: String = "1.10.2" // available: "1.11.3"

    const val slf4j_api: String = "1.7.25" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "4.10.2"

        const val currentVersion: String = "5.0"

        const val nightlyVersion: String = "5.2-20181216000032+0000"

        const val releaseCandidate: String = "5.1-rc-1"
    }
}
