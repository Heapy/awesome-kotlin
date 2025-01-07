package infra.ktor.auth

import io.heapy.komok.tech.di.lib.Module
import usecases.signup.JwtModule

@Module
open class KtorAuthModule(
    private val jwtModule: JwtModule,
) {
    open val ktorAuth by lazy {
        KtorAuth(
            jwt = jwtModule.jwt,
        )
    }
}
