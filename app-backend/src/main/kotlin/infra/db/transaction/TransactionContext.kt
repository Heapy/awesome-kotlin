package infra.db.transaction

import infra.coroutines.Loom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

@TransactionDsl
sealed interface TransactionContext

class JooqTransactionContext(
    val type: TransactionType,
    val dslContext: DSLContext,
) : TransactionContext

object MockTransactionContext : TransactionContext

enum class TransactionType {
    READ_ONLY,
    READ_WRITE,
    NONE,
}

@DslMarker
annotation class TransactionDsl

@TransactionDsl
inline fun <T> TransactionContext.dslContext(
    body: DSLContext.() -> T,
): T {
    return body(dslContext)
}

@TransactionDsl
inline val TransactionContext.dslContext: DSLContext
    get() = when(this) {
        is JooqTransactionContext -> dslContext
        MockTransactionContext -> throw IllegalStateException("MockTransactionContext does not have a DSLContext")
    }

@TransactionDsl
inline val DSLContext.dslContext
    get() = this

interface TransactionProvider {
    @TransactionDsl
    suspend fun <T> transaction(
        type: TransactionType = TransactionType.NONE,
        block: suspend TransactionContext.() -> T,
    ): T
}

class JooqTransactionProvider(
    private val dslContext: DSLContext,
) : TransactionProvider {
    override suspend fun <T> transaction(
        type: TransactionType,
        block: suspend (TransactionContext) -> T,
    ): T = withContext(Dispatchers.Loom) {
        when (type) {
            TransactionType.READ_ONLY -> dslContext.transactionResult { configuration ->
                val transactionDslContext = configuration.dsl()
                transactionDslContext.connection { connection ->
                    connection.isReadOnly = true
                }

                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = transactionDslContext,
                )

                runBlocking {
                    block(transactionContext)
                }
            }

            TransactionType.READ_WRITE -> dslContext.transactionResult { configuration ->
                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = configuration.dsl(),
                )

                runBlocking {
                    block(transactionContext)
                }
            }

            TransactionType.NONE -> {
                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = dslContext,
                )

                runBlocking {
                    block(transactionContext)
                }
            }
        }
    }
}
