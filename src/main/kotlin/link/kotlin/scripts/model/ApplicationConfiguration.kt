package link.kotlin.scripts.model

import link.kotlin.scripts.utils.logger
import java.lang.System.getenv

interface ApplicationConfiguration {
    val ghToken: String
    val siteUrl: String
    val cacheEnabled: Boolean
    val dryRun: Boolean

    companion object
}

data class DataApplicationConfiguration(
    override val ghToken: String = env("GH_TOKEN", ""),
    override val siteUrl: String = "https://kotlin.link/",
    override val cacheEnabled: Boolean = env("AWESOME_KOTLIN_CACHE", "true").toBoolean(),
    override val dryRun: Boolean = false
): ApplicationConfiguration

fun ApplicationConfiguration.Companion.default(): ApplicationConfiguration {
    val configuration = DataApplicationConfiguration()

    return if (configuration.ghToken.isEmpty()) {
        logger<DataApplicationConfiguration>().info("GH_TOKEN is not defined, dry run...")
        configuration.copy(dryRun = true)
    } else configuration
}

private fun env(key: String, default: String): String {
    return getenv(key) ?: default
}
