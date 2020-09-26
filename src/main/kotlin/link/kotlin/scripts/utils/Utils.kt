package link.kotlin.scripts.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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

fun parseInstant(date: String): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.parse(date), ZoneId.of("UTC"))
}

@Suppress("ObjectLiteralToLambda")
inline fun <reified T : Any> callLogger(instance: T): T {
    return Proxy.newProxyInstance(
        T::class.java.classLoader,
        arrayOf(T::class.java),
        object : InvocationHandler {
            override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
                val log = logger<T>()
                log.info("Start: ${method.name}.")
                val start = System.nanoTime()

                val result: Any? = try {
                    if (args != null) {
                        method.invoke(instance, *args)
                    } else {
                        method.invoke(instance)
                    }
                } catch (e: RuntimeException) {
                    throw e
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }

                val total = System.nanoTime() - start
                val duration = Duration.ofNanos(total).seconds
                log.info("Done: ${method.name}. Time taken: ${duration}s")

                return result
            }
        }
    ) as T
}
