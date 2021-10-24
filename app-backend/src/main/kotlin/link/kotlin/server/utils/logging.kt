package link.kotlin.server.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory

val REVIEW: Marker = MarkerFactory.getMarker("REVIEW")

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
