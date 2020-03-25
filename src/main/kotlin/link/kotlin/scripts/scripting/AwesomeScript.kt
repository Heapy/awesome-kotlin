package link.kotlin.scripts.scripting

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    displayName = "Awesome Kotlin Executor",
    fileExtension = "kts",
    compilationConfiguration = LinkScriptCompilationConfiguration::class
)
abstract class AwesomeScript

internal object LinkScriptCompilationConfiguration : ScriptCompilationConfiguration({

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

    defaultImports()
})
