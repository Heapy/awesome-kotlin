package usecases.kug

import infra.db.transaction.TransactionContext
import infra.ktor.auth.UserContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import kotlinx.serialization.Serializable

class UpdateKugsRoute(
    private val kugDownloadService: KugDownloadService,
) : EasyKtorRoute {
    override val path = "/api/kugs"

    override val method = HttpMethod.Post

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        kugDownloadService.pull()
            .flatMap { section ->
                section.groups.map { group ->
                    CreateKugRequest(
                        continent = section.section,
                        name = group.name,
                        country = group.country,
                        url = group.url,
                        latitude = group.position?.lat,
                        longitude = group.position?.lng
                    )
                }
            }

        call.respond(HttpStatusCode.Accepted)
    }
}


@Serializable
data class KotlinUserGroups(
    val section: String,
    val anchorId: String,
    val groups: List<KotlinUserGroup>
)

@Serializable
data class KotlinUserGroup(
    val name: String,
    val country: String,
    val url: String,
    val position: KotlinUserGroupPosition? = null,
)

@Serializable
data class KotlinUserGroupPosition(
    val lat: Double,
    val lng: Double,
)
