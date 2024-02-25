package utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

@Suppress("NOTHING_TO_INLINE")
inline fun logger(noinline lambda: () -> Unit): Logger {
    val name = lambda.javaClass.name
    val contextName = when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }

    return LoggerFactory.getLogger(contextName)
}
