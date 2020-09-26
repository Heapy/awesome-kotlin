package link.kotlin.scripts.model

import java.lang.System.getenv

interface ApplicationConfiguration {
    val ghUser: String
    val ghToken: String
    val siteUrl: String
    val cacheEnabled: Boolean

    companion object
}

data class DataApplicationConfiguration(
    override val ghUser: String = env("GH_USER", ""),
    override val ghToken: String = env("GH_TOKEN", ""),
    override val siteUrl: String = "https://kotlin.link/",
    override val cacheEnabled: Boolean = env("AWESOME_KOTLIN_CACHE", "true").toBoolean()
): ApplicationConfiguration

fun ApplicationConfiguration.Companion.default(): ApplicationConfiguration {
    return DataApplicationConfiguration()
}

private fun env(key: String, default: String): String {
    return getenv(key) ?: default
}
