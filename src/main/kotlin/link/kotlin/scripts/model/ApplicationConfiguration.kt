package link.kotlin.scripts.model

import java.lang.System.getenv

interface ApplicationConfiguration {
    val ghToken: String
    val siteUrl: String
    val cacheEnabled: Boolean

    companion object
}

data class DataApplicationConfiguration(
    override val ghToken: String = env("GH_TOKEN", ""),
    override val siteUrl: String = "https://kotlin.link/",
    override val cacheEnabled: Boolean = env("AWESOME_KOTLIN_CACHE", "true").toBoolean()
): ApplicationConfiguration

fun ApplicationConfiguration.Companion.default(): ApplicationConfiguration {
    val configuration = DataApplicationConfiguration()

    if (configuration.ghToken.isEmpty()) {
        throw RuntimeException("You should run this script only when you added GH_TOKEN to env." +
            "Token can be found here: https://github.com/settings/tokens")
    }

    return configuration
}

private fun env(key: String, default: String): String {
    return getenv(key) ?: default
}
