@file:JvmName("Application")

package link.kotlin.scripts

import io.heapy.komok.tech.di.delegate.buildModule
import link.kotlin.scripts.module.ApplicationModule
import link.kotlin.scripts.utils.logger
import kotlin.system.exitProcess

fun main() {
    try {
        val app = buildModule<ApplicationModule>()
        val generator = app.awesomeKotlinGenerator.value

        // Load data
        val articles = generator.getArticles()
        val links = generator.getLinks()

        // Generate resources for site
        generator.generateSiteResources(links, articles)

        LOGGER.info("Done, exit.")
        exitProcess(0)
    } catch (e: Exception) {
        LOGGER.error("Failed, exit.", e)
        exitProcess(1)
    }
}

private val LOGGER = logger {}
