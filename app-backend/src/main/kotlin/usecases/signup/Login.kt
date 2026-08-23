package usecases.signup

import JooqModule
import at.favre.lib.crypto.bcrypt.BCrypt
import io.heapy.komok.tech.di.lib.Module
import io.ktor.http.Cookie
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import ktor.KtorRoute
import ktor.plugins.AuthenticationException
import kotlin.time.Duration.Companion.days

@Module
class LoginModule(
    private val jooqModule: JooqModule,
    private val jwtModule: JwtModule,
) {
    val bcryptVerifier: BCrypt.Verifyer by lazy {
        BCrypt.verifyer()
    }

    val kotlinerDao by lazy {
        DefaultKotlinerDao(
            dslContext = jooqModule.dslContext,
        )
    }

    val route by lazy {
        LoginRoute(
            generateJwt = jwtModule.generateJwt,
            bcryptVerifier = bcryptVerifier,
            kotlinerDao = kotlinerDao,
        )
    }
}

class LoginRoute(
    private val generateJwt: GenerateJwt,
    private val bcryptVerifier: BCrypt.Verifyer,
    private val kotlinerDao: KotlinerDao,
) : KtorRoute {
    override fun Routing.install() {
        post("/login") {
            val request = call.receive<LoginBody>()
            val db = kotlinerDao.get(request.email)

            db ?: throw AuthenticationException()

            val result = bcryptVerifier.verify(
                request.password,
                db.password
            )

            if (result.verified) {
                val token = generateJwt(db.id.toString())
                call.response.cookies.append(Cookie(
                    name ="token",
                    value = token,
                    secure = false,
                    httpOnly = true,
                    maxAge = 30.days.inWholeSeconds.toInt(),
                    path = "/",
                    extensions = mapOf(
                        "SameSite" to "Strict",
                    )
                ))
                call.respond(HttpStatusCode.Accepted)
            } else {
                throw AuthenticationException()
            }
        }
    }

    @Serializable
    data class LoginBody(
        val email: String,
        val password: CharArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LoginBody

            if (email != other.email) return false
            if (!password.contentEquals(other.password)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = email.hashCode()
            result = 31 * result + password.contentHashCode()
            return result
        }
    }
}
