package usecases.signup

import infra.db.transaction.TransactionContext
import infra.db.transaction.dslContext
import jooq.main.enums.KotlinerStatusEnum
import jooq.main.keys.UNIQUE_KOTLINER_EMAIL
import jooq.main.keys.UNIQUE_KOTLINER_NICKNAME
import jooq.main.tables.references.KOTLINER
import infra.ktor.features.ConstraintViolationException
import infra.ktor.features.ConstraintViolationFields
import org.jooq.exception.DataAccessException
import java.time.OffsetDateTime

class KotlinerDao {
    class LoginView(
        val id: Long,
        val password: CharArray,
    )

    class SaveView(
        val nickname: String,
        val email: String,
        val password: String,
    )

    context(transactionContext: TransactionContext)
    fun get(email: String): LoginView? = transactionContext.dslContext {
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

    context(transactionContext: TransactionContext)
    fun save(kotlin: SaveView) = transactionContext.dslContext {
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
                                SaveView::email.name
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
                                SaveView::nickname.name
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
