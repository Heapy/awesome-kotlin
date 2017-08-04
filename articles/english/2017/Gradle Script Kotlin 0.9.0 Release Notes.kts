

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Gradle Script Kotlin 0.9.0 Release Notes
========================================

Gradle Script Kotlin v0.9.0 is another major step forward in usability, bringing improvements to the DSL, IntelliJ experience, performance, and finally automatic detection of Kotlin based builds.

v0.9.0 is expected to be included in the upcoming Gradle 4.0 RC1.

The features in this release are also available for immediate use within the latest Gradle Script Kotlin distribution snapshot. To use it, upgrade your Gradle wrapper in the following fashion:

    ${'$'} cd ${'$'}YOUR_PROJECT_ROOT
    ${'$'} gradle wrapper --gradle-distribution-url https://repo.gradle.org/gradle/dist-snapshots/gradle-script-kotlin-4.0-20170518042627+0000-all.zip

Updates since v0.8.0
--------------------

 * **Automatic detection of Kotlin based builds** ([#37][10037], [#80][10080]). After a little more than an year since the issue was first added to our board this huge usability improvement has finally landed!

    No more `rootProject.buildFileName = 'build.gradle.kts'` boilerplate or `settings.gradle` file required in order to enable Kotlin build script! :tada:

 * **Default imports for the whole Gradle API** ([#215][100215], [#347][100347]). To make build scripts more concise, the set of default imports now includes the whole Gradle API as documented in the [Default imports section of the Gradle User Guide](https://docs.gradle.org/current/userguide/writing_build_scripts.html#script-default-imports).

 * **Improved Gradle API with type safe setters** ([#341][100341]). Kotlin recognizes mutable JavaBean properties only when both the getter and at least one setter agree on the property type.

     More than 50 strongly typed setters have been recently added to the Gradle API enabling build scripts to migrate from invocation heavy configuration syntax such as:

    ```kotlin
    val someBuild by tasks.creating(GradleBuild::class) {
        setDir(file("some/path"))      // NOT RECOGNIZED AS PROPERTY BECAUSE OF UNTYPED SETTER
        setTasks(listOf("foo", "bar")) // NOT RECOGNIZED AS PROPERTY BECAUSE OF UNTYPED SETTER
    }
    ```

    to the more declarative:

    ```kotlin
    val someBuild by tasks.creating(GradleBuild::class) {
        dir = file("some/path")
        tasks = listOf("foo", "bar")
    }
    ```

 * **Improved project extension accessors with properties** ([#330][100330]). So one can now write `java.sourceSets` instead of `java().sourceSets` as in `0.8.0`.

 * **API documentation** ([#209][100209]). A first cut of this important piece of documentation, generated using [Dokka](https://kotlinlang.org/docs/reference/kotlin-doc.html), is now available at https://gradle.github.io/gradle-script-kotlin-docs/api/.

 * **IntelliJ improvements**

   * **Classpath computation is now asynchronous** ([#249][100249]). And should no longer block the UI (pending a fix to [this IntelliJ issue](https://youtrack.jetbrains.com/issue/KT-17771))

   * **Type-safe accessors are correctly included in the classpath given to IntelliJ** ([#340][100340]). Upon changes to the `plugins` block (pending a fix to [this IntelliJ issue](https://youtrack.jetbrains.com/issue/KT-17770))

   * **Source code navigation now works for everything Gradle** ([#281][100281]).

     ![source-code-navigation](https://cloud.githubusercontent.com/assets/51689/25772994/1fb02b2a-324c-11e7-91aa-8c7e51c86d86.gif)

   * **Source code navigation to sources of included Kotlin libraries** ([#96][10096]). As long as there's at least one `buildscript` repository configured that can resolve the Kotlin source artifacts.

 * **Miscellaneous**

   * **Polished Android Sample** ([#351][100351]). With all the improvements in this release, our [hello-android sample](https://github.com/gradle/gradle-script-kotlin/tree/master/samples/hello-android) is now boilerplate free:

        ```kotlin
        buildscript {
            dependencies {
                classpath("com.android.tools.build:gradle:2.3.1")
                classpath(kotlinModule("gradle-plugin"))
            }
            repositories {
                jcenter()
            }
        }

        apply {
            plugin("com.android.application")
            plugin("kotlin-android")
        }

        android {
            buildToolsVersion("25.0.0")
            compileSdkVersion(23)

            defaultConfig {
                minSdkVersion(15)
                targetSdkVersion(23)

                applicationId = "com.example.kotlingradle"
                versionCode = 1
                versionName = "1.0"
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles("proguard-rules.pro")
                }
            }
        }

        dependencies {
            compile("com.android.support:appcompat-v7:23.4.0")
            compile("com.android.support.constraint:constraint-layout:1.0.0-alpha8")
            compile(kotlinModule("stdlib"))
        }

        repositories {
            jcenter()
        }
        ```

        And it works with the latest Android Studio (2.3.2).

   * **Gradle initialization overhead removed** ([#320][100320]). The implementation of type-safe accessors in `0.8.0` added some undue overhead to project configuration even when there was no Kotlin build script involved. This has been fixed.

   * **Idiomatic support for Gradle's `PropertyState<T>` and `ConfigurableFileCollection` properties** ([#344][100344]). Via Kotlin delegated properties:

        ```kotlin
        open class GreetingPluginExtension(project: Project) {

            // Declare a `PropertyState<String>` backing field
            private
            val messageState = project.property<String>()

            // Expose `messageState` as the `message` property whose type is inferred as String
            var message by messageState

            // Can also be exposed as `Provider<String>` for additional functionality
            val messageProvider: Provider<String> get() = messageState

            // `outputFiles` property type is inferred as `ConfigurableFileCollection`
            // with the following behaviour:
            //  - getting will always return the original instance
            //  - setting will `setFrom` the source
            var outputFiles by project.files()
        }
        ```

        Check out the [provider-properties sample](/gradle/gradle-script-kotlin/tree/master/samples/provider-properties) for more information.

   * **Better caching behaviour for type-safe accessors** ([#338][338]).

 * **Bug fixes**

    * **Setting non-existent Kotlin build script in settings.gradle no longer causes the build to fail** ([#302][100302], [#331][100331]). Following standard Gradle behaviour.

    * **Generated extension accessor for the `publishing` extension will work as expected** ([#327][100327], [#328][100328]). And defer configuration until necessary.

    * **Projects with Kotlin build scripts in `buildSrc` can be edited with the correct classpath in IntelliJ** ([#339][100339]). As build scripts will now be executed in a best-effort manner when computing the classpath.


[10037]: https://github.com/gradle/gradle-script-kotlin/issues/37
[10080]: https://github.com/gradle/gradle-script-kotlin/issues/80
[100215]: https://github.com/gradle/gradle-script-kotlin/issues/215
[100347]: https://github.com/gradle/gradle-script-kotlin/issues/347
[100341]: https://github.com/gradle/gradle-script-kotlin/issues/341
[100330]: https://github.com/gradle/gradle-script-kotlin/issues/330
[100209]: https://github.com/gradle/gradle-script-kotlin/issues/209
[100249]: https://github.com/gradle/gradle-script-kotlin/issues/249
[100340]: https://github.com/gradle/gradle-script-kotlin/issues/340
[100281]: https://github.com/gradle/gradle-script-kotlin/issues/281
[10096]: https://github.com/gradle/gradle-script-kotlin/issues/96
[100351]: https://github.com/gradle/gradle-script-kotlin/issues/351
[100320]: https://github.com/gradle/gradle-script-kotlin/issues/320
[100344]: https://github.com/gradle/gradle-script-kotlin/issues/344
[100338]: https://github.com/gradle/gradle-script-kotlin/issues/338
[100302]: https://github.com/gradle/gradle-script-kotlin/issues/302
[100331]: https://github.com/gradle/gradle-script-kotlin/issues/331
[100327]: https://github.com/gradle/gradle-script-kotlin/issues/327
[100328]: https://github.com/gradle/gradle-script-kotlin/issues/328
[100339]: https://github.com/gradle/gradle-script-kotlin/issues/339
"""

Article(
  title = "Gradle Script Kotlin 0.9.0 Release Notes",
  url = "https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.9.0",
  categories = listOf(
    "Kotlin",
    "Gradle Script Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Rodrigo B. de Oliveira",
  date = LocalDate.of(2017, 5, 18),
  body = body
)
