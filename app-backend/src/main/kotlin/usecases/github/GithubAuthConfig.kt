package usecases.github

import kotlinx.serialization.Serializable

@Serializable
data class GithubAuthConfig(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
)
