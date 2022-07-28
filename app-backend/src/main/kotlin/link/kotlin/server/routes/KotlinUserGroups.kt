package link.kotlin.server.routes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import link.kotlin.server.dao.KugDao

fun Routing.kugs(
    httpClient: HttpClient,
    kugDao: KugDao,
) {
    get("/kugs") {
        call.respond(kugDao.getAll())
    }

    post("/kugs") {
        httpClient
            .get("https://kotlinlang.org/data/user-groups.json")
            .body<List<KotlinUserGroups>>()
            .forEach { kugs ->
                kugs.groups.forEach { kug ->
                    kugDao.create(KugDao.CreateView(
                        continent = kugs.section,
                        name = kug.name,
                        country = kug.country,
                        url = kug.url,
                        latitude = kug.position?.lat,
                        longitude = kug.position?.lng
                    ))
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
