package link.kotlin.server.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import link.kotlin.server.jooq.main.keys.UNIQUE_KOTLINER_EMAIL
import link.kotlin.server.jooq.main.keys.UNIQUE_KOTLINER_NICKNAME
import link.kotlin.server.jooq.main.tables.references.KOTLINER
import link.kotlin.server.plugins.ConstraintViolationException
import link.kotlin.server.plugins.ConstraintViolationFields
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException

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
    override suspend fun get(email: String): KotlinerDao.LoginView? = withContext(Dispatchers.IO) {
        val db = dslContext.select(KOTLINER.ID, KOTLINER.PASSWORD)
            .from(KOTLINER)
            .where(KOTLINER.NORMALIZED_EMAIL.eq(email.normalize()))
            .fetchOne()

        db?.let {
            KotlinerDao.LoginView(
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
                    KOTLINER.DESCRIPTION,
                )
                .values(
                    kotlin.email.normalize(),
                    kotlin.email,
                    kotlin.password,
                    kotlin.nickname,
                    "",
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
