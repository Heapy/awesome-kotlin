package link.kotlin.scripts

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun parseInstant(date: String): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.parse(date), ZoneId.of("UTC"))
}
