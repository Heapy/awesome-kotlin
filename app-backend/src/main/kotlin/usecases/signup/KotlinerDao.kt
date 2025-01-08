package usecases.signup

import jooq.main.enums.KotlinerStatusEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import jooq.main.keys.UNIQUE_KOTLINER_EMAIL
import jooq.main.keys.UNIQUE_KOTLINER_NICKNAME
import jooq.main.tables.references.KOTLINER
import infra.ktor.features.ConstraintViolationException
import infra.ktor.features.ConstraintViolationFields
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import usecases.signup.KotlinerDao.LoginView
import java.time.OffsetDateTime

interface KotlinerDao {
    suspend fun get(email: String): LoginView?
    suspend fun save(kotlin: SaveView)

    class LoginView(
        val id: Long,
        val password: CharArray,
    )

    class SaveView(
        val nickname: String,
        val email: String,
        val password: String,
    )
}

class DefaultKotlinerDao(
    private val dslContext: DSLContext,
) : KotlinerDao {
    override suspend fun get(email: String): LoginView? = withContext(Dispatchers.IO) {
        val db = dslContext.select(KOTLINER.ID, KOTLINER.PASSWORD)
            .from(KOTLINER)
            .where(KOTLINER.NORMALIZED_EMAIL.eq(email.normalize()))
            .fetchOne()

        db?.let {
            LoginView(
                id = db.get(KOTLINER.ID)!!,
                password = db.get(KOTLINER.PASSWORD)!!.toCharArray(),
            )
        }
    }

    override suspend fun save(kotlin: KotlinerDao.SaveView) = withContext(Dispatchers.IO) {
        try {
            dslContext
                .insertInto(
                    KOTLINER,
                    KOTLINER.NORMALIZED_EMAIL,
                    KOTLINER.ORIGINAL_EMAIL,
                    KOTLINER.PASSWORD,
                    KOTLINER.NICKNAME,
                    KOTLINER.STATUS,
                    KOTLINER.DESCRIPTION,
                    KOTLINER.CREATED,
                    KOTLINER.UPDATED,
                )
                .values(
                    kotlin.email.normalize(),
                    kotlin.email,
                    kotlin.password,
                    kotlin.nickname,
                    KotlinerStatusEnum.UNVERIFIED,
                    "",
                    OffsetDateTime.now(),
                    OffsetDateTime.now(),
                )
                .execute()

            Unit
        } catch (e: DataAccessException) {
            if (e.message?.contains(UNIQUE_KOTLINER_EMAIL.name) == true) {
                throw ConstraintViolationException(
                    fields = listOf(
                        ConstraintViolationFields(
                            message = UNIQUE_KOTLINER_EMAIL.name,
                            fields = listOf(
                                KotlinerDao.SaveView::email.name
                            ),
                        ),
                    ),
                )
            } else if (e.message?.contains(UNIQUE_KOTLINER_NICKNAME.name) == true) {
                throw ConstraintViolationException(
                    fields = listOf(
                        ConstraintViolationFields(
                            message = UNIQUE_KOTLINER_NICKNAME.name,
                            fields = listOf(
                                KotlinerDao.SaveView::nickname.name
                            ),
                        ),
                    ),
                )
            } else {
                throw e
            }
        }
    }

    internal fun String.normalize(): String {
        return this.lowercase()
    }
}
