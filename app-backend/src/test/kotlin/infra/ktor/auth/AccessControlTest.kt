package infra.ktor.auth

import org.junit.jupiter.api.Assertions.*

import types.UnitTest

class AccessControlTest {
    private val userContext = UserContext(
        id = 1,
        roles = setOf("admin", "editor"),
        permissions = setOf("read", "write", "delete")
    )

    @UnitTest
    fun `Role is authorized`() {
        val accessControl = Role("admin")
        assertTrue(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `Role is not authorized`() {
        val accessControl = Role("viewer")
        assertFalse(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `Permission is authorized`() {
        val accessControl = Permission("read")
        assertTrue(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `Permission is not authorized`() {
        val accessControl = Permission("execute")
        assertFalse(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AllOf is authorized`() {
        val accessControl = AccessControl.allOf(
            Role("admin"),
            Permission("read")
        )
        assertTrue(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AllOf is not authorized`() {
        val accessControl = AccessControl.allOf(
            Role("admin"),
            Permission("execute")
        )
        assertFalse(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AnyOf is authorized`() {
        val accessControl = AccessControl.anyOf(
            Role("viewer"),
            Permission("read")
        )
        assertTrue(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AnyOf is not authorized`() {
        val accessControl = AccessControl.anyOf(
            Role("viewer"),
            Permission("execute")
        )
        assertFalse(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AlwaysAllow is authorized`() {
        assertTrue(isAuthorized(userContext, AlwaysAllow))
    }

    @UnitTest
    fun `AllOf empty list is authorized`() {
        val accessControl = AllOf(emptyList())
        assertTrue(isAuthorized(userContext, accessControl))
    }

    @UnitTest
    fun `AnyOf empty list is not authorized`() {
        val accessControl = AnyOf(emptyList())
        assertFalse(isAuthorized(userContext, accessControl))
    }
}
