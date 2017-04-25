package link.kotlin.scripts

data class ApplicationConfiguration(
    val ghUser: String,
    val ghToken: String,
    val mercuryToken: String,
    val siteUrl: String = "https://kotlin.link/"
)
