package link.kotlin.scripts.scripting

import java.io.File
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class AwesomeScriptHost {
    private val scriptingHost = BasicJvmScriptingHost()

    fun <T> eval(file: File): T {
        return (scriptingHost.evalWithTemplate<AwesomeScript>(file.toScriptSource()).valueOrNull()
            ?.returnValue as ResultValue.Value).value as T
    }
}

