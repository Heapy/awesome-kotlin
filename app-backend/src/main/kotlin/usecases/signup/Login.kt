package usecases.signup

import JooqModule
import at.favre.lib.crypto.bcrypt.BCrypt
import di.bean
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import ktor.KtorRoute
import ktor.plugins.AuthenticationException
import kotlin.time.Duration.Companion.days

open class LoginModule(
    private val jooqModule: JooqModule,
    private val jwtModule: JwtModule,
) {
    open val bcryptVerifier by bean<BCrypt.Verifyer> {
        BCrypt.verifyer()
    }

    open val kotlinerDao by bean {
        DefaultKotlinerDao(
            dslContext = jooqModule.dslContext.get,
        )
    }

    open val route by bean {
        LoginRoute(
            generateJwt = jwtModule.generateJwt.get,
            bcryptVerifier = bcryptVerifier.get,
            kotlinerDao = kotlinerDao.get,
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
