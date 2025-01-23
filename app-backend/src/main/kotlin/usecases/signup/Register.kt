package usecases.signup

import at.favre.lib.crypto.bcrypt.BCrypt
import infra.db.transaction.TransactionContext
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import infra.ktor.auth.UserContext
import infra.ktor.easy.EasyKtorRoute
import io.ktor.http.HttpMethod
import io.ktor.server.routing.RoutingContext

class RegisterRoute(
    private val bcryptHasher: BCrypt.Hasher,
    private val kotlinerDao: KotlinerDao,
) : EasyKtorRoute {
    override val method = HttpMethod.Post
    override val path = "/register"

    context(_: TransactionContext, _: UserContext)
    override suspend fun RoutingContext.handle() {
        val request = call.receive<RegisterBody>()

        val password = withContext(Dispatchers.Default) {
            bcryptHasher.hashToString(11, request.password)
        }

        kotlinerDao.save(
            KotlinerDao.SaveView(
                nickname = request.nickname,
                email = request.email,
                password = password
            )
        )

        call.respond(HttpStatusCode.OK)
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
}
