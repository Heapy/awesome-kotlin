package usecases.signup

import ConfigModule
import io.heapy.komok.tech.di.delegate.bean
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

open class JwtModule(
    private val configModule: ConfigModule,
) {
    open val jwtConfig by bean<JwtConfig> {
        Hocon.decodeFromConfig(configModule.config.value.getConfig("jwt"))
    }

    open val generateJwt by bean {
        GenerateJwt(
            jwtConfig = jwtConfig.value,
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
