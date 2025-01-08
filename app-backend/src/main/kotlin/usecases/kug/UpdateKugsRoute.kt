package usecases.kug

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import infra.ktor.KtorRoute

class UpdateKugsRoute(
    private val kugDownloadService: KugDownloadService,
) : KtorRoute {
    override fun Route.install() {
        post("/kugs") {
            kugDownloadService.pull()
                .map { section ->
                    section.groups.map { group ->
                        KugCreatePrj(
                            continent = section.section,
                            name = group.name,
                            country = group.country,
                            url = group.url,
                            latitude = group.position?.lat,
                            longitude = group.position?.lng
                        )
                    }
                }
                .flatten()

            call.respond(HttpStatusCode.Accepted)
        }
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
