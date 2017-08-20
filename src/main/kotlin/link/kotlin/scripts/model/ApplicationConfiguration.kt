package link.kotlin.scripts.model

import java.lang.System.getenv

data class ApplicationConfiguration(
    val ghUser: String = env("GH_USER", ""),
    val ghToken: String = env("GH_TOKEN", ""),
    val mercuryToken: String = env("MERCURY_TOKEN", ""),
    val siteUrl: String = "https://kotlin.link/"
)

private fun env(key: String, default: String): String {
    return getenv(key) ?: default
}
