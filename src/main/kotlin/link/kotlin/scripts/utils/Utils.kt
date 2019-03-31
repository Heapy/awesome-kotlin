package link.kotlin.scripts.utils

import link.kotlin.scripts.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun parseInstant(date: String): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.parse(date), ZoneId.of("UTC"))
}

private val LOGGER = logger<Application>()
suspend fun <R> measureAndLog(message: String, block: suspend () -> R): R {
    LOGGER.info("Start: $message.")
    val start = System.currentTimeMillis()
    val result = block()
    val total = System.currentTimeMillis() - start
    val duration = Duration.ofMillis(total).seconds
    LOGGER.info("Done: $message. Time taken: ${duration}s")
    return result
}
