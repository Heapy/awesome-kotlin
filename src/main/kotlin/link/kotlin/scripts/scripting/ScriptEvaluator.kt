package link.kotlin.scripts.scripting

import link.kotlin.scripts.utils.Cache
import link.kotlin.scripts.utils.cacheKey
import link.kotlin.scripts.utils.default
import kotlin.reflect.KClass
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.StringScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

interface ScriptEvaluator {
    fun <T : Any> eval(source: String, name: String, type: KClass<T>): T

    companion object
}

private class ScriptingScriptEvaluator(
    private val scriptingHost: BasicJvmScriptingHost
) : ScriptEvaluator {
    override fun <T : Any> eval(source: String, name: String, type: KClass<T>): T {
        val eval = scriptingHost.evalWithTemplate<AwesomeScript>(StringScriptSource(source, name))
        @Suppress("UNCHECKED_CAST")
        return (eval.valueOrThrow().returnValue as ResultValue.Value).value as T
    }
}

private class CachingScriptEvaluator(
    private val cache: Cache,
    private val scriptEvaluator: ScriptEvaluator
) : ScriptEvaluator {
    override fun <T : Any> eval(source: String, name: String, type: KClass<T>): T {
        val cacheKey = Cache.cacheKey(source)
        val cacheValue = cache.get(cacheKey, type)

        return if (cacheValue == null) {
            val result = scriptEvaluator.eval(source, name, type)
            cache.put(cacheKey, result)
            result
        } else {
            cacheValue
        }
    }
}

fun ScriptEvaluator.Companion.default(
    scriptingHost: BasicJvmScriptingHost = BasicJvmScriptingHost(),
    cache: Cache
): ScriptEvaluator {
    val scriptingScriptEvaluator = ScriptingScriptEvaluator(
        scriptingHost = scriptingHost
    )

    return CachingScriptEvaluator(
        cache = cache,
        scriptEvaluator = scriptingScriptEvaluator
    )
}
