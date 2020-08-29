package link.kotlin.scripts.scripting

import java.io.File
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class AwesomeScriptHost {
    private val scriptingHost = BasicJvmScriptingHost()

    fun <T> eval(file: File): T {
        val eval = scriptingHost.evalWithTemplate<AwesomeScript>(file.toScriptSource())
        @Suppress("UNCHECKED_CAST")
        return (eval.valueOrThrow().returnValue as ResultValue.Value).value as T
    }
}

