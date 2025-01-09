package infra.ktor.auth

data class UserContext(
    val id: Long,
    val roles: Set<String>,
    val permissions: Set<String>,
)
