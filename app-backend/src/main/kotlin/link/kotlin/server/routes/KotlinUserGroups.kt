package link.kotlin.server.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import link.kotlin.server.dao.KugCreatePrj
import link.kotlin.server.dao.KugDao
import link.kotlin.server.kug.KugDownloadService

fun Routing.kugs(
    kugDownloadService: KugDownloadService,
    kugDao: KugDao,
) {
    get("/kugs") {
        call.respond(kugDao.getAll())
    }

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
