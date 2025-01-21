package usecases.signup

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies
import infra.db.JooqModule
import io.heapy.komok.tech.di.lib.Module
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
