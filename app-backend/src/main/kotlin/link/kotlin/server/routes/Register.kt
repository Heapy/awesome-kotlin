package link.kotlin.server.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import link.kotlin.server.dao.KotlinerDao
import link.kotlin.server.dao.KotlinerDao.*

fun Routing.register(
    bcryptHasher: BCrypt.Hasher,
    kotlinerDao: KotlinerDao,
) {
    post("/register") {
        val request = call.receive<RegisterBody>()

        val password = withContext(Dispatchers.IO) {
            bcryptHasher.hashToString(11, request.password)
        }

        kotlinerDao.save(SaveView(
            nickname = request.nickname,
            email = request.email,
            password = password
        ))

        call.respond(HttpStatusCode.OK)
    }
}

@Serializable
data class RegisterBody(
    val nickname: String,
    val email: String,
    val password: CharArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegisterBody

        if (nickname != other.nickname) return false
        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nickname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.contentHashCode()
        return result
    }
}
