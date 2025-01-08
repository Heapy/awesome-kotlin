package usecases.web

import kotlinx.serialization.Serializable

@Serializable
class DeploymentContext(
    /**
     * Internal context, i.e domain/port/protocol in container to build URL for internal services
     */
    val internalContext: WebApplicationContext,
    /**
     * External context, i.e domain/port/protocol in container to build URL for external services
     */
    val externalContext: WebApplicationContext,
)
