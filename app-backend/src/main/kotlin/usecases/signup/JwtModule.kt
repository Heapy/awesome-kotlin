package usecases.signup

import ConfigModule
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import di.Bean1
import di.bean
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import java.util.*

open class JwtModule(
    private val configModule: ConfigModule,
) {
    open val jwtConfig by bean<JwtConfig> {
        Hocon.decodeFromConfig(configModule.config.get.getConfig("jwt"))
    }

    open val generateJwt by bean {
        GenerateJwt(
            jwtConfig = jwtConfig.get,
        )
    }

    @Serializable
    data class JwtConfig(
        val audience: String,
        val realm: String,
        val issuer: String,
        val secret: String,
    )
}

class GenerateJwt(
    private val jwtConfig: JwtModule.JwtConfig,
) : Bean1<String, String> {
    override fun invoke(id: String): String {
        return JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.issuer)
            .withClaim("id", id)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .sign(Algorithm.HMAC512(jwtConfig.secret))
    }
}
