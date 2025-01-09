package usecases.signup

import infra.db.JooqModule
import at.favre.lib.crypto.bcrypt.BCrypt
import infra.jwt.Jwt
import io.heapy.komok.tech.di.lib.Module
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import infra.ktor.KtorRoute
import infra.ktor.features.AuthenticationException
import io.ktor.server.routing.Route
import kotlin.time.Duration.Companion.days

@Module
open class LoginModule(
    private val jooqModule: JooqModule,
    private val jwtModule: JwtModule,
) {
    open val bcryptVerifier by lazy<BCrypt.Verifyer> {
        BCrypt.verifyer()
    }

    open val kotlinerDao by lazy {
        DefaultKotlinerDao(
            dslContext = jooqModule.dslContext,
        )
    }

    open val route by lazy {
        LoginRoute(
            bcryptVerifier = bcryptVerifier,
            kotlinerDao = kotlinerDao,
            jwt = jwtModule.jwt,
        )
    }
}

class LoginRoute(
    private val bcryptVerifier: BCrypt.Verifyer,
    private val kotlinerDao: KotlinerDao,
    private val jwt: Jwt,
) : KtorRoute {
    override fun Route.install() {
        post("/api/login") {
            val request = call.receive<LoginBody>()
            val db = kotlinerDao.get(request.email)

            db ?: throw AuthenticationException()

            val result = bcryptVerifier.verify(
                request.password,
                db.password
            )

            if (result.verified) {
                val token = jwt.generateTokenDefaultDuration(db.id.toString())
                call.response.cookies.append(Cookie(
                    name ="token",
                    value = token,
                    secure = true,
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
