package usecases.github_trending

import kotlinx.serialization.Serializable

@Serializable
data class GithubConfig(
    val token: String,
)
