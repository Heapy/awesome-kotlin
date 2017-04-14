package link.kotlin.scripts

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import java.io.InputStream

interface ScriptCompiler {
    fun <T> execute(inputStream: InputStream): T
}

class DefaultScriptCompiler : ScriptCompiler {
    private val factory = KotlinJsr223JvmLocalScriptEngineFactory()
    private val scriptEngine = factory.scriptEngine

    @Suppress("UNCHECKED_CAST")
    override fun <T> execute(inputStream: InputStream): T {
        return scriptEngine.eval(inputStream.reader()) as T
    }
}