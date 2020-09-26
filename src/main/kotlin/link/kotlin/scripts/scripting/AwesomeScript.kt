package link.kotlin.scripts.scripting

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    displayName = "Awesome Kotlin Executor",
    fileExtension = "awesome.kts",
    compilationConfiguration = LinkScriptCompilationConfiguration::class
)
abstract class AwesomeScript

internal object LinkScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports(
        "link.kotlin.scripts.dsl.*",
        "link.kotlin.scripts.dsl.PlatformType.*",
        "link.kotlin.scripts.dsl.PlatformType.ANDROID",
        "link.kotlin.scripts.dsl.PlatformType.COMMON",
        "link.kotlin.scripts.dsl.PlatformType.IOS",
        "link.kotlin.scripts.dsl.PlatformType.JS",
        "link.kotlin.scripts.dsl.PlatformType.JVM",
        "link.kotlin.scripts.dsl.PlatformType.NATIVE",
        "link.kotlin.scripts.dsl.PlatformType.WASM",
    )

    jvm {
        dependenciesFromClassContext(
            AwesomeScript::class,
            "awesome-kotlin",
            "kotlin-stdlib"
        )
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Project)
    }
})
