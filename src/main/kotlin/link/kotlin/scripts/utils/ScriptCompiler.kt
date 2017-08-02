package link.kotlin.scripts.utils

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import java.io.InputStream
import java.util.concurrent.atomic.AtomicInteger
import javax.script.ScriptEngine

interface ScriptCompiler {
    fun <T> execute(inputStream: InputStream): T
}

class DefaultScriptCompiler : ScriptCompiler {
    private val factory = KotlinJsr223JvmLocalScriptEngineFactory()
    private var scriptEngine = factory.scriptEngine
    private var counter = AtomicInteger(0)

    private fun getEngine(): ScriptEngine {
        if (counter.incrementAndGet() > 20) {
            LOGGER.info("Create new script engine.")
            scriptEngine = factory.scriptEngine
            counter.set(0)
        }

        return scriptEngine
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> execute(inputStream: InputStream): T {
        return getEngine().eval(inputStream.reader()) as T
    }

    companion object {
        private val LOGGER = logger<DefaultScriptCompiler>()
    }
}
