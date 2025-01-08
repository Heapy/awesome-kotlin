package usecases.signup

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class GenerateJwt(
    private val jwtConfig: JwtModule.JwtConfig,
) {
    operator fun invoke(
        id: String,
    ): String {
        return JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.issuer)
            .withClaim("id", id)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .sign(Algorithm.HMAC512(jwtConfig.secret))
    }
}
