package infra.ktor.auth

sealed interface UserContext {
    val id: String
    val roles: Set<String>
    val permissions: Set<String>
}

data class DefaultUserContext(
    override val id: String,
    override val roles: Set<String>,
    override val permissions: Set<String>,
) : UserContext

/**
 * In this case we're expecting that id/roles/permissions will not be used by downstream methods
 */
object AnonymousUserContext : UserContext {
    override val id: String
        get() = error("UserContext not provided for anonymous user")
    override val roles: Set<String>
        get() = error("UserContext not provided for anonymous user")
    override val permissions: Set<String>
        get() = error("UserContext not provided for anonymous user")
}
