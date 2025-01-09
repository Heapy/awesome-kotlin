package usecases.signup

import infra.config.ConfigModule
import infra.config.decode
import infra.jwt.Jwt
import io.heapy.komok.tech.di.lib.Module
import io.heapy.komok.tech.time.TimeSourceModule
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Module
open class JwtModule(
    private val configModule: ConfigModule,
    private val timeSourceModule: TimeSourceModule,
) {
    open val jwtConfig: JwtConfig by lazy {
        configModule.decode("jwt", JwtConfig.serializer())
    }

    open val jwt by lazy {
        Jwt(
            timeSource = timeSourceModule.timeSource,
            secret = jwtConfig.secret,
            json = Json,
        )
    }

    @Serializable
    data class JwtConfig(
        val secret: String,
    )
}
