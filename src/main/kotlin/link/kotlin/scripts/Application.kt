@file:JvmName("Application")

package link.kotlin.scripts

import link.kotlin.scripts.utils.logger
import kotlin.system.exitProcess

fun main() {
    try {
        val generator = AwesomeKotlinGenerator.default()

        // Load data
        val articles = generator.getArticles()
        val links = generator.getLinks()

        // Create README.md
        generator.generateReadme(links)

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
