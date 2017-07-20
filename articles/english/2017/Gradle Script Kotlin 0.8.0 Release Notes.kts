

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Gradle Script Kotlin v0.8.0 is a major step forward in usability, bringing a more consistent DSL, convenient and type-safe access to contributed project extensions and conventions, much better error reporting, bug fixes and, of course, the latest and greatest Kotlin release.

v0.8.0 is expected to be included in the upcoming Gradle 3.5 RC1.

The features in this release are also available for immediate use within the latest Gradle Script Kotlin distribution snapshot. To use it, upgrade your Gradle wrapper in the following fashion:

    $ cd ${"$"}YOUR_PROJECT_ROOT
    $ gradle wrapper --gradle-distribution-url https://repo.gradle.org/gradle/dist-snapshots/gradle-script-kotlin-3.5-20170305000422+0000-all.zip

## Updates since v0.7.0

 * **Kotlin 1.1.0** ([#289](https://github.com/gradle/gradle-script-kotlin/issues/289)). Build scripts are now compiled against [Kotlin 1.1.0](https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1/)! This enables, among other things, [the use of coroutines in build scripts](https://github.com/gradle/gradle-script-kotlin/issues/292#issuecomment-284367696).

 * **Better error reporting** ([#177](https://github.com/gradle/gradle-script-kotlin/issues/177), [#170](https://github.com/gradle/gradle-script-kotlin/issues/170), [#254](https://github.com/gradle/gradle-script-kotlin/issues/254), [#290](https://github.com/gradle/gradle-script-kotlin/issues/290)). Gradle will now report the correct location for compilation errors occurring inside `buildscript` and `plugins` blocks, will do so in a format that's readily recognised by many tools - clicking a compilation error in a console window should open the configured text editor, for instance - and will only ever display stack traces when explicitly instructed via the `--stacktrace` argument.

 * **Consistent DSL across core and community plugins** ([#156](https://github.com/gradle/gradle-script-kotlin/issues/156), [#155](https://github.com/gradle/gradle-script-kotlin/issues/155), [#157](https://github.com/gradle/gradle-script-kotlin/issues/157), [#224](https://github.com/gradle/gradle-script-kotlin/issues/224)). The build script compiler will now treat [Gradle's `Action<T>`](https://docs.gradle.org/current/javadoc/org/gradle/api/Action.html) type as an alias to the `T.() -> Unit` type (a [function literal with receiver](https://kotlinlang.org/docs/reference/lambdas.html#function-literals-with-receiver) in Kotlin parlance) finally solving the [_dreaded `it` problem_](https://www.youtube.com/watch?v=vv4zh_oPBTw&feature=youtu.be&t=1387) once and for all. _WARNING: This is a breaking change and some scripts will fail to compile due to `it` no longer being defined inside lambda expressions passed as `Action<T>` parameters. Simply replace `it` by `this` or remove it altogether._

 * **Type-safe accessors for project extensions and conventions** ([#235](https://github.com/gradle/gradle-script-kotlin/issues/235), [#229](https://github.com/gradle/gradle-script-kotlin/issues/229), [#230](https://github.com/gradle/gradle-script-kotlin/issues/230)). This long awaited feature enables the replacement of type-heavy configuration code such as:

   ```kotlin
   configure<ApplicationPluginConvention> {
       mainClassName = "my.App"
   }
   ```

   by the terser and familiar looking:

   ```kotlin
   application {
       mainClassName = "my.App"
   }
   ```

   Given the inherent flexibility in Gradle's plugin system, it's not possible to cover all the different plugin application scenarios with a single, one-size-fits-all solution so type-safe accessors can be used in three different modes: _just-in-time_, _ahead-of-time_ and _ad hoc_.

   *TL;DR*: Opt-in to type-safe accessors by setting the `org.gradle.script.lang.kotlin.accessors.auto` project property to `"true"` and hack on, if you get _Unresolved reference_ compilation errors, read on.

    * **just-in-time** In just-in-time mode, accessors are generated immediatelly after the evaluation of the `plugins` block, just before the evaluation of the build script body and, because of that, accessors for extensions and conventions registered later in the process are not available. In summary, the following example works in just-in-time mode:

        ```kotlin
        plugins {
            application
        }

        // type-safe accessor for `application` convention generated as:
        //
        //   `fun application(configuration: ApplicationPluginConvention.() -> Unit): ApplicationPluginConvention`
        //

        application {
            mainClassName = "my.App"
        }
        ```

        But the following one does not:

        ```kotlin
        apply {
            plugin("application")
        }

        application { // 💣 Unresolved reference
            mainClassName = "my.App"
        }
        ```
        Due to the potential for build script compilation failures resulting from unaccessible extension types or illegal extension names, just-in-time accessors must be explicitly enabled via the `org.gradle.script.lang.kotlin.accessors.auto` project property set to `"true"`, check out the [gradle.properties file from the hello-world sample](https://github.com/gradle/gradle-script-kotlin/blob/e75d7d99734c5c6fbe67f9b2f7128a8e037f2fbd/samples/hello-world/gradle.properties) for an example.

    * **ahead-of-time** In ahead-of-time mode, type-safe accessors must be explicitly requested via the `gskGenerateAccessors` task. The upside is that all extensions and conventions available at task execution time will be taken into account. The downside is that for the task to even execute, the build script must compile cleanly, which implies we must edit the build script in two steps: step one, apply desired plugins and run `gskGenerateAccessors`, step two, proceed to configure the available extensions and conventions. Additionally, the `buildSrc` file created by `gskGenerateAccessors` must be added to our VCS.

      * Step 1:
        ```kotlin
        // build.gradle.kts
        apply {
            plugin("application")
        }
        ```

        Followed by:

            $ ./gradlew gskGenerateAccessors
            :gskGenerateAccessors

            BUILD SUCCESSFUL

            Total time: 1.867 secs


      * Step 2:
        ```kotlin
        // build.gradle.kts
        apply {
            plugin("application")
        }

        // type-safe accessor for `application` convention
        application {
            mainClassName = "my.App"
        }
        ```

    * **ad-hoc** In this usage mode, the `gskProjectAccessors` task is executed whenever a new type-safe accessor is needed. `gskProjectAccessors` will then write the Kotlin code for all available type-safe accessors to stdout from where it can be copied into the build script or to a Kotlin file under `buildSrc`.

 * **Improved Gradle API**([#239](https://github.com/gradle/gradle-script-kotlin/issues/239), [#122](https://github.com/gradle/gradle-script-kotlin/issues/122), [#219](https://github.com/gradle/gradle-script-kotlin/issues/219), [#246](https://github.com/gradle/gradle-script-kotlin/issues/246), [#245](https://github.com/gradle/gradle-script-kotlin/issues/245), [#247](https://github.com/gradle/gradle-script-kotlin/issues/247), [#244](https://github.com/gradle/gradle-script-kotlin/issues/244), [#243](https://github.com/gradle/gradle-script-kotlin/issues/243), [#242](https://github.com/gradle/gradle-script-kotlin/issues/242), [#241](https://github.com/gradle/gradle-script-kotlin/issues/241), [#240](https://github.com/gradle/gradle-script-kotlin/issues/240), [#238](https://github.com/gradle/gradle-script-kotlin/issues/238), [#206](https://github.com/gradle/gradle-script-kotlin/issues/206), [#226](https://github.com/gradle/gradle-script-kotlin/issues/226)). Many methods in the Gradle API previously only available to Groovy have been overloaded with versions better suited to Kotlin.

 * **Improved Groovy interoperability**([#286](https://github.com/gradle/gradle-script-kotlin/issues/286)). Groovy closures can now be invoked using regular function invocation syntax.

 * **Sub-project build scripts inherit parent project compilation classpath** ([#190](https://github.com/gradle/gradle-script-kotlin/issues/190)).

 * **Projects can use `kotlin-gradle-plugin` 1.0.x again** ([#189](https://github.com/gradle/gradle-script-kotlin/issues/189)). Thanks to the upgrade to Kotlin 1.1.0, compatibility with Kotlin 1.0.x has been restored.

 * **Custom task actions will no longer interfere with the build cache** ([#263](https://github.com/gradle/gradle-script-kotlin/issues/263), [#171](https://github.com/gradle/gradle-script-kotlin/issues/171)).

 * **Build scripts can contain Windows line endings** ([#227](https://github.com/gradle/gradle-script-kotlin/issues/227), [#220](https://github.com/gradle/gradle-script-kotlin/issues/220)).
"""

Article(
  title = "Gradle Script Kotlin 0.8.0 Release Notes",
  url = "https://github.com/gradle/gradle-script-kotlin/releases/tag/v0.8.0",
  categories = listOf(
    "Kotlin",
    "Gradle Script Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Rodrigo B. de Oliveira",
  date = LocalDate.of(2017, 3, 9),
  body = body
)
