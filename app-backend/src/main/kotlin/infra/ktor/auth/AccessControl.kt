package infra.ktor.auth

sealed interface AccessControl {
    companion object {
        fun allOf(vararg accessControls: AccessControl): AllOf {
            return AllOf(accessControls.toList())
        }

        fun anyOf(vararg accessControls: AccessControl): AnyOf {
            return AnyOf(accessControls.toList())
        }
    }
}

data class Role(val role: String) : AccessControl
data class Permission(val permission: String) : AccessControl
data class AllOf(val accessControls: List<AccessControl>) : AccessControl
data class AnyOf(val accessControls: List<AccessControl>) : AccessControl
object AlwaysAllow : AccessControl

fun isAuthorized(
    userContext: UserContext,
    accessControl: AccessControl,
): Boolean {
    return when (accessControl) {
        is Role -> userContext.roles.contains(accessControl.role)
        is Permission -> userContext.permissions.contains(accessControl.permission)
        is AllOf -> accessControl.accessControls.all { isAuthorized(userContext, it) }
        is AnyOf -> accessControl.accessControls.any { isAuthorized(userContext, it) }
        is AlwaysAllow -> true
    }
}
