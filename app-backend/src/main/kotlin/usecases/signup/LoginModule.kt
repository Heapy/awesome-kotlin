package usecases.signup

import at.favre.lib.crypto.bcrypt.BCrypt
import infra.db.JooqModule
import io.heapy.komok.tech.di.lib.Module

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
