package link.kotlin.backend

data class GithubConfiguration(
    val user: String,
    val token: String
)

data class ApplicationConfiguration(
    val baseUrl: String,
    val github: GithubConfiguration
)
