package usecases.signup

import at.favre.lib.crypto.bcrypt.BCrypt
import io.heapy.komok.tech.di.lib.Module

@Module
open class LoginModule(
    private val jwtModule: JwtModule,
) {
    open val bcryptVerifier by lazy<BCrypt.Verifyer> {
        BCrypt.verifyer()
    }

    open val kotlinerDao by lazy {
        KotlinerDao()
    }

    open val route by lazy {
        LoginRoute(
            bcryptVerifier = bcryptVerifier,
            kotlinerDao = kotlinerDao,
            jwt = jwtModule.jwt,
        )
    }
}
