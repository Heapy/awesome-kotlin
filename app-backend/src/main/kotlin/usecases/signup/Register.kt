package usecases.signup

import infra.db.JooqModule
import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies
import io.heapy.komok.tech.di.lib.Module
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import infra.ktor.KtorRoute
import io.ktor.server.routing.Route
import java.security.SecureRandom

@Module
open class RegisterModule(
    private val jooqModule: JooqModule,
) {
    open val bcryptHasher by lazy<BCrypt.Hasher> {
        BCrypt.with(
            BCrypt.Version.VERSION_2A,
            SecureRandom(),
            LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)
        )
    }

    open val kotlinerDao by lazy<KotlinerDao> {
        DefaultKotlinerDao(jooqModule.dslContext)
    }

    open val route by lazy {
        RegisterRoute(
            bcryptHasher = bcryptHasher,
            kotlinerDao = kotlinerDao,
        )
    }
}

class RegisterRoute(
    private val bcryptHasher: BCrypt.Hasher,
    private val kotlinerDao: KotlinerDao,
) : KtorRoute {
    override fun Route.install() {
        post("/register") {
            val request = call.receive<RegisterBody>()

            val password = withContext(Dispatchers.IO) {
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
